/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.web.struts.action.business;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import net.sourceforge.model.admin.Department;
import net.sourceforge.model.admin.Site;
import net.sourceforge.model.admin.query.CustomerQueryCondition;
import net.sourceforge.model.admin.query.CustomerQueryOrder;
import net.sourceforge.model.admin.query.UserQueryCondition;
import net.sourceforge.model.admin.query.UserQueryOrder;
import net.sourceforge.model.business.BaseRecharge;
import net.sourceforge.model.business.BuyForTarget;
import net.sourceforge.model.business.Rechargeable;
import net.sourceforge.model.metadata.CustomerType;
import net.sourceforge.model.metadata.EnabledDisabled;
import net.sourceforge.model.metadata.RechargeType;
import net.sourceforge.model.metadata.YesNo;
import net.sourceforge.service.admin.CustomerManager;
import net.sourceforge.service.admin.DepartmentManager;
import net.sourceforge.service.admin.UserManager;
import net.sourceforge.web.struts.action.BaseAction;
import net.sourceforge.web.struts.action.ServiceLocator;
import net.sourceforge.web.struts.form.business.RechargeCustomerQueryForm;
import net.sourceforge.web.struts.form.business.RechargePersonQueryForm;
import com.shcnc.hibernate.PersistentEnum;
import com.shcnc.struts.action.ActionException;
import com.shcnc.struts.action.ActionUtils;
import com.shcnc.struts.form.BeanForm;

/**
 * ´¦ÀíRechargeµÄAction
 * @author nicebean
 * @version 1.0 (2005-11-23)
 */
public class RechargeAction extends BaseAction {
    
    protected void setRechargeInfoToRequest(Rechargeable rechargeTarget, Collection rechargeList, Site s, BeanForm form, HttpServletRequest request) {
        setRechargeInfoToRequest(rechargeTarget, rechargeList, s, form, request, true);
    }
    
    protected void setRechargeInfoToRequest(Rechargeable rechargeTarget, Collection rechargeList, Site s, BeanForm form, HttpServletRequest request, boolean canChangeRecharge) {
        String rechargeTargetClassName = rechargeTarget.getClass().getName();
        rechargeTargetClassName = rechargeTargetClassName.substring(rechargeTargetClassName.lastIndexOf('.') + 1);
        request.setAttribute("X_RECHARGETARGET", rechargeTarget);
        request.setAttribute("X_RECHARGETARGETTYPE", rechargeTargetClassName);
        request.setAttribute("X_RECHARGESITE", s);
        request.setAttribute("x_canChangeRecharge", Boolean.valueOf(canChangeRecharge));
        //boolean canBuyFor = false;
        boolean canRecharge = canChangeRecharge ? YesNo.YES.equals(s.getCanRecharge()) : YesNo.YES.equals(rechargeTarget.getIsRecharge());
        request.setAttribute("x_canRecharge", Boolean.valueOf(canRecharge));
        if (canRecharge) {
            if (!isBack(request)) {
                request.setAttribute("X_RECHARGELIST", rechargeList);
            }
            request.setAttribute("X_RECHARGETYPELIST", PersistentEnum.getEnumList(RechargeType.class));
            request.setAttribute("X_YESNOLIST", PersistentEnum.getEnumList(YesNo.class));
        } else {
            form.set("isRecharge", YesNo.NO.getEnumCode().toString());
        }
        rechargeTarget.setIsRecharge((YesNo)PersistentEnum.fromEnumCode(YesNo.class, ActionUtils.parseInt((String)form.get("isRecharge"))));
        
        if (canChangeRecharge || !canRecharge) {
            //canBuyFor = rechargeTarget instanceof BuyForTarget;               
            //request.setAttribute("x_canBuyFor", Boolean.valueOf(canBuyFor));
        }
        ServiceLocator.getDepartmentManager(request).fillDepartment(s, true);
        request.setAttribute("X_DEPARTMENTLIST", s.getDepartments());
        request.setAttribute("x_rechargeTypeCustomer", RechargeType.CUSTOMER);
        request.setAttribute("x_rechargeTypeEntity", RechargeType.ENTITY);
        request.setAttribute("x_rechargeTypePerson", RechargeType.PERSON);
        request.setAttribute("x_yes", YesNo.YES);
    }

