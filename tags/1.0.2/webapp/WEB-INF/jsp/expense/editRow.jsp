<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/fn.tld" prefix="fn" %>
<script type="text/javascript">
<!--
	
	var totalExpenseAmount = 0;
	var t_totalExpenseAmount=0;
	<logic:iterate id="expenseSubCategory" name="x_subCategoryList">
		var totalExpense_${expenseSubCategory.id}_Amount = 0;
    </logic:iterate>

	function insertExpenseRow(table,rowPos, e_date, <logic:iterate id="sc" name="x_subCategoryList">e_sc${sc.id},e_description${sc.id},</logic:iterate>projectCodeId, caculateTotal) {
	//function insertExpenseRow(table,rowPos,caculateTotal) {

		var row = table.insertRow(rowPos);

		var cell = row.insertCell();		
		cell.innerHTML = "<table width=100% border=\"0\" cellspacing=\"0\" cellpadding=\"0\"><tr><td><input type=\"text\" name=\"expense_date\" id=\"expdateID"+rowPos+"\" size=\"6\" maxlength=\"10\"/><a onclick=\"event.cancelBubble=true;\" href=\"javascript:showCalendar('dimg"+rowPos+"',false,'expdateID"+rowPos+"',null,null,'expenseForm')\"><IMG align=\"absMiddle\" border=\"0\" id=\"dimg"+rowPos+"\" src=\"images/datebtn.gif\" ></A><span id=\"span_expense_date\" /></td></tr></table>";

		<logic:iterate id="sc" name="x_subCategoryList">
			cell = row.insertCell();
			cell.align = "right";
			cell.innerHTML = '<input type="text"  size="7" name="expense_${sc.id}" style="text-align:right" onblur="this.value=this.oldValue" onkeyup="calculateExpenseAmount(this,false,\'totalExpense_${sc.id}_Amount\')"/>';
			cell = row.insertCell();
			cell.align = "right";
			cell.innerHTML = '<input type="text"  size="20" name="description_${sc.id}" style="text-align:left" />';
		</logic:iterate>
		
		cell = row.insertCell();
		cell.align = "center";
		cell.width = "1px";		
		cell.innerHTML = '<select name="projectCode">' + 
						 '<option value="" selected></option>' +
						<logic:iterate id="projectCode" name="x_projectCodeList">
							'<option value="${projectCode.id}"'  + '>${projectCode.code}</option>' +
    					</logic:iterate>
						 '</select>';
		
		cell = row.insertCell();
		cell.align = "right";
		cell.innerHTML = '<input type="hidden" name="expense_row_total"/><span id="span_expense_row_total"/>';
		
		cell = row.insertCell();
		cell.align = "center";
		cell.innerHTML = '<a href="javascript:void(0)" onclick="deleteExpenseRow(this.parentNode.parentNode);"><bean:message key="all.delete"/></a>';

		//row.all('span_expense_date').innerText = e_date
		row.all('expense_date').value =  e_date;
		var amount=0;
		var eValue=0;
		<logic:iterate id="sc" name="x_subCategoryList">
			eValue=parseFloat(e_sc${sc.id});
			eValue=Math.round(eValue*100)/100;
			if (eValue==0){
				row.all('expense_${sc.id}').value = '';
			}else{
				row.all('expense_${sc.id}').value = eValue;
			}
			row.all('expense_${sc.id}').oldValue = eValue;
			eDescription=e_description${sc.id};
			row.all('description_${sc.id}').value = eDescription;
			amount += eValue;
			eval('totalExpense_${sc.id}_Amount+= eValue' );
			eval('totalExpense_${sc.id}_Amount= Math.round(totalExpense_${sc.id}_Amount*100)/100');
		</logic:iterate>
		row.all('expense_row_total').value =  Math.round(amount*100)/100;
		row.all('span_expense_row_total').innerText =  Math.round(amount*100)/100;
				
		if (projectCodeId != "") {
			var optionIndex = 1;
		<logic:iterate id="projectCode" name="x_projectCodeList">			
			if ("${projectCode.id}" == projectCodeId) {
				row.all('projectCode').selectedIndex = optionIndex;				
			} 		
			optionIndex++;
    	</logic:iterate>
    	}
		
		
		totalExpenseAmount +=amount;
		if (caculateTotal) {
			totalAmountChanged(totalExpenseAmount);
		}			
	}

	function deleteExpenseRow(row) {
		var table = row.parentNode;
		var amount=0;
		<logic:iterate id="sc" name="x_subCategoryList">
			eValue=row.all('expense_${sc.id}').oldValue;
			eValue=Math.round(eValue*100)/100;
			amount += eValue;
			eval('totalExpense_${sc.id}_Amount-= eValue' );
			eval('totalExpense_${sc.id}_Amount= Math.round(totalExpense_${sc.id}_Amount*100)/100');
		</logic:iterate>
		totalExpenseAmount -= amount;
		totalAmountChanged(totalExpenseAmount);
		table.deleteRow(row.sectionRowIndex);
		applyRowStyle(table);
	}
	
	function calculateExpenseAmount(expenseInput , isTemplate , totVarName ) {
		var row=expenseInput.parentNode.parentNode;
		var amount=Math.round(parseFloat(expenseInput.value)*100)/100;
		if (isNaN(amount)) amount = 0;
		
		if (!isTemplate) {
			totalExpenseAmount -= expenseInput.oldValue - amount;
			totalExpenseAmount=Math.round(totalExpenseAmount*100)/100;
			var row_total=parseFloat(row.all('expense_row_total').value);
			row_total -= expenseInput.oldValue - amount;
			row_total = Math.round(row_total*100)/100;
			row.all.expense_row_total.value=row_total;
			row.all.span_expense_row_total.innerText=row.all.expense_row_total.value;
			eval(totVarName+'-=expenseInput.oldValue - amount' );
			eval(totVarName+'= Math.round('+totVarName+'*100)/100');
			totalAmountChanged(totalExpenseAmount);
		} else {
			t_totalExpenseAmount-= expenseInput.oldValue -amount;
			t_totalExpenseAmount=Math.round(t_totalExpenseAmount*100)/100;
		}
		
		expenseInput.oldValue=amount;
		
	}
	
	

	function addNewExpenseRowClicked() {
		var table = document.all('expense_datatable');
		var edate = "";
		//var edate= document.getElementById('t_expense_date').value;
		/*
		if (trim(edate).length==0) {
			alertRequired('<bean:message key="expense.row.error.date"/>');
			document.getElementById('t_expense_date').focus();
			return;
		}
		if (!checkCenturyDate(edate)) {
			alertDate('<bean:message key="expense.row.error.date"/>');
			document.getElementById('t_expense_date').focus();
			return;
		}
		*/
		//var rows=table.rows;
		var rowPos=table.rows.length;
		/*
		for (var i=0;i<rows.length;i++) {
			row=table.rows[i];
			if(edate==row.all.expense_date.value) {
				//alert('<bean:message key="expense.row.error.date.duplicate"/>');
				//document.getElementById('t_expense_date').focus();
				//return;
				rowPos=i;
				break;
			}
			if(edate<row.all.expense_date.value) {
				rowPos=i;
				break;
			} 
		}
		*/
		<logic:iterate id="expenseSubCategory" name="x_subCategoryList">
	    	var e_${expenseSubCategory.id}=document.getElementById('t_expense_${expenseSubCategory.id}').value;
	    </logic:iterate>
		insertExpenseRow(table, rowPos , edate,
		 <logic:iterate id="expenseSubCategory" name="x_subCategoryList">
	    	e_${expenseSubCategory.id},'',
	     </logic:iterate>'' ,true);
	     //insertExpenseRow(table, rowPos , true);
		applyRowStyle(table);
	}
	
	
	function checkExpenseListForRecharge() {
		// always return true, enable recharge when multiple category in expense -- nicebean 07/01/17
		/*
		var firstValue=false;
		<logic:iterate id="expenseSubCategory" name="x_subCategoryList">
			if (firstValue==false) {
				if (totalExpense_${expenseSubCategory.id}_Amount!=0) {
					firstValue=true;
				}
			} else {
				if (totalExpense_${expenseSubCategory.id}_Amount!=0) {
					alert('<bean:message key="expense.error.rechargeOneCategory"/>');
					return false;
				}
			}
    	</logic:iterate>
    	*/
		return true;
	}
	
	function checkExpenseList() {
		var expenseDates = document.getElementsByName("expense_date");
		for (var i = 0; i < expenseDates.length; i++) {
		
			if (trim(expenseDates[i].value).length==0) {
				alertRequired('<bean:message key="expense.row.error.date"/>');
				expenseDates[i].focus();
				return false;
			}
			if (!checkCenturyDate(expenseDates[i].value)) {
				alertDate('<bean:message key="expense.row.error.date"/>');
				expenseDates[i].focus();
				return false;
			}
		}
		return true;
	}
	
	
