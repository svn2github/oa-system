<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>
<%@ page import="com.aof.model.metadata.*"%>


<xml id="dataCPC">
<data>
<logic:iterate id="x_country" name="x_countryList">
	<country id="${x_country.id}" name="<bean:write name="x_country" property="${x_lang}Name"/>">
	<logic:iterate id="x_p" name="x_country" property="enabledProvinceList">
		<province id="${x_p.id}" name="<bean:write name="x_p" property="${x_lang}Name"/>">
		<logic:iterate id="x_c" name="x_p" property="enabledCityList">
			<city id="${x_c.id}" name="<bean:write name="x_c" property="${x_lang}Name"/>">
			</city>
		</logic:iterate>
		</province>
	</logic:iterate>
	</country>
</logic:iterate>
</data>
</xml>

<xml id="configCPC">
<config>
<country>
<province>
<city>
</city>
</province>
</country>
</config>
</xml>


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
			description="<bean:write name="x_room" property="description"/>" 
			breakfast="<bean:write name="x_room" property="breakfast"/>" 
			price="<bean:write name="x_room" property="price"/>"
			serviceFee="<bean:write name="x_room" property="discount"/>">			
		</room>
	</logic:iterate>
	<room id="-1" value="other" description="" breakfast="" price="" serviceFee="">
			
	</room>
	
	</hotel>
</logic:iterate>
	<hotel id="-1" value="other" description="" specialService="" contact="" email="">
		<room id="-1" value="other" description="" breakfast="" price="" serviceFee="">
			
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

	window.onload=body_onload;

	function body_onload()
	{
		with(document.travelApplicationForm)
		{
			hotel_id.oldValue=
				"<bean:write name="travelApplicationForm" property="hotel_id"/>";
			price_id.oldValue=
				"<bean:write name="travelApplicationForm" property="price_id"/>";

			fromCity_province_country_id.oldValue=
				"<bean:write name="travelApplicationForm" property="fromCity_province_country_id"/>";
			fromCity_province_id.oldValue=
				"<bean:write name="travelApplicationForm" property="fromCity_province_id"/>";
			fromCity_id.oldValue=
				"<bean:write name="travelApplicationForm" property="fromCity_id"/>";
		}
	
		var mapping=new Map();
		mapping.put("hotel_id","hotel");
		mapping.put("price_id","room");
	
	    initCascadeSelect("config","data","travelApplicationForm",mapping,true);
	    
	    
	    mapping=new Map();
		mapping.put("fromCity_province_country_id","country");
		mapping.put("fromCity_province_id","province");
		mapping.put("fromCity_id","city");
		
	    initCascadeSelect("configCPC","dataCPC","travelApplicationForm",mapping,true);
	    
	    
	    document.all.hotelNameSpan.style.
	    	setExpression("display","document.travelApplicationForm.hotel_id.value=='-1'?'':'none'");
	    	
	    document.all.roomDescriptionSpan.style.
	    	setExpression("display","document.travelApplicationForm.price_id.value=='-1'?'':'none'");
	    	
	    document.all.hotel_description_span.setExpression("innerHTML","getHotelDescription()");
	    document.all.room_description_span.setExpression("innerHTML","getRoomDescription()");	
	}
	
	

	function saveAsDraft()
	{
		with(document.travelApplicationForm)
		{
			draft.value="true";
			if(validateForm(document.travelApplicationForm))
				document.travelApplicationForm.submit();
		}
	}
	
	function submitTA()
	{
		with(document.travelApplicationForm)
		{
			draft.value="false";
			if(validateForm(document.travelApplicationForm))
			{
				var urgentMessage="<bean:message key="travelApplication.urgent.warning"/>";
				var submitMessage="<bean:message key="travelApplication.submit.confirm"/>";
								
				if (document.travelApplicationForm.urgent.options[document.travelApplicationForm.urgent.selectedIndex].value == "<%=com.aof.model.metadata.TravelApplicationUrgent.URGENT%>") {				
					if (confirm(urgentMessage)) {
						document.travelApplicationForm.submit();
					}
				} else {
					if (confirm(submitMessage)) {
						document.travelApplicationForm.submit();
					}
				}
			}
		}
	}
	
	function viewApprover() {
		if (validateForm(document.travelApplicationForm)) {
			var form=document.travelApplicationForm;
			var action = form.action;
			var i = action.lastIndexOf('.');
			if (i == -1) {
				form.action = action + "_viewApprover";
			} else {
				form.action = action.substring(0, i) + "_viewApprover" + action.substring(i);
			}
			form.target = "viewapprover";
			form.submit();
			form.action = action;
			form.target = "";
			document.getElementById("oldApprovers").style.display = "none";
		}
	}
	
	function viewApprover_callback(content) {
		with (document.getElementById("newApprovers")) {
			//style.display = "block";
			innerHTML = content;
			scrollIntoView();
		}
	}
	
	function getHotelDescription()
	{
		var form=document.travelApplicationForm;
		var aOption=form.hotel_id.options[form.hotel_id.selectedIndex];
		return "<b><bean:message key="hotel.description" />:</b> " + aOption.description + "<br><b><bean:message key="hotel.specialService" />:</b> "  + aOption.specialService +  "<br><b><bean:message key="hotel.contact" />:</b> " + aOption.contact+"<br><b><bean:message key="hotel.email" />:</b> " + aOption.email;
	}
	
	function getRoomDescription()
	{
		var form=document.travelApplicationForm;
		var aOption=form.price_id.options[form.price_id.selectedIndex];
		return "<b><bean:message key="price.price" />:</b> " + aOption.price+"<br><b><bean:message key="price.discount" />:</b> " + aOption.serviceFee+"<br><b><bean:message key="price.breakfast" />:</b> " + aOption.breakfast+"<br><b><bean:message key="price.network" />:</b> "+aOption.network+"<br><b><bean:message key="price.description" />:</b> "+aOption.description;
	}
	
	
	function validateForm(form)
	{
		if(!validateTravelApplicationForm(form))
		{
			return false;
		}
		with(travelApplicationForm)
		{
			hotelName.value=trim(hotelName.value);
			roomDescription.value=trim(roomDescription.value);
			/*if(hotel_id.value=="-1" && hotelName.value=="")
			{
				hotelName.focus();
				alertRequired("<bean:message key="travelApplication.hotel.id"/>");
				return false;
			}
			if(price_id.value=="-1" && roomDescription.value=="")
			{
				roomDescription.focus();
				alertRequired("<bean:message key="travelApplication.roomDescription"/>");
				return false;
			}*/
			fromDate.value=trim(fromDate.value);
			toDate.value=trim(toDate.value);
			if(fromDate.value!="" && toDate.value!="")
			{
				if(fromDate.value>toDate.value)
				{
					toDate.focus();
					alert("<bean:message key="travelApplication.fromDateAfterToDate"/>");
					return false;
				}
			}
		}
		return true;
	}

</script>

<html:javascript formName="travelApplicationForm" staticJavascript="false" />