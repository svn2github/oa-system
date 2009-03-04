<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<c:set var="x_expense_expenseType_TRAVEL" value="<%=com.aof.model.metadata.ExpenseType.TRAVEL%>"/>
<script>
	var allowChangeRequestAmount = true;
	
	function validateForm(form)
	{
		if (!validateExpenseForm(form)) {
			return false;
		}
		
		return true;
	}
	
	function validateExpenseForm(form)
	{
		if (trim(form["title"].value).length==0) {
			alertRequired('<bean:message key="expense.title"/>');
			form["title"].focus();
			return false;
		}
		if (trim(form["expenseCurrency_code"].value).length==0) {
			alertRequired('<bean:message key="expense.expenseCurrency"/>');
			form["expenseCurrency_code"].focus();
			return false;
		}
		form["exchangeRate"].value=trim(form["exchangeRate"].value);
		if (trim(form["exchangeRate"].value).length==0) {
			alertRequired('<bean:message key="expense.exchangeRate"/>');
			form["exchangeRate"].focus();
			return false;
		}
		if (!validateFormFloat(form["exchangeRate"].value)) {
			alertFloat('<bean:message key="expense.exchangeRate"/>');
			form["exchangeRate"].focus();
			return false;
		}
		
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
		if (form["draft"].value=="false" && totalExpenseAmount<=0) {
			alert('<bean:message key="expense.error.zeroAmount"/>');
			return false;
		}
		if (!allowChangeRequestAmount && Math.round(totalExpenseAmount * 100) != Math.round(${x_expense.amount} * 100)) {
			alert('<bean:message key="expense.error.amountCannotChange"/>' + ${x_expense.amount});
			return false;
		}
		return true;
	}
	
	function addAttachment() {
		var url="newExpenseAttachment${x_postfix}.do?expense_id=${x_expense.id}";
		var v=dialogAction(url,'expenseAttachment.new.title',400,150);
		if (v) {
			var table = document.all('attachTable');
			appendRow(table, v);
		    applyRowStyle(table);
		};
	}
	
	function removeAttachment(id) {
	
		var v = window.showModalDialog(
		    'showDialog.do?title=expenseAttachment.delete.title&confirmOperationDialog.do?message=expenseAttachment.delete.confirm&deleteExpenseAttachment${x_postfix}.do?id=' + id, 
		    null, 'dialogWidth:300px;dialogHeight:143px;status:no;help:no;scroll:no');
		if (v) {
			table=document.all('ratt' + id).parentNode;
			deleteRow(document.all('ratt' + id));
			applyRowStyle(table);
		}
	}
	
	function changeExpenseCurrency(selectObj) {
		selectObj.form.exchangeRate.value=exchangeRateCurrency[selectObj.selectedIndex];
	}

	var exchangeRateCurrency=[];
	<logic:iterate id="ec" name="x_expenseCurrencyList">
		exchangeRateCurrency[exchangeRateCurrency.length]=${ec.exchangeRate};
	</logic:iterate>

</script>

