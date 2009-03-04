<%@ page contentType="text/html; charset=utf-8"%>
<%@ page import="java.io.StringWriter"%>
<%@ page import="java.io.PrintWriter"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<div style="margin:10px">
<table border=0 width='90%' cellspacing='0' cellpadding='0'>
<tr>
  <td width='100%' height='20'>
    <strong><bean:message key="error.page.title"/></strong>
  </td>
</tr>
<tr>
  <td width='100%'>
    <table width='100%' border='0' cellpadding='0' cellspacing='0'>
    <tr>
      <td>
        <logic:present name="X_exception">
          <pre>
<%
	StringWriter sw = new StringWriter();
	Exception ex = (Exception)request.getAttribute("X_exception");
	ex.printStackTrace(new PrintWriter(sw));
	out.print(sw.getBuffer().toString());
%>
          </pre>
        </logic:present>
        <logic:notPresent name="X_exception"><span class="warningMsg"><html:errors/></span></logic:notPresent>
      </td>
    </tr>
    <tr height="50">
      <td align="right" valign="bottom">
        <input type="button" value="<bean:message key="all.ok"/>" onclick="window.parent.close();"/>
      </td>
    </tr>
    </table>
  </td>
</tr>
</table>
</div>