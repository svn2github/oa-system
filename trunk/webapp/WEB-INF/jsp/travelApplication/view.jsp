<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ page import="net.sourceforge.model.metadata.TravelApplicationStatus"%>
<%@ page import="net.sourceforge.model.metadata.TravelApplicationBookStatus"%>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
<tr>
	<td>
		<jsp:include page="baseView.jsp"/>
	</td>
</tr>
</table>

<table width="90%">
<tr>
	<td align="right">
		<c:choose>
			<c:when test="${x_postfix=='_report'}">
				<input type="button" value="<bean:message key="all.back"/>"	onclick="window.location.href='reportTravelApplication.do'">
			</c:when>
			<c:otherwise>
				<c:set var="x_bookStatus" value="<%=TravelApplicationBookStatus.BOOKED%>"/>
				<c:set var="x_receivedStatus" value="<%=TravelApplicationBookStatus.RECEIVED%>"/>
				<script>
					function pdf()
					{
						var url="exportPdfTravelApplication${x_postfix}.do?id=${x_travelApplication.id}";
						window.location.href=url;
					}
				</script>
				<c:if test="${x_travelApplication.bookStatus.enumCode==x_bookStatus.enumCode || x_travelApplication.bookStatus.enumCode==x_receivedStatus.enumCode}">
					<input type="button" value="<bean:message key="all.pdf"/>" onclick="pdf()" />					
				</c:if>
			
				<c:set var="x_pendingStatus" value="<%=TravelApplicationStatus.PENDING%>"/>
				<c:if test="${x_travelApplication.status.enumCode==x_pendingStatus.enumCode}">
				<script>
					function withdraw()
					{	
						if(confirm("<bean:message key="travelApplication.withdraw.confirm"/>"))
						{
							var url="withdrawTravelApplication${x_postfix}.do?id=${x_travelApplication.id}";
							window.location.href=url;
						}
					}
				</script>
				<input type="button" value="<bean:message key="all.withdraw"/>" onclick="withdraw()" />
				</c:if>
				<input type="button" value="<bean:message key="all.back"/>"	onclick="window.location.href='listTravelApplication${x_postfix}.do'">
			</c:otherwise>
		</c:choose>
	</td>
</tr>
</table>