<html:form action="/updateExpense${x_postfix}" onsubmit="return validateForm(this)">
	<html:hidden property="id" />
	<html:hidden property="department_id" />
	<html:hidden property="expenseCategory_id" />
	<html:hidden property="requestor_id" />
	<html:hidden property="requestDate" />

	<input type="hidden" name="draft" value="false"/>
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
			<td width="80%" colspan="3">
				${x_expense.department.site.name}
			</td>
		</tr>
		<tr>
			<td class="bluetext" width="20%"><bean:message key="expense.department" />:</td>
			<td width="80%" colspan="3">
				${x_expense.department.name}
			</td>
		</tr>	
		<tr>
			<td class="bluetext" width="20%"><bean:message key="expense.requestor.id" />:</td>
			<td width="30%">
				${x_expense.requestor.name}
			</td>
			<td class="bluetext" width="20%"><bean:message key="expense.creatDate" />:</td>
			<td width="30%">
				<fmt:formatDate value="${x_expense.createDate}" pattern="yyyy/MM/dd"/>
			</td>
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
		<td width="80%" colspan="3"><html:text property="title" size="60"/><span class="required">*</span></td>
	</tr>
	<tr>
		<td class="bluetext" width="20%"><bean:message key="expense.description" />:</td>
		<td width="80%" colspan="3"><html:textarea property="description"  cols="60" rows="3"/></td>
	</tr>
	<tr>
		<td class="bluetext" width="20%"><bean:message key="expense.expenseCategory" />:</td>
		<td width="30%">
			${x_expense.expenseCategory.description}
		</td>
		<c:if test="${x_expense.expenseCategory.type==x_expense_expenseType_TRAVEL}">
		<td class="bluetext" width="20%"><bean:message key="expense.travelApplication.id" />:</td>
		<td width="30%">
			${x_expense.travelApplication.id}
		</td>
		</c:if>
		<c:if test="${x_expense.expenseCategory.type!=x_expense_expenseType_TRAVEL}">
		<td></td><td></td>
		</c:if>
	</tr>
		<tr>
			<td class="bluetext" width="20%">
				<bean:message key="expense.requestdate" />
				:
			</td>
			<td width="30%">
				<fmt:formatDate value="${x_expense.requestDate}" pattern="yyyy/MM/dd" />
			</td>
			<td class="bluetext" width="20%">
				<bean:message key="expense.budget" />
				:
			</td>
			<td width="30%">
				${x_expense.yearlyBudget.code}
			</td>
		</tr>
		<c:if test="${x_canViewExpenseBudgetAmount == true}">
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
		</c:if>
		<tr>

			<td class="bluetext" width="20%">
				<bean:message key="expense.baseCurrency" />
				:
			</td>
			<td width="30%">
				${x_expense.department.site.baseCurrency.name}
			</td>
			<td class="bluetext" width="20%">
				<bean:message key="expense.status" />
				:
			</td>
			<td width="30%" colspan="3">
				<span style="color: ${x_expense.status.color}"> <c:if
						test="${sessionScope.LOGIN_USER.locale=='en'}">${x_expense.status.engShortDescription}</c:if>
					<c:if test="${sessionScope.LOGIN_USER.locale!='en'}">${x_expense.status.chnShortDescription}</c:if>
				</span>
			</td>
		</tr>
		<tr>
		<td class="bluetext" width="20%"><bean:message key="expense.amount" />:</td>
		<td width="30%">
			<div id="baseAmount">
			<fmt:formatNumber value="${x_expense.baseAmount}" maxFractionDigits="2" minFractionDigits="2"/>
			</div>
		</td>
	</tr>			
	</table>
	<hr align="left" width="100%">
	<table width="100%">
		<tr>
			<td align="left" width='10%' class='bluetext'><font color="blue">
			<h3><bean:message key="expenseAttachment.list.title" /></h3>
			</font></td>
	</table>
	<table width=100%>
		<tr>
			<td><input type="button"
				value="<bean:message key="expenseAttachment.new.title"/>"
				onclick="addAttachment()"></td>
		</tr>
	</table>
	<table class="data" width="100%">
		<thead>
			<tr bgcolor="#9999ff">
				<th width="40%">
				<div align="center"><bean:message key="expenseAttachment.title" /></div>
				</th>
				<th width="40%">
				<div align="center"><bean:message key="expenseAttachment.fileName" /></div>
				</th>
				<th width="15%">
				<div align="center"><bean:message key="expenseAttachment.uploadDate" /></div>
				</th>
				<th width="5%">
				</th>
			</tr>
		<thead>
		<tbody id="attachTable">
			<logic:iterate id="X_OBJECT" name="x_attachmentList">
				<bean:define id="X_OBJECT" toScope="request" name="X_OBJECT" />
				<jsp:include page="../expenseAttachment/row.jsp" />
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
		var rechargeFormName = "expenseForm";
  	</script>  	
	<jsp:include page="../recharge/edit.jsp"/>	 
	<hr align="left" width="100%">
</html:form>
<div id="oldApprovers"><jsp:include page="../approve/list.jsp"/></div>
<div id="newApprovers"></div>
<iframe id="viewapprover" name="viewapprover" src="" frameborder="0" height="200" width="570" style="display:none"></iframe>
<script type="text/javascript">
	totalAmountChanged(totalExpenseAmount);

	var exchangeRateCurrecny=[];
	<logic:iterate id="ec" name="x_expenseCurrencyList">
		exchangeRateCurrency[exchangeRateCurrency.length]=${ec.exchangeRate};
	</logic:iterate>
    applyRowStyle(document.all('attachTable'));
</script>
