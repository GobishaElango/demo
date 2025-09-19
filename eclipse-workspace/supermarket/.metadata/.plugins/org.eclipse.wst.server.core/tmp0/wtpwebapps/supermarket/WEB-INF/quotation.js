//view
function formatDateToDDMMYYYY(dateStr){
	let [yr,mnth,day]=dateStr.split("-");
	return `${day}-${mnth}-${yr}`
}
//update
function formatDateToYYYYMMDD(dateStr){
    let [day,mnth,yr]=dateStr.split("-");
    return `${yr}-${mnth}-${day}`
}

//show lineItems
let inViewRow;
function showLineItem(button,lineItems){
	console.log(lineItems);
    let rows=button.parentNode.parentNode;
    inViewRow=rows;
    let liquoteId=document.getElementById("liquoteId");
    let liquoteDate=document.getElementById("liquoteDate");
    let livtd=document.getElementById("livtd");
    let livendor=document.getElementById("livendor");
	let LIBody=document.getElementById("LIBody");
	LIBody.innerHTML = '';
	let quotationId=rows.cells[2].textContent
    liquoteId.textContent=quotationId;
    liquoteDate.textContent=rows.cells[4].textContent;
    livtd.textContent=rows.cells[5].textContent;
    livendor.textContent=rows.cells[3].textContent;
	lineItems.forEach((lineItem,index)=>{
		let newRow = LIBody.insertRow(index);
		newRow.insertCell(0).innerHTML=index+1;
		newRow.insertCell(1).innerHTML=lineItem.product
		newRow.insertCell(2).innerHTML='&#8377 '+lineItem.price
		newRow.insertCell(3).innerHTML=lineItem.quantity
		newRow.insertCell(4).innerHTML=lineItem.uom
		newRow.insertCell(5).innerHTML='&#8377 '+lineItem.lineNetAmt				
	})				
    
    inViewRow.style.backgroundColor="#eee"
    document.getElementById("linedetails").style.display="block"
    document.title="Quotation Details"

}

//hide lineItem table
function hideLineItem(){
    document.getElementById("linedetails").style.display="none"
    document.getElementById("LIBody").innerHTML=""
    document.title="Quotations"
    inViewRow.style.backgroundColor='white';
}

//set default value
function initializeFormFields(){
    document.getElementById("quoteDate").valueAsDate = new Date();
    document.getElementById("quoteDate").max=new Date().toISOString().split("T")[0];
    document.getElementById("vtd").min=new Date().toISOString().split("T")[0];
    document.getElementById("vtd").value="";
    document.getElementById("vendor").value="Select";
}

//clear input fields and Error class  in ADD product
function clear(){
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
    quantError.innerHTML="";
    prodError.innerHTML="";
    priceError.innerHTML="";
}
document.getElementById('clearBtn').addEventListener('click',clear);

//remove  error class in form header feild
function errorClassRemove(){
    document.getElementById("quoteDate").classList.remove("error");
    document.getElementById("vtd").classList.remove("error");
    document.getElementById("vendor").classList.remove("error");
    document.getElementById("fs").style.borderColor="#ccc";
}

//when quoteDate changed
function changeValidTill(){
    let quoteDate=document.getElementById("quoteDate")
    if(quoteDate.value==""){
        quoteDate.classList.add("error");
    }
    else{
        quoteDate.classList.remove("error");
        document.getElementById("vtd").min=quoteDate.value
    }
    document.getElementById("vtd").value="";
}

