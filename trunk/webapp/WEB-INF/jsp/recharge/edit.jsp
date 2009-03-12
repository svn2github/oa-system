<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<script type="text/javascript">
<!--
    // 这个变量应由包含该jsp的页面提供
  	//var rechargeFormName = "expenseForm";
  	
  	var X_totalAmount = 0;
  	
  	function totalAmountChanged(totalAmount) {
		<c:if test="${x_canRecharge}">
  		X_totalAmount = totalAmount;
		totalRechargePercentage = 0;
		//var amountObj = document.all.t_recharge_amount;
		//var percentageObj = document.all.t_recharge_percentage;
		//amountObj.oldValue = Math.round(percentageObj.oldValue * 100) * X_totalAmount / 10000;
		//amountObj.value = amountObj.oldValue;
		var table = document.all('recharge_datatable');
		for (var i = 0; i < table.rows.length; i++) {
			var row = table.rows[i];
			amountObj = row.all.recharge_amount;
			percentageObj = row.all.recharge_percentage;
			if (X_totalAmount != 0) {
				percentageObj.oldValue = Math.round(amountObj.oldValue * 10000 / X_totalAmount) / 100;
			} else {
				percentageObj.oldValue = 0;
			}
			percentageObj.value = percentageObj.oldValue;
			totalRechargePercentage += percentageObj.oldValue;
		}
		</c:if>
  	}
//-->
</script>

<!--
为了不显示recharge, hr和table添加了style="display:none"属性
//-->
<hr size="1" width="100%" style="display:none">

<table width="100%" style="display:none">
	<tr>
		<td align="left" width='8%' class='bluetext' nowrap><bean:message key="recharge.buyFor.recharge"/>:</td>
		<td>
			<c:choose>
				<c:when test="${x_canRecharge&&x_canChangeRecharge}">
					<html:select property="isRecharge">
						<html:options collection="X_YESNOLIST" property="enumCode" labelProperty="${x_lang}ShortDescription"/>
					</html:select>
				</c:when>
				<c:otherwise>
					<span style="color:${X_RECHARGETARGET.isRecharge.color}">
						<bean:write name="X_RECHARGETARGET" property="isRecharge.${x_lang}ShortDescription"/>
					</span>
					<html:hidden property="isRecharge"/>
				</c:otherwise>
			</c:choose>
		</td>
	</tr>
</table>

<c:if test="${x_canBuyFor}">
<div id="choose_buyfor" style="display:none">
	<h3><font color="blue"><bean:message key="recharge.buyFor"/></font></h3>
	<script type="text/javascript">
	<!--
		document.getElementById('choose_buyfor').style.setExpression("display", "document.forms(rechargeFormName).isRecharge.value=='<%=net.sourceforge.model.metadata.YesNo.NO.getEnumCode()%>' ? '' : 'none'");

		function buyFor_selectUser() {
			var form = document.forms(rechargeFormName);
			var	v = window.showModalDialog(
				'showDialog.do?title=recharge.selectPerson.title&select${X_RECHARGETARGETTYPE}RechargePerson.do?departmentId=' + form.buyForDepartment_id.value , 
				null, 'dialogWidth:650px;dialogHeight:500px;status:no;help:no;scroll:no');
			if (v) {
				form.buyForUser_id.value = v['id'];
				document.getElementById('span_buy_for_user_name').innerText = v['name'];
			}
		}
	
	//-->
	</script>
	<table width="100%">
		<tr>
			<td class='bluetext'><bean:message key="recharge.buyFor.department"/>:</td>
			<td>
			    <html:select property="buyForDepartment_id" onchange="document.forms(rechargeFormName).buyForUser_id.value = ''; document.getElementById('span_buy_for_user_name').innerText = '';">
			      <html:options collection="X_DEPARTMENTLIST" property="id" labelProperty="indentName"/>
			    </html:select>
			</td>
		</tr>	
		<tr>
			<td class='bluetext'><bean:message key="recharge.buyFor.user"/>:</td>
			<td>
			    <span id="span_buy_for_user_name"><bean:write name="${X_FORMNAME}" property="buyForUser_name"/></span>
			    &nbsp;<a href="javascript:buyFor_selectUser()"><img src="images/select.gif" width="16" height="16" border="0"/></a>
			    <html:hidden property="buyForUser_id"/>
			</td>
		</tr>
	</table>
