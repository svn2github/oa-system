<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<script type="text/javascript">
<!--
	function confirmClick(form) {
		if (getStrLenUTF8(form.comment.value) > 255) {
			alert("<bean:message key="errors.maxlength" arg0="Comment" arg1="255"/>");
			return;
		}
		window.parent.returnValue = [ form.comment.value ];
		window.parent.close();
	}
//-->
</script>
<form>
<table width=100% border=0 cellpadding=0 cellspacing=0>
	<tr>
		<td class="bluetext" valign="top"><bean:message key="approveRequest.comment" />:</td>
		<td><textarea name="comment" cols="40" rows="5"></textarea></td>
	</tr>
</table>
<hr width="100%">
<table width="90%" border=0 cellpadding=0 cellspacing=0>
	<tr>
		<td align="right">
			<input type="button" value="<bean:message key="all.confirm" />" onclick="confirmClick(this.form)"/>
			<input type="button" value="<bean:message key="all.cancel" />" onclick="window.parent.close();"/>
		</td>
	</tr>
</table>
</form>
