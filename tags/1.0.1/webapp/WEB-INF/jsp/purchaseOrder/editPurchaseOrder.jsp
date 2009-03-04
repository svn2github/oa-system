<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@ page import="com.aof.model.metadata.PurchaseOrderStatus"%>
<script>
	function viewSupplier(id) {
		var url="viewSupplierBaseInfo.do?id=" + id;
		var title="supplier.view.title";
		dialogAction(url,title,550,350);
	}
</script>
<!-- 
	显示或编辑purchaseOrder主内容（编辑的时候只能编辑title和description）
	attribute x_edit: 编辑还是显示
	attribute x_po: purchaseOrder对象
-->

<table width=90% cellpadding=1 cellspacing=1>
	<tr>
		<td class="bluetext"><bean:message key="purchaseOrder.id" />:</td>
		<td>${x_po.id}</td>
		<td class="bluetext"><bean:message key="purchaseOrder.erpNo" />:</td>
		<td>
			<c:if test="${x_edit}">
				<html:text property="erpNo" />
			</c:if> 
			<c:if test="${!x_edit}">
				${x_po.erpNo}
			</c:if>
		</td>
	</tr>
	<tr>
		<td class="bluetext"><bean:message key="purchaseOrder.supplier.id" />:</td>
		<td align="left" nowrap>
		<table>
			<tr>
				<td>
				<logic:notPresent name="x_confirmPurchaseOrder">
				<div id="supplier1"><a
					href='javascript:viewSupplier("${x_po.supplier.id}");'>${x_po.supplier.name}</a>
				</div>
				</logic:notPresent>
				<logic:present name="x_confirmPurchaseOrder">
				<div id="supplier_div">				
				<jsp:include page="../purchaseOrderConfirm/supplierContent.jsp"/>
				</div>
				</logic:present>
				</td>
			<tr>
		</table>
		</td>
		<td class="bluetext"><bean:message key="purchaseOrder.title" />:</td>
		<td align="left">
			<c:if test="${x_edit}">
				<html:text property="title" />
			</c:if> 
			<c:if test="${!x_edit}">
				${x_po.title}
			</c:if>
		</td>
	</tr>
	<tr>
		<td class="bluetext" width=29%><bean:message
			key="purchaseOrder.description" />:</td>
		<td colspan=3>
			<c:if test="${x_edit}">
				<html:textarea property="description" rows="2" cols="40" />
			</c:if> 
			<c:if test="${!x_edit}">
				${x_po.description}
			</c:if>
		</td>
	</tr>
	<tr>
		<td class="bluetext"><bean:message key="purchaseOrder.category" />:</td>
		<td align="left">${x_po.subCategory.purchaseCategory.description}</td>
		<td class='bluetext'><bean:message key="purchaseOrder.status" />:</td>
		<td><font color="${x_po.status.color}"><bean:write name="x_po"
			property="status.${x_lang}ShortDescription"/></font></td>
	</tr>
	<tr>
		<td class="bluetext"><bean:message key="purchaseOrder.subCategory.id" />:</td>
		<td align="left">${x_po.subCategory.description}</td>
	</tr>
</table>

<hr align="left" width="90%">
<table width="90%">
	<tr>
		<td class="bluetext"><bean:message key="purchaseOrder.amount" />:</td>
		<td><label id="span_purchaseOrder_amount">${x_po.amount}</label>&nbsp;&nbsp;&nbsp;</td>
		<td class="bluetext"><bean:message key="purchaseOrder.currency" />:</td>
		<td align="left"><label>${x_po.exchangeRate.currency.code}</label></td>
		<td class="bluetext"><bean:message key="purchaseOrder.exchangeRate" />:</td>
		<td align="left">${x_po.exchangeRateValue}</td>
	</tr>
	<tr>
		<td class="bluetext"><bean:message key="purchaseOrder.baseAmount" />:</td>
		<td><label id="span_purchaseOrder_baseAmount">${x_po.baseCurrencyAmount}</label>&nbsp;&nbsp;&nbsp;</td>

		<td class="bluetext">
			<bean:message key="purchaseOrder.baseCurrency.code" />:
		</td>
		<td align="left"><label>${x_po.baseCurrency.code}</label></td>
	</tr>
	<tr>
		<td class='bluetext'><bean:message key="purchaseOrder.createUser.id" />:&nbsp;</td>
		<td>${x_po.createUser.name}</td>
		<td class='bluetext'><bean:message key="purchaseOrder.createDate" />:&nbsp;</td>
		<td>
			<fmt:formatDate value="${x_po.createDate}"
				pattern="yyyy/MM/dd" />
		</td>
	</tr>
	
<c:if test="${x_notShowInspector==null}">	
<c:set var="x_confirmed" value="<%=PurchaseOrderStatus.CONFIRMED.getEnumCode()%>"/>
<c:set var="x_received" value="<%=PurchaseOrderStatus.RECEIVED.getEnumCode()%>"/>
<c:if test="${x_po.status.enumCode==x_confirmed or x_po.status.enumCode==x_received}">
	<tr>
		<td class='bluetext'><bean:message key="purchaseOrder.inspector.id" />:&nbsp;</td>
		<td>
			
			${x_po.inspector.name}
			
		</td>
	</tr>
</c:if>	
</c:if>	
</table>


