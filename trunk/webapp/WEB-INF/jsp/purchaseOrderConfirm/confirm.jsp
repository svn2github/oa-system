<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>


<script type="text/javascript">
<!--
	function confirmSupplier(id)
	{
		var url="confirmSupplier_purchaseOrder.do?id="+id;
		var title="supplier.confirm.title";
		var v=dialogAction(url,title,770,600);
		if(v)
		{
			document.getElementById("supplier_div").innerHTML=v;
			with(document.purchaseOrderConfirmForm)
			{
				if(supplier_enabled.value=="false")
				{
					rejectMe();
				}
			}
		}
	}
	
	function getConfirmButtonDisabled()
	{
		with(document.purchaseOrderConfirmForm) {
			return supplier_confirmed.value!="true";
		}		
	}
	function backToList()
	{
		var url="listPurchaseOrder_confirm.do"
		window.location.href=url;
	}
	
	function rejectMe()
	{
	
		v = window.showModalDialog(
			"showDialog.do?title=approveRequest.reject&approveRequestAddComment.do", 
			null, 'dialogWidth:450px;dialogHeight:250px;status:no;help:no;scroll:no');
		if (v) {
			var url="rejectPurchaseOrder.do?id=${x_po.id}&rejectComment="+v[0];
			window.location.href=url;
		};

	}
	
	function confirmMe()
	{
		var form=document.purchaseOrderConfirmForm;
		if(!validatePurchaseOrderConfirmForm(form))
			return false;
		<c:if test="${!x_fromTa}">
			if(form.inspector_id.value=="")
			{
				alert("<bean:message key="purchaseOrder.confirm.inspector.null" />");
				return false;
			}
		</c:if>	
		if(confirm("<bean:message key="purchaseOrder.confirm.confirm"/>"))
			form.submit();
	}
	function selectInspector()
	{
		var url="selectInspector_purchaseOrderConfirm.do?id=${x_po.id}";
		var title="purchaseOrder.confirm.selectInspector.title";
		var v=dialogAction(url,title,550,350);
		if(v)
		{
			var form=document.purchaseOrderConfirmForm;
			form.inspector_id.value=v["id"];
			document.all.inspector_span.innerHTML=v["name"];
		}
	}
//-->
</script>
<html:javascript formName="purchaseOrderConfirmForm" staticJavascript="false"/>
<html:form action="confirmPurchaseOrder_result.do" onsubmit="validatePurchaseOrderConfirmForm(this)">
	<input type="hidden" name="id" value="${x_po.id}"/>
	<%request.setAttribute("x_edit",Boolean.FALSE);%>
	<%request.setAttribute("x_confirmPurchaseOrder",Boolean.TRUE);%>
	<jsp:include page="../purchaseOrder/editPurchaseOrder.jsp"/>
	<c:if test="${!x_fromTa}">
	<table width="30%">
	<tr>
		<td class='bluetext'><bean:message key="purchaseOrder.inspector.id" />:&nbsp;</td>
		<td align="right">
			<html:hidden property="inspector_id" />
			<span id="inspector_span">${x_inspector.name}</span>&nbsp;
			<a href="javascript:selectInspector()"><img src="images/select.gif" width="16" height="16" border="0"/></a>
			<span class="required">*</span>
			
		</td>
	</tr>
	</table>
	</c:if>
	
	<jsp:include page="itemList.jsp"/>
	<%request.setAttribute("x_edit",Boolean.FALSE);%>
	<jsp:include page="../purchaseOrder/attachmentList.jsp"/>
	<jsp:include page="../purchaseOrder/paymentScheduleDetailList.jsp"/>
	<div id="oldApprovers">
		<jsp:include page="../approve/list.jsp"/>
	</div>
</html:form>

<table width="100%">
	<tr>
		<td align="right">
			<input type="button" name="confirmButton" value="<bean:message key="all.confirm" />" onclick="confirmMe()"/>
			<input type="button" name="rejectButton" value="<bean:message key="all.reject" />" onclick="rejectMe()"/>
			<input type="button" value="<bean:message key="all.back"/>"	onclick="backToList()">
		</td>
	</tr>
</table>
<script>
	with(document.purchaseOrderConfirmForm)
	{
		confirmButton.setExpression("disabled", "getConfirmButtonDisabled()");
	}		
</script>