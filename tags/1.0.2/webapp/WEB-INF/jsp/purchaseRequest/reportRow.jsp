<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<tr id="r${X_OBJECT.id}">
  <td>
	<a href='javascript:view("${X_OBJECT.id}")'>${X_OBJECT.id}</a>
  </td>
  <td>${X_OBJECT.requestor.name}</td>  
  <td>${X_OBJECT.purchaseSubCategory.purchaseCategory.description}</td>
  <td>${X_OBJECT.purchaseSubCategory.description}</td>
  <td>${X_OBJECT.amount}</td>
  <td><bean:write name="X_OBJECT" property="requestDate" format="yyyy/MM/dd"/></td>
  <td>
    <span style="color:${X_OBJECT.status.color}">
    	<bean:write name="X_OBJECT" property="status.${x_lang}ShortDescription" />
    </span>
  </td>
  <td>${X_OBJECT.approveDurationDay}</td>
</tr>
