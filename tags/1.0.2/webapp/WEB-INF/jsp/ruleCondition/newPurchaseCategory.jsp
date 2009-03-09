<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<script language="javascript" src="includes/collection.js"></script>
<script language="javascript" src="includes/cascadeSelect.js"></script>

<xml id="data">
<data>
  <logic:iterate id="X_PC" name="X_PURCHASECATEGORYLIST">
  <category id="${X_PC.id}" name="<bean:write name="X_PC" property="description"/>">
    <subCategory id="" name="<bean:message key="ruleCondition.allSubCategory"/>">
    </subCategory>
    <logic:iterate id="X_PSC" name="X_PC" property="enabledPurchaseSubCategoryList">
    <subCategory id="${X_PSC.id}" name="<bean:write name="X_PSC" property="description"/>">
    </subCategory>
    </logic:iterate>
  </category>
  </logic:iterate>
</data>
</xml>

<xml id="config">
<config>
  <category>
    <subCategory>
    </subCategory>
  </category>
</config>
</xml>

<html:javascript formName="ruleConditionForm" staticJavascript="false" />
<html:form action="insert${X_RULETYPE.prefixUrl}RuleCondition${WebDragAndDraw}.do" onsubmit="return validateRuleConditionForm(this);">
<html:hidden property="rule_id"/>
<html:hidden property="type"/>
<input type="hidden" name="site_id" value="<%= request.getParameter("site_id") %>"/>
<table border="0" cellpadding="4" cellspacing="0">
<tr>
  <td>
    <span style="color:${X_RULECONDITION.type.color}"><bean:write name="X_RULECONDITION" property="type.${x_lang}Description"/></span>
  </td>
  <td>
    <logic:notPresent name="X_COMPARETYPELIST"><html:hidden property="compareType"/></logic:notPresent>
    <logic:present name="X_COMPARETYPELIST">
    <html:select property="compareType">
      <html:options collection="X_COMPARETYPELIST" property="enumCode" labelProperty="${x_lang}Description"/>
    </html:select>
    </logic:present>
  </td>
  <td>
    <html:select property="pvalue"></html:select><br/>
  </td>
</tr>
<tr>
  <td></td>
  <td></td>
  <td>
    <html:select property="value"></html:select>
  </td>
</tr>
</table>
<hr/>
<table width="90%" border="0" cellpadding="4" cellspacing="0">
<tr>
  <td align="right">
  	<html:submit><bean:message key="all.save"/></html:submit>
  	<input type="button" value="<bean:message key="all.cancel"/>" onclick="window.parent.close();"/>
  </td>
</tr>
</table>
</html:form>
<script type="text/javascript">
    var mapping=new Map();
	mapping.put("pvalue", "category");
	mapping.put("value", "subCategory");
    initCascadeSelect("config", "data", "ruleConditionForm", mapping, true);
</script>
