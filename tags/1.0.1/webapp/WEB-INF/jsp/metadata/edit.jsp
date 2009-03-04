<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<script language=javascript type="text/javascript"
	src="includes/palette/color.js"></script>
<script  language=javascript>
 	function colorKeyUp(inputObj,colorTd)
 	{
 		var oTd=document.getElementById(colorTd);
 		colorString=changeColorStyleWithDefault(inputObj.value);
 		if (colorString!="")
	 		oTd.style.backgroundColor=colorString;
 	}
 	
</script>

<html:form action="/updateMetadata" method="post" >
<table width=100% border=0 cellpadding=4 cellspacing=0>
	<tr>
		<td height="30">
			<bean:message key="metadata.edit.code"/>:
		</td>
		<td>
			<html:hidden property="id"/>
			<bean:write name="metadataForm" property="id" />
		</td>
	</tr>
	
	<tr>
		<td height="30">
			<bean:message key="metadata.edit.description"/>:
		</td>
		<td>
			<html:text property="description" size="40"  maxlength="80"/>
		</td>
	</tr>
</table>
<hr align="left" width="100%">
<table>
	<tr>
		<td>
			<h3><font color="blue">Detail</font></h3>
		</td>
	</tr>
</table>
<table  width="100%" class="data">
<thead>
	<tr bgcolor="#9999ff">
		<th>
			<div align="center">
				<bean:message key="metadata.edit.detail.code"/>
			</div>
		</th>
		<th>
			<div align="center">
				<bean:message key="metadata.edit.detail.description"/>
			</div>
		</th>
		<th>
			<div align="center">
				<bean:message key="metadata.edit.detail.description.second"/>
			</div>
		</th>
		<th>
			<div align="center">
				<bean:message key="metadata.edit.detail.shortDescription"/>
			</div>
		</th>
		<th>
			<div align="center">
				<bean:message key="metadata.edit.detail.shortDescription.second"/>
			</div>
		</th>
		<th>
			<div align="center">
				<bean:message key="metadata.edit.detail.color"/>
			</div>
		</th>
	</tr>
<thead>
<tbody>
<%
	boolean oddRow = false;
	int index=0;
%>
<logic:iterate id="mdd" name="X_METADATADETAILLIST">
<%
	oddRow = !oddRow;
%>
<tr class="<%=oddRow ? "odd" : "even"%>">
  <td height="30" align="center">${mdd.enumCode}<html:hidden property="detailId" value="${mdd.enumCode}"/></td>
  <td align="center"><input name="engFull" size="20" maxLength="80" value="${mdd.engDescription}"/></td>
  <td align="center"><input name="chnFull" size="20" maxLength="40" value="${mdd.chnDescription}"/></td>
  <td align="center"><input name="engShort" size="10" maxLength="40" value="${mdd.engShortDescription}"/></td>
  <td align="center"><input name="chnShort" size="10" maxLength="20" value="${mdd.chnShortDescription}"/></td>
  <td align="center">
  	<table border="0" cellpadding="0" cellspacing="0" >
  	<tr>
  		<td>
		  	<input name="color" onKeyUp="colorKeyUp(this,'colortd${mdd.enumCode}')" style="width:60px" maxLength="20" value="${mdd.color}"/>
		</td>
		<td width="2"></td>
		<logic:empty name="mdd" property="color">
			<td id="colortd${mdd.enumCode}" width="25" onclick="event.cancelBubble=true;javascript:showPalette('colortd${mdd.enumCode}',false,'color',<%=index%>,'colortd${mdd.enumCode}',null,'metadataForm',colortd${mdd.enumCode}.style.backgroundColor)" style="cursor:hand;background-color:#000000"></td>
		</logic:empty>
		<logic:notEmpty name="mdd" property="color">
			<td id="colortd${mdd.enumCode}" width="25" onclick="event.cancelBubble=true;javascript:showPalette('colortd${mdd.enumCode}',false,'color',<%=index%>,'colortd${mdd.enumCode}',null,'metadataForm',colortd${mdd.enumCode}.style.backgroundColor)" style="cursor:hand;background-color:${mdd.color}"></td>
		</logic:notEmpty>
		
	</tr>
	</table>
  </td>
</tr>
<%index++;%>
</logic:iterate>


</tbody>
</table>

<table border="0" cellpadding="0" cellspacing="0" width="100%" >
<tr><td height="5"></td></tr>
<tr>
	<td align="right"><html:submit><bean:message key="all.save"/></html:submit>&nbsp;
	<input type="button" value="<bean:message key="all.back" />" onclick="history.back()"></td>
</tr>
</table>

</html:form>