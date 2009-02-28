<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<table width="90%">
	<tr>
		<td align="right">
			<input type="button" value="<bean:message key="approveRequest.approve"/>"	onclick="doApprove(document.forms('capexRequestForm'), validateCapexRequestForm);">
			<input type="button" value="<bean:message key="approveRequest.reject"/>"	onclick="doReject(document.forms('capexRequestForm'), 'rejectCapexRequestApproveRequest.do');">
			<input type="button" value="<bean:message key="all.back"/>" onclick="doBack();">
		</td>
	</tr>
</table>
<jsp:include page="../capexRequest/baseEdit.jsp"/>
<table width="90%">
	<tr>
		<td align="right">
			<input type="button" value="<bean:message key="approveRequest.approve"/>"	onclick="doApprove(document.forms('capexRequestForm'), validateCapexRequestForm);">
			<input type="button" value="<bean:message key="approveRequest.reject"/>"	onclick="doReject(document.forms('capexRequestForm'), 'rejectCapexRequestApproveRequest.do');">
			<input type="button" value="<bean:message key="all.back"/>" onclick="doBack();">
		</td>
	</tr>
</table>
<script>
	var form = document.capexRequestForm;
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

	form.action = 'updateAndApproveCapexRequestApproveRequest.do';
</script>

