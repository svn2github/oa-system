<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<html:javascript formName="roleForm" staticJavascript="false" />
<html:form action="insertRole.do" onsubmit="return validateRoleForm(this);">
<table width="100%" border="0" cellpadding="4" cellspacing="0">
<tr>
  <td class="bluetext"><bean:message key="role.id"/>:</td>
  <td><bean:message key="common.id.generateBySystem"/></td>
</tr>
<tr>
  <td class="bluetext"><bean:message key="role.name"/>:</td>
  <td><html:text property="name"/><span class="required">*</span></td>
</tr>
<tr>
  <td class="bluetext"><bean:message key="role.description"/>:</td>
  <td><html:text property="description"/></td>
</tr>
<tr>
  <td class="bluetext"><bean:message key="role.type"/>:</td>
  <td>
    <html:select property="type">
      <c:if test="${sessionScope.LOGIN_USER.locale=='en'}"><html:options collection="X_ROLETYPELIST" property="enumCode" labelProperty="engShortDescription"/></c:if>
      <c:if test="${sessionScope.LOGIN_USER.locale!='en'}"><html:options collection="X_ROLETYPELIST" property="enumCode" labelProperty="chnShortDescription"/></c:if>
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
