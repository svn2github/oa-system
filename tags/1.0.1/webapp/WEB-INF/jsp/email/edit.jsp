<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>

<html:form action="/updateEmail">
	<table width=90% border=0 cellpadding=4 cellspacing=0>
		<tr>
			<td class="bluetext"><bean:message key="email.id" />:</td>
			<td><bean:write name="x_email" property="id"/></td>
		</tr>
		<tr>
			<td class="bluetext"><bean:message key="email.mailFrom" />:</td>
			<td><html:text property="mailFrom" /></td>
		</tr>			
		<tr>
			<td class="bluetext"><bean:message key="email.mailTo" />:</td>
			<td><html:text property="mailTo" /></td>
		</tr>			
		<tr>
			<td class="bluetext"><bean:message key="email.subject" />:</td>
			<td><html:text property="subject" /></td>
		</tr>			
		<tr>
			<td class="bluetext"><bean:message key="email.body" />:</td>
			<td><html:text property="body" /></td>
		</tr>			
		<tr>
			<td class="bluetext"><bean:message key="email.createTime" />:</td>
			<td><html:text property="createTime" /></td>
		</tr>			
		<tr>
			<td class="bluetext"><bean:message key="email.sentTime" />:</td>
			<td><html:text property="sentTime" /></td>
		</tr>			
		<tr>
			<td class="bluetext"><bean:message key="email.failCount" />:</td>
			<td><html:text property="failCount" /></td>
		</tr>			
		<tr>
			<td class="bluetext"><bean:message key="email.status" />:</td>
			<td><html:text property="status" /></td>
		</tr>			

		<tr>
			<td class="bluetext">Status:</td>
			<td><html:select property="enabled_enumCode">
				<c:if test="${sessionScope.LOGIN_USER.locale=='zh'}">
					<html:options collection="X_ENABLEDDISABLEDLIST"
						property="enumCode" labelProperty="chnShortDescription" />
				</c:if>
				<c:if test="${sessionScope.LOGIN_USER.locale!='zh'}">
					<html:options collection="X_ENABLEDDISABLEDLIST"
						property="enumCode" labelProperty="engShortDescription" />
				</c:if>
			</html:select></td>
		</tr>
		<tr>
			<td colspan="2" align="right"><html:submit>
				<bean:message key="all.save" />
			</html:submit></td>
		</tr>
	</table>
</html:form>
