/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.service.admin.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.aof.dao.admin.SupplierDAO;
import com.aof.dao.admin.SupplierHistoryDAO;
import com.aof.model.admin.Currency;
import com.aof.model.admin.PurchaseSubCategory;
import com.aof.model.admin.Site;
import com.aof.model.admin.Supplier;
import com.aof.model.admin.SupplierHistory;
import com.aof.model.admin.query.SupplierQueryCondition;
import com.aof.model.admin.query.SupplierQueryOrder;
import com.aof.model.metadata.EnabledDisabled;
import com.aof.model.metadata.SupplierConfirmStatus;
import com.aof.model.metadata.SupplierPromoteStatus;
import com.aof.model.metadata.YesNo;
import com.aof.service.BaseManager;
import com.aof.service.admin.SupplierManager;
import com.aof.web.struts.action.ActionUtils;

/**
 * SupplierItemManager的实现
 * 
 * @author ych
 * @version 1.0 (Nov 17, 2005)
 */
public class SupplierManagerImpl extends BaseManager implements SupplierManager {

    private SupplierDAO dao;

    private SupplierHistoryDAO supplierHistoryDao;

    private static String lastCode = null;

    private String getIncCode(String lastCode) {
        String tpStr = "000000" + ((new Integer(lastCode.substring(2, 8))).intValue() + 1);
        return "SP" + tpStr.substring(tpStr.length() - 6);
    }

    private synchronized String getLastCode(Site site) {
        if (lastCode == null) {
            lastCode = dao.getLastSupplierCode();
        }
        lastCode = getIncCode(lastCode);
        for (int repeatTimes = 0; repeatTimes < 10; repeatTimes++) {
            if (!dao.isCodeUsed(lastCode, site))
                return lastCode;
            lastCode = getIncCode(dao.getLastSupplierCode());
        }
        lastCode = null;
        return lastCode;
    }

    public void setSupplierDAO(SupplierDAO dao) {
        this.dao = dao;
    }

    public void setSupplierHistoryDAO(SupplierHistoryDAO supplierHistoryDao) {
        this.supplierHistoryDao = supplierHistoryDao;
    }

    public Supplier getSupplier(Integer id) {
        return dao.getSupplier(id);
    }

    public Supplier updateSupplier(Supplier supplier) {
        setContractDate(supplier);
        if (supplier.getConfirmed() == YesNo.YES) {
            Supplier oldSupplier = dao.getSupplier(supplier.getId());
            if (!supplier.contentEqual(oldSupplier)) {
                supplier.setConfirmed(YesNo.NO);
                supplier.setConfirmStatus(SupplierConfirmStatus.MODIFY);
            }
        }
        return dao.updateSupplier(supplier);
    }

    public Supplier insertSupplier(Supplier supplier) {
        if (supplier.getSite() == null)
            supplier.setPromoteStatus(SupplierPromoteStatus.GLOBAL);
        else
            supplier.setPromoteStatus(SupplierPromoteStatus.SITE);
        setContractDate(supplier);
        supplier.setConfirmed(YesNo.NO);
        supplier.setConfirmStatus(SupplierConfirmStatus.NEW);
        String supplierCode = supplier.getCode();
        if (supplierCode == null) {
            supplierCode = getLastCode(supplier.getSite());
            if (supplierCode == null)
                throw new RuntimeException("error occurs when supplier get last code");
        }
        supplier.setCode(supplierCode);
        return dao.insertSupplier(supplier);
    }

    public int getSupplierListCount(Map conditions) {
        return dao.getSupplierListCount(conditions);
    }

    public List getSupplierList(Map conditions, int pageNo, int pageSize, SupplierQueryOrder order, boolean descend) {
        return dao.getSupplierList(conditions, pageNo, pageSize, order, descend);
    }

    private void setContractDate(Supplier supplier) {
        if (supplier.getContractStartDate() != null)
            supplier.setContractStartDate(ActionUtils.getStartDate(supplier.getContractStartDate()));
        if (supplier.getContractExpireDate() != null)
            supplier.setContractExpireDate(ActionUtils.getExpireDate(supplier.getContractExpireDate()));
    }

    public void cancelSupplier(Supplier supplier) {
        if (supplier.getConfirmStatus() == SupplierConfirmStatus.NEW) {
            supplier.setEnabled(EnabledDisabled.DISABLED);
        }
        if (supplier.getConfirmStatus() == SupplierConfirmStatus.MODIFY) {
            SupplierHistory supplierHistory = supplierHistoryDao.getSupplierHistory(supplier.getId());
            supplier.copySupplier(supplierHistory);
            supplier.setConfirmed(YesNo.YES);
        }
        dao.updateSupplier(supplier);
    }

