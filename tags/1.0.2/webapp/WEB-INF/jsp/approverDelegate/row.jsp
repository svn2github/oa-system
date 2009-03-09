<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>



    <tr id="r${X_OBJECT.id}">
      <td><a href='javascript:edit("${X_OBJECT.id}")'>${X_OBJECT.delegateApprover.name}</a> </td>
      <td><bean:write name="X_OBJECT" property="fromDate" format="yyyy/MM/dd"/> </td>
      <td><bean:write name="X_OBJECT" property="toDate" format="yyyy/MM/dd"/></td>
		     
    </tr>



