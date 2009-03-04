<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<script type="text/javascript">
<!--
	function getItemRowById(id)
	{
		 var table=getItemTable();
		 return table.all('r' + id);		
	}
	function viewPR(id) {
		var title="purchaseRequest.view.title";
		var url="viewPurchaseRequest_dialog.do?id="+id ;
		dialogAction(url,title,700,550);
	}
	function getItemTable()
	{
		return document.all('item_datatable');
	}
	function editItem(id) {
		var title="purchaseOrderItem.edit.title";
		var url="editPurchaseOrderItem_confirm.do?purchaseOrder_id=${x_po.id}&id="+id ;
		var v=dialogAction(url,title,500,600);
		if (v) {
			updateRow(getItemRowById(id), v);
		};
	}
//-->
</script>


<h3 style="color:blue"><bean:message key="purchaseOrder.item"/></h3>

<table class="data">
	<thead>
		<tr class="new_bg">
			<th><bean:message key="purchaseOrderItem.item.id" /></th>
			<th><bean:message key="purchaseOrderItem.itemSpec" /></th>
			<th><bean:message key="purchaseOrderItem.price" /></th>
			<th><bean:message key="purchaseOrderItem.quantity" /></th>
			<th><bean:message key="purchaseOrderItem.amount" /></th>
			<th><bean:message key="purchaseOrderItem.baseCurrencyAmount" /></th>
			<th><bean:message key="purchaseOrderItem.purchaseType.code" /></th>
			<th><bean:message key="purchaseOrderItem.dueDate" /></th>			
			<th><bean:message key="purchaseOrderItem.referencedNo" /></th>	
			<c:if test="${!x_po.fromAirTicket}">					
			</c:if>
			<th></th>
			
		</tr>
	</thead>
	<tbody id="item_datatable">
		<c:forEach var="X_OBJECT" items="${x_purchaseOrderItemList}">
			<c:set var="X_OBJECT" scope="request" value="${X_OBJECT}"/>
			<jsp:include page="itemRow.jsp" />
		</c:forEach>
	</tbody>
</table>
<script type="text/javascript">
<!--
    applyRowStyle(document.all('item_datatable'));
//-->
</script>
