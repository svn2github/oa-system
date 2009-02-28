<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>
<script language="javascript" src="includes/table.js"></script>

<html:form action="listSystemLog${x_version}">
<html:hidden property="order"/>
<html:hidden property="descend"/>
<table width="100%" border="0" cellpadding="4" cellspacing="0">
  <tbody>
    <tr>
		<td class="bluetext"><bean:message key="systemLog.search.site"/>:</td>
		<td align="left"><html:select property="siteId">
			<logic:empty name="x_version">
				<option value=""><bean:message key="site.allSite" /></option>
			</logic:empty>
			<html:options collection="X_SITELIST" property="id"
				labelProperty="name" />
		</html:select></td>
		<td></td><td></td>
	</tr>
	<tr>
		<td class="bluetext"><bean:message key="systemLog.search.userId" />:</td>
		<td><html:text property="userId"/></td>
		<td class="bluetext"><bean:message key="systemLog.search.userName" />:</td>
		<td><html:text property="userName" /></td>
		<td></td>
	</tr>
	<tr>
		<td class="bluetext"><bean:message key="systemLog.search.object" />:</td>
		<td align="left">
			<html:select property="targetObject">
			<html:option value="">All</html:option>
			<html:option value="Yearly Budget">Yearly Budget</html:option>
			<html:option value="Capex">Capex</html:option>
			<html:option value="PO">Purchase Order</html:option>
			<html:option value="PR">Purchase Reqeust</html:option>
			<html:option value="Expense">Expense</html:option>
			<html:option value="TA">Travel Application</html:option>
			</html:select>
		</td>
		<td class="bluetext"><bean:message key="systemLog.search.date" />:</td>
		<td>
		<table border=0 cellpadding=0 cellspacing=0>
	  		<tr>
	  		<td>
				<table border=0 cellpadding=0 cellspacing=0>
				<tr>
					<td>
						<html:text property="actionDateFrom" size="6"  maxlength="10" />
					</td>
					<td>
						<a onclick="event.cancelBubble=true;" href="javascript:showCalendar('dimg1',false,'actionDateFrom',null,null,'systemLogQueryForm')"><IMG align="absMiddle" border="0" id="dimg1" src="images/datebtn.gif" ></A>
					</td>
				</tr>
				</table>
	  		</td>
	  		<td width="20" align="center">~</td>
	  		<td>
				<table border=0 cellpadding=0 cellspacing=0>
				<tr>
					<td>
						<html:text property="actionDateTo" size="6"  maxlength="10" />
					</td>
					<td>
						<a onclick="event.cancelBubble=true;" href="javascript:showCalendar('dimg2',false,'actionDateTo',null,null,'systemLogQueryForm')"><IMG align="absMiddle" border="0" id="dimg2" src="images/datebtn.gif" ></A>
					</td>
				</tr>
				</table>
	  		</td>
	  		</tr>
  		</table>
		</td>
		<td>
		<html:submit><bean:message key="all.query"/></html:submit>
		</td>
	</tr>
  </tbody>
</table>
</html:form>
<hr/>
<page:form action="listSystemLog${x_version}.do" method="post">			
  <jsp:include page="../pageHead.jsp"/>
  <table class="data">
    <thead>
      <tr bgcolor="#9999ff">
        <th>
          <page:order order="site_name" style="text-decoration:none">
            <bean:message key="systemLog.tablehead.site"/>
            <page:desc><img src="images/down.gif" border="0"/></page:desc>
            <page:asc><img src="images/up.gif" border="0"/></page:asc>
          </page:order>
        </th>
        <th>
          <page:order order="user_id" style="text-decoration:none">
            <bean:message key="systemLog.tablehead.userId"/>
            <page:desc><img src="images/down.gif" border="0"/></page:desc>
            <page:asc><img src="images/up.gif" border="0"/></page:asc>
          </page:order>
        </th>
	    <th>
   	      <bean:message key="systemLog.tablehead.userName"/>
	    </th>
	    <th>
   	      <bean:message key="systemLog.tablehead.updateTime"/>
	    </th>
	    <th>
   	      <bean:message key="systemLog.tablehead.object"/>
	    </th>
	    <th>
   	      <bean:message key="systemLog.tablehead.objectId"/>
	    </th>
	    <th>
   	      <bean:message key="systemLog.tablehead.action"/>
	    </th>
	    <th>
   	      <bean:message key="systemLog.tablehead.keyField"/>
	    </th>	    
      </tr>
    </thead>
    
    <tbody id="datatable">
	<logic:present name="X_RESULTLIST">
      <logic:iterate id="X_OBJECT" name="X_RESULTLIST">
        <bean:define id="X_OBJECT" toScope="request" name="X_OBJECT"/>
        <jsp:include page="row.jsp"/>
      </logic:iterate>
    </logic:present>  
    </tbody>
  </table>
  <script type="text/javascript">
    applyRowStyle(document.all('datatable'));
  </script>
  <logic:present name="X_RESULTLIST">
	  <jsp:include page="../pageTail.jsp"/>
  </logic:present>  
</page:form>		      
