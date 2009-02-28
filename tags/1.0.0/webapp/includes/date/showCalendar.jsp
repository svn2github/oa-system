<%@page contentType="text/html;charset=utf-8" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML><HEAD>
<META http-equiv=Content-Type content="text/html; charset=utf-8">
<STYLE type=text/css>TD {
	FONT-SIZE: 12px; FONT-FAMILY: arial; TEXT-ALIGN: center
}
TD.dt {
	FONT-SIZE: 11px; FONT-FAMILY: arial; TEXT-ALIGN: center
}
A {
	COLOR: blue;
	text-decoration: none
}
A:hover {
	COLOR: red;
	text-decoration: none
}
A.bt {
	COLOR: #888888;
	text-decoration: none
}
</STYLE>

<SCRIPT language=javascript>
<!--//
var str='',i,j,yy,mm,openbound,callback;
var fld1,fld2;
var wp=window.parent;
var cf=wp.document.getElementById("CalFrame");
var fld,curday,today=new Date();
today.setHours(0);today.setMinutes(0);today.setSeconds(0);today.setMilliseconds(0);
//var lastyear=today.getYear(),lastmonth=today.getMonth();
var todayStr=today.getYear()+"<bean:message key="date.year"/>"+(today.getMonth()+1)+"<bean:message key="date.month"/>"+today.getDate()+"<bean:message key="date.day"/>";
function parseDate(s)
{
	var reg=new RegExp("[^0-9/]","")
	if(s.search(reg)>=0)return today;
	var ss=s.split("/");
	if(ss.length!=3)return today;
	if(isNaN(ss[0])||isNaN(ss[1])||isNaN(ss[2]))return today;
	return new Date(parseFloat(ss[0]),parseFloat(ss[1])-1,parseFloat(ss[2]));
}
function resizeCalendar(){cf.width=158;cf.height=192;}
function initCalendar()
{
	if(fld1&&fld1.value.length>0){curday=parseDate(fld1.value);}
	else if(fld2&&fld2.value.length>0){curday=parseDate(fld2.value);}
	else curday=today;
	drawCalendar(curday.getFullYear(),curday.getMonth());
}
function drawCalendar(y,m)
{
	var x=new Date(y,m,1),mv=x.getDay(),d=x.getDate(),de;
	yy=x.getFullYear();mm=x.getMonth();
	document.getElementById("yyyymm").innerHTML=yy+"."+(mm+1>9?mm+1:"0"+(mm+1));
	for(var i=1;i<=mv;i++)
	{
		de=document.getElementById("d"+i);
		de.innerHTML="";
		de.bgColor="";
	}
	while(x.getMonth()==mm)
	{
		de=document.getElementById("d"+(d+mv));
		if(x.getTime()==curday.getTime())
			de.bgColor="#dddddd";
		else
			de.bgColor="white";
		if(x.getTime()==today.getTime())
			de.innerHTML="<a href=javascript:setDate("+d+");><font color=red>"+d+"</font></a>";
		else 
			de.innerHTML="<a href=javascript:setDate("+d+");>"+d+"</a>";
		x.setDate(++d);
	}
	while(d+mv<=42)
	{
		de=document.getElementById("d"+(d+mv));
		de.innerHTML="";
		de.bgColor="";
		d++;
	}
}
function setDate(d)
{
	wp.hideCalendar();
	var dstr=yy+"/"+((mm+1)<10?"0"+(mm+1):(mm+1))+"/"+(d<10?"0"+d:d);
	if(callback&&callback.length>0){eval("wp."+callback+"(\""+dstr+"\")");}
	else{fld1.value=dstr;}
}
//-->
</SCRIPT>

<META content="MSHTML 6.00.2800.1400" name=GENERATOR></HEAD>
<BODY bottomMargin=0 bgColor=red leftMargin=0 topMargin=0 
onload=resizeCalendar(); rightMargin=0>
<TABLE id=tbl0 cellSpacing=0 cellPadding=1 bgColor=#336699 border=0>
  <TBODY>
  <TR>
    <TD>
      <TABLE cellSpacing=1 cellPadding=2 width="100%" bgColor=white border=0>
        <TBODY>
        <TR bgColor=gray>
          <TD id=prevYear width=16><A 
            href="javascript:drawCalendar(yy-1,mm);"><IMG height=16 
            src="prev.gif" width=16 border=0></A></TD>
          <TD id=nextYear width=16><A 
            href="javascript:drawCalendar(yy+1,mm);"><IMG height=16 
            src="next.gif" width=16 
          border=0></A></TD>
          <TD id=yyyymm style="FONT-SIZE: 11px; COLOR: white" width="99%"></TD>
          <TD id=prev width=16><A 
            href="javascript:drawCalendar(yy,mm-1);"><IMG height=16 
            src="prev.gif" width=16 border=0></A></TD>
          <TD id=next width=16><A 
            href="javascript:drawCalendar(yy,mm+1);"><IMG height=16 
            src="next.gif" width=16 
      border=0></A></TD></TR></TBODY></TABLE>
      <TABLE cellSpacing=2 cellPadding=0 width=156 bgColor=white border=0>
        <TBODY>
        <TR height=18>
          <TD width=20><FONT color=red><bean:message key="date.7"/></FONT></TD>
          <TD width=20><bean:message key="date.1"/></TD>
          <TD width=20><bean:message key="date.2"/></TD>
          <TD width=20><bean:message key="date.3"/></TD>
          <TD width=20><bean:message key="date.4"/></TD>
          <TD width=20><bean:message key="date.5"/></TD>
          <TD width=20><FONT color=green><bean:message key="date.6"/></FONT></TD></TR>
        <TR height=1>
          <TD bgColor=gray colSpan=7></TD></TR>
        <SCRIPT language=javascript>
<!--//
for(i=0;i<6;i++)
{
	str+="<tr height=18>";
	for(j=1;j<=7;j++)str+="<td id=d"+(i*7+j)+" class=dt></td>";
	str+="</tr>";
}
document.write(str);
//-->
</SCRIPT>

        <TR height=1>
          <TD bgColor=gray colSpan=7></TD></TR>
<!--        <TR height=18>
          <TD colSpan=7><A 

          href="javascript:wp.hideCalendar();">Close</A></TD></TR>-->
          <TR height=18>
          <TD colSpan=7><font color="red"><bean:message key="date.today"/></font><bean:message key="date.is"/><SCRIPT language=javascript>document.write(todayStr);</script></TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE>
<SCRIPT language=javascript>
<!--//
var bCalLoaded=true;
//-->
</SCRIPT>
</BODY></HTML>
