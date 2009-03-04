<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<c:set var="YesNo_NO" value="<%=com.aof.model.metadata.YesNo.NO%>"/>
<c:set var="YesNo_YES" value="<%=com.aof.model.metadata.YesNo.YES%>"/>
<xml id="data">
<data>
<logic:iterate id="x_site" name="x_siteList">
	<site id="${x_site.id}" name="<bean:write name="x_site" property="name"/>">
		<department id="" name="<bean:message key="department.allDepartment"/>"/>
		<logic:iterate id="x_department" name="x_site" property="departments">
		<%
			com.aof.model.admin.Department dep = (com.aof.model.admin.Department)x_department;
			String blank = "";
			while (dep.getParentDepartment() != null) {
				blank += "    ";
				dep = dep.getParentDepartment();
			}			
		%>
			<department id="${x_department.id}" name="<%=blank%><bean:write name="x_department" property="name"/>"/>
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
	function editRecharge(poItemId, airTicketId) {
		v = window.showModalDialog(
			'showDialog.do?title=airTicket.poItem.editRecharge&editAirTicket.do?id=' + poItemId + '&airTicket_id=' + airTicketId, 
			null, 'dialogWidth:500px;dialogHeight:400px;status:no;help:no;scroll:no');
	}
	
	function validateForm(form) {
		if (!hasCheckboxSelected(form, 'id')) {
			alert('<bean:message key="airTicket.poItem.notChecked"/>');
			return false;
		}
		return true;
	}
	function doSave()
	{
		document.form1.action="saveBoardingPass.do";
		document.form1.submit();
	}
//-->
</script>
<html:form action="listAirTicket">

	<table width=100% border=0 cellpadding=4 cellspacing=0>
		<tr>
	      <td class="bluetext"><bean:message key="airTicket.search.site"/>:</td>
	      <td><html:select property="site_id"/></td>

	      <td class="bluetext"><bean:message key="airTicket.search.department"/>:</td>
	      <td><html:select property="department_id" /></td>
	    </tr>
		<tr>
	      <td class="bluetext"><bean:message key="airTicket.search.buyfor"/>:</td>
	      <td><html:text property="buyFor" size="8"/></td>
	      <td class="bluetext"><bean:message key="airTicket.supplier.id"/>:</td>
	      <td><html:text property="supplier_name" size="8"/></td>
	    </tr>
	    <tr>
	      <td class="bluetext"><bean:message key="airTicket.search.dateFrom"/>:</td>
	      <td><html:text property="leave_datetime_from" size="6"/>
	      <a onclick="event.cancelBubble=true;"
				href="javascript:showCalendar('dimg1',false,'leave_datetime_from',null,null,'airTicketQueryForm')"><img
				align="absmiddle" border="0" id="dimg1" src="images/datebtn.gif" /></a>
		  </td>
	      <td class="bluetext"><bean:message key="airTicket.search.dateTo"/>:</td>
	      <td><html:text property="leave_datetime_to" size="6"/>
	      <a onclick="event.cancelBubble=true;"
				href="javascript:showCalendar('dimg2',false,'leave_datetime_to',null,null,'airTicketQueryForm')"><img
				align="absmiddle" border="0" id="dimg2" src="images/datebtn.gif" /></a>
		  </td>
	    </tr>
	    <tr>
	      <td class="bluetext"><bean:message key="airTicket.search.isOnTravel"/>:</td>
	      <td>
	      	<html:select property="is_on_travel">
	      		<option value=""><bean:message key="all.select"/> <bean:message key="all.all"/></option>
	      		<html:options collection="x_yesno" property="enumCode" labelProperty="${x_lang}Description"/>
	      	</html:select>
	      </td>
	      <td colspan="2" class="bluetext"><bean:message key="airTicket.search.includeRecievedAirTicket"/>
	      	<html:checkbox property="include_received_air_ticket" value="1"/>
	      </td>	      
	    </tr>
	    <tr>
	    	<td colspan="4" align="right">
				<html:submit><bean:message key="all.query"/></html:submit>
		  </td>
	    </tr>
	</table>
