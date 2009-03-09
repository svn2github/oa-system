<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>


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


<script type="text/javascript">
<!--
	function doSelect(id) {
		window.parent.returnValue=id;
		window.parent.close();
	}
	
	function doClose() {
		window.parent.returnValue=false;
		window.parent.close();
	}
	
	function validateForm(form)
	{
		if(!validateTravelApplicationQueryForm(form)) return false;
		with(form)
		{
			trimControlValue(fromDate1);
			trimControlValue(fromDate2);
			trimControlValue(toDate1);
			trimControlValue(toDate2);
			if(fromDate1.value!="" && fromDate2.value!="")
			{
				if(fromDate1.value>fromDate2.value)
				{
					fromDate1.focus();
					alert("<bean:message key="travelApplication.query.FromDate1AfterDate2"/>");
					return false;
				}
			}	
			if(toDate1.value!="" && toDate2.value!="")
			{
				if(toDate1.value>toDate2.value)
				{
					toDate1.focus();				
					alert("<bean:message key="travelApplication.query.ToDate1AfterDate2"/>");
					return false;
				}
			}	
			
			
		}
	}
	
	function trimControlValue(c)
	{
		c.value=trim(c.value);
	}
	
//-->
</script>
<html:javascript formName="travelApplicationQueryForm" staticJavascript="false"/>
<html:form action="selectTravelApplication" onsubmit="return validateForm(this)">
	<html:hidden property="order" />
	<html:hidden property="descend" />
	<input type="hidden" name="toCountry_id_value"
		value="<bean:write name="travelApplicationQueryForm" property="toCountry_id"/>" />
	<input type="hidden" name="toProvince_id_value"
		value="<bean:write name="travelApplicationQueryForm" property="toProvince_id"/>" />
	<input type="hidden" name="toCity_id_value"
		value="<bean:write name="travelApplicationQueryForm" property="toCity_id"/>" />	
	<table  border=0 cellpadding=4 cellspacing=0 width="100%">
	
		<tr>
			<td class="bluetext" ><bean:message key="travelApplication.id" />:
			&nbsp;</td>
			<td><html:text property="id" size="10" /></td>
			<td class="bluetext"><bean:message
				key="travelApplication.search.title"/>:&nbsp;</td>
			<td><html:text property="title" /></td>
			<td></td><td></td>
		</tr>
		<tr>
			<td class="bluetext"><bean:message key="travelApplication.toCountry" />:</td>
			<td>
				<html:select property="toCountry_id">
				</html:select>
			</td>
			
			<td class="bluetext"><bean:message key="travelApplication.toProvince" />:</td>
			<td>
				<html:select property="toProvince_id">
				</html:select>
			</td>

			<td class="bluetext"><bean:message key="travelApplication.toCity" />:</td>
			<td>
				<html:select property="toCity_id">
				</html:select>
			</td>
		</tr>
		<tr>
			<td class="bluetext"><bean:message key="travelApplication.fromDate" />:</td>
			<td>
				<table  border=0 cellpadding=0 cellspacing=0>
				<tr>
				<td>
					<html:text property="fromDate1" size="6" maxlength="10"/>
				</td>
				<td>
					<a onclick="event.cancelBubble=true;"
					href="javascript:showCalendar('dimg1',false,'fromDate1',null,null,'travelApplicationQueryForm')"><img
					align="absmiddle" border="0" id="dimg1" src="images/datebtn.gif" /></a>
				</td>
				<td width="10" align="center">~</td>
				<td>
				<html:text property="fromDate2" size="6" maxlength="10"/>
				</td>
				<td>
					<a onclick="event.cancelBubble=true;"
					href="javascript:showCalendar('dimg2',false,'fromDate2',null,null,'travelApplicationQueryForm')"><img
					align="absmiddle" border="0" id="dimg2" src="images/datebtn.gif" /></a>
				</td>
				</tr>
				</table>
			</td>
			<td class="bluetext"><bean:message key="travelApplication.toDate" />:</td>
			<td>
				<table  border=0 cellpadding=0 cellspacing=0>
				<tr>
				<td>
					<html:text property="toDate1" size="6" maxlength="10"/>
				</td>
				<td>
					<a onclick="event.cancelBubble=true;"
					href="javascript:showCalendar('dimg3',false,'toDate1',null,null,'travelApplicationQueryForm')"><img
					align="absmiddle" border="0" id="dimg3" src="images/datebtn.gif" /></a>
				</td>
				<td width="10" align="center">~</td>
				<td>
					<html:text property="toDate2" size="6" maxlength="10"/>
				</td>
				<td>
					<a onclick="event.cancelBubble=true;"
					href="javascript:showCalendar('dimg4',false,'toDate2',null,null,'travelApplicationQueryForm')"><img
					align="absmiddle" border="0" id="dimg4" src="images/datebtn.gif" /></a>
				</td>
				</tr>
				</table>
			</td>
			<td class="bluetext"><bean:message key="travelApplication.urgent" />:</td>
			<td >
				<html:select property="urgent">
					<html:option value=""><bean:message key="all.all"/></html:option>
					<c:if test="${sessionScope.LOGIN_USER.locale=='en'}"><html:options collection="x_urgentList" property="enumCode" labelProperty="engShortDescription" /></c:if>
					<c:if test="${sessionScope.LOGIN_USER.locale!='en'}"><html:options collection="x_urgentList" property="enumCode" labelProperty="chnShortDescription" /></c:if>
				</html:select>
			</td>
		</tr>
		<tr>
			<td align="right" colspan="6">
				<html:submit><bean:message key="all.query"/></html:submit>
				<input type="button" value="<bean:message key="all.cancel"/>" onclick="javascript:doClose();">
			</td>
		</tr>
	</table>
	<hr>
