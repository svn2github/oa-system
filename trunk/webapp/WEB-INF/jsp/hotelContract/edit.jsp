<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>

<html:form action="/updateHotelContract">
	<table width=90% border=0 cellpadding=4 cellspacing=0>
		<tr>
			<td class="bluetext"><bean:message key="hotelContract.id" />:</td>
			<td><bean:write name="x_hotelContract" property="id"/></td>
		</tr>
		<tr>
			<td class="bluetext"><bean:message key="hotelContract.hotel.id" />:</td>
			<td>
				<html:select property="hotel_id" >
				</html:select>
			</td>
		</tr>			
		<tr>
			<td class="bluetext"><bean:message key="hotelContract.fileName" />:</td>
			<td><html:text property="fileName" /></td>
		</tr>			
		<tr>
			<td class="bluetext"><bean:message key="hotelContract.description" />:</td>
			<td><html:text property="description" /></td>
		</tr>			
		<tr>
			<td class="bluetext"><bean:message key="hotelContract.fileContent" />:</td>
			<td><html:text property="fileContent" /></td>
		</tr>			
		<tr>
			<td class="bluetext"><bean:message key="hotelContract.uploadDate" />:</td>
			<td><html:text property="uploadDate" /></td>
		</tr>			

		<tr>
			<td class="bluetext">Status:</td>
			<td><html:select property="enabled_enumCode">
				<c:if test="${sessionScope.LOGIN_USER.locale=='zh'}">
					<html:options collection="X_ENABLEDDISABLEDLIST"
						property="enumCode" labelProperty="chnShortDescription" />
				</c:if>
				<c:if test="${sessionScope.LOGIN_USER.locale!='zh'}">
					<html:options collection="X_ENABLEDDISABLEDLIST"
						property="enumCode" labelProperty="engShortDescription" />
				</c:if>
			</html:select></td>
		</tr>
		<tr>
			<td colspan="2" align="right"><html:submit>
				<bean:message key="all.save" />
			</html:submit></td>
		</tr>
	</table>
</html:form>
