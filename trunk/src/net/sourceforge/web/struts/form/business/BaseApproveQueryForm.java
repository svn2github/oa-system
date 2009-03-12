/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.web.struts.form.business;

import net.sourceforge.model.metadata.ApproveStatus;
import net.sourceforge.web.struts.form.BaseSessionQueryForm;


/**
 * ≤È—ØapproveµƒForm¿‡ 
 * @author ych
 * @version 1.0 (Nov 15, 2005)
 */
public class BaseApproveQueryForm extends BaseSessionQueryForm {
    
    private String code;
    
    private String title;
    
    private String approveStatus;
    
    private String submitDateFrom;
    
    private String submitDateTo;

    

    /**
     * @return Returns the approveStatus.
     */
    public String getApproveStatus() {
        return approveStatus;
    }

    /**
     * @param approveStatus The approveStatus to set.
     */
    public void setApproveStatus(String approveStatus) {
        this.approveStatus = approveStatus;
    }

   
    /**
     * @return Returns the code.
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code The code to set.
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @return Returns the submitDateFrom.
     */
    public String getSubmitDateFrom() {
        return submitDateFrom;
    }

    /**
     * @param submitDateFrom The submitDateFrom to set.
     */
    public void setSubmitDateFrom(String submitDateFrom) {
        this.submitDateFrom = submitDateFrom;
    }

    /**
     * @return Returns the submitDateTo.
     */
    public String getSubmitDateTo() {
        return submitDateTo;
    }

    /**
     * @param submitDateTo The submitDateTo to set.
     */
    public void setSubmitDateTo(String submitDateTo) {
        this.submitDateTo = submitDateTo;
    }

    /**
     * @return Returns the title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title The title to set.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    
    public BaseApproveQueryForm()
    {
        this.setApproveStatus(ApproveStatus.WAITING_FOR_APPROVE.getEnumCode().toString());
    }
    
    
}
