<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<html:javascript formName="exchangeRateForm" staticJavascript="false"/>
<html:form action="updateExchangeRate.do"  method="post" onsubmit="return validateExchangeRateForm(this)">
<html:hidden property="id"/>
<table width="100%" border="0" cellpadding="4" cellspacing="0">
<tr>
  <td class="bluetext"><bean:message key="exchangeRate.site"/>:</td>
  <td><html:hidden property="site_id"/><bean:write property="site_name" name="exchangeRateForm"/></td>
</tr>
<tr>
  <td class="bluetext"><bean:message key="exchangeRate.currency"/>:</td>
  <td><html:hidden property="currency_code"/><bean:write property="currency_code" name="exchangeRateForm"/></td>
</tr>
<tr>
  <td class="bluetext"><bean:message key="exchangeRate.exchangeRate"/>:</td>
  <td>
    <html:text property="exchangeRate"/><span class="required">*</span>
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
