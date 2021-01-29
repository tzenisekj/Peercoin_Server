document.getElementById('login-btn').addEventListener('click', e => {
    document.getElementById('sign-in-card').classList.add('active');  
    document.getElementById('overlay').classList.add('active'); 
})

document.getElementById('overlay').addEventListener('click', e => {
    if(document.getElementById('overlay').classList.contains('active')) {
        document.getElementById('overlay').classList.remove('active'); 
        document.getElementById('sign-in-card').classList.remove('active'); 
    }
    else {
        return; 
    }
})



