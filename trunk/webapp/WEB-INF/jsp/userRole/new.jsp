<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ page import="net.sourceforge.model.metadata.RoleType" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<script type="text/javascript" src="includes/selection.js"></script>
<script type="text/javascript">
<!--
	var roleTypeArray = [];
	var siteArray = [];
	var departmentArray = [];

<logic:iterate id="r" name="X_ROLELIST">
	roleTypeArray[roleTypeArray.length] = ${r.type.enumCode};
</logic:iterate>
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

	var oldRoleType = -1;
	function roleChanged(index) {
		var newRoleType = roleTypeArray[index];
		if (newRoleType == oldRoleType) return;
		oldRoleType = newRoleType;
		switch (newRoleType) {
			case <%=RoleType.GLOBAL_ADMIN.getEnumCode()%>: 
			case <%=RoleType.COMMON_GLOBAL_LEVEL.getEnumCode()%>: {
				document.getElementById("grantedSiteNoNeed").style.display = "";
				userRoleForm.grantedSite_id.style.display = "none";
				document.getElementById("grantedDepartmentNoNeed").style.display = "";
				userRoleForm.grantedDepartment_id.style.display = "none";
				break;
			}
			case <%=RoleType.SITE_ADMIN.getEnumCode()%>: {
				document.getElementById("grantedSiteNoNeed").style.display = "none";
				userRoleForm.grantedSite_id.style.display = "";
				document.getElementById("grantedDepartmentNoNeed").style.display = "";
				userRoleForm.grantedDepartment_id.style.display = "none";
				break;			
			}
			case <%=RoleType.ENDUSER.getEnumCode()%>: {
				document.getElementById("grantedSiteNoNeed").style.display = "none";
				userRoleForm.grantedSite_id.style.display = "";
				document.getElementById("grantedDepartmentNoNeed").style.display = "none";
				userRoleForm.grantedDepartment_id.style.display = "";
				break;			
			}
		}
	}
//-->
</script>
<html:javascript formName="userRoleForm" staticJavascript="false" />
<html:form action="insertUserRole.do" onsubmit="return validateUserRoleForm(this);">
<html:hidden property="user_id"/>
<html:hidden property="user_loginName"/>
<table border="0" cellpadding="4" cellspacing="0">
<tr>
  <td colspan="2" class="warningMsg"><html:errors/></td>
</tr>
<tr>
  <td class="bluetext"><bean:message key="user.loginName"/>:</td>
  <td><bean:write name="userRoleForm" property="user_loginName"/></td>
</tr>
<tr>
  <td class="bluetext"><bean:message key="userRole.role"/>:</td>
  <td><html:select property="role_id" onchange="roleChanged(this.selectedIndex);"><html:options collection="X_ROLELIST" property="id" labelProperty="name"/></html:select></td>
</tr>
<tr>
  <td class="bluetext"><bean:message key="userRole.grantedSite"/>:</td>
  <td>
    <div id="grantedSiteNoNeed"><bean:message key="userRole.grantedSite.noNeed"/></div>
    <html:select property="grantedSite_id" onchange="cascadeUpdate(this, grantedDepartment_id, departmentArray);"></html:select>
  </td>
</tr>
<tr>
  <td class="bluetext"><bean:message key="userRole.grantedDepartment"/>:</td>
  <td>
    <div id="grantedDepartmentNoNeed"><bean:message key="userRole.grantedDepartment.noNeed"/></div>
    <html:select property="grantedDepartment_id"></html:select>
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
<script type="text/javascript">
    initCascadeSelect(userRoleForm.grantedSite_id, userRoleForm.grantedDepartment_id, siteArray, departmentArray, '<bean:write name="userRoleForm" property="grantedSite_id"/>', '<bean:write name="userRoleForm" property="grantedDepartment_id"/>');		
	roleChanged(userRoleForm.role_id.selectedIndex);
</script>