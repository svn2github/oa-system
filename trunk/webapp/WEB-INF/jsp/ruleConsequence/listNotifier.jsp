<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<script type="text/javascript">
	function addNotifier() {
		var v = window.showModalDialog(
			'showDialog.do?title=ruleConsequence.newNotifier.title&new${X_RULE.type.prefixUrl}RuleConsequence.do?rule_id=${X_RULE.id}', 
			null, 'dialogWidth:650px;dialogHeight:570px;status:no;help:no;scroll:no');
		if (v) {
			var table = document.all('datatableNotifier');
			appendRow(table, v);
		    applyRowStyle(table);
		};
	}
	
	function deleteNotifier(id) {
		var v = window.showModalDialog(
		    'showDialog.do?title=ruleConsequence.deleteNotifier.title&confirmOperationDialog.do?message=ruleConsequence.deleteNotifier.message&delete${X_RULE.type.prefixUrl}RuleConsequence.do?rule_id=${X_RULE.id}&user_id=' + id, 
		    null, 'dialogWidth:300px;dialogHeight:143px;status:no;help:no;scroll:no');
		if (v) {
			deleteRow(document.all('datatableNotifier').all('r' + id));
		}
	}
//-->
</script>
<table width="100%">
  <tbody>
    <tr>
      <td><h3 class="formtitle"><bean:message key="ruleConsequence.notifier"/></h3></td>
    </tr>
    <tr>
      <td align="left"><input type="button" value="<bean:message key="ruleConsequence.button.addnotifier"/>" onclick="addNotifier();"/></td>
    </tr>
 </tbody>
</table>
<table class="data">
  <thead>
    <tr bgcolor="#9999ff">
      <th width="90%"><bean:message key="ruleConsequence.notifier"/></th>
      <th width="10%"></th>
    </tr>
  </thead>
  <tbody id="datatableNotifier">
  	<c:if test="${X_RULE != null}">
    <bean:define id="X_OBJECTS" toScope="request" name="X_RULE" property="consequences"/>
    <jsp:include page="notifierRows.jsp"/>
    </c:if>
  </tbody>
</table>
<script type="text/javascript">
  applyRowStyle(document.all('datatableNotifier'));
</script>
