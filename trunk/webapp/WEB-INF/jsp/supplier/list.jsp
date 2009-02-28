<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>
<%@ page import="com.aof.model.metadata.SupplierPromoteStatus"%>

<script type="text/javascript">
<!--
	function add() {
		var siteId=getSelectedSiteId();

		<logic:notEmpty name="x_version">
			if(siteId=="")
			{
				alert("<bean:message key="supplier.error.siteNewGlobal"/>");
				return;
			}
		</logic:notEmpty>
		<logic:empty name="x_version">
			siteId="";
		</logic:empty>
	
		var url="newSupplier${x_version}.do?";
		if(siteId!="")
		{
			url+=("site_id="+siteId);
		}

		window.location.href=url;
	}

	function edit(id) {
		window.location.href="editSupplier${x_version}.do?id="+id;
	}
	
	function promote(id) {
		window.location.href="promoteSupplier.do?id="+id;
	}
	
	function request(id) {
		window.location.href="requestPromoteSupplier.do?id="+id;
	}
	
	function response(id) {
		window.location.href="responsePromoteSupplier.do?id="+id;
	}
	
	function modifyItem(id) {
		window.location.href="listSupplierItem${x_version}.do?supplier_id="+id;
	}
	function changeSite(combo)
	{
		combo.form.submit();
	}
	function getSelectedSiteId()
	{
		return document.supplierQueryForm["site_id"].value;
	}
//-->
</script>
<html:form action="listSupplier${x_version}">
	<html:hidden property="order" />
	<html:hidden property="descend" />
	

	<table width=100% border=0 cellpadding=4 cellspacing=0>
		<tr>
			<td class="bluetext"><bean:message key="supplier.site"/>:</td>
			<td align="left"><html:select property="site_id" onchange="changeSite(this)">
				<option value=""><bean:message key="supplier.search.site.globallevel" /></option>
				<html:options collection="X_SITELIST" property="id"
					labelProperty="name" />
			</html:select></td>
			<td></td><td></td>
		</tr>
		<tr>
			<!-- id -->
			<td class="bluetext"><bean:message key="supplier.code" />:</td>
			<td><html:text property="code"/></td>
			<td class="bluetext"><bean:message key="supplier.description" />:</td>
			<td><html:text property="name" /></td>
		</tr>
		
		
		<tr>
			<td class="bluetext"><bean:message key="supplier.contractStatus" />:</td>
			<td>
				<html:select property="contractStatus">
				  <html:option value=""><bean:message key="all.all"/></html:option>
			      <c:if test="${sessionScope.LOGIN_USER.locale=='en'}"><html:options collection = "X_SUPPLIERSTATUSLIST" property = "enumCode" labelProperty = "engShortDescription"/></c:if>
			      <c:if test="${sessionScope.LOGIN_USER.locale!='en'}"><html:options collection = "X_SUPPLIERSTATUSLIST" property = "enumCode" labelProperty = "chnShortDescription"/></c:if>
			    </html:select>
			</td>
			
			<td class="bluetext"><bean:message key="supplier.status"/>:</td>
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
			
		</tr>
		<tr>
			<td class="bluetext"><bean:message key="supplier.promoteStatus"/>:</td>
			<td align="left">
				<html:select property="promoteStatus">
				  <html:option value=""><bean:message key="all.all"/></html:option>
			      <c:if test="${sessionScope.LOGIN_USER.locale=='en'}"><html:options collection = "X_PROMOTESTATUSLIST" property = "enumCode" labelProperty = "engShortDescription"/></c:if>
			      <c:if test="${sessionScope.LOGIN_USER.locale!='en'}"><html:options collection = "X_PROMOTESTATUSLIST" property = "enumCode" labelProperty = "chnShortDescription"/></c:if>
			    </html:select>
			</td>
			<td></td><td></td>
		</tr>
		<tr>
			<td align="right" colspan="4">
				<html:submit><bean:message key="all.query"/></html:submit>
				<input type="button" value="<bean:message key="all.new"/>"
					onClick="add()" />
			</td>
		</tr>
	</table>
