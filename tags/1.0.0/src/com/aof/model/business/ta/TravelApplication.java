/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

/*
 * Created Fri Sep 23 14:47:57 CST 2005 by MyEclipse Hibernate Tool.
 */
package com.aof.model.business.ta;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.aof.model.Loggable;
import com.aof.model.admin.City;
import com.aof.model.admin.Country;
import com.aof.model.admin.Site;
import com.aof.model.business.Approvable;
import com.aof.model.business.BaseApproveRequest;
import com.aof.model.business.Notifiable;
import com.aof.model.metadata.RuleType;
import com.aof.model.metadata.TravelType;
import com.aof.model.metadata.TravellingMode;
import com.aof.web.struts.action.ActionUtils;
import com.shcnc.struts.form.DateFormatter;

/**
 * A class that represents a row in the 'travel_application' table. 
 */
public class TravelApplication extends AbstractTravelApplication implements Serializable, Approvable, Notifiable,Loggable{
    /**
     * Simple constructor of TravelApplication instances.
     */
    public TravelApplication() {
    }

    /**
     * Constructor of TravelApplication instances given a simple primary key.
     * 
     * @param id
     */
    public TravelApplication(String id) {
        super(id);
    }

    /* Add customized code below */

    /* (non-Javadoc)
     * @see com.aof.model.business.Approvable#createNewApproveRequestObj()
     */
    public BaseApproveRequest createNewApproveRequestObj() {
        return new TravelApplicationApproveRequest();
    }

    /* (non-Javadoc)
     * @see com.aof.model.business.Approvable#getApproveFlowName()
     */
    public String getApproveFlowName() {
        return this.getDepartment().getSite().getId() + RuleType.TRAVEL_APPROVAL_RULES.getPrefixUrl();
    }

    /**
     * 返回TA所属的部门id (审批用)
     * 
     * @return 部门id
     */
    public Integer getApproveDepartment() {
        return this.getDepartment().getId();
    }

    /**
     * 返回TA的TravellingMode (审批用)
     * 
     * @return TravellingMode
     */
    public TravellingMode getApproveTravellingMode() {
        return this.getTravellingMode();
    }

    /**
     * 根据TA的fromCity所在国家和site所在国家计算TravelType (审批用)
     * 
     * @return 如果是同一个城市，返回TravelType.LOCAL，如果是同一国家，返回TravelType.DOMESTIC，否则返回TravelType.OVERSEA
     */
    public TravelType getApproveTravelFrom() {
        City requestorCity = getDepartment().getSite().getCity();
        if (getFromCity().equals(requestorCity)) {
            return TravelType.LOCAL;
        }
        Country fromCountry = getFromCity().getProvince().getCountry();
        Country siteCountry = getDepartment().getSite().getCity().getProvince().getCountry();
        if (fromCountry.equals(siteCountry)) {
            return TravelType.DOMESTIC;
        }
        return TravelType.OVERSEA;
    }

    /**
     * 根据TA的toCity所在国家和site所在国家计算TravelType (审批用)
     * 
     * @return 如果是同一个城市，返回TravelType.LOCAL，如果是同一国家，返回TravelType.DOMESTIC，否则返回TravelType.OVERSEA
     */
    public TravelType getApproveTravelTo() {
        City requestorCity = getDepartment().getSite().getCity();
        if (getToCity().equals(requestorCity)) {
            return TravelType.LOCAL;
        }
        Country toCountry = getToCity().getProvince().getCountry();
        Country siteCountry = getDepartment().getSite().getCity().getProvince().getCountry();
        if (toCountry.equals(siteCountry)) {
            return TravelType.DOMESTIC;
        }
        return TravelType.OVERSEA;
    }
    
    /**
     * 返回TA的Expense Fee (审批用)
     * 
     * @return BigDecimal
     */
    public BigDecimal getApproveAmount() {
        return this.getFee();
    }
    
    public Integer getApproveDurationDay() {
        Date approveDate = getApproveDate();
        if (approveDate == null) return null;
        Date requestDate = getRequestDate();
        if (requestDate == null) return null;
        return new Integer((int)Math.ceil(((double)(approveDate.getTime() - requestDate.getTime())) / 86400000));
    }

    /* (non-Javadoc)
     * @see com.aof.model.business.Notifiable#getNotifyFlowName()
     */
    public String getNotifyFlowName() {
        if(this.getDepartment()==null) return null;
        if(this.getDepartment().getSite()==null) return null;
        return this.getDepartment().getSite().getId() + RuleType.TRAVEL_APPLICATION_FILTERS.getPrefixUrl();
    }

    public String getNotifyEmailTemplateName() {
        return "TAFilter.vm";
    }

    public Map getNotifyEmailContext() {
        Map context = new HashMap();
        context.put("ta", this);
        return context;
    }

    public Site getLogSite() {
        return this.getDepartment().getSite();
    }

    public String getLogTargetName() {
        return LOG_TARGET_NAME;
    }

    public String getLogTargetId() {
        return this.getId();
    }

