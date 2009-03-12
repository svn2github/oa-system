/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.service.business.ta.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import net.sourceforge.dao.admin.ExchangeRateDAO;
import net.sourceforge.dao.admin.SupplierItemDAO;
import net.sourceforge.dao.business.po.PurchaseOrderDAO;
import net.sourceforge.dao.business.ta.AirTicketDAO;
import net.sourceforge.dao.business.ta.TravelApplicationDAO;
import net.sourceforge.model.admin.ExchangeRate;
import net.sourceforge.model.admin.Site;
import net.sourceforge.model.admin.Supplier;
import net.sourceforge.model.admin.SupplierItem;
import net.sourceforge.model.admin.User;
import net.sourceforge.model.business.BaseApproveRequest;
import net.sourceforge.model.business.BaseRecharge;
import net.sourceforge.model.business.po.PurchaseOrderItem;
import net.sourceforge.model.business.po.PurchaseOrderItemRecharge;
import net.sourceforge.model.business.ta.AirTicket;
import net.sourceforge.model.business.ta.AirTicketRecharge;
import net.sourceforge.model.business.ta.TravelApplication;
import net.sourceforge.model.business.ta.TravelApplicationApproveRequest;
import net.sourceforge.model.business.ta.TravelApplicationHistory;
import net.sourceforge.model.business.ta.query.TravelApplicationQueryOrder;
import net.sourceforge.model.metadata.AirTicketStatus;
import net.sourceforge.model.metadata.ApproverDelegateType;
import net.sourceforge.model.metadata.EnabledDisabled;
import net.sourceforge.model.metadata.TravelApplicationBookStatus;
import net.sourceforge.model.metadata.TravelApplicationStatus;
import net.sourceforge.model.metadata.TravelApplicationUrgent;
import net.sourceforge.model.metadata.TravellingMode;
import net.sourceforge.model.metadata.YesNo;
import net.sourceforge.service.BaseManager;
import net.sourceforge.service.admin.EmailManager;
import net.sourceforge.service.admin.FunctionManager;
import net.sourceforge.service.admin.SystemLogManager;
import net.sourceforge.service.admin.UserManager;
import net.sourceforge.service.business.ApproveRelativeEmailManager;
import net.sourceforge.service.business.rule.FlowManager;
import net.sourceforge.service.business.ta.TravelApplicationManager;
import net.sourceforge.web.struts.action.ActionUtils;
import com.shcnc.struts.form.converters.DateConverter;
import com.shcnc.utils.UUID;

public class TravelApplicationManagerImpl extends BaseManager implements TravelApplicationManager {

    private TravelApplicationDAO dao;

    private AirTicketDAO airTicketDAO;

    private PurchaseOrderDAO purchaseOrderDAO;

    private ExchangeRateDAO exchangeRateDAO;
    
    private FlowManager flowManager;
    
    private SystemLogManager systemLogManager;
    
    private ApproveRelativeEmailManager approveRelativeEmailManager;

    private EmailManager emailManager;
    
    private UserManager userManager;
    
    private FunctionManager functionManager;
    
    private SupplierItemDAO supplierItemDAO;
    
    
    public void setSystemLogManager(SystemLogManager systemLogManager) {
        this.systemLogManager = systemLogManager;
    }

    public void setApproveRelativeEmailManager(ApproveRelativeEmailManager approveRelativeEmailManager) {
        this.approveRelativeEmailManager = approveRelativeEmailManager;
    }

    public void setSupplierItemDAO(SupplierItemDAO supplierItemDAO) {
        this.supplierItemDAO = supplierItemDAO;
    }

    private String getLastCode(Site site,Date date) {
        StringBuffer sb=new StringBuffer("TA");
        String siteId=site.getId().toString();
        for(int i=0;i<3-siteId.length();i++)
            sb.append('0');
        sb.append(siteId);
        sb.append(StringUtils.right(ActionUtils.get8CharsFromDate(date),6));
        String prefix=sb.toString();
        String maxId=dao.getMaxTravelApplicationIdBeginWith(prefix);
        
        int serialNo=1;
        if(maxId!=null)
        {
            Integer maxSN=ActionUtils.parseInt(StringUtils.right(maxId,5));
            if(maxSN==null)throw new RuntimeException("max serial no. is not digit");
            serialNo=maxSN.intValue()+1;
        }
        String sn=String.valueOf(serialNo);
        for(int i=0;i<5-sn.length();i++)
            sb.append('0');
        sb.append(sn);
        return sb.toString();
    }

