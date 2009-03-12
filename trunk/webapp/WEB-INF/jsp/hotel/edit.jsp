<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>
<c:set var="x_hotel_promoteStatus_GLOBAL" value="<%=net.sourceforge.model.metadata.HotelPromoteStatus.GLOBAL%>"/>
<c:set var="x_hotel_promoteStatus_SITE" value="<%=net.sourceforge.model.metadata.HotelPromoteStatus.SITE%>"/>
<c:set var="x_hotel_promoteStatus_REQUEST" value="<%=net.sourceforge.model.metadata.HotelPromoteStatus.REQUEST%>"/>

<c:set var="modifyable" value="${(x_version=='' && x_hotel.promoteStatus == x_hotel_promoteStatus_GLOBAL) || (x_version!='' && x_hotel.promoteStatus != x_hotel_promoteStatus_GLOBAL)}"/>
<script type="text/javascript">
<!--
	function addContract() {
		var url="newHotelContract${x_version}.do?hotel_id=${x_hotel.id}";
		var v=dialogAction(url,'hotelContract.new.title',400,150);
		if (v) {
			var table = document.all('datatable');
			appendRow(table, v);
		    applyRowStyle(table);
		};
	}
	function addPrice() {
		var url="newPrice${x_version}.do?hotel_id=${x_hotel.id}";
		var v=dialogAction(url,'price.new.title',400,320);
		if (v) {
			var table = document.all('datatable2');
			appendRow(table, v);
		    applyRowStyle(table);
		};
	}
	
	function editPrice(id) {
		var url="editPrice${x_version}.do?id="+id;
		var v=dialogAction(url,'price.edit.title',400,320);
		if (v) {
			updateRow(document.all('s'+id),v);
		};
	}
	
	function deletePrice(id)
	{
		var url="deletePrice${x_version}.do?id="+id;
		var v=dialogAction(url,'price.delete.title',400,180);
		if(v)
		{
			updateRow(document.all('s'+id),v);
		}
	}
	
	<c:if test="${x_version=='' && x_hotel.site != null && x_hotel.promoteStatus == x_hotel_promoteStatus_SITE}">
	function request(id) {
		var url="requestPromoteHotel.do?id="+id;
		var v= dialogAction(url,'hotel.promote.request.title',400,180);
		if (v) {
			document.getElementById("promoteMessageValue").innerHTML=v;
			document.getElementById("promoteSuccessMessage").style.display="block";
			document.getElementById("promoteMessageTD").style.display="block";
			document.getElementById("promoteRequestButton").style.display="none";			
		};
	}
	</c:if>
	
	<c:if test="${x_version!='' && x_hotel.site != null && x_hotel.promoteStatus == x_hotel_promoteStatus_REQUEST}">
	function response(id) {
		var confirmMessage = "<bean:message key="hotel.promote.response.confirm.noMessage" arg0="${x_hotel.name}"/>";
		if (confirm(confirmMessage)) {
			window.location.href="responsePromoteHotel.do?id="+id;
		}
		//var url="responsePromoteHotel.do?id="+id;
		//var v=dialogAction(url,'hotel.promote.response.title',400,180);
		/*
		if (v) {
			var table = document.all('datatable');
			deleteRow(document.all('r'+id));
		    applyRowStyle(table);
		};
		*/
	}
	</c:if>

	/*function delete(id) {
		v = window.showModalDialog(
			'showDialog.do?title=hotelContract.delete.title&editExpenseSubCategory.do?id=' + id , 
			null, 'dialogWidth:400px;dialogHeight:180px;status:no;help:no;scroll:no');
		if (v) {
			updateRow(document.all('r' + id), v);
		};
	}*/
	
//-->
</script>

<xml id="data">
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

<xml id="config">
<config>
<country>
<province>
<city>
</city>
</province>
</country>
</config>
</xml>

