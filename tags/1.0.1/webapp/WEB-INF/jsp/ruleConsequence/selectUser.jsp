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

	function select(id, name) {
		var result = [];
		result['id'] = id;
		result['name'] = name;
<c:choose>
	<c:when test="${param.iniframe==null}">
		window.parent.returnValue = result;
		window.parent.close();
	</c:when>
	<c:otherwise>
		window.parent.selectUser(result);
	</c:otherwise>
</c:choose>
	}
//-->
</script>
<html:form action="select${X_RULETYPE.prefixUrl}ConsequenceUser">
<html:hidden property="order"/>
<html:hidden property="descend"/>
<c:if test="${param.iniframe}">
<input type="hidden" name="iniframe" value="true"/>
</c:if>
<table width="100%" border="0" cellpadding="4" cellspacing="0">
  <tbody>
    <tr>
      <td class="bluetext"><bean:message key="userDepartment.site"/>:</td>
      <td><html:select property="siteId" onchange="cascadeUpdate(this, departmentId, departmentArray);"></html:select></td>
      <td class="bluetext"><bean:message key="userDepartment.department"/>:</td>
      <td colspan="2"><html:select property="departmentId"></html:select></td>
    </tr>
    <tr>
      <td class="bluetext"><bean:message key="user.loginName"/>:</td>
      <td><html:text property="loginName" size="8"/></td>
      <td class="bluetext"><bean:message key="user.name"/>:</td>
      <td><html:text property="name" size="20"/></td>
      <td align="center">
        <html:submit><bean:message key="all.query"/></html:submit>
      </td>
    </tr>
  </tbody>
</table>
</html:form>
<script type="text/javascript">
	initCascadeSelect(userQueryForm.siteId, userQueryForm.departmentId, siteArray, departmentArray, "${userQueryForm.siteId}", "${userQueryForm.departmentId}");
</script><hr/>
<page:form action="select${X_RULETYPE.prefixUrl}ConsequenceUser" method="post">			
  <jsp:include page="../pageHead.jsp"/>
  <table class="data">
    <thead>
      <tr bgcolor="#9999ff">
        <th width="20%">
          <page:order order="loginName" style="text-decoration:none">
            <bean:message key="user.loginName"/>
            <page:desc><img src="images/down.gif" border="0"/></page:desc>
            <page:asc><img src="images/up.gif" border="0"/></page:asc>
          </page:order>
        </th>
	    <th width="20%">
          <page:order order="name" style="text-decoration:none">
    	      <bean:message key="user.name"/>
            <page:desc><img src="images/down.gif" border="0"/></page:desc>
            <page:asc><img src="images/up.gif" border="0"/></page:asc>
          </page:order>
	    </th>
	    <th width="25%">
          <page:order order="email" style="text-decoration:none">
            <bean:message key="user.email"/>
            <page:desc><img src="images/down.gif" border="0"/></page:desc>
            <page:asc><img src="images/up.gif" border="0"/></page:asc>
          </page:order>
	    </th>
	    <th width="25%">
          <page:order order="telephone" style="text-decoration:none">
            <bean:message key="user.telephone"/>
            <page:desc><img src="images/down.gif" border="0"/></page:desc>
            <page:asc><img src="images/up.gif" border="0"/></page:asc>
          </page:order>
	    </th>
        <th width="10%">
        </th>
      </tr>
    </thead>
    <tbody id="datatable">
      <logic:iterate id="X_OBJECT" name="X_RESULTLIST">
        <bean:define id="X_OBJECT" toScope="request" name="X_OBJECT"/>
        <jsp:include page="userRow.jsp"/>
      </logic:iterate>
    </tbody>
  </table>
  <script type="text/javascript">
    applyRowStyle(document.all('datatable'));
  </script>
</page:form>		      
