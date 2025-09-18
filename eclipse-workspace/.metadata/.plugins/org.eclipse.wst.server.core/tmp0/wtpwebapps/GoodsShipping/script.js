document.getElementById("addShipmentButton").onclick = function() {
	document.getElementById("addShipmentFormModal").style.display = "block";
	document.getElementById("action").value = "add";
	let lastDocno = document.getElementById("documentnos").value.split(",");
	if (lastDocno.length != 0) {
		let no = Number(lastDocno.reverse()[0].substring(2)) + 1;
		document.getElementById("document_no").defaultValue = "GS" + String(no).padStart(3, "0");
	} else {
		document.getElementById("document_no").defaultValue = "GS001";
	}
	document.getElementById("document_date").defaultValue = "";
	const selectedIndex = document.getElementById('customer_id').selectedIndex;
	const selectedOption = document.getElementById('customer_id').options[selectedIndex];
	selectedOption.removeAttribute("selected")
	const customerId = document.getElementById("customer_id").value;
	console.log(self.crypto.randomUUID());
	console.log(typeof self.crypto.randomUUID());
};

document.getElementById("deleteShipmentButton").onclick = function() {
	deleteSelectedRows();
};

document.querySelector(".close").onclick = function() {
	document.getElementById("addShipmentForm").reset();
	clearLineInputs();
	const lineTableBody = document.getElementById("lineTable").getElementsByTagName("tbody")[0];

	while (lineTableBody && lineTableBody.rows.length > 0) {
		lineTableBody.deleteRow(0);
	}
	document.getElementById("addShipmentFormModal").style.display = "none";
	revertToAddLine();
};

document.querySelector(".close-view").onclick = function() {
	document.getElementById("viewShipmentModal").style.display = "none";
};

document.querySelector(".close-notification").onclick = function() {
	document.getElementById("notificationModal").style.display = "none";
};

document.getElementById("addLineButton").onclick = function() {
	addLine();
};

document.getElementById("cancelLineButton").onclick = function() {
	clearLineInputs();
};

document.getElementById("addShipmentForm").onsubmit = function(event) {
	event.preventDefault();
	submitShipment();
	revertToAddLine();
};

let currentEditRow = null;
let currentEditCombinedRow = null;
let rowToDelete = null;
let isShipmentLineDelete = false;
var shipmentLines = document.getElementById("shipmentLines");
var lineTableRows = document.getElementById("lineTable").getElementsByTagName("tbody")[0];

//adding shipmentlines
function addLine() {
	const productId = document.getElementById("product_id").value;
	const quantity = document.getElementById("quantity").value;
	if (!productId || !quantity) {
		alert("Please fill in both product ID and quantity.");
		return;
	}

	if (quantity <= 0) {
		alert("Please enter a quantity greater than 0.");
		return;
	}

	const lineTable = document.getElementById("lineTable").getElementsByTagName("tbody")[0];
	const existingRows = lineTable.getElementsByTagName("tr");
	for (let i = 0; i < existingRows.length; i++) {
		const existingProductId = existingRows[i].cells[0].innerText.trim();
		if (existingProductId === productId && existingRows[i] !== currentEditRow) {
			alert(`Product ID ${productId} already exists. Please edit the existing entry.`);
			return;
		}
	}
	if (currentEditRow) {
		currentEditRow.cells[0].innerText = productId;
		currentEditRow.cells[1].innerText = quantity;
		currentEditRow = null;
		document.getElementById("addLineButton").innerText = "Add Line";
	} else {
		const newRow = lineTable.insertRow();
		newRow.insertCell(0).innerText = productId;
		newRow.insertCell(1).innerText = quantity;

		const actionCell = newRow.insertCell(2);
		actionCell.appendChild(createButton('edit-btn', '<button type="button" class="icon-btn"><i class="fas fa-edit"></i></button>', () => editLine(newRow)));
		actionCell.appendChild(createButton('delete-btn', '<button type="button" class="icon-btn"><i class="fas fa-trash"></i></button>', () => deleteShipmentLine(newRow)));
	}

	clearLineInputs();
}

function createButton(className, iconHtml, clickHandler) {
	const button = document.createElement("button");
	button.className = `action-btn ${className}`;//creating action-btn
	button.type = "button";
	button.innerHTML = iconHtml;
	button.onclick = clickHandler;
	return button;
}

function editLine(row) {
	currentEditRow = row;
	document.getElementById("product_id").value = row.cells[0].innerText;
	document.getElementById("quantity").value = row.cells[1].innerText;
	document.getElementById("addLineButton").innerText = "Update Line";
	const cancelButton = document.getElementById("cancelLineButton");
	cancelButton.onclick = function() {
		clearLineInputs();
		revertToAddLine();
	};

}


