<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>

<script type="text/javascript">
<!--
	function selectDelegate(id,name) {
		if(id+""==document.approverQueryForm.originalApprover_id.value)
		{
			alert("<bean:message key="approverDelegate.select.notSelf"/>");
			return;
		}
		var result = [];
		result['id'] = id;
		result['name'] = name;
		window.parent.returnValue = result;
		window.parent.close();		
	}
	
//-->
</script>

<xml id="data">
<data>

<site id="" name="<bean:message key="all.selectAll"/>">
<department id="" name="<bean:message key="all.selectAll"/>">
</department>
</site>

<logic:iterate id="x_site" name="x_siteList">
	<site id="${x_site.id}" name="<bean:write name="x_site" property="name"/>">
	<department id="" name="<bean:message key="all.selectAll"/>">
	</department>
	<logic:iterate id="x_d" name="x_site" property="departments">
		<department id="${x_d.id}" name="<bean:write name="x_d" property="name"/>">
		</department>
	</logic:iterate>
	</site>
</logic:iterate>
</data>
</xml>

<xml id="config">
<config>
<site>
<department>
</department>
</site>
</config>
</xml>


<html:form action="/selectDelegateApprover${x_postfix}">
	
	<html:hidden property="ruleType" />
	<html:hidden property="order" />
	<html:hidden property="descend" />

	<input type="hidden" name="siteId_value"
		value="<bean:write name="approverQueryForm" property="siteId"/>" />
	<input type="hidden" name="departmentId_value"
		value="<bean:write name="approverQueryForm" property="departmentId"/>" />

	<table  border=0 cellpadding=4 cellspacing=0>
		<tr>
			<td class="bluetext" ><bean:message
				key="approverDelegate.originalApprover.id" />:</td>
			<td>${x_originalApprover.name}<html:hidden
				property="originalApprover_id" /></td>
			<td>
		</tr>	
		<tr>
			<td class="bluetext" ><bean:message
				key="approverDelegate.site" />:</td>
			<td><html:select property="siteId">
			</html:select></td>
		</tr>			
		<tr>
			<td class="bluetext" ><bean:message
				key="approverDelegate.listApprover.name" />:</td>
			<td><html:text property="name"/></td>		
		
			<td class="bluetext" ><bean:message
				key="approverDelegate.department" />:</td>
			<td><html:select property="departmentId">
			</html:select></td>
			
			<td><html:submit>
				<bean:message key="all.query" />
			</html:submit></td>
		</tr>

	</table>
</html:form>
<hr />

<page:form action="/selectDelegateApprover${x_postfix}" method="post">
	<jsp:include page="../pageHead.jsp"/>
	<table class="data">
		<thead>
			<tr bgcolor="#9999ff">
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
			<logic:iterate id="X_OBJECT" name="X_RESULTLIST">
				<tr>
					<td>${X_OBJECT.name}</td>
					<td><a href="mailto:${X_OBJECT.email}">${X_OBJECT.email}</a></td>
					<td>${X_OBJECT.telephone}</td>
					<td><a href='javascript:selectDelegate("${X_OBJECT.id}","${X_OBJECT.name}")'>
						<bean:message key="all.select"/>
					</a>
				</tr>
			</logic:iterate>
		</tbody>
	</table>
</page:form>

<script type="text/javascript">

    var mapping=new Map();
	mapping.put("siteId","site");
	mapping.put("departmentId","department");
    initCascadeSelect("config","data","approverQueryForm",mapping,true);
    applyRowStyle(document.all('datatable'));
</script>


