async function showPopup(message) {
    let display = document.getElementById("popup");
    let attachTo = document.getElementById("popup-content");
    let p = document.createElement("p");
    p.textContent = message;
    attachTo.appendChild(p);
    display.style.display = "block";
}

async function closePopup() {
    let detachFrom = document.getElementById("popup");
    detachFrom.style.display = "none";
}

async function loadpopup() {
    let message = getUrlParam("message", -1);
    if (message != -1) {
        message = message.replace("+", " ");
        showPopup(message);
    }
}

function getUrlVars() {
    var vars = {};
    var parts = window.location.href.replace(/[?&]+([^=&]+)=([^&]*)/gi, function(m,key,value) {
        vars[key] = value;
    });
    return vars;
}

function getUrlParam(parameter, defaultvalue){
    var urlparameter = defaultvalue;
    if(window.location.href.indexOf(parameter) > -1){
        urlparameter = getUrlVars()[parameter];
        }
    return urlparameter;
}