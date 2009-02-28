<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<html:javascript formName="ruleForm" staticJavascript="false" />
<html:form action="insert${X_RULETYPE.prefixUrl}Rule.do" onsubmit="return validateRuleForm(this);">
<html:hidden property="site_id"/>
<table width="100%" border="0" cellpadding="4" cellspacing="0">
<tr>
  <td class="bluetext"><bean:message key="rule.site"/>:</td>
  <td><bean:write name="ruleForm" property="site_name"/></td>
</tr>
<tr>
  <td class="bluetext"><bean:message key="rule.id"/>:</td>
  <td><bean:message key="common.id.generateBySystem"/></td>
</tr>
<tr>
  <td class="bluetext"><bean:message key="rule.description"/>:</td>
  <td><html:text property="description" size="40"/></td>
</tr>
<tr>
  <td class="bluetext"><bean:message key="rule.enabled"/>:</td>
  <td>
    <html:select property="enabled">
      <html:options collection="X_ENABLEDDISABLEDLIST" property="enumCode" labelProperty="${x_lang}ShortDescription"/>
    </html:select>
  </td>
</tr>
</table>
<hr width="100%"/>
<table width="90%" border="0" cellpadding="4" cellspacing="0">
<tr>
  <td align="right">
  	<html:submit><bean:message key="all.save"/></html:submit>
  	<input type="button" value="<bean:message key="all.cancel"/>" onclick="window.parent.close();"/>
  </td>
</tr>
</table>
</html:form>