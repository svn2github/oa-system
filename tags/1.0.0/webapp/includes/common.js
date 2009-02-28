
/**
* @title: java-like Map
* @author: lizhantao
* @date: 2004-11-22
* @email: lazett@gmail.com
* @warning: only support simple type key,String best!;
*/
function Map() {
	this.pairs = new List();
	
	this.get = get;
	this.put = put;
	this.remove = remove;
	this.size = size;
	this.isEmpty = isEmpty;
	this.keySet = keySet;
	this.containsKey = containsKey;
	
	function Item(key, value){
		this.key = key;
		this.value = value;
	}

	function put(key, value){
		for(var iter = this.pairs.iterator();iter.hasNext();){
			var item = iter.next();
			if(item.key === key){
				item.value = value;
				return;
			}
		}
		this.pairs.add(new Item(key,value));
	}

	function get(key){
		for(var iter = this.pairs.iterator();iter.hasNext();){
			var item = iter.next();
			if(item.key === key){
				return item.value;
			}
		}
		return null;
	}

	function remove(key) {
		for(var iter = this.pairs.iterator();iter.hasNext();){
			var item = iter.next();
			if(item.key === key){
				iter.remove();
			}
		}
	}

	function size(){
		return this.pairs.size();
	}

	function isEmpty(){
		return this.pairs.size() <= 0;
	}

	function keySet(){
		var keys = new List();
		for(var iter = this.pairs.iterator();iter.hasNext();){
			var item = iter.next();
			keys.add(item.key);
		}
		return keys;
	}

	function containsKey(key){
		for(var iter = this.pairs.iterator();iter.hasNext();){
			var item = iter.next();
			if(item.key === key){
				return true;
			}
		}
		return false;
	}
}

/**
* @title: java-like List
* @author: lizhantao
* @date: 2004-11-22
* @email: lazett@gmail.com
*/
function List(){
	this.innerArray = new Array();
	this.add = add;
	this.get = get;
	this.remove = remove;
	this.size = size;
	this.isEmpty = isEmpty;
	this.iterator = iterator;
	this.contains = contains;

	function add(value){
		this.innerArray[this.innerArray.length] = value;
	}

	function get(i){
		if (i< 0 || i>= this.innerArray.length)	{
			return;
		}
		return this.innerArray[i];
	}

	function remove(i){
		if (i< 0 || i>= this.innerArray.length)	{
			return;
		}
		this.innerArray.splice(i,1);
	}

	function size(){
		return this.innerArray.length;
	}

	function isEmpty(){
		return this.innerArray.length <= 0;
	}
	
	function iterator(){
		return new Iterator(this);
	}

	function contains(object){
		for (var i = 0; i < this.pairs.length; i++){
			if ( this.innerArray[i] === object ){
				return true;
			}
		}
		return false;
	}
}

/**
* @title: java-like Set
* @author: lizhantao
* @date: 2004-11-22
* @email: lazett@gmail.com
* @warning: only support simple type value,String best!;
*/
function Set(){
	this.innerArray = new Array();
	this.add = add;
	this.get = get;
	this.remove = remove;
	this.size = size;
	this.isEmpty = isEmpty;
	this.iterator = iterator;
	this.contains = contains;

	function add(value){
		for (var i = 0; i < this.pairs.length; i++)	{
			if ( this.innerArray[i] === value )	{
				return;
			}
		}
		this.innerArray[this.innerArray.length] = value;
	}

	function get(i){
		if (i< 0 || i>= this.innerArray.length){
			return;
		}
		return this.innerArray[i];
	}

	function remove(i){
		if (i< 0 || i>= this.innerArray.length)	{
			return;
		}
		this.innerArray.splice(i,1);
	}

	function size(){
		return this.innerArray.length;
	}

	function isEmpty(){
		return this.innerArray.length <= 0;
	}

	function iterator(){
		return new Iterator(this);
	}

	function contains(object){
		for (var i = 0; i < this.pairs.length; i++){
			if ( this.innerArray[i] === object ){
				return true;
			}
		}
		return false;
	}
}

