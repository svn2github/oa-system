/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */
package com.aof.dao.business.pr;

import java.util.List;
import java.util.Map;

import com.aof.model.business.pr.YearlyBudgetDepartment;
import com.aof.model.business.pr.query.YearlyBudgetDepartmentQueryOrder;

/**
 * dao interface for domain model YearlyBudgetDepartment
 * 
 * @author shi1
 * @version 1.0 (Nov 30, 2005)
 */
public interface YearlyBudgetDepartmentDAO {

    /**
     * get YearlyBudgetDepartment List by conditions
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
     * @return YearlyBudgetDepartment list
     */
    public List getYearlyBudgetDepartmentList(final Map conditions, final int pageNo, final int pageSize, final YearlyBudgetDepartmentQueryOrder order,
            final boolean descend);
    
    /**
     * get YearlyBudgetDepartment List count by conditions
     * 
     * @param conditions
     * @return
     */
    public int getYearlyBudgetDepartmentListCount(final Map conditions) ;

    /**
     * get Yearly Budget Department by id
     * 
     * @param id
     * @return
     */
    public YearlyBudgetDepartment getYearlyBudgetDepartment(Integer id);

    /**
     * insert YearlyBudgetDepartment
     * 
     * @param yearlyBudgetDepartment
     * @return
     */
    public YearlyBudgetDepartment insertYearlyBudgetDepartment(YearlyBudgetDepartment yearlyBudgetDepartment);

    /**
     * update YearlyBudgetDepartment
     * 
     * @param yearlyBudgetDepartment
     * @return
     */
    public YearlyBudgetDepartment updateYearlyBudgetDepartment(YearlyBudgetDepartment yearlyBudgetDepartment);

    /**
     * delete YearlyBudgetDepartment
     * 
     * @param ybd
     */
    public void delete(YearlyBudgetDepartment ybd);



}
