<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>

/*
function alertBeforeToday(s)
{
	alert(s+"<bean:message key="all.error.beforeToday" />");
}

function alertAfterToday(s)
{
	alert(s+"<bean:message key="all.error.afterToday" />");
}

function alertBeforeDate(s, d)
{
	alert(s+"<bean:message key="all.error.beforeDate" />"+d);
}
*/
function alertInteger(s)
{
	alert(s+"<bean:message key="all.errors.integer" />");
}

function alertFloat(s)
{
	alert(s+"<bean:message key="all.errors.float" />");
}
function alertRequired(s)
{
	alert(s+"<bean:message key="all.error.required" />");
}


function alertDate(s)
{
	alert(s+"<bean:message key="all.errors.date" />");
}

function getFullDate(date) {
	return date.substring(0,4)+"?"+date.substring(4,2)+"?"+date.substring(6)+"?"
}
