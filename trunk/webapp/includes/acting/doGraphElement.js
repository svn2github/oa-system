// this file contains the basic method for graph-element eg.NodeCreate NodeRedraw and so on.
/*
 * @author: shennan , amushen@yahoo.com.cn
 */
 
function calcArrowPoint(sPoint,dPoint){
//determine the arrowPoints regarded by the source and the destination point 	
	var ret=new Array(2);
	var p1=new Position();
	var p2=new Position();
	var d=Math.sqrt((dPoint.top-sPoint.top)*(dPoint.top-sPoint.top)+(dPoint.left-sPoint.left)*(dPoint.left-sPoint.left));
	p1.left = dPoint.left + 10 * ((sPoint.left - dPoint.left) + (sPoint.top - dPoint.top) / 2) / d;	
	p1.top = dPoint.top + 10 * ((sPoint.top - dPoint.top) - (sPoint.left - dPoint.left) / 2) / d; 
	p2.left = dPoint.left + 10 * ((sPoint.left - dPoint.left) - (sPoint.top - dPoint.top) / 2) / d; 
	p2.top = dPoint.top + 10 * ((sPoint.top - dPoint.top) + (sPoint.left - dPoint.left) / 2) / d; 	
	ret[0]=p1;ret[1]=p2;
	return ret;
}

function getLineSDPoint(line){
// according the sourceNode and DetinationNode to determine the line's sPoint midPoint and dPoint
	if(line.source==null||line.destination==null)return;
	if(rules.length<2)return;	//if there is not at least two node , then return
	var sDom=$("rule"+line.source);
	var dDom=$("rule"+line.destination);
	var p1,p2,p3,p4;
	var d1,d2,d3,d4;
	p1=new Position(sDom.style.posTop,sDom.style.posLeft);	//top-left
	p2=new Position(p1.top,p1.left+sDom.offsetWidth);	//top-right;
	p3=new Position(p1.top+sDom.offsetHeight,p1.left);	//bottem-left;
	p4=new Position(p1.top+sDom.offsetHeight,p1.left+sDom.offsetWidth);		//bottom-right
	d1=new Position(dDom.style.posTop,dDom.style.posLeft);	//top-left
	d2=new Position(d1.top,d1.left+dDom.offsetWidth);	//top-right;
	d3=new Position(d1.top+dDom.offsetHeight,d1.left);	//bottem-left;
	d4=new Position(d1.top+dDom.offsetHeight,d1.left+dDom.offsetWidth);

	if((p1.top+sDom.offsetHeight)<d1.top){
		//source is above the destination
		line.sPoint=new Position(p3.top,(p3.left+p4.left)/2);
		line.dPoint=new Position(d1.top,(d1.left+d2.left)/2);
		line.midPoint=new Position((p3.top+d1.top)/2,(line.sPoint.left+line.dPoint.left)/2);
	}else if((d1.top+dDom.offsetHeight)<p1.top){
		//destination is above the source
		line.sPoint=new Position(p1.top,(p1.left+p2.left)/2);
		line.dPoint=new Position(d3.top,(d3.left+d4.left)/2);
		line.midPoint=new Position((d3.top+p1.top)/2,(line.sPoint.left+line.dPoint.left)/2);
	}else if(p1.left<d1.left){
		//source is at the left of the destination
		line.sPoint=new Position((p2.top+p4.top)/2,p2.left);
		line.dPoint=new Position((d1.top+d3.top)/2,d1.left);
		line.midPoint=new Position((line.sPoint.top+line.dPoint.top)/2,(line.sPoint.left+line.dPoint.left)/2);
	}else{
		//source is at the right of the destination
		line.sPoint=new Position((p1.top+p3.top)/2,p1.left);
		line.dPoint=new Position((d2.top+d4.top)/2,d2.left);
		line.midPoint=new Position((line.sPoint.top+line.dPoint.top)/2,(line.sPoint.left+line.dPoint.left)/2);		
	}
	return line;
}


function calcInOutLine(index){
//calculate the inlines and outlines of a node which index is parameter : index	
var i,inline=new Array(),outline=new Array();
for(i=0;i<lines.length;i++){
	if(lines[i]==null)continue;
	if(lines[i].source==index) outline[outline.length]=i;
	if(lines[i].destination==index) inline[inline.length]=i;
}
rules[index].inLine=inline;
rules[index].outLine=outline;
}

