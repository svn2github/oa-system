<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>

<script type="text/javascript">
<!--
	function assign(id) {
		var url="assignPurchaseOrder.do?id="+id;
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
<html:form action="listPurchaseOrder_assign" focus="id" onsubmit="return validateForm(this)">
	<html:hidden property="order" />
	<html:hidden property="descend" />
	
	<table width="100%" border=0 cellpadding=4 cellspacing=0>
		<tr>
			<td class="bluetext" ><bean:message key="purchaseOrder.search.site"/>:</td>
			<td colspan="4">
				<html:select property="site_id">
					<html:options collection="x_siteList" property="id"	labelProperty="name" />
				</html:select>
			</td>
		</tr>
		<tr>
			<td class="bluetext" ><bean:message key="purchaseOrder.id"/>:</td>
			<td><html:text property="id" size="13"/></td>
			<td class="bluetext" ><bean:message key="purchaseOrder.title"/>:</td>
			<td colspan="2"><html:text property="title" size="14"/></td>
		</tr>
		<tr>
			<td class="bluetext" ><bean:message key="purchaseOrder.purchaser.id"/>:</td>
			<td><html:text property="purchaser_name" size="14"/></td>
			<td class="bluetext" ><bean:message key="purchaseOrder.status"/>:</td>
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
<page:form action="listPurchaseOrder_assign.do" method="post">
	<jsp:include page="../pageHead.jsp"/>
	<table class="data">
		<thead>
			<tr class="new_bg">
				<th width="120"><page:order order="id" style="text-decoration:none">
					<bean:message key="purchaseOrder.id" />
					<page:desc>
						<img src="images/down.gif" border="0" />
					</page:desc>
					<page:asc>
						<img src="images/up.gif" border="0" />
					</page:asc>
				</page:order></th>
				

				<th width="120"><page:order order="purchaser_name" style="text-decoration:none">
					<bean:message key="purchaseOrder.purchaser.id" />
					<page:desc>
						<img src="images/down.gif" border="0" />
					</page:desc>
					<page:asc>
						<img src="images/up.gif" border="0" />
					</page:asc>
				</page:order></th>

				<th ><page:order order="title"
					style="text-decoration:none">
					<bean:message key="purchaseOrder.title" />
					<page:desc>
						<img src="images/down.gif" border="0" />
					</page:desc>
					<page:asc>
						<img src="images/up.gif" border="0" />
					</page:asc>
				</page:order></th>
				
				<th width="80"><page:order order="requestDate"
					style="text-decoration:none">
					<bean:message key="purchaseOrder.requestDate" />
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
				<jsp:include page="assignRow.jsp" />
			</logic:iterate>
		</tbody>
	</table>

</page:form>

<script type="text/javascript">
    applyRowStyle(document.all('datatable'));
</script>

