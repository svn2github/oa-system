/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */
package com.aof.web.struts.action.admin;

import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import com.aof.model.admin.ExpenseCategory;
import com.aof.model.admin.ExpenseSubCategory;
import com.aof.model.admin.Site;
import com.aof.model.admin.TravelGroup;
import com.aof.model.admin.TravelGroupDetail;
import com.aof.model.admin.query.TravelGroupQueryCondition;
import com.aof.model.admin.query.TravelGroupQueryOrder;
import com.aof.model.metadata.EnabledDisabled;
import com.aof.service.admin.ExpenseCategoryManager;
import com.aof.service.admin.ExpenseSubCategoryManager;
import com.aof.service.admin.TravelGroupManager;
import com.aof.utils.SessionTempFile;
import com.aof.web.struts.action.BaseAction;
import com.aof.web.struts.action.ServiceLocator;
import com.aof.web.struts.form.admin.TravelGroupQueryForm;
import com.shcnc.hibernate.PersistentEnum;
import com.shcnc.struts.action.ActionException;
import com.shcnc.struts.action.ActionUtils;
import com.shcnc.struts.form.BeanForm;
import com.shcnc.utils.BeanHelper;
import com.shcnc.utils.ExportUtil;
import com.shcnc.utils.Exportable;

/**
 * the struts action class for domain model TravelGroup
 * @author shilei
 * @version 1.0 (Nov 15, 2005)
 */
public class TravelGroupAction extends BaseAction {

	/**
     * the action method for searching TravelGroup
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TravelGroupManager fm = ServiceLocator.getTravelGroupManager(request);

		List siteList = this.getAndCheckGrantedSiteList(request);
		request.setAttribute("X_SITELIST", siteList);

		TravelGroupQueryForm queryForm = (TravelGroupQueryForm) form;
		if (queryForm.getSite_id() == null) {
			if (siteList.size() > 0) {
				queryForm.setSite_id(((Site) siteList.get(0)).getId()
						.toString());
			}
		}
        
        if(StringUtils.isEmpty(queryForm.getOrder()))
        {
            queryForm.setOrder(TravelGroupQueryOrder.NAME.getName());
            queryForm.setEnabled(EnabledDisabled.ENABLED.toString());
            queryForm.setDescend(false);
        }


		Map conditions = constructQueryMap(queryForm);
        
        String exportType = queryForm.getExportType();
        if (StringUtils.isNotEmpty(exportType)) {
            List data = fm.getTravelGroupList(conditions, 0, -1, TravelGroupQueryOrder.getEnum(queryForm.getOrder()), queryForm.isDescend());

            int index = SessionTempFile.createNewTempFile(request);
            String fileName = "travelGroup";
            String suffix = ExportUtil.export(exportType, data, request, new FileOutputStream(SessionTempFile.getTempFile(index, request)), new Exportable() {

                public void exportHead(List row, HttpServletRequest request) throws Exception {
                    MessageResources messages = getResources(request);
                    row.add(messages.getMessage(getLocale(request), "travelGroup.name"));
                    row.add(messages.getMessage(getLocale(request), "travelGroup.enabled"));
                }

                public void exportRow(List row, Object data, HttpServletRequest request) throws Exception {
                    row.add(BeanHelper.getBeanPropertyValue(data, "name"));
                    if (isEnglish(request))
                        row.add(BeanHelper.getBeanPropertyValue(data, "enabled.engShortDescription"));
                    else
                        row.add(BeanHelper.getBeanPropertyValue(data, "enabled.chnShortDescription"));    
                }
            });
            return new ActionForward("download/" + index + "/" + URLEncoder.encode(fileName, "UTF-8") + '.' + suffix, true);
        }
        
        
        if(queryForm.isFirstInit())
        {
            queryForm.init(fm.getTravelGroupListCount(conditions));
        }
        else
        {
            queryForm.init();
        }
        
		int pageNo =queryForm.getPageNoAsInt();
		int pageSize = queryForm.getPageSizeAsInt();
	

		List result = fm.getTravelGroupList(conditions, pageNo, pageSize,
				TravelGroupQueryOrder.getEnum(queryForm.getOrder()), queryForm
						.isDescend());
		request.setAttribute("X_RESULTLIST", result);
        putEnumListToRequest(request);

		return mapping.findForward("page");
	}

	private Map constructQueryMap(TravelGroupQueryForm queryForm) {
		Map conditions = new HashMap();
		/* id */
		Integer id = ActionUtils.parseInt(queryForm.getId());
		if (id != null) {
			conditions.put(TravelGroupQueryCondition.ID_EQ, id);
		}

