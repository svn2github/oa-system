<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<c:if test="${x_canWithDraw}">
<script type="text/javascript">
	function doWithdraw() {
		if (confirm('<bean:message key="expense.withdraw.confirm"/>')) {
			window.location.href='withdrawExpense${x_postfix}.do?id=${x_expense.id}';
		}	
	}
</script>
</c:if>
<table width="100%" border="0" cellpadding="4" cellspacing="0">
<tr>
	<td>
		<jsp:include page="baseView.jsp"/>
	</td>
</tr>
</table>
<table width="100%" border="0" cellpadding="4" cellspacing="0">
	<tr>
		<td><jsp:include page="../recharge/view.jsp"/></td>
	</tr>
</table>
<hr align="left" width="100%">
<table width="100%" border="0" cellpadding="4" cellspacing="0">
	<tr>
		<td><jsp:include page="../approve/list.jsp" /></td>
	</tr>
</table>
<table width="100%" border="0" cellpadding="4" cellspacing="0">
<TR>
<TD ALIGN="RIGHT">
	<logic:present name="x_report">
		<input type="button" value="<bean:message key="all.back"/>"	onclick="window.location.href='reportExpense.do'">
	</logic:present>
	<logic:notPresent name="x_report">
		<c:if test="${x_canWithDraw}">
			<input type="button" value="<bean:message key="expense.withdraw"/>" onclick="doWithdraw();">
		</c:if>
		<c:if test="${x_pdf}">
		<input type="button" value="<bean:message key="all.pdf"/>"	onclick="window.location.href='exportExpenseDetailPDF${x_postfix}.do?id=${x_expense.id}'">
		</c:if>
		<input type="button" value="<bean:message key="all.back"/>"	onclick="window.location.href='listExpense${x_postfix}.do'">
	</logic:notPresent>
</TD>
</TR>
</table>
<script type="text/javascript">
<c:if test="${x_recharged}">
	<logic:notPresent name="x_expenseClaimRow">
		setTotalAmount(${x_expenseSumRow.rowSubTotal});
	</logic:notPresent>
	<logic:present name="x_expenseClaimRow">
		setTotalAmount(${x_expenseClaimRow.rowSubTotal});
	</logic:present>
</c:if>
</script>
