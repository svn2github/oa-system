<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>

<html:form action="/updateExpenseAttachment">
	<table width=90% border=0 cellpadding=4 cellspacing=0>
		<tr>
			<td class="bluetext"><bean:message key="expenseAttachment.id" />:</td>
			<td>
				<bean:write name="x_expenseAttachment" property="id"/>
				<html:hidden property="id"/>
			</td>
		</tr>
		<tr>
			<td class="bluetext"><bean:message key="expenseAttachment.expense.id" />:</td>
			<td>
				<html:select property="expense_id" >
				</html:select>
			</td>
		</tr>			
		<tr>
			<td class="bluetext"><bean:message key="expenseAttachment.fileSize" />:</td>
			<td><html:text property="fileSize" /></td>
		</tr>			
		<tr>
			<td class="bluetext"><bean:message key="expenseAttachment.fileName" />:</td>
			<td><html:text property="fileName" /></td>
		</tr>			
		<tr>
			<td class="bluetext"><bean:message key="expenseAttachment.description" />:</td>
			<td><html:text property="description" /></td>
		</tr>			
		<tr>
			<td class="bluetext"><bean:message key="expenseAttachment.uploadDate" />:</td>
			<td><html:text property="uploadDate" /></td>
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
