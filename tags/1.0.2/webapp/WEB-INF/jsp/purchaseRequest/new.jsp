<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>
<%@ page import="com.aof.model.metadata.PurchaseRequestStatus"%>

<xml id="data">
<data>
<logic:iterate id="x_site" name="x_siteList">
	<site id="${x_site.id}" name="<bean:write name="x_site" property="name"/>">
		<currency id="${x_site.baseCurrency.code}" name="<bean:write name="x_site" property="baseCurrency.name"/>"/>
		<logic:iterate id="x_department" name="x_site" property="departments">
		<c:if test="${x_department.granted}">
			<department id="${x_department.id}" name="<bean:write name="x_department" property="indentName"/>"/>
		</c:if>
		</logic:iterate>
		<logic:iterate id="x_pc" name="x_site" property="enabledPurchaseCategoryList">
		<purchaseCategory id="${x_pc.id}" name="<bean:write name="x_pc" property="description"/>">
			<logic:iterate id="x_psc" name="x_pc" property="enabledPurchaseSubCategoryList">
				<purchaseSubCategory id="${x_psc.id}" name="<bean:write name="x_psc" property="description"/>"/>
			</logic:iterate>
		</purchaseCategory>
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
	</site>
</config>
</xml>

<script type="text/javascript">
<!--
	
	function clearCapex()
	{
		with(document.purchaseRequestForm)
		{
			capex_id.value="";
			clearSpan("capex_id_span");
			clearSpan("capex_description_span");
			clearSpan("capex_purchaseCategory_span");
			clearSpan("capex_purchaseSubCategory_span");
			clearSpan("capex_requestor_span");
			clearSpan("capex_requestDate");
		}
	}
	
	function site_change()
	{
		with(document.purchaseRequestForm)
		{
			checkBudget();
			clearRequestor();
		}
	}
	
	function department_change()
	{
		with(document.purchaseRequestForm)
		{
			checkBudget();
			clearRequestor();
		}
	}
	
	function comboChange()
	{
		var form=document.purchaseRequestForm;
		form.action="newPurchaseRequest${x_postfix}.do?X_I_M_BACK=true";
		form.submit();
	}
	
	function purchaseCategory_change()
	{
		with(document.purchaseRequestForm)
		{
			checkBudget();
		}
	}
	
	function purchaseSubCategory_change()
	{
		with(document.purchaseRequestForm)
		{
			checkBudget();
		}
	}
	
	
	function clearCapexWithAlert()
	{
		alert("<bean:message key="yearlyBudget.clearCapex"/>");
		clearCapex();
	}
	
	function clearYearlyBudget()
	{
		with(document.purchaseRequestForm)
		{
			yearlyBudget_id.value="";
			clearSpan("yearlyBudget_code_span");
			clearSpan("yearlyBudget_name_span");			
		}
	}
	
	function clearYearlyBudgetWithAlert()
	{
		alert("<bean:message key="yearlyBudget.clearYearlyBudget"/>");
		clearYearlyBudget();
	}
	

	function selectNoneBudget()
	{
		clearCapex();
		clearYearlyBudget();
	}
	
	function clearSpan(spanId)
	{
		document.getElementById(spanId).innerHTML="";		
	}
	
	function checkBudget()
	{
		with(document.purchaseRequestForm)
		{
			if(budgetType[0].checked)
			{
				this.clearYearlyBudget();

				this.checkCapex();				
			}
			else if(budgetType[1].checked)
			{
				this.clearCapex();

				this.checkYearlyBudget();
			}
		}			
	}
	
	function checkCapex()
	{
		with(document.purchaseRequestForm)
		{
			if (capex_id.value == "") return;
			
			var form = document.purchaseRequestForm;
			
			if (capex_site_id.value != department_site_id.value) {
				clearCapexWithAlert();
				return;
			}
			
			if (capex_departmentIds.value.indexOf(department_id.value) == -1) {
				clearCapexWithAlert();
				return;
			}
			
			if (capex_purchaseCategory_id.value == '') return;
			if (capex_purchaseCategory_id.value != purchaseSubCategory_purchaseCategory_id.value) {
				clearCapexWithAlert();
				return;
			}
			
			if (capex_purchaseSubCategory_id.value == '') return;
			if (capex_purchaseSubCategory_id.value != purchaseSubCategory_id.value) {
				clearCapexWithAlert();
				return;
			}
		}		
	}
	
	function checkYearlyBudget()
	{
		with(document.purchaseRequestForm)
		{
			if (yearlyBudget_id.value == "") return;
			
			var form = document.purchaseRequestForm;
			
			if (yearlyBudget_site_id.value != department_site_id.value) {
				clearYearlyBudgetWithAlert();
				return;
			}
			
			if (yearlyBudget_departmentIds.value.indexOf(department_id.value) == -1) {
				clearYearlyBudgetWithAlert();
				return;
			}
			
			if (yearlyBudget_purchaseCategory_id.value == '') return;
			if (yearlyBudget_purchaseCategory_id.value != purchaseSubCategory_purchaseCategory_id.value) {
				clearYearlyBudgetWithAlert();
				return;
			}
			
			if (yearlyBudget_purchaseSubCategory_id.value == '') return;
			
			if (yearlyBudget_purchaseSubCategory_id.value != purchaseSubCategory_id.value) {
				clearYearlyBudgetWithAlert();
				return;
			}
		}		
	}
	
	
	function clearBudget()
	{
		clearCapex();
		clearYearlyBudget();		
	}
	
	function getCurrentCode()
	{
		with(document.purchaseRequestForm)
		{
			if(!currency_code.options) return "";		
			var aOption=currency_code.options[currency_code.selectedIndex];
			if(!aOption) return "";
			else return aOption.text;
		}
	}
	
	function validateForm(form)
	{
		if(!validatePurchaseRequestForm(form)) return false;
		with(document.purchaseRequestForm)
		{
			if(budgetType[0].checked)
			{
				if(capex_id.value=="")
				{
					alert("<bean:message key="purchaseRequest.capex.required"/>");
					return false;
				}
			}
			else if(budgetType[1].checked)
			{
				if(yearlyBudget_id.value=="")
				{
					alert("<bean:message key="purchaseRequest.yearlyBudget.required"/>");
					return false;
				}
			}
		}		
		return true;
	}
	
	function selectCapex()
	{
		with(document.purchaseRequestForm)
		{
			var url="newPurchaseRequest${x_postfix}_selectCapex.do?purchaseSubCategory_id="+
				purchaseSubCategory_id.value+"&department_id="+department_id.value;
			var v=dialogAction(url,"purchaseRequest.selectCapexBudget",400,300);
			if(v)
			{
				document.getElementById("capexContentDIV").innerHTML=v;
			}
		}	
	}
	
	function selectYearlyBudget()
	{
		with(document.purchaseRequestForm)
		{
			var url="newPurchaseRequest${x_postfix}_selectYearlyBudget.do?purchaseSubCategory_id="+
				purchaseSubCategory_id.value+"&department_id="+department_id.value;
			var v=dialogAction(url,"purchaseRequest.selectYearlyBudget",400,300);
			if(v)
			{
				document.getElementById("yearlyBudgetContentDIV").innerHTML=v;
			}
		}	
	}
	
	function backToList()
	{
		var url="listPurchaseRequest${x_postfix}.do"
		window.location.href=url;
	}
	
	function getYearlyBudgetDIVDisplay()
	{
		with(document.purchaseRequestForm)
		{
			if(budgetType[1].checked) return "block";
			else return "none";
		}
	}
	
	function getYCapexDIVDisplay()
	{
		with(document.purchaseRequestForm)
		{
			if(budgetType[0].checked) return "block";
			else return "none";
		}
		
	}
	
	function onChangeSubCtgy()
	{		
		with(document.purchaseRequestForm)
		{
			var subCategory = document.purchaseRequestForm.purchaseSubCategory_id;
			var site = document.purchaseRequestForm.department_site_id;
			if (subCategory.options.selectedIndex != null) 
			{
				var siteValue = site.options[site.options.selectedIndex].value;
				var subCategoryValue = subCategory.options[subCategory.options.selectedIndex].value;
				var url="viewAviableSupplier.do?siteId="+siteValue+"&subCategoryValue="+subCategoryValue;
				var v=dialogAction(url,"supplier.viewAvailableSupplier",400,300);
			}
		}	
	}
	
	function selectRequestor() {		
		with(document.purchaseRequestForm)
		{
			var dep = document.purchaseRequestForm.department_id;
			var	v = window.showModalDialog(
			'showDialog.do?title=recharge.selectPerson.title&selectPurchaseRequestItemRechargePerson.do?departmentId=' + dep.options[dep.options.selectedIndex].value , 
			null, 'dialogWidth:650px;dialogHeight:500px;status:no;help:no;scroll:no');
			if(v)
			{
				document.getElementById("span_requestor").innerHTML=v['name'];
				document.getElementById("requestor_id").value=v['id'];
			}
		}	
	}
	
	function clearRequestor() {
		document.getElementById("span_requestor").innerHTML="";
		document.getElementById("requestor_id").value="";
	}
	
