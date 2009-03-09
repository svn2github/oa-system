<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>
<script type="text/javascript">
<!--
	function selectTravelApplication() {
		v = window.showModalDialog(
			'showDialog.do?title=expense.selectTA.title&selectTravelApplication.do?requestor_id='+document.expenseForm.requestor_id.value, 
			null, 'dialogWidth:840px;dialogHeight:580px;status:no;help:no;scroll:no');
		if (v) {
			document.getElementById("searchTASpan").outerHTML="<span id=\"searchTASpan\">"+v+"<a href=\"javascript:selectTravelApplication();\"><img src=\"images/search.gif\" border=0/></a></span>";
			document.all.searchTASpan.style.setExpression("display","document.expenseForm[\"expenseCategory_id\"].selectedIndex=='0'?'block':'none'");
			document.getElementById("travelApplication_id").value=v;
			document.getElementById("title").value = v;
			
		};
	}
	
	function comboChange()
	{
		with(document.expenseForm)
		{
			action="newExpense_other.do";
			submit();
		}
	}
	function comboDepartmentChange()
	{
		with(document.expenseForm)
		{
			var selectedOption=department_id.options(department_id.selectedIndex);
			if(selectedOption.style.color=="gray")
			{
				alert('<bean:message key="expense.department.noPower"/>');
				department_id.value=old_department_id.value
			}
			else
			{
				comboChange();
			}
		}
	}

	
//-->
</script>	


<html:javascript formName="expenseForm" staticJavascript="false"/>

<html:form action="/insertExpense_other"  onsubmit="return validateExpenseForm(this)">
	<table width="90%">
		<tr>
			<td >
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
	<table width="100%">
		<tr>
			<td class="bluetext" width="20%"><bean:message key="expense.site" />:</td>
			<td width="80%" colspan="3">
				<html:select property="department_site_id" onchange="comboChange()">
					<html:options collection="x_siteList" property="id" labelProperty="name" />
				</html:select>
				<span class="required">*</span>
			</td>
		</tr>
		<tr>
			<td class="bluetext" width="20%"><bean:message key="expense.department" />:</td>
			<td width="80%" colspan="3">
				<html:select property="department_id" onchange="comboDepartmentChange()">
				<logic:iterate name="x_departmentList" id="x_department">
				<c:set var="x_style" value=""/>				
				<c:if test="${!x_department.granted}">				
					<c:set var="x_style" value="color:gray"/>				
				</c:if>
					<html:option style="${x_style}" value="${x_department.id}">${x_department.indentName}</html:option>
				</logic:iterate>
				</html:select><span class="required">*</span>
			</td>
		</tr>	
		<tr>
			<td class="bluetext" width="20%"><bean:message key="expense.requestor.id" />:</td>
			<td width="30%">
				<html:select property="requestor_id" >
					<html:options collection="x_requestorList" property="id" labelProperty="name" />
				</html:select><span class="required">*</span>
			</td>
			<td class="bluetext" width="20%"><bean:message key="expense.requestdate" />:</td>
			<td width="30%">
			</td>
		</tr>	
		<tr>
			<td class="bluetext" width="20%"><bean:message key="expense.creator.id" />:</td>
			<td width="30%">
				${x_newExpense.creator.name}
			</td>
			<td class="bluetext" width="20%"><bean:message key="expense.creatDate" />:</td>
			<td width="30%">
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
					<html:options collection="x_expenseCategoryList" property="id" labelProperty="description" /><span class="required">*</span>
				</html:select>
			</td>
			<td class="bluetext" width="20%"><span id="searchTALabelSpan"><bean:message key="expense.travelApplication.id" />:</span></td>
			<td width="30%">
				<html:hidden property="travelApplication_id"/>
				<span id="searchTASpan">
					<bean:write property="travelApplication_id" name="expenseForm"/>
					<a href="javascript:selectTravelApplication();">
						<img src="images/select.gif" border="0"/>
					</a>
				</span>
			</td>
		</tr>
		<tr>
			
			<td class="bluetext"><bean:message key="expense.amount" />:</td>
			<td>
			</td>
			<td class="bluetext"><bean:message key="expense.baseCurrency" />:</td>
			<td>
				${x_baseCurrency}
			</td>
		</tr>			
		<tr>
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
    document.all.searchTASpan.style.
    	setExpression('display','document.expenseForm["expenseCategory_id"].value=="${x_travelExpenseCategoryId}"?"block":"none"');
    document.all.searchTALabelSpan.style.
    	setExpression('display','document.expenseForm["expenseCategory_id"].value=="${x_travelExpenseCategoryId}"?"block":"none"'); 
       	
</script>