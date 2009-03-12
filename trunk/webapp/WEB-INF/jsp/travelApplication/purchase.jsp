<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ page import="net.sourceforge.model.metadata.ApproveStatus"%>
<%@ page import="net.sourceforge.model.metadata.YesNo"%>
<%@ page import="net.sourceforge.model.metadata.SingleReturn"%>
<%@ page import="net.sourceforge.model.metadata.TravellingMode"%>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>



<xml id="data">
<data>
<logic:iterate id="x_hotel" name="x_hotelList">
	<hotel id="${x_hotel.id}" name="<bean:write name="x_hotel" property="name"/>" 
		description="<bean:write name="x_hotel" property="description"/>"
		specialService="<bean:write name="x_hotel" property="specialService"/>"
		contact="<bean:write name="x_hotel" property="contact"/>" 
		email="<bean:write name="x_hotel" property="email"/>"  >
	<logic:iterate id="x_room" name="x_hotel" property="rooms">
		<room id="${x_room.id}" name="<bean:write name="x_room" property="room"/>" 
			network="<bean:write name="x_room" property="network"/>"
			description="<bean:write name="x_room" property="description"/>"
			price="<bean:write name="x_room" property="price"/>"
			serviceFee="<bean:write name="x_room" property="discount"/>"
			breakfast="<bean:write name="x_room" property="breakfast"/>"			
			>
		</room>
	</logic:iterate>
	<room id="" value="other"  description="" price="" serviceFee="" breakfast="" network="">

	</room>

	</hotel>
</logic:iterate>
<hotel id="" value="other"  description="" specialService="" contact="" email="">
<room id="" value="other"   description="" price="" serviceFee="" breakfast="" network="">

</room>
</hotel>

</data>
</xml>


<xml id="config">
<config>
<hotel>
<room>
</room>
</hotel>
</config>
</xml>

<script>

	
	
	function book()
	{
		var form=document.travelApplicationPurchaseForm;
		if(!validateForm(form))
			return;
		if(form.travellingMode.value=='<%=TravellingMode.AIR.getEnumCode()%>')
		{
			if(!form.newAirTicket)	 
			{
				alert("<bean:message key="travelApplication.book.noAirTicket"/>");
				return;
			}
		}
		with(form)
		{
			saveAction.value="book";
			submit();
		}
	}
	
	
	
	function backToList()
	{
		var url="listTravelApplicationPurchase.do"
		window.location.href=url;
	}
	
	


	
	function validateForm(form)
	{
		if(!validateTravelApplicationPurchaseForm(form))
		{
			return false;
		}
		with(travelApplicationPurchaseForm)
		{
			hotelName.value=trim(hotelName.value);
			roomDescription.value=trim(roomDescription.value);
			if(hotel_id.value=="" && hotelName.value=="")
			{
				hotelName.focus();
				alertRequired("<bean:message key="travelApplication.hotel.id"/>");
				return false;
			}
			if(price_id.value=="" && roomDescription.value=="")
			{
				roomDescription.focus();
				alertRequired("<bean:message key="travelApplication.roomDescription"/>");
				return false;
			}
			
			if(checkInDate.value>checkOutDate.value)
			{
				checkOutDate.focus();
				alert("<bean:message key="travelApplication.purchase.checkInDateAfterOutDate"/>");
				return false;
			}
		}
		return true;
	}
	
	function price_changed(price) {
		price = parseFloat(price);
		if (isNaN(price)) price = 0;
		totalAmountChanged(price);
	}
</script>


<html:javascript formName="travelApplicationPurchaseForm"
	staticJavascript="false" />

