<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<tr id="r${X_OBJECT.user.id}">
  <td>${X_OBJECT.user.name}</td>
  <td>
    <a href="javascript:deleteNotifier(${X_OBJECT.user.id})"><bean:message key="all.delete"/></a>
  </td>
</tr>