<html:javascript formName="hotelForm" staticJavascript="false" />
<html:form action="${x_action}" method="post"
	onsubmit="return validateHotelForm(this)">
	<html:hidden property="promoteStatus" />
	<html:hidden property="id" />
	<html:hidden property="site_id" />
	
	<input type="hidden" name="city_province_country_id_value" value="<bean:write name="hotelForm" property="city_province_country_id"/>">
	<input type="hidden" name="city_province_id_value"  value="<bean:write name="hotelForm" property="city_province_id"/>">
	<input type="hidden" name="city_id_value"   value="<bean:write name="hotelForm" property="city_id"/>">
	<input type="hidden" name="promoteMessage" value="<bean:write name="hotelForm" property="promoteMessage"/>">

	<table width=90% border=0 cellpadding=4 cellspacing=0>
		<tr>
			<td colspan="4">
				<html:errors/>
			</td>
		</tr>
		<tr>
			<td colspan="4">
				<span style="color:green;font-weight:bold"><html:messages id="x_message" name="success" message="true">
					${x_message}
				</html:messages>
				<c:if test="${(x_version=='' && x_hotel.site != null)}">
					<div id="promoteSuccessMessage" style="display:none">
						<bean:message key="hotel.promote.success"/>
					</div>
				</c:if>
				</span>
				
			</td>
		</tr>

		<tr id="promoteMessageTD" <c:if test="${x_hotel.promoteStatus != x_hotel_promoteStatus_REQUEST}">style="display:none"</c:if>>
			<td class="bluetext"><bean:message key="hotel.promoteMessage" />:</td>
			<td colspan="3">
				<div id="promoteMessageValue">
					${x_hotel.promoteMessage}
				</div>
			</td>
		</tr>
		
		<tr>
			<td width="20%" class="bluetext"><bean:message key="hotel.id" />:</td>
			<td width="30%">${x_hotel.id}</td>

			<td width="20%" class="bluetext"><bean:message key="hotel.name" />:</td>
			<td width="30%">
				<c:choose>
					<c:when test="${modifyable}">
						<html:text property="name" /><span class="required">*</span>
					</c:when>
					<c:otherwise>
						${x_hotel.name}
					</c:otherwise>
				</c:choose>
			</td>
		</tr>
		<tr>
			<td class="bluetext"><bean:message key="country.id" />:</td>
			<td align="left">
				<c:choose>
					<c:when test="${modifyable}">
						<html:select property="city_province_country_id">
						</html:select>
					</c:when>
					<c:otherwise>
						<c:if test="${sessionScope.LOGIN_USER.locale=='en'}">					
							${x_hotel.city.province.country.engName}
						</c:if>
						<c:if test="${sessionScope.LOGIN_USER.locale!='en'}">
							${x_hotel.city.province.country.chnName}
						</c:if>											
					</c:otherwise>
				</c:choose>
			</td>
			
			<td class="bluetext"><bean:message key="hotel.currency" />:</td>
			<td>	
				<c:choose>
					<c:when test="${modifyable}">
						<html:select property="currency_code">
							<html:options collection="x_currencyList" property="code" labelProperty="name" />
						</html:select>
					</c:when>
					<c:otherwise>
						${x_hotel.currency.name}							
					</c:otherwise>
				</c:choose>
			</td>
		</tr>
		<tr>
			<td class="bluetext"><bean:message key="province.id" />:</td>
			<td align="left">
				<c:choose>
					<c:when test="${modifyable}">
						<html:select property="city_province_id">
						</html:select>
					</c:when>
					<c:otherwise>
						<c:if test="${sessionScope.LOGIN_USER.locale=='en'}">					
							${x_hotel.city.province.engName}
						</c:if>
						<c:if test="${sessionScope.LOGIN_USER.locale!='en'}">
							${x_hotel.city.province.chnName}
						</c:if>											
					</c:otherwise>
				</c:choose>
			</td>
			
			<td class="bluetext"><bean:message key="hotel.telephone" />:</td>
			<td>
				<c:choose>
					<c:when test="${modifyable}">
						<html:text property="telephone" />
					</c:when>
					<c:otherwise>					
						${x_hotel.telephone}										
					</c:otherwise>
				</c:choose>
			</td>
		</tr>
		<tr>
			<td class="bluetext"><bean:message key="hotel.city.id" />:</td>
			<td align="left">
				<c:choose>
					<c:when test="${modifyable}">
						<html:select property="city_id">
						</html:select>
						<span class="required">*</span>
					</c:when>
					<c:otherwise>
						<c:if test="${sessionScope.LOGIN_USER.locale=='en'}">					
							${x_hotel.city.engName}
						</c:if>
						<c:if test="${sessionScope.LOGIN_USER.locale!='en'}">
							${x_hotel.city.chnName}
						</c:if>											
					</c:otherwise>
				</c:choose>
			</td>
			
			<td class="bluetext"><bean:message key="hotel.fax" />:</td>
			<td>
				<c:choose>
					<c:when test="${modifyable}">
						<html:text property="fax" />
					</c:when>
					<c:otherwise>					
						${x_hotel.fax}										
					</c:otherwise>
				</c:choose>
			</td>
		</tr>
		<tr>
			<td class="bluetext"><bean:message key="hotel.address" />:</td>
			<td colspan="3">
				<c:choose>
					<c:when test="${modifyable}">
						<html:text property="address" size="77"/>
					</c:when>
					<c:otherwise>					
						${x_hotel.address}										
					</c:otherwise>
				</c:choose>
			</td>
		</tr>
		<tr>
			<td class="bluetext"><bean:message key="hotel.description" />:</td>
			<td colspan="3">	
				<c:choose>
					<c:when test="${modifyable}">
						<html:textarea property="description" cols="60" rows="2" />
					</c:when>
					<c:otherwise>					
						${x_hotel.description}										
					</c:otherwise>
				</c:choose>
			</td>
		</tr>
		<tr>
			<td class="bluetext"><bean:message key="hotel.specialService" />:</td>
			<td colspan="3">	
				<c:choose>
					<c:when test="${modifyable}">
						<html:textarea property="specialService" cols="60" rows="2" />
					</c:when>
					<c:otherwise>					
						${x_hotel.specialService}										
					</c:otherwise>
				</c:choose>
			</td>
		</tr>
		<tr>
			<td class="bluetext"><bean:message key="hotel.contact" />:</td>
			<td colspan="3">
				<c:choose>
				<c:when test="${modifyable}">
					<html:text property="contact" size="77" />
				</c:when>
				<c:otherwise>
					${x_hotel.contact}																
				</c:otherwise>	
				</c:choose>
			</td>
		</tr>
		<tr>
			<td class="bluetext"><bean:message key="hotel.email" />:</td>
			<td colspan="3">
				<c:choose>
				<c:when test="${modifyable}">			
					<html:text property="email" size="77" />
				</c:when>
				<c:otherwise>
					${x_hotel.email}																
				</c:otherwise>	
				</c:choose>
			</td>
		</tr>
		<tr>
			<td class="bluetext"><bean:message key="hotel.level" />:</td>
			<td>
				<c:choose>
					<c:when test="${modifyable}">
						<html:select property="level">							
							<c:if test="${sessionScope.LOGIN_USER.locale=='en'}">
								<html:options collection="x_hotelLevelList" property="enumCode" labelProperty="engShortDescription" />
							</c:if>
							<c:if test="${sessionScope.LOGIN_USER.locale!='en'}">
								<html:options collection="x_hotelLevelList" property="enumCode" labelProperty="chnShortDescription" />
							</c:if>
						</html:select>
					</c:when>
					<c:otherwise>	
						<c:if test="${sessionScope.LOGIN_USER.locale=='en'}">					
							${x_hotel.level.engDescription}	
						</c:if>
						<c:if test="${sessionScope.LOGIN_USER.locale!='en'}">
							${x_hotel.level.chnDescription}	
						</c:if>						
					</c:otherwise>
				</c:choose>
			</td>
			
			<td class="bluetext"><bean:message key="hotel.status" />:</td>
			<td>
				<c:choose>
					<c:when test="${modifyable}">
						<html:select property="enabled">
							<c:if test="${sessionScope.LOGIN_USER.locale=='en'}">
								<html:options collection="X_ENABLEDDISABLEDLIST"
									property="enumCode" labelProperty="engShortDescription" />
							</c:if>
							<c:if test="${sessionScope.LOGIN_USER.locale!='en'}">
								<html:options collection="X_ENABLEDDISABLEDLIST"
									property="enumCode" labelProperty="chnShortDescription" />
							</c:if>
						</html:select>
					</c:when>
					<c:otherwise>	
						<c:if test="${sessionScope.LOGIN_USER.locale=='en'}">					
							${x_hotel.enabled.engDescription}	
						</c:if>
						<c:if test="${sessionScope.LOGIN_USER.locale!='en'}">
							${x_hotel.enabled.chnDescription}	
						</c:if>						
					</c:otherwise>
				</c:choose>
			</td>
		</tr>
		<tr>
			<td colspan="4" align="right">
				<table>
					<tr>
						<c:if test="${modifyable}">
						<td>
							<html:submit>
								<bean:message key="all.save" />
							</html:submit>&nbsp;
						</td>
						</c:if>
						
						<c:if test="${x_version=='' && x_hotel.site != null && x_hotel.promoteStatus == x_hotel_promoteStatus_SITE}">
						<td id="promoteRequestButton" style="display:block">							
							<input type="button" value="<bean:message key="hotel.promote.request.title"/>" onclick="request(${x_hotel.id})">
						</td>
						</c:if>
						
						<c:if test="${x_version!='' && x_hotel.site != null && x_hotel.promoteStatus == x_hotel_promoteStatus_REQUEST}">
						<td id="promoteRequestButton" style="display:block">					
							<input type="button" value="<bean:message key="hotel.promote.response.title"/>" onclick="response(${x_hotel.id})">
						</td>
						</c:if>
						<td>
							<input type="button" value="<bean:message key="all.back"/>" onclick="window.location.href='listHotel<bean:write name="x_version"/>.do'">
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
	<hr width="100%" align="left">
	<table width="86%">
		<tr>
			<td align="left" width='10%' class='formtitle'><bean:message key="hotelContract.list.title" />
			</td>
		</tr>
	</table>
	<table width="70%">
		<tr>
			<td class="bluetext"><bean:message key="hotel.contractStartDate" />:</td>
			<td align="left">
				<c:choose>
					<c:when test="${modifyable}">
						<html:text property="contractStartDate" size="8" />
						<a onclick="event.cancelBubble=true;" 
							href="javascript:showCalendar('dimg1',false,'contractStartDate',null,null,'hotelForm')">
							<img align="absmiddle" border="0" id="dimg1" src="images/datebtn.gif"/>
						</a>
					</c:when>
					<c:otherwise>
						<bean:write name="x_hotel" property="contractStartDate" format="yyyy/MM/dd"/>
					</c:otherwise>
				</c:choose>
			</td>
			<td>~</td>
			<td class="bluetext"><bean:message key="hotel.contractExpireDate" />:</td>
			<td align="left">
				<c:choose>
					<c:when test="${modifyable}">
						<html:text property="contractExpireDate" size="8" />
						<a onclick="event.cancelBubble=true;" 
							href="javascript:showCalendar('dimg2',false,'contractExpireDate',null,null,'hotelForm')">
							<img align="absmiddle" border="0" id="dimg2" src="images/datebtn.gif"/>
						</a>
					</c:when>
					<c:otherwise>
						<bean:write name="x_hotel" property="contractExpireDate" format="yyyy/MM/dd"/>			
					</c:otherwise>
				</c:choose>
			</td>
		</tr>
	</table>
	<c:if test="${modifyable}">
	<table width=90% border=0 cellpadding=4 cellspacing=0>
		<tr>
			<td><input type="button"
				value="<bean:message key="hotelContract.new.title"/>"
				onclick="addContract()"></td>
		</tr>
	</table>
	</c:if>
	<table style="width:90%" class="data">
		<thead>
			<tr class="new_bg">
				<th width="40%">
				<div align="center"><bean:message key="hotelContract.description" /></div>
				</th>
				<th width="40%">
				<div align="center"><bean:message key="hotelContract.fileName" /></div>
				</th>
				
				<th width="20%">
				<div align="center"><bean:message key="hotelContract.uploadDate" /></div>
				</th>
			</tr>
		<thead>
		<tbody id="datatable">
			<logic:iterate id="X_OBJECT" name="x_contractList">
				<bean:define id="X_OBJECT" toScope="request" name="X_OBJECT" />
				<jsp:include page="../hotelContract/row.jsp" />
			</logic:iterate>
		</tbody>
	</table>

	<hr width="100%" align="left">
	<table>
		<tr>
			<td class="formtitle"><bean:message key="price.list.title" /></td>
		</tr>
	</table>
	<c:if test="${modifyable}">
	<table width=90% border=0 cellpadding=4 cellspacing=0>
		<tr>
			<td><input type="button"
				value="<bean:message key="price.new.title"/>"
				onclick="addPrice()"></td>
		</tr>
	</table>
	</c:if>
	<table style="width:90%" class="data">
		<thead>
			<tr class="new_bg">
				<th>
				<div align="center"><bean:message key="price.room"/></div>
				</th>
				<th>
				<div align="center"><bean:message key="price.price"/></div>
				</th>
				<th>
				<div align="center"><bean:message key="price.discount"/></div>
				</th>
				<th>
				<div align="center"><bean:message key="price.network"/></div>
				</th>
				<th>
				<div align="center"><bean:message key="price.breakfast"/></div>
				</th>
				<th>
				<div align="center"><bean:message key="price.description"/></div>
				</th>
				<th>
				<div align="center"><bean:message key="price.enabled"/></div>
				</th>				
			</tr>
		<thead>
		<tbody id="datatable2">
			<logic:iterate id="X_OBJECT" name="x_priceList">
				<bean:define id="X_OBJECT" toScope="request" name="X_OBJECT" />
				
				<jsp:include page="../price/row.jsp" />
			</logic:iterate>
			
			

		</tbody>

	</table>
	
</html:form>

<c:if test="${modifyable}">
<script type="text/javascript">
    var mapping=new Map();
	mapping.put("city_province_country_id","country");
	mapping.put("city_province_id","province");
	mapping.put("city_id","city");
    initCascadeSelect("config","data","hotelForm",mapping,true);
</script>
</c:if>

<script type="text/javascript">
    applyRowStyle(document.all('datatable'));
    applyRowStyle(document.all('datatable2'));
</script>
