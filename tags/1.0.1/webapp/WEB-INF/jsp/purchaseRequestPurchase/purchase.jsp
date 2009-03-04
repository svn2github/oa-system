<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<div class="message"><html:messages id="x_message" message="true">${x_message}<br></html:messages></div>
<div class="warningMsg"><html:errors/></div>

<script>
	function backToList()
	{
		var url='listPurchaseRequest_purchase.do';
		window.location.href=url;
	}
	function validateForm(form)
	{
		return true;
	}
	function reject()
	{
		v = window.showModalDialog(
			"showDialog.do?title=approveRequest.approve&approveRequestAddComment.do", 
			null, 'dialogWidth:450px;dialogHeight:250px;status:no;help:no;scroll:no');
		if (v) {
			var url="rejectPurchaseRequest_purchase.do?id=${x_pr.id}&rejectComment="+v[0];
			window.location=url;
		};
	}
	function checkSelectedPOItem()
	{
		var objForm = document.purchaseRequestPurchaseForm;
	    var objLen = objForm.length;
	    var checked=false;
	    var supplier="";
	    var exchangeRate="";
	    for (var iCount = 0; iCount < objLen; iCount++)
	    {
	    	var control=objForm.elements[iCount];
	    	if (control.type != "checkbox") continue;
	    	if (control.name != "po_ids") continue;
            if(!control.checked)continue; 	    	
            
			if(!checked)//first	            	
			{
				checked=true;
			}
			else// not first
			{
				if(supplier!=control.supplier)
				{
					alert("<bean:message key="purchaseRequest.purchase.createPO.supplierNotSame"/>");
					return false;
				}
				if(exchangeRate!=control.exchangeRate)
				{
					alert("<bean:message key="purchaseRequest.purchase.createPO.exchangeRateNotSame"/>");
					return false;
				}
			}
			supplier=control.supplier;
			exchangeRate=control.exchangeRate;
		}	
		if(!checked)
		{
			alert("<bean:message key="purchaseRequest.purchase.createPO.noSelected"/>");
			return false;
		}
		return true;
		
	}
	function createPO()
	{
		var form=document.purchaseRequestPurchaseForm;
		if(checkSelectedPOItem(form))
		{
			form.createPO.value="true";
			form.submit();
		}
	}
	function save()
	{
		var form=document.purchaseRequestPurchaseForm;
		with(form)		
		{
			createPO.value="false";
			form.submit();
		}
	}
	
</script>
<%
	//baseEdit.jsp, attachmentList.jsp为只读状态
	Boolean oldEdit=(Boolean)request.getAttribute("x_edit");
	request.setAttribute("x_edit",Boolean.FALSE);
%>
<jsp:include page="../purchaseRequest/editPurchaseRequest.jsp"/>
<jsp:include page="../purchaseRequest/attachmentList.jsp"/>

<html:form action="purchasePurchaseRequest_result.do" onsubmit="return validateForm(this)">
<input type="hidden" name="id" value="${x_pr.id}"/>
<input type="hidden" name="comment" value=""/>
<input type="hidden" name="createPO" value="false"/>
<%
	request.setAttribute("x_edit",oldEdit);
%>
<jsp:include page="itemList.jsp"/>
</html:form>

<table width="100%">
	<tr>
		<td align="right">
			<c:if test="${x_edit}">
			<input type="button" value="<bean:message key="purchaseRequest.purchase.createPO"/>"	
				onclick="createPO()">
			<input type="button" value="<bean:message key="all.save"/>"	
				onclick="save()">
			<input type="button" value="<bean:message key="all.reject"/>"	
				onclick="reject()">
			</c:if>
			<input type="button" value="<bean:message key="all.back"/>"	
				onclick="backToList()">				
				
		</td>
	</tr>
</table>

