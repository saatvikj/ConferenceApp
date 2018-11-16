var delete_btn = document.getElementById("delete-conference-btn");

delete_btn.onclick = function() {
		
	if (confirm("Are you sure you want to delete? The action is irreversible.")) {
		document.getElementById("function").value = "delete";
		document.getElementById("modify-conference").submit();		
	}
}

var download_btn = document.getElementById("download-apk-btn");

download_btn.onclick = function() {
	document.getElementById("function").value = "download";
	document.getElementById("modify-conference").submit();	
}