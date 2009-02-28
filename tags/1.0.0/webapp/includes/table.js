/*
	oldRow -> the row to update
	v -> string, must contain a table with id resultTable and at least one row
*/
function updateRow(oldRow, v) {
	var buffer = document.createElement("div");
	buffer.innerHTML = v;
	var srcRow = buffer.all('resultTable').rows[0];
	var dstRow = oldRow.parentNode.insertRow(oldRow.sectionRowIndex);
	// copy srcRow -> dstRow
	dstRow.id = srcRow.id;
	for (var i = 0; i < srcRow.cells.length; i++) {
		var srcCell = srcRow.cells[i];
		var dstCell = dstRow.insertCell();
		dstCell.innerHTML = srcCell.innerHTML;
		dstCell.className = srcCell.className;
		dstCell.align = srcCell.align;

	}
	dstRow.className = oldRow.className;
	oldRow.parentNode.deleteRow(oldRow.sectionRowIndex);
}

/*
	tbody -> the tbody to append row
	v -> string, must contain a table with id resultTable and at least one row
*/
function appendRow(tbody, v) {
	var buffer = document.createElement("div");
	buffer.innerHTML = v;
	var srcRow = buffer.all('resultTable').rows[0];
	var dstRow = tbody.insertRow(-1);
	// copy srcRow -> dstRow
	dstRow.id = srcRow.id;
	for (var i = 0; i < srcRow.cells.length; i++) {
		var srcCell = srcRow.cells[i];
		var dstCell = dstRow.insertCell();
		dstCell.innerHTML = srcCell.innerHTML;
		dstCell.className = srcCell.className;
		dstCell.align = srcCell.align;
	}
}

/*
	oldRow -> the row to delete
*/
function deleteRow(oldRow) {
	oldRow.parentNode.deleteRow(oldRow.sectionRowIndex);
}

function clearRow(tbody) {
	while(tbody != null && tbody.rows.length > 0) {
		tbody.deleteRow(0);
	}
}

/*
	tbody -> the tbody to refresh
	v -> string, must contain a table with id resultTable
*/
function refreshTable(tbody, v) {
	var buffer = document.createElement("div");
	buffer.innerHTML = v;
	while (tbody.rows.length > 0) tbody.deleteRow(0);
	var srcRows = buffer.all('resultTable').rows;
	for (var j = 0; j < srcRows.length; j++) {
		var srcRow = srcRows[j];
		var dstRow = tbody.insertRow(-1);
		// copy srcRow -> dstRow
		dstRow.id = srcRow.id;
		for (var i = 0; i < srcRow.cells.length; i++) {
			var srcCell = srcRow.cells[i];
			var dstCell = dstRow.insertCell();
			dstCell.innerHTML = srcCell.innerHTML;
			dstCell.className = srcCell.className;
			dstCell.align = srcCell.align;
		}
	}
}

/*
	apply odd and even row style
*/
function applyRowStyle(tbody) {
	var rows = tbody.rows;
	for (var i = 0; i < rows.length; i++) {
		rows[i].className = (i % 2 == 0) ? 'odd' : 'even';
	}
}
