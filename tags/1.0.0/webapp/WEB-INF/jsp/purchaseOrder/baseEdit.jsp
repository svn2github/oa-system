<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<div class="message"><html:messages id="x_message" message="true">${x_message}<br></html:messages></div>
<c:choose>
<c:when test="${x_edit}">
	<div class="warningMsg"><html:errors/></div>
	<html:javascript formName="purchaseOrderForm" staticJavascript="false" />
	<html:form action="updatePurchaseOrder.do">
		<html:hidden property="id"/>
		<input type="hidden" name="draft"/>
		<jsp:include page="editPurchaseOrder.jsp"/>
		<jsp:include page="itemList.jsp"/>
		<jsp:include page="attachmentList.jsp"/>
		<jsp:include page="paymentScheduleDetailList.jsp"/>
		<div id="oldApprovers">
			<jsp:include page="../approve/list.jsp"/>
		</div>
	</html:form>
</c:when>
<c:otherwise>
	<jsp:include page="editPurchaseOrder.jsp"/>
	<jsp:include page="itemList.jsp"/>
	<jsp:include page="attachmentList.jsp"/>
	<logic:notEmpty name="x_paymentScheduleDetailList">
		<jsp:include page="paymentScheduleDetailList.jsp"/>
	</logic:notEmpty>
	<div id="oldApprovers">
		<jsp:include page="../approve/list.jsp"/>
	</div>
</c:otherwise>
</c:choose>	