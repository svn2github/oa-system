<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<tr id="r${X_OBJECT.id}">
  <td><a href="javascript:edit(${X_OBJECT.id})">${X_OBJECT.loginName}</a></td>
  <td>${X_OBJECT.name}</td>
  <td>${X_OBJECT.email}</td>
  <td>${X_OBJECT.telephone}</td>
  <td>
    <span style="color:${X_OBJECT.enabled.color}">
      <c:if test="${sessionScope.LOGIN_USER.locale=='en'}">${X_OBJECT.enabled.engShortDescription}</c:if>
      <c:if test="${sessionScope.LOGIN_USER.locale!='en'}">${X_OBJECT.enabled.chnShortDescription}</c:if>
    </span>
  </td>
  <td><a href="listUserSiteDepartment.do?userId=${X_OBJECT.id}"><bean:message key="all.enter"/></a></td>
  <td><a href="listUserRole.do?userId=${X_OBJECT.id}"><bean:message key="all.enter"/></a></td>
</tr>
