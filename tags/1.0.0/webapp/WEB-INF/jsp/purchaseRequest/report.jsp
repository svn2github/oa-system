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
		<department id="0" name="All"/>
		<logic:iterate id="x_department" name="x_site" property="departments">
		<c:if test="${x_department.granted}">
			<department id="${x_department.id}" name="<bean:write name="x_department" property="indentName"/>"/>
		</c:if>
		</logic:iterate>
		
		<purchaseCategory id="" name="<bean:message key="all.all"/>">
			<purchaseSubCategory id="" name="<bean:message key="all.all"/>"/>
		</purchaseCategory>
		
		<logic:iterate id="x_pc" name="x_site" property="enabledPurchaseCategoryList">
		<purchaseCategory id="${x_pc.id}" name="<bean:write name="x_pc" property="description"/>">
			<purchaseSubCategory id="" name="<bean:message key="all.all"/>"/>
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
		<department/>
		<purchaseCategory>
			<purchaseSubCategory/>
		</purchaseCategory>
	</site>
</config>
</xml>



<script type="text/javascript">
<!--
	function view(id) {
		var url="viewPurchaseRequest_report.do?id="+id;
		window.location.href=url;
	
	}	
	
	function validateForm(form)
	{
		if(!validatePurchaseRequestQueryForm(form)) return false;
		return true;
	}
	
//-->
</script>

<div class="message"><html:messages id="x_message" message="true">${x_message}<br></html:messages></div>

