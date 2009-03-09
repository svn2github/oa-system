	function clearOptions(select_control)
	{
		var oldLength=select_control.options.length;
		var i;
		for(i=0;i<oldLength;i++)
			select_control.options.remove(0);
	}
	
	function getOldValue(form,control)
	{
		var oldValue="";
		if(control.oldValue)
			oldValue=control.oldValue;
		else
		{
			var valueControl=form[control.name+"_value"];
			if(valueControl) oldValue=valueControl.value;
		}	
		return oldValue;
	}
	
	function initData(config,form)
	{
		var xml_config=document.all[config.xmlConfigId];
		var xml_data=document.all[config.xmlDataId];
		var tagSelectNameMapping=config.tagSelectNameMapping;
		
		var node=xml_config.XMLDocument.selectSingleNode("//config");
		var childList=node.childNodes;
		var child;
		var control;

		var value;
		
		for(i=0;i<childList.length;i++)
		{
			child=childList.item(i);
			if (child.nodeType != 1) {
				continue;
			}
			control=form[tagSelectNameMapping.get(child.tagName)];

			clearOptions(control);
			//control.onchange="";
	
			value=getOldValue(form,control);
			
			var dataNodeList=xml_data.XMLDocument.selectNodes("//"+child.tagName);
			var dataNode;
			var i;
			for(i=0;i<dataNodeList.length;i++)
			{
				dataNode=dataNodeList.item(i);
				
				var oOption = document.createElement("OPTION");
				oOption.text=dataNode.attributes[1].value;
				oOption.value=dataNode.attributes[0].value;
				for (var j=2;j<dataNode.attributes.length;j++)
				{
					var attr=dataNode.attributes[j];
					oOption[attr.name]=attr.value;
				}
				control.add(oOption);
				if(value==oOption.value) 
				{
					oOption.selected=true;
				}
			}
			cascadeUpdate(control);
			control.onchange=cascadeUpdate;
		}
	}
	

	
	function initCascadeSelect(xml_config_id,xml_data_id,form_name,selectTagMapping,init_data)
	{
		var form=document[form_name];
		var select_name;
		var control;
		
		var config=new Object();
		config.tagSelectNameMapping=new Map();
		config.xmlConfigId=xml_config_id;
		config.xmlDataId=xml_data_id;
		
		var select_name_iter=selectTagMapping.keySet().iterator();
		while(select_name_iter.hasNext())
		{
			select_name=select_name_iter.next();
			control=form[select_name];
			control.xmlTagName=selectTagMapping.get(select_name);
			config.tagSelectNameMapping.put(control.xmlTagName,select_name);		
			control.config=config;
		}
		if(init_data)
		{
			config.first=true;
			initData(config,form);
			config.first=false;
		}
	}
	
	function getXMLDataNodeFromControl(control)
	{
		var config=control.config;
		var xml=document.all[config.xmlDataId];
		return xml.XMLDocument.selectSingleNode("//"+control.xmlTagName+"[@id = '"+
			control.value
			+"']");
	}
	
	function getXMLConfigNodeFromControl(control)
	{
		var config=control.config;
		var xml=document.all[config.xmlConfigId];
		return xml.XMLDocument.selectSingleNode("//"+control.xmlTagName);
	}
	
	function getChildSelectList(control)
	{
		var node=getXMLConfigNodeFromControl(control);
		var form=control.form;
		var config=control.config;
		var tagSelectNameMapping=config.tagSelectNameMapping;
		
		var retVal=new List();
		var childList=node.childNodes;
		var child;
		for(i=0;i<childList.length;i++)
		{
			child=childList.item(i);
			retVal.add(form[tagSelectNameMapping.get(child.tagName)]);
		}
		return retVal;
	}
	
	function cascadeUpdate(control)
	{
		if(!control)control=this;
		if (control.afterChange) control.afterChange(control);
		var form=control.form;
		var node=getXMLDataNodeFromControl(control);
		var config=control.config;
		var tagSelectNameMapping=config.tagSelectNameMapping;
		var select;
		
		var childSelectList=getChildSelectList(control);
		
		var selectIter;
		selectIter=childSelectList.iterator();
		while(selectIter.hasNext())
		{
			select=selectIter.next();
			//select.oldOnChange=select.onchange;
			//select.onchange="";
			clearOptions(select);
		}
		
		if(node)
		{
			var childList=node.childNodes;
			var child;
			var i;
			var value;
			for(i=0;i<childList.length;i++)
			{
				child=childList.item(i);
				if (child.nodeType != 1) {
					continue;
				}
				select=form[tagSelectNameMapping.get(child.tagName)];
			
				var oOption = document.createElement("OPTION");
				oOption.text=child.attributes[1].value;
				oOption.value=child.attributes[0].value;
				for (var j=2;j<child.attributes.length;j++)
				{
					var attr=child.attributes[j];
					oOption[attr.name]=attr.value;
				}
				select.add(oOption);
				if(config.first)
				{
					value=getOldValue(form,select);
					if(value==oOption.value) 
					{
						oOption.selected=true;
					}						
				}
			}
		}
		selectIter=childSelectList.iterator();
		while(selectIter.hasNext())
		{
			select=selectIter.next();
			cascadeUpdate(select);
			select.onchange=cascadeUpdate;
		}
	}
	