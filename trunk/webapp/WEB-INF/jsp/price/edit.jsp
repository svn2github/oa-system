<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>
<html:javascript formName="priceForm" staticJavascript="true"/>
<html:form action="/updatePrice${x_version}" onsubmit="return validatePriceForm(this)">
	<html:hidden property="id" />
	<html:hidden property="hotel_id" />
	<table width=90% border=0 cellpadding=4 cellspacing=0>
		<tr>
			<td colspan="2">
				<html:messages id="x_message">
					${x_messages}<br>
				</html:messages>
				
				<html:messages id="x_message" message="true">
					${x_messages}<br>
				</html:messages>
				
			</td>
		</tr>
		<tr>
			<td class="bluetext"><bean:message key="price.id" />:</td>
			<td><bean:write name="x_price" property="id" /></td>
		</tr>
		<tr>
			<td class="bluetext"><bean:message key="price.room" />:</td>
			<td><html:text property="room" /><span class="required">*</span></td>
		</tr>
		<tr>
			<td class="bluetext"><bean:message key="price.price" />:</td>
			<td><html:text property="price" /><span class="required">*</span></td>
		</tr>
		<tr>
			<td class="bluetext"><bean:message key="price.discount" />:</td>
			<td><html:text property="discount" />%<span class="required">*</span></td>
		</tr>
		<tr>
			<td class="bluetext"><bean:message key="price.description" />:</td>
			<td><html:text property="description" /></td>
		</tr>
		<tr>
			<td class="bluetext"><bean:message key="price.network" />:</td>
			<td><html:text property="network" /></td>
		</tr>
		<tr>
			<td class="bluetext"><bean:message key="price.breakfast" />:</td>
			<td><html:text property="breakfast" /></td>
		</tr>
		<tr>
			<td class="bluetext"><bean:message key="price.enabled" />:</td>
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
			</html:submit></td>
		</tr>
	</table>
</html:form>
