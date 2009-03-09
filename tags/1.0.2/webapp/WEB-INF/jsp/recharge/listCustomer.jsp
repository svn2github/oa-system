<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<script type="text/javascript" src="includes/table.js"></script>
<script type="text/javascript">
<!--
	function select(id, name, prefixColor, prefix) {
		var result = [];
		result['id'] = id;
		result['name'] = name;
		result['prefixColor'] = prefixColor;
		result['prefix'] = prefix;
		window.parent.returnValue = result;
		window.parent.close();
	}
	
//-->
</script>
<html:form action="${X_ACTION}">
<html:hidden property="order"/>
<html:hidden property="descend"/>
<html:hidden property="siteId"/>
<table width="100%" border="0" cellpadding="4" cellspacing="0">
  <tbody>
    <tr>
      <td class="bluetext"><bean:message key="customer.code"/>:</td>
      <td><html:text property="code" size="20"/></td>
      <td class="bluetext"><bean:message key="customer.description"/>:</td>
      <td><html:text property="description" size="20"/></td>
      <td align="center">
        <html:submit><bean:message key="all.query"/></html:submit>
      </td>
    </tr>
  </tbody>
</table>
</html:form>
<hr/>
<page:form action="${X_ACTION}" method="post">			
  <jsp:include page="../pageHead.jsp"/>
  <table class="data">
    <thead>
      <tr class="new_bg">
        <th width="30%">
          <page:order order="code" style="text-decoration:none">
            <bean:message key="customer.code"/>
            <page:desc><img src="images/down.gif" border="0"/></page:desc>
            <page:asc><img src="images/up.gif" border="0"/></page:asc>
          </page:order>
        </th>
	    <th width="60%">
          <page:order order="description" style="text-decoration:none">
    	      <bean:message key="customer.description"/>
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
        <jsp:include page="customerRow.jsp"/>
      </logic:iterate>
    </tbody>
  </table>
  <script type="text/javascript">
    applyRowStyle(document.all('datatable'));
  </script>
</page:form>		      
