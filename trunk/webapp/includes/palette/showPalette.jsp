<%@page contentType="text/html;charset=utf-8" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML><HEAD>
<META http-equiv=Content-Type content="text/html; charset=utf-8">
<script language=javascript type="text/javascript"
	src="colorTable.js"></script>
<SCRIPT language=javascript>
<!--//
var str='',i,j,yy,mm,openbound,callback;
var fld1,fld2;
var initColor;
var wp=window.parent;
var cf=wp.document.getElementById("ColorFrame");
var fld;

var ColorHex=new Array('00','33','66','99','CC','FF')
var SpColorHex=new Array('FF0000','00FF00','0000FF','FFFF00','00FFFF','FF00FF')
var current=null

function intocolor()
{
	var colorTable=''
	for (i=0;i<2;i++)
	 {
	  for (j=0;j<6;j++)
	   {
	    colorTable=colorTable+'<tr height=12>'
	    //colorTable=colorTable+'<td width=11 style="background-color:#000000">'
	    
	    if (i==0){
	    colorTable=colorTable+'<td width=11 style="background-color:#'+ColorHex[j]+ColorHex[j]+ColorHex[j]+'">'} 
	    else{
	    colorTable=colorTable+'<td width=11 style="background-color:#'+SpColorHex[j]+'">'} 
	
	    
	    //colorTable=colorTable+'<td width=11 style="background-color:#000000">'
	    for (k=0;k<3;k++)
	     {
	       for (l=0;l<6;l++)
	       {
	        colorTable=colorTable+'<td width=11 style="background-color:#'+ColorHex[k+i*3]+ColorHex[l]+ColorHex[j]+'">'
	       }
	     }
	     colorTable=colorTable+'</tr>';
	  }
	}
	colorTable='<table width=209 border="0" cellspacing="0" cellpadding="0" style="border:1px #000000 solid;border-bottom:none;border-collapse: collapse" bordercolor="000000">'
	           +'<tr height=30><td bgcolor=#cccccc>'
	           +'<table cellpadding="0" cellspacing="1" border="0" style="border-collapse: collapse">'
	           +'<tr><td width="3"></td><td><input type="text"  name="DisColor" size="6" disabled style="border:solid 1px #000000;background-color:#ffff00"></td>'
	           +'<td width="3"></td><td><input type="text" onKeyUp="HexColorKeyUp(this);" name="HexColor" size="7" style="border:inset 1px;font-family:Arial;" value="#000000"></td></tr></table></td></tr></table>'
	           +'<table width=209 border="1" cellspacing="0" cellpadding="0" style="border-collapse: collapse" bordercolor="000000" onmouseover="doOver()" onmouseout="doOut()" onclick="doclick()" style="cursor:hand;">'
	           +colorTable+'</table>';          
	colorpanel.innerHTML=colorTable;
	resizePalette();
}

function doOver() {
      if ((event.srcElement.tagName=="TD") && (current!=event.srcElement)) {
        if (current!=null){current.style.backgroundColor = current._background}     
        event.srcElement._background = event.srcElement.style.backgroundColor
        DisColor.style.backgroundColor = event.srcElement.style.backgroundColor
        HexColor.value = event.srcElement.style.backgroundColor.toUpperCase();
        event.srcElement.style.backgroundColor = "white"
        current = event.srcElement
      }
}

function doOut() {

    if (current!=null) current.style.backgroundColor = current._background
}

function doclick(){
	if (event.srcElement.tagName=="TD"){
		wp.hidePalette();
		if(callback&&callback.length>0){eval("wp."+callback+"(\""+event.srcElement._background+"\")");}
		else{
			fld1.value=event.srcElement._background.toUpperCase();
			fld2.style.backgroundColor=event.srcElement._background;
		}
	}
}

function HexColorKeyUp(inputObj)
{
	var oDis=document.getElementById("DisColor");
	colorString=changeColorStyleWithDefault(inputObj.value);
	if (colorString!="")
		oDis.style.backgroundColor=colorString;
	if (event.keyCode==13) {
		wp.hidePalette();
		if(callback&&callback.length>0){eval("wp."+callback+"(\""+oDis.style.backgroundColor+"\")");}
		else{
			fld1.value=oDis.style.backgroundColor.toUpperCase();
			fld2.style.backgroundColor=oDis.style.backgroundColor.toUpperCase();
		}
	}
}


function resizePalette(){cf.width=209;cf.height=177;}

//-->
</SCRIPT>

</HEAD>
<body onload="intocolor()" bottomMargin=0 bgColor=red leftMargin=0 topMargin=0 rightMargin=0>
<div id="colorpanel" style="position: absolute;">
</div>
<SCRIPT language=javascript>
<!--//
var bCalLoaded=true;
//-->
</SCRIPT>
</BODY></HTML>
