<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<c:if test="${!x_supplierEnabled}">
	<a	href='javascript:viewSupplier("${x_supplier.id}");'>${x_supplier.name}</a>
	(<span style="${x_supplier.enabled.color}">
		<bean:write name="x_supplier" property="enabled.${x_lang}ShortDescription" />
	</span>)
	<input type="hidden" name="supplier_enabled" value="false"/>
	<input type="hidden" name="supplier_confirmed" value="false"/>	
</c:if>

<c:if test="${x_supplierEnabled}">
	<input type="hidden" name="supplier_enabled" value="true"/>
	
	<c:if test="${x_supplierConfirmed}">
		<a	href='javascript:viewSupplier("${x_supplier.id}");'>${x_supplier.name}</a>
		<input type="hidden" name="supplier_confirmed" value="true"/>
		(<span style="${x_supplier.confirmed.color}">
			<bean:message key="supplier.confirmed"/>
		</span>)
	</c:if>
	
	<c:if test="${x_supplierSiteLevel && !x_supplierConfirmed}">
		<a	href='javascript:confirmSupplier("${x_supplier.id}");'>${x_supplier.name}</a>
		<input type="hidden" name="supplier_confirmed" value="false"/>	
		(<span style="${x_supplier.confirmed.color}">
			<bean:message key="supplier.notConfirmed"/>
		</span>)
	</c:if>
	
	<c:if test="${!x_supplierSiteLevel && !x_supplierConfirmed}">
		<a	href='javascript:viewSupplier("${x_supplier.id}");'>${x_supplier.name}</a>
		<input type="hidden" name="supplier_confirmed" value="false"/>
		(<span style="${x_supplier.confirmed.color}">
			<bean:message key="supplier.notConfirmed"/>
		</span>)
	</c:if>
</c:if>
