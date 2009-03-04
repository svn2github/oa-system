/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */
package com.aof.web.struts.action.business.pr;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.DynaBean;

import com.aof.model.admin.Department;
import com.aof.model.admin.Site;
import com.aof.service.admin.UserManager;
import com.aof.web.struts.action.ServiceLocator;
import com.shcnc.struts.form.DynaBeanComboBox;

/**
 * page manager for newPurchaseRequest_other.do
 * @author shilei
 * @version 1.0 (Dec 15, 2005)
 */
public class PurchaseRequestNewOtherPageManager {

    private DynaBeanComboBox comboSite;

    private DynaBeanComboBox comboDepartment;

    private HttpServletRequest request;

    private List siteList;
    
    private Site site;
    
    private Department department;

    public PurchaseRequestNewOtherPageManager(List siteList,DynaBean form, HttpServletRequest request) {
        this.request = request;
        this.siteList=siteList;

        this.comboSite = new DynaBeanComboBox("id", "name", form, "department_site_id");
        this.comboDepartment = new DynaBeanComboBox("id", "name", form, "department_id");
    }

    public void process() throws Exception {
        this.processComboSite();
        this.processComboDepartment();
        this.processRequestor();
    }

    private void processRequestor() {
        List requestorList = null;
        if (this.department != null) {
            UserManager um = ServiceLocator.getUserManager(request);
            requestorList = um.getEnabledUserListOfDepartment(this.department);
        } else {
            requestorList = Collections.EMPTY_LIST;
        }
        request.setAttribute("x_requestorList", requestorList);
    }

    private void processComboDepartment() throws Exception{
        if(site!=null)
            this.comboDepartment.setList(site.getDepartments());
        else
            this.comboDepartment.setEmptyList();
        this.department = (Department) comboDepartment.getSelectedItem();
    }

    private void processComboSite() throws Exception{
        this.comboSite.setList(siteList);
        this.site = (Site) comboSite.getSelectedItem();
    }
    
}