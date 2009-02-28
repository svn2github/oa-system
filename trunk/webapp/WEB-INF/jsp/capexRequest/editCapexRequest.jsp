<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%try {%>
<c:set var="metadata_yesno_YES" value="<%=com.aof.model.metadata.YesNo.YES%>"/>
<c:set var="metadata_yesno_NO" value="<%=com.aof.model.metadata.YesNo.NO%>"/>
<xml id="dataExchangeRate">
<data>
<c:forEach var="x_exchangeRate" items="${x_refCurrency}">
	<refenceCurrency id="${x_exchangeRate.currency.code}" name="${x_exchangeRate.currency.name}" exchangeRate="${x_exchangeRate.exchangeRate}"/>
</c:forEach>
</data>
</xml>

<xml id="configExchangeRate">
<config>
	<refenceCurrency/>
</config>
</xml>
<script type="text/javascript">
<!--
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
		refTotalAmountObj.value = Math.round(refTotalAmount * 100) / 100;		
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
-->	
</script>
<table width="100%" border="0" cellpadding="4" cellspacing="0">
	<tr>
		<td width="20%"></td>
		<td width="30%"></td>
		<td width="20%"></td>
		<td width="30%"></td>
	</tr>
	<tr>
		<td class="bluetext"><bean:message key="capexRequest.title" />:</td>
		<td colspan="3">
			<c:choose>
				<c:when test="${x_edit}"><html:text property="title" size="20"/><span class="required">*</span></c:when>
				<c:otherwise>${x_capexRequest.title}</c:otherwise>
			</c:choose>
		</td>
	</tr>
	<tr>
		<td class="bluetext"><bean:message key="capexRequest.activity" />:</td>
		<td>${x_capexRequest.activity}</td>
		<td class="bluetext"><bean:message key="capexRequest.capex.id" />:</td>
		<td><span class="blue-highlight">${x_capexRequest.capex.id}</span></td>
	</tr>
	<tr>
		<td class="bluetext" rowspan="4"><bean:message key="capexRequest.capex.department" />:</td>
		<td rowspan="4">
			<logic:iterate id="x_cd" name="x_capexDepartmentList" length="1">${x_cd.department.name}</logic:iterate>
			<logic:iterate id="x_cd" name="x_capexDepartmentList" offset="1">, ${x_cd.department.name}</logic:iterate>
		</td>
		<td class="bluetext"><bean:message key="capexRequest.requestDate"/>:</td>
		<td><bean:write name="x_capexRequest" property="requestDate" format="yyyy/MM/dd"/></td>
	</tr>
	<tr>
		<td class="bluetext"><bean:message key="capexRequest.capex.purchaseCategory.id" />:</td>
		<td>
			<c:choose>
				<c:when test="${x_capexRequest.capex.purchaseCategory==null}"><bean:message key="capexRequest.capex.purchaseCategory.all"/></c:when>
				<c:otherwise>${x_capexRequest.capex.purchaseCategory.description}</c:otherwise>
			</c:choose>
		</td>
	</tr>
	<tr>
		<td class="bluetext"><bean:message key="capexRequest.capex.purchaseSubCategory.id"/>:</td>
		<td>
			<c:choose>
				<c:when test="${x_capexRequest.capex.purchaseSubCategory==null}"><bean:message key="capexRequest.capex.purchaseSubCategory.all"/></c:when>
				<c:otherwise>${x_capexRequest.capex.purchaseSubCategory.description}</c:otherwise>
			</c:choose>
		</td>
	</tr>
	<tr>
		<td class="bluetext"><bean:message key="capexRequest.type" />:</td>
		<td>
			<span style="color:${capexRequest.type.color}">
				<bean:write name="x_capexRequest" property="type.${x_lang}ShortDescription"/>
			</span>
		</td>
	</tr>
	<tr>
		<td class="bluetext"><bean:message key="capexRequest.capex.site.location" />:</td>
		<c:if test="${sessionScope.LOGIN_USER.locale=='en'}">	
		<td>${x_capexRequest.capex.site.city.engName}</td>
		</c:if>
		<c:if test="${sessionScope.LOGIN_USER.locale!='en'}">
		<td>${x_capexRequest.capex.site.city.chnName}</td>
		</c:if>
		<td class="bluetext"><bean:message key="capexRequest.capex.yearlyBudget.id" />:</td>
		<c:if test="${x_capexRequest.capex.yearlyBudget != null}">	
		<td>${x_capexRequest.capex.yearlyBudget.code}</td>
		</c:if>
	</tr>
	<c:if test="${x_canViewYearlyBudgetAmount}">
	<tr>
		<td colspan="2"></td>		
		<td class="bluetext"><bean:message key="yearlyBudget.remainAmount" />:</td>
		<td>
			<c:if test="${x_capexRequest.capex.yearlyBudget != null}">	
				<fmt:formatNumber value="${x_capexRequest.capex.yearlyBudget.remainAmount}" maxFractionDigits="2" minFractionDigits="0"></fmt:formatNumber>
			</c:if>
		</td>
	</tr>
	</c:if>
	<tr>
		<td class="bluetext"><bean:message key="capexRequest.capex.site.id" />:</td>
		<td>${x_capexRequest.capex.site.name}</td>
		<td class="bluetext"><bean:message key="capexRequest.postAuditDate" />:</td>
		<td>
			<c:choose>
				<c:when test="${x_edit}">
					<html:text property="postAuditDate" size="6"  maxlength="10" />
					<a onclick="event.cancelBubble=true;" href="javascript:showCalendar('dimg1',false,'postAuditDate',null,null,'capexRequestForm')">
						<IMG align="absMiddle" border="0" id="dimg1" src="images/datebtn.gif" >
					</a>
				</c:when>
				<c:otherwise>
					<bean:write name="x_capexRequest" property="postAuditDate" format="yyyy/MM/dd"/>
				</c:otherwise>
			</c:choose>			
		</td>
	</tr>
	<tr>
		<td class="bluetext"><bean:message key="capexRequest.status" />:</td>
		<td>
			<span style="color:${capexRequest.status.color}">
				<bean:write name="x_capexRequest" property="status.${x_lang}ShortDescription"/>
			</span>
		</td>
	</tr>
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
			<c:choose>
			<c:when test="${x_edit}">
			<td colspan="2" rowspan="6">
				<html:textarea property="description" rows="12" cols="43" />					
			</td>
			</c:when>
			<c:otherwise>
			<td colspan="2" rowspan="6" align="left" valign="top">
				<bean:write name="x_capexRequest" property="description" format="yyyy/MM/dd"/>
			</td>
			</c:otherwise>
			</c:choose>	
			<td class="bluetext"><bean:message key="capexRequest.firstExpenseDate" />:</td>
			<td>
			<c:choose>
				<c:when test="${x_edit}">
					<html:text property="firstExpenseDate" size="6"  maxlength="10" />
					<a onclick="event.cancelBubble=true;" href="javascript:showCalendar('dimg2',false,'firstExpenseDate',null,null,'capexRequestForm')">
						<IMG align="absMiddle" border="0" id="dimg2" src="images/datebtn.gif" >
					</a>
				</c:when>
				<c:otherwise>	
					<bean:write name="x_capexRequest" property="firstExpenseDate" format="yyyy/MM/dd"/>
				</c:otherwise>
			</c:choose>	
			</td>
		</tr>
		<tr>
			<td class="bluetext"><bean:message key="capexRequest.lastExpenseDate" />:</td>
			<td>
				<c:choose>
					<c:when test="${x_edit}">
						<html:text property="lastExpenseDate" size="6"  maxlength="10" />
						<a onclick="event.cancelBubble=true;" href="javascript:showCalendar('dimg3',false,'lastExpenseDate',null,null,'capexRequestForm')">
							<IMG align="absMiddle" border="0" id="dimg3" src="images/datebtn.gif" >
						</a>
					</c:when>
					<c:otherwise>
						<bean:write name="x_capexRequest" property="lastExpenseDate" format="yyyy/MM/dd"/>
					</c:otherwise>
				</c:choose>	
			</td>
		</tr>
		<tr>
			<td class="bluetext"><bean:message key="capexRequest.completionDate" />:</td>
			<td>
				<c:choose>
					<c:when test="${x_edit}">
						<html:text property="completionDate" size="6"  maxlength="10" />
						<a onclick="event.cancelBubble=true;" href="javascript:showCalendar('dimg4',false,'completionDate',null,null,'capexRequestForm')">
							<IMG align="absMiddle" border="0" id="dimg4" src="images/datebtn.gif" >
						</a>
					</c:when>
					<c:otherwise>
						<bean:write name="x_capexRequest" property="completionDate" format="yyyy/MM/dd"/>
					</c:otherwise>
				</c:choose>	
			</td>
		</tr>
		<tr>
			<td class="bluetext"><bean:message key="capexRequest.projectLeader" />:</td>
			<td>
				<c:choose>
					<c:when test="${x_edit}">
						<span id="span_projectLeader">
							<c:if test="${x_capexRequest.projectLeader != null}">
								<bean:write name="x_capexRequest" property="projectLeader.name"/>
							</c:if>
						</span>&nbsp;
						<a href="javascript:selectProjectLeader()">
							<img src="images/select.gif" width="16" height="16" border="0"/>
						</a>
						<input type="hidden" name="projectLeader_id" value="<c:if test="${x_capexRequest.projectLeader != null}"><bean:write name="x_capexRequest" property="projectLeader.id"/></c:if>"/>
					</c:when>
					<c:otherwise>
						<c:if test="${x_capexRequest.projectLeader != null}">
							<bean:write name="x_capexRequest" property="projectLeader.name"/>
						</c:if>
					</c:otherwise>
				</c:choose>	
			</td>
		</tr>
		<tr>
			<td class="bluetext"><bean:message key="capexRequest.projectLeaderTitle" />:</td>
			<td>
				<c:choose>
					<c:when test="${x_edit}">
						<html:text property="projectLeaderTitle"/>
					</c:when>
					<c:otherwise>
						<bean:write name="x_capexRequest" property="projectLeaderTitle"/>
					</c:otherwise>
				</c:choose>	
			</td>
		</tr>
		<tr>
			<td class="bluetext"><bean:message key="capexRequest.accountingCode" />:</td>
			<td>
				<c:choose>
					<c:when test="${x_edit}">
						<html:text property="accountingCode"/>
					</c:when>
					<c:otherwise>
						<bean:write name="x_capexRequest" property="accountingCode"/>
					</c:otherwise>
				</c:choose>	
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
	<c:choose>
		<c:when test="${x_edit}">
				<html:checkbox property="isCapexType" value="${metadata_yesno_YES}" onclick="reverseCheckSameNameCheckBox(this);"/>
			</td>
			<td style="display:none">
				<html:checkbox property="isCapexType" value="${metadata_yesno_NO}" onclick="reverseCheckSameNameCheckBox(this);"/>			
		</c:when>
		<c:otherwise>
				<input type="checkbox" name="isCapexType" disabled <c:if test="${x_capexRequest.isCapexType == metadata_yesno_YES}">checked</c:if> value="${metadata_yesno_YES}"/>
		</c:otherwise>
	</c:choose>	
			</td>
			<td></td>
			
			<td class="bluetext" align="right">
				<bean:message key="capexRequest.newImplantation" />:
	<c:choose>
		<c:when test="${x_edit}">
				<html:checkbox property="isNewImplantationReason" value="${metadata_yesno_YES}" onclick="reverseCheckSameNameCheckBox(this);"/>
			</td>
			<td style="display:none">
				<html:checkbox property="isNewImplantationReason" value="${metadata_yesno_NO}" onclick="reverseCheckSameNameCheckBox(this);"/>
		</c:when>
		<c:otherwise>
				<input type="checkbox" name="isNewImplantationReason" disabled <c:if test="${x_capexRequest.isNewImplantationReason == metadata_yesno_YES}">checked</c:if> value="${metadata_yesno_YES}"/>				
		</c:otherwise>
	</c:choose>	
			</td>
			<td></td>
		</tr>
		<tr>			
			<td colspan="2"></td>
			<td class="bluetext" align="right">
				<bean:message key="capexRequest.capacityIncrease" />:
	<c:choose>
		<c:when test="${x_edit}">
				<html:checkbox property="isCapacityIncreaseReason" value="${metadata_yesno_YES}" onclick="reverseCheckSameNameCheckBox(this);"/>
			</td>
			<td style="display:none">
				<html:checkbox property="isCapacityIncreaseReason" value="${metadata_yesno_NO}" onclick="reverseCheckSameNameCheckBox(this);"/>
		</c:when>
		<c:otherwise>
				<input type="checkbox" name="isCapacityIncreaseReason" disabled <c:if test="${x_capexRequest.isCapacityIncreaseReason == metadata_yesno_YES}">checked</c:if> value="${metadata_yesno_YES}"/>
		</c:otherwise>
	</c:choose>	
			</td>
			<td></td>
		</tr>
		<tr>
			<td class="bluetext" align="right">
				<bean:message key="capexRequest.assetDisposal" />:
	<c:choose>
		<c:when test="${x_edit}">
				<html:checkbox property="isAssetDisposalType" value="${metadata_yesno_YES}" onclick="reverseCheckSameNameCheckBox(this);"/>
			</td>
			<td style="display:none">
				<html:checkbox property="isAssetDisposalType" value="${metadata_yesno_NO}" onclick="reverseCheckSameNameCheckBox(this);"/>
		</c:when>
		<c:otherwise>
				<input type="checkbox" name="isAssetDisposalType" disabled <c:if test="${x_capexRequest.isAssetDisposalType == metadata_yesno_YES}">checked</c:if> value="${metadata_yesno_YES}"/>				
		</c:otherwise>
	</c:choose>	
			</td>
			<td></td>
			
			<td class="bluetext" align="right">
				<bean:message key="capexRequest.costImprovement" />:
	<c:choose>
		<c:when test="${x_edit}">
				<html:checkbox property="isCostImprovementReason" value="${metadata_yesno_YES}" onclick="reverseCheckSameNameCheckBox(this);"/>
			</td>
			<td style="display:none">
				<html:checkbox property="isCostImprovementReason" value="${metadata_yesno_NO}" onclick="reverseCheckSameNameCheckBox(this);"/>
			</td>
		</c:when>
		<c:otherwise>
				<input type="checkbox" name="isCostImprovementReason" disabled <c:if test="${x_capexRequest.isCostImprovementReason == metadata_yesno_YES}">checked</c:if> value="${metadata_yesno_YES}"/>			
		</c:otherwise>
	</c:choose>	
			<td></td>
		</tr>
		<tr>			
			<td colspan="2"></td>
			<td class="bluetext" align="right">
				<bean:message key="capexRequest.newProductLine" />:
	<c:choose>
		<c:when test="${x_edit}">
				<html:checkbox property="isNewProductLineReason" value="${metadata_yesno_YES}" onclick="reverseCheckSameNameCheckBox(this);"/>
			</td>
			<td style="display:none">
				<html:checkbox property="isNewProductLineReason" value="${metadata_yesno_NO}" onclick="reverseCheckSameNameCheckBox(this);"/>
		</c:when>
		<c:otherwise>
				<input type="checkbox" name="isNewProductLineReason" disabled <c:if test="${x_capexRequest.isNewProductLineReason == metadata_yesno_YES}">checked</c:if> value="${metadata_yesno_YES}"/>
		</c:otherwise>
	</c:choose>	
			</td>
			<td></td>
		</tr>
		<tr>
			<td class="bluetext" align="right">
				<bean:message key="capexRequest.otherType" />:
	<c:choose>
		<c:when test="${x_edit}">
				<input type="text" name="otherType" value="${x_capexRequest.otherType}" 
				<c:if test="${x_capexRequest.otherType == null}">class="disabledStyle" disabled</c:if> 
				/>
				<input type="checkbox" 
				<c:if test="${x_capexRequest.otherType != null}">checked</c:if> 
				onclick="checkUncheckOther(this, document.getElementById('otherType'));"
				/>
		</c:when>
		<c:otherwise>
				<input type="text" name="otherType" value="<bean:write name="x_capexRequest" property="otherType"/>" style="border:0"/>				
				<input type="checkbox" disabled <c:if test="${x_capexRequest.otherType != null}">checked</c:if> />
		</c:otherwise>
	</c:choose>	
			</td>
			<td></td>
			<td class="bluetext" align="right">
				<bean:message key="capexRequest.downsizing" />:
	<c:choose>
		<c:when test="${x_edit}">
				<html:checkbox property="isDownsizingReason" value="${metadata_yesno_YES}" onclick="reverseCheckSameNameCheckBox(this);"/>
			</td>
			<td style="display:none">
				<html:checkbox property="isDownsizingReason" value="${metadata_yesno_NO}" onclick="reverseCheckSameNameCheckBox(this);"/>
		</c:when>
		<c:otherwise>
				<input type="checkbox" name="isDownsizingReason" disabled <c:if test="${x_capexRequest.isDownsizingReason == metadata_yesno_YES}">checked</c:if> value="${metadata_yesno_YES}"/>
		</c:otherwise>
	</c:choose>	
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
	<c:choose>
		<c:when test="${x_edit}">
				<html:checkbox property="isHSEReason" value="${metadata_yesno_YES}" onclick="reverseCheckSameNameCheckBox(this);"/>
			</td>
			<td style="display:none">
				<html:checkbox property="isHSEReason" value="${metadata_yesno_NO}" onclick="reverseCheckSameNameCheckBox(this);"/>
		</c:when>
		<c:otherwise>
				<input type="checkbox" name="isHSEReason" disabled <c:if test="${x_capexRequest.isHSEReason == metadata_yesno_YES}	">checked</c:if> value="${metadata_yesno_YES}"/>
		</c:otherwise>
	</c:choose>
			</td>
			<td></td>
		</tr>
		<tr>
			<td class="bluetext" align="right">
				<bean:message key="capexRequest.otherReason" />:				
	<c:choose>
		<c:when test="${x_edit}">
				<input type="text" name="otherReason" value="${x_capexRequest.otherReason}" 
				<c:if test="${x_capexRequest.otherReason == null}">class="disabledStyle" disabled</c:if> 
				/>
				<input type="checkbox" 
				<c:if test="${x_capexRequest.otherReason != null}">checked</c:if> 
				onclick="checkUncheckOther(this, document.getElementById('otherReason'));"
				/>
		</c:when>
		<c:otherwise>
				<input type="text" name="otherReason" value="<bean:write name="x_capexRequest" property="otherReason" />" style="border:0"/>							
				<input type="checkbox" disabled <c:if test="${x_capexRequest.otherReason != null}">checked</c:if> />
		</c:otherwise>
	</c:choose>		
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
				${x_capexRequest.capex.currency.name}
			</td>
			<td class="bluetext" align="right" valign="top">
				<bean:message key="capexRequest.refenceCurrency" />:
			</td>
			<td>
			<c:choose>
				<c:when test="${x_edit}">
					<html:select property="referenceCurrency_code"/><span class="required">*</span>
				</c:when>
				<c:otherwise>
					${x_capexRequest.referenceCurrency.code}
				</c:otherwise>
			</c:choose>		
			</td>
		</tr>
		<tr>
			<td class="bluetext" align="right">
				<bean:message key="capexRequest.capexCapitalizedAmount" />:
			</td>
			<td>
				<c:choose>
					<c:when test="${x_edit}">
					<html:text property="capexCapitalizedAmount" size="8" style="text-align=right" onblur="this.oldValue=this.value" onchange="lc_amount_onchange_with_change_tatal(this);"/>
					<span class="required">*</span>
					</c:when>
					<c:otherwise>		
					<input type="text" name="capexCapitalizedAmount" value="<fmt:formatNumber value="${x_capexRequest.capexCapitalizedAmount}" maxFractionDigits="2" minFractionDigits="0"></fmt:formatNumber>" size="8" readonly style="border=0;text-align=right"/>
					</c:otherwise>
				</c:choose>	
			</td>
			<td></td>
			<td>	
				<c:choose>
					<c:when test="${x_edit}">				
					<input type="text" name="reference_capexCapitalizedAmount" size="8" style="text-align=right" onblur="this.oldValue=this.value" onchange="rc_amount_onchange_with_change_tatal(this);"/>
					<span class="required">*</span>
				</c:when>
					<c:otherwise>
					<input type="text" name="reference_capexCapitalizedAmount" value="<fmt:formatNumber value="${x_capexRequest.capexCapitalizedAmount / x_capexRequest.referenceExchangeRate}" maxFractionDigits="2" minFractionDigits="0"></fmt:formatNumber>" readonly size="8" style="border=0;text-align=right"/>
					</c:otherwise>
				</c:choose>	
			</td>
		</tr>
		<tr>
			<td class="bluetext" align="right">
				<bean:message key="capexRequest.otherExpenseAmount" />:
			</td>
			<td>
				<c:choose>
					<c:when test="${x_edit}">
						<html:text property="otherExpenseAmount" size="8" style="text-align=right" onblur="this.oldValue=this.value" onchange="lc_amount_onchange_with_change_tatal(this);"/>
					</c:when>
					<c:otherwise>
						<input type="text" name="otherExpenseAmount" value="<fmt:formatNumber value="${x_capexRequest.otherExpenseAmount}" maxFractionDigits="2" minFractionDigits="0"></fmt:formatNumber>" size="8" readonly="true" style="border=0;text-align=right" onblur="this.oldValue=this.value" onchange="lc_amount_onchange_with_change_tatal(this);"/>
					</c:otherwise>
				</c:choose>	
			</td>
			<td></td>
			<td>
				<c:choose>
					<c:when test="${x_edit}">
						<input type="text" name="reference_otherExpenseAmount" size="8" style="text-align=right" onblur="this.oldValue=this.value" onchange="rc_amount_onchange_with_change_tatal(this);"/>
					</c:when>
					<c:otherwise>
						<input type="text" name="reference_otherExpenseAmount" value="<fmt:formatNumber value="${x_capexRequest.otherExpenseAmount / x_capexRequest.referenceExchangeRate}" maxFractionDigits="2" minFractionDigits="0"></fmt:formatNumber>" readonly size="8" style="border=0;text-align=right" onblur="this.oldValue=this.value" onchange="rc_amount_onchange_with_change_tatal(this);"/>
					</c:otherwise>
				</c:choose>	
			</td>
		</tr>
		<tr>
			<td class="bluetext" align="right">
				<bean:message key="capexRequest.assetDisposalAmount" />:
			</td>
			<td>
				<c:choose>
					<c:when test="${x_edit}">
						<html:text property="assetDisposalAmount" size="8" style="text-align=right" onblur="this.oldValue=this.value" onchange="lc_amount_onchange_with_change_tatal(this);"/>
					</c:when>
					<c:otherwise>
						<input type="text" name="assetDisposalAmount" value="<fmt:formatNumber value="${x_capexRequest.assetDisposalAmount}" maxFractionDigits="2" minFractionDigits="0"></fmt:formatNumber>" size="8" style="border=0;text-align=right" readonly="true" onblur="this.oldValue=this.value" onchange="lc_amount_onchange_with_change_tatal(this);"/>
					</c:otherwise>
				</c:choose>	
			</td>
			<td></td>
			<td>
				<c:choose>
					<c:when test="${x_edit}">
						<input type="text" name="reference_assetDisposalAmount" size="8" style="text-align=right" onblur="this.oldValue=this.value" onchange="rc_amount_onchange_with_change_tatal(this);"/>
					</c:when>
					<c:otherwise>
						<input type="text" name="reference_assetDisposalAmount" value="<fmt:formatNumber value="${x_capexRequest.assetDisposalAmount / x_capexRequest.referenceExchangeRate}" maxFractionDigits="2" minFractionDigits="0"></fmt:formatNumber>" size="8" style="border=0;text-align=right" readonly onblur="this.oldValue=this.value" onchange="rc_amount_onchange_with_change_tatal(this);"/>
					</c:otherwise>
				</c:choose>	
			</td>
		</tr>
		<tr>
			<td class="bluetext" align="right">
				<bean:message key="capexRequest.totalRequestAmount" />:
			</td>
			<td>
				<c:choose>
					<c:when test="${x_edit}">
						<input type="text" name="totalRequestAmount" size="8" value="<bean:write name="x_capexRequest" property="totalAmount"/>" onblur="this.oldValue=this.value" readonly style="text-align=right"/>
					</c:when>
					<c:otherwise>
						<input type="text" name="totalRequestAmount" size="8" value="<fmt:formatNumber value="${x_capexRequest.totalAmount}" maxFractionDigits="2" minFractionDigits="0"></fmt:formatNumber>" onblur="this.oldValue=this.value" readonly style="border=0;text-align=right"/>
					</c:otherwise>
				</c:choose>					
			</td>
			<td></td>
			<td>
				<c:choose>
					<c:when test="${x_edit}">
						<input type="text" name="reference_totalRequestAmount" value="<bean:write name="x_capexRequest" property="totalAmount"/>" onblur="this.oldValue=this.value" size="8" readonly style="text-align=right"/>
					</c:when>
					<c:otherwise>
						<input type="text" name="reference_totalRequestAmount" value="<fmt:formatNumber value="${x_capexRequest.totalAmount / x_capexRequest.referenceExchangeRate}" maxFractionDigits="2" minFractionDigits="0"></fmt:formatNumber>" onblur="this.oldValue=this.value" size="8" readonly style="border=0;text-align=right"/>
					</c:otherwise>
				</c:choose>					
			</td>
		</tr>
		<tr>
			<td class="bluetext" align="right">
				<bean:message key="capexRequest.grossBookAmount" />:
			</td>
			<td>
				<c:choose>
					<c:when test="${x_edit}">
						<html:text property="grossBookAmount" size="8" style="text-align=right" onblur="this.oldValue=this.value" onchange="lc_amount_onchange(this);"/>						
					</c:when>
					<c:otherwise>						
						<input type="text" name="grossBookAmount" value="<fmt:formatNumber value="${x_capexRequest.grossBookAmount}" maxFractionDigits="2" minFractionDigits="0"></fmt:formatNumber>" size="8" style="border=0;text-align=right" readonly="true" onblur="this.oldValue=this.value" onchange="lc_amount_onchange_with_change_tatal(this);"/>
					</c:otherwise>
				</c:choose>	
				
			</td>
			<td></td>
			<td>
				<c:choose>
					<c:when test="${x_edit}">
						<input type="text" name="reference_grossBookAmount" size="8" style="text-align=right" onblur="this.oldValue=this.value" onchange="rc_amount_onchange_with_change_tatal(this);"/>						
					</c:when>
					<c:otherwise>
						<input type="text" name="reference_grossBookAmount" value="<fmt:formatNumber value="${x_capexRequest.grossBookAmount / x_capexRequest.referenceExchangeRate}" maxFractionDigits="2" minFractionDigits="0"></fmt:formatNumber>" readonly size="8" style="border=0;text-align=right" onblur="this.oldValue=this.value" onchange="rc_amount_onchange_with_change_tatal(this);"/>						
					</c:otherwise>
				</c:choose>	
				
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
				<c:choose>
					<c:when test="${x_edit}">
						<html:text property="projectImpactNonOperating1" size="8"/>
					</c:when>
					<c:otherwise>
						${x_capexRequest.projectImpactNonOperating1}
					</c:otherwise>
				</c:choose>	
			</td>
			<td></td>
			<td>
				<c:choose>
					<c:when test="${x_edit}">
						<html:text property="projectImpactNonOperating2" size="8"/>
					</c:when>
					<c:otherwise>
						${x_capexRequest.projectImpactNonOperating2}
					</c:otherwise>
				</c:choose>	
			</td>
		</tr>
		<tr>
			<td class="bluetext" align="right">
				<bean:message key="capexRequest.projectImpactOther" />:
				<c:choose>
					<c:when test="${x_edit}">
						<html:text property="projectImpactOther1"/>
					</c:when>
					<c:otherwise>
						<input type="text" style="border:0" readonly name="projectImpactOther1" value="${x_capexRequest.projectImpactOther1}"/>
					</c:otherwise>
				</c:choose>	
			</td>
			<td>
				<c:choose>
					<c:when test="${x_edit}">
						<html:text property="projectImpactOther2" size="8"/>
					</c:when>
					<c:otherwise>
						${x_capexRequest.projectImpactOther2}
					</c:otherwise>
				</c:choose>	
			</td>
			<td></td>
			<td>
				<c:choose>
					<c:when test="${x_edit}">
						<html:text property="projectImpactOther3" size="8"/>
					</c:when>
					<c:otherwise>
						${x_capexRequest.projectImpactOther3}
					</c:otherwise>
				</c:choose>	
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
				<c:choose>
					<c:when test="${x_edit}">
						<html:text property="referenceExchangeRate" size="8" style="text-align=right"/>
						<span class="required">*</span>
					</c:when>
					<c:otherwise>
						<input type="text" style="border:0" name="referenceExchangeRate" size="8" style="text-align=right" readonly value="${x_capexRequest.referenceExchangeRate}"/>
					</c:otherwise>
				</c:choose>	
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
				<c:choose>
					<c:when test="${x_edit}">	
						<input type="radio" name="isIncludeLastForecast" <c:if test="${x_capexRequest.lastForecastAmount != null}">checked</c:if> onclick="clickIncludeLastForecast();"/>
					</c:when>
					<c:otherwise>
						<input type="radio" disabled name="isIncludeLastForecast" <c:if test="${x_capexRequest.lastForecastAmount != null}">checked</c:if> onclick="clickIncludeLastForecast();"/>
					</c:otherwise>
				</c:choose>	
			</td>
		</tr>
		<tr>
			<td class="bluetext" align="right">
				<bean:message key="capexRequest.lastForecastAmount" />:
			</td>
			<td>	
				<c:choose>
					<c:when test="${x_edit}">		
						<input type="text" name="lastForecastAmount" value="${x_capexRequest.lastForecastAmount}" size="8" <c:if test="${x_capexRequest.lastForecastAmount == null}">disabled class="disabledStyle"</c:if> style="text-align=right"/>
					</c:when>
					<c:otherwise>
						<input type="text" name="lastForecastAmount" value="${x_capexRequest.lastForecastAmount}" size="8" readonly style="text-align=right;border:0"/>
					</c:otherwise>
				</c:choose>	
			</td>
		</tr>
		<tr>
			<td class="bluetext" align="right">
				<bean:message key="capexRequest.notIncludeLastForecast" />:
			</td>
			<td>
				<c:choose>
					<c:when test="${x_edit}">	
						<input type="radio" name="isIncludeLastForecast" <c:if test="${x_capexRequest.lastForecastAmount == null}">checked</c:if> onclick="clickNotIncludeLastForecast();"/>
					</c:when>
					<c:otherwise>
						<input type="radio" name="isIncludeLastForecast" disabled <c:if test="${x_capexRequest.lastForecastAmount == null}">checked</c:if> onclick="clickNotIncludeLastForecast();"/>
					</c:otherwise>
				</c:choose>	
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
	
	<table width="100%" border=0 cellpadding=4 cellspacing=0>
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
				<c:choose>
					<c:when test="${x_edit}">	
						<html:text property="discountedCashFlowPayback" size="8" style="text-align=right"/>
					</c:when>
					<c:otherwise>
						<input type="text" name="discountedCashFlowPayback" value="${x_capexRequest.discountedCashFlowPayback}" size="8" style="border=0;text-align=right"/>						
					</c:otherwise>
				</c:choose>					
			</td>			
		</tr>
		<tr>
			<td class="bluetext" align="right">
				<bean:message key="capexRequest.internalRateOfReturn"/>:
			</td>
			<td>
				<c:choose>
					<c:when test="${x_edit}">	
						<html:text property="internalRateOfReturn" size="8" style="text-align=right"/>
					</c:when>
					<c:otherwise>
						
						<input type="text" name="internalRateOfReturn" value="${x_capexRequest.internalRateOfReturn}" size="8" style="border=0;text-align=right"/>						
					</c:otherwise>
				</c:choose>						
			</td>			
		</tr>	
		<tr>
			<td class="bluetext" align="right">
				<bean:message key="capexRequest.npvAmount" />:
			</td>
			<td>
				<c:choose>
					<c:when test="${x_edit}">	
						<html:text property="npvAmount" size="8" style="text-align=right" onblur="this.oldValue=this.value" onchange="lc_amount_onchange(this);"/>
					</c:when>
					<c:otherwise>
						<input type="text" name="npvAmount" value="<fmt:formatNumber value="${x_capexRequest.npvAmount}" maxFractionDigits="2" minFractionDigits="0"></fmt:formatNumber>" onblur="this.oldValue=this.value" onchange="lc_amount_onchange(this);" size="8" style="border=0;text-align=right"/>						
					</c:otherwise>
				</c:choose>				
			</td>
			<td class="bluetext" align="right">
				<bean:message key="capexRequest.referenceNpvAmount" />:
			</td>
			<td>
				<c:choose>
					<c:when test="${x_edit}">	
						<input type="text" name="reference_npvAmount" size="8" value="0" style="text-align=right" onblur="this.oldValue=this.value" onchange="rc_amount_onchange(this);"/>
					</c:when>
					<c:otherwise>
						<input type="text" name="reference_npvAmount" size="8" value="<fmt:formatNumber value="${x_capexRequest.npvAmount / x_capexRequest.referenceExchangeRate}" maxFractionDigits="2" minFractionDigits="0"></fmt:formatNumber>" readonly style="border=0;text-align=right" onblur="this.oldValue=this.value" onchange="rc_amount_onchange(this);"/>
					</c:otherwise>
				</c:choose>						
			</td>			
		</tr>	
		<tr>
			<td class="bluetext" align="right">
				<bean:message key="capexRequest.discountRate" />:
			</td>
			<td>
				<c:choose>
					<c:when test="${x_edit}">	
						<html:text property="discountRate" size="8" style="text-align=right"/>
					</c:when>
					<c:otherwise>
						<input type="text" name="discountRate" value="${x_capexRequest.discountRate}" size="8" style="border=0;text-align=right"/>						
					</c:otherwise>
				</c:choose>					
			</td>			
		</tr>	
	</table>
	
	<logic:notEmpty name="x_capexRequestHistoryList">
	<hr size="1"/>
	<table width="100%" border=0 cellpadding=4 cellspacing=0>
	<tr>
		<td colspan="4">
			<h3 style="color:blue"><bean:message key="capexRequest.history"/></h3>
			<table class="data">
				<thead>
					<tr bgcolor="#9999ff">
						<th><bean:message key="capexRequest.history.version" /></th>
						<th><bean:message key="capexRequest.title" /></th>
						<th><bean:message key="capexRequest.amount" /></th>
						<th><bean:message key="capexRequest.requestor.id" /></th>
						<th><bean:message key="capexRequest.requestDate" /></th>
					</tr>
				</thead>
				<tbody id="history_datatable">
					<logic:iterate id="x_cr" name="x_capexRequestHistoryList" indexId="x_index">
					<tr>
						<td>${x_index+1}</td>
						<td><a href="viewCapexRequest.do?id=${x_cr.id}&onlyview=true" target="capexRequestHistory">${x_cr.title}</a></td>
						<td align="right">${x_cr.amount}</td>
						<td>${x_cr.requestor.name}</td>
						<td><bean:write name="x_cr" property="requestDate" format="yyyy/MM/dd" /></td>
					</tr>
					</logic:iterate>
				</tbody>
			</table>
			<script type="text/javascript">
			    applyRowStyle(document.all('history_datatable'));
			</script>
		</td>
	</tr>
	</logic:notEmpty>
	<tr>
		<td colspan="4"><hr size="1"/></td>
	</tr>
</table>
<c:choose>
<c:when test="${x_edit}">
<script type="text/javascript">
<!--
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
    
	var totalBaseCurrencyAmount = ${x_capexRequest.amount};
	//var spanCapexRequestAmount =  document.getElementById('span_capexRequest_amount');
	//if (spanCapexRequestAmount) spanCapexRequestAmount.setExpression('innerText', 'Math.round(totalBaseCurrencyAmount * 100) / 100');
	
	mapping = new Map();
	mapping.put("referenceCurrency_code", "refenceCurrency");	
	form.referenceCurrency_code.oldValue = '${x_capexRequest.referenceCurrency.code}';	
	form.referenceCurrency_code.afterChange = rc_onchange;
	initCascadeSelect("configExchangeRate", "dataExchangeRate", "capexRequestForm", mapping, true);
	form.referenceCurrency_code.afterChange = referenceExchangeRate_onChange;		
	
	//parse Total Requested: 
	totalAmountObj = getTotalLC();
	totalAmountObj.value = parseFloat(totalAmountObj.value);
	
	//parse If yes, for which amount (k RC): 
	if (form.lastForecastAmount.value != "") {
		form.lastForecastAmount.value = parseFloat(form.lastForecastAmount.value);
	}
	
	//parse discountedCashFlowPayback
	if (form.discountedCashFlowPayback.value != "") {
		form.discountedCashFlowPayback.value = parseFloat(form.discountedCashFlowPayback.value);
	}
	
	//parse internalRateOfReturn
	if (form.internalRateOfReturn.value != "") {
		form.internalRateOfReturn.value = parseFloat(form.internalRateOfReturn.value);
	}
	
	//parse discountRate
	if (form.discountRate.value != "") {
		form.discountRate.value = parseFloat(form.discountRate.value);
	}	
//-->
</script>
</c:when>
</c:choose>
<%
	} catch(Exception ex) {
		ex.printStackTrace();
	} 
%>