</div>
</c:if>

<c:if test="${x_canRecharge}">
<div id="choose_recharge" style="display:none">
	<h3><font color="blue"><bean:message key="recharge.buyFor.recharge"/></font></h3>
	<script type="text/javascript">
	<!--
		var rowCount = 0;
		var totalRechargeAmount = 0;
		var totalRechargePercentage = 0;

		function insertRechargeRow(table, customer_code, person_dep_id, person_id, name, amount, customer_code_type) {
			rowCount++;
			var row = table.insertRow();

			var templateRow = document.getElementById('template_recharge_row');
			for (var i = 0; i < templateRow.cells.length; i++) {
				var cell = row.insertCell();
				cell.innerHTML = templateRow.cells[i].innerHTML;
				cell.align = templateRow.cells[i].align;
			}

			row.all('span_recharge_seq').innerText = rowCount;
			row.all('recharge_customer_code').value = customer_code;
			row.all('recharge_person_id').value = person_id;
			row.all('span_recharge_name').innerHTML = name;
			row.all('recharge_amount').value = amount;
			row.all('recharge_amount').oldValue = amount;
			totalRechargeAmount += amount;
	
			var percentage = 0;
			if (X_totalAmount != 0) {
				percentage = Math.round(amount * 10000 / X_totalAmount) / 100;
			}
			row.all('recharge_percentage').value = percentage;
			row.all('recharge_percentage').oldValue = percentage;
			totalRechargePercentage += percentage;
	
			selectOption(row.all('rechargeType'), customer_code_type);
			selectOption(row.all('recharge_person_dep_id'), person_dep_id);
			row.all("recharge_person_dep_id").style.display = row.all("rechargeType").value == "${x_rechargeTypePerson.enumCode}" ? "" : "none";

		}
		
		function selectOption(selObj, selValue) {
			for (var i = 0; selObj != null && i < selObj.options.length; i++) {
				if (selObj.options[i].value == selValue) {
					selObj.options[i].selected = true;
					return
				}
			}
		}
	
		function deleteRechargeRow(row) {
			rowCount--;
	
			var table = row.parentNode;
			totalRechargeAmount -= row.all('recharge_amount').oldValue;
			totalRechargePercentage -= row.all('recharge_percentage').oldValue;
			table.deleteRow(row.sectionRowIndex);
	
			for (var i = 0; i < table.rows.length; i++) {
				var index = i + 1;
				row = table.rows[i];
				row.all('span_recharge_seq').innerText = index;
			}
			applyRowStyle(table);
		}
		
		function calculatePercentage(row) {
			var amount, percentage = 0;
			var amountObj, percentageObj;
			amountObj = row.all.recharge_amount;
			percentageObj = row.all.recharge_percentage;
			amount = Math.round(parseFloat(amountObj.value) * 100) / 100;
			if (isNaN(amount)) amount = 0;
			if (X_totalAmount != 0) {
				percentage = Math.round(amount * 10000 / X_totalAmount) / 100;
			}
			totalRechargeAmount -= amountObj.oldValue - amount;
			totalRechargePercentage -= percentageObj.oldValue - percentage;
			amountObj.oldValue = amount;
			percentageObj.oldValue = percentage;
			percentageObj.value = percentage;
		}
	
		function calculateAmount(row) {
			var amount, percentage;
			var amountObj, percentageObj;
			amountObj = row.all.recharge_amount;
			percentageObj = row.all.recharge_percentage;
			percentage = Math.round(parseFloat(percentageObj.value) * 100) / 100;
			if (isNaN(percentage)) percentage = 0;
			amount = Math.round(percentage * X_totalAmount) / 100;
			totalRechargeAmount -= amountObj.oldValue - amount;
			totalRechargePercentage -= percentageObj.oldValue - percentage;
			amountObj.oldValue = amount;
			percentageObj.oldValue = percentage;
			amountObj.value = amount;
		}
	
		function addNewClicked() {
			var table = document.all('recharge_datatable');
			insertRechargeRow(table, "", "", "", "", 0, "${x_rechargeTypeCustomer.enumCode}");
			applyRowStyle(table);
		}
		
		function recharge_selectUser(row) {
			var type = row.all("rechargeType").value;
			var v = null;
			switch (type) {
				case "${x_rechargeTypeCustomer.enumCode}": {
					v = window.showModalDialog(
						'showDialog.do?title=recharge.selectCustomer.title&select${X_RECHARGETARGETTYPE}RechargeCustomer.do?siteId=${X_RECHARGESITE.id}', 
						null, 'dialogWidth:650px;dialogHeight:500px;status:no;help:no;scroll:no');
					if (v) {
						row.all("recharge_person_id").value = '';
						row.all("recharge_customer_code").value = v['id'];
					};
					break;
				}
				case "${x_rechargeTypeEntity.enumCode}": {
					v = window.showModalDialog(
						'showDialog.do?title=recharge.selectEntity.title&select${X_RECHARGETARGETTYPE}RechargeEntity.do?siteId=${X_RECHARGESITE.id}', 
						null, 'dialogWidth:650px;dialogHeight:500px;status:no;help:no;scroll:no');
					if (v) {
						row.all("recharge_person_id").value = '';
						row.all("recharge_customer_code").value = v['id'];
					};
					break;
				}
				case "${x_rechargeTypePerson.enumCode}": {
					v = window.showModalDialog(
						'showDialog.do?title=recharge.selectPerson.title&select${X_RECHARGETARGETTYPE}RechargePerson.do?departmentId=' + row.all("recharge_person_dep_id").value, 
						null, 'dialogWidth:650px;dialogHeight:500px;status:no;help:no;scroll:no');
					if (v) {
						row.all("recharge_person_id").value = v['id'];
						row.all("recharge_customer_code").value = '';
					};
					break;
				}
			}
			if (v) {
				row.all('span_recharge_name').innerHTML = v['name'] + ' (<span style="color:' + v['prefixColor'] + '">' + v['prefix'] + '</span>)';
			}
		}
		
		function rechargeTypeChanged(row) {
			row.all("recharge_person_id").value = '';
			row.all("recharge_customer_code").value = '';
			row.all("span_recharge_name").innerHTML = '';
			row.all("recharge_person_dep_id").style.display = row.all("rechargeType").value == "${x_rechargeTypePerson.enumCode}" ? "" : "none";
		}
		
		function checkRecharge() {
			if (document.all('isRecharge').value == "${x_yes.enumCode}") {
				var table = document.all('recharge_datatable');
				for (var i = 0; i < table.rows.length; i++ ) {
					//person must be choosen where recharge to person --nicebean 07-01-12
					//if (table.rows[i].all('rechargeType').value != "${x_rechargeTypePerson.enumCode}") {
						if (table.rows[i].all('span_recharge_name').innerHTML == "") {
							alert('<bean:message key="recharge.selectUser.first"/>');
							table.rows[i].all('selectIcon').focus();
							return false;
						}
					//}
				}
				if (Math.abs(totalRechargeAmount - X_totalAmount) > 0.01) {
					alert('<bean:message key="recharge.totalPercentageMustBe100"/>');
					return false;
				}
			}
			
			return true;
		}
	//-->
	</script>
	<table>
		<tr>
			<td align="right"><input type="button" value="<bean:message key="recharge.button.addline"/>" onclick="addNewClicked();"/></td>
		</tr>
	</table>
	<table class="data" width="100%">
		<thead>
			<tr class="new_bg">
				<th width="20px" height="29">#</th>
				<th><bean:message key="recharge.user"/></th>
				<th width="100px"><bean:message key="recharge.amount"/></th>
				<th width="90px"><bean:message key="recharge.percentage"/></th>
				<th width="40px">&nbsp;</th>
			</tr>
			<tr style="display:none" id="template_recharge_row">
				<td align="center"><span id="span_recharge_seq"></span></td>
				<td>
					<html:select property="rechargeType" onchange="rechargeTypeChanged(this.parentNode.parentNode)">
						<html:options collection="X_RECHARGETYPELIST" property="enumCode" labelProperty = "${x_lang}ShortDescription"/>
					</html:select>
				    <select name="recharge_person_dep_id" onchange="this.parentNode.all('recharge_person_id').value = ''; this.parentNode.all('span_recharge_name').innerText = '';">
				    	<c:forEach var="x_department" items="${X_DEPARTMENTLIST}">
				    		<option value="${x_department.id}">${x_department.indentName}</option>
				    	</c:forEach>
				    </select>
					<span id="span_recharge_name"></span>
					&nbsp;<a href="javascript:void(0)" id="selectIcon" onclick="recharge_selectUser(this.parentNode.parentNode)"><img src="images/select.gif" width="16" height="16" border="0"/></a>
					<input type="hidden" name="recharge_customer_code" value=""/>
					<input type="hidden" name="recharge_person_id" value=""/>
				</td>
				<td align="right"><input type="text" name="recharge_amount" size="7" value="0" onblur="this.value=this.oldValue" onkeyup="calculatePercentage(this.parentNode.parentNode)"/></td>
				<td align="right"><input type="text" name="recharge_percentage" size="2" value="0" onblur="this.value=this.oldValue" onkeyup="calculateAmount(this.parentNode.parentNode)"/></td>
				<td align="center"><a href="javascript:void(0)" onclick="deleteRechargeRow(this.parentNode.parentNode);"><bean:message key="all.delete"/></a></td>
			</tr>
		</thead>
		<tbody id="recharge_datatable">
		</tbody>
		<tfoot>
			<tr>
				<td colspan="5"><hr width="100%"></td>
			</tr>
			<tr>
				<td></td>
				<td align="right"><strong><bean:message key="recharge.total"/></strong></td>
				<td align="right"><span id="span_recharge_total_amount"></span></td>
				<td align="right"><span id="span_recharge_total_percentage"></span></td>
			</tr>
		</tfoot>
	</table>
	<script type="text/javascript">
	<!--
		var table = document.all('recharge_datatable');
		<c:forEach var="X_RECHARGE" items="${X_RECHARGELIST}">
			<c:choose>
				<c:when test="${X_RECHARGE.customer!=null}">
		insertRechargeRow(table, "${X_RECHARGE.customer.code}", "", "", "${X_RECHARGE.customer.description} (<span style='color:${X_RECHARGE.customer.type.rechargeType.color}'><bean:write name="X_RECHARGE" property="customer.type.rechargeType.${x_lang}ShortDescription"/></span>)", ${X_RECHARGE.amount}, "${X_RECHARGE.customer.type.enumCode}");
				</c:when>
				<c:when test="${X_RECHARGE.person!=null}">
		insertRechargeRow(table, "", "${X_RECHARGE.personDepartment.id}", "${X_RECHARGE.person.id}", "${X_RECHARGE.person.name} (<span style='color:${x_rechargeTypePerson.color}'><bean:write name="x_rechargeTypePerson" property="${x_lang}ShortDescription"/></span>)", ${X_RECHARGE.amount}, "${x_rechargeTypePerson.enumCode}");
				</c:when>
				<c:otherwise>
		insertRechargeRow(table, "", "${X_RECHARGE.personDepartment.id}", "${X_RECHARGE.person.id}", "", ${X_RECHARGE.amount}, "${x_rechargeTypePerson.enumCode}");
				</c:otherwise>
			</c:choose>
		</c:forEach>
		applyRowStyle(table);
		document.getElementById('span_recharge_total_amount').setExpression("innerText", "Math.round(totalRechargeAmount * 100) / 100");
		document.getElementById('span_recharge_total_percentage').setExpression("innerText", "Math.round(totalRechargePercentage * 100) / 100");

		document.getElementById('choose_recharge').style.setExpression('display', 'document.forms(rechargeFormName).isRecharge.value=="${x_yes.enumCode}" ? "" : "none"');
	//-->
	</script>
</div>
</c:if>
