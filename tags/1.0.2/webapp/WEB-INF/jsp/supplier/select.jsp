<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>
<script type="text/javascript">
<!--
	function doSelect(id, name) {
		window.parent.returnValue = id + "," + name;
		window.parent.close();
	}
	
//-->
</script>

<xml id="data">
<data>
<logic:iterate id="x_country" name="x_countryList">
	<country id="${x_country.id}" name="<bean:write name="x_country" property="${x_lang}Name"/>">
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
<table width=100% border=1 cellpadding=4 cellspacing=0 bgcolor=ffffff>
	<tr>
		<td bgcolor=f0f0f0 width=100% colspan=2 valign=top>    
			<table width=100% cellpadding=0 cellspacing=0>
	      <tr>
	        <td bgcolor=f0f0f0 width=80% valign=top>
	        	<h3 class="formtitle"><bean:message key="supplier.selectSupplier.title" /> </h3>
	        </td>
	       	<td valign=top align ="right"></td>
	      </tr>
	    </table>
	  </td>
	</tr>
</table>
<html:form action="selectSupplier">
	<html:hidden property="order" />
	<html:hidden property="descend" />
	<html:hidden property="site_id" />
	<html:hidden property="includeGlobal" value="true"/>
	<html:hidden property="confirmed"/>
	<input type="hidden" name="country_id_value"
		value="<bean:write name="supplierQueryForm" property="country_id"/>" />
	<input type="hidden" name="city_id_value"
		value="<bean:write name="supplierQueryForm" property="city_id"/>" />

	<table width=100% border=0 cellpadding=4 cellspacing=0>
		<tr>
			<!-- id -->
			<td class="bluetext"><bean:message key="supplier.code" />:</td>
			<td><html:text property="code" size="8"/></td>
			<td class="bluetext"><bean:message key="supplier.description" />:</td>
			<td><html:text property="name" /></td>
		</tr>
		<tr>
			<td class="bluetext"><bean:message key="supplier.country" />:</td>
			<td>
				<html:select property="country_id"></html:select>
			</td>
			<td class="bluetext"><bean:message key="supplier.city" />:</td>
			<td>
				<html:select property="city_id"></html:select>
			</td>
		</tr>
		<tr>
			<td align="right" colspan="4">
				<html:hidden property="includeDisabled" value="false"/> 
				<html:submit><bean:message key="all.query"/></html:submit>
			</td>
		</tr>
	</table>
</html:form>
<hr />
<page:form action="selectSupplier.do" method="post">
	<jsp:include page="../pageHead.jsp"/>
	<table class="data">
		<thead>
			<tr class="new_bg">
				<th width="15%"><page:order order="code" style="text-decoration:none">
					<bean:message key="supplier.code" />
					<page:desc>
						<img src="images/down.gif" border="0" />
					</page:desc>
					<page:asc>
						<img src="images/up.gif" border="0" />
					</page:asc>
				</page:order></th>

				<th width="25%"><page:order order="name"
					style="text-decoration:none">
					<bean:message key="supplier.name" />
					<page:desc>
						<img src="images/down.gif" border="0" />
					</page:desc>
					<page:asc>
						<img src="images/up.gif" border="0" />
					</page:asc>
				</page:order></th>
				<th width="15%">
				<c:if test="${sessionScope.LOGIN_USER.locale=='en'}">
					<page:order order="cityEngName" style="text-decoration:none">
						<bean:message key="supplier.city" />
						<page:desc>
							<img src="images/down.gif" border="0" />
						</page:desc>
						<page:asc>
							<img src="images/up.gif" border="0" />
						</page:asc>
					</page:order>
				</c:if>
				<c:if test="${sessionScope.LOGIN_USER.locale!='en'}">
					<page:order order="cityChnName" style="text-decoration:none">
						<bean:message key="supplier.city" />
						<page:desc>
							<img src="images/down.gif" border="0" />
						</page:desc>
						<page:asc>
							<img src="images/up.gif" border="0" />
						</page:asc>
					</page:order>
				</c:if>
				</th>
				<th width="15%"><bean:message key="supplier.country" /></th>
				<th width="10%"><bean:message key="supplier.telephone" /></th>
				<th width="15%"><bean:message key="supplier.contactor" /></th>
				<th width="5%">&nbsp;</th>
			</tr>
		</thead>

		<tbody id="datatable">
			<logic:iterate id="X_OBJECT" name="X_RESULTLIST">
				<tr id="r${X_OBJECT.id}">
				  <td>
					  ${X_OBJECT.code}
				  </td>
				  <td>${X_OBJECT.name}</td>
				  <td>
					  <c:if test="${sessionScope.LOGIN_USER.locale=='en'}">${X_OBJECT.city.engName}</c:if>
				      <c:if test="${sessionScope.LOGIN_USER.locale!='en'}">${X_OBJECT.city.chnName}</c:if>
				  </td>
				  <td>
					  <c:if test="${sessionScope.LOGIN_USER.locale=='en'}">${X_OBJECT.city.province.country.engName}</c:if>
				      <c:if test="${sessionScope.LOGIN_USER.locale!='en'}">${X_OBJECT.city.province.country.chnName}</c:if>
				  </td>
				  <td>${X_OBJECT.telephone1}</td>
				  <td>${X_OBJECT.contact}</td>
				  <td><a href='javascript:doSelect(${X_OBJECT.id}, "${X_OBJECT.name}")'><bean:message key="all.select"/></a></td>
				</tr>
			</logic:iterate>
		</tbody>
	</table>
</page:form>
<table width="100%">
<tr><td align="right"><input type="button" value="<bean:message key="all.close" />" onclick="window.parent.close();" /></td></tr>
</table>
<script type="text/javascript">
    applyRowStyle(document.all('datatable'));
    var mapping=new Map();
	mapping.put("country_id","country");
	mapping.put("city_id","city");
    initCascadeSelect("config","data","supplierQueryForm",mapping,true);
</script>

