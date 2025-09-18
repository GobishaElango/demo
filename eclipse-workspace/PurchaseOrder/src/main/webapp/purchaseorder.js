//view date
function formatDateToDDMMYYYY(dateStr) {
	let [yr, mnth, day] = dateStr.split("-");
	return `${day}-${mnth}-${yr}`
}

//update date
function formatDateToYYYYMMDD(dateStr) {
	const date = new Date(dateStr);
	if (isNaN(date)) return dateStr; 
	const year = date.getFullYear();
	const month = (1 + date.getMonth()).toString().padStart(2, '0');
	const day = date.getDate().toString().padStart(2, '0');
	return `${year}-${month}-${day}`;
}


document.querySelector(".close").onclick = function(event) {
	closeForm(event);
};

//show lineItems in view
let inViewRow;
function showLineItem(button, lineItems) {
	console.log(lineItems);
	let rows = button.parentNode.parentNode;
	inViewRow = rows;
	let lineorderId = document.getElementById("lineorderId");
	let lineorderDate = document.getElementById("lineorderDate");
	let linedeliveryDate = document.getElementById("linedeliveryDate");
	let linevendorNameDetails = document.getElementById("linevendorNameDetails");
	let linevendorAddressDetails = document.getElementById("linevendorAddressDetails");
	let linedeliveryAddress = document.getElementById("linedeliveryAddress");
	let linetotalAmount = document.getElementById("linetotalAmount");
	let LineItemBody = document.getElementById("LineItemBody");
	lineorderId.textContent = rows.cells[1].textContent;
	lineorderDate.textContent = rows.cells[2].textContent;
	linedeliveryDate.textContent = rows.cells[3].textContent;
	linevendorNameDetails.textContent = rows.cells[4].textContent;
	linevendorAddressDetails.textContent = rows.cells[5].textContent;
	linedeliveryAddress.textContent = rows.cells[6].textContent;
	linetotalAmount.textContent = rows.cells[7].textContent;
	lineItems.forEach((lineItem, index) => {
		let newRow = LineItemBody.insertRow(index);
		newRow.insertCell(0).innerHTML = lineItem.product
		newRow.insertCell(1).innerHTML = '&#8377 ' + lineItem.price
		newRow.insertCell(2).innerHTML = lineItem.quantity
		newRow.insertCell(3).innerHTML = lineItem.uom
		newRow.insertCell(4).innerHTML = '&#8377 ' + lineItem.amount
	})

	inViewRow.style.backgroundColor = "#eee"
	document.getElementById("linedetails").style.display = "block"
	document.title = "Purchase Order Details"
	document.getElementById("pagination").style.display = "none";

}

//checks validation for headers
function removeErrorClassMsg(id) {
	let element = document.getElementById(id);
	if (element.value == "" || element.value == "Select") {
		element.classList.add("error");
	}
	else {
		element.classList.remove("error");
	}
}

//close the view
function hideLineItem() {
	document.getElementById("linedetails").style.display = "none"
	document.getElementById("LineItemBody").innerHTML = ""
	document.title = "Purchaseorders"
	inViewRow.style.backgroundColor = 'white';
	document.getElementById("pagination").style.display = "flex";
}

//while opening form its initialize header fields
function initializeFormFields() {
	document.getElementById("orderDate").valueAsDate = new Date();
	document.getElementById("orderDate").max = new Date().toISOString().split("T")[0];
	document.getElementById("deliveryDate").min = new Date().toISOString().split("T")[0];
	document.getElementById("deliveryDate").value = "";
	document.getElementById("vendorNameDetails").value = "Select";
	document.getElementById("vendorAddressDetails").value = "";
	document.getElementById("deliveryAddress").value = "";
	document.getElementById("totalAmount").value = "";
}

//clear line fields
function clear() {
	let product = document.getElementById("productName");
	let price = document.getElementById("price");
	let quantity = document.getElementById("quantity");
	document.getElementById("uom").innerHTML = "";
	product.value = "Select";
	price.value = "";
	quantity.value = "";
	product.classList.remove("error");
	price.classList.remove("error");
	quantity.classList.remove("error");
	quantityError.innerHTML = "";
	productError.innerHTML = "";
	priceError.innerHTML = "";

}
document.getElementById('clearButton').addEventListener('click', clear);


//remove  errors globally
function errorClassRemove() {
	document.getElementById("orderDate").classList.remove("error");
	document.getElementById("deliveryDate").classList.remove("error");
	document.getElementById("vendorNameDetails").classList.remove("error");
	document.getElementById("vendorAddressDetails").classList.remove("error");
	document.getElementById("deliveryAddress").classList.remove("error");
	document.getElementById("legendError").style.borderColor = "#ccc";
	document.getElementById("legend").innerText = "Enter Purchase Order Line Details";
	document.getElementById("addButton").innerText = "Add";
	document.getElementById("clearButton").innerText = "Clear";
}

