<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<html:javascript formName="functionForm" staticJavascript="false" />
<html:form action="updateFunction.do" onsubmit="return validateFunctionForm(this);">
<html:hidden property="id"/>
<table width="100%" border="0" cellpadding="4" cellspacing="0">
<tr>
  <td class="bluetext"><bean:message key="function.id"/>:</td>
  <td><bean:write name="functionForm" property="id"/></td>
</tr>
<tr>
  <td class="bluetext"><bean:message key="function.name"/>:</td>
  <td><html:text property="name"/><font color="red">*</font></td>
</tr>
<tr>
  <td class="bluetext"><bean:message key="function.description"/>:</td>
  <td><html:text property="description"/></td>
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
