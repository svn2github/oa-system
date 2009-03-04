<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<script type="text/javascript">
	<c:if test="${X_RULE != null}">
	function addApprover() {
		var v = window.showModalDialog(
			'showDialog.do?title=ruleConsequence.newApprover.title&new${X_RULE.type.prefixUrl}RuleConsequence.do?rule_id=${X_RULE.id}', 
			null, 'dialogWidth:400px;dialogHeight:230px;status:no;help:no;scroll:no');
		if (v) {
			var table = document.all('datatableApprover');
			refreshTable(table, v);
		    applyRowStyle(table);
		};
	}
	
	function editApprover(id, seq, canModify, superiorId) {
		var v = window.showModalDialog(
			'showDialog.do?title=ruleConsequence.editApprover.title&edit${X_RULE.type.prefixUrl}RuleConsequence.do?rule_id=${X_RULE.id}&user_id=' + id, 
			null, 'dialogWidth:400px;dialogHeight:230px;status:no;help:no;scroll:no');
		if (v) {
			var table = document.all('datatableApprover');
			refreshTable(table, v);
		    applyRowStyle(table);
		};
	}

	function deleteApprover(id) {
		var v = window.showModalDialog(
		    'showDialog.do?title=ruleConsequence.deleteApprover.title&confirmOperationDialog.do?message=ruleConsequence.deleteApprover.message&delete${X_RULE.type.prefixUrl}RuleConsequence.do?rule_id=${X_RULE.id}&user_id=' + id, 
		    null, 'dialogWidth:300px;dialogHeight:143px;status:no;help:no;scroll:no');
		if (v) {
			var table = document.all('datatableApprover');
			refreshTable(table, v);
		    applyRowStyle(table);
		}
	}
	</c:if>	
	<c:if test="${X_RULETYPE != null}">	
	function addApprover() {
		var approvers = document.getElementById("datatableApprover");
		var v = window.showModalDialog(
			'showDialog.do?title=ruleConsequence.newApprover.title&new${X_RULETYPE.prefixUrl}RuleConsequenceWebDragAndDraw.do?site_id=<%=request.getParameter("site_id")%>&MaxConsequenceSeqNo='+approvers.rows.length, 
			null, 'dialogWidth:400px;dialogHeight:230px;status:no;help:no;scroll:no');
		if (v) {
			var table = document.all('datatableApprover');
			refreshTable(table, v);
		    applyRowStyle(table);
		};
	}
	
	function editApprover(id, seq, canModify, superiorId) {
		var approvers = document.getElementById("datatableApprover");
		var v = window.showModalDialog(
			'showDialog.do?title=ruleConsequence.editApprover.title&edit${X_RULETYPE.prefixUrl}RuleConsequenceWebDragAndDraw.do?site_id=<%=request.getParameter("site_id")%>&MaxConsequenceSeqNo='+approvers.rows.length + '&user_id=' + id + '&seq=' + seq + '&canModify=' + canModify + '&superiorId=' + superiorId, 
			null, 'dialogWidth:400px;dialogHeight:230px;status:no;help:no;scroll:no');
		if (v) {
			var table = document.all('datatableApprover');
			refreshTable(table, v);
		    applyRowStyle(table);
		};
	}

	function deleteApprover(id) {
		var table = document.all('datatableApprover');
		deleteRow(table.all('r' + id));
	    applyRowStyle(table);
	}
	</c:if>
//-->
</script>
<table width="100%">
  <tbody>
    <tr>
      <td><h3 class="formtitle"><bean:message key="ruleConsequence.approver"/></h3></td>
    </tr>
    <tr>
      <td><input type="button" value="<bean:message key="ruleConsequence.button.addapprover"/>" onclick="javascript:addApprover();"/></td>
    </tr>
 </tbody>
</table>
<table class="data">
  <thead>
    <tr class="new_bg">
      <th width="10%"><bean:message key="ruleConsequence.seq"/></th>
      <th width="30%"><bean:message key="ruleConsequence.approver"/></th>
      <th width="20%"><bean:message key="ruleConsequence.canModify"/></th>
      <th width="30%"><bean:message key="ruleConsequence.superior"/></th>
      <th width="10%"></th>
    </tr>
  </thead>
  <tbody id="datatableApprover">
  	<c:if test="${X_RULE != null}">
    <bean:define id="X_OBJECTS" toScope="request" name="X_RULE" property="consequences"/>   
    <jsp:include page="approverRows.jsp"/>
    </c:if>   
  </tbody>
</table>
<script type="text/javascript">
  applyRowStyle(document.all('datatableApprover'));
</script>