//click on ADD button
function displayPurchaseorderForm() {
	document.getElementById("addPurchaseOrderFormModal").style.display = "block";
	document.getElementById("pagination").style.display = "none";
	document.getElementById("formHeading").innerHTML = "Add Purchase Order";
	document.title = "Add Purchase Order";
	initializeFormFields();
	document.getElementById("lineItemsBody").innerHTML = "";
	let submitOrUpdateButton = document.getElementById("submit")
	submitOrUpdateButton.textContent = "Submit"
	submitOrUpdateButton.onclick = submitForm
	let productOptions = document.getElementById("productName").options;
	for (let i = 0; i < productOptions.length; i++) {
		productOptions[i].style.display = "block";
	}
	let xhttp = new XMLHttpRequest();
	xhttp.onload = function() {
		if (this.status == 200) {
			document.getElementById("orderId").innerHTML = this.responseText;
		}
	}
	xhttp.open('GET', "GeneratePurchaseorderDocumentNo", false);
	xhttp.send();
	clear();
	errorClassRemove()
}

//close form for cancel button
function closeForm() {
	document.getElementById("orderDate").value = "";
	document.getElementById("deliveryDate").value = "";
	document.getElementById("vendorAddressDetails").value = "";
	document.getElementById("deliveryAddress").value = "";
	document.getElementById("totalAmount").value = "";
	document.getElementById("vendorNameDetails").value = "select";
	clear();
	errorClassRemove();
	document.getElementById("addPurchaseOrderFormModal").style.display = "none";
	document.title = "Purchase Order";
	document.getElementById("pagination").style.display = "flex";
	let formHeading = document.getElementById("formHeading").innerHTML;
	if (formHeading === "Purchase Order Form") {
		docId = docId - 1;
	}
}

//check validation 
function validateOrderDate() {
	let orderDate = document.getElementById("orderDate");
	if (orderDate.value == "") {
		orderDate.classList.add("error");
	}
	else {
		orderDate.classList.remove("error");
		document.getElementById("deliveryDate").min = orderDate.value;
	}
	document.getElementById("deliveryDate").value = "";
}

function validateDeliveryDate(id) {
	let element = document.getElementById(id)
	if (element.value == "") {
		element.classList.add("error");
	}
	else {
		element.classList.remove("error");
	}
}

let productError = document.getElementById("productError")
let priceError = document.getElementById("priceError")
let quantityError = document.getElementById("quantityError")

function validatePrice(price) {
	if (price.value <= 0) {
		priceError.innerHTML = "* invalid Price"
		price.value = ""
	}
	else {
		priceError.innerHTML = ""
		price.classList.remove("error");
	}
}

function validateQuantity() {
	if (quantity.value <= 0) {
		quantityError.innerHTML = "* invalid Quantity"
		quantity.value = ""
	}
	else {
		quantityError.innerHTML = "";
		quantity.classList.remove("error")
	}
}


function validateProductDetail() {
	let productName = document.getElementById("productName").value;
	let price = document.getElementById("price").value;
	let quantity = document.getElementById("quantity").value;
	let isValid = true;
	if (productName === "Select") {
		productError.innerHTML = "* required ";
		document.getElementById("productName").classList.add("error");
		isValid = false;
	}
	else {
		productError.innerHTML = "";
		document.getElementById("productName").classList.remove("error");
	}
	if (price === "") {
		priceError.innerHTML = "* required ";
		document.getElementById("price").classList.add("error");
		isValid = false;
	}
	else {
		priceError.innerHTML = "";
		document.getElementById("price").classList.remove("error");
	}
	if (quantity === "") {
		quantityError.innerHTML = "* required ";
		document.getElementById("quantity").classList.add("error");
		isValid = false;
	}
	else {
		quantityError.innerHTML = "";
		document.getElementById("quantity").classList.remove("error");
	}
	return isValid;

}

//UOM change based on selected product
function fillProductDetails(product) {
	const selectedProduct = product.value;
	const uom = document.getElementById("uom");
	switch (selectedProduct) {
		case "Cucumber":
			uom.innerHTML = "Kg";
			product.classList.remove("error");
			productError.innerHTML = "";
			break;
		case "Aachi Chilli Powder - 100g":
		case "Gold Winner Refined Sunflower Oil - 1L":
		case "Tata Urad Dal - 1Kg":
		case "Rice - 1Kg":
		case "Bru Instant Coffee - 200g":
		case "Maggi Noodles-150g":
			uom.innerHTML = "pcs";
			product.classList.remove("error");
			productError.innerHTML = "";
			break;
		case "Orange":
		case "Onion":
			uom.innerHTML = "Kg";
			product.classList.remove("error");
			productError.innerHTML = "";
			break;
		default:
			uom.innerHTML = "";
			product.classList.add("error");
			productError.innerHTML = "* required";
			break;
	}
}

