<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<tr id="r${X_OBJECT.code}">
  <td>${X_OBJECT.code}</td>
  <td>${X_OBJECT.description}</td>
  <td><a href='javascript:select("${X_OBJECT.code}", "<bean:write name="X_OBJECT" property="description"/>", "${X_OBJECT.type.rechargeType.color}", "<bean:write name="X_OBJECT" property="type.rechargeType.${x_lang}ShortDescription"/>");'/><bean:message key="all.select"/></a></td>
</tr>
