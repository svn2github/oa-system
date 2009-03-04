<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<script type="text/javascript">
<!--
	function select(id) {
		var url="?yearlyBudget_id="+id;
		window.location.href=url;
	}
//-->
</script>

<div id="fixedHeaderTableContainer" class="fixedHeaderTableContainer" style="overflow=auto;height:200px;">
	<table class="data">
		<thead>
			<tr bgcolor="#9999ff">
				<th><bean:message key="yearlyBudget.code" /></th>
				<th><bean:message key="yearlyBudget.name"/></th>
				<!-- 
				<th><bean:message key="yearlyBudget.amount"/></th>
				<th><bean:message key="yearlyBudget.actualAmount"/></th>
				<th><bean:message key="yearlyBudget.remainAmount"/></th>
				
				-->
				<th><bean:message key="yearlyBudget.version"/></th>
				<th></th>
			</tr>
		<thead>
		<tbody id="datatable">
			<logic:iterate id="x_yb" name="X_RESULTLIST">
			<tr>
				<td>${x_yb.code}</td>
				<td>${x_yb.name}</td>
				
				<td align="right">${x_yb.version}</td>
				<td align="center">
					<a href='javascript:select("${x_yb.id}")'><bean:message key="all.select"/></a>
				</td>
			</tr>
			</logic:iterate>
		</tbody>
	</table>
</div>
<script type="text/javascript">
    applyRowStyle(document.all('datatable'));
</script>
<div style="text-align:right"><input type="button" value="<bean:message key="all.close"/>" onclick="window.parent.close();"/></div>
