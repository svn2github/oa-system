<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<div>
	<iframe name="export" src="" style="display:none"></iframe>
	<bean:message key="page.export"/>:
	<c:if test="${!x_onlypdf}">
		<c:if test="${!x_nocsv}"><img src="images/csv.gif" border=0/> <page:export type="csv"><bean:message key="page.export.csv"/></page:export></c:if>
		<c:if test="${!x_noexcel}"><img src="images/excel.gif" border=0/> <page:export type="excel"><bean:message key="page.export.excel"/></page:export></c:if>
		<c:if test="${!x_noxml}"><img src="images/xml.gif" border=0/> <page:export type="xml"><bean:message key="page.export.xml"/></page:export></c:if>
	</c:if>
	<c:if test="${x_pdf || x_onlypdf}"><img src="images/pdf.gif" border=0/> <page:export type="pdf"><bean:message key="page.export.pdf"/></page:export></c:if>
</div>
