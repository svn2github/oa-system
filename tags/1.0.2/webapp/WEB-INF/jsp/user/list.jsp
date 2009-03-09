<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<script language="javascript" src="includes/table.js"></script>
<script type="text/javascript" src="includes/selection.js"></script>
<script type="text/javascript">
<!--
	var siteArray = [];
	var departmentArray = [];

<c:if test="${X_GLOBAL}">
	siteArray[siteArray.length] = new Option('<bean:message key="site.allSite"/>', '');
	a = [];
	a[a.length] = new Option('<bean:message key="department.selectSite"/>', '');
	departmentArray[departmentArray.length] = a;
</c:if>	
<logic:iterate id="s" name="X_SITELIST">
	siteArray[siteArray.length] = new Option("${s.name}", "${s.id}");
	a = [];
	a[a.length] = new Option('<bean:message key="department.allDepartment"/>', '');
  <logic:notEmpty name="s" property="departments">
    <logic:iterate id="d" name="s" property="departments">
	a[a.length] = new Option("${d.indentName}", "${d.id}");
    </logic:iterate>
  </logic:notEmpty>
	departmentArray[departmentArray.length] = a;
</logic:iterate>

	function add() {
		v = window.showModalDialog(
			'showDialog.do?title=user.new.title&newUser.do', 
			null, 'dialogWidth:400px;dialogHeight:450px;status:no;help:no;scroll:no');
		if (v) {
			var table = document.all('datatable');
			appendRow(table, v);
		    applyRowStyle(table);
		};
	}

	function edit(id) {
		v = window.showModalDialog(
			'showDialog.do?title=user.edit.title&editUser.do?id=' + id, 
			null, 'dialogWidth:400px;dialogHeight:450px;status:no;help:no;scroll:no');
		if (v) {
			updateRow(document.all('r' + id), v);
		};
	}
//-->
</script>
<c:choose>
	<c:when test="${X_GLOBAL}"><c:set var="x_action" value="listGlobalUser"/></c:when>
	<c:otherwise><c:set var="x_action" value="listUser"/></c:otherwise>
</c:choose>
<html:form action="${x_action}">
<html:hidden property="order"/>
<html:hidden property="descend"/>
<table width="100%" border="0" cellpadding="4" cellspacing="0">
  <tbody>
    <tr>
      <td class="bluetext"><bean:message key="userDepartment.site"/>:</td>
      <td><html:select property="siteId" onchange="cascadeUpdate(this, departmentId, departmentArray);"></html:select></td>
      <td class="bluetext"><bean:message key="user.loginName"/>:</td>
      <td><html:text property="loginName" size="20"/></td>
    </tr>
    <tr>
      <td class="bluetext"><bean:message key="userDepartment.department"/>:</td>
      <td><html:select property="departmentId"></html:select></td>
      <td class="bluetext"><bean:message key="user.name"/>:</td>
      <td><html:text property="name" size="20"/></td>
    </tr>
    <tr>
      <td class="bluetext"><bean:message key="site.enabled"/>:</td>
      <td>
        <html:select property="enabled">
        <html:option value=""><bean:message key="all.all"/></html:option>
		<c:if test="${sessionScope.LOGIN_USER.locale=='en'}">
			<html:options collection="X_ENABLEDDISABLEDLIST"
				property="enumCode" labelProperty="engShortDescription" />
		</c:if>
		<c:if test="${sessionScope.LOGIN_USER.locale!='en'}">
			<html:options collection="X_ENABLEDDISABLEDLIST"
				property="enumCode" labelProperty="chnShortDescription" />
		</c:if>
        </html:select>
      </td>
      <td></td>
      <td align="center">
        <html:submit><bean:message key="all.query"/></html:submit>
        <input type="button" value="<bean:message key="all.new"/>" onclick="add();"/>
      </td>
    </tr>
  </tbody>
</table>
</html:form>
<script type="text/javascript">
	initCascadeSelect(userQueryForm.siteId, userQueryForm.departmentId, siteArray, departmentArray, "${userQueryForm.siteId}", "${userQueryForm.departmentId}");
</script><hr/>
<page:form action="${x_action}" method="post">			
  <jsp:include page="../pageHead.jsp"/>
  <table class="data">
    <thead>
      <tr class="new_bg">
        <th>
          <page:order order="loginName" style="text-decoration:none">
            <bean:message key="user.loginName"/>
            <page:desc><img src="images/down.gif" border="0"/></page:desc>
            <page:asc><img src="images/up.gif" border="0"/></page:asc>
          </page:order>
        </th>
	    <th>
          <page:order order="name" style="text-decoration:none">
    	      <bean:message key="user.name"/>
            <page:desc><img src="images/down.gif" border="0"/></page:desc>
            <page:asc><img src="images/up.gif" border="0"/></page:asc>
          </page:order>
	    </th>
	    <th>
          <page:order order="email" style="text-decoration:none">
            <bean:message key="user.email"/>
            <page:desc><img src="images/down.gif" border="0"/></page:desc>
            <page:asc><img src="images/up.gif" border="0"/></page:asc>
          </page:order>
	    </th>
	    <th>
          <page:order order="telephone" style="text-decoration:none">
            <bean:message key="user.telephone"/>
            <page:desc><img src="images/down.gif" border="0"/></page:desc>
            <page:asc><img src="images/up.gif" border="0"/></page:asc>
          </page:order>
	    </th>
	    <th>
          <page:order order="enabled" style="text-decoration:none">
            <bean:message key="user.enabled"/>
            <page:desc><img src="images/down.gif" border="0"/></page:desc>
            <page:asc><img src="images/up.gif" border="0"/></page:asc>
          </page:order>
	    </th>
	    <th><bean:message key="user.site&department"/></th>
	    <th><bean:message key="user.grantRole"/></th>
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
  <jsp:include page="../pageTail.jsp"/>
</page:form>		      
