<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<script language="javascript" src="includes/table.js"></script>
<script type="text/javascript">
<!--
	function addUserRole() {
		v = window.showModalDialog(
			'showDialog.do?title=userRole.new.title&newUserRole.do?user_id=${X_USER.id}', 
			null, 'dialogWidth:500px;dialogHeight:280px;status:no;help:no;scroll:no');
		if (v) {
			var table = document.all('datatable');
			appendRow(table, v);
		    applyRowStyle(table);
		};
	}

	function editUserRole(id) {
		v = window.showModalDialog(
			'showDialog.do?title=userRole.edit.title&editUserRole.do?&id=' + id, 
			null, 'dialogWidth:500px;dialogHeight:280px;status:no;help:no;scroll:no');
		if (v) {
			updateRow(document.all('r' + id), v);
		};
	}

	function deleteUserRole(id) {
		var v = window.showModalDialog('showDialog.do?title=userRole.delete.title&confirmOperationDialog.do?message=userRole.delete.message&deleteUserRole.do?id=' + id, null, 'dialogWidth:300px;dialogHeight:143px;status:no;help:no;scroll:no');
		if (v) {
			deleteRow(document.all('r' + id));
		}
	}
//-->
</script>
<table width="100%" border="0" cellpadding="4" cellspacing="0">
  <tbody>
    <tr>
      <td class="bluetext"><bean:message key="user.loginName"/>:</td>
      <td>${X_USER.loginName}</td>
      <td class="bluetext"><bean:message key="user.name"/>:</td>
      <td>${X_USER.name}</td>
    </tr>
    <tr>
      <td class="bluetext"><bean:message key="user.email"/>:</td>
      <td>${X_USER.email}</td>
      <td class="bluetext"><bean:message key="user.gender"/>:</td>
      <td>
        <span style="color:${X_USER.gender.color}">
          <c:if test="${sessionScope.LOGIN_USER.locale=='en'}">${X_USER.gender.engShortDescription}</c:if>
          <c:if test="${sessionScope.LOGIN_USER.locale!='en'}">${X_USER.gender.chnShortDescription}</c:if>
        </span>
      </td>
    </tr>
    <tr>
      <td class="bluetext"><bean:message key="user.telephone"/>:</td>
      <td>${X_USER.telephone}</td>
      <td class="bluetext"><bean:message key="user.enabled"/>:</td>
      <td>
        <span style="color:${X_USER.enabled.color}">
          <c:if test="${sessionScope.LOGIN_USER.locale=='en'}">${X_USER.enabled.engShortDescription}</c:if>
          <c:if test="${sessionScope.LOGIN_USER.locale!='en'}">${X_USER.enabled.chnShortDescription}</c:if>
        </span>
      </td>
    </tr>
    <tr>
      <td class="bluetext"><bean:message key="user.primarySite"/>:</td>
      <td span="3">${X_USER.primarySite.name}</td>
    </tr>
  </tbody>
</table>
<div style="margin-top:10px"><h3 class="formtitle"><bean:message key="userDepartment"/></h3></div>
<table class="data">
  <thead>
    <tr class="new_bg">
      <th width="35%"><bean:message key="userDepartment.site"/></th>
      <th width="35%"><bean:message key="userDepartment.department"/></th>
      <th width="30%"><bean:message key="userDepartment.title"/></th>
    </tr>
  </thead>
  <tbody id="datatableDepartment">
    <logic:iterate id="X_OBJECT" name="X_DEPARTMENTLIST">
    <tr>
      <td>${X_OBJECT.department.site.name}</td>
      <td>${X_OBJECT.department.name}</td>
      <td>${X_OBJECT.title}</td>
    </tr>
    </logic:iterate>
  </tbody>
</table>
<script type="text/javascript">
  applyRowStyle(document.all('datatableDepartment'));
</script>
<hr/>
<div><h3 class="formtitle"><bean:message key="userRole.grantedRole"/></h3></div>
<input type="button" value="<bean:message key="all.new"/>" onclick="addUserRole();" />
<c:choose>
	<c:when test="${X_GLOBAL}"><c:set var="x_action" value="listGlobalUser"/></c:when>
	<c:otherwise><c:set var="x_action" value="listUser"/></c:otherwise>
</c:choose>
<input type="button" value="<bean:message key="all.back"/>" onclick='window.location.href="${x_action}.do";'/>
<form action="listUserRole" method="get">
<input type="hidden" name="userId" value="${X_USER.id}"/>
<table class="data">
  <thead>
    <tr class="new_bg">
      <th width="30%"><bean:message key="userRole.role"/></th>
      <th width="30%"><bean:message key="userRole.grantedSite"/></th>
      <th width="30%"><bean:message key="userRole.grantedDepartment"/></th>
      <th width="5%"></th>
      <th width="5%"></th>
    </tr>
  </thead>
  <tbody id="datatable">
    <logic:iterate id="X_OBJECT" name="X_RESULTLIST">
      <bean:define id="X_OBJECT" toScope="request" name="X_OBJECT"/>
      <jsp:include page="row.jsp"/>
    </logic:iterate>
  </tbody>
</table>
<script type="text/javascript">
  applyRowStyle(document.all('datatable'));
</script>
</form>
