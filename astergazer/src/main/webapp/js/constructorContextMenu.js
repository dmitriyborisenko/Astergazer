function initContextMenu() {
    $.contextMenu({
        selector: ".div-canvas-subwrapper, .block",
        build: function ($trigger, e) {
            var menuItems = {};
            posX = e.offsetX / window.canvasScaleFactor;
            posY = e.offsetY / window.canvasScaleFactor;
            if ($trigger.hasClass("block")) {
                block = $trigger[0];
                deselectBlocks();
                changeCurrentBlock(block);
                if (block.isLocked) {
                    return false;
                }
                if (!block.isCaseBlock) {
                    menuItems["copy"] = {name: contextMenuCopyText};
                }
                if (block.type == "Switch") {
                    menuItems["addCase"] = {name: contextMenuAddCaseText};
                }
                if (block.type == "VoiceMenu") {
                    menuItems["addDigitCase"] = {name: contextMenuAddCaseText};
                }
                menuItems["delete"] = {name: contextMenuDeleteText};
            }
            else {
                if (window.bufferedBlock == null) {
                    return false;
                }
                menuItems["paste"] = {name: contextMenuPasteText};
            }
            return {
                callback: function (key, options) {
                    block = options.$trigger[0];
                    if (key == "copy") {
                        window.bufferedBlock = block;
                    }
                    if (key == "delete") {
                        deleteBlock(block);
                    }
                    if (key == "paste") {
                        cloneBlock(window.bufferedBlock, posX, posY);
                    }
                    if (key == "addCase") {
                        addCase(block);
                    }
                    if (key == "addDigitCase") {
                        addDigitCase(block);
                    }
                },
                items: menuItems
            };
        }
    });
}