/**
* @title: java-like Iterator
* @author: lizhantao
* @date: 2004-11-22
* @email: lazett@gmail.com
*/
function Iterator(list){
	this.list = list;
	this.cursor = 0;
	this.hasNext = hasNext;
	this.next = next;
	this.remove = remove;

	function hasNext(){
		return this.cursor != this.list.size();
	}
	function next(){
		var result = this.list.get(this.cursor);
		this.cursor++;
		return result;
	}
	
	function remove(){
		this.cursor--;
		this.list.remove(this.cursor);
	}
}

/**
* @title: ************demo example*************
* @author: lizhantao
* @date: 2004-11-22
* @email: lazett@gmail.com
*/
/*var a = new Map();
alert("a.isEmpty()=" + a.isEmpty());
a.put("1","lizhantao");
a.put("2","lazett@gmail.com");
a.put("3","beijing");
a.put("4","2004-11-22");
alert("a.isEmpty()=" + a.isEmpty());
a.remove("2");
alert("a.size()=" + a.size());
var b = a.keySet();
for(var c = b.iterator();c.hasNext();){
var key = c.next();
var value = a.get(key);
alert("key=" + key + " value=" + value);
}
document.write("using java-like util example end");
*/

function Stack() {
	this.innerArray = new Array();
	this.push = push;
	this.pop = pop;
	this.size = size;
	this.isEmpty = isEmpty;
	
	function push(obj) {
		this.innerArray[this.innerArray.length] = obj;
	}
	
	function pop() {
		var len = this.innerArray.length;
		if (len == 0) return;
		var obj = this.innerArray[len - 1];
		this.innerArray.length--;
		return obj;
	}
	
	function size() {
		return this.innerArray.length;
	}
	
	function isEmpty() {
		return this.innerArray.length == 0;
	}
}

function replaceVar(s,v)
{
	var map=new Map();
	var regexp=new RegExp("@{([^@]+)}", "g")
	//var regexp=/@{([^@]+)}/g;
	var match;
	for(;;)
	{
		match=regexp.exec(s);
		if(match)
		{
			map.put(match[0],match[1]);
		}
		else
		{
			break;
		}
	}
	
	var itor=map.keySet().iterator();
	while(itor.hasNext())
	{
		var match0=itor.next();
		var match1=map.get(match0);
		var r=eval("v."+match1);
		if(typeof(r) == 'undefined')
		{
			alert(match1+" not found!");
			return "";
		}
		s=s.replace(new RegExp(match0, "g"),r)
	}
	return s;
}

function trim(s)
{	
	s=s.replace(/^ */,"");
	return s.replace(/ *$/,"");
}

function chknum(s)
{
	var num;
	s=trim(s);
	if (s=="0")
		return true;
	if (s=="")
		return false;
	s=s.replace(/^0*/,"");
	num=parseInt(s);
	if (s!=''+num) 
		return false;
	else 
		return true;
}

function isAlien(a) {
   return isObject(a) && typeof a.constructor != 'function';
}

function isArray(a) {
    return isObject(a) && a.constructor == Array;
}

function isBoolean(a) {
    return typeof a == 'boolean';
}

function isEmpty(o) {
    var i, v;
    if (isObject(o)) {
        for (i in o) {
            v = o[i];
            if (isUndefined(v) && isFunction(v)) {
                return false;
            }
        }
    }
    return true;
}

function isFunction(a) {
    return typeof a == 'function';
}

function isNull(a) {
    return typeof a == 'object' && !a;
}

function isNumber(a) {
    return typeof a == 'number' && isFinite(a);
}

function isObject(a) {
    return (a && typeof a == 'object') || isFunction(a);
}

function isString(a) {
    return typeof a == 'string';
}

function isUndefined(a) {
    return typeof a == 'undefined';
} 



