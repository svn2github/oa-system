<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>



<script> 
	function add() {
		var title="expenseCategory.new.title";
		var url="newExpenseCategory.do?site_id="+
			document.forms['expenseCategoryQueryForm'].site_id.value;
		var v=dialogAction(url,title,400,250);
		if (v) {
			var table = document.all('datatable');
			appendRow(table, v);
		    applyRowStyle(table);
		};
	}

	function edit(id) {
		var title="expenseCategory.edit.title";
		var url="editExpenseCategory.do?id="+id;
		var v=dialogAction(url,title,480,560);
		if (v) {
			updateRow(document.all('r' + id), v);
		};
	}
	function changeSite(combo)
	{
		if (!validateExpenseCategoryQueryForm(combo.form))
		{
			return false;
		}
		combo.form.submit();
	}
</script>
<html:javascript formName="expenseCategoryQueryForm"
	staticJavascript="false" />
<html:form action="listExpenseCategory"
	onsubmit="return validateExpenseCategoryQueryForm(this)">
	<html:hidden property="order" />
	<html:hidden property="descend" />
	<table  border=0 cellpadding=4 cellspacing=0>
		<tr>
			<td colspan="4"><html:messages id="x_message">${x_message}<br>
			</html:messages></td>
		</tr>
		<tr>
			<td class="bluetext"><bean:message key="expenseCategory.search.site" />:
			</td>
			<td>
				<html:select property="site_id"
					onchange="changeSite(this)">
					<html:options collection="x_siteList" property="id"
						labelProperty="name" />
				</html:select>
			</td>
		</tr>
		
		<tr>
			<td class="bluetext"><bean:message key="expenseCategory.search.description" />:
			</td>
			<td><html:text property="description" size="20" /></td>

			<td class="bluetext"><bean:message key="expenseCategory.enabled" />:
			</td>
			<td><html:select property="enabled">
				<html:option value=""><bean:message key="all.all"/></html:option>
				<c:if test="${sessionScope.LOGIN_USER.locale=='en'}">
					<html:options collection="X_ENABLEDDISABLEDLIST"
						property="enumCode" labelProperty="engShortDescription" />
				</c:if>
				<c:if test="${sessionScope.LOGIN_USER.locale!='en'}">
					<html:options collection="X_ENABLEDDISABLEDLIST"
						property="enumCode" labelProperty="chnShortDescription" />
				</c:if>
			</html:select></td>

			<td align="right" colspan="4"><html:submit>
				<bean:message key="all.query" />
			</html:submit> <input type="button"
				value="<bean:message key="all.new"/>" onClick="add()"></td>
		</tr>
	</table>
</html:form>
<hr />
<page:form action="listExpenseCategory.do" method="post">
	<jsp:include page="../pageHead.jsp"/>
	<table class="data">
		<thead>
			<tr bgcolor="#9999ff">
				<th width="70%"><page:order order="description"
					style="text-decoration:none">
					<bean:message key="expenseCategory.description" />
					<page:desc>
						<img src="images/down.gif" border="0" />
					</page:desc>
					<page:asc>
						<img src="images/up.gif" border="0" />
					</page:asc>
				</page:order></th>
				<th width="70%"><page:order order="enabled"
					style="text-decoration:none">
					<bean:message key="expenseCategory.enabled" />
					<page:desc>
						<img src="images/down.gif" border="0" />
					</page:desc>
					<page:asc>
						<img src="images/up.gif" border="0" />
					</page:asc>
				</page:order></th>
			</tr>
		</thead>

		<tbody id="datatable">
			<logic:iterate id="X_OBJECT" name="X_RESULTLIST">
				<bean:define id="X_OBJECT" toScope="request" name="X_OBJECT" />
				<jsp:include page="row.jsp" />
			</logic:iterate>
		</tbody>
	</table>
	<jsp:include page="../pageTail.jsp"/>

</page:form>
<script type="text/javascript">
    applyRowStyle(document.all('datatable'));
</script>
