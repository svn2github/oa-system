<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>
<html:javascript formName="travelGroupForm" staticJavascript="false"/>

<script>
	function validateForm(form)
	{
		if(!validateTravelGroupForm(form))
		{
			return false;
		}
		for(var i=0;i<form.elements.tags('INPUT').length;i++)
		{
			var o=form.elements.tags('INPUT')[i];
			if (o.name.substring(0,4)==('subs') || o.name.substring(0,10)==('abroadSubs'))
			{
				o.value=trim(o.value);
				/*if(o.value=="")
				{
					o.focus();
					alert("<bean:message key="travelGroup.error.mustSetValue" />");
					return false;
				}*/
				if(o.value!="")
				{
					if(!validateFormFloat(o.value))
					{
						o.focus();
						alert("<bean:message key="travelGroup.error.mustBeFloat"/>");
						return false;
					}
				}
			}
		}
		return true;
	}
</script>
<html:form action="/insertTravelGroup"
	onsubmit="return validateForm(this)">
	<html:hidden property="site_id" />
	<table width=90% border=0 cellpadding=4 cellspacing=0>
		<tr>
			<td class="bluetext"><bean:message key="travelGroup.site" />:</td>
			<td>${x_site.name}</td>
		</tr>

		<tr>
			<td class="bluetext"><bean:message key="travelGroup.id" />:</td>
			<td><bean:message key="common.id.generateBySystem" /></td>
		</tr>
		<tr>
			<td class="bluetext"><bean:message key="travelGroup.name" />:</td>
			<td><html:text property="name" size="30" /><span
				class="required">*</span></td>
		</tr>
		<tr>
			<td class="bluetext"><bean:message key="site.baseCurrency" />:</td>
			<td>${x_site.baseCurrency.name}</td>
		</tr>
		<tr>
			<td class="bluetext"><bean:message key="travelGroup.enabled" />:</td>
			<td><html:select property="enabled">
				<c:if test="${sessionScope.LOGIN_USER.locale=='en'}">
					<html:options collection="X_ENABLEDDISABLEDLIST"
						property="enumCode" labelProperty="engShortDescription" />
				</c:if>
				<c:if test="${sessionScope.LOGIN_USER.locale!='en'}">
					<html:options collection="X_ENABLEDDISABLEDLIST"
						property="enumCode" labelProperty="chnShortDescription" />
				</c:if>
			</html:select></td>
		</tr>
	</table>
	<hr>
	<table width=90% border=0 cellpadding=4 cellspacing=0>
		<tr>
			<td colspan="2">
			<table width=90%>
				<tr>
					<td><font color="blue">
					<h3>${x_ec.description} <bean:message key="travelGroupDetail.domesticAmountLimit"/></h3>
					</font></td>
				</tr>
			</table>
			</td>
		</tr>
		<logic:iterate id="x_esc" name="x_ec"
			property="enabledExpenseSubCategoryList">
			<tr>
				<td class="bluetext">${x_esc.description}:</td>
				<td><html:text property="subs(${x_esc.id})" size="30" /></td>
			</tr>
		</logic:iterate>
	</table>
	<hr>
	<table width=90% border=0 cellpadding=4 cellspacing=0>
		<tr>
			<td colspan="2">
			<table width=90%>
				<tr>
					<td><font color="blue">
					<h3>${x_ec.description} <bean:message key="travelGroupDetail.abroadAmountLimit"/></h3>
					</font></td>
				</tr>
			</table>
			</td>
		</tr>
		<logic:iterate id="x_esc" name="x_ec"
			property="enabledExpenseSubCategoryList">
			<tr>
				<td class="bluetext">${x_esc.description}:</td>
				<td><html:text property="abroadSubs(${x_esc.id})" size="30" /></td>
			</tr>
		</logic:iterate>
	</table>
	<hr>
	<table width=90% border=0 cellpadding=4 cellspacing=0>
		<tr>
			<td align="right"><html:submit>
				<bean:message key="all.save" />
			</html:submit> <input type="button"
				value="<bean:message key="all.back"/>"
				onclick="window.location.href='listTravelGroup.do'"></td>
		</tr>
	</table>
</html:form>
