<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<script language="JavaScript">
	function setTableHeadContent(table,categoryDesc,subCategoryDesc,subCategoryId) {
		var row = table.insertRow();
		var cell = row.insertCell();
		cell.width='10%';
		cell.className='bluetext';
		cell.innerHTML='<bean:message key="supplierItem.purchaseSubCategory.purchaseCategory.id" />';
		
		cell = row.insertCell();
		cell.width='10%';
		cell.innerHTML=categoryDesc;
		
		cell = row.insertCell();
		cell.width='10%';
		cell.className='bluetext';
		cell.innerHTML='<bean:message key="supplierItem.purchaseSubCategory.id" />';
		
		cell = row.insertCell();
		cell.width='10%';
		cell.innerHTML=subCategoryDesc;
		
		cell = row.insertCell();
		cell.width='5%';
		cell.innerHTML='--〉';
		
		cell = row.insertCell();
		cell.width='10%';
		cell.className='bluetext';
		cell.innerHTML='<bean:message key="supplierItem.purchaseSubCategory.purchaseCategory.id" />';
		
		cell = row.insertCell();
		cell.width='15%';
		cell.innerHTML='<select name=purchaseCategory_'+subCategoryId+'></select>';
		
		cell = row.insertCell();
		cell.width='10%';
		cell.className='bluetext';
		cell.innerHTML='<bean:message key="supplierItem.purchaseSubCategory.id" />';
		
		cell = row.insertCell();
		cell.width='15%';
		cell.innerHTML='<select name=purchaseSubCategory_'+subCategoryId+'></select>';
		
	}
</script>

<xml id="purchase">
<data>
<category id=""
	name="<bean:message key="supplierItem.pleaseSelectCategory"/>">
<subcategory id="" name="<bean:message key="supplierItem.pleaseSelectCategory"/>">
</subcategory>
</category>
<logic:iterate id="x_purchaseCategory" name="x_purchaseCategoryList">
	<category id="${x_purchaseCategory.id}"
		name="<bean:write name="x_purchaseCategory" property="description"/>">
	<subcategory id="" name="<bean:message key="supplierItem.pleaseSelectCategory"/>">
	</subcategory>
	<logic:iterate id="x_c" name="x_purchaseCategory"
		property="enabledPurchaseSubCategoryList">
		<subcategory id="${x_c.id}" name="<bean:write name="x_c" property="description"/>">
		</subcategory>
	</logic:iterate>
	</category>
</logic:iterate>
</data>
</xml>


<xml id="purchaseConfig">
<config>
<category>
<subcategory>
</subcategory>
</category>
</config>
</xml>


<logic:present name="x_conflictItemList">
	
	<logic:iterate id="x_category" name="x_conflictItemList">
	
	<table id="conflictTableHead" align="center" border="0" cellpadding="0" cellspacing="0" width="100%">
	
	</table>
	<table class="data" width="100%" style="margin: 5px 0 20px 0">
		<thead>
			<tr class="new_bg">
				<th width="20%"><bean:message
					key="supplierItem.purchaseSubCategory.purchaseCategory.id" /></th>
				<th width="20%"><bean:message
					key="supplierItem.purchaseSubCategory.id" /></th>
				<th width="25%"><bean:message key="supplierItem.sepc" /></th>
				<th width="15%" style="text-align:right"><bean:message
					key="supplierItem.unitPrice" /></th>
				<th width="7%"><bean:message key="supplierItem.currency.code" /></th>
				<th width="7%"><bean:message key="supplierItem.status" /></th>
			</tr>
		</thead>

		<tbody id="conflictTable">
			<logic:iterate id="X_OBJECT" name="x_category">
				<tr id="r${X_OBJECT.id}">
					<td>${X_OBJECT.purchaseSubCategory.purchaseCategory.description}</td>
					<td>${X_OBJECT.purchaseSubCategory.description}</td>
					<td>${X_OBJECT.sepc}</td>
					<td align="right">${X_OBJECT.unitPrice}</td>
					<td>${X_OBJECT.currency.name}</td>
					<td><span style="color:${X_OBJECT.enabled.color}"> <c:if
						test="${sessionScope.LOGIN_USER.locale=='en'}">${X_OBJECT.enabled.engShortDescription}</c:if>
					<c:if test="${sessionScope.LOGIN_USER.locale!='en'}">${X_OBJECT.enabled.chnShortDescription}</c:if>
					</span></td>
				</tr>
			</logic:iterate>
		</tbody>
	</table>
	<script type="text/javascript">
		document.all('conflictTableHead').id='conflictTableHead${X_OBJECT.purchaseSubCategory.id}';
		setTableHeadContent(document.all('conflictTableHead${X_OBJECT.purchaseSubCategory.id}'),"${X_OBJECT.purchaseSubCategory.purchaseCategory.description}","${X_OBJECT.purchaseSubCategory.description}","${X_OBJECT.purchaseSubCategory.id}");
		document.all('conflictTable').id='conflictTable${X_OBJECT.purchaseSubCategory.id}';
		applyRowStyle(document.all('conflictTable${X_OBJECT.purchaseSubCategory.id}'));
		var mapping=new Map();
		mapping.put("purchaseCategory_${X_OBJECT.purchaseSubCategory.id}","category");
		mapping.put("purchaseSubCategory_${X_OBJECT.purchaseSubCategory.id}","subcategory");
	    initCascadeSelect("purchaseConfig","purchase","supplierForm",mapping,true);
	</script>
	<input type="hidden" name="changeSubCategoryId" value="${X_OBJECT.purchaseSubCategory.id}"/>
	</logic:iterate>
</logic:present>
<script type="text/javascript">
    //applyRowStyle(document.all('conflictTable'));
</script>
	