</html:form>




<page:form action="selectTravelApplication.do" method="post">
	<jsp:include page="../pageHead.jsp"/>
	<table class="data">
		<thead>
			<tr class="new_bg">
				<th width="15%"><page:order order="id" style="text-decoration:none">
					<bean:message key="travelApplication.id" />
					<page:desc>
						<img src="images/down.gif" border="0" />
					</page:desc>
					<page:asc>
						<img src="images/up.gif" border="0" />
					</page:asc>
				</page:order></th>
				<th width="20%"><page:order order="title" style="text-decoration:none">
						<bean:message key="travelApplication.title" />
						<page:desc>
							<img src="images/down.gif" border="0" />
						</page:desc>
						<page:asc>
							<img src="images/up.gif" border="0" />
						</page:asc>
					</page:order>	</th>
				<th width="20%"><page:order order="toCity_${x_lang}" style="text-decoration:none">
						<bean:message key="travelApplication.toCity.id" />
						<page:desc>
							<img src="images/down.gif" border="0" />
						</page:desc>
						<page:asc>
							<img src="images/up.gif" border="0" />
						</page:asc>
					</page:order>	</th>
				<th width="10%"><bean:message key="travelApplication.fromDate" /></th>
				<th width="10%"><bean:message key="travelApplication.toDate" /></th>
					
				
				<th width="10%" >
					<page:order order="urgent" style="text-decoration:none">
					<bean:message key="travelApplication.urgent" />
					<page:desc>
						<img src="images/down.gif" border="0" />
					</page:desc>
					<page:asc>
						<img src="images/up.gif" border="0" />
					</page:asc>
					</page:order>					
				</th>
				<th width="10%">
					<page:order order="requestDate" style="text-decoration:none">
					<bean:message key="travelApplication.requestDate" />
					<page:desc>
						<img src="images/down.gif" border="0" />
					</page:desc>
					<page:asc>
						<img src="images/up.gif" border="0" />
					</page:asc>
				</page:order>								
				</th>
				<th width="5%"></th>
			</tr>
		</thead>

		<tbody id="datatable">
			<logic:iterate id="X_OBJECT" name="X_RESULTLIST">
				<tr id="r${X_OBJECT.id}">
				  <td>
					  ${X_OBJECT.id}
				  </td>
				  <td>${X_OBJECT.title}</td>
				  <td>
					<bean:write name="X_OBJECT" property="toCity.${x_lang}Name"/>
				  </td>
				  <td>
				  	<bean:write name="X_OBJECT" property="fromDate" format="yyyy/MM/dd"/>
				  </td>
				  <td>
				  	<bean:write name="X_OBJECT" property="toDate" format="yyyy/MM/dd"/>
				  </td>
				  <td>
					<span style="color:${X_OBJECT.urgent.color}"> 
						<bean:write name="X_OBJECT" property="urgent.${x_lang}ShortDescription"/>
					</span>
				  </td>
				  <td>
					  <bean:write name="X_OBJECT" property="requestDate" format="yyyy/MM/dd" />
				  </td>
				  <td><a href='javascript:doSelect("${X_OBJECT.id}")'><bean:message key="all.select"/></a></td>
				</tr>
			</logic:iterate>
		</tbody>
	</table>
	
</page:form>
<table width="100%">
<tr><td align="right"><input type="button" value="<bean:message key="all.close" />" onclick="window.parent.close();" /></td></tr>
</table>
<script type="text/javascript">
    var mapping=new Map();
	mapping.put("toCountry_id","country");
	mapping.put("toProvince_id","province");
	mapping.put("toCity_id","city");
    initCascadeSelect("config","data","travelApplicationQueryForm",mapping,true);
    applyRowStyle(document.all('datatable'));
</script>
