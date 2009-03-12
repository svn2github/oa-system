/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.web.struts.form.business.po;

import net.sourceforge.web.struts.form.business.BaseApproveQueryForm;

/**
 * 查询PurchaseOrder的ApproveRequest的Form类
 * 
 * @author nicebean
 * @version 1.0 (2007-01-12)
 */
public class PurchaseOrderApproveRequestQueryForm extends BaseApproveQueryForm {
    private String supplier_name;

    private String amountFrom;

    private String amountTo;

    public String getSupplier_name() {
        return supplier_name;
    }

    public void setSupplier_name(String supplier_name) {
        this.supplier_name = supplier_name;
    }

    /**
     * @return Returns the amountFrom.
     */
    public String getAmountFrom() {
        return amountFrom;
    }

    /**
     * @param amountFrom
     *            The amountFrom to set.
     */
    public void setAmountFrom(String amountFrom) {
        this.amountFrom = amountFrom;
    }

    /**
     * @return Returns the amountTo.
     */
    public String getAmountTo() {
        return amountTo;
    }

    /**
     * @param amountTo
     *            The amountTo to set.
     */
    public void setAmountTo(String amountTo) {
        this.amountTo = amountTo;
    }

}

