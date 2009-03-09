<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>
<c:set var="x_supplier_promoteStatus_GLOBAL" value="<%=com.aof.model.metadata.SupplierPromoteStatus.GLOBAL%>"/>
<c:set var="x_supplier_promoteStatus_SITE" value="<%=com.aof.model.metadata.SupplierPromoteStatus.SITE%>"/>
<c:set var="x_supplier_promoteStatus_REQUEST" value="<%=com.aof.model.metadata.SupplierPromoteStatus.REQUEST%>"/>

<c:set var="modifyable" scope="request" value="${(x_version=='' && x_supplier.promoteStatus == x_supplier_promoteStatus_GLOBAL) || (x_version!='' && x_supplier.promoteStatus != x_supplier_promoteStatus_GLOBAL)}"/>

<xml id="data">
<data>
<category id="" name="<bean:message key="supplierItem.purchaseCategory.all"/>">
	<subcategory id="" name="<bean:message key="supplierItem.purchaseSubCategory.all"/>">
	</subcategory>
</category>
<logic:iterate id="x_purchaseCategory" name="x_purchaseCategoryList">
	<category id="${x_purchaseCategory.id}" name="<bean:write name="x_purchaseCategory" property="description"/>">
		<subcategory id="" name="<bean:message key="supplierItem.purchaseSubCategory.all"/>">
		</subcategory>
		<logic:iterate id="x_c" name="x_purchaseCategory" property="enabledPurchaseSubCategoryList">
			<subcategory id="${x_c.id}" name="<bean:write name="x_c" property="description"/>">
			</subcategory>
		</logic:iterate>
	</category>
</logic:iterate>
</data>
</xml>


<xml id="config">
<config>
<category>
<subcategory>
</subcategory>
</category>
</config>
</xml>

<script type="text/javascript">
<!--
	function add(form) {
		v = window.showModalDialog(
			'showDialog.do?title=supplierItem.new.title&newSupplierItem${x_version}.do?supplier_id=${x_supplier.id}'+
			'&category='+form.purchaseCategory_id.value+'&subcategory='+form.purchaseSubCategory_id.value, 
			null, 'dialogWidth:640px;dialogHeight:240px;status:no;help:no;scroll:no');
		if (v) {
			var table = document.all('datatable');
			appendRow(table, v);
		    applyRowStyle(table);
		};
	}

	function edit(id) {
		v = window.showModalDialog(
			'showDialog.do?title=supplierItem.edit.title&editSupplierItem${x_version}.do?id=' + id, 
			null, 'dialogWidth:640px;dialogHeight:240px;status:no;help:no;scroll:no');
		if (v) {
			updateRow(document.all('r' + id), v);
		};
	}
	
	function del(id) {
		var v = window.showModalDialog(
		    'showDialog.do?title=supplierItem.delete.title&confirmOperationDialog.do?message=supplierItem.delete.confirm&deleteSupplierItem${x_version}.do?id=' + id, 
		    null, 'dialogWidth:300px;dialogHeight:170px;status:no;help:no;scroll:no');
		if (v) {
			var table = document.all('datatable');
			deleteRow(table.all('r' + id));
		    applyRowStyle(table);
		}
	}
	
//-->
</script>

<html:form action="listSupplierItem${x_version}">
<input type="hidden" name="purchaseCategory_id_value"
	value="<bean:write name="supplierItemQueryForm" property="purchaseCategory_id"/>" />
<input type="hidden" name="purchaseSubCategory_id_value"
	value="<bean:write name="supplierItemQueryForm" property="purchaseSubCategory_id"/>" />
