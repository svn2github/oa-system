<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<tr id="r${X_OBJECT.id}">
  <td>
  	<c:choose>
  		<c:when test="${X_OBJECT.editable}">
  			<a href='javascript:edit("${X_OBJECT.id}")'>${X_OBJECT.id}</a>
  		</c:when>
  		<c:otherwise><a href='javascript:view("${X_OBJECT.id}")'>${X_OBJECT.id}</a></c:otherwise>
  	</c:choose>
  </td>
  <c:if test="${x_postfix=='_other'}">
	  <td>${X_OBJECT.requestor.name}</td>  
  </c:if>
  <td>${X_OBJECT.title}</td>
  <td>${X_OBJECT.amount}</td>
  <td><bean:write name="X_OBJECT" property="createDate" format="yyyy/MM/dd"/></td>
  <td>
    <span style="color:${X_OBJECT.status.color}">
    	<bean:write name="X_OBJECT" property="status.${x_lang}ShortDescription" />
    </span>
  </td>
  <td align="center">
	<a href='javascript:copy("${X_OBJECT.id}")'><bean:message key="all.copy" /></a>
  </td>
</tr>
