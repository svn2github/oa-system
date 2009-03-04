<%@ page language="java" contentType="text/html; charset=utf-8"%>
<html>
<body>
<div id="contentDIV">
    <jsp:include page="${X_CONTENTPAGE}"/>
</div>
<script language='javascript'>
    window.parent.returnValue = document.all('contentDIV').innerHTML;
    window.parent.close();
</script>
</body>
</html>