/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.service.dataTransfer.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.LogFactory;

import com.aof.model.admin.Site;
import com.aof.model.business.expense.Expense;
import com.aof.model.business.expense.ExpenseCell;
import com.aof.model.business.expense.ExpenseClaim;
import com.aof.model.business.expense.ExpenseRecharge;
import com.aof.model.metadata.CustomerType;
import com.aof.model.metadata.ExportStatus;
import com.aof.model.metadata.YesNo;

/**
 * 导出Expense的类
 * @author ych
 * @version 1.0 (Dec 23, 2005)
 */
public class ExportExpenseManagerImpl extends BaseExportManager {

    private static final String EXPENSE_MASTER="1";
    private static final String EXPENSE_DETAIL="2";
    private static final String EXPENSE_RECHARGE="3";
    
    
    public ExportExpenseManagerImpl() {
        super();
        log = LogFactory.getLog(ExportExpenseManagerImpl.class);
        exportClassName="Expense";
        folder="expense";
    }

    public String exportTextFile(Site site) throws Exception {
        List expenseList=dao.getExpenseList(site);
        if (expenseList.size()>0) {
            try {
                File tempFile=new File(getLocalFileName("expense","txt"));
                OutputStreamWriter writer=new OutputStreamWriter(new FileOutputStream(tempFile),FILE_ENCODE);
                for (Iterator itorExpense = expenseList.iterator(); itorExpense.hasNext();) {
                    Expense expense=(Expense) itorExpense.next();
                    List expenseDetailList=dao.getExpenseDetailList(expense);
                    List expenseRechargeList=dao.getExpenseRechargeList(expense);
                    writeExpense(writer,expense,!expenseRechargeList.isEmpty());
                    if (expenseRechargeList.size()==0) {
                        for (Iterator itorDetail = expenseDetailList.iterator(); itorDetail.hasNext();) {
                            ExpenseCell cell = (ExpenseCell) itorDetail.next();
                            writeExpenseDetail(writer,cell,YesNo.NO,expense.getDepartment().getId(),expense.getRequestor().getId(),null,null,true);
                        }
                    } else {
//                        for (Iterator itor = expenseRechargeList.iterator(); itor.hasNext();) {
//                            ExpenseRecharge recharge = (ExpenseRecharge) itor.next();
//                            recharge.setTotalAmount(expense.getConfirmedAmount());
//                            if (recharge.getPerson()!=null || recharge.getPersonDepartment()!=null) {
//                                //3 Department;4 Person
//                                for (Iterator itorDetail = expenseDetailList.iterator(); itorDetail.hasNext();) {
//                                    ExpenseCell cell = (ExpenseCell) itorDetail.next();
//                                    writeExpenseDetail(writer,cell,YesNo.NO,(Integer)getValue(recharge,"personDepartment.id"),(Integer)getValue(recharge,"person.id"),recharge.getPercentage(),recharge,!itorDetail.hasNext());
//                                }
//                            } else {
//                                //1 Customer;2 Entity
//                                for (Iterator itorDetail = expenseDetailList.iterator(); itorDetail.hasNext();) {
//                                    ExpenseCell cell = (ExpenseCell) itorDetail.next();
//                                    writeExpenseRecharge(writer,recharge, cell, !itorDetail.hasNext());
//                                    //export to customer not fill personId -- nicebean 07-01-12
//                                    writeExpenseDetail(writer,cell,YesNo.YES,expense.getDepartment().getId(),null,recharge.getPercentage(),recharge,!itorDetail.hasNext());
//                                }
//                            }
//                        }
                    }
                    expense.setExportStatus(ExportStatus.EXPORTED);
                    dao.updateExpense(expense);
                }
                writer.close();
                return tempFile.getPath();
            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
        } else {
            return "";
        }
    }

    public String exportXmlFile(Site site) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }
    
