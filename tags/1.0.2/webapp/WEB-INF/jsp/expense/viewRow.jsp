<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<table width="470px">
		<tr>
			<td class='bluetext'><bean:message key="expense.expenseCurrency" />:</td>
			<td>
				${x_expense.expenseCurrency.name}
			</td>
			<td class='bluetext'><bean:message key="expense.exchangeRate" />:</td>
			<td>
				<fmt:formatNumber value="${x_expense.exchangeRate}" maxFractionDigits="4" minFractionDigits="4"/>
			</td>
	</tr>
</table>
<table class="data" width="100%">
<thead>
  <tr class="new_bg">
    <th width="100px" rowspan="2"><bean:message key="expense.row.date"/></th>
    <logic:iterate id="expenseSubCategory" name="x_subCategoryList">
    	<th colspan="2">${expenseSubCategory.description}</th>
    </logic:iterate>
    <th width="50px" rowspan="2"><bean:message key="expense.row.projectCode"/></th>
    <th width="50px" rowspan="2"><bean:message key="expense.row.subTotal"/></th>
  </tr>
  <tr class="new_bg">
  	<logic:iterate id="expenseSubCategory" name="x_subCategoryList">
  	<th width="80px" ><bean:message key="expense.row.amount"/></th>
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
  </tr>	
  </c:if>
  </logic:present>
</thead>
<tbody id="expense_datatable">
	<logic:iterate id="expenseRow" name="x_expenseRowList">
	<tr>
		<td>
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td><bean:write name="expenseRow" property="date" format="yyyy/MM/dd"/></td>
			</tr>
			</table>
		</td>
		<logic:iterate id="expenseCell" name="expenseRow" property="expenseCellList">
			<td style="text-align:right">
				<c:if test='${(expenseCell.amount != "0.00") and (expenseCell.amount != "0")}'>
					${expenseCell.amount}
				</c:if>
			</td>

			<td style="text-align:left">${expenseCell.description}</td>
		</logic:iterate>
		<td style="text-align:right">${expenseRow.projectCode.code}</td>
		<td style="text-align:right">${expenseRow.rowSubTotal}</td>
	</tr>
	</logic:iterate>
</tbody>
<tfoot>
	<tr>
		<td id="hrSize" colspan="1"><hr></td>
	</tr>
  <tr>
   <td align="right"><strong><bean:message key="expense.row.total"/></strong></td>
    <logic:iterate id="expenseCell" name="x_expenseSumRow" property="expenseCellList">
    	<td align="right">
    	<c:if test='${(expenseCell.amount != "0.00") and (expenseCell.amount != "0")}'>
    	<b>
    	${expenseCell.amount}
    	</b>
    	</c:if>
		</td>
    	<td></td>
    </logic:iterate>
    <td></td>
    <td align="right"><b>${x_expenseSumRow.rowSubTotal}</b></td>
  </tr>
  <logic:present name="finalConfirm">
  <tr bgcolor="#f1f1f1" class="bb" style="display:none">
	<jsp:include page="finalConfirmRow.jsp"/>
  </tr> 	
  </logic:present>
  <logic:present name="x_expenseClaimRow">
  <tr bgcolor="#f1f1f1" class="bb"  style="display:none">
	<td class='bluetext' width="150" align="right"><bean:message key="expense.finalConfirm.confirmedDescription" /></td>
	<logic:iterate id="expenseCell" name="x_expenseClaimRow" property="expenseCellList">
    	<td colspan="2" align="right">${expenseCell.description}</td>
    </logic:iterate>
    <td align="right" colspan="2"></td>
  </tr> 
</logic:present>
</tfoot>
</table>

<script type="text/javascript">
	var table = document.all('expense_datatable');
	applyRowStyle(table);
	var subcategoryCount = 0;
	<logic:iterate id="expenseSubCategory" name="x_subCategoryList">
		subcategoryCount+=2;
    </logic:iterate>
	document.getElementById("hrSize").colSpan = subcategoryCount + 3;
</script>