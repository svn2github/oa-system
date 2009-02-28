<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ page import="com.aof.model.metadata.TravelApplicationStatus" %>
<%@ page import="com.aof.model.metadata.TravelApplicationBookStatus" %>
<%@ page import="com.aof.model.metadata.TravelApplicationUrgent" %>



<tr id="r${X_OBJECT.id}">

	<!--  id -->
	<c:set var="status_draft" value="<%=TravelApplicationStatus.DRAFT.getEnumCode()%>"/>
	<c:set var="status_rejected" value="<%=TravelApplicationStatus.REJECTED.getEnumCode()%>"/>
	<c:set var="status_pending" value="<%=TravelApplicationStatus.PENDING.getEnumCode()%>"/>
	<c:set var="status_approved" value="<%=TravelApplicationStatus.APPROVED.getEnumCode()%>"/>
	<c:set var="urgent" value="<%=TravelApplicationUrgent.URGENT.getEnumCode()%>"/>
	<c:set var="normal" value="<%=TravelApplicationUrgent.NORMAL.getEnumCode()%>"/>	
	<c:set var="book_na" value="<%=TravelApplicationBookStatus.NA.getEnumCode()%>"/>	
	<c:set var="book_booked" value="<%=TravelApplicationBookStatus.BOOKED.getEnumCode()%>"/>		
	<c:set var="book_reveived" value="<%=TravelApplicationBookStatus.RECEIVED.getEnumCode()%>"/>			
	
	<c:if test="${X_OBJECT.status.enumCode==status_draft}">
	<td><a href='javascript:edit("${X_OBJECT.id}")'>${X_OBJECT.id}</a></td>
	</c:if>

	<c:if test="${ ( X_OBJECT.status.enumCode==status_pending or X_OBJECT.status.enumCode==status_rejected
		or X_OBJECT.status.enumCode==status_approved ) && X_OBJECT.bookStatus.enumCode==book_na}">
		<td><a href='javascript:view("${X_OBJECT.id}")'>${X_OBJECT.id}</a></td>
	</c:if>
	
	<c:if test="${ ( X_OBJECT.status.enumCode==status_pending or X_OBJECT.status.enumCode==status_rejected
		or X_OBJECT.status.enumCode==status_approved ) && X_OBJECT.bookStatus.enumCode!=book_na}">
		<td><a href='javascript:viewPurchase("${X_OBJECT.id}")'>${X_OBJECT.id}</a></td>
	</c:if>

	
	
	<logic:notEmpty name="x_version">
		
		<!-- department 
		<td>
			<div align="left">${X_OBJECT.department.name}</div>
		</td>
		-->
		
		<!-- requestor -->
		<td>
			<div align="left">${X_OBJECT.requestor.name}</div>
		</td>
		
	</logic:notEmpty>
	
	<!-- title -->
	<td>
		<div align="left">${X_OBJECT.title}</div>
	</td>
	
	<!-- toCity -->
	<td align="left">
		<bean:write name="X_OBJECT" property="toCity.${x_lang}Name"/>
	</td>	
	
	<!-- duration -->
	<td>
		<div align="left">
			<bean:write name="X_OBJECT" property="fromDate" format="yyyy/MM/dd"/>
		</div>
	</td>
	<td>
		<div>
			<bean:write name="X_OBJECT" property="toDate" format="yyyy/MM/dd"/>
		</div>
	</td>
	
	<!-- requestDate -->
	<td align="center">
		<bean:write name="X_OBJECT" property="requestDate"
			format="yyyy/MM/dd" />
	</td>

	<!-- urgent -->
	<td>
		<span style="color:${X_OBJECT.urgent.color}"> 
			<bean:write name="X_OBJECT" property="urgent.${x_lang}ShortDescription"/>
		</span>
	</td>


	<!-- status -->
	<td>
		<span style="color:${X_OBJECT.status.color}"> 
			<bean:write name="X_OBJECT" property="status.${x_lang}ShortDescription"/>
		</span>
	</td>
	
	<!-- bookStatus -->
	<td>
		<span style="color:${X_OBJECT.bookStatus.color}"> 
			<bean:write name="X_OBJECT" property="bookStatus.${x_lang}ShortDescription"/>
		</span>
	</td>
</tr>