    private void writeExpenseRecharge(OutputStreamWriter writer, ExpenseRecharge recharge, ExpenseClaim claim, boolean lastClaim) throws IOException {
        writer.write(EXPENSE_RECHARGE);
        writer.write(getFormatString(getValue(recharge,"expense.id"),16));
        writer.write(getFormatString(getValue(claim,"expenseSubCategory.referenceErpId"),8));
        writer.write(getFormatString(getValue(recharge,"id"),20));
        /*if (recharge.getPerson()!=null) {
            writer.write(RECHARGE_TYPE_PERSON);
            writer.write(getFormatString(RECHARGE_EMPTY_CUSTOMER,8));
            writer.write(getFormatString(RECHARGE_EMPTY_ENTITY,8));
            writer.write(getFormatString(getValue(recharge,"personDepartment.id"),8));
            writer.write(getFormatString(getValue(recharge,"person.id"),10));
        } else {
            if (recharge.getPersonDepartment()!=null) { 
                writer.write(RECHARGE_TYPE_DEPARTMENT);
                writer.write(getFormatString(RECHARGE_EMPTY_CUSTOMER,8));
                writer.write(getFormatString(RECHARGE_EMPTY_ENTITY,8));
                writer.write(getFormatString(getValue(recharge,"personDepartment.id"),8));
                writer.write(getFormatString(getValue(recharge,"person.id"),10));
            } else {
                if (recharge.getCustomer().getType().equals(CustomerType.CUSTOMER)) {
                    writer.write(RECHARGE_TYPE_CUSTOMER);
                    writer.write(getFormatString(getValue(recharge,"customer.code"),8));
                    writer.write(getFormatString(RECHARGE_EMPTY_ENTITY,8));
                    writer.write(getFormatString(RECHARGE_EMPTY_DEPARTMENT,8));
                    writer.write(getFormatString(RECHARGE_EMPTY_PERSON,10));
                } else {
                    writer.write(RECHARGE_TYPE_ENTITY);
                    writer.write(getFormatString(RECHARGE_EMPTY_CUSTOMER,8));
                    writer.write(getFormatString(getValue(recharge,"customer.code"),8));
                    writer.write(getFormatString(RECHARGE_EMPTY_DEPARTMENT,8));
                    writer.write(getFormatString(RECHARGE_EMPTY_PERSON,10));
                }
            }
        }*/
        if (recharge.getCustomer().getType().equals(CustomerType.CUSTOMER)) {
            writer.write(RECHARGE_TYPE_CUSTOMER);
            writer.write(getFormatString(getValue(recharge,"customer.code"),17));//modified by gaga
            writer.write(getFormatString(RECHARGE_EMPTY_ENTITY,8));
            writer.write(getFormatString(RECHARGE_EMPTY_DEPARTMENT,8));
            writer.write(getFormatString(RECHARGE_EMPTY_PERSON,10));
        } else {
            writer.write(RECHARGE_TYPE_ENTITY);
            writer.write(getFormatString(RECHARGE_EMPTY_CUSTOMER,8));
            writer.write(getFormatString(getValue(recharge,"customer.code"),17));//
            writer.write(getFormatString(RECHARGE_EMPTY_DEPARTMENT,8));
            writer.write(getFormatString(RECHARGE_EMPTY_PERSON,10));
        }
        BigDecimal amount = lastClaim ? recharge.getAmount().subtract(recharge.getOutputedAmount()) : claim.getAmount().multiply(recharge.getPercentage()).divide(new BigDecimal(100d), 2, BigDecimal.ROUND_HALF_UP);
        writer.write(getFormatString(decimalFormat.format(amount.doubleValue()),10));
        writer.write(getFormatString(getDecimalValue(recharge,"percentage",decimalFormat),6));
        writer.write(getFormatString(claim.getDescription(),24)); //add by nicebean 20070215
        writer.write(EOL);
    }

