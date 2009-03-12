/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.service.business.ta.impl;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import net.sourceforge.dao.business.po.PurchaseOrderDAO;
import net.sourceforge.dao.business.ta.AirTicketDAO;
import net.sourceforge.dao.business.ta.TravelApplicationDAO;
import net.sourceforge.model.admin.ExchangeRate;
import net.sourceforge.model.admin.Site;
import net.sourceforge.model.admin.Supplier;
import net.sourceforge.model.admin.User;
import net.sourceforge.model.business.po.PurchaseOrder;
import net.sourceforge.model.business.po.PurchaseOrderItem;
import net.sourceforge.model.business.ta.AirTicket;
import net.sourceforge.model.business.ta.TravelApplication;
import net.sourceforge.model.business.ta.query.AirTicketQueryCondition;
import net.sourceforge.model.business.ta.query.AirTicketQueryOrder;
import net.sourceforge.model.metadata.AirTicketStatus;
import net.sourceforge.model.metadata.ExportStatus;
import net.sourceforge.model.metadata.PurchaseOrderStatus;
import net.sourceforge.model.metadata.TravelApplicationBookStatus;
import net.sourceforge.service.BaseManager;
import net.sourceforge.service.admin.SystemLogManager;
import net.sourceforge.service.business.ta.AirTicketManager;
import net.sourceforge.web.struts.action.ActionUtils;

/**
 * service implement for domain model air ticket
 * 
 * @author shi1
 * @version 1.0 (Nov 25, 2005)
 */
public class AirTicketManagerImpl extends BaseManager implements AirTicketManager {
    private AirTicketDAO dao;
    
    private TravelApplicationDAO taDao;

    private PurchaseOrderDAO purchaseOrderDAO;
    
    private SystemLogManager systemLogManager;
    
    public void setAirTicketDAO(AirTicketDAO dao) {
        this.dao = dao;
    }
    
    public void setTravelApplicationDAO(TravelApplicationDAO taDao) {
        this.taDao = taDao;
    }

    /**
     * @param purchaseOrderDAO
     *            The purchaseOrderDAO to set.
     */
    public void setPurchaseOrderDAO(PurchaseOrderDAO purchaseOrderDAO) {
        this.purchaseOrderDAO = purchaseOrderDAO;
    }

    public void setSystemLogManager(SystemLogManager systemLogManager) {
        this.systemLogManager = systemLogManager;
    }

    public AirTicket getAirTicket(Integer id) {
        return dao.getAirTicket(id);
    }

    public AirTicket updateAirTicket(AirTicket function) {
        return dao.updateAirTicket(function);
    }

    public AirTicket insertAirTicket(AirTicket function) {
        return dao.insertAirTicket(function);
    }
    
    public void deleteAirTicket(Integer id, String taId)
    {                
        dao.deleteAirTicket(id);        
        Map conds = new HashMap();
        conds.put(AirTicketQueryCondition.TRAVELAPPLICATION_ID_EQ, taId);
        conds.put(AirTicketQueryCondition.STATUS_NOTEQ, AirTicketStatus.CANCELLED);

        List l = this.getAirTicketList(conds, 0, -1, null, false);
        
        if (l == null || l.size() == 0)
        {
            TravelApplication ta = taDao.getTravelApplication(taId);
            ta.setBookStatus(TravelApplicationBookStatus.NA);
            taDao.updateTravelApplication(ta);
        }
    }

    public int getAirTicketListCount(Map conditions) {
        return dao.getAirTicketListCount(conditions);
    }

    public List getAirTicketList(Map conditions, int pageNo, int pageSize, AirTicketQueryOrder order, boolean descend) {
        return dao.getAirTicketList(conditions, pageNo, pageSize, order, descend);
    }

    public AirTicket getTravelApplicationAirTicket(TravelApplication ta) {
        Map conds = new HashMap();
        conds.put(AirTicketQueryCondition.TRAVELAPPLICATION_ID_EQ, ta.getId());
        conds.put(AirTicketQueryCondition.STATUS_NOTEQ, AirTicketStatus.CANCELLED);

        List l = this.getAirTicketList(conds, 0, -1, null, false);

        if (l.size() == 0)
            return null;
        else if (l.size() == 1)
            return (AirTicket) l.get(0);
        else
            throw new RuntimeException("multiple airticket for one travelApplication");
    }

