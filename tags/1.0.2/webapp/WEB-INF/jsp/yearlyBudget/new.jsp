<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>
<%@ page import="java.util.Set"%>


<xml id="data">
<data>
	<pc id="" name="<bean:message key="yearlyBudget.dropdownlistvalue.all"/>">
		<psc id="" name="<bean:message key="yearlyBudget.dropdownlistvalue.all"/>"></psc>
	</pc>
<logic:iterate id="x_pc" name="x_purchaseCategoryList">
	<pc id="${x_pc.id}" name="<bean:write name="x_pc" property="description"/>">
		<psc id="" name="<bean:message key="yearlyBudget.dropdownlistvalue.all"/>"></psc>
		<logic:iterate id="x_psc" name="x_pc" property="enabledPurchaseSubCategoryList">
			<psc id="${x_psc.id}" name="<bean:write name="x_psc" property="description"/>">
			</psc>
		</logic:iterate>
	</pc>
</logic:iterate>
</data>
</xml>

<xml id="config">
<config>
	<pc>
		<psc>
		</psc>
	</pc>
</config>
</xml>

<script>
	function backToList()
	{
		var url="listYearlyBudget.do";
		window.location.href=url;
	}
	function selectAll()
	{
		with(document.yearlyBudgetForm)
		{
			var objForm = document.forms[0];
		    var objLen = objForm.length;
		    for (var iCount = 0; iCount < objLen; iCount++)
		    {
		        if (objForm.elements[iCount].type == "checkbox")
		        {
		            objForm.elements[iCount].checked = true;
		        }
			}	
		}
	}
	
	function selectNone()
	{
		with(document.yearlyBudgetForm)
		{
			var objForm = document.forms[0];
		    var objLen = objForm.length;
		    for (var iCount = 0; iCount < objLen; iCount++)
		    {
		        if (objForm.elements[iCount].type == "checkbox")
		        {
		            objForm.elements[iCount].checked = false;
		        }
			}	
		}
	}
	
	function validateForm(form)
	{
		if(!validateYearlyBudgetForm(form)) return false;
		var objForm = document.forms[0];
	    var objLen = objForm.length;
	    var checked=false;
	    for (var iCount = 0; iCount < objLen; iCount++)
	    {
	        if (objForm.elements[iCount].type == "checkbox")
	        {
	            if(objForm.elements[iCount].checked) 
	            {
	            	checked=true;
		            break;
		        }
	        }
		}	
		if(!checked)
		{
			alert("<bean:message key="yearlyBudget.department.notChecked"/>");
			return false;
		}
		return true;
	}
	
	function initPurchaseCategory() 
	{
		var mapping=new Map();
		mapping.put("purchaseCategory_id","pc");
		mapping.put("purchaseSubCategory_id","psc");
		initCascadeSelect("config","data","yearlyBudgetForm",mapping,true);
	}
		
	function onchangeBudgetType()
	{
		if(yearlyBudgetForm.type.options[yearlyBudgetForm.type.selectedIndex].value == "<%=com.aof.model.metadata.BudgetType.Expense.getEnumCode()%>")
		{
			document.getElementById("yearTr").style.display = "none";
			document.getElementById("durationFromTr").style.display = "inline";
			document.getElementById("durationToTr").style.display = "inline";
			document.getElementById("purchaseCategoryTr").style.display = "none";
			document.getElementById("purchaseSubCategoryTr").style.display = "none";
			document.getElementById("expenseCategoryTr").style.display = "inline";
			
			yearlyBudgetForm.expenseCategory_id.selectedIndex = 0;
		} else {
			document.getElementById("yearTr").style.display = "inline";
			document.getElementById("durationFromTr").style.display = "none";
			document.getElementById("durationToTr").style.display = "none";
			document.getElementById("purchaseCategoryTr").style.display = "inline";
			document.getElementById("purchaseSubCategoryTr").style.display = "inline";
			document.getElementById("expenseCategoryTr").style.display = "none";
			
			initPurchaseCategory();
		}
	}
</script>
<html:javascript formName="yearlyBudgetForm" staticJavascript="false"/>
<html:form action="/insertYearlyBudget" onsubmit="return validateForm(this)">
	<input type="hidden" name="purchaseCategory_id_value" value="<bean:write name="yearlyBudgetForm" property="purchaseCategory_id"/>">
	<input type="hidden" name="purchaseSubCategory_id_value" value="<bean:write name="yearlyBudgetForm" property="purchaseSubCategory_id"/>">

	<table width="90%">
		<tr>
			<td>
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
	<table border=0 cellpadding=4 cellspacing=0>
		<tr>
			<td><bean:message key="yearlyBudget.site.id"/>:</td>
			<td>
				${x_site.name}<html:hidden property="site_id"/>
			</td>
		</tr>
		<tr>
			<td valign="top"><bean:message key="yearlyBudget.department.id"/>:</td>
			<td>
				<logic:iterate name="x_site" property="departments" id="x_department">
				<div>
