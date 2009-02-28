<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<script type="text/javascript">
<!--
	function viewSupplier(id) {
		window.showModalDialog(
			'showDialog.do?title=supplier.view.title&viewSupplierBaseInfo.do?id=' + id, 
			null, 'dialogWidth:550px;dialogHeight:350px;status:no;help:no;scroll:no');
	}
	
<c:if test="${x_edit}">
	function addItem() {
		var v = window.showModalDialog(
			'showDialog.do?title=capexRequestItem.new.title&editCapexRequestItem.do?x_add=t&capexRequest_id=${x_capexRequest.id}&id=' + (maxItemId + 1), 
			null, 'dialogWidth:500px;dialogHeight:400px;status:no;help:no;scroll:no');
		if (v) {
			maxItemId++;
			var table = document.all('item_datatable');
			appendRow(table, v);
		    applyRowStyle(table);
		    var row = table.rows[table.rows.length - 1];
		    var baseCurrencyAmount = parseFloat(row.all('span_item_baseCurrencyAmount').innerText);
		    if (!isNaN(baseCurrencyAmount)) {
		    	totalBaseCurrencyAmount += baseCurrencyAmount;
		    }
		};
	}

	function editItem(id) {
		var table = document.all('item_datatable');
		var row = table.all('r' + id);
		var paramUrl = getParamUrlFromObject(row);
		var v = window.showModalDialog(
			'showDialog.do?title=capexRequestItem.edit.title&editCapexRequestItem.do?capexRequest_id=${x_capexRequest.id}' + paramUrl, 
			null, 'dialogWidth:500px;dialogHeight:400px;status:no;help:no;scroll:no');
		if (v) {
		    var baseCurrencyAmount = parseFloat(row.all('span_item_baseCurrencyAmount').innerText);
		    if (!isNaN(baseCurrencyAmount)) {		    
		    	totalBaseCurrencyAmount -= baseCurrencyAmount;
		    }
			updateRow(row, v);
			row = table.all('r' + id);
			baseCurrencyAmount = parseFloat(row.all('span_item_baseCurrencyAmount').innerText);
		    if (!isNaN(baseCurrencyAmount)) {
		    	totalBaseCurrencyAmount += baseCurrencyAmount;
		    }
		};
	}

	function deleteItem(id) {
		if (confirm('<bean:message key="capexRequestItem.delete.confirm"/>')) {
			var table = document.all('item_datatable');
			var row = table.all('r' + id);
		    var baseCurrencyAmount = parseFloat(row.all('span_item_baseCurrencyAmount').innerText);
		    if (!isNaN(baseCurrencyAmount)) {
		    	totalBaseCurrencyAmount -= baseCurrencyAmount;
		    }
			deleteRow(row);
		    applyRowStyle(table);
		};
	}
	
	function getParamUrlFromObject(obj) {
		var result = '';
		if (obj == null) return result;
		var inputs = obj.getElementsByTagName('INPUT');
		if (inputs == null) return result;
		for (var i = 0; i < inputs.length; i++) {
			result += '&' + inputs[i].name + '=' + encodeURI(inputs[i].value);
		}
		//alert(result);
		return result;
	}
	
	var maxItemId = 0;
</c:if>

//-->
</script>
<h3 style="color:blue"><bean:message key="capexRequest.item"/></h3>
<c:if test="${x_edit}"><input type="button" value="<bean:message key="all.new"/>" onclick="addItem();"/></c:if>
<table class="data">
	<thead>
		<tr bgcolor="#9999ff">
			<th><bean:message key="capexRequestItem.supplier.id" /></th>
			<th><bean:message key="capexRequestItem.supplierItem.id" /></th>
			<th><bean:message key="capexRequestItem.supplierItemSepc" /></th>
			<th><bean:message key="capexRequestItem.price" /></th>
			<th><bean:message key="capexRequestItem.currency.code" /></th>
			<th><bean:message key="capexRequestItem.quantity" /></th>
			<th><bean:message key="capexRequestItem.amount" /></th>
			<th><bean:message key="capexRequestItem.baseCurrencyAmount" /></th>
			<c:if test="${x_edit}"><th></th></c:if>
		</tr>
	</thead>
	<tbody id="item_datatable">
		<c:forEach var="X_OBJECT" items="${x_capexRequestItemList}">
			<c:if test="${x_edit}">
			<script type="text/javascript">
			<!--
			    maxItemId = Math.max(maxItemId, ${X_OBJECT.id});
			//-->
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
