<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<c:if test="${x_edit}">
<script type="text/javascript">
<!--
	function addAttachment() {
		var url='newPurchaseOrderAttachment.do';
		var title="purchaseOrderAttachment.new.title";
	
		var v=dialogAction(url,title,400,180);
		if (v) {
			var table = document.all('attachment_datatable');
			appendRow(table, v);
		    applyRowStyle(table);
		};
	}

	function deleteAttachment(id) {
		if(confirm("<bean:message key="purchaseOrderAttachment.delete.confirm"/>"))
		{
			var table = document.all('attachment_datatable');
			deleteRow(table.all('r' + id));
			applyRowStyle(table);
		};
	}
//-->
</script>
</c:if>
<h3 style="color:blue"><bean:message key="purchaseOrder.attachment"/></h3>
<c:if test="${x_edit}">
	<input type="button" value="<bean:message key="all.new"/>" onclick="addAttachment();"/>
</c:if>
<table class="data">
	<thead>
		<tr bgcolor="#9999ff">
			<th><bean:message key="purchaseOrderAttachment.title" /></th>
			<th><bean:message key="purchaseOrderAttachment.fileName" /></th>
			<th><bean:message key="purchaseOrderAttachment.uploadDate" /></th>
			<c:if test="${x_edit}"><th></th></c:if>
		</tr>
	</thead>
	<tbody id="attachment_datatable">
		<c:forEach var="X_OBJECT" items="${x_purchaseOrderAttachmentList}">
			<c:set var="X_OBJECT" scope="request" value="${X_OBJECT}"/>
			<jsp:include page="attachmentRow.jsp" />
		</c:forEach>
	</tbody>
</table>
<script type="text/javascript">
    applyRowStyle(document.all('attachment_datatable'));
</script>
