<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>

<tr id="r${X_OBJECT.id}">
	<td>${X_OBJECT.description}</td>
	<td><fmt:formatDate value="${X_OBJECT.date}" pattern="yyyy/MM/dd"/></td>
	<td align="right"><span id="span_paymentScheduleDetail_amount">${X_OBJECT.amount}</span></td>
	<c:if test="${x_edit}">
	<td>
		<a href="javascript:editPaymentScheduleDetail(${X_OBJECT.id})"><bean:message key="all.modify"/></a>
		&nbsp;<a href="javascript:deletePaymentScheduleDetail(${X_OBJECT.id})"><bean:message key="all.delete"/></a>
	</td>
	</c:if>
</tr>