//when click on ADD button
function displayForm(){
    document.getElementById("formDiv").style.display="block";
    document.getElementById("formh2").innerHTML="Add Quotation";
    document.title="Add Quotation";
	initializeFormFields();
    document.getElementById("lineItemsBody").innerHTML="";
    let sub2UpdBtn=document.getElementById("submit")
    sub2UpdBtn.textContent="Submit"
    sub2UpdBtn.onclick=submitForm
    let prodOptions = document.getElementById("productName").options;
    for (let i = 0; i < prodOptions.length; i++) {
        prodOptions[i].style.display = "block";
    }
	let xhttp=new XMLHttpRequest();
	xhttp.onload=function(){
		if(this.status==200){
			document.getElementById("quoteId").innerHTML=this.responseText;
		}
		
		
	}
	xhttp.open('GET',"GenerateQuotationDocumentNo",false);
	xhttp.send();
    clear();
    errorClassRemove()
}
//close form when click on cancel btn in form
function cancelForm(event){
    let isCanceled = confirm("Are you sure you want to cancel?");
    if(isCanceled){
      document.getElementById("vendor").value="Select"
      let tbody = document.getElementById("lineItemsBody");
      tbody.innerHTML="";
      clear(); 
      document.getElementById("formDiv").style.display="none"
      document.title="Quotation"
      errorClassRemove();
      
    }
    else{
        event.preventDefault()
    }  
}
//for vendor, valid till feild 
function toggleErrorClass(id){
    let element=document.getElementById(id)
    if(element.value=="" || element.value=="Select") {
       element.classList.add("error");
    }
    else{
        element.classList.remove("error");
    }
}
let prodError=document.getElementById("prodError")
let priceError=document.getElementById("priceError")
let quantError=document.getElementById("quantError")

function validatePrice(price){
    if(price.value<=0){
        priceError.innerHTML="* invalid Price"
        price.value=""  
    }
    else{
         priceError.innerHTML=""
         price.classList.remove("error");
    }   
}

function validateQuantity(qty){
    if(qty.value<=0){
        quantError.innerHTML="* invalid Quantity"
        qty.value=""   
    }
    else{
        quantError.innerHTML="";
        qty.classList.remove("error")
    }   
}


function validateprodDetail(){
    let productName = document.getElementById("productName").value;
    let price = document.getElementById("price").value;
    let quantity = document.getElementById("quantity").value;
    let isValid=true;
    if(productName === "Select") {
        prodError.innerHTML="* required ";
        document.getElementById("productName").classList.add("error");
        isValid= false;
    }
    else{
        prodError.innerHTML="";
        document.getElementById("productName").classList.remove("error");
    }
    if(price === "") {
        priceError.innerHTML="* required ";
        document.getElementById("price").classList.add("error");
        isValid= false;
    }
    else{
        priceError.innerHTML="";
        document.getElementById("price").classList.remove("error");
    }
    if(quantity === "") {
        quantError.innerHTML="* required ";
        document.getElementById("quantity").classList.add("error");
        isValid= false;
    }
    else{
        quantError.innerHTML="";
        document.getElementById("quantity").classList.remove("error");
    }
    return isValid;
   
}

//UOM change based on selected product
function autoFillUOM(product) {
    const selectedProduct = product.value;
    const uom= document.getElementById("uom");
    switch (selectedProduct) {
        case "Apple":
            uom.innerHTML = "Kg";
            product.classList.remove("error");
            prodError.innerHTML="";
            break;
        case "Aachi Chilli Powder - 100g":
        case "Gold Winner Refined Sunflower Oil - 1L":
        case "Tata Urad Dal - 1Kg":
        case "Aashirvaad Whole Wheat Flour - 1Kg":
            uom.innerHTML= "pcs";
            product.classList.remove("error");
            prodError.innerHTML="";
            break;
        case "Sugar":
        case "Onion":
            uom.innerHTML = "Kg";
            product.classList.remove("error");
            prodError.innerHTML="";
            break;
        default:
            uom.innerHTML = "";
            product.classList.add("error");
            prodError.innerHTML="* required";
            break;
    }
}
// Add lineItem
function addLineItem() {
    if (!validateprodDetail()) {
        return; // Exit function if validation fails
    }
    let productName = document.getElementById("productName").value;
    let price = document.getElementById("price").value;
    let quantity = document.getElementById("quantity").value;
    let uom = document.getElementById("uom").textContent;
    let amount=price*quantity;
    let table = document.getElementById("lineItemsBody");
    let newRow = table.insertRow(table.rows.length);
    let checkCell=newRow.insertCell(0);
    let snoCell = newRow.insertCell(1);
    let descCell = newRow.insertCell(2);
    let priceCell = newRow.insertCell(3);
    let quantityCell = newRow.insertCell(4);
    let amountCell = newRow.insertCell(5);
    let unitCell = newRow.insertCell(6);
    let actionCell = newRow.insertCell(7);
    checkCell.innerHTML='<input type="checkbox" class="checkLineItems" onclick="enableDeleteBtnLI()">'
    snoCell.textContent = table.rows.length;
    descCell.textContent =productName;
    priceCell.innerHTML ='&#8377 '+price.trim();
    quantityCell.textContent = quantity.trim();
    amountCell.textContent = uom;
    unitCell.innerHTML = '&#8377 '+amount;
    actionCell.innerHTML = '<i class="fa fa-edit" onclick="updateLineItem(this)"></i><i class="fa fa-trash" onclick="deleteLineItem(this)"></i>';
    let selectedprod=document.getElementById("productName").options[document.getElementById("productName").selectedIndex];
    selectedprod.style.display="none";
    let checkBoxHead= document.getElementById("checkHeadLI");
    if(checkBoxHead.checked){
        checkBoxHead.checked=false
    }
    document.getElementById("fs").style.borderColor="#ccc";
    clear();
}
document.getElementById('addBtn').onclick= addLineItem;

