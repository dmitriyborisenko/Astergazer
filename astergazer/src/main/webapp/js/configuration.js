function saveAll() {
    $.ajax({
        type: "GET",
        url: restControllerUrl + "/getstamp",
        dataType: "json",
        async: true,
        cache: false,
        success: function (data) {
            if (data.status == "OK") {
                currentStamp = data.data.modificationStamp;
                if (currentStamp != initStamp) {
                    showConfirmation(concurrentModificationConfirmText, function () {
                        postData();
                    });
                } else postData();
            } else {
                showErrorMessage(data.data.description);
            }
        },
        failure: function (errMsg) {
            showErrorMessage(errMsg);
        }
    });
}

function getAll() {
    $.ajax({
        type: "GET",
        url: restControllerUrl + "/getall",
        dataType: "json",
        async: true,
        cache: false,
        success: function (data) {
            if (data.status == "OK") {
                currentParameters = data.data.parameters;
                initStamp = getParameterValue(currentParameters, "MODIFICATION_STAMP");
                currentStamp = initStamp;
                fillValues(currentParameters);
            } else {
                showErrorMessage(data.data.description);
            }
        },
        failure: function (errMsg) {
            showErrorMessage(errMsg);
        }
    });
}

function postData() {
    $.ajax({
        type: "POST",
        url: restControllerUrl + "/saveall",
        contentType: "application/json",
        dataType: "json",
        async: true,
        cache: false,
        data: collectParametersData(),
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
    initStamp = currentStamp;
}

function getParameterValue(parameters, name) {
    var result;
    $.each(parameters, function (index, parameter) {
        if (parameter.name == name) {
            result = parameter.value;
        }
    });
    return result;
}

function fillValues(parameters) {
    $("#input-fastagihost").val(getParameterValue(parameters, "FASTAGI_HOST"));
}

function collectParametersData() {
    var parameters = [];
    var parameter = new Object();
    parameter.name = "FASTAGI_HOST";
    parameter.value = $("#input-fastagihost").val();
    parameters.push(parameter);
    parameter = new Object();
    parameter.name = "MODIFICATION_STAMP";
    currentStamp = Date.now() + "/" + Math.random().toString(36).substring(2, 15) +
        Math.random().toString(36).substring(2, 15);
    parameter.value = currentStamp;
    parameters.push(parameter);
    return JSON.stringify(parameters);
}

$(document).ready(function () {
    $(document).ajaxStart(function () {
        spinner.spin($("body")[0]);
    });
    $(document).ajaxStop(function () {
        spinner.stop();
    });
    spinner = new Spinner();
    getAll();

});

