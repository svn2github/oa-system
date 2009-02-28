<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<tr id="r${X_OBJECT.id}">
	<td>
		<c:choose>
			<c:when test="${X_OBJECT.supplier!=null}">
				<a href="javascript:viewSupplier(${X_OBJECT.supplier.id})"/>${X_OBJECT.supplier.name}</a>
			</c:when>
			<c:otherwise>
				${X_OBJECT.supplierName}
			</c:otherwise>
		</c:choose>
	</td>
	<td>${X_OBJECT.supplierItem.sepc}</td>
	<td>${X_OBJECT.supplierItemSepc}</td>
	<td align="right">${X_OBJECT.unitPrice}</td>
	<td>${X_OBJECT.exchangeRate.currency.name}</td>
	<td align="right">${X_OBJECT.quantity}</td>
	<td align="right">${X_OBJECT.amount}</td>
	<td align="right"><span id="span_item_baseCurrencyAmount">${X_OBJECT.baseCurrencyAmount}</span></td>
	<td align="right">
		<fmt:formatDate value="${X_OBJECT.dueDate}" pattern="yyyy/MM/dd"/>
	</td>
	<td align="right">${X_OBJECT.projectCode.code}</td>
	<c:if test="${x_edit}">
	<td>
		<a href="javascript:editItem(${X_OBJECT.id})"><bean:message key="all.modify"/></a>
		<a href="javascript:deleteItem(${X_OBJECT.id})"><bean:message key="all.delete"/></a>
	</td>
	</c:if>
	<c:if test="${!x_edit}">
	<td>
		<a href="javascript:viewItem(${X_OBJECT.id})"><bean:message key="all.detail"/></a>
	</td>
	</c:if>
	
</tr>
