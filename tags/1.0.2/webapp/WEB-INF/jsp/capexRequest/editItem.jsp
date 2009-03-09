<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<script type="text/javascript">
<!--
	function combo_onChange() {
		with (document.capexRequestItemForm) {
			action = 'editCapexRequestItem.do';
			submit();
		}
	}
	
	function supplier_onChange() {
		var trSupplierName = document.getElementById('tr_supplierName');
		var trSupplierItemId = document.getElementById('tr_supplierItem_id');
		if (document.capexRequestItemForm.supplier_id.value == '') {
			trSupplierName.style.display = "";
			trSupplierItemId.style.display = "none";
		} else {
			trSupplierName.style.display = "none";
			trSupplierItemId.style.display = "";
		}
	}

	function supplierItem_onChange() {
		var form = document.capexRequestItemForm;
		if (form.supplierItem_id.value != '') {
			var supplierItemOption = form.supplierItem_id.options[form.supplierItem_id.selectedIndex];
			form.supplierItemSepc.value = supplierItemOption.text;
			form.price.value = supplierItemOption.price;
			var currency = supplierItemOption.currency;
			var exchangeRate_id = form.exchangeRate_id;
			var options = exchangeRate_id.options;
			for (var i = 0; i < options.length; i++) {
				if (options[i].currency == currency && !options[i].selected) {
					exchangeRate_id.selectedIndex = i;
					exchangeRate_id.fireEvent("onchange");
				}
			}
		}
	}
	
	function exchangeRate_onChange() {
		var form = document.capexRequestItemForm;
		var exchangeRateOption = form.exchangeRate_id.options[form.exchangeRate_id.selectedIndex];
		form.exchangeRateValue.value = exchangeRateOption.exchangeRate;
	}
	
	var amount;
	function calculateAmount() {
		var form = document.capexRequestItemForm;
		var price = parseFloat(form.price.value);
		if (isNaN(price)) price = 0;
		price = Math.round(price * 100) / 100;
		form.price.oldValue = price;
		
		var quantity = parseInt(form.quantity.value);
		if (isNaN(quantity)) quantity = 0;
		form.quantity.oldValue = quantity;
		amount = price * quantity;
		return amount;
	}

	function calculateBaseCurrencyAmount() {
		var exchangeRate = parseFloat(document.capexRequestItemForm.exchangeRateValue.value);
		if (isNaN(exchangeRate)) exchangeRate = 0;
		return amount * exchangeRate;
	}
//-->
</script>
<html:javascript formName="capexRequestItemForm" staticJavascript="false" />
<html:form action="/updateCapexRequestItem" onsubmit="return validateCapexRequestItemForm(this);">
<html:hidden property="id"/>
<html:hidden property="capexRequest_id"/>
<html:errors/>
<table width="90%" border="0" cellpadding="4" cellspacing="0">
	<tr>
		<td class="bluetext"><bean:message key="capexRequestItem.capexRequest.capex.purchaseCategory.id" /></td>
		<td>
			<c:choose>
				<c:when test="${x_purchaseCategory!=null}">${x_purchaseCategory.description}</c:when>
				<c:otherwise><html:select property="capexRequest_capex_purchaseCategory_id" onchange="combo_onChange()"><html:options collection="x_purchaseCategoryList" property="id" labelProperty="description"/></html:select></c:otherwise>
			</c:choose>
		</td>
	</tr>
	<tr>
		<td class="bluetext"><bean:message key="capexRequestItem.purchaseSubCategory.id" /></td>
		<td>
			<c:choose>
				<c:when test="${x_purchaseSubCategory!=null}">${x_purchaseSubCategory.description}<html:hidden property="purchaseSubCategory_id"/></c:when>
				<c:otherwise><html:select property="purchaseSubCategory_id" onchange="combo_onChange()"><html:options collection="x_purchaseSubCategoryList" property="id" labelProperty="description"/></html:select></c:otherwise>
			</c:choose>
		</td>
	</tr>
	<tr>
		<td class="bluetext"><bean:message key="capexRequestItem.supplier.id" /></td>
		<td><html:select property="supplier_id"/></td>
	</tr>
	<tr id="tr_supplierName" style="display:none">
		<td class="bluetext"><bean:message key="capexRequestItem.supplierName" /></td>
		<td><html:text property="supplierName"/></td>
	</tr>
	<tr id="tr_supplierItem_id">
		<td class="bluetext"><bean:message key="capexRequestItem.supplierItem.id" /></td>
		<td><html:select property="supplierItem_id"/></td>
	</tr>
	<tr>
		<td class="bluetext"><bean:message key="capexRequestItem.supplierItemSepc" /></td>
		<td><html:text property="supplierItemSepc"/><span class="required">*</span></td>
	</tr>
	<tr>
		<td class="bluetext"><bean:message key="capexRequestItem.price" /></td>
		<td><html:text property="price" onblur="this.value = this.oldValue;"/><span class="required">*</span></td>
	</tr>
	<tr>
		<td class="bluetext"><bean:message key="capexRequestItem.currency.code" /></td>
		<td>
			<html:select property="exchangeRate_id"/><span class="required">*</span>
			<html:hidden property="exchangeRateValue"/>
		</td>
	</tr>
	<tr>
		<td class="bluetext"><bean:message key="capexRequestItem.quantity" /></td>
		<td><html:text property="quantity" onblur="this.value = this.oldValue;"/><span class="required">*</span></td>
	</tr>
	<tr>
		<td class="bluetext"><bean:message key="capexRequestItem.amount" /></td>
		<td><span id="span_amount"></span></td>
	</tr>
	<tr>
		<td class="bluetext"><bean:message key="capexRequestItem.baseCurrencyAmount" /></td>
		<td><span id="span_baseCurrencyAmount"></span></td>
	</tr>
