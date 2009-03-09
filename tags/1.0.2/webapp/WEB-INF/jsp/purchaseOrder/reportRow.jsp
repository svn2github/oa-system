<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<tr id="r${X_OBJECT.id}">
  <td>
	<a href='javascript:view("${X_OBJECT.id}")'>${X_OBJECT.id}</a>
  </td>
  <td>${X_OBJECT.amount}</td>
  <td>${X_OBJECT.supplier.name}</td>  
  <td>${X_OBJECT.subCategory.purchaseCategory.description}</td>
  <td>${X_OBJECT.subCategory.description}</td>
  <td><bean:write name="X_OBJECT" property="createDate" format="yyyy/MM/dd"/></td>
  <td>
    <span style="color:${X_OBJECT.status.color}">
    	<bean:write name="X_OBJECT" property="status.${x_lang}ShortDescription" />
    </span>
  </td>
</tr>
