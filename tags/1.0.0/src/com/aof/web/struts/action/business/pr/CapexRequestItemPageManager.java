package com.aof.web.struts.action.business.pr;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.DynaBean;

import com.aof.model.admin.PurchaseCategory;
import com.aof.model.admin.PurchaseSubCategory;
import com.aof.model.admin.Site;
import com.aof.model.business.pr.CapexRequest;
import com.aof.service.admin.ExchangeRateManager;
import com.aof.service.admin.PurchaseCategoryManager;
import com.aof.service.admin.PurchaseSubCategoryManager;
import com.aof.service.admin.SupplierManager;
import com.aof.web.struts.action.ServiceLocator;
import com.shcnc.struts.form.DynaBeanComboBox;

public class CapexRequestItemPageManager {

    private DynaBeanComboBox comboPurchaseCategory;

    private DynaBeanComboBox comboPurchaseSubCategory;

    private HttpServletRequest request;

    private CapexRequest capexRequest;
    
    private Site site;

    public CapexRequestItemPageManager(DynaBean form, CapexRequest capexRequest, HttpServletRequest request) {
        this.request = request;
        this.capexRequest = capexRequest;

        this.comboPurchaseCategory = new DynaBeanComboBox("id", "description", form, "capexRequest_capex_purchaseCategory_id");
        this.comboPurchaseSubCategory = new DynaBeanComboBox("id", "description", form, "purchaseSubCategory_id");

        this.site = capexRequest.getCapex().getSite();
    }

    public void process() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        processPurchaseCategory();

        ExchangeRateManager erm = ServiceLocator.getExchangeRateManager(request);
        List exchangeRateList = erm.getEnabledExchangeRateListBySiteIncludeBaseCurrency(site);
        request.setAttribute("x_exchangeRateList", exchangeRateList);

        PurchaseSubCategory psc = (PurchaseSubCategory) comboPurchaseSubCategory.getSelectedItem();
        List supplierList;
        if (psc != null) {
            SupplierManager sm = ServiceLocator.getSupplierManager(request);
            supplierList = sm.getSuitableSupplierListForPurchase(site, psc, exchangeRateList);
        } else {
            supplierList = new ArrayList();
        }
        request.setAttribute("x_supplierList", supplierList);
    }
    
    private void processPurchaseCategory() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        PurchaseSubCategory psc = capexRequest.getCapex().getPurchaseSubCategory();
        if (psc == null) {
            PurchaseCategory pc = capexRequest.getCapex().getPurchaseCategory();
            if (pc == null) {
                PurchaseCategoryManager pcm = ServiceLocator.getPurchaseCategoryManager(request);
                this.comboPurchaseCategory.setList(pcm.getEnabledPurchaseCategoryOfSiteIncludeGlobal(site));
            } else {
                request.setAttribute("x_purchaseCategory", pc);
                List purchaseCategoryList = new ArrayList();
                purchaseCategoryList.add(pc);
                this.comboPurchaseCategory.setList(purchaseCategoryList);
            }
            if (this.comboPurchaseCategory.isNull()) {
                this.comboPurchaseSubCategory.setEmptyList();
            } else {
                PurchaseSubCategoryManager pscm = ServiceLocator.getPurchaseSubCategoryManager(request);
                this.comboPurchaseSubCategory.setList(pscm.getEnabledPurchaseSubCategoryOfPurchaseCategory((PurchaseCategory) this.comboPurchaseCategory
                        .getSelectedItem()));
            }
        } else {
            request.setAttribute("x_purchaseSubCategory", psc);
            request.setAttribute("x_purchaseCategory", psc.getPurchaseCategory());
            List purchaseSubCategoryList = new ArrayList();
            purchaseSubCategoryList.add(psc);
            this.comboPurchaseSubCategory.setList(purchaseSubCategoryList);
            List purchaseCategoryList = new ArrayList();
            purchaseCategoryList.add(psc.getPurchaseCategory());
            this.comboPurchaseCategory.setList(purchaseCategoryList);
        }
        request.setAttribute("x_purchaseCategoryList", this.comboPurchaseCategory.getList());
        request.setAttribute("x_purchaseSubCategoryList", this.comboPurchaseSubCategory.getList());
    }

}
