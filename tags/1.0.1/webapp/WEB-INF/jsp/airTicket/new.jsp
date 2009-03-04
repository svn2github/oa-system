<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>

<html:form action="/insertAirTicket">
	<table width=90% border=0 cellpadding=4 cellspacing=0>
		<tr>
			<td class="bluetext"><bean:message key="airTicket.id" />:</td>
			<td>(<bean:message key="common.id.generateBySystem" />)</td>
		</tr>
		<tr>
			<td class="bluetext"><bean:message key="airTicket.travelApplication.id" />:</td>
			<td>
				<html:select property="travelApplication_id" >
				</html:select>
			</td>
		</tr>			
		<tr>
			<td class="bluetext"><bean:message key="airTicket.leavePOItem.id" />:</td>
			<td>
				<html:select property="leavePOItem_id" >
				</html:select>
			</td>
		</tr>			
		<tr>
			<td class="bluetext"><bean:message key="airTicket.returnPOItem.id" />:</td>
			<td>
				<html:select property="returnPOItem_id" >
				</html:select>
			</td>
		</tr>			
		<tr>
			<td class="bluetext"><bean:message key="airTicket.supplier.id" />:</td>
			<td>
				<html:select property="supplier_id" >
				</html:select>
			</td>
		</tr>			
		<tr>
			<td class="bluetext"><bean:message key="airTicket.leaveFromCity.id" />:</td>
			<td>
				<html:select property="leaveFromCity_id" >
				</html:select>
			</td>
		</tr>			
		<tr>
			<td class="bluetext"><bean:message key="airTicket.leaveToCity.id" />:</td>
			<td>
				<html:select property="leaveToCity_id" >
				</html:select>
			</td>
		</tr>			
		<tr>
			<td class="bluetext"><bean:message key="airTicket.returnFromCity.id" />:</td>
			<td>
				<html:select property="returnFromCity_id" >
				</html:select>
			</td>
		</tr>			
		<tr>
			<td class="bluetext"><bean:message key="airTicket.returnToCity.id" />:</td>
			<td>
				<html:select property="returnToCity_id" >
				</html:select>
			</td>
		</tr>			
		<tr>
			<td class="bluetext"><bean:message key="airTicket.currency.code" />:</td>
			<td>
				<html:select property="currency_code" >
				</html:select>
			</td>
		</tr>			
		<tr>
			<td class="bluetext"><bean:message key="airTicket.leaveFlightNo" />:</td>
			<td><html:text property="leaveFlightNo" /></td>
		</tr>			
		<tr>
			<td class="bluetext"><bean:message key="airTicket.leaveFlightClass" />:</td>
			<td><html:text property="leaveFlightClass" /></td>
		</tr>			
		<tr>
			<td class="bluetext"><bean:message key="airTicket.leaveDepartTime" />:</td>
			<td><html:text property="leaveDepartTime" /></td>
		</tr>			
		<tr>
			<td class="bluetext"><bean:message key="airTicket.leaveArriveTime" />:</td>
			<td><html:text property="leaveArriveTime" /></td>
		</tr>			
		<tr>
			<td class="bluetext"><bean:message key="airTicket.returnFlightNo" />:</td>
			<td><html:text property="returnFlightNo" /></td>
		</tr>			
		<tr>
			<td class="bluetext"><bean:message key="airTicket.returnFlightClass" />:</td>
			<td><html:text property="returnFlightClass" /></td>
		</tr>			
		<tr>
			<td class="bluetext"><bean:message key="airTicket.returnDepartTime" />:</td>
			<td><html:text property="returnDepartTime" /></td>
		</tr>			
		<tr>
			<td class="bluetext"><bean:message key="airTicket.returnArriveTime" />:</td>
			<td><html:text property="returnArriveTime" /></td>
		</tr>			
		<tr>
			<td class="bluetext"><bean:message key="airTicket.exchangeRate" />:</td>
			<td><html:text property="exchangeRate" /></td>
		</tr>			
		<tr>
			<td class="bluetext"><bean:message key="airTicket.price" />:</td>
			<td><html:text property="price" /></td>
		</tr>			
		<tr>
			<td class="bluetext"><bean:message key="airTicket.isRecharge" />:</td>
			<td><html:text property="isRecharge" /></td>
		</tr>			
		<tr>
			<td class="bluetext"><bean:message key="airTicket.status" />:</td>
			<td><html:text property="status" /></td>
		</tr>			

		<tr>
			<td class="bluetext">Status:</td>
			<td><html:select property="enabled_enumCode">
				<c:if test="${sessionScope.LOGIN_USER.locale=='zh'}">
					<html:options collection="X_ENABLEDDISABLEDLIST"
						property="enumCode" labelProperty="chnShortDescription" />
				</c:if>
				<c:if test="${sessionScope.LOGIN_USER.locale!='zh'}">
					<html:options collection="X_ENABLEDDISABLEDLIST"
						property="enumCode" labelProperty="engShortDescription" />
				</c:if>
			</html:select></td>
		</tr>
		<tr>
			<td colspan="2" align="right"><html:submit>
				<bean:message key="all.save" />
			</html:submit></td>
		</tr>
	</table>
</html:form>
