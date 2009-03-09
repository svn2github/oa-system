<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<div id="result">
  <html:errors/>
  <logic:present name="X_APPROVELIST">
  <table width="90%" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td><jsp:include page="../approve/list.jsp" /></td>
	</tr>
  </table>
  </logic:present>
</div>
<script type="text/javascript">
	window.parent.viewApprover_callback(document.all('result').innerHTML);
</script>