    protected void setRechargeInfoToRequestForView(Rechargeable rechargeTarget, Collection rechargeList, HttpServletRequest request) {
        request.setAttribute("X_RECHARGETARGET", rechargeTarget);
        request.setAttribute("X_RECHARGELIST", rechargeList);
        request.setAttribute("x_recharged", Boolean.valueOf(YesNo.YES.equals(rechargeTarget.getIsRecharge())));
        request.setAttribute("x_showBuyFor", Boolean.valueOf(rechargeTarget instanceof BuyForTarget));
        request.setAttribute("x_rechargeTypePerson", RechargeType.PERSON);
    }
    
    protected List getRechargeInfoFromRequest(Rechargeable rechargeTarget, HttpServletRequest request) {
        String customer_code[] = request.getParameterValues("recharge_customer_code");
        String person_dep_id[] = request.getParameterValues("recharge_person_dep_id");
        String person_id[] = request.getParameterValues("recharge_person_id");
        String recharge_amount[] = request.getParameterValues("recharge_amount");
        
        List result = new ArrayList();

        if (YesNo.YES.equals(rechargeTarget.getIsRecharge())) {
            /* Never clear buyForDepartment and buyForUser
            if (rechargeTarget instanceof BuyForTarget) {
                BuyForTarget buyForTarget = (BuyForTarget) rechargeTarget;
                buyForTarget.setBuyForDepartment(null);
                buyForTarget.setBuyForUser(null);
            }
            */
            
            CustomerManager cm = ServiceLocator.getCustomerManager(request);
            UserManager um = ServiceLocator.getUserManager(request);
            DepartmentManager dm = ServiceLocator.getDepartmentManager(request);
            if (customer_code != null) {
                for (int i = 1; i < customer_code.length; i++) {
                    BaseRecharge br = rechargeTarget.createNewRechargeObj();
                    if (customer_code[i] != null && customer_code[i].length() > 0) {
                        br.setCustomer(cm.getCustomer(customer_code[i]));
                    } else {
                        Integer personDepartmentId = ActionUtils.parseInt(person_dep_id[i]);
                        if (person_dep_id != null) br.setPersonDepartment(dm.getDepartment(personDepartmentId));
                        Integer personId = ActionUtils.parseInt(person_id[i]);
                        if (person_id != null) br.setPerson(um.getUser(personId));
                    }
                    br.setAmount(new BigDecimal(recharge_amount[i]));
                    result.add(br);
                }
            }
        }
        request.setAttribute("X_RECHARGELIST", result);
        return result;
    }
    
    protected ActionForward selectRechargeCustomer(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        RechargeCustomerQueryForm queryForm = (RechargeCustomerQueryForm) form;
        Map conditions = constructQueryMap(queryForm);
        Integer siteId = ActionUtils.parseInt(queryForm.getSiteId());
        if (siteId == null) {
            throw new ActionException("site.notFound", siteId);
        }
        conditions.put(CustomerQueryCondition.SITE_ID_EQ, siteId);
        conditions.put(CustomerQueryCondition.TYPE_EQ, CustomerType.CUSTOMER);
        conditions.put(CustomerQueryCondition.ENABLED_EQ, EnabledDisabled.ENABLED);
        CustomerManager cm =  ServiceLocator.getCustomerManager(request);
        if (queryForm.isFirstInit()) {
            queryForm.init(cm.getCustomerListCount(conditions), 10);
        } else {
            queryForm.init();
        }

        request.setAttribute("X_ACTION", mapping.getPath());
        request.setAttribute("X_RESULTLIST", cm.getCustomerList(conditions, queryForm.getPageNoAsInt(), queryForm.getPageSizeAsInt(), CustomerQueryOrder.getEnum(queryForm.getOrder()), queryForm.isDescend()));
        return mapping.findForward("page");
    }

