<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>

<html:form action="/requestPromoteHotel_result">
	<html:hidden property="id" />
	<table width=100% border=0 cellpadding=4 cellspacing=0>
		<tr>
			<td class="bluetext"><bean:message key="hotel.name" />:</td>
			<td>${x_hotel.name}</td>
		</tr>
		<tr>
			<td class="bluetext"><bean:message key="hotel.promoteMessage" />:</td>
			<td>
				<html:textarea property="promoteMessage" cols="30" rows="3" />
			</td>
		</tr>
	</table>
	<hr>
	<table width=100% border=0 cellpadding=4 cellspacing=0>
		<tr>
			<td align="right">
				<html:submit>
					<bean:message key="hotel.promote.request.title" />
				</html:submit>
			</td>
		</tr>
	</table>
</html:form>
