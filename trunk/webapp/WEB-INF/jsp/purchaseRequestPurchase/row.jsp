<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ page import="net.sourceforge.model.metadata.PurchaseRequestStatus"%>
<c:set var="x_approved" value="<%=PurchaseRequestStatus.APPROVED.getEnumCode()%>"/>
<tr id="r${X_OBJECT.id}">
  <td>
  	<c:choose>
  		<c:when test="${X_OBJECT.status.enumCode==x_approved}">
  			<a href='javascript:purchase("${X_OBJECT.id}")'>${X_OBJECT.id}</a>
  		</c:when>
  		<c:otherwise><a href='javascript:viewPurchase("${X_OBJECT.id}")'>${X_OBJECT.id}</a></c:otherwise>
  	</c:choose>
  </td>
  <td>${X_OBJECT.requestor.name}</td>  
  <td>${X_OBJECT.title}</td>
  <td>${X_OBJECT.amount}</td>
  <td>
  	<bean:write name="X_OBJECT" property="requestDate" format="yyyy/MM/dd"/>
  </td>
  <td>
    <span style="color:${X_OBJECT.status.color}">
    	<bean:write name="X_OBJECT" property="status.${x_lang}ShortDescription" />
    </span>
  </td>
</tr>
