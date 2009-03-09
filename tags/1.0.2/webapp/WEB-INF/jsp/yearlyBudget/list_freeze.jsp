<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>

<script type="text/javascript">
<!--
	function doFreeze() {
		if(!checkSelected()) return false;
		with(document.yearlyBudgetQueryForm_again)
		{
			freeze.value="true";
			document.yearlyBudgetQueryForm_again["org.apache.struts.taglib.html.TOKEN"].value=
				document.yearlyBudgetQueryForm["org.apache.struts.taglib.html.TOKEN"].value;
			action="freezeYearlyBudget.do";
			submit();
		}
	}

	function unfreeze() {
		if(!checkSelected()) return false;
		with(document.yearlyBudgetQueryForm_again)
		{
			freeze.value="false";
			document.yearlyBudgetQueryForm_again["org.apache.struts.taglib.html.TOKEN"].value=
				document.yearlyBudgetQueryForm["org.apache.struts.taglib.html.TOKEN"].value;
			action="freezeYearlyBudget.do";
			submit();
		}
	}
	
	function checkSelected()
	{
		var objForm = document.forms[1];
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
			alert("<bean:message key="yearlyBudget.freeze.noSelected"/>");
			return false;
		}
		return true;
		
	}
	
//-->
</script>


<xml id="data">
<data>
<logic:iterate id="x_site" name="x_siteList">
	<site id="${x_site.id}" name="<bean:write name="x_site" property="name"/>">
		<department id="" name="<bean:message key="yearlyBudget.allDepartment"/>">
		</department>
		<logic:iterate id="x_d" name="x_site" property="departments">
			<department id="${x_d.id}" name="<bean:write name="x_d" property="name"/>">
			</department>
		</logic:iterate>
	</site>
</logic:iterate>
</data>
</xml>

<xml id="config">
<config>
	<site>
		<department>
		</department>
	</site>
</config>
</xml>

<html:javascript formName="yearlyBudgetQueryForm" staticJavascript="false"/>
<html:form action="listYearlyBudget_freeze" onsubmit="return validateYearlyBudgetQueryForm(this)" >
	<html:hidden property="order" />
	<html:hidden property="descend" />
	
	
	<input type="hidden" name="site_id_value" value="<bean:write name="yearlyBudgetQueryForm" property="site_id"/>">
	<input type="hidden" name="department_id_value" value="<bean:write name="yearlyBudgetQueryForm" property="department_id"/>">
	
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
	
	<table width=100% border=0 cellpadding=4 cellspacing=0>
	<tr>
		<td class="bluetext">
			<bean:message key="yearlyBudget.site.id"/>:
	  	</td>
		<td colspan="3">
			<html:select property="site_id">
			</html:select>
		</td>
	</tr>
	<tr>
		<td class="bluetext">
			<bean:message key="yearlyBudget.department.id"/>:
	  	</td>
		<td colspan="3">
			<html:select property="department_id">
			</html:select>
		</td>
	</tr>
	<tr>
	  	<td class="bluetext">
			<bean:message key="yearlyBudget.year"/>:
	  	</td>
		<td>
			<html:text property="year" size="4" maxlength="4"/>
			<!-- <html:select property="year">
				<logic:iterate name="x_yearList" id="x_year">
					<html:option value="${x_year}">${x_year}</html:option>
				</logic:iterate>
			</html:select> -->
		</td>
		<td class="bluetext">
			<bean:message key="yearlyBudget.status"/>:
	  </td>
		<td>
			<html:select property="status">
				<html:option value=""><bean:message key="all.all"/></html:option>
				<html:options collection="x_statusList"
						property="enumCode" labelProperty="${x_lang}ShortDescription" />
			</html:select>
		</td>
	</tr>
	<tr>
	  	<td class="bluetext">
			<bean:message key="yearlyBudget.code"/>:
	  	</td>
		<td>
			<html:text property="code" size="20" />
		</td>
		<td class="bluetext">
			<bean:message key="yearlyBudget.name"/>:
		</td>
		<td>
			<html:text property="name" size="20" />
		</td>
	</tr>
	<tr>
	  	<td class="bluetext">
			<bean:message key="yearlyBudget.amount"/>:
	  	</td>
		<td>
			<html:text property="amountFrom" size="10"/>
			~
			<html:text property="amountTo" size="10"/>
		</td>
	  	<td class="bluetext">
			<bean:message key="yearlyBudget.type"/>:
	  	</td>
		<td>
			<html:select property="type">
				<html:option value=""><bean:message key="all.all"/></html:option>
				<html:options collection="x_typeList"
						property="enumCode" labelProperty="${x_lang}ShortDescription" />
			</html:select>
		</td>
	</tr>
	<tr>
		<td class="bluetext"><bean:message key="yearlyBudget.isFreeze"/></td>
		<td>
			<html:select property="isFreeze">
				<html:option value=""><bean:message key="all.all"/></html:option>
				<html:options collection="x_YesNoList"
						property="enumCode" labelProperty="${x_lang}ShortDescription" />
			</html:select>
		</td>
	</tr>
	<tr>
		<td align="right" colspan="18">
			<html:submit><bean:message key="all.query"/></html:submit>
		</td>
	</tr>
