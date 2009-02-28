<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<script type="text/javascript" src="includes/selection.js"></script>
<script type="text/javascript">
<!--
	var functionArray = [];
	var urlArray = [];
	
	functionArray[0] = new Option('');
	var a = [];
	var url;
	a[0] = new Option('<bean:message key="menu.selectFunction"/>', '');
	urlArray[0] = a;
	
<logic:iterate id="f" name="X_FUNCTIONLIST">
	functionArray[functionArray.length] = new Option("${f.name}", "${f.id}");
	a = [];
  <logic:notEmpty name="f" property="actionMappings">
    <logic:iterate id="a" name="f" property="actionMappings">
    url = "${a.path}".substr(1);
	a[a.length] = new Option(url, url);
    </logic:iterate>
  </logic:notEmpty>
	urlArray[urlArray.length] = a;
</logic:iterate>
//-->
</script>
<html:javascript formName="menuForm" staticJavascript="false" />
<html:form action="insertMenu.do" onsubmit="return validateMenuForm(this);">
<logic:present name="X_PARENT"><input type="hidden" name="parentId" value="${X_PARENT.id}"/></logic:present>
<div class="warningMsg"><html:errors/></div>
<table border="0" cellpadding="4" cellspacing="0">
<tr>
  <td class="bluetext"><bean:message key="menu.id"/>:</td>
  <td><bean:message key="common.id.generateBySystem"/></td>
</tr>
<tr>
  <td class="bluetext"><bean:message key="menu.parent"/>:</td>
  <td><html:select property="parentMenu_id"><html:option value="" style="color:red"><bean:message key="menu.createAsRoot"/></html:option><logic:present name="X_PARENT"><html:option value="${X_PARENT.id}">${X_PARENT.name}</html:option></logic:present></html:select></td>
</tr>
<tr>
  <td class="bluetext"><bean:message key="menu.name"/>:</td>
  <td><html:text property="name"/><span class="required">*</span></td>
</tr>
<tr>
  <td class="bluetext"><bean:message key="menu.description"/>:</td>
  <td><html:text property="description"/></td>
</tr>
<tr>
  <td class="bluetext"><bean:message key="menu.secondName"/>:</td>
  <td><html:text property="secondName"/><span class="required">*</span></td>
</tr>
<tr>
  <td class="bluetext"><bean:message key="menu.secondDescription"/>:</td>
  <td><html:text property="secondDescription"/></td>
</tr>
<tr>
  <td class="bluetext"><bean:message key="menu.function"/>:</td>
  <td><html:select property="function_id" onchange="cascadeUpdate(this, url, urlArray);"></html:select></td>
</tr>
<tr>
  <td class="bluetext"><bean:message key="menu.url"/>:</td>
  <td><html:select property="url"></html:select></td>
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
<script type="text/javascript">
	initCascadeSelect(menuForm.function_id, menuForm.url, functionArray, urlArray, '<bean:write name="menuForm" property="function_id"/>', '<bean:write name="menuForm" property="url"/>');
</script>