// Add lineItem
function addLineItem() {
	if (!validateProductDetail()) {
		return; 
	}
	let totalAmount = document.getElementById("totalAmount");
	let currentTotal = parseInt(totalAmount.value.replace(/[^0-9.]/g, '')) || 0;
	let productName = document.getElementById("productName").value;
	let price = document.getElementById("price").value;
	let quantity = document.getElementById("quantity").value;
	let uom = document.getElementById("uom").textContent;
	let amount = price * quantity;
	let table = document.getElementById("lineItemsBody");
	let newRow = table.insertRow(table.rows.length);
	let descCell = newRow.insertCell(0);
	let priceCell = newRow.insertCell(1);
	let quantityCell = newRow.insertCell(2);
	let amountCell = newRow.insertCell(3);
	let unitCell = newRow.insertCell(4);
	let actionCell = newRow.insertCell(5);
	descCell.textContent = productName;
	priceCell.innerHTML = '&#8377 ' + price;
	quantityCell.textContent = quantity;
	amountCell.textContent = uom;
	unitCell.innerHTML = '&#8377 ' + amount;
	actionCell.innerHTML = '<i class="fa fa-edit" onclick="updateLineItem(this)"></i><i class="fa fa-trash" onclick="deleteLineItem(this)"></i>';
	let selectedproduct = document.getElementById("productName").options[document.getElementById("productName").selectedIndex];
	selectedproduct.style.display = "none";
	let newTotal = currentTotal + amount;
	totalAmount.value = newTotal.toFixed(2);
	document.getElementById("legendError").style.borderColor = "#ccc";
	clear();
}
document.getElementById('addButton').onclick = addLineItem;

//Update LineItem
function updateLineItem(button) {
	document.getElementById("pagination").style.display = "none";
	let legend = document.getElementById("legend");
	legend.textContent = "Update Purchase Order Line Details";
	let table = document.getElementById("lineItemsBody");
	let rows = table.getElementsByTagName("tr");
	for (i = 0; i < rows.length; i++) {
		rows[i].style.backgroundcolor = "#fff";
	}
	let row = button.parentNode.parentNode;
	row.style.backgroundColor = "#eee";
	let td = row.getElementsByTagName("td");
	let addToUpdateButton = document.getElementById("addButton");
	let clearToCancel = document.getElementById("clearButton");
	let product = document.getElementById("productName");
	let price = document.getElementById("price");
	let quantity = document.getElementById("quantity");
	let uom = document.getElementById("uom");
	for (let i = 0; i < product.options.length; i++) {
		if (product.options[i].textContent.trim() === td[0].textContent.trim()) {
			product.options[i].style.display = "block";
			break;
		}
	}
	product.value = td[0].textContent;
	price.value = Number(td[1].textContent.substring(2)) || 0;
	quantity.value = td[2].textContent;
	uom.innerHTML = td[3].textContent;
	addToUpdateButton.textContent = "Update";
	clearToCancel.textContent = "Cancel";
	addToUpdateButton.onclick = function() {
		if (!validateProductDetail()) {
			return;
		}
		let totalAmount = document.getElementById("totalAmount");
		let oldAmount = parseFloat(td[4].textContent.replace(/[^0-9.]/g, '')) || 0;
		let newAmount = parseFloat(price.value) * parseFloat(quantity.value);
		let currentTotal = parseFloat(totalAmount.value.replace(/[^0-9.]/g, '')) || 0;
		td[0].textContent = product.value;
		td[1].innerHTML = '₹ ' + price.value;
		td[2].textContent = quantity.value;
		td[3].textContent = uom.textContent;
		td[4].innerHTML = '₹ ' + (price.value * quantity.value);
		totalAmount.value = (currentTotal - oldAmount + newAmount).toFixed(2);
		addToUpdateButton.textContent = "Add";
		addToUpdateButton.onclick = addLineItem;
		clearToCancel.textContent = "Clear";
		clearToCancel.onclick = clear;
		row.style.backgroundColor = "#fff";
		legend.textContent = "Enter Purchase Order Line Details";
		product.options[product.selectedIndex].style.display = "none";
		clear();
	};
	clearToCancel.onclick = function() {
		addToUpdateButton.textContent = "Add";
		addToUpdateButton.onclick = addLineItem;
		clearToCancel.textcontent = "Clear";
		clearToCancel.onclick = clear;
		row.style.backgroundColor = "#fff";
		legend.textContent = "Enter Purchase Order Line Details";
		product.options[product.selectedIndex].style.display = "none";
		clear();
	}
}

//deletelineItem
async function deleteLineItem(button) {
	document.getElementById("pagination").style.display = "none";
	let product = document.getElementById("productName");
	let addToUpdateButton = document.getElementById("addButton");
	let clearToCancel = document.getElementById("clearButton");
	let row = button.parentNode.parentNode;
	let alertDetails = document.getElementById("alertDetails");
	alertDetails.style.display = "block";
	let isDelete = await alertMsg(`Do you want to delete Product: ${row.cells[0].textContent} ?`);
	if (isDelete) {
		let tableBody = row.parentNode;
		let totalAmount = document.getElementById("totalAmount");
		let currentTotal = parseInt(totalAmount.value.replace(/[^0-9.]/g, '')) || 0;
		let amountCell = row.cells[4].textContent;
		let amount = parseInt(amountCell.replace(/[^0-9.]/g, '')) || 0;
		let newTotal = currentTotal - amount;
		totalAmount.value = newTotal.toFixed(2);
		tableBody.removeChild(row);
		for (let i = 0; i < product.options.length; i++) {
			if (product.options[i].textContent.trim() === row.cells[0].textContent.trim()) {
				product.options[i].style.display = "block";
				break;
			}
		}
		document.getElementById("legend").textContent = "Enter Purchase Order Line Details";
		addToUpdateButton.textContent = "Add";
		addToUpdateButton.onclick = addLineItem;
		clearToCancel.textContent = "Clear";
		clearToCancel.onclick = clear;
		clear();
	}
	else {
		return;
	}


}