//Update LineItem
function updateLineItem(button) {
    let legend=document.getElementById("legend")
    legend.textContent="Update Product"
    let table = document.getElementById("lineItemsBody");
    let rows = table.getElementsByTagName("tr");
    for (let i = 0; i < rows.length; i++) {
        rows[i].style.backgroundColor = "#fff";
    }
    let row = button.parentNode.parentNode;
    row.style.backgroundColor="#eee"
    let td = row.getElementsByTagName("td");
    let add2SaveBtn = document.getElementById("addBtn");
    let clear2Cancel = document.getElementById("clearBtn");
    let product = document.getElementById("productName");
    let price = document.getElementById("price");
    let quantity = document.getElementById("quantity");
    let uom = document.getElementById("uom");
    product.options.namedItem(td[2].textContent).style.display="block";
    product.value=td[2].textContent
    price.value = td[3].textContent.substring(2);
    quantity.value = td[4].textContent;
    uom.innerHTML = td[5].textContent;
    add2SaveBtn.textContent = "Save";
    clear2Cancel.textContent="Cancel"
    add2SaveBtn.onclick = function() {
        if (!validateprodDetail()) {
            return; // Exit function if validation fails
        }
        td[2].textContent=product.value
        td[3].innerHTML = '&#8377 '+price.value;
        td[4].textContent = quantity.value;
        td[5].textContent = uom.textContent;
        td[6].innerHTML = '&#8377 '+ (price.value* quantity.value);
        add2SaveBtn.textContent = "Add";
        add2SaveBtn.onclick=addLineItem
        clear2Cancel.textContent="Clear";
        clear2Cancel.onclick=clear
        row.style.backgroundColor="#fff";
        legend.textContent="Add Product"
        product.options[product.selectedIndex].style.display="none";
        clear();
    };
    clear2Cancel.onclick= function(){
        add2SaveBtn.textContent = "Add";
        add2SaveBtn.onclick=addLineItem;
        clear2Cancel.textContent="Clear";
        clear2Cancel.onclick=clear;
        row.style.backgroundColor="#fff";
        legend.textContent="Add Product";
        product.options[product.selectedIndex].style.display="none";
        clear();
    }
}
//alert box
function showConfirmBox(msg) {
    document.getElementById("alertContent").innerHTML = msg;
    let isClicked;
    return new Promise((resolve) => {
        let yesBtn = document.getElementById("yes");
        let noBtn = document.getElementById("no");
        yesBtn.onclick = function() {
            isClicked = true;
            alertDiv.style.display = "none";
            resolve(isClicked);
        };
        noBtn.onclick = function() {
            isClicked = false;
            alertDiv.style.display = "none";
            resolve(isClicked);
        };
    });
}

