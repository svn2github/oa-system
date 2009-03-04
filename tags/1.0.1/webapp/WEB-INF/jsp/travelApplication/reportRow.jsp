<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<tr id="r${X_OBJECT.id}">
	<td><a href='javascript:view("${X_OBJECT.id}")'>${X_OBJECT.id}</a></td>
	<td>${X_OBJECT.requestor.name}</td>
	<td>${X_OBJECT.department.name}</td>
	<td><bean:write name="X_OBJECT" property="toCity.${x_lang}Name"/></td>
	<td>
	  <span style="color:${X_OBJECT.travellingMode.color}"><bean:write name="X_OBJECT" property="travellingMode.${x_lang}ShortDescription"/></spane>
	</td>
	<td align="center"><bean:write name="X_OBJECT" property="createDate" format="yyyy/MM/dd"/></td>
	<td>
	  <span style="color:${X_OBJECT.urgent.color}"><bean:write name="X_OBJECT" property="urgent.${x_lang}ShortDescription"/></spane>
	</td>
	<td>
	  <span style="color:${X_OBJECT.status.color}"><bean:write name="X_OBJECT" property="status.${x_lang}ShortDescription"/></spane>
	</td>
	<td>
	  <span style="color:${X_OBJECT.bookStatus.color}"><bean:write name="X_OBJECT" property="bookStatus.${x_lang}ShortDescription"/></spane>
	</td>
	<td align="right">${X_OBJECT.approveDurationDay}</td>
</tr>



