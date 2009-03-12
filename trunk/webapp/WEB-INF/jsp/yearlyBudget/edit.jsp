<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>
<%@ page import="java.util.Set"%>




<script>
	function viewHistory()
	{
		var url="listYearlyBudget_history.do?id=${x_yearlyBudget.id}";
		var v=dialogAction(url,"yearlyBudget.history.title",600,600);
	}
	
	function validateForm(form)
	{
		if(!validateYearlyBudgetEditForm(form)) return false;
		var objForm = document.forms[0];
	    var objLen = objForm.length;
		if(!hasCheckboxSelected(form, "departments"))
		{
			alert("<bean:message key="yearlyBudget.department.notChecked"/>");
			return false;
		}
		return true;
	}
	function backToList()
	{
		var url="listYearlyBudget.do";
		window.location.href=url;
	}
	
	
</script>
<html:javascript formName="yearlyBudgetEditForm" staticJavascript="false"/>
<html:form action="/updateYearlyBudget" onsubmit="return validateForm(this)">
	<html:hidden property="id" />
	<html:hidden property="rowVersion" />
	<table width="90%">
		<tr>
			<td>
				<html:errors />
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
	<table border=0 cellpadding=4 cellspacing=0>
		<tr>
			<td><bean:message key="yearlyBudget.site.id"/>:</td>
			<td>
				${x_site.name}
			</td>
		</tr>
		<tr>
			<td valign="top"><bean:message key="yearlyBudget.department.id"/>:</td>
			<td>
				<logic:iterate name="x_site" property="departments" id="x_department">
				<div>
<c:set var="x_department_id" value="${x_department.id}" scope="request"/>				 
					<input type="checkbox" name="departments" value="${x_department.id}" 
<%
	Set departmentIdSet=(Set)request.getAttribute("x_departmentIdSet");
	String departmentId=request.getAttribute("x_department_id").toString();
	if(departmentIdSet.contains(departmentId)) out.print("checked");
%>					
					 />
					${x_department.indentName}
				</div>
				</logic:iterate>
				<div>
					<input type="button" value="<bean:message key="all.selectall"/>" onclick="selectAll(this.form, 'departments');">
					<input type="button" value="<bean:message key="all.selectnone"/>" onclick="selectNone(this.form, 'departments');">
					<span class="required">*</span>
				</div>
			</td>
		</tr>
		<tr>
			<td><bean:message key="yearlyBudget.code"/>:</td>
			<td>${x_yearlyBudget.code}</td>
		</tr>
		<tr>
			<td><bean:message key="yearlyBudget.name"/>:</td>
			<td><html:text property="name" size="30"  maxlength="50"/><span class="required">*</span></td>
		</tr>
		<tr>
			<td><bean:message key="yearlyBudget.type"/>:</td>
			<td>
				<bean:write name="x_yearlyBudget" property="type.${x_lang}ShortDescription"/>
			</td>
		</tr>
		<c:set var="x_budgettype_expense" value="<%=net.sourceforge.model.metadata.BudgetType.Expense.getEnumCode()%>" scope="request" />
		<c:choose>
			<c:when
				test="${x_yearlyBudget.type.enumCode == x_budgettype_expense}">
				<c:set var="x_expensedisplay" value=""
					scope="request" />
				<c:set var="x_purchasedisplay" value="style='display:none'" scope="request" />
			</c:when>
			<c:otherwise>
				<c:set var="x_expensedisplay" value="style='display:none'" scope="request" />
				<c:set var="x_purchasedisplay" value=""
					scope="request" />
			</c:otherwise>
		</c:choose>
		<tr ${x_purchasedisplay}>
			<td><bean:message key="yearlyBudget.year"/>:</td>
			<td>
				${x_yearlyBudget.year}
			</td>
		</tr>
		<tr ${x_expensedisplay}>
			<td><bean:message key="yearlyBudget.durationFrom"/>:</td>
			<td>
				${x_yearlyBudget.durationFrom}
			</td>
		</tr>
		<tr ${x_expensedisplay}>
			<td><bean:message key="yearlyBudget.durationTo"/>:</td>
			<td>
				${x_yearlyBudget.durationTo}
			</td>
		</tr>
		<tr>
			<td><bean:message key="yearlyBudget.amount"/>:</td>
			<td><html:text property="amount" size="10"/><span class="required">*</span></td>
		</tr>
		<tr ${x_purchasedisplay}>
			<td><bean:message key="yearlyBudget.purchaseCategory.id"/>:</td>
			<td align="left">
				${x_yearlyBudget.purchaseCategory.description}
			</td>
		</tr>
		<tr ${x_purchasedisplay}>
			<td><bean:message key="yearlyBudget.purchaseSubCategory.id"/>:</td>
			<td align="left">
				${x_yearlyBudget.purchaseSubCategory.description}
			</td>
		</tr>
		<tr ${x_expensedisplay}>
			<td><bean:message key="yearlyBudget.expenseCategory.id"/>:</td>
			<td align="left">
				${x_yearlyBudget.expenseCategory.description}
			</td>
		</tr>
		<tr>
			<td><bean:message key="yearlyBudget.baseCurrency"/>:</td>
			<td>${x_site.baseCurrency.name}</td>
		</tr>
		<tr>
			<td><bean:message key="yearlyBudget.status"/>:</td>
			<td align="left">
				<html:select property="status">
					<html:options collection="x_statusList"
						property="enumCode" labelProperty="${x_lang}ShortDescription" />
				</html:select>
			</td>
		</tr>
	</table>
	<hr>
	<table width=90% border=0 cellpadding=4 cellspacing=0>
		<tr>
			<td align="right">
				<html:submit>
					<bean:message key="all.save" />
				</html:submit> 
				<input type="button" value="<bean:message key="all.viewHistory"/>"
					onclick="viewHistory()">
				<input type="button" value="<bean:message key="all.backToList"/>"
					onclick="backToList()">
			</td>
		</tr>
	</table>
</html:form>

