<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>
<script type="text/javascript">
    function validateForm(form) {
    	if (!validateGlobalParameterForm(form)) return false;
    	if(form["ldapPassword"].length!=0) {
    		if(form["ldapPassword"].value!=form["ldapPasswordAgain"].value) {
    			alert('<bean:message key="globalParameter.passwordNotEqual" />');
    			form["ldapPassword"].focus();
    			return false;
    		}
    	}
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
    	return true;
    }
</script>
<html:javascript formName="globalParameterForm" staticJavascript="false"/>

<html:form action="/updateGlobalParameter" method="post" onsubmit="return validateForm(this);">
	<table width=90% border=0 cellpadding=4 cellspacing=0>
		<html:hidden property="id"/>
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
		<tr><td colspan="2"><font color="blue"><h3><bean:message key="globalParameter.loginSetup" /></h3></font></td></tr>
		<tr>
			<td class="bluetext"><bean:message key="globalParameter.minPasswordLength" />:</td>
			<td><html:text property="minPasswordLength" style="width:30px" /></td>
		</tr>			
		<tr>
			<td class="bluetext"><bean:message key="globalParameter.passwordExpireDay" />:</td>
			<td><html:text property="passwordExpireDay" style="width:30px" /></td>
		</tr>			
		<tr>
			<td class="bluetext"><bean:message key="globalParameter.accountLock" />:</td>
			<td><html:text property="accountLock" style="width:30px" /></td>
		</tr>			
		<tr><td colspan="2"><font color="blue"><h3><bean:message key="globalParameter.smtpSetup" /></h3></font></td></tr>
		<tr>
			<td width="30%" class="bluetext"><bean:message key="globalParameter.smtpAddress" />:</td>
			<td><html:text property="smtpAddress" style="width:200px" /></td>
		</tr>			
		<tr>
			<td class="bluetext"><bean:message key="globalParameter.smtpUsername" />:</td>
			<td><html:text property="smtpUsername" style="width:200px" /></td>
		</tr>			
		<tr>
			<td class="bluetext"><bean:message key="globalParameter.smtpPassword" />:</td>
			<td><html:password property="smtpPassword" style="width:200px" ></html:password></td>
		</tr>	
		<tr><td colspan="2"><font color="blue"><h3><bean:message key="globalParameter.ldapSetup" /></h3></font></td></tr>		
		<tr>
			<td class="bluetext"><bean:message key="globalParameter.isLdapAuth" />:</td>
			<td>
				<html:select property="isLdapAuth">
				<c:if test="${sessionScope.LOGIN_USER.locale=='en'}">
					<html:options collection="X_YESNOLIST"
						property="enumCode" labelProperty="engShortDescription" />
				</c:if>
				<c:if test="${sessionScope.LOGIN_USER.locale!='en'}">
					<html:options collection="X_YESNOLIST"
						property="enumCode" labelProperty="chnShortDescription" />
				</c:if>
				</html:select>
			</td>
		</tr>
		<tr>
			<td class="bluetext"><bean:message key="globalParameter.ldapServerName" />:</td>
			<td><html:text property="ldapServerName" style="width:200px" /></td>
		</tr>
		<tr>
			<td class="bluetext"><bean:message key="globalParameter.ldapServerIp" />:</td>
			<td><html:text property="ldapServerIp" style="width:200px" /></td>
		</tr>
		<tr>
			<td class="bluetext"><bean:message key="globalParameter.ldapServerPort" />:</td>
			<td><html:text property="ldapServerPort" style="width:200px" /></td>
		</tr>
		<tr>
			<td class="bluetext"><bean:message key="globalParameter.ldapRootDN" />:</td>
			<td><html:text property="ldapRootDN" style="width:200px" /></td>
		</tr>
		<tr>
			<td class="bluetext"><bean:message key="globalParameter.ldapUserName" />:</td>
			<td><html:text property="ldapUserName" style="width:200px" /></td>
		</tr>
		<tr>
			<td class="bluetext"><bean:message key="globalParameter.ldapPassword" />:</td>
			<td><html:password property="ldapPassword" style="width:200px" ></html:password></td>
		</tr>
		<tr>
			<td class="bluetext"><bean:message key="globalParameter.repeatLdapPassword" />:</td>
			<td><input type="password" name="ldapPasswordAgain" style="width:200px" /></td>
		</tr>
		<tr>
			<td class="bluetext"><bean:message key="globalParameter.ldapQuery" />:</td>
			<td><html:text property="ldapQuery" style="width:200px" /></td>
		</tr>
		<tr>
			<td class="bluetext"><bean:message key="globalParameter.ldapFilter" />:</td>
			<td><html:text property="ldapFilter" style="width:200px" /></td>
		</tr>
		
		
		<tr><td colspan="2"><font color="blue"><h3><bean:message key="globalParameter.mailReminderSetup" /></h3></font></td></tr>
		<tr>
			<td colspan="2">
			<table width=100% class="data" style="margin: 10px 0 20px 0;">
			<thead>
				<tr bgcolor="#9999ff">
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
		<tr>
			<td colspan="2" align="right"><html:submit>
				<bean:message key="all.save" />
			</html:submit></td>
		</tr>
	</table>
</html:form>
<script type="text/javascript">
    applyRowStyle(document.all('remind_datatable'));
</script>
