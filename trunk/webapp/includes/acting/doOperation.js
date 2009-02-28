// some methods of designing the graph
/*
 * @author: shennan , amushen@yahoo.com.cn
 */
////////////////////////////////////the panel function
function setPanelPosition(){
	$('panel1').style.left=event.left;
	$('panel1').style.top=event.top;
}
function setPanelScroll(){
	$('panel1').style.top=parseInt(document.body.scrollTop);
	$('panel1').style.left=parseInt(document.body.scrollLeft) + 10;
	$('panelDraghandle').style.top=parseInt(document.body.scrollTop);
	$('panelDraghandle').style.left=parseInt(document.body.scrollLeft) + 10;
	$('main1').style.top=parseInt(document.body.scrollTop);
	$('main1').style.left=parseInt(document.body.scrollLeft) + 110;
	
	//set the position of propertiesPane
	initPropPos();
	
}
function hideOrShowPane(){
	var v=$('panel1').style.visibility;
	if(v=="visible")v="hidden";
	else v="visible";
	$('panel1').style.visibility=v;
	$('main1').style.visibility=v;
}

/////////////////////////////////
function beginCreateNode(type){
	selectState=type;
	//showHip("请点击画图区域以确定任务位置");	
	showHip("Please click the screen to put down the node.");
	event.cancelBubble=true;
	document.body.attachEvent("onclick",readyForCreateNode);
}

function beginCreateLine(){
	selectState=100;
	//showHip("请选择源任务节点");
	showHip("Please select source node.");
	event.cancelBubble=true;
}

function readyForCreateNode(){
	if(selectState<1||selectState>99)return;
	if (selectState != 1 && rules.length == 0) {
		//showHip("必须先创建一个开始任务，已自动将任务改为开始任务");
		showHip("Start node must be created first, auto changed normal node to start node.");
		selectState = 1;
	}
	if (selectState == 1) {
		if (!checkStartNode()) {
			//showHip("只能创建一个开始任务，已自动将任务改为普通任务");
			showHip("Only one start node can be created, auto changed start node to normal node.");
			selectState = 2;
		}
	}	
	var top=event.y+document.body.scrollTop;
	var left=event.x+document.body.scrollLeft;
	var rule=new Rule();
	rule.index=rules.length;
	rule.ruleType=document.getElementById("ruleType").value;
	rule.enabled=0;
	//rule.description="未命名任务"+rule.index;
	rule.description="Undefined Node"+rule.index;
	rule.position=new Position(top,left);	
	rule.type=selectState;
	rules[rule.index]=rule;	
	createNode(rule);
	canDragOfNode(rule.index);
	selectState=0;	
	//showHip("创建任务完成");
	showHip("Created node completely.");
	document.body.detachEvent("onclick",readyForCreateNode);
}

function initNode(top, left, index, ruleType, enabled, description, type, conditions, consequences) {
	var top=top;
	var left=left;
	var rule=new Rule();
	rule.index=index;
	rule.ruleType=ruleType;
	rule.enabled=enabled;
	rule.description=description;
	rule.position=new Position(top,left);	
	rule.type=type;
	rule.conditions=conditions;
	rule.consequences=consequences;
	rules[rule.index]=rule;	
	createNode(rule);
	canDragOfNode(rule.index);
}
	
function checkStartNode() {
	for(i=0;i<rules.length;i++) {
		if (rules[i] != null && rules[i].type == 1) {
			return false;
		}
	}
	return true;
}

function readyForCreateLine(){
	if(selectedOne==selectedTwo){
		//showHip("请选择不同的任务");
		showHip("Please select another node.");
		return;
	}
	var line=new Line();
	line.source=selectedTwo;
	line.destination=selectedOne;	
	line.index=lines.length;
	line.name="";
	line=getLineSDPoint(line);
	lines[line.index]=line;
	createLine(line);
	canDragOfLine(line.index);
	selectThisLine(line.index);
	showDecisionPane(line);
	//showHip("创建连线完成");
	showHip("Created line completely");
}

function initLine(index, name, value, source, destination) {
	var line=new Line();
	line.source=source;
	line.destination=destination;	
	line.index=index;
	line.name=name;
	line.value=value;
	line=getLineSDPoint(line);
	lines[line.index]=line;
	createLine(line);
	canDragOfLine(line.index);
}

