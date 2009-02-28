<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<tr id="r${X_OBJECT.id}">
	<td>${X_OBJECT.purchaseSubCategory.purchaseCategory.description}</td>
	<td>${X_OBJECT.purchaseSubCategory.description}</td>
	<td><a href="javascript:edit(${X_OBJECT.id})">${X_OBJECT.sepc}</a></td>
	<td align="right">${X_OBJECT.unitPrice}</td>
	<td>${X_OBJECT.currency.name}</td>
	<td><span style="color:${X_OBJECT.enabled.color}"> <c:if
		test="${sessionScope.LOGIN_USER.locale=='en'}">${X_OBJECT.enabled.engShortDescription}</c:if>
	<c:if test="${sessionScope.LOGIN_USER.locale!='en'}">${X_OBJECT.enabled.chnShortDescription}</c:if>
	</span></td>
	<c:if test="${modifyable}">
	<td><a href="javascript:del(${X_OBJECT.id})"><bean:message key="all.delete"/></a></td>
	</c:if>
</tr>

