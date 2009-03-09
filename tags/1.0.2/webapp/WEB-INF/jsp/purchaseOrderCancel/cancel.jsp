<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<script>
	function validateForm(form) {
		with (form) {
			if (!validatePurchaseOrderItemCancelQuantityForm(form)) {
				return false;
			}
		}
		return true;
	}
</script>
<html:javascript formName="purchaseOrderItemCancelQuantityForm" staticJavascript="false" />
<html:form action="cancelPurchaseOrderItemQuantityResult.do" onsubmit="return validateForm(this);">
	<html:hidden property="id"/>
	<table width=100% border=1 cellpadding=4 cellspacing=0 bgcolor=ffffff>
		<tr>
			<td bgcolor=f0f0f0 width=100% colspan=2 valign=top>
			<table width=100% cellpadding=0 cellspacing=0>
				<tr>
					<td bgcolor=f0f0f0 width=80% valign=top>
					<h3 class="formtitle"><bean:message
						key="purchaseOrder.cancel.view.title" /></h3>
					</td>
					<td valign=top align="right"></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
	<span id="warningMsg"><html:errors/></span><br>
	<table width=100% border=0 cellpadding=4 cellspacing=0>
		<tr>
			<td class="bluetext" width="30%"><bean:message key="purchaseOrderItem.quantity"/>:</td>
			<td width="70%">${x_poi.quantity}</td>
		</tr>
		<tr>
			<td class="bluetext" width="30%"><bean:message key="purchaseOrderItem.receivedQuantity"/>:</td>
			<td width="70%">${x_poi.receivedQuantity}</td>
		</tr>
		<tr>
			<td class="bluetext" width="30%"><bean:message key="purchaseOrderItem.returnedQuantity"/>:</td>
			<td width="70%">${x_poi.returnedQuantity}</td>
		</tr>
		<tr>
			<td class="bluetext" width="30%"><bean:message key="purchaseOrderItem.cancelledQuantity"/>:</td>
			<td width="70%">${x_poi.cancelledQuantity}</td>
		</tr>
		<tr>
			<td class="bluetext" width="30%"><bean:message key="purchaseOrderItem.cancelQuantity"/>:</td>
			<td width="70%"><html:text property="cancelQuantity"/></td>
		</tr>
	</table>
	<hr align="left" width="100%">
	<table width="100%">
		<tr>
			<td width="90%" align="right">
				<html:submit><bean:message key="all.confirm"/></html:submit>
				<input type="button" value="<bean:message key="all.cancel"/>" onclick="window.parent.close();">
			</td>
		</tr>
	</table>
</html:form>	