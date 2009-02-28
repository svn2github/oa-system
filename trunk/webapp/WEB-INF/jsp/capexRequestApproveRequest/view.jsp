<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<table width="90%">
<tr>
<td align="right">
<logic:notEmpty name="X_SHOWAPPROVEBUTTON">
	<input type="button" value="<bean:message key="approveRequest.approve"/>"	onclick="javascript:doApprove(document.forms('approveForm'));">
	<input type="button" value="<bean:message key="approveRequest.reject"/>"	onclick="javascript:doReject(document.forms('approveForm'), 'rejectCapexRequestApproveRequest.do');">
</logic:notEmpty>
	<input type="button" value="<bean:message key="all.back"/>"	onclick="javascript:doBack();">
</td>
</tr>
</table>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
<tr>
	<td>
		<jsp:include page="../capexRequest/baseEdit.jsp"/>
	</td>
</tr>
</table>
<hr/>
<table width="90%">
<tr>
<td align="right">
<logic:notEmpty name="X_SHOWAPPROVEBUTTON">
	<form name="approveForm" action="approveCapexRequestApproveRequest.do" method="post">
	<input type="hidden" name="request_id" value="${X_APPROVEREQUEST.approveRequestId}"/>
	<input type="hidden" name="approver_id" value="${X_APPROVEREQUEST.approver.id}"/>
	<input type="hidden" name="comment"/>
	</form>
	<input type="button" value="<bean:message key="approveRequest.approve"/>"	onclick="javascript:doApprove(document.forms('approveForm'));">
	<input type="button" value="<bean:message key="approveRequest.reject"/>"	onclick="javascript:doReject(document.forms('approveForm'), 'rejectCapexRequestApproveRequest.do');">
</logic:notEmpty>
	<input type="button" value="<bean:message key="all.back"/>"	onclick="javascript:doBack();">
</td>
</tr>
</table>
