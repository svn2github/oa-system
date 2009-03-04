<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<link rel="stylesheet" type="text/css" href="includes/xmlTree/xmlTree.css"/>
<script type="text/javascript" src="includes/xmlTree/xmlTree.js"></script>
<script language="javascript">
	var tree = new XmlTree("includes/xmlTree");
	
	function addMenu() {
		var obj = tree.activeNode;
		var v = null;
		if (obj == null) {
			v = window.showModalDialog('showDialog.do?title=menu.new.title&newMenu.do', null, 'dialogWidth:500px;dialogHeight:320px;status:no;help:no;scroll:no');
		} else {
		    v = window.showModalDialog('showDialog.do?title=menu.new.title&newMenu.do?parentId=' + tree.activeNodeID, null, 'dialogWidth:550px;dialogHeight:350px;status:no;help:no;scroll:no');
		}
		if (v == null) return;
		tree.addNode(v['id'], v['name'], v['parentId']);
		tree.activeNode.scrollIntoView(true);			
	}
	
	function editMenu() {
		var obj = tree.activeNode;
		if (obj == null) {
			alert('no menu active');
			return;
		}
		var v = window.showModalDialog('showDialog.do?title=menu.edit.title&editMenu.do?id=' + tree.activeNodeID, null, 'dialogWidth:550px;dialogHeight:350px;status:no;help:no;scroll:no');
		if (v == null) return;
		tree.editNode(v['id'], v['name'], v['parentId']);
	}
	
	function deleteMenu() {
		var obj = tree.activeNode;
		if (obj == null) {
			alert('no menu active');
			return;
		}
		var v = window.showModalDialog('showDialog.do?title=menu.delete.title&confirmOperationDialog.do?message=menu.delete.message&deleteMenu.do?id=' + tree.activeNodeID, null, 'dialogWidth:300px;dialogHeight:143px;status:no;help:no;scroll:no');
		if (v == null) return;
		tree.deleteNode(tree.activeNodeID);
	}
</script>
<div id="tree" style="margin:5px"></div>
<input type="button" value="<bean:message key="all.new"/>" onclick="addMenu();"/>
<input type="button" value="<bean:message key="all.modify"/>" onclick="editMenu();"/>
<input type="button" value="<bean:message key="all.delete"/>" onclick="deleteMenu();"/>
<xml id="xmlDoc">
  <bean:write name="X_MENUXML" filter="false"/>
</xml>
<xml id="xslDoc">
  <%@include file="/includes/xmlTree/xmlTree.xsl"%>
</xml>
<script language="javascript">
	tree.buildTree(document.getElementById("tree"), document.getElementById("xmlDoc"), document.getElementById("xslDoc"));
</script>