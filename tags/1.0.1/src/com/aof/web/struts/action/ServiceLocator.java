/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */
package com.aof.web.struts.action;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.aof.service.admin.CityManager;
import com.aof.service.admin.CountryManager;
import com.aof.service.admin.CurrencyManager;
import com.aof.service.admin.CustomerManager;
import com.aof.service.admin.DataTransferManager;
import com.aof.service.admin.DepartmentManager;
import com.aof.service.admin.EmailManager;
import com.aof.service.admin.ExchangeRateManager;
import com.aof.service.admin.ExpenseCategoryManager;
import com.aof.service.admin.ExpenseSubCategoryManager;
import com.aof.service.admin.FunctionManager;
import com.aof.service.admin.GlobalManager;
import com.aof.service.admin.ParameterManager;
import com.aof.service.admin.HotelContractManager;
import com.aof.service.admin.HotelManager;
import com.aof.service.admin.MenuManager;
import com.aof.service.admin.MetadataManager;
import com.aof.service.admin.PriceManager;
import com.aof.service.admin.ProjectCodeManager;
import com.aof.service.admin.ProvinceManager;
import com.aof.service.admin.PurchaseCategoryManager;
import com.aof.service.admin.PurchaseSubCategoryManager;
import com.aof.service.admin.PurchaseTypeManager;
import com.aof.service.admin.RoleManager;
import com.aof.service.admin.SiteManager;
import com.aof.service.admin.SupplierContractManager;
import com.aof.service.admin.SupplierHistoryManager;
import com.aof.service.admin.SupplierItemManager;
import com.aof.service.admin.SupplierManager;
import com.aof.service.admin.SystemLogManager;
import com.aof.service.admin.TravelGroupManager;
import com.aof.service.admin.UserManager;
import com.aof.service.business.expense.ExpenseApproveRequestManager;
import com.aof.service.business.expense.ExpenseAttachmentManager;
import com.aof.service.business.expense.ExpenseManager;
import com.aof.service.business.po.PurchaseOrderApproveRequestManager;
import com.aof.service.business.po.PurchaseOrderItemReceiptManager;
import com.aof.service.business.po.PurchaseOrderManager;
import com.aof.service.business.pr.CapexManager;
import com.aof.service.business.pr.CapexRequestApproveRequestManager;
import com.aof.service.business.pr.PurchaseRequestApproveRequestManager;
import com.aof.service.business.pr.PurchaseRequestManager;
import com.aof.service.business.pr.YearlyBudgetManager;
import com.aof.service.business.rule.ApproverDelegateManager;
import com.aof.service.business.rule.FlowManager;
import com.aof.service.business.rule.RuleManager;
import com.aof.service.business.ta.AirTicketManager;
import com.aof.service.business.ta.TravelApplicationApproveRequestManager;
import com.aof.service.business.ta.TravelApplicationManager;
import com.aof.service.kpi.KPIManager;

/**
 * service locator for service manager
 * 
 * @author shilei
 * @version 1.0 (Nov 15, 2005)
 */
public class ServiceLocator {

