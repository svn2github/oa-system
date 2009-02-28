/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.web.struts.form.business.ta;


import com.aof.model.business.ta.query.TravelApplicationApproveRequestQueryOrder;
import com.aof.web.struts.form.business.BaseApproveQueryForm;

/**
 * 查询Travel Application的ApproveRequest的Form类
 * @author ych
 * @version 1.0 (Dec 4, 2005)
 */
public class TravelApplicationApproveRequestQueryForm extends BaseApproveQueryForm {
    
    private String urgent;
    
    private String requestor;

    /**
     * @return Returns the requestor.
     */
    public String getRequestor() {
        return requestor;
    }

    /**
     * @param requestor The requestor to set.
     */
    public void setRequestor(String requestor) {
        this.requestor = requestor;
    }

    /**
     * @return Returns the urgent.
     */
    public String getUrgent() {
        return urgent;
    }

    /**
     * @param urgent The urgent to set.
     */
    public void setUrgent(String urgent) {
        this.urgent = urgent;
    }


    public TravelApplicationApproveRequestQueryForm() {
        this.setOrder(TravelApplicationApproveRequestQueryOrder.TA_CODE.getName());
        this.setDescend(true);
    }
    
    
    
    
}
