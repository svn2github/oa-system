/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.web.struts.action.admin;

import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sourceforge.utils.SessionTempFile;
import net.sourceforge.web.struts.action.ServiceLocator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import net.sourceforge.model.admin.ExchangeRate;
import net.sourceforge.model.admin.Site;
import net.sourceforge.model.admin.query.ExchangeRateQueryCondition;
import net.sourceforge.model.admin.query.ExchangeRateQueryOrder;
import net.sourceforge.model.metadata.EnabledDisabled;
import net.sourceforge.service.admin.CurrencyManager;
import net.sourceforge.service.admin.ExchangeRateManager;
import net.sourceforge.service.admin.SiteManager;
import net.sourceforge.web.struts.action.BaseAction;
import net.sourceforge.web.struts.form.admin.ExchangeRateQueryForm;
import com.shcnc.struts.action.ActionException;
import com.shcnc.struts.action.ActionUtils;
import com.shcnc.struts.form.BeanForm;
import com.shcnc.utils.BeanUtils;
import com.shcnc.utils.ExportUtil;
import com.shcnc.utils.Exportable;

/**
 * ExchangeRate的Action类
 * @author ych
 * @version 1.0 (Nov 14, 2005)
 */
public class ExchangeRateAction extends BaseAction {
    
    /**
     * 构建查询map
     * @param queryForm
     * @return
     */
	private Map constructQueryMap(ExchangeRateQueryForm queryForm) {
        Map conditions = new HashMap();
        
        String id=queryForm.getSiteId();
        if (id!=null) {
        	id=id.trim();
        	if (id.length()==0) id=null;
        }
        if (id!=null) conditions.put(ExchangeRateQueryCondition.SITE_ID_EQ,id);
        
        String code = queryForm.getCurrencyCode();
        if (code != null) {
            code = code.trim();
            if (code.length() == 0) code = null;
        }
        if (code != null) conditions.put(ExchangeRateQueryCondition.CURRENCY_CODE_LIKE, code);
        
        conditions.put(ExchangeRateQueryCondition.CURRENCY_STATUS_EQ,EnabledDisabled.ENABLED.getEnumCode().toString());
        
        return conditions;
    }
	
