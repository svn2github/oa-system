<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<html:javascript formName="ruleConditionForm" staticJavascript="false" />
<html:form action="update${X_RULETYPE.prefixUrl}RuleCondition${WebDragAndDraw}.do" onsubmit="return validateRuleConditionForm(this);">
<html:hidden property="rule_id"/>
<html:hidden property="type"/>
<input type="hidden" name="site_id" value="<%= request.getParameter("site_id") %>"/>
<table border="0" cellpadding="4" cellspacing="0">
<tr>
  <td>
    <span style="color:${X_RULECONDITION.type.color}"><bean:write name="X_RULECONDITION" property="type.${x_lang}Description"/></span>
  </td>
  <td>
    <logic:notPresent name="X_COMPARETYPELIST"><html:hidden property="compareType"/></logic:notPresent>
    <logic:present name="X_COMPARETYPELIST">
    <html:select property="compareType">
      <html:options collection="X_COMPARETYPELIST" property="enumCode" labelProperty="${x_lang}Description"/>
    </html:select>
    </logic:present>
  </td>
  <td>
    <html:select property="value">
      <html:options collection="X_YESNOLIST" property="enumCode" labelProperty="${x_lang}Description"/>
    </html:select>
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