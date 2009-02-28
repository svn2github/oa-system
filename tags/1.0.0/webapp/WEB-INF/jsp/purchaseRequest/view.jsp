<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<jsp:include page="baseEdit.jsp"/>
<table width="100%">
	<tr>
		<td align="right">
			<c:if test="${x_postfix!=null}">
				<c:if test="${x_canWithDraw}">
					<script>
						function withdraw()
						{
							if(confirm("<bean:message key="purchaseRequest.withdraw.confirm"/>"))
							{
								var url="withdrawPurchaseRequest${x_postfix}.do?id=${x_pr.id}";
								window.location.href=url;
							}
						}
					</script>

					<input type="button" value="<bean:message key="purchaseRequest.withdraw"/>"	
						onclick="javascript:withdraw()">
				</c:if>
				<c:if test="${x_canExportPdf}">
				<input type="button" value="<bean:message key="all.pdf"/>"	
					onclick="window.location.href='exportPurchaseRequestDetailPDF${x_postfix}.do?id=${x_pr.id}'">
				</c:if>	
				<input type="button" value="<bean:message key="all.back"/>"	
					onclick="window.location.href='listPurchaseRequest${x_postfix}.do'">
			</c:if>
			
			<c:if test="${x_postfix==null}">
				<input type="button" value="<bean:message key="all.back"/>"	
					onclick="window.location.href='reportPurchaseRequest.do'">
			</c:if>
		</td>
	</tr>
</table>