<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<script language="javascript" src="includes/table.js"></script>
<script type="text/javascript" src="includes/selection.js"></script>
<script type="text/javascript">
<!--
	var siteArray = [];
	var departmentArray = [];

<logic:iterate id="s" name="X_SITELIST">
	siteArray[siteArray.length] = new Option("${s.name}", "${s.id}");
	a = [];
	a[a.length] = new Option('<bean:message key="department.allDepartment"/>', '');
  <logic:notEmpty name="s" property="departments">
    <logic:iterate id="d" name="s" property="departments">
	a[a.length] = new Option("${d.indentName}", "${d.id}");
    </logic:iterate>
  </logic:notEmpty>
	departmentArray[departmentArray.length] = a;
</logic:iterate>

//-->
</script>
<html:form action="/reportUserRole">
<html:hidden property="order"/>
<html:hidden property="descend"/>
<table width="100%" border="0" cellpadding="4" cellspacing="0">
  <tbody>
    <tr>
      <td class="bluetext"><bean:message key="userDepartment.site"/>:</td>
      <td><html:select property="siteId" onchange="cascadeUpdate(this, departmentId, departmentArray);"></html:select></td>
      <td class="bluetext"><bean:message key="user.loginName"/>:</td>
      <td><html:text property="loginName" size="20"/></td>
    </tr>
    <tr>
      <td class="bluetext"><bean:message key="userDepartment.department"/>:</td>
      <td><html:select property="departmentId"></html:select></td>
      <td class="bluetext"><bean:message key="user.name"/>:</td>
      <td><html:text property="name" size="20"/></td>
    </tr>
    <tr>
      <td class="bluetext"><bean:message key="userRole.role"/>:</td>
      <td>
      	<html:select property="roleId">
      		<html:option value=""><bean:message key="all.all"/></html:option>
      		<html:options collection="x_roleList" property="id" labelProperty="name"/>
      	</html:select>
      </td>
      <td>
        <html:select property="enabled">
        <html:option value=""><bean:message key="all.all"/></html:option>
		<c:if test="${sessionScope.LOGIN_USER.locale=='en'}">
			<html:options collection="X_ENABLEDDISABLEDLIST"
				property="enumCode" labelProperty="engShortDescription" />
		</c:if>
		<c:if test="${sessionScope.LOGIN_USER.locale!='en'}">
			<html:options collection="X_ENABLEDDISABLEDLIST"
				property="enumCode" labelProperty="chnShortDescription" />
		</c:if>
        </html:select>
      </td>
      <td></td>
      <td align="center">
        <html:submit><bean:message key="all.query"/></html:submit>
        <input type="button" value="<bean:message key="all.new"/>" onclick="add();"/>
      </td>
    </tr>
  </tbody>
</table>
</html:form>
<script type="text/javascript">
	initCascadeSelect(userQueryForm.siteId, userQueryForm.departmentId, siteArray, departmentArray, "${userQueryForm.siteId}", "${userQueryForm.departmentId}");
</script><hr/>
<page:form action="/reportUserRole" method="post">			
  <jsp:include page="../pageHead.jsp"/>
  <table class="data">
    <thead>
      <tr class="new_bg">
	    <th>
          <page:order order="name" style="text-decoration:none">
    	      <bean:message key="user.name"/>
            <page:desc><img src="images/down.gif" border="0"/></page:desc>
            <page:asc><img src="images/up.gif" border="0"/></page:asc>
          </page:order>
	    </th>	  	    
	    <th>
            <bean:message key="userRole.role"/>
	    </th>
	    <th>	    
            <bean:message key="role.type"/>
	    </th>
	    <th>
            <bean:message key="userDepartment.site"/>
	    </th>
	    <th>
            <bean:message key="userDepartment.department"/>
	    </th>
      </tr>
    </thead>
    <tbody id="datatable">
    <%boolean isOdd=true;%>
      <logic:iterate id="X_OBJECT" name="X_RESULTLIST">
	
	<tr class="<%=isOdd?"odd":"even"%>">
	  <td valign="top" rowspan="${X_OBJECT.userRoleListSize}">${X_OBJECT.name}</td>
	   <td>
		${X_OBJECT.firstUserRole.role.name}
	  </td>
	   <td>
	   <c:if test="${sessionScope.LOGIN_USER.locale=='en'}">					
			${X_OBJECT.firstUserRole.role.type.engDescription}
		</c:if>
		<c:if test="${sessionScope.LOGIN_USER.locale!='en'}">
			${X_OBJECT.firstUserRole.role.type.chnDescription}
		</c:if>		
	  </td>
	  <td>
		${X_OBJECT.firstUserRole.grantedSite.name}
	  </td>
	  <td>
		${X_OBJECT.firstUserRole.grantedDepartment.name}
	  </td>
	 
	</tr>
	
		<logic:iterate name="X_OBJECT" property="remainUserRoles" id="x_userRole">
		<tr class="<%=isOdd?"odd":"even"%>">
		   <td>${x_userRole.role.name}</td>
		  <td>
		  	<c:if test="${sessionScope.LOGIN_USER.locale=='en'}">					
				${x_userRole.role.type.engDescription}
			</c:if>
			<c:if test="${sessionScope.LOGIN_USER.locale!='en'}">
				${x_userRole.role.type.chnDescription}
			</c:if>
		  </td>
		  <td>
			${x_userRole.grantedSite.name}
		  </td>
		  <td>
			${x_userRole.grantedDepartment.name}
		  </td>
	  	  
		</tr>
	</logic:iterate>
		<%isOdd=!isOdd;%>
      </logic:iterate>
    </tbody>
  </table>
<%  request.setAttribute("x_onlypdf",Boolean.TRUE);%>
  <jsp:include page="../pageTail.jsp"/>
</page:form>		      
