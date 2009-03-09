<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>
<script language="javascript" src="includes/table.js"></script>
<script type="text/javascript">
<!--
	function add() {
		window.location.href='newTravelApplication${x_postfix}.do';
	}
	
	function view(id) {
		var url="viewTravelApplication${x_postfix}.do?id="+ id;
		window.location=url;
	}	
	
	function viewPurchase(id) {
		var url="viewTravelApplicationPurchase_requestor.do?id="+ id;
		window.location=url;
	}	

	function edit(id) {
		var url="editTravelApplication${x_postfix}.do?id="+ id;
		window.location=url;
	}
	
	
	
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
</script>

<html:javascript formName="travelApplicationQueryForm" staticJavascript="false"/>
<html:form action="listTravelApplication${x_postfix}" onsubmit="return validateForm(this)">
	<html:hidden property="order" />
	<html:hidden property="descend" />
	
	<input type="hidden" name="toCountry_id_value"
		value="<bean:write name="travelApplicationQueryForm" property="toCountry_id"/>" />
	<input type="hidden" name="toProvince_id_value"
		value="<bean:write name="travelApplicationQueryForm" property="toProvince_id"/>" />
	<input type="hidden" name="toCity_id_value"
		value="<bean:write name="travelApplicationQueryForm" property="toCity_id"/>" />	
		
	<table  border=0 cellpadding=4 cellspacing=0>
		<tr>
			<td colspan="16">
				<span style="color:green;font-weight:bold">
					<html:messages id="x_message" name="success" message="true">
						${x_message}
					</html:messages>
				</span>
			</td>
		</tr>
		
		<tr>
			<td class="bluetext" ><bean:message key="travelApplication.id" />
			:&nbsp;</td>
			<td><html:text property="id" size="16" maxlength="16"/></td>
			
			<td class="bluetext"><bean:message
				key="travelApplication.search.title" />:&nbsp;</td>
			<td><html:text property="title" size="16" maxlength="50"/></td>
			
			<logic:notEmpty name="x_version">
			<td class="bluetext"><bean:message
				key="travelApplication.search.requestor.name" />:&nbsp;</td>
			<td><html:text property="requestor_name" size="12"/></td>
			</logic:notEmpty>
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
			<td colspan="3">
				<html:text property="fromDate1" size="6" maxlength="10"/>
				<a onclick="event.cancelBubble=true;"
				href="javascript:showCalendar('dimg1',false,'fromDate1',null,null,'travelApplicationQueryForm')"><img
				align="absmiddle" border="0" id="dimg1" src="images/datebtn.gif" /></a>
				~
				<html:text property="fromDate2" size="6" maxlength="10"/>
				<a onclick="event.cancelBubble=true;"
				href="javascript:showCalendar('dimg2',false,'fromDate2',null,null,'travelApplicationQueryForm')"><img
				align="absmiddle" border="0" id="dimg2" src="images/datebtn.gif" /></a>
			</td>
		</tr>
		<tr>	
			<td class="bluetext"><bean:message key="travelApplication.toDate" />:</td>
			<td colspan="3">
				<html:text property="toDate1" size="6" maxlength="10"/>
				<a onclick="event.cancelBubble=true;"
				href="javascript:showCalendar('dimg3',false,'toDate1',null,null,'travelApplicationQueryForm')"><img
				align="absmiddle" border="0" id="dimg3" src="images/datebtn.gif" /></a>
				~
				<html:text property="toDate2" size="6" maxlength="10"/>
				<a onclick="event.cancelBubble=true;"
				href="javascript:showCalendar('dimg4',false,'toDate2',null,null,'travelApplicationQueryForm')"><img
				align="absmiddle" border="0" id="dimg4" src="images/datebtn.gif" /></a>
				
			</td>
		</tr>
		<tr>
			<td class="bluetext"><bean:message key="travelApplication.status" />:&nbsp;</td>
			<td>
				<html:select property="status">
					<html:option value="">
						<bean:message key="all.all"/>
					</html:option>
					<html:options collection="x_taStatusList" property="enumCode"
						labelProperty="${x_lang}ShortDescription" />
				</html:select>
			</td>
					
			<td class="bluetext"><bean:message key="travelApplication.urgent" />:</td>
			<td >
				<html:select property="urgent">
					<html:option value="">
						<bean:message key="all.all"/>
					</html:option>
					<html:options collection="x_urgentList" property="enumCode" labelProperty="${x_lang}ShortDescription" />
				</html:select>
			</td>
			<td class="bluetext"><bean:message key="travelApplication.bookStatus"/></td>
			<td>
				<html:select property="bookStatus">
					<html:option value="">
						<bean:message key="all.all"/>
					</html:option>
					<html:options collection="x_travelApplicationBookStatusList" property="enumCode" labelProperty="${x_lang}ShortDescription" />
				</html:select>
			</td>
		</tr>
		<tr>	
			<td  align="right" colspan="12">
				<html:submit>
					<bean:message key="all.query" />
				</html:submit>&nbsp;
				<input type="button" value="<bean:message key="all.new"/>" onClick="add()">
			</td>
		</tr>
	</table>
	<hr>
