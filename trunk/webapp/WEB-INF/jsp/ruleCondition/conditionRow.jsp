<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<% 	
	long id = System.currentTimeMillis();
%>
<tr id="r<%= id %>">  
  <td>  	
    <span style="color:${X_OBJECT.type.color}"><bean:write name="X_OBJECT" property="type.${x_lang}Description"/></span>
    <span style="color:${X_OBJECT.compareType.color}"><bean:write name="X_OBJECT" property="compareType.${x_lang}Description"/></span>
    <span>${X_OBJECT.displayValue}</span>
    <span style="display:none">${X_OBJECT.type.enumCode}</span>
    <span style="display:none">${X_OBJECT.compareType.enumCode}</span>
    <span style="display:none">${X_OBJECT.value}</span>
    <span style="display:none">${X_OBJECT.type.color}</span>
    <span style="display:none">${X_OBJECT.compareType.color}</span>
  </td>
  <td>
    <a href="javascript:editCondition(${X_OBJECT.type.enumCode}, ${X_OBJECT.compareType.enumCode}, ${X_OBJECT.value}, <%= id %>)"><bean:message key="all.modify"/></a>
  </td>
  <td>
    <a href="javascript:deleteCondition(${X_OBJECT.type.enumCode}, <%= id %>)"><bean:message key="all.delete"/></a>
  </td>  
</tr>
