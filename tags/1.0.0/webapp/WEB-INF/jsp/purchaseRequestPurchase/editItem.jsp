<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<script type="text/javascript">
<!--
	function supplier_onChange() {
		var trSupplierName = document.getElementById('tr_supplierName');
		var trItemId = document.getElementById('tr_item_id');
		if (document.purchaseOrderItemForm.supplier_id.value == '') {
			trSupplierName.style.display = "";
			trItemId.style.display = "none";
		} else {
			trSupplierName.style.display = "none";
			trItemId.style.display = "";
		}
		reCalculate();
	}

	function item_onchange() {
		var form = document.purchaseOrderItemForm;
		if (form.item_id.value != '') {
			var supplierItemOption = form.item_id.options[form.item_id.selectedIndex];
			form.itemSpec.value = supplierItemOption.text;
			form.unitPrice.value = supplierItemOption.unitPrice;
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
		reCalculate();
	}
	
	function exchangeRate_onChange() {
		var form = document.purchaseOrderItemForm;
		var exchangeRateOption = form.exchangeRate_id.options[form.exchangeRate_id.selectedIndex];
		form.exchangeRateValue.value = exchangeRateOption.exchangeRate;
		reCalculate();
	}
	
	function projectCode_onChange() {
		var form = document.purchaseOrderItemForm;
		var projectCodeOption = form.projectCode_id.options[form.projectCode_id.selectedIndex];
		form.projectCode_code.value = projectCodeOption.lable;
	}
	
	var amount;
	function calculateTheAmount() {
		var form = document.purchaseOrderItemForm;
		var unitPrice = parseFloat(form.unitPrice.value);
		if (isNaN(unitPrice)) unitPrice = 0;
		unitPrice = Math.round(unitPrice * 100) / 100;
		form.unitPrice.oldValue = unitPrice;
		
		var quantity = parseInt(form.quantity.value);
		if (isNaN(quantity)) quantity = 0;
		form.quantity.oldValue = quantity;
		amount = unitPrice * quantity;
		
		return amount;
	}

	function calculateBaseCurrencyAmount() {
		var exchangeRate = parseFloat(document.purchaseOrderItemForm.exchangeRateValue.value);
		if (isNaN(exchangeRate)) exchangeRate = 0;
		
		var retVal=Math.round(amount*exchangeRate*100)/100;
		totalAmountChanged(retVal);
		return retVal;
	}
	
	function reCalculate()
	{
		document.getElementById('span_amount').innerText= calculateTheAmount()+"";
	    document.getElementById('span_baseCurrencyAmount').innerText=calculateBaseCurrencyAmount()+"";
	}
	
	function validateForm(form) {
		if (!validatePurchaseOrderItemForm(form)) {
			return false;
		}
		<c:if test="${x_split}">
			var form=document.purchaseOrderItemForm;
			form.quantity.value=trim(form.quantity.value);
			if(parseInt(form.quantity.value)>${x_oldPoi.quantity-1})
			{
				alert("<bean:message key="purchaseRequest.purchase.split.quantityMustLessTranOld"/>");
				return false;
			}
		</c:if>
		
		<c:if test="${x_canRecharge}">
		//check recharge list
		if (!checkRecharge()) {
			return false;
		}
		</c:if>
		
	}
//-->
</script>
<html:javascript formName="purchaseOrderItemForm" staticJavascript="false" />

<c:if test="${!x_split}">
	<c:set var="x_action" value="/updatePurchaseOrderItem_purchaseRequest"/>
</c:if>

<c:if test="${x_split}">
	<c:set var="x_action" value="/splitPurchaseOrderItem_result"/>
</c:if>


<html:form action="${x_action}" onsubmit="return validateForm(this);">
<html:hidden property="id"/>
<html:hidden property="purchaseRequestItem_purchaseRequest_id"/>
<html:errors/>
<table width="90%" border="0" cellpadding="4" cellspacing="0">
	<tr>
		<td class="bluetext"><bean:message key="purchaseOrderItem.supplier.id" /></td>
		<td><html:select property="supplier_id"/></td>
	</tr>
	<tr id="tr_supplierName" style="display:none">
		<td class="bluetext"><bean:message key="purchaseOrderItem.supplierName" /></td>
		<td><html:text property="supplierName"/></td>
	</tr>
	<tr id="tr_item_id">
		<td class="bluetext"><bean:message key="purchaseOrderItem.item.id" /></td>
		<td><html:select property="item_id"/></td>
	</tr>
	<tr>
		<td class="bluetext"><bean:message key="purchaseOrderItem.itemSpec" /></td>
		<td><html:text property="itemSpec"/><span class="required">*</span></td>
	</tr>
	<tr>
		<td class="bluetext"><bean:message key="purchaseOrderItem.unitPrice" /></td>
		<td>
			<html:text property="unitPrice" onkeyup="reCalculate()" onblur="reCalculate();this.value = this.oldValue;"/>
			<span class="required">*</span>
		</td>
	</tr>
	<tr>
		<td class="bluetext"><bean:message key="purchaseOrderItem.exchangeRate.id" /></td>
		<td>
			<html:select property="exchangeRate_id"/>
			<input type="hidden" name="exchangeRateValue"/>			
		</td>
	</tr>
	<tr>
		<td class="bluetext"><bean:message key="purchaseOrderItem.quantity" /></td>
		<td>
			<c:if test="${x_split}">
				<html:text property="quantity" onkeyup="reCalculate()" onblur="reCalculate();this.value = this.oldValue;"/>
				<span class="required">*</span>
				(<bean:message key="purchaseRequest.purchase.split.quantity.max"/>:${x_oldPoi.quantity-1})
			</c:if>
			<c:if test="${!x_split}">
				<html:hidden property="quantity" />
				${x_poi.quantity}
			</c:if>
		</td>
	</tr>
	<tr>
		<td class="bluetext"><bean:message key="purchaseOrderItem.amount" /></td>
		<td><span id="span_amount"></span></td>
	</tr>
	<tr>
		<td class="bluetext"><bean:message key="purchaseOrderItem.baseCurrencyAmount" /></td>
		<td><span id="span_baseCurrencyAmount"></span></td>
	</tr>
	<tr>
		<td class="bluetext"><bean:message key="purchaseOrderItem.purchaseType" /></td>
		<td>
			<html:select property="purchaseType_code">
				<html:options collection="x_purchaseTypeList" property="code"
						labelProperty="description" />
			</html:select>
		</td>
	</tr>
	<tr>
		<td class="bluetext"><bean:message key="purchaseOrderItem.dueDate" /></td>
		<td>
			<html:text property="dueDate" size="6" />
			<a onclick="event.cancelBubble=true;"
			href="javascript:showCalendar('dimg3',false,'dueDate',null,null,'purchaseOrderItemForm')"><img
			align="absmiddle" border="0" id="dimg3" src="images/datebtn.gif" /></a>		
		</td>
	</tr>
	<tr>
		<td class="bluetext"><bean:message key="purchaseRequestItem.projectCode" /></td>
		<td>			
			<html:select property="projectCode_id" onchange="javascript:projectCode_onChange();">
				<html:option value=""></html:option>				
					<html:options collection="x_projectCodeList" property="id"
						labelProperty="code" />					
			</html:select>
		</td>
	</tr>	
	<tr id="tr_projectCode" style="display:none">
		<td class="bluetext"><bean:message key="purchaseRequestItem.projectCode" /></td>
		<td><html:text property="projectCode_code"/></td>
	</tr>
</table>
<script language="javascript">
	var rechargeFormName = "purchaseOrderItemForm";
</script>
<jsp:include page="../recharge/edit.jsp"/>

<jsp:include page="../purchaseOrder/itemAttachmentList.jsp"/>


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
		<supplierItem id="${x_supplierItem.id}" name="<bean:write name="x_supplierItem" property="sepc"/>" currency="${x_supplierItem.currency.code}" unitPrice="${x_supplierItem.unitPrice}"/>
		</c:forEach>
		<!--
		<supplierItem id="" name="<bean:message key="purchaseOrderItem.supplierItem.other"/>"/>
		-->
	</supplier>
	</c:forEach>
	<!--
	<supplier id="" name="<bean:message key="purchaseOrderItem.supplier.other"/>">
		<supplierItem id="" name="<bean:message key="purchaseOrderItem.supplierItem.other"/>"/>
	</supplier>
	-->
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
	<exchangeRate id="${x_exchangeRate.id}" name="<bean:write name="x_exchangeRate" property="currency.name"/>" currency="${x_exchangeRate.currency.code}" exchangeRate="${x_exchangeRate.exchangeRate}"/>
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
		var form = document.purchaseOrderItemForm;
		
		//这事件要放在initCascadeSelect前做，以便initCascadeSelect时触发
		form.supplier_id.afterChange = supplier_onChange;
		form.supplier_id.oldValue = '<bean:write name="purchaseOrderItemForm" property="supplier_id"/>';
		
		form.item_id.oldValue = '<bean:write name="purchaseOrderItemForm" property="item_id"/>';

	    mapping = new Map();
		mapping.put("supplier_id", "supplier");
		mapping.put("item_id", "supplierItem");
		
	    initCascadeSelect("config", "data", "purchaseOrderItemForm", mapping, true);
		
		//在initCascadeSelect之前做
		//总是保证exchangeRateValue是最新了
		form.exchangeRate_id.afterChange = exchangeRate_onChange;	    
		form.exchangeRate_id.oldValue = '<bean:write name="purchaseOrderItemForm" property="exchangeRate_id"/>';

	    mapping = new Map();
	    mapping.put("exchangeRate_id", "exchangeRate");

	    initCascadeSelect("configExchangeRate", "dataExchangeRate", "purchaseOrderItemForm", mapping, true);

		//在	initCascadeSelect之后做
		//保证新打开修改窗口时候，保留上一次的值
		form.item_id.afterChange = item_onchange;

		reCalculate();
//-->
</script>	    