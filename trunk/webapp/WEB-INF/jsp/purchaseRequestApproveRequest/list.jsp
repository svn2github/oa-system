<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>
<script type="text/javascript">
<!--

	function edit(approveRequestId,approverId) {
		window.location.href="viewPurchaseRequestApproveRequest.do?request_id="+approveRequestId+"&approver_id="+approverId;
	}
	
//-->
</script>
<html:form action="listPurchaseRequestApproveRequest">
	<html:hidden property="order" />
	<html:hidden property="descend" />

	<table width=100% border=0 cellpadding=4 cellspacing=0>
		<tr>
			<td class="bluetext"><bean:message key="purchaseRequestApproveRequest.search.no" />:</td>
			<td><html:text property="code"/></td>
			<td class="bluetext"><bean:message key="purchaseRequestApproveRequest.search.title" />:</td>
			<td colspan="4"><html:text property="title"/></td>
		</tr>
		<tr>
			<td class="bluetext"><bean:message key="purchaseRequestApproveRequest.search.approveStatus"/>:</td>
			<td>
			    <html:select property="approveStatus">
			    <html:option value=""><bean:message key="all.selectall"/></html:option>
			      <c:if test="${sessionScope.LOGIN_USER.locale=='en'}"><html:options collection = "X_APPROVESTATUSLIST" property = "enumCode" labelProperty = "engShortDescription"/></c:if>
			      <c:if test="${sessionScope.LOGIN_USER.locale!='en'}"><html:options collection = "X_APPROVESTATUSLIST" property = "enumCode" labelProperty = "chnShortDescription"/></c:if>
			    </html:select>
			</td>
	  		<td class="bluetext"><bean:message key="purchaseRequestApproveRequest.search.submitDate"/>:</td>
	  		<td>
	  		<table border=0 cellpadding=0 cellspacing=0>
		  		<tr>
		  		<td>
					<table border=0 cellpadding=0 cellspacing=0>
					<tr>
						<td>
							<html:text property="submitDateFrom" size="6"  maxlength="10" />
						</td>
						<td>
							<a onclick="event.cancelBubble=true;" href="javascript:showCalendar('dimg1',false,'submitDateFrom',null,null,'purchaseRequestApproveRequestQueryForm')"><IMG align="absMiddle" border="0" id="dimg1" src="images/datebtn.gif" ></A>
						</td>
					</tr>
					</table>
		  		</td>
		  		<td width="20" align="center">~</td>
		  		<td>
					<table border=0 cellpadding=0 cellspacing=0>
					<tr>
						<td>
							<html:text property="submitDateTo" size="6"  maxlength="10" />
						</td>
						<td>
							<a onclick="event.cancelBubble=true;" href="javascript:showCalendar('dimg2',false,'submitDateTo',null,null,'purchaseRequestApproveRequestQueryForm')"><IMG align="absMiddle" border="0" id="dimg2" src="images/datebtn.gif" ></A>
						</td>
					</tr>
					</table>
		  		</td>
		  		</tr>
	  		</table>
	  		</td>
			<td align="left">
				<html:submit><bean:message key="all.query"/></html:submit>
			</td>
		</tr>
	</table>
</html:form>
<hr />
<page:form action="listPurchaseRequestApproveRequest.do" method="post">
	<jsp:include page="../pageHead.jsp"/>
	<table class="data">
		<thead>
			<tr bgcolor="#9999ff">
				<th width="30%"><page:order order="purchaseRequest_capex_id" style="text-decoration:none">
					<bean:message key="purchaseRequest.id" />
					<page:desc><img src="images/down.gif" border="0" /></page:desc>
					<page:asc><img src="images/up.gif" border="0" /></page:asc>
				</page:order></th>
				<th width="15%">
					<bean:message key="purchaseRequest.title" />
				</th>
				<th width="15%">
					<bean:message key="purchaseRequest.requestor.id" />
				</th>
				<th width="10%">
					<bean:message key="purchaseRequest.amount" />
				</th>
				<th width="10%">
					<bean:message key="purchaseRequestApproveRequest.table.submitDate" />
				</th>
				<th width="20%">
					<bean:message key="purchaseRequestApproveRequest.table.status" />
				</th>
			</tr>

		</thead>

		<tbody id="datatable">
			<logic:iterate id="X_OBJECT" name="X_RESULTLIST">
			<tr>
				<td>
					<a href='javascript:edit("${X_OBJECT.purchaseRequest.approveRequestId}","${X_OBJECT.purchaseRequestApproveRequest.approver.id}")'>${X_OBJECT.purchaseRequest.id}</a>
				</td>
				<td>${X_OBJECT.purchaseRequest.title}</td>
				<td>${X_OBJECT.purchaseRequest.requestor.name}</td>
				<td align="right">${X_OBJECT.purchaseRequest.amount}</td>
				<td align="center">
					<bean:write name="X_OBJECT" property="purchaseRequest.requestDate" format="yyyy/MM/dd"/>
				</td>
				<td align="center">
				  	<span style="color:${X_OBJECT.purchaseRequestApproveRequest.status.color}">
				      <c:if test="${sessionScope.LOGIN_USER.locale=='en'}">${X_OBJECT.purchaseRequestApproveRequest.status.engShortDescription}</c:if>
				      <c:if test="${sessionScope.LOGIN_USER.locale!='en'}">${X_OBJECT.purchaseRequestApproveRequest.status.chnShortDescription}</c:if>
				    </span>  
				</td>
			</tr>	
			</logic:iterate>
		</tbody>
	</table>
</page:form>

<script type="text/javascript">
    applyRowStyle(document.all('datatable'));
</script>

