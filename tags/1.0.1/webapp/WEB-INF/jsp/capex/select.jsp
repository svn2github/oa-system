<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<script type="text/javascript">
<!--
	function select(id) {
		var url="?capex_id="+id;
		window.location.href=url;
	}
//-->
</script>

<div id="fixedHeaderTableContainer" class="fixedHeaderTableContainer" style="overflow=auto;height:200px;">
	<table class="data">
		<thead>
			<tr bgcolor="#9999ff">
				<th><bean:message key="capex.id" /></th>
				<th><bean:message key="capex.purchaseCategory"/></th>
				<th><bean:message key="capex.purchaseSubCategory"/></th>
				<th></th>
			</tr>
		<thead>
		<tbody id="datatable">
			<logic:iterate id="x_c" name="X_RESULTLIST">
			<tr>
				<td>${x_c.id}</td>
				<td>${x_c.purchaseCategory.description}</td>
				<td>${x_c.purchaseSubCategory.description}</td>
				<td align="center">
					<a href='javascript:select("${x_c.id}")'><bean:message key="all.select"/></a>
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