    public String[][] getLogFieldInfo(String action) {
        return (String[][]) actionFieldInfo.get(action);
    }
    
    private static final String LOG_TARGET_NAME="TA";
    
    public static final String LOG_ACTION_SUBMIT = "Submit";
    public static final String LOG_ACTION_DELETE = "Delete";
    
    public static final String LOG_ACTION_CONFIRM = "Confirm";
    //public static final String LOG_ACTION_CANCELAIRTICKET = "Cancel Air Ticket";    
    public static final String LOG_ACTION_WITHDRAW = "Widthdraw";
    public static final String LOG_ACTION_APPROVE = "Approve";
    public static final String LOG_ACTION_REJECT = "Reject";
    //public static final String LOG_ACTION_BOOK = "Book";
    
    
    
    private static final Map actionFieldInfo = new HashMap();
    static {
        actionFieldInfo.put(LOG_ACTION_SUBMIT, 
                new String[][] { 
                    { "Department", "department", "id" },
                    { "Requestor", "requestor", "name" },
                    { "Urgent Level", "urgent", "label" },
                    { "Travel From(country/province/city)", "fromLocation", null },
                    { "Travel To(country/province/city)", "toLocation", null },
                    { "Travel Mode", "travellingMode", "label" },
                    { "Single Or Return", "singleOrReturn", "label" },
                    { "Travel Duration From ~ To", "duration",null  },
        });
        
        actionFieldInfo.put(LOG_ACTION_CONFIRM, 
                new String[][] { 
                    { "Hotel", "nameOfHotel", null },
                    { "Room", "nameOfRoom", null },
                    { "Check In Date", "checkInDate", null },
                    { "Check Out Date", "checkOutDate", null },
                    { "Travel Mode", "travellingMode", "label" },
        });
        
        actionFieldInfo.put(LOG_ACTION_APPROVE,
                new String[][] { });
        
        actionFieldInfo.put(LOG_ACTION_DELETE,
                new String[][] { });
        
        actionFieldInfo.put(LOG_ACTION_WITHDRAW,
                new String[][] { });
        
        actionFieldInfo.put(LOG_ACTION_REJECT, 
                new String[][] {}
        );
        
    }
    
    public String getNameOfHotel()
    {
        if(this.getHotel()!=null) return this.getHotel().getName();
        return this.getHotelName();
    }
    
    public String getNameOfRoom()
    {
        if(this.getPrice()!=null) return this.getPrice().getDescription();
        return this.getRoomDescription();
    }
    
    public String getFromLocation()
    {
        StringBuffer sb=new StringBuffer();
        if(this.getFromCity()==null) return null;
        sb.append(this.getFromCity().getProvince().getCountry().getEngName());
        sb.append('/');
        sb.append(this.getFromCity().getProvince().getEngName());
        sb.append('/');
        sb.append(this.getFromCity().getEngName());
        return sb.toString();
    }
    
    public String getToLocation()
    {
        
        if(this.getToCity()==null) return null;
        StringBuffer sb=new StringBuffer();
        sb.append(this.getToCity().getProvince().getCountry().getEngName());
        sb.append('/');
        
        sb.append(this.getToCity().getProvince().getEngName());
        sb.append('/');
        sb.append(this.getToCity().getEngName());
        return sb.toString();
    }
    
    public String getDuration()
    {
        StringBuffer sb=new StringBuffer();
        if(this.getFromDate()!=null)
            sb.append(ActionUtils.getDisplayDateFromDate(this.getFromDate()));
        if(this.getFromTime()!=null)
            sb.append(dfDisplayDate.format(this.getFromTime()));
        sb.append("~");
        if(this.getToDate()!=null)
            sb.append(ActionUtils.getDisplayDateFromDate(this.getToDate()));
        if(this.getToTime()!=null)
            sb.append(dfDisplayDate.format(this.getToTime()));
        return sb.toString();
    }
    private static DateFormatter dfDisplayDate = new DateFormatter(
            java.util.Date.class, "HH:mm");

    public String getApprovalBatchEmailTemplateName() {
        return "TAApprovalBatch.vm";
    }
    
    public String getApprovalNotifyEmailTemplateName() {
        return "TAApproval.vm";
    }

    public String getApprovedNotifyEmailTemplateName() {
        return "TAApproved.vm";
    }

    public String getRejectedNotifyEmailTemplateName() {
        return "TARejected.vm";
    }

    public Map getApprovalNotifyEmailContext() {
        Map context = new HashMap();
        context.put("ta", this);
        return context;
    }

    public Map getApprovedNotifyEmailContext() {
        Map context = new HashMap();
        context.put("ta", this);
        return context;
    }

    public Map getRejectedNotifyEmailContext() {
        Map context = new HashMap();
        context.put("ta", this);
        return context;
    }

    public void emailed(Date d) {
        this.setEmailDate(d);
        this.setEmailTimes(this.getEmailTimes()+1);
    }

    public String getRefNo() {
        return this.getId();
    }
}
