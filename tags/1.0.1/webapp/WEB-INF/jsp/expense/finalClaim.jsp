<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<html:form action="/finalClaimExpense_result" onsubmit="return validateForm(this)" style="padding:0;margin:0">

<input type="hidden" value="${x_expense.id}" name="id" />
<input type="hidden" value="" name="isReject" />
<input type="hidden" value="" name="rejectComment" />

<jsp:include page="baseView.jsp"/>

<jsp:include page="../recharge/view.jsp"/>

<hr align="left" width="100%">

<jsp:include page="../approve/list.jsp" />

<script type="text/javascript">
<c:if test="${x_recharged}">
	<logic:notPresent name="x_expenseClaimRow">
		setTotalAmount(${x_expenseSumRow.rowSubTotal});
	</logic:notPresent>
	<logic:present name="x_expenseClaimRow">
		setTotalAmount(${x_expenseClaimRow.rowSubTotal});
	</logic:present>
</c:if>

	function confirmMe() {
		if(validateForm(document.expenseFinalClaimForm)) {
			if(confirm("<bean:message key="expense.finalClaim.confirm"/>")) {
				with(document.expenseFinalClaimForm) {
					submit();
				}
			}
		}
	}

	function rejectMe()
	{
	
		v = window.showModalDialog(
			"showDialog.do?title=approveRequest.reject&approveRequestAddComment.do", 
			null, 'dialogWidth:450px;dialogHeight:250px;status:no;help:no;scroll:no');
		if (v) {
			with(document.expenseFinalClaimForm) {
				isReject.value="true";
				rejectComment.value=v[0];
				submit();
			}
		};

	}

	function validateForm(form)	{
		return true;
	}
</script>

<table align="right">
	<tr>
		<td><input type="button" value="<bean:message key="all.confirm" />" onclick="confirmMe()"/></td>
		<td><input type="button" value="<bean:message key="expense.finalClaim.reject"/>" onclick="rejectMe()" /></td>
	</tr>
</table>
	
</html:form>	
	


