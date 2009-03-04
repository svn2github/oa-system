<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<script>
	function validateForm(form)
	{
		with(form)
		{
			price.value=trim(price.value);
			if(price.value=="")
			{
				price.focus();
				alertRequired("<bean:message key="travelApplication.airTicket.cancel.returnPrice"/>");
				return false;
			}
			if(!validateFormFloat(price.value))
			{
				price.focus();
				alert("<bean:message key="travelApplication.airTicket.cancel.returnPrice.notFloat"/>");
				return false;
			}
			if(parseFloat(price.value)>${x_at.price})
			{
				price.focus();
				alert("<bean:message key="travelApplication.airTicket.cancel.returnPriceMoreTranPrice"/>");
				return false;
			}
		}
		return true;
	}
</script>
<form action="cancelTravelApplicationAirTicket.do"
	onsubmit="return validateForm(this)">
	<input type="hidden" value="${x_at.id}" name="id"/>
	<table width=100% border=1 cellpadding=4 cellspacing=0 bgcolor=ffffff>
		<tr>
			<td bgcolor=f0f0f0 width=100% colspan=2 valign=top>
			<table width=100% cellpadding=0 cellspacing=0>
				<tr>
					<td bgcolor=f0f0f0 width=80% valign=top>
					<h3 class="formtitle"><bean:message
						key="travelApplication.airTicket.cancel.title" /></h3>
					</td>
					<td valign=top align="right"></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
	<br>
	<table width=100% border=0 cellpadding=4 cellspacing=0>
		<tr>
			<td class="bluetext" width="30%">
			<div><bean:message
				key="travelApplication.airTicket.cancel.returnPrice" />:</div>
			</td>
			<td width="70%"><input type="text" size="5" name="price"></td>
		</tr>
		<!--
		<tr>
			<td class="bluetext">
			<div><bean:message key="travelApplication.creator" />:</div>
			</td>
			<td>${x_at.travelApplication.creator.name}</td>
			<td class="bluetext">
			<div><bean:message key="travelApplication.createDate" />:</div>
			</td>
			<td><bean:write name="x_at" property="travelApplication.createDate"
				format="yyyy/MM/dd" /></td>
		</tr>
		-->
	</table>
	<hr align="left" width="100%">
	<table width="100%">
		<tr>
			<td width="90%" align="right">
				
			<input type="submit"
				value="<bean:message key="all.confirm"/>"
				> <input
				type="button" value="<bean:message key="all.cancel"/>"
				onclick="window.parent.close();"></td>
		</tr>
	</table>
</form>	