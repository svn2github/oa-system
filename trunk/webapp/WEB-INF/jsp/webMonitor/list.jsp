<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>
<script language="javascript" src="includes/table.js"></script>
<html:form action="${x_action}">
<html:hidden property="order"/>
<html:hidden property="descend"/>
<table width="100%" border="0" cellpadding="4" cellspacing="0">
  <tbody>
    <tr>
      <td class="bluetext"><bean:message key="webMonitor.site"/>:</td>
	  <td>
	    <html:select property="siteId">
          <c:if test="${X_GLOBAL}"><html:option value=""><bean:message key="site.allSite"/></html:option></c:if>
	      <html:options collection = "X_SITELIST" property="id" labelProperty="name"/>
	    </html:select>
	  </td>
      <td align="center">
        <html:submit><bean:message key="all.query"/></html:submit>
      </td>
    </tr>
  </tbody>
</table>
</html:form>
<hr/>
<page:form action="${x_action}" method="post">			
  <jsp:include page="../pageHead.jsp"/>
  <table class="data">
    <thead>
      <tr class="new_bg">
      	<th>
      		<bean:message key="all.seq"/>
      	</th>
      	<c:if test="${X_GLOBAL}">
        <th>
          <page:order order="site" style="text-decoration:none">
            <bean:message key="webMonitor.site"/>
            <page:desc><img src="images/down.gif" border="0"/></page:desc>
            <page:asc><img src="images/up.gif" border="0"/></page:asc>
          </page:order>
        </th>
        </c:if>
        <th>
          <page:order order="user_id" style="text-decoration:none">
            <bean:message key="webMonitor.userId"/>
            <page:desc><img src="images/down.gif" border="0"/></page:desc>
            <page:asc><img src="images/up.gif" border="0"/></page:asc>
          </page:order>
        </th>
        
	    <th>
		   	<page:order order="user_name" style="text-decoration:none">
	            <bean:message key="webMonitor.userName"/>
	            <page:desc><img src="images/down.gif" border="0"/></page:desc>
	            <page:asc><img src="images/up.gif" border="0"/></page:asc>
		    </page:order>
	    </th>
	    <th>
	    	<page:order order="ip" style="text-decoration:none">
	            <bean:message key="webMonitor.ip"/>
	            <page:desc><img src="images/down.gif" border="0"/></page:desc>
	            <page:asc><img src="images/up.gif" border="0"/></page:asc>
		    </page:order>
	    </th>
	    <th>
			<page:order order="login_time" style="text-decoration:none">
	            <bean:message key="webMonitor.loginTime"/>
	            <page:desc><img src="images/down.gif" border="0"/></page:desc>
	            <page:asc><img src="images/up.gif" border="0"/></page:asc>
			</page:order>
	    </th>
	    <th>
   	    	<page:order order="access_time" style="text-decoration:none">
	            <bean:message key="webMonitor.lastAccessTime"/>
	            <page:desc><img src="images/down.gif" border="0"/></page:desc>
	            <page:asc><img src="images/up.gif" border="0"/></page:asc>
			</page:order>
	    </th>
	    <th>
   	      	<page:order order="live_time" style="text-decoration:none">
	            <bean:message key="webMonitor.timeToLive"/>
	            <page:desc><img src="images/down.gif" border="0"/></page:desc>
	            <page:asc><img src="images/up.gif" border="0"/></page:asc>
			</page:order>
	    </th>
      </tr>
    </thead>
    
    <tbody id="datatable">
      <logic:iterate id="X_OBJECT" name="X_RESULTLIST">
        <bean:define id="X_OBJECT" toScope="request" name="X_OBJECT"/>
        <jsp:include page="row.jsp"/>
      </logic:iterate>
    </tbody>
  </table>
  
  <script type="text/javascript">
    applyRowStyle(document.all('datatable'));
  </script>
  
  <jsp:include page="../pageTail.jsp"/>
</page:form>		      
