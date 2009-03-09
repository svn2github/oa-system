<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<script type="text/javascript">
<!--
	function validateForm(form) {
		if (!validatePaymentScheduleDetailForm(form)) {
			return false;
		}
	}
//-->
</script>
<html:javascript formName="paymentScheduleDetailForm" staticJavascript="false" />

<c:if test="${x_add}">
	<c:set var="x_action" value="insert"/>
</c:if>

<c:if test="${!x_add}">
	<c:set var="x_action" value="update"/>
</c:if>


<html:form action="/${x_action}PaymentScheduleDetail" onsubmit="return validateForm(this);">
<html:hidden property="id"/>
<html:hidden property="purchaseOrder_id"/>

<div>
	<html:errors/>
</div>
<table width="90%" border="0" cellpadding="4" cellspacing="0">
	<tr>
		<td class="bluetext"><bean:message key="paymentScheduleDetail.description" /></td>
		<td><html:text property="description"/><span class="required">*</span></td>
	</tr>
	<tr>
		<td class="bluetext"><bean:message key="paymentScheduleDetail.date" /></td>
		<td>
			<html:text property="date" size="6"/><span class="required">*</span>
			<a onclick="event.cancelBubble=true;"
			href="javascript:showCalendar('dimg3',false,'date',null,null,'paymentScheduleDetailForm')"><img
			align="absmiddle" border="0" id="dimg3" src="images/datebtn.gif" /></a>	
		</td>
	</tr>
	<tr>
		<td class="bluetext"><bean:message key="paymentScheduleDetail.amount" /></td>
		<td>
			<html:text property="amount"/><span class="required">*</span>
		</td>
	</tr>
</table>

<table width=90% border=0 cellpadding=4 cellspacing=0>
	<tr>
		<td align="right">
			<html:submit><bean:message key="all.save" /></html:submit>
		</td>
	</tr>
</table>
</html:form>
