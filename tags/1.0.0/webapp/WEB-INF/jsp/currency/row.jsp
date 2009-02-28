<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<tr id="r${X_OBJECT.code}">
<%
	String seq="";
	if (request.getAttribute("X_STARTSEQ")!=null) {
		seq=""+((Integer)request.getAttribute("X_STARTSEQ")).intValue();
	}
%>
  <td><a href='javascript:edit("${X_OBJECT.code}","<%=seq%>")'>${X_OBJECT.code}</a></td>
  <td>${X_OBJECT.name}</td>
  <td>
  	<span style="color:${X_OBJECT.enabled.color}">
      <c:if test="${sessionScope.LOGIN_USER.locale=='en'}">${X_OBJECT.enabled.engShortDescription}</c:if>
      <c:if test="${sessionScope.LOGIN_USER.locale!='en'}">${X_OBJECT.enabled.chnShortDescription}</c:if>
    </span>
  </td>
</tr>

