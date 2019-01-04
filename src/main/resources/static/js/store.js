function getStore() {
    var token = $.cookie('token');
    if (token != null) {
        $.get({
            headers: {
                "token": token
            },
            dataType: "json",
            url: "/user/stores",
            contentType: "application/json; charset=utf-8",
            success: function (result) {
                if (result.message !== "success"){
                    alert(result.message);
                }
                else {
                    storeIdArray = [];
                    $.each(result.contents, function () {
                        storeIdArray.push($(this)[0].store_id);
                    });
                    $('#storeTable').bootstrapTable({
                        data: result.contents,
                        columns: [
                            [
                                {
                                    field: "storename",
                                    title: "store name",
                                    align: "center",
                                    valign: "middle",
                                    rowspan: 2
                                },
                                {
                                    title: "address",
                                    align: "center",
                                    valign: "middle",
                                    colspan: 3,
                                    rowspan: 1
                                },
                                {
                                    field: "introduction",
                                    title: "introduction",
                                    align: "center",
                                    valign: "middle",
                                    rowspan: 2
                                },
                                {
                                    title: "Action",
                                    align: "center",
                                    valign: "middle",
                                    formatter: storeAction,
                                    rowspan: 2
                                }
                            ],
                            [
                                {
                                    field: "address.province",
                                    title: "province",
                                    align: "center",
                                    valign: "middle"
                                },
                                {
                                    field: "address.city",
                                    title: "city",
                                    align: "center",
                                    valign: "middle"
                                },
                                {
                                    field: "address.line1",
                                    title: "line1",
                                    align: "center",
                                    valign: "middle"
                                }
                            ]
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

function storeAction(value, row, index) {
    result = "<a href='javascript:;' title='manage' id='manage'>manage</a>";
    return result;
}

function applyForStore(storename, address_id, bank_card, introduction) {
    var token = $.cookie('token');
    var postdata = {
        "storename": storename,
        "address_id": address_id,
        "bank_card": bank_card,
        "introduction": introduction
    };
    if (token != null) {
        $.post({
            headers: {
                "token": token
            },
            dataType: "json",
            data: JSON.stringify(postdata),
            url: "/application/new",
            contentType: "application/json; charset=utf-8",
            success: function (result) {
                if (result.message !== "success"){
                    alert(result.message);
                }
                else {
                    alert("Your application has been submitted, please wait for confirm. Thank you!");
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