function _deleteLine(index){
	//delete a Line
	if(lines[index]==null)return;
	
	unSetRuleLine(index);
	
	lines[index]=null;
	var lc=$('lc'+index);	
	try{
		lc.innerHTML="";
		document.body.removeChild(lc);	
	}catch(e){}
}
function _deleteNode(index){
	//delete a node;
	//first delete relavent line		
		if(rules[index]!=null && rules[index].type==1) {
			//showHip("开始任务不能删除");
			showHip("Start node can not be deleted");
			return;
		}
		if(rules[index]==null)return;
		//calcInOutLine(index);
		var passLine=rules[index].passLine;
		_deleteLine(passLine);
		var failLine=rules[index].failLine;
		_deleteLine(failLine);
		//second delete node		
		rules[index]=null;
		var nc=$('nc'+index);
		nc.innerHTML="";
		try{
			document.body.removeChild(nc);
		}catch(e){}
}

function _deleteGraphElement(){
	if(selectedLine!=null){
		_deleteLine(selectedLine);
		selectedLine=null;
	}else if(selectedOne!=null){		
		_deleteNode(selectedOne);
		selectedOne=null;
		selectedTwo=null;
	}else if(selectedArray!=null&&selectedArray.length>0){
		for(var i=0;i<selectedArray.length;i++)
			if(selectedArray[i].type==1)
				_deleteNode(selectedArray[i].index);
			else if(selectedArray[i].type==2)
				_deleteLine(selectedArray[i].index);
		
	}
}

function deleteGraphElement(){
	//if(!confirm("确定要删除么?"))return;
	if(!confirm("Do you want delete?"))return;
	_deleteGraphElement();
}

////区域选择功能
function beginSelectRange(){
	//开始选择一个区域
	_detachEventWhenRange();
	document.body.attachEvent("onmousedown",beginRange);
	document.body.attachEvent("onmouseup",endRange);
	document.body.attachEvent("onmousemove",createRange);
	beginPosition=null;	
	_lockAll();
	//showHip("请选择区域");
	$('rangeBt').style.border="solid 2px red";
	$('arrowbt').style.border="solid 1px black";
	document.body.style.cursor="url(includes/image/icon_arrow1.gif)";
}

function _detachEventWhenRange(){
	try{
		document.body.detachEvent("onmousedown",beginRange);
		document.body.detachEvent("onmouseup",endRange);
		document.body.detachEvent("onmousemove",createRange);
	}catch(e){}
	
}

function beginRange(){
	_clearSelected();
	beginPosition=new Position(event.y+document.body.scrollTop,event.x+document.body.scrollLeft);
	var img=document.createElement("img");
	img.id="range1";
	img.src="includes/image/blank.gif";
	img.style.position="absolute";
	img.style.top=beginPosition.top;
	img.style.left=beginPosition.left;
	img.style.width="0px";
	img.style.height="0px";
	img.style.zIndex="9999";
	img.style.border="solid 1px #000000";
	img.style.backgroundColor="#9999ff";
	img.style.filter="Alpha(Opacity=50)";
	document.body.appendChild(img);
}
function endRange(){
	_detachEventWhenRange();
	try{
		document.body.removeChild($('range1'));
	}catch(e){}
	beginPosition=null;
	// set the selectedObject into Array
	selectedArray=new Array();
	var i,obj;
	for(i=0;i<rules.length;i++)
		if(rules[i]!=null){
			if($('rule'+i).style.borderColor=="red"){
				obj=new Object();
				obj.type=1;
				obj.index=i;
				selectedArray[selectedArray.length]=obj;
				calcInOutLine(i);
				//showHip("1+"+i);
			}
		}
	for(i=0;i<lines.length;i++)
		if(lines[i]!=null){
			if($('mid'+i).style.borderColor=="red"){
				obj=new Object();
				obj.type=2;
				obj.index=i;
				selectedArray[selectedArray.length]=obj;
				//showHip("2+"+i);
			}	
		}
	//showHip(selectedArray.length);
	beginSelectRange();
}

