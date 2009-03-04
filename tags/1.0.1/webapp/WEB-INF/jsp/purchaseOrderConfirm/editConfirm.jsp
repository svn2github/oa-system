<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>


<script type="text/javascript">
<!--
	function backToList()
	{
		var url="listPurchaseOrder_confirm.do"
		window.location.href=url;
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
<div class="message"><html:messages id="x_message" message="true">${x_message}<br></html:messages></div>
<html:form action="updateConfirmedPurchaseOrder.do" onsubmit="validatePurchaseOrderConfirmForm(this)">
	<input type="hidden" name="id" value="${x_po.id}"/>
	<%request.setAttribute("x_edit",Boolean.FALSE);%>
	<%request.setAttribute("x_notShowInspector",Boolean.TRUE);%>
	<jsp:include page="../purchaseOrder/editPurchaseOrder.jsp"/>
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
	
	<jsp:include page="../purchaseOrder/itemList.jsp"/>
	<%request.setAttribute("x_edit",Boolean.FALSE);%>
	<jsp:include page="../purchaseOrder/attachmentList.jsp"/>
	<jsp:include page="../purchaseOrder/paymentScheduleDetailList.jsp"/>
	<div id="oldApprovers">
		<jsp:include page="../approve/list.jsp"/>
	</div>


<table width="100%">
	<tr>
		<td align="right">
			<input type="button" value="<bean:message key="all.pdf"/>"	
				onclick="window.location.href='exportPurchaseOrderPDF_confirm.do?id=${x_po.id}'">
			<html:submit><bean:message key="all.save" /></html:submit>
			<input type="button" value="<bean:message key="all.back"/>"	onclick="backToList()">
		</td>
	</tr>
</table>
</html:form>