<html:form action="purchaseTravelApplication_result.do"
	onsubmit="return validateForm(this)">



	<input type="hidden" value="${x_ta.id}" name="id" />
	
	<input type="hidden" value="" name="saveAction" />

	<table width=90% cellpadding=1 cellspacing=1>
		<tr>
			<td colspan="8"><html:errors/></td>
		</tr>
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
		<td width="80%" colspan="3" id="hotelTD1" style="display:block">
			<table>
				<tr>
					<td>
						<html:select property="hotel_id">
						</html:select> 
					</td>
					<td>
						<span id="hotelNameSpan"> <html:text property="hotelName" /> </span><span class="required">*</span>
					</td>
				</tr>
			</table>	
		</td>
	</tr>
	<tr>
		<td></td>
		<td colspan="3"><span id="hotel_description_span"></span></td>
	</tr>
	<tr>
		<td class="bluetext" width="20%"><bean:message
			key="travelApplication.roomDescription" />:</td>
		<td width="80%" colspan="3" id="roomTD1" style="display:block">
			<table>
				<tr>
					<td>
						<html:select property="price_id">
						</html:select> 
					</td>
					<td>
						<span id="roomDescriptionSpan"> <html:text property="roomDescription" /> </span><span class="required">*</span>
						(<bean:message key="travelApplication.limitationPerDay"/>
						<c:if test="${x_limit!=null}"> ${x_limit} ${x_currency.name}</c:if>
						<c:if test="${x_limit==null}"><bean:message key="travelApplication.purchase.noLimit"/></c:if>)
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td></td>
		<td colspan="3"><span id="room_description_span"></span></td>
	</tr>
	<tr>
			<td class="bluetext" width="20%"><bean:message key="travelApplication.checkInDate" /> :</td>
			<td >
				<html:text property="checkInDate" size="6" />
				<a onclick="event.cancelBubble=true;"
					href="javascript:showCalendar('dimg1',false,'checkInDate',null,null,'travelApplicationPurchaseForm')"><img
					align="absmiddle" border="0" id="dimg1" src="images/datebtn.gif" /></a><span
					class="required">*</span>
			</td>

			<td class="bluetext" width="20%"><bean:message
				key="travelApplication.checkOutDate" /> :</td>
			<td >
				<html:text property="checkOutDate" size="6" />
				<a onclick="event.cancelBubble=true;"
					href="javascript:showCalendar('dimg2',false,'checkOutDate',null,null,'travelApplicationPurchaseForm')"><img
					align="absmiddle" border="0" id="dimg2" src="images/datebtn.gif" /></a><span
					class="required">*</span>
			</td>
		</tr>
	</table>
	
	<hr align="left" width="100%">
	<table width="100%">
	<tr>
		<td class="bluetext" width="20%"><bean:message
			key="travelApplication.travellingMode" /> :</td>
		<td width="30%"><html:select property="travellingMode" onchange="changeTravellingMode()">
				<html:options collection="x_taModeList" property="enumCode"
					labelProperty="${x_lang}ShortDescription" />
			</html:select>
		</td>
		<td class="bluetext" width="20%"><bean:message
			key="travelApplication.singleOrReturn" /> :</td>
		<td width="30%">
			<span style="color:<bean:write name="x_ta" property="singleOrReturn.color" />">
				<bean:write name="x_ta" property="singleOrReturn.${x_lang}ShortDescription" />
			</span>
		</td>
	</tr>
	
	<tr>
		<td width="20%" class="bluetext"><bean:message key="travelApplication.duration" />:</td>
		<td colspan="3"><bean:write name="x_ta" property="fromDate" format="yyyy/MM/dd" />
		~
		<bean:write name="x_ta" property="toDate" format="yyyy/MM/dd" /></td>
	</tr>
	<tr>
		<td class="bluetext" width="20%"><bean:message key="travelApplication.fromTime" />
		:</td>
		<td width="30%"><bean:write name="x_ta" property="fromTime" format="HH:mm" /></td>
		<td class="bluetext" width="20%"><bean:message key="travelApplication.toTime" />
		:</td>
		<td width="30%"><bean:write name="x_ta" property="toTime" format="HH:mm" /></td>
	</tr>
	</table>
	
	<jsp:include page="airTicketList.jsp"/>	

	<hr align="left" width="100%">

	<!--  approver list -->
	<jsp:include page="../approve/list.jsp"/>


	<table width="90%">
		<tr>
			<td width="100%" align="right">
				<input type="button" name="bookButton"
					value="<bean:message key="travelApplication.book"/>"
					onclick="book()"> 

				<input type="button"
					value="<bean:message key="all.backToList"/>"
					onclick="backToList()"></td>
		</tr>
	</table>

</html:form>


<script type="text/javascript">
	function getHotelDescription()
	{
		var form=document.travelApplicationPurchaseForm;
		var aOption=form.hotel_id.options[form.hotel_id.selectedIndex];
		return "<b><bean:message key="hotel.description" />:</b> " + aOption.description + "<br><b><bean:message key="hotel.specialService" />:</b> " + aOption.specialService + "<br><b><bean:message key="hotel.contact" />:</b> " + aOption.contact+"<br><b><bean:message key="hotel.email" />:</b> " + aOption.email;
	}
	
	function getRoomDescription()
	{
		var form=document.travelApplicationPurchaseForm;
		var aOption=form.price_id.options[form.price_id.selectedIndex]
		return "<b><bean:message key="price.price" />:</b> " + aOption.price+"<br><b><bean:message key="price.discount" />:</b> " + aOption.serviceFee+"<br><b><bean:message key="price.breakfast" />:</b> " + aOption.breakfast+"<br><b><bean:message key="price.network" />:</b> "+aOption.network+"<br><b><bean:message key="price.description" />:</b> "+aOption.description;
	}

    var mapping=new Map();
    document.travelApplicationPurchaseForm.hotel_id.oldValue = "${x_ta.hotel.id}";
    document.travelApplicationPurchaseForm.price_id.oldValue = "${x_ta.price.id}";
	mapping.put("hotel_id","hotel");
	mapping.put("price_id","room");
    initCascadeSelect("config","data","travelApplicationPurchaseForm",mapping,true);    
    
    document.all.hotelNameSpan.style.
    	setExpression("display",
    		"document.travelApplicationPurchaseForm.hotel_id.value==''?'':'none'");
    	
    document.all.roomDescriptionSpan.style.
    	setExpression("display",
    		"document.travelApplicationPurchaseForm.price_id.value==''?'':'none'");
    		
    
    
    document.all.hotel_description_span.setExpression("innerHTML","getHotelDescription()");
    document.all.room_description_span.setExpression("innerHTML","getRoomDescription()");
    
   
    
    	
</script>

<c:if test="${x_edit}">
 	<script type="text/javascript">
 	function getAirTicketDIVDisplay()
	{
		with(document.travelApplicationPurchaseForm)
		{
			if(travellingMode.value=='<%=TravellingMode.AIR.getEnumCode()%>')
			{
				return "inline";
			}
			else
			{
				return "none";
			}
		}
	}
     with(document.travelApplicationPurchaseForm)
	 {
			document.all.airTicket.style.setExpression("display","getAirTicketDIVDisplay()");
	 }
	 </script>
</c:if>