//check form validation before submit 
function checkFormValidity() {
	let orderDate = document.getElementById('orderDate').value;
	let deliveryDate = document.getElementById('deliveryDate').value;
	let vendorNameDetails = document.getElementById('vendorNameDetails').value;
	let vendorAddressDetails = document.getElementById('vendorAddressDetails').value.trim();
	let deliveryAddress = document.getElementById('deliveryAddress').value.trim();
	let tbody = document.getElementById('lineItemsBody');
	let isValid = true;
	if (vendorNameDetails == "Select") {
		document.getElementById("vendorNameDetails").classList.add("error");
		isValid = false;
	} else {
		document.getElementById("vendorNameDetails").classList.remove("error");
	}
	if (vendorAddressDetails == "") {
		document.getElementById("vendorAddressDetails").classList.add("error");
		isValid = false;
	} else {
		document.getElementById("vendorAddressDetails").classList.remove("error");
	}
	if (deliveryAddress == "") {
		document.getElementById("deliveryAddress").classList.add("error");
		isValid = false;
	} else {
		document.getElementById("deliveryAddress").classList.remove("error");
	}
	if (orderDate === "") {
		document.getElementById("orderDate").classList.add("error");
		isValid = false;
	}
	else {
		document.getElementById("orderDate").classList.remove("error");
	}
	if (deliveryDate == "") {
		document.getElementById("deliveryDate").classList.add("error");
		isValid = false;
	}
	else {
		document.getElementById("deliveryDate").classList.remove("error");
	}
	if (tbody.innerHTML == "") {
		document.getElementById("legendError").style.borderColor = "red";
		isValid = false;
	}
	else {
		document.getElementById("legendError").style.borderColor = "#ccc";
	}
	if (!isValid) {
		return false;
	}
	else {
		return true;
	}
}

//submit form
let purchaseorderDataObject = {};
function submitForm() {
	if (!checkFormValidity()) {
		return false;
	}
	else {
		let orderId = document.getElementById("orderId").innerHTML;
		let orderDate = document.getElementById("orderDate").value;
		let deliveryDate = document.getElementById("deliveryDate").value;
		let vendorNameDetails = document.getElementById("vendorNameDetails").value;
		let vendorAddressDetails = document.getElementById("vendorAddressDetails").value.trim();
		let deliveryAddress = document.getElementById("deliveryAddress").value.trim();
		let totalAmount = document.getElementById("totalAmount").value;
		let lineItemTable = document.getElementById("lineItemsBody");
		let rows = lineItemTable.getElementsByTagName("tr");
		let lineItems = [];
		for (let eachRow = 0; eachRow < rows.length; eachRow++) {
			let cells = rows[eachRow].getElementsByTagName("td");
			let itemData = {};
			itemData['product'] = cells[0].innerHTML;
			itemData['price'] = cells[1].innerHTML;
			itemData['quantity'] = cells[2].innerHTML;
			itemData['uom'] = cells[3].innerHTML;
			itemData['amount'] = cells[4].innerHTML;
			lineItems.push(itemData);
		}
		purchaseorderDataObject[orderId] = {
			"orderId": orderId,
			"orderDate": orderDate,
			"deliveryDate": deliveryDate,
			"vendorNameDetails": vendorNameDetails,
			"vendorAddressDetails": vendorAddressDetails,
			"deliveryAddress": deliveryAddress,
			"totalAmount": totalAmount,
			"lineItems": lineItems
		};
		addPurchaseOrder(purchaseorderDataObject[orderId]);
		return true;
	}
}

// add purchaseorder
function addPurchaseOrder(purchaseorder) {
	let purchaseorderString = JSON.stringify(purchaseorder);
	let xhttp = new XMLHttpRequest();
	xhttp.onload = function() {
		if (this.status == 200) {
			const confirmMsg = document.getElementById("confirmMsg");
			if (this.responseText === "REDIRECT_TO_PAGE_1") {
				window.name = "Purchase Order Added Successfully!";
				window.location.href = "ViewPurchaseorder?page=1";
			} else {
				if (confirmMsg) {
					confirmMsg.style.display = 'block';
					confirmMsg.style.color = 'red';
					confirmMsg.innerHTML = "Failed to Add Purchaseorder";
				}
			}
		}
		else {
			console.error('Request failed with status:', xhttp.status);
		}

	}
	xhttp.open("POST", "InsertPurchaseorder", true);
	xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xhttp.send("purchaseorder=" + purchaseorderString);
	document.getElementById("lineItemsBody").innerHTML = "";
	document.getElementById("addPurchaseOrderFormModal").style.display = "none";
	clear();
	errorClassRemove();
	document.getElementById("pagination").style.display = "flex";

}