function revertToAddLine() {
	const actionButton = document.getElementById("addLineButton");
	actionButton.textContent = "Add Line";
	actionButton.onclick = function() {
		addLine();
	};
	console.log("Reverted to Add Line mode.");
}

function deleteShipmentLine(row) {
	const productId = row.cells[0].innerText;
	const quantity = row.cells[1].innerText;

	document.getElementById("deleteConfirmationMessage").innerText =
		`Are you sure you want to delete this shipment line with Product ID: ${productId} and Quantity: ${quantity}?`;

	document.getElementById("deleteConfirmationModal").style.display = "block";

	rowToDelete = row;
	isShipmentLineDelete = true;
}

function clearLineInputs() {
	document.getElementById("product_id").value = '';
	document.getElementById("quantity").value = '';


}

// Submit the shipment form
function submitShipment() {
	const documentNo = document.getElementById("document_no").value;
	const documentDate = document.getElementById("document_date").value;
	const customerId = document.getElementById("customer_id").value;
	const createdBy = "22";
	const updatedBy = "22";

	const lineTable = document.getElementById("lineTable").getElementsByTagName("tbody")[0];
	const lineRows = lineTable.rows;

	if (lineRows.length === 0) {
		showNotification("Please add at least one product line.");
		return;
	}

	var shipmentLinesRequest = "";
	const products = [];
	for (let row of lineRows) {
		const productId = row.cells[0].innerText;
		const quantity = row.cells[1].innerText;
		shipmentLinesRequest += row.cells[0].innerText + "?" + row.cells[1].innerText + "|"
		products.push({ productId, quantity });
	}

	var action = document.getElementById("action").value;
	var inoutid = "";

	if (currentEditCombinedRow) {
		currentEditCombinedRow.cells[1].innerText = documentNo;
		currentEditCombinedRow.cells[2].innerText = documentDate;
		currentEditCombinedRow.cells[3].innerText = customerId;
		currentEditCombinedRow.cells[4].innerText = createdBy;
		currentEditCombinedRow.cells[5].innerText = updatedBy;
		currentEditCombinedRow.dataset.lines = JSON.stringify(products);
		inoutid = "&inout_id=" + currentEditCombinedRow.id.replace('row-', '');
		document.getElementById("ln-" + currentEditCombinedRow.id.replace('row-', '')).value = shipmentLinesRequest;
		currentEditCombinedRow = null;


	} else {
		const shipmentRecords = document.getElementById("shipmentRecords").getElementsByTagName("tbody")[0];
		let uuid;
		do {
			uuid = self.crypto.randomUUID().replaceAll('-', '');
		} while (Array.from(shipmentRecords.rows).some(row => row.id === `row-${uuid}`));
		const newRow = shipmentRecords.insertRow();
		newRow.id = `row-${uuid}`;
		newRow.insertCell(0).innerHTML = `<input type="checkbox" class="shipmentRowcheckbox" onclick="setSelectAllCheckBox()" >`;
		newRow.insertCell(1).innerText = documentNo;
		newRow.insertCell(2).innerText = documentDate;
		newRow.insertCell(3).innerText = customerId;
		newRow.insertCell(4).innerText = "22";
		newRow.insertCell(5).innerText = "22";
		newRow.dataset.lines = JSON.stringify(products);

		inoutid = "&inout_id=" + newRow.id.replace('row-', '');

		// Create a div to contain the buttons
		const actionCell = newRow.insertCell(6);
		const buttonContainer = document.createElement('div');
		buttonContainer.style.display = 'flex';
		buttonContainer.style.gap = '8px';
		buttonContainer.style.justifyContent = 'center';
		buttonContainer.style.alignItems = 'center';

		const lines = document.createElement('input');
		lines.id = "ln-" + newRow.id.replace('row-', '');
		lines.type = "hidden";
		lines.value = shipmentLinesRequest;

		buttonContainer.append(lines);
		buttonContainer.appendChild(createButton('edit-btn', '<button><i class="fas fa-edit"></i></button>', () => editrow(newRow)));
		buttonContainer.appendChild(createButton('view-btn', '<button><i class="fas fa-eye"></i></button>', () => viewShipment(newRow)));
		buttonContainer.appendChild(createButton('delete-btn', '<button><i class="fas fa-trash"></i></button>', () => deleteParticularRows(newRow.id.replace('row-', ''), documentNo)));

		actionCell.appendChild(buttonContainer);
		if (document.getElementById("documentnos").value) {
			document.getElementById("documentnos").value += "," + documentNo;

		}
		else {
			document.getElementById("documentnos").value += documentNo;
		}


	}

	var params = `action=${(action)}${(inoutid)}&documentNo=${(documentNo)}&documentDate=${documentDate}&customerId=${customerId}&createdBy=${createdBy}&updatedBy=${updatedBy}&shipmentLinesRequest=${(shipmentLinesRequest)}`;

	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {
		if (xhr.readyState == 4 && xhr.status == 200) {
			var data = xhr.responseText;
			console.log(xhr.responseText);

		}

	};

	xhr.open("POST", "GoodsShipping", true);
	xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

	xhr.send(params);


	document.getElementById("addShipmentForm").reset();
	clearLineInputs();
	const lineTableBody = document.getElementById("lineTable").getElementsByTagName("tbody")[0];

	while (lineTableBody && lineTableBody.rows.length > 0) {
		lineTableBody.deleteRow(0);
	}
	document.getElementById("addShipmentFormModal").style.display = "none";
}

