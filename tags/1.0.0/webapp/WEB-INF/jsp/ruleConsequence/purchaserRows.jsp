<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<c:forEach var="X_OBJECT" items="${X_OBJECTS}">
	<c:set var="X_OBJECT" scope="request" value="${X_OBJECT}"/>
	<jsp:include page="purchaserRow.jsp"/>
</c:forEach>
