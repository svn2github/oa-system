/**
  * the basic graph element -- Rule  
  * @author: shennan  amushen@yahoo.com.cn
  * @date: 2008-1-6
  */
  
function Rule(index,
			  type, 
			  ruleType,
			  description,
			  enabled,
			  conditions,
			  consequences,
			  /*Position Object*/position,width,height,
			  passLine, failLine){
	//construction	
	this.index=index;
	this.type=type;
	this.ruleType=ruleType;
	this.description=description;
	this.enabled=enabled;
	this.conditions=conditions;
	this.consequences=consequences;
	this.position=position;	//top and left
	this.width=width;
	this.height=height;
	this.passLine=passLine;
	this.failLine=failLine;
	this.index=-1;
}