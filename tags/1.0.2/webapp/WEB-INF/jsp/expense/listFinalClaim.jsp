<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>
<script type="text/javascript">
<!--
	function finalClaim(id) {
		var url="finalClaimExpense.do?id="+ id;
		window.location=url;
	}
	
	function viewFinalClaim(id) {
		var url="viewExpense_finalClaim.do?id="+ id;
		window.location=url;
	}
	
	
//-->
</script>
<html:form action="/listExpense_finalClaim">
	<html:hidden property="order" />
	<html:hidden property="descend" />

	<table width=100% border=0 cellpadding=4 cellspacing=0>
		<tr>
			<td colspan="10">
				<div class="message">
					<html:messages id="x_message" message="true">
						${x_message}<br>
					</html:messages>
				</div>
			</td>
		</tr>
		<tr>
			<td class="bluetext" ><bean:message key="travelApplication.site" />
			&nbsp;</td>
			<td colspan="8">
				<html:select property="site_id">
					<html:options collection="x_siteList" property="id"
						labelProperty="name" />
				</html:select>
			</td>
		</tr>
		<tr>
			<td class="bluetext"><bean:message key="expense.id" />:</td>
			<td><html:text property="id" /></td>

			<td class="bluetext"><bean:message key="expense.title" />:</td>
			<td><html:text property="title"/></td>
		</tr>
		<tr>
			<td class="bluetext"><bean:message key="expense.status" /></td>
			<td>
				<html:select property="status">
					<html:option value=""><bean:message key="all.selectall"/></html:option>
					<html:options collection="x_expenseStatusList" property="enumCode"	labelProperty="${x_lang}ShortDescription" />
				</html:select>
			</td>
			<td align="right" colspan="2">
				<html:submit><bean:message key="all.query"/></html:submit>
			</td>
		</tr>
	</table>
</html:form>
<hr />
<page:form action="listExpense_finalClaim.do" method="post">
	<jsp:include page="../pageHead.jsp"/>
	<table class="data">
		<thead>
			<tr class="new_bg">
				<th width="15%"><page:order order="id" style="text-decoration:none">
					<bean:message key="expense.listExpense.code" />
					<page:desc>
						<img src="images/down.gif" border="0" />
					</page:desc>
					<page:asc>
						<img src="images/up.gif" border="0" />
					</page:asc>
				</page:order></th>

				<th width="20%"><page:order order="category"
					style="text-decoration:none">
					<bean:message key="expense.listExpense.category" />
					<page:desc>
						<img src="images/down.gif" border="0" />
					</page:desc>
					<page:asc>
						<img src="images/up.gif" border="0" />
					</page:asc>
				</page:order></th>
				<th width="10%">
					<bean:message key="expense.listExpense.requestor" />
				</th>
				<th width="15%"><page:order order="department"
					style="text-decoration:none">
					<bean:message key="expense.listExpense.department" />
					<page:desc>
						<img src="images/down.gif" border="0" />
					</page:desc>
					<page:asc>
						<img src="images/up.gif" border="0" />
					</page:asc>
				</page:order></th>
				<th width="10%">
					<bean:message key="expense.listExpense.currency" />
				</th>
				<th width="10%">
					<bean:message key="expense.listExpense.totalAmount" />
				</th>
				<th width="10%">
					<bean:message key="expense.listExpense.createDate" />
				</th>
				<th width="10%">
					<bean:message key="expense.listExpense.status" />
				</th>
			</tr>
		</thead>

		<tbody id="datatable">
			<logic:iterate id="X_OBJECT" name="X_RESULTLIST">
				<tr>
				<td>
					<c:if test="${X_OBJECT.status.enumCode!='6'}">
						<a href='javascript:finalClaim("${X_OBJECT.id}")'>${X_OBJECT.id}</a>
					</c:if>
					
					<c:if test="${X_OBJECT.status.enumCode=='6'}">
						<a href='javascript:viewFinalClaim("${X_OBJECT.id}")'>${X_OBJECT.id}</a>
					</c:if>
					
				</td>
				<td>${X_OBJECT.expenseCategory.description}</td>
				<td>${X_OBJECT.requestor.name}</td>
				<td>${X_OBJECT.department.name}</td>
				<td>${X_OBJECT.expenseCurrency.name}</td>
				<td>${X_OBJECT.amount}</td>
				<td><bean:write name="X_OBJECT" property="createDate" format="yyyy/MM/dd"/></td>
				<td>
					<span style="color:${X_OBJECT.status.color}">
				      <c:if test="${sessionScope.LOGIN_USER.locale=='en'}">${X_OBJECT.status.engShortDescription}</c:if>
				      <c:if test="${sessionScope.LOGIN_USER.locale!='en'}">${X_OBJECT.status.chnShortDescription}</c:if>
				    </span> 
				</td>
				</tr>
			</logic:iterate>
		</tbody>
	</table>
	<jsp:include page="../pageTail.jsp"/>
</page:form>

<script type="text/javascript">
    applyRowStyle(document.all('datatable'));
</script>

