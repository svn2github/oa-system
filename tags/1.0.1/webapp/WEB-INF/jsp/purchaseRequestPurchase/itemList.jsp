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
	
	function viewPO(id) {
		var title="purchaseOrder.view.title";
		var url="viewPurchaseOrder_dialog.do?id="+id ;
		dialogAction(url,title,700,550);
	}
	
	function viewItem(id) {
		var title="purchaseOrderItem.view.title";
		var url="viewPurchaseOrderItem_purchaseRequest.do?id="+id ;
		dialogAction(url,title,500,400);
	}
	
	function getLastItemRow()
	{
		 var table=getItemTable();
		 var row = table.rows[table.rows.length - 1];
		 return row;
	}
	
	function getItemRowById(id)
	{
		 var table=getItemTable();
		 return table.all('r' + id);		
	}
	
	function getItemTable()
	{
		return document.all('item_datatable');
	}
	
	
	function editItem(id) {
		var title="purchaseOrderItem.edit.title";
		var url="editPurchaseOrderItem_purchaseRequest.do?purchaseRequestItem_purchaseRequest_id=${x_pr.id}&id="+id ;
		var v=dialogAction(url,title,500,600);
		if (v) {
			updateRow(getItemRowById(id), v);
		};
	}
	
	function splitItem(id) {
		var title="purchaseOrderItem.split.title";
		var url="splitPurchaseOrderItem.do?purchaseRequestItem_purchaseRequest_id=${x_pr.id}&id="+id ;
		
		var v=dialogAction(url,title,500,600);
		
		if (v) {
			var oldRow = getItemRowById(id);
			var oldQuantitySpan=oldRow.all('span_item_quantity');
			var oldAmountSpan=oldRow.all("span_item_amount");
			var oldBaseAmountSpan=oldRow.all("span_item_baseCurrencyAmount");
			
			var oldQuantity = parseInt(oldQuantitySpan.innerText);
			var oldAmount=parseFloat(oldAmountSpan.innerText);
			var oldBaseAmount=parseFloat(oldBaseAmountSpan.innerText);
		    
		    appendRow(getItemTable(),v);
		    
			var newRow=getLastItemRow();
			var newQuantity= parseInt(newRow.all('span_item_quantity').innerText);
			var newAmount=parseFloat(newRow.all('span_item_amount').innerText);
			var newBaseAmount=parseFloat(newRow.all('span_item_baseCurrencyAmount').innerText);
			
			oldQuantitySpan.innerText=""+(oldQuantity-newQuantity);
			oldAmountSpan.innerText=""+Math.round((oldAmount-newAmount)*100)/100;
			oldBaseAmountSpan.innerText=""+Math.round((oldBaseAmount-newBaseAmount)*100)/100;
		    applyRowStyle(getItemTable());
		};
	}
	

//-->
</script>


<h3 style="color:blue"><bean:message key="purchaseOrder.item"/></h3>

<table class="data">
	<thead>
		<tr bgcolor="#9999ff">
			<c:if test="${x_edit}">
			<th></th>
			</c:if>
			<th><bean:message key="purchaseOrderItem.supplier.id" /></th>
			<th><bean:message key="purchaseOrderItem.item.id" /></th>
			<th><bean:message key="purchaseOrderItem.itemSpec" /></th>
			<th><bean:message key="purchaseOrderItem.price" /></th>
			<th><bean:message key="purchaseOrderItem.exchangeRate.id" /></th>
			<th><bean:message key="purchaseOrderItem.quantity" /></th>
			<th><bean:message key="purchaseOrderItem.amount" /></th>
			<th><bean:message key="purchaseOrderItem.baseCurrencyAmount" /></th>
			<th><bean:message key="purchaseOrderItem.purchaseType.code" /></th>
			<th><bean:message key="purchaseOrderItem.dueDate" /></th>	
			<th><bean:message key="purchaseOrderItem.projectCode" /></th>			
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
