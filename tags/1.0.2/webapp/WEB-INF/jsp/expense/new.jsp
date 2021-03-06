<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>
<script type="text/javascript">
<!--
	var departments = "";
	var durationFrom = "";
	var durationTo = "";
	function selectTravelApplication() {
		v = window.showModalDialog(
			'showDialog.do?title=expense.selectTA.title&selectTravelApplication.do', 
			null, 'dialogWidth:840px;dialogHeight:580px;status:no;help:no;scroll:no');
		if (v) {
			document.getElementById("searchTASpan").outerHTML="<span id=\"searchTASpan\">"+v+"<a href=\"javascript:selectTravelApplication();\"><img src=\"images/search.gif\" border=0/></a></span>";
			document.all.searchTASpan.style.setExpression("display","document.expenseForm[\"expenseCategory_id\"].options[document.expenseForm[\"expenseCategory_id\"].selectedIndex].type=='1'?'block':'none'");
			document.getElementById("travelApplication_id").value=v;
			document.getElementById("title").value = v;
			
		};
	}
	
	function checkCleanSelectedBudget() {
		var requestDate = document.getElementById("requestDate").value;
		var departmentId = "," + document.getElementById("department_id").value + ",";
		
		if (!(durationFrom <= requestDate && requestDate <= durationTo && departments.indexOf(departmentId))) {
			if (document.getElementById("yearlyBudget_id").value != "") {
				alert("<bean:message key="expense.clearExpenseBudget"/>");
				cleanSelectedBudget();
			}
		}
	}
	
	function cleanSelectedBudget() {
		document.getElementById("yearlyBudget_code_span").innerHTML="";
		document.getElementById("yearlyBudget_id").value="";
		document.getElementById("canViewYearlyBudgetAmountTr").style.display = "none";
		document.getElementById("yearlyBudget_amount").innerHTML="";
		document.getElementById("yearlyBudget_remainAmount").innerHTML="";
		departments = "";
		durationFrom = "";
		durationTo = "";
	}
	
	function selectBudget()
	{
		with(document.expenseForm)
		{
			var url="newExpenseSelectBudget.do?expenseCategory_id="+
				expenseCategory_id.value+"&department_id="+department_id.value + "&requestDate="+requestDate.value;
			var v=dialogAction(url,"purchaseRequest.selectYearlyBudget",600,300);
			if(v)
			{
				
				document.getElementById("yearlyBudget_code_span").innerHTML=v['code'];
				document.getElementById("yearlyBudget_id").value=v['id'];
				
				if (v['amount'] != "" && v['remainAmount'] != "") {
					document.getElementById("canViewYearlyBudgetAmountTr").style.display = "inline";
					document.getElementById("yearlyBudget_amount").innerHTML=v['amount'];
					document.getElementById("yearlyBudget_remainAmount").innerHTML=v['remainAmount'];
				} else {
					document.getElementById("canViewYearlyBudgetAmountTr").style.display = "none";
					document.getElementById("yearlyBudget_amount").innerHTML="";
					document.getElementById("yearlyBudget_remainAmount").innerHTML="";
				}
				
				departments = v['departments'];
				durationFrom = v['durationFrom'];
				durationTo = v['durationTo'];
			} else {
				cleanSelectedBudget();
			}
		}	
	}
//-->
</script>	

<xml id="data">
<data>
<logic:iterate id="x_userSite" name="x_userSiteList">
	<site id="${x_userSite.site.id}" name="<bean:write name="x_userSite" property="site.name"/>">
		<logic:iterate id="x_userDepartment" name="x_userSite" property="enabledUserDepartmentList">
			<department id="${x_userDepartment.department.id}" name="<bean:write name="x_userDepartment" property="department.name"/>">
			</department>
		</logic:iterate>
		<logic:iterate id="x_expenseCategory" name="x_userSite" property="site.enabledExpenseCategoryList">
			<expenseCategory id="${x_expenseCategory.id}" name="<bean:write name="x_expenseCategory" property="description"/>" type="${x_expenseCategory.type.enumCode}">
			</expenseCategory>
		</logic:iterate>
		<currency id="${x_userSite.site.baseCurrency.code}" name="<bean:write name="x_userSite" property="site.baseCurrency.name"/>">
		</currency>
	</site>
</logic:iterate>
</data>
</xml>

<xml id="config">
<config>
<site>
<department>
</department>
<expenseCategory>
</expenseCategory>
<currency>
</currency>
</site>
</config>
</xml>


<html:javascript formName="expenseForm" staticJavascript="false"/>

