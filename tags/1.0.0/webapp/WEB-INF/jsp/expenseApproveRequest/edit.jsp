<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<table width="90%">
	<tr>
		<td align="right">
			<input type="button" value="<bean:message key="approveRequest.approve"/>"	onclick="doApprove(document.forms('expenseForm'), validateForm);">
			<input type="button" value="<bean:message key="approveRequest.reject"/>"	onclick="doReject(document.forms('expenseForm'), 'rejectExpenseApproveRequest.do');">
			<input type="button" value="<bean:message key="all.back"/>" onclick="doBack();">
		</td>
	</tr>
</table>
<jsp:include page="../expense/baseEdit.jsp"/>
<table width="90%">
	<tr>
		<td align="right">
			<input type="button" value="<bean:message key="approveRequest.approve"/>"	onclick="doApprove(document.forms('expenseForm'), validateForm);">
			<input type="button" value="<bean:message key="approveRequest.reject"/>"	onclick="doReject(document.forms('expenseForm'), 'rejectExpenseApproveRequest.do');">
			<input type="button" value="<bean:message key="all.back"/>" onclick="doBack();">
		</td>
	</tr>
</table>
<script>
	var form = document.expenseForm;
	var hiddenElement = document.createElement("<input name='request_id'/>");
	hiddenElement.type = "hidden";
	hiddenElement.value = "${X_APPROVEREQUEST.approveRequestId}";
	form.appendChild(hiddenElement);
	
	hiddenElement = document.createElement("<input name='approver_id'/>");
	hiddenElement.type = "hidden";
	hiddenElement.value = "${X_APPROVEREQUEST.approver.id}";
	form.appendChild(hiddenElement);
	
	hiddenElement = document.createElement("<input name='comment'/>");
	hiddenElement.type = "hidden";
	form.appendChild(hiddenElement);

	form.action = 'updateAndApproveExpenseApproveRequest.do';
	
	allowChangeRequestAmount = false;
</script>

