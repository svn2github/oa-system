<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%
	String action = request.getQueryString();
	action = action.substring(action.indexOf('&') + 1);
%>
<table width="100%" height="100%">
<tr>
  <td align="center">
	<table>
	<tr>
	  <td>
        <bean:message key="${param.message}"/>
      </td>
	</tr>
	<tr>
	  <td align="right">
	    <form action="<%=action%>" method="post">
	    <div style='margin:5px'>
	      <input type="submit" value="<bean:message key="all.ok"/>"/>
	      <input type="button" value="<bean:message key="all.cancel"/>" onclick="window.parent.close();"/>
	    </div>
	    </form>
	  </td>
	</tr>
	</table>
  </td>
</tr>
</table>