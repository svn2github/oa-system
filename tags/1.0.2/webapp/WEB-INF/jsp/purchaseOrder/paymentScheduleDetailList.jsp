<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<script type="text/javascript">
<!--
	function editPaymentScheduleDetail(id) {
		var title="paymentScheduleDetail.edit.title";
		var url="editPaymentScheduleDetail.do?purchaseOrder_id=${x_po.id}&id="+id ;
		var v=dialogAction(url,title,400,300);
		if (v) {
			modifyPSDTotalAmount(
				getPSDRowById(id),"delete");
			updateRow(getPSDRowById(id), v);
			modifyPSDTotalAmount(
				getPSDRowById(id),"add");
		};
	}
	
	function getLastPSDRow()
	{
		 var table=getPSDTable();
		 var row = table.rows[table.rows.length - 1];
		 return row;
	}
	
	function modifyPSDTotalAmount(row,addOrDelete)
	{
		var amount = parseFloat(row.all('span_paymentScheduleDetail_amount').innerText);
		if (!isNaN(amount)) {
			if(addOrDelete=='add')
			  	totalPaymentScheduleDetailAmount += amount;
			else if(addOrDelete=='delete') 	
				totalPaymentScheduleDetailAmount -= amount;
			else alert("addOrDelete error");
		}
	}
	
	function getPSDRowById(id)
	{
		 var table=getPSDTable();
		 return table.all('r' + id);		
	}
	
	function getPSDTable()
	{
		return document.all('paymentScheduleDetail_datatable');
	}
	
	
	function addPaymentScheduleDetail() {
		var title="paymentScheduleDetail.new.title";
		var url="newPaymentScheduleDetail.do?purchaseOrder_id=${x_po.id}";
		var v=dialogAction(url,title,400,300);
		
		if (v) {
			appendRow(getPSDTable(),v);
		    modifyPSDTotalAmount(getLastPSDRow(),"add");
		    			
		    applyRowStyle(getPSDTable());
		};
	}
	
	
	function deletePaymentScheduleDetail(id) {
		var title="paymentScheduleDetail.delete.title";
		var url="deletePaymentScheduleDetail.do?purchaseOrder_id=${x_po.id}&id="+id ;
		var v=dialogAction(url,title,500,600);
		if (v) {
			modifyPSDTotalAmount(getPSDRowById(id),"delete");
			
		    deleteRow(getPSDRowById(id));
		    
		    applyRowStyle(getPSDTable());
		};
	}
	

//-->
</script>


<h3 style="color:blue"><bean:message key="purchaseOrder.paymentScheduleDetail"/></h3>

<table width=100% cellpadding=1 cellspacing=1>
	 <tr>
 		<td>
 			<bean:message key="purchaseOrder.receiver"/>:
 		</td>
 		<td>
 			<c:if test="${x_edit}">
	 			<html:text size="10" property="receiver"/>
	 		</c:if>
	 		<c:if test="${!x_edit}">
	 			${x_po.receiver}
	 		</c:if>
 		</td>
 		<td>
 			<bean:message key="purchaseOrder.receiveAddress"/>:
 		</td>
 		<td>
 			<c:if test="${x_edit}">
	 			<html:text size="50" property="receiveAddress"/>
	 		</c:if>
	 		<c:if test="${!x_edit}">
	 			${x_po.receiveAddress}
	 		</c:if>
 		</td>
	</tr>
</table>	
<hr>
<c:if test="${x_edit}">
	<input type="button" value="<bean:message key="all.new"/>" onclick="addPaymentScheduleDetail();"/>
	<script>
		var totalPaymentScheduleDetailAmount=0;
	</script>
</c:if>

<table class="data">
	<thead>
		<tr class="new_bg">
			<th><bean:message key="paymentScheduleDetail.description" /></th>
			<th><bean:message key="paymentScheduleDetail.date" /></th>
			<th><bean:message key="paymentScheduleDetail.amount" /></th>
			<c:if test="${x_edit}">
			<th></th>
			</c:if>
		</tr>
	</thead>
	<tbody id="paymentScheduleDetail_datatable">
		<c:forEach var="X_OBJECT" items="${x_paymentScheduleDetailList}">
			<c:set var="X_OBJECT" scope="request" value="${X_OBJECT}"/>
			<c:if test="${x_edit}">
				<script>
					totalPaymentScheduleDetailAmount+=${X_OBJECT.amount};
				</script>
			</c:if>
			<jsp:include page="paymentScheduleDetailRow.jsp" />
		</c:forEach>
	</tbody>
</table>
<script type="text/javascript">
<!--
    applyRowStyle(document.all('paymentScheduleDetail_datatable'));
//-->
</script>
