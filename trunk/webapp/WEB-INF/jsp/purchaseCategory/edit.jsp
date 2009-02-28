<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>
<html:javascript formName="purchaseCategoryForm" staticJavascript="false"/>

<html:form action="updatePurchaseCategory${x_version}.do" method="post" onsubmit="return validatePurchaseCategoryForm(this);">
<html:hidden property="id"/>
<html:hidden property="site_id"/>
<table width="100%" border="0" cellpadding="4" cellspacing="0">
<tr>
  <td class="bluetext"><bean:message key="purchaseCategory.id"/>:</td>
  <td>
  	<bean:write property="id" name="purchaseCategoryForm"/>
  </td>
</tr>
<logic:notEmpty name="x_version">
<tr>
  <td class="bluetext"><bean:message key="purchaseCategory.site"/>:</td>
  <td><bean:write property="site_name" name="purchaseCategoryForm"/></td>
</tr>
</logic:notEmpty>
<tr>
  <td class="bluetext"><bean:message key="purchaseCategory.description"/>:</td>
  <td><html:text property="description" maxlength="50"/><span class="required">*</span></td>
</tr>
<tr>
  <td class="bluetext"><bean:message key="purchaseCategory.status"/>:</td>
  <td>
    <html:select property="enabled">
      <c:if test="${sessionScope.LOGIN_USER.locale=='en'}"><html:options collection = "X_ENABLEDDISABLEDLIST" property = "enumCode" labelProperty = "engShortDescription"/></c:if>
      <c:if test="${sessionScope.LOGIN_USER.locale!='en'}"><html:options collection = "X_ENABLEDDISABLEDLIST" property = "enumCode" labelProperty = "chnShortDescription"/></c:if>
    </html:select>
  </td>
</tr>
</table>

<table width="90%" border="0" cellpadding="4" cellspacing="0">
<tr>
  <td align="right">
  	<html:submit><bean:message key="all.save"/></html:submit>
  	<input type="button" value="<bean:message key="all.cancel"/>" onclick="window.parent.close();"/>
  </td>
</tr>
</table>
</html:form>
<hr width="100%" align="left"/>

<iframe name="frameSubCategoryList" src="listPurchaseSubCategory${x_version}.do?purchaseCategory_id=<bean:write property="id" name="purchaseCategoryForm"/>" frameborder="0" height="320" width="100%" scrolling="auto"></iframe>



