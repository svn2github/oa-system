<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ page import="net.sourceforge.model.metadata.AirTicketStatus" %>
<%@ page import="net.sourceforge.model.metadata.YesNo" %>
<c:set var="status_booked" value="<%=AirTicketStatus.BOOKED.getEnumCode()%>"/>
<c:set var="status_received" value="<%=AirTicketStatus.RECEIVED.getEnumCode()%>"/>
<c:set var="yes" value="<%=YesNo.YES.getEnumCode()%>"/>
<tr id="r${X_OBJECT.id}">

	<td >
		${X_OBJECT.supplier.name}
	</td>
	<logic:notEmpty name="x_purchaseTypeList">
		<td >
			${X_OBJECT.purchaseType.description}
		</td>
	</logic:notEmpty>
	<td>
		${X_OBJECT.flightNo}
	</td>
	<td>
		<span style="color:${X_OBJECT.flightClass.color}"> <bean:write name="X_OBJECT" property="flightClass.${x_lang}ShortDescription" /> </span>
	</td>
	<td>
		<bean:write name="X_OBJECT" property="fromCity.${x_lang}Name" />
	</td>
	<td>
		<bean:write name="X_OBJECT" property="toCity.${x_lang}Name" />
	</td>

	<td>
		<bean:write name="X_OBJECT" property="departTime" format="yyyy/MM/dd HH:mm" />
	</td>

	<td>
		<bean:write name="X_OBJECT" property="arriveTime" format="yyyy/MM/dd HH:mm" />
	</td>

	<td>
		${X_OBJECT.exchangeRate.currency.name}
	</td>

	<td>
		${X_OBJECT.price}
	</td>
	<td align="center" >
		<c:if test="${X_OBJECT.status==null}">
			<font color="gray"><bean:message key="travelApplication.purchase.airTicket.draft"/></font>
		</c:if>
		<c:if test="${X_OBJECT.status!=null}">
			<span style="color:${X_OBJECT.status.color}"> <bean:write name="X_OBJECT" property="status.${x_lang}ShortDescription" /> </span>
		</c:if>
	</td>
	<td>
		&nbsp;
		<c:set var="hasEdit" value="false"/>
		<c:if test="${x_edit}">
			<c:if test="${X_OBJECT.status==null || (X_OBJECT.status.enumCode==status_booked)}">
				<a href="javascript:editAirTicket(${X_OBJECT.id})"><bean:message key="all.modify"/></a>
				<c:set var="hasEdit" value="true"/>
				&nbsp;
			</c:if>
		</c:if>
		<c:if test="${!hasEdit && (X_OBJECT.isRecharge.enumCode==yes)}">
			<a href="javascript:viewAirTicket(${X_OBJECT.id})"><bean:message key="all.detail"/></a>
			&nbsp;
		</c:if>
		<c:if test="${x_edit}">
			<c:if test="${X_OBJECT.status==null || (X_OBJECT.status.enumCode==status_booked)}">
				<a href="javascript:deleteAirTicket(${X_OBJECT.id})"><bean:message key="all.delete"/></a>
				<input type="hidden" name="newAirTicket" />
				&nbsp;
			</c:if>
		</c:if>	
		<c:if test="${x_requestor}">
			<c:if test="${(X_OBJECT.status.enumCode==status_booked)}">
				<a href="javascript:confirmAirTicket(${X_OBJECT.id})"><bean:message key="all.confirm"/></a>
				&nbsp;
				<a href="javascript:deleteAirTicket(${X_OBJECT.id})"><bean:message key="all.delete"/></a>
				&nbsp;
			</c:if>
			<c:if test="${(X_OBJECT.status.enumCode==status_received)}">
				<a href="javascript:cancelAirTicket(${X_OBJECT.id})"><bean:message key="travelApplication.purchase.airTicket.cancel" /></a>
				&nbsp;
			</c:if>
		</c:if>
	</td>
</tr>


