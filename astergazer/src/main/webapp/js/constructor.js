function defineGlobals() {
  MAX_PARAMS_PER_BLOCK = 15;
  MIN_RANDON_POSITION = 100;
  MAX_RANDON_POSITION = 200;
  CANVAS_TOP_OFFSET = 50;
  CANVAS_LEFT_OFFSET = 250;
  canvasScaleFactor = 1;
  selectedBlocks = [];
  canvasClickFlag = false;
  defaultAnchor = ["Perimeter", {shape: "Square"}];
  defaultPaintStyle = {strokeStyle: "#555555", lineWidth: 1};
}

function getModificationStamp() {
  $.ajax({
    type: "GET",
    url: restControllerUrl + "/getstamp/" + scriptId,
    dataType: "json",
    async: false,
    cache: false,
    success: function (data) {
      if (data.status == "OK") {
        currentStamp = data.data.modificationStamp;
      } else {
        showErrorMessage(data.data.description);
      }
    },
    failure: function (errMsg) {
      showErrorMessage(errMsg);
    }
  });
}

function serializeScriptData() {
  var scriptDataDto = function () {
    this.blocks = [];
    this.connections = [];
  };
  var dto = new scriptDataDto();
  dto.blocks = serializeBlocks();
  dto.connections = serializeConnections();
  return JSON.stringify(dto);
}

function serializeBlocks() {
  var blocks = [];
  var block;
  $("#canvas .block").each(function (index, element) {
    block = $(element);
    blocks.push({
      localId: block.attr("id"),
      type: block.prop("type"),
      caption: block.prop("caption"),
      isLocked: block.prop("isLocked"),
      parameters: block.prop("commandParams"),
      posX: parseFloat(block.css("left")),
      posY: parseFloat(block.css("top"))
    });
  });
  return blocks;
}

function serializeConnections() {
  var connections = [];
  $.each(jsPlumbInstance.getConnections(), function (index, connection) {
    connections.push({
      isLocked: connection.isLocked,
      sourceBlockLocalId: connection.sourceId,
      targetBlockLocalId: connection.targetId
    });
  });
  return connections;
}

function loadScriptData() {
  $.ajax({
    type: "GET",
    url: restControllerUrl + "/getscriptdata/" + scriptId,
    dataType: "json",
    async: false,
    cache: false,
    success: function (data) {
      if (data.status == "OK") {
        currentStamp = data.data.dto.modificationStamp;
        data.data.dto.blocks.forEach(function (item, i, arr) {
          var block = createBlock(item.type, item.localId, item.posX, item.posY);
          updateBlockName(block, item.caption);
          for (i in item.parameters) {
            block.commandParams[i].value = item.parameters[i].value;
          }
        });
        data.data.dto.connections.forEach(function (item, i, arr) {
          jsPlumbInstance.connect({
            source: String(item.sourceBlockLocalId),
            target: String(item.targetBlockLocalId),
            anchor: defaultAnchor,
            paintStyle: defaultPaintStyle
          }).isLocked = item.isLocked;
        });
      } else {
        showErrorMessage(data.data.description);
      }
    },
    failure: function (errMsg) {
      showErrorMessage(errMsg);
    }
  });
}

function saveScriptData() {
  getModificationStamp();
  if (currentStamp != initStamp) {
    showConfirmation(concurrentModificationConfirmText, function () {
      postData();
    });
  } else postData();
}

function postData() {
  $.ajax({
    type: "POST",
    url: restControllerUrl + "/updatescriptdata/" + scriptId,
    contentType: "application/json",
    dataType: "json",
    async: false,
    cache: false,
    data: serializeScriptData(),
    success: function (data) {
      if (data.status == "OK") {
        showInformationMessage(successText);
      } else {
        showErrorMessage(data.data.description);
      }
    },
    failure: function (errMsg) {
      showErrorMessage(errMsg);
    }
  });
  getModificationStamp();
  initStamp = currentStamp;
}

function initAddCaseDialog(block) {
  var dialogButtons = {};
  dialogButtons["OK"] = function () {
    var posX = getRandomPosition(parseFloat(block.style.left) - 30, parseFloat(block.style.left) + 30);
    var posY = getRandomPosition(parseFloat(block.style.top) + 80, parseFloat(block.style.top) + 100);
    equalCaseBlock = createBlock("EqualCase", null, posX, posY);
    updateBlockName(equalCaseBlock, $("#input-expression-value").val());
    jsPlumbInstance.connect({
      source: block.id,
      target: equalCaseBlock.id,
      anchor: defaultAnchor,
      paintStyle: defaultPaintStyle
    }).isLocked = true;
    $(this).dialog("close");
  };
  dialogButtons[cancelText] = function () {
    $(this).dialog("close");
  };
  $("#dialog-addcase").dialog({
    autoOpen: false,
    modal: true,
    title: addCaseText,
    open: function () {
      $("#input-expression-value").val("");
    },
    buttons: dialogButtons
  })
}

