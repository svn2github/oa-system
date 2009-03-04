<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>
<script language="javascript" src="includes/table.js"></script>
<script type="text/javascript">
<!--
	function assign(id) {
		var url='assignTravelApplication.do?id='+id;
		window.location.href=url;
	}
	
	function changeSite()
	{
		with(document.travelApplicationQueryForm)
		{
			submit();
		}
	}
	
	function comboDepartmentChange()
	{
		with(document.travelApplicationQueryForm)
		{
			var selectedOption=department_id.options(department_id.selectedIndex);
			if(selectedOption.style.color=="gray")
			{
				alert("<bean:message key="travelApplication.department.noPower"/>");
				department_id.value=old_department_id.value
			}
			else
			{
				old_department_id.value=department_id.value
			}
		}
	}

//-->
</script>
<html:form action="listTravelApplication_assign">
	<html:hidden property="order" />
	<html:hidden property="descend" />
	
		<input type="hidden" name="old_department_id" 
			value="<bean:write name="travelApplicationQueryForm" property="department_id"/>">

	<table  border=0 cellpadding=4 cellspacing=0>
		<tr>
			<td class="bluetext" ><bean:message key="travelApplication.site" />
			&nbsp;</td>
			<td >
				<html:select property="site_id" onchange="changeSite()">
					<html:options collection="x_siteList" property="id"
						labelProperty="name" />
				</html:select>
			</td>
			<td class="bluetext" ><bean:message key="travelApplication.department.id" />
			&nbsp;</td>
			<td colspan="2">
				<html:select property="department_id" onchange="comboDepartmentChange()"> 
					<logic:iterate name="x_departmentList" id="x_department">
						<c:set var="x_style" value=""/>				
						<c:if test="${!x_department.granted}">				
							<c:set var="x_style" value="color:gray"/>				
						</c:if>
						<html:option style="${x_style}" value="${x_department.id}">${x_department.indentName}</html:option>
						</logic:iterate>
				</html:select>
			</td>
		</tr>
		<tr>
			
			<td class="bluetext" ><bean:message key="travelApplication.id" />
			&nbsp;</td>
			<td><html:text property="id" size="10" />
			<td class="bluetext"><bean:message
				key="travelApplication.search.title" />&nbsp;</td>
			<td colspan="2"><html:text property="title" />
		</tr>
		<tr>
			<td class="bluetext"><bean:message key="travelApplication.bookStatus" /></td>
			<td><html:select property="bookStatus">
				<html:option value=""><bean:message key="all.all"/></html:option>
				<c:if test="${sessionScope.LOGIN_USER.locale=='en'}">
					<html:options collection="x_travelApplicationBookStatusList" property="enumCode"
						labelProperty="engShortDescription" />
				</c:if>
				<c:if test="${sessionScope.LOGIN_USER.locale!='en'}">
					<html:options collection="x_travelApplicationBookStatusList" property="enumCode"
						labelProperty="chnShortDescription" />
				</c:if>
			</html:select></td>
			
			<td class="bluetext"><bean:message key="travelApplication.urgent" /></td>
			<td><html:select property="urgent">
				<html:option value=""><bean:message key="all.all"/></html:option>
				<c:if test="${sessionScope.LOGIN_USER.locale=='en'}">
					<html:options collection="x_taUrgentList" property="enumCode"
						labelProperty="engShortDescription" />
				</c:if>
				<c:if test="${sessionScope.LOGIN_USER.locale!='en'}">
					<html:options collection="x_taUrgentList" property="enumCode"
						labelProperty="chnShortDescription" />
				</c:if>
			</html:select></td>
			<td align="center"><html:submit>
				<bean:message key="all.query" />
			</html:submit> </td>
		</tr>
	</table>
	<hr>
</html:form>




<page:form action="listTravelApplication_assign" method="post">
	<jsp:include page="../pageHead.jsp"/>
	<table class="data">
		<thead>
			<tr bgcolor="#9999ff">
				<th ><page:order order="id" style="text-decoration:none">
					<bean:message key="travelApplication.id" />
					<page:desc>
						<img src="images/down.gif" border="0" />
					</page:desc>
					<page:asc>
						<img src="images/up.gif" border="0" />
					</page:asc>
				</page:order></th>



				<c:set var="x_order" value="requestor"/>
				<c:set var="x_key" value="travelApplication.requestor"/>



				<th >
					<page:order order="${x_order}" style="text-decoration:none">
					
					<bean:message key="${x_key}" />
					
					<page:desc>
						<img src="images/down.gif" border="0" />
					</page:desc>
					<page:asc>
						<img src="images/up.gif" border="0" />
					</page:asc>
				</page:order></th>
				
				<c:set var="x_order" value="creator"/>
				<c:set var="x_key" value="travelApplication.creator"/>


				<th >
					<page:order order="${x_order}" style="text-decoration:none">
					
					<bean:message key="${x_key}" />
					
					<page:desc>
						<img src="images/down.gif" border="0" />
					</page:desc>
					<page:asc>
						<img src="images/up.gif" border="0" />
					</page:asc>
				</page:order></th>
				
				
				
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
					<page:order order="booker" style="text-decoration:none">
					<bean:message key="travelApplication.booker.id" />
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
				<jsp:include page="assignRow.jsp" />
			</logic:iterate>
		</tbody>
	</table>

</page:form>

<script type="text/javascript">
	    applyRowStyle(document.all('datatable'));
	</script>
