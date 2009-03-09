<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>
<%@ page import="com.aof.model.metadata.ExpenseStatus"%>

<xml id="data">
<data>
<logic:iterate id="x_site" name="x_siteList">
	<site id="${x_site.id}" name="<bean:write name="x_site" property="name"/>">
		<department id="0" name="All"/>
		<logic:iterate id="x_department" name="x_site" property="departments">
		<c:if test="${x_department.granted}">
			<department id="${x_department.id}" name="<bean:write name="x_department" property="indentName"/>"/>
		</c:if>
		</logic:iterate>
	</site>
</logic:iterate>
</data>
</xml>

<xml id="config">
<config>
	<site>
		<department/>
	</site>
</config>
</xml>




<script type="text/javascript">
<!--
	function validateForm(form) {
		if (trim(form["createDateFrom"].value).length!=0) {
			if (!checkCenturyDate(trim(form["createDateFrom"].value))) {
				alertDate('<bean:message key="expense.search.createDateFrom"/>');
				form["createDateFrom"].focus();
				return false;
			}
		}
		if (trim(form["createDateTo"].value).length!=0) {
			if (!checkCenturyDate(trim(form["createDateTo"].value))) {
				alertDate('<bean:message key="expense.search.createDateFrom"/>');
				form["createDateTo"].focus();
				return false;
			}
		}
		return true;
	}
	
	
	function view(id) {
		var url="viewExpense.do?id="+ id;
		window.location=url;
	}
	
//-->
</script>
<html:form action="reportExpense" onsubmit="return validateForm(this);">
	<html:hidden property="order" />
	<html:hidden property="descend" />
	<input type="hidden" name="site_id_value"
		value="<bean:write name="expenseQueryForm" property="site_id"/>" />
	<input type="hidden" name="department_id_value"
		value="<bean:write name="expenseQueryForm" property="department_id"/>" />

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
			<td class="bluetext"><bean:message key="expense.site" />:</td>
			<td><html:select property="site_id" >
				</html:select></td>

			<td class="bluetext"><bean:message key="expense.expenseCategory" />:</td>
			<td><html:select property="department_id" >
				</html:select></td>
		</tr>
		<tr>
			<td class="bluetext"><bean:message key="expense.id" />:</td>
			<td><html:text property="id" /></td>
			<td class="bluetext"><bean:message key="expense.title" />:</td>
			<td><html:text property="title"/></td>
		</tr>
		<tr>
			<td class="bluetext"><bean:message key="expense.search.createDate" />:</td>
			<td>
			<table border=0 cellpadding=0 cellspacing=0>
		  		<tr>
		  		<td>
					<table border=0 cellpadding=0 cellspacing=0>
					<tr>
						<td>
							<html:text property="createDateFrom" size="6"  maxlength="10" />
						</td>
						<td>
							<a onclick="event.cancelBubble=true;" href="javascript:showCalendar('dimg1',false,'createDateFrom',null,null,'expenseQueryForm')"><IMG align="absMiddle" border="0" id="dimg1" src="images/datebtn.gif" ></A>
						</td>
					</tr>
					</table>
		  		</td>
		  		<td width="20" align="center">~</td>
		  		<td>
					<table border=0 cellpadding=0 cellspacing=0>
					<tr>
						<td>
							<html:text property="createDateTo" size="6"  maxlength="10" />
						</td>
						<td>
							<a onclick="event.cancelBubble=true;" href="javascript:showCalendar('dimg2',false,'createDateTo',null,null,'expenseQueryForm')"><IMG align="absMiddle" border="0" id="dimg2" src="images/datebtn.gif" ></A>
						</td>
					</tr>
					</table>
		  		</td>
		  		</tr>
	  		</table>
			</td>
			<td class="bluetext"><bean:message key="expense.requestor.id" />:</td>
			<td>
		  		<html:text property="requestor" />
		  	</td>
		</tr>
		<tr>
			<td class="bluetext"><bean:message key="expense.status" />:</td>
			<td>
				<html:select property="status">
					<html:option value=""><bean:message key="all.all"/></html:option>
					<c:if test="${sessionScope.LOGIN_USER.locale=='en'}"><html:options collection="x_expenseStatusList" property="enumCode"	labelProperty="engShortDescription" /></c:if>
					<c:if test="${sessionScope.LOGIN_USER.locale!='en'}"><html:options collection="x_expenseStatusList" property="enumCode" labelProperty="chnShortDescription" /></c:if>
				</html:select>
			</td>
		</tr>
		<tr>
			<td align="right" colspan="4">
				<html:submit><bean:message key="all.query"/></html:submit>
			</td>
		</tr>
	</table>
