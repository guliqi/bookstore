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