<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<html:javascript formName="ruleForm" staticJavascript="false" />
<script language="javascript">
function show(/*object*/info){
	document.ruleForm.description.value=info.description;
	document.ruleForm.enabled.selectedIndex=info.enabled;
	
	initDatatableCondition(info.conditions);
	
	if (document.getElementById("datatableApprover") != null) {
		initDatatableApprover(info.consequences);
	} else if (document.getElementById("datatableNotifier") != null) {
		initDatatableNotifier(info.consequences);
	} else if (document.getElementById("datatablePurchaser") != null) {
		initDatatablePurchaser(info.consequences);
	}
}
function setProp(){
	if(!valid()){
		alert('信息填写错误');
		return;
	}
	var info=new Object();
	//将本页的信息添加到info中
	info.description=document.ruleForm.description.value;
	info.enabled=document.ruleForm.enabled[document.ruleForm.enabled.selectedIndex].value;
	info.conditions=getConditions();
	if (document.getElementById("datatableApprover") != null) {
		info.consequences=getApprovers();
	} else if (document.getElementById("datatableNotifier") != null) {
		info.consequences=getNotifiers();
	} else if (document.getElementById("datatablePurchaser") != null) {
		info.consequences=getPurchasers();
	} else {
		consequences = new Object();
		consequences.type = "Acceptable";
		info.consequences=consequences;
	}
	parent.setProperties(info);
	parent.hidePropertiesPane();
}
function getConditions() {
	var conditionTable = document.getElementById("datatable");
	if (conditionTable.rows.length > 0) {
		var conditions = new Array(conditionTable.rows.length);
		for (var i = 0; i < conditionTable.rows.length; i++) {
			conditions[i] = new Object();
			conditions[i].conditionTypeDesc = conditionTable.rows[i].children[0].children[0].innerHTML;
			conditions[i].compareTypeDesc = conditionTable.rows[i].children[0].children[1].innerHTML;
			conditions[i].displayValue = conditionTable.rows[i].children[0].children[2].innerHTML;
			conditions[i].conditionTypeEunmCode = conditionTable.rows[i].children[0].children[3].innerHTML;
			conditions[i].compareTypeEunmCode = conditionTable.rows[i].children[0].children[4].innerHTML;			
			conditions[i].value = conditionTable.rows[i].children[0].children[5].innerHTML;
			conditions[i].conditionTypeColor = conditionTable.rows[i].children[0].children[6].innerHTML;
			conditions[i].compareTypeColor = conditionTable.rows[i].children[0].children[7].innerHTML;
		}
		return conditions;
	}
}
function getApprovers() {
	var approverTable = document.getElementById("datatableApprover");
	if (approverTable.rows.length > 0) {
		var consequences = new Array(approverTable.rows.length);
		for (var i = 0; i < approverTable.rows.length; i++) {
			consequences[i] = new Object();
			consequences[i].type="Approver";
			consequences[i].seq = approverTable.rows[i].children[0].children[0].innerHTML;
			consequences[i].canModifyEnumCode = approverTable.rows[i].children[2].children[0].innerHTML;
			consequences[i].canModifyDesc = approverTable.rows[i].children[2].children[1].innerHTML;
			consequences[i].canModifyColor = approverTable.rows[i].children[2].children[2].innerHTML;
			consequences[i].userId = approverTable.rows[i].children[1].children[0].innerHTML;
			consequences[i].userName = approverTable.rows[i].children[1].children[1].innerHTML;
			consequences[i].superiorId = approverTable.rows[i].children[3].children[0].innerHTML;
			consequences[i].superiorName = approverTable.rows[i].children[3].children[1].innerHTML;			
		}
		return consequences;
	}	
}
function getNotifiers() {
	var notifierTable = document.getElementById("datatableNotifier");
	if (notifierTable.rows.length > 0) {
		var consequences = new Array(notifierTable.rows.length);
		for (var i = 0; i < notifierTable.rows.length; i++) {
			consequences[i] = new Object();
			consequences[i].type="Notifier";
		}
		return consequences;
	}	
}
function getPurchasers() {
	var purchaserTable = document.getElementById("datatablePurchaser");
	if (purchaserTable.rows.length > 0) {
		var consequences = new Array(purchaserTable.rows.length);
		for (var i = 0; i < purchaserTable.rows.length; i++) {
			consequences[i] = new Object();
			consequences[i].type="Purchaser";
			
			consequences[i].seq = 1;
			consequences[i].userId = purchaserTable.rows[i].children[0].children[1].innerHTML;
			consequences[i].userName = purchaserTable.rows[i].children[0].children[0].innerHTML;
		}
		return consequences;
	}	
}
function initDatatableCondition(conditions) {
	var conditionTable = document.getElementById("datatable");
	clearRow(conditionTable);
	if (conditions != null && conditions.length > 0) {		
		for (var i = 0; i < conditions.length; i++) {
			var html="";
			html+='<table id="resultTable">';  
			html+='<tbody>';
			html+='<tr id=r'+i+'>';  
		  	html+='<td>';  	
		    html+='<span style="color:'+conditions[i].conditionTypeColor+'">'+conditions[i].conditionTypeDesc+'</span>'; 
		    html+='<span style="color:'+conditions[i].compareTypeColor+'}">'+conditions[i].compareTypeDesc+'</span>'; 
		    html+='<span>'+conditions[i].displayValue+'</span>';
		    html+='<span style="display:none">'+conditions[i].conditionTypeEunmCode+'</span>'; 
		    html+='<span style="display:none">'+conditions[i].compareTypeEunmCode+'</span>'; 
		    html+='<span style="display:none">'+conditions[i].value+'</span>'; 
			html+='<span style="display:none">'+conditions[i].conditionTypeColor+'</span>';
   	 		html+='<span style="display:none">'+conditions[i].compareTypeColor+'</span>';
		  	html+='</td>'; 
		  	html+='<td>'; 
		    html+='<a href="javascript:editCondition('+conditions[i].conditionTypeEunmCode+', '+conditions[i].compareTypeEunmCode+', '+conditions[i].value+', '+i+')"><bean:message key="all.modify"/></a>'; 
		  	html+='</td>'; 
		   	html+='<td>'; 
		    html+='<a href="javascript:deleteCondition('+conditions[i].conditionTypeEunmCode+', '+i+')"><bean:message key="all.delete"/></a>'; 
		    html+='</td>'; 
		    html+='</tr>';
		    html+='</tbody>';
		    html+='</table>';
		    appendRow(conditionTable, html);		    
		}
		applyRowStyle(conditionTable);
	}
}
function initDatatableApprover(consequences) {
	var approverTable = document.getElementById("datatableApprover");
	clearRow(approverTable);
	if (consequences != null && consequences.length > 0) {		
		for (var i = 0; i < consequences.length; i++) {
			var html="";
			html+='<table id="resultTable">';  
			html+='<tbody>';
			html+='<tr id="r'+consequences[i].userId+'">';
			if (consequences[i].superiorId != null && consequences[i].superiorId != "") {
				html+='<td align="center"><a href="javascript:editApprover('+consequences[i].userId+', '+consequences[i].seq+', '+consequences[i].canModifyEnumCode+', '+consequences[i].superiorId+')">'+consequences[i].seq+'</a></td>';
			} else {
				html+='<td align="center"><a href="javascript:editApprover('+consequences[i].userId+', '+consequences[i].seq+', '+consequences[i].canModifyEnumCode+')">'+consequences[i].seq+'</a></td>';
			}
			html+='<td>';
			html+='<span style="display:none">'+consequences[i].userId+'</span>';
			html+='<span>'+consequences[i].userName+'</span>';
			html+='</td>';
			html+='<td>';
			html+='<span style="display:none">'+consequences[i].canModifyEnumCode+'</span>';
			html+='<span style="color:'+consequences[i].canModifyColor+'">'+consequences[i].canModifyDesc+'</span>';
			html+='<span style="display:none">'+consequences[i].canModifyColor+'</span>';
			html+='</td>';
			html+='<td>';
			html+='<span style="display:none">'+consequences[i].superiorId+'</span>';
			html+='<span>'+consequences[i].superiorName+'</span>  	';
			html+='</td>';
			html+='<td>';
			html+='<a href="javascript:deleteApprover('+consequences[i].userId+')"><bean:message key="all.delete"/></a>';
			html+='</td>';
			html+='</tr>';
			html+='</tbody>';
		    html+='</table>';
			appendRow(approverTable, html);	
		}
		applyRowStyle(approverTable);
	}	
}
function initDatatableNotifier(consequences) {
	var notifierTable = document.getElementById("datatableNotifier");
	clearRow(notifierTable);
	if (consequences != null && consequences.length > 0) {		
		for (var i = 0; i < consequences.length; i++) {
			var html="";
			html+='<table id="resultTable">';  
			html+='<tbody>';			
				
			html+='</tbody>';
		    html+='</table>';
			appendRow(notifierTable, html);	
		}
		applyRowStyle(notifierTable);
	}	
}
function initDatatablePurchaser(consequences) {
	var purchaserTable = document.getElementById("datatablePurchaser");
	clearRow(purchaserTable);
	
	if (consequences != null && consequences.length > 0) {		
		for (var i = 0; i < consequences.length; i++) {
			var html="";
			html+='<table id="resultTable">';  
			html+='<tbody>';
			html+='<tr id="r'+consequences[i].userId+'">';
  			html+='<td>';
  			html+='<span>'+consequences[i].userName+'</span>';
  			html+='<span style="display:none">'+consequences[i].userId+'</span>';
  			html+='</td>';
  			html+='<td>';
    		html+='<a href="javascript:deletePurchaser('+consequences[i].userId+')"><bean:message key="all.delete"/></a>';
  			html+='</td>';
			html+='</tr>';
			html+='</tbody>';
		    html+='</table>';
			appendRow(purchaserTable, html);	
		}
		applyRowStyle(purchaserTable);
	}	
}
function valid(){
	//验证
	//TODO
	
	return true;
}
</script>
<html:form action="update${X_RULETYPE.prefixUrl}Rule.do" onsubmit="setProp();return false;">
<html:hidden property="site_id"/>
<table width="100%" border="0" cellpadding="4" cellspacing="0">
<tr>
  <td class="bluetext"><bean:message key="rule.site"/>:</td>
  <td><bean:write name="ruleForm" property="site_name"/></td>
</tr>
<tr>
  <td class="bluetext"><bean:message key="rule.description"/>:</td>
  <td><html:text property="description" size="40"/></td>
</tr>
<tr style="dispaly:none">
  <td class="bluetext"><bean:message key="rule.enabled"/>:</td>
  <td>
    <html:select property="enabled">
      <html:options collection="X_ENABLEDDISABLEDLIST" property="enumCode" labelProperty="${x_lang}ShortDescription"/>
    </html:select>
  </td>
</tr>
</table>
<hr width="100%"/>
<table width="90%" border="0" cellpadding="4" cellspacing="0">
  <tr>
	  <td align="right">
	  	<button onclick='setProp()'><bean:message key="all.confirm" /></button>
	  	<button onclick="parent.hidePropertiesPane()"><bean:message key="all.cancel" /></button>
	  </td>
	</tr>
</table>
</html:form>
<jsp:include page="../ruleCondition/list.jsp"/>