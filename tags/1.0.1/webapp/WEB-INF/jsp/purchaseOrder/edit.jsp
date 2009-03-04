<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>


<script type="text/javascript">
<!--
	
	
	
	function validateForm(form)
	{
		if(!validatePurchaseOrderForm(form)) return false;
		with(document.purchaseOrderForm)
		{
		}		
		return true;
	}
	
	function backToList()
	{
		var url="listPurchaseOrder.do"
		window.location.href=url;
	}
	
	function cancelMe()
	{
		if(confirm("<bean:message key="purchaseOrder.cancel.confirm" />"))
		{
			var url="cancelPurchaseOrder.do?id=${x_po.id}";
			window.location.href=url;
		}
	}
	
	function submitMe()
	{
		var form=document.purchaseOrderForm;
		form.draft.value="false";
		if(validateForm(form))
		{	
			if (getPSDTable().rows.length > 0) {
				if(Math.round(totalAmount*100)!=Math.round(totalPaymentScheduleDetailAmount*100))
				{
					alert("<bean:message key="purchaseOrder.submit.totalAmountNotEqualTotalPaymentAmount"/>");
					return false;
				}
			}
			if(confirm("<bean:message key="purchaseOrder.submit.confirm"/>"))
				form.submit();
		}
	}
	
	function saveAsDraft()
	{
		var form=document.purchaseOrderForm;
		form.draft.value="true";
		if(validateForm(form))
			form.submit();
	}
	
	function viewApprover_callback(content) {
		with (document.getElementById("newApprovers")) {
			innerHTML = content;
			scrollIntoView();
		}
	}
	
	function viewApprover()
	{
		var form=document.purchaseOrderForm;
		if (validateForm(form)) {
			var action = form.action;
			var i = action.lastIndexOf('.');
			if (i == -1) {
				form.action = action + "_viewApprover";
			} else {
				form.action = action.substring(0, i) + "_viewApprover" + action.substring(i);
			}
			form.target = "viewapprover";
			form.submit();
			form.action = action;
			form.target = "";
			document.getElementById("oldApprovers").style.display = "none";
		}
	}
//-->
</script>

<jsp:include page="baseEdit.jsp"/>
<div id="newApprovers"></div>
<iframe id="viewapprover" name="viewapprover" src="" frameborder="0" height="200" width="570" style="display:none">
</iframe>
<table width="100%">
	<tr>
		<td>
			<c:if test="${x_po.editable}">
				<input type="button" value="<bean:message key="purchaseOrder.viewApprover"/>" 
					onclick="viewApprover();"/>
			</c:if>
		</td>
		<td align="right">
			<input type="button" value="<bean:message key="capexRequest.saveAsDraft" />" onclick="saveAsDraft()"/>
			<input type="button" value="<bean:message key="all.cancel" />" onclick="cancelMe()"/>
			<input type="button" value="<bean:message key="all.submit" />" onclick="submitMe()"/>
			<input type="button" value="<bean:message key="all.back"/>"	onclick="backToList()">
		</td>
	</tr>
</table>