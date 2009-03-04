<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>

<script type="text/javascript"> 
<!--
	function add() {
		var title="expenseSubCategory.new.title";
		var url="newExpenseSubCategory.do?expenseCategory_id=${param.expenseCategory_id}";
		var v = dialogAction(url,title,400,266);
		if (v) {
			var table = document.all('datatable');
			appendRow(table, v);
		    applyRowStyle(table);
		};
	}

	function edit(id) {
		v = window.showModalDialog(
			'showDialog.do?title=exchangeRate.edit.title&editExpenseSubCategory.do?id=' + id , 
			null, 'dialogWidth:400px;dialogHeight:200px;status:no;help:no;scroll:no');
		if (v) {
			updateRow(document.all('r' + id), v);
		};
	}
	
//-->
</script>

<table width="90%">
	<tr>
		<td align="left" width='100%' class='bluetext'><font color="blue">
		<h3><bean:message key="expenseSubCategory.title"/></h3>
		</font></td>
	</tr>
</table>


<page:form action="listExpenseSubCategory.do" method="post">

	<jsp:include page="../pageHead.jsp"/>
	
	<div id="fixedHeaderTableContainer" class="fixedHeaderTableContainer" style="overflow=auto;height:135px;">
	<table class="data">
		<thead>
			<tr class="new_bg">
				<th width="70%"><page:order order="description"
					style="text-decoration:none">
					<bean:message key="expenseSubCategory.description" />
					<page:desc>
						<img src="images/down.gif" border="0" />
					</page:desc>
					<page:asc>
						<img src="images/up.gif" border="0" />
					</page:asc>
				</page:order></th>
				<th width="70%"><page:order order="enabled"
					style="text-decoration:none">
					<bean:message key="expenseSubCategory.enabled" />
					<page:desc>
						<img src="images/down.gif" border="0" />
					</page:desc>
					<page:asc>
						<img src="images/up.gif" border="0" />
					</page:asc>
				</page:order></th>
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
	<table>
		<tr>
			<td valign="bottom" align="right"><input type="button" onclick="add()" value="<bean:message key="all.new"/>"></td>
		</tr>
	</table>

</page:form>

<script type="text/javascript">
    applyRowStyle(document.all('datatable'));
</script>
