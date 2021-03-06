<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>
<%@ page import="com.aof.model.metadata.*"%>


<script>
	function comboChange()
	{
		with(document.travelApplicationForm)
		{
			combo_change.value="true";
			action="newTravelApplication_other.do";
			submit();
		}
	}
	
	var old_department_id;
	old_department_id="<bean:write name="travelApplicationForm" property="department_id"/>";
	
	function comboDepartmentChange()
	{
		with(document.travelApplicationForm)
		{
			var selectedOption=department_id.options(department_id.selectedIndex);
			if(selectedOption.style.color=="gray")
			{
				alert('<bean:message key="travelApplication.department.noPower"/>');
				department_id.value=old_department_id;
			}
			else
			{
				comboChange();
			}
		}
	}
</script>

<jsp:include page="edit_common1.jsp"/>
<c:if test="${sessionScope.LOGIN_USER.locale!='en'}">
	<c:set var="labelName" value="chnName" />
</c:if>
<c:if test="${sessionScope.LOGIN_USER.locale=='en'}">
	<c:set var="labelName" value="engName" />
</c:if>
<c:if test="${sessionScope.LOGIN_USER.locale!='en'}">
	<c:set var="metaLabelName" value="chnDescription" />
</c:if>
<c:if test="${sessionScope.LOGIN_USER.locale=='en'}">
	<c:set var="metaLabelName" value="engDescription" />	
