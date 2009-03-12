<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ page import="net.sourceforge.model.metadata.RoleType" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<script type="text/javascript">
<!--
	function selectSuperior() {
		v = window.showModalDialog(
			'showDialog.do?title=ruleConsequence.selectSuperior.title&select${X_RULETYPE.prefixUrl}ConsequenceUser.do', 
			null, 'dialogWidth:650px;dialogHeight:500px;status:no;help:no;scroll:no');
		if (v) {
			ruleConsequenceForm.superior_id.value = v['id'];
			ruleConsequenceForm.superior_name.value = v['name'];
			document.getElementById('span_superior_name').innerHTML = v['name'];
		};
	}
//-->
</script>
<html:javascript formName="ruleConsequenceForm" staticJavascript="false" />
<html:form action="update${X_RULETYPE.prefixUrl}RuleConsequence${WebDragAndDraw}.do" onsubmit="return validateRuleConsequenceForm(this);">
<html:hidden property="rule_id"/>
<html:hidden property="user_id"/>
<html:hidden property="user_name"/>
<html:hidden property="superior_id"/>
<html:hidden property="superior_name"/>
<input type="hidden" name="site_id" value='<%=request.getParameter("site_id") %>'/>
<table border="0" cellpadding="4" cellspacing="0">
<tr>
  <td colspan="2" class="warningMsg"><html:errors/></td>
</tr>
<tr>
  <td class="bluetext"><bean:message key="ruleConsequence.seq"/>:</td>
  <td>
    <html:select property="seq">
<%
	int max_seq = ((Integer) request.getAttribute("X_MAXSEQ")).intValue();
	for (int i = max_seq; i > 0; i--) {
		pageContext.setAttribute("X_SEQ", new Integer(i));
%>
      <html:option value="${X_SEQ}"/>
<%
	}
%>
    </html:select>
  </td>
</tr>
<tr>
  <td class="bluetext"><bean:message key="ruleConsequence.approver"/>:</td>
  <td><bean:write name="ruleConsequenceForm" property="user_name"/></td>
</tr>
<tr>
  <td class="bluetext"><bean:message key="ruleConsequence.canModify"/>:</td>
  <td>
    <html:select property="canModify">
      <html:options collection="X_YESNOLIST" property="enumCode" labelProperty="${x_lang}ShortDescription"/>
    </html:select>
  </td>
</tr>
<tr>
  <td class="bluetext"><bean:message key="ruleConsequence.superior"/>:</td>
  <td>
    <span id="span_superior_name"><bean:write name="ruleConsequenceForm" property="superior_name"/></span>
    &nbsp;<a href="javascript:selectSuperior()"><img src="images/select.gif" width="16" height="16" border="0"/></a>
  </td>
</tr>
</table>
<hr/>
<table width="90%" border="0" cellpadding="4" cellspacing="0">
<tr>
  <td align="right">
  	<html:submit><bean:message key="all.save"/></html:submit>
  	<input type="button" value="<bean:message key="all.cancel"/>" onclick="window.parent.close();"/>
  </td>
</tr>
</table>
</html:form>
