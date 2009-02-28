<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>



<tr id="r${X_OBJECT.id}">

	<td><a href='javascript:assign("${X_OBJECT.id}")'>${X_OBJECT.id}</a></td>



	

	<td>
	<div align="left">${X_OBJECT.requestor.name}</div>
	</td>
	
	<td>
	<div align="left">${X_OBJECT.creator.name}</div>
	</td>
	



	<td>
	<div align="left">${X_OBJECT.department.name}</div>
	</td>

	<td align="left">
	<div align="left">
		<c:if test="${sessionScope.LOGIN_USER.locale!='en'}">
			${X_OBJECT.toCity.chnName}
		</c:if> <c:if test="${sessionScope.LOGIN_USER.locale=='en'}">
			${X_OBJECT.toCity.engName}
	
		</c:if></div>
	</td>


	<td><span style="color:${X_OBJECT.travellingMode.color}"> <c:if
		test="${sessionScope.LOGIN_USER.locale=='en'}">
	 ${X_OBJECT.travellingMode.engShortDescription}
	 </c:if> <c:if test="${sessionScope.LOGIN_USER.locale!='en'}">
	 ${X_OBJECT.travellingMode.chnShortDescription}
	 </c:if> </span></td>


	<td><bean:write name="X_OBJECT" property="requestDate"
		format="yyyy/MM/dd" /></td>
		
		<td>${X_OBJECT.booker.name}</td>



	<td><span style="color:${X_OBJECT.bookStatus.color}"> <c:if
		test="${sessionScope.LOGIN_USER.locale=='en'}">
	 ${X_OBJECT.bookStatus.engShortDescription}
	 </c:if> <c:if test="${sessionScope.LOGIN_USER.locale!='en'}">
	 ${X_OBJECT.bookStatus.chnShortDescription}
	 </c:if> </span></td>
</tr>



