function addContext() {
    $("#dialog-context")
            .dialog("option", "restUrl", restControllerUrl + "/addcontext")
            .dialog("option", "name", "")
            .dialog("open");
}

function addExtension(contextId) {
    loadScriptsForDialogList();
    $("#dialog-extension")
            .dialog("option", "restUrl", restControllerUrl + "/addextension")
            .dialog("option", "name", "")
            .dialog("option", "contextId", contextId)
            .dialog("option", "scriptId", 0)
            .dialog("open");
}

function addExtensionToCurrentContext() {
    var currentNode = $("#div-dialplan-tree").jstree("get_selected", true)[0];
    if (typeof currentNode != "undefined") {
        if (currentNode.type == "context") {
            var contextId = currentNode.data.id;
        } else if (currentNode.type == "extension") {
            var contextId = currentNode.data.contextId;
        } else {
            showErrorMessage(unknownNodeTypeErrorText);
            return;
        }
        addExtension(contextId);
    }
}

function editContext(id, name) {
    if (typeof id != "undefined") {
        $("#dialog-context")
                .dialog("option", "restUrl", restControllerUrl + "/updatecontext/" + id)
                .dialog("option", "name", name)
                .dialog("open");
    }
}

function deleteContext(id, name) {
    if (typeof id != "undefined") {
        showConfirmation(deleteContextConfirmText + " " + name + "?", function() {
            $.ajax({
                type : "POST",
                url : restControllerUrl + "/deletecontext/" + id,
                dataType : "json",
                async : false,
                cache : false,
                success : function(data) {
                    if (data.status == "OK") {
                        $("#div-dialplan-tree").jstree("refresh");
                    } else {
                        showErrorMessage(data.data.description);
                    }
                },
                failure : function(errMsg) {
                    showErrorMessage(errMsg);
                }
            });
        });
    }
}

function editExtension(id, name, scriptId) {
    loadScriptsForDialogList(scriptId);
    if (typeof id != "undefined") {
        $("#dialog-extension").dialog("option", "restUrl",
                restControllerUrl + "/updateextension/" + id)
                .dialog("option", "name", name)
                .dialog("option", "scriptId", scriptId)
                .dialog("open");
    }
}

function deleteExtension(id, name) {
    if (typeof id != "undefined") {
        showConfirmation(deleteExtensionConfirmText + " " + name + "?", function() {
            $.ajax({
                type : "POST",
                url : restControllerUrl + "/deleteextension/" + id,
                dataType : "json",
                async : false,
                cache : false,
                success : function(data) {
                    if (data.status == "OK") {
                        $("#div-dialplan-tree").jstree("refresh");
                    } else {
                        showErrorMessage(data.data.description);
                    }
                },
                failure : function(errMsg) {
                    showErrorMessage(errMsg);
                }
            });
        });
    }
}

function editCurrentDialplanTreeNode() {
    var currentNode = $("#div-dialplan-tree").jstree("get_selected", true)[0];
    if (typeof currentNode != "undefined") {
        if (currentNode.type == "context") {
            var entityId = currentNode.data.id;
            editContext(entityId, currentNode.text);
        } else if (currentNode.type == "extension") {
            var entityId = currentNode.data.id;
            var scriptId = currentNode.data.scriptId;
            editExtension(entityId, currentNode.text, scriptId);
        } else {
            showErrorMessage(unknownNodeTypeErrorText);
        }
    }
}

function deleteCurrentDialplanTreeNode() {
    var currentNode = $("#div-dialplan-tree").jstree("get_selected", true)[0];
    if (typeof currentNode != "undefined") {
        if (currentNode.type == "context") {
            var entityId = currentNode.data.id;
            deleteContext(entityId, currentNode.text);
        } else if (currentNode.type == "extension") {
            var entityId = currentNode.data.id;
            deleteExtension(entityId, currentNode.text);
        } else {
            showErrorMessage(unknownNodeTypeErrorText);
        }
    }
}

function cloneContext(contextId) {
    if (typeof contextId != "undefined") {
        $("#dialog-context")
        .dialog("option", "restUrl", restControllerUrl + "/clonecontext/" + contextId)
        .dialog("open");
    }
}

function cloneCurrentContext() {
    var currentNode = $("#div-dialplan-tree").jstree("get_selected", true)[0];
    if (typeof currentNode != "undefined") {
        if (currentNode.type == "context") {
            var contextId = currentNode.data.id;
        } else if (currentNode.type == "extension") {
            var contextId = currentNode.data.contextId;
        } else {
            showErrorMessage(unknownNodeTypeErrorText);
            return;
        }
        cloneContext(contextId);
    }
}

function initContextDialog() {
    var dialogButtons = {};
    dialogButtons["OK"] = function() {
        var name = $("#input-context-name").val();
        var restUrl = $(this).dialog("option", "restUrl");
        $.ajax({
            type : "POST",
            url : restUrl,
            data : {
                name : name
            },
            dataType : "json",
            async : false,
            cache : false,
            success : function(data) {
                if (data.status == "OK") {
                    $("#div-dialplan-tree").jstree("refresh");
                } else {
                    showErrorMessage(data.data.description);
                }
            },
            failure : function(errMsg) {
                showErrorMessage(errMsg);
            }
        });
        $(this).dialog("close");
    };
    dialogButtons[cancelText] = function() {
        $(this).dialog("close"); 
    };
    $("#dialog-context").dialog({
        autoOpen : false,
        modal : true,
        title : contextNameText,
        open : function(event, ui) {
            $("#input-context-name").val($(this).dialog("option", "name"));
        },
        buttons : dialogButtons
    });
}

function initExtensionDialog() {
    var dialogButtons = {};
    dialogButtons["OK"] = function() {
        if ($("#select-script").val() === null) {
            if ($("#select-script").combobox("getInputValue") != "") {
                var scriptId = $(this).dialog("option", "scriptId");
            } else {
                var scriptId = 0;
            }
        } else {
            var scriptId = ($("#select-script").val());
        }
        var name = $("#input-extension-name").val();
        var restUrl = $(this).dialog("option", "restUrl");
        var contextId = $(this).dialog("option", "contextId");
        $.ajax({
            type : "POST",
            url : restUrl,
            data : {
                name : name,
                contextId : contextId,
                scriptId : scriptId
            },
            dataType : "json",
            async : false,
            cache : false,
            success : function(data) {
                if (data.status == "OK") {
                    $("#div-dialplan-tree").jstree("refresh");
                } else {
                    showErrorMessage(data.data.description);
                }
            },
            failure : function(errMsg) {
                showErrorMessage(errMsg);
            }
        });
        $(this).dialog("close");
    };
    dialogButtons[cancelText] = function() {
        $(this).dialog("close"); 
    };
    $("#dialog-extension").dialog({
        autoOpen : false,
        modal : true,
        title : extensionNameText,
        open : function(event, ui) {
            var currentScriptId = $(this).dialog("option", "scriptId");
            $("#input-extension-name").val($(this).dialog("option", "name"));
            $("#select-script").combobox("autocomplete", currentScriptId);
        },
        buttons : dialogButtons,
    });
}

function clearSelectScript() {
    $("#select-script").combobox("autocomplete", null);
}
