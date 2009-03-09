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
		if(!validatePurchaseRequestEditForm(form)) return false;
		with(document.purchaseRequestEditForm)
		{
		}		
		return true;
	}
	
	function backToList()
	{
		var url="listPurchaseRequest${x_postfix}.do"
		window.location.href=url;
	}
	<c:if test="${x_deletable}">
	function deleteMe()
	{
		if(confirm("<bean:message key="purchaseRequest.delete.confirm" />"))
		{
			var url="deletePurchaseRequest${x_postfix}.do?id=${x_pr.id}";
			window.location.href=url;
		}
	}
	</c:if>
	
	function submitMe()
	{
		var form=document.purchaseRequestEditForm;
		form.draft.value="false";
		if(validateForm(form))
		{
			if (Math.round(totalBaseCurrencyAmount * 100) == 0) {
				alert("<bean:message key="purchaseRequest.submit.requestAmountZero"/>");
				return false;
			}
			if(confirm("<bean:message key="purchaseRequest.submit.confirm"/>")) {
				form.submit();
			}
		}
	}
	
	function saveAsDraft()
	{
		var form=document.purchaseRequestEditForm;
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
		var form=document.purchaseRequestEditForm;
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
			<c:if test="${x_pr.editable}">
				<input type="button" value="<bean:message key="purchaseRequest.viewApprover"/>" 
					onclick="viewApprover();"/>
			</c:if>
		</td>
		<td align="right">
			<input type="button" value="<bean:message key="capexRequest.saveAsDraft" />" onclick="saveAsDraft()"/>
			<c:if test="${x_deletable}">
				<input type="button" value="<bean:message key="all.delete" />" onclick="deleteMe()"/>
			</c:if>
			<input type="button" value="<bean:message key="all.submit" />" onclick="submitMe()"/>
			<input type="button" value="<bean:message key="all.back"/>"	onclick="backToList()">
		</td>
	</tr>
</table>