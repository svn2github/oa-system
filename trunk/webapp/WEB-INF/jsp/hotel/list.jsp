<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>
<script language="javascript" src="includes/table.js"></script>
<script language="javascript" src="includes/cascadeSelect.js"></script>
<script language="javascript" src="includes/common.js"></script>

<script type="text/javascript">
<!--
	function add() {
		var siteId=getSelectedSiteId();

		<logic:notEmpty name="x_version">
			if(siteId=="")
			{
				alert("<bean:message key="hotel.error.siteNewGlobal"/>");
				return;
			}
		</logic:notEmpty>
		<logic:empty name="x_version">
			siteId="";
		</logic:empty>
	
		var url="newHotel${x_version}.do?";
		if(siteId!="")
		{
			url+=("site_id="+siteId);
		}

		window.location.href=url;
	}

	function edit(id) {
		window.location.href="editHotel${x_version}.do?id="+id;
	}
	
	
	/*
	function request(id) {
		var url="requestPromoteHotel.do?id="+id;
		var v=dialogAction(url,'hotel.promote.request.title',400,180);
		if (v) {
			updateRow(document.all('r'+id),v);
		};
	}
	
	function response(id) {
		var url="responsePromoteHotel.do?id="+id;
		var v=dialogAction(url,'hotel.promote.response.title',400,180);
		if (v) {
			var table = document.all('datatable');
			deleteRow(document.all('r'+id));
		    applyRowStyle(table);
		};
	}
	*/
	
	
	
//-->
</script>

<xml id="data">
<data>
<country id="" name="<bean:message key="all.all"/>">
<province id="" name="<bean:message key="all.all"/>">
<city id="" name="<bean:message key="all.all"/>">
</city>
</province>
</country>
<logic:iterate id="x_country" name="x_countryList">
	<country id="${x_country.id}" name="<bean:write name="x_country" property="${x_lang}Name"/>">
	<province id="" name="<bean:message key="all.all"/>">
	<city id="" name="<bean:message key="all.all"/>">
	</city>
	</province>

	<logic:iterate id="x_p" name="x_country" property="enabledProvinceList">
		<province id="${x_p.id}" name="<bean:write name="x_p" property="${x_lang}Name"/>">
		<city id="" name="<bean:message key="all.all"/>">
		</city>

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
	function getSelectedSiteId()
	{
		return document.hotelQueryForm["site_id"].value;
	}
	function changeSite(combo)
	{
		combo.form.submit();
	}
</script>


<html:form action="listHotel${x_version}">
	<html:hidden property="order" />
	<html:hidden property="descend" />
	<input type="hidden" name="country_id_value"
		value="<bean:write name="hotelQueryForm" property="country_id"/>" />
	<input type="hidden" name="province_id_value"
		value="<bean:write name="hotelQueryForm" property="province_id"/>" />
	<input type="hidden" name="city_id_value"
		value="<bean:write name="hotelQueryForm" property="city_id"/>" />

	<table width="89%" border=0 cellpadding=4 cellspacing=0>
		<tr>
			<td class="bluetext"><bean:message key="hotel.site" />:</td>
			<td align="left" colspan="15">
			<html:select property="site_id" onchange="changeSite(this)">
				<option value=""><bean:message key="hotel.search.site.globallevel" /></option>
				<html:options collection="x_siteList" property="id"
					labelProperty="name" />
			</html:select></td>
		</tr>
		<tr>
			<td class="bluetext"><bean:message key="hotel.country" />:</td>
			<td align="left"><html:select property="country_id">
			</html:select></td>
			<td class="bluetext"><bean:message key="hotel.province" />:</td>
			<td align="left"><html:select property="province_id">
			</html:select></td>
			<td class="bluetext"><bean:message key="hotel.city" />:</td>
			<td align="left"><html:select property="city_id">
			</html:select></td>
		</tr>
		<tr>
			<td class="bluetext"><bean:message key="hotel.name" />:</td>
			<td><html:text property="name" size="14" /></td>
			
			<td class="bluetext"><bean:message key="hotel.promoteStatus"/>:</td>
			<td><html:select property="promoteStatus">
					<html:option value=""><bean:message key="all.all"/></html:option>
					<c:if test="${sessionScope.LOGIN_USER.locale=='en'}">
						<html:options collection="x_hotelPromoteStatusList"
							property="enumCode" labelProperty="engShortDescription" />
					</c:if>
					<c:if test="${sessionScope.LOGIN_USER.locale!='en'}">
						<html:options collection="x_hotelPromoteStatusList"
							property="enumCode" labelProperty="chnShortDescription" />
					</c:if>
			</html:select>
			</td>

			<td class="bluetext"><bean:message
				key="hotel.status" />:</td>
			<td><html:select property="enabled">
				<html:option value=""><bean:message key="all.all"/></html:option>
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
		<tr>
			<td colspan="10" align="right"><html:submit>
				<bean:message key="all.query" />
			</html:submit> &nbsp;&nbsp;&nbsp;&nbsp;<input name="new"
				type="button" value="<bean:message key="all.new"/>" onclick="add()"></td>
		</tr>
	</table>
