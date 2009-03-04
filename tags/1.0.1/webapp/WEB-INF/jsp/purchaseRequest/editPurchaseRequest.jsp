<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>

<!-- 
	显示或编辑purchaseRequest主内容（编辑的时候只能编辑title和description）
	attribute x_edit: true 编辑
	attribute x_edit: false 显示
	attribute x_pr: purchaseRequest对象
-->

	<table width=90% border=0 cellpadding=4 cellspacing=0>
		<tr>
			<td class="bluetext"><bean:message key="purchaseRequest.site.id" />:</td>
			<td colspan="1">${x_pr.department.site.name}</td>
			
			<td class="bluetext"><bean:message key="purchaseRequest.department" />:</td>
			<td>${x_pr.department.name}</td>
		</tr>			   

		<tr>
			<td class="bluetext"><bean:message key="purchaseRequest.title"/>:</td>
			<td>
				<c:if test="${x_edit}">
					<html:text property="title"/><span class="required">*</span>
				</c:if>
				<c:if test="${!x_edit}">
					${x_pr.title}
				</c:if>
			</td>
			
			<td class="bluetext"><bean:message key="purchaseRequest.id" />:</td>
			<td>${x_pr.id}</td>
		</tr>
		<tr>
			<td class="bluetext"><bean:message key="purchaseRequest.description" />:</td>
			<td colspan="3">
				<c:if test="${x_edit}">
					<html:textarea property="description" rows="2" cols="60" />
				</c:if>
				<c:if test="${!x_edit}">
					${x_pr.description}
				</c:if>			
			</td>
		</tr>			    
		<tr>
			<td class="bluetext"><bean:message key="purchaseRequest.purchaseCategory.id" />:</td>
			<td>
				${x_pr.purchaseSubCategory.purchaseCategory.description}
			</td>
			
			<td class="bluetext"><bean:message key="purchaseRequest.purchaseSubCategory.id" />:</td>
			<td>
				${x_pr.purchaseSubCategory.description}
			</td>
		</tr>
		<tr>


			<td class="bluetext"><bean:message key="purchaseRequest.status" />:</td>
			<td>
				<span style="color:${x_pr.status.color}">
					<bean:write name="x_pr" property="status.${x_lang}ShortDescription"/>
				</span>
			</td>
		</tr>
	</table>
	<hr/>
	<table >
		<tr>
			<td class='bluetext' width="30%">
				<bean:message key="purchaseRequest.withinBudget"/>:
			</td>
			<td align="center">
				<c:if test="${x_pr.capex!=null}">
					<bean:message key="purchaseRequest.capexBudget"/>
				</c:if>
				<c:if test="${x_pr.yearlyBudget!=null}">
					<bean:message key="purchaseRequest.budget.code"/>
				</c:if>
				<c:if test="${x_pr.capex==null && x_pr.yearlyBudget==null}">
					<bean:message key="purchaseRequest.nonBudget"/>
				</c:if>
			</td>
		</tr>
	</table>
	<c:if test="${x_pr.yearlyBudget!=null}">
		<div id="yearlyBudgetContentDIV">
			<jsp:include page="yearlyBudgetContent.jsp" />
		</div>
	</c:if>
	
	<c:if test="${x_pr.capex!=null}">
		<jsp:include page="capexContent.jsp" />
	</c:if>
	<hr/>
	
	<table width="86%">
		<tr>
			<td  class="bluetext"><bean:message key="purchaseRequest.requestAmount"/> : </td>
			<td >
				<span id="span_purchaseRequest_amount">${x_pr.amount}</span>
				<c:if test="${x_showBudgetWarning}">
				<c:if test="${x_pr.yearlyBudget!=null}">
				<c:if test="${x_pr.yearlyBudget.remainAmount<0}">
				<span style="font-weight:bold;color:yellow"><bean:message key="purchaseRequest.overBudget"/></span>
				</c:if>				
				</c:if>
				<c:if test="${x_pr.capex!=null}">
				<c:if test="${x_pr.capex.remainAmount<0}">
				<span style="font-weight:bold;color:yellow"><bean:message key="purchaseRequest.overBudget"/></span>
				</c:if>				
				</c:if>
				</c:if>
			</td>
		 	<td  align="left" class='bluetext'>
				<bean:message key="purchaseRequest.baseCurrency"/>:
			</td>
			<td  align="left" >
				${x_pr.currency.code}
			</td>
	   </tr>
	   <tr>	
			<td class='bluetext'><bean:message key="purchaseRequest.requestor.id"/>:</td>
		 	<td>${x_pr.requestor.name}</td>
		 	
		 	<td class='bluetext'><bean:message key="purchaseRequest.requestDate"/>:</td>
			<td>
				<c:if test="${x_pr.requestDate!=null}">
					<bean:write name="x_pr" property="requestDate" format="yyyy/MM/dd"/>
				</c:if>
			</td>
		</tr>
	</table>	

<c:if test="${x_edit}">	
	<script type="text/javascript">
		var totalBaseCurrencyAmount = 0;
		document.getElementById('span_purchaseRequest_amount').
			setExpression('innerText', 'Math.round(totalBaseCurrencyAmount * 100) / 100');
	</script>
</c:if>

