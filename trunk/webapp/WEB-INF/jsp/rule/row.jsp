<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<tr id="r${X_OBJECT.id}">
  <td><a href="javascript:edit(${X_OBJECT.id})">${X_OBJECT.description}</a></td>
  <td>
    <span style="color:${X_OBJECT.enabled.color}"><bean:write name="X_OBJECT" property="enabled.${x_lang}ShortDescription"/></span>
  </td>
  <td><a href="javascript:del(${X_OBJECT.id})"><bean:message key="all.delete"/></a></td>
</tr>
