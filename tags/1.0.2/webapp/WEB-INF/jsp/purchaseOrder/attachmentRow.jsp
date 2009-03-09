<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<tr id="r${X_OBJECT.id}">
	<td>${X_OBJECT.description}</td>
	<td>
		<a href="downloadPurchaseOrderAttachment.do?id=${X_OBJECT.id}">
			${X_OBJECT.fileName}
		</a>
	</td>
	<td align="center"><bean:write name="X_OBJECT" property="uploadDate" format="yyyy/MM/dd"/></td>
	<c:if test="${x_edit}">
	<td align="center">
		<a href="javascript:deleteAttachment(${X_OBJECT.id});">
			<bean:message key="all.delete" />
		</a>
		<input type="hidden" name="attachment_id" value="${X_OBJECT.id}" />
	</td>
	</c:if>
</tr>

