<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>

<script type="text/javascript">
<!--
	function viewPR(id) {
		var title="purchaseRequest.view.title";
		var url="viewPurchaseRequest_dialog.do?id="+id ;
		dialogAction(url,title,700,550);
	}
	function listReceipt(po_id)
	{
		var url="listPurchaseOrderItemReceipt.do?purchaseOrderItem_id="+po_id;
		window.location.href=url;
	}
	
	function validateForm(form) {
		if (!form.includeReceivedItem.checked) {
			form.includeReceivedItem.checked = true;
			form.includeReceivedItem.value="";
		}
	}
//-->
</script>

<div class="message"><html:messages id="x_message" message="true">${x_message}<br></html:messages></div>

<html:form action="listPurchaseOrderItem_receipt" onsubmit="return validateForm(this);">
	<html:hidden property="order" />
	<html:hidden property="descend" />
	
	<table width="100%" border=0 cellpadding=4 cellspacing=0>
		<tr>
			<td class="bluetext"><bean:message key="purchaseOrderItem.receipt.search.id"/>:</td>
			<td><html:text property="id"/></td>
			<td class="bluetext"><bean:message key="purchaseOrderItem.receipt.search.itemSpec"/>:</td>
			<td><html:text property="itemSpec"/></td>
			<td class="bluetext"><bean:message key="purchaseOrderItem.receipt.search.purchaseRequest.id"/>:</td>
			<td><html:text property="purchaseRequest_id" size="13"/></td>
			<td class="bluetext"><bean:message key="purchaseOrderItem.receipt.search.includeReceiptFinished"/><html:checkbox property="includeReceivedItem"/></td>
			<td><html:submit><bean:message key="all.query"/></html:submit></td>
		</tr>
	</table>
</html:form>
<hr />
<page:form action="listPurchaseOrderItem_receipt.do" method="post">
	<jsp:include page="../pageHead.jsp"/>
	<table class="data">
		<thead>
			<tr bgcolor="#9999ff">
				<th width="140"><bean:message key="purchaseOrder.id" /></th>
				<th><page:order order="id" style="text-decoration:none">
					<bean:message key="purchaseOrderItem.id" />
					<page:desc><img src="images/down.gif" border="0" /></page:desc>
					<page:asc><img src="images/up.gif" border="0" /></page:asc>
				</page:order></th>
				<th><page:order order="itemSpec" style="text-decoration:none">
					<bean:message key="purchaseOrderItem.itemSpec" />
					<page:desc>
						<img src="images/down.gif" border="0" />
					</page:desc>
					<page:asc>
						<img src="images/up.gif" border="0" />
					</page:asc>
				</page:order></th>
				<th width="100"><bean:message key="purchaseOrderItem.quantity" /></th>
				<th width="100"><bean:message key="purchaseOrderItem.receivedQuantity" /></th>
				<th width="100"><bean:message key="purchaseOrderItem.returnedQuantity" /></th>
				<th width="100"><bean:message key="purchaseOrderItem.cancelledQuantity" /></th>
				<th width="140"><page:order order="purchaseRequest_id" style="text-decoration:none">
					<bean:message key="purchaseOrderItem.referencedNo" />
					<page:desc>
						<img src="images/down.gif" border="0" />
					</page:desc>
					<page:asc>
						<img src="images/up.gif" border="0" />
					</page:asc>
				</page:order></th>
			</tr>
		</thead>

		<tbody id="datatable">
			<logic:iterate id="X_OBJECT" name="X_RESULTLIST">
				<bean:define id="X_OBJECT" toScope="request" name="X_OBJECT" />
				<jsp:include page="receiptRow.jsp" />
			</logic:iterate>
		</tbody>
	</table>

</page:form>

<script type="text/javascript">
    applyRowStyle(document.all('datatable'));
</script>

