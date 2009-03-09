<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<script type="text/javascript">
<!--
	function validateForm(form) {
		if (!validateFlowForm(form)) return false;
		
		var warningMsg = '';

		switch (detectLoopOrIsland(document.all('datatable'))) {
			case -1: {
				alert('<bean:message key="flow.save.loopFound"/>'); 
				return false;
			}
			case -2: {
				warningMsg += '<bean:message key="flow.islandFound.message"/>\n';
				break;
			}
			case -3: {
				alert('<bean:message key="flow.save.noRule"/>'); 
				return false;
			}
		}
<logic:present name="X_OTHERENABLEDFLOW">
		if (form.enabled.value == "${X_ENABLED.enumCode}") {
			warningMsg += '<bean:message key="flow.newEnabled.message" arg0="${X_OTHERENABLEDFLOW.description}"/>\n';
		}
</logic:present>

		if (warningMsg.length > 0) {
			warningMsg += '<bean:message key="flow.confirm.message"/>';
			return confirm(warningMsg);
		}
		return true;
	}
	
	function selectLastOption(selObj) {
		if (selObj != null && selObj.options.length > 0) {
			selObj.options[selObj.options.length - 1].selected=true;
		}
	}
//-->
</script>
<html:javascript formName="flowForm" staticJavascript="false" />
<html:form action="insert${X_RULETYPE.prefixUrl}Flow.do" onsubmit="return validateForm(this);">
<html:hidden property="site_id"/>
<table width="100%" border="0" cellpadding="4" cellspacing="0">
<tr>
  <td class="bluetext" width="20%"><bean:message key="flow.site"/>:</td>
  <td><bean:write name="flowForm" property="site_name"/></td>
</tr>
<tr>
  <td class="bluetext" width="20%"><bean:message key="flow.id"/>:</td>
  <td><bean:message key="common.id.generateBySystem"/></td>
</tr>
<tr>
  <td class="bluetext" width="20%"><bean:message key="flow.description"/>:</td>
  <td><html:text property="description" size="60"/><span class="required">*</span></td>
</tr>
<tr>
  <td class="bluetext" width="20%"><bean:message key="flow.enabled"/>:</td>
  <td>
    <html:select property="enabled">
      <c:if test="${sessionScope.LOGIN_USER.locale=='en'}"><html:options collection="X_ENABLEDDISABLEDLIST" property="enumCode" labelProperty="engShortDescription"/></c:if>
      <c:if test="${sessionScope.LOGIN_USER.locale!='en'}"><html:options collection="X_ENABLEDDISABLEDLIST" property="enumCode" labelProperty="chnShortDescription"/></c:if>
    </html:select>
  </td>
</tr>
</table>
<hr/>
<script language="javascript" src="includes/table.js"></script>
<script language="javascript" src="includes/selection.js"></script>
<script type="text/javascript">
<!--
	var rowCount = ${X_FLOWRULESIZE};
	var ruleArray = [];
	var seqArray = [];
	var nextSeqArray = [ new Option('<bean:message key="flow.next.end"/>', 0) ];

<logic:iterate id="r" name="X_RULELIST">
	ruleArray[ruleArray.length] = new Option("${r.description}", "${r.id}");
