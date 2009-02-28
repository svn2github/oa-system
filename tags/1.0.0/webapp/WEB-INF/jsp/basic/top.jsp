<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<script type="text/javascript">
<!--
	function changePassword() {
		v = window.showModalDialog('showDialog.do?title=user.changePassword.title&changePassword.do', null, 'dialogWidth:300px;dialogHeight:200px;status:no;help:no;scroll:no');
	}
//-->
</script>
<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
<tr height="40">
  <td align="left" valign="top">
    <c:if test="${X_LOGINED&&X_SITEHASLOGO}">
    <img src="showSiteLogo.do?id=${X_SITEID}" width="121" height="35" border="0"/>
    </c:if>
    <c:if test="${!(X_LOGINED&&X_SITEHASLOGO)}">
    <img src="images/sg_logo.gif" width="121" height="35" border="0"/>
    </c:if>
  </td>
  <td align="right" valign="bottom">
    <table cellpadding="0" cellspacing="2" border="0">
      <tr>
        <c:if test="${X_LOGINED}">
        <td><strong><bean:message key="user"/>: ${sessionScope.LOGIN_USER.name}</strong>&nbsp;</td>
        <td><a href="javascript:changePassword();"><img src="images/key.gif" width="16" height="16" border="0"/></a></td>
        <td>
    	  <c:if test="${sessionScope.LOGIN_USER.locale=='en'}">
          <a href="switchLocale.do" target="mainFrame"><img id="localeIcon" src="images/zh.gif" width="16" height="16" border="0"/></a>
          </c:if>
          <c:if test="${sessionScope.LOGIN_USER.locale!='en'}">
          <a href="switchLocale.do" target="mainFrame"><img id="localeIcon" src="images/en.gif" width="16" height="16" border="0"/></a>
    	  </c:if>
        </td>
        </c:if>
        <td><a href="help/User Manual for Admin.rar"><img src="images/help.gif" width="16" height="16" border="0"/></a></td>
        <c:if test="${X_LOGINED}">
        <td><a href="logout.do"><img src="images/logoff.gif" width="16" height="16" border="0"/></a></td>
        </c:if>
      </tr>
    </table>
  </td>
</tr>
<tr height="15">
  <td colspan="2" bgcolor="#003366"></td>
</tr>
</table>