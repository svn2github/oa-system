<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<tr id="r${X_OBJECT.id}">
	<td>
		<c:choose>
			<c:when test="${X_OBJECT.supplier!=null}"><a href="javascript:viewSupplier(${X_OBJECT.supplier.id})"/>${X_OBJECT.supplier.name}</a></c:when>
			<c:otherwise>${X_OBJECT.supplierName}</c:otherwise>
		</c:choose>
	</td>
	<td>${X_OBJECT.supplierItem.sepc}</td>
	<td>${X_OBJECT.supplierItemSepc}</td>
	<td align="right">${X_OBJECT.price}</td>
	<td>${X_OBJECT.exchangeRate.currency.name}</td>
	<td align="right">${X_OBJECT.quantity}</td>
	<td align="right">${X_OBJECT.amount}</td>
	<td align="right"><span id="span_item_baseCurrencyAmount">${X_OBJECT.baseCurrencyAmount}</span></td>
	<c:if test="${x_edit}">
	<td>
		<input type="hidden" name="id" value="${X_OBJECT.id}"/>
		<input type="hidden" name="capexRequest_capex_purchaseCategory_id" value="${X_OBJECT.purchaseSubCategory.purchaseCategory.id}"/>
		<input type="hidden" name="purchaseSubCategory_id" value="${X_OBJECT.purchaseSubCategory.id}"/>
		<input type="hidden" name="supplier_id" value="${X_OBJECT.supplier.id}"/>
		<input type="hidden" name="supplierName" value="${X_OBJECT.supplierName}"/>
		<input type="hidden" name="supplierItem_id" value="${X_OBJECT.supplierItem.id}"/>
		<input type="hidden" name="supplierItemSepc" value="${X_OBJECT.supplierItemSepc}"/>
		<input type="hidden" name="price" value="${X_OBJECT.price}"/>
		<input type="hidden" name="exchangeRate_id" value="${X_OBJECT.exchangeRate.id}"/>
		<input type="hidden" name="quantity" value="${X_OBJECT.quantity}"/>
		<input type="hidden" name="exchangeRateValue" value="${X_OBJECT.exchangeRateValue}"/>
		<a href="javascript:editItem(${X_OBJECT.id})"><bean:message key="all.modify"/></a>
		<a href="javascript:deleteItem(${X_OBJECT.id})"><bean:message key="all.delete"/></a>
	</td>
	</c:if>
</tr>
