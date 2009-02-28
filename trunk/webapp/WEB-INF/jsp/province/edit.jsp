<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>
<html:javascript formName="provinceForm" staticJavascript="false"/>
<div class="message"><html:messages id="x_message" message="true">${x_message}<br></html:messages></div>
<div class="warningMsg"><html:errors/></div>

<html:form action="/updateProvince" onsubmit="return validateProvinceForm(this)">
	<html:hidden property="id"/>
	<html:hidden property="country_id"/>
	<html:hidden property="site_id"/>
	<table width=90% border=0 cellpadding=4 cellspacing=0>
		<tr>
			<td class="bluetext"><bean:message key="province.id" />:</td>
			<td>${x_province.id}</td>
		</tr>
		<tr>
			<td class="bluetext"><bean:message key="province.engName" />:</td>
			<td><html:text property="engName" /><span
				class="required">*</span></td>
		</tr>
		<tr>
			<td class="bluetext"><bean:message key="province.chnName" />:</td>
			<td><html:text property="chnName" /><span
				class="required">*</span></td>
		</tr>
		<tr>
			<td class="bluetext">Status:</td>
			<td><html:select property="enabled">
				<c:if test="${sessionScope.LOGIN_USER.locale=='en'}">
					<html:options collection="X_ENABLEDDISABLEDLIST"
						property="enumCode" labelProperty="engShortDescription" />
				</c:if>
				<c:if test="${sessionScope.LOGIN_USER.locale!='en'}">
					<html:options collection="X_ENABLEDDISABLEDLIST"
						property="enumCode" labelProperty="chnShortDescription" />
				</c:if>
			</html:select></td>
		</tr>
		<tr>
			<td colspan="2" align="right"><html:submit>
				<bean:message key="all.save" />
			</html:submit>&nbsp;<input type="button"
				value="<bean:message key="all.cancel"/>"
				onclick="window.parent.close();" /></td>
		</tr>
	</table>
</html:form>
