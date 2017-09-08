$(document).ready(function() {
    var spinner = new Spinner();
    $(document).ajaxStart(function(){
        spinner.spin($("body")[0]);
    });
    $(document).ajaxStop(function(){ 
        spinner.stop();
    });
    $(".ui-button").button();
    
    $("#div-script-tree").jstree({
        "plugins" : ["types"],
        "core" : {
            "multiple": false,
            "themes" : { "stripes" : true },
            "dblclick_toggle" : false,
            "data" : {
                "cache" : false,
                "url" : treeControllerUrl + "/getscripts",
                "data" : function (node) {
                    return { "id" : node.id };
                }
            }  
        },
        "types" : {
            "#" : {"max_children" : 1, "max_depth" : 1, "valid_children" : ["root"]},
            "script" : {"icon" : imageUrl + "/script.svg",  "valid_children" : []},
        },
    }).on('select_node.jstree', function (e, data) {
        $("#button-edit-script").button("option", "disabled", false);
        $("#button-clone-script").button("option", "disabled", false);
        $("#button-construct-script").button("option", "disabled", false);
        $("#button-delete-script").button("option", "disabled", false);
    }).on('deselect_all.jstree', function (e, data) {
        $("#button-edit-script").button("option", "disabled", true);
        $("#button-clone-script").button("option", "disabled", true);
        $("#button-construct-script").button("option", "disabled", true);
        $("#button-delete-script").button("option", "disabled", true);
    }).jstree("deselect_all").delegate("a","dblclick", function() {
        editCurrentScript();
    });;
    
    $("#div-dialplan-tree").jstree({
        "plugins" : ["types", "grid"],
        "grid" : {
            "columns": [
                      {width: 320, header: nameColumnCaptionText, headerClass: "jstreegrid-column-header", columnClass: "jstreegrid-striped"},
                      {width: 300, header: scriptColumnCaptionText, value: "scriptName", headerClass: "jstreegrid-column-header", columnClass : "jstreegrid-striped"}
            ],
        },
        "core" : {
            "multiple": false,
            "themes" : { "stripes" : true },
            "dblclick_toggle" : false,
            "data" : {
                "cache" : false,
                "url" : treeControllerUrl + "/getcontexts",
                "data" : function (node) {
                    return { "id" : node.id };
                }
            }  
        },
        "types" : {
            "#" : {"max_children" : 1, "max_depth" : 1, "valid_children" : ["root"]},
            "context" : {"icon" : imageUrl + "/context.svg",    "valid_children" : ["extension"]},
            "extension" : {"icon" : imageUrl + "/extension.svg",    "valid_children" : ["script"]},
            "script" : {"icon" : imageUrl + "/script.svg",  "valid_children" : []}
        },
    }).on('select_node.jstree', function (e, data) {
        $("#button-add-exten").button("option", "disabled", false);
        $("#button-edit").button("option", "disabled", false);
        $("#button-delete").button("option", "disabled", false);
    }).on('deselect_all.jstree', function (e, data) {
        $("#button-add-exten").button("option", "disabled", true);
        $("#button-edit").button("option", "disabled", true);
        $("#button-delete").button("option", "disabled", true);
    }).jstree("deselect_all").delegate("a","dblclick", function(e) {
        editCurrentDialplanTreeNode();  
    });

    initScriptDialog();
    initContextDialog();
    initExtensionDialog();
    loadScriptsForDialogList();
    $("#select-script").combobox();
    
});