    public void setTravelApplicationDAO(TravelApplicationDAO dao) {
        this.dao = dao;
    }

    public TravelApplication getTravelApplication(String id) {
        return dao.getTravelApplication(id);
    }

    public TravelApplication updateTravelApplication(TravelApplication ta) {
        return dao.updateTravelApplication(ta);
    }
    
    public TravelApplication updateTravelApplication(TravelApplication ta, List approveRequestList, boolean isDraft,User user) {
        ta.setBookStatus(TravelApplicationBookStatus.NA);

        if (isDraft) {
            ta.setStatus(TravelApplicationStatus.DRAFT);
            ta.setApproveRequestId(null);
            ta = dao.updateTravelApplication(ta);
        } else {
            ta.setStatus(TravelApplicationStatus.PENDING);
            ta.setRequestDate(new Date());
            ta = dao.updateTravelApplication(ta);
            insertApproveRequests(ta, approveRequestList);
            systemLogManager.generateLog(null,ta,TravelApplication.LOG_ACTION_SUBMIT,user);
            if(ta.getUrgent().equals(TravelApplicationUrgent.URGENT))
            {
                ta.setApproveDate(new Date());
                ta = dao.updateTravelApplication(ta);
                notifyBooker(ta);
            }
            approveRelativeEmailManager.sendApprovalEmail(ta, ApproverDelegateType.TRAVEL_APPLICATION_APPROVER, (BaseApproveRequest)approveRequestList.get(0));
        }

        return ta;
    }

    private void insertApproveRequests(TravelApplication ta, List approveRequestList) {
        ta.setApproveRequestId(UUID.getUUID());
        dao.updateTravelApplication(ta);

        for (Iterator iter = approveRequestList.iterator(); iter.hasNext();) {
            TravelApplicationApproveRequest taar = (TravelApplicationApproveRequest) iter.next();
            taar.setApproveRequestId(ta.getApproveRequestId());
            dao.saveTravelApplicationApproveRequst(taar);
        }

    }

    public TravelApplication insertTravelApplication(TravelApplication ta, List approveRequestList, boolean isDraft,User user) {

        ta.setCreateDate(new Date());
        ta.setId(this.getLastCode(ta.getDepartment().getSite(),ta.getCreateDate()));
        ta.setBookStatus(TravelApplicationBookStatus.NA);
        
        if (isDraft) {
            ta.setStatus(TravelApplicationStatus.DRAFT);
            ta = dao.insertTravelApplication(ta);
        } else {
            ta.setStatus(TravelApplicationStatus.PENDING);
            ta.setRequestDate(new Date());
            ta = dao.insertTravelApplication(ta);
            insertApproveRequests(ta, approveRequestList);
            systemLogManager.generateLog(null,ta,TravelApplication.LOG_ACTION_SUBMIT,user);
            
            if(ta.getUrgent().equals(TravelApplicationUrgent.URGENT))
            {
                ta.setApproveDate(new Date());
                ta=dao.updateTravelApplication(ta);
                notifyBooker(ta);
            }
        }

        return ta;
    }

    public int getTravelApplicationListCount(Map conditions) {
        return dao.getTravelApplicationListCount(conditions);
    }

    public List getTravelApplicationList(Map conditions, int pageNo, int pageSize, TravelApplicationQueryOrder order, boolean descend) {
        return dao.getTravelApplicationList(conditions, pageNo, pageSize, order, descend);
    }

    public TravelApplication getTravelApplicationByApproveRequestId(String approveRequestId) {
        return dao.getTravelApplicationByApproveRequestId(approveRequestId);
    }

