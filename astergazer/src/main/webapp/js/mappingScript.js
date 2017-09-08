function loadScriptsForDialogList() {
    $.get(restControllerUrl + "/getscripts", function (data) {
        if (data.status == "OK") {
            var combobox = $("#select-script");
            combobox.empty();
            data.data.scriptList.forEach(function (item, i, arr) {
                combobox.append("<option value=" + item.id + ">" + item.name + "</option>");
            });
            combobox.val("0");
        } else {
            showErrorMessage(data.data.description);
        }
    });
}

function addScript() {
    $("#dialog-script")
    .dialog("option", "restUrl", restControllerUrl + "/addscript")
    .dialog("option", "name", "")
    .dialog("open");
}

function editScript(scriptId, scriptName) {
    if (typeof scriptId != "undefined") {
        $("#dialog-script")
        .dialog("option", "restUrl", restControllerUrl + "/updatescript/" + scriptId)
        .dialog("option", "name", scriptName)
        .dialog("open");
    }
}

function editCurrentScript() {
    var currentNode = $("#div-script-tree").jstree("get_selected", true)[0];
    if (typeof currentNode != "undefined") {
        var entityId = currentNode.data.id;
        editScript(entityId, currentNode.text);
    }
}

function cloneScript(scriptId) {
    if (typeof scriptId != "undefined") {
        $("#dialog-script")
        .dialog("option", "restUrl", restControllerUrl + "/clonescript/" + scriptId)
        .dialog("open");
    }
}


function cloneCurrentScript() {
    var currentNode = $("#div-script-tree").jstree("get_selected", true)[0];
    if (typeof currentNode != "undefined") {
        var entityId = currentNode.data.id;
        cloneScript(entityId, currentNode.text);
    }
}

function deleteScript(scriptId, name) {
    if (typeof scriptId != "undefined") {
        showConfirmation(deleteScriptConfirmText + " " + name + "?", function () {
            $.ajax({
                type: "POST",
                url: restControllerUrl + "/deletescript/" + scriptId,
                dataType: "json",
                async: false,
                cache: false,
                success: function (data) {
                    if (data.status == "OK") {
                        $("#div-script-tree").jstree("refresh");
                        $("#div-dialplan-tree").jstree("refresh");
                    } else {
                        showErrorMessage(data.data.description);
                    }
                },
                failure: function (errMsg) {
                    showErrorMessage(errMsg);
                }
            });
        });
    }
}

function deleteCurrentScript() {
    var currentNode = $("#div-script-tree").jstree("get_selected", true)[0];
    if (typeof currentNode != "undefined") {
        var entityId = currentNode.data.id;
        deleteScript(entityId, currentNode.text);
    }
}

function constructScript(id) {
    if (typeof id != "undefined") {
        $(location).attr("href", constructorControllerUrl + "/" + id);
    }
}

function constructCurrentScript() {
    var currentNode = $("#div-script-tree").jstree("get_selected", true)[0];
    if (typeof currentNode != "undefined") {
        var entityId = currentNode.data.id;
        constructScript(entityId);
    }
}

function initScriptDialog() {
    var dialogButtons = {};
    dialogButtons["OK"] = function () {
        var name = $("#input-script-name").val();
        var restUrl = $(this).dialog("option", "restUrl");
        $.ajax({
            type: "POST",
            url: restUrl,
            data: {
                name: name
            },
            dataType: "json",
            async: false,
            cache: false,
            success: function (data) {
                if (data.status == "OK") {
                    $("#div-script-tree").jstree("refresh");
                } else {
                    showErrorMessage(data.data.description);
                }
            },
            failure: function (errMsg) {
                showErrorMessage(errMsg);
            }
        });
        $(this).dialog("close");
    };
    dialogButtons[cancelText] = function () {
        $(this).dialog("close");
    };
    $("#dialog-script").dialog({
        autoOpen: false,
        modal: true,
        title: scriptNameText,
        open: function (event, ui) {
            $("#input-script-name").val($(this).dialog("option", "name"));
        },
        buttons: dialogButtons
    });
}
