<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>
<c:set var="x_supplier_promoteStatus_GLOBAL" value="<%=com.aof.model.metadata.SupplierPromoteStatus.GLOBAL%>"/>
<c:set var="x_supplier_promoteStatus_SITE" value="<%=com.aof.model.metadata.SupplierPromoteStatus.SITE%>"/>
<c:set var="x_supplier_promoteStatus_REQUEST" value="<%=com.aof.model.metadata.SupplierPromoteStatus.REQUEST%>"/>

<c:set var="modifyable" value="${(x_version=='' && x_supplier.promoteStatus == x_supplier_promoteStatus_GLOBAL) || (x_version!='' && x_supplier.promoteStatus != x_supplier_promoteStatus_GLOBAL)}"/>

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
		<logic:present name="x_conflictItemList">
		if (!validateConflictSubCategory(form))
		{
			return false;
		}
		</logic:present>
		
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

	<logic:present name="x_conflictItemList">
	function validateConflictSubCategory(form) {
		var category=form["changeSubCategoryId"];
		if (category.length>1) {
			for (var index=0;index<category.length;index++) {
				if (trim(form["purchaseSubCategory_"+category[index].value].value).length==0) {
					alert('<bean:message key="supplier.error.changeCategory"/>');
					form["purchaseSubCategory_"+category[index].value].focus();
					return false;
				}
			}
		} else {
			if (trim(form["purchaseSubCategory_"+category.value].value).length==0) {
				alert('<bean:message key="supplier.error.changeCategory"/>');
				form["purchaseSubCategory_"+category.value].focus();
				return false;
			}
		}
		return true;
	}
	</logic:present>
	
		
	
	function addContract() {
		var url="newSupplierContract${x_version}.do?supplier_id=${x_supplier.id}";
		var v=dialogAction(url,'supplierContract.new.title',400,150);
		if (v) {
			var table = document.all('datatable');
			appendRow(table, v);
		    applyRowStyle(table);
		};
	}
	<logic:notPresent name="x_confirm">
	<c:if test="${x_version=='' && x_supplier.site != null && x_supplier.promoteStatus == x_supplier_promoteStatus_SITE}">
	function request(id) {
		var url="requestPromoteSupplier.do?id="+id;
		var v= dialogAction(url,'supplier.promote.request.title',400,180);
		if (v!=null) {
			document.getElementById("promoteMessageValue").innerHTML=v;
			document.getElementById("promoteSuccessMessage").style.display="block";
			document.getElementById("promoteMessageTD").style.display="block";
			document.getElementById("promoteRequestButton").style.display="none";			
		};
	}
	</c:if>
	
	<c:if test="${x_version!='' && x_supplier.site != null && x_supplier.promoteStatus == x_supplier_promoteStatus_REQUEST}">
	function response(id) {
		var confirmMessage = "<bean:message key="supplier.promote.response.confirm.noMessage" arg0="${x_supplier.name}"/>";
		if (confirm(confirmMessage)) {
			if(!validateForm(document.all["supplierForm"]))return;
			document.all["supplierForm"].action="responsePromoteSupplier.do";
			document.all["supplierForm"].submit();
			//window.location.href="responsePromoteSupplier.do?id="+id;
		}
	}
	</c:if>
	
	function viewItem() {
		window.location.href="listSupplierItem${x_version}.do?supplier_id=${x_supplier.id}&backPage=edit";
	}
	
	</logic:notPresent>
	
	<logic:present name="x_confirm">
	<logic:notPresent name="x_fromPO">
	function viewItem() {
		window.location.href="listSupplierItem${x_version}.do?supplier_id=${x_supplier.id}&backPage=confirm";
	}
	</logic:notPresent>
	<logic:present name="x_fromPO">
	function viewItem() {
		window.location.href="listSupplierItem${x_version}.do?supplier_id=${x_supplier.id}&fromPO=true";
	}
	</logic:present>
		
		
	function doCancel() {
		if (confirm("<bean:message key="supplier.confirm.cancel.confirm"/>"))
		{
			document.forms["supplierForm"].cancel.value="true";
			document.forms["supplierForm"].submit();
		}
	}
	
	function doConfirm() {
		if (confirm("<bean:message key="supplier.confirm.confirm"/>"))
		{
			document.forms["supplierForm"].cancel.value="false";
			document.forms["supplierForm"].submit();
		}
	}
	</logic:present>

