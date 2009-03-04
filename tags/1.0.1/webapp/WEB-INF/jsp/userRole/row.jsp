<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<tr id="r${X_OBJECT.id}">
  <td>${X_OBJECT.role.name}</td>
  <td>${X_OBJECT.grantedSite.name}</td>
  <td>${X_OBJECT.grantedDepartment.name}</td>
  <td>
    <a href="javascript:editUserRole(${X_OBJECT.id})"><bean:message key="all.modify"/></a>
  </td>
  <td>
    <a href="javascript:deleteUserRole(${X_OBJECT.id})"><bean:message key="all.delete"/></a>
  </td>
</tr>
