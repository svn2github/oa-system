/**
  * the basic graph element -- RuleConsequence  
  * @author: shennan  amushen@yahoo.com.cn
  * @date: 2008-1-6
  */
  
function RuleConsequence(type,
			  seq, 
			  canModifyEnumCode,
			  canModifyDesc,
			  canModifyColor,
			  userId,
			  userName,
			  superiorId,
			  superiorName){
	//construction	
	this.type=type;
	this.seq=seq;
	this.canModifyEnumCode=canModifyEnumCode;
	this.canModifyDesc=canModifyDesc;
	this.canModifyColor=canModifyColor;
	this.userId=userId;
	this.userName=userName;
	this.superiorId=superiorId;
	this.superiorName=superiorName;
}