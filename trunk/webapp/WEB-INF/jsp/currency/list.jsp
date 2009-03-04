<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>
<script language="javascript" src="includes/table.js"></script>
<script type="text/javascript">
<!--
	var seqno=1;
	function add() {
		v = window.showModalDialog(
			'showDialog.do?title=currency.new.title&newCurrency.do', 
			null, 'dialogWidth:400px;dialogHeight:180px;status:no;help:no;scroll:no');
		if (v) {
			var table = document.all('datatable');
			appendRow(table, v);
		    applyRowStyle(table);
		};
	}

	function edit(id,seq) {
		v = window.showModalDialog(
			'showDialog.do?title=currency.edit.title&editCurrency.do?code=' + id + '&seq=' + seq, 
			null, 'dialogWidth:400px;dialogHeight:180px;status:no;help:no;scroll:no');
		if (v) {
			updateRow(document.all('r' + id), v);
		};
	}
	
//-->
</script>
<html:form action="listCurrency">
<html:hidden property="order"/>
<html:hidden property="descend"/>
<table width="100%" border="0" cellpadding="4" cellspacing="0">
  <tbody>
    <tr>
      <td class="bluetext"><bean:message key="currency.search.code"/>:</td>
      <td><html:text property="code" size="8"/></td>
      <td class="bluetext"><bean:message key="currency.search.name"/>:</td>
      <td><html:text property="name" size="12"/></td>
      <td class="bluetext"><bean:message key="currency.search.status"/>:</td>
	  <td>
	    <html:select property="status">
	    	<html:option value=""><bean:message key="all.all"/></html:option>
	      <c:if test="${sessionScope.LOGIN_USER.locale=='en'}"><html:options collection = "X_ENABLEDDISABLEDLIST" property = "enumCode" labelProperty = "engShortDescription"/></c:if>
	      <c:if test="${sessionScope.LOGIN_USER.locale!='en'}"><html:options collection = "X_ENABLEDDISABLEDLIST" property = "enumCode" labelProperty = "chnShortDescription"/></c:if>
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
<page:form action="listCurrency.do" method="post">			
  <jsp:include page="../pageHead.jsp"/>
  <table class="data">
    <thead>
      <tr class="new_bg">
        <th width="35%">
          <page:order order="code" style="text-decoration:none">
            <bean:message key="currency.tablehead.code"/>
            <page:desc><img src="images/down.gif" border="0"/></page:desc>
            <page:asc><img src="images/up.gif" border="0"/></page:asc>
          </page:order>
        </th>
	    <th width="50%">
   	      <bean:message key="currency.tablehead.description"/>
	    </th>
	    <th width="15%">
          <page:order order="enabled" style="text-decoration:none">
            <bean:message key="currency.tablehead.status"/>
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
<%
	request.setAttribute("X_STARTSEQ", new Integer(((Integer)request.getAttribute("X_STARTSEQ")).intValue()+1));
%>        
      </logic:iterate>
    </tbody>
  </table>
  <script type="text/javascript">
    applyRowStyle(document.all('datatable'));
  </script>
  <jsp:include page="../pageTail.jsp"/>
</page:form>		      
