<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<script type="text/javascript">
<!--
	function viewSupplier(id) {
		var url="viewSupplierBaseInfo.do?id=" + id;
		var title="supplier.view.title";
		dialogAction(url,title,550,350);
	}
	
	function viewItem(id) {
		var title="purchaseOrderItem.view.title";
		var url="viewPurchaseOrderItem.do?id="+id ;
		dialogAction(url,title,500,400);
	}
	
	function cancelItemQuantity(id) {
		var table = document.all('item_datatable');
		var row = table.all('r' + id);
		
		var title="purchaseOrder.cancel.title";
		var url="cancelPurchaseOrderItemQuantity.do?id=" + id ;
		var v = dialogAction(url,title,300,340);
		if (v) {
			updateRow(row, v);
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
			<th><bean:message key="purchaseOrderItem.receivedQuantity" /></th>
			<th><bean:message key="purchaseOrderItem.returnedQuantity" /></th>
			<th><bean:message key="purchaseOrderItem.cancelledQuantity" /></th>
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
