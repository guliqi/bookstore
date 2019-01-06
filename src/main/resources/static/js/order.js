function createOrder(address_id, amount, comments, tx_id, callback){
    var token = $.cookie('token');
    var postdata = {
        "book": {
            "book_id": $.getUrlParam('book_id')
        },
        "address": {
            "address_id": address_id
        },
        "amount": amount,
        "comments": comments,
        "tx_id": tx_id
    };

    if (token != null) {
        $.post({
            headers: {
                "token": token
            },
            data: JSON.stringify(postdata),
            dataType: "json",
            url: "/request/order",
            contentType: "application/json; charset=utf-8",
            success: function (result) {
                if (result.message !== "success"){
                    alert(result.message);
                }
                else {
                    callback(result.order_id);
                }
            },
            error: function () {
                $.cookie('token', '', {expires: -1});
                window.location.href = "error";
            }
        });
    }
    else {
        window.location.href = "login";
    }
}

function payOrder(order_id, callback){
    var token = $.cookie('token');
    if (token != null) {
        $.ajax({
            type: "put",
            headers: {
                "token": token
            },
            data: {"order_id": order_id},
            dataType: "json",
            url: "/request/order/pay",
            success: function (result) {
                if (result.message !== "success"){
                    alert(result.message);
                }
                else {
                    callback();
                }
            },
            error: function () {
                $.cookie('token', '', {expires: -1});
                window.location.href = "error";
            }
        });
    }
    else {
        window.location.href = "login";
    }
}

function deliverOrder(order_id, callback){
    var token = $.cookie('token');
    if (token != null) {
        $.ajax({
            type: "put",
            headers: {
                "token": token
            },
            data: {"order_id": order_id},
            dataType: "json",
            url: "/request/order/deliver",
            success: function (result) {
                if (result.message !== "success"){
                    alert(result.message);
                }
                else {
                    callback();
                }
            },
            error: function () {
                $.cookie('token', '', {expires: -1});
                window.location.href = "error";
            }
        });
    }
    else {
        window.location.href = "login";
    }
}

function receiveOrder(order_id, callback){
    var token = $.cookie('token');
    if (token != null) {
        $.ajax({
            type: "put",
            headers: {
                "token": token
            },
            data: {"order_id": order_id},
            dataType: "json",
            url: "/request/order/receive",
            success: function (result) {
                if (result.message !== "success"){
                    alert(result.message);
                }
                else {
                    callback();
                }
            },
            error: function () {
                $.cookie('token', '', {expires: -1});
                window.location.href = "error";
            }
        });
    }
    else {
        window.location.href = "login";
    }
}