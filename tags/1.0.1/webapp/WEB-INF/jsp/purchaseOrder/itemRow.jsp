<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>

<tr id="r${X_OBJECT.id}">
	<td>${X_OBJECT.item.sepc}</td>
	<td>${X_OBJECT.itemSpec}</td>
	<td align="right">${X_OBJECT.unitPrice}</td>
	<td align="right"><span id="span_item_quantity">${X_OBJECT.quantity}</span></td>
	<td align="right"><span id="span_item_amount">${X_OBJECT.amount}</span></td>
	<td align="right"><span id="span_item_baseCurrencyAmount">${X_OBJECT.baseCurrencyAmount}</span></td>
	<td>${X_OBJECT.purchaseType.description}</td>
	<td align="left">
		<fmt:formatDate value="${X_OBJECT.dueDate}" pattern="yyyy/MM/dd"/>
	</td>
	<td>
		<c:if test="${X_OBJECT.purchaseRequestItem!=null}">
			<a href='javascript:viewPR("${X_OBJECT.purchaseRequestItem.purchaseRequest.id}")'>
				${X_OBJECT.purchaseRequestItem.purchaseRequest.id}
			</a>
		</c:if>
	</td>
	<td align="right"><span id="span_item_projectCode">${X_OBJECT.projectCode.code}</span></td>	
	<c:if test="${x_edit}">
	<td>
		<a href="javascript:editItem(${X_OBJECT.id})"><bean:message key="all.modify"/></a>
		&nbsp;<a href="javascript:cancelItem(${X_OBJECT.id})"><bean:message key="purchaseOrderItem.purchase.cancel"/></a>
	</td>
	</c:if>
	<c:if test="${!x_edit}">
	<td>
		<a href='javascript:viewItem("${X_OBJECT.id}")'><bean:message key="all.detail"/></a>
	</td>
	</c:if>
	
</tr>
