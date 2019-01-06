function logout() {
    var token = $.cookie('token');
    $.ajax({
        type: "delete",
        headers: {
            "token": token
        },
        dataType: "json",
        contentType: "application/json; charset=utf-8",
        url: "/user/token",
        success: function (result) {
            if (result.message !== "success"){
                alert(result.message);
                return false;
            }
        }
    });
    $.cookie('token', '', {expires: -1});
}

(function ($) {
    $.getUrlParam = function (name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
        var r = window.location.search.substr(1).match(reg);
        if (r != null) return unescape(r[2]); return null;
    };

    $.setUrlParam = function (name, val) {
        var thisURL = document.location.href;
        if (thisURL.indexOf(name+'=') > 0) {
            var v = $.getUrlParam(name);
            if (v != null) {
                thisURL = thisURL.replace(name + '=' + v, name + '=' + val);
            }
            else {
                thisURL = thisURL.replace(name + '=', name + '=' + val);
            }
        }
        else {
            if (thisURL.indexOf("?") > 0) {
                thisURL = thisURL + "&" + name + "=" + val;
            }
            else {
                thisURL = thisURL + "?" + name + "=" + val;
            }
        }
        location.href = thisURL;
    }
})(jQuery);

function getEtherAddress(callback) {
    var token = $.cookie('token');
    if (token != null) {
        $.get({
            headers: {
                "token": token
            },
            dataType: "json",
            url: "/user/profile",
            contentType: "application/json; charset=utf-8",
            success: function (result) {
                if (result.message === "success"){
                    callback(result.contents.ether_address);
                }
            },
            error: function () {
                $.cookie('token', '', {expires: -1});
                window.location.href="error";
            }
        })
    }
    else {
        window.location.href="login";
    }
}

function changeCursor(item){
    item.style.cursor = "pointer";
}