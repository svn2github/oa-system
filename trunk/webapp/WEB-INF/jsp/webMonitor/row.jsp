<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<tr>
  <td align="center">${webMonitorQueryForm.pageNextSeq}</td>
  <c:if test="${X_GLOBAL}">
  <td>${X_OBJECT.user.primarySite.name}</td>
  </c:if>
  <td>${X_OBJECT.user.loginName}</td>
  <td>${X_OBJECT.user.name}</td>
  <td>${X_OBJECT.ip}</td>
  <td>${X_OBJECT.loginTime}</td>
  <td>${X_OBJECT.accessTime}</td>  
  <td>
  	<c:if test="${X_OBJECT.liveMinute>0}">${X_OBJECT.liveMinute}<bean:message key="webMonitor.minute"/></c:if> 
  	<c:if test="${X_OBJECT.liveSecond>0}">${X_OBJECT.liveSecond}<bean:message key="webMonitor.second"/></c:if>
  </td>
  
  
</tr>
