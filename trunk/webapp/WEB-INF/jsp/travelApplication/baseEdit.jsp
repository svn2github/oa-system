<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>
<%@ page import="com.aof.model.metadata.*"%>


<script>
	function comboChange()
	{
		with(document.travelApplicationForm)
		{
			combo_change.value="true";
		<logic:present scope="request" name="approveRequestAction">
			action="viewTravelApplicationApproveRequest.do";
		</logic:present>			
		<logic:notPresent scope="request" name="approveRequestAction">
			action="editTravelApplication_self.do";
		</logic:notPresent>				
			submit();
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
<html:form action="/updateTravelApplication_self" focus="department_site_id" onsubmit="return validateForm(this)">
	
	<html:hidden property="id" />
	<html:hidden property="requestor_id" />
	
	<input type="hidden" name="draft" value="false"/>
	<input type="hidden" value="" name="combo_change" />	

	<table width="90%">
		<tr>
			<td>
				<html:errors />
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
			<td width="20%" class="bluetext"><bean:message key="travelGroup.site" /></td>
			<td width="80%" colspan="3" class="bluetext">
				<html:hidden property="department_site_id" />
				${x_travelApplication.department.site.name}
			</td>
		</tr>
		<tr>
			<td width="20%" class="bluetext"><bean:message key="travelGroup.department" /></td>
			<td width="80%" colspan="3" class="bluetext">
				<html:hidden property="department_id" />
				${x_travelApplication.department.name}
			</td>
		</tr>
		<tr>
			<td width="20%" class="bluetext"><bean:message key="travelApplication.requestor" />:</td>
			<td class="bluetext" width="30%">${x_travelApplication.requestor.name}</td>
			<td width="20%" class="bluetext"><bean:message key="travelApplication.requestDate" />:</td>
			<td class="bluetext" width="30%">
				<fmt:formatDate value="${x_travelApplication.requestDate}" pattern="yyyy/MM/dd"/>
			</td>
		</tr>
		<tr>
			<td width="20%" class="bluetext"><bean:message key="travelApplication.creator" />:</td>
			<td class="bluetext" width="30%">${x_travelApplication.creator.name}</td>
			<td width="20%" class="bluetext"><bean:message key="travelApplication.createDate" />:</td>
			<td class="bluetext" width="30%">
				<fmt:formatDate value="${x_travelApplication.createDate}" pattern="yyyy/MM/dd"/>
			</td>
		</tr>
	</table>
	<hr align="left" width="100%">
	<table width=100%>
		<tr>
			<td width="20%" class='bluetext'><bean:message
				key="travelApplication.id" />:&nbsp;</td>
			<td width="80%" colspan="3">${x_travelApplication.id}</td>
		</tr>
		<tr>
			<td class="bluetext"  width="20%"><bean:message key="travelApplication.title" />:
			</td>
			<td width="80%" colspan="3">
				<html:text property="title" size="60"/><span class="required">*</span></td>
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
			</html:select><span class="required">*</span></td>

			<td class='bluetext'  width="20%"><bean:message key="travelApplication.status" />:</td>
			<td><font color="${x_travelApplication.status.color}">
				<bean:write name="x_travelApplication" property="status.${x_lang}Description"/>
			 </font></td>
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
			<td class="bluetext"><bean:message key="travelApplication.country" /></td>
			<td><html:select property="fromCity_province_country_id" >
			</html:select></td>
			<td><html:select property="toCity_province_country_id" onchange="comboChange()">
				<html:options collection="x_countryList" property="id"
					labelProperty="${labelName}" />
			</html:select></td>
		</tr>
		<tr>
			<td class="bluetext"><bean:message key="travelApplication.province" /></td>
			<td><html:select property="fromCity_province_id" >
			</html:select></td>
			<td><html:select property="toCity_province_id" onchange="comboChange()">
				<html:options collection="x_toProvinceList" property="id"
					labelProperty="${labelName}" />
			</html:select></td>
		</tr>
		<tr>
			<td class="bluetext"><bean:message key="travelApplication.city" /></td>
			<td><html:select property="fromCity_id" >
			</html:select><span
				class="required">*</span></td>
			<td><html:select property="toCity_id" onchange="comboChange()">
				<html:options collection="x_toCityList" property="id"
					labelProperty="${labelName}" />
			</html:select><span
				class="required">*</span></td>
		</tr>

	</table>
	<hr align="left" width="100%">
	<table width="100%">
		<tr>
			<td class="bluetext" width="20%"><bean:message
				key="travelApplication.hotel.id" />:</td>
			<td colspan="3" >
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
			<td colspan="3" >
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
			<td colspan="3"><html:text property="fromDate" size="6" /><span class="required">*</span> <a
				onclick="event.cancelBubble=true;"
				href="javascript:showCalendar('dimg1',false,'fromDate',null,null,'travelApplicationForm')"><img
				align="absmiddle" border="0" id="dimg1" src="images/datebtn.gif" /></a>
			&nbsp;~&nbsp; <html:text property="toDate" size="6" /><span class="required">*</span> <a
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


</html:form>
<div id="oldApprovers"><jsp:include page="../approve/list.jsp"/></div>
<div id="newApprovers"></div>
<iframe id="viewapprover" name="viewapprover" src="" frameborder="0" height="200" width="570" style="display:none"></iframe>
