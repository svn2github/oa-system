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
			'showDialog.do?title=expense.selectTA.title&selectTravelApplication.do', 
			null, 'dialogWidth:840px;dialogHeight:580px;status:no;help:no;scroll:no');
		if (v) {
			document.getElementById("searchTASpan").outerHTML="<span id=\"searchTASpan\">"+v+"<a href=\"javascript:selectTravelApplication();\"><img src=\"images/search.gif\" border=0/></a></span>";
			document.all.searchTASpan.style.setExpression("display","document.expenseForm[\"expenseCategory_id\"].options[document.expenseForm[\"expenseCategory_id\"].selectedIndex].type=='1'?'block':'none'");
			document.getElementById("travelApplication_id").value=v;
			document.getElementById("title").value = v;
			
		};
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
			
			<td class="bluetext"><bean:message key="expense.amount" />:</td>
			<td>
			</td>
			<td class="bluetext"><bean:message key="expense.baseCurrency" />:</td>
			<td>
				<span id="baseCurrencySpan"></span>
				<html:select property="baseCurrency_code" style="display:none">
				</html:select>
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
</script>