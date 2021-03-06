<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ page import="net.sourceforge.model.metadata.PurchaseOrderStatus"%>
<c:set var="x_approved" value="<%=PurchaseOrderStatus.APPROVED.getEnumCode()%>"/>
<c:set var="x_confirmed" value="<%=PurchaseOrderStatus.CONFIRMED.getEnumCode()%>"/>
<c:set var="x_received" value="<%=PurchaseOrderStatus.RECEIVED.getEnumCode()%>"/>

<tr id="r${X_OBJECT.id}">
  <td>
	<c:if test="${X_OBJECT.status.enumCode==x_approved}">
		<a href='javascript:confirm("${X_OBJECT.id}")'>${X_OBJECT.id}</a>
	</c:if>
	<c:if test="${X_OBJECT.status.enumCode==x_confirmed}">	
		<a href='javascript:editInsecptor("${X_OBJECT.id}")'>${X_OBJECT.id}</a>
	</c:if>
	<c:if test="${X_OBJECT.status.enumCode==x_received}">	
		<a href='javascript:view("${X_OBJECT.id}")'>${X_OBJECT.id}</a>
	</c:if>
  </td>
  <td>${X_OBJECT.supplier.name}</td>  
  <td>${X_OBJECT.subCategory.purchaseCategory.description}</td>    
  <td>${X_OBJECT.subCategory.description}</td>      
  <td align="right">${X_OBJECT.baseCurrencyAmount}</td>
  <td>
  	<bean:write name="X_OBJECT" property="createDate" format="yyyy/MM/dd"/>
  </td>
  <td>${X_OBJECT.createUser.name}</td>    
  <td><bean:write name="X_OBJECT" property="confirmDate" format="yyyy/MM/dd"/></td>
  <td>
    <span style="color:${X_OBJECT.status.color}">
    	<bean:write name="X_OBJECT" property="status.${x_lang}ShortDescription" />
    </span>
  </td>
</tr>
