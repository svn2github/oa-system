/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */
package com.aof.web.struts.action.business.pr;

import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;
import org.springframework.orm.hibernate.HibernateOptimisticLockingFailureException;

import com.aof.model.admin.Department;
import com.aof.model.admin.Site;
import com.aof.model.business.pr.YearlyBudget;
import com.aof.model.business.pr.YearlyBudgetDepartment;
import com.aof.model.business.pr.query.YearlyBudgetDepartmentQueryCondition;
import com.aof.model.business.pr.query.YearlyBudgetQueryOrder;
import com.aof.model.metadata.BudgetStatus;
import com.aof.model.metadata.BudgetType;
import com.aof.model.metadata.YesNo;
import com.aof.service.admin.DepartmentManager;
import com.aof.service.admin.PurchaseCategoryManager;
import com.aof.service.business.pr.YearlyBudgetManager;
import com.aof.utils.SessionTempFile;
import com.aof.web.struts.action.ActionUtils;
import com.aof.web.struts.action.BaseAction;
import com.aof.web.struts.action.ServiceLocator;
import com.aof.web.struts.form.business.pr.YearlyBudgetQueryForm;
import com.shcnc.struts.action.ActionException;
import com.shcnc.struts.action.BackToInputActionException;
import com.shcnc.struts.form.BeanForm;
import com.shcnc.struts.form.DynaValidatorForm;
import com.shcnc.utils.BeanHelper;
import com.shcnc.utils.ExportUtil;
import com.shcnc.utils.Exportable;

/**
 * struts action for domain model YearlyBudget
 * 
 * @author shilei
 * @version 1.0 (Nov 29, 2005)
 */
public class YearlyBudgetAction extends BaseAction {
    
    public class BudgetGroup
    {
        private int size;
        
        private List otherYearlyBudgetList;
        private Set departmentList;
        private YearlyBudget sum;
        private YearlyBudget first;
        
        public int getSize() {
            return size;
        }
        public void setSize(int size) {
            this.size = size;
        }
        public YearlyBudget getSum() {
            return sum;
        }
        public void setSum(YearlyBudget sum) {
            this.sum = sum;
        }
        public Set getDepartmentList() {
            return departmentList;
        }
        public void setDepartmentList(Set theDepartmentList) {
            this.departmentList = theDepartmentList;
        }
        public List getOtherYearlyBudgetList() {
            return otherYearlyBudgetList;
        }
        public void setOtherYearlyBudgetList(List yearlyBudgetList) {
            this.otherYearlyBudgetList = yearlyBudgetList;
        }
        public YearlyBudget getFirst() {
            return first;
        }
        public void setFirst(YearlyBudget first) {
            this.first = first;
        }
        
    }
    
    private List sortYearBudgetList(Set ybSet, YearlyBudgetQueryOrder order, boolean desc) {
        String theOrder = null;
        Object[][] orders={
                {YearlyBudgetQueryOrder.AMOUNT,"amount"},
                {YearlyBudgetQueryOrder.CODE,"code"},
                {YearlyBudgetQueryOrder.ACTUALAMOUNT,"actualAmount"},
                {YearlyBudgetQueryOrder.REMAINAMOUNT,"remainAmount"},
                {YearlyBudgetQueryOrder.SITE,"site.name"},
        };
        
        for (int i = 0; i < orders.length; i++) {
            if(orders[i][0].equals(order)) theOrder=(String) orders[i][1];
        }
        
        if(theOrder==null) throw new RuntimeException("order not found");
        
        if (desc)
            theOrder += " desc";
        return BeanHelper.sortToList(ybSet, theOrder);
    }
    
