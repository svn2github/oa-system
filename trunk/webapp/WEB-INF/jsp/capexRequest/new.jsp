<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>

<xml id="data">
<data>
<logic:iterate id="x_site" name="x_siteList">
	<c:if test="${sessionScope.LOGIN_USER.locale=='en'}">					
	<site id="${x_site.id}" name="<bean:write name="x_site" property="name"/>" location="<bean:write name="x_site" property="city.engName"/>" activity="<bean:write name="x_site" property="activity"/>">
	</c:if>
	<c:if test="${sessionScope.LOGIN_USER.locale!='en'}">
	<site id="${x_site.id}" name="<bean:write name="x_site" property="name"/>" location="<bean:write name="x_site" property="city.chnName"/>" activity="<bean:write name="x_site" property="activity"/>">
	</c:if>		
		<currency id="${x_site.baseCurrency.code}" name="<bean:write name="x_site" property="baseCurrency.name"/>"/>
		<logic:iterate id="x_department" name="x_site" property="departments">
		<c:if test="${x_department.granted}">
		<department id="${x_department.id}" name="<bean:write name="x_department" property="indentName"/>"/>
		</c:if>
		</logic:iterate>
		<purchaseCategory id="" name="<bean:message key="capexRequest.capex.purchaseCategory.all" />">
			<purchaseSubCategory id="" name="<bean:message key="capexRequest.capex.purchaseSubCategory.select" />"/>
		</purchaseCategory>
		<logic:iterate id="x_pc" name="x_site" property="enabledPurchaseCategoryList">
		<purchaseCategory id="${x_pc.id}" name="<bean:write name="x_pc" property="description"/>">
			<purchaseSubCategory id="" name="<bean:message key="capexRequest.capex.purchaseSubCategory.all" />"/>
			<logic:iterate id="x_psc" name="x_pc" property="enabledPurchaseSubCategoryList">
			<purchaseSubCategory id="${x_psc.id}" name="<bean:write name="x_psc" property="description"/>"/>
			</logic:iterate>
		</purchaseCategory>
		</logic:iterate>
		<logic:iterate id="x_exchangeRate" name="x_site" property="enabledExchangeRateList">
		<refenceCurrency id="${x_exchangeRate.currency.code}" name="${x_exchangeRate.currency.name}" exchangeRate="${x_exchangeRate.exchangeRate}"/>
		</logic:iterate>
	</site>
</logic:iterate>
</data>
</xml>

<xml id="config">
<config>
	<site>
		<currency/>
		<department/>
		<purchaseCategory>
			<purchaseSubCategory/>
		</purchaseCategory>
		<refenceCurrency/>
	</site>
</config>
</xml>

