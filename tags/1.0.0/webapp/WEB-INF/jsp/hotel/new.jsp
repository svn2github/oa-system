<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>
<html:javascript formName="hotelForm" staticJavascript="false" />


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

<html:form action="/insertHotel${x_version}" method="post"
	onsubmit="return validateHotelForm(this)">
	<input type="hidden" name="city_province_country_id_value"
		value="<bean:write name="hotelForm" property="city_province_country_id"/>">
	<input type="hidden" name="city_province_id_value"
		value="<bean:write name="hotelForm" property="city_province_id"/>">
	<input type="hidden" name="city_id_value"
		value="<bean:write name="hotelForm" property="city_id"/>">
	<html:hidden property="promoteStatus" />
	<html:hidden property="site_id" />
	<table width=90% border=0 cellpadding=4 cellspacing=0>
		<tr>
			<td width="20%" class="bluetext"><bean:message key="hotel.id" />:</td>
			<td width="30%">(<bean:message key="common.id.generateBySystem" />)</td>

			<td width="20%" class="bluetext"><bean:message key="hotel.name" />:</td>
			<td width="30%"><html:text property="name" /><span class="required">*</span></td>
		</tr>
		<tr>
			<td class="bluetext"><bean:message key="country.id" />:</td>
			<td align="left"><html:select property="city_province_country_id">
			</html:select></td>
			
			<td class="bluetext"><bean:message key="hotel.currency" />:</td>
			<td><html:select property="currency_code">
				<html:options collection="x_currencyList" property="code"
					labelProperty="name" />
			</html:select></td>
		</tr>
		<tr>
			<td class="bluetext"><bean:message key="province.id" />:</td>
			<td align="left"><html:select property="city_province_id">
			</html:select></td>
			
			<td class="bluetext"><bean:message key="hotel.telephone" />:</td>
			<td><html:text property="telephone" /></td>
		</tr>
		<tr>
			<td class="bluetext"><bean:message key="hotel.city.id" />:</td>
			<td align="left"><html:select property="city_id">
			</html:select><span class="required">*</span></td>
			
			<td class="bluetext"><bean:message key="hotel.fax" />:</td>
			<td><html:text property="fax" /></td>
		</tr>
		<tr>
			<td class="bluetext"><bean:message key="hotel.address" />:</td>
			<td colspan="3"><html:text property="address" size="77"/></td>
		</tr>
		<tr>
			<td class="bluetext"><bean:message key="hotel.description" />:</td>
			<td colspan="3"><html:textarea property="description" cols="60" rows="2" /></td>
		</tr>
		<tr>
			<td class="bluetext"><bean:message key="hotel.specialService" />:</td>
			<td colspan="3"><html:textarea property="specialService" cols="60" rows="2" /></td>
		</tr>
		<tr>
			<td class="bluetext"><bean:message key="hotel.contact" />:</td>
			<td colspan="3"><html:text property="contact" size="77" /></td>
		</tr>
		<tr>
			<td class="bluetext"><bean:message key="hotel.email" />:</td>
			<td colspan="3"><html:text property="email" size="77" /></td>
		</tr>
		
		<tr>
			<td class="bluetext"><bean:message key="hotel.level" />:</td>
			<td><html:select property="level">
				<html:options collection="x_hotelLevelList" property="enumCode"
					labelProperty="label" />
			</html:select></td>
			<td class="bluetext"><bean:message key="hotel.status" />:</td>
			<td><html:select property="enabled">
				<c:if test="${sessionScope.LOGIN_USER.locale=='en'}">
					<html:options collection="X_ENABLEDDISABLEDLIST"
						property="enumCode" labelProperty="engShortDescription" />
				</c:if>
				<c:if test="${sessionScope.LOGIN_USER.locale!='en'}">
					<html:options collection="X_ENABLEDDISABLEDLIST"
						property="enumCode" labelProperty="chnShortDescription" />
				</c:if>
			</html:select></td>
		</tr>
	</table>
	<hr width="100%" align="left">
	<!--  
	<table width="60%">
		<tr>
			<td class="bluetext"><bean:message key="hotel.contractStartDate" />:</td>
			<td><html:text property="contractStartDate" size="10" /></td>
			<td>~</td>
			<td class="bluetext"><bean:message key="hotel.contractExpireDate" />:</td>
			<td><html:text property="contractExpireDate" size="10" /></td>
		</tr>
	</table>
	-->
	<table width="90%">
		<tr>
			<td colspan="2" align="right"><html:submit>
				<bean:message key="all.save" />
			</html:submit>&nbsp;
			<input type="button" value="<bean:message key="all.back"/>" onclick="window.location.href='listHotel<bean:write name="x_version"/>.do'"></td>
		</tr>
	</table>
</html:form>

<script type="text/javascript">
    var mapping=new Map();
	mapping.put("city_province_country_id","country");
	mapping.put("city_province_id","province");
	mapping.put("city_id","city");
    initCascadeSelect("config","data","hotelForm",mapping,true);
    
</script>
