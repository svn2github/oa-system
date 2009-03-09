<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>

<html:form action="/insertPurchaseOrder">
	<table width=90% border=0 cellpadding=4 cellspacing=0>
		<tr>
			<td class="bluetext"><bean:message key="purchaseOrder.id" />:</td>
			<td>(<bean:message key="common.id.generateBySystem" />)</td>
		</tr>
		<tr>
			<td class="bluetext"><bean:message key="purchaseOrder.site.id" />:</td>
			<td>
				<html:select property="site_id" >
				</html:select>
			</td>
		</tr>			
		<tr>
			<td class="bluetext"><bean:message key="purchaseOrder.supplier.id" />:</td>
			<td>
				<html:select property="supplier_id" >
				</html:select>
			</td>
		</tr>			
		<tr>
			<td class="bluetext"><bean:message key="purchaseOrder.subCategory.id" />:</td>
			<td>
				<html:select property="subCategory_id" >
				</html:select>
			</td>
		</tr>			
		<tr>
			<td class="bluetext"><bean:message key="purchaseOrder.currency.code" />:</td>
			<td>
				<html:select property="currency_code" >
				</html:select>
			</td>
		</tr>			
		<tr>
			<td class="bluetext"><bean:message key="purchaseOrder.baseCurrency.code" />:</td>
			<td>
				<html:select property="baseCurrency_code" >
				</html:select>
			</td>
		</tr>			
		<tr>
			<td class="bluetext"><bean:message key="purchaseOrder.createUser.id" />:</td>
			<td>
				<html:select property="createUser_id" >
				</html:select>
			</td>
		</tr>			
		<tr>
			<td class="bluetext"><bean:message key="purchaseOrder.purchaser.id" />:</td>
			<td>
				<html:select property="purchaser_id" >
				</html:select>
			</td>
		</tr>			
		<tr>
			<td class="bluetext"><bean:message key="purchaseOrder.erpNo" />:</td>
			<td><html:text property="erpNo" /></td>
		</tr>			
		<tr>
			<td class="bluetext"><bean:message key="purchaseOrder.title" />:</td>
			<td><html:text property="title" /></td>
		</tr>			
		<tr>
			<td class="bluetext"><bean:message key="purchaseOrder.description" />:</td>
			<td><html:text property="description" /></td>
		</tr>			
		<tr>
			<td class="bluetext"><bean:message key="purchaseOrder.amount" />:</td>
			<td><html:text property="amount" /></td>
		</tr>			
		<tr>
			<td class="bluetext"><bean:message key="purchaseOrder.exchangeRate" />:</td>
			<td><html:text property="exchangeRate" /></td>
		</tr>			
		<tr>
			<td class="bluetext"><bean:message key="purchaseOrder.status" />:</td>
			<td><html:text property="status" /></td>
		</tr>			
		<tr>
			<td class="bluetext"><bean:message key="purchaseOrder.createDate" />:</td>
			<td><html:text property="createDate" /></td>
		</tr>			
		<tr>
			<td class="bluetext"><bean:message key="purchaseOrder.exportStatus" />:</td>
			<td><html:text property="exportStatus" /></td>
		</tr>			
		<tr>
			<td class="bluetext"><bean:message key="purchaseOrder.approveRequestId" />:</td>
			<td><html:text property="approveRequestId" /></td>
		</tr>			

		<tr>
			<td class="bluetext">Status:</td>
			<td><html:select property="enabled_enumCode">
				<c:if test="${sessionScope.LOGIN_USER.locale=='zh'}">
					<html:options collection="X_ENABLEDDISABLEDLIST"
						property="enumCode" labelProperty="chnShortDescription" />
				</c:if>
				<c:if test="${sessionScope.LOGIN_USER.locale!='zh'}">
					<html:options collection="X_ENABLEDDISABLEDLIST"
						property="enumCode" labelProperty="engShortDescription" />
				</c:if>
			</html:select></td>
		</tr>
		<tr>
			<td colspan="2" align="right"><html:submit>
				<bean:message key="all.save" />
			</html:submit></td>
		</tr>
	</table>
</html:form>
