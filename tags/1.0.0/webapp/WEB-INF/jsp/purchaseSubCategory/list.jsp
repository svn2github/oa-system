<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>
<script language="javascript" src="includes/table.js"></script>
<script type="text/javascript">
<!--
	function add() {
		v = window.showModalDialog(
			'showDialog.do?title=purchaseSubCategory.new.title&newPurchaseSubCategory${x_version}.do?purchaseCategory_id=${X_PURCHASECATEGORY.id}' , 
			null, 'dialogWidth:400px;dialogHeight:250px;status:no;help:no;scroll:no');
		if (v) {
			var table = document.all('datatable');
			appendRow(table, v);
		    applyRowStyle(table);
		};
	}

	function edit(id) {
		v = window.showModalDialog(
			'showDialog.do?title=purchaseSubCategory.edit.title&editPurchaseSubCategory${x_version}.do?id=' + id , 
			null, 'dialogWidth:400px;dialogHeight:250px;status:no;help:no;scroll:no');
		if (v) {
			updateRow(document.all('r' + id), v);
		};
	}
	
//-->
</script>
<font color="blue"><h3><bean:message key="purchaseCategory.sublist"/></h3></font>

<page:form action="listPurchaseSubCategory${x_version}.do" method="post">
	<jsp:include page="../pageHead.jsp"/>
	<div id="fixedHeaderTableContainer" class="fixedHeaderTableContainer" style="overflow=auto;height:135px;">
	<table class="data">
		<thead>
		<tr bgcolor="#9999ff">
	        <th width="50%">
	          <page:order order="description" style="text-decoration:none">
	            <bean:message key="purchaseSubCategory.description"/>
	            <page:desc><img src="images/down.gif" border="0"/></page:desc>
	            <page:asc><img src="images/up.gif" border="0"/></page:asc>
	          </page:order>
	        </th>
		    <th width="30%">
	   	      <page:order order="supplier" style="text-decoration:none">
	            <bean:message key="purchaseSubCategory.defaultSupplier"/>
	            <page:desc><img src="images/down.gif" border="0"/></page:desc>
	            <page:asc><img src="images/up.gif" border="0"/></page:asc>
	          </page:order>
		    </th>
		    <th width="10%">
	            <bean:message key="purchaseCategory.status"/>
		    </th>
		</tr>
		</thead>

		<tbody id="datatable">
			<logic:iterate id="X_OBJECT" name="X_RESULTLIST">
				<bean:define id="X_OBJECT" toScope="request" name="X_OBJECT" />
				<jsp:include page="row.jsp" />
			</logic:iterate>
		</tbody>
	</table>
	</div>
</page:form>
<script type="text/javascript">
    applyRowStyle(document.all('datatable'));
</script>
<table width="90%" border="0" cellspacing="0" cellpadding="0">
<tr><td align="left">&nbsp;<input type="button" value="<bean:message key="all.new"/>" onclick="add();"/></td></tr>
</table>
