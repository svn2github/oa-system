<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<script type="text/javascript">
<!--


	function validateForm(form) {
		if (form["loginName"].value.length==0) {
			alertRequired('<bean:message key="user.loginName" />');
			form["loginName"].focus();
			return false;
		}
		return true;
		
	}
	
	function login(){
		window.location.href="login.do";
	}	
	
//-->
</script>

<table width="100%" height="100%" cellpadding="0" cellspacing="0" border="0">
<tr>
  <td align="center" valign="middle">
  <logic:notPresent name="success">
    <form action="forgetPassword.do" method="post" focus="loginName" onsubmit="return validateForm(this)">
    <table>
    <tr>
      <td><strong><bean:message key="user.loginName"/>:</strong></td>
      <td><input  name="loginName" style="width:120px" /></td>
    </tr>
    <tr>
      <td></td>
      <td>
        <html:submit><bean:message key="all.submit"/></html:submit>
      </td>
    </tr>
    </table>
    </form>
  </logic:notPresent>
  <logic:present name="success">  
  	<table>
    <tr>
      <td><bean:message key="user.forgetPassword.send"/></td>
    </tr>
    <tr>
      <td align="center"><input type="button" onClick="login()" value="<bean:message key="all.back"/>"/></td>
    </tr>
    </table>
  </logic:present>
  </td>
</tr>
</table>
