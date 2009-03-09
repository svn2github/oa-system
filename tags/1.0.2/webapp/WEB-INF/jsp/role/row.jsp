<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<tr id="r${X_OBJECT.id}">
  <td><a href="javascript:edit(${X_OBJECT.id})">${X_OBJECT.name}</a></td>
  <td>
    <span style="color:${X_OBJECT.type.color}">
      <c:if test="${sessionScope.LOGIN_USER.locale=='en'}">${X_OBJECT.type.engShortDescription}</c:if>
      <c:if test="${sessionScope.LOGIN_USER.locale!='en'}">${X_OBJECT.type.chnShortDescription}</c:if>
    </span>
  </td>
  <td><a href="grantRoleFunction.do?id=${X_OBJECT.id}"><bean:message key="all.grant"/></a></td>
</tr>
