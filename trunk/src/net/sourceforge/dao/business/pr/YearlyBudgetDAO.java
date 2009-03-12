/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */
package net.sourceforge.dao.business.pr;

import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sourceforge.model.admin.ExpenseCategory;
import net.sourceforge.model.admin.PurchaseCategory;
import net.sourceforge.model.admin.PurchaseSubCategory;
import net.sourceforge.model.admin.Site;
import net.sourceforge.model.admin.User;
import net.sourceforge.model.business.pr.YearlyBudget;
import net.sourceforge.model.business.pr.YearlyBudgetHistory;
import net.sourceforge.model.business.pr.YearlyBudgetHistoryDepartment;
import net.sourceforge.model.business.pr.query.YearlyBudgetQueryOrder;
import net.sourceforge.model.metadata.BudgetType;

/**
 * dao interface for domain model yearlyBudget
 * @author shilei
 * @version 1.0 (Nov 29, 2005)
 */
public interface YearlyBudgetDAO {

    /**
     * get yearlyBudget by id
     * @param id
     *      id of yearlyBudget
     * @return
     */
    public YearlyBudget getYearlyBudget(Integer id);

    /**
     * get Yearly Budget List Count according to conditions
     * @param conditions
     *      query conditions
     * @return count of Yearly BudgetList
     */
    public int getYearlyBudgetListCount(Map conditions);

    /**
     * get Yearly Budget List by conditions
     * 
     * @param conditions
     *            search condition
     * @param pageNo
     *            start page no(0 based), ignored if is -1
     * @param pageSize
     *            page size, ignored if is -1
     * @param order
     *            search order
     * @param descend
     *            asc or desc
     * @return Yearly Budget list
     */
    public List getYearlyBudgetList(Map conditions, int pageNo, int pageSize, YearlyBudgetQueryOrder order, boolean descend);

    /**
     * insert YearlyBudget
     * @param yearlyBudget
     * @return
     */
    public YearlyBudget insertYearlyBudget(YearlyBudget yearlyBudget);

    /**
     * update YearlyBudget
     * @param yearlyBudget
     * @return
     */
    public YearlyBudget updateYearlyBudget(YearlyBudget yearlyBudget);

    

    /**
     * insert Yearly Budget History
     * @param ybh
     * @return
     */
    public YearlyBudgetHistory insertYearlyBudgetHistory(YearlyBudgetHistory ybh);

    /**
     * insert Yearly Budget History Department
     * @param ybhd
     */
    public void insertYearlyBudgetHistoryDepartment(YearlyBudgetHistoryDepartment ybhd);

    /**
     * 根据指定的Site、PurchaseCategory、PurchaseSubCategory和Department
     * List，返回合适的YearlyBudget列表。
     * 
     * @param site
     *            Site对象，如果pc或者psc不为null，此参数被忽略
     * @param pc
     *            PurchaseCategory对象，如果psc不为null，此参数被忽略
     * @param psc
     *            PurchaseSubCategory对象
     * @param departmentList
     *            Department对象列表
     * @return 合适的YearlyBudget列表
     */
    public List getSuitableYearlyBudget(Site site, PurchaseCategory pc, PurchaseSubCategory psc,
            List departmentList,BudgetType budgetType);


    public List getSuitableYearlyBudget(Site s, ExpenseCategory ec, List departmentList,BudgetType budgetType, Date effectiveDate);
}
