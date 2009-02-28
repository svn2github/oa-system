<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>

<table width="90%">
	<tr>
		<td colspan="10">
			<div class="message">
					<html:messages id="x_message" message="true">
						${x_message}<br>
					</html:messages>
				</div>
			</td>
		</tr>
</table>

	<table width="100%">
	<tr>
		<td width="20%" class="bluetext"><bean:message key="travelGroup.site" /></td>
		<td width="80%" colspan="3">${x_travelApplication.department.site.name}</td>
	</tr>
	<tr>
		<td width="20%" class="bluetext"><bean:message
			key="travelGroup.department" /></td>
		<td width="80%" colspan="3">${x_travelApplication.department.name}</td>
	</tr>
	<tr>
			<td width="20%" class="bluetext"><bean:message key="travelApplication.requestor" />:</td>
			<td width="30%">${x_travelApplication.requestor.name}</td>
			<td width="20%" class="bluetext"><bean:message key="travelApplication.requestDate" />:</td>
			<td width="30%">
				<fmt:formatDate value="${x_travelApplication.requestDate}" pattern="yyyy/MM/dd"/>
			</td>
		</tr>
		<tr>
			<td width="20%" class="bluetext"><bean:message key="travelApplication.creator" />:</td>
			<td width="30%">${x_travelApplication.creator.name}</td>
			<td width="20%" class="bluetext"><bean:message key="travelApplication.createDate" />:</td>
			<td width="30%">
				<fmt:formatDate value="${x_travelApplication.createDate}" pattern="yyyy/MM/dd"/>
			</td>
		</tr>
	</table>
	<hr align="left" width="100%">
	<table width=100%>
		<tr>
			<td width="20%" class='bluetext'><bean:message key="travelApplication.id" />:&nbsp;</td>
			<td width="80%" colspan="3"  class="blue-highlight">${x_travelApplication.id}</td>
		</tr>
		<tr>
			<td class="bluetext" width="20%"><bean:message
				key="travelApplication.title" />:</td>
			<td width="80%" colspan="3">${x_travelApplication.title}</td>
		</tr>
		<tr>
			<td class="bluetext" width="20%"><bean:message
				key="travelApplication.description" />:</td>
			<td width="80%" colspan="3">${x_travelApplication.description}</td>
		</tr>
		<tr>
			<td class="bluetext" width="20%"><bean:message
				key="travelApplication.urgent" /> :</td>
			<td width="30%"><span
				style="color:${x_travelApplication.urgent.color}"> <c:if
				test="${sessionScope.LOGIN_USER.locale=='en'}">${x_travelApplication.urgent.engDescription}</c:if>
			<c:if test="${sessionScope.LOGIN_USER.locale!='en'}">${x_travelApplication.urgent.chnDescription}</c:if>
			</span></td>
			<td class='bluetext' width="20%"><bean:message
				key="travelApplication.status" />:</td>
			<td width="30%"><span
				style="color:${x_travelApplication.status.color}"> <c:if
				test="${sessionScope.LOGIN_USER.locale=='en'}">${x_travelApplication.status.engDescription}</c:if>
			<c:if test="${sessionScope.LOGIN_USER.locale!='en'}">${x_travelApplication.status.chnDescription}</c:if>
			</span>
			</td>
			
		</tr>
		<tr>
			<td class="bluetext" width="20%"><bean:message key="travelApplication.fee" />:</td>
			<td colspan=4>${x_travelApplication.fee}</td>			
		</tr>
	</table>
	<hr align="left" width="100%">
	<table width=100%>
	<tr>
			<td width="20%"></td>
			<td width="40%" align="left" class="bluetext"><h3><bean:message key="travelApplication.from" /></h3></td>
			<td width="40%" align="left" class="bluetext"><h3><bean:message key="travelApplication.to" /></h3></td>
		</tr>

	<tr>
		<td class="bluetext"><bean:message key="travelApplication.country" />:</td>
		<td><c:if test="${sessionScope.LOGIN_USER.locale=='en'}">${x_travelApplication.fromCity.province.country.engName}</c:if>
		<c:if test="${sessionScope.LOGIN_USER.locale!='en'}">${x_travelApplication.fromCity.province.country.chnName}</c:if>
		</td>
		<td><c:if test="${sessionScope.LOGIN_USER.locale=='en'}">${x_travelApplication.toCity.province.country.engName}</c:if>
		<c:if test="${sessionScope.LOGIN_USER.locale!='en'}">${x_travelApplication.toCity.province.country.chnName}</c:if>
		</td>
	</tr>

	<tr>
		<td class="bluetext"><bean:message key="travelApplication.province" />:</td>
		<td><c:if test="${sessionScope.LOGIN_USER.locale=='en'}">${x_travelApplication.fromCity.province.engName}</c:if>
		<c:if test="${sessionScope.LOGIN_USER.locale!='en'}">${x_travelApplication.fromCity.province.chnName}</c:if>
		</td>
		<td><c:if test="${sessionScope.LOGIN_USER.locale=='en'}">${x_travelApplication.toCity.province.engName}</c:if>
		<c:if test="${sessionScope.LOGIN_USER.locale!='en'}">${x_travelApplication.toCity.province.chnName}</c:if>
		</td>
	</tr>

	<tr>
		<td class="bluetext"><bean:message key="travelApplication.city" />:</td>
		<td><c:if test="${sessionScope.LOGIN_USER.locale=='en'}">${x_travelApplication.fromCity.engName}</c:if>
		<c:if test="${sessionScope.LOGIN_USER.locale!='en'}">${x_travelApplication.fromCity.chnName}</c:if>
		</td>
		<td><c:if test="${sessionScope.LOGIN_USER.locale=='en'}">${x_travelApplication.toCity.engName}</c:if>
		<c:if test="${sessionScope.LOGIN_USER.locale!='en'}">${x_travelApplication.toCity.chnName}</c:if>
		</td>
	</tr>
	</table>

	<hr align="left" width="100%">
	<table width="100%">
	<tr>
		<td class="bluetext" width="20%"><bean:message
			key="travelApplication.hotel.id" />:</td>
		<td width="30%">
			<c:if test="${x_travelApplication.hotel.id!=null}">
				${x_travelApplication.hotel.name}
			</c:if> 
			<c:if test="${x_travelApplication.hotel.id==null}">
				${x_travelApplication.hotelName}
			</c:if>
		</td>
		<td colspan="2" width="50%">	
			<c:if test="${x_travelApplication.hotel.id!=null}">		 	
				<span id="hotel_description_span">
					<b><bean:message key="hotel.description" />:</b> ${x_travelApplication.hotel.description}
					<br>
					<b><bean:message key="hotel.contact" />:</b> ${x_travelApplication.hotel.contact}
					<br>
					<b><bean:message key="hotel.email" />:</b> ${x_travelApplication.hotel.email}
				</span>
			</c:if>
		</td>
	</tr>
	<tr>
		<td class="bluetext" width="20%"><bean:message
			key="travelApplication.roomDescription" />:</td>
		<td width="30%">
			<c:if test="${x_travelApplication.price.id!=null}">
				${x_travelApplication.price.room}
			</c:if>
			<c:if test="${x_travelApplication.price.id==null}">
				${x_travelApplication.roomDescription}
			</c:if>
		</td>
		<td colspan="2" width="50%">	
			<c:if test="${x_travelApplication.price.id!=null}">		 	
				<span id="hotel_description_span">
					<b><bean:message key="price.price" />:</b>${x_travelApplication.price.price}
					<br>
					<b><bean:message key="price.discount" />:</b>${x_travelApplication.price.discount}
					<br>
					<b><bean:message key="price.breakfast" />:</b>${x_travelApplication.price.breakfast}
					<br>
					<b><bean:message key="price.network" />:</b>${x_travelApplication.price.network}					
					<br>
					<b><bean:message key="price.description" />:</b>${x_travelApplication.price.description}					
				</span>
			</c:if>
		</td>
	</tr>
	
	<tr>
		<td class="bluetext" width="20%">
			<bean:message key="travelApplication.checkInDate" />:
		</td>
		<td >
			<bean:write name="x_travelApplication" property="checkInDate" format="yyyy/MM/dd"/>
		</td>
		<td class="bluetext" width="20%">
			<bean:message key="travelApplication.checkOutDate" />:
		</td>
		<td >
			<bean:write name="x_travelApplication" property="checkOutDate" format="yyyy/MM/dd"/>		
		</td>
	</tr>
	
	</table>

	<hr align="left" width="100%">
	<table width="100%">
	<tr>
		<td class="bluetext" width="20%"><bean:message
			key="travelApplication.travellingMode" /> :</td>
		<td width="30%"><span
			style="color:${x_travelApplication.travellingMode.color}"> <c:if
			test="${sessionScope.LOGIN_USER.locale=='en'}">${x_travelApplication.travellingMode.engDescription}</c:if>
		<c:if test="${sessionScope.LOGIN_USER.locale!='en'}">${x_travelApplication.travellingMode.chnDescription}</c:if>
		</span></td>
		<td class="bluetext" width="20%"><bean:message
			key="travelApplication.singleOrReturn" /> :</td>
		<td width="30%"><span
			style="color:${x_travelApplication.singleOrReturn.color}"> <c:if
			test="${sessionScope.LOGIN_USER.locale=='en'}">${x_travelApplication.singleOrReturn.engDescription}</c:if>
		<c:if test="${sessionScope.LOGIN_USER.locale!='en'}">${x_travelApplication.singleOrReturn.chnDescription}</c:if>
		</span></td>
	</tr>
	
	<tr>
		<td width="20%" class="bluetext"><bean:message key="travelApplication.duration" />:</td>
		<td colspan="3"><bean:write name="x_travelApplication"
			property="fromDate" format="yyyy/MM/dd" /> <c:if
			test="${x_travelApplication.fromDate!=null || x_travelApplication.toDate!=null}">
		~
		</c:if> <bean:write name="x_travelApplication" property="toDate"
			format="yyyy/MM/dd" /></td>
	</tr>
	<tr>
		<td class="bluetext" width="20%"><bean:message key="travelApplication.fromTime" />
		:</td>
		<td width="30%"><bean:write name="x_travelApplication"
			property="fromTime" format="HH:mm" /></td>
		<td class="bluetext" width="20%"><bean:message key="travelApplication.toTime" />
		:</td>
		<td width="30%"><bean:write name="x_travelApplication"
			property="toTime" format="HH:mm" /></td>
	</tr>
	</table>

	<hr align="left" width="100%">



<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td><jsp:include page="../approve/list.jsp" /></td>
	</tr>
</table>

