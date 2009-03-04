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
			'showDialog.do?title=site.new.title&newSite.do', 
			null, 'dialogWidth:500px;dialogHeight:440px;status:no;help:no;scroll:no');
		if (v) {
			var table = document.all('datatable');
			appendRow(table, v);
		    applyRowStyle(table);
		};
	}

	function edit(id) {
		v = window.showModalDialog(
			'showDialog.do?title=site.edit.title&editSite.do?id=' + id, 
			null, 'dialogWidth:500px;dialogHeight:440px;status:no;help:no;scroll:no');
		if (v) {
			updateRow(document.all('r' + id), v);
		};
	}
//-->
</script>
<html:form action="listSite">
<html:hidden property="order"/>
<html:hidden property="descend"/>
<table width="100%" border="0" cellpadding="4" cellspacing="0">
  <tbody>
    <tr>
      <td class="bluetext"><bean:message key="site.name"/>:</td>
      <td><html:text property="name" size="20"/></td>
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
      <td align="center">
        <html:submit><bean:message key="all.query"/></html:submit>
        <input type="button" value="<bean:message key="all.new"/>" onclick="add();"/>
      </td>
    </tr>
  </tbody>
</table>
</html:form>
<hr/>
<page:form action="listSite.do" method="post">
  <jsp:include page="../pageHead.jsp"/>
  <table class="data">
    <thead>
      <tr bgcolor="#9999ff">
        <th width="80%">
          <page:order order="name" style="text-decoration:none">
            <bean:message key="site.name"/>
            <page:desc><img src="images/down.gif" border="0"/></page:desc>
            <page:asc><img src="images/up.gif" border="0"/></page:asc>
          </page:order>
        </th>
	    <th width="20%">
          <page:order order="enabled" style="text-decoration:none">
            <bean:message key="site.enabled"/>
            <page:desc><img src="images/down.gif" border="0"/></page:desc>
            <page:asc><img src="images/up.gif" border="0"/></page:asc>
          </page:order>
	    </th>
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
