<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>

<html:javascript formName="hotelContractForm" staticJavascript="false" />
<html:form action="/insertHotelContract${x_version}" enctype="multipart/form-data" onsubmit="return validateHotelContractForm(this)">
	<html:hidden property="hotel_id" />
	<table width=100% border=0 cellpadding=4 cellspacing=0>
		<tr>
			<td class="bluetext"><bean:message key="hotelContract.description" />:</td>
			<td><html:text property="description" size="30"/><span class="required">*</span></td>
		</tr>			
		<tr>
			<td class="bluetext"><bean:message key="hotelContract.fileContent" />:</td>
			<td><html:file property="fileContent" size="30"/><span class="required">*</span></td>
		</tr>			
		<tr>
			<td colspan="2" align="right"><html:submit>
				<bean:message key="all.save" />
			</html:submit></td>
		</tr>
	</table>
</html:form>
