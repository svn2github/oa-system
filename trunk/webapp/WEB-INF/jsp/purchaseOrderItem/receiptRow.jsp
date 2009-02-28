<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<tr id="r${X_OBJECT.id}">
  <td align="center">${X_OBJECT.purchaseOrder.id}</td>
  <td><a href='javascript:listReceipt("${X_OBJECT.id}")'>${X_OBJECT.id}</a></td>
  <td><a href='javascript:listReceipt("${X_OBJECT.id}")'>${X_OBJECT.itemSpec}</a></td>
  <td align="right">${X_OBJECT.quantity}</td>  
  <td align="right">${X_OBJECT.receivedQuantity}</td>  
  <td align="right">${X_OBJECT.returnedQuantity}</td>  
  <td align="right">${X_OBJECT.cancelledQuantity}</td>  
  <td align="center"><a href='javascript:viewPR("${X_OBJECT.purchaseRequestItem.purchaseRequest.id}")'>${X_OBJECT.purchaseRequestItem.purchaseRequest.id}</a></td>
</tr>
