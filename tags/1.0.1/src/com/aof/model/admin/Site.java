/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

/*
 * Created Thu Sep 22 15:04:41 CST 2005 by MyEclipse Hibernate Tool.
 */
package com.aof.model.admin;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A class that represents a row in the 't_site' table. This class may be
 * customized as it is never re-generated after being created.
 */
public class Site extends AbstractSite implements Serializable {

    /**
     * Simple constructor of Site instances.
     */
    public Site() {
    }

    /**
     * Constructor of Site instances given a simple primary key.
     * 
     * @param id
     */
    public Site(Integer id) {
        super(id);
    }

    /* Add customized code below */
    private List departments;
    private List travelGroups;
    private ExpenseCategory enabledTravelExpenseCategory;
    private List enabledNotTravelExpenseCategoryList;
    private List enabledPurchaseCategoryList;
    private List enabledExchangeRateList;
    
    public List getDepartments() {
        return departments;
    }
    public void setDepartments(List departments) {
        this.departments = departments;
    }
    
    public List getTravelGroups() {
        return travelGroups;
    }
    public void setTravelGroups(List travelGroups) {
        this.travelGroups = travelGroups;
    }

    
    /**
     * @return Returns the enabledNotTravelExpenseCategoryList.
     */
    public List getEnabledNotTravelExpenseCategoryList() {
        return enabledNotTravelExpenseCategoryList;
    }

    /**
     * @param enabledNotTravelExpenseCategoryList The enabledNotTravelExpenseCategoryList to set.
     */
    public void setEnabledNotTravelExpenseCategoryList(List enabledNotTravelExpenseCategoryList) {
        this.enabledNotTravelExpenseCategoryList = enabledNotTravelExpenseCategoryList;
    }

    /**
     * @return Returns the enabledTravelExpenseCategory.
     */
    public ExpenseCategory getEnabledTravelExpenseCategory() {
        return enabledTravelExpenseCategory;
    }

    /**
     * @param enabledTravelExpenseCategory The enabledTravelExpenseCategory to set.
     */
    public void setEnabledTravelExpenseCategory(ExpenseCategory enabledTravelExpenseCategory) {
        this.enabledTravelExpenseCategory = enabledTravelExpenseCategory;
    }

    public List getEnabledExpenseCategoryList() {
        List result=new ArrayList();
        if (this.enabledTravelExpenseCategory!=null) 
            result.add(0,this.enabledTravelExpenseCategory);
        result.addAll(this.enabledNotTravelExpenseCategoryList);
        return result;
    }

    /**
     * @return Returns the enabledPurchaseCategoryList.
     */
    public List getEnabledPurchaseCategoryList() {
        return enabledPurchaseCategoryList;
    }

    /**
     * @param enabledPurchaseCategoryList The enabledPurchaseCategoryList to set.
     */
    public void setEnabledPurchaseCategoryList(List enabledPurchaseCategoryList) {
        this.enabledPurchaseCategoryList = enabledPurchaseCategoryList;
    }

    /**
     * @return Returns the enabledExchangeRateList.
     */
    public List getEnabledExchangeRateList() {
        return enabledExchangeRateList;
    }

    /**
     * @param enabledExchangeRateList The enabledExchangeRateList to set.
     */
    public void setEnabledExchangeRateList(List enabledExchangeRateList) {
        this.enabledExchangeRateList = enabledExchangeRateList;
    }
    
    private Map mailReminders;

    public Map getMailReminders() {
        return mailReminders;
    }

    public void setMailReminders(Map mailReminder) {
        this.mailReminders = mailReminder;
    }
    
    
}
