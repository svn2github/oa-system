<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>
<page:form action="/listMetadata.do" method="post">
<html:hidden property="order"/>
<html:hidden property="descend"/>
<jsp:include page="../pageHead.jsp"/>
<table class="data" >
<thead>
<tr bgcolor="#9999ff">
  <th><bean:message key="metadata.code"/></th>
  <th><bean:message key="metadata.description"/></th>
</tr>
</thead>
<tbody>
<%
	boolean oddRow = false;
%>
<logic:iterate id="mt" name="X_RESULTLIST">
<%
	oddRow = !oddRow;
%>
<tr class="<%=oddRow ? "odd" : "even"%>">
  <td><a href="editMetadata.do?id=${mt.id}">${mt.id}</a></td>
  <td>${mt.description}</td>
</tr>
</logic:iterate>
</tbody>
</table>
<jsp:include page="../pageTail.jsp"/>
</page:form>	