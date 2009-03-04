<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>

<xml id="data">
<data>
<logic:iterate id="x_site" name="x_siteList">
	<site id="${x_site.id}" name="<bean:write name="x_site" property="name"/>">
	
		<category id="" name="<bean:message key="all.all"/>">
			<subCategory id="" name="<bean:message key="all.all"/>"/>
		</category>
		
		<logic:iterate id="x_pc" name="x_site" property="enabledPurchaseCategoryList">
		<category id="${x_pc.id}" name="<bean:write name="x_pc" property="description"/>">
			<subCategory id="" name="<bean:message key="all.all"/>"/>
			<logic:iterate id="x_psc" name="x_pc" property="enabledPurchaseSubCategoryList">
			<subCategory id="${x_psc.id}" name="<bean:write name="x_psc" property="description"/>"/>
			</logic:iterate>
		</category>
		</logic:iterate>
	</site>
</logic:iterate>
</data>
</xml>

<xml id="config">
<config>
	<site>
		<category>
			<subCategory/>
		</category>
	</site>
</config>
</xml>



<script type="text/javascript">
<!--
	function view(id) {
		var url="viewPurchaseOrder_report.do?id="+id;
		window.location.href=url;
	
	}	
	
	function validateForm(form)
	{
		if(!validatePurchaseOrderQueryForm(form)) return false;
		return true;
	}
	
//-->
</script>

<div class="message"><html:messages id="x_message" message="true">${x_message}<br></html:messages></div>

<html:javascript formName="purchaseOrderQueryForm" staticJavascript="false"/>
<html:form action="reportPurchaseOrder" focus="id" onsubmit="return validateForm(this)">
	<html:hidden property="order" />
	<html:hidden property="descend" />
	
	<input type="hidden" name="site_id_value" 
		value="<bean:write name="purchaseOrderQueryForm" property="site_id"/>"/>
	<input type="hidden" name="subCategory_id_value" 
		value="<bean:write name="purchaseOrderQueryForm" property="subCategory_id"/>"/>
	<input type="hidden" name="category_id_value" 
		value="<bean:write name="purchaseOrderQueryForm" property="category_id"/>"/>
	
	<table  border=0 cellpadding=4 cellspacing=0>
		<tr>
			<td class="bluetext" ><bean:message key="purchaseOrder.search.site"/>&nbsp;</td>
			<td colspan="3">
				<html:select property="site_id">
				</html:select>
			</td>
		</tr>
		<tr>
			<td class="bluetext" ><bean:message key="purchaseOrder.id"/>&nbsp;</td>
			<td colspan="3"><html:text property="id" size="13"/></td>
		</tr>	
		<tr>
			<td class="bluetext" ><bean:message key="purchaseOrder.supplier.id"/>&nbsp;</td>
			<td><html:text property="supplier_name" size="13"/></td>
	
			<td class="bluetext" ><bean:message key="purchaseOrder.status"/></td>
			<td>
				 <html:select property="status">
		           <html:option value=""><bean:message key="all.all"/></html:option>
		           <html:options collection="x_statusList" property="enumCode" labelProperty="${x_lang}ShortDescription" />
		         </html:select>
			</td>
		</tr>	
		<tr>
			<td class="bluetext" ><bean:message key="purchaseOrder.category"/>&nbsp;</td>
			<td>
				 <html:select property="category_id">
		         </html:select>
			</td>
			<td class="bluetext" ><bean:message key="purchaseOrder.subCategory"/>&nbsp;</td>
			<td>
				 <html:select property="subCategory_id">
		         </html:select>
			</td>
		</tr>	
		<tr>
			<td class="bluetext" ><bean:message key="purchaseOrder.amount"/>&nbsp;</td>
			<td>
				 <html:text property="amount1" size="3"/>~<html:text property="amount2" size="3"/>
			</td>
			<td class="bluetext" ><bean:message key="purchaseOrder.createDate"/>&nbsp;</td>
			<td>
				<html:text property="createDate1" size="6" maxlength="10"/>
				<a onclick="event.cancelBubble=true;"
				href="javascript:showCalendar('dimg1',false,'createDate1',null,null,'purchaseOrderQueryForm')"><img
				align="absmiddle" border="0" id="dimg1" src="images/datebtn.gif" /></a>
				~
				<html:text property="createDate2" size="6" maxlength="10"/>
				<a onclick="event.cancelBubble=true;"
				href="javascript:showCalendar('dimg2',false,'createDate2',null,null,'purchaseOrderQueryForm')"><img
				align="absmiddle" border="0" id="dimg2" src="images/datebtn.gif" /></a>
			</td>
		</tr>	
		<tr>
			<td colspan="6" align="right">
				<html:submit><bean:message key="all.query"/></html:submit>&nbsp;&nbsp;&nbsp;
				<input type="button" value="<bean:message key="all.new"/>"
					 onClick="add()">
			</td>
		</tr>
	</table>
</html:form>
<hr />
<page:form action="reportPurchaseOrder.do" method="post">
	<jsp:include page="../pageHead.jsp"/>
	<table class="data">
		<thead>
			<tr class="new_bg">
				<th width="180"><page:order order="id" style="text-decoration:none">
					<bean:message key="purchaseOrder.id" />
					<page:desc>
						<img src="images/down.gif" border="0" />
					</page:desc>
					<page:asc>
						<img src="images/up.gif" border="0" />
					</page:asc>
				</page:order></th>
				
				<th ><page:order order="amount"
					style="text-decoration:none">
					<bean:message key="purchaseOrder.amount" />
					<page:desc>
						<img src="images/down.gif" border="0" />
					</page:desc>
					<page:asc>
						<img src="images/up.gif" border="0" />
					</page:asc>
				</page:order></th>
				

				<th width="120"><page:order order="supplier_name" style="text-decoration:none">
					<bean:message key="purchaseOrder.supplier.id" />
					<page:desc>
						<img src="images/down.gif" border="0" />
					</page:desc>
					<page:asc>
						<img src="images/up.gif" border="0" />
					</page:asc>
				</page:order></th>

				<th >
					<bean:message key="purchaseOrder.category" />
				</th>
				<th >
					<bean:message key="purchaseOrder.subCategory" />
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
			</tr>
		</thead>

		<tbody id="datatable">
			<logic:iterate id="X_OBJECT" name="X_RESULTLIST">
				<bean:define id="X_OBJECT" toScope="request" name="X_OBJECT" />
				<jsp:include page="reportRow.jsp" />
			</logic:iterate>
		</tbody>
	</table>
	<jsp:include page="../pageTail.jsp"/>
</page:form>

<script type="text/javascript">
    applyRowStyle(document.all('datatable'));
</script>


<script type="text/javascript">
    var mapping=new Map();
	mapping.put("site_id", "site");
	mapping.put("category_id", "category");
	mapping.put("subCategory_id", "subCategory");
	
    initCascadeSelect("config", "data", "purchaseOrderQueryForm", mapping, true);
	
</script>

