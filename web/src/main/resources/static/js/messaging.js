async function sendMessage(offerId, user, message) {
    let url = window.location.origin + "/api/v1/chat/" + offerId;
    let toSend = {
        "sender": user,
        "message": message
    };
    await fetch(url, {
        method: "POST",
        headers: {
            "Content-Type": "application/json;charset=utf-8"
        },
        body: JSON.stringify(toSend)
    })
    .then(response => {
        console.log(response);
        receiveMessage(offerId,user);
    });
}

function receiveMessage(offerId, user) {
    let msg_history = document.getElementsByClassName("msg_history")[0];
    console.log(msg_history);
    let url = window.location.origin + "/api/v1/chat/" + offerId;
    fetch(url)
    .then(response => response.json())
    .then(json => {
        removeAllChildNodes(msg_history);
        console.log(json)
        json.forEach( item => {
            let message = item.contents;
            console.log(message);
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

function removeAllChildNodes(parent) {
    while (parent.firstChild) {
        parent.removeChild(parent.firstChild);
    }
}

function send() {
    let offerId = document.getElementById("messaging").dataset.offer;
    let username = document.getElementById("messaging").dataset.user;
    let message = document.getElementById("text").value;
    sendMessage(offerId,username, message);    
    document.getElementById("text").value = "";
}

async function initiateMessaging() {
    console.log("messaging initiated");
    let offerId = document.getElementById("messaging").dataset.offer;
    let username = document.getElementById("messaging").dataset.user;
    console.log(offerId);
    while(true) {
        receiveMessage(offerId, username);
        await new Promise(r => setTimeout(r, 3000));
    }
}