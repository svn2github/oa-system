/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.web.struts.action.admin;

import java.io.FileOutputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sourceforge.utils.SessionTempFile;
import net.sourceforge.web.struts.action.ServiceLocator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import net.sourceforge.model.admin.Currency;
import net.sourceforge.model.admin.query.CurrencyQueryCondition;
import net.sourceforge.model.admin.query.CurrencyQueryOrder;
import net.sourceforge.model.metadata.EnabledDisabled;
import net.sourceforge.service.admin.CurrencyManager;
import net.sourceforge.web.struts.action.BaseAction;
import net.sourceforge.web.struts.form.admin.CurrencyQueryForm;
import com.shcnc.hibernate.PersistentEnum;
import com.shcnc.struts.action.ActionException;

import com.shcnc.struts.form.BeanForm;
import com.shcnc.utils.BeanUtils;
import com.shcnc.utils.ExportUtil;
import com.shcnc.utils.Exportable;
/**
 * Currency的Action类
 * @author ych
 * @version 1.0 (Nov 14, 2005)
 */
public class CurrencyAction extends BaseAction {
	
    /**
     * 构建一个查询map
     * @param queryForm
     * @return
     */
	private Map constructQueryMap(CurrencyQueryForm queryForm) {
        Map conditions = new HashMap();

        String code = queryForm.getCode();
        if (code != null) {
            code = code.trim();
            if (code.length() == 0) code = null;
        }
        if (code != null) conditions.put(CurrencyQueryCondition.CODE_LIKE, code);

        String name = queryForm.getName();
        if (name != null) {
            name = name.trim();
            if (name.length() == 0) name = null;
        }
        if (name != null) conditions.put(CurrencyQueryCondition.NAME_LIKE, name);
        
        String status = queryForm.getStatus();
        if (status!=null && status.trim().length()!=0) {
            if (status.equals(EnabledDisabled.ENABLED.getEnumCode().toString()))
                conditions.put(CurrencyQueryCondition.STATUS_EQ,EnabledDisabled.ENABLED.getEnumCode());
            if (status.equals(EnabledDisabled.DISABLED.getEnumCode().toString()))
                conditions.put(CurrencyQueryCondition.STATUS_EQ,EnabledDisabled.DISABLED.getEnumCode());
        }
        
        
        return conditions;
    }
	
    /**
     * 查询Currency
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		CurrencyManager cm = ServiceLocator.getCurrencyManager(request);
		
		CurrencyQueryForm queryForm = (CurrencyQueryForm) form;
        if(StringUtils.isEmpty(queryForm.getOrder()))
        {
            queryForm.setStatus(EnabledDisabled.ENABLED.getEnumCode().toString());
            queryForm.setOrder(CurrencyQueryOrder.CODE.getName());
            queryForm.setDescend(false);
        }
		Map conditions = constructQueryMap(queryForm);
        
        String exportType = queryForm.getExportType();
        if (exportType != null && exportType.length() > 0) {
            List data =cm.getCurrencyList(conditions, 0, -1, CurrencyQueryOrder.getEnum(queryForm.getOrder()), queryForm.isDescend());
            
            int index = SessionTempFile.createNewTempFile(request);
            String fileName = "currency";
            String suffix = ExportUtil.export(exportType, data, request, new FileOutputStream(SessionTempFile.getTempFile(index, request)), new Exportable() {

                        public void exportHead(List row, HttpServletRequest request) throws Exception {
                            MessageResources messages = getResources(request);
                            row.add(messages.getMessage(getLocale(request), "currency.code"));
                            row.add(messages.getMessage(getLocale(request), "currency.description"));
                            row.add(messages.getMessage(getLocale(request), "currency.status"));
                        }

                        public void exportRow(List row, Object data, HttpServletRequest request) throws Exception {
                            row.add(BeanUtils.getProperty(data, "code"));
                            row.add(BeanUtils.getProperty(data, "name"));
                            String locale = getCurrentUser(request).getLocale();
                            if ("en".equals(locale)) {
                                row.add(BeanUtils.getProperty(data, "enabled.engShortDescription"));
                            } else {
                                row.add(BeanUtils.getProperty(data, "enabled.chnShortDescription"));
                            }
                        }
                    });
            return new ActionForward("download/" + index + "/" + URLEncoder.encode(fileName, "UTF-8") + '.' + suffix, true);
        }
        
		if(queryForm.isFirstInit())
		{
			queryForm.init(cm.getCurrencyListCount(conditions));
		}
		else
		{
			queryForm.init();
		}
		int pageNo=queryForm.getPageNoAsInt();
		int pageSize=queryForm.getPageSizeAsInt();
        request.setAttribute("X_RESULTLIST", cm.getCurrencyList(conditions, pageNo, pageSize, CurrencyQueryOrder.getEnum(queryForm.getOrder()), queryForm.isDescend()));
        request.setAttribute("X_STARTSEQ",new Integer(pageNo*pageSize+1));
        request.setAttribute("X_ENABLEDDISABLEDLIST", PersistentEnum.getEnumList(EnabledDisabled.class));
        return mapping.findForward("page");
    }
	
    /**
     * 新增Currency
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
	public ActionForward newObject(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (!isBack(request)) {
            Currency c = new Currency();
            BeanForm currencyForm = (BeanForm) getForm("/insertCurrency", request);
            currencyForm.populate(c, BeanForm.TO_FORM);
        }
        request.setAttribute("X_ENABLEDDISABLEDLIST", PersistentEnum.getEnumList(EnabledDisabled.class));
        return mapping.findForward("page");
    }

    /**
     * 插入新增的Currency
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward insert(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        CurrencyManager cm = ServiceLocator.getCurrencyManager(request);
        BeanForm currencyForm = (BeanForm) form;
        
        Currency c = new Currency();
        currencyForm.populate(c, BeanForm.TO_BEAN);
        if (cm.getCurrency(c.getCode())!=null) throw new ActionException("currency.error.exist");
        request.setAttribute("X_OBJECT", cm.insertCurrency(c));
        request.setAttribute("X_ROWPAGE", "currency/row.jsp");
        return mapping.findForward("success");
    }
    
    /**
     * 编辑Currency
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (!isBack(request)) {
        	String code=request.getParameter("code");
        	String seq=request.getParameter("seq");
            CurrencyManager cm = ServiceLocator.getCurrencyManager(request);
            Currency c = cm.getCurrency(code);
            if (c == null) throw new ActionException("currency.error.notFound", code);
            BeanForm currencyForm = (BeanForm) getForm("/updateCurrency", request);
            currencyForm.populate(c, BeanForm.TO_FORM);
            request.setAttribute("seq",seq);
        }
        request.setAttribute("X_ENABLEDDISABLEDLIST", PersistentEnum.getEnumList(EnabledDisabled.class));
        return mapping.findForward("page");
    }

    /**
     * 更新Currency
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward update(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        BeanForm currencyForm = (BeanForm) form;
        String seq=request.getParameter("seq");
        Currency c=new Currency();
        currencyForm.populate(c, BeanForm.TO_BEAN);
        CurrencyManager cm = ServiceLocator.getCurrencyManager(request);
        request.setAttribute("X_OBJECT", cm.updateCurrency(c));
        request.setAttribute("X_ROWPAGE", "currency/row.jsp");
        if (!seq.equals(""))
        	request.setAttribute("X_STARTSEQ",new Integer(seq));
        return mapping.findForward("success");
    }
}
