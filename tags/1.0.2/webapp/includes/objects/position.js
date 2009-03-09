/**
  * the positon contains top and left property
  * @auther:shennan amushen@yahoo.com.cn
  * @date:2008-1-6
  */
function Position(top,left){
	this.top=parseFloat(top);
	this.left=parseFloat(left);
	this.toString=function(){
		return this.top+","+this.left;	
	}
}