</table>
<hr><br>
</html:form>	

<logic:notPresent name="x_first">
<page:form action="listYearlyBudget_freeze" >

	<input type="hidden" name="freeze" value=""/>
	<input type="hidden" name="org.apache.struts.taglib.html.TOKEN" value="" />
	
	<table class="pagebanner">
		<tr>
			<td align="right">
				<bean:message key="page.total" />1<bean:message key="page.page" />(${x_count}<bean:message key="page.record" />)
			</td>
		</tr>
	</table>
	<br>
	<logic:notEmpty name="X_RESULTLIST">
	<logic:present name="x_toFreeze">
		<input type="button" value="<bean:message key="yearlyBudget.freeze"/>" onclick="doFreeze()">
	</logic:present>
	<logic:present name="x_toUnfreeze">
		<input type="button" value="<bean:message key="yearlyBudget.unfreeze"/>" onclick="unfreeze()">
	</logic:present>
	

	<table class="data">
		<thead>
			<tr class="new_bg">
				<th>
					&nbsp;
				</th>
				<th>
					<page:order order="site" style="text-decoration:none">
						<bean:message key="yearlyBudget.site.id" />
						<page:desc>
							<img src="images/down.gif" border="0" />
						</page:desc>
						<page:asc>
							<img src="images/up.gif" border="0" />
						</page:asc>
					</page:order>
				</th>
				<th>
					<bean:message key="yearlyBudget.department.id"/>
				</th>
				<th>
					<page:order order="code" style="text-decoration:none">
						<bean:message key="yearlyBudget.code" />
						<page:desc>
							<img src="images/down.gif" border="0" />
						</page:desc>
						<page:asc>
							<img src="images/up.gif" border="0" />
						</page:asc>
					</page:order>
				</th>
				<th>
					<bean:message key="yearlyBudget.name"/>
				</th>
				<th>
					<bean:message key="yearlyBudget.type"/>
				</th>
				<th>
					<page:order order="amount" style="text-decoration:none">
					<bean:message key="yearlyBudget.amount"/>
					<page:desc>
						<img src="images/down.gif" border="0" />
					</page:desc>
					<page:asc>
						<img src="images/up.gif" border="0" />
					</page:asc>
					</page:order>
				</th>
				<th>
					<page:order order="actualAmount" style="text-decoration:none">
					<bean:message key="yearlyBudget.actualAmount"/>
					<page:desc>
						<img src="images/down.gif" border="0" />
					</page:desc>
					<page:asc>
						<img src="images/up.gif" border="0" />
					</page:asc>
					</page:order>
				</th>
				<th>
					<page:order order="remainAmount" style="text-decoration:none">
					<bean:message key="yearlyBudget.remainAmount"/>
					<page:desc>
						<img src="images/down.gif" border="0" />
					</page:desc>
					<page:asc>
						<img src="images/up.gif" border="0" />
					</page:asc>
					</page:order>
				</th>
				<th>
					<bean:message key="yearlyBudget.version"/>
				</th>
			</tr>
		<thead>
		<tbody id="datatable">
			<logic:iterate id="x_yb" name="X_RESULTLIST">
			<tr>
				<td><input type="checkbox" name="yearlyBudgetIds" value="${x_yb.id}"/></td>
				<td>${x_yb.site.name}</td>
				<td>
					<logic:iterate id="x_department" name="x_yb" property="departments">
						${x_department.name}<br>
					</logic:iterate>
				</td>
				<td>${x_yb.code}</td>
				<td>${x_yb.name}</td>
				<td><bean:write name="x_yb" property="type.${x_lang}ShortDescription"/></td>
				<td>${x_yb.amount}</td>
				<td>${x_yb.actualAmount}</td>
				<td>${x_yb.remainAmount}</td>
				<td>${x_yb.version}</td>
			</tr>
			</logic:iterate>
		</tbody>
	</table>
	

	<logic:present name="x_toFreeze">
		<input type="button" value="<bean:message key="yearlyBudget.freeze"/>" onclick="doFreeze()">
	</logic:present>
	<logic:present name="x_toUnfreeze">
		<input type="button" value="<bean:message key="yearlyBudget.unfreeze"/>" onclick="unfreeze()">
	</logic:present>
	
	<script type="text/javascript">
    	applyRowStyle(document.all('datatable'));
	</script>
	
	
	</logic:notEmpty>
</page:form>


</logic:notPresent>

<script type="text/javascript">
    var mapping=new Map();
	mapping.put("site_id","site");
	mapping.put("department_id","department");
    initCascadeSelect("config","data","yearlyBudgetQueryForm",mapping,true);
</script>
