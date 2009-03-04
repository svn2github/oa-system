<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>
<script type="text/javascript">
<!--
	function validateForm(form) {
		if (!validateAirTicketForm(form)) {
			return false;
		}
		<c:if test="${x_canRecharge}">
		//check recharge list
		if (!checkRecharge()) {
			return false;
		}
		</c:if>
		return true;
	}
//-->
</script>
<html:javascript formName="airTicketForm" staticJavascript="false" />
<html:form action="/updateAirTicket" onsubmit="return validateForm(this);">
	<html:hidden property="id" />
	<html:hidden property="airTicket_id" />
	<table width="100%">
		<tr>
			<td width="15%" class="bluetext"><bean:message key="airTicket.exchangeRate.id"/>:</td>
			<td width="35%">${x_poi.exchangeRate.currency.name}</td>
			<td width="15%"  class="bluetext"><bean:message key="purchaseOrderItem.unitPrice"/></td>
			<td width="35%">${x_poi.unitPrice}</td>
		</tr>
	</table> 
	<script language="javascript">
		var rechargeFormName = "airTicketForm";
  	</script>
	<jsp:include page="../recharge/edit.jsp"/>
	<table width="100%">
		<tr>
			<td align="right"><html:submit><bean:message key="all.save"/></html:submit></td>
		</tr>
	</table>
</html:form>
<script type="text/javascript">
<!--
	totalAmountChanged(${x_poi.unitPrice});
//-->
</script>
