var currentBlock;
var bufferedBlock;

function setBlockHTML(block) {
    block.innerHTML = "<div class=\"ep\"></div><div align=\"center\" class=\"caption\">" + block.caption + "</div>";
}

function initSingleBlock(block, posX, posY) {
    block.commandParams = [];
    block.isLocked = false;
    block.className = "block block-" + block.type;
    block.caption = block.type + "_" + block.id;
    block.style.left = posX + "px";
    block.style.top = posY + "px";
    block.onclick = function (event) {
        if (event.ctrlKey) {
            var domBlock = $("#" + block.id);
            domBlock.addClass("selected-block");
            jsPlumbInstance.addToDragSelection(domBlock);
        }
        changeCurrentBlock(block);
        event.stopPropagation();
    };
    setBlockHTML(block);
}

var blockParameter = function (orderIndex, name, value) {
    this.orderIndex = orderIndex;
    this.name = name;
    this.value = value;
};

function addBlockParam(block, orderIndex, name, value) {
    block.commandParams.push(new blockParameter(orderIndex, name, value));
}

function deleteBlock(block) {
    if (block !== null) {
        if (block.isSwitcher) {
            jsPlumbInstance.select({source: block}).each(function (element) {
                jsPlumbInstance.remove(element.target.id);
            });
            jsPlumbInstance.remove(block.id);
        } else {
            if (!block.isLocked) {
                jsPlumbInstance.remove(block.id);
            } else {
                showErrorMessage(cannotRemoveErrorText);
            }
        }
        if (block === currentBlock) {
            changeCurrentBlock();
        }
    }
}

function cloneBlock(block, newPosX, newPosY) {
    if (typeof block === "undefined") {
        showErrorMessage(noSelectedBlockErrorText);
        return null;
    }
    if (block.isLocked) {
        showErrorMessage(cannotCloneErrorText);
        return null;
    }
    if (newPosX === null) {
        newPosX = getRandomPosition(parseFloat(block.style.left) + 5, parseFloat(block.style.left) + 35);
    }
    if (newPosY === null) {
        newPosY = getRandomPosition(parseFloat(block.style.top) + 5, parseFloat(block.style.top) + 35);
    }
    var newBlock;
    if (block.isSwitcher) {
        newBlock = createBlockComplex(block.type, newPosX, newPosY);
    } else {
        newBlock = createBlock(block.type, null, newPosX, newPosY);
    }
    newBlock.commandParams = [];
    for (var i in block.commandParams) {
        newBlock.commandParams.push(new blockParameter(block.commandParams[i].orderIndex, block.commandParams[i].name, block.commandParams[i].value));
    }
    return newBlock;
}

function addCase(block) {
    if (block.type === "Switch") {
        addValueCase(block);
    } else if (block.type === "VoiceMenu") {
        addDigitCase(block);
    } else showErrorMessage("Wrong block type: " + block.type);
}

function addValueCase(block) {
    initAddCaseDialog(block);
    $("#dialog-addcase").dialog("open");
}

function addDigitCase(block) {
    initAddDigitCaseDialog(block);
    $("#dialog-adddigitcase").dialog("open");
}

function addDigitCaseBlock(mainBlock, digit) {
    var posX = getRandomPosition(parseFloat(mainBlock.style.left) - 50, parseFloat(mainBlock.style.left) + 60);
    var posY = getRandomPosition(parseFloat(mainBlock.style.top) + 80, parseFloat(mainBlock.style.top) + 120);
    var equalCaseBlock = createBlock("EqualCase", null, posX, posY);
    updateBlockName(equalCaseBlock, digit);
    jsPlumbInstance.connect({
        source: mainBlock.id,
        target: equalCaseBlock.id,
        anchor: defaultAnchor,
        paintStyle: defaultPaintStyle
    }).isLocked = true;
}


