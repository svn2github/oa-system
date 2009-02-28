<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<div class="warningMsg"><html:errors/></div>
<div class="message"><html:messages id="x_message" message="true">${x_message}<br></html:messages></div>
<c:choose>
<c:when test="${x_edit}">
	<html:javascript formName="capexRequestForm" staticJavascript="false" />
	<html:form action="updateCapexRequest.do">
		<html:hidden property="id"/>
		<input type="hidden" name="draft"/>
		<jsp:include page="editCapexRequest.jsp"/>
		<jsp:include page="itemList.jsp"/>
		<hr size="1"/>
		<jsp:include page="attachmentList.jsp"/>
		<div id="oldApprovers">
			<jsp:include page="../approve/list.jsp"/>
		</div>
	</html:form>
</c:when>
<c:otherwise>
	<jsp:include page="editCapexRequest.jsp"/>
	<jsp:include page="itemList.jsp"/>
	<hr size="1"/>
	<jsp:include page="attachmentList.jsp"/>
	<div id="oldApprovers">
		<jsp:include page="../approve/list.jsp"/>
	</div>
</c:otherwise>
</c:choose>	