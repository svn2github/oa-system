<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>

<html>
<head>
<script>
	var oldSelectedCity;
	function selectCity(id, city)
	{
		if(oldSelectedCity)
		{
			oldSelectedCity.style.backgroundColor="#ffffff";
			oldSelectedCity.style.fontSize= "12px";
			oldSelectedCity.style.color="black";
		}
	
		var selectedItem;
		selectedItem=document.getElementById("c"+id);
		selectedItem.style.backgroundColor="#2A3B88";
		selectedItem.style.fontSize= "16px";
		selectedItem.style.color="#ffffff";

		oldSelectedCity=selectedItem;
		
		var promoteCityButton = window.parent.document.getElementById("promoteCity");
		if (promoteCityButton != null) {
			if (city != null) {
				promoteCityButton.style.display="block";
			} else {
				promoteCityButton.style.display="none";
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
			'showDialog.do?title=city.new.title&newCity.do?province_id=${param.province_id}'+site , 
			null, 'dialogWidth:400px;dialogHeight:240px;status:no;help:no;scroll:no');
		if (v) {
			var table = document.all('datatable');
			appendRow(table, v);
		    applyRowStyle(table);
		};
	}
	function promote()
	{
		if(oldSelectedCity)
		{
			var id;
			id=oldSelectedCity.id.substring(1);
			v = window.showModalDialog(
				'showDialog.do?title=city.promote.title&promoteCity.do?id=' +  id, 
				null, 'dialogWidth:400px;dialogHeight:240px;status:no;help:no;scroll:no');
			if (v) {
				updateRow(document.all('r' + id), v);
				selectCity(id);
			};
		}
		
	}

	function edit() {
		if(oldSelectedCity)
		{
			var id;
			id=oldSelectedCity.id.substring(1);
			v = window.showModalDialog(
				'showDialog.do?title=city.edit.title&editCity.do?id=' +  id, 
				null, 'dialogWidth:400px;dialogHeight:240px;status:no;help:no;scroll:no');
			if (v) {
				updateRow(document.all('r' + id), v);
				selectCity(id);
			};
		}
	}
	
	function deleteMe() {
		if(oldSelectedCity)
		{
			var id;
			id=oldSelectedCity.id.substring(1);
				
			var url='deleteCity.do?id=' +  id;
			var message="city.delete.confirm";
			var title="city.delete.title";
			
			var v=confirmDialog(url,title,message,250,150);
			if (v) {
				deleteRow(document.all('r' + id));
				oldSelectedCity=null;
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
	oldSelectedCity=document.getElementById("${X_FIRST.id}");
	selectCity("${X_FIRST.id}");
</script>	
</logic:notEmpty>	
</body>
</html>