</table>
<table width=90% border=0 cellpadding=4 cellspacing=0>
	<tr>
		<td align="right">
			<html:submit><bean:message key="all.save" /></html:submit>
		</td>
	</tr>
</table>
</html:form>


<xml id="data">
<data>
	<c:forEach var="x_supplier" items="${x_supplierList}">
	<supplier id="${x_supplier.id}" name="<bean:write name="x_supplier" property="name"/>">
		<c:forEach var="x_supplierItem" items="${x_supplier.items}">
		<supplierItem id="${x_supplierItem.id}" name="<bean:write name="x_supplierItem" property="sepc"/>" currency="${x_supplierItem.currency.code}" price="${x_supplierItem.unitPrice}"/>
		</c:forEach>
		<supplierItem id="" name="<bean:message key="capexRequestItem.supplierItem.other"/>"/>
	</supplier>
	</c:forEach>
	<supplier id="" name="<bean:message key="capexRequestItem.supplier.other"/>">
		<supplierItem id="" name="<bean:message key="capexRequestItem.supplierItem.other"/>"/>
	</supplier>
</data>
</xml>

<xml id="config">
<config>
	<supplier>
		<supplierItem/>
	</supplier>
</config>
</xml>

<xml id="dataExchangeRate">
<data>
<c:forEach var="x_exchangeRate" items="${x_exchangeRateList}">
	<exchangeRate id="${x_exchangeRate.id}" name="${x_exchangeRate.currency.name}" currency="${x_exchangeRate.currency.code}" exchangeRate="${x_exchangeRate.exchangeRate}"/>
</c:forEach>
</data>
</xml>

<xml id="configExchangeRate">
<config>
	<exchangeRate/>
</config>
</xml>

<script type="text/javascript">
<!--
		var form = document.capexRequestItemForm;
		
		//这事件要放在initCascadeSelect前做，以便initCascadeSelect时触发
		form.supplier_id.afterChange = supplier_onChange;
		form.supplier_id.oldValue = '<bean:write name="capexRequestItemForm" property="supplier_id"/>';
		<c:if test="${x_add}">
		//新增item时这个事件要放在initCascadeSelect前做，以便initCascadeSelect时触发
		form.supplierItem_id.afterChange = supplierItem_onChange;
		</c:if>
		form.supplierItem_id.oldValue = '<bean:write name="capexRequestItemForm" property="supplierItem_id"/>';

	    mapping = new Map();
		mapping.put("supplier_id", "supplier");
		mapping.put("supplierItem_id", "supplierItem");
		
	    initCascadeSelect("config", "data", "capexRequestItemForm", mapping, true);
	    
		<c:if test="${x_add}">
		//新增item时这个事件要放在initCascadeSelect前做，以便initCascadeSelect时触发
		form.exchangeRate_id.afterChange = exchangeRate_onChange;
		</c:if>
		form.exchangeRate_id.oldValue = '<bean:write name="capexRequestItemForm" property="exchangeRate_id"/>';

	    mapping = new Map();
	    mapping.put("exchangeRate_id", "exchangeRate");

	    initCascadeSelect("configExchangeRate", "dataExchangeRate", "capexRequestItemForm", mapping, true);
	    
	    document.getElementById('span_amount').setExpression('innerText', 'calculateAmount()');
	    document.getElementById('span_baseCurrencyAmount').setExpression('innerText', 'calculateBaseCurrencyAmount()');

		<c:if test="${!x_add}">
		//修改item时这两个事件要放在initCascadeSelect后做，防止initCascadeSelect时触发
		form.supplierItem_id.afterChange = supplierItem_onChange;
		form.exchangeRate_id.afterChange = exchangeRate_onChange;
		</c:if>
//-->
</script>
	    