<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>
<%@ page import="java.util.Set"%>


<script type="text/javascript">
<!--
	function select(id,name) {
		var result = [];
		result['id'] = id;
		result['name'] = name;
		window.parent.returnValue = result;
		window.parent.close();		
	}
	
//-->
</script>

<html:form action="/selectInspector_purchaseOrderConfirm">
	<html:hidden property="order" />
	<html:hidden property="descend" />
	<input type="hidden" name="id"  value="${x_po.id}"/>
	<table  border=0 cellpadding=4 cellspacing=0>
		<!-- <tr>
			<td class="bluetext" ><bean:message
				key="purchaseOrder.confirm.selectInspector.site" />:</td>
			<td>${x_po.site.name}</td>
		</tr> --> 			
		<tr>
			<td class="bluetext" ><bean:message
				key="purchaseOrder.confirm.selectInspector.name" />:</td>
			<td><html:text property="name"/></td>		
		
			<td class="bluetext" ><bean:message
				key="purchaseOrder.confirm.selectInspector.department" />:</td>
			<td><html:select property="departmentId">
				<html:option value=""><bean:message key="all.all" /></html:option>
				<html:options collection="x_departmentList"
								property="id" labelProperty="name" />
			</html:select></td>
			
			<td><html:submit>
				<bean:message key="all.query" />
			</html:submit></td>
		</tr>

	</table>
</html:form>
<hr />

<page:form action="/selectInspector_purchaseOrderConfirm" method="post">
	<input type="hidden" name="id"  value="${x_po.id}"/>
	<jsp:include page="../pageHead.jsp"/>
	<table class="data">
		<thead>
			<tr class="new_bg">
				<th width="20%"><page:order order="name" style="text-decoration:none">
					<bean:message key="user.name" />
					<page:desc>
						<img src="images/down.gif" border="0" />
					</page:desc>
					<page:asc>
						<img src="images/up.gif" border="0" />
					</page:asc>
				</page:order></th>
				
				<th width="30%"><page:order order="email" style="text-decoration:none">
					<bean:message key="user.email" />
					<page:desc>
						<img src="images/down.gif" border="0" />
					</page:desc>
					<page:asc>
						<img src="images/up.gif" border="0" />
					</page:asc>
				</page:order></th>				

				
				<th width="30%"><page:order order="telephone" style="text-decoration:none">
					<bean:message key="user.telephone" />
					<page:desc>
						<img src="images/down.gif" border="0" />
					</page:desc>
					<page:asc>
						<img src="images/up.gif" border="0" />
					</page:asc>
				</page:order></th>				
				<th>&nbsp;</th>				
			</tr>
		</thead>

		<tbody id="datatable">
<%
	Set requestorSet=(Set)request.getAttribute("x_requestorSet");
%>		
			<logic:iterate id="X_OBJECT" name="X_RESULTLIST">
<%
	Object user=pageContext.getAttribute("X_OBJECT",PageContext.PAGE_SCOPE);
%>			
				<tr>
					<td>${X_OBJECT.name}</td>
					<td><a href="mailto:${X_OBJECT.email}">${X_OBJECT.email}</a></td>
					<td>${X_OBJECT.telephone}</td>
					<td>
<%	if(!requestorSet.contains(user)){%>						
						<a href='javascript:select("${X_OBJECT.id}","${X_OBJECT.name}")'>
							<bean:message key="all.select"/>
						</a>
<%}%>						
					</td>
				</tr>
			</logic:iterate>
		</tbody>
	</table>
</page:form>

<script type="text/javascript">
    applyRowStyle(document.all('datatable'));
</script>



