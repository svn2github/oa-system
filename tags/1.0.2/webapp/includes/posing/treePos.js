// 将节点自动排列
/*实现目标：
	1 将所有的节点自动排序
	2 解决连线的重叠问题
分析：
	1 设置每个节点的parentNode和childNode
	2 找到一个parentNode 为null的做为树根设坐标（row=0,0)
	3 判断childNode的节点数为每个子节点设置坐标（row+1,col)，
		3.1 子节点的位置（row+1,fa.col+(i-(total/2))）
	4 如果坐标位置被占用，那么修改父节点坐标，使其col++ ，重复3
		4.1 判断占用关键是看col因为是自顶到下的，所以row不会重复
	5 设置完这个节点的子节点以后，设置这个节点为已操作already=1
	6 查找一个父节点不为空already=0的节点，重复3
	7 重复2
	8 完成节点的坐标设置，进行位移

	9 建立两点之间的连线
	10 写连线的名称和备注（选）
	11 判断如果两个连线的midPoint重叠，就左右移开
	12 如果连线的midPoint和node重叠，就移开
	
	author:shennan amushen@yahoo.com.cn
*/

var treeRow=0;		//记录当前行数
var already=new Array();	//记录是否已经操作
var treeNodes=null;		//这个树的所有节点，自顶至下，自左到右
var i_tn;					//treeNodes的循环变量
var minest=999;				//横坐标最小值，在位移的时候使用
var M_LEFT=120;				//坐标放大的倍数
var M_TOP=60;
var isNoRoot=true;		//如果出现环，没有根节点

function initPosing(){
	treeRow=0;
	minest=999;
	isNoRoot=true;
	for(var i=0;nodes!=null&&i<nodes.length;i++)
		already[i]=0;
}

function setNodeRelation(){
	//设置每个节点的父子节点
	if(nodes==null||nodes.length<1)return;
	var i;
	for(i=0;i<nodes.length;i++){
		if(nodes[i]!=null){
			nodes[i].parentNode=new Array();
			nodes[i].childNode=new Array();
		}
	}
	if(lines==null||lines.length<1)return;
	for(i=0;i<lines.length;i++){
		if(lines[i]==null)continue;
		nodes[lines[i].source].childNode.push(lines[i].destination);
		nodes[lines[i].destination].parentNode.push(lines[i].source);		
	}
}

function _getRoot(){
	var i;
	for(i=0;nodes!=null&&i<nodes.length;i++){
		if(nodes[i]==null)continue;
		if(already[i]==1)continue;
		if(nodes[i].parentNode.length>0)continue;		
		isNoRoot=false;
		return i;
	}
	if(isNoRoot){//出现环就返回第一个节点
		isNoRoot=false;
		for(i=0;nodes!=null&&i<nodes.length;i++){
			if(nodes[i]!=null){		
				return i;
			}
		}
	}
	return null;
}

function _setPosOfTree(root){
	//设置treeNodes数组
	treeNodes=new Array();
	treeNodes.push(root);
	var j,flag;
	i_tn=0;
	var k=0;
	while(k<treeNodes.length){
		for(var i=0;i<nodes[treeNodes[k]].childNode.length;i++){
			if(already[nodes[treeNodes[k]].childNode[i]]==1)continue;
			//必须保证这个点没有出现过,防止出现环路
			flag=true;
			for(j=0;j<treeNodes.length;j++)
				if(treeNodes[j]==nodes[treeNodes[k]].childNode[i]){
					flag=false;
					break;
				}
			if(flag)treeNodes.push(nodes[treeNodes[k]].childNode[i]);	
		}
		k++;
	}
}

function _getNextRoot(){
//下一个要处理的节点
	if(i_tn==treeNodes.length-1)return null;
	i_tn++;
	return treeNodes[i_tn];
}

