<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<script type="text/javascript">
<!--
	function selectUser(v) {
		ruleConsequenceForm.user_id.value = v['id'];
		ruleConsequenceForm.user_name.value = v['name'];
		ruleConsequenceForm.submit();
	}
//-->
</script>
<html:javascript formName="ruleConsequenceForm" staticJavascript="false" />
<html:form action="insert${X_RULETYPE.prefixUrl}RuleConsequence.do" onsubmit="return validateRuleConsequenceForm(this);">
<html:hidden property="rule_id"/>
<html:hidden property="user_id"/>
<html:hidden property="user_name"/>
<input type="hidden" name="site_id" value='<%=request.getParameter("site_id") %>'/>
<div class="warningMsg"><html:errors/></div>
</html:form>
<iframe src="select${X_RULECONSEQUENCE.rule.type.prefixUrl}ConsequenceUser.do?iniframe=true" frameborder="0" height="500" width="100%" scrolling="auto"></iframe>