/***********************************************************
Function formatnumber(value,num)
Written by zergling
javascript???FormatNumber??????VBScript???????????????????????????
????????????
????????????????????????
***********************************************************/
function adv_format(value,num) //????
{
	var a_str = formatnumber(value,num);
	var a_int = parseFloat(a_str);
	if (value.toString().length>a_str.length)
	{
		var b_str = value.toString().substring(a_str.length,a_str.length+1);
		var b_int = parseFloat(b_str);
		if (b_int<5)
		{
			return a_str;
		}
		else
		{
			var bonus_str,bonus_int;
			if (num==0)
			{
				bonus_int = 1;
			}
			else
			{
				bonus_str = "0.";
				for (var i=1; i<num; i++)
					bonus_str+="0";
				bonus_str+="1";
				bonus_int = parseFloat(bonus_str);
			}
			a_str = formatnumber(a_int + bonus_int, num);
		}
	}
	return a_str;
}

function formatnumber(value,num) //????
{
	var a,b,c,i;
	a = value.toString();
	b = a.indexOf('.');
	c = a.length;
	if (num==0)
	{
		if (b!=-1)
			a = a.substring(0,b);
	}
	else
	{
		if (b==-1)
		{
			a = a + ".";
			for (i=1;i<=num;i++) a = a + "0";
		}
		else
		{
			a = a.substring(0,b+num+1);
			for (i=c;i<=b+num;i++) a = a + "0";
		}
	}
	return a;
}

function dialogAction(url,title,width,height)
{
	return  window.showModalDialog(
				'showDialog.do?title='+title+"&"+url, 
				null, 'dialogWidth:'+width+'px;dialogHeight:'+height+'px;status:no;help:no;scroll:no');
}

function confirmDialog(url,title,message,width,height)
{
	var confirmUrl="confirmOperationDialog.do?message="+message+"&"+url;
	return dialogAction(confirmUrl,title,width,height);	
}


function validateFormFloat(value)
{
	var bValid=true;
	if (value.length > 0) {
        // remove '.' before checking digits
        var tempArray = value.split('.');
        if(tempArray.length>2) return false;
        //Strip off leading '0'
        var zeroIndex = 0;
        var joinedString= tempArray.join('');
        while (joinedString.charAt(zeroIndex) == '0') {
            zeroIndex++;
        }
        var noZeroString = joinedString.substring(zeroIndex,joinedString.length);

        if (!isAllDigits(noZeroString)) {
            return false;
        } else {
	        var iValue = parseFloat(value);
	        if (isNaN(iValue)) {
	            bValid = false;
	        }
        }
    }
    return bValid;
}

function getFullDate(date) {
	return date.substring(0,4)+"?"+date.substring(4,2)+"?"+date.substring(6)+"?"
}

/*  ????: dateValue ????? YYYY/MM/DD
 *  ????:
 *		true 	??????
 *		false 	??????
 *
 *	Author	: derek
 *      Date	: 2004/09/23
 *
 */
