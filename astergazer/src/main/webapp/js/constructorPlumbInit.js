var jsPlumbInstance;

function plumbInitSingleBlock(block) {
    jsPlumbInstance.getContainer().appendChild(block);
    jsPlumbInstance.draggable(block);
    jsPlumbInstance.makeSource(block, {
        filter: ".ep",
        anchor: defaultAnchor,
        connectorStyle: defaultPaintStyle,
        connectionType:"basic",
        extract:{
            "action":"the-action"
        },
        maxConnections: 1
    });
    jsPlumbInstance.makeTarget(block, {
        dropOptions: {
            hoverClass: "dragHover",
            tolerance: "touch",
            activeClass: "dragActive"
        },
        anchor: defaultAnchor,
        allowLoopback: false
    });
}


function initJsPlumb() {
    jsPlumbInstance = jsPlumb.getInstance(
       { DragOptions: {
            stop: function() {
                jsPlumbInstance.repaintEverything();
            }
        },
        Endpoint: ["Dot", {radius: 2}],
        Connector:"Straight",
        HoverPaintStyle: {strokeStyle: "#ff0000", lineWidth: 2 },
        ConnectionOverlays: [
            [ "Arrow", {
                location: 1,
                id: "arrow",
                length: 10,
        width: 10,
                foldback: 0.5
            } ]
        ],
        Container: "canvas"
    });
    jsPlumbInstance.bind("click", function (connection) {
        if (!connection.isLocked) {
            jsPlumbInstance.detach(connection);
        }
    });
    jsPlumbInstance.bind("connection", function(currentConnection) {
        // add the caseBlock to the VoiceMenu digits array
        if (currentConnection.source.type === "VoiceMenu" && currentConnection.target.type === "EqualCase") {
            currentConnection.source.digitCases.push(currentConnection.target.caption);    
        }
        // check for cross-connection
        $.each(jsPlumbInstance.getConnections(), function (index, connection) {
            if (connection.sourceId === currentConnection.targetId && connection.targetId === currentConnection.sourceId) {
                jsPlumbInstance.detach(currentConnection);
                showWarningMessage(crossConnectionWarningText);
            }
        });
     });
    jsPlumbInstance.bind("connectionDetached", function(currentConnection) {
        // removing case from VoiceMenu block 
        if (currentConnection.source.type === "VoiceMenu" && currentConnection.target.type === "EqualCase") {
            currentConnection.source.digitCases.splice(currentConnection.source.digitCases.indexOf(currentConnection.target.caption), 1);
        }
    });
}
