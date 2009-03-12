/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */
package net.sourceforge.service.business.pr.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sourceforge.dao.business.pr.YearlyBudgetDAO;
import net.sourceforge.dao.business.pr.YearlyBudgetDepartmentDAO;
import net.sourceforge.dao.business.pr.YearlyBudgetHistoryDAO;
import net.sourceforge.model.admin.Department;
import net.sourceforge.model.admin.ExpenseCategory;
import net.sourceforge.model.admin.Function;
import net.sourceforge.model.admin.PurchaseCategory;
import net.sourceforge.model.admin.PurchaseSubCategory;
import net.sourceforge.model.admin.Site;
import net.sourceforge.model.admin.User;
import net.sourceforge.model.business.pr.YearlyBudget;
import net.sourceforge.model.business.pr.YearlyBudgetDepartment;
import net.sourceforge.model.business.pr.YearlyBudgetHistory;
import net.sourceforge.model.business.pr.YearlyBudgetHistoryDepartment;
import net.sourceforge.model.business.pr.query.YearlyBudgetDepartmentQueryCondition;
import net.sourceforge.model.business.pr.query.YearlyBudgetQueryCondition;
import net.sourceforge.model.business.pr.query.YearlyBudgetQueryOrder;
import net.sourceforge.model.metadata.BudgetStatus;
import net.sourceforge.model.metadata.BudgetType;
import net.sourceforge.model.metadata.YesNo;
import net.sourceforge.service.BaseManager;
import net.sourceforge.service.admin.FunctionManager;
import net.sourceforge.service.admin.PurchaseCategoryManager;
import net.sourceforge.service.admin.PurchaseSubCategoryManager;
import net.sourceforge.service.admin.SystemLogManager;
import net.sourceforge.service.admin.UserManager;
import net.sourceforge.service.business.pr.YearlyBudgetManager;
import net.sourceforge.service.business.rule.FlowManager;

/**
 * service implement for domain model YearlyBudget
 * 
 * @author shilei
 * @version 1.0 (Nov 29, 2005)
 */
public class YearlyBudgetManagerImpl extends BaseManager implements YearlyBudgetManager {

    private YearlyBudgetDAO dao;

    private YearlyBudgetHistoryDAO yearlyBudgetHistoryDAO;

    private YearlyBudgetDepartmentDAO yearlyBudgetDepartmentDAO;

    private PurchaseCategoryManager purchaseCategoryManager;

    private PurchaseSubCategoryManager purchaseSubCategoryManager;

    private FlowManager flowManager;
    
    private FunctionManager functionManager;
    private UserManager userManager;
    
    private SystemLogManager systemLogManager;

    public void setSystemLogManager(SystemLogManager systemLogManager) {
        this.systemLogManager = systemLogManager;
    }

    public void setYearlyBudgetDepartmentDAO(YearlyBudgetDepartmentDAO yearlyBudgetDepartmentDAO) {
        this.yearlyBudgetDepartmentDAO = yearlyBudgetDepartmentDAO;
    }

    public void setYearlyBudgetDAO(YearlyBudgetDAO dao) {
        this.dao = dao;
    }

    public void setPurchaseCategoryManager(PurchaseCategoryManager purchaseCategoryManager) {
        this.purchaseCategoryManager = purchaseCategoryManager;
    }

    public void setPurchaseSubCategoryManager(PurchaseSubCategoryManager purchaseSubCategoryManager) {
        this.purchaseSubCategoryManager = purchaseSubCategoryManager;
    }

    public void setFlowManager(FlowManager flowManager) {
        this.flowManager = flowManager;
    }

    public YearlyBudget getYearlyBudget(Integer id) {
        return dao.getYearlyBudget(id);
    }

