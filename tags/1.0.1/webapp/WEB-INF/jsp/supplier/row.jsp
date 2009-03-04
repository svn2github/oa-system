<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<tr id="r${X_OBJECT.id}">
  <td>
	  <a href='javascript:edit("${X_OBJECT.id}")'>${X_OBJECT.id}</a>
  </td>
  <td>${X_OBJECT.name}</td>
  <td>
	  <c:if test="${sessionScope.LOGIN_USER.locale=='en'}">${X_OBJECT.city.engName}</c:if>
      <c:if test="${sessionScope.LOGIN_USER.locale!='en'}">${X_OBJECT.city.chnName}</c:if>
  </td>
  <td>
	  <c:if test="${sessionScope.LOGIN_USER.locale=='en'}">${X_OBJECT.country.engName}</c:if>
      <c:if test="${sessionScope.LOGIN_USER.locale!='en'}">${X_OBJECT.country.chnName}</c:if>
  </td>
  <td>${X_OBJECT.telephone1}</td>
  <td>${X_OBJECT.contact}</td>
  <td>
  </td>
</tr>
