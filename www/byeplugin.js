var exec = require('cordova/exec');

exports.bye = function (message, timeout, color, successCallback, errorCallback) {
    exec(successCallback, errorCallback, "ByePlugin", "bye", [message, timeout, color]);
};
