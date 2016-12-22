function initMessageDialog() {
	$("#dialog-message").dialog({
	  	autoOpen: false,
	    modal: true,
	    buttons : {
	    	"OK" : function() {
	            $(this).dialog("close");
	        },
	    }
	});
}

function showErrorMessage(text) {
	$("#dialog-message")
	.empty()
	.append(text)
	.dialog("option", "title", errorText)
	.dialog({dialogClass:"dialog-error"})
	.dialog("open");
}

function showWarningMessage(text) {
	$("#dialog-message")
	.empty()
	.append(text)
	.dialog("option", "title", warningText)
	.dialog({dialogClass:"dialog-warning"})
	.dialog("open");
}

function showInformationMessage(text) {
	$("#dialog-message")
	.empty()
	.append(text)
	.dialog("option", "title", informationText)
	.dialog({dialogClass:""})
	.dialog("open");
}

function showConfirmation(text, onOkFunction) {
    var dialogButtons = {};
    dialogButtons["OK"] = function() {
        onOkFunction();
        $(this).dialog("close");    
    }
    dialogButtons[cancelText] = function() {
        $(this).dialog("close"); 
    }
    var dialog = $("#dialog-confirmation").dialog({
	  	autoOpen: false,
	    modal: true,
	    buttons : dialogButtons
	});
	dialog.empty()
	.append(text)
	.dialog("option", "title", confirmationText)
	.dialog("open");
}


$(document).ready(function() {
    initMessageDialog();
});