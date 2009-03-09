<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<c:if test="${x_edit}">
<script type="text/javascript">
<!--
	function addAttachment() {
		v = window.showModalDialog(
			'showDialog.do?title=capexRequestAttachment.new.title&newCapexRequestAttachment.do?capexRequest_id=${x_capexRequest.id}', 
			null, 'dialogWidth:400px;dialogHeight:180px;status:no;help:no;scroll:no');
		if (v) {
			var table = document.all('attachment_datatable');
			appendRow(table, v);
		    applyRowStyle(table);
		};
	}

	function deleteAttachment(id) {
		v = window.showModalDialog(
			'showDialog.do?title=capexRequestAttachment.delete.title&confirmOperationDialog.do?message=capexRequestAttachment.delete.confirm&deleteCapexRequestAttachment.do?id=' + id, 
			null, 'dialogWidth:250px;dialogHeight:150px;status:no;help:no;scroll:no');
		if (v) {
			deleteRow(document.all('attachment_datatable').all('r' + id));
		};
	}
//-->
</script>
</c:if>
<h3 style="color:blue"><bean:message key="capexRequest.attachment"/></h3>
<c:if test="${x_edit}"><input type="button" value="<bean:message key="all.new"/>" onclick="addAttachment();"/></c:if>
<table class="data">
	<thead>
		<tr class="new_bg">
			<th><bean:message key="capexRequestAttachment.title" /></th>
			<th><bean:message key="capexRequestAttachment.fileName" /></th>
			<th><bean:message key="capexRequestAttachment.uploadDate" /></th>
			<c:if test="${x_edit}"><th></th></c:if>
		</tr>
	</thead>
	<tbody id="attachment_datatable">
		<c:forEach var="X_OBJECT" items="${x_capexRequestAttachmentList}">
			<c:set var="X_OBJECT" scope="request" value="${X_OBJECT}"/>
			<jsp:include page="attachmentRow.jsp" />
		</c:forEach>
	</tbody>
</table>
<script type="text/javascript">
    applyRowStyle(document.all('attachment_datatable'));
</script>