    protected ActionForward selectRechargeEntity(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        RechargeCustomerQueryForm queryForm = (RechargeCustomerQueryForm) form;
        Map conditions = constructQueryMap(queryForm);
        Integer siteId = ActionUtils.parseInt(queryForm.getSiteId());
        if (siteId == null) {
            throw new ActionException("site.notFound", siteId);
        }
        conditions.put(CustomerQueryCondition.SITE_ID_EQ, siteId);
        conditions.put(CustomerQueryCondition.TYPE_EQ, CustomerType.ENTITY);
        conditions.put(CustomerQueryCondition.ENABLED_EQ, EnabledDisabled.ENABLED);
        CustomerManager cm =  ServiceLocator.getCustomerManager(request);
        if (queryForm.isFirstInit()) {
            queryForm.init(cm.getCustomerListCount(conditions), 10);
        } else {
            queryForm.init();
        }

        request.setAttribute("X_ACTION", mapping.getPath());
        request.setAttribute("X_RESULTLIST", cm.getCustomerList(conditions, queryForm.getPageNoAsInt(), queryForm.getPageSizeAsInt(), CustomerQueryOrder.getEnum(queryForm.getOrder()), queryForm.isDescend()));
        return mapping.findForward("page");
    }

    private Map constructQueryMap(RechargeCustomerQueryForm queryForm) {
        Map conditions = new HashMap();

        String code = queryForm.getCode();
        if (code != null) {
            code = code.trim();
            if (code.length() == 0) code = null;
        }
        if (code != null) conditions.put(CustomerQueryCondition.CODE_LIKE, code);

        String description = queryForm.getDescription();
        if (description != null) {
            description = description.trim();
            if (description.length() == 0) description = null;
        }
        if (description != null) conditions.put(CustomerQueryCondition.DESCRIPTION_LIKE, description);
        
        return conditions;
    }


    protected ActionForward selectRechargePerson(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        RechargePersonQueryForm queryForm = (RechargePersonQueryForm) form;
        Map conditions = constructQueryMap(queryForm);
        DepartmentManager dm = ServiceLocator.getDepartmentManager(request);
        if (request.getParameter("siteId") == null) {
            Integer departmentId = ActionUtils.parseInt(queryForm.getDepartmentId());
            Department d = dm.getDepartment(departmentId);
            if (d == null) {
                throw new ActionException("department.notFound", departmentId);
            }
            request.setAttribute("x_department", d);
        } else {
            Integer siteId = ActionUtils.parseInt(queryForm.getSiteId());
            Site site = ServiceLocator.getSiteManager(request).getSite(siteId);
            if (site == null) {
                throw new ActionException("site.notFound", siteId);
            }
            dm.fillDepartment(site, true);
            request.setAttribute("X_DEPARTMENTLIST", site.getDepartments());
            conditions.put(CustomerQueryCondition.SITE_ID_EQ, siteId);
        }
        conditions.put(UserQueryCondition.ENABLED_EQ, EnabledDisabled.ENABLED.getEnumCode());
        UserManager um =  ServiceLocator.getUserManager(request);
        if (queryForm.isFirstInit()) {
            queryForm.init(um.getUserListCount(conditions), 10);
        } else {
            queryForm.init();
        }

        request.setAttribute("X_ACTION", mapping.getPath());
        request.setAttribute("X_RESULTLIST", um.getUserList(conditions, queryForm.getPageNoAsInt(), queryForm.getPageSizeAsInt(), UserQueryOrder.getEnum(queryForm.getOrder()), queryForm.isDescend()));
        return mapping.findForward("page");
    }

    private Map constructQueryMap(RechargePersonQueryForm queryForm) {
        Map conditions = new HashMap();

        String loginName = queryForm.getLoginName();
        if (loginName != null) {
            loginName = loginName.trim();
            if (loginName.length() == 0) loginName = null;
        }
        if (loginName != null) conditions.put(UserQueryCondition.LOGINNAME_LIKE, loginName);

        String name = queryForm.getName();
        if (name != null) {
            name = name.trim();
            if (name.length() == 0) name = null;
        }
        if (name != null) conditions.put(UserQueryCondition.NAME_LIKE, name);
        
        Integer departmentId = ActionUtils.parseInt(queryForm.getDepartmentId());
        if (departmentId != null) {
            conditions.put(UserQueryCondition.DEPARTMENT_ID_EQ, departmentId);
        }
        
        return conditions;
    }

}
