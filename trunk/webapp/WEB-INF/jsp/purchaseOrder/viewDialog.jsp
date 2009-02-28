<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<div class="message"><html:messages id="x_message" message="true">${x_message}<br></html:messages></div>
<jsp:include page="editPurchaseOrder.jsp"/>
<jsp:include page="itemList.jsp"/>
<jsp:include page="attachmentList.jsp"/>
<jsp:include page="paymentScheduleDetailList.jsp"/>

<table width="100%">
	<tr>
		<td align="right">
			<input type="button" value="<bean:message key="all.close"/>" onclick="window.close();">
		</td>
	</tr>
</table>
