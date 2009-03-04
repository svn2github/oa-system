<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<table width="100%" height="100%">
<tr>
  <td align="center">
	<table>
	<tr>
	  <td>
        <html:messages id="message" message="true"> 
        	<bean:write name="message"/>
        </html:messages>
      </td>
	</tr>
	<tr>
	  <td align="right">
	    <form method="post">
	    	<input type="hidden" name="confirm" value="true">
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