function XmlTree(strPath) {
	this.path = strPath;
}

XmlTree.prototype.buildTree = function(objContainer, xmlTreeIsland, xslIsland, objPopup) {
	this.container = objContainer;

	this.openImg = new Image();
	this.closedImg = new Image();
	this.openImg.src = this.path + "/open.gif";
	this.closedImg.src = this.path + "/closed.gif";
	
	if (objPopup != null) {
		this.popup = objPopup;
		if (this.popup != null) {
			this.popup.show(0, 0, 0, 0);
			this.popupWidth = this.popup.document.body.scrollWidth;
			this.popupHeight = this.popup.document.body.scrollHeight;
			this.popup.hide();
		}
	}

	var source = new ActiveXObject("Microsoft.XMLDOM");
	var stylesheet = new ActiveXObject("Microsoft.XMLDOM");
	source.loadXML(xmlTreeIsland.innerHTML);
	stylesheet.loadXML(xslIsland.innerHTML);
	this.container.innerHTML = source.transformNode(stylesheet);
	
	var children = this.container.all;
	for (var i = 0; i < children.length; i++) {
		var obj = children.item(i);
		var c = obj.id;
		if (c != null) c = c.charAt(0);
		switch (c) {
		case 'I':
			obj.scriptRootObject = this;
			if (obj._src == 'closed.gif') {
				this.addEvent(obj, "click", __XmlTree_SwapBranch);
			}
			obj.src = this.path + '/' + obj._src;
			break;
		case 'N':
			obj.scriptRootObject = this;
			this.addEvent(obj, "click", __XmlTree_SelectItem);
			this.addEvent(obj, "dblclick", __XmlTree_HandleDblClick);
			this.addEvent(obj, "mouseover", __XmlTree_HandleMouseOver);
			this.addEvent(obj, "mouseout", __XmlTree_HandleMouseOut);
			this.addEvent(obj, "mousedown", __XmlTree_HandleMouseDown);
			if (this.popup != null) {
				this.addEvent(obj, "contextmenu", __XmlTree_HandleContextMenu);
			}
			break;
		}
	}
}

XmlTree.prototype.addEvent = function(obj, evType, fn) {
	if (obj.addEventListener) {
		obj.addEventListener(evType, fn, true);
		return true;
	}
	if (obj.attachEvent) {
		var r = obj.attachEvent("on" + evType, fn);
		return r;
	}
	return false;
}

XmlTree.prototype.removeEvent = function(obj, evType, fn, useCapture) {
	if (obj.removeEventListener) {
		obj.removeEventListener(evType, fn, useCapture);
		return true;
	} else if (obj.detachEvent){
		var r = obj.detachEvent("on" + evType, fn);
		return r;
	} else {
		alert("Handler could not be removed");
	}
}

XmlTree.prototype.showBranch = function(objBranch) {
    if (objBranch.style.display != "block") {
        objBranch.style.display="block";
        var objImg = this.container.all('I' + objBranch._id);
        objImg.src = this.openImg.src;
    }
}

XmlTree.prototype.swapFolder = function(img) {
    var objImg = this.container.all(img);
    if (objImg.src == this.closedImg.src)
        objImg.src = this.openImg.src;
    else
        objImg.src = this.closedImg.src;
}

XmlTree.prototype.setActiveItem = function(id) {
	var obj = this.container.all('N' + id);
	if (obj == null) return;
    var p = obj.parentNode;
    var desc = this.getDesc(p);
	this.setActiveNode(obj);
    this.activeNodeID = id;
    this.activeNodePathDesc = desc;
}
    
XmlTree.prototype.getDesc = function(o) {
    var p = o.parentNode;
    if (p.className == "branch") {
    	this.showBranch(p);
        return this.getDesc(p) + "\\" + o.desc;
    }
    return o.desc;
}

