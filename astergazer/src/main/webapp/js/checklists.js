function addChecklist() {
  $("#dialog-checklist")
    .dialog("option", "restUrl", restControllerUrl + "/addchecklist")
    .dialog("option", "name", "")
    .dialog("open");
}

function addEntryToCurrentChecklist() {
  var currentNode = $("#div-checklists-tree").jstree("get_selected", true)[0];
  if (typeof currentNode != "undefined") {
    if (currentNode.type == "checklist") {
      var checklistId = currentNode.data.id;
    } else if (currentNode.type == "entry") {
      var checklistId = currentNode.data.checklistId;
    } else {
      showErrorMessage(unknownNodeTypeErrorText);
      return;
    }
    addEntry(checklistId);
  }
}

function addEntry(checklistId) {
  $("#dialog-entry")
    .dialog("option", "restUrl", restControllerUrl + "/addentry")
    .dialog("option", "controlValue", "")
    .dialog("option", "returnValue", "")
    .dialog("option", "checklistId", checklistId)
    .dialog("open");
}

function editCurrentTreeNode() {
  var currentNode = $("#div-checklists-tree").jstree("get_selected", true)[0];
  if (typeof currentNode != "undefined") {
    if (currentNode.type == "checklist") {
      var entityId = currentNode.data.id;
      editChecklist(entityId, currentNode.text);
    } else if (currentNode.type == "entry") {
      var entityId = currentNode.data.id;
      editEntry(entityId, currentNode.text, currentNode.data.returnValue);
    } else {
      showErrorMessage(unknownNodeTypeErrorText);

    }
  }
}

function editChecklist(id, name) {
  if (typeof id != "undefined") {
    $("#dialog-checklist")
      .dialog("option", "restUrl", restControllerUrl + "/updatechecklist/" + id)
      .dialog("option", "name", name)
      .dialog("open");
  }
}

function editEntry(id, controlValue, returnValue) {
  if (typeof id != "undefined") {
    $("#dialog-entry").dialog("option", "restUrl", restControllerUrl + "/updateentry/" + id)
      .dialog("option", "controlValue", controlValue)
      .dialog("option", "returnValue", returnValue)
      .dialog("open");
  }
}

function deleteCurrentTreeNode() {
  var currentNode = $("#div-checklists-tree").jstree("get_selected", true)[0];
  if (typeof currentNode != "undefined") {
    if (currentNode.type == "checklist") {
      var entityId = currentNode.data.id;
      deleteChecklist(entityId, currentNode.text);
    } else if (currentNode.type == "entry") {
      var entityId = currentNode.data.id;
      deleteEntry(entityId, currentNode.text);
    } else {
      showErrorMessage(unknownNodeTypeErrorText);

    }
  }
}

function deleteChecklist(id, name) {
  if (typeof id != "undefined") {
    showConfirmation(deleteChecklistConfirmText + " " + name + "?", function () {
      $.ajax({
        type: "POST",
        url: restControllerUrl + "/deletechecklist/" + id,
        dataType: "json",
        async: false,
        cache: false,
        success: function (data) {
          if (data.status == "OK") {
            $("#div-checklists-tree").jstree("refresh");
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

function deleteEntry(id, controlValue) {
  if (typeof id != "undefined") {
    showConfirmation(deleteEntryConfirmText + " " + controlValue + "?", function () {
      $.ajax({
        type: "POST",
        url: restControllerUrl + "/deleteentry/" + id,
        dataType: "json",
        async: false,
        cache: false,
        success: function (data) {
          if (data.status == "OK") {
            $("#div-checklists-tree").jstree("refresh");
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

function initCheckListDialog() {
  var dialogButtons = {};
  dialogButtons["OK"] = function () {
    var name = $("#input-checklist-name").val();
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
          $("#div-checklists-tree").jstree("refresh");
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
  $("#dialog-checklist").dialog({
    autoOpen: false,
    modal: true,
    title: checklistNameText,
    open: function (event, ui) {
      $("#input-checklist-name").val($(this).dialog("option", "name"));
    },
    buttons: dialogButtons
  });
}

function initEntryDialog() {
  var dialogButtons = {};
  dialogButtons["OK"] = function () {
    var controlValue = $("#input-entry-controlvalue").val();
    var returnValue = $("#input-entry-returnvalue").val();
    var checklistId = $(this).dialog("option", "checklistId");
    var restUrl = $(this).dialog("option", "restUrl");
    $.ajax({
      type: "POST",
      url: restUrl,
      data: {
        controlValue: controlValue,
        returnValue: returnValue,
        checklistId: checklistId
      },
      dataType: "json",
      async: false,
      cache: false,
      success: function (data) {
        if (data.status == "OK") {
          $("#div-checklists-tree").jstree("refresh");
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
  $("#dialog-entry").dialog({
    autoOpen: false,
    modal: true,
    title: entryText,
    open: function (event, ui) {
      $("#input-entry-controlvalue").val($(this).dialog("option", "controlValue"));
      $("#input-entry-returnvalue").val($(this).dialog("option", "returnValue"));
    },
    buttons: dialogButtons
  });
}

$(document).ready(function () {
  $(document).ajaxStart(function () {
    spinner.spin($("body")[0]);
  });
  $(document).ajaxStop(function () {
    spinner.stop();
  });
  spinner = new Spinner();
  $(".ui-button").button();

  $("#div-checklists-tree").jstree({
    "plugins": ["types", "grid"],
    "grid": {
      "columns": [
        {width: 350, header: entryControlValueText, headerClass: "jstreegrid-column-header", columnClass: "jstreegrid-striped"},
        {width: 450, header: entryReturnValueText, value: "returnValue", headerClass: "jstreegrid-column-header", columnClass: "jstreegrid-striped"}
      ],
    },
    "core": {
      "multiple": false,
      "themes": {"stripes": true},
      "dblclick_toggle": false,
      "data": {
        "cache": false,
        "url": function (node) {
          if (node.id == "#") {
            return treeControllerUrl + "/getchecklists"
          } else {
            return treeControllerUrl + "/getentries/" + node.data.id;
          }
        },
        "data": function (node) {
          return {"id": node.id};
        }
      }
    },
    "types": {
      "#": {"max_children": 1, "max_depth": 1, "valid_children": ["root"]},
      "checklist": {"icon": imageUrl + "/checklist.svg", "valid_children": ["entry"]},
      "entry": {"icon": imageUrl + "/checklistEntry.svg", "valid_children": []},
    }
  }).on('select_node.jstree', function (e, data) {
    $("#button-add-entry").button("option", "disabled", false);
    $("#button-edit").button("option", "disabled", false);
    $("#button-delete").button("option", "disabled", false);
  }).on('deselect_all.jstree', function (e, data) {
    $("#button-add-entry").button("option", "disabled", true);
    $("#button-edit").button("option", "disabled", true);
    $("#button-delete").button("option", "disabled", true);
  }).jstree("deselect_all");

  $("#div-checklists-tree").delegate("a", "dblclick", function (e) {
    editCurrentTreeNode();
  });
  initCheckListDialog();
  initEntryDialog();
});