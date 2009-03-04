/**
  * the basic graph element -- RuleCondition  
  * @author: shennan  amushen@yahoo.com.cn
  * @date: 2008-1-6
  */
  
function RuleCondition(conditionTypeDesc, 
			  compareTypeDesc,
			  displayValue,
			  conditionTypeEunmCode,
			  compareTypeEunmCode,
			  conditionTypeColor, 
			  compareTypeColor,
			  value){
	//construction	
	this.conditionTypeDesc=conditionTypeDesc;
	this.compareTypeDesc=compareTypeDesc;
	this.displayValue=displayValue;
	this.conditionTypeEunmCode=conditionTypeEunmCode;
	this.compareTypeEunmCode=compareTypeEunmCode;
	this.conditionTypeColor=conditionTypeColor; 
	this.compareTypeColor=compareTypeColor;
	this.value=value;
}