<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<c:if test="${x_canWithDraw}">
<script type="text/javascript">
	function doWithdraw() {
		if (confirm('<bean:message key="capexRequest.withdraw.confirm"/>')) {
			window.location.href='withdrawCapexRequest.do?id=${x_capexRequest.id}';
		}	
	}
</script>
</c:if>
<jsp:include page="baseEdit.jsp"/>
<table width="100%">
	<tr>
		<td align="right">
			<c:if test="${x_capexRequest==x_capexRequest.capex.lastApprovedRequest}">
				<input type="button" value="<bean:message key="capexRequest.createIncreaseRequest"/>"	onclick="window.location.href='createIncreaseCapexRequest.do?id=${x_capexRequest.id}'">
			</c:if>
			<c:if test="${x_canWithDraw}">
				<input type="button" value="<bean:message key="capexRequest.withdraw"/>" onclick="doWithdraw();">
			</c:if>
			<c:if test="${x_capexRequest.status==x_approved}">
				<input type="button" value="<bean:message key="all.pdf"/>" onclick="window.location.href='exportCapexRequest.do?id=${x_capexRequest.id}'">
			</c:if>
			<input type="button" value="<bean:message key="all.back"/>"	onclick="window.location.href='listCapexRequest.do'">
		</td>
	</tr>
</table>
