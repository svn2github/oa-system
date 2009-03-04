<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<c:if test="${x_edit}">
<script type="text/javascript">
<!--
	function addAttachment() {
		var url='newPurchaseOrderItemAttachment.do';
		var title="purchaseOrderItemAttachment.new.title";
	
		var v=dialogAction(url,title,400,180);
		if (v) {
			var table = document.all('item_attachment_datatable');
			appendRow(table, v);
		    applyRowStyle(table);
		    
		    var row = table.rows[table.rows.length - 1];
		};
	}

	function deleteAttachment(id) {
		if(confirm("<bean:message key="purchaseOrderItemAttachment.delete.confirm"/>"))
		{
			var table = document.all('item_attachment_datatable');
			deleteRow(table.all('r' + id));
			applyRowStyle(table);
		};
	}
//-->
</script>
</c:if>
<h3 style="color:blue"><bean:message key="purchaseOrderItem.attachment"/></h3>
<c:if test="${x_edit}">
 	<input type="button" value="<bean:message key="all.new"/>" onclick="addAttachment();"/>
</c:if>
<table class="data">
	<thead>
		<tr bgcolor="#9999ff">
			<th><bean:message key="purchaseOrderItemAttachment.title" /></th>
			<th><bean:message key="purchaseOrderItemAttachment.fileName" /></th>
			<th><bean:message key="purchaseOrderItemAttachment.uploadDate" /></th>
			<c:if test="${x_edit}"><th></th></c:if>
		</tr>
	</thead>
	<tbody id="item_attachment_datatable">
		<c:forEach var="X_OBJECT" items="${x_poi.attachments}">
			<c:set var="X_OBJECT" scope="request" value="${X_OBJECT}"/>
			<jsp:include page="itemAttachmentRow.jsp" />
		</c:forEach>
	</tbody>
</table>
<script type="text/javascript">
    applyRowStyle(document.all('item_attachment_datatable'));
</script>