//when click on delete icon in lineItem
async function deleteLineItem(button) {
    let product=document.getElementById("productName");
    let add2UpdBtn = document.getElementById("addBtn");
    let clear2Cancel = document.getElementById("clearBtn");
    let row = button.parentNode.parentNode;
    let alertDiv = document.getElementById("alertDiv");
    alertDiv.style.display = "block";
    let isDelete=await showConfirmBox(`Do you want to delete S.No.${row.cells[1].textContent} Product: ${row.cells[2].textContent} ?`)
    if(isDelete){
        let tableBody = row.parentNode;
        tableBody.removeChild(row);
        product.options.namedItem(row.cells[2].textContent).style.display="block";
        let rows = tableBody.getElementsByTagName("tr");
        for (let i = 0; i < rows.length; i++) {
            rows[i].cells[1].textContent = i + 1;
        }
        document.getElementById("legend").textContent="Add Product";
        add2UpdBtn.textContent = "Add";
        add2UpdBtn.onclick=addLineItem
        clear2Cancel.textContent="Clear";
        clear2Cancel.onclick=clear
        clear()   
    }
    else{
        return;
    }
    
}

// when click on each Line Item -->respective row color change, control delete Btn, select and deselect all
function enableDeleteBtnLI(){
   let delBtn= document.getElementById("deleteSelectedBtn");
   let checkBoxHead= document.getElementById("checkHeadLI");
   let checkList=document.getElementById("lineItemsBody").getElementsByClassName("checkLineItems")
   let checkCount=0
   for(let i = checkList.length - 1; i >= 0; i--){
      let isChecked = checkList[i].checked;
      if(isChecked){
        checkCount++;
        checkList[i].parentNode.parentNode.style.backgroundColor="#f2f2f2"
      }
      else{
        checkList[i].parentNode.parentNode.style.backgroundColor="white"
      }
   }
   if(checkCount){
        delBtn.disabled=false
   }
   else{
        delBtn.disabled=true
   }
   if(checkCount==checkList.length){
        checkBoxHead.checked=true
   }
   else{
        checkBoxHead.checked=false
   }
}
async function deleteSelectedLineItems(){
    let alertContent="Do you want to delete all the Selected Line Items?"
    let alertDiv = document.getElementById("alertDiv");
    alertDiv.style.display = "block";
    let delBtn= document.getElementById("deleteSelectedBtn");
    let checkBoxHead= document.getElementById("checkHeadLI");
    let checkList=document.getElementById("lineItemsBody").getElementsByClassName("checkLineItems")
    let product=document.getElementById("productName");
    let checkCount=0;
    let row;
    for(let i = checkList.length - 1; i >= 0; i--){
       let isChecked = checkList[i].checked;
       if(isChecked){
         checkCount++;
         row=checkList[i].parentNode.parentNode
       }
    }
    if(checkCount==1){
        alertContent=`Do you want to delete S.No.${row.cells[1].textContent} Product: ${row.cells[2].textContent} ?`
    }
    let isDelete= await showConfirmBox(alertContent)
    if(isDelete){
        for(let i = checkList.length - 1; i >= 0; i--){
            let isChecked = checkList[i].checked;
            if(isChecked){
                let checkedRow = checkList[i].parentNode.parentNode;
                let tableBody = checkedRow.parentNode;
                tableBody.removeChild(checkedRow);
                product.options.namedItem(checkedRow.cells[2].textContent).style.display="block";
            }
        }
        let tableBody =document.getElementById("lineItemsBody")
        let rows = tableBody.getElementsByTagName("tr");
        for (let i = 0; i < rows.length; i++) {
            rows[i].cells[1].textContent = i + 1;
        } 
        delBtn.disabled=true
        checkBoxHead.checked=false 
    }
    else{
        delBtn.disabled=false
        return
    }
}
function selectDeselectAllLineItems(checkBox){
    let delBtn= document.getElementById("deleteSelectedBtn");
    let checkList=document.getElementById("lineItemsBody").getElementsByClassName("checkLineItems")
    if(document.getElementById("lineItemsBody").innerHTML !==""){
        if(checkBox.checked){
            delBtn.disabled=false    
            for(let i = checkList.length - 1; i >= 0; i--){
                checkList[i].checked=true
                checkList[i].parentNode.parentNode.style.backgroundColor="#f2f2f2"
             }
        }
        else{
            delBtn.disabled=true
            for(let i = checkList.length - 1; i >= 0; i--){
                checkList[i].checked=false
                checkList[i].parentNode.parentNode.style.backgroundColor="white"
             }
        }
    }
    else{
        delBtn.disabled=true
        checkBox.checked=false
    } 
}