    public YearlyBudget updateYearlyBudget(YearlyBudget oldYb,YearlyBudget newYb, Department[] departments,User user) {
        YearlyBudget yb = oldYb;
        
        dao.updateYearlyBudget(yb);
        
        if (yb.getIsFreeze().equals(YesNo.YES))
            throw new RuntimeException("freeze");

        boolean needHistory = false;
        if (!newYb.getName().equals(yb.getName()))
            needHistory = true;
        boolean ignoreAmountCondition = newYb.getAmount().equals(yb.getAmount()); 
        if (!ignoreAmountCondition)
            needHistory = true;

        List ybdList = this.getYearlyBudgetDepartmentList(newYb);
        Set oldDepartments=new HashSet();
        Set newDepartments=toSet(departments);
        Map ybdMap = new HashMap();
        for (Iterator iter = ybdList.iterator(); iter.hasNext();) {
            YearlyBudgetDepartment ybd = (YearlyBudgetDepartment) iter.next();
            oldDepartments.add(ybd.getDepartment());
            ybdMap.put(ybd.getDepartment(), ybd);
        }
        
        oldYb.setDepartments(oldDepartments);
        newYb.setDepartments(newDepartments);
        systemLogManager.generateLog(oldYb,newYb,YearlyBudget.LOG_ACTION_MODIFY,user);
        
        
        if (!needHistory) {
            if(!oldDepartments.equals(newDepartments))
                needHistory = true;
        }

        if (!needHistory) {

            if (yb.getStatus().equals(newYb.getStatus())) {
                // no change
                return yb;
            }
            // only change status
            yb.setStatus(newYb.getStatus());
            this.updateAndNotifyYearlyBudget(yb, true);
            return yb;
        }

        // need History
        YearlyBudgetHistory ybh = new YearlyBudgetHistory();
        ybh.setAmount(yb.getAmount());
        ybh.setCreateDate(yb.getModifyDate());
        ybh.setCreator(yb.getModifier());
        ybh.setName(yb.getName());
        ybh.setVersion(yb.getVersion());
        ybh.setYearlyBudget(yb);

        ybh = dao.insertYearlyBudgetHistory(ybh);

        for (Iterator iter = ybdList.iterator(); iter.hasNext();) {
            YearlyBudgetDepartment ybd = (YearlyBudgetDepartment) iter.next();

            YearlyBudgetHistoryDepartment ybhd = new YearlyBudgetHistoryDepartment();
            ybhd.setDepartment(ybd.getDepartment());
            ybhd.setYearlyBudgetHistory(ybh);

            dao.insertYearlyBudgetHistoryDepartment(ybhd);

        }

        yb.setModifier(newYb.getModifier());
        yb.setModifyDate(new Date());
        yb.setVersion(yb.getVersion() + 1);

        yb.setAmount(newYb.getAmount());
        yb.setName(newYb.getName());
        yb.setStatus(newYb.getStatus());

        for (int i = 0; i < departments.length; i++) {
            Department department = departments[i];
            YearlyBudgetDepartment ybd = (YearlyBudgetDepartment) ybdMap.get(department);
            if (ybd == null) {
                ybd = new YearlyBudgetDepartment();
                ybd.setDepartment(department);
                ybd.setYearlyBudget(yb);
                yearlyBudgetDepartmentDAO.insertYearlyBudgetDepartment(ybd);
            } else {
                ybdMap.remove(ybd.getDepartment());
            }
        }

        for (Iterator iter = ybdMap.keySet().iterator(); iter.hasNext();) {
            Department d = (Department) iter.next();
            YearlyBudgetDepartment ybd = (YearlyBudgetDepartment) ybdMap.get(d);
            yearlyBudgetDepartmentDAO.delete(ybd);
        }

        this.updateAndNotifyYearlyBudget(yb, ignoreAmountCondition);
        return yb;
    }

    public YearlyBudget insertYearlyBudget(YearlyBudget yb, Department[] departments,User user) {
        yb.setActualAmount(new BigDecimal(0d));
        yb.setStatus(BudgetStatus.OPEN);
        yb.setIsFreeze(YesNo.NO);
        yb.setCreateDate(new Date());
        yb.setVersion(1);
        yb.setModifier(yb.getCreator());
        yb.setModifyDate(yb.getCreateDate());

        dao.insertYearlyBudget(yb);
        for (int i = 0; i < departments.length; i++) {
            Department department = departments[i];
            YearlyBudgetDepartment ybd = new YearlyBudgetDepartment();
            ybd.setDepartment(department);
            ybd.setYearlyBudget(yb);
            yearlyBudgetDepartmentDAO.insertYearlyBudgetDepartment(ybd);
        }
        setExtraInformationToYearlyBudgetForExecuteFlow(yb);
        flowManager.executeNotifyFlow(yb);
        yb.setDepartments(toSet(departments));
        systemLogManager.generateLog(null,yb,YearlyBudget.LOG_ACTION_NEW,user);
        return yb;
    }

