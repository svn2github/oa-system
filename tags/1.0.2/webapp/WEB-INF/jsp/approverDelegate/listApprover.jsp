<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>

<script type="text/javascript">
<!--
	function edit(aId) {
		with(document.approverQueryForm)
		{
			if(ruleType.value=="") return;
			 window.location="listApproverDelegate.do?originalApprover_id="+aId+"&ruleType="+ruleType.value;
		}
	}
	
//-->
</script>

<xml id="data">
<data>

<c:if test="${x_version!='_department'}">
<site id="" name="<bean:message key="all.selectAll"/>">
<department id="" name="<bean:message key="all.selectAll"/>">
</department>
</site>
</c:if>
<logic:iterate id="x_site" name="x_siteList">
	<site id="${x_site.id}" name="<bean:write name="x_site" property="name"/>">
	<c:if test="${x_version!='_department'}">
	<department id="" name="<bean:message key="all.selectAll"/>">
	</department>
	</c:if>
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


<html:form action="listApprover${x_version}">
	<html:hidden property="order" />
	<html:hidden property="descend" />

	<input type="hidden" name="siteId_value"
		value="<bean:write name="approverQueryForm" property="siteId"/>" />
	<input type="hidden" name="departmentId_value"
		value="<bean:write name="approverQueryForm" property="departmentId"/>" />

	<table  border=0 cellpadding=4 cellspacing=0>
		<tr>
			<td class="bluetext" ><bean:message
				key="approverDelegate.site" />:</td>
			<td><html:select property="siteId">
			</html:select></td>
			
			<td class="bluetext" ><bean:message
				key="approverDelegate.department" />:</td>
			<td><html:select property="departmentId">
			</html:select></td>
		</tr>	
		<tr>
			<td class="bluetext" ><bean:message
				key="approverDelegate.listApprover.name" />:</td>
			<td><html:text property="name"/></td>
		
			<td class="bluetext" ><bean:message
				key="approverDelegate.ruleType" />:</td>
			<td><html:select property="ruleType">
				<html:options collection="x_ruleTypeList" property="enumCode"
					labelProperty="label" />
			</html:select></td>
			
			<td align="right" colspan="2"><html:submit>
				<bean:message key="all.query" />
			</html:submit></td>
		</tr>

	</table>
</html:form>
<hr />

<page:form action="listApprover${x_version}" method="post">
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
			<logic:iterate id="X_OBJECT" name="X_RESULTLIST">
				<tr>
					<td>${X_OBJECT.name}</td>
					<td><a href="mailto:${X_OBJECT.email}">${X_OBJECT.email}</a></td>
					<td>${X_OBJECT.telephone}</td>
					<td><a href="javascript:edit(${X_OBJECT.id})">
						<bean:message key="approverDelegate.edit.title"/>
					</a>
				</tr>
			</logic:iterate>
		</tbody>
	</table>
	<jsp:include page="../pageTail.jsp"/>
</page:form>

<script type="text/javascript">

    var mapping=new Map();
	mapping.put("siteId","site");
	mapping.put("departmentId","department");
    initCascadeSelect("config","data","approverQueryForm",mapping,true);
    applyRowStyle(document.all('datatable'));
</script>


