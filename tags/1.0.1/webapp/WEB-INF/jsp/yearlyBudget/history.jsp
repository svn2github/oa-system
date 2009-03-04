<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>


<table width=100% border=0 cellpadding=4 cellspacing=0>
	<tr>
		<td><bean:message key="yearlyBudget.site.id"/>:</td>
		<td>${x_yb.site.name}</td>
	</tr>
	<tr>
		<td><bean:message key="yearlyBudget.code"/>:</td>
		<td>${x_yb.code}</td>
	</tr>
	<tr>
		<td><bean:message key="yearlyBudget.name"/>:</td>
		<td>${x_yb.name}</td>
	</tr>
	<tr>
		<td><bean:message key="yearlyBudget.type"/>:</td>
		<td><bean:write name="x_yb" property="type.${x_lang}ShortDescription"/></td>
	</tr>
	<tr>
		<td><bean:message key="yearlyBudget.year"/>:</td>
		<td>${x_yb.year}</td>
	</tr>
	<tr>
		<td><bean:message key="yearlyBudget.purchaseCategory.id"/>:</td>
		<td align="left">${x_yb.purchaseCategory.description}</td>
	</tr>
	<tr>
		<td><bean:message key="yearlyBudget.purchaseSubCategory.id"/>:</td>
		<td align="left">${x_yb.purchaseSubCategory.description}</td>
	</tr>
	<tr>
		<td><bean:message key="yearlyBudget.baseCurrency"/>:</td>
		<td>${x_yb.site.baseCurrency.name}</td>
	</tr>
</table>
<%int ii=1;%>				
<table id="table1" class="data">
	<thead>
		<tr bgcolor="#9999ff">
			<th><bean:message key="yearlyBudget.version"/></th>
			<th><bean:message key="yearlyBudget.department"/></th>
			<th><bean:message key="yearlyBudget.name"/></th>
			<th><bean:message key="yearlyBudget.amount"/></th>
			<th><bean:message key="yearlyBudget.modifier.id"/></th>
			<th><bean:message key="yearlyBudget.modifyDate"/></th>
		</tr>
	<thead>
	<tbody>
		<logic:iterate name="x_history" id="x_h">
		<tr class="<%=(ii++)%2==0?"odd":"even" %>">
			<td align="left">${x_h.version}</td>
			<td >
				<logic:iterate name="x_h" property="departments" id="x_d" >
					${x_d.name}<br>
				</logic:iterate>
			</td>
			<td >${x_h.name}</td>
			<td align="right">${x_h.amount}</td>
			<td align="left">${x_h.creator.name}</td>
			<td align="center"><bean:write name="x_h" property="createDate" format="yyyy/MM/dd"/></td>
		</tr>
		</logic:iterate>
	</tbody>
</table>
<hr>

<table width=90% border=0 cellpadding=4 cellspacing=0>
	<tr>
		<td align="right"><input type="button" value="<bean:message key="all.close"/>"
			onclick="window.parent.close();"></td>
	</tr>
</table>
