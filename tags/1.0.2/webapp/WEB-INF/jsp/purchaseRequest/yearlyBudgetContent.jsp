<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<table>
	<tr>
		<td  class='bluetext'>
			<bean:message key="purchaseRequest.yealyBudget.id"/> : 
		</td>
		<td >
			<input type="hidden" value="${x_yearlyBudget.id}" name="yearlyBudget_id"/>
			<input type="hidden" value="${x_yearlyBudgetDepartmentIds}" name="yearlyBudget_departmentIds"/>
			<input type="hidden" value="${x_yearlyBudget.purchaseCategory.id}" name="yearlyBudget_purchaseCategory_id"/>
			<input type="hidden" value="${x_yearlyBudget.purchaseSubCategory.id}" name="yearlyBudget_purchaseSubCategory_id"/>
			<input type="hidden" value="${x_yearlyBudget.site.id}" name="yearlyBudget_site_id"/>			
			
			<span id="yearlyBudget_code_span">${x_yearlyBudget.code}</span>
		</td>
		<td width="23%" class='bluetext'>
			<bean:message key="purchaseRequest.yealyBudget.description"/>: 
		</td>
		<td width="14%" class='bluetext'>
			<span id="yearlyBudget_name_span">${x_yearlyBudget.name}</span>
		</td>
	</tr>
	<c:if test="${x_canViewYearlyBudgetAmount}">
	<tr>
		<td class='bluetext'>
			<bean:message key="purchaseRequest.yearlyBudget.amount"/>: 
		</td>
		<td>
			${x_yearlyBudget.amount}
		</td>
		<td class='bluetext'>
			<bean:message key="purchaseRequest.yearlyBudget.actualAmount"/>: 
		</td>
		<td>
			${x_yearlyBudget.actualAmount}
		</td>
	</tr>
	</c:if>
</table>
