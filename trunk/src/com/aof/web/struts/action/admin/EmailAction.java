/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */
package com.aof.web.struts.action.admin;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.aof.model.admin.query.EmailQueryCondition;
import com.aof.model.admin.query.EmailQueryOrder;
import com.aof.service.admin.EmailManager;
import com.aof.web.struts.action.BaseAction;
import com.aof.web.struts.form.admin.EmailQueryForm;
import com.aof.web.struts.action.ActionUtils;
import com.aof.web.struts.action.ServiceLocator;

/**
 * Email的Action类
 * @author ych
 * @version 1.0 (Nov 14, 2005)
 */
public class EmailAction extends BaseAction {
	
	private static final String FAKE_DATE_BEGIN="1900/01/01";
	private static final String FAKE_DATE_TO="2099/01/01";
	
    /**
     * 查询Email
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
	    EmailManager fm = ServiceLocator.getEmailManager(request);

        EmailQueryForm queryForm = (EmailQueryForm) form;

        Map conditions = constructQueryMap(queryForm);

        if(queryForm.isFirstInit())
		{
			queryForm.init(fm.getEmailListCount(conditions));
		}
		else
		{
			queryForm.init();
		}
		int pageNo=queryForm.getPageNoAsInt();
		int pageSize=queryForm.getPageSizeAsInt();
		
        List result=fm.getEmailList(
        	conditions, pageNo, pageSize, EmailQueryOrder.getEnum(queryForm.getOrder()), queryForm.isDescend());
        request.setAttribute("X_RESULTLIST", result);
        return mapping.findForward("page");
    }
    
    /**
     * 构建查询map
     * @param queryForm
     * @return
     */
    private Map constructQueryMap(EmailQueryForm queryForm) {
        Map conditions = new HashMap();
         /*id*/
		Integer id =
			ActionUtils.parseInt(queryForm.getId());
		if (id != null) 
		{
			conditions.put(EmailQueryCondition.ID_EQ,
				 id);
		}

		/*keyPropertyList*/

		/*kmtoIdList*/

		/*mtoIdList*/


  
		/*property*/
		String mailFrom = 
			queryForm.getMailFrom();
		if(mailFrom != null && mailFrom.trim().length()!=0)
		{
			conditions.put(EmailQueryCondition.MAILFROM_LIKE,
				 mailFrom);
		}
		String mailTo = 
			queryForm.getMailTo();
		if(mailTo != null && mailTo.trim().length()!=0)
		{
			conditions.put(EmailQueryCondition.MAILTO_LIKE,
				 mailTo);
		}
		String subject = 
			queryForm.getSubject();
		if(subject != null && subject.trim().length()!=0)
		{
			conditions.put(EmailQueryCondition.SUBJECT_LIKE,
				 subject);
		}
		String body = 
			queryForm.getBody();
		if(body != null && body.trim().length()!=0)
		{
			conditions.put(EmailQueryCondition.BODY_LIKE,
				 body);
		}
		String createTimeBegin = queryForm.getCreateTimeBegin();
		String createTimeTo = queryForm.getCreateTimeTo();
		
		if ( (createTimeBegin != null && createTimeBegin.trim().length()!=0)
				|| (createTimeTo != null && createTimeTo.trim().length()!=0) )
		{
			if (createTimeBegin==null || createTimeBegin.trim().length()==0) createTimeBegin=FAKE_DATE_BEGIN;
			if (createTimeTo==null || createTimeTo.trim().length()==0) createTimeTo=FAKE_DATE_TO;
			Date queryCreateTimeBegin=ActionUtils.getQueryBeginDateFromDisplayDate(createTimeBegin);
			Date queryCreateTimeTo=ActionUtils.getQueryToDateFromDisplayDate(createTimeTo);
			conditions.put(EmailQueryCondition.CREATETIME_BT, new Object[]{queryCreateTimeBegin,queryCreateTimeTo,} );
		}		
		
		String sentTimeBegin = queryForm.getSentTimeBegin();
		String sentTimeTo = queryForm.getSentTimeTo();
		
		if ( (sentTimeBegin != null && sentTimeBegin.trim().length()!=0)
				|| (sentTimeTo != null && sentTimeTo.trim().length()!=0) )
		{
			if (sentTimeBegin==null || sentTimeBegin.trim().length()==0) sentTimeBegin=FAKE_DATE_BEGIN;
			if (sentTimeTo==null || sentTimeTo.trim().length()==0) sentTimeTo=FAKE_DATE_TO;
			Date querySentTimeBegin=ActionUtils.getQueryBeginDateFromDisplayDate(sentTimeBegin);
			Date querySentTimeTo=ActionUtils.getQueryToDateFromDisplayDate(sentTimeTo);
			conditions.put(EmailQueryCondition.SENTTIME_BT, new Object[]{querySentTimeBegin,querySentTimeTo,} );
		}
		
		Integer failCount =
			ActionUtils.parseInt(queryForm.getFailCount());
		if (failCount != null) 
		{
			conditions.put(EmailQueryCondition.FAILCOUNT_GE,
				 failCount);
		}
		Integer waitToSend =
			ActionUtils.parseInt(queryForm.getWaitToSend());
		if (waitToSend != null) 
		{
			conditions.put(EmailQueryCondition.WAITTOSEND_EQ,
					waitToSend);
		}
        return conditions;
    }


   
}