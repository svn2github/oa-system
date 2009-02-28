<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<html:javascript formName="ruleForm" staticJavascript="false" />
<html:form action="update${X_RULETYPE.prefixUrl}Rule.do" onsubmit="return validateRuleForm(this);">
<html:hidden property="id"/>
<table width="100%" border="0" cellpadding="4" cellspacing="0">
<tr>
  <td class="bluetext"><bean:message key="rule.site"/>:</td>
  <td><bean:write name="ruleForm" property="site_name"/></td>
</tr>
<tr>
  <td class="bluetext"><bean:message key="rule.id"/>:</td>
  <td><bean:write name="ruleForm" property="id"/></td>
</tr>
<tr>
  <td class="bluetext"><bean:message key="rule.description"/>:</td>
  <td><html:text property="description" size="40"/></td>
</tr>
<tr>
  <td class="bluetext"><bean:message key="rule.enabled"/>:</td>
  <td>
    <c:if test="${X_RULEINUSE}">
    <html:hidden property="enabled"/>
      <bean:write name="X_ENABLED" property="${x_lang}ShortDescription"/>
    </c:if>
    <c:if test="${!X_RULEINUSE}">
    <html:select property="enabled">
      <html:options collection="X_ENABLEDDISABLEDLIST" property="enumCode" labelProperty="${x_lang}ShortDescription"/>
    </html:select>
    </c:if>
  </td>
</tr>
</table>
<table width="90%" border="0" cellpadding="4" cellspacing="0">
<tr>
  <td align="right">
  	<html:submit><bean:message key="all.save"/></html:submit>
  	<input type="button" value="<bean:message key="all.cancel"/>" onclick="window.parent.close();"/>
  </td>
</tr>
</table>
</html:form>
<hr/>
<iframe src="list${X_RULETYPE.prefixUrl}RuleCondition.do?id=<bean:write name="ruleForm" property="id"/>" frameborder="0" height="400" width="100%" scrolling="auto"></iframe>