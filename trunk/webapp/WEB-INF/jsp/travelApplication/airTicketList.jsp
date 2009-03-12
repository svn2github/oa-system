<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ page import="net.sourceforge.model.metadata.TravellingMode"%>

	<script type="text/javascript">
<!--
	function viewAirTicket(id) {
		var url='viewAirTicket.do?id='+id+"&travelApplication_id=${x_ta.id}";
		var title="airTicket.view.title";
		dialogAction(url,title,600,580);
	}
//-->
	</script>

<c:if test="${x_edit}">
	<script type="text/javascript">
<!--
	function editAirTicket(id)
	{
		var url="editBookAirTicket.do?id=" + id+"&travelApplication_id=${x_ta.id}";
		var title="airTicket.edit.title";
		var v=dialogAction(url,title,600,580);
		if (v) {
			updateRow(getItemRowById(id),v);
		};
	}

	function addAirTicket() {
		var url='bookAirTicket.do?travelApplication_id=${x_ta.id}';
		var title="airTicket.new.title";
	
		var v=dialogAction(url,title,600,580);
		if (v) {
			var table = getItemTable();
			appendRow(table, v);
		    applyRowStyle(table);
		};
	}
	
	function getLastItemRow()
	{
		 var table=getItemTable();
		 var row = table.rows[table.rows.length - 1];
		 return row;
	}
	
	function getItemRowById(id)
	{
		 var table=getItemTable();
		 return table.all('r' + id);		
	}
	
	function getItemTable()
	{
		return document.all('airTicket_datatable');
	}
	
	
	function deleteAirTicket(id)
	{
		var deleteUrl="deleteBookAirTicket.do?id=" + id+"&travelApplication_id=${x_ta.id}";
		var message="airTicket.delete.confirm";
		var title="airTicket.delete.title";
		var v=confirmDialog(deleteUrl,title,message,250,150);
		
		if (v) {
			deleteRow(getItemRowById(id));
			applyRowStyle(getItemTable());
		};
	}

//-->
</script>
</c:if>
<div id="airTicket" style="display:block">
<h3 style="color:blue">
	<bean:message key="travelApplication.airTicket" />
</h3>
<c:if test="${x_edit}">
	<input type="button" name="buttonAddAirTicket" value="<bean:message key="all.new"/>" onclick="addAirTicket();" />
</c:if>

<table width=100% class="data">
	<thead>
		<tr class="new_bg">
			<th>
				<div align="center">
					<bean:message key="airTicket.supplier.id" />
				</div>
			</th>
			<logic:notEmpty name="x_purchaseTypeList">
				<th>
					<div align="center">
						<bean:message key="airTicket.purchaseType.code" />
					</div>
				</th>
			</logic:notEmpty>
			<th>
				<div align="center">
					<bean:message key="airTicket.flightNo" />
					.
				</div>
			</th>
			<th>
				<div align="center">
					<bean:message key="airTicket.class" />
				</div>
			</th>
			<th>
				<div align="center">
					<bean:message key="airTicket.from" />
				</div>
			</th>
			<th>
				<div align="center">
					<bean:message key="airTicket.to" />
				</div>
			</th>
			<th>
				<div align="center">
					<bean:message key="airTicket.depart" />
				</div>
			</th>
			<th>
				<div align="center">
					<bean:message key="airTicket.arrive" />
				</div>
			</th>
			<th>
				<div align="center">
					<bean:message key="airTicket.currency.code" />
				</div>
			</th>
			<th>
				<div align="center">
					<bean:message key="airTicket.price" />
				</div>
			</th>
			<th>
				<div align="center">
					<bean:message key="airTicket.status" />
				</div>
			</th>
			<th>
				&nbsp;
			</th>
		</tr>
	</thead>
	<tbody id="airTicket_datatable">
		<c:forEach var="X_OBJECT" items="${x_airTicketList}">
			<bean:define id="X_OBJECT" toScope="request" name="X_OBJECT" />
			<jsp:include page="airTicketRow.jsp" />
		</c:forEach>
	</tbody>
</table>
</div>

<script type="text/javascript">
    applyRowStyle(document.all('airTicket_datatable'));
</script>

 