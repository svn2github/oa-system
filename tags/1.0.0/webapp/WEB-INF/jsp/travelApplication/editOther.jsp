<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>
<%@ page import="com.aof.model.metadata.*"%>


<script>
	function deleteMe()
	{
		if(confirm("<bean:message key="travelApplication.delete.confirm" />"))
		{
			var url="deleteTravelApplication_other.do?id=${x_travelApplication.id}";
			window.location=url;			
		}
	}
	function saveMe()
	{
		if(!validateForm(document.travelApplicationForm)) return;
		with(document.travelApplicationForm)
		{
			submit();
		}
	}
</script>
<jsp:include page="baseEditOther.jsp"/>
<table width="90%">
	<tr>
		<td><input type="button" value="<bean:message key="travelApplication.button.viewApprover"/>" onclick="viewApprover();"/></td>
		<td align="right">
			<input type="button" value="<bean:message key="travelApplication.saveAsDraft"/>" onclick="saveAsDraft()"/>
			<input type="button" value="<bean:message key="all.delete" />" onclick="deleteMe()"/>
			<input type="button" value="<bean:message key="all.submit" />" onclick="submitTA()"/>
			<input type="button" value="<bean:message key="all.back"/>"	onclick="window.location.href='listTravelApplication_other.do'">
		</td>
	</tr>
</table>