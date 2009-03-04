/**
  * the basic element of graph - line
  * @author: shennan  amushen@yahoo.com.cn
  * @date:2008-1-6
  */
function Line(name,value,/*Node index*/source,/*Node index*/destination,
						  /*Position Object*/midPoint,sPoint,dPoint
						 ){
//construction	
	this.name=name;
	this.value=value;
	this.source=source;
	this.destination=destination;
	this.midPoint=midPoint;
	this.sPoint=sPoint;
	this.dPoint=dPoint;
	this.index=-1;// save the index in the array
}