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
		var title="purchaseRequestItem.edit.title";
		var url="viewPurchaseRequestItem${x_postfix}.do?id="+id ;		
		dialogAction(url,title,500,400);
	}
	
	
<c:if test="${x_edit}">
	function addItem() {
		var url="newPurchaseRequestItem${x_postfix}.do?purchaseRequest_id=${x_pr.id}";
		
		var title="purchaseRequest.item.new.title";
		var v=dialogAction(url,title,500,600);
		
		if (v) {
			var table = getItemTable();
			appendRow(table, v);
		    applyRowStyle(table);
		    
			modifyTotalBaseCurrencyAmount(getLastItemRow(),'add');				    
		};
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
		
		var title="purchaseRequestItem.edit.title";
		var url="editPurchaseRequestItem${x_postfix}.do?purchaseRequest_id=${x_pr.id}&id="+id ;
		
		var v=dialogAction(url,title,500,600);
		
		if (v) {
			modifyTotalBaseCurrencyAmount(getItemRowById(id),'delete');
			updateRow(getItemRowById(id), v);
			modifyTotalBaseCurrencyAmount(getItemRowById(id),'add');		
		};
	}
	
	function modifyTotalBaseCurrencyAmount(row,addOrDelete)
	{
		var baseCurrencyAmount = parseFloat(row.all('span_item_baseCurrencyAmount').innerText);
		if (!isNaN(baseCurrencyAmount)) {
			if(addOrDelete=='add')
			  	totalBaseCurrencyAmount += baseCurrencyAmount;
			else if(addOrDelete=='delete') 	
				totalBaseCurrencyAmount -= baseCurrencyAmount;
			else alert("addOrDelete error");
		}
	}

	function deleteItem(id) {
		var deleteUrl="deletePurchaseRequestItem${x_postfix}.do?purchaseRequest_id=${x_pr.id}&id=" + id;
		var message="purchaseRequestItem.delete.confirm";
		var title="purchaseRequestItem.delete.title";
		var v=confirmDialog(deleteUrl,title,message,250,150);
		
		if (v) {
			modifyTotalBaseCurrencyAmount(getItemRowById(id),'delete');
			deleteRow(getItemRowById(id));
			applyRowStyle(getItemTable());
		};
	}
	

</c:if>

//-->
</script>


<h3 style="color:blue"><bean:message key="purchaseRequest.item"/></h3>
<c:if test="${x_edit}"><input type="button" value="<bean:message key="all.new"/>" onclick="addItem();"/></c:if>
<table class="data">
	<thead>
		<tr class="new_bg">
			<th><bean:message key="purchaseRequestItem.supplier.id" /></th>
			<th><bean:message key="purchaseRequestItem.supplierItem.id" /></th>
			<th><bean:message key="purchaseRequestItem.supplierItemSepc" /></th>
			<th><bean:message key="purchaseRequestItem.price" /></th>
			<th><bean:message key="purchaseRequestItem.exchangeRate.id" /></th>
			<th><bean:message key="purchaseRequestItem.quantity" /></th>
			<th><bean:message key="purchaseRequestItem.amount" /></th>
			<th><bean:message key="purchaseRequestItem.baseCurrencyAmount" /></th>
			<th><bean:message key="purchaseRequestItem.dueDate" /></th>
			<th><bean:message key="purchaseRequestItem.projectCode" /></th>				
			<th></th>
		</tr>
	</thead>
	
	
	<tbody id="item_datatable">
		<c:forEach var="X_OBJECT" items="${x_purchaseRequestItemList}">
			<c:if test="${x_edit}">
				<script type="text/javascript">
				    totalBaseCurrencyAmount+=${X_OBJECT.baseCurrencyAmount};
				</script>
			</c:if>
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
