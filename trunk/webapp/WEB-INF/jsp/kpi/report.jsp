<!--DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"-->
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ page import="java.util.*" %>
<%@ page import="net.sourceforge.model.admin.*" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<style>
		<!--
		.leftbar {
			BORDER-RIGHT: thin; BORDER-TOP: thin; FONT-WEIGHT: bold; FONT-SIZE: 10px; BORDER-LEFT: thin; COLOR: #ffffff; BORDER-BOTTOM: thin; FONT-FAMILY: "Verdana", "Arial", "Helvetica", "sans-serif"; TEXT-DECORATION: none
		}
		.leftbar A:visited {
			COLOR: #ffffff; TEXT-DECORATION: none
		}
		.leftbar A:hover {
			FONT-WEIGHT: bold; COLOR: #ffffff; TEXT-DECORATION: none
		}
		.leftbar A:link {
			COLOR: #ffffff; TEXT-DECORATION: none
		}
		.leftbar A:active {
			COLOR: #ffffff; TEXT-DECORATION: none
		}
		.tittle {
			FONT-WEIGHT: bold; FONT-SIZE: 18px; TEXT-TRANSFORM: none; COLOR: #000066; FONT-STYLE: normal; FONT-FAMILY: "Verdana", "Arial", "Helvetica", "sans-serif"; FONT-VARIANT: normal; TEXT-DECORATION: none
		}
		.text {
			FONT-SIZE: 10px; COLOR: #333333; LINE-HEIGHT: 15px; FONT-FAMILY: "Verdana", "Arial", "Helvetica", "sans-serif"; TEXT-DECORATION: none
		}
		.text A:visited {
			COLOR: #000066; TEXT-DECORATION: none
		}
		.text A:hover {
			FONT-WEIGHT: normal; COLOR: #000066; TEXT-DECORATION: underline
		}
		.text A:link {
			COLOR: #003399; TEXT-DECORATION: none
		}
		.text A:active {
			COLOR: #000066; TEXT-DECORATION: none
		}
		.smalltittle {
			FONT-WEIGHT: bold; FONT-SIZE: 11px; COLOR: #ffffff; FONT-FAMILY: "Verdana", "Arial", "Helvetica", "sans-serif"; TEXT-DECORATION: none
		}
		.bluetittle {
			FONT-WEIGHT: bold; FONT-SIZE: 10px; COLOR: #000066; FONT-FAMILY: "Verdana", "Arial", "Helvetica", "sans-serif"; TEXT-DECORATION: none
		}
		.footer {
			FONT-SIZE: 9px; COLOR: #666666; FONT-FAMILY: "Verdana", "Arial", "Helvetica", "sans-serif"; TEXT-DECORATION: none
		}
		.border {
			BORDER-RIGHT: #cccccc 1px; BORDER-TOP: #cccccc 1px solid; BORDER-LEFT: #cccccc 1px; BORDER-BOTTOM: #cccccc 1px
		}
		.whitetext {
			FONT-SIZE: 10px; COLOR: #ffffff; FONT-FAMILY: "Verdana", "Arial", "Helvetica", "sans-serif"; TEXT-DECORATION: none
		}
		.leftbat-s {
			BORDER-RIGHT: #cccccc 1px; BORDER-TOP: #cccccc 1px; FONT-SIZE: 10px; WORD-SPACING: normal; VERTICAL-ALIGN: baseline; BORDER-LEFT: #cccccc 1px; COLOR: #ffffff; BORDER-BOTTOM: #cccccc 1px; FONT-FAMILY: "Verdana", "Arial", "Helvetica", "sans-serif"; WHITE-SPACE: normal; LETTER-SPACING: normal; TEXT-DECORATION: none
		}
		.leftbat-s A:visited {
			COLOR: #ffffff; TEXT-DECORATION: none
		}
		.leftbat-s A:hover {
			FONT-WEIGHT: bold; COLOR: #ffffff; TEXT-DECORATION: none
		}
		.leftbat-s A:link {
			COLOR: #ffffff; TEXT-DECORATION: none
		}
		.leftbat-s A:active {
			COLOR: #ffffff; TEXT-DECORATION: none
		}
		.blacktittle {
			FONT-WEIGHT: bold; FONT-SIZE: 10px; COLOR: #000000; FONT-FAMILY: "Verdana", "Arial", "Helvetica", "sans-serif"; TEXT-DECORATION: none
		}
		.greytitlle {
			FONT-WEIGHT: bold; FONT-SIZE: 16px; COLOR: #999999; FONT-STYLE: italic; FONT-FAMILY: "Verdana", "Arial", "Helvetica", "sans-serif"; TEXT-DECORATION: none
		}
		.redtitle {
			FONT-WEIGHT: bold; FONT-SIZE: 10px; COLOR: #990066; FONT-FAMILY: "Verdana", "Arial", "Helvetica", "sans-serif"; TEXT-DECORATION: none
		}
		.redalerts {
			FONT-WEIGHT: bold; FONT-SIZE: 10px; COLOR: #ff0000; FONT-FAMILY: "Verdana", "Arial", "Helvetica", "sans-serif"; TEXT-DECORATION: none
		}
		.larger {
			FONT-WEIGHT: bold; FONT-SIZE: 10px; COLOR: #000066; FONT-FAMILY: Verdana, Arial, Helvetica, sans-serif; TEXT-DECORATION: underline
		}
		.larger A:visited {
			TEXT-DECORATION: underline
		}
		.larger A:hover {
			FONT-WEIGHT: normal; TEXT-DECORATION: underline
		}
		.larger A:link {
			TEXT-DECORATION: underline
		}
		.larger A:active {
			TEXT-DECORATION: underline
		}
		.underline {
			FONT-SIZE: 10px; COLOR: #000000; FONT-FAMILY: Verdana, Arial, Helvetica, sans-serif; TEXT-DECORATION: underline
		}
		.underline A:visited {
			TEXT-DECORATION: underline
		}
		.underline A:hover {
			FONT-WEIGHT: normal; TEXT-DECORATION: underline
		}
		.underline A:link {
			TEXT-DECORATION: underline
		}
		.underline A:active {
			TEXT-DECORATION: underline
		}
		.pc {
			FONT-WEIGHT: bold; FONT-SIZE: 11px; COLOR: #000066; FONT-FAMILY: Verdana, Arial, Helvetica, sans-serif; TEXT-DECORATION: none
		}	
		-->			
	</style>
