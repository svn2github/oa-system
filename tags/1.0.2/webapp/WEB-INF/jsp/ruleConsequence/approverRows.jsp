<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<logic:iterate id="X_OBJECT" name="X_OBJECTS">
  <bean:define id="X_OBJECT" toScope="request" name="X_OBJECT"/>
  <jsp:include page="approverRow.jsp"/>
</logic:iterate>
