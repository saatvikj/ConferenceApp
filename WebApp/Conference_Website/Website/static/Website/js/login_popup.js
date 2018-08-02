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