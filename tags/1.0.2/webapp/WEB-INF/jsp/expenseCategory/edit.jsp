<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>


<html:javascript formName="expenseCategoryForm" staticJavascript="false"/>
<html:form action="/updateExpenseCategory"
	onsubmit="return validateExpenseCategoryForm(this)">
	<html:hidden property="id" />
	<table width=100% border=0 cellpadding=4 cellspacing=0>
		<tr>
			<td><html:messages id="x_message">${x_message}<br>
			</html:messages></td>
		</tr>
		<tr>
			<td class="bluetext"><bean:message key="exchangeRate.site" />:</td>
			<td><html:hidden property="site_id" />${x_expenseCategory.site.name}</td>
		</tr>
		<tr>
			<td class="bluetext"><bean:message key="expenseCategory.id" />:</td>
			<td>${x_expenseCategory.id}</td>
		</tr>
		<tr>
			<td class="bluetext"><bean:message key="expenseCategory.type" />:</td>
			<td><html:select property="type">
				<c:if test="${sessionScope.LOGIN_USER.locale=='en'}">
					<html:options collection="x_typeList" property="enumCode"
						labelProperty="engShortDescription" />
				</c:if>
				<c:if test="${sessionScope.LOGIN_USER.locale!='en'}">
					<html:options collection="x_typeList" property="enumCode"
						labelProperty="chnShortDescription" />
				</c:if>
			</html:select></td>
		</tr>
		<tr>
			<td class="bluetext"><bean:message key="expenseCategory.description" />:
			</td>
			<td><html:text property="description" size="30" /><span
				class="required">*</span></td>
		</tr>
		<tr>
			<td class="bluetext"><bean:message key="expenseCategory.enabled" />:
			</td>
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
	</table>
	<table width=90% border=0 cellpadding=4 cellspacing=0>
		<tr>
			<td align="right"><html:submit>
				<bean:message key="all.save" />
			</html:submit> <input type="button"
				value="<bean:message key="all.cancel"/>"
				onclick="window.parent.close();" /></td>
		</tr>
	</table>


	<hr align="left" width="100%" />
	<iframe
		src="listExpenseSubCategory.do?pageSize=5&expenseCategory_id=${x_expenseCategory.id}"
		frameborder="0" height="320" width="100%" scrolling="auto"></iframe>
</html:form>