    private void writeExpenseDetail(OutputStreamWriter writer, ExpenseCell cell,YesNo isRecharge,Integer departmentId,Integer personId, BigDecimal percentage, ExpenseRecharge recharge, boolean lastClaim) throws IOException {
//        writer.write(EXPENSE_DETAIL);
//        writer.write(getFormatString(getValue(claim, "expense.id"), 16));
//        writer.write(getFormatString(getValue(claim, "expenseSubCategory.referenceErpId"), 8));
//        if (percentage == null) {
//            writer.write(getFormatString(getDecimalValue(claim, "enterAmount", decimalFormat), 10));
//            writer.write(getFormatString(getDecimalValue(claim, "amount", decimalFormat), 10));
//        } else {
//            BigDecimal enterAmount = lastClaim ? recharge.getAmount().subtract(recharge.getOutputedEnterAmount()) : claim.getEnterAmount().multiply(percentage)
//                    .divide(new BigDecimal(100d), 2, BigDecimal.ROUND_HALF_UP);
//            BigDecimal amount = lastClaim ? recharge.getAmount().subtract(recharge.getOutputedAmount()) : claim.getAmount().multiply(percentage).divide(
//                    new BigDecimal(100d), 2, BigDecimal.ROUND_HALF_UP);
//            writer.write(getFormatString(decimalFormat.format(enterAmount.doubleValue()), 10));
//            writer.write(getFormatString(decimalFormat.format(amount.doubleValue()), 10));
//            if (!lastClaim) {
//                recharge.setOutputedAmount(recharge.getOutputedAmount().add(amount));
//                recharge.setOutputedEnterAmount(recharge.getOutputedEnterAmount().add(enterAmount));
//            }
//        }
//        writer.write(isRecharge.equalsYesNo(YesNo.YES) ? RECHARGE_YES : RECHARGE_NO);
//        writer.write(getFormatString(departmentId == null ? "" : departmentId.toString(), 8));
//        writer.write(getFormatString(personId == null ? "" : personId.toString(), 10));
//        writer.write(getFormatString(claim.getDescription(), 3000));
//        writer.write(getFormatString(claim.getId(), 20));
//        writer.write(EOL);
        
        writer.write(EXPENSE_DETAIL);
        writer.write(getFormatString(getValue(cell, "row.expense.id"), 16));
        writer.write(getFormatString(getValue(cell, "expenseSubCategory.referenceErpId"), 8));
        writer.write(getFormatString(getValue(cell, "amount"), 10));    //entered amount
        writer.write(getFormatString(getValue(cell, "amount"), 10));    //confirmed amount
        writer.write(isRecharge.equalsYesNo(YesNo.YES) ? RECHARGE_YES : RECHARGE_NO);
        writer.write(getFormatString(getValue(cell, "row.expense.department.id"), 8));
        writer.write(getFormatString(getValue(cell, "row.expense.requestor.id"), 10));
        writer.write(getFormatString(getValue(cell, "description"), 3000));
        writer.write(getFormatString(getValue(cell, "row.projectCode.code"), 8));
        writer.write(EOL);
    }

    private void writeExpense(OutputStreamWriter writer, Expense expense, boolean recharge) throws IOException {
        writer.write(EXPENSE_MASTER);
        writer.write(getFormatString(getValue(expense,"id"),16));
        writer.write(getFormatString(getValue(expense,"title"),50));
        writer.write(getFormatString(getValue(expense,"description"),50));
        writer.write(getFormatString(getValue(expense,"department.name"),20));
        writer.write(getFormatString(getValue(expense,"department.id"),8));
        writer.write(getFormatString(recharge ? "" : getValue(expense,"requestor.id") ,10));
        writer.write(getFormatString(getDateValue(expense,"requestDate",dateFormat),8));
        writer.write(getFormatString(getValue(expense,"expenseCurrency.code"),8));
        writer.write(getFormatString(getDecimalValue(expense,"amount",decimalFormat),10));
        writer.write(getFormatString(getValue(expense,"requestor.id") ,10));
        //writer.write(expense.getIsRecharge().equalsYesNo(YesNo.YES)?RECHARGE_YES:RECHARGE_NO);
        writer.write(EOL);
    }


    public String getRemoteExportFileName() {
        return getRemoteFileName("expense","txt");
    }
    

}