function applyBlockChanges(block) {
    updateBlockName(block, $("#current-block-name").val());
    for (var keyNum in block.commandParams) {
        block.commandParams[keyNum].value = $("#block-param-value-" + keyNum).val().trim();
    }
}

function getRandomPosition(minPos, maxPos) {
    return Math.floor(Math.random() * (maxPos - minPos + 1)) + minPos;
}

function getNewId() {
    var maxId = 1;
    canvas.find(".block").each(function () {
        maxId = Math.max(maxId, parseInt($(this).attr("id").replace(/[^\d]/g, "")) + 1);
    });
    return maxId;
}

function updateBlockName(block, newName) {
    var checkResult = true;
    canvas.find(".block").each(function (index, element) {
        if (block.type !== "FalseCase" && element.caption === newName && element.id !== block.id) {
            showWarningMessage(duplicateNameWarningText);
            checkResult = false;
            return false;
        }
    });
    if (checkResult) {
        block.caption = newName.trim();
        setBlockHTML(block);
        alignCaption(block);
    }
}

function changeCurrentBlock(block) {
    bufferedBlock = null;
    currentBlock = block;
    var keyNum = 0;
    var inputCurrentBlockName = $(".input-current-block-name");
    var blockParamKey;
    var blockParamValue;
    if (typeof block === "undefined") {
        inputCurrentBlockName.val("");
        inputCurrentBlockName.attr("disabled", true);
        $("#button-apply").button("option", "disabled", true);
        $("#button-clone").button("option", "disabled", true);
        $("#button-delete").button("option", "disabled", true);
        $("#button-addcase").button("option", "disabled", true);
    } else {
        inputCurrentBlockName.val(block.caption);
        inputCurrentBlockName.attr("disabled", block.isLocked);
        $("#button-apply").button("option", "disabled", block.isLocked);
        $("#button-clone").button("option", "disabled", block.isLocked || block.isCaseBlock);
        $("#button-delete").button("option", "disabled", block.isLocked);
        $("#button-addcase").button("option", "disabled", block.type !== "Switch" && block.type !== "VoiceMenu");
        while (keyNum < block.commandParams.length) {
            blockParamKey = $("#block-param-key-" + keyNum);
            blockParamValue = $("#block-param-value-" + keyNum);
            blockParamKey.text(block.commandParams[keyNum].name);
            blockParamValue.val(block.commandParams[keyNum].value);
            blockParamKey.attr("hidden", false);
            blockParamValue.attr("hidden", false);
            blockParamValue.attr("disabled", false);
            keyNum++;
        }
    }
    // Cleaning and hiding all unused fields
    for (keyNum; keyNum <= window.MAX_PARAMS_PER_BLOCK; keyNum++) {
        blockParamKey = $("#block-param-key-" + keyNum);
        blockParamValue = $("#block-param-value-" + keyNum);
        blockParamKey.text("");
        blockParamValue.val("");
        blockParamKey.attr("hidden", true);
        blockParamValue.attr("hidden", true);
        blockParamValue.attr("disabled", true);
    }
}

function alignCaption(block) {
    var divCaption = $("#" + block.id + " .caption");
    var divBlock = $("#" + block.id);
    divCaption.css("margin-left", Math.floor(divCaption[0].offsetWidth / 2 * (-1)) + Math.floor(divBlock[0].offsetWidth / 2));
}

function cleanAll() {
    showConfirmation(deleteAllConfirmText, function () {
        $("#canvas").find(".block").each(function (index, element) {
            var block = $(element);
            jsPlumbInstance.remove(block.attr("id"));
        });
        changeCurrentBlock();
        createBlock("Start", null, 20, 20);
    });
}

function findEqualCaseBlockId(parentBlock, caption) {
    var result = {};
    $.each(jsPlumbInstance.getConnections(), function (index, connection) {
        if (connection.source === parentBlock) {
            if (connection.target.caption === caption) {
                result = connection.targetId;
            }
        }
    });
    return result;
}