</script>
<html:form action="${x_action}" method="post" onsubmit="return validateForm(this)">
<html:hidden property="id"/>
<html:hidden property="code"/>
<html:hidden property="site_id"/>
<html:hidden property="promoteStatus" />
<html:hidden property="confirmed" />
<html:hidden property="cancel" value="false" />
<input type="hidden" name="country_id_value" value="<bean:write name="supplierForm" property="country_id"/>">
<input type="hidden" name="city_id_value"   value="<bean:write name="supplierForm" property="city_id"/>">

	<table width=90% border=0 cellpadding=4 cellspacing=0>
		<tr>
			<td colspan="4">
				<html:errors/>
			</td>
		</tr>
		<tr>
			<td colspan="4">
				<span style="color:green;font-weight:bold">
				<html:messages id="x_message" message="true">
					${x_message}
				</html:messages>
				<c:if test="${(x_version=='' && x_supplier.site != null)}">
					<div id="promoteSuccessMessage" style="display:none">
						<bean:message key="supplier.promote.success"/>
					</div>
				</c:if>
				</span>
			</td>
		</tr>
		<logic:present name="x_conflictItemList">
		<tr>
			<td colspan="4">
			<jsp:include page="conflictItem.jsp"/>
			</td>
		</tr>
		</logic:present>
		<tr id="promoteMessageTD" <c:if test="${x_supplier.promoteStatus != x_supplier_promoteStatus_REQUEST}">style="display:none"</c:if>>
			<td class="bluetext"><bean:message key="supplier.promote.promoteMessage" />:</td>
			<td colspan="3">
				<div id="promoteMessageValue">
					${x_supplier.promoteMessage}
				</div>
			</td>
		</tr>
		
		<tr>
			<td class="bluetext"><bean:message key="supplier.code" />:</td>
			<td>${x_supplier.code}</td>
			<td class="bluetext"><bean:message key="supplier.description" />:</td>
			<td>
				<c:choose>
					<c:when test="${modifyable}">
						<html:text property="name" maxlength="50" /><span class="required">*</span>
					</c:when>
					<c:otherwise>
						${x_supplier.name}
					</c:otherwise>
				</c:choose>
			</td>
		</tr>
		<tr>
			<td class="bluetext"><bean:message key="supplier.address1" />:</td>
			<td colspan="3">
				<c:choose>
					<c:when test="${modifyable}">
						<html:text property="address1"  size="75" maxlength="50"/><span class="required">*</span>
					</c:when>
					<c:otherwise>
						${x_supplier.address1}
					</c:otherwise>
				</c:choose>
			</td>
		</tr>
		<tr>
			<td class="bluetext"><bean:message key="supplier.address2" />:</td>
			<td colspan="3">
				<c:choose>
					<c:when test="${modifyable}">
						<html:text property="address2"  size="75" maxlength="50"/>
					</c:when>
					<c:otherwise>
						${x_supplier.address2}
					</c:otherwise>
				</c:choose>
			</td>
		</tr>
		<tr>
			<td class="bluetext"><bean:message key="supplier.address3" />:</td>
			<td colspan="3">
				<c:choose>
					<c:when test="${modifyable}">
						<html:text property="address3"  size="75" maxlength="50"/>
					</c:when>
					<c:otherwise>
						${x_supplier.address3}
					</c:otherwise>
				</c:choose>
			</td>
		</tr>
		<tr>
			<td class="bluetext"><bean:message key="supplier.country" />:</td>
			<td align="left">
				<c:choose>
					<c:when test="${modifyable}">
						<html:select property="country_id"></html:select><span class="required">*</span>
					</c:when>
					<c:otherwise>
						<c:if test="${sessionScope.LOGIN_USER.locale=='en'}">					
							${x_supplier.city.province.country.engName}
						</c:if>
						<c:if test="${sessionScope.LOGIN_USER.locale!='en'}">
							${x_supplier.city.province.country.chnName}
						</c:if>			
					</c:otherwise>
				</c:choose>
				
			</td>
			<td class="bluetext"><bean:message key="supplier.city" />:</td>
			<td align="left">
				<c:choose>
					<c:when test="${modifyable}">
						<html:select property="city_id"></html:select>
					</c:when>
					<c:otherwise>
						<c:if test="${sessionScope.LOGIN_USER.locale=='en'}">					
							${x_supplier.city.engName}
						</c:if>
						<c:if test="${sessionScope.LOGIN_USER.locale!='en'}">
							${x_supplier.city.chnName}
						</c:if>			
					</c:otherwise>
				</c:choose>
			</td>
		</tr>
		<tr>
			<td class="bluetext"><bean:message key="supplier.attention1" />:</td>
			<td>
				<c:choose>
					<c:when test="${modifyable}">
						<html:text property="attention1"  maxlength="24"/>
					</c:when>
					<c:otherwise>
						${x_supplier.attention1}
					</c:otherwise>
				</c:choose>
			</td>	
			<td class="bluetext"><bean:message key="supplier.attention2" />:</td>
			<td>
				<c:choose>
					<c:when test="${modifyable}">
						<html:text property="attention2"  maxlength="24"/>
					</c:when>
					<c:otherwise>
						${x_supplier.attention2}
					</c:otherwise>
				</c:choose>
			</td>	
		</tr>
		<tr>
			<td class="bluetext"><bean:message key="supplier.telephone1" />:</td>
			<td>
				<c:choose>
					<c:when test="${modifyable}">
						<html:text property="telephone1"  maxlength="16"/>&nbsp;&nbsp;
						<span class="bluetext"><bean:message key="supplier.ext1" />:</span>
						<html:text property="ext1"  maxlength="4" size="2"/>
					</c:when>
					<c:otherwise>
						${x_supplier.telephone1}&nbsp;&nbsp;
						<span class="bluetext"><bean:message key="supplier.ext1" />:</span>
						${x_supplier.ext1}
					</c:otherwise>
				</c:choose>
			</td>	
			<td class="bluetext"><bean:message key="supplier.telephone2" />:</td>
			<td>
				<c:choose>
					<c:when test="${modifyable}">
						<html:text property="telephone2"  maxlength="16"/>&nbsp;&nbsp;
						<span class="bluetext"><bean:message key="supplier.ext2" />:</span>
						<html:text property="ext2"  maxlength="4" size="2"/>
					</c:when>
					<c:otherwise>
						${x_supplier.telephone2}&nbsp;&nbsp;
						<span class="bluetext"><bean:message key="supplier.ext2" />:</span>
						${x_supplier.ext2}
					</c:otherwise>
				</c:choose>
			</td>
		</tr>
		<tr>
			<td class="bluetext"><bean:message key="supplier.fax1" />:</td>
			<td>
				<c:choose>
					<c:when test="${modifyable}">
						<html:text property="fax1"  maxlength="16"/>
					</c:when>
					<c:otherwise>
						${x_supplier.fax1}
					</c:otherwise>
				</c:choose>
			</td>
			<td class="bluetext"><bean:message key="supplier.fax2" />:</td>
			<td>
				<c:choose>
					<c:when test="${modifyable}">
						<html:text property="fax2"  maxlength="16"/>
					</c:when>
					<c:otherwise>
						${x_supplier.fax2}
					</c:otherwise>
				</c:choose>
			</td>
		</tr>
		<tr>
			<td class="bluetext"><bean:message key="supplier.post" />:</td>
			<td>
				<c:choose>
					<c:when test="${modifyable}">
						<html:text property="post" maxlength="9"/>
					</c:when>
					<c:otherwise>
						${x_supplier.post}
					</c:otherwise>
				</c:choose>
			</td>	
			<td class="bluetext"><bean:message key="supplier.contactor" />:</td>
			<td>
				<c:choose>
					<c:when test="${modifyable}">
						<html:text property="contact" maxlength="20"/>
					</c:when>
					<c:otherwise>
						${x_supplier.contact}
					</c:otherwise>
				</c:choose>
			</td>	
		</tr>
		<tr>
			<td class="bluetext"><bean:message key="supplier.currency" />:</td>
			<td>
				<c:choose>
					<c:when test="${modifyable}">
						<html:select property="currency_code">
							<html:options collection="X_CURRENCYLIST" property="code" labelProperty="name" />
						</html:select>
					</c:when>
					<c:otherwise>
						${x_supplier.currency.name}							
					</c:otherwise>
				</c:choose>
			</td>
			<td class="bluetext"><bean:message key="supplier.airTicket"/>:</td>
			<td>
				<c:choose>
					<c:when test="${modifyable}">
						<html:select property="airTicket">
							<html:options collection="X_YESNOLIST" property="enumCode" labelProperty="${x_lang}ShortDescription" />
						</html:select>
					</c:when>
					<c:otherwise>
						<c:if test="${sessionScope.LOGIN_USER.locale=='en'}">					
							${x_supplier.airTicket.engDescription}	
						</c:if>
						<c:if test="${sessionScope.LOGIN_USER.locale!='en'}">
							${x_supplier.airTicket.chnDescription}	
						</c:if>						
					</c:otherwise>
				</c:choose>
			</td>
		</tr>
		<tr>
			<td class="bluetext"><bean:message key="supplier.status"/>:</td>
			<td>
				<c:choose>
					<c:when test="${modifyable}">
						<html:select property="enabled">
							<c:if test="${sessionScope.LOGIN_USER.locale=='en'}">
								<html:options collection="X_ENABLEDDISABLEDLIST"
									property="enumCode" labelProperty="engShortDescription" />
							</c:if>
							<c:if test="${sessionScope.LOGIN_USER.locale!='en'}">
								<html:options collection="X_ENABLEDDISABLEDLIST"
									property="enumCode" labelProperty="chnShortDescription" />
							</c:if>
						</html:select>
					</c:when>
					<c:otherwise>
						<c:if test="${sessionScope.LOGIN_USER.locale=='en'}">					
							${x_supplier.enabled.engDescription}	
						</c:if>
						<c:if test="${sessionScope.LOGIN_USER.locale!='en'}">
							${x_supplier.enabled.chnDescription}	
						</c:if>						
					</c:otherwise>
				</c:choose>
			</td>
			<td class="bluetext"><bean:message key="supplier.lastModifyDate"/>:</td>
			<td><bean:write name="supplierForm" property="lastModifyDate" format="yyyy/MM/dd" /></td>
		</tr>
		<tr>
			<td colspan="4" align="right">
				<table>
					<tr>
						<logic:notPresent name="x_confirm">
							<c:if test="${modifyable}">
							<td>
								<html:submit>
									<bean:message key="all.save" />
								</html:submit>&nbsp;
							</td>
							</c:if>
							
							<c:if test="${x_version=='' && x_supplier.site != null && x_supplier.promoteStatus == x_supplier_promoteStatus_SITE}">
							<td id="promoteRequestButton" style="display:block">							
								<input type="button" value="<bean:message key="supplier.promote.request.title"/>" onclick="request(${x_supplier.id})">
							</td>
							</c:if>
							
							<c:if test="${x_version!='' && x_supplier.site != null && x_supplier.promoteStatus == x_supplier_promoteStatus_REQUEST}">
							<td id="promoteRequestButton" style="display:block">					
								<input type="button" value="<bean:message key="supplier.promote.response.title"/>" onclick="response(${x_supplier.id})">
							</td>
							</c:if>
							<td>
								<input type="button" value="<bean:message key="all.back"/>" onclick="window.location.href='listSupplier<bean:write name="x_version"/>.do'">
							</td>
						</logic:notPresent>
						<logic:present name="x_confirm">
							<td>
								<input type="button" value="<bean:message key="all.confirm" />" onClick="doConfirm()" />
							</td>
							<td>
								<input type="button" value="<bean:message key="all.cancel" />" onClick="doCancel()" />
							</td>
							<logic:notPresent name="x_fromPO">
							<td>
								<input type="button" value="<bean:message key="all.back"/>" onclick="window.location.href='listConfirmSupplier<bean:write name="x_version"/>.do'">
							</td>
							</logic:notPresent>
						</logic:present>
					</tr>
				</table>
			</td>
		</tr>
	</table>
	<hr width="90%" align="left">
	<table width="90%">
		<tr>
			<td align="left" width='10%' class='formtitle'><bean:message key="supplierContract.list.title" /></td>
		</tr>
	</table>
	
	<table width="90%" border=0 cellpadding=0 cellspacing=0>
		<tr>
			<td class="bluetext"><bean:message key="supplier.contractStartDate" />:</td>
			<td>
				<c:choose>
					<c:when test="${modifyable}">
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
					</c:when>
					<c:otherwise>
						<bean:write name="x_supplier" property="contractStartDate" format="yyyy/MM/dd"/>
					</c:otherwise>
				</c:choose>	
			</td>
			<td>~</td>
			<td class="bluetext"><bean:message key="supplier.contractExpireDate" />:</td>
			<td>
				<c:choose>
					<c:when test="${modifyable}">
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
					</c:when>
					<c:otherwise>
						<bean:write name="x_supplier" property="contractExpireDate" format="yyyy/MM/dd"/>
					</c:otherwise>
				</c:choose>	
			</td>
			<td class="bluetext"><bean:message key="supplier.contractStatus"/>:</td>
			<td>
				<span style="color:${x_supplier.contractStatus.color}">
			      <c:if test="${sessionScope.LOGIN_USER.locale=='en'}">${x_supplier.contractStatus.engShortDescription}</c:if>
			      <c:if test="${sessionScope.LOGIN_USER.locale!='en'}">${x_supplier.contractStatus.chnShortDescription}</c:if>
			    </span>  
			</td>
		</tr>
	</table>
	<c:if test="${modifyable}">
	<table width=90% border=0 cellpadding=4 cellspacing=0>
		<tr>
			<td><input type="button"
				value="<bean:message key="supplierContract.new.title"/>"
				onclick="addContract()"></td>
		</tr>
	</table>
	</c:if>
	<table class="data" style="width:90%">
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
	<hr width="90%" align="left">
	<table width="90%">
		<tr>
			<td align="left" width='10%' class='formtitle'><bean:message key="supplier.item" /></td>
			<td>
				<a href="#" onclick="viewItem()"><bean:message key="supplier.itementry" /></a>
			</td>
		</tr>
	</table>
	
</html:form>

<c:if test="${modifyable}">
<script type="text/javascript">
    var mapping=new Map();
	mapping.put("country_id","country");
	mapping.put("city_id","city");
    initCascadeSelect("config","data","supplierForm",mapping,true);
</script>
</c:if>

<script type="text/javascript">
    applyRowStyle(document.all('datatable'));
</script>
