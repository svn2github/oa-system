<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<html:javascript formName="departmentForm" staticJavascript="false" />
<html:form action="insertDepartment.do" onsubmit="return validateDepartmentForm(this);">
<html:hidden property="site_id"/>
<logic:present name="X_PARENT"><input type="hidden" name="parentId" value="${X_PARENT.id}"/></logic:present>
<table width="100%" border="0" cellpadding="4" cellspacing="0">
<tr>
  <td class="bluetext"><bean:message key="department.id"/>:</td>
  <td><bean:message key="common.id.generateBySystem"/></td>
</tr>
<tr>
  <td class="bluetext"><bean:message key="department.parent"/>:</td>
  <td>
    <html:select property="parentDepartment_id">
      <html:option value="" style="color:red">${X_SITE.name}</html:option>
      <html:optionsCollection name="X_SITE" property="departments" value="id" label="indentName"/>
    </html:select>
  </td>
</tr>
<tr>
  <td class="bluetext"><bean:message key="department.name"/>:</td>
  <td><html:text property="name"/><span class="required">*</span></td>
</tr>
<tr>
  <td class="bluetext"><bean:message key="department.enabled"/>:</td>
  <td>
    <html:select property="enabled">
      <c:if test="${sessionScope.LOGIN_USER.locale=='en'}"><html:options collection="X_ENABLEDDISABLEDLIST" property="enumCode" labelProperty="engShortDescription"/></c:if>
      <c:if test="${sessionScope.LOGIN_USER.locale!='en'}"><html:options collection="X_ENABLEDDISABLEDLIST" property="enumCode" labelProperty="chnShortDescription"/></c:if>
    </html:select>
  </td>
</tr>
</table>
<hr/>
<table width="90%" border="0" cellpadding="4" cellspacing="0">
<tr>
  <td align="right">
  	<html:submit><bean:message key="all.save"/></html:submit>
  	<input type="button" value="<bean:message key="all.cancel"/>" onclick="window.parent.close();"/>
  </td>
</tr>
</table>
</html:form>