function showRangeBt(){
	//hiden the button of arrow ,and display rangeBt
	$('arrowBt').style.border="solid 2px red";
	$('rangeBt').style.border="solid 1px black";
	document.body.style.cursor="auto";
	
}

function _judgeObjInRange(objPos,rangePos,rangeWidth,rangeHeight){
	if(objPos.top>=rangePos.top&&objPos.top<=rangePos.top+rangeHeight&&
	   objPos.left>=rangePos.left&&objPos.left<=rangePos.left+rangeWidth)
		return true;
	return false;	
}

function _clearSelected(){
//clear all the selected object
	selectedLine=null;
	selectedOne=null;
	selectedTwo=null;
	for(var i=0;i<rules.length;i++)
		if(rules[i]!=null){
			$('rule'+i).style.border="solid 1px black";	
		}
	for(var i=0;i<lines.length;i++)
		if(lines[i]!=null){
			$('mid'+i).style.border="solid 1px black";	
		}
}

function _clearSelectedArray(){
	selectedArray=null;	
	for(var i=0;i<rules.length;i++)
		if(rules[i]!=null){
			$('rule'+i).style.border="solid 1px black";	
		}
	for(var i=0;i<lines.length;i++)
		if(lines[i]!=null){
			$('mid'+i).style.border="solid 1px black";	
		}
}

function createRange(){
	if(beginPosition==null)return;
	// change the range at time
	var pos=new Position(event.y+document.body.scrollTop,event.x+document.body.scrollLeft);
	var _w=Math.abs(beginPosition.left-pos.left);
	var _h=Math.abs(beginPosition.top-pos.top);
	var rng=$('range1');
	var _t,_l;
	if(beginPosition.top>pos.top)_t=pos.top;
	else _t=beginPosition.top;
	if(beginPosition.left>pos.left)_l=pos.left;
	else _l=beginPosition.left;
	rng.style.top=_t+"px";
	rng.style.left=_l+"px";
	rng.style.width=_w+"px";
	rng.style.height=_h+"px";
	var rangePos=new Position(_t,_l);
	//select the objects when which are in the range	
	//TODO  优化下面的代码
	var i;
	for(i=0;i<rules.length;i++)
		if(rules[i]!=null){
			if(_judgeObjInRange(rules[i].position,rangePos,_w,_h))//在区域内									
				$('rule'+i).style.border="solid 2px red";
			else
				$('rule'+i).style.border="solid 1px black";			
		}
	for(i=0;i<lines.length;i++)
		if(lines[i]!=null){
			if(_judgeObjInRange(lines[i].midPoint,rangePos,_w,_h))	
				$('mid'+i).style.border="solid 2px red";				
			else
				$('mid'+i).style.border="solid 1px black";
		}	
}
////////////////////////////////general operation,not be triged bt the buttons 
function _lockAll(){
	var i;
	for(i=0;i<rules.length;i++)
		if(rules[i]!=null)
			$('rule'+i).locked();
	for(i=0;i<lines.length;i++)
		if(lines[i]!=null)
			$('mid'+i).locked();	
}

function _unlockAll(){
	var i;
	for(i=0;i<rules.length;i++)
		if(rules[i]!=null)
			$('rule'+i).unlocked();
	for(i=0;i<lines.length;i++)
		if(lines[i]!=null)
			$('mid'+i).unlocked();	
}

function doubleClickThisNode(index){	
	selectThisNode(index);
	if(selectState==0 && rules[selectedOne].type == 1){
		//show开始节点property
		showPropertiesPane();		
		return;
	} else if (selectState==0 && rules[selectedOne].type == 2) {
		//show普通任务节点property
		showPropertiesPane();		
		return;
	} else if (selectState==0 && rules[selectedOne].type == 99) {
		//结束节点，直接返回
		return;
	}

}

function selectThisNode(index){	
	event.cancelBubble=true
	_clearSelectedArray();
		
	if(selectedOne!=null){
		$('rule'+selectedOne).style.border="solid 1px black";
	}
	if(selectedLine!=null){
		$('mid'+selectedLine).style.border="solid 1px black";
		selectedLine=null;		
	}
	
	selectedTwo=selectedOne;
	selectedOne=index;
	if(selectedOne!=null){
		$('rule'+selectedOne).style.border="solid 2px red";
	}
	
		
	if(selectState==100){
		selectState=101;		
		//showHip("请选择目标任务");
		showHip("Please select target node.")
		return;
	}
	if(selectState==101){		
		selectState=0;
		readyForCreateLine();
		return;
	}	
}

