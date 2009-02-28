<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<html:javascript formName="mailForm" staticJavascript="false"/>
<table width=100% border=1 cellpadding=4 cellspacing=0 bgcolor=ffffff>
	<tr>
		<td bgcolor=f0f0f0 width=100% colspan=2 valign=top>    <table width=100% cellpadding=0 cellspacing=0>
	      <tr>
	        <td bgcolor=f0f0f0 width=80% valign=top><h3 class="formtitle"><bean:message key="mailsetup.title"/></h3></td>
	        <td valign=top align ="right"></td>
	      </tr>
	    </table></td>
	</tr>
</table>
<br>
<table width="100%" height="100%" cellpadding="0" cellspacing="0" border="0">
<tr>
  <td width="100%">
    <html:form action="mailSetup.do" method="post" focus="server" onsubmit="return validateMailForm(this);">
    <table  width=90% border=0 cellpadding=4 cellspacing=0>
		<tr>
			<td class="bluetext" >
				<bean:message key="mailsetup.server"/>:
			</td>
			<td>
				<html:text property="server" style="width:120px" maxlength="50"/>
			</td>
		</tr>
		<tr>
			<td class="bluetext">
				<bean:message key="mailsetup.username"/>:
			</td>
			<td>
				<html:text property="username" style="width:120px" maxlength="50"/>
			</td>
		</tr>
		<tr>
			<td class="bluetext">
				<bean:message key="mailsetup.password"/>:
			</td>
			<td>
				<html:password property="password" style="width:120px" />
			</td>
		</tr>		
		<tr>	
			<td colspan="2" align="right">
				<html:submit><bean:message key="all.save"/></html:submit>											
			</td>
		</tr>
    </table>
    </html:form>
  </td>
</tr>
</table>
<hr>