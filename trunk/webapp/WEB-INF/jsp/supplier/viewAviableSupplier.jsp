<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>

<table class="data">
		<thead>bgcolor="#9999ff""#9999ff">
				<th width="30%">
					<bean:message key="supplier.code" />
				</th>

				<th width="70%">
					<bean:message key="supplier.name" />
				</th>				
			</tr>
		</thead>

		<tbody id="datatable">
			<logic:iterate id="X_OBJECT" name="X_RESULTLIST">
				<tr>
			  <td>
				  ${X_OBJECT.code}
			  </td>
			  <td>${X_OBJECT.name}</td>
		</logic:iterate>
	</tbody>
</table>
<hr width="90%" align="left">
<table width=90% border=0 cellpadding=4 cellspacing=0>
	<tr>
		<td align="right"><input type="button" value="<bean:message key="all.close"/>" onclick="window.parent.close();"/></td>
	</tr>
</table>