<script type="text/javascript">
<!--
	var currentYearlyBudget = null;
	function selectYearlyBudget() {
		var form = document.capexRequestForm;
		var dep_url = '';
		with (form.capex_department_id) {
			for (i = 0; i < options.length; i++) {
				if (options[i].selected) {
					dep_url += '&capex_department_id=' + options[i].value;
				}
			}
		}
		if (dep_url == '') {
			alert('You muse select department');
			return;
		}
		var	v = window.showModalDialog(
			'showDialog.do?title=capexRequest.selectYearlyBudget.title&selectYearlyBudget.do?' +
			'&capex_site_id=' + form.capex_site_id.value +
			'&capex_purchaseCategory_id=' + form.capex_purchaseCategory_id.value + 
			'&capex_purchaseSubCategory_id=' + form.capex_purchaseSubCategory_id.value + dep_url, 
			null, 'dialogWidth:650px;dialogHeight:300px;status:no;help:no;scroll:no');
		if (v) {
			form.capex_yearlyBudget_id.value = v['id'];
			document.getElementById('span_yearlyBudget').innerText = v['code'];
			currentYearlyBudget = v;
		}
	}
	
	function checkYearlyBudget() {
		if (currentYearlyBudget == null) return;
		var form = document.capexRequestForm;
		if (currentYearlyBudget['site.id'] != form.capex_site_id.value) {
			clearYearlyBudget();
			return;
		}
		with (form.capex_department_id) {
			for (i = 0; i < options.length; i++) {
				if (options[i].selected) {
					if (currentYearlyBudget['departments'].indexOf(',' + options[i].value + ',') == -1) {
						clearYearlyBudget();
						return;
					}
				}
			}
		}
		if (currentYearlyBudget['purchaseCategory.id'] == '') return;
		if (currentYearlyBudget['purchaseCategory.id'] != form.capex_purchaseCategory_id.value) {
			clearYearlyBudget();
			return;
		}
		if (currentYearlyBudget['purchaseSubCategory.id'] == '') return;
		if (currentYearlyBudget['purchaseSubCategory.id'] != form.capex_purchaseSubCategory_id.value) {
			clearYearlyBudget();
			return;
		}
	}
	
	function clearYearlyBudget() {
		currentYearlyBudget = null;
		document.capexRequestForm.capex_yearlyBudget_id.value = '';
		document.getElementById('span_yearlyBudget').innerText = '';
		alert('<bean:message key="capexRequest.clearYearlyBudget.msg"/>');
	}
	
	function capex_site_onChange() {
		capex_site_activity_onChange();
		capex_site_location_onChange();
		checkYearlyBudget();
	}
	
	function capex_site_activity_onChange() {
		var form = document.capexRequestForm;
		var capexOption = form.capex_site_id.options[form.capex_site_id.selectedIndex];
		form.activity.value = capexOption.activity;
	}
	
	function capex_site_location_onChange() {
		var form = document.capexRequestForm;
		var capexOption = form.capex_site_id.options[form.capex_site_id.selectedIndex];
		form.capex_site_location.value = capexOption.location;
	}
	
	function referenceExchangeRate_onChange() {
		var form = document.capexRequestForm;
		var currencyOption = form.referenceCurrency_code.options[form.referenceCurrency_code.selectedIndex];
		form.referenceExchangeRate.value = currencyOption.exchangeRate;
		rc_onchange();
	}
	
	function checkUncheckOther(chkObj, txtObj) {
		txtObj.disabled = !chkObj.checked;
		if (txtObj.disabled) {
			txtObj.className = "disabledStyle";
		} else {
			txtObj.className = "";
		}
	}
	
	function clickIncludeLastForecast() {
		var form = document.capexRequestForm;
		form.lastForecastAmount.disabled = false;
		form.lastForecastAmount.className = ""
	}
	
	function clickNotIncludeLastForecast() {
		var form = document.capexRequestForm;
		form.lastForecastAmount.disabled = true;
		form.lastForecastAmount.className = "disabledStyle"
	}
	
	function lc_amount_onchange (obj) {
		if (obj.value == "") {
			obj.value = 0;
		} else if (isNaN(obj.value)) {
			obj.value = obj.oldValue;
			return false;
		}	
		obj.value = parseFloat(obj.value);			
		var refObj = document.getElementById("reference_" + obj.name);		
		var refExchangeRate = getReferenceExchangeRate();
		refObj.value = Math.round(obj.value * 100 / refExchangeRate) / 100;			
	}
	
	function lc_amount_onchange_with_change_tatal (obj) {
		if (obj.value == "") {
			obj.value = 0;
		} else if (isNaN(obj.value)) {
			obj.value = obj.oldValue;
			return false;
		}	
		var totalAmount = getTotalLC();
		var refTotalAmount = getTotalRC();		
		obj.value = parseFloat(obj.value);	
		totalAmount.value = parseFloat(totalAmount.value) - parseFloat(obj.oldValue) + parseFloat(obj.value);					
		var refObj = document.getElementById("reference_" + obj.name);	
		refTotalAmount.value = parseFloat(refTotalAmount.value) - parseFloat(refObj.value);	
		var refExchangeRate = getReferenceExchangeRate();
		refObj.value = Math.round(obj.value * 100 / refExchangeRate) / 100;		
		refTotalAmount.value = Math.round((parseFloat(refTotalAmount.value) + parseFloat(refObj.value)) *100) /100;		
	}
	
	function rc_amount_onchange (refObj) {
		if (refObj.value == "") {
			refObj.value = 0;
		} else if (isNaN(refObj.value)) {
			obj.value = obj.oldValue;
			return false;
		} else {
			refObj.value = parseFloat(refObj.value);
		}
		var obj = document.getElementById(refObj.name.substring(10));
		var refExchangeRate = getReferenceExchangeRate();
		obj.value = Math.round(refObj.value * 100 * refExchangeRate) / 100;	
	}
	
	function rc_amount_onchange_with_change_tatal (refObj) {
		if (refObj.value == "") {
			refObj.value = 0;
		} else if (isNaN(refObj.value)) {
			obj.value = obj.oldValue;
			return false;
		} else {
			refObj.value = parseFloat(refObj.value);
		}
		var totalAmount = getTotalLC();
		var refTotalAmount = getTotalRC();	
		refTotalAmount.value = parseFloat(refTotalAmount.value) - parseFloat(refObj.oldValue) + parseFloat(refObj.value);					
		var obj = document.getElementById(refObj.name.substring(10));
		totalAmount.value = parseFloat(totalAmount.value) - parseFloat(obj.value);
		var refExchangeRate = getReferenceExchangeRate();
		obj.value = Math.round(refObj.value * 100 * refExchangeRate) / 100;	
		totalAmount.value = Math.round((parseFloat(totalAmount.value) + parseFloat(obj.value)) *100) /100;
	}
		
	function rc_onchange() {
		var refTotalAmountObj = getTotalRC();	
		var refExchangeRate = getReferenceExchangeRate();
		refTotalAmount = 0;
		var capexCapitalized = document.getElementById("capexCapitalizedAmount");
		lc_amount_onchange(capexCapitalized);
		refTotalAmount += Math.round(capexCapitalized.value * 100 / refExchangeRate) / 100
		var otherExpense = document.getElementById("otherExpenseAmount");
		lc_amount_onchange(otherExpense);
		refTotalAmount += Math.round(otherExpense.value * 100 / refExchangeRate) / 100
		var assetDisposal = document.getElementById("assetDisposalAmount");
		lc_amount_onchange(assetDisposal);	
		refTotalAmount += Math.round(assetDisposal.value * 100 / refExchangeRate) / 100	
		var grossBook = document.getElementById("grossBookAmount");
		lc_amount_onchange(grossBook);
		var npv = document.getElementById("npvAmount");
		lc_amount_onchange(npv);
		refTotalAmountObj.value = refTotalAmount;	
	}
	
	function getReferenceExchangeRate() {
		return document.getElementById("referenceExchangeRate").value;
	}
	
	function getTotalLC() {
		return document.getElementById("totalRequestAmount");
	}
	
	function getTotalRC() {
		return document.getElementById("reference_totalRequestAmount");
	}
