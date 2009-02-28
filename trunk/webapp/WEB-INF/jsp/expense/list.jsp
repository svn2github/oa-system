<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>
<%@ page import="com.aof.model.metadata.ExpenseStatus"%>
<%
	request.setAttribute("EXPENSE_STATUS_DRAFT",ExpenseStatus.DRAFT);
	request.setAttribute("EXPENSE_STATUS_REJECTED",ExpenseStatus.REJECTED);
%>

<xml id="data">
<data>
<site id="" name="<bean:message key="expense.search.site.all"/>">
	<expenseCategory id="" name="<bean:message key="expense.search.expenseCategory.all"/>">
	</expenseCategory>
</site>
<logic:iterate id="x_userSite" name="x_userSiteList">
	<site id="${x_userSite.site.id}" name="<bean:write name="x_userSite" property="site.name"/>">
		<expenseCategory id="" name="<bean:message key="expense.search.expenseCategory.all"/>">
		</expenseCategory>
		<logic:iterate id="x_expenseCategory" name="x_userSite" property="site.enabledExpenseCategoryList">
			<expenseCategory id="${x_expenseCategory.id}" name="<bean:write name="x_expenseCategory" property="description"/>">
			</expenseCategory>
		</logic:iterate>
	</site>
</logic:iterate>
</data>
</xml>

<xml id="config">
<config>
<site>
<expenseCategory>
</expenseCategory>
</site>
</config>
</xml>



<script type="text/javascript">
<!--
	function validateForm(form) {
		if (trim(form["requestDateFrom"].value).length!=0) {
			if (!checkCenturyDate(trim(form["requestDateFrom"].value))) {
				alertDate('<bean:message key="expense.search.requestDate"/>');
				form["requestDateFrom"].focus();
				return false;
			}
		}
		if (trim(form["requestDateTo"].value).length!=0) {
			if (!checkCenturyDate(trim(form["requestDateTo"].value))) {
				alertDate('<bean:message key="expense.search.requestDate"/>');
				form["requestDateTo"].focus();
				return false;
			}
		}
		if (trim(form["amountFrom"].value).length!=0) {
			if (!validateFormFloat(form["amountFrom"].value)) {
				alertFloat('<bean:message key="expense.search.amount"/>');
				form["amountFrom"].focus();
				return false;
			}
		}
		if (trim(form["amountTo"].value).length!=0) {
			if (!validateFormFloat(form["amountTo"].value)) {
				alertFloat('<bean:message key="expense.search.amount"/>');
				form["amountTo"].focus();
				return false;
			}
		}
	
		return true;
	}
	
	function add() {
		window.location.href='newExpense${x_postfix}.do';
	}
	

	function edit(id) {
		var url="editExpense${x_postfix}.do?id="+ id;
		window.location=url;
	}
	
	function view(id) {
		var url="viewExpense${x_postfix}.do?id="+ id;
		window.location=url;
	}
	
	function copy(id) {
		if(confirm("<bean:message key="expense.copy.confirm"/>")) {
			var url="copyExpense${x_postfix}.do?id="+ id;
			window.location=url;
		}
	}
