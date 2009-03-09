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
	
	function viewPR(id) {
		var title="purchaseRequest.view.title";
		var url="viewPurchaseRequest_dialog.do?id="+id ;
		dialogAction(url,title,700,550);
	}
	
	function viewItem(id) {
		var title="purchaseOrderItem.view.title";
		var url="viewPurchaseOrderItem.do?id="+id ;
		dialogAction(url,title,500,400);
	}
	
	function modifyTotalAmount(row,addOrDelete)
	{
		var amount = parseFloat(row.all('span_item_amount').innerText);
		if (!isNaN(amount)) {
			if(addOrDelete=='add')
			  	totalAmount += amount;
			else if(addOrDelete=='delete') 	
				totalAmount -= amount;
			else alert("addOrDelete error");
		}
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
		var table = document.all('item_datatable');
		var row = table.all('r' + id);
		
		var title="purchaseOrderItem.edit.title";
		var url="editPurchaseOrderItem.do?purchaseOrder_id=${x_po.id}&id="+id ;
		
		var v=dialogAction(url,title,500,600);
		
		if (v) {
			modifyTotalAmount(getItemRowById(id),'delete');
			updateRow(getItemRowById(id), v);
			modifyTotalAmount(getItemRowById(id),'add');		
		};
	}
	
	function cancelItem(id) {
		var table = document.all('item_datatable');
		var row = table.all('r' + id);
		
		var title="purchaseOrderItem.cancel.title";
		var url="cancelPurchaseOrderItem.do?purchaseOrder_id=${x_po.id}&id="+id ;
		var message="purchaseOrderItem.cancel.confirm";
		var v=confirmDialog(url,title,message,250,150);
		if (v) {
		    modifyTotalAmount(getItemRowById(id),'delete');
			deleteRow(getItemRowById(id));
			applyRowStyle(table);
		};
	}
	

//-->
</script>


<h3 style="color:blue"><bean:message key="purchaseOrder.item"/></h3>
<c:if test="${x_edit}">	
	<script type="text/javascript">
		var totalAmount = 0;
		document.getElementById('span_purchaseOrder_amount').
			setExpression('innerText', 'Math.round(totalAmount * 100) / 100');
		
		document.getElementById('span_purchaseOrder_baseAmount').
			setExpression('innerText', 'Math.round(totalAmount*${x_po.exchangeRateValue} * 100) / 100');
	</script>
</c:if>
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
			<th><bean:message key="purchaseOrderItem.projectCode" /></th>							
			<th></th>
		</tr>
	</thead>
	<tbody id="item_datatable">
		<c:forEach var="X_OBJECT" items="${x_purchaseOrderItemList}">
			<c:set var="X_OBJECT" scope="request" value="${X_OBJECT}"/>
			<c:if test="${x_edit}">
				<script type="text/javascript">
				    totalAmount+=${X_OBJECT.amount};
				</script>
			</c:if>
			<jsp:include page="itemRow.jsp" />
		</c:forEach>
	</tbody>
</table>
<script type="text/javascript">
<!--
    applyRowStyle(document.all('item_datatable'));
//-->
</script>
