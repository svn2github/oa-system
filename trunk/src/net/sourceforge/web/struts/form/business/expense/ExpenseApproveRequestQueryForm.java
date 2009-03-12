/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.web.struts.form.business.expense;

import net.sourceforge.model.business.expense.query.ExpenseApproveRequestQueryOrder;
import net.sourceforge.web.struts.form.business.BaseApproveQueryForm;

/**
 * 查询Expense的ApproveRequest的Form类
 * 
 * @author nicebean
 * @version 1.0 (2005-12-13)
 */
public class ExpenseApproveRequestQueryForm extends BaseApproveQueryForm {
    private String amountFrom;

    private String amountTo;

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

    public ExpenseApproveRequestQueryForm() {
        this.setOrder(ExpenseApproveRequestQueryOrder.EXPENSE_CODE.getName());
        this.setDescend(true);
    }

}
