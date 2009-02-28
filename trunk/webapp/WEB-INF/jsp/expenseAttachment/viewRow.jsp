<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<tr id="ratt${X_OBJECT.id}">
	<td  align="left">
		${X_OBJECT.description}
	</td>
	<td  align="left">
		<a href="downloadExpenseAttachment${x_postfix}.do?id=${X_OBJECT.id}">${X_OBJECT.fileName}</a>
	</td>
	<td  align="center">
		<bean:write name="X_OBJECT" property="uploadDate" format="yyyy/MM/dd"/>
	</td>
</tr>