</html:form>
<hr />
<page:form action="listSupplier${x_version}.do" method="post">
	<jsp:include page="../pageHead.jsp"/>
	<table class="data">
		<thead>
			<tr bgcolor="#9999ff">
				<th width="13%"><page:order order="code" style="text-decoration:none">
					<bean:message key="supplier.code" />
					<page:desc>
						<img src="images/down.gif" border="0" />
					</page:desc>
					<page:asc>
						<img src="images/up.gif" border="0" />
					</page:asc>
				</page:order></th>

				<th width="20%"><page:order order="name"
					style="text-decoration:none">
					<bean:message key="supplier.name" />
					<page:desc>
						<img src="images/down.gif" border="0" />
					</page:desc>
					<page:asc>
						<img src="images/up.gif" border="0" />
					</page:asc>
				</page:order></th>
				<th width="10%"><bean:message key="supplier.telephone" /></th>
				<th width="10%"><bean:message key="supplier.fax" /></th>
				<th width="7%"><bean:message key="supplier.post" /></th>
				<th width="12%"><bean:message key="supplier.contactor" /></th>
				<th width="10%"><bean:message key="supplier.contractStatus" /></th>
				<th width="13%">
				<page:order order="promoteStatus"
					style="text-decoration:none">
					<bean:message key="supplier.promoteStatus" />
					<page:desc>
						<img src="images/down.gif" border="0" />
					</page:desc>
					<page:asc>
						<img src="images/up.gif" border="0" />
					</page:asc>
				</page:order></th>
				<th width="5%">
				<page:order order="enabled"
					style="text-decoration:none">
					<bean:message key="supplier.status" />
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
			<logic:iterate id="X_OBJECT" name="X_RESULTLIST">
				<tr id="r${X_OBJECT.code}">
				  <td>
					  <a href='javascript:edit("${X_OBJECT.id}")'>${X_OBJECT.code}</a>
				  </td>
				  <td>${X_OBJECT.name}</td>
				  <td>${X_OBJECT.telephone1}</td>
				  <td>${X_OBJECT.fax1}</td>
				  <td>${X_OBJECT.post}</td>
				  <td>${X_OBJECT.contact}</td>
				  <td>
				  	<span style="color:${X_OBJECT.contractStatus.color}">
				      <c:if test="${sessionScope.LOGIN_USER.locale=='en'}">${X_OBJECT.contractStatus.engShortDescription}</c:if>
				      <c:if test="${sessionScope.LOGIN_USER.locale!='en'}">${X_OBJECT.contractStatus.chnShortDescription}</c:if>
				    </span>  
				  </td>
				  <td>
				  	<span style="color:${X_OBJECT.promoteStatus.color}">
				      <c:if test="${sessionScope.LOGIN_USER.locale=='en'}">${X_OBJECT.promoteStatus.engShortDescription}</c:if>
				      <c:if test="${sessionScope.LOGIN_USER.locale!='en'}">${X_OBJECT.promoteStatus.chnShortDescription}</c:if>
				    </span>  
				  </td>
				  <td>
					<span style="color:${X_OBJECT.enabled.color}">
				      <c:if test="${sessionScope.LOGIN_USER.locale=='en'}">${X_OBJECT.enabled.engShortDescription}</c:if>
				      <c:if test="${sessionScope.LOGIN_USER.locale!='en'}">${X_OBJECT.enabled.chnShortDescription}</c:if>
				    </span> 
				  
				  </td>
				</tr>
			</logic:iterate>
		</tbody>
	</table>
	<jsp:include page="../pageTail.jsp"/>	
</page:form>

<script type="text/javascript">
    applyRowStyle(document.all('datatable'));
</script>