    public void confirmSupplier(Supplier supplier) {
        Supplier oldSupplier = dao.getSupplier(supplier.getId());
        oldSupplier.setConfirmed(YesNo.YES);
        oldSupplier.setConfirmDate(new Date());
        supplierHistoryDao.copySupplier(oldSupplier);
        setContractDate(supplier);
        supplier.setConfirmed(YesNo.YES);
        supplier.setConfirmDate(new Date());
        dao.updateSupplier(supplier);
    }

    public Supplier requestPromote(Integer id, String promoteMessage) {
        Supplier supplier = this.getSupplier(id);
        if (!supplier.getPromoteStatus().equals(SupplierPromoteStatus.SITE)) {
            throw new RuntimeException("Promote Status error");
        }
        supplier.setPromoteMessage(promoteMessage);
        supplier.setPromoteStatus(SupplierPromoteStatus.REQUEST);
        supplier.setPromoteDate(new Date());
        this.updateSupplier(supplier);
        return supplier;
    }

    public Supplier responsePromote(Integer id) {
        Supplier supplier = this.getSupplier(id);
        if (!supplier.getPromoteStatus().equals(SupplierPromoteStatus.REQUEST)) {
            throw new RuntimeException("Promote Status error");
        }
        // supplier.setPromoteStatus(SupplierPromoteStatus.RESPONSED);
        // 改为response后直接提升到global级
        supplier.setPromoteStatus(SupplierPromoteStatus.GLOBAL);
        this.updateSupplier(supplier);
        return supplier;
    }

    public void promoteCreate(Supplier supplier) {
        supplier.setPromoteStatus(SupplierPromoteStatus.GLOBAL);
        if (supplierHistoryDao.getSupplierHistory(supplier.getId()) != null)
            supplier.setConfirmStatus(SupplierConfirmStatus.MODIFY);
        else
            supplier.setConfirmStatus(SupplierConfirmStatus.NEW);
        supplier.setConfirmed(YesNo.NO);
        setContractDate(supplier);
        dao.updateSupplier(supplier);
    }

    public void promoteDelete(Supplier supplier) {
        supplier.setPromoteStatus(SupplierPromoteStatus.SITE);
        dao.updateSupplier(supplier);
    }

    public List getEnabledAirTicketSuppliersForSiteAndIncludeGlobal(Site site) {
        Map conds = new HashMap();
        conds.put(SupplierQueryCondition.GLOBAL_OR_SITE_ID_EQ, site.getId());
        conds.put(SupplierQueryCondition.ENABLED_EQ, EnabledDisabled.ENABLED.getEnumCode());
        conds.put(SupplierQueryCondition.IS_AIRTICKET, YesNo.YES.getEnumCode());
        return this.getSupplierList(conds, 0, -1, SupplierQueryOrder.NAME, false);
    }
    
    public List getEnabledNonAirTicketSuppliersForSiteAndIncludeGlobal(Site site) {
        Map conds = new HashMap();
        conds.put(SupplierQueryCondition.GLOBAL_OR_SITE_ID_EQ, site.getId());
        conds.put(SupplierQueryCondition.ENABLED_EQ, EnabledDisabled.ENABLED.getEnumCode());
        conds.put(SupplierQueryCondition.IS_AIRTICKET, YesNo.NO.getEnumCode());
        return this.getSupplierList(conds, 0, -1, SupplierQueryOrder.NAME, false);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.aof.service.admin.SupplierManager#isCodeUsed(java.lang.String,
     *      com.aof.model.admin.Site)
     */
    public boolean isCodeUsed(String code, Site site) {
        return dao.isCodeUsed(code, site);
    }

    /* (non-Javadoc)
     * @see com.aof.service.admin.SupplierManager#getSuitableSupplierListForPurchase(com.aof.model.admin.Site, com.aof.model.admin.PurchaseSubCategory, java.util.List)
     */
    public List getSuitableSupplierListForPurchase(Site site, PurchaseSubCategory psc, List exchangeRateList) {
        return dao.getSuitableSupplierListForPurchase(site, psc, exchangeRateList);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.aof.service.admin.SupplierManager#getSuitableSupplierItemListForPurchase(com.aof.model.admin.Supplier,
     *      com.aof.model.admin.PurchaseSubCategory,
     *      com.aof.model.admin.Currency)
     */
    public List getSuitableSupplierItemListForPurchase(Supplier supplier, PurchaseSubCategory psc, Currency currency) {
        return dao.getSuitableSupplierItemListForPurchase(supplier, psc, currency);

    }

}