//-->
</script>
<html:form action="listExpense${x_postfix}" onsubmit="return validateForm(this);">
	<html:hidden property="order" />
	<html:hidden property="descend" />
	<input type="hidden" name="site_id_value"
		value="<bean:write name="expenseQueryForm" property="site_id"/>" />
	<input type="hidden" name="expenseCategory_id_value"
		value="<bean:write name="expenseQueryForm" property="expenseCategory_id"/>" />

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
			<td class="bluetext"><bean:message key="expense.id" />:</td>
			<td><html:text property="id" /></td>
			<td class="bluetext"><bean:message key="expense.title" />:</td>
			<td><html:text property="title"/></td>
		</tr>
		<tr>
			<td class="bluetext"><bean:message key="expense.site" />:</td>
			<td><html:select property="site_id" >
				</html:select></td>

			<td class="bluetext"><bean:message key="expense.expenseCategory" />:</td>
			<td><html:select property="expenseCategory_id">
				</html:select></td>
		</tr>
		<tr>
			<td class="bluetext"><bean:message key="expense.search.requestDate" />:</td>
			<td>
			<table border=0 cellpadding=0 cellspacing=0>
		  		<tr>
		  		<td>
					<table border=0 cellpadding=0 cellspacing=0>
					<tr>
						<td>
							<html:text property="requestDateFrom" size="6"  maxlength="10" />
						</td>
						<td>
							<a onclick="event.cancelBubble=true;" href="javascript:showCalendar('dimg1',false,'requestDateFrom',null,null,'expenseQueryForm')"><IMG align="absMiddle" border="0" id="dimg1" src="images/datebtn.gif" ></A>
						</td>
					</tr>
					</table>
		  		</td>
		  		<td width="20" align="center">~</td>
		  		<td>
					<table border=0 cellpadding=0 cellspacing=0>
					<tr>
						<td>
							<html:text property="requestDateTo" size="6"  maxlength="10" />
						</td>
						<td>
							<a onclick="event.cancelBubble=true;" href="javascript:showCalendar('dimg2',false,'requestDateTo',null,null,'expenseQueryForm')"><IMG align="absMiddle" border="0" id="dimg2" src="images/datebtn.gif" ></A>
						</td>
					</tr>
					</table>
		  		</td>
		  		</tr>
	  		</table>
			</td>
			<td class="bluetext"><bean:message key="expense.search.amount" />:</td>
			<td>
			<table border=0 cellpadding=0 cellspacing=0>
		  		<tr>
		  		<td>
		  			<html:text property="amountFrom" size="6"/>
		  		</td>
		  		<td width="14" align="center">~</td>
		  		<td>
		  			<html:text property="amountTo" size="6"/>
		  		</td>
		  		</tr>
	  		</table>	
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
			<c:choose>
				<c:when test="${x_postfix=='_other'}">
					<td class="bluetext"><bean:message key="expense.requestor.id" />:</td>
					<td>
				  		<html:text property="requestor" />
				  	</td>
			  	</c:when>
			  	<c:otherwise>
					<td></td><td></td>			  	
			  	</c:otherwise>
			</c:choose>
		</tr>
		<tr>
			<td align="right" colspan="4">
				<html:submit><bean:message key="all.query"/></html:submit>
				 <c:if test="${x_postfix=='_self'}">
					<input type="button" value="<bean:message key="expense.listMyTA"/>"
						onClick="window.location.href='listMyTravelApplicationForExpense.do'" />
				</c:if>
				<input type="button" value="<bean:message key="all.new"/>"
					onClick="window.location.href='newExpense${x_postfix}.do'" />
			</td>
		</tr>
	</table>
</html:form>
<hr />
<page:form action="listExpense${x_postfix}.do" method="post">
	<jsp:include page="../pageHead.jsp"/>
	<table class="data">
		<thead>
			<tr bgcolor="#9999ff">
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
				<th width="25%">
					<bean:message key="expense.listExpense.title" />
				</th>
				<th width="5%">
					<bean:message key="expense.listExpense.currency" />
				</th>
				<th width="12%">
					<bean:message key="expense.listExpense.totalAmount" />
				</th>
				<th width="8%">
					<bean:message key="expense.listExpense.requestDate" />
					
				</th>
				<c:if test="${x_postfix=='_other'}">
				<th width="8%">
					<bean:message key="expense.listExpense.requestor" />
				</th>
				</c:if>
				<th width="8%">
					<bean:message key="expense.listExpense.status" />
				</th>
				<th width="5%">
					<bean:message key="all.copy" />
				</th>
			</tr>
		</thead>

		<tbody id="datatable">
			<logic:iterate id="X_OBJECT" name="X_RESULTLIST">
				<tr>
				<td>
					<c:choose>
					<c:when test="${X_OBJECT.status==EXPENSE_STATUS_DRAFT}">
						<a href='javascript:edit("${X_OBJECT.id}")'>${X_OBJECT.id}</a>
					</c:when>
					<c:otherwise>
						<a href='javascript:view("${X_OBJECT.id}")'>${X_OBJECT.id}</a>
					</c:otherwise>	
					</c:choose>
				</td>
				<td>${X_OBJECT.expenseCategory.description}</td>
				<td>
					${X_OBJECT.title}
				</td>
				<td>${X_OBJECT.expenseCurrency.name}</td>
				<td style="text-align:right">${X_OBJECT.amount}</td>
				<td><bean:write name="X_OBJECT" property="requestDate" format="yyyy/MM/dd"/></td>
				<c:if test="${x_postfix=='_other'}">
					<td>${X_OBJECT.requestor.name}</td>
				</c:if>
				<td>
					<span style="color:${X_OBJECT.status.color}">
				      <c:if test="${sessionScope.LOGIN_USER.locale=='en'}">${X_OBJECT.status.engShortDescription}</c:if>
				      <c:if test="${sessionScope.LOGIN_USER.locale!='en'}">${X_OBJECT.status.chnShortDescription}</c:if>
				    </span> 
				</td>
				<td align="center">
					<a href='javascript:copy("${X_OBJECT.id}")'><bean:message key="all.copy" /></a>
				</td>
				</tr>
			</logic:iterate>
		</tbody>
	</table>
	<jsp:include page="../pageTail.jsp"/>
</page:form>

<script type="text/javascript">
    applyRowStyle(document.all('datatable'));
    var mapping=new Map();
	mapping.put("expenseCategory_id","expenseCategory");
	mapping.put("site_id","site");
    initCascadeSelect("config","data","expenseQueryForm",mapping,true);
</script>
