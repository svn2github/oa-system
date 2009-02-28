<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<link rel="stylesheet" type="text/css" href="includes/xmlTree/xmlTree.css"/>
<script type="text/javascript" src="includes/xmlTree/xmlTree.js"></script>
<script language="javascript">
	var tree = new XmlTree("includes/xmlTree");
	
	function addDepartment() {
		var obj = tree.activeNode;
		var v = null;
		if (obj == null) {
			v = window.showModalDialog('showDialog.do?title=department.new.title&newDepartment.do?siteId=${departmentQueryForm.site.value}', null, 'dialogWidth:500px;dialogHeight:260px;status:no;help:no;scroll:no');
		} else {
		    v = window.showModalDialog('showDialog.do?title=department.new.title&newDepartment.do?siteId=${departmentQueryForm.site.value}&parentId=' + tree.activeNodeID, null, 'dialogWidth:500px;dialogHeight:260px;status:no;help:no;scroll:no');
		}
		if (v == null) return;
		tree.addNode(v['id'], v['name'], v['parentId']);
		tree.activeNode.scrollIntoView(true);			
	}
	
	function editDepartment() {
		var obj = tree.activeNode;
		if (obj == null) {
			alert('no department active');
			return;
		}
		var v = window.showModalDialog('showDialog.do?title=department.edit.title&editDepartment.do?id=' + tree.activeNodeID, null, 'dialogWidth:500px;dialogHeight:260px;status:no;help:no;scroll:no');
		if (v == null) return;
		tree.editNode(v['id'], v['name'], v['parentId']);
	}
	
</script>
<html:form action="listDepartment">
<table border="0" cellpadding="4" cellspacing="0">
  <tbody>
    <tr>
      <td class="bluetext"><bean:message key="department.site"/>:</td>
      <td><html:select property="site.value" onchange="this.form.submit();"><html:optionsCollection property="site.list" value="id" label="name"/></html:select></td>
    </tr>
  </tbody>
</table>
</html:form>
<hr/>
<div id="tree" style="margin:5px"></div>
<input type="button" value="<bean:message key="all.new"/>" onclick="addDepartment();"/>
<input type="button" value="<bean:message key="all.modify"/>" onclick="editDepartment();"/>
<xml id="xmlDoc">
  <bean:write name="X_DEPARTMENTXML" filter="false"/>
</xml>
<xml id="xslDoc">
  <%@include file="/includes/xmlTree/xmlTree.xsl"%>
</xml>
<script language="javascript">
	tree.buildTree(document.getElementById("tree"), document.getElementById("xmlDoc"), document.getElementById("xslDoc"));
</script>