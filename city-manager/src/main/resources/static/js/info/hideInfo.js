$(function () {
    $('#messageTable').bootstrapTable({
        ajax: function (request) {
            $.ajax({
                type: "GET",
                // /info/feign/状态标识符（0特殊状态1全状态）/展示标识符(0不展示1展示2全部)/info
                url: "/info/feign/1/0/info",
                contentType: "application/json;charset=utf-8",
                dataType: "json",
                data: '',
                success: function (msg) {
                    request.success({
                        row: msg
                    });
                    $('#messageTable').bootstrapTable('load', msg.data);
                },
                error: function () {
                    swal("失败！", "表格读取失败！", "error");
                }
            });
        },

        toolbar: '#toolbar',
        singleSelect: true,
        sidePagination: "client",
        showRefresh: false,
        uniqueId: "infoId",
        sortName: "infoUpdateTime",
        sortOrder: "desc",
        pageSize: 10,
        pageList: "[10, 25, 50, 100]",
        pageNumber: 1,
        search: true,
        pagination: true,
        columns: [{
            field: 'imageEntity',
            title: '图片',
            align: 'center',
            formatter: imageFormatter,
            switchable: true,
            width: 200
        }, {
            field: 'infoName',
            title: '消息名称',
            align: 'center',
            switchable: true,
            width: 200
        }, {
            field: 'infoDescription',
            title: '图片描述',
            align: 'center',
            switchable: true,
            width: 200
        }, {
            field: 'infoSource',
            title: '消息来源',
            align: 'center',
            formatter: sourceFormatter,
            switchable: true,
            width: 200
        }, {
            field: 'infoIdentify',
            title: '识别状态',
            align: 'center',
            formatter: identifyFormatter,
            switchable: true,
            width: 250
        }, {
            field: 'coordinateEntity',
            title: '坐标信息',
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
            field: 'infoUpdateTime',
            title: '更新时间',
            align: 'center',
            switchable: true,
            sortable: true,
            width: 200
        }, {
            field: 'infoStatus',
            title: '消息状态',
            align: 'center',
            formatter: infoStatusFormatter,
            switchable: true,
            sortable: true,
            width: 200
        }, {
            field: 'ID',
            title: '操作',
            align: 'center',
            formatter: actionHideInfoFormatter,
            valign: 'middle',
            width: 50
        }],
        formatLoadingMessage: function () {
            return "加载中，请稍后！";
        },
        formatNoMatches: function(){
            return "没有相关的匹配结果";
        }
    });
});

function deleteInfo(infoId) {
    swal({
        title: "确定是否删除该信息？",
        text: "删除后数据不可恢复！",
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
            swal("删除信息中，请稍后！", {buttons: false});
            $.ajax({
                url: "/info/feign/deleteInfoByInfoId/" + infoId,
                method: 'get',
                success: function () {
                    swal.close();
                    swal('成功', '删除信息成功!', 'success');
                    $('#messageTable').bootstrapTable('refresh');
                },
                error: function () {
                    swal.close();
                    swal('失败', '删除信息失败!', 'error');
                }
            });
        }
    });
}


//操作栏的格式化
function actionHideInfoFormatter(value, row) {
    var result = "";
    result += "<button type='button:' data-toggle='tooltip' class='btn btn-link btn-primary btn-lg' " +
        "data-original-title='Edit Task' title='编辑' onclick='editInfo(" + row.infoId + ")'> <i class='fa fa-edit'></i></button>";
    result += "<button type='button:' data-toggle='tooltip' class='btn btn-link btn-danger' " +
        "data-original-title='Remove' title='删除' onclick='deleteInfo(" + row.infoId + ")'> <i class='fa fa-times'></i></button>";
    return result;
}