//Update Purchaseorder
function updatePurchaseorder(button, lineItems) {
	document.getElementById("addPurchaseOrderFormModal").style.display = "block";
	document.getElementById("pagination").style.display = "none";
	document.title = "Update Purchase Order";
	clear();

	let updateRow = button.parentNode.parentNode;
	let orderId = updateRow.cells[1].textContent;

	document.getElementById("formHeading").innerHTML = "Update Purchase Order";
	document.getElementById('orderId').innerHTML = orderId;
	document.getElementById('orderDate').value = updateRow.cells[2].textContent;
	document.getElementById('deliveryDate').value = updateRow.cells[3].textContent;
	document.getElementById('vendorNameDetails').value = updateRow.cells[4].textContent;
	document.getElementById('vendorAddressDetails').value = updateRow.cells[5].textContent;
	document.getElementById('deliveryAddress').value = updateRow.cells[6].textContent;
	document.getElementById('totalAmount').value = updateRow.cells[7].textContent;
	let LineItemBody = document.getElementById("lineItemsBody");
	LineItemBody.innerHTML = "";
	let productOptions = document.getElementById("productName").options;
	for (let i = 0; i < productOptions.length; i++) {
		productOptions[i].style.display = "block";
	}
	lineItems.forEach((lineItem, index) => {
		let newRow = LineItemBody.insertRow(index);
		newRow.insertCell(0).innerHTML = lineItem.product;
		newRow.insertCell(1).innerHTML = '&#8377 ' + lineItem.price;
		newRow.insertCell(2).innerHTML = lineItem.quantity;
		newRow.insertCell(3).innerHTML = lineItem.uom;
		newRow.insertCell(4).innerHTML = '&#8377 ' + lineItem.amount;
		newRow.insertCell(5).innerHTML = '<i class="fa fa-edit" onclick="updateLineItem(this)"></i><i class="fa fa-trash" onclick="deleteLineItem(this)"></i>';
		let productSelect = document.getElementById("productName");
		let optionToHide = null;

		for (let i = 0; i < productSelect.options.length; i++) {
			if (productSelect.options[i].value === lineItem.product) {
				optionToHide = productSelect.options[i];
				break;
			}
		}

		if (optionToHide) {
			optionToHide.style.display = "none";
		}
	});
	let submitToUpdateButton = document.getElementById("submit");
	submitToUpdateButton.textContent = "Save";
	submitToUpdateButton.onclick = function() {
		if (!checkFormValidity()) return;
		let orderDate = document.getElementById('orderDate').value;
		let deliveryDate = document.getElementById('deliveryDate').value;
		let vendorName = document.getElementById('vendorNameDetails').value;
		let vendorAddress = document.getElementById('vendorAddressDetails').value;
		let deliveryAddress = document.getElementById('deliveryAddress').value;
		let totalAmount = document.getElementById('totalAmount').value;
		let tbody = document.getElementById("lineItemsBody");
		let row = tbody.getElementsByTagName('tr');
		let lineItems = [];
		for (let eachRow = 0; eachRow < row.length; eachRow++) {
			let cells = row[eachRow].getElementsByTagName("td");
			let itemData = {};
			itemData["product"] = cells[0].innerHTML;
			itemData[`price`] = cells[1].innerHTML.substring(1);
			itemData[`quantity`] = cells[2].innerHTML;
			itemData[`uom`] = cells[3].innerHTML;
			itemData[`amount`] = cells[4].innerHTML.substring(1);
			lineItems.push(itemData);
		}
		let purchaseOrderObj = {
			"orderId": orderId,
			"orderDate": orderDate,
			"deliveryDate": deliveryDate,
			"vendorName": vendorName,
			"vendorAddress": vendorAddress,
			"deliveryAddress": deliveryAddress,
			"totalAmount": totalAmount,
			"lineItems": lineItems
		};

		let xhttp = new XMLHttpRequest();
		xhttp.onload = function() {
			let confirmMsg = document.getElementById("confirmMsg");

			try {
				let resText = this.responseText;
				console.log(resText);
				let response = JSON.parse(resText);

				if (this.status == 200 && parseInt(response.rowCount) > 0) {
					button.onclick = function() { updatePurchaseorder(button, response.lineItems) };
					let viewButton = button.parentNode.querySelector(".fa-eye");
					viewButton.onclick = function() { showLineItem(viewButton, response.lineItems) };
					if (confirmMsg) {
						confirmMsg.style.display = 'block';
						confirmMsg.style.visibility = 'visible';
						confirmMsg.style.color = 'green';
						confirmMsg.innerText = `Purchase Order ${orderId} Updated Successfully!`;
					}
					let updatetd = updateRow.getElementsByTagName("td");
					updatetd[2].innerHTML = formatDateToYYYYMMDD(orderDate);
					updatetd[3].innerHTML = formatDateToYYYYMMDD(deliveryDate);
					updatetd[4].innerHTML = vendorName;
					updatetd[5].innerHTML = vendorAddress;
					updatetd[6].innerHTML = deliveryAddress;
					totalAmount = totalAmount.replace(/[^0-9.]/g, '');
					updatetd[7].innerHTML = parseFloat(totalAmount).toFixed(1);
				} else {
					if (confirmMsg) {
						confirmMsg.style.display = 'block';
						confirmMsg.style.visibility = 'visible';
						confirmMsg.style.color = 'red';
						confirmMsg.innerHTML = `Purchase Order Update failed`;
					}
				}

			} catch (error) {
				console.error("Error parsing response:", error);
				if (confirmMsg) {
					confirmMsg.style.display = 'block';
					confirmMsg.style.visibility = 'visible';
					confirmMsg.style.color = 'red';
					confirmMsg.innerHTML = "Server response was invalid.";
				}
			}

		}

		xhttp.open("POST", "UpdatePurchaseorder", true);
		xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		xhttp.send("purchaseorder=" + JSON.stringify(purchaseOrderObj));
		document.getElementById("lineItemsBody").innerHTML = "";
		document.getElementById("addPurchaseOrderFormModal").style.display = "none";
		clear();
		errorClassRemove();
		document.getElementById("pagination").style.display = "flex";
	};
}

