// some basic tools
function $(name){
	var e=document.getElementById(name);
	return e;
}

function showHip(text){
	if(flag_hideAll==true)return;
	$('hip').showHip(text);	
}
function noNull(str){
	if(str==null)return "";	
	if(typeof(str)=="undefined")return "";
	return str;
}

function loadJsFile(url,id){
	//读取一个javascript文件
	var element = document.createElement("script");
	element.type = "text/javascript";
	element.src = url;
	if(id!=null)element.id = id;
	document.getElementsByTagName("head")[0].appendChild(element);
}

function mixIn(/*Object*/source,/*Object*/des){
// make the source's each prop to des
	for(var i in source)
		des[i]=source[i];
	return des;
}