XmlTree.prototype.setActiveNode = function(obj) {
    if (this.activeNode != null) {
    	this.activeNode.className = "clsNormal";
    }
    obj.className = "clsActive";
    this.activeNode = obj;
}

XmlTree.prototype.swapBranch = function(id) {
    var objBranch = this.container.all('B' + id).style;
    if (objBranch.display == "block")
        objBranch.display="none";
    else
        objBranch.display="block";
    this.swapFolder('I' + id);
}

XmlTree.prototype.selectItem = function(obj) {
    var p = obj.parentNode;
    var desc = this.getDesc(p);
	this.setActiveNode(obj);
    this.activeNodeID = obj._id;
    this.activeNodePathDesc = desc;
}

XmlTree.prototype.addNode = function(id, desc, parentId) {
	var node = document.createElement('div');
	node.className = 'node';
	node.desc = desc;
	var img = document.createElement('img');
	img.id = 'I' + id;
	img._id = id;
	img.src = this.path + '/doc.gif';
	img.width = img.height = '11';
	img.align = 'absmiddle';
	img.style.marginRight = '5px';
	node.appendChild(img);
	var span = document.createElement('span');
	span.id = 'N' + id;
	span._id = id;
	span.className = 'clsNormal';
	span.scriptRootObject = this;
	this.addEvent(span, 'click', __XmlTree_SelectItem);
	this.addEvent(span, 'dblclick', __XmlTree_HandleDblClick);
	this.addEvent(span, 'mouseover', __XmlTree_HandleMouseOver);
	this.addEvent(span, 'mouseout', __XmlTree_HandleMouseOut);
	this.addEvent(span, 'mousedown', __XmlTree_HandleMouseDown);
	if (this.popup != null) {
		this.addEvent(span, 'contextmenu', __XmlTree_HandleContextMenu);
	}
	span.innerText = desc;
	node.appendChild(span);
	if (parentId == null) {
		var root = this.container.all('treeroot');
		root.appendChild(node);
	} else {
		var branch = this.container.all('B' + parentId);
		if (branch == null) {
			img = this.container.all('I' + parentId);
			if (img != null) {
				img.src = this.path + '/open.gif';
				img.scriptRootObject = this;
				this.addEvent(img, 'click', __XmlTree_SwapBranch)
			}
			var root = this.container.all('N' + parentId);
			if (root == null) return;
			root = root.parentNode;
			branch = document.createElement('span');
			branch.className = 'branch';
			branch.id = 'B' + parentId;
			branch._id = parentId;
			branch.desc = root.desc;
			if (root.nextSibling == null) {
				root.parentNode.appendChild(branch);
			} else {
				root.parentNode.insertBefore(branch, root.nextSibling);
			}
		}
		branch.appendChild(node);
	}
	this.setActiveItem(parseInt(id));
}

XmlTree.prototype.editNode = function(id, desc, parentId) {
	var obj = this.container.all('N' + id);
	if (obj == null) return;
	obj.innerText = desc;
	obj.parentNode.desc = desc;
	var bobj = this.container.all('B' + id);
	if (bobj != null) bobj.desc = desc;
	var node = obj.parentNode;
	var pp = node.parentNode;
	var pid = pp._id;
	if (pid != parentId) {
		// remove from old parent
		var s = node.nextSibling;
		pp.removeChild(node);
		if (s != null && s.className == 'branch') {
			pp.removeChild(s);
		} else {
			s = null;
		}
		if (pp.className == 'branch' && pp.firstChild == null) {
			var pid = pp._id;
			pp.parentNode.removeChild(pp);
			var img = this.container.all('I' + pid);
			if (img != null) {
				img.src = this.path + '/doc.gif';
				this.removeEvent(img, 'click', __XmlTree_SwapBranch);
			}
		}
		
		//add to new parent
		if (parentId == null) {
			var root = this.container.all('treeroot');
			root.appendChild(node);
			if (s != null) root.appendChild(s);
		} else {
			var branch = this.container.all('B' + parentId);
			if (branch == null) {
				img = this.container.all('I' + parentId);
				if (img != null) {
					img.src = this.path + '/open.gif';
					img.scriptRootObject = this;
					this.addEvent(img, 'click', __XmlTree_SwapBranch)
				}
				var root = this.container.all('N' + parentId);
				if (root == null) return;
				root = root.parentNode;
				branch = document.createElement('span');
				branch.className = 'branch';
				branch.id = 'B' + parentId;
				branch._id = parentId;
				branch.desc = root.desc;
				if (root.nextSibling == null) {
					root.parentNode.appendChild(branch);
				} else {
					root.parentNode.insertBefore(branch, root.nextSibling);
				}
			}
			branch.appendChild(node);
			if (s != null) branch.appendChild(s);
		}
	}
	this.setActiveItem(id);
}

