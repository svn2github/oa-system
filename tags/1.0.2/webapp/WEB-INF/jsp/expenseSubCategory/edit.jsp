<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>

<html:javascript formName="expenseSubCategoryForm" staticJavascript="false"/>
<html:form action="/updateExpenseSubCategory" onsubmit="return validateExpenseSubCategoryForm(this)">
<html:hidden property="id"/>
<html:hidden property="expenseCategory_id"/>
<table width=100% border=0 cellpadding=4 cellspacing=0>
	<tr>
		<td class="bluetext">
			<bean:message key="expenseSubCategory.id"/>:
		</td>
		<td>
			${x_esc.id}
		</td>
	</tr>
	<tr>
		<td class="bluetext">
			<bean:message key="expenseSubCategory.description"/>:
		</td>
		<td>
			<html:text property="description" size="30"/><span class="required">*</span>
		</td>
	</tr>
	<tr>
		<td class="bluetext"><bean:message key="expenseSubCategory.enabled"/>:
		</td>
		<td>
			<html:select property="enabled">
      <c:if test="${sessionScope.LOGIN_USER.locale=='en'}"><html:options collection = "X_ENABLEDDISABLEDLIST" property = "enumCode" labelProperty = "engShortDescription"/></c:if>
      <c:if test="${sessionScope.LOGIN_USER.locale!='en'}"><html:options collection = "X_ENABLEDDISABLEDLIST" property = "enumCode" labelProperty = "chnShortDescription"/></c:if>
    </html:select>
		</td>
	</tr>
	<logic:notEmpty name="x_isTravel">
	<tr>
		<td class="bluetext"><bean:message key="expenseSubCategory.isHotel"/>:
		</td>
		<td>
			<html:select property="isHotel">
      <c:if test="${sessionScope.LOGIN_USER.locale=='en'}"><html:options collection = "x_yesNoList" property = "enumCode" labelProperty = "engShortDescription"/></c:if>
      <c:if test="${sessionScope.LOGIN_USER.locale!='en'}"><html:options collection = "x_yesNoList" property = "enumCode" labelProperty = "chnShortDescription"/></c:if>
    </html:select>
		</td>
	</tr>
	</logic:notEmpty>
	<logic:empty name="x_isTravel">
		<html:hidden property="isHotel"/>
	</logic:empty>
</table>
<hr>
<table width=90% border=0 cellpadding=4 cellspacing=0>
	<tr>	
		<td align="right">
			<html:submit><bean:message key="all.save"/></html:submit>
			<input type="button" value="<bean:message key="all.cancel"/>" onclick="window.parent.close();"/>
		</td>
	</tr>
</table>
</html:form>