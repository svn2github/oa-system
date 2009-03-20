<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>
<html:javascript formName="expenseAttachmentForm" staticJavascript="false" />
<html:messages id="message">
  <bean:write name="message"/>
  <br/>
</html:messages> 
<html:form action="/insertExpenseAttachment${x_postfix}" method="post" enctype="multipart/form-data" onsubmit="return validateExpenseAttachmentForm(this)" >
	<html:hidden property="expense_id" />
	<table width=100% border=0 cellpadding=4 cellspacing=0>
		<tr>
			<td class="bluetext"><bean:message key="expenseAttachment.title" />:</td>
			<td><html:text property="description"  size="30"/><span class="required">*</span></td>
		</tr>			
		<tr>
			<td class="bluetext"><bean:message key="expenseAttachment.fileContent" />:</td>
			<td><html:file property="fileContent"  size="30"/><span class="required">*</span></td>
		</tr>			
		<tr>
			<td colspan="2"  align="right"><html:submit>
				<bean:message key="all.save" />
			</html:submit></td>
		</tr>
	</table>
</html:form>
