<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<script type="text/javascript" src="includes/selection.js"></script>
<script type="text/javascript">
<!--
	var siteArray = [];
	var departmentArray = [];
	
<logic:iterate id="s" name="X_SITELIST">
	siteArray[siteArray.length] = new Option("${s.name}", "${s.id}");
	a = [];
  <logic:notEmpty name="s" property="departments">
    <logic:iterate id="d" name="s" property="departments">
	a[a.length] = new Option("${d.indentName}", "${d.id}");
    </logic:iterate>
  </logic:notEmpty>
	departmentArray[departmentArray.length] = a;
</logic:iterate>
//-->

	function validateForm(form) {
		if (form.department_id.options.length == 0) {
			alert("<bean:message key="userDepartment.departmentNotMaintained"/>");
			form.department_site_id.focus();
			return false;
		}
		if (!validateUserDepartmentForm(form)) return false;
	}
</script>
<html:javascript formName="userDepartmentForm" staticJavascript="false" />
<html:form action="insertUserDepartment.do" onsubmit="return validateForm(this);">
<html:hidden property="user_id"/>
<html:hidden property="user_loginName"/>
<table width="100%" border="0" cellpadding="4" cellspacing="0">
<tr>
  <td colspan="2" class="warningMsg"><html:errors/></td>
</tr>
<tr>
  <td class="bluetext"><bean:message key="user.loginName"/>:</td>
  <td><bean:write name="userDepartmentForm" property="user_loginName"/></td>
</tr>
<tr>
  <td class="bluetext"><bean:message key="userDepartment.site"/>:</td>
  <td><html:select property="department_site_id" onchange="cascadeUpdate(this, department_id, departmentArray);"></html:select></td>
</tr>
<tr>
  <td class="bluetext"><bean:message key="userDepartment.department"/>:</td>
  <td><html:select property="department_id"></html:select></td>
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
<script type="text/javascript">
	initCascadeSelect(userDepartmentForm.department_site_id, userDepartmentForm.department_id, siteArray, departmentArray, '<bean:write name="userDepartmentForm" property="department_site_id"/>', '<bean:write name="userDepartmentForm" property="department_id"/>');
</script>