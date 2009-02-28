/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.model.business.expense;

import java.io.Serializable;

import com.aof.model.BaseAttachmentContent;

/**
 * 该类代表expense_attach表中的一行(仅file_content列)
 * 
 * @author nicebean
 * @version 1.0 (2005-11-18)
 */
public abstract class AbstractExpenseAttachmentContent extends BaseAttachmentContent implements Serializable {

    /** default constructor */
    public AbstractExpenseAttachmentContent() {
    }

    /** minimal constructor */
    public AbstractExpenseAttachmentContent(Integer id) {
        super(id);
    }

    /**
     * Implementation of the equals comparison on the basis of equality of the
     * primary key values.
     * 
     * @param rhs
     * @return boolean
     */
    public boolean equals(Object rhs) {
        if (rhs == null)
            return false;
        if (this == rhs)
            return true;
        if (!(rhs instanceof ExpenseAttachmentContent))
            return false;
        ExpenseAttachmentContent that = (ExpenseAttachmentContent) rhs;
        if (this.getId() != null)
            return this.getId().equals(that.getId());
        return that.getId() == null;
    }

}