</html:form>
<hr />
<page:form action="reportExpense.do" method="post">
	<jsp:include page="../pageHead.jsp"/>
	<table class="data">
		<thead>
			<tr class="new_bg">
				<th width="15%"><page:order order="id" style="text-decoration:none">
					<bean:message key="expense.reportExpense.code" />
					<page:desc>
						<img src="images/down.gif" border="0" />
					</page:desc>
					<page:asc>
						<img src="images/up.gif" border="0" />
					</page:asc>
				</page:order></th>

				<th width="20%"><page:order order="category"
					style="text-decoration:none">
					<bean:message key="expense.reportExpense.category" />
					<page:desc>
						<img src="images/down.gif" border="0" />
					</page:desc>
					<page:asc>
						<img src="images/up.gif" border="0" />
					</page:asc>
				</page:order></th>
				<th width="10%">
					<bean:message key="expense.reportExpense.requestor" />
				</th>
				<th width="20%"><page:order order="department" style="text-decoration:none">
					<bean:message key="expense.reportExpense.department" />
					<page:desc>
						<img src="images/down.gif" border="0" />
					</page:desc>
					<page:asc>
						<img src="images/up.gif" border="0" />
					</page:asc>
				</page:order>
				</th>
				<th width="12%">
					<bean:message key="expense.reportExpense.totalAmount" />
				</th>
				<th width="10%">
					<bean:message key="expense.reportExpense.createDate" />
				</th>
				<th width="8%">
					<bean:message key="expense.reportExpense.status" />
				</th>
				<th width="6%">
					<bean:message key="expense.reportExpense.approvalDuration" />
				</th>
			</tr>
		</thead>

		<tbody id="datatable">
			<logic:iterate id="X_OBJECT" name="X_RESULTLIST">
				<tr>
				<td>
					<a href='javascript:view("${X_OBJECT.id}")'>${X_OBJECT.id}</a>
				</td>
				<td>${X_OBJECT.expenseCategory.description}</td>
				<td>
					${X_OBJECT.requestor.name}
				</td>
				<td>${X_OBJECT.department.name}</td>
				<td style="text-align:right">${X_OBJECT.amount}</td>
				<td><bean:write name="X_OBJECT" property="createDate" format="yyyy/MM/dd"/></td>
				<td>
					<span style="color:${X_OBJECT.status.color}">
				      <c:if test="${sessionScope.LOGIN_USER.locale=='en'}">${X_OBJECT.status.engShortDescription}</c:if>
				      <c:if test="${sessionScope.LOGIN_USER.locale!='en'}">${X_OBJECT.status.chnShortDescription}</c:if>
				    </span> 
				</td>
				<td>${X_OBJECT.approveDurationDay}</td>
				</tr>
			</logic:iterate>
		</tbody>
	</table>
	<c:set var="x_pdf" scope="request" value="true"/>
	<jsp:include page="../pageTail.jsp"/>
</page:form>

<script type="text/javascript">
    applyRowStyle(document.all('datatable'));
    var mapping=new Map();
	mapping.put("site_id","site");
	mapping.put("department_id","department");
    initCascadeSelect("config","data","expenseQueryForm",mapping,true);
</script>