// Edit the shipment row
function editrow(row) {
	currentEditCombinedRow = row;
	const documentNo = row.cells[1].innerText;
	const documentDate = row.cells[2].innerText;
	const customerId = row.cells[3].innerText;
	const ln = document.getElementById("ln-" + currentEditCombinedRow.id.replace('row-', '')).value;

	document.getElementById("document_no").defaultValue = documentNo;
	document.getElementById("document_date").defaultValue = documentDate;
	document.getElementById("customer_id").value = customerId;

	const selectedIndex = document.getElementById('customer_id').selectedIndex;
	const selectedOption = document.getElementById('customer_id').options[selectedIndex];
	selectedOption.setAttribute("selected", "selected");

	const products = ln.split("|");
	const lineTable = document.getElementById("lineTable").getElementsByTagName("tbody")[0];
	lineTable.innerHTML = '';

	products.forEach(product => {
		if (!product.includes("?")) return;
		let productTemp = product.split("?");
		const newRow = lineTable.insertRow();
		newRow.insertCell(0).innerText = productTemp[0];
		newRow.insertCell(1).innerText = productTemp[1];

		const actionCell = newRow.insertCell(2);
		actionCell.appendChild(createButton('edit-btn', '<i class="fas fa-edit"></i>', () => editLine(newRow)));
		actionCell.appendChild(createButton('delete-btn', '<i class="fas fa-trash"></i>', () => deleteShipmentLine(newRow)));
	});


	document.getElementById("addShipmentFormModal").style.display = "block";
	document.getElementById("action").value = "edit";
}

// View the shipment row
function viewShipment(row) {
	console.log("Row data-lines:", row.dataset.lines);
	const documentNo = row.cells[1].innerText;
	const documentDate = row.cells[2].innerText;
	const customerId = row.cells[3].innerText;

	document.getElementById("viewDocumentNo").value = documentNo;
	document.getElementById("viewDocumentDate").value = documentDate;
	document.getElementById("viewCustomerId").value = customerId;

	const products = document.getElementById("ln-" + row.id.replace('row-', '')).value.split("|");
	const viewLineTable = document.getElementById("viewLineTable").getElementsByTagName("tbody")[0];
	viewLineTable.innerHTML = '';

	products.forEach(product => {
		if (!product.includes("?")) return;
		let productTemp = product.split("?");
		const newRow = viewLineTable.insertRow();
		newRow.insertCell(0).innerText = productTemp[0];
		newRow.insertCell(1).innerText = productTemp[1];
	});

	document.getElementById("viewShipmentModal").style.display = "block";
}

function deleteShipmentRow(row) {
	const documentNo = row.cells[1].innerText;
	const customerId = row.cells[3].innerText;

	document.getElementById("deleteConfirmationMessage").innerText =
		`Are you sure you want to delete this shipment with Document No: ${documentNo} and Customer ID: ${customerId}?`;

	document.getElementById("deleteConfirmationModal").style.display = "block";
	rowToDelete = row;
	isShipmentLineDelete = false;
}

function confirmDelete() {
	const shipmentRecords = document.getElementById("shipmentRecords").getElementsByTagName("tbody")[0];
	const lineTable = document.getElementById("lineTable").getElementsByTagName("tbody")[0];

	if (isShipmentLineDelete) {
		lineTable.deleteRow(rowToDelete.rowIndex - 1);
		showNotification(`Shipment line with Product ID: ${rowToDelete.cells[0].innerText} has been deleted.`);
	} else {
		shipmentRecords.deleteRow(rowToDelete.rowIndex - 1);
		showNotification(`Shipment with Customer ID: ${rowToDelete.cells[3].innerText} and Document No: ${rowToDelete.cells[1].innerText} has been deleted.`);
	}

	rowToDelete = null;
	closeDeleteConfirmation();
}

