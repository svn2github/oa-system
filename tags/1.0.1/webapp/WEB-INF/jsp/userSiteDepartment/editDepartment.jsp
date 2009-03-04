<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<html:javascript formName="userDepartmentForm" staticJavascript="false" />
<html:form action="updateUserDepartment.do" onsubmit="return validateUserDepartmentForm(this);">
<html:hidden property="user_id"/>
<html:hidden property="department_id"/>
<table width="100%" border="0" cellpadding="4" cellspacing="0">
<tr>
  <td class="bluetext"><bean:message key="user.loginName"/>:</td>
  <td><bean:write name="userDepartmentForm" property="user_loginName"/></td>
</tr>
<tr>
  <td class="bluetext"><bean:message key="userDepartment.site"/>:</td>
  <td><bean:write name="userDepartmentForm" property="department_site_name"/></td>
</tr>
<tr>
  <td class="bluetext"><bean:message key="userDepartment.department"/>:</td>
  <td><bean:write name="userDepartmentForm" property="department_name"/></td>
</tr>
<tr>
  <td class="bluetext"><bean:message key="userDepartment.title"/>:</td>
  <td><html:text property="title"/></td>
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
