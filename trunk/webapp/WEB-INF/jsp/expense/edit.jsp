<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>
<%@ page import="net.sourceforge.model.metadata.*"%>
<script>

	function save()
	{
		with(document.expenseForm)
		{
			if(validateForm(document.expenseForm)) {
				if(confirm("<bean:message key="expense.submit.confirm"/>")) {
					document.expenseForm.submit();
				}
			}
		}
	}
	
	function saveAsDraft()
	{
		with(document.expenseForm)
		{
			draft.value="true";
			if(validateForm(document.expenseForm))
				document.expenseForm.submit();
		}
	}
	
	function deleteMe()
	{
		if(confirm("<bean:message key="expense.delete.confirm" />"))
		{
			var url="deleteExpense${x_postfix}.do?id=${x_expense.id}";
		}
		window.location.href=url;
		
	}

	
	function viewApprover() {
		if (validateForm(document.expenseForm)) {
			var form=document.expenseForm;
			var action = form.action;
			var i = action.lastIndexOf('.');
			if (i == -1) {
				form.action = action + "_viewApprover";
			} else {
				form.action = action.substring(0, i) + "_viewApprover" + action.substring(i);
			}
			form.target = "viewapprover";
			form.submit();
			form.action = action;
			form.target = "";
			document.getElementById("oldApprovers").style.display = "none";
		}
	}
	
	function viewApprover_callback(content) {
		with (document.getElementById("newApprovers")) {
			//style.display = "block";
			innerHTML = content;
			scrollIntoView();
		}
	}
	
	
	
</script>
<jsp:include page="baseEdit.jsp"/>
<table width="100%">
	<tr>
		<td><logic:empty name="X_APPROVELIST"><input type="button" value="<bean:message key="expense.viewApprover"/>" onclick="viewApprover();"/></logic:empty></td>
		<td align="right">
			<input type="button" value="<bean:message key="expense.saveAsDraft" />" onclick="saveAsDraft()"/>
			<input type="button" value="<bean:message key="all.delete" />" onclick="deleteMe()"/>
			<input type="button" value="<bean:message key="all.submit" />" onclick="save()"/>
			<input type="button"
				value="<bean:message key="all.back"/>"
			onclick="window.location.href='listExpense${x_postfix}.do'"></td>
	</tr>
</table>
