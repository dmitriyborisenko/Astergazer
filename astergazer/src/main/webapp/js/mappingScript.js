function loadScriptsForDialogList() {
    $.ajax({
        type : "GET",
        url : restControllerUrl + "/getscripts",
        async : true,
        cache : false,
        success : function (data) {
            var combobox = $("#select-script");
            combobox.empty();
            data.scriptList.forEach(function (item) {
                combobox.append("<option value=" + item.id + ">" + item.name + "</option>");
            });
            combobox.val("0");
        },
        error : function (data) {
            showErrorMessage(data.responseText);
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
    if (typeof scriptId !== "undefined") {
        $("#dialog-script")
        .dialog("option", "restUrl", restControllerUrl + "/updatescript/" + scriptId)
        .dialog("option", "name", scriptName)
        .dialog("open");
    }
}

function editCurrentScript() {
    var currentNode = $("#div-script-tree").jstree("get_selected", true)[0];
    if (typeof currentNode !== "undefined") {
        var entityId = currentNode.data.id;
        editScript(entityId, currentNode.text);
    }
}

function cloneScript(scriptId) {
    if (typeof scriptId !== "undefined") {
        $("#dialog-script")
        .dialog("option", "restUrl", restControllerUrl + "/clonescript/" + scriptId)
        .dialog("open");
    }
}


function cloneCurrentScript() {
    var currentNode = $("#div-script-tree").jstree("get_selected", true)[0];
    if (typeof currentNode !== "undefined") {
        var entityId = currentNode.data.id;
        cloneScript(entityId, currentNode.text);
    }
}

function deleteScript(scriptId, name) {
    if (typeof scriptId !== "undefined") {
        showConfirmation(deleteScriptConfirmText + " " + name + "?", function () {
            $.ajax({
                type: "POST",
                url: restControllerUrl + "/deletescript/" + scriptId,
                async: true,
                cache: false,
                success: function () {
                    $("#div-script-tree").jstree("refresh");
                    $("#div-dialplan-tree").jstree("refresh");
                },
                error : function (data) {
                    showErrorMessage(data.responseText);
                }
            });
        });
    }
}

function deleteCurrentScript() {
    var currentNode = $("#div-script-tree").jstree("get_selected", true)[0];
    if (typeof currentNode !== "undefined") {
        var entityId = currentNode.data.id;
        deleteScript(entityId, currentNode.text);
    }
}

function constructScript(id) {
    if (typeof id !== "undefined") {
        $(location).attr("href", constructorControllerUrl + "/" + id);
    }
}

function constructCurrentScript() {
    var currentNode = $("#div-script-tree").jstree("get_selected", true)[0];
    if (typeof currentNode !== "undefined") {
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
            async: true,
            cache: false,
            success: function () {
                $("#div-script-tree").jstree("refresh");
                $("#div-dialplan-tree").jstree("refresh");
            },
            error : function (data) {
                showErrorMessage(data.responseText);
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
        open: function () {
            $("#input-script-name").val($(this).dialog("option", "name"));
        },
        buttons: dialogButtons
    });
}