//-->
</script>
<html:javascript formName="capexRequestForm" staticJavascript="false" />
<html:form action="/insertCapexRequest" onsubmit="return validateCapexRequestForm(this)">
	<input type="hidden" name="capex_site_id_value" value="<bean:write name="capexRequestForm" property="capex_site_id"/>"/>
	<input type="hidden" name="capex_currency_code_value" value="<bean:write name="capexRequestForm" property="capex_currency_code"/>"/>
	<input type="hidden" name="capex_purchaseCategory_id_value" value="<bean:write name="capexRequestForm" property="capex_purchaseCategory_id"/>"/>
	<input type="hidden" name="capex_purchaseSubCategory_id_value" value="<bean:write name="capexRequestForm" property="capex_purchaseSubCategory_id"/>"/>
	<div class="warning"><html:errors/></div>
	<table width="100%" border=0 cellpadding=4 cellspacing=0>
		<tr>
			<td width="20%"></td>
			<td width="30%"></td>
			<td width="20%"></td>
			<td width="30%"></td>
		</tr>
		<tr>
			<td class="bluetext"><bean:message key="capexRequest.title"/>:</td>
			<td colspan="3"><html:text property="title" size="40" /><span class="required">*</span></td>			
		</tr>
		<tr>
			<td class="bluetext"><bean:message key="capexRequest.activity" />:</td>
			<td>
				<input type="text" name="activity" style="border:0px" value="" readonly/>
			</td>
			<td class="bluetext"><bean:message key="capexRequest.capex.id" />:</td>
			<td>(<bean:message key="common.id.generateBySystem" />)</td>
		</tr>
		
		<tr>
			<td class="bluetext" rowspan="4"><bean:message key="capexRequest.capex.department" />:</td>
			<td rowspan="4">
				<html:select property="capex_department_id" size="5" multiple="true" onblur="checkYearlyBudget()" />
				<span class="required">*</span>
			</td>
			<td class="bluetext"><bean:message key="capexRequest.requestDate" />:</td>
			<td><bean:write name="x_capexRequest" property="requestDate" format="yyyy/MM/dd"/></td>
		</tr>		
		<tr>
			<td class="bluetext"><bean:message key="capexRequest.capex.purchaseCategory.id" />:</td>
			<td><html:select property="capex_purchaseCategory_id" onblur="checkYearlyBudget()" /></td>			
		</tr>
		<tr>
			<td class="bluetext"><bean:message key="capexRequest.capex.purchaseSubCategory.id" />:</td>
			<td><html:select property="capex_purchaseSubCategory_id" onblur="checkYearlyBudget()" /></td>			
		</tr>	
		<tr>
			<td class="bluetext"><bean:message key="capexRequest.type" />:</td>
			<td>
				<span style="color:${x_capexRequest.type.color}">
					<c:if test="${sessionScope.LOGIN_USER.locale=='en'}">${x_capexRequest.type.engShortDescription}</c:if>
					<c:if test="${sessionScope.LOGIN_USER.locale!='en'}">${x_capexRequest.type.chnShortDescription}</c:if>
				</span>
			</td>
		</tr>		
		<tr>		
			<td class="bluetext"><bean:message key="capexRequest.capex.site.location" />:</td>
			<td>
				<input type="text" name="capex_site_location" style="border:0px" value="" readonly/>
			</td>
			<td class="bluetext"><bean:message key="capexRequest.capex.yearlyBudget.id" />:</td>
			<td>
				<span id="span_yearlyBudget"></span>&nbsp;<a href="javascript:selectYearlyBudget()"><img src="images/select.gif" width="16" height="16" border="0"/></a>
				<html:hidden property="capex_yearlyBudget_id" />
			</td>
		</tr>
		<tr>		
			<td class="bluetext"><bean:message key="capexRequest.capex.site.id" />:</td>
			<td><html:select property="capex_site_id" onblur="checkYearlyBudget()"/><span class="required">*</span></td>
			<td class="bluetext"><bean:message key="capexRequest.postAuditDate" />:</td>
			<td>
				<input type="text" name="postAuditDate" value="" size="6" maxlength="10"/>
				<a onclick="event.cancelBubble=true;" href="javascript:showCalendar('dimg1',false,'postAuditDate',null,null,'capexRequestForm')">
					<IMG align="absMiddle" border="0" id="dimg1" src="images/datebtn.gif" >
				</a>
			</td>
		</tr>	
		<tr>
			<td class="bluetext"><bean:message key="capexRequest.status" />:</td>
			<td>
				<span style="color:${x_capexRequest.status.color}">
					<c:if test="${sessionScope.LOGIN_USER.locale=='en'}">${x_capexRequest.status.engShortDescription}</c:if>
					<c:if test="${sessionScope.LOGIN_USER.locale!='en'}">${x_capexRequest.status.chnShortDescription}</c:if>
				</span>
			</td>
		</tr>
	</table>
	<hr size="1"/>
	<table width="100%">
		<tr>
			<td align="left" width='10%' class='formtitle'><bean:message key="capexRequest.description" />
			</td>
		</tr>
	</table>
	<table width="100%" border=0 cellpadding=4 cellspacing=0>
		<tr>
			<td width="20%"></td>
			<td width="30%"></td>
			<td width="20%"></td>
			<td width="30%"></td>
		</tr>
		<tr>
			<td colspan="2" rowspan="6">
				<html:textarea property="description" rows="12" cols="43" />
			</td>
			<td class="bluetext"><bean:message key="capexRequest.firstExpenseDate" />:</td>
			<td>
				<input type="text" name="firstExpenseDate" value="" size="6" maxlength="10"/>
				<a onclick="event.cancelBubble=true;" href="javascript:showCalendar('dimg2',false,'firstExpenseDate',null,null,'capexRequestForm')">
					<IMG align="absMiddle" border="0" id="dimg2" src="images/datebtn.gif" >
				</a>
			</td>
		</tr>
		<tr>
			<td class="bluetext"><bean:message key="capexRequest.lastExpenseDate" />:</td>
			<td>
				<input type="text" name="lastExpenseDate" value="" size="6" maxlength="10"/>
				<a onclick="event.cancelBubble=true;" href="javascript:showCalendar('dimg3',false,'lastExpenseDate',null,null,'capexRequestForm')">
					<IMG align="absMiddle" border="0" id="dimg3" src="images/datebtn.gif" >
				</a>
			</td>
		</tr>
		<tr>
			<td class="bluetext"><bean:message key="capexRequest.completionDate" />:</td>
			<td>
				<input type="text" name="completionDate" value="" size="6" maxlength="10"/>
				<a onclick="event.cancelBubble=true;" href="javascript:showCalendar('dimg4',false,'completionDate',null,null,'capexRequestForm')">
					<IMG align="absMiddle" border="0" id="dimg4" src="images/datebtn.gif" >
				</a>
			</td>
		</tr>
		<tr>
			<td class="bluetext"><bean:message key="capexRequest.projectLeader" />:</td>
			<td>
				<span id="span_projectLeader"></span>&nbsp;
				<a href="javascript:selectProjectLeader()">
					<img src="images/select.gif" width="16" height="16" border="0"/>
				</a>
				<input type="hidden" name="projectLeader_id" value=""/>
			</td>
		</tr>
		<tr>
			<td class="bluetext"><bean:message key="capexRequest.projectLeaderTitle" />:</td>
			<td>
				<input type="text" name="projectLeaderTitle" value=""/>
			</td>
		</tr>
		<tr>
			<td class="bluetext"><bean:message key="capexRequest.accountingCode" />:</td>
			<td>
				<input type="text" name="accountingCode" value=""/>
			</td>
		</tr>
	</table>
	<hr size="1"/>
	<table width="100%">
		<tr>
			<td align="left" width='10%' class='formtitle'><bean:message key="capexRequest.projectTypeAndReasons" />
			</td>
		</tr>
	</table>
	<table width="100%" border=0 cellpadding=4 cellspacing=0>
		<tr>
			<td width="45%"></td>
			<td width="5%"></td>
			<td width="45%"></td>
			<td width="5%"></td>
		</tr>
		<tr>
			<td class="bluetext" align="right">
				<bean:message key="capexRequest.capitalExpenditure" />:
				<input type="checkbox" name="isCapexType" checked value="<%=net.sourceforge.model.metadata.YesNo.YES%>" onclick="reverseCheckSameNameCheckBox(this);"/>
			</td>
			<td style="display:none">
				<input type="checkbox" name="isCapexType" value="<%=net.sourceforge.model.metadata.YesNo.NO%>" onclick="reverseCheckSameNameCheckBox(this);"/>
			</td>
			<td></td>
			<td class="bluetext" align="right">
				<bean:message key="capexRequest.newImplantation" />:
				<input type="checkbox" name="isNewImplantationReason" value="<%=net.sourceforge.model.metadata.YesNo.YES%>" onclick="reverseCheckSameNameCheckBox(this);"/>
			</td>
			<td style="display:none">
				<input type="checkbox" name="isNewImplantationReason" checked value="<%=net.sourceforge.model.metadata.YesNo.NO%>" onclick="reverseCheckSameNameCheckBox(this);"/>
			</td>
			<td></td>
		</tr>
		<tr>			
			<td colspan="2"></td>
			<td class="bluetext" align="right">
				<bean:message key="capexRequest.capacityIncrease" />:
				<input type="checkbox" name="isCapacityIncreaseReason" value="<%=net.sourceforge.model.metadata.YesNo.YES%>" onclick="reverseCheckSameNameCheckBox(this);"/>
			</td>
			<td style="display:none">
				<input type="checkbox" name="isCapacityIncreaseReason" checked value="<%=net.sourceforge.model.metadata.YesNo.NO%>" onclick="reverseCheckSameNameCheckBox(this);"/>
			</td>
			<td></td>
		</tr>
		<tr>
			<td class="bluetext" align="right">
				<bean:message key="capexRequest.assetDisposal" />:
				<input type="checkbox" name="isAssetDisposalType" value="<%=net.sourceforge.model.metadata.YesNo.YES%>" onclick="reverseCheckSameNameCheckBox(this);"/>
			</td>
			<td style="display:none">
				<input type="checkbox" name="isAssetDisposalType" checked value="<%=net.sourceforge.model.metadata.YesNo.NO%>" onclick="reverseCheckSameNameCheckBox(this);"/>
			</td>
			<td></td>
			<td class="bluetext" align="right">
				<bean:message key="capexRequest.costImprovement" />:
				<input type="checkbox" name="isCostImprovementReason" value="<%=net.sourceforge.model.metadata.YesNo.YES%>" onclick="reverseCheckSameNameCheckBox(this);"/>
			</td>
			<td style="display:none">
				<input type="checkbox" name="isCostImprovementReason" checked value="<%=net.sourceforge.model.metadata.YesNo.NO%>" onclick="reverseCheckSameNameCheckBox(this);"/>
			</td>
			<td></td>
		</tr>
		<tr>			
			<td colspan="2"></td>
			<td class="bluetext" align="right">
				<bean:message key="capexRequest.newProductLine" />:
				<input type="checkbox" name="isNewProductLineReason" value="<%=net.sourceforge.model.metadata.YesNo.YES%>" onclick="reverseCheckSameNameCheckBox(this);"/>
			</td>
			<td style="display:none">
				<input type="checkbox" name="isNewProductLineReason" checked value="<%=net.sourceforge.model.metadata.YesNo.NO%>" onclick="reverseCheckSameNameCheckBox(this);"/>
			</td>
			<td></td>
		</tr>
		<tr>
			<td class="bluetext" align="right">
				<bean:message key="capexRequest.otherType" />:
				<input type="text" class="disabledStyle" name="otherType" value="" disabled/>
				<input type="checkbox" onclick="checkUncheckOther(this, document.getElementById('otherType'));"/>
			</td>
			<td></td>
			<td class="bluetext" align="right">
				<bean:message key="capexRequest.downsizing" />:
				<input type="checkbox" name="isDownsizingReason" value="<%=net.sourceforge.model.metadata.YesNo.YES%>" onclick="reverseCheckSameNameCheckBox(this);"/>
			</td>
			<td style="display:none">
				<input type="checkbox" name="isDownsizingReason" checked value="<%=net.sourceforge.model.metadata.YesNo.NO%>" onclick="reverseCheckSameNameCheckBox(this);"/>
			</td>
			<td></td>
		</tr>
		<tr>
			<td class="bluetext" align="right" valign="top" rowspan="2">
				<bean:message key="capexRequest.otherTypeDesc" />			
			</td>
			<td rowspan="2"></td>
			<td class="bluetext" align="right">
				<bean:message key="capexRequest.hse" />:
				<input type="checkbox" name="isHSEReason" value="<%=net.sourceforge.model.metadata.YesNo.YES%>" onclick="reverseCheckSameNameCheckBox(this);"/>
			</td>
			<td style="display:none">
				<input type="checkbox" name="isHSEReason" checked value="<%=net.sourceforge.model.metadata.YesNo.NO%>" onclick="reverseCheckSameNameCheckBox(this);"/>
			</td>
			<td></td>
		</tr>
		<tr>
			<td class="bluetext" align="right">
				<bean:message key="capexRequest.otherReason" />:
				<input type="text" class="disabledStyle" name="otherReason" value="" disabled/>
				<input type="checkbox" onclick="checkUncheckOther(this, document.getElementById('otherReason'));"/>
			</td>
			<td></td>
		</tr>
	</table>
	<hr size="1"/>
	<table width="100%">
		<tr>
			<td align="left" width='10%' class='formtitle'><bean:message key="capexRequest.amountRequested" />
			</td>
		</tr>
	</table>	
	<table width="100%" border=0 cellpadding=4 cellspacing=0>
		<tr>
			<td width="50%"></td>
			<td width="15%"></td>
			<td width="20%"></td>
			<td width="15%"></td>
		</tr>
		<tr>
			<td class="bluetext" align="right">
				<bean:message key="capexRequest.capex.currency.code" />:
			</td>
			<td>
				<html:select property="capex_currency_code" style="display:none" />
				<span id="span_capex_currency"></span>
			</td>
			<td class="bluetext" align="right" valign="top">
				<bean:message key="capexRequest.refenceCurrency" />:
			</td>
			<td>
				<html:select property="referenceCurrency_code"/><span class="required">*</span>
			</td>
		</tr>
		<tr>
			<td class="bluetext" align="right">
				<bean:message key="capexRequest.capexCapitalizedAmount" />:
			</td>
			<td>
				<input type="text" name="capexCapitalizedAmount" size="8" value="0" style="text-align=right" onblur="this.oldValue=this.value" onchange="lc_amount_onchange_with_change_tatal(this);"/>
				<span class="required">*</span>
			</td>
			<td></td>
			<td>
				<input type="text" name="reference_capexCapitalizedAmount" value="0" size="8" style="text-align=right" onblur="this.oldValue=this.value" onchange="rc_amount_onchange_with_change_tatal(this);"/>
				<span class="required">*</span>
			</td>
		</tr>
		<tr>
			<td class="bluetext" align="right">
				<bean:message key="capexRequest.otherExpenseAmount" />:
			</td>
			<td>
				<input type="text" name="otherExpenseAmount" size="8" value="0" style="text-align=right" onblur="this.oldValue=this.value" onchange="lc_amount_onchange_with_change_tatal(this);"/>
			</td>
			<td></td>
			<td>
				<input type="text" name="reference_otherExpenseAmount" value="0" size="8" style="text-align=right" onblur="this.oldValue=this.value" onchange="rc_amount_onchange_with_change_tatal(this);"/>
			</td>
		</tr>
		<tr>
			<td class="bluetext" align="right">
				<bean:message key="capexRequest.assetDisposalAmount" />:
			</td>
			<td>
				<input type="text" name="assetDisposalAmount" size="8" value="0" style="text-align=right" onblur="this.oldValue=this.value" onchange="lc_amount_onchange_with_change_tatal(this);"/>
			</td>
			<td></td>
			<td>
				<input type="text" name="reference_assetDisposalAmount" value="0" size="8" style="text-align=right" onblur="this.oldValue=this.value" onchange="rc_amount_onchange_with_change_tatal(this);"/>
			</td>
		</tr>
		<tr>
			<td class="bluetext" align="right">
				<bean:message key="capexRequest.totalRequestAmount" />:
			</td>
			<td>
				<input type="text" name="totalRequestAmount" size="8" value="0" onblur="this.oldValue=this.value" readonly style="text-align=right"/>
			</td>
			<td></td>
			<td>
				<input type="text" name="reference_totalRequestAmount" value="0" onblur="this.oldValue=this.value" size="8" readonly style="text-align=right"/>
			</td>
		</tr>
		<tr>
			<td class="bluetext" align="right">
				<bean:message key="capexRequest.grossBookAmount" />:
			</td>
			<td>
				<input type="text" name="grossBookAmount" size="8" value="0" style="text-align=right" onblur="this.oldValue=this.value" onchange="lc_amount_onchange(this);"/>
			</td>
			<td></td>
			<td>
				<input type="text" name="reference_grossBookAmount" value="0" size="8" style="text-align=right" onblur="this.oldValue=this.value" onchange="rc_amount_onchange(this);"/>
			</td>
		</tr>		
		<tr>
			<td>
				<br/>
			</td>
		</tr>
		<tr>
			<td class="bluetext" align="right">
				<bean:message key="capexRequest.otherProjectImpact" />
			</td>
		</tr>
		<tr>
			<td class="bluetext" align="right">
				<bean:message key="capexRequest.projectImpactNonOperating" />:
			</td>
			<td>
				<input type="text" name="projectImpactNonOperating1" size="8" value=""/>
			</td>
			<td></td>
			<td>
				<input type="text" name="projectImpactNonOperating2" value="" size="8"/>
			</td>
		</tr>
		<tr>
			<td class="bluetext" align="right">
				<bean:message key="capexRequest.projectImpactOther" />:
				<input type="text" name="projectImpactOther1" size="" value=""/>
			</td>
			<td>
				<input type="text" name="projectImpactOther2" size="8" value=""/>
			</td>
			<td></td>
			<td>
				<input type="text" name="projectImpactOther3" value="" size="8"/>
			</td>
		</tr>
		<tr>
			<td>
				<br/>
			</td>
		</tr>
		<tr>
			<td colspan="3" class="bluetext" align="right">
				<bean:message key="capexRequest.referenceExchangeRate" />:
			</td>
			<td>
				<input type="text" name="referenceExchangeRate" size="8" value="" style="text-align=right"/>
				<span class="required">*</span>
			</td>
		</tr>
		<tr>
			<td>
				<br/>
			</td>
		</tr>
		<tr>
			<td class="bluetext" align="right">
				<bean:message key="capexRequest.includeLastForecast" />:
			</td>
			<td>
				<input type="radio" name="isIncludeLastForecast" onclick="clickIncludeLastForecast();"/>
			</td>
		</tr>
		<tr>
			<td class="bluetext" align="right">
				<bean:message key="capexRequest.lastForecastAmount" />:
			</td>
			<td>		
				<input type="text" name="lastForecastAmount" value="" size="8" disabled class="disabledStyle" style="text-align=right"/>
			</td>
		</tr>
		<tr>
			<td class="bluetext" align="right">
				<bean:message key="capexRequest.notIncludeLastForecast" />:
			</td>
			<td>
				<input type="radio" name="isIncludeLastForecast" onclick="clickNotIncludeLastForecast();" checked/>
			</td>
		</tr>
	</table>
	<hr size="1"/>
	<table width="100%">
		<tr>
			<td align="left" width='10%' class='formtitle'><bean:message key="capexRequest.profitability" />
			</td>
		</tr>
	</table>	
	<table width="100%" border=0 ellpadding=4 cellspacing=0>
		<tr>
			<td width="50%"></td>
			<td width="15%"></td>
			<td width="20%"></td>
			<td width="15%"></td>
		</tr>
		<tr>
			<td class="bluetext" align="right">
				<bean:message key="capexRequest.discountedCashFlowPayback" />:
			</td>
			<td>
				<input type="text" name="discountedCashFlowPayback" size="8" value="" style="text-align=right"/>
			</td>			
		</tr>
		<tr>
			<td class="bluetext" align="right">
				<bean:message key="capexRequest.internalRateOfReturn"/>:
			</td>
			<td>
				<input type="text" name="internalRateOfReturn" size="8" value="" style="text-align=right"/>
			</td>			
		</tr>	
		<tr>
			<td class="bluetext" align="right">
				<bean:message key="capexRequest.npvAmount" />:
			</td>
			<td>
				<input type="text" name="npvAmount" size="8" value="0" style="text-align=right" onblur="this.oldValue=this.value" onchange="lc_amount_onchange(this);"/>
			</td>
			<td class="bluetext" align="right">
				<bean:message key="capexRequest.referenceNpvAmount" />:
			</td>
			<td>
				<input type="text" name="reference_npvAmount" size="8" value="0" style="text-align=right" onblur="this.oldValue=this.value" onchange="rc_amount_onchange(this);"/>
			</td>			
		</tr>	
		<tr>
			<td class="bluetext" align="right">
				<bean:message key="capexRequest.discountRate" />:
			</td>
			<td>
				<input type="text" name="discountRate" size="8" value="" style="text-align=right"/>
			</td>			
		</tr>		    		    		
	</table>
	<hr size="1"/>
	<table width=90% border=0 cellpadding=4 cellspacing=0>
		<tr>
			<td align="right">
				<html:submit><bean:message key="all.save" /></html:submit>
				<input type="button" value="<bean:message key="all.back" />" onclick="history.back()">
			</td>
		</tr>
	</table>
