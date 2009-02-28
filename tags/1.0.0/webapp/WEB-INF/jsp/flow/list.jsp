<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>
<script language="javascript" src="includes/table.js"></script>
<script type="text/javascript">
<!--
 	<c:if test="${requestScope.WebDragAndDraw==null}">        
	function add() {
		v = window.showModalDialog(
			'showDialog.do?title=flow.new${X_RULETYPE.prefixUrl}.title&new${X_RULETYPE.prefixUrl}Flow.do?site_id=' + flowQueryForm.elements['site.value'].value, 
			null, 'dialogWidth:710px;dialogHeight:590px;status:no;help:no;scroll:no');
		if (v) {
			var table = document.all('datatable');
			appendRow(table, v[0]);
			if (v.length > 1) {
				var row = table.all('r' + v[2]);
				if (row != null) updateRow(row, v[1]);
			}
		    applyRowStyle(table);
		};
	}

	function edit(id) {
		v = window.showModalDialog(
			'showDialog.do?title=flow.edit${X_RULETYPE.prefixUrl}.title&edit${X_RULETYPE.prefixUrl}Flow.do?id=' + id, 
			null, 'dialogWidth:710px;dialogHeight:590px;status:no;help:no;scroll:no');
		if (v) {
			var table = document.all('datatable');
			updateRow(table.all('r' + id), v[0]);
			if (v.length > 1) {
				var row = table.all('r' + v[2]);
				if (row != null) updateRow(row, v[1]);
			}
		};
	}
	</c:if>
    <c:if test="${requestScope.WebDragAndDraw!=null}">
       function addWebDragAndDraw() {
       		document.newflowFormWebDragAndDraw.elements["site_id"].value=flowQueryForm.elements['site.value'].value;
       		document.newflowFormWebDragAndDraw.submit();
       }
       
       function editWebDragAndDraw(id) {
       		document.editflowFormWebDragAndDraw.elements["site_id"].value=flowQueryForm.elements['site.value'].value;
       		document.editflowFormWebDragAndDraw.elements["id"].value=id;
       		document.editflowFormWebDragAndDraw.submit();
       }
    </c:if>
	function del(id) {
		var v = window.showModalDialog(
		    'showDialog.do?title=flow.delete${X_RULETYPE.prefixUrl}.title&confirmOperationDialog.do?message=flow.delete${X_RULETYPE.prefixUrl}.message&delete${X_RULETYPE.prefixUrl}Flow.do?id=' + id, 
		    null, 'dialogWidth:300px;dialogHeight:143px;status:no;help:no;scroll:no');
		if (v) {
			deleteRow(document.all('r' + id));
		}
	}
//-->
</script>
<html:form action="list${X_RULETYPE.prefixUrl}Flow${WebDragAndDraw}">
<html:hidden property="order"/>
<html:hidden property="descend"/>
<table width="100%" border="0" cellpadding="4" cellspacing="0">
  <tbody>
    <tr>
      <td class="bluetext"><bean:message key="flow.site"/>:</td>
      <td colspan="4"><html:select property="site.value" onchange="flowQueryForm.submit();"><html:optionsCollection property="site.list" value="id" label="name"/></html:select></td>
    </tr>
    <tr>
      <td class="bluetext"><bean:message key="flow.description"/>:</td>
      <td><html:text property="description"/></td>
      <td class="bluetext"><bean:message key="flow.enabled"/>:</td>
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
        <c:if test="${requestScope.WebDragAndDraw==null}">
        	<input type="button" value="<bean:message key="all.new"/>" onclick="add();"/>
        </c:if>
        <c:if test="${requestScope.WebDragAndDraw!=null}">
        	<input type="button" value="<bean:message key="all.new"/>" onclick="addWebDragAndDraw();"/>
        </c:if>
      </td>
    </tr>
  </tbody>
</table>
</html:form>
<c:if test="${requestScope.WebDragAndDraw!=null}">
<form name="newflowFormWebDragAndDraw" method="post" action="new${X_RULETYPE.prefixUrl}FlowWebDragAndDraw.do">
	<input type="hidden" name="site_id"/>
</form>
<form name="editflowFormWebDragAndDraw" method="post" action="edit${X_RULETYPE.prefixUrl}FlowWebDragAndDraw.do">
	<input type="hidden" name="site_id"/>
	<input type="hidden" name="id"/>
</form>
 </c:if>
<hr/>
<page:form action="list${X_RULETYPE.prefixUrl}Flow.do" method="post">			
  <jsp:include page="../pageHead.jsp"/>
  <table class="data">
    <thead>
      <tr bgcolor="#9999ff">
	    <th width="80%">
          <page:order order="description" style="text-decoration:none">
    	      <bean:message key="flow.description"/>
            <page:desc><img src="images/down.gif" border="0"/></page:desc>
            <page:asc><img src="images/up.gif" border="0"/></page:asc>
          </page:order>
	    </th>
	    <th width="10%">
          <page:order order="enabled" style="text-decoration:none">
            <bean:message key="flow.enabled"/>
            <page:desc><img src="images/down.gif" border="0"/></page:desc>
            <page:asc><img src="images/up.gif" border="0"/></page:asc>
          </page:order>
	    </th>
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
