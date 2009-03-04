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
	<td align="right">${X_OBJECT.receivedQuantity}</td>
	<td align="right">${X_OBJECT.returnedQuantity}</td>
	<td align="right">${X_OBJECT.cancelledQuantity}</td>
	<td>
		<a href='javascript:viewItem("${X_OBJECT.id}")'><bean:message key="all.detail"/></a>
		<c:set var="x_maxCancelQuantity" value="${X_OBJECT.quantity - (X_OBJECT.receivedQuantity + X_OBJECT.returnedQuantity + X_OBJECT.cancelledQuantity)}"/>
		<c:if test="${x_maxCancelQuantity > 0}">
			<a href='javascript:cancelItemQuantity("${X_OBJECT.id}")'><bean:message key="purchaseOrder.cancel.cancelQuantity"/></a>
		</c:if>
	</td>
	
</tr>
