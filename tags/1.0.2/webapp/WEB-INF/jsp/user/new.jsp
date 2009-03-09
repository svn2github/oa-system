<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<script type="text/javascript">
<!--
	function check() {
		var loginName = userForm.loginName.value;
		if (loginName != "") {
			var checkForm = document.frames("frameCheckLoginName").document.forms[0];
			checkForm.loginName.value = loginName;
			checkForm.submit();
		}
	}
	
	function validateForm(form) {
		if (!validateUserForm(form)) return false;
		if (form.password.value == "") {
			alert("<bean:message key="user.password"/> <bean:message key="all.error.required"/>");
			form.password.focus();
			return false;
		}
		if (form.password.value != form.passwordAgain.value) {
			alert("<bean:message key="user.password.notMatch"/>");
			form.password.focus();
			return false;
		}
	}
//-->
</script>
<html:javascript formName="userForm" staticJavascript="false" />
<html:form action="insertUser.do" onsubmit="return validateForm(this);">
<table width="100%">
	<tr>
		<td >
			<html:errors/>
		</td>
	</tr>
	<tr>
		<td>
			<div class="message">
				<html:messages id="x_message" message="true">
					${x_message}<br>
				</html:messages>
			</div>
		</td>
	</tr>
</table>
<table width="100%" border="0" cellpadding="4" cellspacing="0">
<tr>
  <td class="bluetext"><bean:message key="user.loginName"/>:</td>
  <td>
    <table  border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td><html:text property="loginName" size="8"/><span class="required">*</span></td>
        <td>&nbsp;<input type="button" value="<bean:message key="user.loginName.check"/>" onclick="check();"/></td>
        <td><iframe name="frameCheckLoginName" src="checkLoginName.do" frameborder="0" height="24" width="120" scrolling="no"></iframe></td>
      </tr>
    </table>
  </td>
</tr>
<tr>
  <td class="bluetext"><bean:message key="user.password"/>:</td>
  <td><html:password property="password"/><span class="required">*</span></td>
</tr>
<tr>
  <td class="bluetext"><bean:message key="user.passwordAgain"/>:</td>
  <td><input type="password" name="passwordAgain"/><span class="required">*</span></td>
</tr>
<tr>
  <td class="bluetext"><bean:message key="user.name"/>:</td>
  <td><html:text property="name"/><span class="required">*</span></td>
</tr>
<tr>
  <td class="bluetext"><bean:message key="user.gender"/>:</td>
  <td>
    <html:select property="gender">
      <c:if test="${sessionScope.LOGIN_USER.locale!='en'}"><html:options collection="X_GENDERLIST" property="enumCode" labelProperty="chnShortDescription"/></c:if>
      <c:if test="${sessionScope.LOGIN_USER.locale=='en'}"><html:options collection="X_GENDERLIST" property="enumCode" labelProperty="engShortDescription"/></c:if>
    </html:select>
  </td>
</tr>
<tr>
  <td class="bluetext"><bean:message key="user.email"/>:</td>
  <td><html:text property="email"/><span class="required">*</span></td>
</tr>
<tr>
  <td class="bluetext"><bean:message key="user.telephone"/>:</td>
  <td><html:text property="telephone"/></td>
</tr>
<tr>
  <td class="bluetext"><bean:message key="user.primarySite"/>:</td>
  <td><html:select property="primarySite_id"><html:options collection="X_SITELIST" property="id" labelProperty="name"/></html:select></td>
</tr>
<tr>
  <td class="bluetext"><bean:message key="user.enabled"/>:</td>
  <td>
    <html:select property="enabled">
      <c:if test="${sessionScope.LOGIN_USER.locale=='en'}"><html:options collection="X_ENABLEDDISABLEDLIST" property="enumCode" labelProperty="engShortDescription"/></c:if>
      <c:if test="${sessionScope.LOGIN_USER.locale!='en'}"><html:options collection="X_ENABLEDDISABLEDLIST" property="enumCode" labelProperty="chnShortDescription"/></c:if>
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
