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

	function select(id, name) {
		var result = [];
		result['id'] = id;
		result['name'] = name;
		window.parent.returnValue = result;
		window.parent.close();
	}
//-->
</script>
<html:form action="selectInspector_site">
<html:hidden property="order"/>
<html:hidden property="descend"/>
<html:hidden property="siteId"/>
<table width="100%" border="0" cellpadding="4" cellspacing="0">
  <tbody>
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
<hr/>
<page:form action="selectInspector_site" method="post">			
  <jsp:include page="../pageHead.jsp"/>
  <table class="data">
    <thead>
      <tr class="new_bg">
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
        <jsp:include page="selectRow.jsp"/>
      </logic:iterate>
    </tbody>
  </table>
  <script type="text/javascript">
    applyRowStyle(document.all('datatable'));
  </script>
</page:form>		      
