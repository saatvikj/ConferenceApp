var modal = document.getElementById('my_modal');
var btn = document.getElementById("my_btn");
var closeBtn = document.getElementsByClassName("close")[0];
btn.onclick = function() {
    modal.style.display = "block";
}
window.onclick = function(event) {
    if (event.target == modal) {
        modal.style.display = "none";
    }
}
closeBtn.onclick = function() {
	modal.style.display = "none";
}

// var topnav = document.getElementsByClassName('topnav')[0];
// var topnav_height = topnav.style.height();
// console.log(topnav_height);
// var content = document.getElementsByClassName('full-height')[0];
// content.style.height() = 'calc(100vh-topnav_height)';
