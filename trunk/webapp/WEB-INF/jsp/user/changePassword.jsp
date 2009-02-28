<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<script type="text/javascript">
<!--
	function validateForm(form) {
		if (form["oldPwd"].value.length==0) {
			alertRequired('<bean:message key="user.changePassword.oldPassword" />');
			form["oldPwd"].focus();
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
<form method="post" onsubmit="return validateForm(this);">
<table width="100%" border="0" cellpadding="4" cellspacing="0">
<tr>
  <td colspan="2" class="warningMsg"><html:errors/></td>
</tr>
<tr>
  <td class="bluetext"><bean:message key="user.changePassword.oldPassword"/>:</td>
  <td><input type="password" name="oldPwd"/></td>
</tr>
<tr>
  <td class="bluetext"><bean:message key="user.password"/>:</td>
  <td><input type="password" name="newPwd"/></td>
</tr>
<tr>
  <td class="bluetext"><bean:message key="user.passwordAgain"/>:</td>
  <td><input type="password" name="passwordAgain"/></td>
</tr>
</table>
<hr/>
<table width="90%" border="0" cellpadding="4" cellspacing="0">
<tr>
  <td align="right">
  	<input type="submit" value="<bean:message key="all.save"/>"/>
  	<input type="button" value="<bean:message key="all.cancel"/>" onclick="window.parent.close();"/>
  </td>
</tr>
</table>
</form>
