<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>

<script type="text/javascript">
<!--
	function add() {
		var url="newPurchaseRequest${x_postfix}.do";
		window.location.href=url;
	}

	function edit(id) {
		var url="editPurchaseRequest${x_postfix}.do?id="+id;
		window.location.href=url;
	}
	
	function view(id) {
		var url="viewPurchaseRequest${x_postfix}.do?id="+id;
		window.location.href=url;
	
	}	
	
	function validateForm(form)
	{
		if(!validatePurchaseRequestQueryForm(form)) return false;
		return true;
	}
	
	function copy(id) {
		if(confirm("<bean:message key="purchaseRequest.copy.confirm"/>")) {
			var url="copyPurchaseRequest${x_postfix}.do?id="+ id;
			window.location=url;
		}
	}
	
//-->
</script>

<div class="message"><html:messages id="x_message" message="true">${x_message}<br></html:messages></div>

<html:javascript formName="purchaseRequestQueryForm" staticJavascript="false"/>
<html:form action="listPurchaseRequest${x_postfix}" focus="id" onsubmit="return validateForm(this)">
	<html:hidden property="order" />
	<html:hidden property="descend" />
	
	<table  border=0 cellpadding=4 cellspacing=0>
		<tr>
			<td class="bluetext" ><bean:message key="purchaseRequest.search.site"/>&nbsp;</td>
			<td colspan="8">
				<html:select property="site_id">
					<html:options collection="x_siteList" property="id"
						labelProperty="name" />
				</html:select>
			</td>

		</tr>
		<tr>
			<td><bean:message key="purchaseRequest.id"/>&nbsp;</td>
			<td><html:text property="id" size="13"/></td>
			<td><bean:message key="purchaseRequest.title"/>&nbsp;</td>
			<td><html:text property="title" size="14"/></td>
			<td><bean:message key="purchaseRequest.status"/></td>
			<td>
				 <html:select property="status">
		           <html:option value=""><bean:message key="all.all"/></html:option>
		           <html:options collection="x_statusList" property="enumCode" labelProperty="${x_lang}ShortDescription" />
		         </html:select>
			</td>
			<td>
				<html:submit><bean:message key="all.query"/></html:submit>&nbsp;&nbsp;&nbsp;
				<input type="button" value="<bean:message key="all.new"/>"
					 onClick="add()">&nbsp;&nbsp;&nbsp;
			</td>
		</tr>
	</table>
</html:form>
<hr />
<page:form action="listPurchaseRequest${x_postfix}.do" method="post">
	<jsp:include page="../pageHead.jsp"/>
	<table class="data">
		<thead>
			<tr class="new_bg">
				<th width="180"><page:order order="id" style="text-decoration:none">
					<bean:message key="purchaseRequest.id" />
					<page:desc>
						<img src="images/down.gif" border="0" />
					</page:desc>
					<page:asc>
						<img src="images/up.gif" border="0" />
					</page:asc>
				</page:order></th>
				
				<c:if test="${x_postfix=='_other'}">
					<th width="120"><page:order order="requestor_name" style="text-decoration:none">
						<bean:message key="purchaseRequest.requestor.id" />
						<page:desc>
							<img src="images/down.gif" border="0" />
						</page:desc>
						<page:asc>
							<img src="images/up.gif" border="0" />
						</page:asc>
					</page:order></th>
				</c:if>

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
				
				<th ><page:order order="amount"
					style="text-decoration:none">
					<bean:message key="purchaseRequest.amount" />
					<page:desc>
						<img src="images/down.gif" border="0" />
					</page:desc>
					<page:asc>
						<img src="images/up.gif" border="0" />
					</page:asc>
				</page:order></th>
				
				<th width="80"><page:order order="createDate"
					style="text-decoration:none">
					<bean:message key="purchaseRequest.createDate" />
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
				<th>
					<bean:message key="all.copy" />
				</th>
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