</logic:iterate>

	for (var i = 2; i <= rowCount; i++) {
		nextSeqArray[nextSeqArray.length] = new Option(i, i);
	}
	
	for (var i = 1; i <= rowCount + 1; i++) {
		seqArray[seqArray.length] = new Option(i, i);
	}

	function initRow(row, seq, rule, nextSeqPass, nextSeqFail) {
		var cell = row.insertCell();
		cell.width = '10%';
		cell.innerHTML = '<input type="hidden" name="fr_seq"/><span id="span_fr_seq"></span>';

		cell = row.insertCell();
		cell.width = '60%';
		cell.innerHTML = '<select name="fr_rule"/>';

		cell = row.insertCell();
		cell.width = '10%';
		cell.innerHTML = '<select name="fr_seq_pass"/>';

		cell = row.insertCell();
		cell.width = '10%';
		cell.innerHTML = '<select name="fr_seq_fail"/>';

		cell = row.insertCell();
		cell.width = '10%';
		cell.innerHTML = '<a href="javascript:void(0)" onclick="deleteRow(this.parentNode.parentNode);"><bean:message key="all.delete"/></a>';

		initSelect(row.all('fr_rule'), ruleArray, rule);
		initSelect(row.all('fr_seq_pass'), nextSeqArray, nextSeqPass);
		initSelect(row.all('fr_seq_fail'), nextSeqArray, nextSeqFail);
		row.all('fr_seq').value = seq;
		row.all('span_fr_seq').innerText = seq;
	}
	
	function insertRow(table, seq, rule, nextSeqPass, nextSeqFail) {
		rowCount++;

		var row = table.insertRow(seq - 1);

		initRow(row, seq, rule, nextSeqPass, nextSeqFail);
		
		for (var i = 0; i < table.rows.length; i++) {
			var index = i + 1;
			row = table.rows[i];
			if (index > seq) {
				row.all('fr_seq').value = index;
				row.all('span_fr_seq').innerText = index;
			}
			if (rowCount > 1) {
				increaseSelect(row.all('fr_seq_pass'), rowCount, seq);
				increaseSelect(row.all('fr_seq_fail'), rowCount, seq);
			}
		}
		increaseSelect(document.all('tfr_seq'), rowCount + 1);
		selectLastOption(document.all('tfr_seq'));
		if (rowCount > 1) {
			nextSeqArray[nextSeqArray.length] = new Option(rowCount, rowCount);
		}
		applyRowStyle(table);
	}
	
	function deleteRow(row) {
		rowCount--;

		var table = row.parentNode;
		var seq = parseInt(row.all('fr_seq').value);
		table.deleteRow(row.sectionRowIndex);

		for (var i = 0; i < table.rows.length; i++) {
			var index = i + 1;
			row = table.rows[i];
			row.all('fr_seq').value = index;
			row.all('span_fr_seq').innerText = index;
			decreaseSelect(row.all('fr_seq_pass'), seq);
			decreaseSelect(row.all('fr_seq_fail'), seq);
		}
		var tfr_seq = document.all('tfr_seq');
		if (tfr_seq.selectedIndex == tfr_seq.options.length - 1) tfr_seq.selectedIndex--;
		tfr_seq.options.length--;
		if (rowCount > 0) nextSeqArray.length--;
	}
	
	function increaseSelect(selObj, max, seq) {
		selObj.options[selObj.options.length] = new Option(max, max);
		if (seq != null && selObj.selectedIndex > 0 && selObj.selectedIndex >= seq - 1) selObj.selectedIndex++;
	}
	
	function decreaseSelect(selObj, seq) {
		if (selObj.value > seq) {
			selObj.selectedIndex--;
		} else if (selObj.value == seq) {
			selObj.selectedIndex = 0;
		}
		selObj.options.length--;
	}
	
	/*
	 * return: -1 loop found
	 *         -2 no loop, but island found
	 *         -3 flow empty
	 *          0 no loop and island
	 */
	function detectLoopOrIsland(table) {
		if (rowCount == 0) return -3;
		var nodeArray = [];
		var stack = new Stack();
		
		nodeArray[0] = createNode(table.rows[0]);
		stack.push(nodeArray[0]);

		for (var i = 1; i < rowCount; i++) {
			nodeArray[i] = null;
		}

		while (!stack.isEmpty()) {
			var node = stack.pop();
			var next = node.next;
			for (var i = 0; i < next.length; i++) {
				node = nodeArray[next[i]];
				if (node == null) {
					node = createNode(table.rows[next[i]]);
					nodeArray[next[i]] = node;
					stack.push(node);
				}
				node.inDegree++;
			}
		}
		
		stack.push(nodeArray[0]);
		while (!stack.isEmpty()) {
			var node = stack.pop();
			var next = node.next;
			for (var i = 0; i < next.length; i++) {
				var nextNode = nodeArray[next[i]];
				nextNode.inDegree--;
				if (nextNode.inDegree == 0) stack.push(nextNode);
			}
		}

		var foundIsland = false;
		for (var i = 1; i < rowCount; i++) {
			var node = nodeArray[i];
			if (node == null) {
				foundIsland = true;
			} else {
				if (node.inDegree > 0) return -1;
			}
		}
		return foundIsland ? -2 : 0;
	}
	
	function createNode(row) {
		var node = new Object();
		node.inDegree = 0;
		var nextSeq = row.all('fr_seq_pass').selectedIndex;
		var next = [];
		if (nextSeq > 0) next[next.length] = nextSeq;
		nextSeq = row.all('fr_seq_fail').selectedIndex;
		if (nextSeq > 0) next[next.length] = nextSeq;
		node.next = next;
		return node;
	}
	
	function detect() {
		switch (detectLoopOrIsland(document.all('datatable'))) {
			case -1: alert('<bean:message key="flow.detect.loopFound"/>'); break;
			case -2: alert('<bean:message key="flow.detect.islandFound"/>'); break;
			case -3: alert('<bean:message key="flow.detect.noRule"/>'); break;
			case  0: alert('<bean:message key="flow.detect.ok"/>'); break;
		}
	}
