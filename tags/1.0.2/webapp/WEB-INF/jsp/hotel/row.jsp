<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<tr id="r${X_OBJECT.id}">
	<td>
		<a href="javascript:edit(${X_OBJECT.id})"> ${X_OBJECT.name}</a>
	</td>

	<td>
	<logic:empty property="site" name="X_OBJECT">
		<bean:message key="hotel.list.sitevalue.global"/>
	</logic:empty>
	<logic:notEmpty property="site" name="X_OBJECT">
		${X_OBJECT.site.name}
	</logic:notEmpty>
	</td>
	
	<td><c:if test="${sessionScope.LOGIN_USER.locale=='en'}">
	 ${X_OBJECT.city.province.country.engName}
	 </c:if> <c:if test="${sessionScope.LOGIN_USER.locale!='en'}">
	 ${X_OBJECT.city.province.country.chnName}
	 </c:if></td>

	<td><c:if test="${sessionScope.LOGIN_USER.locale=='en'}">
	 ${X_OBJECT.city.province.engName}
	 </c:if> <c:if test="${sessionScope.LOGIN_USER.locale!='en'}">
	 ${X_OBJECT.city.province.chnName}
	 </c:if></td>

	<td><c:if test="${sessionScope.LOGIN_USER.locale=='en'}">
	 ${X_OBJECT.city.engName}
	 </c:if> <c:if test="${sessionScope.LOGIN_USER.locale!='en'}">
	 ${X_OBJECT.city.chnName}
	 </c:if></td>




	<td>${X_OBJECT.telephone}</td>

	<td><c:if test="${sessionScope.LOGIN_USER.locale=='en'}">
	 ${X_OBJECT.level.engShortDescription}
	 </c:if> <c:if test="${sessionScope.LOGIN_USER.locale!='en'}">
	 ${X_OBJECT.level.chnShortDescription}
	 </c:if></td>


	<td><span style="color:${X_OBJECT.promoteStatus.color}"> <c:if
		test="${sessionScope.LOGIN_USER.locale=='en'}">
	 ${X_OBJECT.promoteStatus.engShortDescription}
	 </c:if> <c:if test="${sessionScope.LOGIN_USER.locale!='en'}">
	 ${X_OBJECT.promoteStatus.chnShortDescription}
	 </c:if>
	</td>

	<td><span style="color:${X_OBJECT.enabled.color}"> <c:if
		test="${sessionScope.LOGIN_USER.locale=='en'}">
	 ${X_OBJECT.enabled.engShortDescription}
	 </c:if> <c:if test="${sessionScope.LOGIN_USER.locale!='en'}">
	 ${X_OBJECT.enabled.chnShortDescription}
	 </c:if> </span></td>
</tr>
