<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>
<script type="text/javascript">
    function validateForm(form) {
    	if (!validateSiteParameterForm(form)) return false;
    	if (isObject(form["dueDay"])) {
    		if(form["dueDay"].length!=undefined) {
		    	for (var index=0;index<form["dueDay"].length;index++) {
		    		if (!isAllDigits(form["dueDay"][index].value)) {
		    			alertInteger('<bean:message key="globalParameter.mailReminder.dueDay" />');
		    			form["dueDay"][index].focus();
		    			return false;
		    		}
		    	}
		    	for (var index=0;index<form["intervalDay"].length;index++) {
		    		if (!isAllDigits(form["intervalDay"][index].value)) {
		    			alertInteger('<bean:message key="globalParameter.mailReminder.interval" />');
		    			form["intervalDay"][index].focus();
		    			return false;
		    		}
		    	}
		    	for (var index=0;index<form["maxTime"].length;index++) {
		    		if (!isAllDigits(form["maxTime"][index].value)) {
		    			alertInteger('<bean:message key="globalParameter.mailReminder.maxTimes" />');
		    			form["maxTime"][index].focus();
		    			return false;
		    		}
		    	}
	    	} else {
	    		if (!isAllDigits(form["dueDay"].value)) {
	    			alertInteger('<bean:message key="globalParameter.mailReminder.dueDay" />');
	    			form["dueDay"].focus();
	    			return false;
	    		}
	    		if (!isAllDigits(form["intervalDay"].value)) {
	    			alertInteger('<bean:message key="globalParameter.mailReminder.interval" />');
	    			form["intervalDay"].focus();
	    			return false;
	    		}
	    		if (!isAllDigits(form["maxTime"].value)) {
	    			alertInteger('<bean:message key="globalParameter.mailReminder.maxTimes" />');
	    			form["maxTime"].focus();
	    			return false;
	    		}
	    	}
    	}
    	if (form["interval"].value=='') {
    		form["intervalMin"].value='';
    	} else {
	    	if (!isAllDigits(form["interval"].value)) {
	    		alertInteger('<bean:message key="dataTransferParameter.interval" />');
				form["interval"].focus();
				return false;
	    	}
	    	if (form["intervalType"].value=="1") {
		    	form["intervalMin"].value=parseInt(form["interval"].value)*60;
	    	} else {
	    		form["intervalMin"].value=form["interval"].value;
	    	}
	    }
	    if (trim(form["startTime"].value)!='' && trim(form["interval"].value)!='' && trim(form["timePerDay"].value)!='' && trim(form["serverAddress"].value)=='') {
	    	alertRequired('<bean:message key="dataTransferParameter.serverAddress" />');
			form["serverAddress"].focus();
			return false;
	    }
   	   	return true;
    }
    
    function changeSite(combo)
	{
		window.location.href="editSiteParameter.do?site_id="+combo.value;
	}
	
	function startJob(siteId) 
	{
		window.location.href="startJob.do?site_id="+siteId;
	}
	
</script>
<html:javascript formName="siteParameterForm" staticJavascript="false"/>

