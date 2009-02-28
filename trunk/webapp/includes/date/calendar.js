document.write('<iframe id=CalFrame name=CalFrame frameborder=0 src="includes/date/showCalendar.jsp" style=display:none;position:absolute;z-index:100></iframe>');
var cal_old_onclick = document.onclick;
document.onclick = function() {
	if (cal_old_onclick) cal_old_onclick();
	hideCalendar();
}

function showCalendar(sImg, bOpenBound, sFld1, sFld2, sCallback, sForm) {
	var fld1,fld2;
	var cf=document.getElementById("CalFrame");
	var wcf=window.frames.CalFrame;
	var oImg=document.getElementById(sImg);
	if(!oImg){alert("Cannot find command control, you said its name was '" + sImg + "'.");return;}
	if(!sFld1){alert("Please specify the input control name, it's the third parameter of showCalendar method.");return;}
	fld1=document.forms[sForm][sFld1];
	if(!fld1){alert("Cannot find input control, you said its name was '" + sFld1 + "' and on form '" + sForm + "'.");return;}
	if(fld1.tagName!="INPUT"||fld1.type!="text"){alert("Type of input control is not supported, textbox is required.");return;}
	if(sFld2) {
		fld2=document.getElementById(sFld2);
		if(!fld2){alert("Cannot find reference control, you said its name was '" + sFld2 + "'.");return;}
		if(fld2.tagName!="INPUT"||fld2.type!="text"){alert("Type of reference control is not supported, textbox is required.");return;}
	}
	if(cf.style.display=="block"){cf.style.display="none";return;}
	
	var eT=0,eL=0,p=oImg;
	var sT=document.body.scrollTop,sL=document.body.scrollLeft;
	var eH=oImg.height,eW=oImg.width;
	while(p&&p.tagName!="BODY"){eT+=p.offsetTop;eL+=p.offsetLeft;p=p.offsetParent;}
	cf.style.top=(document.body.clientHeight-(eT-sT)-eH>=cf.height)?eT+eH:eT-cf.height;
	cf.style.left=(document.body.clientWidth-(eL-sL)>=cf.width)?eL:eL+eW-cf.width;
	cf.style.display="block";
	
	wcf.openbound=bOpenBound;
	wcf.fld1=fld1;
	wcf.fld2=fld2;
	wcf.callback=sCallback;
	wcf.initCalendar();
}

function hideCalendar() {
	var cf=document.getElementById("CalFrame");
	cf.style.display="none";
}