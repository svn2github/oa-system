<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>

<script type="text/javascript">
<!--

	function confirm(id) {
		window.location.href="confirmSupplier${x_version}.do?id="+id;
	}
	function changeSite(combo)
	{
		combo.form.submit();
	}
//-->
</script>

<html:form action="listConfirmSupplier${x_version}">
	<html:hidden property="order" />
	<html:hidden property="descend" />

	<table width=100% border=0 cellpadding=4 cellspacing=0>
	<logic:notEmpty name="x_version">
		<tr>
			<td class="bluetext"><bean:message key="supplier.site"/>:</td>
			<td colspan="6" align="left"><html:select property="site_id" onchange="changeSite(this)">
				<html:options collection="X_SITELIST" property="id"
					labelProperty="name" />
			</html:select></td>
			
		</tr>
	</logic:notEmpty>
		<tr>
			<!-- id -->
			<td class="bluetext"><bean:message key="supplier.code"/>:</td>
			<td><html:text property="code"/></td>
			<td class="bluetext"><bean:message key="supplier.description"/>:</td>
			<td><html:text property="name"/></td>
			<td class="bluetext"><bean:message key="supplier.confirm.type" />:</td>
			<td>
				<html:select property="confirmType">
				<html:option value="All"><bean:message key="all.all"/></html:option>
			      <c:if test="${sessionScope.LOGIN_USER.locale=='en'}"><html:options collection = "X_CONFIRMSTATUSLIST" property = "enumCode" labelProperty = "engShortDescription"/></c:if>
			      <c:if test="${sessionScope.LOGIN_USER.locale!='en'}"><html:options collection = "X_CONFIRMSTATUSLIST" property = "enumCode" labelProperty = "chnShortDescription"/></c:if>
				</html:select>
			</td>
			<td align="right" colspan="4">
				<html:submit><bean:message key="all.query"/></html:submit>
			</td>
		</tr>
	</table>
</html:form>
<hr />
<page:form action="listConfirmSupplier${x_version}.do" method="post">
	<jsp:include page="../pageHead.jsp"/>
	<table class="data">
		<thead>
			<tr class="new_bg">
				<th width="25%"><page:order order="code" style="text-decoration:none">
					<bean:message key="supplier.code" />
					<page:desc>
						<img src="images/down.gif" border="0" />
					</page:desc>
					<page:asc>
						<img src="images/up.gif" border="0" />
					</page:asc>
				</page:order></th>

				<th width="50%"><page:order order="name"
					style="text-decoration:none">
					<bean:message key="supplier.name" />
					<page:desc>
						<img src="images/down.gif" border="0" />
					</page:desc>
					<page:asc>
						<img src="images/up.gif" border="0" />
					</page:asc>
				</page:order></th>
				<th width="8%"><bean:message key="supplier.confirm.type" /></th>
				<th width="17%"><bean:message key="supplier.lastModifyDate" /></th>
				
			</tr>
		</thead>

		<tbody id="datatable">
			<logic:iterate id="X_OBJECT" name="X_RESULTLIST">
				<tr id="r${X_OBJECT.id}">
				  <td>
					  <a href='javascript:confirm("${X_OBJECT.id}")'>${X_OBJECT.code}</a>
				  </td>
				  <td>${X_OBJECT.name}</td>
				  <td>
				  	<span style="color:${X_OBJECT.confirmStatus.color}">
				      <c:if test="${sessionScope.LOGIN_USER.locale=='en'}">${X_OBJECT.confirmStatus.engShortDescription}</c:if>
				      <c:if test="${sessionScope.LOGIN_USER.locale!='en'}">${X_OBJECT.confirmStatus.chnShortDescription}</c:if>
				    </span>  
				  </td>
				  <td><bean:write name="X_OBJECT" property="lastModifyDate" format="yyyy/MM/dd"/></td>
				</tr>
			</logic:iterate>
		</tbody>
	</table>
	<jsp:include page="../pageTail.jsp"/>
</page:form>

<script type="text/javascript">
    applyRowStyle(document.all('datatable'));
</script>