<html:form action="/updateSiteParameter" method="post" onsubmit="return validateForm(this);">
	<table width=90% border=0 cellpadding=4 cellspacing=0>
		<tr>
			<td colspan="2">
				<html:errors/>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<span style="color:green;font-weight:bold">
				<html:messages id="x_message" message="true">
					${x_message}
				</html:messages>
				</span>
			</td>
		</tr>
	</table>
	<table width=90% border=0 cellpadding=0 cellspacing=0>	
		<tr>
			<td class="bluetext"><bean:message key="siteParameter.site"/>:</td>
			<td align="left"><html:select property="site_id" onchange="changeSite(this)">
				<html:options collection="x_siteList" property="id"
					labelProperty="name" />
			</html:select></td>
		</tr>
	</table>
	<table width=90% border=0 cellpadding=4 cellspacing=0>	
				
		<tr><td colspan="2"><font color="blue"><h3><bean:message key="globalParameter.mailReminderSetup" /></h3></font></td></tr>
		<tr>
			<td colspan="2">
			<table width=100% class="data" style="margin: 10px 0 20px 0;">
			<thead>
				<tr class="new_bg">
					<th align="center">
						<bean:message key="globalParameter.mailReminder.event" />
					</th>
					<th align="center">
						<bean:message key="globalParameter.mailReminder.dueDay" />
					</th>
					<th align="center">
						<bean:message key="globalParameter.mailReminder.interval" />
					</th>
					<th align="center">
						<bean:message key="globalParameter.mailReminder.maxTimes" />
					</th>
				</tr>
			</thead>
			<tbody id="remind_datatable">
				<logic:iterate id="X_OBJECT" name="x_mailReminderList">
				<tr>
					<td>
					<input type="hidden" name="type" value="${X_OBJECT.type}"/>
					<span style="color:${X_OBJECT.type.color}">
				      <c:if test="${sessionScope.LOGIN_USER.locale=='en'}">${X_OBJECT.type.engDescription}</c:if>
				      <c:if test="${sessionScope.LOGIN_USER.locale!='en'}">${X_OBJECT.type.chnDescription}</c:if>
				    </span>  
					</td>
					<td><input type="text" name="dueDay" value="${X_OBJECT.dueDay}" size="1"></td>
					<td><input type="text" name="intervalDay" value="${X_OBJECT.intervalDay}" size="1"></td>
					<td><input type="text" name="maxTime" value="${X_OBJECT.maxTime}" size="1"></td>
				</tr>
				</logic:iterate>
			</tbody>
			</table>
			
			</td>
		</tr>
		
		<tr><td colspan="2"><font color="blue"><h3><bean:message key="siteParameter.dataTransferSetup" /></h3></font></td></tr>
		<tr>
			<td class="bluetext"><bean:message key="dataTransferParameter.startTime" />:</td>
			<td><html:text property="startTime" style="width:40px" maxlength="5"/></td>
		</tr>	
		<tr>
			<td class="bluetext"><bean:message key="dataTransferParameter.timePerDay" />:</td>
			<td><html:text property="timePerDay" style="width:40px" /></td>
		</tr>			
		<tr>
			<td class="bluetext"><bean:message key="dataTransferParameter.interval" />:</td>
			<td>
				<html:text property="interval" style="width:40px" />
				<html:hidden property="intervalMin" />
				<html:select property="intervalType">
					<html:option value="1"><bean:message key="dataTransferParameter.intervalType.hour" /></html:option>
					<html:option value="2"><bean:message key="dataTransferParameter.intervalType.minute" /></html:option>
				</html:select>
			</td>
		</tr>			
		<tr>
			<td class="bluetext"><bean:message key="dataTransferParameter.exprotFileType" />:</td>
			<td>
				<html:select property="exportFileType">
			      <c:if test="${sessionScope.LOGIN_USER.locale=='en'}"><html:options collection = "x_exportFileTypeList" property = "enumCode" labelProperty = "engShortDescription"/></c:if>
			      <c:if test="${sessionScope.LOGIN_USER.locale!='en'}"><html:options collection = "x_exportFileTypeList" property = "enumCode" labelProperty = "chnShortDescription"/></c:if>
			    </html:select>
			</td>
		</tr>
		<tr>
			<td class="bluetext"><bean:message key="dataTransferParameter.succEmail" />:</td>
			<td><html:text property="succEmail" style="width:200px" /></td>
		</tr>			
		<tr>
			<td class="bluetext"><bean:message key="dataTransferParameter.failEmail" />:</td>
			<td><html:text property="failEmail" style="width:200px" /></td>
		</tr>			
		
		<tr>
			<td class="bluetext"><bean:message key="dataTransferParameter.serverAddress" />:</td>
			<td><html:text property="serverAddress" style="width:200px" /></td>
		</tr>			
		<tr>
			<td class="bluetext"><bean:message key="dataTransferParameter.serverPort" />:</td>
			<td><html:text property="serverPort" style="width:200px" /></td>
		</tr>	
		<tr>
			<td class="bluetext"><bean:message key="dataTransferParameter.serverUserName" />:</td>
			<td><html:text property="serverUserName" style="width:200px" /></td>
		</tr>		
		<tr>
			<td class="bluetext"><bean:message key="dataTransferParameter.serverPassword" />:</td>
			<td><html:password property="serverPassword" style="width:200px" ></html:password></td>
		</tr>
		<tr>
			<td class="bluetext"><bean:message key="dataTransferParameter.serverDir" />:</td>
			<td><html:text property="serverDir" style="width:200px" /></td>
		</tr>
		
		<tr>
			<td colspan="2" align="right">
			<html:submit>
				<bean:message key="all.save" />
			</html:submit>
			<input type="button" value="<bean:message key="dataTransferParameter.startJob"/>" onclick='startJob("${x_dataTransferParameter.site.id}")'>
			</td>
		</tr>
	</table>
</html:form>
<script type="text/javascript">
    applyRowStyle(document.all('remind_datatable'));
</script>
