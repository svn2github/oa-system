/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.dao.admin.hibernate;


import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;

import net.sf.hibernate.Hibernate;
import net.sf.hibernate.type.Type;


import com.aof.dao.BaseDAOHibernate;
import com.aof.dao.admin.DataTransferDAO;
import com.aof.model.admin.Currency;
import com.aof.model.admin.Customer;
import com.aof.model.admin.ExchangeRate;
import com.aof.model.admin.ExpenseCategory;
import com.aof.model.admin.ExpenseSubCategory;
import com.aof.model.admin.PurchaseType;
import com.aof.model.admin.Site;
import com.aof.model.admin.Supplier;
import com.aof.model.business.expense.Expense;
import com.aof.model.business.expense.ExpenseClaim;
import com.aof.model.business.po.PurchaseOrder;
import com.aof.model.business.po.PurchaseOrderItem;
import com.aof.model.business.po.PurchaseOrderItemReceipt;
import com.aof.model.metadata.EnabledDisabled;
import com.aof.model.metadata.ExpenseStatus;
import com.aof.model.metadata.ExpenseType;
import com.aof.model.metadata.ExportStatus;
import com.aof.model.metadata.PurchaseOrderStatus;
import com.aof.model.metadata.SupplierPromoteStatus;


/**
 * DataTransferDAOµÄHibernateÊµÏÖ
 * @author ych
 * @version 1.0 (Dec 20, 2005)
 */
public class DataTransferDAOHibernate extends BaseDAOHibernate implements DataTransferDAO {
    
    public List getDataTransferParameterList() {
        return getHibernateTemplate().find("from DataTransferParameter dtp where dtp.startTime is not null and dtp.timePerDay is not null and dtp.intervalMin is not null");
    }
    
    public List getPOList(Site site) {
        return getHibernateTemplate().find("from PurchaseOrder po where (po.exportStatus=? or po.exportStatus=?) and po.site.id=? and (po.status=? or po.status=?) order by po.id",
                new Object[] {ExportStatus.UNEXPORTED.getEnumCode().toString(),ExportStatus.NEEDREEXPORT.getEnumCode().toString(),site.getId(),PurchaseOrderStatus.CONFIRMED.getEnumCode().toString(),PurchaseOrderStatus.RECEIVED.getEnumCode().toString()},
                new Type[] {Hibernate.STRING,Hibernate.STRING,Hibernate.INTEGER,Hibernate.STRING,Hibernate.STRING});
    }
    
    public List getPOItemList(PurchaseOrder po) {
        return getHibernateTemplate().find("from PurchaseOrderItem poItem where poItem.purchaseOrder.id=?",po.getId(),Hibernate.STRING);
    }
    
    public List getPOItemRecharge(PurchaseOrderItem poItem) {
        return getHibernateTemplate().find("from PurchaseOrderItemRecharge recharge where recharge.purchaseOrderItem.id=?",poItem.getId(),Hibernate.INTEGER); 
    }
    
    public List getPOItemReceiptList(Site site) {
        return getHibernateTemplate().find("from PurchaseOrderItemReceipt poir where poir.exportStatus=? and poir.purchaseOrderItem.purchaseOrder.site.id=? and poir.receiveQty1=poir.receiveQty2",
                new Object[] {ExportStatus.UNEXPORTED.getEnumCode().toString(),site.getId()},
                new Type[] {Hibernate.STRING,Hibernate.INTEGER});
    }
    
    public List getExpenseList(Site site) {
        return getHibernateTemplate().find("from Expense ex where ex.exportStatus=? and ex.department.site.id=? and ex.status=? order by ex.id",
                new Object[] {ExportStatus.UNEXPORTED.getEnumCode().toString(),site.getId(),ExpenseStatus.CLAIMED.getEnumCode().toString()},
                new Type[] {Hibernate.STRING,Hibernate.INTEGER,Hibernate.STRING});
    }
    
    public List getExpenseDetailList(Expense expense) {
//        List claimList = getHibernateTemplate().find("from ExpenseClaim ec where ec.expense.id=? and ec.amount<>0", expense.getId(), Hibernate.STRING);
//        List sumCellList = getHibernateTemplate().find(
//                "select sum(ec.amount),ec.expenseSubCategory.id  from ExpenseCell ec where ec.row.expense.id=? group by ec.expenseSubCategory.id",
//                expense.getId(), Hibernate.STRING);
//        for (Iterator itor = claimList.iterator(); itor.hasNext();) {
//            ExpenseClaim claim = (ExpenseClaim) itor.next();
//            claim.setEnterAmount(getSumEnterAmount(claim.getExpenseSubCategory(), sumCellList));
//        }
//        return claimList;
        return getHibernateTemplate().find("from ExpenseCell c where c.row.expense.id = ? and e.amount <> 0", expense.getId(), Hibernate.STRING);
    }
    
    private BigDecimal getSumEnterAmount(ExpenseSubCategory expenseSubCategory,List sumCellList) {
        for (Iterator itor = sumCellList.iterator(); itor.hasNext();) {
            Object[] content = (Object[]) itor.next();
            if (((Integer)content[1]).equals(expenseSubCategory.getId())) {
                return (BigDecimal)content[0];
            }
        }
        return new BigDecimal(0d);
    }
    
    public List getExpenseRechargeList(Expense expense) {
        return getHibernateTemplate().find("from ExpenseRecharge er where er.expense.id=?",expense.getId(),Hibernate.STRING);
    }
    