</html:form>
<hr />
<form action="createAirTicketPO.do" name="form1" method="post" onsubmit="return validateForm(this);">
<input type="hidden" name="${x_tokenName}" value="${requestScope[x_tokenValue]}"/>
<input type="hidden" name="site_id" value="${airTicketQueryForm.site_id}" />
<table class="data">
	<thead>
		<tr bgcolor="#9999ff">
			<th>#</th>			
			<th><bean:message key="airTicket.supplier.id" /></th>
			<th><bean:message key="airTicket.flightNo" /></th>
			<th><bean:message key="airTicket.class" /></th>
			<th><bean:message key="airTicket.from" /></th>
			<th><bean:message key="airTicket.to" /></th>
			<th><bean:message key="airTicket.date" /></th>
			<th><bean:message key="airTicket.currency.code" /></th>
			<th><bean:message key="airTicket.price" /></th>
			<th><bean:message key="airTicket.department" /></th>			
			<th><bean:message key="airTicket.buyFor" /></th>
			<th><bean:message key="airTicket.ta.no" /></th>
			<th><bean:message key="airTicket.ta.isOnTravel" /></th>
		</tr>
	</thead>
	<tbody id="datatable">
		<c:set var="x_sum" value="0"/>
		<c:forEach var="x_poi" items="${x_purchaseOrderItemList}">
			<c:choose>
				<c:when test="${x_row=='odd'}"><c:set var="x_row" value="even"/></c:when>
				<c:otherwise><c:set var="x_row" value="odd"/></c:otherwise>
			</c:choose>
			<c:choose>
				<c:when test="${x_poi.id==null}">
					<tr class="${x_row}">
						<td colspan="8" align="right"><strong><bean:message key="airTicket.subtotal"/>:</td>
						<td align="right">${x_poi.unitPrice}</td>
						<td colspan="4"></td>
					</tr>
				</c:when>
				<c:otherwise>
					<tr class="${x_row}">
						<td align="center">
							<c:if test="${x_poi.purchaseOrder == null}">
								<input type="checkbox" name="id" value="${x_poi.id}"/>
								<input type="hidden" name="airticketId" value="${x_poi.airTicket.id}"/>
							</c:if>
						</td>
						<td>${x_poi.supplier.name}</td>
						<td>${x_poi.airTicket.flightNo}</td>
						<td><span style="${x_poi.airTicket.flightClass.color}"><bean:write name="x_poi" property="airTicket.flightClass.${x_lang}ShortDescription"/></span></td>
						<td><bean:write name="x_poi" property="airTicket.fromCity.${x_lang}Name"/></td>
						<td><bean:write name="x_poi" property="airTicket.toCity.${x_lang}Name"/></td>
						<td align="center"><bean:write name="x_poi" property="airTicket.departTime" format="yyyy/MM/dd"/></td>
						<td align="center">${x_poi.exchangeRate.currency.name}</td>
						<td align="right">${x_poi.unitPrice}</td>
						<c:choose>						
								<c:when test="${x_poi.isRecharge==x_yes}">
						<td colspan="2" rowspan="${x_rowspan}">
									<a href="javascript:editRecharge(${x_poi.id}, ${x_poi.airTicket.id});"><bean:message key="airTicket.recharge"/></a>
						</td>
								</c:when>
								<c:otherwise>
						<td>${x_poi.buyForDepartment.name}</td>
						<td>
									<c:if test="${x_poi.buyForUser!=null}">
										${x_poi.buyForUser.name}
									</c:if>
						</td>
								</c:otherwise>
							</c:choose>
						<td>${x_poi.airTicket.travelApplication.id}</td>
						<td align="center">
						<c:choose>	
							<c:when test="${x_poi.airTicket.boardingPassRecevied == YesNo_YES}">
								<c:choose>	
									<c:when test="${x_poi.purchaseOrder == null}">								
										<select name="boardingPassReceived" >
											<c:if test="${sessionScope.LOGIN_USER.locale=='en'}">
											<option value="${YesNo_YES}" selected>${YesNo_YES.engDescription}</option>
											<option value="${YesNo_NO}">${YesNo_NO.engDescription}</option>
											</c:if>											
										</select>
									</c:when>
									<c:otherwise>
										<c:if test="${sessionScope.LOGIN_USER.locale=='en'}">${YesNo_YES.engDescription}</c:if>
										<c:if test="${sessionScope.LOGIN_USER.locale!='en'}">${YesNo_YES.chnDescription}</c:if>
									</c:otherwise>
								</c:choose>
							</c:when>
							<c:otherwise>
								<c:choose>	
									<c:when test="${x_poi.purchaseOrder == null}">								
										<select name="boardingPassReceived" >
											<c:if test="${sessionScope.LOGIN_USER.locale=='en'}">
											<option value="${YesNo_YES}">${YesNo_YES.engDescription}</option>
											<option value="${YesNo_NO}" selected>${YesNo_NO.engDescription}</option>
											</c:if>											
										</select>
									</c:when>
									<c:otherwise>
										<c:if test="${sessionScope.LOGIN_USER.locale=='en'}">${YesNo_NO.engDescription}</c:if>
										<c:if test="${sessionScope.LOGIN_USER.locale!='en'}">${YesNo_NO.chnDescription}</c:if>
									</c:otherwise>
								</c:choose>
							</c:otherwise>
						</c:choose>
						</td>
					</tr>
				</c:otherwise>
			</c:choose>
		</c:forEach>
	</tbody>
</table>
<input type="button" value="<bean:message key="all.save"/>" onclick="doSave();"/>
<html:submit><bean:message key="airTicket.ceatePO"/></html:submit>
<input type="button" value="<bean:message key="all.selectall"/>" onclick="selectAll(this.form, 'id');"/>
<input type="button" value="<bean:message key="all.selectnone"/>" onclick="selectNone(this.form, 'id');"/>
</form>
<script type="text/javascript">
	var mapping=new Map();
	mapping.put("site_id", "site");
	mapping.put("department_id", "department");
	
    initCascadeSelect("config", "data", "airTicketQueryForm", mapping, true);
</script>