function validateForm(){
    let quoteDate = document.getElementById('quoteDate').value;
    let vtd = document.getElementById('vtd').value;
    let vendor = document.getElementById('vendor').value;
    let tbody = document.getElementById("lineItemsBody");
    let isValid=true;
    if(vendor == "Select") {
        document.getElementById("vendor").classList.add("error");
        isValid=false;
    }else{
        document.getElementById("vendor").classList.remove("error");
    }
    if(quoteDate === "" ){
        document.getElementById("quoteDate").classList.add("error");
        isValid=false;
    }
    else{
        document.getElementById("quoteDate").classList.remove("error");
    }
    if(vtd == "" ){
        document.getElementById("vtd").classList.add("error");
        isValid=false;
    } 
    else{
        document.getElementById("vtd").classList.remove("error");
    }
    if(tbody.innerHTML==""){
        document.getElementById("fs").style.borderColor="red";
        isValid= false;
    }else{
        document.getElementById("fs").style.borderColor="#ccc";
    } 
    if(!isValid){
        return false;
    }
    else{
        return true;
    }


}
//when click on submit form button
let quotationObject={};
function submitForm(){
    if(!validateForm()){
        return false;
    }
    else{
        let quotationId = document.getElementById('quoteId').innerHTML;
        let date = document.getElementById('quoteDate').value;
        let validTill = document.getElementById('vtd').value;
        let vendor = document.getElementById('vendor').value;
        let lineItemTable= document.getElementById("lineItemsBody");
        let rows=lineItemTable.getElementsByTagName("tr");
        let lineItems = [];
        for (let  eachRow= 0; eachRow < rows.length; eachRow++) {
            let cells = rows[eachRow].getElementsByTagName("td");
            let itemData = {};
            itemData[`sNo`] = cells[1].innerHTML;
            itemData[`product`] = cells[2].innerHTML;
            itemData[`price`] = cells[3].innerHTML.substring(1);
            itemData[`quantity`] = cells[4].innerHTML;
            itemData[`uom`] = cells[5].innerHTML;
            itemData[`amount`] = cells[6].innerHTML.substring(1);
            lineItems.push(itemData);
        }
        quotationObject[quotationId]={
            quotationId:quotationId,
            date: date,
            validTill: validTill,
            vendor: vendor,
            lineItems:lineItems
        };
		//console.log(quotationObj);
        addQuotation(quotationObject[quotationId]);
        return true;
    }
}

//add quotation
function addQuotation(quotation){
	let quotationString = JSON.stringify(quotation);
	let xhttp=new XMLHttpRequest();
	xhttp.onload=function(){
		if(this.status==200){
			let lineItemCount=parseInt(this.responseText);
			let confirmMsg=document.getElementById("confirmMsg");
			confirmMsg.style.display='block';
			if(lineItemCount>0){
				confirmMsg.style.color ='green';
				window.location.href="ViewQuotation?page=1&confirmMsg=Added Successfully";
			}
			else{
				confirmMsg.style.color ='red';
				confirmMsg.innerHTML="Failed to Add Quotation";
			}
			
			
		}
		else {
		    console.error('Request failed with status:', xhttp.status);
		}
		
	}
	xhttp.open("POST", "InsertQuotation", false);
	xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xhttp.send("quotation="+quotationString);
	document.getElementById("lineItemsBody").innerHTML="";
	document.getElementById("formDiv").style.display="none";
	clear();
	errorClassRemove();
	
}
//confirm Message after insertion
/*function showAlertBox(msg,count,action){
	document.getElementById("formDiv").style.display="none";
    let confirmDiv = document.getElementById("confirmDiv");
	confirmDiv.style.display = "block";
    document.getElementById("confirmContent").innerHTML = msg;
	if(action=="add"){
		document.getElementById("lineItemCount").innerHTML="LineItem: "+count;
	}else{
		document.getElementById("lineItemCount").innerHTML="";
	}
    let okBtn = document.getElementById("ok");
    okBtn.onclick=function(){
		if(action=="add"){
			if(+count){
				okBtn.href="ViewQuotation?page=1";		
			}
			else{
				okBtn.removeAttribute("href");
				document.getElementById("formDiv").style.display="block";
		    }
		}
		else{
			if(+count){
				okBtn.href="";		
			}
			else{
				okBtn.removeAttribute("href");
				document.getElementById("formDiv").style.display="block";
			}			
		}
		
		
        confirmDiv.style.display = "none";
    }
}*/

