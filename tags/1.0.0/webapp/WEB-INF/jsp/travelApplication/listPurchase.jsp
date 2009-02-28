<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>
<script language="javascript" src="includes/table.js"></script>
<xml id="data">
<data>
<logic:iterate id="x_site" name="x_siteList">
	<site id="${x_site.id}" name="<bean:write name="x_site" property="name"/>">
	
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
	function purchase(id) {
		var url='purchaseTravelApplication.do?id='+id;
		window.location.href=url;
	}
	
	function editPurchase(id) {
		var url="editTravelApplicationPurchase.do?id="+ id;
		window.location=url;
	}	
	
	function trimControlValue(c)
	{
		c.value=trim(c.value);
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
	
	
//-->
</script>
<html:javascript formName="travelApplicationQueryForm" staticJavascript="false"/>
<html:form action="listTravelApplicationPurchase" onsubmit="return validateForm(this)">
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
			
			<td class="bluetext" ><bean:message key="travelApplication.id" />
			&nbsp;</td>
			<td><html:text property="id" size="10" maxlength="16"/>
			<td class="bluetext"><bean:message
				key="travelApplication.search.title" />&nbsp;</td>
			<td colspan="3"><html:text property="title" />
			
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
			<td class="bluetext"><bean:message key="travelApplication.requestDate" />:</td>
			<td colspan="3">
				<html:text property="requestDate1" size="6" maxlength="10"/>
				<a onclick="event.cancelBubble=true;"
				href="javascript:showCalendar('dimg5',false,'requestDate1',null,null,'travelApplicationQueryForm')"><img
				align="absmiddle" border="0" id="dimg5" src="images/datebtn.gif" /></a>
				~
				<html:text property="requestDate2" size="6" maxlength="10"/>
				<a onclick="event.cancelBubble=true;"
				href="javascript:showCalendar('dimg7',false,'requestDate2',null,null,'travelApplicationQueryForm')"><img
				align="absmiddle" border="0" id="dimg7" src="images/datebtn.gif" /></a>
				
			</td>
		</tr>
		<tr>
			<td class="bluetext"><bean:message
				key="travelApplication.search.requestor.name" />:&nbsp;</td>
			<td><html:text property="requestor_name" size="12"/></td>
			
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
			
		</tr>
		<tr>	
			<td class="bluetext">&nbsp;</td>
			<td align="right" colspan="5"><html:submit>
				<bean:message key="all.query" />
			</html:submit> </td>
		</tr>
	</table>
	<hr>
</html:form>




<page:form action="listTravelApplicationPurchase.do" method="post">
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

				<th >
					<page:order order="requestor" style="text-decoration:none">
					
					<bean:message key="travelApplication.requestor" />
					
					<page:desc>
						<img src="images/down.gif" border="0" />
					</page:desc>
					<page:asc>
						<img src="images/up.gif" border="0" />
					</page:asc>
					</page:order>
				</th>

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
				<jsp:include page="purchaseRow.jsp" />
			</logic:iterate>
		</tbody>
	</table>
	<jsp:include page="../pageTail.jsp"/>
</page:form>

<script type="text/javascript">
	applyRowStyle(document.all('datatable'));
	
    var mapping=new Map();
	mapping.put("site_id", "site");
	mapping.put("department_id", "department");
	
    initCascadeSelect("config", "data", "travelApplicationQueryForm", mapping, true);
	
</script>
