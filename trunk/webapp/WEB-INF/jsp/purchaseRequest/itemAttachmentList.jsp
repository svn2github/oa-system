<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<c:if test="${x_edit}">
<script type="text/javascript">
<!--
	function addAttachment() {
		var url='newPurchaseRequestItemAttachment${x_postfix}.do';
		var title="purchaseRequestItemAttachment.new.title";
	
		var v=dialogAction(url,title,400,180);
		if (v) {
			var table = document.all('item_attachment_datatable');
			appendRow(table, v);
		    applyRowStyle(table);
		    
		    var row = table.rows[table.rows.length - 1];
		    //alert(row.innerHTML);
		};
	}

	function deleteAttachment(id) {
		if(confirm("<bean:message key="purchaseRequestItemAttachment.delete.confirm"/>"))
		{
			var table = document.all('item_attachment_datatable');
			deleteRow(table.all('r' + id));
			applyRowStyle(table);
		};
	}
//-->
</script>
</c:if>
<h3 style="color:blue"><bean:message key="purchaseRequestItem.attachment"/></h3>
<c:if test="${x_edit}">
 	<input type="button" value="<bean:message key="all.new"/>" onclick="addAttachment();"/>
</c:if>
<table class="data">
	<thead>
		<tr class="new_bg">
			<th><bean:message key="purchaseRequestItemAttachment.title" /></th>
			<th><bean:message key="purchaseRequestItemAttachment.fileName" /></th>
			<th><bean:message key="purchaseRequestItemAttachment.uploadDate" /></th>
			<c:if test="${x_edit}"><th></th></c:if>
		</tr>
	</thead>
	<tbody id="item_attachment_datatable">
		<c:forEach var="X_OBJECT" items="${x_pri.attachments}">
			<c:set var="X_OBJECT" scope="request" value="${X_OBJECT}"/>
			<jsp:include page="itemAttachmentRow.jsp" />
		</c:forEach>
	</tbody>
</table>
<script type="text/javascript">
    applyRowStyle(document.all('item_attachment_datatable'));
</script>
