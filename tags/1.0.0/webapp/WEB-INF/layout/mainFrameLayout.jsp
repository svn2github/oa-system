<!--DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"-->
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<tiles:importAttribute name="pageMainTitle" scope="page" ignore="true"/>
<tiles:importAttribute name="pageSubTitle" scope="page" ignore="true"/>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title><tiles:getAsString name="title"/></title>
<link href="includes/default.css" rel="stylesheet" type="text/css">

<script language=javascript type="text/javascript" src="includes/date/calendar.js"></script>
<script language="javascript" src="includes/validate.js"></script>
<script language="javascript" src="includes/common.js"></script>
<script language="javascript" src="includes/alert.jsp"></script>
<script language="javascript" src="includes/cascadeSelect.js"></script>
<script language="javascript" src="includes/table.js"></script>

<script language="javascript">
	var menuFrameWidth = 230;
	
	function changeWin(){
	  if (parent.oa.cols != "0,*") {
	    menuFrameWidth = parent.frames("leftFrame").document.body.offsetWidth;
	    parent.oa.cols = "0,*";
	    document.all.menuSwitch.src="images/split2.gif";
	  } else {
	    parent.oa.cols = menuFrameWidth + ",*";
	    document.all.menuSwitch.src="images/split1.gif";
	  }
	}
</script>
</head>
<body onload="setFormPreventRepeatSubmit();">
<table width="100%" height="100%" cellspacing="0" cellpadding="0" border="0">
<tr>
  <td width="8" id="menuDrag" background="images/splitbg.gif" bgcolor="#d1cdd3">
    <img id="menuSwitch" src="images/split1.gif" title="隐藏/显示菜单栏" style="cursor:hand" onclick="changeWin()"/>
  </td>
  <td valign="top">
    <table width="100%" cellspacing="0" cellpadding="0" border="0">
    <tr height="5">
      <td colspan="3"></td>
    </tr>
    <tr>
      <td width="5"></td>
      <td>
	      <logic:present name="pageMainTitle" scope="page">
	      <table width=100% border=1 cellpadding=4 cellspacing=0 bgcolor=ffffff>
			<tr>
				<td bgcolor=f0f0f0 width=100% colspan=2 valign=top>    
					<table width=100% cellpadding=0 cellspacing=0>
			      <tr>
			        <td bgcolor=f0f0f0 width=80% valign=top>
			        	<h3 class="formtitle"><bean:message key="${pageMainTitle}" />
			        	<logic:present name="pageSubTitle" scope="page">
			        		<bean:message key="${pageSubTitle}" />
			        	</logic:present>
			        	</h3>
			        </td>
			       	<td valign=top align ="right"></td>
			      </tr>
			    </table>
			  </td>
			</tr>
		</table>
		<br>
		</logic:present>
        <tiles:insert attribute="body"/>
      </td>
      <td width="5"></td>
    </tr>
    </table>
  </td>
</tr>
</table>
</body>
</html>
