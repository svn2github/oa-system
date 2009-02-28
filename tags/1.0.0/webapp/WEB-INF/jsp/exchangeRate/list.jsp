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
		siteId=document.forms["exchangeRateQueryForm"].siteId.value;
		v = window.showModalDialog(
			'showDialog.do?title=exchangeRate.new.title&newExchangeRate.do?siteId=' + siteId, 
			null, 'dialogWidth:400px;dialogHeight:180px;status:no;help:no;scroll:no');
		if (v) {
			var table = document.all('datatable');
			appendRow(table, v);
		    applyRowStyle(table);
		};
	}

	function edit(id) {
		v = window.showModalDialog(
			'showDialog.do?title=exchangeRate.edit.title&editExchangeRate.do?id=' + id , 
			null, 'dialogWidth:400px;dialogHeight:180px;status:no;help:no;scroll:no');
		if (v) {
			updateRow(document.all('r' + id), v);
		};
	}
	function changeSite(combo)
	{
		combo.form.submit();
	}
//-->
</script>
<html:form action="listExchangeRate">
<html:hidden property="order"/>
<html:hidden property="descend"/>
<table width="100%" border="0" cellpadding="4" cellspacing="0">
  <tbody>
    <tr>
      <td class="bluetext"><bean:message key="exchangeRate.search.site"/>:</td>
	  <td><html:select property="siteId" onchange="changeSite(this)"><html:options collection = "X_SITELIST" property = "id" labelProperty = "name"/></html:select></td>
      <td class="bluetext"><bean:message key="exchangeRate.search.currency"/>:</td>
      <td><html:text property="currencyCode"/></td>
      <td align="center">
        <html:submit><bean:message key="all.query"/></html:submit>
        <input type="button" value="<bean:message key="all.new"/>" onclick="add();"/>
      </td>
    </tr>
  </tbody>
</table>
</html:form>
<hr/>
<logic:notEmpty name="X_BASECURRENCY">
	<font color="blue"><h3><bean:message key="exchangeRate.siteBaseCurrency"/>:&nbsp;${X_BASECURRENCY}</h3></font><br>
</logic:notEmpty>		
<page:form action="listExchangeRate.do" method="post">			
  <jsp:include page="../pageHead.jsp"/>
  <table class="data">
    <thead>
      <tr bgcolor="#9999ff">
        <th width="25%">
          <page:order order="code" style="text-decoration:none">
            <bean:message key="exchangeRate.tablehead.code"/>
            <page:desc><img src="images/down.gif" border="0"/></page:desc>
            <page:asc><img src="images/up.gif" border="0"/></page:asc>
          </page:order>
        </th>
	    <th width="40%">
   	      <bean:message key="exchangeRate.tablehead.name"/>
	    </th>
	    <th width="35%">
          <page:order order="exchangeRate" style="text-decoration:none">
            <bean:message key="exchangeRate.tablehead.exchangeRate"/>
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