    /**
     * 查询ExchangeRate
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ExchangeRateManager em = ServiceLocator.getExchangeRateManager(request);
		SiteManager sm = ServiceLocator.getSiteManager(request);
		List siteList=this.getAndCheckGrantedSiteList(request);
		request.setAttribute("X_SITELIST",siteList);
		ExchangeRateQueryForm queryForm = (ExchangeRateQueryForm) form;
		if (queryForm.getSiteId()==null) {
			if (siteList.size()>0) {
				queryForm.setSiteId(((Site)siteList.get(0)).getId().toString());
				request.setAttribute("X_BASECURRENCY",((Site)siteList.get(0)).getBaseCurrency().getCode());
			}
		} else {
			request.setAttribute("X_BASECURRENCY",sm.getSite(new Integer(queryForm.getSiteId())).getBaseCurrency().getCode());
		}
		Map conditions = constructQueryMap(queryForm);
        
        String exportType = queryForm.getExportType();
        if (exportType != null && exportType.length() > 0) {
            List data =em.getExchangeRateList(conditions, 0, -1, ExchangeRateQueryOrder.getEnum(queryForm.getOrder()), queryForm.isDescend());
            
            int index = SessionTempFile.createNewTempFile(request);
            String fileName = "exchangeRate";
            String suffix = ExportUtil.export(exportType, data, request, new FileOutputStream(SessionTempFile.getTempFile(index, request)), new Exportable() {

                        public void exportHead(List row, HttpServletRequest request) throws Exception {
                            MessageResources messages = getResources(request);
                            row.add(messages.getMessage(getLocale(request), "exchangeRate.tablehead.code"));
                            row.add(messages.getMessage(getLocale(request), "exchangeRate.tablehead.name"));
                            row.add(messages.getMessage(getLocale(request), "exchangeRate.tablehead.exchangeRate"));
                        }

                        public void exportRow(List row, Object data, HttpServletRequest request) throws Exception {
                            row.add(BeanUtils.getProperty(data, "currency.code"));
                            row.add(BeanUtils.getProperty(data, "currency.name"));
                            row.add(BeanUtils.getProperty(data, "exchangeRate"));
                        }
                    });
            return new ActionForward("download/" + index + "/" + URLEncoder.encode(fileName, "UTF-8") + '.' + suffix, true);
        }
        
		if(queryForm.isFirstInit())
		{
			queryForm.init(em.getExchangeRateListCount(conditions));
		}
		else
		{
			queryForm.init();
		}
		int pageNo=queryForm.getPageNoAsInt();
		int pageSize=queryForm.getPageSizeAsInt();
		
        request.setAttribute("X_RESULTLIST", em.getExchangeRateList(conditions, pageNo, pageSize, ExchangeRateQueryOrder.getEnum(queryForm.getOrder()), queryForm.isDescend()));
        request.setAttribute("X_STARTSEQ",new Integer(pageNo*pageSize+1));
        return mapping.findForward("page");
    }
	
    /**
     * 新增ExchangeRate
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
	public ActionForward newObject(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (!isBack(request)) {
        	String siteId=request.getParameter("siteId");
        	SiteManager sm = ServiceLocator.getSiteManager(request);
        	Site site=sm.getSite(new Integer(siteId));
            this.checkSite(site,request);
            ExchangeRate e = new ExchangeRate();
            e.setSite(site);
            e.setExchangeRate(new BigDecimal(0d));
            BeanForm exchangeRateForm = (BeanForm) getForm("/insertExchangeRate", request);
            exchangeRateForm.populate(e, BeanForm.TO_FORM);
        }
        CurrencyManager cm =  ServiceLocator.getCurrencyManager(request);
        request.setAttribute("X_CURRENCYLIST",cm.getAllEnabledCurrencyList());
        return mapping.findForward("page");
    }

    /**
     * 插入新增的ExchangeRate
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward insert(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Site site = getAndCheckSite(request);
        ExchangeRateManager em = ServiceLocator.getExchangeRateManager(request);
        BeanForm exchangeRateForm = (BeanForm) form;
        CurrencyManager cm=ServiceLocator.getCurrencyManager(request);
        ExchangeRate e = new ExchangeRate();
        exchangeRateForm.populate(e, BeanForm.TO_BEAN);
        e.setSite(site);
        e.setCurrency(cm.getCurrency(e.getCurrency().getCode()));
        if (em.getExchangeRate(e.getCurrency(),e.getSite())!=null) throw new ActionException("exchangeRate.error.exist");
        request.setAttribute("X_OBJECT", em.saveExchangeRate(e));
        request.setAttribute("X_ROWPAGE", "exchangeRate/row.jsp");
        
        return mapping.findForward("success");
    }
    
    private Site getAndCheckSite(HttpServletRequest request) throws Exception {
        return this.getAndCheckSite("site_id", request);
    }
    /**
     * 编辑ExchangeRate
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (!isBack(request)) {
            ExchangeRate e=getExchangeRateFromRequest(request);
            checkSite(e.getSite(), request);
            BeanForm exchangeRateForm = (BeanForm) getForm("/updateExchangeRate", request);
            exchangeRateForm.populate(e, BeanForm.TO_FORM);
        }
        return mapping.findForward("page");
    }

    private ExchangeRate getExchangeRateFromRequest(HttpServletRequest request) {
        Integer id = ActionUtils.parseInt(request.getParameter("id"));
        ExchangeRateManager em = ServiceLocator.getExchangeRateManager(request);
        ExchangeRate exchangeRate=em.getExchangeRate(id);
        if (exchangeRate == null)
            throw new ActionException("exchangeRate.error.notFound");
        return exchangeRate;
    }
    
    /**
     * 更新ExchangeRate
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward update(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Site site = getAndCheckSite(request);
        BeanForm exchangeRateForm = (BeanForm) form;
        ExchangeRate e=new ExchangeRate();
        exchangeRateForm.populate(e, BeanForm.TO_BEAN);
        e.setSite(site);
        CurrencyManager cm=ServiceLocator.getCurrencyManager(request);
        e.setCurrency(cm.getCurrency(e.getCurrency().getCode()));
        ExchangeRateManager em = ServiceLocator.getExchangeRateManager(request);
        request.setAttribute("X_OBJECT", em.saveExchangeRate(e));
        request.setAttribute("X_ROWPAGE", "exchangeRate/row.jsp");
        return mapping.findForward("success");
    }
}
