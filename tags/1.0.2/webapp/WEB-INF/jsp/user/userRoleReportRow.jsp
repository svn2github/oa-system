<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<tr >
  <td valign="top" rowspan="${X_OBJECT.userRoleListSize}">${X_OBJECT.name}</td>
  <td>
	${X_OBJECT.firstUserRole.grantedSite.name}
  </td>
  <td>
	${X_OBJECT.firstUserRole.grantedDepartment.name}
  </td>
  <td>
	${X_OBJECT.firstUserRole.role.name}
  </td>
</tr>

	<logic:iterate name="X_OBJECT" property="remainUserRoles" id="x_userRole">	
	<tr>
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
