<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<tr id="r${X_OBJECT.id}">
	<td>
		${X_OBJECT.description}
	</td>
	<td>
		<a href="downloadHotelContract${x_version}.do?id=${X_OBJECT.id}" target="_blank">${X_OBJECT.fileName}</a>
	</td>
	<td align="center">
		&nbsp;<bean:write name="X_OBJECT" property="uploadDate" format="yyyy/MM/dd"/>
	</td>
</tr>

