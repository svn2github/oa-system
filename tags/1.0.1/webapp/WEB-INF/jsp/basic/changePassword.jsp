<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<script type="text/javascript">
<!--
	function alertRequired(s) {
		alert(s+"<bean:message key="all.error.required" />");
	}

	function validateForm(form) {
		if (form["password"].value.length==0) {
			alertRequired('<bean:message key="user.changePassword.oldPassword" />');
			form["password"].focus();
			return false;
		}
		if (form["newPwd"].value.length==0) {
			alertRequired('<bean:message key="user.changePassword.newPassword" />');
			form["newPwd"].focus();
			return false;
		}
		if (form["passwordAgain"].value.length==0) {
			alertRequired('<bean:message key="user.passwordAgain" />');
			form["passwordAgain"].focus();
			return false;
		}
		if (form["newPwd"].value!=form["passwordAgain"].value) {
			alert("<bean:message key="user.password.notMatch" />");
			form["passwordAgain"].focus();
			return false;
		}
		return checkPassword(form);
		
	}
	
	
	function checkPassword(form) {
		if (form["newPwd"].value.length<${x_minPwdLen}) {
			alert('<bean:message key="user.password.minLength" arg0="${x_minPwdLen}"/>');
			form["newPwd"].focus();
			return false;
		}
		var foundLetter=false;
		var foundDigit=false;
		for (var index=0;index<form["newPwd"].value.length;index++) {
			var c=form["newPwd"].value.charAt(index);
			if (c>='0' && c<='9') 
				foundDigit=true;
			if ((c>='a' && c<='z') || (c>='A' && c<='Z'))
				foundLetter=true;	
		}
		if (!(foundLetter && foundDigit)) {
			alert('<bean:message key="user.password.notLetterOrDigit" />');
			form["newPwd"].focus();
			return false;
		}
		
	}
//-->
</script>
<table width="100%" height="100%" cellpadding="0" cellspacing="0" border="0">
<tr>
  <td align="center" valign="middle">
    <html:form action="changePasswordFirstTime.do" method="post" focus="password" onsubmit="return validateForm(this);">
    <input type="hidden" name="loginName" value="${x_changePasswordUser.loginName}"/>
    <table>
    <tr>
      <td colspan="2" class="warningMsg"><html:errors/></td>
    </tr>
    <tr>
      <td colspan="2">
      	<span style="color:green;font-weight:bold">
				<html:messages id="x_message" message="true">
					${x_message}
				</html:messages>
		</span>
	  </td>
    </tr>
    <tr>
	  <td class="bluetext"><bean:message key="user.changePassword.oldPassword"/>:</td>
	  <td><input type="password" name="password"/></td>
	</tr>
	<tr>
	  <td class="bluetext"><bean:message key="user.changePassword.newPassword"/>:</td>
	  <td><input type="password" name="newPwd"/></td>
	</tr>
	<tr>
	  <td class="bluetext"><bean:message key="user.passwordAgain"/>:</td>
	  <td><input type="password" name="passwordAgain"/></td>
	</tr>

    <tr>
      <td colspan="2" align="right">
        <html:submit><bean:message key="all.save"/></html:submit>
        <html:reset><bean:message key="all.reset"/></html:reset>
      </td>
    </tr>
    </table>
    </html:form>
  </td>
</tr>
</table>
