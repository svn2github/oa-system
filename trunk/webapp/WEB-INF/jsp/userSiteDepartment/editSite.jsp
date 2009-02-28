<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<html:javascript formName="userSiteForm" staticJavascript="false" />
<html:form action="updateUserSite.do" onsubmit="return validateUserSiteForm(this);">
<html:hidden property="user_id"/>
<html:hidden property="site_id"/>
<table width="100%" border="0" cellpadding="4" cellspacing="0">
<tr>
  <td class="bluetext"><bean:message key="user.loginName"/>:</td>
  <td><bean:write name="userSiteForm" property="user_loginName"/></td>
</tr>
<tr>
  <td class="bluetext"><bean:message key="userSite.site"/>:</td>
  <td><bean:write name="userSiteForm" property="site_name"/></td>
</tr>
<tr>
  <td class="bluetext"><bean:message key="userSite.travelGroup"/>:</td>
  <td><html:select property="travelGroup_id"><html:options collection="X_TRAVELGROUPLIST" property="id" labelProperty="name"/></html:select></td>
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