//delete Particularrows
function deleteParticularRows(inoutId, docno) {

	var userConfirmed = confirm(`Are you sure you want to delete this shipment with Document No ${docno}?`);

	if (userConfirmed) {
		var action = "delete";
		var xhr = new XMLHttpRequest();
		var params = `action=${action}&inoutId=${encodeURIComponent(inoutId)}`;

		xhr.open("POST", "GoodsShipping", true);
		xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
		xhr.send(params);

		xhr.onload = function() {
			var messageElement = document.getElementById("message");
			if (xhr.status === 200) {
				console.log("Row deleted successfully");

				var row = document.getElementById(`row-${inoutId}`);
				if (row) {
					row.remove();
				}
				messageElement.textContent = `Shipment with Document No ${docno} deleted successfully!`;
				messageElement.style.color = "green";
			} else {
				messageElement.textContent = "Failed to delete shipment. Please try again.";
				messageElement.style.color = "red";
			}

			messageElement.style.display = "block";

			setTimeout(() => {
				messageElement.style.display = "none";
			}, 5000);

		};
		document.getElementById("documentnos").value
			= document.getElementById("documentnos").value.replace(docno, "");




	}
}

// Delete selected rows
function deleteSelectedRows() {

	const checkboxes = document.querySelectorAll('.shipmentRowcheckbox');
	let checked = false;
	const rowsToDelete = [];
	let rowsId = "&selected_inout_ids=";
	let docno = [];

	checkboxes.forEach((checkbox) => {
		if (checkbox.checked) {
			checked = true;
			const row = checkbox.closest('tr');
			if (row) {
				rowsId += row.id.replace('row-', '') + ",";
				const documentNo = row.cells[1].innerText;
				const customerId = row.cells[3].innerText;
				rowsToDelete.push(`Document No: ${documentNo}, Customer ID: ${customerId}`);
				docno.push(documentNo);
			}
		}
	});
	if (!checked) {
		alert('Please select a customer to delete.');
	} else {
		const shipmentIds = rowsToDelete.join("\n");
		const confirmDelete = confirm(`Are you sure you want to delete the selected customers?\n\n${shipmentIds}`);
		if (confirmDelete) {
			checkboxes.forEach((checkbox) => {
				if (checkbox.checked) {
					const row = checkbox.closest('tr');
					if (row) {
						row.remove();
					}
					const action = "deleteSelected";
					const xhr = new XMLHttpRequest();
					const params = `action=${(action)}${(rowsId)}`;
					xhr.open("POST", "GoodsShipping", true);
					xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
					xhr.send(params);
				}
			});

			const currentDocnos = document.getElementById("documentnos").value.split(',').filter(Boolean);

			const updatedDocnos = currentDocnos.filter(doc => !docno.includes(doc));

			document.getElementById("documentnos").value = updatedDocnos;

			console.log("Updated Document Numbers:", updatedDocnos);

		}
	}
}


document.addEventListener("DOMContentLoaded", function() {
	const searchInput = document.getElementById("shipmentSearchInput");
	const shipmentFilterDropdown = document.getElementById("shipmentFilterDropdown");
	const filterTable = () => {
		const tableRows = document.querySelectorAll("#shipmentRecords tbody tr");
		console.log(tableRows);
		const searchValue = searchInput.value.toLowerCase().trim();
		const filterKey = shipmentFilterDropdown.value;

		tableRows.forEach((row) => {
			const cells = row.querySelectorAll("td");
			let isMatch = false;

			if (filterKey) {
				const columnIndex = getColumnIndex(filterKey);
				const cellValue = cells[columnIndex]?.innerText.toLowerCase() || "";
				isMatch = cellValue.includes(searchValue);
			} else {
				isMatch = Array.from(cells).some((cell) =>
					cell.innerText.toLowerCase().includes(searchValue)
				);
			}
			row.style.display = isMatch ? "" : "none";
		});
	};

	const getColumnIndex = (key) => {
		switch (key) {
			case "documentNo":
				return 1;
			case "MovementDate":
				return 2;
			case "customerId":
				return 3;
			default:
				return -1;
		}
	};
	searchInput.addEventListener("keyup", filterTable);
	shipmentFilterDropdown.addEventListener("change", filterTable);

	filterTable();
});


function setSelectAllCheckBox() {
	var selectAllCheckbox = document.getElementById('selectAllCheckbox');
	var rowCheckboxes = document.querySelectorAll('.shipmentRowcheckbox');
	var allSelected = Array.from(rowCheckboxes).every(function(checkbox) {
		return checkbox.checked;
	});
	selectAllCheckbox.checked = allSelected;
}

function toggleSelectAll(source) {
	var checkboxes = document.querySelectorAll('input[type="checkbox"]');
	for (var i = 0; i < checkboxes.length; i++) {
		if (checkboxes[i] != source) checkboxes[i].checked = source.checked;
	}
}

// Show notification
function showNotification(message) {
	const notificationModal = document.getElementById("notificationModal");
	notificationModal.querySelector(".notification-message").innerText = message;
	notificationModal.style.display = "block";
}

// Close delete confirmation modal
function closeDeleteConfirmation() {
	document.getElementById("deleteConfirmationModal").style.display = "none";
}