function doubleClickThisLine(index) {
	selectThisLine(index);
	var line = lines[index];
	showDecisionPane(line);
}

function selectThisLine(index){
	event.cancelBubble=true;	
	_clearSelectedArray();
	if(selectState!=0)return;
	if(selectedOne!=null){
		$('rule'+selectedOne).style.border="solid 1px black";
	}
	if(selectedLine!=null){
		$('mid'+selectedLine).style.border="solid 1px black";
		selectedLine=null;		
	}
	selectedOne=null;
	selectedTwo=null;
	selectedLine=index;
	if(selectedLine!=null){
		$('mid'+selectedLine).style.border="solid 2px red";
	}
}
//////////////////////属性设置

function showDecisionPane(line) {
	initDecision(line);
	if($('decision').src!="includes/html/setDecision.html")
		$('decision').src="includes/html/setDecision.html";
	$('decision').style.visibility="visible";	
	
	if(selectedLine!=null){
		var obj=new Object();
		obj.index=selectedLine;
		obj.name=lines[selectedLine].name;
		obj.value=lines[selectedLine].value;	
		window["decision"].show(obj);
	}
}

function showPropertiesPane(){
	if(flag_hideAll)return;
	initPropPos();
	if($('props').src!=propertyActionUrl)
		$('props').src=propertyActionUrl;
	$('props').style.visibility="visible";	
	
	if(selectedOne!=null){
		var obj=new Object();
		obj.index=selectedOne;
		obj.description=rules[selectedOne].description;
		obj.enabled=rules[selectedOne].enabled;
		obj.conditions=rules[selectedOne].conditions;
		obj.consequences=rules[selectedOne].consequences;
		window["props"].show(obj);
	}	
}

function hideDecisionPane(){
	$('decision').style.visibility="hidden";	
	if($('decision').src!="includes/html/setDecision.html")
		$('decision').src="includes/html/setDecision.html";
}

function hidePropertiesPane(){
	$('props').style.visibility="hidden";	
	if($('props').src!=propertyActionUrl)
		$('props').src=propertyActionUrl;
}

function setDecision(/*object*/ info) {
	//设置选择的路径参数
	if(selectedLine!=null){	
		lines[selectedLine].name=info.name;
		lines[selectedLine].value=info.value;
		
		var sourceIndex=lines[selectedLine].source;
		
		if (info.value == "Pass") {
			setPassLine(selectedLine);
		} else if (info.value == "Fail") {
			setFailLine(selectedLine);
		} else {
			unSetRuleLine(selectedLine);
		}
		setLineName(selectedLine);
	}else{
		//showHip("设置失败：未选择连线");	
		showHip("Fail: line must be selected");
	}
}

function setPassLine(selectedLine) {
	var sourceIndex=lines[selectedLine].source;
	if ((rules[sourceIndex].passLine != null)
		&& rules[sourceIndex].passLine != lines[selectedLine].index) {
		//如果source节点已经选择了另外的passline，警告。
		//showHip("规则[" + rules[sourceIndex].description+"]已经有了一条Pass连线");
		showHip("Node[" + rules[sourceIndex].description+"] already has a pass line.");
		lines[selectedLine].name="";
		lines[selectedLine].value="";
		unSetRuleLine(selectedLine);
	} else {
		rules[sourceIndex].passLine = lines[selectedLine].index;
	}
}

function setFailLine(selectedLine) {
	var sourceIndex=lines[selectedLine].source;
	if ((rules[sourceIndex].failLine != null)
		&& rules[sourceIndex].failLine != lines[selectedLine].index) {
		//如果source节点已经选择了另外的passline，警告。
		//showHip("规则[" + rules[sourceIndex].description+"]已经有了一条Fail连线");
		showHip("Node[" + rules[sourceIndex].description+"] already has a fail line.");
		lines[selectedLine].name="";
		lines[selectedLine].value="";
		unSetRuleLine(selectedLine);
	} else {
		rules[sourceIndex].failLine = lines[selectedLine].index;
	}
}

