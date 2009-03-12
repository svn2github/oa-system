<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ page import="net.sourceforge.model.metadata.PurchaseRequestStatus"%>
<jsp:include page="baseEdit.jsp"/>
<table width="100%">
	<tr>
		<td align="right">
			<input type="button" value="<bean:message key="all.close"/>" onclick="window.close();">
		</td>
	</tr>
</table>