//-->
</script>
<table width="470px">
		<tr>
			<td class='bluetext'><bean:message key="expense.expenseCurrency" />:</td>
			<td><html:select property="expenseCurrency_code" onchange="changeExpenseCurrency(this)" >
					<html:options collection="x_expenseCurrencyList" property="currency.code"
					labelProperty="currency.name" />
				</html:select></td>
			<td class='bluetext'><bean:message key="expense.exchangeRate" />:</td>
			<td><html:text property="exchangeRate" size="6"/></td>

		<td>
			<input type="button" value="<bean:message key="expense.button.addexpenserow"/>" onclick="addNewExpenseRowClicked()"/>
		</td>
				</tr>
	</table>
<table class="data" width="100%">
<thead>
  <tr class="new_bg">
    <th width="100px" rowspan="2"><bean:message key="expense.row.date"/></th>
    <logic:iterate id="expenseSubCategory" name="x_subCategoryList">
    	<th colspan="2" align="center">${expenseSubCategory.description}</th>
    </logic:iterate>
    <th width="50px" rowspan="2"><bean:message key="expense.row.projectCode"/></th>
    <th width="50px" rowspan="2"><bean:message key="expense.row.subTotal"/></th>  
    <th width="40px" rowspan="2"></th>
  </tr>
  <tr class="new_bg">
  	<logic:iterate id="expenseSubCategory" name="x_subCategoryList">
  	<th width="80px"><bean:message key="expense.row.amount"/></th>
    <th><bean:message key="expense.row.description"/></th>
    </logic:iterate>
  </tr>
  <logic:present name="x_expenseLimitRow">
  <c:if test="${x_expense.requestor.id!=sessionScope.LOGIN_USER.id}">
  <tr bgcolor="#005496">
  	<td style="color:white;font-weight:bold"><bean:message key="expense.finalConfirm.limit"/></td>
  	<logic:iterate id="expenseCell" name="x_expenseLimitRow" property="expenseCellList">
  		<td style="color:white;font-weight:bold" align="right">
  		<c:if test="${expenseCell.amount!=0}">
  		${expenseCell.amount}
  		</c:if>
  		</td>
  		<td></td>
  	</logic:iterate>
  	<td/>
  	<td/>  	  	
  </tr>	
  </c:if>
  </logic:present>
  <tr style="display:none">
    <td>
		<table border="0" cellpadding="0" cellspacing="0">    	
		<tr>
			<td>
				<input type="text" name="t_expense_date" size="6"  maxlength="10"/>
			</td>
			<td>
				<a onclick="event.cancelBubble=true;" href="javascript:showCalendar('dimg',false,'t_expense_date',null,null,'expenseForm')"><IMG align="absMiddle" border="0" id="dimg" src="images/datebtn.gif" ></A>
			</td>
		</tr>
		</table>
    </td>
    <logic:iterate id="expenseSubCategory" name="x_subCategoryList">
    	<td align="right"><input type="text" size="7" name="t_expense_${expenseSubCategory.id}" style="text-align:right" value="0" onblur="this.value=this.oldValue" onkeyup="calculateExpenseAmount(this, true)"/></td>
    </logic:iterate>
    <td align="right"><span id="t_span_expense_row_total"></span></td>
    <td align="right"><input type="button" value="<bean:message key="all.new"/>" onclick="addNewExpenseRowClicked()"/></td>
  </tr>
