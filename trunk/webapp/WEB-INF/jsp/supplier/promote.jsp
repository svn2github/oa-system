<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>

<html:javascript formName="supplierForm" staticJavascript="false"/>

<xml id="data">
<data>
<logic:iterate id="x_country" name="x_countryList">
	<country id="${x_country.id}" name="<bean:write name="x_country" property="${x_lang}Name"/>">
		<city id="" name="">
		</city>
	<logic:iterate id="x_c" name="x_country" property="enabledCityList">
		<city id="${x_c.id}" name="<bean:write name="x_c" property="${x_lang}Name"/>">
		</city>
	</logic:iterate>
	</country>
</logic:iterate>
</data>
</xml>

<xml id="config">
<config>
<country>
<city>
</city>
</country>
</config>
</xml>


<script language="JavaScript">
	function validateForm(form)
	{
		

		if (!validateSupplierForm(form))
		{
			return false;
		}
		if(form["contractStartDate"].value!="" && form["contractExpireDate"].value!="") {	
			if (form["contractStartDate"].value>form["contractExpireDate"].value) {
				alert("<bean:message key="supplier.error.contractDate"/>");
				return false;
			}	
		}
		
		return true;
	}
	
	function addContract() {
		var url="newSupplierContract${x_version}.do?supplier_id=${x_supplier.id}";
		var v=dialogAction(url,'supplierContract.new.title',350,130);
		if (v) {
			var table = document.all('datatable');
			appendRow(table, v);
		    applyRowStyle(table);
		};
	}
	
	function deletePromote()	{
		document.forms["supplierForm"].action='promoteSupplierDelete.do';
		var command;
		command="document.supplierForm.submit()";
		setTimeout(command,10);
	}
	function createPromote()	{
		document.forms["supplierForm"].action='promoteSupplierCreate.do';
		var command;
		command="document.supplierForm.submit()";
		setTimeout(command,10);
	}
	function savePromote()	{
		document.forms["supplierForm"].action='promoteSupplierSave.do';
		var command;
		command="document.supplierForm.submit()";
		setTimeout(command,10);
	}

</script>
<html:form action="/promoteSupplierCreate" method="post" onsubmit="return validateForm(this)">
<html:hidden property="id"/>
<html:hidden property="code"/>
<html:hidden property="site_id"/>
<html:hidden property="promoteStatus" />
<html:hidden property="confirmed" />

