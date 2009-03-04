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
</head>
<frameset rows="55,*" border="1" name="wholeoa" framespacing="0" frameborder="no" topmargin="0" leftmargin="0" marginheight="0" marginwidth="0">
  <frame name="topFrame" src="top.do" marginwidth="0" marginheight="0" frameborder="no" noresize scrolling="no"/>
  <frameset border="0" name="oa" framemargin=0 framespacing="1" frameborder="no" cols="230,*">
    <frame src="menu.do" name="leftFrame" marginwidth="0" marginheight="0" frameborder="no"/>
    <frame src="blank.do" name="mainFrame" marginwidth="0" marginheight="0" frameborder="no" scrolling="yes"/>
  </frameset>
</frameset>
<noframes>
</noframes>
</html>