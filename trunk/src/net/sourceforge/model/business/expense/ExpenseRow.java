/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.model.business.expense;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * A class that represents a row in the 'expense_row_det' table.
 * 
 * @author nicebean
 * @version 1.0 (2005-11-18)
 */
public class ExpenseRow extends AbstractExpenseRow implements Serializable {
    /**
     * Simple constructor of ExpenseRow instances.
     */
    public ExpenseRow() {
    }

    /**
     * Constructor of ExpenseRow instances given a simple primary key.
     * 
     * @param id
     */
    public ExpenseRow(Integer id) {
        super(id);
    }

    /* Add customized code below */
    private List expenseCellList=new ArrayList();
    
    private BigDecimal rowSubTotal=new BigDecimal(0d);

    /**
     * @return Returns the expenseCellList.
     */
    public List getExpenseCellList() {
        return expenseCellList;
    }

    /**
     * @param expenseCellList The expenseCellList to set.
     */
    public void setExpenseCellList(List expenseCellList) {
        this.expenseCellList = expenseCellList;
    }

    /**
     * @return Returns the rowSubTotal.
     */
    public BigDecimal getRowSubTotal() {
        rowSubTotal=new BigDecimal(0d);
        for (Iterator itor = this.expenseCellList.iterator(); itor.hasNext();) {
            ExpenseCell cell = (ExpenseCell) itor.next();
            rowSubTotal=rowSubTotal.add(cell.getAmount());
        }
        return rowSubTotal;
    }

    /**
     * @param rowSubTotal The rowSubTotal to set.
     */
    public void setRowSubTotal(BigDecimal rowSubTotal) {
        this.rowSubTotal = rowSubTotal;
    }
    
    
    
    
}
