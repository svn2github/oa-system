<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>
<script language="javascript" src="includes/common.js"></script>
<html>
<head>
<script>
	var oldSelectedCountry;
	function selectCountry(id, site)
	{
		_selectCountry(id, site);
		window.parent.selectCountry(id);
	}
	function _selectCountry(id, site)
	{
		if(oldSelectedCountry)
		{
			oldSelectedCountry.style.backgroundColor="#ffffff";
			oldSelectedCountry.style.fontSize= "12px";
			oldSelectedCountry.style.color="black";
		}
	
		var selectedItem;
		selectedItem=document.getElementById("c"+id);
		selectedItem.style.backgroundColor="#2A3B88";
		selectedItem.style.fontSize= "16px";
		selectedItem.style.color="#ffffff";

		oldSelectedCountry=selectedItem;
		
		var promoteCountryButton = window.parent.document.getElementById("promoteCountry");
		if (promoteCountryButton != null) {
			if (site != null) {
				promoteCountryButton.style.display="block";
			} else {
				promoteCountryButton.style.display="none";
			}
		}
	}
</script>
<script language="javascript" src="includes/table.js"></script>
<script type="text/javascript">
<!--
	function add() {
		var site="";
		
		if(window.parent.getSelectedSiteId)
			site="?site_id="+window.parent.getSelectedSiteId();
		
		var v = window.showModalDialog(
			'showDialog.do?title=country.new.title&newCountry.do'+site , 
			null, 'dialogWidth:400px;dialogHeight:240px;status:no;help:no;scroll:no');
		if (v) {
			var table = document.all('datatable');
			appendRow(table, v);
		    applyRowStyle(table);
		};
	}

	function edit() {
		if(oldSelectedCountry)
		{
			var id;
			id=oldSelectedCountry.id.substring(1);
				
			var url='editCountry.do?id=' +  id;
			v=dialogAction(url,'country.edit.title',400,240);
			if (v) {
				updateRow(document.all('r' + id), v);
				_selectCountry(id);
			};
		}
	}
	
	function deleteMe() {
		if(oldSelectedCountry)
		{
			var id;
			id=oldSelectedCountry.id.substring(1);
				
			var url='deleteCountry.do?id=' +  id;
			var message="country.delete.confirm";
			var title="country.delete.title";
			
			var v=confirmDialog(url,title,message,250,150);
			if (v) {
				deleteRow(document.all('r' + id));
				oldSelectedCountry=null;
			};
		}
	}
	
	function promote() {
		if(oldSelectedCountry)
		{
			var id;
			id=oldSelectedCountry.id.substring(1);
			var url="promoteCountry.do?id="+id;
			v=dialogAction(url,'country.promote.title',400,240);
			if (v) {
				updateRow(document.all('r' + id), v);
				_selectCountry(id);
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
	oldSelectedCountry=document.getElementById("${X_FIRST.id}");
	selectCountry("${X_FIRST.id}");
</script>	
</logic:notEmpty>
</body>
</html>
