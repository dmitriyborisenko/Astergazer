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
    if (typeof currentNode !== "undefined") {
        var contextId;
        if (currentNode.type === "context") {
            contextId = currentNode.data.id;
        } else if (currentNode.type === "extension") {
            contextId = currentNode.data.contextId;
        } else {
            showErrorMessage(unknownNodeTypeErrorText);
            return;
        }
        addExtension(contextId);
    }
}

function editContext(id, name) {
    if (typeof id !== "undefined") {
        $("#dialog-context")
                .dialog("option", "restUrl", restControllerUrl + "/updatecontext/" + id)
                .dialog("option", "name", name)
                .dialog("open");
    }
}

function deleteContext(id, name) {
    if (typeof id !== "undefined") {
        showConfirmation(deleteContextConfirmText + " " + name + "?", function() {
            $.ajax({
                type : "POST",
                url : restControllerUrl + "/deletecontext/" + id,
                async : true,
                cache : false,
                success : function () {
                    $("#div-dialplan-tree").jstree("refresh");
                },
                error : function (data) {
                    showErrorMessage(data.responseText);
                }
            });
        });
    }
}

function editExtension(id, name, scriptId) {
    loadScriptsForDialogList(scriptId);
    if (typeof id !== "undefined") {
        $("#dialog-extension").dialog("option", "restUrl",
                restControllerUrl + "/updateextension/" + id)
                .dialog("option", "name", name)
                .dialog("option", "scriptId", scriptId)
                .dialog("open");
    }
}

function deleteExtension(id, name) {
    if (typeof id !== "undefined") {
        showConfirmation(deleteExtensionConfirmText + " " + name + "?", function() {
            $.ajax({
                type : "POST",
                url : restControllerUrl + "/deleteextension/" + id,
                async : true,
                cache : false,
                success : function () {
                    $("#div-dialplan-tree").jstree("refresh");
                },
                error : function (data) {
                    showErrorMessage(data.responseText);
                }
            });
        });
    }
}

function editCurrentDialplanTreeNode() {
    var currentNode = $("#div-dialplan-tree").jstree("get_selected", true)[0];
    if (typeof currentNode !== "undefined") {
        var entityId;
        if (currentNode.type === "context") {
            entityId = currentNode.data.id;
            editContext(entityId, currentNode.text);
        } else if (currentNode.type === "extension") {
            entityId = currentNode.data.id;
            var scriptId = currentNode.data.scriptId;
            editExtension(entityId, currentNode.text, scriptId);
        } else {
            showErrorMessage(unknownNodeTypeErrorText);
        }
    }
}

function deleteCurrentDialplanTreeNode() {
    var currentNode = $("#div-dialplan-tree").jstree("get_selected", true)[0];
    if (typeof currentNode !== "undefined") {
        var entityId;
        if (currentNode.type === "context") {
            entityId = currentNode.data.id;
            deleteContext(entityId, currentNode.text);
        } else if (currentNode.type === "extension") {
            entityId = currentNode.data.id;
            deleteExtension(entityId, currentNode.text);
        } else {
            showErrorMessage(unknownNodeTypeErrorText);
        }
    }
}

function cloneContext(contextId) {
    if (typeof contextId !== "undefined") {
        $("#dialog-context")
        .dialog("option", "restUrl", restControllerUrl + "/clonecontext/" + contextId)
        .dialog("open");
    }
}

function cloneCurrentContext() {
    var currentNode = $("#div-dialplan-tree").jstree("get_selected", true)[0];
    if (typeof currentNode !== "undefined") {
        var contextId;
        if (currentNode.type === "context") {
            contextId = currentNode.data.id;
        } else if (currentNode.type === "extension") {
            contextId = currentNode.data.contextId;
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
            async : true,
            cache : false,
            success : function () {
                $("#div-dialplan-tree").jstree("refresh");
            },
            error : function (data) {
                showErrorMessage(data.responseText);
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
        open : function() {
            $("#input-context-name").val($(this).dialog("option", "name"));
        },
        buttons : dialogButtons
    });
}

function initExtensionDialog() {
    var dialogButtons = {};
    dialogButtons["OK"] = function() {
        var scriptId;
        var scriptSelector = $("#select-script");
        if (scriptSelector.val() === null) {
            if (scriptSelector.combobox("getInputValue") !== "") {
                scriptId = $(this).dialog("option", "scriptId");
            } else {
                scriptId = 0;
            }
        } else {
            scriptId = (scriptSelector.val());
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
            async : true,
            cache : false,
            success : function () {
                $("#div-dialplan-tree").jstree("refresh");
            },
            error : function (data) {
                showErrorMessage(data.responseText);
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
        open : function() {
            var currentScriptId = $(this).dialog("option", "scriptId");
            $("#input-extension-name").val($(this).dialog("option", "name"));
            $("#select-script").combobox("autocomplete", currentScriptId);
        },
        buttons : dialogButtons
    });
}

function clearSelectScript() {
    $("#select-script").combobox("autocomplete", null);
}