XmlTree.prototype.deleteNode = function(id) {
	var obj = this.container.all('N' + id);
	if (obj == null) return;
	var p = obj.parentNode;
	var pp = p.parentNode;
	var s = p.nextSibling;
	pp.removeChild(p);
	if (s != null && s.className == 'branch') {
		pp.removeChild(s);
	}
	if (pp.className == 'branch' && pp.firstChild == null) {
		var pid = pp._id;
		pp.parentNode.removeChild(pp);
		var img = this.container.all('I' + pid);
		if (img != null) {
			img.src = this.path + '/doc.gif';
			this.removeEvent(img, 'click', __XmlTree_SwapBranch);
		}
	}
	if (this.activeNode == obj) {
		this.activeNode = null;
		this.activeNodeID = null;
		this.activeNodeDesc = null;
	}
}

XmlTree.prototype.handleDblClick = function(obj) {
	this.selectItem(obj); 
	if (this.returnValue) this.returnValue();
}

XmlTree.prototype.handleContextMenu = function(obj, x, y) {
	this.selectItem(obj);
	if (this.popup != null) this.showPopup(x, y, obj);
}

XmlTree.prototype.handleMouseOver = function(obj) {
	obj.className = "clsMouseOver";
}

XmlTree.prototype.handleMouseDown = function(obj) {
	obj.className = "clsMouseDown";
}

XmlTree.prototype.handleMouseOut = function(obj) {
	if (obj == this.activeNode) {
		obj.className = "clsActive";
	} else {
		obj.className = "clsNormal";
	}
}

XmlTree.prototype.showPopup = function(x, y, obj) {
	this.popup.show(x, y, this.popupWidth, this.popupHeight, document.body);
}

__XmlTree_SwapBranch = function() {
	var obj = event.srcElement;
	var tree = obj.scriptRootObject;
	var id = obj._id;
	tree.swapBranch(id);
}

__XmlTree_SelectItem = function() {
	var obj = event.srcElement;
	var tree = obj.scriptRootObject;
	tree.selectItem(obj);
}

__XmlTree_HandleDblClick = function() {
	var obj = event.srcElement;
	var tree = obj.scriptRootObject;
	tree.handleDblClick(obj); 
}

__XmlTree_HandleContextMenu = function() {
	var obj = event.srcElement;
	var tree = obj.scriptRootObject;
	if (tree.popup != null) {
		tree.handleContextMenu(obj, event.offsetX + 20, event.offsetY + 5); 
		event.returnValue = false;
	}
}

__XmlTree_HandleMouseOver = function() {
	var obj = event.srcElement;
	var tree = obj.scriptRootObject;
	tree.handleMouseOver(obj);
}

__XmlTree_HandleMouseDown = function() {
	var obj = event.srcElement;
	var tree = obj.scriptRootObject;
	tree.handleMouseDown(obj);
}

__XmlTree_HandleMouseOut = function() {
	var obj = event.srcElement;
	var tree = obj.scriptRootObject;
	tree.handleMouseOut(obj);
}
