<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>


<tr id="r${X_OBJECT.id}">
	<td width="20%">${X_OBJECT.receiveQty1}</td>
	<td width="20%"><bean:write name="X_OBJECT" property="receiveDate1" format="yyyy/MM/dd"/></td>
	<td width="20%">${X_OBJECT.receiveQty2}</td>
	<td width="20%"><bean:write name="X_OBJECT" property="receiveDate2" format="yyyy/MM/dd"/></td>	
	<td>
		<c:if test="${!X_OBJECT.purchaseOrderItem.received}">
			<c:if test="${!X_OBJECT.finished}">
				<a href='javascript:edit("${X_OBJECT.id}")'><bean:message key="all.confirm"/></a>
				&nbsp;
				<a href='javascript:deleteReceipt("${X_OBJECT.id}")'><bean:message key="all.delete"/></a>
				<span id="row_qty" style="display:none">0</span>
			</c:if>
			<span id="received_span" style="display:none">false</span>
		</c:if>
		<c:if test="${X_OBJECT.finished}">
			<span id="row_qty" style="display:none">${X_OBJECT.receiveQty1}</span>
		</c:if>
		<c:if test="${X_OBJECT.purchaseOrderItem.received}">
			<span id="received_span" style="display:none">true</span>
			<c:if test="${!X_OBJECT.finished}">
				<a href='javascript:deleteReceipt("${X_OBJECT.id}")'><bean:message key="all.delete"/></a>
			</c:if>
		</c:if>
	</td>
	
</tr>