<c:set var="x_department_id" value="${x_department.id}" scope="request"/>							
					<input type="checkbox" name="departments" value="${x_department.id}"
<%
	Set departmentIdSet=(Set)request.getAttribute("x_departmentIdSet");
	String departmentId=request.getAttribute("x_department_id").toString();
	if(departmentIdSet.contains(departmentId)) out.print("checked");
%>										
					/>
					${x_department.indentName}
				</div>
				</logic:iterate>
				<div>
					<input type="button" value="select all" onclick="selectAll()">
					<input type="button" value="select none" onclick="selectNone()">
					<span class="required">*</span>
				</div>
			</td>
		</tr>
		<tr>
			<td><bean:message key="yearlyBudget.code"/>:</td>
			<td><html:text property="code" size="20" maxlength="20"/><span class="required">*</span></td>
		</tr>
		<tr>
			<td><bean:message key="yearlyBudget.name"/>:</td>
			<td><html:text property="name" size="30"  maxlength="50"/><span class="required">*</span></td>
		</tr>
		<tr>
			<td><bean:message key="yearlyBudget.type"/>:</td>
			<td>
				<html:select property="type" onchange="javascript:onchangeBudgetType();">
					<html:options collection="x_typeList"
						property="enumCode" labelProperty="${x_lang}ShortDescription" />
				</html:select><span class="required">*</span>
			</td>
		</tr>
		<tr id="yearTr">
			<td><bean:message key="yearlyBudget.year"/>:</td>
			<td><html:select property="year">
				<logic:iterate name="x_yearList" id="x_year">
					<html:option value="${x_year}">${x_year}</html:option>
				</logic:iterate>
			</html:select><span class="required">*</span></td>
		</tr>
		<tr id="durationFromTr" style="display:none">
			<td><bean:message key="yearlyBudget.durationFrom"/>:</td>
			<td><html:text property="durationFrom" size="8" value="${x_currentDate}"/><a onclick="event.cancelBubble=true;" href="javascript:showCalendar('dimgFrom',false,'durationFrom',null,null,'yearlyBudgetForm')"><IMG align="absMiddle" border="0" id="dimgFrom" src="images/datebtn.gif" ></A><span class="required">*</span></td>
		</tr>
		<tr id="durationToTr" style="display:none">
			<td><bean:message key="yearlyBudget.durationTo"/>:</td>
			<td><html:text property="durationTo" size="8" value="${x_currentDate}"/><a onclick="event.cancelBubble=true;" href="javascript:showCalendar('dimgTo',false,'durationTo',null,null,'yearlyBudgetForm')"><IMG align="absMiddle" border="0" id="dimgTo" src="images/datebtn.gif" ></A><span class="required">*</span></td>
		</tr>
		<tr>
			<td><bean:message key="yearlyBudget.amount"/>:</td>
			<td><html:text property="amount" size="10"/><span class="required">*</span></td>
		</tr>
		<tr id="purchaseCategoryTr">
			<td><bean:message key="yearlyBudget.purchaseCategory.id"/>:</td>
			<td align="left">
				<html:select property="purchaseCategory_id">
				</html:select>
			</td>
		</tr>
		<tr id="purchaseSubCategoryTr">
			<td><bean:message key="yearlyBudget.purchaseSubCategory.id"/>:</td>
			<td align="left">
				<html:select property="purchaseSubCategory_id">
				</html:select>
			</td>
		</tr>
		<tr id="expenseCategoryTr" style="display:none">
			<td><bean:message key="yearlyBudget.expenseCategory.id"/>:</td>
			<td align="left">
				<html:select property="expenseCategory_id">
					<html:option value= ""><bean:message key="yearlyBudget.dropdownlistvalue.all"/></html:option>
					<html:options collection="x_expenseCategoryList"
						property="id" labelProperty="description" />
				</html:select>
			</td>
		</tr>		
		<tr>
			<td><bean:message key="yearlyBudget.baseCurrency"/>:</td>
			<td>${x_site.baseCurrency.name}</td>
		</tr>
		<tr>
			<td><bean:message key="yearlyBudget.status"/>:</td>
			<td align="left">
				<html:select property="status">
					<html:options collection="x_statusList"
						property="enumCode" labelProperty="${x_lang}ShortDescription" />
				</html:select>
			</td>
		</tr>
	</table>
	<hr>
	<table width=90% border=0 cellpadding=4 cellspacing=0>
		<tr>
			<td align="right">
				<html:submit>
					<bean:message key="all.save" />
				</html:submit> 
				<input type="button" value="<bean:message key="all.backToList"/>"
					onclick="backToList()">
			</td>
		</tr>
	</table>
</html:form>

<script type="text/javascript">
    initPurchaseCategory();
</script>
