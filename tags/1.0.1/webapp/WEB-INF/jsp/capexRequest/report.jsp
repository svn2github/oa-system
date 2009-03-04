<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>
<html:javascript formName="capexRequestQueryForm" staticJavascript="false" />
<xml id="data">
<data>
<logic:iterate id="x_site" name="x_siteList">
	<site id="${x_site.id}" name="<bean:write name="x_site" property="name"/>">
		<department id="0" name="All"/>
		<logic:iterate id="x_department" name="x_site" property="departments">
		<c:if test="${x_department.granted}">
			<department id="${x_department.id}" name="<bean:write name="x_department" property="indentName"/>"/>
		</c:if>
		</logic:iterate>
	</site>
</logic:iterate>
</data>
</xml>

<xml id="config">
<config>
	<site>
		<department/>
	</site>
</config>
</xml>
<html:form action="reportCapexRequest" onsubmit="return validateCapexRequestQueryForm(this);">
	<html:hidden property="order" />
	<html:hidden property="descend" />

	<table width=100% border=0 cellpadding=4 cellspacing=0>
		<tr>
			<td class="bluetext" ><bean:message key="capexRequest.capex.site.id"/>&nbsp;</td>
			<td >
				<html:select property="capex_site_id">
				</html:select>
			</td>
			<td class="bluetext" ><bean:message key="capexRequest.capex.department.id"/>&nbsp;</td>
			<td >
				<html:select property="capex_department_id">
				</html:select>
			</td>	
		</tr>
		<tr>
			<td class="bluetext"><bean:message key="capexRequest.capex.id" />:</td>
			<td><html:text property="capex_id" size="8"/></td>
			<td class="bluetext"><bean:message key="capexRequest.title" />:</td>
			<td><html:text property="title" size="16"/></td>
		</tr>
		<tr>
			<td class="bluetext"><bean:message key="capexRequest.capex.yearlyBudget.year" />:</td>
			<td><html:text property="capex_yearlyBudget_year" size="4" maxlength="4"/></td>
			<td colspan="2" align="right">
				<html:submit><bean:message key="all.query"/></html:submit>
			</td>
		</tr>
		<tr>
			<td class="bluetext"><bean:message key="capexRequest.amount" />:</td>
			<td colspan="3">
				<html:text property="amountFrom" size="7"/> ~ <html:text property="amountTo" size="7"/>
			</td>
		</tr>
		<tr>
			<td class="bluetext"><bean:message key="capexRequest.capex.remainAmount" />:</td>
			<td colspan="3">
				<html:text property="capex_remainAmountFrom" size="7"/> ~ <html:text property="capex_remainAmountTo" size="7"/>
			</td>
		</tr>
	</table>
