<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<jsp:include page="baseEdit.jsp"/>
<table width="100%">
	<tr>
		<td align="right">
			<c:if test="${x_report==null}">
				<c:if test="${x_confirm==null}">
					<c:if test="${x_canWithDraw}">
						<script>
							function withdraw()
							{
								if(confirm("<bean:message key="purchaseOrder.withdraw.confirm"/>"))
								{
									var url="withdrawPurchaseOrder.do?id=${x_po.id}";
									window.location.href=url;
								}
							}
						</script>
						<input type="button" value="<bean:message key="purchaseOrder.withdraw"/>"	
							onclick="javascript:withdraw()">
					</c:if>
					<input type="button" value="<bean:message key="all.pdf"/>"	
						onclick="window.location.href='exportPurchaseOrderPDF.do?id=${x_po.id}'">
					<input type="button" value="<bean:message key="all.back"/>"	
						onclick="window.location.href='listPurchaseOrder.do'">
				</c:if>	
				<c:if test="${x_confirm!=null}">
					<input type="button" value="<bean:message key="all.pdf"/>"	
						onclick="window.location.href='exportPurchaseOrderPDF_confirm.do?id=${x_po.id}'">
					<input type="button" value="<bean:message key="all.back"/>"	
						onclick="window.location.href='listPurchaseOrder_confirm.do'">
				</c:if>
			</c:if>
			<c:if test="${x_report!=null}">
				<input type="button" value="<bean:message key="all.back"/>"	
					onclick="window.location.href='reportPurchaseOrder.do'">
			</c:if>
				
		</td>
	</tr>
</table>
