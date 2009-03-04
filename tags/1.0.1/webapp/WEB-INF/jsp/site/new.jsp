<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<script type="text/javascript">
<!--
	function updateImage(src) {
		if (src == '') {
			document.getElementById('previewImage').innerHTML = document.getElementById('orginalImage').innerHTML;
		} else {
			document.getElementById('previewImage').innerHTML = "<img src='" + src + "' width='121' height='35' align='absmiddle'>";
		}
	}
	
	function selectUser() {
		v = window.showModalDialog(
			'showDialog.do?title=site.selectManager.title&selectUser.do', 
			null, 'dialogWidth:650px;dialogHeight:500px;status:no;help:no;scroll:no');
		if (v) {
			siteForm.manager_id.value = v['id'];
			document.getElementById('manager_name').innerHTML = v['name'];
		};
	}
//-->
</script>

<xml id="data">
<data>
  <logic:iterate id="X_COUNTRY" name="X_COUNTRYLIST">
    <country id="${X_COUNTRY.id}" name="<bean:write name="X_COUNTRY" property="${x_lang}Name"/>">
    <logic:iterate id="X_PROVINCE" name="X_COUNTRY" property="enabledProvinceList">
      <province id="${X_PROVINCE.id}" name="<bean:write name="X_PROVINCE" property="${x_lang}Name"/>">
      <logic:iterate id="X_CITY" name="X_PROVINCE" property="enabledCityList">
        <city id="${X_CITY.id}" name="<bean:write name="X_CITY" property="${x_lang}Name"/>">
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

<html:javascript formName="siteForm" staticJavascript="false" />
<html:form action="insertSite.do" enctype="multipart/form-data" onsubmit="return validateSiteForm(this);">
<table width="100%" border="0" cellpadding="4" cellspacing="0">
<tr>
  <td class="bluetext"><bean:message key="site.id"/>:</td>
  <td><bean:message key="common.id.generateBySystem"/></td>
</tr>
<tr>
  <td class="bluetext"><bean:message key="site.activity"/>:</td>
  <td><html:text property="activity"/><span class="required">*</span></td>
</tr>
<tr>
  <td class="bluetext"><bean:message key="site.name"/>:</td>
  <td><html:text property="name"/><span class="required">*</span></td>
</tr>
<tr>
  <td class="bluetext"><bean:message key="country.id" />:</td>
  <td><html:select property="city_province_country_id"></html:select></td>
</tr>
<tr>
  <td class="bluetext"><bean:message key="province.id" />:</td>
  <td><html:select property="city_province_id"></html:select></td>
</tr>
<tr>
  <td class="bluetext"><bean:message key="site.city.id" />:</td>
  <td><html:select property="city_id"></html:select><span class="required">*</span></td>
</tr>
<!--
<tr>
  <td class="bluetext"><bean:message key="site.manager"/>:</td>
  <td>
    <html:hidden property="manager_id"/>
    <span id="manager_name"></span>
    &nbsp;<a href="javascript:selectUser()"><img src="images/select.gif" width="16" height="16" border="0"/></a>
  </td>
</tr>
-->
<tr>
  <td class="bluetext"><bean:message key="site.logo"/>:</td>
  <td><html:file property="logo" onchange="updateImage(this.value);"/></td>
</tr>
<tr>
  <td></td>
  <td>
    <div id="orginalImage" style="display:none"><bean:message key="site.logo.noPicture"/></div>
    <table border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td id="previewImage" valign="middle" align="center" style="width:121px;height:35px;border:solid 1px #ddd"><bean:message key="site.logo.noPicture"/></td>
      <td width="10"></td>
      <td><bean:message key="site.logo.suggest"/></td>
    </tr>
    </table>
  </td>
</tr>
<tr>
  <td class="bluetext"><bean:message key="site.baseCurrency"/>:</td>
  <td>
    <logic:empty name="X_CURRENCYLIST"><span class="warningMsg"><bean:message key="site.new.noAvailiableCurrency"/></span></logic:empty>
    <logic:notEmpty name="X_CURRENCYLIST">
      <html:select property="baseCurrency_code">
        <html:options collection="X_CURRENCYLIST" property="code" labelProperty="name"/>
      </html:select>
    </logic:notEmpty>
  </td>
</tr>
<tr>
  <td class="bluetext"><bean:message key="site.canRecharge"/>:</td>
  <td>
    <html:select property="canRecharge">
      <html:options collection="X_YESNOLIST" property="enumCode" labelProperty="${x_lang}ShortDescription"/>
    </html:select>
  </td>
</tr>
<tr>
  <td class="bluetext"><bean:message key="site.enabled"/>:</td>
  <td>
    <html:select property="enabled">
      <html:options collection="X_ENABLEDDISABLEDLIST" property="enumCode" labelProperty="${x_lang}ShortDescription"/>
    </html:select>
  </td>
</tr>
</table>
<hr/>
<table width="90%" border="0" cellpadding="4" cellspacing="0">
<tr>
  <td align="right">
  	<logic:notEmpty name="X_CURRENCYLIST"><html:submit><bean:message key="all.save"/></html:submit></logic:notEmpty>
  	<logic:empty name="X_CURRENCYLIST"><html:submit disabled="true"><bean:message key="all.save"/></html:submit></logic:empty>
  	<input type="button" value="<bean:message key="all.cancel"/>" onclick="window.parent.close();"/>
  </td>
</tr>
</table>
</html:form>
<script type="text/javascript">
    var mapping=new Map();
	mapping.put("city_province_country_id", "country");
	mapping.put("city_province_id", "province");
	mapping.put("city_id", "city");
    initCascadeSelect("config", "data", "siteForm", mapping, true);
</script>