    public void setFlowManager(FlowManager flowManager) {
        this.flowManager=flowManager;
    }
    
    public TravelApplication updateAirTickets(String taId,List airTicketList,User user) {
        return this.updateAirTickets(taId, airTicketList, false, user);
    }

    public TravelApplication updateAirTickets(String taId,List airTicketList, boolean setIsOnTravel, User user)
    {
        TravelApplication ta=dao.getTravelApplication(taId);
        checkApprovedOrUrgent(ta);
        if (ta.getBookStatus().equals(TravelApplicationBookStatus.NA))
            throw new RuntimeException("book status is n/a");
        if (!ta.getTravellingMode().equals(TravellingMode.AIR))
            throw new RuntimeException("not air");
        
        List idList=dao.getBookedAirTicketIdList(ta);
        
        for (Iterator iter = airTicketList.iterator(); iter.hasNext();) {
            AirTicket at = (AirTicket) iter.next();
            if(at.getStatus()==null)
            {
                at.setCancelPoItem(null);
                ExchangeRate er = exchangeRateDAO.getExchangeRate(at.getExchangeRate().getId());
                at.setExchangeRate(er);
                at.setExchangeRateValue(er.getExchangeRate());
                at.setPoItem(null);
                at.setTravelApplication(ta);
                at.setStatus(AirTicketStatus.BOOKED);
                airTicketDAO.insertAirTicket(at);
                for (Iterator iterator = at.getRecharges().iterator(); iterator.hasNext();) {
                    AirTicketRecharge atr = (AirTicketRecharge) iterator.next();
                    airTicketDAO.insertAirTicketRecharge(atr);
                }
                systemLogManager.generateLog(null,at,AirTicket.LOG_ACTION_BOOK,user);
            }
            else if(at.getStatus().equals(AirTicketStatus.BOOKED))
            {
                idList.remove(at.getId());
            }
        }
        
        dao.lockTravelApplication(ta);
        for (Iterator iter = idList.iterator(); iter.hasNext();) {
            Integer  id = (Integer ) iter.next();
            airTicketDAO.deleteAirTicket(id);
        }
        
        if(dao.hasBookedAirTicket(ta))
        {
            ta.setBookStatus(TravelApplicationBookStatus.BOOKED);
        }
        else if(dao.hasReceivedAirTicket(ta))
        {
            ta.setBookStatus(TravelApplicationBookStatus.RECEIVED);
        }
        else
        {
            ta.setBooker(null);
            ta.setBookStatus(TravelApplicationBookStatus.NA);
        }
        
        if (setIsOnTravel) {
            ta.setIsOnTravel(YesNo.YES);
        }
        dao.updateTravelApplication(ta);
        //TODO: send email => ta.requestor
        return ta;
    }
    

    public void book(TravelApplication ta, List atList,User user)
    {
        
        if (!ta.getBookStatus().equals(TravelApplicationBookStatus.NA))
            throw new RuntimeException("book status is not n/a");
       
        ta.setBooker(user);
            
        if (ta.getTravellingMode().equals(TravellingMode.AIR)) {
            ta.setBookStatus(TravelApplicationBookStatus.BOOKED);
            dao.updateTravelApplication(ta);

            for (Iterator iter = atList.iterator(); iter.hasNext();) {
                AirTicket at = (AirTicket) iter.next();
                if(at.getStatus()==null)
                {
                    at.setCancelPoItem(null);
                    ExchangeRate er = exchangeRateDAO.getExchangeRate(at.getExchangeRate().getId());
                    at.setExchangeRate(er);
                    at.setExchangeRateValue(er.getExchangeRate());
                    at.setPoItem(null);
                    at.setTravelApplication(ta);
                    at.setStatus(AirTicketStatus.BOOKED);
                    airTicketDAO.insertAirTicket(at);
                    for (Iterator iterator = at.getRecharges().iterator(); iterator.hasNext();) {
                        AirTicketRecharge atr = (AirTicketRecharge) iterator.next();
                        airTicketDAO.insertAirTicketRecharge(atr);
                    }
                    systemLogManager.generateLog(null,at,AirTicket.LOG_ACTION_BOOK,user);
                }
            }
 
        } else {
             ta.setBookStatus(TravelApplicationBookStatus.RECEIVED);
             systemLogManager.generateLog(null,ta,TravelApplication.LOG_ACTION_CONFIRM,user);
        }
        dao.updateTravelApplication(ta);

    }

