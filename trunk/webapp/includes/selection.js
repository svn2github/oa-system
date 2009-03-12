/* ====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * ==================================================================== *
 */
 
/**
 *
 * @param  
 * @param
 * @param
 */
function transferAllOption(fromSelObj, toSelObj) {
	selectAllOption(fromSelObj);
	transferOption(fromSelObj, toSelObj);
}

/**
 * select all options
 * @param  
 */
function selectAllOption(selObj) {
	for (var i = 0; i < selObj.options.length; i++) {
			selObj.options[i].selected = true;
	}
}

function selectOptions(selObj, patten) {
	for (var i = 0; i < selObj.options.length; i++) {
		var o = selObj.options[i];
		o.selected = (o.text.indexOf(patten) != -1);
	}
}

function transferOption(fromSelObj, toSelObj) {
		for (var i = fromSelObj.options.length - 1; i >= 0 ; i--) {
				if (fromSelObj.options[i].selected) { 
						var dupOpt = false;
						var insPos = 0;
						for (var j = 0; j < toSelObj.options.length; j++) {
								if (toSelObj.options[j].value == fromSelObj.options[i].value) {
										//duplicate option, do not insert
										dupOpt = true;
										break;
								} else {
										if (toSelObj.options[j].innerText < fromSelObj.options[i].innerText) {
												insPos = j + 1;
										}
								}
						}
						if (!dupOpt) {
								for (var k = toSelObj.options.length; k > insPos; k--) {
									//alert(k);
										toSelObj.options[k] = new Option(toSelObj.options[k - 1].innerText, toSelObj.options[k - 1].value);;
								}
								toSelObj.options[insPos] = new Option(fromSelObj.options[i].innerText, fromSelObj.options[i].value);
						}
						
						//remove from select
						fromSelObj.options.remove(i);
				}
		}
}



function AddOption(p_aVal, p_aText, p_oSel)
{
	var nLength		= p_oSel.length;
	var nALen		= p_aText.length;
	var i, j;
	var bTag ;
	var sVal, sText ;

	for(i=0; i<nALen; i++)
	{
		bTag	= 0;
		sVal	= p_aVal[i];
		sText	= p_aText[i];
		
		for(j=0; j<nLength; j++)
		{
			if((sText==p_oSel.options[j].text) && (sVal==p_oSel.options[j].value)) 
			{
				bTag	= 1 ; 
				break;
			}
			if((sVal=="")||(sText=="")) 
			{
				bTag	= 1 ; 
				break;
			}
		}
		if(bTag == 0) 
		{
			p_oSel.options[nLength]	= new Option(sText,nLength);
			p_oSel.options[nLength].value	= sVal;
			nLength++;
		}
	}
}

function delOption(p_oSel)
{
	for (var i = p_oSel.length - 1; i >= 0; i--) {
		if (p_oSel.options[i].selected) {
				p_oSel.options.remove(i);
		}
	}
}

function delAllOption(p_oSel)
{
	p_oSel.length	= 0;
}


function moveUp(p_oSel)
{
	with (p_oSel)
	{
		if(1 == selectedIndex)
		{
			options[length]	= new Option(options[1].text,options[1].value) ;
			options[1]		= null ;
			selectedIndex	= length - 1 ;
		}
		else if(selectedIndex > 1) 
		{
			moveGo(p_oSel,-1) ;
		}
	}
}


function moveDown(p_oSel)
{
	with (p_oSel)
	{
		if(selectedIndex == (length-1) )
		{
			var i ;
			var sText	= options[selectedIndex].text ;
			var sVal	= options[selectedIndex].value ;

			for(i=selectedIndex; i>1; i--)
			{
				options[i].text		= options[i-1].text ;
				options[i].value	= options[i-1].value ;
			}
			options[i].text		= sText
			options[i].value	= sVal ;
			selectedIndex		= 1 ;
		}
		else if( (selectedIndex>0) && (selectedIndex<(length-1)) )
		{
			moveGo(p_oSel,+1) ;
		}
	}
}

function moveGo(p_oSel,nOffset)
{
	with (p_oSel)
	{
		nDesIndex	= selectedIndex + nOffset ;
		var sText	= options[nDesIndex].text ;
		var sVal	= options[nDesIndex].value ;
		options[nDesIndex].text		= options[selectedIndex].text ;
		options[nDesIndex].value	= options[selectedIndex].value ;
		options[selectedIndex].text		= sText ;
		options[selectedIndex].value	= sVal ;
		selectedIndex	= nDesIndex ;
	}
}

function cascadeUpdate(selObj1, selObj2, selAry2, selVal2)
{
	var selIndex = selObj1.options.selectedIndex;
	selObj2.options.length = 0;
	for (var i = 0; i < selAry2[selIndex].length; i++) {
			selObj2.options[i] = new Option(selAry2[selIndex][i].text, selAry2[selIndex][i].value);
			if (selVal2 == selAry2[selIndex][i].value) {
					selObj2.options[i].selected = true;
			}
	}
	
	//if (selVal2 == null) {
	//		selObj2.options[0].selected = true;
	//}
}

function initCascadeSelect(selObj1, selObj2, selAry1, selAry2, selVal1, selVal2)
{
	var i = 0;
	for (; i < selAry1.length; i++) {
		selObj1.options[i] = new Option(selAry1[i].text, selAry1[i].value);
		if (selVal1 == selAry1[i].value) {
				selObj1.options[i].selected = true;
		}
	}
	
	cascadeUpdate(selObj1, selObj2, selAry2, selVal2);
}

function initSelect(selObj, selAry, selVal)
{
	for (var i = 0; i < selAry.length; i++) {
		selObj.options[i] = new Option(selAry[i].text, selAry[i].value);
		if (selVal == selAry[i].value) {
				selObj.options[i].selected = true;
		}
	}
}
