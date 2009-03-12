<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<script type="text/javascript" src="includes/table.js"></script>
<script type="text/javascript">
<!--
	function select(id, name) {
		var result = [];
		result['id'] = id;
		result['name'] = name;
		result['prefixColor'] = '<%=net.sourceforge.model.metadata.RechargeType.PERSON.getColor()%>';
		<c:if test="${sessionScope.LOGIN_USER.locale=='en'}">
		result['prefix'] = '<%=net.sourceforge.model.metadata.RechargeType.PERSON.getEngShortDescription()%>';
		</c:if>
		<c:if test="${sessionScope.LOGIN_USER.locale!='en'}">
		result['prefix'] = '<%=net.sourceforge.model.metadata.RechargeType.PERSON.getChnShortDescription()%>';
		</c:if>
		window.parent.returnValue = result;
		window.parent.close();
	}
	
//-->
</script>
<html:form action="${X_ACTION}">
<html:hidden property="order"/>
<html:hidden property="descend"/>
<table width="100%" border="0" cellpadding="4" cellspacing="0">
  <tbody>
    <tr>
      <td class="bluetext"><bean:message key="user.loginName"/>:</td>
      <td><html:text property="loginName" size="20"/></td>
      <td class="bluetext"><bean:message key="user.name"/>:</td>
      <td><html:text property="name" size="20"/></td>
    </tr>
    <tr>
      <td class="bluetext"><bean:message key="userDepartment.department"/>:</td>
      <td>
        <c:choose>
          <c:when test="${x_department!=null}">
            ${x_department.name}
            <html:hidden property="departmentId"/>
          </c:when>
          <c:otherwise>
            <html:select property="departmentId">
              <html:option value=""/>
              <html:options collection="X_DEPARTMENTLIST" property="id" labelProperty="indentName"/>
            </html:select>
			<html:hidden property="siteId"/>
          </c:otherwise>
        </c:choose>
      </td>
      <td></td>
      <td align="center">
        <html:submit><bean:message key="all.query"/></html:submit>
      </td>
    </tr>
  </tbody>
</table>
</html:form>
<hr/>
<page:form action="${X_ACTION}" method="post">			
  <jsp:include page="../pageHead.jsp"/>
  <table class="data">
    <thead>
      <tr class="new_bg">
        <th width="20%">
          <page:order order="loginName" style="text-decoration:none">
            <bean:message key="user.loginName"/>
            <page:desc><img src="images/down.gif" border="0"/></page:desc>
            <page:asc><img src="images/up.gif" border="0"/></page:asc>
          </page:order>
        </th>
	    <th width="20%">
          <page:order order="name" style="text-decoration:none">
    	      <bean:message key="user.name"/>
            <page:desc><img src="images/down.gif" border="0"/></page:desc>
            <page:asc><img src="images/up.gif" border="0"/></page:asc>
          </page:order>
	    </th>
	    <th width="25%">
          <page:order order="email" style="text-decoration:none">
            <bean:message key="user.email"/>
            <page:desc><img src="images/down.gif" border="0"/></page:desc>
            <page:asc><img src="images/up.gif" border="0"/></page:asc>
          </page:order>
	    </th>
	    <th width="25%">
          <page:order order="telephone" style="text-decoration:none">
            <bean:message key="user.telephone"/>
            <page:desc><img src="images/down.gif" border="0"/></page:desc>
            <page:asc><img src="images/up.gif" border="0"/></page:asc>
          </page:order>
	    </th>
        <th width="10%">
        </th>
      </tr>
    </thead>
    <tbody id="datatable">
      <logic:iterate id="X_OBJECT" name="X_RESULTLIST">
        <bean:define id="X_OBJECT" toScope="request" name="X_OBJECT"/>
        <jsp:include page="personRow.jsp"/>
      </logic:iterate>
    </tbody>
  </table>
  <script type="text/javascript">
    applyRowStyle(document.all('datatable'));
  </script>
</page:form>		      