    private void createPurchaseOrderItem(AirTicket at) {
        PurchaseOrderItem item = this.createPOItem(at);
        item.setUnitPrice(at.getPrice());
        purchaseOrderDAO.insertPurchaseOrderItem(item);

        this.createPOItemRecharges(at, item);
        at.setPoItem(item);
    }

    private void createCancelPurchaseOrderItem(AirTicket at, BigDecimal returnPrice) {
        PurchaseOrderItem item = this.createPOItem(at);
        item.setUnitPrice(returnPrice.negate());
        purchaseOrderDAO.insertPurchaseOrderItem(item);

        this.createPOItemRecharges(at, item);
        at.setCancelPoItem(item);
    }

    private void createPOItemRecharges(AirTicket at, PurchaseOrderItem item) {
        if (at.getIsRecharge().equals(YesNo.YES)) {
            List atRechargeList = airTicketDAO.getAirTicketRechargeList(at);
            for (Iterator iter = atRechargeList.iterator(); iter.hasNext();) {
                BaseRecharge poir = item.createNewRechargeObj();
                poir.copyFrom((BaseRecharge) iter.next());
                purchaseOrderDAO.insertPurchaseOrderItemRecharge((PurchaseOrderItemRecharge) poir);
            }
        }
    }

    private PurchaseOrderItem createPOItem(AirTicket at) {
        TravelApplication ta = at.getTravelApplication();
        PurchaseOrderItem item = new PurchaseOrderItem();
        item.setBuyForDepartment(ta.getDepartment());
        item.setBuyForUser(ta.getRequestor());
        item.setCancelledQuantity(new Integer(0));
        item.setExchangeRate(at.getExchangeRate());
        item.setDueDate(ta.getRequestDate());
        item.setIsRecharge(at.getIsRecharge());
        Supplier supplier = at.getSupplier();
        List supplierItemList = supplierItemDAO.getSupplierItemListBySupplier(supplier);
        for (Iterator itor = supplierItemList.iterator(); itor.hasNext();) {
            SupplierItem supplierItem = (SupplierItem) itor.next();
            if (EnabledDisabled.ENABLED.equals(supplierItem.getEnabled())) {
                item.setItem(supplierItem);
                break;
            }
        }
        if (item.getItem() == null) {
            throw new RuntimeException("cannot find enabled item under air ticket supplier '" + supplier.getName() + "'");
        }

        StringBuffer sb = new StringBuffer();
        sb.append(ta.getId());
        sb.append(',');
        sb.append(at.getFlightNo());
        sb.append(' ');
        DateConverter dateTimeConverter = new DateConverter();
        dateTimeConverter.setFormat("yyyy/MM/dd HH:mm");
        sb.append(dateTimeConverter.getAsString(at.getDepartTime()));

        item.setItemSpec(sb.toString());
        item.setPurchaseOrder(null);
        item.setPurchaseType(at.getPurchaseType());
        item.setQuantity(1);
        item.setReceivedQuantity(new Integer(1));
        item.setReturnedQuantity(new Integer(0));
        item.setSupplier(supplier);
        item.setUnitPrice(at.getPrice());

        return item;
    }

    private void checkApprovedOrUrgent(TravelApplication ta) {
        if (!ta.getStatus().equals(TravelApplicationStatus.APPROVED) && !ta.getUrgent().equals(TravelApplicationUrgent.URGENT))
            throw new RuntimeException("not approved or urgent");
    }