<html:javascript formName="purchaseRequestQueryForm" staticJavascript="false"/>
<html:form action="reportPurchaseRequest" focus="id" onsubmit="return validateForm(this)">
	<html:hidden property="order" />
	<html:hidden property="descend" />
	
	<input type="hidden" name="site_id_value" 
		value="<bean:write name="purchaseRequestQueryForm" property="site_id"/>"/>
	<input type="hidden" name="department_id_value" 
		value="<bean:write name="purchaseRequestQueryForm" property="department_id"/>"/>
	<input type="hidden" name="purchaseSubCategory_id_value" 
		value="<bean:write name="purchaseRequestQueryForm" property="purchaseSubCategory_id"/>"/>
	<input type="hidden" name="purchaseCategory_id_value" 
		value="<bean:write name="purchaseRequestQueryForm" property="purchaseCategory_id"/>"/>
	
	<table  border=0 cellpadding=4 cellspacing=0>
		<tr>
			<td class="bluetext" ><bean:message key="purchaseRequest.search.site"/>&nbsp;</td>
			<td >
				<html:select property="site_id">
				</html:select>
			</td>
			<td class="bluetext" ><bean:message key="purchaseRequest.search.department"/>&nbsp;</td>
			<td >
				<html:select property="department_id">
				</html:select>
			</td>	
		</tr>
		<tr>
			<td class="bluetext" ><bean:message key="purchaseRequest.id"/>&nbsp;</td>
			<td><html:text property="id" size="13"/></td>
			<td class="bluetext" ><bean:message key="purchaseRequest.title"/>&nbsp;</td>
			<td><html:text property="title" size="14"/></td>
		</tr>	
		<tr>
			<td class="bluetext" ><bean:message key="purchaseRequest.requestor"/>&nbsp;</td>
			<td><html:text property="requestor_name" size="13"/></td>
	
			<td class="bluetext" ><bean:message key="purchaseRequest.status"/></td>
			<td>
				 <html:select property="status">
		           <html:option value=""><bean:message key="all.all"/></html:option>
		           <html:options collection="x_statusList" property="enumCode" labelProperty="${x_lang}ShortDescription" />
		         </html:select>
			</td>
		</tr>	
		<tr>
			<td class="bluetext" ><bean:message key="purchaseRequest.purchaseCategory"/>&nbsp;</td>
			<td>
				 <html:select property="purchaseCategory_id">
		         </html:select>
			</td>
			<td class="bluetext" ><bean:message key="purchaseRequest.purchaseSubCategory"/>&nbsp;</td>
			<td>
				 <html:select property="purchaseSubCategory_id">
		         </html:select>
			</td>
		</tr>	
		<tr>
			<td class="bluetext" ><bean:message key="purchaseRequest.requestAmount"/>&nbsp;</td>
			<td>
				 <html:text property="amount1" size="3"/>~<html:text property="amount2" size="3"/>
			</td>
			<td class="bluetext" ><bean:message key="purchaseRequest.createDate"/>&nbsp;</td>
			<td>
				<html:text property="createDate1" size="6" maxlength="10"/>
				<a onclick="event.cancelBubble=true;"
				href="javascript:showCalendar('dimg1',false,'createDate1',null,null,'purchaseRequestQueryForm')"><img
				align="absmiddle" border="0" id="dimg1" src="images/datebtn.gif" /></a>
				~
				<html:text property="createDate2" size="6" maxlength="10"/>
				<a onclick="event.cancelBubble=true;"
				href="javascript:showCalendar('dimg2',false,'createDate2',null,null,'purchaseRequestQueryForm')"><img
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
<page:form action="reportPurchaseRequest.do" method="post">
	<jsp:include page="../pageHead.jsp"/>
	<table class="data">
		<thead>
			<tr bgcolor="#9999ff">
				<th width="180"><page:order order="id" style="text-decoration:none">
					<bean:message key="purchaseRequest.id" />
					<page:desc>
						<img src="images/down.gif" border="0" />
					</page:desc>
					<page:asc>
						<img src="images/up.gif" border="0" />
					</page:asc>
				</page:order></th>
				
				<th width="120"><page:order order="requestor_name" style="text-decoration:none">
					<bean:message key="purchaseRequest.requestor.id" />
					<page:desc>
						<img src="images/down.gif" border="0" />
					</page:desc>
					<page:asc>
						<img src="images/up.gif" border="0" />
					</page:asc>
				</page:order></th>

				<!--
				 <th ><page:order order="title"
					style="text-decoration:none">
					<bean:message key="purchaseRequest.title" />
					<page:desc>
						<img src="images/down.gif" border="0" />
					</page:desc>
					<page:asc>
						<img src="images/up.gif" border="0" />
					</page:asc>
				</page:order></th>  
				-->
				<th >
					<bean:message key="purchaseRequest.purchaseCategory" />
				</th>
				<th >
					<bean:message key="purchaseRequest.purchaseSubCategory" />
				</th>
				
				
				<th ><page:order order="amount"
					style="text-decoration:none">
					<bean:message key="purchaseRequest.amount" />
					<page:desc>
						<img src="images/down.gif" border="0" />
					</page:desc>
					<page:asc>
						<img src="images/up.gif" border="0" />
					</page:asc>
				</page:order></th>
				
				<th width="80"><page:order order="requestDate"
					style="text-decoration:none">
					<bean:message key="purchaseRequest.requestDate" />
					<page:desc>
						<img src="images/down.gif" border="0" />
					</page:desc>
					<page:asc>
						<img src="images/up.gif" border="0" />
					</page:asc>
				</page:order></th>
				
				<th width="80"><page:order order="status"
					style="text-decoration:none">
					<bean:message key="purchaseRequest.status" />
					<page:desc>
						<img src="images/down.gif" border="0" />
					</page:desc>
					<page:asc>
						<img src="images/up.gif" border="0" />
					</page:asc>
				</page:order></th>
				<th><bean:message key="purchaseRequest.approvalDuration" /></th>
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
	mapping.put("department_id", "department");
	mapping.put("purchaseCategory_id", "purchaseCategory");
	mapping.put("purchaseSubCategory_id", "purchaseSubCategory");
	
    initCascadeSelect("config", "data", "purchaseRequestQueryForm", mapping, true);
</script>

