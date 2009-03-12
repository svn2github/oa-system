<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ page import="net.sourceforge.model.metadata.EnabledDisabled"%>
<tr id="r${X_OBJECT.id}">
	<td width="100%">
	<div id="c${X_OBJECT.id}" site_id="${X_OBJECT.site.id}" style="cursor:hand;width:100%;font-size:12px;color:black"
		onclick='selectCity("${X_OBJECT.id}"<logic:notEmpty name="X_OBJECT" property="site">,"${X_OBJECT.site}"</logic:notEmpty>);'>
		<span
			<logic:notEmpty name="X_OBJECT" property="site">
				style="color:gray"
			</logic:notEmpty>
		
		>
		<c:if test="${sessionScope.LOGIN_USER.locale=='en'}">
		${X_OBJECT.engName}
		</c:if>
		<c:if test="${sessionScope.LOGIN_USER.locale!='en'}">
		${X_OBJECT.chnName}
		</c:if>
		<logic:notEmpty name="X_OBJECT" property="site">
		(${X_OBJECT.site.name})
		</logic:notEmpty>	
	<%request.setAttribute("x_disabled",EnabledDisabled.DISABLED);%>		
		<c:if test="${X_OBJECT.enabled.enumCode==x_disabled.enumCode}">
			<span style="color:${x_disabled.color}">
				(<bean:write name="x_disabled" property="${x_lang}ShortDescription"/>)
			</span>
		</c:if>
		
		
		</span>
		
	</div>
	</td>
</tr>

