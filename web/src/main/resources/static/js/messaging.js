async function sendMessage(offerId, user, message, key) {
    let url = window.location.origin + "/api/v1/chat/" + offerId;
    let toSend = {
        "sender": user,
        "message": message
    };
    fetch(url, {
        method: "POST",
        headers: {
            "Content-Type": "application/json;charset=utf-8",
            "credentials": key
        },
        body: JSON.stringify(toSend)
    })
    .then(response => {
        if (response.ok) {
            receiveMessage(offerId,user, key);
        }
    });
}

async function receiveMessage(offerId, user, key) {
    let msg_history = document.getElementsByClassName("msg_history")[0];
    let url = window.location.origin + "/api/v1/chat/" + offerId;
    fetch(url)
    .then(response => response.json())
    .then(json => {
        removeAllChildNodes(msg_history);
        json.forEach( item => {
            let message = item.contents;
            let sender = item.sender.username;
            if (user == sender) {
                let newDiv = document.createElement("div");
                newDiv.classList.add("outgoing_msg");
                let secondary = document.createElement("div");
                secondary.classList.add("sent_msg");
                newDiv.appendChild(secondary);
                let triciary = document.createElement("div");
                triciary.classList.add("received_withd_msg");
                secondary.appendChild(triciary);
                let p = document.createElement("p");
                p.textContent = message;
                let span = document.createElement("span");
                span.classList.add("time_date");
                span.textContent = item.dateTimeSent;
                triciary.appendChild(p);
                triciary.appendChild(span);
                msg_history.appendChild(newDiv);
            } else {
                let newDiv = document.createElement("div");
                newDiv.classList.add("incoming_msg");
                let secondary = document.createElement("div");
                secondary.classList.add("received_msg");
                newDiv.appendChild(secondary);
                let p = document.createElement("p");
                p.textContent = message;
                secondary.appendChild(p);
                msg_history.appendChild(newDiv)
            }
        });
    });
}

async function escrow() {
    let offerId = document.getElementById("messaging").dataset.offer;
    let url = window.location.origin + "/api/v1/chat/" + offerId + "/escrow";
    basicUserFunctions(url);
}

async function fiatsent() {
    let offerId = document.getElementById("messaging").dataset.offer;
    let url = window.location.origin + "/api/v1/chat/" + offerId + "/marksent";
    basicUserFunctions(url);
}

async function fiatreceived() {
    let offerId = document.getElementById("messaging").dataset.offer;
    let url = window.location.origin + "/api/v1/chat/" + offerId + "/markreceived";
    basicUserFunctions(url);  
}

async function basicUserFunctions(url) {
    let username = document.getElementById("messaging").dataset.user;
    let key = document.getElementById("messaging").dataset.key;
    await fetch(url, {
        method: "POST",
        headers: {
            "credentials": key
        }
    })
    .then(response => response.json())
    .then(json => {
        console.log(json);
        let status = json.status;
        if (status == "Ok") {
            location.reload();
        } else {
            alert("Server Error:\n" + json.status);
        }
    });
}

function removeAllChildNodes(parent) {
    while (parent.firstChild) {
        parent.removeChild(parent.firstChild);
    }
}

function send() {
    let offerId = document.getElementById("messaging").dataset.offer;
    let username = document.getElementById("messaging").dataset.user;
    let key = document.getElementById("messaging").dataset.key;
    let message = document.getElementById("text").value;
    sendMessage(offerId,username, message, key);
    document.getElementById("text").value = "";
}

async function initiateMessaging() {
    let offerId = document.getElementById("messaging").dataset.offer;
    let username = document.getElementById("messaging").dataset.user;
    let key = document.getElementById("messaging").dataset.key;
    while(true) {
        receiveMessage(offerId, username, key);
        await new Promise(r => setTimeout(r, 3000));
    }
}