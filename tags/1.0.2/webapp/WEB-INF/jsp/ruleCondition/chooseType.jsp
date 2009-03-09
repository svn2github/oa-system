<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<html:form action="new${X_RULETYPE.prefixUrl}RuleCondition${WebDragAndDraw}.do" method="post">
<html:hidden property="rule_id"/>
<table border="0" cellpadding="4" cellspacing="0">
<tr>
  <td class="bluetext"><bean:message key="ruleCondition.type"/>:</td>
  <td>
    <html:select property="type">
      <html:options collection="X_AVAILIABLECONDITIONS" property="enumCode" labelProperty="${x_lang}Description"/>
    </html:select>
    <input type="hidden" name="site_id" value="<%= request.getParameter("site_id")%>">
  </td>
</tr>
</table>
<hr/>
<table width="90%" border="0" cellpadding="4" cellspacing="0">
<tr>
  <td align="right">
  	<html:submit><bean:message key="all.next"/></html:submit>
  	<input type="button" value="<bean:message key="all.cancel"/>" onclick="window.parent.close();"/>
  </td>
</tr>
</table>
</html:form>