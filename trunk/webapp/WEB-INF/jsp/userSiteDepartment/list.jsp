<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<script language="javascript" src="includes/table.js"></script>
<script type="text/javascript">
<!--
	function addUserSite() {
		v = window.showModalDialog(
			'showDialog.do?title=userSite.new.title&newUserSite.do?user_id=${X_USER.id}', 
			null, 'dialogWidth:400px;dialogHeight:220px;status:no;help:no;scroll:no');
		if (v) {
			var table = document.all('datatable');
			appendRow(table, v);
		    applyRowStyle(table);
		};
	}

	function editUserSite(id) {
		v = window.showModalDialog(
			'showDialog.do?title=userSite.edit.title&editUserSite.do?user_id=${X_USER.id}&site_id=' + id, 
			null, 'dialogWidth:400px;dialogHeight:220px;status:no;help:no;scroll:no');
		if (v) {
			updateRow(document.all('datatable').all('r' + id), v);
		};
	}

	function deleteUserSite(id) {
		var v = window.showModalDialog('showDialog.do?title=userSite.delete.title&confirmOperationDialog.do?message=userSite.delete.message&deleteUserSite.do?user_id=${X_USER.id}&site_id=' + id, null, 'dialogWidth:300px;dialogHeight:143px;status:no;help:no;scroll:no');
		if (v) {
			window.location.reload(true);
		}
	}

	function addUserDepartment() {
		v = window.showModalDialog(
			'showDialog.do?title=userDepartment.new.title&newUserDepartment.do?user_id=${X_USER.id}', 
			null, 'dialogWidth:400px;dialogHeight:220px;status:no;help:no;scroll:no');
		if (v) {
			var table = document.all('datatable2');
			appendRow(table, v);
		    applyRowStyle(table);
		};
	}

	function editUserDepartment(id) {
		v = window.showModalDialog(
			'showDialog.do?title=userDepartment.edit.title&editUserDepartment.do?user_id=${X_USER.id}&department_id=' + id, 
			null, 'dialogWidth:400px;dialogHeight:220px;status:no;help:no;scroll:no');
		if (v) {
			updateRow(document.all('datatable2').all('r' + id), v);
		};
	}

	function deleteUserDepartment(id) {
		var v = window.showModalDialog('showDialog.do?title=userDepartment.delete.title&confirmOperationDialog.do?message=userDepartment.delete.message&deleteUserDepartment.do?user_id=${X_USER.id}&department_id=' + id, null, 'dialogWidth:300px;dialogHeight:143px;status:no;help:no;scroll:no');
		if (v) {
			deleteRow(document.all('datatable2').all('r' + id));
		}
	}
//-->
</script>
<c:choose>
	<c:when test="${X_GLOBAL}"><c:set var="x_action" value="listGlobalUser"/></c:when>
	<c:otherwise><c:set var="x_action" value="listUser"/></c:otherwise>
</c:choose>
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
<hr/>
<div><h3 class="formtitle"><bean:message key="userSite" /></h3></div>
<div>
  <input type="button" value="<bean:message key="all.new"/>&nbsp;<bean:message key="user.site"/>" onclick="addUserSite();" />
  <input type="button" value="<bean:message key="all.back"/>" onclick='window.location.href="${x_action}.do";' />
</div>
<table class="data">
  <thead>
    <tr class="new_bg">
      <th width="45%"><bean:message key="userSite.site"/></th>
      <th width="45%"><bean:message key="userSite.travelGroup"/></th>
      <th width="5%"></th>
      <th width="5%"></th>
    </tr>
  </thead>
  <tbody id="datatable">
    <logic:iterate id="X_OBJECT" name="X_SITELIST">
      <bean:define id="X_OBJECT" toScope="request" name="X_OBJECT"/>
      <jsp:include page="siteRow.jsp"/>
    </logic:iterate>
  </tbody>
</table>
<script type="text/javascript">
  applyRowStyle(document.all('datatable'));
</script>
<hr/>
<div><h3 class="formtitle"><bean:message key="userDepartment" /></h3></div>
<div>
  <input type="button" value="<bean:message key="all.new"/>&nbsp;<bean:message key="user.department"/>" onclick="addUserDepartment();" />
  <input type="button" value="<bean:message key="all.back"/>" onclick='window.location.href="${x_action}.do";' />
</div>
<table class="data">
  <thead>
    <tr class="new_bg">
      <th width="30%"><bean:message key="userDepartment.site"/></th>
      <th width="30%"><bean:message key="userDepartment.department"/></th>
      <th width="30%"><bean:message key="userDepartment.title"/></th>
      <th width="5%"></th>
      <th width="5%"></th>
    </tr>
  </thead>
  <tbody id="datatable2">
    <logic:iterate id="X_OBJECT" name="X_DEPARTMENTLIST">
      <bean:define id="X_OBJECT" toScope="request" name="X_OBJECT"/>
      <jsp:include page="departmentRow.jsp"/>
    </logic:iterate>
  </tbody>
</table>
<script type="text/javascript">
  applyRowStyle(document.all('datatable2'));
</script>
