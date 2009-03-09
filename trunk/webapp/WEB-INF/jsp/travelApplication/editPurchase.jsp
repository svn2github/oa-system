<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ page import="com.aof.model.metadata.ApproveStatus"%>
<%@ page import="com.aof.model.metadata.YesNo"%>
<%@ page import="com.aof.model.metadata.TravellingMode"%>
<%@ page import="com.aof.model.metadata.SingleReturn"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<c:set var="ta_bookStatus_Received" value="<%=com.aof.model.metadata.TravelApplicationBookStatus.RECEIVED%>"/>
<c:set var="air" value="<%=com.aof.model.metadata.TravellingMode.AIR.getEnumCode()%>"/>
<c:set var="YesNo_NO" value="<%=com.aof.model.metadata.YesNo.NO%>"/>
<c:set var="YesNo_YES" value="<%=com.aof.model.metadata.YesNo.YES%>"/>
<script>
	
	
	function backToList()
	{
		var url="listTravelApplicationPurchase.do"
		window.location.href=url;
	}
	
</script>
	<table width=100%>
		<tr>
			<td colspan="8">
				<span class="message">
					<html:messages id="x_message" message="true">
						${x_message}<br>
					</html:messages>
				</span>
			</td>
		</tr>
	</table>
	<table width="100%">
		<tr>
			<td width="20%" class="bluetext"><bean:message key="travelGroup.site" /></td>
			<td width="80%" colspan="3">${x_ta.department.site.name}</td>
		</tr>
		<tr>
			<td width="20%" class="bluetext"><bean:message
				key="travelGroup.department" /></td>
			<td width="80%" colspan="3">${x_ta.department.name}</td>
		</tr>
		<tr>
			<td width="20%" class="bluetext"><bean:message key="travelApplication.requestor" />:</td>
			<td width="30%">${x_ta.requestor.name}</td>
			<td width="20%" class="bluetext"><bean:message key="travelApplication.requestDate" />:</td>
			<td width="30%">
				<fmt:formatDate value="${x_ta.requestDate}" pattern="yyyy/MM/dd"/>
			</td>
		</tr>
		<tr>
			<td width="20%" class="bluetext"><bean:message key="travelApplication.creator" />:</td>
			<td width="30%">${x_ta.creator.name}</td>
			<td width="20%" class="bluetext"><bean:message key="travelApplication.createDate" />:</td>
			<td width="30%">
				<fmt:formatDate value="${x_ta.createDate}" pattern="yyyy/MM/dd"/>
			</td>
		</tr>
	</table>
	<hr align="left" width="100%">
	<table width=100%>
		<tr>
			<td width="20%" class='bluetext'><bean:message key="travelApplication.id" />:&nbsp;</td>
			<td width="80%" colspan="3"  class="blue-highlight">${x_ta.id}</td>
		</tr>
		<tr>
			<td class="bluetext" width="20%"><bean:message
				key="travelApplication.title" />:</td>
			<td width="80%" colspan="3">${x_ta.title}</td>
		</tr>
		<tr>
			<td class="bluetext" width="20%"><bean:message
				key="travelApplication.description" />:</td>
			<td width="80%" colspan="3">${x_ta.description}</td>
		</tr>
		<tr>
			<td class="bluetext" width="20%"><bean:message
				key="travelApplication.urgent" /> :</td>
			<td width="30%"><span
				style="color:${x_ta.urgent.color}"> <c:if
				test="${sessionScope.LOGIN_USER.locale=='en'}">${x_ta.urgent.engDescription}</c:if>
			<c:if test="${sessionScope.LOGIN_USER.locale!='en'}">${x_ta.urgent.chnDescription}</c:if>
			</span></td>
			<td class='bluetext' width="20%"><bean:message
				key="travelApplication.status" />:</td>
			<td width="30%"><span
				style="color:${x_ta.status.color}"> <c:if
				test="${sessionScope.LOGIN_USER.locale=='en'}">${x_ta.status.engDescription}</c:if>
			<c:if test="${sessionScope.LOGIN_USER.locale!='en'}">${x_ta.status.chnDescription}</c:if>
			</span>
			</td>
		</tr>
		<tr>
			<td class="bluetext" width="20%"><bean:message key="travelApplication.fee" />:</td>
			<td>${x_ta.fee}</td>
			<td class="bluetext" width="20%"><bean:message key="travelApplication.currecny" />:</td>
			<td>${x_ta.currency.name}
			</td>			
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
		<td><c:if test="${sessionScope.LOGIN_USER.locale=='en'}">${x_ta.fromCity.province.country.engName}</c:if>
		<c:if test="${sessionScope.LOGIN_USER.locale!='en'}">${x_ta.fromCity.province.country.chnName}</c:if>
		</td>
		<td><c:if test="${sessionScope.LOGIN_USER.locale=='en'}">${x_ta.toCity.province.country.engName}</c:if>
		<c:if test="${sessionScope.LOGIN_USER.locale!='en'}">${x_ta.toCity.province.country.chnName}</c:if>
		</td>
	</tr>

	<tr>
		<td class="bluetext"><bean:message key="travelApplication.province" />:</td>
		<td><c:if test="${sessionScope.LOGIN_USER.locale=='en'}">${x_ta.fromCity.province.engName}</c:if>
		<c:if test="${sessionScope.LOGIN_USER.locale!='en'}">${x_ta.fromCity.province.chnName}</c:if>
		</td>
		<td><c:if test="${sessionScope.LOGIN_USER.locale=='en'}">${x_ta.toCity.province.engName}</c:if>
		<c:if test="${sessionScope.LOGIN_USER.locale!='en'}">${x_ta.toCity.province.chnName}</c:if>
		</td>
	</tr>

	<tr>
		<td class="bluetext"><bean:message key="travelApplication.province" />:</td>
		<td><c:if test="${sessionScope.LOGIN_USER.locale=='en'}">${x_ta.fromCity.engName}</c:if>
		<c:if test="${sessionScope.LOGIN_USER.locale!='en'}">${x_ta.fromCity.chnName}</c:if>
		</td>
		<td><c:if test="${sessionScope.LOGIN_USER.locale=='en'}">${x_ta.toCity.engName}</c:if>
		<c:if test="${sessionScope.LOGIN_USER.locale!='en'}">${x_ta.toCity.chnName}</c:if>
		</td>
	</tr>
	</table>

	<hr align="left" width="100%">
	<table width="100%">
	<tr>
		<td class="bluetext" width="20%"><bean:message
			key="travelApplication.hotel.id" />:</td>
		<td width="80%" colspan="3"><c:if test="${x_ta.hotel.id!=null}">
			${x_ta.hotel.name}
		</c:if> <c:if test="${x_ta.hotel.id==null}">
			${x_ta.hotelName}
		</c:if></td>
	</tr>
	<tr>
		<td class="bluetext" width="20%"><bean:message
			key="travelApplication.roomDescription" />:</td>
		<td width="80%" colspan="3"><c:if test="${x_ta.price.id!=null}">
			${x_ta.price.room}
		</c:if> <c:if test="${x_ta.price.id==null}">
			${x_ta.roomDescription}
		</c:if></td>
	</tr>
	<tr>
		<td class="bluetext" width="20%"><bean:message
			key="travelApplication.hotel.id" />:</td>
		<td>
			<bean:write name="x_ta" property="checkInDate" format="yyyy/MM/dd"/>
		</td>
		<td class="bluetext" width="20%"><bean:message
				key="travelApplication.checkOutDate" /> :</td>
		<td>
				<bean:write name="x_ta" property="checkOutDate" format="yyyy/MM/dd" />			
			</td>
		</tr>
	<tr>
	</table>
	<hr align="left" width="100%">

	<html:form action="/updateTravelApplicationPurchase">
		<input type="hidden" name="id" value="${x_ta.id}">

		<table width="100%">
		<tr>
			<td class="bluetext" width="20%"><bean:message
				key="travelApplication.travellingMode" /> :</td>
			<td width="30%"><span
				style="color:${x_ta.travellingMode.color}"> <c:if
				test="${sessionScope.LOGIN_USER.locale=='en'}">${x_ta.travellingMode.engDescription}</c:if>
			<c:if test="${sessionScope.LOGIN_USER.locale!='en'}">${x_ta.travellingMode.chnDescription}</c:if>
			</span></td>
			<td class="bluetext" width="20%"><bean:message
				key="travelApplication.singleOrReturn" /> :</td>
			<td width="30%"><span
				style="color:${x_ta.singleOrReturn.color}"> <c:if
				test="${sessionScope.LOGIN_USER.locale=='en'}">${x_ta.singleOrReturn.engDescription}</c:if>
			<c:if test="${sessionScope.LOGIN_USER.locale!='en'}">${x_ta.singleOrReturn.chnDescription}</c:if>
			</span></td>
		</tr>
		
		<tr>
			<td width="20%" class="bluetext"><bean:message key="travelApplication.duration" />:</td>
			<td colspan="3"><bean:write name="x_ta"
				property="fromDate" format="yyyy/MM/dd" /> <c:if
				test="${x_ta.fromDate!=null || x_ta.toDate!=null}">
			~
			</c:if> <bean:write name="x_ta" property="toDate"
				format="yyyy/MM/dd" /></td>
		</tr>
		<tr>
			<td class="bluetext" width="20%"><bean:message key="travelApplication.fromTime" />
			:</td>
			<td width="30%"><bean:write name="x_ta"
				property="fromTime" format="HH:mm" /></td>
			<td class="bluetext" width="20%"><bean:message key="travelApplication.toTime" />
			:</td>
			<td width="30%"><bean:write name="x_ta"
				property="toTime" format="HH:mm" /></td>
		</tr>
		<c:if test="${x_ta.bookStatus == ta_bookStatus_Received}">
		<tr style="display:none">					
			<td class="bluetext" width="20%"><bean:message key="travelApplication.isontravel" /> :</td>
			<td >
				<c:choose>
					<c:when test="${x_ta.isOnTravel == YesNo_NO}">
						<select name="isOnTravel">
							<c:if test="${sessionScope.LOGIN_USER.locale=='en'}">					
								<option value="${YesNo_YES.enumCode}">
									${YesNo_YES.engDescription}
								</option>
								<option value="${YesNo_NO.enumCode}" selected>
									${YesNo_NO.engDescription}
								</option>
							</c:if>
							<c:if test="${sessionScope.LOGIN_USER.locale!='en'}">
								<option value="${YesNo_YES.enumCode}">
									${YesNo_YES.chnDescription}
								</option>
								<option value="${YesNo_NO.enumCode}" selected>
									${YesNo_NO.chnDescription}
								</option>
							</c:if>									
						</select>
					</c:when>
					<c:otherwise>
						<c:if test="${sessionScope.LOGIN_USER.locale=='en'}">					
							${YesNo_YES.engDescription}
						</c:if>
						<c:if test="${sessionScope.LOGIN_USER.locale!='en'}">
							${YesNo_YES.chnDescription}
						</c:if>		
					</c:otherwise>
				</c:choose>				
			</td>
			<td>
				<input type="hidden" name="id" value="<bean:write name="x_ta" property="id"/>">
			</td>			
		</tr>	
		</c:if>	
		</table>  
	
		<c:if test="${x_ta.travellingMode.enumCode==air}">
			<jsp:include page="airTicketList.jsp"/>	
		</c:if>
	</html:form>

	<hr align="left" width="100%">

	<!--  approver list -->
	<table width="100%">
		<tr>
			<td><jsp:include page="../approve/list.jsp"/></td>
		</tr>
	</table>


	<table width="90%">
		
		<tr>
		
			<td width="100%" align="right">
				<c:if test="${x_edit}">
				<input type="button"
					value="<bean:message key="all.save"/>"
					onclick="document.updateTravelApplicationPurchaseForm.submit();">
				</c:if>	
				
				<input type="button"
					value="<bean:message key="all.backToList"/>"
					onclick="backToList()"></td>
		</tr>
	</table>




