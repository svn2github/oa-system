<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<script language="javascript" src="includes/selection.js"></script>
<script type="text/javascript">
<!--
	function doFilter(form) {
		var selAvailable = form.available;
		var selFilted = form.filted;
		var patten = form.patten.value;
		if (trim(patten).length == 0) {
			//transferAllOption(selFilted, selAvailable);
			for (var i = 0; i < selAvailable.options.length; i++) {
				var o = selAvailable.options[i];
				o.style.color = "";
			}
		} else {
			//transferAllOption(selAvailable, selFilted);
			//selectOptions(selFilted, patten);
			//transferOption(selFilted, selAvailable);
			for (var i = 0; i < selAvailable.options.length; i++) {
				var o = selAvailable.options[i];
				o.style.color = o.text.indexOf(patten) != -1 ? "red" : "";
			}
		}
	}
//-->
</script>
<table border="0" cellpadding="4" cellspacing="0">
  <tbody>
    <tr>
      <td class="bluetext"><bean:message key="role.id"/>:</td>
      <td>${X_OBJECT.id}</td>
    </tr>
    <tr>
      <td class="bluetext"><bean:message key="role.name"/>:</td>
      <td>${X_OBJECT.name}</td>
    </tr>
    <tr>
      <td class="bluetext"><bean:message key="role.description"/>:</td>
      <td>${X_OBJECT.description}</td>
    </tr>
    <tr>
      <td class="bluetext"><bean:message key="role.type"/>:</td>
      <td>
        <span style="color:${X_OBJECT.type.color}">
          <c:if test="${sessionScope.LOGIN_USER.locale=='en'}">${X_OBJECT.type.engShortDescription}</c:if>
          <c:if test="${sessionScope.LOGIN_USER.locale!='en'}">${X_OBJECT.type.chnShortDescription}</c:if>
        </span>
      </td>
    </tr>
  </tbody>
</table>
<hr/>
<form action="grantRoleFunction.do" method="post">
<input type="hidden" name="id" value="${X_OBJECT.id}"/>
<table width="100%" border="0" cellpadding="4" cellspacing="0">
  <tbody>
    <tr>
      <td align="center"><bean:message key="role.function.available"/></td>
      <td></td>
      <td align="center"><bean:message key="role.function.granted"/></td>
    </tr>
    <tr>
      <td align="center">
        <select name="available" size="15" multiple style="width:300px" ondblclick="transferOption(this, this.form.granted);">
          <logic:iterate id="f" name="X_AVAILABLEFUNCTION">
          <option value="${f.id}">${f.name}</option>
          </logic:iterate>
        </select>
        <select name="filted" size="15" multiple style="display:none"></select>
      </td>
      <td align="center" valign="middle">
        <input type="button" value="<bean:message key="all.moveLeft"/>" style="width:60px" onclick="transferOption(this.form.available, this.form.granted);"><br>
        <input type="button" value="<bean:message key="all.moveAllLeft"/>" style="width:60px" onclick="transferAllOption(this.form.available, this.form.granted);"><br>
        <input type="button" value="<bean:message key="all.moveAllRight"/>" style="width:60px" onclick="transferAllOption(this.form.granted, this.form.available);"><br>
        <input type="button" value="<bean:message key="all.moveRight"/>" style="width:60px" onclick="transferOption(this.form.granted, this.form.available);">
      </td>
      <td align="center">
        <select name="granted" size="15" multiple style="width:300px" ondblclick="transferOption(this, this.form.available);">
          <logic:iterate id="f" name="X_GRANTEDFUNCTION">
          <option value="${f.id}">${f.name}</option>
          </logic:iterate>
        </select>
      </td>
    </tr>
    <tr>
      <td align="center"><input type="text" name="patten" size="10"/><input type="button" value="Highlight" onclick="doFilter(this.form);"/></td>
      <td align="center">
        <input type="button" value="<bean:message key="all.save"/>" onclick="selectAllOption(this.form.granted); this.form.submit();"/>
        <input type="button" value="<bean:message key="all.back"/>" onclick="window.location.href='listRole.do'"/>
      </td>
      <td></td>
    </tr>
  </tbody>
</table>
</form>
