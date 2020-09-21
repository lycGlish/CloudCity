$(function () {
    $('#messageTable').bootstrapTable({
        ajax: function (request) {
            $.ajax({
                type: "GET",
                // /info/feign/状态标识符（0特殊状态1全状态）/展示标识符(0不展示1展示2全部)/info
                url: "/info/feign/0/1/info",
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
            formatter: actionInfoFormatter,
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

