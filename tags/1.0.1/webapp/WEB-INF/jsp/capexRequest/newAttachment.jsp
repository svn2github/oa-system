<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<html:javascript formName="capexRequestAttachmentForm" staticJavascript="false" />
<html:form action="/insertCapexRequestAttachment" enctype="multipart/form-data" onsubmit="return validateCapexRequestAttachmentForm(this)" >
	<html:hidden property="capexRequest_id" />
	<table width=100% border=0 cellpadding=4 cellspacing=0>
		<tr>
			<td class="bluetext"><bean:message key="capexRequestAttachment.title" />:</td>
			<td><html:text property="description"  size="30"/><span class="required">*</span></td>
		</tr>			
		<tr>
			<td class="bluetext"><bean:message key="capexRequestAttachment.fileContent" />:</td>
			<td><html:file property="fileContent"  size="30"/><span class="required">*</span></td>
		</tr>			
		<tr>
			<td colspan="2" align="right"><html:submit><bean:message key="all.save" /></html:submit></td>
		</tr>
	</table>
</html:form>