    public AirTicket cancelAirTicket(Integer airTicketId, BigDecimal returnPrice,User user) {
        AirTicket airTicket = airTicketDAO.getAirTicket(airTicketId);
        this.checkReceived(airTicket);

        TravelApplication ta = airTicket.getTravelApplication();
       
        airTicket.setStatus(AirTicketStatus.CANCELLED);
        airTicketDAO.updateAirTicket(airTicket);
        
        
        dao.lockTravelApplication(ta);
        if(dao.hasBookedAirTicket(ta))
        {
            ta.setBookStatus(TravelApplicationBookStatus.BOOKED);
        }
        else if(dao.hasReceivedAirTicket(ta))
        {
            ta.setBookStatus(TravelApplicationBookStatus.RECEIVED);
        }
        else
        {
            //ta.setBooker(null);
            ta.setBookStatus(TravelApplicationBookStatus.NA);
        }
        dao.updateTravelApplication(ta);

        this.createCancelPurchaseOrderItem(airTicket, returnPrice);
        airTicketDAO.updateAirTicket(airTicket);
        
        systemLogManager.generateLog(null,airTicket,AirTicket.LOG_ACTION_CANCELAIRTICKET,user);
        //TODO: send TADeleted.vm => ta.booker
        return airTicket;
    }

    public void deleteAirTicket(Integer airTicketId) {
        AirTicket airTicket = airTicketDAO.getAirTicket(airTicketId);
        checkBooked(airTicket);

        TravelApplication ta = airTicket.getTravelApplication();

        airTicketDAO.deleteAirTicket(airTicket);

        dao.lockTravelApplication(ta);
        if(dao.hasBookedAirTicket(ta))
        {
            ta.setBookStatus(TravelApplicationBookStatus.BOOKED);
        }
        else if(dao.hasReceivedAirTicket(ta))
        {
            ta.setBookStatus(TravelApplicationBookStatus.RECEIVED);
        }
        else
        {
            ta.setBookStatus(TravelApplicationBookStatus.NA);
        }
        dao.updateTravelApplication(ta);
    }

    private void checkReceived(AirTicket at) {
        if (!at.getStatus().equals(AirTicketStatus.RECEIVED))
            throw new RuntimeException("not RECEIVED");
        TravelApplication ta = at.getTravelApplication();
        checkApprovedOrUrgent(ta);

    }

    private void checkBooked(AirTicket at) {
        if (!at.getStatus().equals(AirTicketStatus.BOOKED))
            throw new RuntimeException("not booked");
        TravelApplication ta = at.getTravelApplication();
        checkApprovedOrUrgent(ta);
    }

    public AirTicket confirmAirTicket(Integer atId) {
        AirTicket airTicket = airTicketDAO.getAirTicket(atId);
        checkBooked(airTicket);
        
        airTicket.setStatus(AirTicketStatus.RECEIVED);
        airTicketDAO.updateAirTicket(airTicket);

        TravelApplication ta = airTicket.getTravelApplication();
        dao.lockTravelApplication(ta);

        if(dao.hasBookedAirTicket(ta))
        {
            ta.setBookStatus(TravelApplicationBookStatus.BOOKED);
        }
        else 
        {
            ta.setBookStatus(TravelApplicationBookStatus.RECEIVED);
        }
        dao.updateTravelApplication(ta);

        createPurchaseOrderItem(airTicket);
        airTicketDAO.updateAirTicket(airTicket);
        //TODO: send TAConfirmed.vm => airTicket.getTravelApplication().getBooker()
        return airTicket;
    }

    public void setFunctionManager(FunctionManager functionManager) {
        this.functionManager = functionManager;
    }

    public void setAirTicketDAO(AirTicketDAO airTicketDAO) {
        this.airTicketDAO = airTicketDAO;
    }

    public void setPurchaseOrderDAO(PurchaseOrderDAO purchaseOrderDAO) {
        this.purchaseOrderDAO = purchaseOrderDAO;
    }

    public void setExchangeRateDAO(ExchangeRateDAO exchangeRateDAO) {
        this.exchangeRateDAO = exchangeRateDAO;
    }

    public void deleteTravelApplication(TravelApplication ta,User user) {

        dao.deleteTravelApplication(ta);
        systemLogManager.generateLog(null,ta,TravelApplication.LOG_ACTION_DELETE,user);

    }