//-->
</script>
<table>
  <tr>
    <td class="bluetext"><h3><bean:message key="flowrule.title"/></he></td>
  </tr>
  <tr>
    <td align="center"><input type="button" value="<bean:message key="flowrule.button.add"/>" onclick="insertRow(document.all('datatable'), document.all('tfr_seq').value, document.all('tfr_rule').value, 0, 0);"/>
    &nbsp;<bean:message key="flowrule.addto"/>&nbsp;
    <select name="tfr_seq"></select>
    </td>
  </tr>
</table>
<div style="width:680px">
  <table width="660px" style="border:1px solid #666">
      <tr class="new_bg">
        <th width="10%"><bean:message key="flowrule.seq"/></th>
        <th width="60%"><bean:message key="flowrule.rule"/></th>
        <th width="10%"><bean:message key="flowrule.nextSeqWhenPass"/></th>
        <th width="10%"><bean:message key="flowrule.nextSeqWhenFail"/></th>
        <th width="10%"></th>
      </tr>
  </table>
   <table style="display:none">
      <tr>
        <td></td>
        <td><select name="tfr_rule"/></td>
        <td><bean:message key="flow.next.end"/></td>
        <td><bean:message key="flow.next.end"/></td>
        <td align="right"><input type="button" value="<bean:message key="all.new"/>" onclick="insertRow(document.all('datatable'), document.all('tfr_seq').value, document.all('tfr_rule').value, 0, 0);"/></td>
      </tr>
   </table>
  <div style="width:676px;height:264px;overflow-y:auto;">
    <table width="660px" style="border:1px solid #666">
      <tbody id="datatable">
      </tbody>
    </table>
  </div>
</div>
<table width="100%" border="0" cellpadding="4" cellspacing="0">
<tr>
  <td>
    <input type="button" value="<bean:message key="flow.detect"/>" onclick="detect();"/>
  </td>
  <td align="right">
  	<html:submit><bean:message key="all.save"/></html:submit>
  	<input type="button" value="<bean:message key="all.cancel"/>" onclick="window.parent.close();"/>
  </td>
</tr>
</table>
</html:form>
<div id="rowTemplate" style="display:none">
  <table id="resultTable">
    <tbody>
      <jsp:include page="row.jsp"/>
    </tbody>
  </table>
</div>
<script type="text/javascript">
	initSelect(document.all('tfr_seq'), seqArray);
	selectLastOption(document.all('tfr_seq'));
	initSelect(document.all('tfr_rule'), ruleArray);
	var table = document.all('datatable');
<logic:iterate id="X_FR" name="X_FLOWRULES">
	initRow(table.insertRow(), ${X_FR.seq}, ${X_FR.rule.id}, ${X_FR.nextSeqWhenPass}, ${X_FR.nextSeqWhenFail});
</logic:iterate>
	applyRowStyle(table);
</script>
