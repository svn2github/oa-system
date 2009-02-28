<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<jsp:include page="../purchaseOrder/editPurchaseOrder.jsp"/>
<jsp:include page="itemList.jsp"/>
<jsp:include page="../purchaseOrder/attachmentList.jsp"/>
<jsp:include page="../purchaseOrder/paymentScheduleDetailList.jsp"/>
<jsp:include page="../approve/list.jsp"/>
<table width="100%">
	<tr>
		<td align="right">
			<input type="button" value="<bean:message key="all.back"/>"	
				onclick="window.location.href='listPurchaseOrder_cancel.do'">
		</td>
	</tr>
</table>
