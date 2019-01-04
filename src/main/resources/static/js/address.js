function getAddress(forAddress, item) {
    var token = $.cookie('token');
    if (token != null) {
        $.get({
            headers: {
                "token": token
            },
            dataType: "json",
            url: "/user/addresses",
            contentType: "application/json; charset=utf-8",
            success: function (result) {
                if (result.message === "success"){
                    addressIdArray = [];
                    if (!forAddress){
                        var i = 0;
                        var address = item[0];
                        address.innerHTML = '';
                        $.each(result.contents, function () {
                            addressIdArray.push($(this)[0].address_id);
                            var option=document.createElement("option");
                            option.value = i++;
                            option.innerHTML = $(this)[0].line1 + ', ' + $(this)[0].city + ', ' + $(this)[0].province;
                            address.appendChild(option);
                        });
                        return;
                    }
                    $.each(result.contents, function () {
                        addressIdArray.push($(this)[0].address_id);
                    });
                    $('#addressTable').bootstrapTable({
                        data: result.contents,
                        showToggle: true,
                        silent: true,
                        showPaginationSwitch: true,
                        columns: [
                            {
                                field: "province",
                                title: "Province",
                                align: "center",
                                valign: "middle"
                            },
                            {
                                field: "city",
                                title: "City",
                                align: "center",
                                valign: "middle"
                            },
                            {
                                field: "line1",
                                title: "Line1",
                                align: "center",
                                valign: "middle"
                            },
                            {
                                title: "Action",
                                align: "center",
                                valign: "middle",
                                formatter: addressAction,
                                edit: false
                            }
                        ]
                    })
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

function addressAction(value, row, index) {
    var result = "";
    result += "<a href='javascript:;' class='btn btn-xs blue' title='edit' id='edit'><span id='state_edit' class='glyphicon glyphicon-pencil'></span></a>";
    result += "<a href='javascript:;' class='btn btn-xs red' title='remove' id='remove'><span id='state_remove' class='glyphicon glyphicon-trash'></span></a>";
    return result;
}

function addAddress(province, city, line1) {
    var token = $.cookie('token');
    var postdata = {
        "province": province,
        "city": city,
        "line1": line1
    };
    if (token != null) {
        $.post({
            headers: {
                "token": token
            },
            data: JSON.stringify(postdata),
            dataType: "json",
            url: "/address",
            contentType: "application/json; charset=utf-8",
            success: function (result) {
                if (result.message !== "success"){
                    alert(result.message);
                }
                else {
                    $("#addressTable").bootstrapTable('destroy');
                    getAddress(true);
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

function removeAddress(address_id){
    var token = $.cookie('token');
    if (token != null) {
        $.ajax({
            type: "delete",
            data: {"address_id": address_id},
            headers: {
                "token": token
            },
            url: "/address",
            success: function (result) {
                if (result.message !== "success"){
                    alert(result.message);
                }
                else {
                    $("#addressTable").bootstrapTable('destroy');
                    getAddress(true);
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

function updateAddress(address_id, province, city, line1){
    var token = $.cookie('token');
    var putdata = {
        "address_id": address_id,
        "province": province,
        "city": city,
        "line1": line1
    };
    if (token != null) {
        $.ajax({
            type: "put",
            data: JSON.stringify(putdata),
            headers: {
                "token": token
            },
            dataType: "json",
            url: "/address",
            contentType: "application/json; charset=utf-8",
            success: function (result) {
                if (result.message !== "success"){
                    alert(result.message);
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