<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>
<script language="javascript" src="includes/table.js"></script>
<script type="text/javascript">
<!--
	function add() {
		var url="newApproverDelegate${x_postfix}.do?originalApprover_id=${x_originalApprover.id}&type="+document.approverDelegateQueryForm["type"].value;

		var title="approverDelegate.new.title";
		var v=dialogAction(url,title,400,380);
		if (v) {
			var table = document.all('datatable');
			appendRow(table, v);
		    applyRowStyle(table);
		};
	}

	function edit(id) {
		var url="editApproverDelegate${x_postfix}.do?id=" + id
		var title="approverDelegate.edit.title";
		var v=dialogAction(url,title,400,380);
		if (v) {
			updateRow(document.all('r' + id), v);
		};
	}
	
	function changeType()
	{
		var form =document.approverDelegateQueryForm;
		if(validateApproverDelegateQueryForm(form))
		{
			form.submit();
		}
	}
	
//-->
</script>
<html:javascript formName="approverDelegateQueryForm" staticJavascript="false"/>
<html:form action="listApproverDelegate${x_postfix}" onsubmit="return validateApproverDelegateQueryForm(this)">
	<html:hidden property="order" />
	<html:hidden property="descend" />

	<table  border=0 cellpadding=4 cellspacing=0>
		<tr>
			<td class="bluetext"><bean:message key="approverDelegate.originalApprover.id" />:</td>
			<td>
				<html:hidden property="originalApprover_id"/>${x_originalApprover.name}
			</td>
			
	
			<logic:empty name="x_postfix">
				<td class="bluetext"><bean:message key="approverDelegate.type" />:</td>
				<td>
					<html:hidden property="type"/><bean:write name="x_type" property="${x_lang}ShortDescription"/>
				</td>
			</logic:empty>
			
			<logic:notEmpty name="x_postfix">
				<td class="bluetext"><bean:message key="approverDelegate.type" />:</td>
				<td>
					<html:select property="type" onchange="changeType()">
						<c:if test="${sessionScope.LOGIN_USER.locale=='en'}">
							<html:options collection="x_typeList"
								property="enumCode" labelProperty="engShortDescription" />
						</c:if>
						<c:if test="${sessionScope.LOGIN_USER.locale!='en'}">
							<html:options collection="x_typeList"
								property="enumCode" labelProperty="chnShortDescription" />
						</c:if>
						</html:select> 
				</td>
			</logic:notEmpty>
			<!-- 
			
			<td class="bluetext"><bean:message key="approverDelegate.fromDate" />:</td>
			<td><html:text property="fromDate" size="8"/></td>
			<td class="bluetext"><bean:message key="approverDelegate.toDate" />:</td>
			<td><html:text property="toDate" size="8"/></td>
			
			-->
		</tr>	
		<tr>
			<td class="bluetext"><bean:message key="approverDelegate.fromDate"/></td>
			<td colspan="3">
				<html:text property="fromDate1" size="6" maxlength="10"/>
				<a onclick="event.cancelBubble=true;"
				href="javascript:showCalendar('dimg1',false,'fromDate1',null,null,'approverDelegateQueryForm')"><img
				align="absmiddle" border="0" id="dimg1" src="images/datebtn.gif" /></a>
				~
				<html:text property="fromDate2" size="6" maxlength="10"/>
				<a onclick="event.cancelBubble=true;"
				href="javascript:showCalendar('dimg2',false,'fromDate2',null,null,'approverDelegateQueryForm')"><img
				align="absmiddle" border="0" id="dimg2" src="images/datebtn.gif" /></a>
			</td>
		</tr>
		<tr>
			<td class="bluetext"><bean:message key="approverDelegate.toDate"/></td>
			<td colspan="3">
				<html:text property="toDate1" size="6" maxlength="10"/>
				<a onclick="event.cancelBubble=true;"
				href="javascript:showCalendar('dimg3',false,'toDate1',null,null,'approverDelegateQueryForm')"><img
				align="absmiddle" border="0" id="dimg3" src="images/datebtn.gif" /></a>
				~
				<html:text property="toDate2" size="6" maxlength="10"/>
				<a onclick="event.cancelBubble=true;"
				href="javascript:showCalendar('dimg4',false,'toDate2',null,null,'approverDelegateQueryForm')"><img
				align="absmiddle" border="0" id="dimg4" src="images/datebtn.gif" /></a>
			</td>
			
			<td align="left">
				<html:submit>
					<bean:message key="all.query" />
				</html:submit>
				
				<input type="button" value="<bean:message key="all.new"/>"
					onClick="add()" />
			</td>
			
			
		</tr>
	</table>
</html:form>
<hr />
<page:form action="listApproverDelegate${x_postfix}.do" method="post">
	<jsp:include page="../pageHead.jsp"/>
	<table class="data">
		<thead>
			<tr class="new_bg">
				<th width="30%"><page:order order="delegateApprover.name" style="text-decoration:none">
					<bean:message key="approverDelegate.delegateApprover.id" />
					<page:desc>
						<img src="images/down.gif" border="0" />
					</page:desc>
					<page:asc>
						<img src="images/up.gif" border="0" />
					</page:asc>
				</page:order></th>

				<th width="30%"><page:order order="fromDate"
					style="text-decoration:none">
					<bean:message key="approverDelegate.fromDate" />
					<page:desc>
						<img src="images/down.gif" border="0" />
					</page:desc>
					<page:asc>
						<img src="images/up.gif" border="0" />
					</page:asc>
				</page:order></th>
				
				<th width="30%"><page:order order="toDate"
					style="text-decoration:none">
					<bean:message key="approverDelegate.toDate" />
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
	<jsp:include page="../pageTail.jsp"/>
</page:form>

<script type="text/javascript">
    applyRowStyle(document.all('datatable'));
</script>