    public List getSupplierList(Site site) {
        return getHibernateTemplate().find("from Supplier sp where sp.exportStatus=? and sp.site.id=? and sp.promoteStatus=? order by sp.id",
                new Object[] {ExportStatus.UNEXPORTED.getEnumCode().toString(),site.getId(),SupplierPromoteStatus.SITE.getEnumCode().toString()},
                new Type[] {Hibernate.STRING,Hibernate.INTEGER,Hibernate.STRING});
    }
    
    public List getDepartmentList(Site site) {
        return getHibernateTemplate().find("from Department dp where dp.site.id=? order by dp.id",site.getId(),Hibernate.INTEGER);
    }
    
    public List getCountryList(Site site) {
        return getHibernateTemplate().find("from Country country where country.site.id=? or country.site.id is null order by country.id",site.getId(),Hibernate.INTEGER);
    }
    
    public List getPurchaseTypeCodeList(Site site) {
        return getHibernateTemplate().find("select pt.code from PurchaseType pt where pt.site.id=? order by pt.code",site.getId(),Hibernate.INTEGER);
    }
    
    public List getCustomerList(Site site) {
        return getHibernateTemplate().find("from Customer c where c.site.id=? order by c.code",site.getId(),Hibernate.INTEGER);
    }
    
    public List getExchangeRateList(Site site) {
        return getHibernateTemplate().find("from ExchangeRate ex where ex.site.id=? order by ex.currency.code",site.getId(),Hibernate.INTEGER);
    }
    
    public Currency getCurrency(String code) {
        if (code == null) return null;
        List cList=getHibernateTemplate().find("from Currency c where ucase(c.code)=?",code.toUpperCase(),Hibernate.STRING);
        Currency currency = null;
        if (cList.size()>0)
            currency=(Currency)cList.get(0);
        if (currency==null) {
            currency=new Currency();
            currency.setCode(code);
            currency.setName(code);
            currency.setEnabled(EnabledDisabled.ENABLED);
            getHibernateTemplate().save(currency);
            getHibernateTemplate().flush();
        }
        return currency;
    }
    
    public ExpenseCategory getExpenseCategory(Site site,String code) {
        List result = getHibernateTemplate().find("from ExpenseCategory ex where ex.site.id=? and ex.referenceErpId=?",
                new Object[] {site.getId(),code},
                new Type[]{Hibernate.INTEGER,Hibernate.STRING});
        if (result.size()>0) 
            return (ExpenseCategory) result.get(0);
        else {
            ExpenseCategory ex = new ExpenseCategory();
            ex.setSite(site);
            ex.setType(ExpenseType.OTHER);
            ex.setDescription(code);
            ex.setEnabled(EnabledDisabled.ENABLED);
            ex.setReferenceErpId(code);
            getHibernateTemplate().save(ex);
            getHibernateTemplate().flush();
            return ex;
        }
    }

    public List getExpenseCategoryList(Site site) {
        return getHibernateTemplate().find("from ExpenseCategory ex where ex.site.id=?",site.getId(),Hibernate.INTEGER);
    }

    /* (non-Javadoc)
     * @see com.aof.dao.admin.DataTransferDAO#getExpenseSubCategory(com.aof.model.admin.ExpenseCategory, java.lang.String)
     */
    public ExpenseSubCategory getExpenseSubCategory(ExpenseCategory expenseCategory, String code) {
        // TODO Auto-generated method stub
        return null;
    }

    public List getExpenseSubCategoryList(Site site) {
        return getHibernateTemplate().find("from ExpenseSubCategory ex where ex.expenseCategory.site.id=?",site.getId(),Hibernate.INTEGER);
    }

    public void updatePurchaseOrder(PurchaseOrder po) {
        getHibernateTemplate().update(po);
    }
    
    public void updatePurchaseOrderItemReceipt(PurchaseOrderItemReceipt receipt) {
        getHibernateTemplate().update(receipt);
    }
    
    public void updateSupplier(Supplier supplier) {
        getHibernateTemplate().update(supplier);
    }
    
    public void updateExpense(Expense expense) {
        getHibernateTemplate().update(expense);
    }
    
    public void updatePurchaseType(PurchaseType purchaseType,boolean insert) {
        if (insert)
            getHibernateTemplate().save(purchaseType);
        else    
            getHibernateTemplate().update(purchaseType);
        getHibernateTemplate().flush();
    }
    
    public void updateExchangeRate(ExchangeRate exchangeRate,boolean insert) {
        if (insert)
            getHibernateTemplate().save(exchangeRate);
        else
            getHibernateTemplate().update(exchangeRate);
        getHibernateTemplate().flush();
    }
    
    public void updateExpenseCategory(ExpenseCategory expenseCategory,boolean insert) {
        if (insert)
            getHibernateTemplate().save(expenseCategory);
        else
            getHibernateTemplate().update(expenseCategory);
        getHibernateTemplate().flush();
    }
    
    public void updateExpenseSubCategory(ExpenseSubCategory expenseSubCategory,boolean insert) {
        if (insert)
            getHibernateTemplate().save(expenseSubCategory);
        else
            getHibernateTemplate().update(expenseSubCategory);
        getHibernateTemplate().flush();
    }
    
    public void updateCustomer(Customer customer,boolean insert) {
        if (insert)
            getHibernateTemplate().save(customer);
        else
            getHibernateTemplate().update(customer);
        getHibernateTemplate().flush();
    }
}
