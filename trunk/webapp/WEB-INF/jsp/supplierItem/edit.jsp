<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<c:set var="x_supplier_promoteStatus_GLOBAL" value="<%=net.sourceforge.model.metadata.SupplierPromoteStatus.GLOBAL%>"/>
<c:set var="x_supplier_promoteStatus_SITE" value="<%=net.sourceforge.model.metadata.SupplierPromoteStatus.SITE%>"/>
<c:set var="x_supplier_promoteStatus_REQUEST" value="<%=net.sourceforge.model.metadata.SupplierPromoteStatus.REQUEST%>"/>

<c:set var="modifyable" value="${(x_version=='' && x_supplier.promoteStatus == x_supplier_promoteStatus_GLOBAL) || (x_version!='' && x_supplier.promoteStatus != x_supplier_promoteStatus_GLOBAL)}"/>

<xml id="data">
<data>
<logic:iterate id="x_purchaseCategory" name="x_purchaseCategoryList">
	<category id="${x_purchaseCategory.id}" name="<bean:write name="x_purchaseCategory" property="description"/>">
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



<html:javascript formName="supplierItemForm" staticJavascript="false"/>
<html:form action="updateSupplierItem${x_version}.do" method="post" onsubmit="return validateSupplierItemForm(this);">
<input type="hidden" name="purchaseSubCategory_purchaseCategory_id_value"
	value="<bean:write name="supplierItemForm" property="purchaseSubCategory_purchaseCategory_id"/>" />
<input type="hidden" name="purchaseSubCategory_id_value"
	value="<bean:write name="supplierItemForm" property="purchaseSubCategory_id"/>" />
<html:hidden property="id"/>	
<table width="600" border="0" cellpadding="4" cellspacing="0">
<tr>
  <td width="25%" class="bluetext"><bean:message key="supplier.code"/>:</td>
  <td width="25%" align="left"><html:hidden property="supplier_id"/>${x_supplier.code}</td>
  <td width="25%" class="bluetext" width="20%"><bean:message key="supplier.description" />:</td>
  <td width="25%" align="left">${x_supplier.name}</td>
</tr>
<tr>
  <td class="bluetext"><bean:message key="supplierItem.purchaseSubCategory.purchaseCategory.id"/>:</td>
  <td>
  	<c:choose>
		<c:when test="${modifyable}">
			<html:select property="purchaseSubCategory_purchaseCategory_id"></html:select><span class="required">*</span>
		</c:when>
		<c:otherwise>
			${x_supplierItem.purchaseSubCategory.purchaseCategory.description}
		</c:otherwise>
	</c:choose>
  </td>
  <td class="bluetext"><bean:message key="supplierItem.purchaseSubCategory.id"/>:</td>
  <td>
  	<c:choose>
		<c:when test="${modifyable}">
			<html:select property="purchaseSubCategory_id"></html:select><span class="required">*</span>
		</c:when>
		<c:otherwise>
			${x_supplierItem.purchaseSubCategory.description}
		</c:otherwise>
	</c:choose>
  </td>
</tr>
<tr>
  <td class="bluetext"><bean:message key="supplierItem.sepc"/>:</td>
  <td colspan="3">
  	<c:choose>
		<c:when test="${modifyable}">
			<html:text property="sepc" maxlength="250" style="width:430px"/><span class="required">*</span>
		</c:when>
		<c:otherwise>
			${x_supplierItem.sepc}
		</c:otherwise>
	</c:choose>
  </td>
</tr>
<tr>
  <td class="bluetext"><bean:message key="supplierItem.currency.code"/>:</td>
  <td>
  	<c:choose>
		<c:when test="${modifyable}">
			<html:select property="currency_code"><html:options collection = "x_currencyList" property = "code" labelProperty = "code"/></html:select>
		</c:when>
		<c:otherwise>
			${x_supplierItem.currency.name}
		</c:otherwise>
	</c:choose>
  </td>
  <td class="bluetext"><bean:message key="supplierItem.unitPrice"/>:</td>
  <td colspan="3">
  	<c:choose>
		<c:when test="${modifyable}">
			<html:text property="unitPrice" style="width:130px"/><span class="required">*</span>
		</c:when>
		<c:otherwise>
			${x_supplierItem.unitPrice}
		</c:otherwise>
	</c:choose>
  </td>
</tr>
<tr>
  <td class="bluetext"><bean:message key="currency.status"/>:</td>
  <td>
  	<c:choose>
		<c:when test="${modifyable}">
			<html:select property="enabled">
		      <c:if test="${sessionScope.LOGIN_USER.locale=='en'}"><html:options collection = "x_enabledDisabledList" property = "enumCode" labelProperty = "engShortDescription"/></c:if>
		      <c:if test="${sessionScope.LOGIN_USER.locale!='en'}"><html:options collection = "x_enabledDisabledList" property = "enumCode" labelProperty = "chnShortDescription"/></c:if>
		    </html:select>
		</c:when>
		<c:otherwise>
			<c:if test="${sessionScope.LOGIN_USER.locale=='en'}">${x_supplierItem.enabled.engDescription}</c:if>
			<c:if test="${sessionScope.LOGIN_USER.locale!='en'}">${x_supplierItem.enabled.chnDescription}</c:if>	
		</c:otherwise>
	</c:choose>
  </td>
</tr>
</table>
<hr width="600" align="left"/>
<table width="600" border="0" cellpadding="4" cellspacing="0">
<tr>
  <td align="right">
  	<c:choose>
		<c:when test="${modifyable}">
  			<html:submit><bean:message key="all.save"/></html:submit>
  		  	<input type="button" value="<bean:message key="all.cancel"/>" onclick="window.parent.close();"/>
  		</c:when>
	  	<c:otherwise>
			<input type="button" value="<bean:message key="all.ok"/>" onclick="window.parent.close();"/>  	
  		</c:otherwise>
  	</c:choose>
  	</>
  </td>
</tr>
</table>
</html:form>
<c:if test="${modifyable}">
<script type="text/javascript">
	var mapping=new Map();
	mapping.put("purchaseSubCategory_purchaseCategory_id","category");
	mapping.put("purchaseSubCategory_id","subcategory");
    initCascadeSelect("config","data","supplierItemForm",mapping,true);
</script>
</c:if>
