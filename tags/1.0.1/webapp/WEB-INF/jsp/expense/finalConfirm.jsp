<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<c:set var="x_expense_expenseType_TRAVEL" value="<%=com.aof.model.metadata.ExpenseType.TRAVEL%>"/>
<script>
	function validateForm(form) {
		if (!validateExpenseForm(form)) {
			return false;
		}
		
		if (!validateFinalConfirmDescritions(form)) {
			return false;
		}
		
		return true;
	}
	
	function validateExpenseForm(form) {
		if(form["isRecharge"].value=='<%=com.aof.model.metadata.YesNo.YES%>') {
			if(!checkExpenseListForRecharge() ) {
				return false;
			}
		}
		//check expense list
		if(!checkExpenseList() )
		{
			return false;
		}
		<c:if test="${x_canRecharge}">
		//check recharge list
		if (!checkRecharge()) {
			return false;
		}
		</c:if>
		if (Math.round(totalExpenseAmount * 100) != Math.round(${x_expense.amount} * 100)) {
			alert('<bean:message key="expense.error.amountCannotChange"/>' + ${x_expense.amount});
			return false;
		}
		return true;
	}

	function confirmMe() {
		if(validateForm(document.expenseFinalConfirmForm)) {
			if(confirm("<bean:message key="expense.finalConfirm.confirm"/>")) {
				with(document.expenseFinalConfirmForm) {
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
			with(document.expenseFinalConfirmForm) {
				isReject.value="true";
				rejectComment.value=v[0];
				submit();
			}
		};

	}
</script>

<html:form action="/finalConfirmExpense_result" onsubmit="return validateForm(this)" style="padding:0;margin:0">
	<input type="hidden" value="${x_expense.id}" name="id" />
	<input type="hidden" value="" name="isReject" />
	<input type="hidden" value="" name="rejectComment" />

	<table width="100%">
		<tr>
			<td >
				<html:errors/>
			</td>
		</tr>
		<tr>
			<td>
				<div class="message">
					<html:messages id="x_message" message="true">
						${x_message}<br>
					</html:messages>
				</div>
			</td>
		</tr>
	</table>
	<table width="100%">
		<tr>
			<td class="bluetext" width="20%"><bean:message key="expense.site" />:</td>
			<td width="80%" colspan="3">${x_expense.department.site.name}</td>
		</tr>
		<tr>
			<td class="bluetext" width="20%"><bean:message key="expense.department" />:</td>
			<td width="80%" colspan="3">${x_expense.department.name}</td>
		</tr>	
		<tr>
			<td class="bluetext" width="20%"><bean:message key="expense.requestor.id" />:</td>
			<td width="30%">${x_expense.requestor.name}</td>
			<td class="bluetext" width="20%"><bean:message key="expense.creatDate" />:</td>
			<td width="30%"><fmt:formatDate value="${x_expense.createDate}" pattern="yyyy/MM/dd"/></td>
		</tr>	
	</table>
	<hr width="100%">
	<table width=100%>
	<tr>
		<td class="bluetext" width="20%"><bean:message key="expense.id" />:</td>
		<td width="80%" colspan="3"><font color="blue"><b>${x_expense.id}</b></font></td>
	</tr>
	<tr>
		<td class="bluetext" width="20%"><bean:message key="expense.title" />:</td>
		<td width="80%" colspan="3">${x_expense.title}</td>
	</tr>
	<tr>
		<td class="bluetext" width="20%"><bean:message key="expense.description" />:</td>
		<td width="80%" colspan="3">${x_expense.description}</td>
	</tr>
	<tr>
		<td class="bluetext" width="20%"><bean:message key="expense.expenseCategory" />:</td>
		<td width="30%">${x_expense.expenseCategory.description}</td>
		<c:choose>
			<c:when test="${x_expense.expenseCategory.type==x_expense_expenseType_TRAVEL}">
		<td class="bluetext" width="20%"><bean:message key="expense.travelApplication.id" />:</td>
		<td width="30%">${x_expense.travelApplication.id}</td>
			</c:when>
			<c:otherwise>
		<td></td><td></td>
			</c:otherwise>
		</c:choose>
	</tr>
	<tr>
			<td class="bluetext" width="20%">
				<bean:message key="expense.requestdate" />
				:
			</td>
			<td width="30%">
				<fmt:formatDate value="${x_expense.requestDate}"
					pattern="yyyy/MM/dd" />
			</td>
			<td class="bluetext" width="20%">
				<bean:message key="expense.budget" />
				:
			</td>
			<td width="30%">
				${x_expense.yearlyBudget.code}
			</td>
		</tr>
		<tr>
			<td class='bluetext'>
				<bean:message key="expense.yearlyBudget.amount" />
				:
			</td>
			<td>
				${x_expense.yearlyBudget.amount}
			</td>
			<td class='bluetext'>
				<bean:message key="expense.yearlyBudget.remainAmount" />
				:
			</td>
			<td>
				<span <c:if test="${x_expense.yearlyBudget.remainAmount < 0}">style="color:red"</c:if>>				
				${x_expense.yearlyBudget.remainAmount}
				</span>
			</td>
		</tr>

	<tr>		
		<td class="bluetext" width="20%"><bean:message key="expense.baseCurrency" />:</td>
		<td width="30%">${x_expense.department.site.baseCurrency.name}</td>
		<td class="bluetext" width="20%"><bean:message key="expense.status" />:</td>
		<td width="30%" colspan="3">
			<span style="color:${x_expense.status.color}"><bean:write name="x_expense" property="status.${x_lang}ShortDescription"/></span> 
		</td>
	</tr>			
	<tr>
		<td class="bluetext" width="20%"><bean:message key="expense.amount" />:</td>
		<td width="30%">
			<div id="baseAmount"><fmt:formatNumber value="${x_expense.baseAmount}" maxFractionDigits="2" minFractionDigits="2"/></div>
		</td>
		
	</tr>			
	</table>
	<hr align="left" width="100%">
	<table width="100%">
		<tr>
			<td align="left" width='10%' class='bluetext'><font color="blue"><h3><bean:message key="expenseAttachment.list.title" /></h3></font></td>
		</tr>
	</table>
	<table class="data" width="100%">
		<thead>
			<tr bgcolor="#9999ff">
				<th width="40%" align="center"><bean:message key="expenseAttachment.title" /></th>
				<th width="40%" align="center"><bean:message key="expenseAttachment.fileName" /></th>
				<th width="15%" align="center"><bean:message key="expenseAttachment.uploadDate" /></th>
				<th width="5%">
				</th>
			</tr>
		<thead>
		<tbody id="attachTable">
			<logic:iterate id="X_OBJECT" name="x_attachmentList">
				<bean:define id="X_OBJECT" toScope="request" name="X_OBJECT" />
				<jsp:include page="../expenseAttachment/viewRow.jsp" />
			</logic:iterate>
		</tbody>
	</table>
	<hr align="left" width="100%">
	<table width="100%"  border=0 cellpadding=0 cellspacing=0>
		<tr>
			<td align="left" width='10%' class='bluetext'><font color="blue">
			<h3><bean:message key="expense.row.title" /></h3>
			</font></td>
	</table>
	<jsp:include page="editRow.jsp"/>
	<script language="javascript">
		var rechargeFormName = "expenseFinalConfirmForm";
  	</script>
	<jsp:include page="../recharge/edit.jsp"/>
	<hr align="left" width="100%">
	<jsp:include page="../approve/list.jsp"/>
	<table align="right">
		<tr>
			<td><input type="button" value="<bean:message key="all.confirm" />" onclick="confirmMe()"/></td>
			<td><input type="button" value="<bean:message key="expense.finalConfirm.reject"/>" onclick="rejectMe()" /></td>
		</tr>
	</table>
</html:form>
<script type="text/javascript">
	totalAmountChanged(totalExpenseAmount);

	var exchangeRateCurrency=[];
	<logic:iterate id="ec" name="x_expenseCurrencyList">
		exchangeRateCurrency[exchangeRateCurrency.length]=${ec.exchangeRate};
	</logic:iterate>
    applyRowStyle(document.all('attachTable'));
</script>
	


