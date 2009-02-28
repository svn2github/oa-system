<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<tr id="r${X_OBJECT.user.id}">
  <c:if test="${X_OBJECT.superior != null}">
  	<td align="center"><a href="javascript:editApprover(${X_OBJECT.user.id}, ${X_OBJECT.seq}, ${X_OBJECT.canModify.enumCode}, ${X_OBJECT.superior.id})">${X_OBJECT.seq}</a></td>
  </c:if> 
  <c:if test="${X_OBJECT.superior == null}">
  	<td align="center"><a href="javascript:editApprover(${X_OBJECT.user.id}, ${X_OBJECT.seq}, ${X_OBJECT.canModify.enumCode})">${X_OBJECT.seq}</a></td>
  </c:if> 
  <td>
	  <span style="display:none">${X_OBJECT.user.id}</span>
	  <span>${X_OBJECT.user.name}</span>
  </td>
  <td>
  	<span style="display:none">${X_OBJECT.canModify.enumCode}</span>
    <span style="color:${X_OBJECT.canModify.color}"><bean:write name="X_OBJECT" property="canModify.${x_lang}ShortDescription"/></span>
    <span style="display:none">${X_OBJECT.canModify.color}</span>
  </td>
  <td>
  	<span style="display:none">${X_OBJECT.superior.id}</span>
	<span>${X_OBJECT.superior.name} </span>  	
  </td>
  <td>
    <a href="javascript:deleteApprover(${X_OBJECT.user.id})"><bean:message key="all.delete"/></a>
  </td>
</tr>