//-->
</script>
<html:javascript formName="purchaseRequestForm" staticJavascript="false" />
<html:form action="/insertPurchaseRequest${x_postfix}" onsubmit="return validateForm(this)">
	<input type="hidden" name="department_site_id_value" 
		value="<bean:write name="purchaseRequestForm" property="department_site_id"/>"/>
		
	<input type="hidden" name="department_id_value" 
		value="<bean:write name="purchaseRequestForm" property="department_id"/>"/>
		
		
	<input type="hidden" name="currency_code_value" 
		value="<bean:write name="purchaseRequestForm" property="currency_code"/>"/>
		
	<input type="hidden" name="purchaseSubCategory_purchaseCategory_id_value" 
		value="<bean:write name="purchaseRequestForm" property="purchaseSubCategory_purchaseCategory_id"/>"/>
		
	<input type="hidden" name="purchaseSubCategory_id_value" 
		value="<bean:write name="purchaseRequestForm" property="purchaseSubCategory_id"/>"/>
		

	<html:hidden property="requestor_id" />

		
	<table width="90%">
		<tr>
			<td class="warning">
				<html:errors />
			</td>
		</tr>
		<tr>
			<td>
				<div class="message">
					<html:messages id="x_message" message="true">
						${x_message}<br>
					</html:messages>
				</div>
			</td>
		</tr>
	</table>
	
	<table width=90% border=0 cellpadding=4 cellspacing=0>
		<tr>
			<td class="bluetext"><bean:message key="purchaseRequest.site.id" />:</td>
			<td colspan="1"><html:select property="department_site_id"/><span class="required">*</span></td>
			
			<td class="bluetext"><bean:message key="purchaseRequest.department" />:</td>
			<td><html:select property="department_id" /><span class="required">*</span></td>
		</tr>			   
		<tr>
			<td class="bluetext"><bean:message key="purchaseRequest.title"/>:</td>
			<td>
				<html:text property="title" size="20" />
				<span class="required">*</span>
			</td>
			
			<td class="bluetext"><bean:message key="purchaseRequest.id" />:</td>
			<td>(<bean:message key="common.id.generateBySystem" />)</td>
		</tr>
		<tr>
			<td class="bluetext"><bean:message key="purchaseRequest.description" />:</td>
			<td colspan="3"><html:textarea property="description" rows="2" cols="60" /></td>
		</tr>			    
		<tr>
			<td class="bluetext"><bean:message key="purchaseRequest.purchaseCategory.id" />:</td>
			<td><html:select property="purchaseSubCategory_purchaseCategory_id"  /><span class="required">*</span></td>
			
			<td class="bluetext"><bean:message key="purchaseRequest.purchaseSubCategory.id" />:</td>
			<td nowrap>
				<html:select property="purchaseSubCategory_id" />
				<span class="required">*</span>
				<a href="javascript:onChangeSubCtgy()"><bean:message key="supplier.viewAvailableSupplier" /></a>
			</td>
		</tr>
		<tr>