</html:form>




<page:form action="listTravelApplication${x_postfix}.do" method="post">
	<jsp:include page="../pageHead.jsp"/>
	<table class="data">
		<thead>
			<tr class="new_bg" height="32">
				<th ><page:order order="id" style="text-decoration:none">
					<bean:message key="travelApplication.id" />
					<page:desc>
						<img src="images/down.gif" border="0" />
					</page:desc>
					<page:asc>
						<img src="images/up.gif" border="0" />
					</page:asc>
				</page:order></th>


				<logic:notEmpty name="x_version">
					<!-- other version -->
					<!-- 
					
					<th >
						<page:order order="department" style="text-decoration:none">
							<bean:message key="travelApplication.department.id" />
							<page:desc>
								<img src="images/down.gif" border="0" />
							</page:desc>
							<page:asc>
								<img src="images/up.gif" border="0" />
							</page:asc>
						</page:order>
					</th>
					
					-->
					
					<th >
						<page:order order="requestor" style="text-decoration:none">
						
						<bean:message key="travelApplication.requestor" />
						
						<page:desc>
							<img src="images/down.gif" border="0" />
						</page:desc>
						<page:asc>
							<img src="images/up.gif" border="0" />
						</page:asc>
					</page:order></th>
				</logic:notEmpty>				

				<th >
					<page:order order="title" style="text-decoration:none">
						<bean:message key="travelApplication.title" />
						<page:desc>
							<img src="images/down.gif" border="0" />
						</page:desc>
						<page:asc>
							<img src="images/up.gif" border="0" />
						</page:asc>
					</page:order>				
				</th>
				
				<th >
					<page:order order="toCity_${x_lang}" style="text-decoration:none">
						<bean:message key="travelApplication.toCity.id" />
						<page:desc>
							<img src="images/down.gif" border="0" />
						</page:desc>
						<page:asc>
							<img src="images/up.gif" border="0" />
						</page:asc>
					</page:order>				
				</th>
				
				<th >
					<page:order order="fromDate" style="text-decoration:none">
						<bean:message key="travelApplication.fromDate" />
						<page:desc>
							<img src="images/down.gif" border="0" />
						</page:desc>
						<page:asc>
							<img src="images/up.gif" border="0" />
						</page:asc>
					</page:order>				
				</th>
				
				<th >
					<page:order order="toDate" style="text-decoration:none">
						<bean:message key="travelApplication.toDate" />
						<page:desc>
							<img src="images/down.gif" border="0" />
						</page:desc>
						<page:asc>
							<img src="images/up.gif" border="0" />
						</page:asc>
					</page:order>				
				</th>
				
				
				
				
				<!---
				<th >
					<page:order order="travellingMode" style="text-decoration:none">
					<bean:message key="travelApplication.travellingMode" />
					<page:desc>
						<img src="images/down.gif" border="0" />
					</page:desc>
					<page:asc>
						<img src="images/up.gif" border="0" />
					</page:asc>
				</page:order>								
				</th> 
				--->
				
				<th >
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
				
				<th >
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
				
				<th >
					<page:order order="status" style="text-decoration:none">
					<bean:message key="travelApplication.status" />
					<page:desc>
						<img src="images/down.gif" border="0" />
					</page:desc>
					<page:asc>
						<img src="images/up.gif" border="0" />
					</page:asc>
					</page:order>					
				</th>				
				
				<th >
					<page:order order="bookStatus" style="text-decoration:none">
					<bean:message key="travelApplication.bookStatus" />
					<page:desc>
						<img src="images/down.gif" border="0" />
					</page:desc>
					<page:asc>
						<img src="images/up.gif" border="0" />
					</page:asc>
					</page:order>					
				</th>			
				
				
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
    var mapping=new Map();
	mapping.put("toCountry_id","country");
	mapping.put("toProvince_id","province");
	mapping.put("toCity_id","city");
    initCascadeSelect("config","data","travelApplicationQueryForm",mapping,true);
</script>

<script type="text/javascript">
    applyRowStyle(document.all('datatable'));
</script>
