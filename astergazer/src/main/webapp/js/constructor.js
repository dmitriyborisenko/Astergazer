var MAX_PARAMS_PER_BLOCK = 15;
var MIN_RANDOM_POSITION = 100;
var MAX_RANDOM_POSITION = 200;
var defaultAnchor = ["Perimeter", {shape: "Square"}];
var defaultPaintStyle = {strokeStyle: "#555555", lineWidth: 1};

var canvasScaleFactor = 1;
var canvasClickFlag = false;
var initStamp;
var currentStamp;
var divScrollStartX;
var divScrollStartY;
var dragScrollStartX;
var dragScrollStartY;
var canvas;
var canvasSubWrapper;

function getModificationStamp() {
    $.ajax({
        type: "GET",
        url: restControllerUrl + "/getstamp/" + scriptId,
        async: true,
        cache: false,
        success: function (data) {
            currentStamp = data.modificationStamp;
        },
        error : function (data) {
            showErrorMessage(data.responseText);
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
    canvas.find(".block").each(function (index, element) {
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
        async: true,
        cache: false,
        success: function (data) {
            /** @namespace data.dto.modificationStamp **/
            currentStamp = data.dto.modificationStamp;
            initStamp = currentStamp;
            data.dto.blocks.forEach(function (item, i) {
                var block = createBlock(item.type, item.localId, item.posX, item.posY);
                updateBlockName(block, item.caption);
                for (i in item.parameters) {
                    block.commandParams[i].value = item.parameters[i].value;
                }
            });
            data.dto.connections.forEach(function (item) {
                jsPlumbInstance.connect({
                    source: String(item.sourceBlockLocalId),
                    target: String(item.targetBlockLocalId),
                    anchor: defaultAnchor,
                    paintStyle: defaultPaintStyle
                }).isLocked = item.isLocked;
            });
        },
        error : function(data) {
            showErrorMessage(data.responseText);
        }
    });
}

function saveScriptData() {
    getModificationStamp();
    if (currentStamp !== initStamp) {
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
        async: true,
        cache: false,
        data: serializeScriptData(),
        success: function () {
            showInformationMessage(successText);
        },
        error : function(data) {
            showErrorMessage(data.responseText);
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
        var equalCaseBlock = createBlock("EqualCase", null, posX, posY);
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
            var input = $(element);
            var digit = input.attr("digit");
            // creating missing case blocks
            if (block.digitCases.indexOf(digit) === -1 && input.prop("checked")) {
                addDigitCaseBlock(block, digit);
            }
            // removing redundant case blocks
            if (block.digitCases.indexOf(digit) !== -1 && !input.prop("checked")) {
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
        open: function () {
            $.each($(".input-digit"), function (index, value) {
                value.checked = false;
            });
            block.digitCases.forEach(function (digit) {
                if (digit === "*") {
                    $("#input-digit-star").prop("checked", "true");
                } else if (digit === "#") {
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
    canvas.css({
        "-webkit-transform": "scale(" + scaleFactor + ")",
        "-moz-transform": "scale(" + scaleFactor + ")",
        "-ms-transform": "scale(" + scaleFactor + ")",
        "-o-transform": "scale(" + scaleFactor + ")",
        "transform": "scale(" + scaleFactor + ")"
    });
    jsPlumbInstance.setZoom(scaleFactor);
}

function startDragScroll(event) {
    canvasSubWrapper.css("cursor", "move");
    var div = canvasSubWrapper[0];
    divScrollStartX = div.scrollLeft;
    divScrollStartY = div.scrollTop;
    dragScrollStartX = event.pageX;
    dragScrollStartY = event.pageY;
    $(document).bind("mousemove", dragScroll);
    $(document).bind("mouseup", stopDragScroll);
}

function dragScroll(event) {
    canvasSubWrapper.scrollLeft(divScrollStartX - event.pageX + dragScrollStartX);
    canvasSubWrapper.scrollTop(divScrollStartY - event.pageY + dragScrollStartY);
}

function stopDragScroll() {
    canvasSubWrapper.css("cursor", "default");
    $(document).unbind("mousemove", dragScroll);
    $(document).unbind("mouseup", stopDragScroll);
}

$(document).ready(function () {
    canvas = $("#canvas");
    canvasSubWrapper = $(".div-canvas-subwrapper");
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
            var block = createBlock(draggable.attr("type"), null, posX, posY);
            changeCurrentBlock(block);
        }
    });
    canvasSubWrapper.on("mousedown", function (event) {
        canvasClickFlag = true;
        if (event.shiftKey) {
            startSelection(event);
        }
        else {
            startDragScroll(event);
        }
    });
    canvasSubWrapper.on("mousemove", function () {
        canvasClickFlag = false;
    });
    canvasSubWrapper.on("mouseup", function (event) {
        if (!event.ctrlKey && canvasClickFlag) {
            deselectBlocks();
        }
    });
    canvasSubWrapper.on("wheel", function (event) {
        if (event.shiftKey) {
            event = event.originalEvent || event || window.event;
            var delta = event.deltaY || event.detail || event.wheelDelta;
            var newScaleFactor;
            if (delta > 0) {
                newScaleFactor = canvasScaleFactor - 0.05;
            } else {
                newScaleFactor = canvasScaleFactor + 0.05;
            }
            rescaleCanvas(newScaleFactor);
            return false;
        }
    });
    var spinner = new Spinner();
    $(document).ajaxStart(function () {
        spinner.spin($("body")[0]);
    });
    $(document).ajaxStop(function () {
        spinner.stop();
    });
    initJsPlumb();
    initContextMenu();
    $(".ui-button").button();
    loadScriptData();
    changeCurrentBlock();
    window.onbeforeunload = function () {
        return "";
    }
});