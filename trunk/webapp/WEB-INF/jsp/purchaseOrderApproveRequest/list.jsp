<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>
<script type="text/javascript">
<!--

	function edit(approveRequestId,approverId) {
		window.location.href="viewPurchaseOrderApproveRequest.do?request_id="+approveRequestId+"&approver_id="+approverId;
	}
	
//-->
</script>
<html:javascript formName="purchaseOrderApproveRequestQueryForm" staticJavascript="false" />
<html:form action="listPurchaseOrderApproveRequest" onsubmit="return validatePurchaseOrderApproveRequestQueryForm(this);">
	<html:hidden property="order" />
	<html:hidden property="descend" />

	<table width=100% border=0 cellpadding=4 cellspacing=0>
		<tr>
			<td class="bluetext"><bean:message key="purchaseOrderApproveRequest.search.no" />:</td>
			<td><html:text property="code"/></td>
			<td class="bluetext"><bean:message key="purchaseOrderApproveRequest.search.title" />:</td>
			<td><html:text property="title"/></td>
		</tr>
		<tr>
			<td class="bluetext"><bean:message key="purchaseOrderApproveRequest.search.approveStatus"/>:</td>
			<td>
			    <html:select property="approveStatus">
			    <html:option value=""><bean:message key="all.selectall"/></html:option>
			      <html:options collection="X_APPROVESTATUSLIST" property="enumCode" labelProperty="${x_lang}ShortDescription"/>
			    </html:select>
			</td>
	  		<td class="bluetext"><bean:message key="purchaseOrderApproveRequest.search.submitDate"/>:</td>
	  		<td>
		  		<table border=0 cellpadding=0 cellspacing=0>
			  		<tr>
			  		<td>
						<table border=0 cellpadding=0 cellspacing=0>
						<tr>
							<td>
								<html:text property="submitDateFrom" size="6"  maxlength="10" />
							</td>
							<td>
								<a onclick="event.cancelBubble=true;" href="javascript:showCalendar('dimg1',false,'submitDateFrom',null,null,'purchaseOrderApproveRequestQueryForm')"><IMG align="absMiddle" border="0" id="dimg1" src="images/datebtn.gif" ></A>
							</td>
						</tr>
						</table>
			  		</td>
			  		<td width="20" align="center">~</td>
			  		<td>
						<table border=0 cellpadding=0 cellspacing=0>
						<tr>
							<td>
								<html:text property="submitDateTo" size="6"  maxlength="10" />
							</td>
							<td>
								<a onclick="event.cancelBubble=true;" href="javascript:showCalendar('dimg2',false,'submitDateTo',null,null,'purchaseOrderApproveRequestQueryForm')"><IMG align="absMiddle" border="0" id="dimg2" src="images/datebtn.gif" ></A>
							</td>
						</tr>
						</table>
			  		</td>
			  		</tr>
		  		</table>
	  		</td>
	  	</tr>
	  	<tr>
			<td class="bluetext"><bean:message key="purchaseOrderApproveRequest.search.supplier"/>:</td>
			<td><html:text property="supplier_name"/></td>
	  		<td class="bluetext"><bean:message key="purchaseOrderApproveRequest.search.amount"/>:</td>
	  		<td colspan="2">
	  			<table cellpadding="0" cellspacing="0">
	  				<tr>
	  					<td><html:text property="amountFrom" size="6"/>
	  					<td width="20" align="center">~</td>
	  					<td><html:text property="amountTo" size="6"/></td>
	  				</tr>
	  			</table>
	  		</td>
	  	</tr>
	  	<tr>
			<td colspan="3"></td>
			<td align="left">
				<html:submit><bean:message key="all.query"/></html:submit>
			</td>
		</tr>
	</table>
</html:form>
<hr />
<page:form action="listPurchaseOrderApproveRequest.do" method="post">
	<jsp:include page="../pageHead.jsp"/>
	<table class="data">
		<thead>
			<tr bgcolor="#9999ff">
				<th width="20%"><page:order order="purchaseOrder_id" style="text-decoration:none">
					<bean:message key="purchaseOrder.id" />
					<page:desc><img src="images/down.gif" border="0" /></page:desc>
					<page:asc><img src="images/up.gif" border="0" /></page:asc>
				</page:order></th>
				<th width="20%">
					<bean:message key="purchaseOrder.title" />
				</th>
				<th width="10%">
					<bean:message key="purchaseOrder.supplier" />
				</th>
				<th width="10%">
					<bean:message key="purchaseOrder.purchaser.id" />
				</th>
				<th width="15%">
					<bean:message key="purchaseOrder.amount" />
				</th>
				<th width="10%">
					<bean:message key="purchaseOrderApproveRequest.table.submitDate" />
				</th>
				<th width="10%">
					<bean:message key="purchaseOrderApproveRequest.table.status" />
				</th>
			</tr>

		</thead>

		<tbody id="datatable">
			<logic:iterate id="X_OBJECT" name="X_RESULTLIST">
			<tr>
				<td>
					<a href='javascript:edit("${X_OBJECT.purchaseOrder.approveRequestId}","${X_OBJECT.purchaseOrderApproveRequest.approver.id}")'>${X_OBJECT.purchaseOrder.id}</a>
				</td>
				<td>${X_OBJECT.purchaseOrder.title}</td>
				<td>${X_OBJECT.purchaseOrder.supplier.name}</td>
				<td>${X_OBJECT.purchaseOrder.purchaser.name}</td>
				<td align="right">${X_OBJECT.purchaseOrder.amount}</td>
				<td align="center">
					<bean:write name="X_OBJECT" property="purchaseOrder.requestDate" format="yyyy/MM/dd"/>
				</td>
				<td align="center">
				  	<span style="color:${X_OBJECT.purchaseOrderApproveRequest.status.color}">
				  	  <bean:write name="X_OBJECT" property="purchaseOrderApproveRequest.status.${x_lang}ShortDescription"/>
				    </span>  
				</td>
			</tr>	
			</logic:iterate>
		</tbody>
	</table>
	<jsp:include page="../pageTail.jsp"/>
</page:form>

<script type="text/javascript">
    applyRowStyle(document.all('datatable'));
</script>

