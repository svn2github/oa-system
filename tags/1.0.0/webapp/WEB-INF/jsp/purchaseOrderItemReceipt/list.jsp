<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>
<script language="javascript" src="includes/table.js"></script>
<script type="text/javascript">
<!--
	function backToList()
	{
		var url="listPurchaseOrderItem_receipt.do"
		window.location.href=url;
	}
	function add() {
		var url="newPurchaseOrderItemReceipt.do?purchaseOrderItem_id=${x_poi.id}";
		var title="purchaseOrderItemReceipt.new.title";
		
		var v=dialogAction(url,title,400,200);
		if (v) {
			appendRow(getTable(), v);
		    applyRowStyle(getTable());
		};
	}
	
	function getTable()
	{
		return document.all('datatable');
	}
	
	function getRowById(id)
	{
		return document.all('r' + id);
	}
	
	function deleteReceipt(id)
	{
		var url="deletePurchaseOrderItemReceipt.do?id="+id;
		var title="purchaseOrderItemReceipt.delete.title";
		var message="purchaseOrderItemReceipt.delete.confirm";
		
		var v=confirmDialog(url,title,message,400,200);
		if(v)
		{
			deleteRow(getRowById(id));
			applyRowStyle(getTable());
		}
	}

	function edit(id) {
		if(cmdAdd.disabled) 
		{
			alert("<bean:message key="purchaseOrderItemReceipt.edit.purchaseOrderItem.alreadyReceived"/>");
			return ;
		}
		
		var url="editPurchaseOrderItemReceipt.do?id="+id;
		var title="purchaseOrderItemReceipt.edit.title";
		
		var v=dialogAction(url,title,400,200);
		if (v) {
			updateRow(getRowById(id), v);
			var qty = parseInt(getRowById(id).all('row_qty').innerText);
			if(qty>0)
			{
				var spanReceivedQty=document.all('receivedQuantity_span');
				var receivedQty=parseInt(spanReceivedQty.innerText);
				if (isNaN(receivedQty)) receivedQty=0;
				receivedQty+=qty;
				spanReceivedQty.innerHTML=""+receivedQty;
			}
			else if(qty<0)
			{
				var spanReturnedQty=document.all('returnedQuantity_span');
				var returnedQty=parseInt(spanReturnedQty.innerText);
				if (isNaN(returnedQty)) returnedQty=0;
				returnedQty-=qty;
				spanReturnedQty.innerHTML=""+returnedQty;
			}
			if(qty!=0)
			{
				var spanReceived=getRowById(id).all('received_span');
				if(spanReceived.innerText=="true")
				{
					cmdAdd.disabled=true;
					document.all.message_div.innerHTML="<bean:message key="purchaseOrderItemReceipt.edit.purchaseOrderItem.alreadyReceived"/>";
				}
				else
					cmdAdd.disabled=false;
			}
		};
	}
	function viewPR(id) {
		var title="purchaseRequest.view.title";
		var url="viewPurchaseRequest_dialog.do?id="+id ;
		dialogAction(url,title,700,550);
	}
	
	
//-->
</script>
<div class="message" id="message_div"><html:messages id="x_message" message="true">${x_message}<br></html:messages></div>
<table width=90% cellpadding=1 cellspacing=1>
  <tr>
    <td width="26%" class='bluetext'><bean:message key="purchaseOrderItemReceipt.purchaseRequest.id"/>:&nbsp;</td>
    <td width="22%"><a href='javascript:viewPR("${x_poi.purchaseRequestItem.purchaseRequest.id}");'>${x_poi.purchaseRequestItem.purchaseRequest.id}</a></td>
    <td width="24%" class='bluetext'>&nbsp;</td>	
    <td width="28%">&nbsp;</td>
  </tr>
</table>
<table width="90%">
	<tr>
		<td width="26%" class='bluetext'><bean:message key="purchaseOrderItemReceipt.item.description"/>: </td>
	 	<td width="22%">${x_poi.itemSpec}</td>
		<td width="27%">&nbsp;</td>
		<td width="25%">&nbsp;</td>
   </tr>
   
		<tr>
			<td  class='bluetext'><bean:message key="purchaseOrderItemReceipt.quantity"/>:
			</td>
		<td>${x_poi.quantity}</td>
		</tr>
		<tr>
			<td  class='bluetext'><bean:message key="purchaseOrderItemReceipt.receivedQuantity"/>:
			</td>
		<td><span id="receivedQuantity_span">${x_poi.receivedQuantity}</span></td>
		</tr>
		<tr>
			<td  class='bluetext'><bean:message key="purchaseOrderItemReceipt.returnedQuantity"/>:
			</td>
		<td><span id="returnedQuantity_span">${x_poi.returnedQuantity}</span></td>
		</tr>
		<tr>
			<td  class='bluetext'><bean:message key="purchaseOrderItemReceipt.cancelledQuantity"/>:
			</td>
		<td>${x_poi.cancelledQuantity}</td>
		</tr>
</table>	


<hr />
	<table class="data">
		<thead>
			<tr bgcolor="#9999ff">
				<th colspan="2" width="40%">
					${x_poi.requestor.name}
				</th>
				<th colspan="2" width="40%">
					${x_poi.inspector.name}
				</th>
			  	<th width="20%"></th>
			</tr>
		</thead>

		<tbody id="datatable">
			<logic:iterate id="X_OBJECT" name="X_RESULTLIST">
				<bean:define id="X_OBJECT" toScope="request" name="X_OBJECT" />
				<jsp:include page="row.jsp" />
			</logic:iterate>
		</tbody>
	</table>


<script type="text/javascript">
    applyRowStyle(document.all('datatable'));
</script>

	<table width="81%">
		<tr>	
			<td width=63% align="left">&nbsp;</td>
			<td width="37%" align="right">
			  <c:if test="${!x_poi.received}">	
	              <input id="cmdAdd" type="button" value="<bean:message key="all.new"/>" onClick="add()">			
	          </c:if>    
              <input type="button" value="<bean:message key="all.back"/>" onclick="backToList()">												
		  </td>
		</tr>
	</table>
