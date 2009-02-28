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
			'showDialog.do?title=HotelContract.new.title&newHotelContract.do' , 
			null, 'dialogWidth:400px;dialogHeight:180px;status:no;help:no;scroll:no');
		if (v) {
			var table = document.all('datatable');
			appendRow(table, v);
		    applyRowStyle(table);
		};
	}

	function edit(id) {
		v = window.showModalDialog(
			'showDialog.do?title=HotelContract.edit.title&editHotelContract.do?id=' + id , 
			null, 'dialogWidth:400px;dialogHeight:180px;status:no;help:no;scroll:no');
		if (v) {
			updateRow(document.all('r' + id), v);
		};
	}
	
//-->
</script>
<html:form action="listHotelContract">
	<html:hidden property="order" />
	<html:hidden property="descend" />

	<table width=100% border=0 cellpadding=4 cellspacing=0>
		<tr>
			<!-- id -->
			<td class="bluetext"><bean:message key="hotelContract.id" />:</td>
			<td><html:text property="id" size="8"/></td>

	<!-- keyPropertyList -->

	<!-- kmtoIdList -->

	<!-- mtoIdList -->
			<td class="bluetext"><bean:message key="hotelContract.hotel_id" />:</td>
			<td>
				<html:select property="hotel_id">
				</html:select>
			</td>

	<!-- property -->
			<td class="bluetext"><bean:message key="hotelContract.fileName" />:</td>
			<td><html:text property="fileName" size="8"/></td>
			<td class="bluetext"><bean:message key="hotelContract.description" />:</td>
			<td><html:text property="description" size="8"/></td>
			<td class="bluetext"><bean:message key="hotelContract.fileContent" />:</td>
			<td><html:text property="fileContent" size="8"/></td>
			<td class="bluetext"><bean:message key="hotelContract.uploadDate" />:</td>
			<td><html:text property="uploadDate" size="8"/></td>

			<td align="left">
				<html:submit><bean:message key="all.query"/></html:submit>
				<input type="button" value="<bean:message key="all.new"/>"
					onClick="window.location.href='newHotelContract.do" />
			</td>
		</tr>
	</table>
</html:form>
<hr />
<page:form action="listHotelContract.do" method="post">
	<jsp:include page="../pageHead.jsp"/>
	<table class="data">
		<thead>
			<tr bgcolor="#9999ff">
				<th width="30%"><page:order order="id" style="text-decoration:none">
					<bean:message key="hotelContract.id" />
					<page:desc>
						<img src="images/down.gif" border="0" />
					</page:desc>
					<page:asc>
						<img src="images/up.gif" border="0" />
					</page:asc>
				</page:order></th>

				<th width="70%"><page:order order="name"
					style="text-decoration:none">
					<bean:message key="hotelContract.name" />
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
</page:form>

<script type="text/javascript">
    applyRowStyle(document.all('datatable'));
</script>

