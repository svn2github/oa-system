<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>



<tr id="r${X_OBJECT.id}">
	<c:if test="${X_OBJECT.bookStatus.enumCode=='1'}">
	<td><a href='javascript:purchase("${X_OBJECT.id}")'>${X_OBJECT.id}</a></td>
	</c:if>

	<c:if test="${X_OBJECT.bookStatus.enumCode!='1'}">
	<td><a href='javascript:editPurchase("${X_OBJECT.id}")'>${X_OBJECT.id}</a></td>
	</c:if>

	<td>
		<div align="left">${X_OBJECT.requestor.name}</div>
	</td>
	
	<!-- title -->
	<td>
		<div align="left">${X_OBJECT.title}</div>
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
	<td><bean:write name="X_OBJECT" property="requestDate"
		format="yyyy/MM/dd" /></td>


	<!-- urgent -->
	<td>
		<span style="color:${X_OBJECT.urgent.color}"> 
			<bean:write name="X_OBJECT" property="urgent.${x_lang}ShortDescription"/>
		</span>
	</td>

	<!-- bookStatus -->
	<td>
		<span style="color:${X_OBJECT.bookStatus.color}"> 
			<bean:write name="X_OBJECT" property="bookStatus.${x_lang}ShortDescription"/>
	 	</span>
	 </td>
</tr>



