<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ page import="net.sourceforge.model.metadata.ApproveStatus"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<logic:notEmpty name="X_APPROVELIST">
<script type="text/javascript">
<!--

	function doApprove(form, validateFunction) {
		if (validateFunction) {
			if (!validateFunction(form)) {
				return;
			}
		}
		v = window.showModalDialog(
			"showDialog.do?title=approveRequest.approve&approveRequestAddComment.do", 
			null, 'dialogWidth:450px;dialogHeight:250px;status:no;help:no;scroll:no');
		if (v) {
			form.comment.value = v[0];
			form.submit();
		}
	}
	
	function doReject(form, rejectAction) {
		
		v = window.showModalDialog(
			"showDialog.do?title=approveRequest.reject&approveRequestAddComment.do", 
			null, 'dialogWidth:450px;dialogHeight:250px;status:no;help:no;scroll:no');
		if (v) {
			form.comment.value = v[0];
			form.action = rejectAction;
			form.submit();
		}
	}
	
	function doBack() {
		window.location.href="list${X_APPROVEACTION}ApproveRequest.do";
	}
	
//-->
</script>

<table width="100%">
<tr>
<td><font color="blue"><h3><bean:message key="approveRequest.approveList.title" /></h3></font></td>
</tr>
</table>
<table width="100%" class="dataList">
<thead>
	<tr >
		<th width="15px" align="center">
			#
		</th>
		<th width="150px">
			<bean:message key="approveRequest.approveList.approver" />
		</th>
		<th width="100px">
			<bean:message key="approveRequest.approveList.status" />
		</th>
		<th width="80px">
			<bean:message key="approveRequest.approveList.date" />
		</th>
		<th>
			<bean:message key="approveRequest.approveList.comment" />
		</th>
	</tr>
<thead>
<tbody id="approveTable">

<%request.setAttribute("waitForApprove",ApproveStatus.WAITING_FOR_APPROVE);%>
<c:set var="foundProcess" value="false" />
<logic:iterate id="X_OBJECT" name="X_APPROVELIST">
<c:if test="${foundProcess!=false}">
	<c:set var="trbgcolor" value="" />
</c:if>
<c:if test="${foundProcess==false}">
	<c:if test="${X_OBJECT.status==waitForApprove}">
		<c:set var="trbgcolor" value="#FFFF99" />
		<c:set var="foundProcess" value="true" />
	</c:if>
	<c:if test="${X_OBJECT.status!=waitForApprove}">
		<c:set var="trbgcolor" value="" />
	</c:if>
</c:if>


<tr bgcolor="${trbgcolor}">
	<td align="center">${X_OBJECT.seq}</td>
	<td>
	<c:if test="${X_OBJECT.actualApprover.id!=null}">
		<c:if test="${X_OBJECT.approver.id==X_OBJECT.actualApprover.id}">
			${X_OBJECT.approver.name}
		</c:if>
		<c:if test="${X_OBJECT.approver.id!=X_OBJECT.actualApprover.id}">
			${X_OBJECT.approver.name}/${X_OBJECT.actualApprover.name}
		</c:if>
	</c:if>
	<c:if test="${X_OBJECT.actualApprover.id==null}">
		${X_OBJECT.approver.name}
	</c:if>
	</td>
	<td >
		<div style="color:${X_OBJECT.status.color}">
      <c:if test="${sessionScope.LOGIN_USER.locale=='en'}">${X_OBJECT.status.engShortDescription}</c:if>
      <c:if test="${sessionScope.LOGIN_USER.locale!='en'}">${X_OBJECT.status.chnShortDescription}</c:if>
      </div>
	</td>
	<td align="center">
		<bean:write name="X_OBJECT" property="approveDate" format="yyyy/MM/dd"/>
	</td>
	<td>
		${X_OBJECT.comment}
	</td>
</tr>
</logic:iterate>
</tbody>
</table>
<c:if test="${foundProcess==false}">
<script type="text/javascript">
    applyRowStyle(document.all('approveTable'));
</script>
</c:if>
</logic:notEmpty>