    private List getListView(List ybdList,YearlyBudgetQueryOrder order, boolean desc) {
        Set ybSet = new HashSet();
        Map ybDepartmentMap = new HashMap();
        
        for (Iterator iter = ybdList.iterator(); iter.hasNext();) {
            YearlyBudgetDepartment ybd = (YearlyBudgetDepartment) iter.next();
            YearlyBudget yb = ybd.getYearlyBudget();
            ybSet.add(yb);
            if (ybDepartmentMap.get(yb) == null)
                ybDepartmentMap.put(yb, new HashSet());
            Set departmentSet = (Set) ybDepartmentMap.get(yb);
            departmentSet.add(ybd.getDepartment());
        }
        
        List ybList=this.sortYearBudgetList(ybSet, order, desc);

        List listView = new ArrayList();
        while (!ybList.isEmpty()) {
            YearlyBudget firstYb = (YearlyBudget) ybList.get(0);
            this.fillGroup(firstYb, ybList, ybDepartmentMap, listView);
        }
        return listView;
    }
    
    private void fillGroup(YearlyBudget firstYb, List ybList, Map ybDepartmentMap, List listView) {

        BudgetGroup group = new BudgetGroup();
        List otherYbList = new ArrayList();
        group.setOtherYearlyBudgetList(otherYbList);
        Set theDepartmentList = (Set) ybDepartmentMap.get(firstYb);
        group.setDepartmentList( theDepartmentList);

        YearlyBudget sumYb = new YearlyBudget();
        sumYb.setActualAmount(firstYb.getActualAmount());
        sumYb.setAmount(firstYb.getAmount());

        group.setSum(sumYb);

        listView.add(group);

        ybList.remove(firstYb);
        
        group.setFirst(firstYb);

        for (ListIterator iter = ybList.listIterator(); iter.hasNext();) {
            YearlyBudget yb = (YearlyBudget) iter.next();
            Set departmentList = (Set) ybDepartmentMap.get(yb);
            if (departmentList.equals(theDepartmentList)) {
                iter.remove();
                otherYbList.add(yb);

                sumYb.setActualAmount(sumYb.getActualAmount().add(yb.getActualAmount()));
                sumYb.setAmount(sumYb.getAmount().add(yb.getAmount()));
            }
        }
        
        group.setSize(otherYbList.size()+1);

    }
    
    private int getYearlyBudgetCount(List listView)
    {
        int count=0;
        for (Iterator iter = listView.iterator(); iter.hasNext();) {
            BudgetGroup group = (BudgetGroup) iter.next();
            count+=group.getSize();
        }
        return count;
    }
    
    
    public ActionForward listHistory(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        YearlyBudget yb=this.getYearlyBudgetFromRequest(request);
        this.checkSite(yb.getSite(),request);
        request.setAttribute("x_yb",yb);
        
        YearlyBudgetManager ym=ServiceLocator.getYearlyBudgetManager(request);
        List history=ym.listHistroy(yb);
        
        request.setAttribute("x_history",history);
        return mapping.findForward("page");
    }
    
    public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        // siteList
        List siteList = this.getAndCheckGrantedSiteList(request);
        DepartmentManager dm = ServiceLocator.getDepartmentManager(request);
        dm.fillDepartment(siteList, true);
        request.setAttribute("x_siteList", siteList);

        YearlyBudgetManager fm = ServiceLocator.getYearlyBudgetManager(request);

        this.putYearListToRequest(request);
        this.putEnumListToRequest(request);

        YearlyBudgetQueryForm queryForm = (YearlyBudgetQueryForm) form;

