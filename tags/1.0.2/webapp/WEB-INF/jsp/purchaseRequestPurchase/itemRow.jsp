<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>

<tr id="r${X_OBJECT.id}">
	<c:if test="${x_edit}">
	<td>
		<c:choose>
			<c:when test="${X_OBJECT.supplier!=null}">	
				<c:if test="${X_OBJECT.purchaseOrder==null}">
					<input type="checkbox" name="po_ids" 
						value="${X_OBJECT.id}" 
						supplier="${X_OBJECT.supplier.id}" 
						exchangeRate="${X_OBJECT.exchangeRate.id}" 
					/>
				</c:if>					
			</c:when>
			<c:otherwise>
				<font color="red">
				<bean:message key="purchaseRequest.purchase.maintainsupplier"/>
				</font>
			</c:otherwise>
		</c:choose>
	</td>
	</c:if>
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
	<td>${X_OBJECT.item.sepc}</td>
	<td>${X_OBJECT.itemSpec}</td>
	<td align="right">${X_OBJECT.unitPrice}</td>
	<td>${X_OBJECT.exchangeRate.currency.name}</td>
	<td align="right"><span id="span_item_quantity">${X_OBJECT.quantity}</span></td>
	<td align="right"><span id="span_item_amount">${X_OBJECT.amount}</span></td>
	<td align="right"><span id="span_item_baseCurrencyAmount">${X_OBJECT.baseCurrencyAmount}</span></td>
	<td>${X_OBJECT.purchaseType.description}</td>
	<td align="right">
		<fmt:formatDate value="${X_OBJECT.dueDate}" pattern="yyyy/MM/dd"/>
	</td>
	<td align="right"><span id="span_item_projectCode">${X_OBJECT.projectCode.code}</span></td>
	<td>
		<c:choose>
			<c:when test="${x_edit&&X_OBJECT.purchaseOrder==null}">
				<a href="javascript:editItem(${X_OBJECT.id})"><bean:message key="all.modify"/></a>
				&nbsp;<a href="javascript:splitItem(${X_OBJECT.id})"><bean:message key="purchaseOrderItem.split"/></a>
			</c:when>
			<c:otherwise>
				<a href='javascript:viewItem("${X_OBJECT.id}")'><bean:message key="all.detail"/></a>
				<c:if test="${X_OBJECT.purchaseOrder!=null}">
				&nbsp;<a href='javascript:viewPO("${X_OBJECT.purchaseOrder.id}")'><bean:message key="purchaseRequest.purchase.viewPO"/></a>
				</c:if>
			</c:otherwise>
		</c:choose>
	</td>
</tr>
