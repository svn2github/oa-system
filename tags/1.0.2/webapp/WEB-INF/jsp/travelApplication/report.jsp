<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>

<xml id="data">
<data>
<logic:iterate id="x_site" name="x_siteList">
	<site id="${x_site.id}" name="<bean:write name="x_site" property="name"/>">
		<department id="0" name="All"/>
		<logic:iterate id="x_department" name="x_site" property="departments">
		<c:if test="${x_department.granted}">
			<department id="${x_department.id}" name="<bean:write name="x_department" property="indentName"/>"/>
		</c:if>
		</logic:iterate>
	</site>
</logic:iterate>
</data>
</xml>

<xml id="config">
<config>
	<site>
		<department/>
	</site>
</config>
</xml>

<script type="text/javascript">
<!--
	function view(id) {
		var url="viewTravelApplication${x_postfix}.do?id="+id;
		window.location.href=url;
	
	}	
	
	function validateForm(form) {
		if(!validateTravelApplicationQueryForm(form)) return false;
		return true;
	}
	
//-->
</script>

<div class="message"><html:messages id="x_message" message="true">${x_message}<br></html:messages></div>

<html:javascript formName="travelApplicationQueryForm" staticJavascript="false"/>
<html:form action="reportTravelApplication" focus="id" onsubmit="return validateForm(this)">
	<html:hidden property="order" />
	<html:hidden property="descend" />
	
	<table  border=0 cellpadding=4 cellspacing=0>
		<tr>
			<td class="bluetext" ><bean:message key="travelApplication.site"/>&nbsp;</td>
			<td >
				<html:select property="site_id">
				</html:select>
			</td>
			<td class="bluetext" ><bean:message key="travelApplication.department.id"/>&nbsp;</td>
			<td >
				<html:select property="department_id">
				</html:select>
			</td>	
		</tr>
		<tr>
			<td class="bluetext" ><bean:message key="travelApplication.id"/>&nbsp;</td>
			<td><html:text property="id" size="13"/></td>
			<td class="bluetext" ><bean:message key="travelApplication.title"/>&nbsp;</td>
			<td><html:text property="title" size="14"/></td>
		</tr>	
		<tr>
			<td class="bluetext" ><bean:message key="travelApplication.search.requestor.name"/>&nbsp;</td>
			<td><html:text property="requestor_name" size="13"/></td>
	
			<td class="bluetext" ><bean:message key="travelApplication.status"/></td>
			<td>
				 <html:select property="status">
		           <html:option value=""><bean:message key="all.all"/></html:option>
		           <html:options collection="x_taStatusList" property="enumCode" labelProperty="${x_lang}ShortDescription" />
		         </html:select>
			</td>
		</tr>	
		<tr>
			<td class="bluetext" ><bean:message key="travelApplication.createDate"/>&nbsp;</td>
			<td colspan="3">
				<html:text property="createDate1" size="6" maxlength="10"/>
				<a onclick="event.cancelBubble=true;"
				href="javascript:showCalendar('dimg1',false,'createDate1',null,null,'travelApplicationQueryForm')"><img
				align="absmiddle" border="0" id="dimg1" src="images/datebtn.gif" /></a>
				~
				<html:text property="createDate2" size="6" maxlength="10"/>
				<a onclick="event.cancelBubble=true;"
				href="javascript:showCalendar('dimg2',false,'createDate2',null,null,'travelApplicationQueryForm')"><img
				align="absmiddle" border="0" id="dimg2" src="images/datebtn.gif" /></a>
			</td>
		</tr>	
		<tr>
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
			<td colspan="4" align="right">
				<html:submit><bean:message key="all.query"/></html:submit>&nbsp;&nbsp;&nbsp;
			</td>
		</tr>
		
	</table>
</html:form>
<hr />
<page:form action="reportTravelApplication.do" method="post">
	<jsp:include page="../pageHead.jsp"/>
	<table class="data">
		<thead>
			<tr class="new_bg">
				<th><page:order order="id" style="text-decoration:none">
					<bean:message key="travelApplication.id" />
					<page:desc>
						<img src="images/down.gif" border="0" />
					</page:desc>
					<page:asc>
						<img src="images/up.gif" border="0" />
					</page:asc>
				</page:order></th>
				
				<th><page:order order="requestor" style="text-decoration:none">
					<bean:message key="travelApplication.requestor.id" />
					<page:desc>
						<img src="images/down.gif" border="0" />
					</page:desc>
					<page:asc>
						<img src="images/up.gif" border="0" />
					</page:asc>
				</page:order></th>

				<th><bean:message key="travelApplication.department.id" /></th>
				
				<th><page:order order="toCity_${x_lang}" style="text-decoration:none">
					<bean:message key="travelApplication.toCity.id" />
					<page:desc>
						<img src="images/down.gif" border="0" />
					</page:desc>
					<page:asc>
						<img src="images/up.gif" border="0" />
					</page:asc>
				</page:order></th>
				
				<th><page:order order="travellingMode" style="text-decoration:none">
					<bean:message key="travelApplication.travellingMode" />
					<page:desc>
						<img src="images/down.gif" border="0" />
					</page:desc>
					<page:asc>
						<img src="images/up.gif" border="0" />
					</page:asc>
				</page:order></th>

				<th><bean:message key="travelApplication.createDate" /></th>
				
				<th><page:order order="urgent" style="text-decoration:none">
					<bean:message key="travelApplication.urgent" />
					<page:desc>
						<img src="images/down.gif" border="0" />
					</page:desc>
					<page:asc>
						<img src="images/up.gif" border="0" />
					</page:asc>
				</page:order></th>

				<th><page:order order="status" style="text-decoration:none">
					<bean:message key="travelApplication.status" />
					<page:desc>
						<img src="images/down.gif" border="0" />
					</page:desc>
					<page:asc>
						<img src="images/up.gif" border="0" />
					</page:asc>
				</page:order></th>
				
				<th><page:order order="bookStatus" style="text-decoration:none">
					<bean:message key="travelApplication.bookStatus" />
					<page:desc>
						<img src="images/down.gif" border="0" />
					</page:desc>
					<page:asc>
						<img src="images/up.gif" border="0" />
					</page:asc>
				</page:order></th>
				
				<th><bean:message key="travelApplication.approvalDuration"/></th>

			</tr>
		</thead>

		<tbody id="datatable">
			<logic:iterate id="X_OBJECT" name="X_RESULTLIST">
				<bean:define id="X_OBJECT" toScope="request" name="X_OBJECT" />
				<jsp:include page="reportRow.jsp" />
			</logic:iterate>
		</tbody>
	</table>
	<c:set var="x_pdf" scope="request" value="true"/>
	<jsp:include page="../pageTail.jsp"/>
</page:form>

<script type="text/javascript">
    applyRowStyle(document.all('datatable'));
</script>


<script type="text/javascript">
    var mapping=new Map();
	mapping.put("site_id", "site");
	mapping.put("department_id", "department");
	
    initCascadeSelect("config", "data", "travelApplicationQueryForm", mapping, true);
	
</script>