</thead>
<tbody id="expense_datatable">
</tbody>

<tfoot>
	<tr>
		<td id="hrSize" colspan="20"><hr></td>
	</tr>
  <tr>
    <td align="right"><strong><bean:message key="expense.row.total"/></strong></td>
    <logic:iterate id="expenseSubCategory" name="x_subCategoryList">
    	<td align="right"><span id="span_expense_${expenseSubCategory.id}_total"></span></td><td></td>
    </logic:iterate>
    <td></td>
    <td align="right"><span id="span_expense_total_amount"></span></td>
    <td></td>
  </tr>
  <logic:present name="finalConfirm">
  <tr bgcolor="#f1f1f1" class="bb" style="display:none">
	<jsp:include page="finalConfirmRow.jsp"/>
  </tr> 	
  </logic:present>
  <logic:present name="x_expenseClaimRow">
  <tr bgcolor="#f1f1f1" class="bb">
	<td class='bluetext' width="150" align="right"><bean:message key="expense.finalConfirm.confirmedDescription" /></td>
	<logic:iterate id="expenseCell" name="x_expenseClaimRow" property="expenseCellList">
    	<td colspan="2" align="right">${expenseCell.description}</td>
    </logic:iterate>
    <td class='bluetext' align="right" colspan="2"></td>    
  </tr> 
  </logic:present>
</tfoot>
</table>
<script type="text/javascript">
	var subcategoryCount = 0;
	var table = document.all('expense_datatable');
	var initRow = 0;
	<logic:iterate id="expenseRow" name="x_expenseRowList">
		insertExpenseRow(table,initRow++,'<bean:write name="expenseRow" property="date" format="yyyy/MM/dd"/>',
			<logic:iterate id="expenseCell" name="expenseRow" property="expenseCellList">
				${expenseCell.amount},"${fn:replace(expenseCell.description, '"', '\\"')}",
			</logic:iterate>"${expenseRow.projectCode.id}",false);
	</logic:iterate>
	applyRowStyle(table);
	document.getElementById('span_expense_total_amount').setExpression("innerText", "totalExpenseAmount");
	document.getElementById('t_span_expense_row_total').setExpression("innerText", "t_totalExpenseAmount");
	<logic:iterate id="expenseSubCategory" name="x_subCategoryList">
		subcategoryCount+=2;
		if (totalExpense_${expenseSubCategory.id}_Amount!=0) {
			document.getElementById('span_expense_${expenseSubCategory.id}_total').setExpression("innerText", "totalExpense_${expenseSubCategory.id}_Amount");
		}
    	document.getElementById('t_expense_${expenseSubCategory.id}').oldValue = 0;
    </logic:iterate>
    document.getElementById("hrSize").colSpan = subcategoryCount + 4;
</script>