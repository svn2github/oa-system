<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<tr id="r${X_OBJECT.id}">
	<td  align="center">
		${X_OBJECT.description}
	</td>
	<td  align="center">
		<a href="downloadSupplierContract${x_version}.do?id=${X_OBJECT.id}">${X_OBJECT.fileName}</a>
	</td>
	<td  align="center">
		<bean:write name="X_OBJECT" property="uploadDate" format="yyyy/MM/dd HH:mm:ss"/>
	</td>
</tr>