//Update Quotation
function updateQuotation(button,lineItems) {
	document.getElementById("formDiv").style.display = "block";
	document.title="Update Quotation";
	clear();
    let updRow = button.parentNode.parentNode;
	let quotationId=updRow.cells[2].textContent;
    document.getElementById("formh2").innerHTML="Update Quotation"
    document.getElementById('quoteId').innerHTML =quotationId;
    document.getElementById('quoteDate').value =formatDateToYYYYMMDD(updRow.cells[4].textContent);
    document.getElementById('vtd').value =formatDateToYYYYMMDD(updRow.cells[5].textContent);
    document.getElementById('vendor').value =  updRow.cells[3].textContent;
    let LIBody = document.getElementById("lineItemsBody");
    LIBody.innerHTML="";
    let productOptions = document.getElementById("productName").options;
    for (let i = 0; i < productOptions.length; i++) {
        productOptions[i].style.display = "block";
    }
	lineItems.forEach((lineItem,index)=>{
		let newRow = LIBody.insertRow(index);
		newRow.insertCell(0).innerHTML = '<input type="checkbox" class="checkLineItems" onclick="enableDeleteBtnLI()">';
		newRow.insertCell(1).innerHTML=index+1;
		newRow.insertCell(2).innerHTML=lineItem.product;
		newRow.insertCell(3).innerHTML='&#8377 '+lineItem.price;
		newRow.insertCell(4).innerHTML=lineItem.quantity;
		newRow.insertCell(5).innerHTML=lineItem.uom;
		newRow.insertCell(6).innerHTML='&#8377 '+lineItem.lineNetAmt;				
		newRow.insertCell(7).innerHTML='<i class="fa fa-edit" onclick="updateLineItem(this)"></i><i class="fa fa-trash" onclick="deleteLineItem(this)"></i>';
		productOptions.namedItem(lineItem.product).style.display="none";				
	})				
    let sub2UpdBtn=document.getElementById("submit");
    sub2UpdBtn.textContent="Update"
    sub2UpdBtn.onclick=function(){
        if(!validateForm()){
            return;
        }
        let quoteDate =document.getElementById('quoteDate').value;
        let vtd = document.getElementById('vtd').value;
        let vendor = document.getElementById('vendor').value;
        let tbody = document.getElementById("lineItemsBody");
        let row=tbody.getElementsByTagName('tr')
        let lineItems = [];
        for (let  eachRow= 0; eachRow < row.length; eachRow++) {
            let cells = row[eachRow].getElementsByTagName("td");
            let itemData = {};
            itemData["sNo"] = cells[1].innerHTML;
            itemData["product"] = cells[2].innerHTML;
            itemData[`price`] = cells[3].innerHTML.substring(1);
            itemData[`quantity`] = cells[4].innerHTML;
            itemData[`uom`] = cells[5].innerHTML;
            itemData[`amount`] = cells[6].innerHTML.substring(1);
            lineItems.push(itemData);
        }
		let quotationObj={
			quotationId:quotationId,
		    date: quoteDate,
	        validTill: vtd,
			vendor: vendor,
			lineItems:lineItems
		};
        let updtd = updRow.getElementsByTagName("td");
        updtd[3].innerHTML=vendor;
        updtd[4].innerHTML=formatDateToDDMMYYYY(quoteDate);
        updtd[5].innerHTML=formatDateToDDMMYYYY(vtd);
		let xhttp=new XMLHttpRequest();		
		xhttp.onload=function(){
			if(this.status==200){
				let lineItemCount=parseInt(this.responseText);
				let confirmMsg=document.getElementById("confirmMsg");
				confirmMsg.style.display='block';
				if(lineItemCount>0){
					confirmMsg.style.color ='green';
					confirmMsg.innerHTML=`Quotation ID-${quotationId} Updated Successfully`;
				}
				else{
					confirmMsg.style.color ='red';
					confirmMsg.innerHTML=`Quotation ID-${quotationId} Failed to Update`;
				}	
			}
			else {
				console.error('Request failed with status:', xhttp.status);
			}
				
		}
		xhttp.open("POST", "UpdateQuotation", false);
		xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		xhttp.send("quotation="+JSON.stringify(quotationObj));
		document.getElementById("lineItemsBody").innerHTML="";
        document.getElementById("formDiv").style.display = "none";
		clear();
        errorClassRemove();
    }
     
}

