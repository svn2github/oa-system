<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<script type="text/javascript">
<!--
	function validateForm(form) {
		if (!validatePurchaseOrderItemConfirmForm(form)) {
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
<html:javascript formName="purchaseOrderItemConfirmForm" staticJavascript="false"/>
<html:form action="/updatePurchaseOrderItem_confirm" onsubmit="return validateForm(this);">
<html:hidden property="id"/>
<html:hidden property="purchaseOrder_id"/>
<html:errors/>
<table width="90%" border="0" cellpadding="4" cellspacing="0">
	<tr id="tr_item_id">
		<td class="bluetext"><bean:message key="purchaseOrderItem.item.id" /></td>
		<td>${x_poi.item.sepc}</td>
	</tr>
	<tr>
		<td class="bluetext"><bean:message key="purchaseOrderItem.itemSpec" /></td>
		<td>${x_poi.itemSpec}</td>
	</tr>
	<tr>
		<td class="bluetext"><bean:message key="purchaseOrderItem.unitPrice" /></td>
		<td>
			${x_poi.unitPrice}
		</td>
	</tr>
	<tr>
		<td class="bluetext"><bean:message key="purchaseOrderItem.quantity" /></td>
		<td>
			${x_poi.quantity}
		</td>
	</tr>
	<tr>
		<td class="bluetext"><bean:message key="purchaseOrderItem.amount" /></td>
		<td>${x_poi.amount}</td>
	</tr>
	<tr>
		<td class="bluetext"><bean:message key="purchaseOrderItem.baseCurrencyAmount" /></td>
		<td>${x_poi.baseCurrencyAmount}</td>
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
		<td class="bluetext"><bean:message key="purchaseOrderItem.description" /></td>
		<td>			
			<html:text property="description" size="18"/>
		</td>
	</tr>	
	<tr>
		<td class="bluetext"><bean:message key="purchaseOrderItem.dueDate" /></td>
		<td>
			<bean:write name="x_poi" property="dueDate" format="yyyy/MM/dd"/>
		</td>
	</tr>
	
</table>
<%request.setAttribute("x_edit",Boolean.TRUE);%>
<script language="javascript">
	var rechargeFormName = "purchaseOrderItemConfirmForm";
</script>
<jsp:include page="../recharge/edit.jsp"/>
<%request.setAttribute("x_edit",Boolean.FALSE);%>
<jsp:include page="../purchaseOrder/itemAttachmentList.jsp"/>
<table width=90% border=0 cellpadding=4 cellspacing=0>
	<tr>
		<td align="right">
			<html:submit><bean:message key="all.save" /></html:submit>
		</td>
	</tr>
</table>
</html:form>

<script>
	totalAmountChanged(${x_poi.amount});
</script>
