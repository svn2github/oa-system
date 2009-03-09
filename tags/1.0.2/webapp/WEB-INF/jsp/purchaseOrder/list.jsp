<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>

<xml id="data">
<data>
<category id="" name="<bean:message key="supplierItem.purchaseCategory.all"/>">
	<subcategory id="" name="<bean:message key="supplierItem.purchaseSubCategory.all"/>">
	</subcategory>
</category>
<logic:iterate id="x_purchaseCategory" name="x_purchaseCategoryList">
	<category id="${x_purchaseCategory.id}" name="<bean:write name="x_purchaseCategory" property="description"/>">
		<subcategory id="" name="<bean:message key="supplierItem.purchaseSubCategory.all"/>">
		</subcategory>
		<logic:iterate id="x_c" name="x_purchaseCategory" property="enabledPurchaseSubCategoryList">
			<subcategory id="${x_c.id}" name="<bean:write name="x_c" property="description"/>">
			</subcategory>
		</logic:iterate>
	</category>
</logic:iterate>
</data>
</xml>


<xml id="config">
<config>
<category>
<subcategory>
</subcategory>
</category>
</config>
</xml>

<script type="text/javascript">
<!--
	function edit(id) {
		var url="editPurchaseOrder.do?id="+id;
		window.location.href=url;
	}
	
	function view(id) {
		var url="viewPurchaseOrder.do?id="+id;
		window.location.href=url;
	}
	
	function validateForm(form)
	{
		if(!validatePurchaseOrderQueryForm(form)) return false;
		return true;
	}
	
	function update()
	{
		var form=document.purchaseOrderQueryForm_again;
		if(form.newErpNo)
		{
			form.action="updatePurchaseOrderErpNo.do";
			form.submit();
		}
	}
	
	function consolidate()
	{
		if(checkSelectedPO())
		{
			var form=document.purchaseOrderQueryForm_again;
			form.action="consolidatePurchaseOrder.do";
			form.submit();
		}
	}
	
	function checkSelectedPO()
	{
		var objForm = document.purchaseOrderQueryForm_again;
	    var objLen = objForm.length;
	    var checked=false;
	    var selectedCount=0;
	    var supplier="";
	    var exchangeRate="";
	    var subCategory="";
	    for (var iCount = 0; iCount < objLen; iCount++)
	    {
	    	var control=objForm.elements[iCount];
	    	if (control.type != "checkbox") continue;
	    	if (control.name != "selected_po_id") continue;
            if(!control.checked)continue; 	    	
            selectedCount++;
            
			if(!checked)//first	            	
			{
				checked=true;
			}
			else// not first
			{
				if(supplier!=control.supplier)
				{
					alert("<bean:message key="purchaseOrder.consolidate.supplierNotSame"/>");
					return false;
				}
				if(subCategory!=control.subCategory)
				{
					alert("<bean:message key="purchaseOrder.consolidate.subCategoryNotSame"/>");
					return false;
				}
				if(exchangeRate!=control.exchangeRate)
				{
					alert("<bean:message key="purchaseOrder.consolidate.exchangeRateNotSame"/>");
					return false;
				}
			}
			supplier=control.supplier;
			subCategory=control.subCategory;
			exchangeRate=control.exchangeRate;
		}	
		if(selectedCount<=1)
		{
			alert("<bean:message key="purchaseOrder.consolidate.mustSelectMoreThanOne"/>");
			return false;
		}
		return true;
		
	}
	
	
//-->
</script>

<div class="message"><html:messages id="x_message" message="true">${x_message}<br></html:messages></div>

<html:javascript formName="purchaseOrderQueryForm" staticJavascript="false"/>
<html:form action="/listPurchaseOrder" focus="id" onsubmit="return validateForm(this)">
	<html:hidden property="order" />
	<html:hidden property="descend" />
	<input type="hidden" name="category_id_value" value="<bean:write name="purchaseOrderQueryForm" property="category_id"/>" />
	<input type="hidden" name="subCategory_id_value" value="<bean:write name="purchaseOrderQueryForm" property="subCategory_id"/>" />
	
	<table width=100% border=0 cellpadding=4 cellspacing=0>
		<tr>
			<td class="bluetext" ><bean:message key="purchaseOrder.search.site"/>&nbsp;</td>
			<td colspan="8">
				<html:select property="site_id">
					<html:options collection="x_siteList" property="id"
						labelProperty="name" />
				</html:select>
			</td>

		</tr>
		<tr>
			<td class="bluetext" ><bean:message key="purchaseOrder.id"/>&nbsp;</td>
			<td><html:text property="id" size="13"/></td>
			<td class="bluetext" ><bean:message key="purchaseOrder.erpNo"/>&nbsp;</td>
			<td><html:text property="erpNo" size="14"/></td>
		</tr>	
		<tr>
			<td class="bluetext" ><bean:message key="purchaseOrder.supplier"/>&nbsp;</td>
			<td><html:text property="supplier_name" /></td>
			<td class="bluetext" ><bean:message key="purchaseOrder.status"/></td>
			<td>
				 <html:select property="status">
		           <html:option value=""><bean:message key="all.all"/></html:option>
		           <html:options collection="x_statusList" property="enumCode" labelProperty="${x_lang}ShortDescription" />
		         </html:select>
			</td>
		</tr>
		<tr>
			<td class="bluetext"><bean:message key="purchaseOrder.category"/>:</td>
			<td><html:select property="category_id"></html:select></td>
			<td class="bluetext"><bean:message key="purchaseOrder.subCategory"/>:</td>
			<td><html:select property="subCategory_id"></html:select></td>
		</tr>
		<tr>
			<td class="bluetext" ><bean:message key="purchaseOrder.createDate"/>&nbsp;</td>
			<td>
				<table border=0 cellpadding=0 cellspacing=0>
			  		<tr>
						<td>
							<html:text property="createDate1" size="6"  maxlength="10" />
						</td>
						<td>
							<a onclick="event.cancelBubble=true;" href="javascript:showCalendar('dimg1',false,'createDate1',null,null,'purchaseOrderQueryForm')"><IMG align="absMiddle" border="0" id="dimg1" src="images/datebtn.gif" ></A>
						</td>
						<td width="20" align="center">~</td>
						<td>
							<html:text property="createDate2" size="6"  maxlength="10" />
						</td>
						<td>
							<a onclick="event.cancelBubble=true;" href="javascript:showCalendar('dimg2',false,'createDate2',null,null,'purchaseOrderQueryForm')"><IMG align="absMiddle" border="0" id="dimg2" src="images/datebtn.gif" ></A>
						</td>
			  		</tr>
		  		</table>
			</td>
			<td colspan="2" align="right">
				<html:submit><bean:message key="all.query"/></html:submit>&nbsp;&nbsp;&nbsp;
			</td>
		</tr>
	</table>