</html:form>
<hr />
<page:form action="reportCapexRequest.do" method="post">
	<jsp:include page="../pageHead.jsp"/>
	<table class="data">
		<thead>
			<tr bgcolor="#9999ff">
				<th rowspan="2"><bean:message key="yearlyBudget.code"/></th>
				<th rowspan="2"><bean:message key="yearlyBudget.year" /></th>
				<th rowspan="2"><bean:message key="yearlyBudget.amount" /></th>
				<th rowspan="2"><page:order order="capex_id" style="text-decoration:none">
					<bean:message key="capexRequest.capex.id" />
					<page:desc><img src="images/down.gif" border="0" /></page:desc>
					<page:asc><img src="images/up.gif" border="0" /></page:asc>
				</page:order></th>
				<th rowspan="2"><bean:message key="capexRequest.amount" /></th>
				<th rowspan="2"><bean:message key="capexRequest.capex.actualAmount" /></th>
				<th colspan="12"><bean:message key="capex.actualExpenditure" /></th>
				<th rowspan="2"><bean:message key="purchaseRequestItem.supplierItem.id" /></th>
				<th rowspan="2"><bean:message key="purchaseRequestItem.quantity" /></th>
				<th rowspan="2"><bean:message key="purchaseRequestItem.unitPrice" /></th>
				<th rowspan="2"><bean:message key="purchaseRequestItem.status" /></th>
			</tr>
			<tr bgcolor="#9999ff">
				<th><bean:message key="capex.actualExpenditure.1" /></th>
				<th><bean:message key="capex.actualExpenditure.2" /></th>
				<th><bean:message key="capex.actualExpenditure.3" /></th>
				<th><bean:message key="capex.actualExpenditure.4" /></th>
				<th><bean:message key="capex.actualExpenditure.5" /></th>
				<th><bean:message key="capex.actualExpenditure.6" /></th>
				<th><bean:message key="capex.actualExpenditure.7" /></th>
				<th><bean:message key="capex.actualExpenditure.8" /></th>
				<th><bean:message key="capex.actualExpenditure.9" /></th>
				<th><bean:message key="capex.actualExpenditure.10" /></th>
				<th><bean:message key="capex.actualExpenditure.11" /></th>
				<th><bean:message key="capex.actualExpenditure.12" /></th>
			</tr>
		</thead>

		<tbody id="datatable">
			<c:forEach var="X_OBJECT" items="${X_RESULTLIST}">
				<bean:size name="X_OBJECT" property="capex.purchaseRequestItems" id="x_pr_item_count"/>
				<c:choose>
					<c:when test="${x_row=='odd'}"><c:set var="x_row" value="even"/></c:when>
					<c:otherwise><c:set var="x_row" value="odd"/></c:otherwise>
				</c:choose>
				<c:set var="x_pr_item_count" value="${x_pr_item_count>1?x_pr_item_count:1}"/>
				<tr class="${x_row}" valign="top">
					<td rowspan="${x_pr_item_count}">${X_OBJECT.capex.yearlyBudget.code}</td>
					<td rowspan="${x_pr_item_count}" align="center">${X_OBJECT.capex.year}</td>
					<td rowspan="${x_pr_item_count}" align="right">${X_OBJECT.capex.yearlyBudget.amount}</td>
					<td rowspan="${x_pr_item_count}">${X_OBJECT.capex.id}</td>
					<td rowspan="${x_pr_item_count}" align="right">${X_OBJECT.amount}</td>
					<td rowspan="${x_pr_item_count}" align="right">${X_OBJECT.capex.actualAmount}</td>
					<c:forEach var="x_monthlyAmount" items="${X_OBJECT.capex.monthlyAmount}">
					<td rowspan="${x_pr_item_count}" align="right">${x_monthlyAmount}</td>
					</c:forEach>
					<logic:notEmpty name="X_OBJECT" property="capex.purchaseRequestItems">
						<c:forEach var="x_pr_item" items="${X_OBJECT.capex.purchaseRequestItems}" begin="0" end="0">
							<c:choose>
								<c:when test="${x_pr_item.purchaseOrderItem==null}">
					<td>${x_pr_item.supplierItemSepc}</td>
					<td align="right">${x_pr_item.quantity}</td>
					<td align="right">${x_pr_item.unitPrice}</td>
								</c:when>
								<c:otherwise>
					<td>${x_pr_item.purchaseOrderItem.itemSpec}</td>
					<td align="right">${x_pr_item.purchaseOrderItem.quantity}</td>
					<td align="right">${x_pr_item.purchaseOrderItem.unitPrice}</td>
								</c:otherwise>
							</c:choose>
					<td>
					<c:choose>
						<c:when test="${x_pr_item.purchaseOrderItem.purchaseOrder!=null}">PO:</c:when>
						<c:otherwise>PR:</c:otherwise>
					</c:choose>
					  <span style="color=${x_pr_item.status.color}"><bean:write name="x_pr_item" property="status.${x_lang}ShortDescription"/></span>
					</td>
						</c:forEach>
					</logic:notEmpty>
					<logic:empty name="X_OBJECT" property="capex.purchaseRequestItems">
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					</logic:empty>
				</tr>
				<c:forEach var="x_pr_item" items="${X_OBJECT.capex.purchaseRequestItems}" begin="1">
				<tr class="${x_row}">
					<c:choose>
						<c:when test="${x_pr_item.purchaseOrderItem==null}">
					<td>${x_pr_item.supplierItemSepc}</td>
					<td align="right">${x_pr_item.quantity}</td>
					<td align="right">${x_pr_item.unitPrice}</td>
						</c:when>
						<c:otherwise>
					<td>${x_pr_item.purchaseOrderItem.itemSpec}</td>
					<td align="right">${x_pr_item.purchaseOrderItem.quantity}</td>
					<td align="right">${x_pr_item.purchaseOrderItem.unitPrice}</td>
						</c:otherwise>
					</c:choose>
					<td>
					<c:choose>
						<c:when test="${x_pr_item.purchaseOrderItem.purchaseOrder!=null}">PO:</c:when>
						<c:otherwise>PR:</c:otherwise>
					</c:choose>
					  <span style="color=${x_pr_item.status.color}"><bean:write name="x_pr_item" property="status.${x_lang}ShortDescription"/></span>
					</td>
				</tr>
				</c:forEach>
			</c:forEach>
		</tbody>
	</table>
	<c:set var="x_pdf" scope="request" value="true"/>
	<c:set var="x_nocsv" scope="request" value="true"/>
	<c:set var="x_noxml" scope="request" value="true"/>
	<jsp:include page="../pageTail.jsp"/>
</page:form>

<script type="text/javascript">
    var mapping=new Map();
	mapping.put("capex_site_id", "site");
	mapping.put("capex_department_id", "department");
	
    initCascadeSelect("config", "data", "capexRequestQueryForm", mapping, true);
	
</script>
