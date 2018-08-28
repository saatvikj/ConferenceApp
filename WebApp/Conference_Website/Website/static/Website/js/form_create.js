var currentTab = 0; // Current tab is set to be the first tab (0)
showTab(currentTab); // Display the crurrent tab

function showTab(n) {
  // This function will display the specified tab of the form...
  var x = document.getElementsByClassName("content-create-form");
  x[n].style.display = "block";
 
}

function nextPrev(n) {

  var x = document.getElementsByClassName("content-create-form");
  x[currentTab].style.display = "none";
  currentTab = currentTab + n;
  showTab(currentTab);
}
