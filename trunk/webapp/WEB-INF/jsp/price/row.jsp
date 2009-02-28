<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<c:set var="x_hotel_promoteStatus_GLOBAL" value="<%=com.aof.model.metadata.HotelPromoteStatus.GLOBAL%>"/>
<tr id="s${X_OBJECT.id}">
	<td>
		<c:choose>
			<c:when test="${x_hotel == null || (x_version=='' && x_hotel.promoteStatus == x_hotel_promoteStatus_GLOBAL) || (x_version!='' && x_hotel.promoteStatus != x_hotel_promoteStatus_GLOBAL)}">
				<a href='javascript:editPrice("${X_OBJECT.id}")'>${X_OBJECT.room}</a>
			</c:when>
			<c:otherwise>
				${X_OBJECT.room}
			</c:otherwise>
		</c:choose>
	</td>
	<td>
		${X_OBJECT.price}
	</td>
	<td >
		${X_OBJECT.discount}
	</td>
	<td>
		${X_OBJECT.network}
	</td>
	<td>
		${X_OBJECT.breakfast}
	</td>
	<td>
		${X_OBJECT.description}
	</td>
	<td>
	<span style="color:${X_OBJECT.enabled.color}">
	
	<c:if test="${sessionScope.LOGIN_USER.locale=='en'}">
	 ${X_OBJECT.enabled.engShortDescription}
	 </c:if>
	 <c:if test="${sessionScope.LOGIN_USER.locale!='en'}">
	 ${X_OBJECT.enabled.chnShortDescription}
	 </c:if>
	 
	</span>

	</td>
	
</tr>

