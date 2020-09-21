function updateInfo() {
    var infoId = document.getElementById("infoId").value;
    var infoName = document.getElementById("infoName").value;
    var infoDescription = document.getElementById("infoDescription").value;
    var infoSource = document.getElementById("infoSource").value;
    var infoStatus = document.getElementById("infoStatus").value;
    swal("更新信息中，请稍后！", {buttons: false});
    $.ajax({
        type: "POST",
        url: "/info/feign/updateInfo",
        dataType: "json",
        contentType: "application/json",
        data: JSON.stringify({
            "infoId": infoId,
            "infoName": infoName,
            "infoDescription": infoDescription,
            "infoSource": infoSource,
            "infoStatus": infoStatus
        }),
        success: function (data) {
            swal.close();
            console.log("data is :" + data);
            swal("成功！", "修改成功", "success");
            $("#editModal").modal('hide');
            $('#messageTable').bootstrapTable('refresh');
        },
        error: function () {
            swal.close();
            swal("失败！", "修改失败", "error");
            return false;
        }
    });
}

function editInfo(infoId) {
    var data = JSON.stringify($('#messageTable').bootstrapTable('getRowByUniqueId', infoId));
    data = eval("(" + data + ")");
    $("#infoId").val(data.infoId);
    $("#infoName").val(data.infoName);
    $("#infoDescription").val(data.infoDescription);
    $("#infoSource").val(data.infoSource);
    $("#infoStatus").val(data.infoStatus);
    $("#editModal").modal('show');
}

function deleteLogicInfo(infoId) {
    swal({
        title: "确定是否下架该信息？",
        icon: 'warning',
        buttons: {
            cancel: {
                text: "取消",
                value: "",
                visible: true,
                closeModal: true,
            },
            confirm: {
                text: "确定",
                value: true,
                visible: true,
                closeModal: true
            }
        },
        confirmButtonColor: "#DD6B55",
    }).then(function (isConfirm) {
        if (isConfirm) {
            swal("下架信息中，请稍后！", {buttons: false});
            $.ajax({
                url: "/info/feign/deleteLogicInfoByInfoId/" + infoId,
                method: 'get',
                success: function () {
                    swal.close();
                    swal('成功', '下架信息成功!', 'success');
                    $('#messageTable').bootstrapTable('refresh');
                },
                error: function () {
                    swal.close();
                    swal('失败', '下架信息失败!', 'error');
                }
            });
        }
    });
}