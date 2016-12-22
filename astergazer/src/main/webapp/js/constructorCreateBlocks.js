function createBlockComplex(blockClass, posX, posY) {
    switch(blockClass) {
    case "SwitchComplex":
    case "Switch":
        block = createSwitchBlockComplex(posX, posY);
        break;
    case "VoiceMenuComplex":
    case "VoiceMenu":
        block = createVoiceMenuBlockComplex(posX, posY);
        break;    
    case "GotoIfComplex":
    case "GotoIf":
        block = createGotoIfBlockComplex(posX, posY);
        break;
    case "ChecklistComplex":
    case "Checklist":
        block = createChecklistBlockComplex(posX, posY);
        break;
    case "GotoIfTimeComplex":
    case "GotoIfTime":
        block = createGotoIfTimeBlockComplex(posX, posY);
        break;
    default:
        alert("Unknown block class: " + blockClass);
        return null;
        break;
    }
    alignCaption(block);
    return block;
}

function createBlock(blockClass, localId, posX, posY) {
    posX = posX || getRandomPosition(MIN_RANDON_POSITION, MAX_RANDON_POSITION);
    posY = posY || getRandomPosition(MIN_RANDON_POSITION, MAX_RANDON_POSITION);
    if (blockClass.indexOf("Complex") != -1) {
        return createBlockComplex (blockClass, posX, posY);
    } else {
        return createSingleBlock(blockClass, localId, posX, posY);
    }
}

function createSingleBlock(blockClass, localId, posX, posY) {
    localId = localId || getNewId();
    var block = document.createElement("div");
    block.type = blockClass;
    block.digitCases = [];
    block.id = localId;
    initSingleBlock (block, posX, posY);
    plumbInitSingleBlock (block);
    $.each(blockParams[blockClass], function(index, value) {
        addBlockParam(block, index, value, "");
    });
    block.isLocked = ["Start", "TrueCase", "FalseCase"].indexOf(blockClass) != -1;
    block.isCaseBlock = ["TrueCase", "EqualCase"].indexOf(blockClass) != -1;
    block.isSwitcher = ["Checklist", "GotoIf", "GotoIfTime", "Switch", "VoiceMenu"].indexOf(blockClass) != -1;
    if (blockClass == "FalseCase") {
        block.caption = "False";
        setBlockHTML(block);
    }
    if (blockClass == "TrueCase") {
        block.caption = "True";
        setBlockHTML(block);
    }
    if (blockClass == "EqualCase") {
        block.caption = "";
        setBlockHTML(block);
    }
    if (blockClass == "Start") {
        block.caption = "Start";
        setBlockHTML(block);
    }
    alignCaption(block);
    return block;
}

function createGotoIfBlockComplex(posX, posY) {
    var block = createBlock("GotoIf", null, posX, posY);
    childPosX = posX + 40;
    trueCaseBlock = createBlock("TrueCase", null, childPosX, posY + 85);
    childPosX = posX - 40;
    if (childPosX < 0) {
        childPosX = 0;
    }
    falseCaseBlock = createBlock("FalseCase", null, childPosX, posY + 85);
    jsPlumbInstance.connect({
        source: block.id,
        target: trueCaseBlock.id,
        anchor: defaultAnchor,
        paintStyle: defaultPaintStyle
    }).isLocked = true;
    jsPlumbInstance.connect({
        source: block.id,
        target: falseCaseBlock.id,
        anchor: defaultAnchor,
        paintStyle: defaultPaintStyle
    }).isLocked = true;
    changeCurrentBlock(block);
    return block;
}

function createSwitchBlockComplex(posX, posY) {
    var block = createBlock("Switch", null, posX, posY);
    falseCaseBlock = createBlock("FalseCase", null, posX + 40, posY + 85);
    jsPlumbInstance.connect({
        source: block.id,
        target: falseCaseBlock.id,
        anchor: defaultAnchor,
        paintStyle: defaultPaintStyle
    }).isLocked = true;
    changeCurrentBlock(block);
    return block;
}

function createVoiceMenuBlockComplex(posX, posY) {
    var block = createBlock("VoiceMenu", null, posX, posY);
    falseCaseBlock = createBlock("FalseCase", null, posX + 40, posY + 85);
    jsPlumbInstance.connect({
        source: block.id,
        target: falseCaseBlock.id,
        anchor: defaultAnchor,
        paintStyle: defaultPaintStyle
    }).isLocked = true;
    changeCurrentBlock(block);
    return block;
}

function createChecklistBlockComplex(posX, posY) {
    var block = createBlock("Checklist", null, posX, posY);
    childPosX = posX + 40;
    trueCaseBlock = createBlock("TrueCase", null, childPosX, posY + 85);
    childPosX = posX - 40;
    if (childPosX < 0) {
        childPosX = 0;
    }
    falseCaseBlock = createBlock("FalseCase", null, childPosX, posY + 85);
    jsPlumbInstance.connect({
        source: block.id,
        target: trueCaseBlock.id,
        anchor: defaultAnchor,
        paintStyle: defaultPaintStyle
    }).isLocked = true;
    jsPlumbInstance.connect({
        source: block.id,
        target: falseCaseBlock.id,
        anchor: defaultAnchor,
        paintStyle: defaultPaintStyle
    }).isLocked = true;
    changeCurrentBlock(block);
    return block;
}

function createGotoIfTimeBlockComplex(posX, posY) {
    var block = createBlock("GotoIfTime", null, posX, posY);
    childPosX = posX + 40;
    trueCaseBlock = createBlock("TrueCase", null, childPosX, posY + 85);
    childPosX = posX - 40;
    if (childPosX < 0) {
        childPosX = 0;
    }
    falseCaseBlock = createBlock("FalseCase", null, childPosX, posY + 85);
    jsPlumbInstance.connect({
        source: block.id,
        target: trueCaseBlock.id,
        anchor: defaultAnchor,
        paintStyle: defaultPaintStyle
    }).isLocked = true;
    jsPlumbInstance.connect({
        source: block.id,
        target: falseCaseBlock.id,
        anchor: defaultAnchor,
        paintStyle: defaultPaintStyle
    }).isLocked = true;
    changeCurrentBlock(block);
    return block;
}