    private Set toSet(Object[] array) {
        Set set=new HashSet();
        for (int i = 0; i < array.length; i++) {
            set.add(array[i]);
        }
        return set;
    }

    public int getYearlyBudgetListCount(Map conditions) {
        return dao.getYearlyBudgetListCount(conditions);
    }

    public List getYearlyBudgetList(Map conditions, int pageNo, int pageSize, YearlyBudgetQueryOrder order, boolean descend) {
        return dao.getYearlyBudgetList(conditions, pageNo, pageSize, order, descend);
    }

    public List getYearlyBudgetDepartmentList(YearlyBudget yearlyBudget) {
        Map conds = new HashMap();
        conds.put(YearlyBudgetDepartmentQueryCondition.YEARLYBUDGET_ID_EQ, yearlyBudget.getId());
        List ybdList = yearlyBudgetDepartmentDAO.getYearlyBudgetDepartmentList(conds, 0, -1, null, false);
        return ybdList;
    }

    public Department[] getBudgetDepartments(YearlyBudget yearlyBudget) {
        List ybdList = this.getYearlyBudgetDepartmentList(yearlyBudget);
        Department[] departments = new Department[ybdList.size()];
        int index = 0;
        for (Iterator iter = ybdList.iterator(); iter.hasNext();) {
            YearlyBudgetDepartment ybd = (YearlyBudgetDepartment) iter.next();
            departments[index++] = ybd.getDepartment();
        }
        return departments;
    }

    public YearlyBudget getYearlyBudgetByCode(String code) {
        Map conds = new HashMap();
        conds.put(YearlyBudgetQueryCondition.CODE_EQ, code);
        List l = dao.getYearlyBudgetList(conds, 0, -1, null, false);
        if (l.size() == 1)
            return (YearlyBudget) l.get(0);
        else if (l.size() == 0)
            return null;
        else
            throw new RuntimeException("code duplicate already");

    }

    public List getYearlyBudgetDepartmentList(Map conditions) {
        return yearlyBudgetDepartmentDAO.getYearlyBudgetDepartmentList(conditions, 0, -1, null, false);
    }

    public void freeze(Integer[] yearlyBudgetIds, boolean toFreeze,User user) {
        for (int i = 0; i < yearlyBudgetIds.length; i++) {
            YearlyBudget yb = this.getYearlyBudget(yearlyBudgetIds[i]);
            if (yb == null)
                throw new RuntimeException("YearlyBudget not found");
            boolean frozen = yb.getIsFreeze().equals(YesNo.YES);
            if (frozen && toFreeze)
                throw new RuntimeException("YearlyBudget already frozen");
            if (!frozen && !toFreeze)
                throw new RuntimeException("YearlyBudget already not frozen");
            if (toFreeze)
            {
                yb.setIsFreeze(YesNo.YES);
                systemLogManager.generateLog(null,yb,YearlyBudget.LOG_ACTION_FREEZE,user);
            }
            if (!toFreeze)
            {
                yb.setIsFreeze(YesNo.NO);
                systemLogManager.generateLog(null,yb,YearlyBudget.LOG_ACTION_UNFREEZE,user);
            }
            this.updateAndNotifyYearlyBudget(yb, true);
        }
    }

    public List listHistroy(YearlyBudget yb) {
        return yearlyBudgetHistoryDAO.listHistory(yb);
    }

    public void setYearlyBudgetHistoryDAO(YearlyBudgetHistoryDAO yearlyBudgetHistoryDAO) {
        this.yearlyBudgetHistoryDAO = yearlyBudgetHistoryDAO;
    }

