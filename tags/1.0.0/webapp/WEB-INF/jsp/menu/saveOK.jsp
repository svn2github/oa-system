<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<script language='javascript'>
	var r = [];
	r['id'] = ${X_OBJECT.id};
	<c:choose>
		<c:when test="${sessionScope.LOGIN_USER.locale=='en'}">
	r['name'] = "${X_OBJECT.name}";
		</c:when>
		<c:otherwise>
	r['name'] = "${X_OBJECT.secondName}";
		</c:otherwise>
	</c:choose>
	<c:if test="${X_OBJECT.parentMenu!=null}">
	r['parentId'] = ${X_OBJECT.parentMenu.id};
	</c:if>
    window.parent.returnValue = r;
    window.parent.close();
</script>