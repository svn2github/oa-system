<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<tr id="r${X_OBJECT.user.id}">
  <td>
  	<span>${X_OBJECT.user.name}</span>
  	<span style="display:none">${X_OBJECT.user.id}</span>
  </td>
  <td>
    <a href="javascript:deletePurchaser(${X_OBJECT.user.id})"><bean:message key="all.delete"/></a>
  </td>
</tr>
