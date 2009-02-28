<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>



<script>

	function changeSite(combo)
	{
		if (!validateTravelGroupQueryForm(combo.form))
		{
			return false;
		}
		combo.form.submit();
	}
</script>


<html:javascript formName="travelGroupQueryForm"
	staticJavascript="false" />
<html:form action="listTravelGroup"
	onsubmit="return validateTravelGroupQueryForm(this)">
	<html:hidden property="order" />
	<html:hidden property="descend" />

	<table border=0 cellpadding=4 cellspacing=0>
		<tr>
			<td class="bluetext"><bean:message key="travelGroup.search.site" />:
			</td>
			<td><html:select property="site_id"
				onchange="changeSite(this)">
				<html:options collection="X_SITELIST" property="id"
					labelProperty="name" />
			</html:select></td>
		</tr>
		<tr>
			<td class="bluetext"><bean:message key="travelGroup.name" />:</td>
			<td><html:text property="name" size="20" /></td>

			<td class="bluetext"><bean:message key="travelGroup.enabled" />:</td>
			<td><html:select property="enabled">
				<html:option value=""><bean:message key="all.all"/></html:option>
				<c:if test="${sessionScope.LOGIN_USER.locale=='en'}">
					<html:options collection="X_ENABLEDDISABLEDLIST"
						property="enumCode" labelProperty="engShortDescription" />
				</c:if>
				<c:if test="${sessionScope.LOGIN_USER.locale!='en'}">
					<html:options collection="X_ENABLEDDISABLEDLIST"
						property="enumCode" labelProperty="chnShortDescription" />
				</c:if>
			</html:select></td>

			<td align="left"><html:submit>
				<bean:message key="all.query" />
			</html:submit> <input type="button"
				value="<bean:message key="all.new"/>"
				onClick="window.location.href='newTravelGroup.do?site_id='+document.forms['travelGroupQueryForm'].site_id.value"></td>
			<td colspan="2"/>
		</tr>
	</table>
</html:form>
<hr />
<page:form action="listTravelGroup.do" method="post">
	<jsp:include page="../pageHead.jsp"/>
	<table class="data">
		<thead>
			<tr bgcolor="#9999ff">

				<th width="70%"><page:order order="name"
					style="text-decoration:none">
					<bean:message key="travelGroup.name" />
					<page:desc>
						<img src="images/down.gif" border="0" />
					</page:desc>
					<page:asc>
						<img src="images/up.gif" border="0" />
					</page:asc>
				</page:order></th>

				<th width="70%"><page:order order="enabled"
					style="text-decoration:none">
					<bean:message key="travelGroup.enabled" />
					<page:desc>
						<img src="images/down.gif" border="0" />
					</page:desc>
					<page:asc>
						<img src="images/up.gif" border="0" />
					</page:asc>
				</page:order></th>
			</tr>
		</thead>

		<tbody id="datatable">
			<%int i = 1;%>
			<logic:iterate id="o" name="X_RESULTLIST">
				<tr class="<%=i++%2==0?"even":"odd"%>">
					<td><a href="editTravelGroup.do?id=${o.id}">${o.name}</a></td>
					<td><span style="color:${o.enabled.color}"> <c:if
						test="${sessionScope.LOGIN_USER.locale=='en'}">
					${o.enabled.engShortDescription}
					</c:if> <c:if test="${sessionScope.LOGIN_USER.locale!='en'}">
					${o.enabled.chnShortDescription}
					</c:if></span></td>
				</tr>
			</logic:iterate>
		</tbody>
	</table>
	<jsp:include page="../pageTail.jsp"/>
	
</page:form>
