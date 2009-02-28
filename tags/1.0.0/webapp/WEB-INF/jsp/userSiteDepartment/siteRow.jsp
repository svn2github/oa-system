<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<tr id="r${X_OBJECT.site.id}">
  <td>${X_OBJECT.site.name}</td>
  <td>${X_OBJECT.travelGroup.name}</td>
  <td>
    <a href="javascript:editUserSite(${X_OBJECT.site.id})"><bean:message key="all.modify"/></a>
  </td>
  <td>
    <a href="javascript:deleteUserSite(${X_OBJECT.site.id})"><bean:message key="all.delete"/></a>
  </td>
</tr>