window.addEventListener("load", function() {
	const confirmMsg = document.getElementById("confirmMsg");
	if (window.name && confirmMsg) {
		confirmMsg.innerText = window.name;
		confirmMsg.style.visibility = "visible";
		confirmMsg.style.color = window.name.toLowerCase().includes("delete") ? "red" : "green";
		window.name = ""; 
	} else if (confirmMsg) {
		confirmMsg.style.visibility = 'hidden';
		confirmMsg.innerHTML = "";
	}
});


//alert box
function alertMsg(msg) {
	document.getElementById("alertContent").innerHTML = msg;
	let isClicked;
	document.getElementById("pagination").style.display = "none";
	return new Promise((resolve) => {
		let yesBtn = document.getElementById("yes");
		let noBtn = document.getElementById("no");
		yesBtn.onclick = function() {
			isClicked = true;
			alertDetails.style.display = "none";
			resolve(isClicked);
			document.getElementById("pagination").style.display = "flex";
		};
		noBtn.onclick = function() {
			isClicked = false;
			alertDetails.style.display = "none";
			resolve(isClicked);
			document.getElementById("pagination").style.display = "flex";
		};
	});
}

//refreshing main table and pagination after delete or filter
function updatePurchaseOrderTable(page, message = '') {
	const xhttp = new XMLHttpRequest();
	xhttp.onload = function() {
		if (this.status === 200) {
			const parser = new DOMParser();
			const doc = parser.parseFromString(this.responseText, 'text/html');
			const newTbody = doc.querySelector('#purchaseorderRecords tbody');
			const existingTbody = document.querySelector('#purchaseorderRecords tbody');
			const newPagination = doc.querySelector('#pagination');
			const existingPagination = document.querySelector('#pagination');
			const rowCount = newTbody ? newTbody.rows.length : 0;
			if (rowCount === 0 && page > 1) {
				updatePurchaseOrderTable(page - 1, message);
				history.pushState(null, '', `ViewPurchaseorder?page=${page - 1}`);
				return;
			}
			if (newTbody && existingTbody) {
				existingTbody.innerHTML = newTbody.innerHTML;
			}
			if (newPagination && existingPagination) {
				existingPagination.innerHTML = newPagination.innerHTML;
			}
			let confirmMsg = document.getElementById("confirmMsg");
			if (message) {
				confirmMsg.style.display = 'block';
				confirmMsg.style.color = message.toLowerCase().includes('deleted') ? 'red' : 'green';
				confirmMsg.innerText = message;
			} else {
				if (confirmMsg) {
					confirmMsg.style.display = 'none';
					confirmMsg.innerText = '';
				}
			}

			history.pushState(null, '', `ViewPurchaseorder?page=${page}`);
		}
	};
	xhttp.open("GET", `ViewPurchaseorder?page=${page}`, true);
	xhttp.send();
}



// Delete Purchase Order
async function deletePurchaseorder(button) {
	let row = button.parentNode.parentNode;
	let orderId = row.cells[1].textContent;
	let alertDetails = document.getElementById("alertDetails");
	alertDetails.style.display = "block";
	const isDelete = await alertMsg(`Do you want to delete Purchase Order ${orderId}?`);
	if (!isDelete) return;
	const orderIds = [orderId];
	const xhttp = new XMLHttpRequest();
	xhttp.onload = function() {
		if (this.status == 200) {
			let deletedCount = parseInt(this.responseText);
			let confirmMsg = document.getElementById("confirmMsg");
			if (deletedCount > 0) {
				if (confirmMsg) {
					confirmMsg.style.color = 'red';
					confirmMsg.style.visibility = 'visible';
					confirmMsg.innerHTML = `Purchase Order ${orderId} Deleted Successfully`;
				}
				row.remove();
				deletedCount = 0;
				const searchInput = document.getElementById("searchPurchaseOrder").value.trim();
				if (searchInput.length > 0) {
					window.name = `Purchase Order ${orderId} Deleted Successfully`;
					filterTable();
				} else {
					const tableBody = document.getElementById("purchaseorderBody");
					const remainingRows = tableBody.rows.length;
					const currentPage = parseInt(new URLSearchParams(window.location.search).get("page")) || 1;
					const redirectPage = (remainingRows === 0 && currentPage > 1) ? currentPage - 1 : currentPage;
					updatePurchaseOrderTable(redirectPage, `Purchase Order ${orderId} Deleted Successfully!`);
				}
			} else {
				confirmMsg.style.display = "block";
				confirmMsg.style.color = 'red';
				confirmMsg.style.visibility = 'visible';
				confirmMsg.innerHTML = `Purchase Order ${orderId} Failed to Delete`;
			}
		} else {
			console.error('Request failed with status:', xhttp.status);
		}

	};

	xhttp.open("POST", "DeletePurchaseorder", false);
	xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xhttp.send("orderIds=" + JSON.stringify(orderIds));
}

