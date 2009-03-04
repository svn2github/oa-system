<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>
<script type="text/javascript">
<!--
	function newExpense(ta_id) {
		window.location.href='newExpense${x_postfix}.do?ta_id='+ta_id;
	}
	

	
	
//-->
</script>
<html:form action="listExpense${x_postfix}">
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
			<td class="bluetext"><bean:message key="expense.id" />:</td>
			<td><html:text property="id" /></td>

			<td class="bluetext"><bean:message key="expense.title" />:</td>
			<td><html:text property="title"/></td>
		</tr>
		<tr>
			<td class="bluetext"><bean:message key="expense.status" /></td>
			<td>
				<html:select property="status">
					<option value=""><bean:message key="all.selectall"/></option>
					<c:if test="${sessionScope.LOGIN_USER.locale=='en'}"><html:options collection="x_expenseStatusList" property="enumCode"	labelProperty="engShortDescription" /></c:if>
					<c:if test="${sessionScope.LOGIN_USER.locale!='en'}"><html:options collection="x_expenseStatusList" property="enumCode" labelProperty="chnShortDescription" /></c:if>
				</html:select>
			</td>
			<td align="right" colspan="2">
				<html:submit><bean:message key="all.query"/></html:submit>
				<input type="button" value="<bean:message key="expense.listMyTA"/>"
					onClick="window.location.href='listMyTravelApplicationForExpense.do'" />
				<input type="button" value="<bean:message key="all.new"/>"
					onClick="window.location.href='newExpense${x_postfix}.do'" />
			</td>
		</tr>
	</table>
</html:form>
<hr />
<font color="blue"><h3><bean:message key="expense.listTravelApplication.title" /></h3></font>
<page:form action="listMyTravelApplicationForExpense.do" method="post">
	<jsp:include page="../pageHead.jsp"/>
	<table class="data">
		<thead>
			<tr bgcolor="#9999ff">
				<th width="15%"><page:order order="id" style="text-decoration:none">
					<bean:message key="expense.listTravelApplication.code" />
					<page:desc>
						<img src="images/down.gif" border="0" />
					</page:desc>
					<page:asc>
						<img src="images/up.gif" border="0" />
					</page:asc>
				</page:order></th>

				<th width="40%">
					<bean:message key="expense.listTravelApplication.destination" />
				</th>
		
				<th width="15%">
					<bean:message key="expense.listTravelApplication.travelMode" />
				</th>
				<th width="15%">
					<bean:message key="expense.listTravelApplication.createDate" />
				</th>
				<th width="15%">
					<bean:message key="expense.listTravelApplication.status" />
				</th>
			</tr>
		</thead>

		<tbody id="datatable">
			<logic:iterate id="X_OBJECT" name="X_RESULTLIST">
				<tr>
				<td>
					<a href='javascript:newExpense("${X_OBJECT.id}")'>${X_OBJECT.id}</a>
				</td>
				<td>
					<c:if test="${sessionScope.LOGIN_USER.locale!='en'}">
					${X_OBJECT.toCity.province.country.chnName}/${X_OBJECT.toCity.province.chnName}/${X_OBJECT.toCity.chnName}
					</c:if>
					<c:if test="${sessionScope.LOGIN_USER.locale=='en'}">
					${X_OBJECT.toCity.province.country.engName}/${X_OBJECT.toCity.province.engName}/${X_OBJECT.toCity.engName}
					</c:if>
				</td>
				<td>
					<span style="color:${X_OBJECT.travellingMode.color}">
				      <c:if test="${sessionScope.LOGIN_USER.locale=='en'}">${X_OBJECT.travellingMode.engShortDescription}</c:if>
				      <c:if test="${sessionScope.LOGIN_USER.locale!='en'}">${X_OBJECT.travellingMode.chnShortDescription}</c:if>
				    </span> 
				</td>
				<td><bean:write name="X_OBJECT" property="requestDate" format="yyyy/MM/dd"/></td>
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

