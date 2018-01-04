var initStamp;
var currentStamp;
var inputFastAgiHost;

function saveAll() {
    $.ajax({
        type: "GET",
        url: restControllerUrl + "/getstamp",
        async: true,
        cache: false,
        success: function (data) {
            /** @namespace data.modificationStamp **/
            currentStamp = data.modificationStamp;
            if (currentStamp !== initStamp) {
                showConfirmation(concurrentModificationConfirmText, function () {
                    postData();
                });
            } else postData();
        },
        error: function (data) {
            showErrorMessage(data.responseText);
        }
    });
}

function getAll() {
    $.ajax({
        type: "GET",
        url: restControllerUrl + "/getall",
        async: true,
        cache: false,
        success: function (data) {
            initStamp = getParameterValue(data.parameters, "MODIFICATION_STAMP");
            currentStamp = initStamp;
            fillValues(data.parameters);
        },
        error: function (data) {
            showErrorMessage(data.responseText);
        }
    });
}

function postData() {
    $.ajax({
        type: "POST",
        url: restControllerUrl + "/saveall",
        contentType: "application/json",
        async: true,
        cache: false,
        data: collectParametersData(),
        success: function () {
           showInformationMessage(successText);
        },
        error: function (data) {
            showErrorMessage(data.responseText);
        }
    });
    initStamp = currentStamp;
}

function getParameterValue(parameters, name) {
    var result = {};
    $.each(parameters, function (index, parameter) {
        if (parameter.name === name) {
            result = parameter.value;
        }
    });
    return result;
}

function fillValues(parameters) {
    inputFastAgiHost.val(getParameterValue(parameters, "FASTAGI_HOST"));
}

function collectParametersData() {
    var parameters = [];
    var parameter = {};
    parameter.name = "FASTAGI_HOST";
    parameter.value = inputFastAgiHost.val();
    parameters.push(parameter);
    parameter = {};
    parameter.name = "MODIFICATION_STAMP";
    currentStamp = Date.now() + "/" + Math.random().toString(36).substring(2, 15) +
        Math.random().toString(36).substring(2, 15);
    parameter.value = currentStamp;
    parameters.push(parameter);
    return JSON.stringify(parameters);
}

$(document).ready(function () {
    inputFastAgiHost = $("#input-fastagihost");
    var spinner = new Spinner();
    $(document).ajaxStart(function () {
        spinner.spin($("body")[0]);
    });
    $(document).ajaxStop(function () {
        spinner.stop();
    });
    getAll();
});

