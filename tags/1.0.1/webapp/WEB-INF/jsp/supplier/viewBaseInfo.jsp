<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>

<table width=90% border=0 cellpadding=4 cellspacing=0>
	<tr>
		<td class="bluetext"><bean:message key="supplier.code" />:</td>
		<td>${x_supplier.code}</td>
		<td class="bluetext"><bean:message key="supplier.description" />:</td>
		<td>${x_supplier.name}</td>
	</tr>
	<tr>
		<td class="bluetext"><bean:message key="supplier.address1" />:</td>
		<td colspan="3">${x_supplier.address1}</td>
	</tr>
	<tr>
		<td class="bluetext"><bean:message key="supplier.address2" />:</td>
		<td colspan="3">${x_supplier.address2}</td>
	</tr>
	<tr>
		<td class="bluetext"><bean:message key="supplier.address3" />:</td>
		<td colspan="3">${x_supplier.address3}</td>
	</tr>
	<tr>
		<td class="bluetext"><bean:message key="supplier.country" />:</td>
		<td align="left">
		<c:if test="${sessionScope.LOGIN_USER.locale=='en'}">
		${x_supplier.city.province.country.engName}
		</c:if>
		<c:if test="${sessionScope.LOGIN_USER.locale!='en'}">
		${x_supplier.city.province.country.chnName}
		</c:if>
		</td>
		<td class="bluetext"><bean:message key="supplier.city" />:</td>
		<td align="left">
		<c:if test="${sessionScope.LOGIN_USER.locale=='en'}">
		${x_supplier.city.engName}
		</c:if>
		<c:if test="${sessionScope.LOGIN_USER.locale!='en'}">
		${x_supplier.city.chnName}
		</c:if>
		</td>
	</tr>
	<tr>
		<td class="bluetext"><bean:message key="supplier.telephone" />:</td>
		<td>${x_supplier.telephone1}</td>	
		<td class="bluetext"><bean:message key="supplier.fax" />:</td>
		<td>${x_supplier.fax1}</td>
	</tr>
	<tr>
		<td class="bluetext"><bean:message key="supplier.post" />:</td>
		<td>${x_supplier.post}</td>	
		<td></td><td></td>
		<!-- <td class="bluetext"><bean:message key="supplier.bank" />:</td>
		<td>${x_supplier.bank}</td>//-->
	</tr>
	<tr>
		<td class="bluetext"><bean:message key="supplier.contactor" />:</td>
		<td>${x_supplier.contact}</td>	
		<td class="bluetext"><bean:message key="supplier.currency" />:</td>
		<td>
			${x_supplier.currency.code}
		</td>
	</tr>
	<!-- 
	<tr>
		<td class="bluetext"><bean:message key="supplier.purchaseAccount" />:</td>
		<td>${x_supplier.purchaseAccount}</td>	
		<td class="bluetext"><bean:message key="supplier.purchaseSubAccount" />:</td>
		<td>${x_supplier.purchaseSubAccount}</td>
	</tr>
	<tr>
		<td class="bluetext"><bean:message key="supplier.purchaseCostCenter" />:</td>
		<td>${x_supplier.purchaseCostCenter}</td>	
		<td class="bluetext"><bean:message key="supplier.apAccount" />:</td>
		<td>${x_supplier.apAccount}</td>
	</tr>
	<tr>
		<td class="bluetext"><bean:message key="supplier.apSubAccount" />:</td>
		<td>${x_supplier.apSubAccount}</td>	
		<td class="bluetext"><bean:message key="supplier.apCostCenter" />:</td>
		<td>${x_supplier.apCostCenter}</td>
	</tr>
	//-->
	<tr>
		<td class="bluetext"><bean:message key="supplier.contractStatus"/>:</td>
		<td>
			<span style="color:${x_supplier.contractStatus.color}">
		      <c:if test="${sessionScope.LOGIN_USER.locale=='en'}">${x_supplier.contractStatus.engShortDescription}</c:if>
		      <c:if test="${sessionScope.LOGIN_USER.locale!='en'}">${x_supplier.contractStatus.chnShortDescription}</c:if>
		    </span>  
		</td>
		<td class="bluetext"><bean:message key="supplier.status"/>:</td>
		<td>
			<span style="color:${x_supplier.enabled.color}">
				<c:if test="${sessionScope.LOGIN_USER.locale=='en'}">${x_supplier.enabled.engShortDescription}</c:if>
				<c:if test="${sessionScope.LOGIN_USER.locale!='en'}">${x_supplier.enabled.chnShortDescription}</c:if>
			</span>
		</td>
	</tr>
	<tr>
		<td colspan="2" align="right">&nbsp;</td>
	</tr>
</table>
<hr width="90%" align="left">
<table width="90%" border=0 cellpadding=0 cellspacing=0>
	<tr>
		<td class="bluetext"><bean:message key="supplier.contractStartDate" />:</td>
		<td align="left">
			<bean:write name="x_supplier" property="contractStartDate" format="yyyy/MM/dd"/>
		</td>
		<td>~</td>
		<td class="bluetext"><bean:message key="supplier.contractExpireDate" />:</td>
		<td align="left">
			<bean:write name="x_supplier" property="contractExpireDate" format="yyyy/MM/dd"/>
		</td>
	</tr>
</table>
<hr width="90%" align="left">
<table width=90% border=0 cellpadding=4 cellspacing=0>
	<tr>
		<td align="right"><input type="button" value="<bean:message key="all.close"/>" onclick="window.parent.close();"/></td>
	</tr>
</table>