<input type="hidden" name="country_id_value" value="<bean:write name="supplierForm" property="country_id"/>">
<input type="hidden" name="city_id_value"   value="<bean:write name="supplierForm" property="city_id"/>">

	<table width=90% border=0 cellpadding=4 cellspacing=0>
		<tr>
			<td class="bluetext"><bean:message key="supplier.code" />:</td>
			<td>${x_supplier.code}</td>
			<td class="bluetext"><bean:message key="supplier.description" />:</td>
			<td><html:text property="name" maxlength="50" /></td>
		</tr>
		<tr>
			<td class="bluetext"><bean:message key="supplier.address1" />:</td>
			<td colspan="3"><html:text property="address1"  size="78" maxlength="50"/></td>
		</tr>
		<tr>
			<td class="bluetext"><bean:message key="supplier.address2" />:</td>
			<td colspan="3"><html:text property="address2"  size="78" maxlength="50"/></td>
		</tr>
		<tr>
			<td class="bluetext"><bean:message key="supplier.address3" />:</td>
			<td colspan="3"><html:text property="address3"  size="78" maxlength="50"/></td>
		</tr>
		<tr>
			<td class="bluetext"><bean:message key="supplier.country" />:</td>
			<td align="left"><html:select property="country_id">
			</html:select></td>
			<td class="bluetext"><bean:message key="supplier.city" />:</td>
			<td align="left"><html:select property="city_id">
			</html:select></td>
		</tr>
		<tr>
			<td class="bluetext"><bean:message key="supplier.telephone" />:</td>
			<td><html:text property="telephone1"  maxlength="16"/></td>	
			<td class="bluetext"><bean:message key="supplier.fax" />:</td>
			<td><html:text property="fax1" maxlength="16" /></td>
		</tr>
		<tr>
			<td class="bluetext"><bean:message key="supplier.post" />:</td>
			<td><html:text property="post" maxlength="9"/></td>	
			<td class="bluetext"><bean:message key="supplier.bank" />:</td>
			<td><html:text property="bank" maxlength="8" /></td>
		</tr>
		<tr>
			<td class="bluetext"><bean:message key="supplier.contactor" />:</td>
			<td><html:text property="contact" maxlength="20"/></td>	
			<td class="bluetext"><bean:message key="supplier.currency" />:</td>
			<td>
				<html:select property="currency_code">
				<html:options collection="X_CURRENCYLIST" property="code" labelProperty="name" />
				</html:select>
			</td>
		</tr>
		<tr>
			<td class="bluetext"><bean:message key="supplier.purchaseAccount" />:</td>
			<td><html:text property="purchaseAccount" maxlength="8"/></td>	
			<td class="bluetext"><bean:message key="supplier.purchaseSubAccount" />:</td>
			<td><html:text property="purchaseSubAccount" maxlength="8" /></td>
		</tr>
		<tr>
			<td class="bluetext"><bean:message key="supplier.purchaseCostCenter" />:</td>
			<td><html:text property="purchaseCostCenter" maxlength="8"/></td>	
			<td class="bluetext"><bean:message key="supplier.apAccount" />:</td>
			<td><html:text property="apAccount" maxlength="8" /></td>
		</tr>
		<tr>
			<td class="bluetext"><bean:message key="supplier.apSubAccount" />:</td>
			<td><html:text property="apSubAccount" maxlength="8"/></td>	
			<td class="bluetext"><bean:message key="supplier.apCostCenter" />:</td>
			<td><html:text property="apCostCenter" maxlength="8" /></td>
		</tr>
		
		<tr>
			<td class="bluetext"><bean:message key="supplier.contractStatus"/>:</td>
			<td>
				<span style="color:${x_supplier.contractStatus.color}">
			      <c:if test="${sessionScope.LOGIN_USER.locale=='en'}">${x_supplier.contractStatus.engShortDescription}</c:if>
			      <c:if test="${sessionScope.LOGIN_USER.locale!='en'}">${x_supplier.contractStatus.chnShortDescription}</c:if>
			    </span>  
			</td>
			<td class="bluetext"><bean:message key="supplier.status"/>:</td>
			<td><html:select property="enabled">
				<c:if test="${sessionScope.LOGIN_USER.locale=='en'}">
					<html:options collection="X_ENABLEDDISABLEDLIST"
						property="enumCode" labelProperty="engShortDescription" />
				</c:if>
				<c:if test="${sessionScope.LOGIN_USER.locale!='en'}">
					<html:options collection="X_ENABLEDDISABLEDLIST"
						property="enumCode" labelProperty="chnShortDescription" />
				</c:if>
			</html:select></td>
		</tr>
		<tr>
			<td class="bluetext"><bean:message key="supplier.promote.submitby"/>:</td>
			<td>
				${x_supplier.site.name}  
			</td>
			
		</tr>
		<tr>
			<td colspan="2" align="right">&nbsp;</td>
		</tr>
	</table>
	<hr width="90%" align="left">
	<table width="100%" border=0 cellpadding=0 cellspacing=0>
		<tr>
			<td class="bluetext"><bean:message key="supplier.contractStartDate" />:</td>
			<td>
			<table border=0 cellpadding=0 cellspacing=0>
			<tr>
				<td>
					<html:text property="contractStartDate" size="6"  maxlength="10" />
				</td>
				<td>
					<a onclick="event.cancelBubble=true;" href="javascript:showCalendar('dimg2',false,'contractStartDate',null,null,'supplierForm')"><IMG align="absMiddle" border="0" id="dimg2" src="images/datebtn.gif" ></A>
				</td>
			</tr>
			</table>
			</td>
			<td>~</td>
			<td class="bluetext"><bean:message key="supplier.contractExpireDate" />:</td>
			<td>
			<table border=0 cellpadding=0 cellspacing=0>
			<tr>
				<td>
					<html:text property="contractExpireDate" size="6"  maxlength="10" />
				</td>
				<td>
					<a onclick="event.cancelBubble=true;" href="javascript:showCalendar('dimg3',false,'contractExpireDate',null,null,'supplierForm')"><IMG align="absMiddle" border="0" id="dimg3" src="images/datebtn.gif" ></A>
				</td>
			</tr>
			</table>
			</td>
		</tr>
	</table>
	<table width="90%">
		<tr>
			<td align="left" width='10%' class='bluetext'><font color="blue">
			<h3><bean:message key="supplierContract.list.title" /></h3>
			</font></td>
	</table>
	<table width=90% border=0 cellpadding=4 cellspacing=0>
		<thead>
			<tr bgcolor="#9999ff">
				<th>
				<div align="center"><bean:message key="supplierContract.title" /></div>
				</th>
				<th>
				<div align="center"><bean:message key="supplierContract.fileName" /></div>
				</th>
				<th>
				<div align="center"><bean:message key="supplierContract.uploadDate" /></div>
				</th>
			</tr>
		<thead>
		<tbody id="datatable">
			<logic:iterate id="X_OBJECT" name="x_contractList">
				<bean:define id="X_OBJECT" toScope="request" name="X_OBJECT" />
				<jsp:include page="../supplierContract/row.jsp" />
			</logic:iterate>
		</tbody>
	</table>
	<table width=90% border=0 cellpadding=4 cellspacing=0>
		<tr>
			<td><input type="button"
				value="<bean:message key="supplierContract.new.title"/>"
				onclick="addContract()"></td>
		</tr>
	</table>
	<hr width="90%" align="left">
	<table width=100% border=0 cellpadding=0 cellspacing=0>
	<tr>
		<td>
		<jsp:include page="../supplierItem/list.jsp" />
		</td>
	</tr>
	</table>
	<table width="90%">
		<tr>
			<td colspan="2" align="right">
			<input type="button" value="<bean:message key="supplier.promote.save" />" onclick="savePromote()">
			<input type="button" value="<bean:message key="supplier.promote.delete" />" onclick="deletePromote()">
			<input type="button" value="<bean:message key="supplier.promote.create" />" onclick="createPromote()">
			<input type="button" value="<bean:message key="all.back" />" onclick="history.back()">
			</td>
		</tr>
	</table>
</html:form>

<script type="text/javascript">
    var mapping=new Map();
	mapping.put("country_id","country");
	mapping.put("city_id","city");
    initCascadeSelect("config","data","supplierForm",mapping,true);
    applyRowStyle(document.all('datatable'));
</script>