    public void rejectTravelApplication(TravelApplication travelApplication, User user, String comment) {
        travelApplication.setStatus(TravelApplicationStatus.REJECTED);
        travelApplication = dao.updateTravelApplication(travelApplication);
        TravelApplicationHistory history = createHistory(travelApplication);
        dao.saveTravelApplicationHistory(history);
        
        systemLogManager.generateLog(null,travelApplication,TravelApplication.LOG_ACTION_REJECT,user);
        approveRelativeEmailManager.sendRejectedEmail(travelApplication, user.getName(), comment);

    }
    
    private TravelApplicationHistory createHistory(TravelApplication ta) {
        TravelApplicationHistory history = new TravelApplicationHistory();
        history.setApproveRequestId(ta.getApproveRequestId());
        history.setCheckInDate(ta.getCheckInDate());
        history.setCheckOutDate(ta.getCheckOutDate());
        history.setFromCity(ta.getFromCity());
        history.setFromDate(ta.getFromDate());
        history.setFromTime(ta.getFromTime());
        history.setHotel(ta.getHotel());
        history.setHotelName(ta.getHotelName());
        history.setPrice(ta.getPrice());
        history.setRoomDescription(ta.getRoomDescription());
        history.setSingleOrReturn(ta.getSingleOrReturn());
        history.setStatus(ta.getStatus());
        history.setToCity(ta.getToCity());
        history.setToDate(ta.getToDate());
        history.setToTime(ta.getToTime());
        history.setTravellingMode(ta.getTravellingMode());
        history.setTravelApplication(ta);
        return history;
    }

    public void assignBooker(String taId, User booker) {
        TravelApplication ta=this.getTravelApplication(taId);
        ta.setBooker(booker);
        dao.updateTravelApplication(ta);
    }


    public void withdrawTravelApplication(TravelApplication ta,User user) {
        if(!ta.getStatus().equals(TravelApplicationStatus.PENDING)) throw new RuntimeException("status error");
        
        ta.setStatus(TravelApplicationStatus.DRAFT);
        ta.setApproveRequestId(null);
        dao.updateTravelApplication(ta);
        approveRelativeEmailManager.deleteWithdrawEmail(ta);
        systemLogManager.generateLog(null,ta,TravelApplication.LOG_ACTION_WITHDRAW,user);
        dao.deleteTravelApplicationApproveRequest(ta);
        
    }

    public void approveTravelApplication(TravelApplication travelApplication, User user) {
        travelApplication.setStatus(TravelApplicationStatus.APPROVED);
        travelApplication.setApproveDate(new Date());
        travelApplication = dao.updateTravelApplication(travelApplication);
        flowManager.executeNotifyFlow(travelApplication);
        approveRelativeEmailManager.sendApprovedEmail(travelApplication);
        
        if(travelApplication.getUrgent().equals(TravelApplicationUrgent.NORMAL))
        {
            notifyBooker(travelApplication);
        }
    }
    
    private void notifyBooker(TravelApplication travelApplication)
    {
        Map context=new HashMap();
        context.put("x_ta",travelApplication);
        List bookerList=userManager.getEnabledUserList(functionManager.getFunction("TravelApplicationPurchase"),travelApplication.getDepartment());
        for (Iterator iter = bookerList.iterator(); iter.hasNext();) {
            User emailToUser = (User) iter.next();
            context.put("x_user",emailToUser);
            context.put("role", EmailManager.EMAIL_ROLE_PURCHASER);
            emailManager.insertEmail(travelApplication.getLogSite(),emailToUser.getEmail(),"PendingTA.vm",context);
        }
    }

    public void setEmailManager(EmailManager emailManager) {
        this.emailManager = emailManager;
    }

    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }

    public List getAirTicketListWithDetails(TravelApplication ta) {
        return dao.getAirTicketListWithDetails(ta);
    }

    public List getAirTicketList(TravelApplication ta) {
        return dao.getAirTicketList(ta);
    }

    public AirTicket getAirTicketWithDetails(Integer id) {
        return dao.getAirTicketWithDetails(id);
    }

   


}
