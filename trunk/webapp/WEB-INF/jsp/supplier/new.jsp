<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>

<html:javascript formName="supplierForm" staticJavascript="false"/>



<xml id="data">
<data>
<logic:iterate id="x_country" name="x_countryList">
	<country id="${x_country.id}" name="<bean:write name="x_country" property="${x_lang}Name"/>">
		<city id="" name="">
		</city>
	<logic:iterate id="x_c" name="x_country" property="enabledCityList">
		<city id="${x_c.id}" name="<bean:write name="x_c" property="${x_lang}Name"/>">
		</city>
	</logic:iterate>
	</country>
</logic:iterate>
</data>
</xml>

<xml id="config">
<config>
<country>
<city>
</city>
</country>
</config>
</xml>



<script language="JavaScript">
	function validateForm(form)
	{
		

		if (!validateSupplierForm(form))
		{
			return false;
		}
		
		return true;
	}
</script>
<table width="100%">
<tr>
	<td >
		<html:errors/>
	</td>
</tr>
</table>		
<html:form action="/insertSupplier${x_version}" method="post" onsubmit="return validateForm(this)">
<html:hidden property="site_id"/>
<html:hidden property="promoteStatus" />
<html:hidden property="confirmed" />
<input type="hidden" name="country_id_value" value="<bean:write name="supplierForm" property="country_id"/>">
<input type="hidden" name="city_id_value"   value="<bean:write name="supplierForm" property="city_id"/>">

	<table width=90% border=0 cellpadding=4 cellspacing=0>
		<tr>
			<td class="bluetext"><bean:message key="supplier.code" />:</td>
			<td><html:text property="code" maxlength="8" /></td>
			<td class="bluetext"><bean:message key="supplier.name" />:</td>
			<td><html:text property="name" maxlength="50" /><span class="required">*</span></td>
		</tr>
		<tr>
			<td class="bluetext"><bean:message key="supplier.address1" />:</td>
			<td colspan="3"><html:text property="address1" size="76" maxlength="50"/><span class="required">*</span></td>
		</tr>
		<tr>
			<td class="bluetext"><bean:message key="supplier.address2" />:</td>
			<td colspan="3"><html:text property="address2" size="76" maxlength="50"/></td>
		</tr>
		<tr>
			<td class="bluetext"><bean:message key="supplier.address3" />:</td>
			<td colspan="3"><html:text property="address3" size="76" maxlength="50"/></td>
		</tr>
		<tr>
			<td class="bluetext"><bean:message key="supplier.country" />:</td>
			<td align="left"><html:select property="country_id">
			</html:select><span class="required">*</span></td>
			<td class="bluetext"><bean:message key="supplier.city" />:</td>
			<td align="left"><html:select property="city_id">
			</html:select></td>
		</tr>
		<tr>
			<td class="bluetext"><bean:message key="supplier.attention1" />:</td>
			<td><html:text property="attention1"  maxlength="24"/></td>	
			<td class="bluetext"><bean:message key="supplier.attention2" />:</td>
			<td><html:text property="attention2" maxlength="24" /></td>
		</tr>
		<tr>
			<td class="bluetext"><bean:message key="supplier.telephone1" />:</td>
			<td>
				<html:text property="telephone1"  maxlength="16"/>&nbsp;&nbsp;
				<span class="bluetext"><bean:message key="supplier.ext1" />:</span>
				<html:text property="ext1"  maxlength="4" size="2"/>
			</td>	
			<td class="bluetext"><bean:message key="supplier.telephone2" />:</td>
			<td>
				<html:text property="telephone2"  maxlength="16"/>&nbsp;&nbsp;
				<span class="bluetext"><bean:message key="supplier.ext2" />:</span>
				<html:text property="ext2"  maxlength="4" size="2"/>
			</td>	
		</tr>
		<tr>
			<td class="bluetext"><bean:message key="supplier.fax1" />:</td>
			<td><html:text property="fax1" maxlength="16" /></td>
			<td class="bluetext"><bean:message key="supplier.fax2" />:</td>
			<td><html:text property="fax2" maxlength="16" /></td>
		</tr>
		<tr>
			<td class="bluetext"><bean:message key="supplier.post" />:</td>
			<td><html:text property="post" maxlength="9"/></td>	
			<td class="bluetext"><bean:message key="supplier.contactor" />:</td>
			<td><html:text property="contact" maxlength="20"/></td>	
		</tr>
		<tr>
			
			<td class="bluetext"><bean:message key="supplier.currency" />:</td>
			<td>
				<html:select property="currency_code">
				<html:options collection="X_CURRENCYLIST" property="code" labelProperty="name" />
				</html:select>
			</td>
			<td class="bluetext"><bean:message key="supplier.airTicket"/>:</td>
			<td><html:select property="airTicket">
					<html:options collection="X_YESNOLIST" property="enumCode" labelProperty="${x_lang}ShortDescription" />
			</html:select></td>
		</tr>
		<tr>
			<td class="bluetext"><bean:message key="supplier.status"/>:</td>
			<td><html:select property="enabled">
					<html:options collection="X_ENABLEDDISABLEDLIST" property="enumCode" labelProperty="${x_lang}ShortDescription" />
			</html:select></td>
			<td class="bluetext"><bean:message key="supplier.lastModifyDate"/>:</td>
			<td><bean:write name="supplierForm" property="lastModifyDate" format="yyyy/MM/dd" /></td>
		</tr>
	</table>
	<hr width="90%" align="left">
<!--  	<table width="100%" border=0 cellpadding=0 cellspacing=0>
		<tr>
			<td class="bluetext"><bean:message key="supplier.contractStartDate" />:</td>
			<td>
			<table border=0 cellpadding=0 cellspacing=0>
			<tr>
				<td>
					<html:text property="contractStartDate" size="6"  maxlength="10" />
				</td>
				<td>
					<a onclick="event.cancelBubble=true;" href="javascript:showCalendar('dimg2',false,'contractStartDate',null,null,'supplierForm')"><IMG align="absMiddle" border="0" id="dimg2" src="images/datebtn.gif" ></A>
				</td>
			</tr>
			</table>
			</td>
			<td>~</td>
			<td class="bluetext"><bean:message key="supplier.contractExpireDate" />:</td>
			<td>
			<table border=0 cellpadding=0 cellspacing=0>
			<tr>
				<td>
					<html:text property="contractExpireDate" size="6"  maxlength="10" />
				</td>
				<td>
					<a onclick="event.cancelBubble=true;" href="javascript:showCalendar('dimg3',false,'contractExpireDate',null,null,'supplierForm')"><IMG align="absMiddle" border="0" id="dimg3" src="images/datebtn.gif" ></A>
				</td>
			</tr>
			</table>
			</td>
		</tr>
	</table>
//-->
	<table width="90%">
		<tr>
			<td colspan="2" align="right">
			<html:submit>
				<bean:message key="all.save" />
			</html:submit>
			<input type="button" value="<bean:message key="all.back" />" onclick="history.back()">
			</td>
		</tr>
	</table>
</html:form>

<script type="text/javascript">
    var mapping=new Map();
	mapping.put("country_id","country");
	mapping.put("city_id","city");
    initCascadeSelect("config","data","supplierForm",mapping,true);
    
</script>
