<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<html:javascript formName="loginForm" staticJavascript="false"/>
<table width="100%" height="100%" cellpadding="0" cellspacing="0" border="0" class="new_table_bg">
<tr>
  <td align="center" valign="middle">
    <html:form action="login.do" method="post" focus="loginName" onsubmit="return validateLoginForm(this);">
    <table>
    <tr>
      <td colspan="2" class="warningMsg"><html:errors/></td>
    </tr>
    <tr>
      <td><strong><bean:message key="user.loginName"/>:</strong></td>
      <td><html:text property="loginName" style="width:120px" /></td>
    </tr>
    <tr>
      <td><strong><bean:message key="user.password"/>:</strong></td>
      <td><html:password property="password" style="width:120px" /></td>
    </tr>
    <tr>
      <td><strong><bean:message key="user.locale"/>:</strong></td>
      <td><html:select property="locale"><html:option value=""><bean:message key="user.locale.useDefault"/></html:option><html:option value="en"><bean:message key="user.locale.useEnglish"/></html:option><html:option value="zh"><bean:message key="user.locale.useChinese"/></html:option></html:select></td>
    </tr>
    <tr>
      <td></td>
      <td align="left">
        <html:submit><bean:message key="all.login"/></html:submit>
        <html:reset><bean:message key="all.reset"/></html:reset>
      </td>
    </tr>
    <tr>
      <td></td>
      <td align="left">
        <a href="forgetPassword.do" ><bean:message key="user.forgetPassword"/></a>
      </td>
    </tr>
    </table>
    </html:form>
  </td>
</tr>
</table>
