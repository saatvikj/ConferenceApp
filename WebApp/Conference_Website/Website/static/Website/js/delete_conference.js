var btn = document.getElementById("delete-conference-btn");

btn.onclick = function() {
	
	if (confirm("Are you sure you want to delete? The action is irreversible.")) {
		document.getElementById("delete-conference").submit();		
	}
}