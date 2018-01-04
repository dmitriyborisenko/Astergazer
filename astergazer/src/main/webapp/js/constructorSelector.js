var selection;
var selectedBlocks;
var initialWidth;
var initialHeight;

function deselectBlocks() {
    jsPlumbInstance.clearDragSelection();
    $(".block").each(function () {
        $(this).removeClass("selected-block");
    });
}

function isObjectsCollide(a, b) { 
    var aTop = a.offset().top;
    var aLeft = a.offset().left;
    var bTop = b.offset().top;
    var bLeft = b.offset().left;
    return !(
        ((aTop + a.height()) < (bTop)) ||
        (aTop > (bTop + b.height())) ||
        ((aLeft + a.width()) < bLeft) ||
        (aLeft > (bLeft + b.width()))
    );
}

function resizeSelector(event) {
    var canvas = canvasSubWrapper;
    var posX = event.pageX;
    var posY = event.pageY;
    var width = Math.abs(initialWidth - posX);
    var height = Math.abs(initialHeight - posY);
    var maxWidth;
    if (posX >= initialWidth) {
        maxWidth = canvas[0].clientWidth + canvas.offset().left - initialWidth;
    } else { // selection is inverted at X axis
        maxWidth = -1*(canvas.offset().left - initialWidth);
        var minX = canvas.offset().left;
        if (posX < minX) {
            posX = minX;
        }
        $("#div-selection").css({
            "left": posX
        });
    }
    var maxHeight;
    if (posY >= initialHeight) {
        maxHeight = canvas[0].clientHeight + canvas.offset().top - initialHeight;
    } else { // selection is inverted at Y axis
        maxHeight = -1*(canvas.offset().top - initialHeight);
        var minY = canvas.offset().top;
        if (posY < minY) {
            posY = minY;
        }
        selection.css({
            "top": posY
        });
    }
    if (width > maxWidth) {
        width = maxWidth;
    }  
    if (height > maxHeight) {
        height = maxHeight;
    }  
    selection.css({
        "width": width,
        "height": height
    });
}

function selectElements(event) {
    event.stopImmediatePropagation();
    event.preventDefault();
    $(document).unbind("mousemove", resizeSelector);
    $(document).unbind("mouseup", selectElements);
    selectedBlocks = [];
    var selection = $("#div-selection");
    $(".block").each(function () {
        var block = $(this);
        if (isObjectsCollide(selection, block)) {
            window.selectedBlocks.push(block);
        }
    });
    if (selectedBlocks.length >= 1) {
        $.each(window.selectedBlocks, function(index, block) {
            block.addClass("selected-block");
            jsPlumbInstance.addToDragSelection(block);
        });
    }
    if (selectedBlocks.length === 1) {
        selectedBlocks[0].click()
    }
    selection.removeClass("selection-active");
    selection.width(0).height(0);
}

function startSelection(event) {
    selection = $("#div-selection");
    deselectBlocks();
    selection.addClass("selection-active");
    selection.css({
        "left": event.pageX,
        "top": event.pageY
    });
    initialWidth = event.pageX;
    initialHeight = event.pageY;
    $(document).bind("mouseup", selectElements);
    $(document).bind("mousemove", resizeSelector);
}
