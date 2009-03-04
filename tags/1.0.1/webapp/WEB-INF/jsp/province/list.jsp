<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>

<html>
<head>
<script>
	var oldSelectedProvince;
	function selectProvince(id, province)
	{
		_selectProvince(id, province);
		window.parent.selectProvince(id);
	}
	function _selectProvince(id, province)
	{
		if(oldSelectedProvince)
		{
			oldSelectedProvince.style.backgroundColor="#ffffff";
			oldSelectedProvince.style.fontSize= "12px";
			oldSelectedProvince.style.color="black";
		}
	
		var selectedItem;
		selectedItem=document.getElementById("c"+id);
		selectedItem.style.backgroundColor="#2A3B88";
		selectedItem.style.fontSize= "16px";
		selectedItem.style.color="#ffffff";

		oldSelectedProvince=selectedItem;
		
		var promoteProvinceButton = window.parent.document.getElementById("promoteProvince");
		if (promoteProvinceButton != null) {
			if (province != null) {
				promoteProvinceButton.style.display="block";
			} else {
				promoteProvinceButton.style.display="none";
			}
		}
	}
</script>
<script language="javascript" src="includes/table.js"></script>
<script language="javascript" src="includes/common.js"></script>
<script type="text/javascript">
<!--
	function add() {
		var site="";
		
		if(window.parent.getSelectedSiteId)
			site="&site_id="+window.parent.getSelectedSiteId();
	
		v = window.showModalDialog(
			'showDialog.do?title=province.new.title&newProvince.do?country_id=${param.country_id}'+site , 
			null, 'dialogWidth:400px;dialogHeight:240px;status:no;help:no;scroll:no');
		if (v) {
			var table = document.all('datatable');
			appendRow(table, v);
		    applyRowStyle(table);
		};
	}
	
	function promote() {
		if(oldSelectedProvince)
		{
			var id;
			id=oldSelectedProvince.id.substring(1);
			v = window.showModalDialog(
				'showDialog.do?title=province.promote.title&promoteProvince.do?id=' +  id, 
				null, 'dialogWidth:400px;dialogHeight:240px;status:no;help:no;scroll:no');
			if (v) {
				updateRow(document.all('r' + id), v);
				selectProvince(id);
			};
		}
	}

	function edit() {
		if(oldSelectedProvince)
		{
			var id;
			id=oldSelectedProvince.id.substring(1);
			v = window.showModalDialog(
				'showDialog.do?title=province.edit.title&editProvince.do?id=' +  id, 
				null, 'dialogWidth:400px;dialogHeight:240px;status:no;help:no;scroll:no');
			if (v) {
				updateRow(document.all('r' + id), v);
				selectProvince(id);
			};
		}
	}
	
	function deleteMe() {
		if(oldSelectedProvince)
		{
			var id;
			id=oldSelectedProvince.id.substring(1);
				
			var url='deleteProvince.do?id=' +  id;
			var message="province.delete.confirm";
			var title="province.delete.title";
			
			var v=confirmDialog(url,title,message,250,150);
			if (v) {
				deleteRow(document.all('r' + id));
				oldSelectedProvince=null;
			};
		}
	}
	
	
	
//-->
</script>

</head>
<body bgColor="white">
<table width=100%>
	<tbody id="datatable">
	<logic:iterate id="X_OBJECT" name="X_RESULTLIST">
		<bean:define id="X_OBJECT" toScope="request" name="X_OBJECT" />
		<jsp:include page="row.jsp" />
	</logic:iterate>
	</tbody>
</table>
<logic:notEmpty name="X_FIRST">
<script>
	oldSelectedProvince=document.getElementById("${X_FIRST.id}");
	selectProvince("${X_FIRST.id}");
</script>	
</logic:notEmpty>	
</body>
</html>