function initAddDigitCaseDialog(block) {
  var dialogButtons = {};
  dialogButtons["OK"] = function () {
    $.each($(".input-digit"), function (index, element) {
      input = $(element);
      digit = input.attr("digit");
      // creating missing case blocks
      if (block.digitCases.indexOf(digit) == -1 && input.prop("checked")) {
        addDigitCaseBlock(block, digit);
      }
      // removing redundant case blocks
      if (block.digitCases.indexOf(digit) != -1 && !input.prop("checked")) {
        jsPlumbInstance.remove(findEqualCaseBlockId(block, digit));
      }
    });
    $(this).dialog("close");
  };
  dialogButtons[cancelText] = function () {
    $(this).dialog("close");
  };
  $("#dialog-adddigitcase").dialog({
    autoOpen: false,
    modal: true,
    title: addCaseText,
    open: function (event, ui) {
      $.each($(".input-digit"), function (index, value) {
        value.checked = false;
      });
      block.digitCases.forEach(function (digit, index, array) {
        if (digit == "*") {
          $("#input-digit-star").prop("checked", "true");
        } else if (digit == "#") {
          $("#input-digit-pound").prop("checked", "true");
        } else {
          $("#input-digit-" + digit).prop("checked", "true");
        }
      });
    },
    buttons: dialogButtons
  })
}

function rescaleCanvas(scaleFactor) {
  canvasScaleFactor = scaleFactor;
  var origWidth = parseInt($("#canvas").css("width"));
  $("#canvas").css({
    "-webkit-transform": "scale(" + scaleFactor + ")",
    "-moz-transform": "scale(" + scaleFactor + ")",
    "-ms-transform": "scale(" + scaleFactor + ")",
    "-o-transform": "scale(" + scaleFactor + ")",
    "transform": "scale(" + scaleFactor + ")"
  });
  jsPlumbInstance.setZoom(scaleFactor);
}

function startDragScroll(event) {
  $(".div-canvas-subwrapper").css("cursor", "move");
  var div = $(".div-canvas-subwrapper")[0];
  divScrollStartX = div.scrollLeft;
  divScrollStartY = div.scrollTop;
  dragScrollStartX = event.pageX;
  dragScrollStartY = event.pageY;
  $(document).bind("mousemove", dragScroll);
  $(document).bind("mouseup", stopDragScroll);
}

function dragScroll(event) {
  var div = $(".div-canvas-subwrapper");
  var currentScrollX = div.scrollLeft;
  var currentScrollY = div.scrollTop;
  div.scrollLeft(divScrollStartX - event.pageX + dragScrollStartX);
  div.scrollTop(divScrollStartY - event.pageY + dragScrollStartY);
}

function stopDragScroll(event) {
  $(".div-canvas-subwrapper").css("cursor", "default");
  $(document).unbind("mousemove", dragScroll);
  $(document).unbind("mouseup", stopDragScroll);
}

$(document).ready(function () {
  defineGlobals();
  $(".draggable").draggable({
    revert: "invalid",
    stack: ".draggable",
    helper: "clone"
  });
  $(".droppable").droppable({
    accept: ".draggable",
    drop: function (event, ui) {
      var droppable = $(this);
      var draggable = ui.draggable;
      var posX = (ui.offset.left - droppable.offset().left + droppable.scrollLeft()) / canvasScaleFactor;
      var posY = (ui.offset.top - droppable.offset().top + droppable.scrollTop()) / canvasScaleFactor;
      block = createBlock(draggable.attr("type"), null, posX, posY);
      changeCurrentBlock(block);
    }
  });
  $(".div-canvas-subwrapper").on("mousedown", function (event) {
    canvasClickFlag = true;
    if (event.shiftKey) {
      startSelection(event);
    }
    else {
      startDragScroll(event);
    }
  });
  $(".div-canvas-subwrapper").on("mousemove", function (event) {
    canvasClickFlag = false;
  });
  $(".div-canvas-subwrapper").on("mouseup", function (event) {
    if (!event.ctrlKey && canvasClickFlag) {
      deselectBlocks();
    }
  });
  $(".div-canvas-subwrapper").on("wheel", function (event) {
    if (event.shiftKey) {
      event = event.originalEvent || event || window.event;
      var delta = event.deltaY || event.detail || event.wheelDelta;
      if (delta > 0) {
        var newScaleFactor = canvasScaleFactor - 0.05;
      } else {
        var newScaleFactor = canvasScaleFactor + 0.05;
      }
      rescaleCanvas(newScaleFactor);
      return false;
    }
  });
  $(document).ajaxStart(function () {
    spinner.spin($("body")[0]);
  });
  $(document).ajaxStop(function () {
    spinner.stop();
  });
  spinner = new Spinner();
  initJsPlumb();
  initContextMenu();
  $(".ui-button").button();
  loadScriptData();
  initStamp = currentStamp;
  changeCurrentBlock();
  window.onbeforeunload = function (event) {
    return "";
  }
});