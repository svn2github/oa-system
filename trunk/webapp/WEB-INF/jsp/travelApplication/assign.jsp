<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ page import="net.sourceforge.model.metadata.ApproveStatus"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>


<table width="90%">
	<tr>
		<td>
			<html:errors />
		</td>
	</tr>
</table>

<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td><jsp:include page="baseView.jsp" /></td>
	</tr>
</table>

<html:javascript formName="travelApplicationAssignForm" staticJavascript="false" />
<html:form action="/assignTravelApplication_result" onsubmit="return validateTravelApplicationAssignForm(this)">
<html:hidden property="id"/>
<table  border="0" cellpadding="0" cellspacing="0">
	<tr>

		<td class="bluetext"><bean:message key="travelApplication.booker.id" />: </td>
		<td><html:select property="booker_id">
			<html:option value=""></html:option>
			<html:options collection="x_bookerList" property="id"
				labelProperty="name" />
		</html:select><span class="required">*</span></td>

	</tr>
</table>



<table width="90%">
	<TR>
		<TD ALIGN="RIGHT"><html:submit><bean:message key="all.submit"/></html:submit> <input type="button"
			value="<bean:message key="all.back"/>"
			onclick="window.location.href='listTravelApplication${x_postfix}.do'">
		</TD>
	</TR>
</table>
</html:form>
