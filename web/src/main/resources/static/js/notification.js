async function receiveNotification() {
    let notifications = document.getElementById("notifications");
    let url = window.location.origin + "/api/v1/notification/";
    fetch(url)
        .then(response => response.json())
        .then(json => {
            removeAllChildNodes(notifications);
            json.forEach(item => {
                let newDiv = document.createElement("div");
                newDiv.classList.add("notification");
                notifications.appendChild(newDiv);
                let p = document.createElement("p");
                p.textContent = item;
                newDiv.appendChild(p);
            });
        });
}

function removeAllChildNodes(parent) {
    while (parent.firstChild) {
        parent.removeChild(parent.firstChild);
    }
}

// async function initiateNotification() {
//     while (true) {
//         receiveNotification();
//         await new Promise(r => setTimeout(r, 3000));
//     }
// }