//enable or disable delete icon using toggle select 
function enableDeleteBtn() {
	let deleteButton = document.getElementById("deletePurchaseOrderButton");
	let selectAllCheckbox = document.getElementById("selectAllCheckbox");
	let checkList = document.getElementById("purchaseorderBody").getElementsByClassName("check");
	let checkCount = 0;
	for (let i = checkList.length - 1; i >= 0; i--) {
		let isChecked = checkList[i].checked;
		if (isChecked) {
			checkCount++;
			checkList[i].parentNode.parentNode.style.backgroundColor = "#f2f2f2";
		}
		else {
			checkList[i].parentNode.parentNode.style.backgroundColor = "white";
		}
	}
	if (checkCount) {
		deleteButton.disabled = false;
	}
	else {
		deleteButton.disabled = true;
	}
	if (checkCount == checkList.length) {
		selectAllCheckbox.checked = true;
	}
	else {
		selectAllCheckbox.checked = false;
	}

}

document.getElementById("ok").onclick = function() {
	document.getElementById("confirmDiv").style.display = "none";
};

//delete selected purchase order 
async function deleteSelectedPurchaseOrder() {
	const alertDetails = document.getElementById("alertDetails");
	const selectAllCheckbox = document.getElementById("selectAllCheckbox");
	const checkList = document.getElementById("purchaseorderBody").getElementsByClassName("check");
	let orderIds = [];
	for (let i = checkList.length - 1; i >= 0; i--) {
		if (checkList[i].checked) {
			let checkedRow = checkList[i].parentNode.parentNode;
			orderIds.push(checkedRow.cells[1].textContent); 
		}
	}
	if (orderIds.length === 0) {
		alert("No purchase orders selected.");
		return;
	}
	const msg = (orderIds.length === 1) ? `Do you want to delete Purchase Order ID: ${orderIds[0]}?` : `Do you want to delete all the selected purchase orders?`;
	alertDetails.style.display = "block";
	const isDelete = await alertMsg(msg);
	if (isDelete) {
		const xhttp = new XMLHttpRequest();
		//let deletedCount = 0;
		const message = (orderIds.length === 1)
			? `Purchase Order ${orderIds[0]} Deleted Successfully!`
			: `Purchase Orders Deleted Successfully!`
		xhttp.onload = function() {
			const confirmMsg = document.getElementById("confirmMsg");
			if (this.status === 200) {
				let deletedCount = parseInt(this.responseText);
				if (deletedCount > 0) {
					confirmMsg.style.display = 'block';
					confirmMsg.style.color = 'red';
					confirmMsg.style.visibility = 'visible';
					confirmMsg.innerText = message;
					deletedCount.style.color = 'red';
					deletedCount = 0;
				} else {
					confirmMsg.style.display = 'block';
					confirmMsg.style.color = 'red';
					confirmMsg.style.visibility = 'visible';
					confirmMsg.innerText = "Failed to Delete Purchase Orders!";
				}
			}
		};
		xhttp.open("POST", "DeletePurchaseorder", false);
		xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		xhttp.send("orderIds=" + JSON.stringify(orderIds));
		for (let i = checkList.length - 1; i >= 0; i--) {
			if (checkList[i].checked) {
				let row = checkList[i].parentNode.parentNode;
				row.parentNode.removeChild(row);
			}
		}
		if (selectAllCheckbox) {
			selectAllCheckbox.checked = false;
		}
		const searchInput = document.getElementById("searchPurchaseOrder").value.trim();
		if (searchInput.length > 0) {
			window.name = `Purchase Orders Deleted Successfully!`;
			filterTable();
		} else {
			const tableBody = document.getElementById("purchaseorderBody");
			const remainingRows = tableBody.rows.length;
			let currentPage = parseInt(new URLSearchParams(window.location.search).get("page") || 1);

			const paginationLinks = document.querySelectorAll("#pagination a.page-link");
			let lastPage = currentPage;
			for (let i = 0; i < paginationLinks.length; i++) {
				const pageText = paginationLinks[i].textContent.trim();
				const pageNum = parseInt(pageText);
				if (!isNaN(pageNum)) {
					lastPage = Math.max(lastPage, pageNum);
				}
			}
			if (remainingRows === 0) {
				let nextPage = currentPage + 1;
				if (nextPage <= lastPage) {
					updatePurchaseOrderTable(currentPage, message, nextPage);
				} else {
					updatePurchaseOrderTable(currentPage, message);
				}
			} else {
				updatePurchaseOrderTable(currentPage, message);
			}
			history.pushState(null, '', `ViewPurchaseorder?page=${currentPage}`);

		}
	}
}