function checkCenturyDate(dateValue) {	
        if(dateValue == null || dateValue == "" ){
            return true;
        }else{
	    var dateData = dateValue.match(/(\d{4})\/(\d{2})\/(\d{2})/);      
	    if(dateData == null ){
	          return false;
	    }else{  // ???? 0000/00/00 ???	 	
	          year = parseFloat(RegExp.$1); 
	          month = parseFloat(RegExp.$2);
	          day = parseFloat(RegExp.$3);
	          checkDate = new Date(year,(month-1),day); 	  
	          // ?????????????????????????????????????????:1?32??
	          // ???????2?1?(??????)???????if????????????????????????,
	          // ?????, ????????? 
	          if(checkDate.getFullYear() == year && checkDate.getMonth()+1 == month && checkDate.getDate() == day ){
	                   // ????
	                   return true;	   
	          }else{
	                   return false;
	          }
	        }
         }	
}	

	function isAllDigits(argvalue) {
        argvalue = argvalue.toString();
        var validChars = "0123456789";
        var startFrom = 0;
        if (argvalue.substring(0, 2) == "0x") {
           validChars = "0123456789abcdefABCDEF";
           startFrom = 2;
        } else if (argvalue.charAt(0) == "0") {
           validChars = "01234567";
           startFrom = 1;
        } else if (argvalue.charAt(0) == "-") {
            startFrom = 1;
        }

        for (var n = startFrom; n < argvalue.length; n++) {
            if (validChars.indexOf(argvalue.substring(n, n+1)) == -1) return false;
        }
        return true;
    }

	function selectAll(form, elementName) {
		var elements = elementName == null ? form.elements : form.elements[elementName];
		if (elements == null) return;
		var len = elements.length;
		if (len == null) {
			elements = [ elements ];
			len = 1;
		}
	    for (var index = 0; index < len; index++) {
	        if (elements[index].type == "checkbox") {
	            elements[index].checked = true;
	        }
		}	
	}
	
	function selectNone(form, elementName) {
		var elements = elementName == null ? form.elements : form.elements[elementName];
		if (elements == null) return;
		var len = elements.length;
		if (len == null) {
			elements = [ elements ];
			len = 1;
		}
	    for (var index = 0; index < len; index++) {
	        if (elements[index].type == "checkbox") {
	            elements[index].checked = false;
	        }
		}	
	}

	function hasCheckboxSelected(form, elementName) {
		var elements = elementName == null ? form.elements : form.elements[elementName];
		if (elements == null) return;
		var len = elements.length;
		if (len == null) {
			elements = [ elements ];
			len = 1;
		}
	    for (var index = 0; index < len; index++) {
	        if (elements[index].type == "checkbox") {
	        	if (elements[index].checked) {
	        		return true;
	        	}
	        }
		}
		return false;
	}
	
	function form_prevent_repeat_submit_onsubmit() {
		var form = event.srcElement;
		if (form.original_onsubmit != null) {
			form.detachEvent("onsubmit", form_prevent_repeat_submit_onsubmit);
			form.attachEvent("onsubmit", form.original_onsubmit);
			var cansubmit = form.fireEvent("onsubmit");
			form.detachEvent("onsubmit", form.original_onsubmit);
			form.attachEvent("onsubmit", form_prevent_repeat_submit_onsubmit);
			if (!cansubmit) {
				return false;
			}
		}
		if (form.target == "_self" || form.target == "") {
			if (form.submitted) {
				return false;
			}
			form.submitted = true;
			form_prevent_repeat_submit_disable_button();
		}
	}
	
	function form_prevent_repeat_submit_submit() {
		if (this.target == "_self" || this.target == "") {
			if (this.submitted) {
				return;
			}
			this.submitted = true;
			form_prevent_repeat_submit_disable_button();
		}
		this.original_submit();
	}
	
	function form_prevent_repeat_submit_disable_button() {
		var inputElements = document.getElementsByTagName("INPUT");
		for (var i = 0; i < inputElements.length; i++) {
			var e = inputElements(i);
			if (e.type == "button" || e.type == "submit") {
				e.disabled = "true";
			}
		}
	}
	
	function setFormPreventRepeatSubmit() {
		for (var i = 0; i < document.forms.length; i++) {
			var form = document.forms(i);
			form.original_onsubmit = form.onsubmit;
			form.onsubmit = null;
			form.attachEvent("onsubmit", form_prevent_repeat_submit_onsubmit);
			form.original_submit = form.submit;
			form.submit = form_prevent_repeat_submit_submit;
			form.submitted = false;
		}
	}

	function reverseCheckSameNameCheckBox(obj) {		
		var objs = document.getElementsByName(obj.name);
		var isChecked = obj.checked;
		for (var i = 0; i < objs.length; i++) {
			if (objs[i].checked != null) {
				objs[i].checked = !isChecked;
			}
		}
		obj.checked = isChecked;
	}