</c:if>
<html:form action="/insertTravelApplication_other" focus="department_site_id" onsubmit="return validateForm(this)">
	<input type="hidden" name="draft" value="false"/>
	<input type="hidden" value="" name="combo_change" />	

	<table width="90%">
		<tr>
			<td >
				<html:errors/>
			</td>
		</tr>
		<tr>
			<td>
				<div class="message">
					<html:messages id="x_message" message="true">
						${x_message}<br>
					</html:messages>
				</div>
			</td>
		</tr>
	</table>

	<table width="100%">
		<tr>
			<td width="20%" class="bluetext"><bean:message key="travelGroup.site" />:</td>
			<td width="80%" colspan="3" class="bluetext"><html:select property="department_site_id" onchange="comboChange()">
				<html:options collection="x_siteList" property="id"
					labelProperty="name" />
			</html:select><span class="required">*</span></td>
		</tr>
		<tr>
			<td width="20%" class="bluetext"><bean:message key="travelGroup.department" />:</td>
			<td width="80%" colspan="3" class="bluetext"><html:select property="department_id" onchange="comboDepartmentChange()">
				<logic:iterate name="x_departmentList" id="x_department">
					<c:set var="x_style" value=""/>				
					<c:if test="${!x_department.granted}">				
						<c:set var="x_style" value="color:gray"/>				
					</c:if>
					<html:option style="${x_style}" value="${x_department.id}">${x_department.indentName}</html:option>
				</logic:iterate>
			</html:select><span class="required">*</span></td>
		</tr>
		<tr>
			<td width="20%" class="bluetext"><bean:message key="travelApplication.requestor" />:</td>
			<td width="30%" class="bluetext"><html:select property="requestor_id" onchange="comboDepartmentChange()" >
				<html:options collection="x_requestorList"
					property="id" labelProperty="name" />
			</html:select><span class="required">*</span></td>
			<td width="20%" class="bluetext"><bean:message key="travelApplication.requestDate" />:</td>
			<td class="bluetext" width="30%"></td>
		</tr>
		<tr>
			<td width="20%" class="bluetext"><bean:message key="travelApplication.creator" />:</td>
			<td class="bluetext" width="30%">${sessionScope.LOGIN_USER.name}</td>
			<td width="20%" class="bluetext"><bean:message key="travelApplication.createDate" />:</td>
			<td class="bluetext" width="30%"></td>
		</tr>
	</table>
	<hr align="left" width="100%">
	<table width=100%>
		<tr>
			<td width="20%" class='bluetext'><bean:message
				key="travelApplication.id" />:&nbsp;</td>
			<td width="80%" colspan="3">(<bean:message
				key="common.id.generateBySystem" />)</td>
		</tr>
		<tr>
			<td class="bluetext"  width="20%"><bean:message key="travelApplication.title" />:
			</td>
			<td width="80%" colspan="3"><html:text property="title" size="60"/><span class="required">*</span>
			</td>
		</tr>
		<tr>
			<td class="bluetext" width="20%"><bean:message
				key="travelApplication.description" />:</td>
			<td width="80%" colspan="3"><html:textarea property="description" rows="2" cols="60"></html:textarea></td>
		</tr>
		<tr>
			<td class="bluetext" width="20%"><bean:message
				key="travelApplication.urgent" /> :</td>
			<td width="30%"><html:select property="urgent">
				<html:options collection="x_urgentList" property="enumCode"
					labelProperty="${metaLabelName}" />
			</html:select></td>

			<td class='bluetext'  width="20%"><bean:message key="travelApplication.status" />:</td>
			<td width="30%"><font color="<%=TravelApplicationStatus.DRAFT.getColor()%>"> <c:if
				test="${sessionScope.LOGIN_USER.locale!='en'}">
				<%=TravelApplicationStatus.DRAFT.getChnDescription()%>
			</c:if> <c:if test="${sessionScope.LOGIN_USER.locale=='en'}">
				<%=TravelApplicationStatus.DRAFT.getEngDescription()%>
			</c:if> </font></td>
		</tr>
		<tr>
			<td class="bluetext" width="20%"><bean:message key="travelApplication.fee" />:</td>
			<td colspan=4><html:text property="fee" size="5"></html:text><span class="required">*</span></td>			
		</tr>
	</table>


	<hr align="left" width="100%">
	<table width="100%">
		<tr>
			<td width="20%"></td>
			<td width="40%" align="left" class="bluetext"><h3><bean:message key="travelApplication.from" /></h3></td>
			<td width="40%" align="left" class="bluetext"><h3><bean:message key="travelApplication.to" /></h3></td>
		</tr>
		<tr>
			<td class="bluetext"><bean:message key="travelApplication.country" />:</td>
			<td><html:select property="fromCity_province_country_id" onchange="comboChange()">
				<html:options collection="x_countryList" property="id"
					labelProperty="${labelName}" />
			</html:select></td>
			<td><html:select property="toCity_province_country_id" onchange="comboChange()">
				<html:options collection="x_countryList" property="id"
					labelProperty="${labelName}" />
			</html:select></td>
		</tr>
		<tr>
			<td class="bluetext"><bean:message key="travelApplication.province" />:</td>
			<td><html:select property="fromCity_province_id" onchange="comboChange()">
				<html:options collection="x_fromProvinceList" property="id"
					labelProperty="${labelName}" />
			</html:select></td>
			<td><html:select property="toCity_province_id" onchange="comboChange()">
				<html:options collection="x_toProvinceList" property="id"
					labelProperty="${labelName}" />
			</html:select></td>
		</tr>
		<tr>
			<td class="bluetext"><bean:message key="travelApplication.city" />:</td>
			<td><html:select property="fromCity_id" onchange="comboChange()">
				<html:options collection="x_fromCityList" property="id"
					labelProperty="${labelName}" />

			</html:select><span class="required">*</span></td>
			<td><html:select property="toCity_id" onchange="comboChange()">
				<html:options collection="x_toCityList" property="id"
					labelProperty="${labelName}" />
			</html:select><span class="required">*</span></td>
		</tr>

	</table>
	<hr align="left" width="100%">
	<table width="100%">
		<tr>
			<td class="bluetext" width="20%"><bean:message
				key="travelApplication.hotel.id" />:</td>
			<td colspan="3">
				<table>
					<tr>
						<td>
							<html:select property="hotel_id">			
							</html:select> 
						</td>
						 <td>
							<span id="hotelNameSpan"><html:text property="hotelName" /></span>
						</td>
						 <td>
				     		<span id="hotel_description_span"></span>
				     	</td>
					</tr>
				</table>	
			</td>
		</tr>
		<tr>
			<td class="bluetext" width="20%"><bean:message
				key="travelApplication.roomDescription" />:</td>
			<td colspan="3">
				<table>
					<tr>
						<td>
							<html:select property="price_id">				
							</html:select> 
						</td>
						 <td>
							<span id="roomDescriptionSpan"><html:text property="roomDescription" /></span>
						</td>
						 <td>
							<span id="room_description_span"></span>	
						</td>
					</tr>
				</table>		
			</td>
		</tr>
		<tr>
			<td class="bluetext" width="20%">
				<bean:message key="travelApplication.checkInDate" />:
			</td>
			<td >
				<html:text property="checkInDate" size="6" />
				<a onclick="event.cancelBubble=true;"
				href="javascript:showCalendar('dimg3',false,'checkInDate',null,null,'travelApplicationForm')"><img
				align="absmiddle" border="0" id="dimg3" src="images/datebtn.gif" /></a>
			</td>
			<td class="bluetext" width="20%">
				<bean:message key="travelApplication.checkOutDate" />:
			</td>
			<td >
				<html:text property="checkOutDate" size="6" />
				<a onclick="event.cancelBubble=true;"
				href="javascript:showCalendar('dimg4',false,'checkOutDate',null,null,'travelApplicationForm')"><img
				align="absmiddle" border="0" id="dimg4" src="images/datebtn.gif" /></a>
			</td>
		</tr>
		
	</table>
	<hr align="left" width="100%">

	<table width="100%">
		<tr>
			<td class="bluetext" width="20%"><bean:message
				key="travelApplication.travellingMode" /> :</td>
			<td width="30%"><html:select property="travellingMode">
				<html:options collection="x_taModeList" property="enumCode"
					labelProperty="${metaLabelName}" />
			</html:select><span class="required">*</span></td>
			<td class="bluetext" width="20%"><bean:message
				key="travelApplication.singleOrReturn" /> :</td>
			<td width="30%"><html:select property="singleOrReturn">
				<html:options collection="x_singleOrReturnList" property="enumCode"
					labelProperty="${metaLabelName}" />
			</html:select><span class="required">*</span></td>
		</tr>
		
		
		<tr>
			<td class="bluetext" width="20%"><bean:message key="travelApplication.duration" />
			:</td>
			<td width="80%" colspan="3"><html:text property="fromDate" size="6" /> <span class="required">*</span><a
				onclick="event.cancelBubble=true;"
				href="javascript:showCalendar('dimg1',false,'fromDate',null,null,'travelApplicationForm')"><img
				align="absmiddle" border="0" id="dimg1" src="images/datebtn.gif" /></a>
			&nbsp;~&nbsp; <html:text property="toDate" size="6" /> <span class="required">*</span><a
				onclick="event.cancelBubble=true;"
				href="javascript:showCalendar('dimg2',false,'toDate',null,null,'travelApplicationForm')"><img
				align="absmiddle" border="0" id="dimg2" src="images/datebtn.gif" /></a>
			</td>
		</tr>
		<tr>
			<td class="bluetext" width="20%"><bean:message key="travelApplication.fromTime" />
			:</td>
			<td width="30%"><html:text property="fromTime" size="2" />&nbsp;(HH:MM)<span class="required">*</span></td>
			<td class="bluetext" width="20%"><bean:message key="travelApplication.toTime" />
			:</td>
			<td width="30%"><html:text property="toTime" size="2" />&nbsp;(HH:MM)<span class="required">*</span></td>
		</tr>
	</table>
	<hr align="left" width="100%">
	<div id="oldApprovers"></div>
	<div id="newApprovers"></div>
	<iframe id="viewapprover" name="viewapprover" src="" frameborder="0" height="200" width="570" style="display:none"></iframe>
	<table width="100%">
		<tr>
			<td><input type="button" value="<bean:message key="travelApplication.button.viewApprover"/>" onclick="viewApprover(this.form);"/></td>
			<td align="right">
				<input type="button" value="<bean:message key="travelApplication.saveAsDraft"/>" onclick="saveAsDraft()"/>
				<input type="button" value="<bean:message key="all.submit" />" onclick="submitTA()"/>
				<input type="button" value="<bean:message key="all.back"/>" onclick="window.location.href='listTravelApplication_other.do'">
			</td>
		</tr>
	</table>
</html:form>


