<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<table width="100%" border="0" cellpadding="4" cellspacing="0">
  <tr height="40">
    <td align="center">
      <c:if test="${sessionScope.LOGIN_USER.locale=='en'}"><bean:message key="user.switchLocale.toEnglish"/></c:if>
      <c:if test="${sessionScope.LOGIN_USER.locale!='en'}"><bean:message key="user.switchLocale.toOther"/></c:if>
    </td>
  </tr>
</table>
<script type="text/javascript">
<!--
	var f = top.frames('topFrame');
	if (f != null) {
		var img = f.document.getElementById('localeIcon');
		if (img != null) {
			<c:if test="${sessionScope.LOGIN_USER.locale=='en'}">
        	img.src = 'images/zh.gif';
			</c:if>
			<c:if test="${sessionScope.LOGIN_USER.locale!='en'}">
        	img.src = 'images/en.gif';
			</c:if>
		}
	}
	f = top.frames('leftFrame');
	if (f != null) {
		f.location.reload(true);
	}
//-->
</script>
