<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>



<logic:notEmpty name="x_version">
	<div>
	<form name="siteForm"><bean:message key="expenseCategory.search.site" />:<select
		name="site_id">
		<logic:iterate id="x_site" name="x_siteList">
			<option value="${x_site.id}">${x_site.name}</option>
		</logic:iterate>
	</select></form>
	</div>
	<script>
		function getSelectedSiteId()
		{
			return document.siteForm["site_id"].value;
		}
	</script>
</logic:notEmpty>
<script>
	var oldSelectedCountry;
	
	function selectCountry(id)
	{
		document.all.city.src="about:blank";
		document.all.province.src="listProvince${SITE_VERSION}.do?country_id="+id;

	}
	function selectProvince(id)
	{
		document.all.city.src="listCity${SITE_VERSION}.do?province_id="+id;
	}
</script>
<table width="95%" cellPadding="0" cellSpacing="0">
	<tr>
		<td width="33%" valign="top">
			<!-- country --> 
			<table width="100%">
				<tr>
					<td align="center" class='bluetext'><font color="blue">
					<h3>Country/Region</h3>
					</font></td>
				</tr>
				<tr>
					<td>
						<iframe id="country" src="listCountry.do" FRAMEBORDER="0" style="width:100%;overflow:auto;height:380px;border-style:none"></iframe>
					</td>
				</tr>
				<tr>
					<td width="100%" align="left">
						<table>
							<tr>
								<td align="left">
									<input type="button" value="<bean:message key="country.button.new" />" onClick="window.country.add()">
								</td>
								<td align="left">
									<input type="button" value="<bean:message key="country.button.modify" />" onClick="window.country.edit()">
								</td>
								<td align="left">
									<input type="button" value="<bean:message key="all.delete" />" onClick="window.country.deleteMe()">
								</td>
								<logic:notEmpty name="x_promote">
								<td id="promoteCountry" align="left" style="display:none">
									<input type="button" value="<bean:message key="country.button.promote" />" onClick="window.country.promote()">
								</td>
								</logic:notEmpty>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</td>
		<td width="2px" height="450px">
			<table style="width:100%;height:100%;border-left:#234F9E 2px solid;border-collapse: sepalate;display: block;">
				<tr><td width="100%"/></tr>
			</table>
		</td>
		<td width="33%" valign="top">
			<!-- province --> 
			<table width="100%">
				<tr>
					<td align="center" class='bluetext'><font color="blue">
					<h3>State/Province</h3>
					</font></td>
				</tr>
				<tr>
					<td>
						<iframe id="province" FRAMEBORDER="0" style="width:100%;overflow:auto;height:380px;border-style:none"> </iframe>
					</td>
				</tr>
				<tr>
					<td width="100%" align="left">
						<table>
							<tr>
								<td align="left">
									<input type="button" value="<bean:message key="province.button.new" />" onClick="window.province.add()">
								</td>
								<td align="left">
									<input type="button" value="<bean:message key="province.button.modify" />" onClick="window.province.edit()">
								</td>
								<td align="left">
									<input type="button" value="<bean:message key="all.delete" />" onClick="window.province.deleteMe()">
								</td>
								<logic:notEmpty name="x_promote">
								<td id="promoteProvince" align="left" style="display:none">
									<input type="button" value="<bean:message key="province.button.promote" />" onClick="window.province.promote()">
								</td>
								</logic:notEmpty>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		
		</td>
		<td width="2px" height="450px">
			<table style="width:100%;height:100%;border-left:#234F9E 2px solid;border-collapse: sepalate;display: block;">
				<tr><td width="100%"/></tr>
			</table>
		</td>
		<td width="33%" valign="top">
			<!-- city --> 
			<table width="100%">
				<tr>
					<td align="center" class='bluetext'><font color="blue">
					<h3>City</h3>
					</font></td>
				</tr>
				<tr>
					<td>
						<iframe id="city" FRAMEBORDER="0" style="width:100%;overflow:auto;height:380px;border-style:none"> </iframe>
					</td>
				</tr>
				<tr>
					<td width="100%" align="left">
						<table>
							<tr>
								<td align="left">
									<input type="button" value="<bean:message key="city.button.new" />" onClick="window.city.add()">
								</td>
								<td align="left">
									<input type="button" value="<bean:message key="city.button.modify" />" onClick="window.city.edit()">
								</td>
								<td align="left">
									<input type="button" value="<bean:message key="all.delete" />" onClick="window.city.deleteMe()">
								</td>
								<logic:notEmpty name="x_promote">
								<td id="promoteCity" align="left" style="display:none">
									<input type="button" value="<bean:message key="city.button.promote" />" onClick="window.city.promote()">
								</td>
								</logic:notEmpty>
							</tr>
						</table>
					</td>
				</tr>
			</table>			
		</td>
	</tr>
</table>