function _judgeNodeAndMidPos(nodeId,lineId,/* flag==1?source:destination*/flag){
	//判断节点和线中间块的位置关系
	//返回：1 左；2上；3右；4下
	var rule=$("rule"+nodeId);
	var mid=$("mid"+lineId);
	var p1,p2,p3,p4,d1,d2,d3,d4;
	p1=new Position(rule.style.posTop,rule.style.posLeft);	//top-left
	p2=new Position(p1.top,p1.left+rule.offsetWidth);	//top-right;
	p3=new Position(p1.top+rule.offsetHeight,p1.left);	//bottem-left;
	p4=new Position(p1.top+rule.offsetHeight,p1.left+rule.offsetWidth);		//bottom-right
	d1=new Position(mid.style.posTop,mid.style.posLeft);	//top-left
	d2=new Position(d1.top,d1.left+mid.offsetWidth);	//top-right;
	d3=new Position(d1.top+mid.offsetHeight,d1.left);	//bottem-left;
	d4=new Position(d1.top+mid.offsetHeight,d1.left+mid.offsetWidth);
	
	var pp=flag==1?lines[lineId].sPoint:lines[lineId].dPoint;	//the target which is to be changed
	if((p1.top+rule.offsetHeight)<d1.top){
		pp.top=p3.top;
		pp.left=(p3.left+p4.left)/2;
		return 2;
	}else if((d1.top+mid.offsetHeight)<p1.top){
		pp.top=p1.top;
		pp.left=(p1.left+p2.left)/2;
		return 4;
	}else if(p1.left<d1.left){
		pp.left=p2.left;
		pp.top=(p2.top+p4.top)/2;
		return 1;
	}else{
		pp.left=p1.left;
		pp.top=(p1.top+p3.top)/2;
		return 3;
	}	

}

function moveRelevantLines(index){
// redraw the lines that is connected on the rules[index]
	var atop=rules[index].position.top-event.top;	//the - between the new top and old top
	var aleft=rules[index].position.left-event.left;
	var i;
	for(i=0;rules[index].inLine!=null&&i<rules[index].inLine.length;i++){
		lines[rules[index].inLine[i]].dPoint.top-=atop;
		lines[rules[index].inLine[i]].dPoint.left-=aleft;
		//judge the posiiton with the midPoint
		_judgeNodeAndMidPos(index,rules[index].inLine[i],2);
		
		reDrawLine(rules[index].inLine[i]);
	}
	for(i=0;rules[index].outLine!=null&&i<rules[index].outLine.length;i++){
		lines[rules[index].outLine[i]].sPoint.top-=atop;
		lines[rules[index].outLine[i]].sPoint.left-=aleft;
		//judge the posiiton with the midPoint
		_judgeNodeAndMidPos(index,rules[index].outLine[i],1);

		reDrawLine(rules[index].outLine[i]);
	}
	//update new position
	rules[index].position.top=event.top;
	rules[index].position.left=event.left;

}

function reDrawLine(index){
// for some reasons , the line's position is changed,so we must redraw the line	
	//showHip('redrawline'+index);
	var pp=calcArrowPoint(lines[index].midPoint,lines[index].dPoint);
	var line=lines[index];
	//var cmd="line"+index+".points.value='"+lines[index].sPoint.left+","+lines[index].sPoint.top+","+lines[index].midPoint.left+","+lines[index].midPoint.top+","+lines[index].dPoint.left+","+lines[index].dPoint.top+","+pp[0].left+","+pp[0].top+","+lines[index].dPoint.left+","+lines[index].dPoint.top+","+pp[1].left+","+pp[1].top+"'";
	var old=$("line"+line.index);
	old.points.value="'"+lines[index].sPoint.left+","+lines[index].sPoint.top+","+lines[index].midPoint.left+","+lines[index].midPoint.top+","+lines[index].dPoint.left+","+lines[index].dPoint.top+","+pp[0].left+","+pp[0].top+","+lines[index].dPoint.left+","+lines[index].dPoint.top+","+pp[1].left+","+pp[1].top+"'";
//	var html="<v:polyline id='line"+line.index+"' style='position:absolute;top:0px;left:0px;Z-INDEX:1;' points='"+line.sPoint.left+","+line.sPoint.top+","+line.midPoint.left+","+line.midPoint.top+","+line.dPoint.left+","+line.dPoint.top+","+pp[0].left+","+pp[0].top+","+line.dPoint.left+","+line.dPoint.top+","+pp[1].left+","+pp[1].top+"' filled='f' />";
	
//	var newLine=document.createElement(html);
//	newLine.id="line"+index;
//	newLine.style.position="absolute";
//	newLine.style.top=0;
//	newLine.style.left=0;
//	newLine.points.value="'"+lines[index].sPoint.left+","+lines[index].sPoint.top+","+lines[index].midPoint.left+","+lines[index].midPoint.top+","+lines[index].dPoint.left+","+lines[index].dPoint.top+","+pp[0].left+","+pp[0].top+","+lines[index].dPoint.left+","+lines[index].dPoint.top+","+pp[1].left+","+pp[1].top+"'";
//	newLine.filled="f";
//	container.removeChild(old);
//	container.appendChild(newLine);
}
function reDrawLineMid(index){
//move the midPoint so we must redraw line
	var left,top;
	top=event.top+3;
	left=event.left+3;
	lines[index].midPoint.left=left;
	lines[index].midPoint.top=top;
	//judge the position with the sPoint and dPoint
	_judgeNodeAndMidPos(lines[index].source,index,1);
	_judgeNodeAndMidPos(lines[index].destination,index,2);
	var line=lines[index];
	var pp=calcArrowPoint(lines[index].midPoint,lines[index].dPoint);
	var old=$("line"+line.index);
	old.points.value="'"+lines[index].sPoint.left+","+lines[index].sPoint.top+","+lines[index].midPoint.left+","+lines[index].midPoint.top+","+lines[index].dPoint.left+","+lines[index].dPoint.top+","+pp[0].left+","+pp[0].top+","+lines[index].dPoint.left+","+lines[index].dPoint.top+","+pp[1].left+","+pp[1].top+"'";
	cmd="lineText"+index+".style.top='"+(lines[index].midPoint.top-6)+"px';";
	eval(cmd);
	cmd="lineText"+index+".style.left='"+(lines[index].midPoint.left+10)+"px';";
	eval(cmd);
}

