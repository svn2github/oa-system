<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<div id="DIV1">
  <table id="resultTable">
    <tbody>
      <jsp:include page="row.jsp"/>
    </tbody>
  </table>
</div>
<script language='javascript'>
	var v = [];
    v[0] = document.all('DIV1').all('resultTable').outerHTML;
</script>
<logic:present name="X_OBJECT2">
  <% request.setAttribute("X_OBJECT", request.getAttribute("X_OBJECT2")); %>
  <div id="DIV2">
    <table id="resultTable">
      <tbody>
        <jsp:include page="row.jsp"/>
      </tbody>
    </table>
  </div>
  <script language='javascript'>
	v[1] = document.all('DIV2').all('resultTable').outerHTML;
	v[2] = ${X_OBJECT2.id};
  </script>
</logic:present>
<script language='javascript'>
    window.parent.returnValue = v;
    window.parent.close();
</script>