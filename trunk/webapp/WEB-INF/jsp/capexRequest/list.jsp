<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>
<script type="text/javascript">
<!--
	function validateForm(form) {
		if (trim(form["requestDateFrom"].value).length!=0) {
			if (!checkCenturyDate(trim(form["requestDateFrom"].value))) {
				alertDate('<bean:message key="capexRequest.requestDate"/>');
				form["requestDateFrom"].focus();
				return false;
			}
		}
		if (trim(form["requestDateTo"].value).length!=0) {
			if (!checkCenturyDate(trim(form["requestDateTo"].value))) {
				alertDate('<bean:message key="capexRequest.requestDate"/>');
				form["requestDateTo"].focus();
				return false;
			}
		}
		return true;
	}
	
//-->
</script>
<html:form action="listCapexRequest" onsubmit="return validateForm(this);">
	<html:hidden property="order" />
	<html:hidden property="descend" />

	<table width=100% border=0 cellpadding=4 cellspacing=0>
		<tr>
			<td class="bluetext"><bean:message key="capexRequest.capex.id" />:</td>
			<td><html:text property="capex_id" size="8"/></td>
			<td class="bluetext"><bean:message key="capexRequest.title" />:</td>
			<td><html:text property="title" size="16"/></td>
		</tr>
		<tr>
			<td class="bluetext"><bean:message key="capexRequest.type" />:</td>
			<td>
				<html:select property="type">
					<html:option value=""><bean:message key="all.all"/></html:option>
					<c:if test="${sessionScope.LOGIN_USER.locale=='en'}"><html:options collection="X_CAPEXREQUESTTYPELIST" property="enumCode" labelProperty="engShortDescription"/></c:if>
					<c:if test="${sessionScope.LOGIN_USER.locale!='en'}"><html:options collection="X_CAPEXREQUESTTYPELIST" property="enumCode" labelProperty="chnShortDescription"/></c:if>
				</html:select>
			</td>
			<td class="bluetext"><bean:message key="capexRequest.status" />:</td>
			<td>
				<html:select property="status">
					<html:option value=""><bean:message key="all.all"/></html:option>
					<c:if test="${sessionScope.LOGIN_USER.locale=='en'}"><html:options collection="X_CAPEXREQUESTSTATUSLIST" property="enumCode" labelProperty="engShortDescription"/></c:if>
					<c:if test="${sessionScope.LOGIN_USER.locale!='en'}"><html:options collection="X_CAPEXREQUESTSTATUSLIST" property="enumCode" labelProperty="chnShortDescription"/></c:if>
				</html:select>
			</td>
		<tr>
			<td class="bluetext"><bean:message key="capexRequest.requestDate" />:</td>
			<td>
				<table border=0 cellpadding=0 cellspacing=0>
			  		<tr>
						<td>
							<html:text property="requestDateFrom" size="6"  maxlength="10" />
						</td>
						<td>
							<a onclick="event.cancelBubble=true;" href="javascript:showCalendar('dimg1',false,'requestDateFrom',null,null,'capexRequestQueryForm')"><IMG align="absMiddle" border="0" id="dimg1" src="images/datebtn.gif" ></A>
						</td>
						<td width="20" align="center">~</td>
						<td>
							<html:text property="requestDateTo" size="6"  maxlength="10" />
						</td>
						<td>
							<a onclick="event.cancelBubble=true;" href="javascript:showCalendar('dimg2',false,'requestDateTo',null,null,'capexRequestQueryForm')"><IMG align="absMiddle" border="0" id="dimg2" src="images/datebtn.gif" ></A>
						</td>
			  		</tr>
		  		</table>
			</td>
			<td colspan="2" align="right">
				<html:submit><bean:message key="all.query"/></html:submit>
				<input type="button" value="<bean:message key="all.new"/>" onclick="window.location.href='newCapexRequest.do'" />
			</td>
		</tr>
	</table>
</html:form>
<hr />
<page:form action="listCapexRequest.do" method="post">
	<jsp:include page="../pageHead.jsp"/>
	<table class="data">
		<thead>
			<tr bgcolor="#9999ff">
				<th width="20%"><page:order order="capex_id" style="text-decoration:none">
					<bean:message key="capexRequest.capex.id" />
					<page:desc><img src="images/down.gif" border="0" /></page:desc>
					<page:asc><img src="images/up.gif" border="0" /></page:asc>
				</page:order></th>
				<th width="20%"><page:order order="title" style="text-decoration:none">
					<bean:message key="capexRequest.title" />
					<page:desc><img src="images/down.gif" border="0" /></page:desc>
					<page:asc><img src="images/up.gif" border="0" /></page:asc>
				</page:order></th>
				<th width="20%"><page:order order="capex_yearlyBudget_id" style="text-decoration:none">
					<bean:message key="capexRequest.capex.yearlyBudget.id" />
					<page:desc><img src="images/down.gif" border="0" /></page:desc>
					<page:asc><img src="images/up.gif" border="0" /></page:asc>
				</page:order></th>
				<th width="10%"><bean:message key="capexRequest.amount" /></th>
				<th width="10%"><bean:message key="capexRequest.requestDate" /></th>
				<th width="10%"><page:order order="type" style="text-decoration:none">
					<bean:message key="capexRequest.type" />
					<page:desc><img src="images/down.gif" border="0" /></page:desc>
					<page:asc><img src="images/up.gif" border="0" /></page:asc>
				</page:order></th>
				<th width="10%"><page:order order="status" style="text-decoration:none">
					<bean:message key="capexRequest.status" />
					<page:desc><img src="images/down.gif" border="0" /></page:desc>
					<page:asc><img src="images/up.gif" border="0" /></page:asc>
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