//Delete Quotation
async function deleteQuotation(button) {
    let row = button.parentNode.parentNode;
	let quotationId=row.cells[2].textContent;
    let alertDiv = document.getElementById("alertDiv");
    alertDiv.style.display = "block";
	let quotationIds=[];
    let isDelete= await showConfirmBox(`Do you want to delete Quotation ID: ${quotationId} ?`);
    if(isDelete){
		quotationIds[0]=quotationId;
		let xhttp=new XMLHttpRequest();		
		xhttp.onload=function(){
		if(this.status==200){
			let quotationCount=parseInt(this.responseText);
			let confirmMsg=document.getElementById("confirmMsg");
			confirmMsg.style.display='block';
			if(quotationCount>0){	
				confirmMsg.style.color ='green';
				confirmMsg.innerHTML=`Quotation ID-${quotationId} Deleted Successfully`;
			}
			else{
				confirmMsg.style.color ='red';
				confirmMsg.innerHTML=`Quotation ID-${quotationId} Failed to Delete`;
			}	
		}
		else {
			console.error('Request failed with status:', xhttp.status);
		}				
		}
		xhttp.open("POST", "DeleteQuotation", false);
		xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		xhttp.send("quotationIds="+JSON.stringify(quotationIds));
		window.location.href="";	
		        
    }
    else{
        return;
    }
}

//when click on each Quotation-->respective row color change and control delete Btn, select and deselect all  checkbox
function enableDeleteBtn(){
   let delBtn= document.getElementById("deleteQuoteBtn");
   let checkBoxHead= document.getElementById("checkBoxHead");
   let checkList=document.getElementById("quoteBody").getElementsByClassName("check")
   let checkCount=0;
   for(let i = checkList.length - 1; i >= 0; i--){
      let isChecked = checkList[i].checked;
      if(isChecked){
        checkCount++;
        checkList[i].parentNode.parentNode.style.backgroundColor="#f2f2f2"
      }
      else{
        checkList[i].parentNode.parentNode.style.backgroundColor="white"
      }
   }
   if(checkCount){
        delBtn.disabled=false
   }
   else{
        delBtn.disabled=true
   }
   if(checkCount==checkList.length){
        checkBoxHead.checked=true
   }
   else{
        checkBoxHead.checked=false
   }
   
}
async function deleteSelectedQuote(){
	let msg="Do you want to delete all the Selected Quotations?";
    let alertDiv = document.getElementById("alertDiv");
    alertDiv.style.display = "block";
    let delBtn= document.getElementById("deleteQuoteBtn");
    let checkBoxHead= document.getElementById("checkBoxHead");
    let checkList=document.getElementById("quoteBody").getElementsByClassName("check");
	let quotationIds=[];
	for(let i = checkList.length - 1; i >= 0; i--){
		let isChecked = checkList[i].checked;
		if(isChecked){
			let checkedRow = checkList[i].parentNode.parentNode;
			quotationIds.push(checkedRow.cells[2].textContent);
	    }
	}
	if(quotationIds.length==1){
		msg=`Do you want to delete Quotation ID: ${quotationIds[0]} ?`
	}
    let isDelete= await showConfirmBox(msg);
    if(isDelete){
		let xhttp=new XMLHttpRequest();		
		xhttp.onload=function(){
		if(this.status==200){
			let quotationCount=parseInt(this.responseText);
			let alertMsg;
			if(quotationCount>0){
				document.getElementById("confirmBox").style.border = '1.5px solid green';
				alertMsg="Quotation Deleted Successfully !!";
				showAlertBox(alertMsg,quotationCount,"delete");
			}
			else{
				document.getElementById("confirmBox").style.border = '1.5px solid red';
				alertMsg="Failed to Delete Quotation !!";
				showAlertBox(alertMsg,quotationCount,"delete");
			}	
		}
		else {
			console.error('Request failed with status:', xhttp.status);
		}				
		}
		xhttp.open("POST", "DeleteQuotation", false);
		xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		xhttp.send("quotationIds="+JSON.stringify(quotationIds));
		delBtn.disabled=true;
		checkBoxHead.checked=false;
        
    }
    else{
		quotationIds=[];
        delBtn.disabled=false
        return
    }
}
   
