<%@ page language="java" contentType="text/html; charset=utf-8"%>
<table id="resultTable">
  <tbody>
    <jsp:include page="${X_ROWPAGE}"/>
  </tbody>
</table>
<script language='javascript'>
    window.parent.returnValue = document.all('resultTable').outerHTML;
    window.parent.close();
</script>