        if (StringUtils.isEmpty(queryForm.getOrder()))// first
        {
            queryForm.setOrder(YearlyBudgetQueryOrder.CODE.getName());
            request.setAttribute("x_first", Boolean.TRUE);
            return mapping.findForward("page");
        } else// submited
        {
            Map conditions = constructDepartmentQueryMap(queryForm);
            conditions.remove(YearlyBudgetDepartmentQueryCondition.DEPARTMENT_ID_EQ);

            List ybdList = fm.getYearlyBudgetDepartmentList(conditions);
            
            YearlyBudgetQueryOrder order=YearlyBudgetQueryOrder.getEnum(queryForm.getOrder());
            if(order==null) throw new RuntimeException("order not found!");
                
            List listView=this.getListView(ybdList,order,queryForm.isDescend());
            
            String exportType = queryForm.getExportType();
            if (StringUtils.isNotEmpty(exportType)) {
                return exportListView(listView,exportType,request,response);
            }

            request.setAttribute("X_RESULTLIST", listView);
            request.setAttribute("x_count", new Integer(getYearlyBudgetCount(listView)));

            return mapping.findForward("page");
        }
    }
    
    private ActionForward exportListView(List listView, String exportType, HttpServletRequest request, HttpServletResponse response) throws Exception {
        List table=new ArrayList();
        final MessageResources messages = getResources(request);
        for (Iterator iter = listView.iterator(); iter.hasNext();) {
            List row=new ArrayList();
            table.add(row);
            BudgetGroup bg = (BudgetGroup) iter.next();
            row.add(getDepartmentsString(bg.getDepartmentList()));
            
            YearlyBudget yb=bg.first;
            this.addToRow(row,bg.first,this.isEnglish(request));
            
            for (Iterator iterator = bg.getOtherYearlyBudgetList().iterator(); iterator.hasNext();) {
                yb = (YearlyBudget) iterator.next();
                row=new ArrayList();
                table.add(row);
                row.add("");
                this.addToRow(row,yb,this.isEnglish(request));                
            }
            
            row=new ArrayList();
            table.add(row);
            row.add("");
            row.add("");
            row.add("");
            
            row.add(messages.getMessage(getLocale(request), "yearlyBudget.query.sum"));
            row.add(bg.sum.getAmount());
            row.add(bg.sum.getActualAmount());
            row.add(bg.sum.getRemainAmount());
            row.add("");
        }
        
        int index = SessionTempFile.createNewTempFile(request);
        String fileName = "yearlyBudget";
        String suffix = ExportUtil.export(exportType, table, request, new FileOutputStream(SessionTempFile.getTempFile(index, request)), new Exportable() {

            public void exportHead(List row, HttpServletRequest request) throws Exception {
                row.add(messages.getMessage(getLocale(request), "yearlyBudget.department.id"));
                row.add(messages.getMessage(getLocale(request), "yearlyBudget.code"));
                row.add(messages.getMessage(getLocale(request), "yearlyBudget.name"));
                row.add(messages.getMessage(getLocale(request), "yearlyBudget.type"));
                row.add(messages.getMessage(getLocale(request), "yearlyBudget.amount"));
                row.add(messages.getMessage(getLocale(request), "yearlyBudget.actualAmount"));
                row.add(messages.getMessage(getLocale(request), "yearlyBudget.actualAmount"));
                row.add(messages.getMessage(getLocale(request), "yearlyBudget.remainAmount"));
                row.add(messages.getMessage(getLocale(request), "yearlyBudget.version"));
            }

            public void exportRow(List row, Object data, HttpServletRequest request) throws Exception {
                List theRow=(List) data;
                for (Iterator iter = theRow.iterator(); iter.hasNext();) {
                    row.add(iter.next());
                }   
            }
        });
        return new ActionForward("download/" + index + "/" + URLEncoder.encode(fileName, "UTF-8") + '.' + suffix, true);
        
    }
    private void addToRow(List row,YearlyBudget yb,boolean isEnglish)
    {
        row.add(yb.getCode());
        row.add(yb.getName());
        if(isEnglish)
            row.add(yb.getType().getEngShortDescription());
        else
            row.add(yb.getType().getChnShortDescription());
        row.add(yb.getAmount());
        row.add(yb.getActualAmount());
        row.add(yb.getRemainAmount());
        row.add(String.valueOf(yb.getVersion()));

    }
    
    private String getDepartmentsString(Set departmentSet)
    {
        StringBuffer sb=new StringBuffer();
        for (Iterator iter = departmentSet.iterator(); iter.hasNext();) {
            Department d = (Department) iter.next();
            sb.append(d.getName());
            sb.append('\n');
        }
        return sb.toString();
    }
    

    public ActionForward list_freeze(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        // siteList
        List siteList = this.getAndCheckGrantedSiteList(request);
        DepartmentManager dm = ServiceLocator.getDepartmentManager(request);
        dm.fillDepartment(siteList, true);
        request.setAttribute("x_siteList", siteList);

        YearlyBudgetManager fm = ServiceLocator.getYearlyBudgetManager(request);

        this.putYearListToRequest(request);
        this.putEnumListToRequest(request);

        YearlyBudgetQueryForm queryForm = (YearlyBudgetQueryForm) form;

        if (StringUtils.isEmpty(queryForm.getOrder()))// first
        {
            queryForm.setOrder(YearlyBudgetQueryOrder.CODE.getName());
            request.setAttribute("x_first", Boolean.TRUE);
            return mapping.findForward("page");
        } else// submited
        {
            Map conditions = constructDepartmentQueryMap(queryForm);

            YearlyBudgetQueryOrder order=YearlyBudgetQueryOrder.getEnum(queryForm.getOrder());
            if(order==null) throw new RuntimeException("order not found!");
            
            List ybdList = fm.getYearlyBudgetDepartmentList(conditions);

            List ybList=this.getYbList(ybdList,order,queryForm.isDescend());
            
            request.setAttribute("X_RESULTLIST", ybList);
            request.setAttribute("x_count", new Integer(ybList.size()));
            
            Integer isFreeze = ActionUtils.parseInt(queryForm.getIsFreeze());
            if(isFreeze==null){
                request.setAttribute("x_toFreeze",Boolean.TRUE);
                request.setAttribute("x_toUnfreeze",Boolean.TRUE);
            }
            else 
            {
                YesNo yesNo=(YesNo) YesNo.fromEnumCode(YesNo.class,isFreeze);
                if(yesNo.equals(YesNo.YES))
                    request.setAttribute("x_toUnfreeze",Boolean.TRUE);
                else
                    request.setAttribute("x_toFreeze",Boolean.TRUE);
            }

            return mapping.findForward("page");
        }
    }
    
    private List getYbList(List ybdList,YearlyBudgetQueryOrder order, boolean desc) {
        Set ybSet=new HashSet();
        for (Iterator iter = ybdList.iterator(); iter.hasNext();) {
            YearlyBudgetDepartment ybd = (YearlyBudgetDepartment) iter.next();
            ybSet.add(ybd.getYearlyBudget());
            if(ybd.getYearlyBudget().getDepartments()==null)
                ybd.getYearlyBudget().setDepartments(new HashSet());
            ybd.getYearlyBudget().getDepartments().add(ybd.getDepartment());
        }
        return this.sortYearBudgetList(ybSet,order,desc);
    }
    
    
    public ActionForward freeze(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        boolean toFreeze=this.isToFreeze(request);
        DynaValidatorForm theForm=(DynaValidatorForm) form;
        String[] strYearlyBudgetIds=(String[]) theForm.get("yearlyBudgetIds");
        if(strYearlyBudgetIds.length==0) throw new ActionException("yearlyBudget.freeze.idsNotSet");
        Integer[] yearlyBudgetIds=new Integer[strYearlyBudgetIds.length];
        for (int i = 0; i < strYearlyBudgetIds.length; i++) {
            yearlyBudgetIds[i]=ActionUtils.parseInt(strYearlyBudgetIds[i]);
            if(yearlyBudgetIds[i]==null) throw new ActionException("yearlyBudget.freeze.idsError");
        }
        
        YearlyBudgetManager ym=ServiceLocator.getYearlyBudgetManager(request);
        
        for (int i = 0; i < yearlyBudgetIds.length; i++) {
            YearlyBudget yb=ym.getYearlyBudget(yearlyBudgetIds[i]);
            if(yb==null) throw new ActionException("yearlyBudget.freeze.notFound",yearlyBudgetIds[i]);
            boolean frozen=yb.getIsFreeze().equals(YesNo.YES);
            if(frozen && toFreeze) throw new ActionException("yearlyBudget.freeze.alreadyFrozen",yb.getCode());
            if(!frozen && !toFreeze) throw new ActionException("yearlyBudget.freeze.alreadyNotFrozen",yb.getCode());
        }
        
        ym.freeze(yearlyBudgetIds,toFreeze,this.getCurrentUser(request));
        
        if(toFreeze) this.postGlobalMessage("yearlyBudget.freeze.success",request.getSession());
        if(!toFreeze) this.postGlobalMessage("yearlyBudget.unfreeze.success",request.getSession());
        
        return new ActionForward("listYearlyBudget_freeze.do",true);
    }

    private boolean isToFreeze(HttpServletRequest request) {
        return "true".equals(request.getParameter("freeze"));
    }

    private Map constructDepartmentQueryMap(YearlyBudgetQueryForm queryForm) {
        Map conditions = new HashMap();

        BigDecimal amountFrom = ActionUtils.parseBigDecimal(queryForm.getAmountFrom());
        if (amountFrom != null) {
            conditions.put(YearlyBudgetDepartmentQueryCondition.YEARLYBUDGET_AMOUNT_GE, amountFrom);
        }
        
        Integer department_id=ActionUtils.parseInt(queryForm.getDepartment_id());
        if(department_id!=null)
        {
            conditions.put(YearlyBudgetDepartmentQueryCondition.YEARLYBUDGET_HAS_DEPARTMENT, department_id);
        }

        BigDecimal amountTo = ActionUtils.parseBigDecimal(queryForm.getAmountTo());
        if (amountFrom != null) {
            conditions.put(YearlyBudgetDepartmentQueryCondition.YEARLYBUDGET_AMOUNT_LE, amountTo);
        }

        Integer site_id = ActionUtils.parseInt(queryForm.getSite_id());
        if (site_id != null) {
            conditions.put(YearlyBudgetDepartmentQueryCondition.YEARLYBUDGET_SITE_ID_EQ, site_id);
        }
        
        String code = queryForm.getCode();
        if (code != null && code.trim().length() != 0) {
            conditions.put(YearlyBudgetDepartmentQueryCondition.YEARLYBUDGET_CODE_EQ, code);
        }
        
        String name = queryForm.getName();
        if (name != null && name.trim().length() != 0) {
            conditions.put(YearlyBudgetDepartmentQueryCondition.YEARLYBUDGET_NAME_LIKE, name);
        }
        
        Integer type = ActionUtils.parseInt(queryForm.getType());
        if (type != null ) {
            conditions.put(YearlyBudgetDepartmentQueryCondition.YEARLYBUDGET_TYPE_EQ, type);
        }
        
        Integer year = ActionUtils.parseInt(queryForm.getYear());
        if (year != null) {
            conditions.put(YearlyBudgetDepartmentQueryCondition.YEARLYBUDGET_YEAR_EQ, year);
        }
        
        Integer status = ActionUtils.parseInt(queryForm.getStatus());
        if (status != null ) {
            conditions.put(YearlyBudgetDepartmentQueryCondition.YEARLYBUDGET_STATUS_EQ, status);
        }
        
        Integer isFreeze = ActionUtils.parseInt(queryForm.getIsFreeze());
        if (isFreeze != null ) {
           conditions.put(YearlyBudgetDepartmentQueryCondition.YEARLYBUDGET_ISFREEZE_EQ, isFreeze);
        }
        
        return conditions;
    }

