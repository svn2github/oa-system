function saveFlow(){	
	document.getElementById("flowXml").value = createFlowXml();
	document.flowForm.submit();
}
function loadFlow(filename){
// load the flowDefinition	from a file
	if(!confirm("dontknowwhichword"))return;
	clearAll();
	$('props').src="loadFlow.htm";
	initPropPos();
	$('props').style.visibility="visible";
}

function doLoadFlow(str){
	nodes=new Array();
	lines=new Array();
	eval(str);
	for(var i=0;nn!=null&&i<nn.length;i++){
		var node=new Node();
		node=mixIn(nn[i],node);
		node.type=typeToNum(node.type);
		node.position=new Position(node.position.top,node.position.left);
		nodes[node.index]=node;
		createNode(node);
		canDragOfNode(node.index);
	}
	for(var i=0;ll!=null&&i<ll.length;i++){
		var line=new Line();
		line=mixIn(ll[i],line);
		line.sPoint=new Position(line.sPoint.top,line.sPoint.left);
		line.midPoint=new Position(line.midPoint.top,line.midPoint.left);
		line.dPoint=new Position(line.dPoint.top,line.dPoint.left);
		lines[line.index]=line;
		createLine(line);
		canDragOfLine(line.index);		
	}
}

function createFlowXml() {
	var doc = new ActiveXObject("Msxml2.DOMDocument");
	//创建文件头	
	var p = doc.createProcessingInstruction("xml","version='1.0' encoding='utf-8'");
	//添加文件头
    doc.appendChild(p);
    //添加根节点
    var flow =  doc.createElement("flow");
    
    if (rules.length == 0 || rules[0].type != 1) {
    	showHip("没有发现开始任务");
    	return;
    }
    
    createRuleXml(doc, flow);
    
    doc.appendChild(flow);
    return doc.xml;
}

function createRuleXml(doc, flow) {
	var rulesXml = doc.createElement("rules");
	
	for (i=0;i<rules.length;i++) {
		if (rules[i] != null && rules[i].type != 99) {
			var ruleXml = doc.createElement("rule");
			
			var seq = doc.createAttribute("seq");
			seq.value=rules[i].index + 1;	
			ruleXml.setAttributeNode(seq);
			
			var type = doc.createAttribute("type");
			type.value=rules[i].ruleType;	
			ruleXml.setAttributeNode(type);
			
			var description = doc.createAttribute("description");
			description.value=rules[i].description;		
			ruleXml.setAttributeNode(description);
			
			var enabled = doc.createAttribute("enabled");
			enabled.value=rules[i].enabled;		
			ruleXml.setAttributeNode(enabled);
			
			var top = doc.createAttribute("top");
			top.value=rules[i].position.top;		
			ruleXml.setAttributeNode(top);
			
			var left = doc.createAttribute("left");
			left.value=rules[i].position.left;		
			ruleXml.setAttributeNode(left);
			
			var width = doc.createAttribute("width");
			width.value=rules[i].width;		
			ruleXml.setAttributeNode(width);
			
			var height = doc.createAttribute("height");
			height.value=rules[i].height;		
			ruleXml.setAttributeNode(height);
			
			var nextSeqWhenPass = doc.createAttribute("nextSeqWhenPass");
			if (rules[i].passLine != null) {
				if (lines[rules[i].passLine].destination != null) {
					nextSeqWhenPass.value=rules[lines[rules[i].passLine].destination].index + 1;
				}
			}
			ruleXml.setAttributeNode(nextSeqWhenPass);
			
			var nextSeqWhenFail = doc.createAttribute("nextSeqWhenFail");
			if (rules[i].failLine != null) {
				if (lines[rules[i].failLine].destination != null) {
					nextSeqWhenFail.value=rules[lines[rules[i].failLine].destination].index + 1;
				}
			}
			ruleXml.setAttributeNode(nextSeqWhenFail);
			
			createRuleConditionXml(doc, ruleXml, i);
			createRuleConsequenceXml(doc, ruleXml, i);
			rulesXml.appendChild(ruleXml);
		}
	}
	flow.appendChild(rulesXml);
}

function createRuleConditionXml(doc, ruleXml, ruleIndex) {
	var conditions = rules[ruleIndex].conditions;
	if (conditions != null) {
		var conditionsXml = doc.createElement("conditions");
		for (j=0;j<conditions.length;j++) {
			var conditionXml = doc.createElement("condition");
			
			var conditionType = doc.createAttribute("conditionType");
			conditionType.value=conditions[j].conditionTypeEunmCode;	
			conditionXml.setAttributeNode(conditionType);
			
			var compareType = doc.createAttribute("compareType");
			compareType.value=conditions[j].compareTypeEunmCode;	
			conditionXml.setAttributeNode(compareType);
			
			var value = doc.createAttribute("value");
			value.value=conditions[j].value;	
			conditionXml.setAttributeNode(value);
			
			conditionsXml.appendChild(conditionXml);
		} 
		ruleXml.appendChild(conditionsXml);
	}
}

function createRuleConsequenceXml(doc, ruleXml, ruleIndex) {
	var consequences = rules[ruleIndex].consequences;
	if (consequences != null) {
		var consequencesXml = doc.createElement("consequences");
		for (j=0;j<consequences.length;j++) {
			var consequenceXml = doc.createElement("consequence");
			
			var seq = doc.createAttribute("seq");
			seq.value=consequences[j].seq;	
			consequenceXml.setAttributeNode(seq);
			
			var user = doc.createAttribute("user");
			user.value=consequences[j].userId;	
			consequenceXml.setAttributeNode(user);
			
			var superior = doc.createAttribute("superior");
			superior.value=consequences[j].superiorId;	
			consequenceXml.setAttributeNode(superior);
			
			var canModify = doc.createAttribute("canModify");
			canModify.value=consequences[j].canModifyEnumCode;	
			consequenceXml.setAttributeNode(canModify);
			
			consequencesXml.appendChild(consequenceXml);
		} 
		ruleXml.appendChild(consequencesXml);
	}
}

function createSaveString(){
	if(rules==null||rules.length<1)return "";
	var str="";
	var sline="";
	var i;
//	alert(nodes.length);
	for(i=0;i<rules.length;i++)
		if(rules[i]!=null){
			str+=",{index:'"+nodes[i].index+"',name:'"+nodes[i].name+"',type:'"+numToType(nodes[i].type)+"',comment:'"+noNull(nodes[i].comment)+"',position:{top:"+nodes[i].position.top+",left:"+nodes[i].position.left+"}}";					
		}
	if(str!="")
		str="var nn=["+str.substr(1)+"];";		
//	alert(str+","+nodes[1].comment);
	for(i=0;i<lines.length;i++)
		if(lines[i]!=null){
			sline+=",{index:'"+lines[i].index+"',name:'"+lines[i].name+"',comment:'"+noNull(lines[i].comment)+"',source:"+lines[i].source+",destination:"+lines[i].destination+",midPoint:{top:'"+lines[i].midPoint.top+"',left:'"+lines[i].midPoint.left+"'},sPoint:{top:'"+lines[i].sPoint.top+"',left:'"+lines[i].sPoint.left+"'},dPoint:{top:'"+lines[i].dPoint.top+"',left:'"+lines[i].dPoint.left+"'}}";	
		}
	if(sline!="")
		sline="var ll=["+sline.substr(1)+"];";
	return ""+str+sline;

}