<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>


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



<script>
	function price_changed() {
		var o=document.airTicketBookForm.price;
		var price = parseFloat(o.value);
		if (isNaN(price)) price = 0;
		totalAmountChanged(price);
	}
	function validateForm(form)
	{
		if(!validateAirTicketBookForm(form))
		{
			return false;
		}
		
		if(form.departTime.value>=form.arriveTime.value)
		{
			form.arriveTime.focus();
			alert("<bean:message key="travelApplication.purchase.departTimeAfterArriveTime"/>");
			return false;
		}
		
		<c:if test="${x_canRecharge}">
		//check recharge list
		if (!checkRecharge()) {
			return false;
		}
		</c:if>
		return true;
	}
</script>
<html:javascript formName="airTicketBookForm" staticJavascript="false" />
<html:form action="/updateBookAirTicket" onsubmit="return validateForm(this)">
	<html:hidden property="travelApplication_id"/>
	<html:hidden property="id"/>
	<input type="hidden" name="fromCity_province_country_id_value" value="<bean:write name="airTicketBookForm" property="fromCity_province_country_id"/>">
	<input type="hidden" name="fromCity_province_id_value"  value="<bean:write name="airTicketBookForm" property="fromCity_province_id"/>">
	<input type="hidden" name="fromCity_id_value"   value="<bean:write name="airTicketBookForm" property="fromCity_id"/>">
	
	<input type="hidden" name="toCity_province_country_id_value" value="<bean:write name="airTicketBookForm" property="toCity_province_country_id"/>">
	<input type="hidden" name="toCity_province_id_value"  value="<bean:write name="airTicketBookForm" property="toCity_province_id"/>">
	<input type="hidden" name="toCity_id_value"   value="<bean:write name="airTicketBookForm" property="toCity_id"/>">
	
	<table width=90% border=0 cellpadding=4 cellspacing=0>
		<tr>
			<td class="bluetext"><bean:message key="airTicket.supplier.id" />:</td>
			<td>
				<html:select property="supplier_id">
					<html:options collection="x_supplierList" property="id"
						labelProperty="name" />
				</html:select><span class="required">*</span>
			</td>
		</tr>	
		<logic:notEmpty name="x_purchaseTypeList">
		<tr>
			<td class="bluetext"><bean:message key="airTicket.purchaseType.code" />:</td>
			<td>
			<html:select property="purchaseType_code">
				<html:options collection="x_purchaseTypeList" property="code"
					labelProperty="description" />
			</html:select><span class="required">*</span>
			</td>
		</tr>			
		</logic:notEmpty>
		<tr>
			<td class="bluetext"><bean:message key="airTicket.flightNo" />:</td>
			<td><html:text property="flightNo" size="6"/><span class="required">*</span></td>
		</tr>		
		<tr>
			<td class="bluetext"><bean:message key="airTicket.class" />:</td>
			<td>
				<html:select property="flightClass">
					<html:options collection="x_flightClassList" property="enumCode"
						labelProperty="${x_lang}ShortDescription" />
				</html:select><span class="required">*</span>
			</td>
		</tr>			
		<tr>
			<td class="bluetext"><bean:message key="airTicket.from" />:</td>
			<td>
				<html:select property="fromCity_province_country_id" >
				</html:select><span class="required">*</span>
				<html:select property="fromCity_province_id" >
				</html:select><span class="required">*</span>
				<html:select property="fromCity_id" >
				</html:select><span class="required">*</span>
			</td>
		</tr>			
		<tr>
			<td class="bluetext"><bean:message key="airTicket.to" />:</td>
			<td>
				<html:select property="toCity_province_country_id" >
				</html:select><span class="required">*</span>
				<html:select property="toCity_province_id" >
				</html:select><span class="required">*</span>
				<html:select property="toCity_id" >
				</html:select><span class="required">*</span>
			</td>
		</tr>			
		<tr>
			<td class="bluetext"><bean:message key="airTicket.depart" />:</td>
			<td><html:text property="departTime"  maxlength="16" size="12"/><span class="required">*</span></td>
		</tr>			
		<tr>
			<td class="bluetext"><bean:message key="airTicket.arrive" />:</td>
			<td><html:text property="arriveTime"  maxlength="16" size="12"/><span class="required">*</span></td>
		</tr>			
		<tr>
			<td class="bluetext"><bean:message key="airTicket.currency.code" />:</td>
			<td>
				<html:select property="exchangeRate_id">
					<html:options collection="x_exchangeRateList" property="id"	labelProperty="currency.name" />
				</html:select><span class="required">*</span>
			</td>
		</tr>			
		<tr>
			<td class="bluetext"><bean:message key="airTicket.price" />:</td>
			<td> <html:text property="price" size="4" onkeyup="price_changed()"/><span class="required">*</span></td>
		</tr>	
		<script language="javascript">
			var rechargeFormName = "airTicketBookForm";
	  	</script>		
		<tr>
			<td colspan="2" >
				<jsp:include page="../recharge/edit.jsp"/>
			</td>
		</tr>

		<tr>
			<td colspan="2" align="right"><html:submit>
				<bean:message key="all.save" />
			</html:submit></td>
		</tr>
	</table>
	
	
</html:form>

<script type="text/javascript">
    var mapping=new Map();
	mapping.put("fromCity_province_country_id","country");
	mapping.put("fromCity_province_id","province");
	mapping.put("fromCity_id","city");
	
	
    initCascadeSelect("config","data","airTicketBookForm",mapping,true);
    
    mapping=new Map();
    mapping.put("toCity_province_country_id","country");
	mapping.put("toCity_province_id","province");
	mapping.put("toCity_id","city");
	initCascadeSelect("config","data","airTicketBookForm",mapping,true);
	
	price_changed();
</script>

	
