<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>
<script type="text/javascript">
<!--
	function add() {
		v = window.showModalDialog(
			'showDialog.do?title=rule.new${X_RULETYPE.prefixUrl}.title&new${X_RULETYPE.prefixUrl}Rule.do?site_id=' + ruleQueryForm.elements['site.value'].value, 
			null, 'dialogWidth:400px;dialogHeight:200px;status:no;help:no;scroll:no');
		if (v) {
			var table = document.all('datatable');
			appendRow(table, v);
		    applyRowStyle(table);
		};
	}

	function edit(id) {
		v = window.showModalDialog(
			'showDialog.do?title=rule.edit${X_RULETYPE.prefixUrl}.title&edit${X_RULETYPE.prefixUrl}Rule.do?id=' + id, 
			null, 'dialogWidth:400px;dialogHeight:620px;status:no;help:no;scroll:no');
		if (v) {
			updateRow(document.all('datatable').all('r' + id), v);
		};
	}
	
	function del(id) {
		var v = window.showModalDialog(
		    'showDialog.do?title=rule.delete${X_RULETYPE.prefixUrl}.title&confirmOperationDialog.do?message=rule.delete${X_RULETYPE.prefixUrl}.message&delete${X_RULETYPE.prefixUrl}Rule.do?id=' + id, 
		    null, 'dialogWidth:400px;dialogHeight:200px;status:no;help:no;scroll:no');
		if (v) {
			var table = document.all('datatable');
			deleteRow(table.all('r' + id));
		    applyRowStyle(table);
		    deleteRow(table.all('r' + id));
		    applyRowStyle(table);
		}
	}

	function editCondition(id) {
		window.location.href = "list${X_RULETYPE.prefixUrl}RuleCondition.do?id=" + id;
	}
	
//-->
</script>
<html:form action="list${X_RULETYPE.prefixUrl}Rule">
<html:hidden property="order"/>
<html:hidden property="descend"/>
<table width="100%" border="0" cellpadding="4" cellspacing="0">
  <tbody>
    <tr>
      <td class="bluetext"><bean:message key="rule.site"/>:</td>
      <td ><html:select property="site.value" onchange="ruleQueryForm.submit();"><html:optionsCollection property="site.list" value="id" label="name"/></html:select></td>
      <td class="bluetext"><bean:message key="user.loginName"/>:</td>
      <td><html:text property="username" /></td>
    </tr>
    <tr>
      <td class="bluetext"><bean:message key="rule.description"/>:</td>
      <td><html:text property="description"/></td>
      <td class="bluetext"><bean:message key="rule.enabled"/>:</td>
      <td>
        <html:select property="enabled">
		  <html:option value=""><bean:message key="all.all"/></html:option>
		  <html:options collection="X_ENABLEDDISABLEDLIST" property="enumCode" labelProperty="${x_lang}ShortDescription" />
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
<page:form action="list${X_RULETYPE.prefixUrl}Rule.do" method="post">			
  <jsp:include page="../pageHead.jsp"/>
  <table class="data">
    <thead>
      <tr class="new_bg">
	    <th width="80%">
          <page:order order="description" style="text-decoration:none">
    	      <bean:message key="rule.description"/>
            <page:desc><img src="images/down.gif" border="0"/></page:desc>
            <page:asc><img src="images/up.gif" border="0"/></page:asc>
          </page:order>
	    </th>
	    <th width="10%">
          <page:order order="enabled" style="text-decoration:none">
            <bean:message key="rule.enabled"/>
            <page:desc><img src="images/down.gif" border="0"/></page:desc>
            <page:asc><img src="images/up.gif" border="0"/></page:asc>
          </page:order>
	    </th>
	    <!-- 
	    --below delete by Jackey 2005-11-24
	    <th width="10%"><bean:message key="rule.conditions"/></th>
	     --up delete by Jackey 2005-11-24 
	    -->
	    <th width="10%"></th>
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
