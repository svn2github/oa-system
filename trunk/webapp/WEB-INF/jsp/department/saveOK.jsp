<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<script language='javascript'>
	var r = [];
	r['id'] = ${X_OBJECT.id};
	r['name'] = "${X_OBJECT.name}";
	<logic:present name="X_OBJECT" property="parentDepartment">
	r['parentId'] = ${X_OBJECT.parentDepartment.id};
	</logic:present>
    window.parent.returnValue = r;
    window.parent.close();
</script>