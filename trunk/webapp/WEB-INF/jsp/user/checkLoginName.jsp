<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<table height="100%">
  <tr>
    <td class="bluetext">
      <logic:present name="X_USEREXISTED">
        <logic:equal name="X_USEREXISTED" value="true"><span class="warningMsg"><bean:message key="user.loginName.existed" arg0="${X_LOGINNAME}"/></span></logic:equal>
        <logic:notEqual name="X_USEREXISTED" value="true"><bean:message key="user.loginName.availiable" arg0="${X_LOGINNAME}"/></logic:notEqual>
      </logic:present>
    </td>
  </tr>
</table>
<form method="post">
<input type="hidden" name="loginName"/>
</form>