////////////////crete the HtmlObject
function _getNodeClass(type){
	//select the different style accroding to the type
	var rst="rule";
	switch(type){
		case 1:
			rst="beginNode";
			break;
		case 2:
			rst="generalNode";
			break;
		case 3:
			rst="forkNode";
			break;
		case 4:
			rst="joinNode";
			break;		
		case 99:
			rst="endNode";
			break;
	}
	return rst;
}

function createNode(rule){
	var html="";
	var clazz=_getNodeClass(rule.type);
	html="<div id='rule"+rule.index+"' onBeginDrag='calcInOutLine("+rule.index+");' ondblclick='doubleClickThisNode("+rule.index+");' onSelected='selectThisNode("+rule.index+");' onDraging='moveRelevantLines("+rule.index+")' class='"+clazz+"' style='top:"+rule.position.top+";left:"+rule.position.left+";z-index:"+(currentIndex++)+"'><table border=0 width=100% height=100%><tr><td align=center valign=middle style='font-size:12px' nowrap>"+rule.description+"</td></tr></table></div>";
	var nc=document.createElement("div");
	nc.id="nc"+rule.index;
	document.body.appendChild(nc);
	nc.innerHTML+=html;
}

function canDragOfNode(index){
	if(index!=null){
		eval("$('rule"+index+"').style.behavior='url(includes/htc/sndrag.htc)';");
		return;
	}
	var i;
	for(i=0;i<rules.length;i++)
		eval("$('rule"+i+"').style.behavior='url(includes/htc/sndrag.htc)';");
}

function canDragOfLine(index){
	if(index!=null){
		eval("$('mid"+index+"').style.behavior='url(includes/htc/sndrag.htc)';");
		return;
	}
	var i;
	for(i=0;i<rules.length;i++)
		eval("$('mid"+i+"').style.behavior='url(includes/htc/sndrag.htc)';");
}

function createLine(line){
	var html;
	var pp=calcArrowPoint(line.midPoint,line.dPoint);	
	html="<v:polyline id='line"+line.index+"' style='position:absolute;top:0px;left:0px;Z-INDEX:"+(currentIndex)+";' points='"+line.sPoint.left+","+line.sPoint.top+","+line.midPoint.left+","+line.midPoint.top+","+line.dPoint.left+","+line.dPoint.top+","+pp[0].left+","+pp[0].top+","+line.dPoint.left+","+line.dPoint.top+","+pp[1].left+","+pp[1].top+"' filled='f' />";
	html+="<img id='mid"+line.index+"' ondblclick='doubleClickThisLine("+line.index+")' onselected='selectThisLine("+line.index+")' onDraging='reDrawLineMid("+line.index+")' class='mid' style='Z-INDEX:"+(currentIndex)+";top:"+(line.midPoint.top-3)+";left:"+(line.midPoint.left-3)+"' />";
	html+="<div id='lineText"+line.index+"' style='top:"+(line.midPoint.top-6)+";left:"+(line.midPoint.left+10)+";z-index:"+(currentIndex++)+"' class='linetext' >"+line.name+"</div>";
	var lc=document.createElement("div");
	lc.id="lc"+line.index;
	document.body.appendChild(lc);
	lc.innerHTML+=html;
}

function setNodeName(index){
	if(rules[index]==null)return;
	$('rule'+index).children[0].rows[0].cells[0].innerHTML=rules[index].description;
}
function setLineName(index){
	if(lines[index]==null)return;
	$('lineText'+index).innerHTML=lines[index].name;
}