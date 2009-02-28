<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>
<table class="pagebanner">
	<tr>
		<td align="left">
			<table>
				<tr>
					<td><bean:message key="page.now"/></td>
					<td><page:select style="font-size:11px" format="page.format" resource="true"/></td>
					<td>
						<page:noPrevious><img alt="<bean:message key="page.prevpage"/>" src="images/noprev.gif" border=0/></page:noPrevious>
						<page:previous><img alt="<bean:message key="page.prevpage"/>" src="images/prev.gif" border=0/></page:previous>
						<page:next><img alt="<bean:message key="page.nextpage"/>" src="images/next.gif" border=0/></page:next>
						<page:noNext><img alt="<bean:message key="page.nextpage"/>" src="images/nonext.gif" border=0/></page:noNext>
					</td>
				</tr>
			</table>
		</td>
		<td align="right">
			<bean:message key="page.total"/><page:pageCount/><bean:message key="page.page"/>(<page:count/><bean:message key="page.record"/>)
		</td>
	</tr>
</table>
