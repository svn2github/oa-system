<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ page import="net.sourceforge.model.metadata.PurchaseRequestStatus"%>
<c:set var="x_approved" value="<%=PurchaseRequestStatus.APPROVED.getEnumCode()%>"/>
<tr id="r${X_OBJECT.id}">
	<td>
		<c:if test="${X_OBJECT.editable}">
		<input type="checkbox" name="selected_po_id" value="${X_OBJECT.id}"
			subCategory="${X_OBJECT.subCategory.id}" 
			supplier="${X_OBJECT.supplier.id}" 
			exchangeRate="${X_OBJECT.exchangeRate.id}"
		/>
		</c:if>
	</td>
  <td>
  	<c:choose>
  		<c:when test="${X_OBJECT.editable}">
  			<a href='javascript:edit("${X_OBJECT.id}")'>${X_OBJECT.id}</a>
  		</c:when>
  		<c:otherwise><a href='javascript:view("${X_OBJECT.id}")'>${X_OBJECT.id}</a></c:otherwise>
  	</c:choose>
  </td>
  <td>${X_OBJECT.supplier.name}</td>  
  <td>${X_OBJECT.subCategory.purchaseCategory.description}</td>    
  <td>${X_OBJECT.subCategory.description}</td>      
  <td align="right">${X_OBJECT.baseCurrencyAmount}</td>
  <td>
  	<bean:write name="X_OBJECT" property="createDate" format="yyyy/MM/dd"/>
  </td>
  <td>${X_OBJECT.createUser.name}</td>    
  <td>
    <span style="color:${X_OBJECT.status.color}">
    	<bean:write name="X_OBJECT" property="status.${x_lang}ShortDescription" />
    </span>
  </td>
  <td>
	<input type="text" name="newErpNo" value="${X_OBJECT.erpNo}" maxlength="16" size="10"/>
	<input type="hidden" name="po_id" value="${X_OBJECT.id}"/>
	<c:set var="x_updateErpNo" value="true" scope="request"/>
  </td>
</tr>
