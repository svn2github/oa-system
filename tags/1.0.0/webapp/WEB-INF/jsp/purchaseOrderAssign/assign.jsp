<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<script>
	function backToList() {
		var url='listPurchaseOrder_assign.do';
		window.location.href=url;
	}
</script>

<jsp:include page="../purchaseOrder/baseEdit.jsp"/>
<html:javascript formName="purchaseOrderAssignForm" staticJavascript="false" />
<html:form action="assignPurchaseOrder_result.do" onsubmit="return validatePurchaseOrderAssignForm(this)">
<html:hidden property="id"/>
<table  border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td class="bluetext"><bean:message key="purchaseOrder.purchaser.id" />: </td>
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