</html:form>
<script type="text/javascript">
	var form = document.capexRequestForm;
	
	//添加oldValue属性
    form.capexCapitalizedAmount.oldValue = form.capexCapitalizedAmount.value;
    form.reference_capexCapitalizedAmount.oldValue = form.reference_capexCapitalizedAmount.value;
    form.otherExpenseAmount.oldValue = form.otherExpenseAmount.value;
    form.reference_otherExpenseAmount.oldValue = form.reference_otherExpenseAmount.value;
    form.assetDisposalAmount.oldValue = form.assetDisposalAmount.value;
    form.reference_assetDisposalAmount.oldValue = form.reference_assetDisposalAmount.value;
    form.totalRequestAmount.oldValue = form.totalRequestAmount.value;
    form.reference_totalRequestAmount.oldValue = form.reference_totalRequestAmount.value;
    form.grossBookAmount.oldValue = form.grossBookAmount.value;
    form.reference_grossBookAmount.oldValue = form.reference_grossBookAmount.value;
    form.npvAmount.oldValue = form.npvAmount.value;
    form.reference_npvAmount.oldValue = form.reference_npvAmount.value;
    
    var mapping=new Map();
	mapping.put("capex_site_id", "site");
	mapping.put("capex_currency_code", "currency");
	mapping.put("capex_department_id", "department");
	mapping.put("capex_purchaseCategory_id", "purchaseCategory");
	mapping.put("capex_purchaseSubCategory_id", "purchaseSubCategory");
	mapping.put("referenceCurrency_code", "refenceCurrency");	
	//新增item时这个事件要放在initCascadeSelect前做，以便initCascadeSelect时触发
	form.capex_site_id.afterChange = capex_site_onChange;	
	form.referenceCurrency_code.oldValue = '<bean:write name="capexRequestForm" property="referenceCurrency_code"/>';
	form.referenceCurrency_code.afterChange = referenceExchangeRate_onChange;
    initCascadeSelect("config", "data", "capexRequestForm", mapping, true);
    document.getElementById("span_capex_currency").setExpression("innerText", "document.capexRequestForm.capex_currency_code.options[document.capexRequestForm.capex_currency_code.selectedIndex].innerText");
</script>
