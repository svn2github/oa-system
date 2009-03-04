/**
  * the basic graph element -- node  
  * @author: shennan  amushen@yahoo.com.cn
  * @date: 2008-1-6
  */
  
function Node(name,type,comment,/*Position Object*/position,width,height,/*Object*/info,
							/*Array of NodeIndex*/parentNode,childNode,
							/*Array of lineIndex*/inLine,outLine
						 ){
	//construction					 	
	this.name=name;
	this.type=type;
	this.comment=comment;
	this.position=position;	//top and left
	this.width=width;
	this.height=height;
	this.info=info;	//some extra informations
	this.parentNode=parentNode;//save the Node Object itself
	this.childNode=childNode;
	this.inLine=inLine;//save the Line Object
	this.outLine=outLine;
	this.index=-1;
}