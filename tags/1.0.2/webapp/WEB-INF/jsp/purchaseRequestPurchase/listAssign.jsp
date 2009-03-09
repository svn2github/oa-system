<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>

<script type="text/javascript">
<!--
	function assign(id) {
		var url="assignPurchaseRequest.do?id="+id;
		window.location.href=url;
	}
	
	function validateForm(form) {
		if(!validatePurchaseRequestQueryForm(form)) return false;
		return true;
	}
	
	function changeSite() {
		document.purchaseRequestQueryForm.submit();
	}
	
	function comboDepartmentChange() {
		with(document.purchaseRequestQueryForm) {
			var selectedOption = department_id.options(department_id.selectedIndex);
			if (selectedOption.style.color=="gray") {
				alert('<bean:message key="purchaseRequest.department.noPower"/>');
				department_id.value=old_department_id.value
			} else {
				old_department_id.value=department_id.value
			}
		}
	}
//-->
</script>

<div class="message"><html:messages id="x_message" message="true">${x_message}<br></html:messages></div>

<html:javascript formName="purchaseRequestQueryForm" staticJavascript="false"/>
<html:form action="listPurchaseRequest_assign" focus="id" onsubmit="return validateForm(this)">
	<html:hidden property="order" />
	<html:hidden property="descend" />
	<input type="hidden" name="old_department_id" value="<bean:write name="purchaseRequestQueryForm" property="department_id"/>">
	
	<table width="100%" border=0 cellpadding=4 cellspacing=0>
		<tr>
			<td class="bluetext" ><bean:message key="purchaseRequest.search.site"/>:</td>
			<td>
				<html:select property="site_id" onchange="changeSite()">
					<html:options collection="x_siteList" property="id"	labelProperty="name" />
				</html:select>
			</td>
			<td class="bluetext" ><bean:message key="purchaseRequest.search.department"/>:</td>
			<td colspan="2">
				<html:select property="department_id" onchange="comboDepartmentChange()"> 
					<logic:iterate name="x_departmentList" id="x_department">
						<c:set var="x_style" value=""/>				
						<c:if test="${!x_department.granted}">				
							<c:set var="x_style" value="color:gray"/>				
						</c:if>
						<html:option style="${x_style}" value="${x_department.id}">${x_department.indentName}</html:option>
					</logic:iterate>
				</html:select>
			</td>
		</tr>
		<tr>
			<td class="bluetext" ><bean:message key="purchaseRequest.id"/>:</td>
			<td><html:text property="id" size="13"/></td>
			<td class="bluetext" ><bean:message key="purchaseRequest.title"/>:</td>
			<td colspan="2"><html:text property="title" size="14"/></td>
		</tr>
		<tr>
			<td class="bluetext" ><bean:message key="purchaseRequest.search.requestorName"/>:</td>
			<td><html:text property="requestor_name" size="14"/></td>
			<td class="bluetext" ><bean:message key="purchaseRequest.status"/>:</td>
			<td>
				 <html:select property="status">
		           <html:option value=""><bean:message key="all.all"/></html:option>
		           <html:options collection="x_statusList" property="enumCode" labelProperty="${x_lang}ShortDescription" />
		         </html:select>
			</td>
			<td>
				<html:submit><bean:message key="all.query"/></html:submit>
			</td>
		</tr>
	</table>
</html:form>
<hr />
<page:form action="listPurchaseRequest_assign" method="post">
	<jsp:include page="../pageHead.jsp"/>
	<table class="data">
		<thead>
			<tr class="new_bg">
				<th width="120"><page:order order="id" style="text-decoration:none">
					<bean:message key="purchaseRequest.id" />
					<page:desc>
						<img src="images/down.gif" border="0" />
					</page:desc>
					<page:asc>
						<img src="images/up.gif" border="0" />
					</page:asc>
				</page:order></th>
				

				<th width="120"><page:order order="requestor_name" style="text-decoration:none">
					<bean:message key="purchaseRequest.requestor.id" />
					<page:desc>
						<img src="images/down.gif" border="0" />
					</page:desc>
					<page:asc>
						<img src="images/up.gif" border="0" />
					</page:asc>
				</page:order></th>

				<th><bean:message key="purchaseRequest.department.id" /></th>

				<th ><page:order order="title"
					style="text-decoration:none">
					<bean:message key="purchaseRequest.title" />
					<page:desc>
						<img src="images/down.gif" border="0" />
					</page:desc>
					<page:asc>
						<img src="images/up.gif" border="0" />
					</page:asc>
				</page:order></th>
				
				<th width="80"><page:order order="requestDate"
					style="text-decoration:none">
					<bean:message key="purchaseRequest.requestDate" />
					<page:desc>
						<img src="images/down.gif" border="0" />
					</page:desc>
					<page:asc>
						<img src="images/up.gif" border="0" />
					</page:asc>
				</page:order></th>
				
				<th ><page:order order="purchaser_name"	style="text-decoration:none">
					<bean:message key="purchaseRequest.purchaser.id" />
					<page:desc>
						<img src="images/down.gif" border="0" />
					</page:desc>
					<page:asc>
						<img src="images/up.gif" border="0" />
					</page:asc>
				</page:order></th>

				<th width="80"><page:order order="status"
					style="text-decoration:none">
					<bean:message key="purchaseRequest.status" />
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
				<bean:define id="X_OBJECT" toScope="request" name="X_OBJECT" />
				<jsp:include page="assignRow.jsp" />
			</logic:iterate>
		</tbody>
	</table>

</page:form>

<script type="text/javascript">
    applyRowStyle(document.all('datatable'));
</script>