function treePosing(){
	//主控函数	
	var root=null;
	var i;
	var maxCol=-999;	//下一行的最大left位置
	var total;//孩子总数
	
	root=_getRoot();
	if(root!=null){
		nodes[root].position.left=0;	//根始终在0位置		
		nodes[root].position.top=treeRow;//自顶向下
		_setPosOfTree(root);	//为一个树设置标号，自顶向下，自左向右标
	}
	treeRow++;	//开始第一层
	while(root!=null){
		total=nodes[root].childNode.length;
		//首先确定root的位置是否需要调整
		if(maxCol!=-999){
			//可能不是这层的第一个，有可能需要调整位置
			nodes[root].position.left=maxCol+1+parseInt(total/2);
		}
		//为这个点的孩子节点设置位置
		for(i=0;i<nodes[root].childNode.length;i++){
			if(already[nodes[root].childNode[i]]==1)continue;//如果子节点已经展开，那么就不再设置
			
			nodes[nodes[root].childNode[i]].position.top=treeRow;
			nodes[nodes[root].childNode[i]].position.left=nodes[root].position.left+(i-parseInt(total/2));
//			下面的三行代码可以让布局更对称，但是太占空间了，所以删除
//			if(total%2==0)
//				if(i-parseInt(total/2)>=0)
//					nodes[nodes[root].childNode[i]].position.left++;
					
			maxCol=nodes[nodes[root].childNode[i]].position.left;	
			if(maxCol<minest)minest=maxCol;
		}
		already[root]=1;
		root=_getNextRoot();
		if(root!=null&&nodes[root].position.top==treeRow){
			//换行	
			treeRow++;
			maxCol=-999;
		}
		
		if(root==null){
		//开始下一棵树	
			treeRow++;
			root=_getRoot();
			if(root!=null){
				nodes[root].position.left=0;	//根始终在0位置		
				nodes[root].position.top=treeRow;//自顶向下
				_setPosOfTree(root);	//为一个树设置标号，自顶向下，自左向右标
				treeRow++;
				
			}
		}
		
	}//end of while(root)

	//开始为每个节点设置准确坐标
	_moveAllToRight();
	//坐标放大
	_magnifyPos();
	//创建节点
	_createAllTreeNode();
	//创建节点间的连线
	_createAllTreeLine();	
}
function _moveAllToRight(){
	if(minest==999)minest=0;
	for(var i=0;nodes!=null&&i<nodes.length;i++)
		if(nodes[i]!=null){
			nodes[i].position.left-=minest-1;
		}
}

function _magnifyPos(){
	for(var i=0;nodes!=null&&i<nodes.length;i++)
		if(nodes[i]!=null){
			nodes[i].position.left*=M_LEFT;
			nodes[i].position.top*=M_TOP;
		}
	
}
function _createAllTreeNode(){
	var i;
	for(i=0;nodes!=null&&i<nodes.length;i++)
		if(nodes[i]!=null){
			createNode(nodes[i]);
			canDragOfNode(i);
		}
}

function _createAllTreeLine(){
	lines=null;
	lines=new Array();
	var i,j;
	for(i=0;nodes!=null&&i<nodes.length;i++)
		if(nodes[i]!=null){
			for(j=0;j<nodes[i].childNode.length;j++){
				var line=new Line();
				line.source=i;
				line.destination=nodes[i].childNode[j];
				line.index=lines.length;
				line.name="";
				line=getLineSDPoint(line);
				lines.push(line);				
			}
		}
	//避免连线重叠,只判断mid
	_avoidLineOverlap();
	for(i=0;lines!=null&&i<lines.length;i++)
		if(lines[i]!=null){
			createLine(lines[i]);
			canDragOfLine(i);
		}
}

function _avoidLineOverlap(){
	if(lines==null||lines.length<1)return;
	var i,j,d;
	for(i=0;i<lines.length;i++)
		if(lines[i]!=null){
			for(j=i+1;j<lines.length;j++)
				if(lines[j]!=null){
					//计算两点距离
					d=Math.sqrt(Math.pow(lines[i].midPoint.top-lines[j].midPoint.top,2)+Math.pow(lines[i].midPoint.left-lines[j].midPoint.left,2));
					if(d<10){
						lines[j].midPoint.left=lines[i].midPoint.left+10;
						lines[j].midPoint.top=lines[i].midPoint.top+10;
						
					}
				}			
		}	
}

function _initNodesFromExt(/*nodeDatas*/nd){
//将外部数据转换成系统格式的数据
	var i,j;
	nodes=null;
	nodes=new Array();
	for(i=0;i<nd.length;i++){
		var node=new Node();
		node.index=nd[i].index;
		node.name=nd[i].name;
		node.type=typeToNum(nd[i].type);
		node.position=new Position();
		node.childNode=new Array();
		node.parentNode=new Array();
		if(nd[i].children!=null)node.childNode=nd[i].children;
//		alert(node.index+","+node.name+",child:"+node.childNode);		
		nodes[node.index]=node;
	}
	//设置父节点
	for(i=0;i<nodes.length;i++)
		if(nodes[i]!=null){			
			for(j=0;j<nodes[i].childNode.length;j++){	
				try{
					nodes[nodes[i].childNode[j]].parentNode.push(i);
				}catch(e){}			
			}
		}	
		nd=null;
}
/////////////////

function _testShow(){	
	var str="";
	for(var i=0;i<nodes.length;i++)
		if(nodes[i]!=null)
			str+=nodes[i].name+":"+nodes[i].position+"<br>";
	$('_test').innerHTML=str;
}