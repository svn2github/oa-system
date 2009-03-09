<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<tr id="r${X_OBJECT.id}">
  <td><a href='javascript:assign("${X_OBJECT.id}")'>${X_OBJECT.id}</a></td>
  <td>${X_OBJECT.requestor.name}</td>  
  <td>${X_OBJECT.department.name}</td>  
  <td>${X_OBJECT.title}</td>
  <td><bean:write name="X_OBJECT" property="requestDate" format="yyyy/MM/dd"/></td>
  <td>${X_OBJECT.purchaser.name}</td>
  <td>
    <span style="color:${X_OBJECT.status.color}">
    	<bean:write name="X_OBJECT" property="status.${x_lang}ShortDescription" />
    </span>
  </td>
</tr>