</head>

<body>
	<form name="form1" action="kpiSummary.do" method="post" target="_self">
		<TABLE cellSpacing=1 cellPadding=3 width="90%" bgColor=#ccccff border=0>
	    	<TBODY>
	    		<TR class=text>
	    			<TD colSpan=2>
	    				<SPAN class=blacktittle>Office Automation</SPAN>
	    			</TD>
	    		</TR>
	    		<TR class=text bgColor=#ffffff>
	    			<TD width="77%">No. of sites running</TD>
	    			<TD width="23%"><%=request.getAttribute("x_RunningSiteNums")%></TD>
	    		</TR>
	    		<TR class=text bgColor=#ffffff>
	    			<TD width="77%">No. of sites testing</TD>
	    			<TD width="23%"></TD>
	    		</TR>
	    		<TR class=text bgColor=#ffffff>
	    			<TD width="77%">Ave number of users per day for last month ( 30 days ave. )</TD>
	    			<TD width="23%"><%=request.getAttribute("x_NumberOfUsers")%></TD>
	    		</TR>
	    		<TR class=text bgColor=#ffffff>
	    			<TD width="77%">Ave response time for last month</TD>
	    			<TD width="23%"><%=request.getAttribute("x_ResponseTimes")%></TD>
	    		</TR>
	    		<TR class=text bgColor=#ffffff>
	    			<TD width="77%">Total # of transactions for last week</TD>
	    			<TD width="23%"><%=request.getAttribute("x_TotalRequestCreatedLastWeek")%></TD>
	    		</TR>
	    		<TR class=text bgColor=#ffffff>
	    			<TD width="77%">Total # of transactions for last month</TD>
	    			<TD width="23%"><%=request.getAttribute("x_TotalRequestCreatedLastMonth")%></TD>
	    		</TR>
	    	</TBODY>
	    </TABLE>
	    <TABLE cellSpacing=1 cellPadding=3 width="90%" bgColor=#ccccff border=0>
	    	<TBODY>
	    		<TR class=text bgColor=#ffffcc>
					<TD class=blacktittle bgColor=#ffffcc colSpan=2>By Site </TD>
	                <TD class=blacktittle colSpan=5>                
	                	<SELECT class="text" name="selectedSiteID" onchange="document.form1.Submit();"> 
	                		<%
			                	List siteList = (List)request.getAttribute("x_SiteList");
			                	if (siteList != null && siteList.size() > 0) {
			                		Site selectedSite = (Site)request.getAttribute("x_SelectedSite");
			                		for (int i0 = 0; i0 < siteList.size(); i0++) {
			                			Site site = (Site)siteList.get(i0);
			                %>
	                		<OPTION value="<%=site.getId()%>" <%=site.equals(selectedSite) ? "selected" : ""%>><%=site.getName()%></OPTION>
	                		<%
	                				}
	                			}
	                		%>
	                	</SELECT>
	                </TD>
	            </TR>	            
	            <TR class=text bgColor=#ffffff>
	             	<TD colSpan=7>&nbsp;</TD>
	            </TR>
	            <TR class=text bgColor=#ffffff>
	             	<TD colSpan=7>Top 5 Purchase Categories:</TD>
	            </TR>
	                <TR class=text bgColor=#ffffff>
	                <%
	                	List purchaseCategoryAndNums = (List)request.getAttribute("x_Top5KPIPurchaseCategory");
	                	if (purchaseCategoryAndNums != null && purchaseCategoryAndNums.size() > 0) {
	                		for (int i0 = 0; i0 < purchaseCategoryAndNums.size() && i0 < 5; i0++) {
	                			Object[] obj = (Object[])purchaseCategoryAndNums.get(i0);
	                %>                
		                <TD colSpan=2><%=obj[0]%></TD>
		                <TD colSpan=5><%=obj[1]%></TD>
	                <%
	                		}
	                	}
	                %>
	            </TR>
	            <TR class=text bgColor=#ffffff>
	             	<TD colSpan=7>Top 5 Expense Categories:</TD>
	            </TR>
	            <TR class=text bgColor=#ffffff>
	            	<%
	                	List expenseCategoryAndNums = (List)request.getAttribute("x_Top5KPIExpenseCategory");
	                	if (expenseCategoryAndNums != null && expenseCategoryAndNums.size() > 0) {
	                		for (int i0 = 0; i0 < expenseCategoryAndNums.size() && i0 < 5; i0++) {
	                			Object[] obj = (Object[])expenseCategoryAndNums.get(i0);
	                %>                
		                <TD colSpan=2><%=obj[0]%></TD>
		                <TD colSpan=5><%=obj[1]%></TD>
	                <%
	                		}
	                	}
	                %>
	        	</TR>
			</TBODY>
	    </TABLE>
	</form>
</body>
</html>