<%request.setAttribute("x_draft",PurchaseRequestStatus.DRAFT);%>
			<td class="bluetext"><bean:message key="purchaseRequest.status" />:</td>
			<td>
				<span style="color:${x_draft.color}">
					<bean:write name="x_draft" property="${x_lang}ShortDescription"/>
				</span>
			</td>
		</tr>
	</table>
	<hr/>
	<table >
		<tr>
			<td class='bluetext' width="30%">
				<bean:message key="purchaseRequest.withinBudget"/>:
			</td>
			<td align="center">
				<bean:message key="purchaseRequest.capexBudget"/>
				<html:radio property="budgetType" value="1"/>
			</td>
			<td align="center">
				<bean:message key="purchaseRequest.budget.code"/>
				<html:radio property="budgetType" value="2"/>
			</td>
			<td align="center">
				<bean:message key="purchaseRequest.nonBudget"/>
				<html:radio property="budgetType" value="3" onclick="selectNoneBudget()"/>
			</td>
		</tr>
	</table>
	<div style="display:none" id="yearlyBudgetDIV">
		<table border=0 cellpadding=4 cellspacing=0>
			<tr>
				<td  class="bluetext" >
					<bean:message key="purchaseRequest.selectYealyBudget"/>:
				</td>
				<td>
					<a href="javascript:selectYearlyBudget()"><img src="images/select.gif" width="16" height="16" border="0"/></a>
					<span class="required">*</span>
				</td>
			</tr>
		</table>
		<div id="yearlyBudgetContentDIV">
			<jsp:include page="yearlyBudgetContent.jsp" />
		</div>
	</div>
	
	<div style="display:none" id="capexDIV">
		<table border=0 cellpadding=4 cellspacing=0>
			<tr>
				<td  class="bluetext" >
					<bean:message key="purchaseRequest.selectCapexBudget"/> :
				</td>
				<td>
					<a href="javascript:selectCapex()"><img src="images/select.gif" width="16" height="16" border="0"/></a>
					<span class="required">*</span>
				</td>
			</tr>
		</table>
		
		<div id="capexContentDIV">
			<jsp:include page="capexContent.jsp" />
		</div>
	</div>
	
	<hr/>
	
	<table width="86%">
		<tr>
			<td  class="bluetext"><bean:message key="purchaseRequest.requestAmount"/> : </td>
			<td >
				(<bean:message key="purchaseRequest.requestAmount.autoCalculated"/>)
			</td>
		 	<td  align="left">
				<bean:message key="purchaseRequest.baseCurrency"/>:
			</td>
			<td  align="left">
				<span id="currencyCodeSpan"></span><html:select property="currency_code" style="display:none" />
			</td>
	   </tr>
	   <tr>	
			<td class='bluetext'><bean:message key="purchaseRequest.requestor.id"/>:</td>
		 	 <td>
              <c:choose>
		        <c:when test="${x_postfix=='_self'}">
		                ${sessionScope.LOGIN_USER.name}
		        </c:when>
        	    <c:otherwise>
                    <span id="span_requestor"></span>
                    <a href="javascript:selectRequestor()"><img src="images/select.gif" width="16" height="16" border="0"/><span class="required">*</span>
                    <html:hidden property="requestor_id"/>
				</c:otherwise>
			   </c:choose>
             </td>
		 	<td class='bluetext'><bean:message key="purchaseRequest.requestDate"/>:</td>
			 <td></td>
		</tr>
	</table>	
	
	<hr/>
	
	<table width=90% border=0 cellpadding=4 cellspacing=0>
		<tr>
			<td align="right">
				<html:submit><bean:message key="all.save" /></html:submit>
				<input type="button" value="<bean:message key="all.back" />" onclick="backToList()">
			</td>
		</tr>
	</table>
</html:form>
<script type="text/javascript">
    var mapping=new Map();
	mapping.put("department_site_id", "site");
	mapping.put("currency_code", "currency");
	mapping.put("department_id", "department");
	mapping.put("purchaseSubCategory_purchaseCategory_id", "purchaseCategory");
	mapping.put("purchaseSubCategory_id", "purchaseSubCategory");
	
    initCascadeSelect("config", "data", "purchaseRequestForm", mapping, true);
    document.getElementById("currencyCodeSpan").setExpression("innerText",
		"getCurrentCode()");
		
	document.getElementById("yearlyBudgetDIV").style.setExpression("display","getYearlyBudgetDIVDisplay()");
	document.getElementById("capexDIV").style.setExpression("display","getYCapexDIVDisplay()");	
	
	with(document.purchaseRequestForm)
	{
		department_id.afterChange=department_change;
		department_site_id.afterChange=site_change;
		purchaseSubCategory_purchaseCategory_id.afterChange=purchaseCategory_change;
		purchaseSubCategory_id.afterChange=purchaseSubCategory_change;
	}
	
</script>
