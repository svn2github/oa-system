<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>

<jsp:include page="script.jsp" />

<c:if test="${x_add}">
	<c:set var="x_method" value="insert"/>
</c:if>	
<c:if test="${!x_add}">
	<c:set var="x_method" value="update"/>
</c:if>	

<div><html:errors/></div>
<html:form action="/${x_method}PurchaseOrderItemReceipt" onsubmit="return validateForm(this)">
	<html:hidden property="purchaseOrderItem_id"/>
	<html:hidden property="id"/>
	<table cellpadding=1 cellspacing=1>
      <tr>
        <td class="bluetext"> <bean:message key="purchaseOrderItemReceipt.receivedQuantity"/>: </td>
        <td align="left">
        	<html:text size="5" property="receiveQty${x_receiverNo}"/>
        	<span class="required">*</span>
        	(<bean:message key="purchaseOrderItemReceipt.remain"/>:${x_maxQty})
        </td>
      </tr>
      <tr>
        <td class="bluetext"> <bean:message key="purchaseOrderItemReceipt.receiveDate"/>: </td>
        <td align="left">
			<bean:write name="x_receiveDate"  format="yyyy/MM/dd" />
        </td>
        <td class="bluetext"> <bean:message key="purchaseOrderItemReceipt.receiver"/>: </td>
        <td align="left">${sessionScope.LOGIN_USER.name}</td>
      </tr>
		<tr>
			<td colspan="5" align="right"><html:submit>
				<bean:message key="all.save" />
			</html:submit>
		  	<input type="button" value="<bean:message key="all.cancel"/>" onclick="window.parent.close();"/>				
			</td>
		</tr>
	</table>
</html:form>