<html:form action="/insertExpense_self"  onsubmit="return validateExpenseForm(this)">
	
	<table width="100%">
		<tr>
			<td >
				<html:errors/>
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
	<html:hidden property="requestor_id"/>
	<table width="100%">
		<tr>
			<td class="bluetext" width="20%"><bean:message key="expense.site" />:</td>
			<td width="80%" colspan="3">
				<html:select property="department_site_id" >
				</html:select>
				<span class="required">*</span>
			</td>
		</tr>
		<tr>
			<td class="bluetext" width="20%"><bean:message key="expense.department" />:</td>
			<td width="80%" colspan="3">
				<html:select property="department_id" >
				</html:select><span class="required">*</span>
			</td>
		</tr>
		<tr>
			<td class="bluetext" width="20%"><bean:message key="expense.requestor.id" />:</td>
			<td width="30%">
				${sessionScope.LOGIN_USER.name}
			</td>
			<td class="bluetext" width="20%"><bean:message key="expense.creatDate" />:</td>
			<td width="30%">
				${x_currentDate}
			</td>
		</tr>	
	</table>
	<hr width="100%">
	<table width=100%>
		<tr>
			<td class="bluetext" width="20%"><bean:message key="expense.id" />:</td>
			<td width="80%" colspan="3">(<bean:message key="common.id.generateBySystem" />)</td>
		</tr>
		<tr>
			<td class="bluetext" width="20%"><bean:message key="expense.title" />:</td>
			<td width="80%" colspan="3"><html:text property="title" size="60"/><span class="required">*</span></td>
			
		</tr>
		<tr>
			<td class="bluetext" width="20%"><bean:message key="expense.description" />:</td>
			<td width="80%" colspan="3"><html:textarea property="description"  cols="60" rows="3"/></td>
		</tr>
		
		<tr>
			<td class="bluetext" width="20%"><bean:message key="expense.expenseCategory" />:</td>
			<td width="30%">
				<html:select property="expenseCategory_id">
				</html:select><span class="required">*</span>
			</td>
			<td class="bluetext" width="20%"><span id="searchTALabelSpan"><bean:message key="expense.travelApplication.id" />:</span></td>
			<td width="30%">
				<html:hidden property="travelApplication_id"/>
				<span id="searchTASpan">
					<bean:write property="travelApplication_id" name="expenseForm"/>
					<a href="javascript:selectTravelApplication();"><img src="images/select.gif" border="0"/></a>
				</span>
			</td>
		</tr>	
		<tr>
		<td class="bluetext" width="20%"><bean:message key="expense.requestdate" />:</td>
		<td width="30%"><html:text property="requestDate" size="8" value="${x_currentDate}"/><a onclick="event.cancelBubble=true;" href="javascript:showCalendar('dimg',false,'requestDate',null, 'checkCleanSelectedBudget','expenseForm');"><IMG align="absMiddle" border="0" id="dimg" src="images/datebtn.gif" ></A><span class="required">*</span></td>
		<td class="bluetext" width="20%"><bean:message key="expense.budget" />:</td>
			<td width="30%">
				<html:hidden property="yearlyBudget_id"/>
				<span id="yearlyBudget_code_span"></span>
				<a href="javascript:selectBudget();"><img src="images/select.gif" border="0"/></a>				
			</td>
		</tr>	
		<tr id="canViewYearlyBudgetAmountTr" style="display:none">
			<td class='bluetext'>
				<bean:message key="expense.yearlyBudget.amount"/>: 
			</td>
			<td>
				<span id="yearlyBudget_amount"></span>
			</td>
			<td class='bluetext'>
				<bean:message key="expense.yearlyBudget.remainAmount"/>: 
			</td>
			<td>
				<span id="yearlyBudget_remainAmount"></span>
			</td>
		</tr>
		<tr>
			<td class="bluetext"><bean:message key="expense.baseCurrency" />:</td>
			<td>
				<span id="baseCurrencySpan"></span>
				<html:select property="baseCurrency_code" style="display:none">
				</html:select>
			</td>
			<td class="bluetext"><bean:message key="expense.status" />:</td>
			<td colspan="3">
				<span style="color:${x_newExpense.status.color}">
			      <c:if test="${sessionScope.LOGIN_USER.locale=='en'}">${x_newExpense.status.engShortDescription}</c:if>
			      <c:if test="${sessionScope.LOGIN_USER.locale!='en'}">${x_newExpense.status.chnShortDescription}</c:if>
			    </span> 
			</td>
		</tr>			
		
	</table>
	<hr width=100% align="left"/>
	<table width=90% border=0 cellpadding=4 cellspacing=0>
	<tr>
		<td align="right">
			<html:submit>
				<bean:message key="all.save" />
			</html:submit>
			<input type="button" value="<bean:message key="all.back" />" onclick="history.back()">
		</td>
	</tr>
	</table>
</html:form>
<script type="text/javascript">
    var mapping=new Map();
	mapping.put("expenseCategory_id","expenseCategory");
	mapping.put("department_site_id","site");
	mapping.put("department_id","department");
	mapping.put("baseCurrency_code","currency");
	with (document.expenseForm) {
		expenseCategory_id.oldValue='<bean:write name="expenseForm" property="expenseCategory_id"/>';
		department_site_id.oldValue='<bean:write name="expenseForm" property="department_site_id"/>';
		department_id.oldValue='<bean:write name="expenseForm" property="department_id"/>';
		baseCurrency_code.oldValue='<bean:write name="expenseForm" property="baseCurrency_code"/>';		
	}
    initCascadeSelect("config","data","expenseForm",mapping,true);
    document.all.searchTASpan.style.
    	setExpression("display","document.expenseForm[\"expenseCategory_id\"].options[document.expenseForm[\"expenseCategory_id\"].selectedIndex].type=='1'?'block':'none'");
	document.all.searchTALabelSpan.style.
    	setExpression("display","document.expenseForm[\"expenseCategory_id\"].options[document.expenseForm[\"expenseCategory_id\"].selectedIndex].type=='1'?'block':'none'");    	
   	document.all.baseCurrencySpan.
    	setExpression("innerText","document.expenseForm[\"baseCurrency_code\"].value");
    document.all.title.value = "<bean:write property="travelApplication_id" name="expenseForm"/>";
    //add checkCleanSelectedBudget function to onchange even to site drop down list
    document.all.department_site_id.onchange = document.all.department_site_id.onchange + ";" + checkCleanSelectedBudget;
    document.all.department_id.onchange = checkCleanSelectedBudget;
    document.all.expenseCategory_id.onchange = checkCleanSelectedBudget;
    document.all.requestDate.onchange = checkCleanSelectedBudget;
    document.all.requestDate.onblur = checkCleanSelectedBudget;
</script>