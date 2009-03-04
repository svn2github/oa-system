<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<tr id="r${X_OBJECT.id}">
  <td>${X_OBJECT.loginName}</td>
  <td>${X_OBJECT.name}</td>
  <td>${X_OBJECT.email}</td>
  <td>${X_OBJECT.telephone}</td>
  <td><a href='javascript:select("${X_OBJECT.id}", "${X_OBJECT.name}");'/><bean:message key="all.select"/></a></td>
</tr>
