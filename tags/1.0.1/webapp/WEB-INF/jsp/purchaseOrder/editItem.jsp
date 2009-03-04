<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<script type="text/javascript">
<!--
	function item_onchange() {
		var form = document.purchaseOrderItemForm;
		if (form.item_id.value != '') {
			var itemOption = form.item_id.options[form.item_id.selectedIndex];
			form.itemSpec.value = itemOption.text;
			form.unitPrice.value = itemOption.unitPrice;
		}
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
		
		//070121 - change by nicebean - recharge original currency, not base currency
		totalAmountChanged(amount);

		return amount;
	}

	function calculateBaseCurrencyAmount() {
		var form=document.purchaseOrderItemForm;
		var exchangeRate = parseFloat(form.purchaseOrder_exchangeRateValue.value);
		if (isNaN(exchangeRate)) exchangeRate = 0;
		
		var retVal=Math.round(amount*exchangeRate*100)/100;
		//070121 - change by nicebean - recharge original currency, not base currency
		//totalAmountChanged(retVal);
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
		<c:if test="${x_canRecharge}">
		//check recharge list
		if (!checkRecharge()) {
			return false;
		}
        //add by nicebean 070121 -- amount / item.quatity 
        //remove by nicebean 070215
        /*
		if (document.all('isRecharge').value == "${x_yes.enumCode}") {
			var table = document.all('recharge_datatable');
			for (var i = 0; i < table.rows.length; i++ ) {
				var itemAmount = Math.round(parseFloat(table.rows[i].all['recharge_amount'].value) * 100) / 100;
				if (Math.abs(Math.round(itemAmount * 100 / ${x_poi.quantity}) / 100 * ${x_poi.quantity} - itemAmount) > 0.01) {
					alert('<bean:message key="recharge.amountMustBeFullDividedByQuantity"/>');
					table.rows[i].all('recharge_amount').focus();
					return false;
				}
				
			}
		}
		*/
		</c:if>
		return true;
	}
//-->
</script>
<html:javascript formName="purchaseOrderItemForm" staticJavascript="false" />

<html:form action="/updatePurchaseOrderItem" onsubmit="return validateForm(this);">
<html:hidden property="purchaseOrder_exchangeRateValue"/>
<html:hidden property="id"/>
<html:hidden property="purchaseOrder_id"/>
<html:errors/>
<table width="90%" border="0" cellpadding="4" cellspacing="0">
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
				<html:option value=""></html:option>			
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
	<c:forEach var="x_supplierItem" items="${x_supplierItemList}">
		<supplierItem id="${x_supplierItem.id}" name="<bean:write name="x_supplierItem" property="sepc"/>" 
			currency="${x_supplierItem.currency.code}" unitPrice="${x_supplierItem.unitPrice}"/>
	</c:forEach>
</data>
</xml>

<xml id="config">
<config>
	<supplierItem/>
</config>
</xml>

<xml id="dataExchangeRate">
<data>
</data>
</xml>


<script type="text/javascript">
<!--
		var form = document.purchaseOrderItemForm;
		
		form.item_id.oldValue = '<bean:write name="purchaseOrderItemForm" property="item_id"/>';

	    mapping = new Map();
		mapping.put("item_id", "supplierItem");
		
	    initCascadeSelect("config", "data", "purchaseOrderItemForm", mapping, true);
		
		//在	initCascadeSelect之后做
		//保证新打开修改窗口时候，保留上一次的值
		form.item_id.afterChange = item_onchange;

		reCalculate();
//-->
</script>	    