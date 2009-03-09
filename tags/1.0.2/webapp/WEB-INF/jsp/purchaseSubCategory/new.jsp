<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>
<script type="text/javascript">
<!--
	function selectSupplier() {
	<logic:notEmpty name="x_version">
		v = window.showModalDialog(
			'showDialog.do?title=supplier.selectSupplier.title&selectSupplier.do?site_id=${x_site_id}&includeGlobal=true', 
			null, 'dialogWidth:800px;dialogHeight:650px;status:no;help:no;scroll:no');
	</logic:notEmpty>		
	<logic:empty name="x_version">
		v = window.showModalDialog(
			'showDialog.do?title=supplier.selectSupplier.title&selectSupplier.do', 
			null, 'dialogWidth:800px;dialogHeight:650px;status:no;help:no;scroll:no');
	</logic:empty>		
		if (v) {
			var pos=v.indexOf(",");
			var spId=v.substring(0,pos);
			var spName=v.substring(pos+1,v.length);
			document.getElementById("Supplier").innerHTML=spName;
			document.getElementById("defaultSupplier_id").value=spId;
			
		};
	}
	
	<logic:notEmpty name="x_version">
	function selectUser() {
		v = window.showModalDialog(
			'showDialog.do?title=purchaseSubCategory.selectInspector.title&selectInspector_site.do?siteId=${x_site_id}', 
			null, 'dialogWidth:650px;dialogHeight:500px;status:no;help:no;scroll:no');
		if (v) {
			purchaseSubCategoryForm.inspector_id.value = v['id'];
			document.getElementById('inspector_name').innerHTML = v['name'];
		};
	}
	</logic:notEmpty>
	
//-->
</script>	
<html:javascript formName="purchaseSubCategoryForm" staticJavascript="false"/>
<html:form action="/insertPurchaseSubCategory${x_version}"  method="post" onsubmit="return validatePurchaseSubCategoryForm(this);">
	<table width=90% border=0 cellpadding=4 cellspacing=0>
		<tr>
			<td class="bluetext"><bean:message key="purchaseSubCategory.id" />:</td>
			<td>(<bean:message key="common.id.generateBySystem" />)</td>
		</tr>
		<tr>
			<td class="bluetext"><bean:message key="purchaseSubCategory.purchaseCategory" />:</td>
			<td>
				<html:hidden property="purchaseCategory_id" />
				<bean:write property="purchaseCategory_description" name="purchaseSubCategoryForm"/>
			</td>
		</tr>			
		<tr>
			<td class="bluetext"><bean:message key="purchaseSubCategory.defaultSupplier" />:</td>
			<td>
				<html:hidden property="defaultSupplier_id" value=""/>
				<span id="Supplier"></span>
				<a href="javascript:selectSupplier();"><img src="images/select.gif" border="0"/></a>
				</td>
		</tr>			
		<tr>
			<td class="bluetext"><bean:message key="purchaseSubCategory.description" />:</td>
			<td><html:text property="description" /><span class="required">*</span></td>
		</tr>			
		<logic:notEmpty name="x_version">
		<tr>
		  <td class="bluetext"><bean:message key="purchaseSubCategory.inspector"/>:</td>
		  <td>
		    <html:hidden property="inspector_id"/>
		    <span id="inspector_name"><bean:write name="purchaseSubCategoryForm" property="inspector_name"/></span>
		    &nbsp;<a href="javascript:selectUser()"><img src="images/select.gif" width="16" height="16" border="0"/></a>
		  </td>
		</tr>
		</logic:notEmpty>
		<tr>
			<td class="bluetext"><bean:message key="purchaseSubCategory.status"/>:</td>
			  <td>
			    <html:select property="enabled">
			      <c:if test="${sessionScope.LOGIN_USER.locale=='zh'}"><html:options collection = "X_ENABLEDDISABLEDLIST" property = "enumCode" labelProperty = "chnShortDescription"/></c:if>
			      <c:if test="${sessionScope.LOGIN_USER.locale!='zh'}"><html:options collection = "X_ENABLEDDISABLEDLIST" property = "enumCode" labelProperty = "engShortDescription"/></c:if>
			    </html:select>
			  </td>
		</tr>			

		<tr>
			<td colspan="2" align="right"><html:submit>
				<bean:message key="all.save" />
			</html:submit></td>
		</tr>
	</table>
</html:form>
