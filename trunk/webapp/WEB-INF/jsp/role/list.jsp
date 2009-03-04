<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>
<script language="javascript" src="includes/table.js"></script>
<script type="text/javascript">
<!--
	function add() {
		v = window.showModalDialog(
			'showDialog.do?title=role.new.title&newRole.do', 
			null, 'dialogWidth:400px;dialogHeight:400px;status:no;help:no;scroll:no');
		if (v) {
			var table = document.all('datatable');
			appendRow(table, v);
		    applyRowStyle(table);
		};
	}

	function edit(id) {
		v = window.showModalDialog(
			'showDialog.do?title=role.edit.title&editRole.do?id=' + id, 
			null, 'dialogWidth:400px;dialogHeight:400px;status:no;help:no;scroll:no');
		if (v) {
			updateRow(document.all('r' + id), v);
		};
	}
	
//-->
</script>
<html:form action="listRole">
<html:hidden property="order"/>
<html:hidden property="descend"/>
<table width="100%" border="0" cellpadding="4" cellspacing="0">
  <tbody>
    <tr>
      <td class="bluetext"><bean:message key="role.name"/>:</td>
      <td><html:text property="name"/></td>
      <td class="bluetext"><bean:message key="role.type"/>:</td>
      <td>
        <html:select property="type.value">
          <c:if test="${sessionScope.LOGIN_USER.locale=='en'}"><html:optionsCollection property="type.list" value="enumCode" label="engShortDescription"/></c:if>
          <c:if test="${sessionScope.LOGIN_USER.locale!='en'}"><html:optionsCollection property="type.list" value="enumCode" label="chnShortDescription"/></c:if>
        </html:select>
      </td>
      <td align="center">
        <html:submit><bean:message key="all.query"/></html:submit>
        <input type="button" value="<bean:message key="all.new"/>" onclick="add();"/>
      </td>
    </tr>
  </tbody>
</table>
</html:form>
<hr/>
<page:form action="listRole.do" method="post">			
  <jsp:include page="../pageHead.jsp"/>
  <table class="data">
    <thead>
      <tr class="new_bg">
	    <th width="50%">
          <page:order order="name" style="text-decoration:none">
    	    <bean:message key="role.name"/>
            <page:desc><img src="images/down.gif" border="0"/></page:desc>
            <page:asc><img src="images/up.gif" border="0"/></page:asc>
          </page:order>
	    </th>
	    <th width="20%">
          <page:order order="type" style="text-decoration:none">
            <bean:message key="role.type"/>
            <page:desc><img src="images/down.gif" border="0"/></page:desc>
            <page:asc><img src="images/up.gif" border="0"/></page:asc>
          </page:order>
	    </th>
	    <th width="30%"><bean:message key="role.function"/></th>
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
