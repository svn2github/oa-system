<!--DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"-->
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title><tiles:get name="title"/></title>
<link href="includes/default.css" rel="stylesheet" type="text/css">
<script language="javascript" src="includes/common.js"></script>
<script language="javascript" src="includes/validate.js"></script>
<script language="javascript">
	// prevent this page load into child frame of frameset oa
	if (self.parent != self && top.oa != null) {
		top.location.href = self.location.href;
	}
</script>
</head>
<body onload="setFormPreventRepeatSubmit();">
<table width="100%" height="100%" cellpadding="0" cellspacing="0" border="0">
<tr height="55">
  <td>
    <tiles:insert attribute="top"/>
  </td>
</tr>
<tr>
  <td>
    <tiles:insert attribute="body"/>
  </td>
</tr>
</table>
</body>
</html>
