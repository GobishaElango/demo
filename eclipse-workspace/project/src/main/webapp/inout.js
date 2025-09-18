function viewInoutLine(inoutId) {
    const xhr = new XMLHttpRequest();
    xhr.open("GET", "InoutServlet?action=viewInoutLine&inoutId=" + encodeURIComponent(inoutId), true);
    xhr.onload = function () {
        if (xhr.status === 200) {
            const InoutLine = document.getElementById('inoutLineView');
            if (InoutLine) {
				document.getElementById('inoutLineView').innerHTML = xhr.responseText
				    + '<button id="closeBtn" onclick="CloseLineView()">Close</button>';
				document.getElementById('LineView').style.display = 'flex'; 
            }
        } else {
            console.error('Error fetching inout ');
        }
    };
    xhr.send();
}
 
function CloseLineView() {
    document.getElementById('LineView').style.display = 'none';
}

//viewInoutLine('C8C3704D458B46D0BF3548D26D9A9114');