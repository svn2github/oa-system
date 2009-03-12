/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.web.struts.form.business.rule;

import net.sourceforge.web.struts.form.BaseSessionQueryForm;


/**
 * ²éÑ¯Approver DelegateµÄForm
 * 
 * @author nicebean
 * @version 1.0 (2005-11-15)
 */
public class ApproverDelegateQueryForm extends BaseSessionQueryForm {

    /* id */
    private String id;
    
    private String fromDate1;
    private String fromDate2;
    private String toDate1;
    private String toDate2;


    public String getFromDate2() {
        return fromDate2;
    }

    public void setFromDate2(String fromDate2) {
        this.fromDate2 = fromDate2;
    }

    public String getFromDate1() {
        return fromDate1;
    }

    public void setFromDate1(String fromDate1) {
        this.fromDate1 = fromDate1;
    }

    public String getToDate1() {
        return toDate1;
    }

    public void setToDate1(String toDate1) {
        this.toDate1 = toDate1;
    }

    public String getToDate2() {
        return toDate2;
    }

    public void setToDate2(String toDate2) {
        this.toDate2 = toDate2;
    }

    /**
     * @return Returns the id.
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     *            The id to set.
     */
    public void setId(String id) {
        this.id = id;
    }

    /* keyPropertyList */

    /* kmtoIdList */

    /* mtoIdList */
    private String originalApprover_id;

    private String delegateApprover_id;

    /**
     * @return Returns the delegateApprover_id.
     */
    public String getDelegateApprover_id() {
        return delegateApprover_id;
    }

    /**
     * @param delegateApprover_id
     *            The delegateApprover_id to set.
     */
    public void setDelegateApprover_id(String delegateApprover_id) {
        this.delegateApprover_id = delegateApprover_id;
    }

    /**
     * @return Returns the originalApprover_id.
     */
    public String getOriginalApprover_id() {
        return originalApprover_id;
    }

    /**
     * @param originalApprover_id
     *            The originalApprover_id to set.
     */
    public void setOriginalApprover_id(String originalApprover_id) {
        this.originalApprover_id = originalApprover_id;
    }

    /* property */
    private String type;

    private String fromDate;

    private String toDate;

    /**
     * @return Returns the fromDate.
     */
    public String getFromDate() {
        return fromDate;
    }

    /**
     * @param fromDate
     *            The fromDate to set.
     */
    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    /**
     * @return Returns the toDate.
     */
    public String getToDate() {
        return toDate;
    }

    /**
     * @param toDate
     *            The toDate to set.
     */
    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    /**
     * @return Returns the type.
     */
    public String getType() {
        return type;
    }

    /**
     * @param type
     *            The type to set.
     */
    public void setType(String type) {
        this.type = type;
    }
}