    public List getSuitableYearlyBudget(Site s, PurchaseCategory pc, PurchaseSubCategory psc, List departmentList, BudgetType budgetType, User user) {
        List list = dao.getSuitableYearlyBudget(s, pc, psc, departmentList, budgetType);
        if (list != null) {
            for (int i= 0; i < list.size(); i++) {
                YearlyBudget yb = (YearlyBudget)list.get(i);
                boolean b = canViewYearlyBudgetAmount(yb, user);
                if (!b) {
                    yb.setAmount(null);
                    yb.setActualAmount(null);                    
                }
            }
        }
        
        return list;
    }
    
    public List getSuitableYearlyBudget(Site s, ExpenseCategory ec, List departmentList,BudgetType budgetType, Date effectiveDate, User user) {
        List list = dao.getSuitableYearlyBudget(s, ec, departmentList, budgetType, effectiveDate);
        if (list != null) {
            for (int i= 0; i < list.size(); i++) {
                YearlyBudget yb = (YearlyBudget)list.get(i);
                boolean b = canViewExpenseBudgetAmount(yb, user);
                if (!b) {
                    yb.setAmount(null);
                    yb.setActualAmount(null);                    
                }
            }
        }
        
        return list;
    }

    public void setExtraInformationToYearlyBudgetForExecuteFlow(YearlyBudget yb) {
        Collection ybDepartmentList = getYearlyBudgetDepartmentList(yb);
        Set departments = new HashSet();
        for (Iterator itor = ybDepartmentList.iterator(); itor.hasNext();) {
            YearlyBudgetDepartment ybd = (YearlyBudgetDepartment) itor.next();
            departments.add(ybd.getDepartment());
        }
        yb.setDepartments(departments);
        PurchaseCategory pc = yb.getPurchaseCategory();
        PurchaseSubCategory psc = yb.getPurchaseSubCategory();

        Collection purchaseCategorys;
        Collection purchaseSubCategorys;

        if (pc != null) {
            purchaseCategorys = new ArrayList();
            purchaseCategorys.add(pc);
            if (psc != null) {
                purchaseSubCategorys = new ArrayList();
                purchaseSubCategorys.add(psc);
            } else {
                purchaseSubCategorys = purchaseSubCategoryManager.getEnabledPurchaseSubCategoryOfPurchaseCategory(pc);
            }
        } else {
            purchaseCategorys = purchaseCategoryManager.getEnabledPurchaseCategorySubCategoryOfSiteIncludeGlobal(yb.getSite());
            purchaseSubCategorys = new ArrayList();
            for (Iterator itor = purchaseCategorys.iterator(); itor.hasNext();) {
                PurchaseCategory p = (PurchaseCategory) itor.next();
                purchaseSubCategorys.addAll(p.getEnabledPurchaseSubCategoryList());
            }
        }
        yb.setPurchaseCategorys(purchaseCategorys);
        yb.setPurchaseSubCategorys(purchaseSubCategorys);
    }

    public void updateAndNotifyYearlyBudget(YearlyBudget yb, boolean ignoreAmountCondition) {
        dao.updateYearlyBudget(yb);
        this.setExtraInformationToYearlyBudgetForExecuteFlow(yb);
        yb.setIgnoreAmountCondition(ignoreAmountCondition);
        flowManager.executeNotifyFlow(yb);
    }
    
    public void updateYearBudget(YearlyBudget yb) {
        dao.updateYearlyBudget(yb);
    }

    public boolean canViewYearlyBudgetAmount(YearlyBudget yb,User user) {
        Function function=functionManager.getFunction("ViewYearlyBudgetAmount");
        return canViewBudgetAmount(yb,user, function);
    }
    
    public boolean canViewExpenseBudgetAmount(YearlyBudget yb,User user) {
        Function function=functionManager.getFunction("ViewExpenseBudgetAmount");
        return canViewBudgetAmount(yb,user, function);
    }
    
    private boolean canViewBudgetAmount(YearlyBudget yb,User user, Function function) {
        Department[] departments=this.getBudgetDepartments(yb);
        for (int i = 0; i < departments.length; i++) {
            Department d = departments[i];
            if(userManager.hasDepartmentPower(d,user,function)) return true;
        }
        return false;
    }

    public void setFunctionManager(FunctionManager functionManager) {
        this.functionManager = functionManager;
    }

    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }

}
