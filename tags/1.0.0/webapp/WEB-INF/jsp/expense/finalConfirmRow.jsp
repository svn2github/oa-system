<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>


<td align="right"><bean:message key="expense.finalConfirm.confirmedDescription" /></td>

<logic:iterate name="x_escList" id="x_esc">
<td align="right" colspan="2">
	<html:hidden property="amounts(${x_esc.id})"/>
	<html:text property="descriptions(${x_esc.id})" size="20"/>
</td>
</logic:iterate>
<logic:iterate name="x_escList" id="x_esc">
<script type="text/javascript">
<!--
	document.getElementById("amounts(${x_esc.id})").setExpression("value", "document.getElementById('span_expense_${x_esc.id}_total').innerText");
//-->
</script>
</logic:iterate>
<script type="text/javascript">
<!--

    function validateFinalConfirmDescritions(form) {                                                                   
		var formValidationResult;
       	formValidationResult = validateMaxLength(form); 
	    return (formValidationResult == 1);
	}
	
    function expenseFinalConfirmForm_maxlength () { 
		<c:forEach var="x_esc" items="${x_escList}" varStatus="x_status">
		this.a${x_status.index} = new Array("descriptions(${x_esc.id})", "<bean:message key="expense.finalConfirm.confirmedDescription" /><bean:message key="errors.maxlength" arg0="" arg1="24"/>", new Function ("varName", "this.maxlength='24';  return this[varName];"));
		</c:forEach>
    } 
//-->
</script>
<td align="right" colspan="3"><span id="theSum" style="display:none">&nbsp;</span></td>


