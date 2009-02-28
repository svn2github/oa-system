// some methods about the data ,eg. json , xml
/*
 * @author: shennan , amushen@yahoo.com.cn
 */
 
function numToType(num){
//节点类型的转换，将数字转成对应的字符串
	num=parseInt(num);
	switch(num){
		case 1:
			return "start";
		case 2:
			return "taskNode";
		case 3:
			return "fork";
		case 4:
			return "join";
		case 99:
			return "end";				
	}
}
function typeToNum(type){
	//节点类型转换，将字符串转成对应的数字
	var num=-1;
	if(type=="start")
		num=1;
	else if(type=="taskNode")
		num=2;
	else if(type=="fork")
		num=3;
	else if(type=="join")
		num=4;
	else if(type=="end")
		num=99;
	return num;
}