nodeDatas=[{index:0,name:'开始',type:'start',children:[1,2]},
		{index:1,name:'购买',type:'taskNode',children:[30]},
		{index:2,name:'退货',type:'taskNode',children:[40,50]},
		{index:30,name:'<a href=javascript:alert() >继续3</a>',type:'taskNode'},
		{index:40,name:'<a href=javascript:alert() >继续4</a>',type:'taskNode'},
		{index:50,name:'<a href=javascript:alert() >继续5</a>',type:'taskNode'}
		];

//触发事件,记着一定要调用这个
autoPoseNodes();