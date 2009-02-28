<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<div class="message"><html:messages id="x_message" message="true">${x_message}<br></html:messages></div>
<div class="warningMsg"><html:errors/></div>

<script>
	function backToList() {
		var url='listPurchaseRequest_assign.do';
		window.location.href=url;
	}
</script>

<jsp:include page="../purchaseRequest/editPurchaseRequest.jsp"/>
<jsp:include page="../purchaseRequest/attachmentList.jsp"/>
<html:javascript formName="purchaseRequestAssignForm" staticJavascript="false" />
<jsp:include page="itemList.jsp"/>
<html:form action="assignPurchaseRequest_result.do" onsubmit="return validatePurchaseRequestAssignForm(this)">
<html:hidden property="id"/>
<table  border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td class="bluetext"><bean:message key="purchaseRequest.purchaser.id" />: </td>
		<td><html:select property="purchaser_id">
			<html:option value=""></html:option>
			<html:options collection="x_purchaserList" property="id" labelProperty="name" />
		</html:select><span class="required">*</span></td>
	</tr>
</table>

<table width="90%">
	<tr>
		<td align="right">
			<html:submit><bean:message key="all.submit"/></html:submit>
			<input type="button" value="<bean:message key="all.back"/>"	onclick="backToList();">
		</td>
	</tr>
</table>
</html:form>
