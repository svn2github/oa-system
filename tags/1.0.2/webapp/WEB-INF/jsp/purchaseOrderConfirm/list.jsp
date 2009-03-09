<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>

<script type="text/javascript">
<!--

	function editInsecptor(id) {
		var url="editConfirmedPurchaseOrder.do?id="+id;
		window.location.href=url;
	}

	function confirm(id) {
		var url="confirmPurchaseOrder.do?id="+id;
		window.location.href=url;
	}
	
	function view(id) {
		var url="viewPurchaseOrder.do?id="+id;
		window.location.href=url;
	}
	
	function validateForm(form)
	{
		if(!validatePurchaseOrderQueryForm(form)) return false;
		return true;
	}
//-->
</script>

<div class="message"><html:messages id="x_message" message="true">${x_message}<br></html:messages></div>

<html:javascript formName="purchaseOrderQueryForm" staticJavascript="false"/>
<html:form action="/listPurchaseOrder_confirm" focus="id" onsubmit="return validateForm(this)">
	<html:hidden property="order" />
	<html:hidden property="descend" />
	
	<table  border=0 cellpadding=4 cellspacing=0>
		<tr>
			<td class="bluetext" ><bean:message key="purchaseOrder.search.site"/>&nbsp;</td>
			<td colspan="3">
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
		</tr>
		<tr>
			<td class="bluetext" ><bean:message key="purchaseOrder.confirmDate"/>&nbsp;</td>
			<td>
				<table border=0 cellpadding=0 cellspacing=0>
			  		<tr>
						<td>
							<html:text property="confirmDate1" size="6"  maxlength="10" />
						</td>
						<td>
							<a onclick="event.cancelBubble=true;" href="javascript:showCalendar('dimg1',false,'confirmDate1',null,null,'purchaseOrderQueryForm')"><IMG align="absMiddle" border="0" id="dimg1" src="images/datebtn.gif" ></A>
						</td>
						<td width="20" align="center">~</td>
						<td>
							<html:text property="confirmDate2" size="6"  maxlength="10" />
						</td>
						<td>
							<a onclick="event.cancelBubble=true;" href="javascript:showCalendar('dimg2',false,'confirmDate2',null,null,'purchaseOrderQueryForm')"><IMG align="absMiddle" border="0" id="dimg2" src="images/datebtn.gif" ></A>
						</td>
			  		</tr>
		  		</table>
			</td>
			<td class="bluetext" ><bean:message key="purchaseOrder.status"/></td>
			<td>
				 <html:select property="status">
		           <html:option value=""><bean:message key="all.all"/></html:option>
		           <html:options collection="x_statusList" property="enumCode" labelProperty="${x_lang}ShortDescription" />
		         </html:select>
			</td>
		</tr>
		<tr>
			<td colspan="4" align="right">
				<html:submit><bean:message key="all.query"/></html:submit>
			</td>
		</tr>
	</table>
</html:form>
<hr />
<page:form action="listPurchaseOrder_confirm.do" method="post">
	<jsp:include page="../pageHead.jsp"/>
	<table class="data">
		<thead>
			<tr class="new_bg">

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

				<th width="80"><page:order order="confirmDate"
					style="text-decoration:none">
					<bean:message key="purchaseOrder.search.confirmDate" />
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
	<jsp:include page="../pageTail.jsp"/>
</page:form>

<script type="text/javascript">
    applyRowStyle(document.all('datatable'));
</script>