//checkbox 
function toggleSelectAll(checkBox) {
	let checkList = document.getElementById("purchaseorderBody").getElementsByClassName("check");
	let deleteButton = document.getElementById("deletePurchaseOrderButton");
	if (document.getElementById("purchaseorderBody").innerHTML !== "") {
		if (checkBox.checked) {
			deleteButton.disabled = false;
			for (let i = checkList.length - 1; i >= 0; i--) {
				checkList[i].checked = true;
				checkList[i].parentNode.parentNode.style.backgroundColor = "#f2f2f2";
			}
		} else {
			deleteButton.disabled = true;
			for (let i = checkList.length - 1; i >= 0; i--) {
				checkList[i].checked = false;
				checkList[i].parentNode.parentNode.style.backgroundColor = "white";
			}
		}
	} else {
		deleteButton.disabled = true;
		checkBox.checked = false;
	}
}

// search and filter event listener 
let searchPurchaseOrderInput = document.getElementById("searchPurchaseOrder");
let filterByDropdown = document.getElementById("filterDropdown");
let purchaseOrderBody = document.getElementById("purchaseorderBody");
let paginationDiv = document.getElementById("pagination");
let searchTimeout = null;
let lastPageBeforeSearch = 1;
searchPurchaseOrderInput.addEventListener("input", function() {
	if (searchTimeout) clearTimeout(searchTimeout);

	searchTimeout = setTimeout(() => {
		let searchInput = searchPurchaseOrderInput.value.trim();
		let currentUrl = new URL(window.location.href);
		let currentPage = currentUrl.searchParams.get("page") || 1;

		if (searchInput.length > 0) {
			lastPageBeforeSearch = currentPage;
			filterTable();
		} else {
			updatePurchaseOrderTable(lastPageBeforeSearch);
		}
	}, 400);
});

filterByDropdown.addEventListener("change", function() {
	const searchInput = searchPurchaseOrderInput.value.trim();
	if (searchInput.length > 0) {
		filterTable();
	} else {
		updatePurchaseOrderTable(lastPageBeforeSearch);
	}
});

//executing  search and display
function filterTable() {
	document.getElementById('confirmMsg').innerHTML = ''
	let searchInput = searchPurchaseOrderInput.value.trim();
	if (searchInput.length === 0) return;
	let filterBy = filterByDropdown.value || "documentNo";
	purchaseOrderBody.innerHTML = "";
	paginationDiv.innerHTML = "";
	let xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (this.readyState === 4 && this.status === 200) {
			let ordersArray = JSON.parse(this.responseText);
			if (ordersArray.length > 0) {
				ordersArray.forEach(function(orderObj) {
					let newRow = purchaseOrderBody.insertRow(-1);
					newRow.insertCell(0).innerHTML = `<input type="checkbox" class="check" onclick="enableDeleteBtn()">`;
					newRow.insertCell(1).textContent = orderObj.documentNo || "";
					newRow.insertCell(2).textContent = orderObj.orderDate || "";
					newRow.insertCell(3).textContent = orderObj.deliveryDate || "";
					newRow.insertCell(4).textContent = orderObj.vendorName || "";
					newRow.insertCell(5).textContent = orderObj.vendorAddress || "";
					newRow.insertCell(6).textContent = orderObj.deliveryAddress || "";
					newRow.insertCell(7).textContent = orderObj.totalAmount || "";
					let encodedLineItems = encodeURIComponent(JSON.stringify(orderObj.lineItems || []));
					newRow.insertCell(8).innerHTML = `
                        <i class="fa fa-eye" onclick='showLineItem(this, JSON.parse(decodeURIComponent("${encodedLineItems}")))'></i>
                        <i class="fa fa-edit" onclick='updatePurchaseorder(this, JSON.parse(decodeURIComponent("${encodedLineItems}")))'></i>
                        <i class="fa fa-trash" onclick="deletePurchaseorder(this)"></i>`;
				});
				paginationDiv.innerHTML = `<a href="javascript:void(0);" id="backButton">‹ Back</a>`;
				document.getElementById("backButton").addEventListener("click", function() {
					updatePurchaseOrderTable(lastPageBeforeSearch);
				});
			} else {
				purchaseOrderBody.innerHTML = `<tr><td id='notFound' colspan='10'>Purchase Order Not Found !!</td></tr>`;
				paginationDiv.innerHTML = `<a href="javascript:void(0);" id="backButton">‹ Back</a>`;
				document.getElementById("backButton").addEventListener("click", function() {
					updatePurchaseOrderTable(lastPageBeforeSearch);
				});
			}
		}
		let confirmMsg = document.getElementById("confirmMsg");
		if (window.name && confirmMsg) {
			confirmMsg.style.display = "block";
			let messageLowerCase = window.name.toLowerCase();
			if (messageLowerCase.includes("delete")) {
				confirmMsg.style.color = "red";
				confirmMsg.innerHTML = window.name;
				confirmMsg.style.visibility = 'visible';
				window.name = "";
			} else {
				confirmMsg.style.color = "green";
				confirmMsg.innerHTML = window.name;
				confirmMsg.style.visibility = 'visible';
				window.name = "";
			}
		}
	};
	let url = "searchPurchaseorder?filterType=" + encodeURIComponent(filterBy) +
		"&filterValue=" + encodeURIComponent(searchInput);
	xhttp.open("GET", url, true);
	xhttp.send();
}