    protected static Object getBean(String name, HttpServletRequest request) {
        ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getSession().getServletContext());
        return ctx.getBean(name);
    }

    public static BeanLoader getBeanLoader(HttpServletRequest request) {
        return (BeanLoader) getBean("beanLoader", request);
    }

    public static SiteManager getSiteManager(HttpServletRequest request) {
        return (SiteManager) getBean("siteManager", request);
    }

    public static FunctionManager getFunctionManager(HttpServletRequest request) {
        return (FunctionManager) getBean("functionManager", request);
    }

    public static DepartmentManager getDepartmentManager(HttpServletRequest request) {
        return (DepartmentManager) getBean("departmentManager", request);
    }

    public static UserManager getUserManager(HttpServletRequest request) {
        return (UserManager) getBean("userManager", request);
    }

    public static ExpenseSubCategoryManager getExpenseSubCategoryManager(HttpServletRequest request) {
        return (ExpenseSubCategoryManager) getBean("expenseSubCategoryManager", request);
    }

    public static ExpenseCategoryManager getExpenseCategoryManager(HttpServletRequest request) {
        return (ExpenseCategoryManager) getBean("expenseCategoryManager", request);
    }

    public static CityManager getCityManager(HttpServletRequest request) {
        return (CityManager) getBean("cityManager", request);
    }

    public static ProvinceManager getProvinceManager(HttpServletRequest request) {
        return (ProvinceManager) getBean("provinceManager", request);
    }

    public static CountryManager getCountryManager(HttpServletRequest request) {
        return (CountryManager) getBean("countryManager", request);
    }

    public static CurrencyManager getCurrencyManager(HttpServletRequest request) {
        return (CurrencyManager) getBean("currencyManager", request);
    }

    public static EmailManager getEmailManager(HttpServletRequest request) {
        return (EmailManager) getBean("emailManager", request);
    }

    public static ExchangeRateManager getExchangeRateManager(HttpServletRequest request) {
        return (ExchangeRateManager) getBean("exchangeRateManager", request);
    }

    public static HotelManager getHotelManager(HttpServletRequest request) {
        return (HotelManager) getBean("hotelManager", request);
    }

    public static HotelContractManager getHotelContractManager(HttpServletRequest request) {
        return (HotelContractManager) getBean("hotelContractManager", request);
    }

    public static MenuManager getMenuManager(HttpServletRequest request) {
        return (MenuManager) getBean("menuManager", request);
    }

    public static MetadataManager getMetadataManager(HttpServletRequest request) {
        return (MetadataManager) getBean("metadataManager", request);
    }

    public static GlobalManager getGlobalManager(HttpServletRequest request) {
        return (GlobalManager) getBean("globalManager", request);
    }

    public static RoleManager getRoleManager(HttpServletRequest request) {
        return (RoleManager) getBean("roleManager", request);
    }

    public static PurchaseCategoryManager getPurchaseCategoryManager(HttpServletRequest request) {
        return (PurchaseCategoryManager) getBean("purchaseCategoryManager", request);
    }

    public static PurchaseSubCategoryManager getPurchaseSubCategoryManager(HttpServletRequest request) {
        return (PurchaseSubCategoryManager) getBean("purchaseSubCategoryManager", request);
    }

    public static SupplierManager getSupplierManager(HttpServletRequest request) {
        return (SupplierManager) getBean("supplierManager", request);
    }

    public static TravelGroupManager getTravelGroupManager(HttpServletRequest request) {
        return (TravelGroupManager) getBean("travelGroupManager", request);
    }

    public static PriceManager getPriceManager(HttpServletRequest request) {
        return (PriceManager) getBean("priceManager", request);
    }

    public static TravelApplicationManager getTravelApplicationManager(HttpServletRequest request) {
        return (TravelApplicationManager) getBean("travelApplicationManager", request);
    }

    public static SupplierHistoryManager getSupplierHistoryManager(HttpServletRequest request) {
        return (SupplierHistoryManager) getBean("supplierHistoryManager", request);
    }

    public static RuleManager getRuleManager(HttpServletRequest request) {
        return (RuleManager) getBean("ruleManager", request);
    }

    public static FlowManager getFlowManager(HttpServletRequest request) {
        return (FlowManager) getBean("flowManager", request);
    }

    public static SupplierContractManager getSupplierContractManager(HttpServletRequest request) {
        return (SupplierContractManager) getBean("supplierContractManager", request);
    }

    public static SupplierItemManager getSupplierItemManager(HttpServletRequest request) {
        return (SupplierItemManager) getBean("supplierItemManager", request);
    }

    public static ApproverDelegateManager getApproverDelegateManager(HttpServletRequest request) {
        return (ApproverDelegateManager) getBean("approverDelegateManager", request);
    }

    public static TravelApplicationApproveRequestManager getTravelApplicationApproveRequestManager(HttpServletRequest request) {
        return (TravelApplicationApproveRequestManager) getBean("travelApplicationApproveRequestManager", request);
    }

    public static ExpenseManager getExpenseManager(HttpServletRequest request) {
        return (ExpenseManager) getBean("expenseManager", request);
    }

    public static AirTicketManager getAirTicketManager(HttpServletRequest request) {
        return (AirTicketManager) getBean("airTicketManager", request);
    }

    public static ExpenseAttachmentManager getExpenseAttachmentManager(HttpServletRequest request) {
        return (ExpenseAttachmentManager) getBean("expenseAttachmentManager", request);
    }

    public static CustomerManager getCustomerManager(HttpServletRequest request) {
        return (CustomerManager) getBean("customerManager", request);
    }

    public static ExpenseApproveRequestManager getExpenseApproveRequestManager(HttpServletRequest request) {
        return (ExpenseApproveRequestManager) getBean("expenseApproveRequestManager", request);
    }

    public static PurchaseTypeManager getPurchaseTypeManager(HttpServletRequest request) {
        return (PurchaseTypeManager) getBean("purchaseTypeManager", request);
    }

    public static YearlyBudgetManager getYearlyBudgetManager(HttpServletRequest request) {
        return (YearlyBudgetManager) getBean("yearlyBudgetManager", request);
    }

    public static CapexManager getCapexManager(HttpServletRequest request) {
        return (CapexManager) getBean("capexManager", request);
    }

    public static PurchaseRequestManager getPurchaseRequestManager(HttpServletRequest request) {
        return (PurchaseRequestManager) getBean("purchaseRequestManager", request);
    }

    public static CapexRequestApproveRequestManager getCapexRequestApproveRequestManager(HttpServletRequest request) {
        return (CapexRequestApproveRequestManager) getBean("capexRequestApproveRequestManager", request);
    }

    public static ParameterManager getParameterManager(HttpServletRequest request) {
        return (ParameterManager) getBean("parameterManager", request);
    }

    public static PurchaseRequestApproveRequestManager getPurchaseRequestApproveRequestManager(HttpServletRequest request) {
        return (PurchaseRequestApproveRequestManager) getBean("purchaseRequestApproveRequestManager", request);
    }

    public static PurchaseOrderManager getPurchaseOrderManager(HttpServletRequest request) {
        return (PurchaseOrderManager) getBean("purchaseOrderManager", request);
    }

    public static DataTransferManager getDataTransferManager(HttpServletRequest request) {
        return (DataTransferManager) getBean("dataTransferManager", request);  
    }

    public static PurchaseOrderApproveRequestManager getPurchaseOrderApproveRequestManager(HttpServletRequest request) {
        return (PurchaseOrderApproveRequestManager) getBean("purchaseOrderApproveRequestManager", request);
    }

    public static PurchaseOrderItemReceiptManager getPurchaseOrderItemReceiptManager(HttpServletRequest request) {
        return (PurchaseOrderItemReceiptManager) getBean("purchaseOrderItemReceiptManager", request);
    }

    public static SystemLogManager getSystemLogManager(HttpServletRequest request) {
        return (SystemLogManager) getBean("systemLogManager", request);
    }

    public static KPIManager getKPIManager(HttpServletRequest request) {
        return (KPIManager) getBean("kpiManager", request);
    }
    
    public static ProjectCodeManager getProjectCodeManager(HttpServletRequest request) {
        return (ProjectCodeManager) getBean("projectCodeManager", request);
    }
}
