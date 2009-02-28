<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>

<c:set var="x_expense_expenseType_TRAVEL" value="<%=com.aof.model.metadata.ExpenseType.TRAVEL%>"/>
<table width="100%">
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
			<td class="bluetext" width="20%"><bean:message key="expense.requestdate" />:</td>
			<td width="30%">
				<fmt:formatDate value="${x_expense.requestDate}" pattern="yyyy/MM/dd"/>
			</td>
		</tr>	
		<tr>
			<td class="bluetext" width="20%"><bean:message key="expense.creator.id" />:</td>
			<td width="30%">
				${x_expense.creator.name}
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
		<td width="80%" colspan="3">${x_expense.title}</td>
	</tr>
	<tr>
		<td class="bluetext" width="20%"><bean:message key="expense.description" />:</td>
		<td width="80%" colspan="3">${x_expense.description}</td>
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
	</tr>
	<tr>
		<td class="bluetext" width="20%"><bean:message key="expense.amount" />:</td>
		<td width="30%">
			<div id="baseAmount">
				<fmt:formatNumber value="${x_expense.baseAmount}" maxFractionDigits="2" minFractionDigits="2"/>
			</div>
		</td>
		<td class="bluetext" width="20%"><bean:message key="expense.baseCurrency" />:</td>
		<td width="30%">
			${x_expense.department.site.baseCurrency.name}
		</td>
	</tr>			
	<tr>
		<td class="bluetext" width="20%"><bean:message key="expense.status" />:</td>
		<td width="30%">
			<span style="color:${x_expense.status.color}"><bean:write name="x_expense" property="status.${x_lang}ShortDescription"/></span> 
		</td>
		<td class="bluetext" width="20%"><bean:message key="expense.isRecharge" />:</td>
		<td width="30%">
			<span style="color:${x_expense.isRecharge.color}"><bean:write name="x_expense" property="isRecharge.${x_lang}ShortDescription"/></span>
		</td>
	</tr>			
	</table>
<logic:notEmpty name="x_attachmentList">
<hr align="left" width="100%">
<table width="100%" >
	<tr>
		<td align="left" width='100%' class='bluetext'><font color="blue">
		<h3><bean:message key="expenseAttachment.list.title" /></h3>
		</font></td>
</table>
<table class="data" width=100%>
	<thead>
		<tr bgcolor="#9999ff">
			<th width="45%">
			<div align="center"><bean:message key="expenseAttachment.title" /></div>
			</th>
			<th width="45%">
			<div align="center"><bean:message key="expenseAttachment.fileName" /></div>
			</th>
			<th width="10%">
			<div align="center"><bean:message key="expenseAttachment.uploadDate" /></div>
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
<script type="text/javascript">
    applyRowStyle(document.all('attachTable'));
</script>
</logic:notEmpty>
<hr align="left" width="100%">
<table width="100%"  border=0 cellpadding=0 cellspacing=0>
	<tr>
		<td align="left" width='100%' class='bluetext'><font color="blue">
		<h3><bean:message key="expense.row.title" /></h3>
		</font></td>
</table>
<jsp:include page="viewRow.jsp"/>

