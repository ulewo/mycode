// The uploadStart event handler.  This function variable is assigned to upload_start_handler in the settings object
function fileDialogComplete(numFilesSelected, numFilesQueued, numFilesInQueue) {
	var table = document.getElementById("filesTable");
	table.style.display = "inline";
}
function fileQueued(file) {
	var table = document.getElementById("filesTable");
	var row = table.insertRow();
	var col1 = row.insertCell();
	var col2 = row.insertCell();
	var col3 = row.insertCell();
	row.id = file.id;
	col1.innerHTML = file.name;
	col2.innerHTML = file.size;
	col3.innerHTML = showStatus(file.filestatus);
}
//QUEUED - indicates the this file is waiting in the queue.
//ERROR - indicates that this file caused a queue or upload error.
//COMPLETE - indicates that this file was successfully transmitted to the server.
function showStatus(status) {
	var word;
	switch (status) {
	  case SWFUpload.FILE_STATUS.QUEUED:
		word = "QUEUED";
		break;
	  case SWFUpload.FILE_STATUS.ERROR:
		word = "ERROR";
		break;
	  case SWFUpload.FILE_STATUS.COMPLETE:
		word = "COMPLETE";
		break;
	 }
	return word;
}
// The uploadSuccess event handler.  This function variable is assigned to upload_success_handler in the settings object
function uploadSuccess(file, server_data, receivedResponse) {
	var row = document.getElementById(file.id);
	row.cells[2].innerHTML = showStatus(file.filestatus);
}
// The uploadError event handler.  This function variable is assigned to upload_error_handler in the settings object
function uploadError(file, errorCode, message) {
	var row = document.getElementById(file.id);
	row.cells[2].innerHTML = showStatus(file.filestatus);
}
function uploadComplete(file) {
	this.startUpload();//continue to upload the next file
}

