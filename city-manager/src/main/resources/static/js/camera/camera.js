function addOrUpdateCamera() {
    var cameraId = document.getElementById("cameraId").value;
    var cameraName = document.getElementById("cameraName").value;
    var cameraDescription = document.getElementById("cameraDescription").value;
    var areaCode = document.getElementById("areaCode").value;

    var title = document.getElementById("modelTitle").innerHTML;
    var address;
    if (title === "新增") {
        address = "addCamera";
    } else {
        address = "updateCamera";
    }
    $.ajax({
        type: "POST",
        dataType: "json",
        url: '/camera/feign/' + address,
        contentType: "application/json",
        data: JSON.stringify({
            "cameraId": cameraId,
            "cameraName": cameraName,
            "cameraDescription": cameraDescription,
            "areaCode": areaCode
        }),
        success: function (data) {
            console.log("data is :" + data);
            swal("成功", "摄像头" + title + "成功！", "success");
            $("#cameraModal").modal('hide');
            $('#cameraTable').bootstrapTable('refresh');
        },
        error: function () {
            swal("失败", "摄像头" + title + "失败！", "error");
        }
    });
}

function showAddModel() {
    $("#modelTitle").text("新增");
    $("#modelButton").text('新增');
    $("#address").show();
    $("#cameraId").val(null);
    $("#cameraName").val(null);
    $("#cameraDescription").val(null);
    $("#province").val(null);
    $("#city").val(null);
    $("#areaCode").val(null);
    $("#cameraModal").modal('show');
}

$(function () {
    $('#cameraTable').bootstrapTable({
        ajax: function (request) {
            $.ajax({
                type: "GET",
                url: "/camera/feign/allCamera",
                contentType: "application/json;charset=utf-8",
                dataType: "json",
                data: '',
                success: function (msg) {
                    request.success({
                        row: msg
                    });
                    $('#cameraTable').bootstrapTable('load', msg.data);
                },
                error: function () {
                    swal("错误", "表格获取失败！", "error");
                }
            });
        },

        toolbar: '#toolbar',
        singleSelect: true,
        sidePagination: "client",
        showRefresh: false,
        uniqueId: "cameraId",
        sortName: "cameraId",
        sortOrder: "asc",
        showLoading: true,
        pageSize: 10,
        pageList: "[10, 25, 50, 100]",
        pageNumber: 1,
        search: true,
        pagination: true,
        columns: [{
            field: 'cameraId',
            title: '摄像头id',
            align: 'center',
            switchable: true,
            width: 200
        }, {
            field: 'cameraName',
            title: '摄像头名称',
            align: 'center',
            switchable: true,
            width: 200
        }, {
            field: 'cameraDescription',
            title: '摄像头描述',
            align: 'center',
            switchable: true,
            width: 200
        }, {
            field: 'coordinateEntity',
            title: '坐标',
            align: 'center',
            formatter: coordinateFormatter,
            switchable: true,
            width: 200
        }, {
            field: 'provinceEntity',
            title: '省份',
            align: 'center',
            formatter: provinceFormatter,
            switchable: true,
            width: 200
        }, {
            field: 'cityEntity',
            title: '城市',
            align: 'center',
            formatter: cityFormatter,
            switchable: true,
            width: 200
        }, {
            field: 'areaEntity',
            title: '区/县',
            align: 'center',
            formatter: areaFormatter,
            switchable: true,
            width: 200
        }, {
            field: 'ID',
            title: '操作',
            align: 'center',
            formatter: actionFormatter,
            valign: 'middle',
            width: 50
        }],
        formatLoadingMessage: function () {
            return "加载中，请稍后！";
        },
        formatNoMatches: function () {
            return "没有相关的匹配结果";
        }
    });
});

//操作栏的格式化
function actionFormatter(value, row) {
    var result = "";
    result += "<button type='button:' data-toggle='tooltip' class='btn btn-link btn-primary btn-lg' " +
        "data-original-title='Edit Task' title='编辑' onclick='editCamera(" + row.cameraId + ")'> <i class='fa fa-edit'></i></button>";
    result += "<button type='button:' data-toggle='tooltip' class='btn btn-link btn-danger' " +
        "data-original-title='Remove' title='删除' onclick='deleteCamera(" + row.cameraId + ")'> <i class='fa fa-times'></i></button>";
    return result;
}

function editCamera(cameraId) {
    $("#modelTitle").text('修改');
    $("#modelButton").text('修改');
    var data = JSON.stringify($('#cameraTable').bootstrapTable('getRowByUniqueId', cameraId));
    data = eval("(" + data + ")");
    $("#cameraId").val(data.cameraId);
    $("#cameraName").val(data.cameraName);
    $("#cameraDescription").val(data.cameraDescription);
    $("#address").hide();
    $("#cameraModal").modal('show');
}

function deleteCamera(cameraId) {
    swal({
        title: "确定是否删除该摄像头信息？",
        text: "删除后数据不可恢复!",
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
            $.ajax({
                url: "/manager/menu/deleteMenuById/" + cameraId,
                method: 'get',
                success: function () {
                    swal('成功', '删除摄像头信息成功!', 'success');
                    $('#cameraTable').bootstrapTable('refresh');
                },
                error: function () {
                    swal('失败', '删除摄像头信息失败!', 'error');
                }
            });
        }
    });
}

// 省市区三级联动
function getArea() {
    $("#areaCode").html('<option value="">----请选择区域----</option>');
    $.ajax({
        url: "/info/feign/getAreaByCityCode?cityCode=" + $("#city").val(),
        type: "GET",
        dataType: "json",
        success: function (data) {
            if (data.data != null) {
                $(data.data).each(function (i) {
                    $("#areaCode").append(
                        '<option value="' + data.data[i].areaCode + '">' + data.data[i].areaName + '</option>'
                    );
                });
            }
        },
        error: function () {
            swal("错误", "请先选择城市！", "error");
        }
    });
}

function getCity() {
    $("#city").html('<option value="">----请选择城市----</option>');
    $.ajax({
        url: "/info/feign/getCityByProvinceCode?provinceCode=" + $("#province").val(),
        type: "GET",
        dataType: "json",
        success: function (data) {
            if (data.data != null) {
                $(data.data).each(function (i) {
                    $("#city").append(
                        '<option value="' + data.data[i].cityCode + '">' + data.data[i].cityName + '</option>'
                    );
                });
            }
        },
        error: function () {
            swal("错误", "请先选择省份！", "error");
        }
    });
}