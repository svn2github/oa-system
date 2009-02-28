<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>


<table width=90% border=0 cellpadding=4 cellspacing=0>
	<tr>
		<td colspan="2"><font color="blue"><b><bean:message key="expenseAttachment.delete.confirm" /></b></font></td>
	</tr>			
	<tr>
		<td class="bluetext"><bean:message key="expenseAttachment.title" />:</td>
		<td>${x_expenseAttachment.description}</td>
	</tr>			
	<tr>
		<td class="bluetext"><bean:message key="expenseAttachment.fileName" />:</td>
		<td>${x_expenseAttachment.fileName}</td>
	</tr>
	<tr>
		<td colspan="2" align="right">
		<input type="button" value="<bean:message key="all.delete"/>" onclick="window.location.href='deleteExpenseAttachmentResult${x_postfix}.do?id=${x_expenseAttachment.id}'"/>
		<input type="button" value="<bean:message key="all.cancel"/>" onclick="window.parent.close();"/>
		</td>
	</tr>
</table>

