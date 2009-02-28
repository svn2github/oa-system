<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<html:javascript formName="purchaseOrderItemAttachmentForm" staticJavascript="false" />
<html:form action="/insertPurchaseOrderItemAttachment${x_postfix}" enctype="multipart/form-data" 
	onsubmit="return validatePurchaseOrderItemAttachmentForm(this)" >

	<table width=100% border=0 cellpadding=4 cellspacing=0>
		<tr>
			<td class="bluetext"><bean:message key="purchaseOrderItemAttachment.title" />:</td>
			<td><html:text property="description"  size="30"/><span class="required">*</span></td>
		</tr>			
		<tr>
			<td class="bluetext"><bean:message key="purchaseOrderItemAttachment.fileContent" />:</td>
			<td><html:file property="fileContent"  size="30"/><span class="required">*</span></td>
		</tr>			
		<tr>
			<td colspan="2" align="right"><html:submit><bean:message key="all.save" /></html:submit></td>
		</tr>
	</table>
</html:form>