</html:form>
<hr />
<page:form action="listPurchaseOrder.do" method="post">
	<jsp:include page="../pageHead.jsp"/>
	<table class="data">
		<thead>
			<tr class="new_bg">
				<th>&nbsp;</th>
				<th width="180"><page:order order="id" style="text-decoration:none">
					<bean:message key="purchaseOrder.id" />
					<page:desc>
						<img src="images/down.gif" border="0" />
					</page:desc>
					<page:asc>
						<img src="images/up.gif" border="0" />
					</page:asc>
				</page:order></th>
				

				<th width="120"><page:order order="supplier_name" style="text-decoration:none">
					<bean:message key="purchaseOrder.supplier" />
					<page:desc>
						<img src="images/down.gif" border="0" />
					</page:desc>
					<page:asc>
						<img src="images/up.gif" border="0" />
					</page:asc>
				</page:order></th>


				<th ><page:order order="category_descrption"
					style="text-decoration:none">
					<bean:message key="purchaseOrder.category" />
					<page:desc>
						<img src="images/down.gif" border="0" />
					</page:desc>
					<page:asc>
						<img src="images/up.gif" border="0" />
					</page:asc>
				</page:order></th>
				
				<th ><page:order order="subCategory_descrption"
					style="text-decoration:none">
					<bean:message key="purchaseOrder.subCategory" />
					<page:desc>
						<img src="images/down.gif" border="0" />
					</page:desc>
					<page:asc>
						<img src="images/up.gif" border="0" />
					</page:asc>
				</page:order></th>
				
				<th >
					<bean:message key="purchaseOrder.amount" />
				</th>
				
				<th width="80"><page:order order="createDate"
					style="text-decoration:none">
					<bean:message key="purchaseOrder.createDate" />
					<page:desc>
						<img src="images/down.gif" border="0" />
					</page:desc>
					<page:asc>
						<img src="images/up.gif" border="0" />
					</page:asc>
				</page:order></th>
				
				<th width="80"><page:order order="createUser_name"
					style="text-decoration:none">
					<bean:message key="purchaseOrder.search.createUser" />
					<page:desc>
						<img src="images/down.gif" border="0" />
					</page:desc>
					<page:asc>
						<img src="images/up.gif" border="0" />
					</page:asc>
				</page:order></th>
				
				<th width="80"><page:order order="status"
					style="text-decoration:none">
					<bean:message key="purchaseOrder.status" />
					<page:desc>
						<img src="images/down.gif" border="0" />
					</page:desc>
					<page:asc>
						<img src="images/up.gif" border="0" />
					</page:asc>
				</page:order></th>
				
				<th width="80"><page:order order="erpNo"
					style="text-decoration:none">
					<bean:message key="purchaseOrder.erpNo" />
					<page:desc>
						<img src="images/down.gif" border="0" />
					</page:desc>
					<page:asc>
						<img src="images/up.gif" border="0" />
					</page:asc>
				</page:order></th>
				
			</tr>
		</thead>

		<tbody id="datatable">
			<logic:iterate id="X_OBJECT" name="X_RESULTLIST">
				<bean:define id="X_OBJECT" toScope="request" name="X_OBJECT" />
				<jsp:include page="row.jsp" />
			</logic:iterate>
		</tbody>
	</table>

</page:form>

<script type="text/javascript">
    applyRowStyle(document.all('datatable'));
	var mapping=new Map();
	mapping.put("category_id","category");
	mapping.put("subCategory_id","subcategory");
    initCascadeSelect("config","data","purchaseOrderQueryForm",mapping,true);
</script>

<logic:notEmpty name="X_RESULTLIST">
<table width="100%">
	<tr>
		<td align="right">
			<input type="button" value="<bean:message key="purchaseOrder.consolidate"/>"	
				onclick="consolidate()">
				
			<input type="button" value="<bean:message key="all.update"/>"	
				onclick="update()">
		</td>
	</tr>
</table>
</logic:notEmpty>