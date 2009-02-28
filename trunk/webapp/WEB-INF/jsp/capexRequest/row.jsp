<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<tr id="r${X_OBJECT.id}">
  <td>
  	<c:choose>
  		<c:when test="${X_OBJECT.editable}"><a href="editCapexRequest.do?id=${X_OBJECT.id}">${X_OBJECT.capex.id}</a></c:when>
  		<c:otherwise><a href="viewCapexRequest.do?id=${X_OBJECT.id}">${X_OBJECT.capex.id}</a></c:otherwise>
  	</c:choose>
  </td>
  <td>${X_OBJECT.title}</td>
  <td>${X_OBJECT.capex.yearlyBudget.code}</td>
  <td align="right">${X_OBJECT.amount}</td>
  <td align="center"><bean:write name="X_OBJECT" property="requestDate" format="yyyy/MM/dd"/></td>
  <td>
    <span style="color:${X_OBJECT.type.color}">
      <c:if test="${sessionScope.LOGIN_USER.locale=='en'}">${X_OBJECT.type.engShortDescription}</c:if>
      <c:if test="${sessionScope.LOGIN_USER.locale!='en'}">${X_OBJECT.type.chnShortDescription}</c:if>
    </span>
  </td>
  <td>
    <span style="color:${X_OBJECT.status.color}">
      <c:if test="${sessionScope.LOGIN_USER.locale=='en'}">${X_OBJECT.status.engShortDescription}</c:if>
      <c:if test="${sessionScope.LOGIN_USER.locale!='en'}">${X_OBJECT.status.chnShortDescription}</c:if>
    </span>
  </td>
</tr>
