<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>

<script type="text/javascript">
<!--
	function view(id) {
		var url="viewPurchaseOrder_cancel.do?id="+id;
		window.location.href=url;
	}
	
	function validateForm(form) {
		if(!validatePurchaseOrderQueryForm(form)) return false;
		return true;
	}
//-->
</script>

<div class="message"><html:messages id="x_message" message="true">${x_message}<br></html:messages></div>

<html:javascript formName="purchaseOrderQueryForm" staticJavascript="false"/>
<html:form action="/listPurchaseOrder_cancel" focus="id" onsubmit="return validateForm(this)">
	<html:hidden property="order" />
	<html:hidden property="descend" />
	
	<table  border=0 cellpadding=4 cellspacing=0>
		<tr>
			<td class="bluetext" ><bean:message key="purchaseOrder.search.site"/>&nbsp;</td>
			<td colspan="4">
				<html:select property="site_id">
					<html:options collection="x_siteList" property="id"
						labelProperty="name" />
				</html:select>
			</td>

		</tr>
		<tr>
			<td class="bluetext" ><bean:message key="purchaseOrder.id"/>&nbsp;</td>
			<td><html:text property="id" size="13"/></td>
			<td class="bluetext" ><bean:message key="purchaseOrder.supplier"/>&nbsp;</td>
			<td><html:text property="supplier_name" /></td>
			<td>
				<html:submit><bean:message key="all.query"/></html:submit>
			</td>
		</tr>
	</table>
</html:form>
<hr />
<page:form action="listPurchaseOrder_cancel.do" method="post">
	<jsp:include page="../pageHead.jsp"/>
	<table class="data">
		<thead>
			<tr bgcolor="#9999ff">

				<th width="180"><page:order order="id" style="text-decoration:none">
					<bean:message key="purchaseOrder.id" />
					<page:desc>
						<img src="images/down.gif" border="0" />
					</page:desc>
					<page:asc>
						<img src="images/up.gif" border="0" />
					</page:asc>
				</page:order></th>
				

				<th width="120"><page:order order="supplier_name" style="text-decoration:none">
					<bean:message key="purchaseOrder.supplier" />
					<page:desc>
						<img src="images/down.gif" border="0" />
					</page:desc>
					<page:asc>
						<img src="images/up.gif" border="0" />
					</page:asc>
				</page:order></th>


				<th ><page:order order="category_descrption"
					style="text-decoration:none">
					<bean:message key="purchaseOrder.category" />
					<page:desc>
						<img src="images/down.gif" border="0" />
					</page:desc>
					<page:asc>
						<img src="images/up.gif" border="0" />
					</page:asc>
				</page:order></th>
				
				<th ><page:order order="subCategory_descrption"
					style="text-decoration:none">
					<bean:message key="purchaseOrder.subCategory" />
					<page:desc>
						<img src="images/down.gif" border="0" />
					</page:desc>
					<page:asc>
						<img src="images/up.gif" border="0" />
					</page:asc>
				</page:order></th>
				
				<th >
					<bean:message key="purchaseOrder.amount" />
				</th>
				
				<th width="80"><page:order order="createDate"
					style="text-decoration:none">
					<bean:message key="purchaseOrder.createDate" />
					<page:desc>
						<img src="images/down.gif" border="0" />
					</page:desc>
					<page:asc>
						<img src="images/up.gif" border="0" />
					</page:asc>
				</page:order></th>
				
				<th width="80"><page:order order="createUser_name"
					style="text-decoration:none">
					<bean:message key="purchaseOrder.search.createUser" />
					<page:desc>
						<img src="images/down.gif" border="0" />
					</page:desc>
					<page:asc>
						<img src="images/up.gif" border="0" />
					</page:asc>
				</page:order></th>
				
				<th width="80"><page:order order="status"
					style="text-decoration:none">
					<bean:message key="purchaseOrder.status" />
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
				<jsp:include page="row.jsp" />
			</logic:iterate>
		</tbody>
	</table>

</page:form>

<script type="text/javascript">
    applyRowStyle(document.all('datatable'));
</script>