</html:form>
<hr>
<page:form action="listHotel${x_version}.do" method="post">
	<jsp:include page="../pageHead.jsp"/>
	
	<c:if test="${sessionScope.LOGIN_USER.locale=='en'}">
		<c:set var="lang_postfix" value="_eng"/>
	</c:if>
	<c:if test="${sessionScope.LOGIN_USER.locale!='en'}">
		<c:set var="lang_postfix" value="_chn"/>
	</c:if>
	
	<table class="data">
		<thead>
			<tr class="new_bg">
				<th align="center" nowrap><page:order order="name"
					style="text-decoration:none">
					<bean:message key="hotel.name" />
					<page:desc>
						<img src="images/down.gif" border="0" />
					</page:desc>
					<page:asc>
						<img src="images/up.gif" border="0" />
					</page:asc>
				</page:order></th>			
				
				<th align="center" nowrap><page:order order="site"
					style="text-decoration:none">
					<bean:message key="hotel.site" />
					<page:desc>
						<img src="images/down.gif" border="0" />
					</page:desc>
					<page:asc>
						<img src="images/up.gif" border="0" />
					</page:asc>
				</page:order></th>
			
				<th align="center" nowrap><page:order order="country${lang_postfix}"
					style="text-decoration:none">
					<bean:message key="hotel.country" />
					<page:desc>
						<img src="images/down.gif" border="0" />
					</page:desc>
					<page:asc>
						<img src="images/up.gif" border="0" />
					</page:asc>
				</page:order></th>
				<th align="center" nowrap><page:order order="province${lang_postfix}"
					style="text-decoration:none">
					<bean:message key="hotel.province" />
					<page:desc>
						<img src="images/down.gif" border="0" />
					</page:desc>
					<page:asc>
						<img src="images/up.gif" border="0" />
					</page:asc>
				</page:order></th>
				<th align="center" nowrap><page:order order="city${lang_postfix}"
					style="text-decoration:none">
					<bean:message key="hotel.city" />
					<page:desc>
						<img src="images/down.gif" border="0" />
					</page:desc>
					<page:asc>
						<img src="images/up.gif" border="0" />
					</page:asc>
				</page:order></th>

				<th align="center" nowrap><page:order order="telephone"
					style="text-decoration:none">
					<bean:message key="hotel.telephone" />
					<page:desc>
						<img src="images/down.gif" border="0" />
					</page:desc>
					<page:asc>
						<img src="images/up.gif" border="0" />
					</page:asc>
				</page:order></th>
				
				<th align="center" nowrap><page:order order="level"
					style="text-decoration:none">
					<bean:message key="hotel.level" />
					<page:desc>
						<img src="images/down.gif" border="0" />
					</page:desc>
					<page:asc>
						<img src="images/up.gif" border="0" />
					</page:asc>
				</page:order></th>
				
				
				<th align="center" nowrap><page:order order="promoteStatus"
					style="text-decoration:none">
					<bean:message key="hotel.promoteStatus" />
					<page:desc>
						<img src="images/down.gif" border="0" />
					</page:desc>
					<page:asc>
						<img src="images/up.gif" border="0" />
					</page:asc>
				</page:order></th>
				
				<th align="center" nowrap><page:order order="enabled"
					style="text-decoration:none">
					<bean:message key="hotel.status" />
					<page:desc>
						<img src="images/down.gif" border="0" />
					</page:desc>
					<page:asc>
						<img src="images/up.gif" border="0" />
					</page:asc>
				</page:order></th>



			</tr>
		</thead>

		<tbody id="datatable">
			<logic:iterate id="X_OBJECT" name="X_RESULTLIST">
				<bean:define id="X_OBJECT" toScope="request" name="X_OBJECT" />
				<jsp:include page="row.jsp" />
			</logic:iterate>
		</tbody>
	</table>
	
	<jsp:include page="../pageTail.jsp"/>

</page:form>

<script type="text/javascript">
    applyRowStyle(document.all('datatable'));
    var mapping=new Map();
	mapping.put("country_id","country");
	mapping.put("province_id","province");
	mapping.put("city_id","city");
    initCascadeSelect("config","data","hotelQueryForm",mapping,true);
    
</script>

