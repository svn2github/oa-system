<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<script type="text/javascript">
<!--
	function select(id, code, site, ec, departments, amount, actualAmount, remainAmount, durationFrom, durationTo) {
		var v = [];
		v['id'] = id;
		v['code'] = code;
		v['site.id'] = site;
		v['expenseCategory.id'] = ec;
		v['departments'] = departments;
		v['amount'] = amount;
		v['actualAmount'] = actualAmount;
		v['remainAmount'] = remainAmount;
		v['durationFrom'] = durationFrom;
		v['durationTo'] = durationTo;
		window.parent.returnValue = v;
		window.parent.close();
	}
//-->
</script>

<div id="fixedHeaderTableContainer" class="fixedHeaderTableContainer"
	style="height: 200px;">
	<table class="data">
		<thead>
			<tr bgcolor="#9999ff">
				<th>
					<bean:message key="yearlyBudget.code" />
				</th>
				<th>
					<bean:message key="yearlyBudget.name" />
				</th>
				<th>
					<bean:message key="yearlyBudget.amount" />
				</th>
				<th>
					<bean:message key="yearlyBudget.actualAmount" />
				</th>
				<th>
					<bean:message key="yearlyBudget.remainAmount" />
				</th>
				<th>
					<bean:message key="yearlyBudget.version" />
				</th>
				<th></th>
			</tr>
		<thead>
		<tbody id="datatable">
			<logic:iterate id="x_yb" name="X_RESULTLIST">
				<c:choose>
						<c:when test="${x_yb.amount == null || x_yb.actualAmount == null}">
							<c:set var="remainAmount" value="" scope="request" />
						</c:when>
						<c:otherwise>
							<c:set var="remainAmount" value="${x_yb.remainAmount}"
								scope="request" />
						</c:otherwise>
					</c:choose>
				<tr>
					<td>
						${x_yb.code}
					</td>
					<td>
						${x_yb.name}
					</td>
					<td align="right">
						${x_yb.amount}
					</td>
					<td align="right">
						${x_yb.actualAmount}
					</td>
					<td align="right">
						${remainAmount}
					</td>
					<td align="right">
						${x_yb.version}
					</td>
					<td align="center">
						<a
							href='javascript:select(${x_yb.id}, "${x_yb.code}", ${x_yb.site.id}, "${x_yb.expenseCategory.id}", "${x_yb.departmentIdString}", "${x_yb.amount}", "${x_yb.actualAmount}", "${remainAmount}", "${x_yb.formatedDurationFrom}", "${x_yb.formatedDurationTo}")'><bean:message
								key="all.select" />
						</a>
					</td>
				</tr>
			</logic:iterate>
		</tbody>
	</table>
</div>
<script type="text/javascript">
    applyRowStyle(document.all('datatable'));
</script>
<div style="text-align: right">
	<input type="button" value="<bean:message key="all.close"/>"
		onclick="window.parent.close();" />
</div>
