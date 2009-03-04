<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<tr id="r${X_OBJECT.id}">
  <td><a href='javascript:edit("${X_OBJECT.id}")'>${X_OBJECT.currency.code}</a></td>
  <td>${X_OBJECT.currency.name}</td>
  <td>
    ${X_OBJECT.exchangeRate}
  </td>
</tr>