//    private Map constructQueryMap(YearlyBudgetQueryForm queryForm) {
//        Map conditions = new HashMap();
//
//        Integer department_id=ActionUtils.parseInt(queryForm.getDepartment_id());
//        if(department_id!=null)
//        {
//            conditions.put(YearlyBudgetQueryCondition.HAS_DEPARTMENT, department_id);
//        }
//        
//        Integer id = ActionUtils.parseInt(queryForm.getId());
//        if (id != null) {
//            conditions.put(YearlyBudgetQueryCondition.ID_EQ, id);
//        }
//
//        BigDecimal amountFrom = ActionUtils.parseBigDecimal(queryForm.getAmountForm());
//        if (amountFrom != null) {
//            conditions.put(YearlyBudgetQueryCondition.AMOUNT_GE, amountFrom);
//        }
//
//        BigDecimal amountTo = ActionUtils.parseBigDecimal(queryForm.getAmountTo());
//        if (amountFrom != null) {
//            conditions.put(YearlyBudgetQueryCondition.AMOUNT_LE, amountTo);
//        }
//
//        Integer site_id = ActionUtils.parseInt(queryForm.getSite_id());
//        if (site_id != null) {
//            conditions.put(YearlyBudgetQueryCondition.SITE_ID_EQ, site_id);
//        }
//        
//        Integer purchaseCategory_id = ActionUtils.parseInt(queryForm.getPurchaseCategory_id());
//        if (purchaseCategory_id != null) {
//            conditions.put(YearlyBudgetQueryCondition.PURCHASECATEGORY_ID_EQ, purchaseCategory_id);
//        }
//        
//        Integer purchaseSubCategory_id = ActionUtils.parseInt(queryForm.getPurchaseSubCategory_id());
//        if (purchaseSubCategory_id != null) {
//            conditions.put(YearlyBudgetQueryCondition.PURCHASESUBCATEGORY_ID_EQ, purchaseSubCategory_id);
//        }
//        
//        Integer creator_id = ActionUtils.parseInt(queryForm.getCreator_id());
//        if (creator_id != null) {
//            conditions.put(YearlyBudgetQueryCondition.CREATOR_ID_EQ, creator_id);
//        }
//        
//        Integer modifier_id = ActionUtils.parseInt(queryForm.getModifier_id());
//        if (modifier_id != null) {
//            conditions.put(YearlyBudgetQueryCondition.MODIFIER_ID_EQ, modifier_id);
//        }
//
//        String code = queryForm.getCode();
//        if (code != null && code.trim().length() != 0) {
//            conditions.put(YearlyBudgetQueryCondition.CODE_EQ, code);
//        }
//        
//        String name = queryForm.getName();
//        if (name != null && name.trim().length() != 0) {
//            conditions.put(YearlyBudgetQueryCondition.NAME_LIKE, name);
//        }
//        
//        Integer type = ActionUtils.parseInt(queryForm.getType());
//        if (type != null ) {
//            conditions.put(YearlyBudgetQueryCondition.TYPE_EQ, type);
//        }
//        
//        Integer year = ActionUtils.parseInt(queryForm.getYear());
//        if (year != null) {
//            conditions.put(YearlyBudgetQueryCondition.YEAR_EQ, year);
//        }
//        
//        String amount = queryForm.getAmount();
//        if (amount != null && amount.trim().length() != 0) {
//            conditions.put(YearlyBudgetQueryCondition.AMOUNT_EQ, amount);
//        }
//        
//        String actualAmount = queryForm.getActualAmount();
//        if (actualAmount != null && actualAmount.trim().length() != 0) {
//            conditions.put(YearlyBudgetQueryCondition.ACTUALAMOUNT_EQ, actualAmount);
//        }
//        
//        Integer status = ActionUtils.parseInt(queryForm.getStatus());
//        if (status != null ) {
//            conditions.put(YearlyBudgetQueryCondition.STATUS_EQ, status);
//        }
//        
//        Integer isFreeze =ActionUtils.parseInt( queryForm.getIsFreeze());
//        if (isFreeze != null) {
//            conditions.put(YearlyBudgetQueryCondition.ISFREEZE_EQ, isFreeze);
//        }
//        
//        String createDate = queryForm.getCreateDate();
//        if (createDate != null && createDate.trim().length() != 0) {
//            conditions.put(YearlyBudgetQueryCondition.CREATEDATE_EQ, createDate);
//        }
//        
//        String modifyDate = queryForm.getModifyDate();
//        if (modifyDate != null && modifyDate.trim().length() != 0) {
//            conditions.put(YearlyBudgetQueryCondition.MODIFYDATE_EQ, modifyDate);
//        }
//        
//        return conditions;
//    }

    private void putEnumListToRequest(HttpServletRequest request) {
        request.setAttribute("x_typeList", BudgetType.getEnumList(BudgetType.class));
        request.setAttribute("x_statusList", BudgetStatus.getEnumList(BudgetStatus.class));
        request.setAttribute("x_YesNoList", YesNo.getEnumList(YesNo.class));
    }

    private YearlyBudget getYearlyBudgetFromRequest(HttpServletRequest request) throws Exception {
        Integer id = ActionUtils.parseInt(request.getParameter("id"));
        YearlyBudgetManager yearlyBudgetManager = ServiceLocator.getYearlyBudgetManager(request);
        YearlyBudget yearlyBudget = yearlyBudgetManager.getYearlyBudget(id);
        if (yearlyBudget == null)
            throw new ActionException("yearlyBudget.notFound", id);
        return yearlyBudget;
    }

    private void fillSiteDepartment(Site site, HttpServletRequest request) {
        DepartmentManager dm = ServiceLocator.getDepartmentManager(request);
        dm.fillDepartment(site, true);

    }

    public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        YearlyBudget yearlyBudget = getYearlyBudgetFromRequest(request);
        this.checkSite(yearlyBudget.getSite(), request);
        this.checkNotFreeze(yearlyBudget);

        request.setAttribute("x_yearlyBudget", yearlyBudget);

        this.fillSiteDepartment(yearlyBudget.getSite(), request);
        request.setAttribute("x_site", yearlyBudget.getSite());

        BeanForm yearlyBudgetForm = (BeanForm) getForm("/updateYearlyBudget", request);

        if (!isBack(request)) {

            yearlyBudgetForm.populateToForm(yearlyBudget);

            // departmentIds
            YearlyBudgetManager ybm = ServiceLocator.getYearlyBudgetManager(request);
            Department[] departments = ybm.getBudgetDepartments(yearlyBudget);

            String[] departmentIds = new String[departments.length];
            for (int i = 0; i < departments.length; i++) {
                departmentIds[i] = departments[i].getId().toString();
            }
            yearlyBudgetForm.set("departments", departmentIds);
        }

        this.putDepartmentIdSetToRequest(yearlyBudgetForm, request);

        this.putYearListToRequest(request);
        this.putPurchaseCategoryListToRequest(yearlyBudget.getSite(),request);
        this.putEnumListToRequest(request);

        return mapping.findForward("page");
    }

    private void putYearListToRequest(HttpServletRequest request) {
        List retVal = new ArrayList();
        Calendar c = Calendar.getInstance();
        String thisYear = String.valueOf(c.get(Calendar.YEAR));
        c.add(Calendar.YEAR, 1);
        String nextYear = String.valueOf(c.get(Calendar.YEAR));
        retVal.add(thisYear);
        retVal.add(nextYear);
        request.setAttribute("x_yearList", retVal);
    }

    private void putPurchaseCategoryListToRequest(Site site,HttpServletRequest request) {
        PurchaseCategoryManager pm = ServiceLocator.getPurchaseCategoryManager(request);
        request.setAttribute("x_purchaseCategoryList", pm.getEnabledPurchaseCategorySubCategoryOfSiteIncludeGlobal(site));
    }

    public ActionForward newObject(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Site site = this.getAndCheckSite("site_id", request);
        this.fillSiteDepartment(site, request);
        request.setAttribute("x_site", site);

        BeanForm yearlyBudgetForm = (BeanForm) getForm("/insertYearlyBudget", request);

        if (!isBack(request)) {
            YearlyBudget yearlyBudget = new YearlyBudget();
            yearlyBudget.setSite(site);

            yearlyBudgetForm.populateToForm(yearlyBudget);
        }

        this.putDepartmentIdSetToRequest(yearlyBudgetForm, request);

        this.putYearListToRequest(request);
        this.putPurchaseCategoryListToRequest(site,request);
        this.putEnumListToRequest(request);

        return mapping.findForward("page");
    }

    private void putDepartmentIdSetToRequest(BeanForm yearlyBudgetForm, HttpServletRequest request) {
        String[] departmentIds = (String[]) yearlyBudgetForm.get("departments");
        Set departmentIdSet = new HashSet();
        if (departmentIds != null) {
            for (int i = 0; i < departmentIds.length; i++) {
                departmentIdSet.add(departmentIds[i]);
            }
        }
        request.setAttribute("x_departmentIdSet", departmentIdSet);
    }

    private void checkNotFreeze(YearlyBudget yb) {
        if (yb.getIsFreeze().equalsYesNo(YesNo.YES))
            throw new ActionException("yearlyBudget.frozen");
    }

    private Department[] getDepartments(BeanForm yearlyBudgetForm, HttpServletRequest request) {
        DepartmentManager dm = ServiceLocator.getDepartmentManager(request);
        String[] sDepartmentIds = (String[]) yearlyBudgetForm.get("departments");
        Department[] departments = new Department[sDepartmentIds.length];
        for (int i = 0; i < sDepartmentIds.length; i++) {
            Integer departmentId = ActionUtils.parseInt(sDepartmentIds[i]);
            if (departmentId == null)
                throw new ActionException("yearlyBudget.departmentId.error");
            departments[i] = dm.getDepartment(departmentId);
            if (departments[i] == null)
                throw new ActionException("yearlyBudget.department.notFound", departmentId);
        }
        return departments;
    }

    public ActionForward update(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        YearlyBudget yb = this.getYearlyBudgetFromRequest(request);
        this.checkNotFreeze(yb);
        this.checkSite(yb.getSite(), request);

        BeanForm yearlyBudgetForm = (BeanForm) form;
        yearlyBudgetForm.populateToBean(yb, request, new String[] { "id" });

        yb.setModifier(this.getCurrentUser(request));

        YearlyBudgetManager yearlyBudgetManager = ServiceLocator.getYearlyBudgetManager(request);

        try{
            yearlyBudgetManager.updateYearlyBudget(this.getYearlyBudgetFromRequest(request),yb, this.getDepartments(yearlyBudgetForm, request),this.getCurrentUser(request));
        }
        catch(HibernateOptimisticLockingFailureException e)
        {
            throw new ActionException("yearlyBudget.alreadyModified");
        }

        this.postGlobalMessage("yearlyBudget.update.success", request.getSession());

        return getEditForwardFor(yb);
    }

    private void checkCodeExists(YearlyBudget yearlyBudget, HttpServletRequest request) {
        YearlyBudgetManager yearlyBudgetManager = ServiceLocator.getYearlyBudgetManager(request);
        if (yearlyBudgetManager.getYearlyBudgetByCode(yearlyBudget.getCode()) != null)
            throw new BackToInputActionException("yearlyBudget.code.duplicate");
    }

    public ActionForward insert(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Site site = this.getAndCheckSite("site_id", request);

        BeanForm yearlyBudgetForm = (BeanForm) form;
        YearlyBudget yearlyBudget = new YearlyBudget();
        yearlyBudgetForm.populateToBean(yearlyBudget, request);

        yearlyBudget.setSite(site);
        yearlyBudget.setCreator(this.getCurrentUser(request));

        this.checkCodeExists(yearlyBudget, request);

        YearlyBudgetManager yearlyBudgetManager = ServiceLocator.getYearlyBudgetManager(request);

        yearlyBudgetManager.insertYearlyBudget(yearlyBudget, this.getDepartments(yearlyBudgetForm, request),this.getCurrentUser(request));

        this.postGlobalMessage("yearlyBudget.insert.success", request.getSession());

        return getEditForwardFor(yearlyBudget);
    }

    private ActionForward getEditForwardFor(YearlyBudget yearlyBudget) {
        return new ActionForward("editYearlyBudget.do?id=" + yearlyBudget.getId(), true);
    }

}