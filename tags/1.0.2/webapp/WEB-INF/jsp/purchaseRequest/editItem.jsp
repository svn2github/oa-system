<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ page import="com.aof.model.metadata.YesNo"%>
<script type="text/javascript">
<!--
	function supplier_onChange() {
		var trSupplierName = document.getElementById('tr_supplierName');
		var trSupplierItemId = document.getElementById('tr_supplierItem_id');
		if (document.purchaseRequestItemForm.supplier_id.value == '') {
			trSupplierName.style.display = "";
			trSupplierItemId.style.display = "none";
		} else {
			trSupplierName.style.display = "none";
			trSupplierItemId.style.display = "";
		}
		reCalculate();
	}

	function supplierItem_onChange() {
		var form = document.purchaseRequestItemForm;
		if (form.supplierItem_id.value != '') {
			var supplierItemOption = form.supplierItem_id.options[form.supplierItem_id.selectedIndex];
			form.supplierItemSepc.value = supplierItemOption.text;
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
		var form = document.purchaseRequestItemForm;
		var exchangeRateOption = form.exchangeRate_id.options[form.exchangeRate_id.selectedIndex];
		form.exchangeRateValue.value = exchangeRateOption.exchangeRate;
		reCalculate();
	}
	
	function projectCode_onChange() {
		var form = document.purchaseRequestItemForm;
		var projectCodeOption = form.projectCode_id.options[form.projectCode_id.selectedIndex];
		form.projectCode_code.value = projectCodeOption.lable;
	}
	
	var amount;
	<c:set var="YESNO_YES" value="<%=YesNo.YES%>"/>
	function calculateTheAmount() {
		var form = document.purchaseRequestItemForm;
		var unitPrice = parseFloat(form.unitPrice.value);
		if (isNaN(unitPrice)) unitPrice = 0;
		unitPrice = Math.round(unitPrice * 100) / 100;
		form.unitPrice.oldValue = unitPrice;
		
		var quantity = parseInt(form.quantity.value);
		if (isNaN(quantity)) quantity = 0;
		form.quantity.oldValue = quantity;
		amount = Math.round(unitPrice * quantity*100)/100;
		
		<c:if test="${x_pr.isDelegate.enumCode==YESNO_YES.enumCode}">
			totalAmountChanged(amount);
		</c:if>
		return amount;
	}

	function calculateBaseCurrencyAmount() {
		var exchangeRate = parseFloat(document.purchaseRequestItemForm.exchangeRateValue.value);
		if (isNaN(exchangeRate)) exchangeRate = 0;
		
		var retVal=Math.round(amount*exchangeRate*100)/100;
		
		return retVal;
	}
	
	function reCalculate()
	{
		document.getElementById('span_amount').innerText= calculateTheAmount()+"";
	    document.getElementById('span_baseCurrencyAmount').innerText=calculateBaseCurrencyAmount()+"";
	}
	
	function validateForm(form) {
		if (!validatePurchaseRequestItemForm(form)) {
			return false;
		}
		<c:if test="${x_pr.isDelegate.enumCode==YESNO_YES.enumCode}">
		<c:if test="${x_canRecharge}">
		//check recharge list
		if (!checkRecharge()) {
			return false;
		}
		</c:if>
		</c:if>
		
	}
//-->
</script>
<html:javascript formName="purchaseRequestItemForm" staticJavascript="false" />

<c:if test="${!x_add}">
	<c:set var="x_action" value="update"/>
</c:if>

<c:if test="${x_add}">
	<c:set var="x_action" value="insert"/>
</c:if>


<html:form action="/${x_action}PurchaseRequestItem${x_postfix}" onsubmit="return validateForm(this);">
<html:hidden property="id"/>
<html:hidden property="purchaseRequest_id"/>
<html:errors/>
<table width="90%" border="0" cellpadding="4" cellspacing="0">
	<tr>
		<td class="bluetext"><bean:message key="purchaseRequestItem.supplier.id" /></td>
		<td><html:select property="supplier_id"/></td>
	</tr>
	<tr id="tr_supplierName" style="display:none">
		<td class="bluetext"><bean:message key="purchaseRequestItem.supplierName" /></td>
		<td><html:text property="supplierName"/></td>
	</tr>
	<tr id="tr_supplierItem_id">
		<td class="bluetext"><bean:message key="purchaseRequestItem.supplierItem.id" /></td>
		<td><html:select property="supplierItem_id"/></td>
	</tr>
	<tr>
		<td class="bluetext"><bean:message key="purchaseRequestItem.supplierItemSepc" /></td>
		<td><html:text property="supplierItemSepc"/><span class="required">*</span></td>
	</tr>
	<tr>
		<td class="bluetext"><bean:message key="purchaseRequestItem.unitPrice" /></td>
		<td>
			<html:text property="unitPrice" onkeyup="reCalculate()" onblur="reCalculate();this.value = this.oldValue;"/>
			<span class="required">*</span>
		</td>
	</tr>
	<tr>
		<td class="bluetext"><bean:message key="purchaseRequestItem.exchangeRate.id" /></td>
		<td>
			<html:select property="exchangeRate_id"/>
			<html:hidden property="exchangeRateValue"/>
		</td>
	</tr>
	<tr>
		<td class="bluetext"><bean:message key="purchaseRequestItem.quantity" /></td>
		<td>
			<html:text property="quantity" onkeyup="reCalculate()" onblur="reCalculate();this.value = this.oldValue;"/>
			<span class="required">*</span>
		</td>
	</tr>
	<tr>
		<td class="bluetext"><bean:message key="purchaseRequestItem.amount" /></td>
		<td><span id="span_amount"></span></td>
	</tr>
	<tr>
		<td class="bluetext"><bean:message key="purchaseRequestItem.baseCurrencyAmount" /></td>
		<td><span id="span_baseCurrencyAmount"></span></td>
	</tr>
	<tr>
		<td class="bluetext"><bean:message key="purchaseRequestItem.dueDate" /></td>
		<td>
			<html:text property="dueDate" size="6" />
			<a onclick="event.cancelBubble=true;"
			href="javascript:showCalendar('dimg3',false,'dueDate',null,null,'purchaseRequestItemForm')"><img
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

<c:if test="${x_pr.isDelegate.enumCode==YESNO_YES.enumCode}">
<script language="javascript">
	var rechargeFormName = "purchaseRequestItemForm";
</script>
<jsp:include page="../recharge/edit.jsp"/>
</c:if>

<jsp:include page="itemAttachmentList.jsp"/>


<table width="90%" border="0" cellpadding="4" cellspacing="0">
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
		<supplierItem id="" name="<bean:message key="purchaseRequestItem.supplierItem.other"/>"/>
		-->
	</supplier>
	</c:forEach>
	<!--
	<supplier id="" name="<bean:message key="purchaseRequestItem.supplier.other"/>">
		<supplierItem id="" name="<bean:message key="purchaseRequestItem.supplierItem.other"/>"/>
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
		var form = document.purchaseRequestItemForm;
		
		//这事件要放在initCascadeSelect前做，以便initCascadeSelect时触发
		form.supplier_id.afterChange = supplier_onChange;
		form.supplier_id.oldValue = '<bean:write name="purchaseRequestItemForm" property="supplier_id"/>';
		
		<c:if test="${x_add}">
			//新增item时这个事件要放在initCascadeSelect前做，以便initCascadeSelect时触发
			form.supplierItem_id.afterChange = supplierItem_onChange;
		</c:if>
		form.supplierItem_id.oldValue = '<bean:write name="purchaseRequestItemForm" property="supplierItem_id"/>';

	    mapping = new Map();
		mapping.put("supplier_id", "supplier");
		mapping.put("supplierItem_id", "supplierItem");
		
	    initCascadeSelect("config", "data", "purchaseRequestItemForm", mapping, true);
	    
		<c:if test="${x_add}">
		//新增item时这个事件要放在initCascadeSelect前做，以便initCascadeSelect时触发
		form.exchangeRate_id.afterChange = exchangeRate_onChange;
		</c:if>
		form.exchangeRate_id.oldValue = '<bean:write name="purchaseRequestItemForm" property="exchangeRate_id"/>';

	    mapping = new Map();
	    mapping.put("exchangeRate_id", "exchangeRate");

	    initCascadeSelect("configExchangeRate", "dataExchangeRate", "purchaseRequestItemForm", mapping, true);
	    
	    

		<c:if test="${!x_add}">
		//修改item时这两个事件要放在initCascadeSelect后做，防止initCascadeSelect时触发
		form.supplierItem_id.afterChange = supplierItem_onChange;
		form.exchangeRate_id.afterChange = exchangeRate_onChange;
		</c:if>
		
		reCalculate();
//-->
</script>	    