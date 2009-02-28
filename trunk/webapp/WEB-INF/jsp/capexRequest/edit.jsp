<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<script type="text/javascript">
<!--
	function save() {
		var form = document.capexRequestForm;
		with (form) {
			if(validateForm(form) && confirm("<bean:message key="capexRequest.submit.confirm"/>")) {
				form.submit();
			}
		}
	}
	
	function saveAsDraft() {
		var form = document.capexRequestForm;
		if (validateForm(form)) {
			with (form) {
				draft.value = "true";
				form.submit();
			}
		}
	}
	
	function deleteMe() {
		if(confirm("<bean:message key="capexRequest.delete.confirm" />")) {
			window.location.href = "deleteCapexRequest.do?id=${x_capexRequest.id}";
		}
	}

	
	function viewApprover() {
		var form = document.capexRequestForm;
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
	
	function viewApprover_callback(content) {
		with (document.getElementById("newApprovers")) {
			innerHTML = content;
			scrollIntoView();
		}
	}
	
	function validateForm(form) {
		return validateCapexRequestForm(form);
	}
//-->
</script>
<jsp:include page="baseEdit.jsp"/>
<div id="newApprovers"></div>
<iframe id="viewapprover" name="viewapprover" src="" frameborder="0" height="200" width="570" style="display:none"></iframe>
<table width="100%">
	<tr>
		<td>
			<c:if test="${x_capexRequest.editable}"><input type="button" value="<bean:message key="capexRequest.viewApprover"/>" onclick="viewApprover();"/></c:if>
		</td>
		<td align="right">
			<input type="button" value="<bean:message key="capexRequest.saveAsDraft" />" onclick="saveAsDraft()"/>
			<input type="button" value="<bean:message key="all.delete" />" onclick="deleteMe()"/>
			<input type="button" value="<bean:message key="all.submit" />" onclick="save()"/>
			<input type="button" value="<bean:message key="all.back"/>"	onclick="window.location.href='listCapexRequest.do'">
		</td>
	</tr>
</table>