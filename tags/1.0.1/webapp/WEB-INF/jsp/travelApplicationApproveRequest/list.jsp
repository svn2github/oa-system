<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>
<script language="javascript" src="includes/table.js"></script>
<script type="text/javascript">
<!--

	function edit(approveRequestId,approverId) {
		window.location.href="viewTravelApplicationApproveRequest.do?request_id="+approveRequestId+"&approver_id="+approverId;
	}
	
//-->
</script>
<html:form action="listTravelApplicationApproveRequest">
	<html:hidden property="order" />
	<html:hidden property="descend" />

	<table width=100% border=0 cellpadding=4 cellspacing=0>
		<tr>
			<td class="bluetext"><bean:message key="travelApplicationApproveRequest.search.no" />:</td>
			<td><html:text property="code"/></td>
			<td class="bluetext"><bean:message key="travelApplicationApproveRequest.search.title" />:</td>
			<td colspan="2"><html:text property="title"/></td>
		</tr>
		<tr>
			<td class="bluetext"><bean:message key="travelApplicationApproveRequest.search.approveStatus"/>:</td>
			<td>
			    <html:select property="approveStatus">
		    	<html:option value=""><bean:message key="all.selectall"/></html:option>
			      <c:if test="${sessionScope.LOGIN_USER.locale=='en'}"><html:options collection = "X_APPROVESTATUSLIST" property = "enumCode" labelProperty = "engShortDescription"/></c:if>
			      <c:if test="${sessionScope.LOGIN_USER.locale!='en'}"><html:options collection = "X_APPROVESTATUSLIST" property = "enumCode" labelProperty = "chnShortDescription"/></c:if>
			    </html:select>
			</td>
	  		<td class="bluetext"><bean:message key="travelApplicationApproveRequest.search.submitDate"/>:</td>
	  		<td colspan="2">
	  		<table border=0 cellpadding=0 cellspacing=0>
		  		<tr>
		  		<td>
					<table border=0 cellpadding=0 cellspacing=0>
					<tr>
						<td>
							<html:text property="submitDateFrom" size="6"  maxlength="10" />
						</td>
						<td>
							<a onclick="event.cancelBubble=true;" href="javascript:showCalendar('dimg1',false,'submitDateFrom',null,null,'travelApplicationApproveRequestQueryForm')"><IMG align="absMiddle" border="0" id="dimg1" src="images/datebtn.gif" ></A>
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
							<a onclick="event.cancelBubble=true;" href="javascript:showCalendar('dimg2',false,'submitDateTo',null,null,'travelApplicationApproveRequestQueryForm')"><IMG align="absMiddle" border="0" id="dimg2" src="images/datebtn.gif" ></A>
						</td>
					</tr>
					</table>
		  		</td>
		  		</tr>
	  		</table>
	  		</td>
		</tr>
		<tr>
			<td class="bluetext"><bean:message key="travelApplicationApproveRequest.search.urgent" />:</td>
			<td >
				<html:select property="urgent">
					<html:option value=""><bean:message key="all.all"/></html:option>
					<html:options collection="x_urgentList" property="enumCode" labelProperty="${x_lang}ShortDescription" />
				</html:select>
			</td>
			<td class="bluetext"><bean:message key="travelApplicationApproveRequest.search.requestor" />:</td>
			<td>
				<html:text property="requestor"/>
			</td>
			<td align="left">
				<html:submit><bean:message key="all.query"/></html:submit>
			</td>
		</tr>
	</table>
</html:form>
<hr />
<page:form action="listTravelApplicationApproveRequest.do" method="post">
	<jsp:include page="../pageHead.jsp"/>
	<table class="data">
		<thead>
			<tr bgcolor="#9999ff">
				<th width="10%"><page:order order="ta_code" style="text-decoration:none">
					<bean:message key="travelApplicationApproveRequest.table.no" />
					<page:desc>
						<img src="images/down.gif" border="0" />
					</page:desc>
					<page:asc>
						<img src="images/up.gif" border="0" />
					</page:asc>
				</page:order></th>
				<th width="20%">
					<bean:message key="travelApplicationApproveRequest.table.title" />
				</th>
				<th width="10%">
					<bean:message key="travelApplicationApproveRequest.table.destination" />
				</th>
				<th width="10%">
					<bean:message key="travelApplicationApproveRequest.table.fromDate" />
				</th>
				<th width="10%">
					<bean:message key="travelApplicationApproveRequest.table.toDate" />
				</th>
				<th width="10%">
					<bean:message key="travelApplicationApproveRequest.table.submitDate" />
				</th>
				<th width="10%">
					<bean:message key="travelApplicationApproveRequest.table.requestor" />
				</th>
				<th width="10%">
					<bean:message key="travelApplicationApproveRequest.table.urgent" />
				</th>
				<th width="10%">
					<bean:message key="travelApplicationApproveRequest.table.status" />
				</th>
			</tr>

		</thead>

		<tbody id="datatable">
			<logic:iterate id="X_OBJECT" name="X_RESULTLIST">
			<tr>
				<td>
					<a href='javascript:edit("${X_OBJECT.travelApplication.approveRequestId}","${X_OBJECT.travelApplicationApproveRequest.approver.id}")'>${X_OBJECT.travelApplication.id}</a>
				</td>
				<td>${X_OBJECT.travelApplication.title}</td>
				<td>	
					<c:if test="${sessionScope.LOGIN_USER.locale=='en'}">${X_OBJECT.travelApplication.toCity.engName}</c:if>
					<c:if test="${sessionScope.LOGIN_USER.locale!='en'}">${X_OBJECT.travelApplication.toCity.chnName}</c:if>
				</td>
				<td>
					<bean:write name="X_OBJECT" property="travelApplication.fromDate" format="yyyy/MM/dd"/>
				</td>
				<td>	
					<bean:write name="X_OBJECT" property="travelApplication.toDate" format="yyyy/MM/dd"/>
				</td>
				<td>
					<bean:write name="X_OBJECT" property="travelApplication.requestDate" format="yyyy/MM/dd"/>
				</td>
				<td>	
					${X_OBJECT.travelApplication.requestor.name}
				</td>
				<td>
				  	<span style="color:${X_OBJECT.travelApplication.urgent.color}">
				      <c:if test="${sessionScope.LOGIN_USER.locale=='en'}">${X_OBJECT.travelApplication.urgent.engShortDescription}</c:if>
				      <c:if test="${sessionScope.LOGIN_USER.locale!='en'}">${X_OBJECT.travelApplication.urgent.chnShortDescription}</c:if>
				    </span>  
				 </td>
				<td>
				  	<span style="color:${X_OBJECT.travelApplicationApproveRequest.status.color}">
				      <c:if test="${sessionScope.LOGIN_USER.locale=='en'}">${X_OBJECT.travelApplicationApproveRequest.status.engShortDescription}</c:if>
				      <c:if test="${sessionScope.LOGIN_USER.locale!='en'}">${X_OBJECT.travelApplicationApproveRequest.status.chnShortDescription}</c:if>
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

