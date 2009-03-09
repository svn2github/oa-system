<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>
<%@ page import="java.util.Date"%>

<script>
	function validateForm(form)
	{
		if(!validatePurchaseOrderItemReceiptForm(form)) return false;
		var txtQuantity=form.receiveQty${x_receiverNo};
		txtQuantity.value=trim(txtQuantity.value);
		if(txtQuantity.value=="")
		{
			alertRequired("<bean:message key="purchaseOrderItemReceipt.quantity"/>");
			txtQuantity.focus();	
			return false;
		}
		if(txtQuantity.value=="0")
		{
			alert("<bean:message key="purchaseOrderItemReceipt.quantity.zero"/>");
			txtQuantity.focus();	
			return false;
		}
		var qty=parseInt(txtQuantity.value);
		if (qty<0) qty=-qty;
		if(qty>${x_maxQty})
		{
			alert("<bean:message key="purchaseOrderItemReceipt.quantity.moreThanMax"/>");
			txtQuantity.focus();	
			return false;
		}
		return true;
	}
</script>
<html:javascript formName="purchaseOrderItemReceiptForm" staticJavascript="false"/>
