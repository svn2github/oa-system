<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>


<c:choose>
	<c:when test="${x_recharged}">
		<hr size="1" width="100%">
		<h3 style="color:blue"><bean:message key="recharge.buyFor.recharge"/></h3>
		<table class="data" width="100%">
			<thead>
			<tr bgcolor="#9999ff">
				<th width="20px" height="29" align="right">#</th>
				<th><bean:message key="recharge.user"/></th>
				<th width="100px"><bean:message key="recharge.amount"/></th>
				<th width="90px"><bean:message key="recharge.percentage"/></th>
			</tr>
			</thead>
			<tbody id="recharge_datatable">
				<c:forEach var="X_RECHARGE" items="${X_RECHARGELIST}">
				<tr>
					<td><span id="span_seq"></span></td>
					<td>
						<c:if test="${X_RECHARGE.customer!=null}">
							${X_RECHARGE.customer.description} (<span style="color:${X_RECHARGE.customer.type.rechargeType.color}"><bean:write name="X_RECHARGE" property="customer.type.rechargeType.${x_lang}ShortDescription"/></span>)
						</c:if>
						<c:if test="${X_RECHARGE.personDepartment!=null}">
							${X_RECHARGE.personDepartment.name} \ ${X_RECHARGE.person.name} (<span style="color:${x_rechargeTypePerson.color}"><bean:write name="x_rechargeTypePerson" property="${x_lang}ShortDescription"/></span>)
						</c:if>
					</td>
					<td align="right"><span id="span_amount">${X_RECHARGE.amount}</span></td>
					<td align="right"><span id="span_percentage"></span></td>
				</tr>
				</c:forEach>
			</tbody>
			<tfoot>
			<tr>
				<td></td>
				<td align="right"><strong><bean:message key="recharge.total"/></strong></td>
				<td align="right"><span id="span_recharge_total_amount"></span></td>
				<td align="right"><span id="span_recharge_total_percentage"></span></td>
			</tr>
			</tfoot>
		</table>
		<script type="text/javascript">
		  	function setTotalAmount(totalAmount) {
		  		totalRechargeAmount = totalAmount;
				var table = document.all('recharge_datatable');
				totalRechargePercentage = 0;
				for (i = 0; i < table.rows.length; i++) {
					row = table.rows[i];
					var amount = parseFloat(row.all('span_amount').innerText);
					var percentage = 0;
					if (totalAmount != 0) {
						percentage = Math.round(amount * 10000 / totalAmount) / 100;
					}
					row.all('span_percentage').innerText = percentage;
					totalRechargePercentage += percentage;
				}
		  	}
		  	
			var table = document.all('recharge_datatable');
			var totalRechargeAmount = 0;
			var totalRechargePercentage = 0;
			//for (i = 0; i < table.rows.length; i++) {
			//	var index = i + 1;
			//	row = table.rows[i];
			//	row.all('span_seq').innerText = index;
			//	var percentage = parseFloat(row.all('span_percentage').innerText);
			//	totalRechargePercentage += percentage;
			//}
			applyRowStyle(table);
			document.getElementById('span_recharge_total_amount').setExpression("innerText", "totalRechargeAmount");
			document.getElementById('span_recharge_total_percentage').setExpression("innerText", "totalRechargePercentage");
		</script>
	</c:when>
	<c:otherwise>
		<c:if test="${x_showBuyFor}">
			<hr size="1" width="100%">
			<h3><font color="blue"><bean:message key="recharge.buyFor"/></font></h3>
			<table width="100%">
				<tr>
					<td class='bluetext'><bean:message key="recharge.buyFor.department"/>:</td>
					<td>${X_RECHARGETARGET.buyForDepartment.name}</td>
				</tr>	
				<tr>
					<td class='bluetext'><bean:message key="recharge.buyFor.user"/>:</td>
					<td>${X_RECHARGETARGET.buyForUser.name}</td>
				</tr>
			</table>
		</c:if>
	</c:otherwise>
</c:choose>