<html:hidden property="order"/>
<html:hidden property="descend"/>
<html:hidden property="backPage" />
<html:hidden property="fromPO" />
<html:hidden property="supplier_id" value="${x_supplier.id}"/>
<table width="90%" border="0" cellpadding="4" cellspacing="0">
  <tbody>
    <tr>
	    <td class="bluetext" width="15%"><bean:message key="supplier.code" />:</td>
		<td align="left">${x_supplier.code}</td>
		<td class="bluetext" width="20%"><bean:message key="supplier.description" />:</td>
		<td align="left">${x_supplier.name}</td>
		<td></td>
	</tr>
	<tr>
      <td class="bluetext"><bean:message key="supplierItem.purchaseSubCategory.purchaseCategory.id"/>:</td>
      <td><html:select property="purchaseCategory_id"></html:select></td>
      <td class="bluetext"><bean:message key="supplierItem.purchaseSubCategory.id"/>:</td>
      <td><html:select property="purchaseSubCategory_id"></html:select></td>
      <td align="center">
        <html:submit><bean:message key="all.query"/></html:submit>
        <c:if test="${modifyable}">
	        <input type="button" value="<bean:message key="supplierItem.addNew"/>" onclick="add(this.form);"/>
        </c:if>
        <logic:equal name="x_fromPO" value="true">
	        <input type="button" value="<bean:message key="all.back"/>" onclick='window.location.href="confirmSupplier_purchaseOrder.do?id=${x_supplier.id}";'/>
		</logic:equal>
		<logic:notEqual name="x_fromPO" value="true">
	       	<input type="button" value="<bean:message key="all.back"/>" onclick='window.location.href="${x_back}Supplier${x_version}.do?id=${x_supplier.id}";'/>
        </logic:notEqual>
        
        
      </td>
    </tr>
  </tbody>
</table>
</html:form>
<hr/>
<page:form action="listSupplierItem${x_version}.do" method="post">	
  <jsp:include page="../pageHead.jsp"/>
  <table class="data">
    <thead>
      <tr class="new_bg">
        <th width="20%">
          <page:order order="category" style="text-decoration:none">
            <bean:message key="supplierItem.purchaseSubCategory.purchaseCategory.id"/>
            <page:desc><img src="images/down.gif" border="0"/></page:desc>
            <page:asc><img src="images/up.gif" border="0"/></page:asc>
          </page:order>
        </th>
	    <th width="20%">
   	      <page:order order="subCategory" style="text-decoration:none">
            <bean:message key="supplierItem.purchaseSubCategory.id"/>
            <page:desc><img src="images/down.gif" border="0"/></page:desc>
            <page:asc><img src="images/up.gif" border="0"/></page:asc>
          </page:order>
	    </th>
	    <th width="25%">
	    	<page:order order="sepc" style="text-decoration:none">
    	        <bean:message key="supplierItem.sepc"/>
        	    <page:desc><img src="images/down.gif" border="0"/></page:desc>
            	<page:asc><img src="images/up.gif" border="0"/></page:asc>
	        </page:order>
	    </th>
	    <th width="15%" style="text-align:right">
	    	<page:order order="unitPrice" style="text-decoration:none">
    	        <bean:message key="supplierItem.unitPrice"/>
        	    <page:desc><img src="images/down.gif" border="0"/></page:desc>
            	<page:asc><img src="images/up.gif" border="0"/></page:asc>
	        </page:order>
	    </th>
	    <th width="7%"><bean:message key="supplierItem.currency.code"/></th>
	    <th width="7%">
	    	<page:order order="enabled" style="text-decoration:none">
            <bean:message key="supplierItem.status"/>
            <page:desc><img src="images/down.gif" border="0"/></page:desc>
            <page:asc><img src="images/up.gif" border="0"/></page:asc>
          </page:order>
	    </th>
	    <c:if test="${modifyable}">
	    <th width="6%">
	    </th>
	    </c:if>
      </tr>
    </thead>
    
    <tbody id="datatable">
      <logic:present name="X_RESULTLIST">
	      <logic:iterate id="X_OBJECT" name="X_RESULTLIST">
	        <bean:define id="X_OBJECT" toScope="request" name="X_OBJECT"/>
	        <jsp:include page="row.jsp"/>
	      </logic:iterate>
	  </logic:present>
    </tbody>
  </table>
<logic:present name="X_RESULTLIST">    
<div>
    <bean:message key="page.export"/>:
    <img src="images/csv.gif" border=0/> <page:export type="csv"><bean:message key="page.export.csv"/></page:export>
    <img src="images/excel.gif" border=0/> <page:export type="excel"><bean:message key="page.export.excel"/></page:export>
    <img src="images/xml.gif" border=0/> <page:export type="xml"><bean:message key="page.export.xml"/></page:export>
</div>
</logic:present>
</page:form>		      



<script type="text/javascript">
	applyRowStyle(document.all('datatable'));
	var mapping=new Map();
	mapping.put("purchaseCategory_id","category");
	mapping.put("purchaseSubCategory_id","subcategory");
    initCascadeSelect("config","data","supplierItemQueryForm",mapping,true);
</script>