		/* keyPropertyList */

		/* kmtoIdList */

		/* mtoIdList */
		Integer site_id = ActionUtils.parseInt(queryForm.getSite_id());
		if (site_id != null) {
			conditions.put(TravelGroupQueryCondition.SITE_ID_EQ, site_id);
		}

		/* property */
		String name = queryForm.getName();
		if (name != null && name.trim().length() != 0) {
			conditions.put(TravelGroupQueryCondition.NAME_LIKE, name);
		}
		Integer enabled = ActionUtils.parseInt(queryForm.getEnabled());
		if (enabled != null ) {
			conditions.put(TravelGroupQueryCondition.ENABLED_EQ, enabled);
		}
		return conditions;
	}

	private void putEnumListToRequest(HttpServletRequest request) {
		request.setAttribute("X_ENABLEDDISABLEDLIST", PersistentEnum
				.getEnumList(EnabledDisabled.class));
	}

	/**
     * the action method for editing travel group
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward edit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TravelGroup travelGroup = getTravelGroupFromRequest(request);
		Site site = travelGroup.getSite();
		this.checkSite(travelGroup.getSite(), request);
		request.setAttribute("x_travelGroup", travelGroup);

		ExpenseCategory ec = this
				.getAndCheckEnabledTravelExpenseCategoryOfSite(site, request);

		request.setAttribute("x_ec", ec);

		if (!isBack(request)) {
			BeanForm travelGroupForm = (BeanForm) getForm(
					"/updateTravelGroup", request);
			travelGroupForm.populate(travelGroup, BeanForm.TO_FORM);
			TravelGroupManager travelGroupManager = ServiceLocator
					.getTravelGroupManager(request);
			List tgdList = travelGroupManager.getDetailOf(travelGroup);
			Map subs = new HashMap();
			Map abroadSubs = new HashMap();
			for (Iterator iter = tgdList.iterator(); iter.hasNext();) {
				TravelGroupDetail tgd = (TravelGroupDetail) iter.next();
				subs.put(String.valueOf(tgd.getExpenseSubCategory().getId()),
						tgd.getAmountLimit() == null ? "" : String.valueOf(tgd
								.getAmountLimit()));

				abroadSubs.put(String.valueOf(tgd.getExpenseSubCategory()
						.getId()), tgd.getAbroadAmountLimit() == null ? ""
						: String.valueOf(tgd.getAbroadAmountLimit()));

			}
			travelGroupForm.set("subs", subs);
			travelGroupForm.set("abroadSubs", abroadSubs);

		}

		putEnumListToRequest(request);

		return mapping.findForward("page");
	}

	private TravelGroup getTravelGroupFromRequest(HttpServletRequest request)
			throws Exception {
		Integer id = ActionUtils.parseInt(request.getParameter("id"));
		TravelGroupManager travelGroupManager = ServiceLocator
				.getTravelGroupManager(request);
		TravelGroup travelGroup = travelGroupManager.getTravelGroup(id);
		if (travelGroup == null)
			throw new ActionException("travelGroup.notFound", id);
		return travelGroup;
	}

	private ActionForward getForwardFor(TravelGroup travelGroup) {
		ActionForward forward = new ActionForward("listTravelGroup.do?site_id="
				+ travelGroup.getSite().getId());
		forward.setRedirect(true);

		return forward;
	}

	private Site getAndCheckSite(HttpServletRequest request) throws Exception {
		return this.getAndCheckSite("site_id", request);
	}

	private ExpenseCategory getAndCheckEnabledTravelExpenseCategoryOfSite(
			Site site, HttpServletRequest request) throws Exception {
		ExpenseCategory ec = getEnabledTravelExpenseCategoryOfSite(site.getId()
				.intValue(), request);
		if (ec == null) {
			throw new ActionException(
					"travelGroup.error.noTravelExpenseCategoryOfSite", site
							.getName());
		}
		if (ec.getEnabledExpenseSubCategoryList().isEmpty()) {
			throw new ActionException(
					"travelGroup.error.noTravelExpenseSubCategoryOfSite", site
							.getName());
		}
		return ec;
	}

	/**
     * the action method for updaing travel group
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward update(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		Site site = this.getAndCheckSite(request);

		BeanForm travelGroupForm = (BeanForm) form;
		TravelGroup travelGroup = new TravelGroup();
		travelGroupForm.populate(travelGroup, BeanForm.TO_BEAN);
		travelGroup.setSite(site);

		ExpenseCategory ec = getAndCheckEnabledTravelExpenseCategoryOfSite(
				site, request);

		List detailList = new ArrayList();

		List escList = ec.getEnabledExpenseSubCategoryList();
		for (Iterator iterator = escList.iterator(); iterator.hasNext();) {
			ExpenseSubCategory esc = (ExpenseSubCategory) iterator.next();
			TravelGroupDetail tgd = new TravelGroupDetail();
			tgd.setExpenseSubCategory(esc);
			tgd.setTravelGroup(travelGroup);
			tgd.setAmountLimit(null);
			tgd.setAbroadAmountLimit(null);

			{
				String sAmount = (String) travelGroupForm.get("subs", String
						.valueOf(esc.getId()));
				if (StringUtils.isNotEmpty(sAmount)) {
					tgd.setAmountLimit(new BigDecimal(sAmount));
				}
			}
			{
				String sAbroadAmount = (String) travelGroupForm.get(
						"abroadSubs", String.valueOf(esc.getId()));
				if (StringUtils.isNotEmpty(sAbroadAmount)) {
					tgd.setAbroadAmountLimit(new BigDecimal(sAbroadAmount));
				}
			}
			detailList.add(tgd);
		}

		TravelGroupManager travelGroupManager = ServiceLocator
				.getTravelGroupManager(request);
		travelGroupManager.updateTravelGroup(travelGroup, detailList);

		return getForwardFor(travelGroup);
	}

	private ExpenseCategory getEnabledTravelExpenseCategoryOfSite(int site_id,
			HttpServletRequest request) throws Exception {
		ExpenseCategoryManager fm = ServiceLocator
				.getExpenseCategoryManager(request);
		ExpenseSubCategoryManager ecm = ServiceLocator
				.getExpenseSubCategoryManager(request);
		ExpenseCategory ec = fm.getEnabledTravelExpenseCategoryOfSite(site_id);
		if(ec!=null)
		ec.setEnabledExpenseSubCategoryList(ecm
				.getEnabledChildrenOfExpenseCategory(ec.getId()));

		return ec;
	}

	/**
     * the action method for creating new travel group
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward newObject(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Site site = this.getAndCheckSite(request);
		request.setAttribute("x_site", site);

		ExpenseCategory ec = this
				.getAndCheckEnabledTravelExpenseCategoryOfSite(site, request);

		request.setAttribute("x_ec", ec);

		if (!isBack(request)) {
			TravelGroup travelGroup = new TravelGroup();
			travelGroup.setSite(site);

			BeanForm travelGroupForm = (BeanForm) getForm(
					"/insertTravelGroup", request);

			travelGroupForm.populate(travelGroup, BeanForm.TO_FORM);
		}

		putEnumListToRequest(request);
		return mapping.findForward("page");
	}

	/**
     * the action method for insert new travel group to db
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward insert(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Site site = this.getAndCheckSite(request);

		BeanForm travelGroupForm = (BeanForm) form;
		TravelGroup travelGroup = new TravelGroup();
		travelGroupForm.populate(travelGroup, BeanForm.TO_BEAN);
		travelGroup.setSite(site);

		ExpenseCategory ec = this
				.getAndCheckEnabledTravelExpenseCategoryOfSite(site, request);

		List detailList = new ArrayList();

		List escList = ec.getEnabledExpenseSubCategoryList();
		for (Iterator iterator = escList.iterator(); iterator.hasNext();) {
			ExpenseSubCategory esc = (ExpenseSubCategory) iterator.next();
			TravelGroupDetail tgd = new TravelGroupDetail();
			tgd.setExpenseSubCategory(esc);
			tgd.setTravelGroup(travelGroup);
			tgd.setAmountLimit(null);
			tgd.setAbroadAmountLimit(null);
			{
				String sAmount = (String) travelGroupForm.get("subs", String
						.valueOf(esc.getId()));
				if (StringUtils.isNotEmpty(sAmount)) {
					tgd.setAmountLimit(new BigDecimal(sAmount));
				}
			}
			{
				String sAbroadAmount = (String) travelGroupForm.get(
						"abroadSubs", String.valueOf(esc.getId()));
				if (StringUtils.isNotEmpty(sAbroadAmount)) {
					tgd.setAbroadAmountLimit(new BigDecimal(sAbroadAmount));
				}
			}
			detailList.add(tgd);
		}

		TravelGroupManager cm = ServiceLocator.getTravelGroupManager(request);
		cm.insertTravelGroup(travelGroup, detailList);

		return getForwardFor(travelGroup);
	}
}