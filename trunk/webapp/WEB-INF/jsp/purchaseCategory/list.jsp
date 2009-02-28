<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>
<script type="text/javascript">
<!--
	function add() {
		<logic:notEmpty name="x_version">
		siteId=document.forms["purchaseCategoryQueryForm"].siteId.value;
		v = window.showModalDialog(
			'showDialog.do?title=purchaseCategory.new.title&newPurchaseCategory${x_version}.do?site_id=' + siteId, 
			null, 'dialogWidth:400px;dialogHeight:200px;status:no;help:no;scroll:no');
		</logic:notEmpty>
		<logic:empty name="x_version">	
			v = window.showModalDialog(
			'showDialog.do?title=purchaseCategory.new.title&newPurchaseCategory.do', 
			null, 'dialogWidth:400px;dialogHeight:200px;status:no;help:no;scroll:no');
		</logic:empty>
		if (v) {
			var table = document.all('datatable');
			appendRow(table, v);
		    applyRowStyle(table);
		};
	}

	function edit(id) {
		v = window.showModalDialog(
			'showDialog.do?title=purchaseCategory.edit.title&editPurchaseCategory${x_version}.do?id=' + id , 
			null, 'dialogWidth:480px;dialogHeight:560px;status:no;help:no;scroll:no');
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
<html:form action="listPurchaseCategory${x_version}">
<html:hidden property="order"/>
<html:hidden property="descend"/>
<table  border=0 cellpadding=4 cellspacing=0>
  	<tr>
		<td colspan="4"><html:messages id="x_message">${x_message}<br>
		</html:messages></td>
	</tr>
	<logic:notEmpty name="x_version">
    <tr>
    	<td class="bluetext">
    		<bean:message key="purchaseCategory.search.site"/>:
    	</td>
	 	<td>
	 		<html:select property="siteId" onchange="changeSite(this)">
	 			<html:options collection = "X_SITELIST" property = "id" labelProperty = "name"/>
	 		</html:select>
	 	</td>
	</tr>
	</logic:notEmpty>
	<tr>
      <td class="bluetext">
      	<bean:message key="purchaseCategory.search.description"/>:
      </td>
      <td>
     	<html:text property="description" size="20"/>
     </td>
      <td class="bluetext">
     	<bean:message key="purchaseCategory.search.status"/>:
     </td>
	  <td>
	    <html:select property="status">
	    	<html:option value=""><bean:message key="all.all"/></html:option>
	      <c:if test="${sessionScope.LOGIN_USER.locale=='en'}"><html:options collection = "X_ENABLEDDISABLEDLIST" property = "enumCode" labelProperty = "engShortDescription"/></c:if>
	      <c:if test="${sessionScope.LOGIN_USER.locale!='en'}"><html:options collection = "X_ENABLEDDISABLEDLIST" property = "enumCode" labelProperty = "chnShortDescription"/></c:if>
	    </html:select>
	  </td>
      <td align="right" colspan="4">
        <html:submit><bean:message key="all.query"/></html:submit>
        <input type="button" value="<bean:message key="all.new"/>" onclick="add();"/>
      </td>
    </tr>
</table>
</html:form>
<hr/>
<page:form action="listPurchaseCategory${x_version}.do" method="post">			
  <jsp:include page="../pageHead.jsp"/>
  <table class="data">
    <thead>
      <tr bgcolor="#9999ff">
        <th width="60%">
          <page:order order="description" style="text-decoration:none">
            <bean:message key="purchaseCategory.description"/>
            <page:desc><img src="images/down.gif" border="0"/></page:desc>
            <page:asc><img src="images/up.gif" border="0"/></page:asc>
          </page:order>
        </th>
	    <th width="40%">
            <bean:message key="purchaseCategory.status"/>
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
