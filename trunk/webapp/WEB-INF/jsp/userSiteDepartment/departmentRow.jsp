<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<tr id="r${X_OBJECT.department.id}">
  <td>${X_OBJECT.department.site.name}</td>
  <td>${X_OBJECT.department.name}</td>
  <td>${X_OBJECT.title}</td>
  <td>
    <a href="javascript:editUserDepartment(${X_OBJECT.department.id})"><bean:message key="all.modify"/></a>
  </td> 
  <td>
    <a href="javascript:deleteUserDepartment(${X_OBJECT.department.id})"><bean:message key="all.delete"/></a>
  </td>
</tr>