function unSetRuleLine(selectedLine) {
	var sourceIndex=lines[selectedLine].source;
	if (rules[sourceIndex].passLine == lines[selectedLine].index) {
		rules[sourceIndex].passLine=null;
	} else if (rules[sourceIndex].failLine == lines[selectedLine].index) {
		rules[sourceIndex].failLine=null;
	} else {
		//showHip("连线设置错误");
		showHip("Set line fail.");
	}
}

function setProperties(/*object*/ info){
	//由设置页面调用，传入设置的内容
	if(selectedOne!=null){
		//rules[selectedOne].type=info.type;
		rules[selectedOne].ruleType=info.ruleType;
		rules[selectedOne].description=info.description;
		rules[selectedOne].enabled=info.enabled;
		rules[selectedOne].conditions=info.conditions;
		rules[selectedOne].consequences=info.consequences;
		setNodeName(selectedOne);
	}else{
		//showHip("设置失败：未选择任务");	
		showHip("Fail：node must be select.");
	}
}

function clearAll(){
//删除所有的节点
	var i;
	selectedOne=null;
	selectedLine=null;
	selectedArray=new Array();
	for(i=0;i<rules.length;i++)
		if(rules[i]!=null){
			var obj=new Object();
			obj.type=1;
			obj.index=i;
			selectedArray[selectedArray.length]=obj;	
		}
	for(i=0;i<lines.length;i++)
		if(lines[i]!=null){
			var obj=new Object();
			obj.type=2;
			obj.index=i;
			selectedArray[selectedArray.length]=obj;	
		}
	_deleteGraphElement();	
}

function clearAllDom(){
//只删除所有的HTML对象，不删除rules数组
	var i;
	selectedOne=null;
	selectedLine=null;
	selectedArray=new Array();
	for(i=0;i<rules.length;i++)
		if(rules[i]!=null){
			var nc=$('nc'+i);
			nc.innerHTML="";
			try{
				document.body.removeChild(nc);
			}catch(e){}
		}
	for(i=0;i<lines.length;i++)
		if(lines[i]!=null){
			var lc=$('lc'+i);	
			try{
				lc.innerHTML="";
				document.body.removeChild(lc);	
			}catch(e){}	
		}
}

/////////////////
function initPropPos(){
	//初始化设置属性的窗口的位置
	var pp=$('props');
	pp.style.top=window.document.body.clientHeight+window.document.body.scrollTop-pp.offsetHeight-2;
	pp.style.left=window.document.body.scrollLeft+11;
	pp.style.border="solid 1px black";
}

function initDecision(line){
//初始化设置属性的窗口的位置

	var pp=$('decision');
	pp.style.top=line.midPoint.top - 10;
	pp.style.left=line.midPoint.left - 10;
}

function init(){
	loadJsFile("includes/objects/line.js","js_line");
	loadJsFile("includes/objects/node.js","js_node");
	loadJsFile("includes/objects/position.js","js_pos");
	loadJsFile("includes/objects/rule.js","js_rule");
	loadJsFile("includes/objects/ruleCondition.js","js_ruleCond");
	loadJsFile("includes/objects/ruleConsequence.js","js_ruleCons");
	loadJsFile("includes/acting/doGraphElement.js","js_dge");
	loadJsFile("includes/acting/saveOrLoad.js","js_sl");
	loadJsFile("includes/acting/dataTrans.js","js_trans");
	loadJsFile("includes/posing/treePos.js","js_tp");
	
	if(flag_hideAll){
		$('panelDraghandle').style.visibility="hidden";
		$('panel1').style.visibility="hidden";
	}
}

/////////////////////////////
function autoTreePos(){
	//自动布局
	clearAllDom();
	initPosing();
	setNodeRelation();
	treePosing();
	$('panel1').style.visibility="hidden";
//	_testShow();
}

function autoPoseNodes(){	
	if(nodeDatas==null)return;
	_initNodesFromExt(nodeDatas);
	initPosing();
	treePosing();
	//_testShow();
}