<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<tr>
  <td>${X_OBJECT.site.name}</td>
  <td>${X_OBJECT.user.loginName}</td>
  <td>${X_OBJECT.user.name}</td>
  <td>${X_OBJECT.actionTime}</td>
  <td>${X_OBJECT.target}</td>
  <td>${X_OBJECT.targetId}</td>
  <td>${X_OBJECT.action}</td>
  <td>${X_OBJECT.content}</td>
</tr>