function selectDeselectAll(checkBox){
    let checkList=document.getElementById("quoteBody").getElementsByClassName("check")
    let delBtn= document.getElementById("deleteQuoteBtn");
    if(document.getElementById("quoteBody").innerHTML !==""){
        if(checkBox.checked){
            delBtn.disabled=false    
            for(let i = checkList.length - 1; i >= 0; i--){
                checkList[i].checked=true
                checkList[i].parentNode.parentNode.style.backgroundColor="#f2f2f2"
             }
        }
        else{
            delBtn.disabled=true
            for(let i = checkList.length - 1; i >= 0; i--){
                checkList[i].checked=false
                checkList[i].parentNode.parentNode.style.backgroundColor="white"
             }
        }
    }
    else{
        delBtn.disabled=true
        checkBox.checked=false
    }   
    
}
let searchQuoteInput = document.getElementById("searchQuoteInput");
searchQuoteInput.addEventListener("keydown", function(event) {
	let quotationId=searchQuoteInput.value;
	if(event.key=="Enter" && quotationId ){
		quotationId=quotationId.trim().toUpperCase();
		let quoteBody=document.getElementById("quoteBody");
		quoteBody.innerHTML="";
		let xhttp=new XMLHttpRequest();
		xhttp.onload=function(){
			if(this.status==200){
				//document.getElementById("thCheckBox").style.display="none";
				//document.getElementById("thSNo").style.display="none";
				let quotationObj=JSON.parse(this.responseText);
				if(quotationObj!=null){
					console.log(quotationObj);
					let newRow = quoteBody.insertRow(0);
					newRow.insertCell(0).innerHTML=`<input type="checkbox" class="check" onclick="enableDeleteBtn()">`
					newRow.insertCell(1).textContent = quoteBody.rows.length;
					newRow.insertCell(2).textContent = quotationObj.quotationId;
					newRow.insertCell(3).textContent = quotationObj.vendor;
				    newRow.insertCell(4).textContent = quotationObj.date;
					newRow.insertCell(5).textContent = quotationObj.validTill;
					let lineItems=JSON.stringify(quotationObj.lineItems);
					newRow.insertCell(6).innerHTML = `<i class="fa fa-eye" onclick='showLineItem(this, ${lineItems})'></i><i class="fa fa-edit" onclick='updateQuotation(this,${lineItems})'></i><i class="fa fa-trash" onclick="deleteQuotation(this)"></i>`;
					let paginationDiv=document.getElementById("pagination");
				    paginationDiv.innerHTML="<a href=''>&#8249; Back</a>";
						
				}
				else{
					quoteBody.innerHTML="<tr><td id='notFound' colspan='7' > Quotation Not Found !!</td></tr>";
					let paginationDiv=document.getElementById("pagination");
					paginationDiv.innerHTML="<a href=''>&#8249; Back</a>";
					
					
				}
			}
		}
		xhttp.open("GET", "ViewQuotation?quotationId="+quotationId, false);
		xhttp.send();
	}
	
  	
  }
);