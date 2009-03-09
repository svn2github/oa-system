<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<table width="60%">
	<tr>
		<td  class='bluetext' width="25%">
			<bean:message key="purchaseRequest.capex.id"/> : 
		</td>
		<td >
			<span id="capex_id_span">${x_capex.id}</span>
			<input type="hidden" value="${x_capex.id}" name="capex_id"/>
			<input type="hidden" value="${x_capexDepartmentIds}" name="capex_departmentIds"/>
			<input type="hidden" value="${x_capex.purchaseCategory.id}" name="capex_purchaseCategory_id"/>
			<input type="hidden" value="${x_capex.purchaseSubCategory.id}" name="capex_purchaseSubCategory_id"/>
			<input type="hidden" value="${x_capex.site.id}" name="capex_site_id"/>			
		</td>
		<td class='bluetext' width="30%">
			<bean:message key="purchaseRequest.capex.description"/> : 
		</td>
		<td >
			<span id="capex_description_span"></span>
		</td>
	</tr>
	<tr>
		<td class='bluetext'>
			<bean:message key="purchaseRequest.capex.category"/>: 
		</td>
		<td>
			<span id="capex_purchaseCategory_span">${x_capex.purchaseCategory.description}</span>
		</td>		
		<td  class='bluetext'>
			<bean:message key="purchaseRequest.capex.subCategory"/>: 
		</td>
		<td  class='bluetext' nowrap>
			<span id="capex_purchaseSubCategory_span">${x_capex.purchaseSubCategory.description}</span>
		</td>
	</tr>
	<tr>
		<td class='bluetext'>
			<bean:message key="purchaseRequest.capex.requestor"/>: 
		</td>
		<td>
			<span id="capex_requestor_span">${x_capex.lastApprovedRequest.requestor.name}</span>
		</td>
		<td class='bluetext'>
			<bean:message key="purchaseRequest.capex.requestDate"/>: 
		</td>
		<td>
			<span id="capex_requestDate">
			<logic:notEmpty name="x_capex">
				<logic:notEmpty name="x_capex" property="lastApprovedRequest">
					<bean:write name="x_capex" property="lastApprovedRequest.requestDate" format="yyyy/MM/dd"/>
				</logic:notEmpty>	
			</logic:notEmpty>
			</span>
		</td>
	</tr>
	<c:if test="${x_canViewCapexAmount}">
	<tr>
		<td class='bluetext'>
			<bean:message key="purchaseRequest.capex.amount"/>: 
		</td>
		<td>
			${x_capex.lastApprovedRequest.amount}
		</td>
		<td class='bluetext'>
			<bean:message key="purchaseRequest.capex.actualAmount"/>: 
		</td>
		<td>
			${x_capex.actualAmount}
		</td>
	</tr>
	</c:if>
</table>