    public List getAirTicketRechargeList(AirTicket at) {
        return dao.getAirTicketRechargeList(at);
    }

    public List getReceivedAirTicketList(Map conditions) {
        return dao.getReceivedAirTicketList(conditions);
    }

    public List getSiteReceivedAirTicketPoItemIdList(Site s) {
        return dao.getSiteReceivedAirTicketPoItemIdList(s);
    }

    private String getNewPurchaseOrderCode(Site site, Date date) {
        StringBuffer sb = new StringBuffer("PO");
        String siteId = site.getId().toString();
        for (int i = 0; i < 3 - siteId.length(); i++)
            sb.append('0');
        sb.append(siteId);
        sb.append(StringUtils.right(ActionUtils.get8CharsFromDate(date), 6));
        String prefix = sb.toString();
        String maxId = purchaseOrderDAO.getMaxPurchaseOrderIdBeginWith(prefix);

        int serialNo = 1;
        if (maxId != null) {
            Integer maxSN = ActionUtils.parseInt(StringUtils.right(maxId, 5));
            if (maxSN == null)
                throw new RuntimeException("max serial no. is not digit");
            serialNo = maxSN.intValue() + 1;
        }
        String sn = String.valueOf(serialNo);
        for (int i = 0; i < 5 - sn.length(); i++)
            sb.append('0');
        sb.append(sn);
        return sb.toString();
    }

    public void createMergePO(Site s, User currentUser, Collection ids) {
        Map supplierMap = new HashMap();
        Date today = new Date();
        for (Iterator itor = ids.iterator(); itor.hasNext();) {
            Integer id = (Integer) itor.next();
            PurchaseOrderItem poi = purchaseOrderDAO.getPurchaseOrderItem(id);
            if (poi == null) {
                throw new RuntimeException("cannot found purchase order item with id " + id);
            }
            if (poi.getPurchaseOrder() != null) {
                throw new RuntimeException("Purchase order item with id " + id + " has already associated with purchase order " + poi.getPurchaseOrder().getId());
            }
            Supplier supplier = poi.getSupplier();
            Map exchangeRateMap = (Map) supplierMap.get(supplier);
            if (exchangeRateMap == null) {
                exchangeRateMap = new HashMap();
                supplierMap.put(supplier, exchangeRateMap);
            }
            ExchangeRate exchangeRate = poi.getExchangeRate();
            PurchaseOrder po = (PurchaseOrder) exchangeRateMap.get(exchangeRate);
            if (po == null) {
                po = new PurchaseOrder(getNewPurchaseOrderCode(s, today));
                exchangeRateMap.put(exchangeRate, po);
                po.setAmount(new BigDecimal(0d));
                po.setBaseCurrency(s.getBaseCurrency());
                po.setCreateDate(new Date());
                po.setCreateUser(currentUser);
                po.setExchangeRate(exchangeRate);
                po.setExchangeRateValue(exchangeRate.getExchangeRate());
                po.setExportStatus(ExportStatus.UNEXPORTED);
                po.setPurchaser(currentUser);
                po.setSite(s);
                po.setStatus(PurchaseOrderStatus.APPROVED);
                po.setSupplier(supplier);
                po.setTitle("Air Ticket");
                purchaseOrderDAO.insertPurchaseOrder(po);
            }
            po.setAmount(po.getAmount().add(poi.getAmount()));
            poi.setPurchaseOrder(po);
            purchaseOrderDAO.updatePurchaseOrderItem(poi);
        }
        for (Iterator itor = supplierMap.values().iterator(); itor.hasNext();) {
            Map exchangeRateMap = (Map) itor.next();
            for (Iterator itor2 = exchangeRateMap.values().iterator(); itor2.hasNext();) {
                PurchaseOrder po = (PurchaseOrder) itor2.next();
                purchaseOrderDAO.updatePurchaseOrder(po);
                systemLogManager.generateLog(null, po, PurchaseOrder.LOG_ACTION_CREATE, currentUser);
            }
        }
    }

}
