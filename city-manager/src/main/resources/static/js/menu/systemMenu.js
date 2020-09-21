function addOrUpdateMenu() {
    var menuId = document.getElementById("menuId").value;
    var menuName = document.getElementById("menuName").value;
    var menuUrl = document.getElementById("menuUrl").value;
    var perms = document.getElementById("perms").value;
    var parentId = document.getElementById("parentId").value;
    var type = document.getElementById("type").value;
    var icon = document.getElementById("icon").value;
    var orderNum = document.getElementById("orderNum").value;

    var title = document.getElementById("modelTitle").innerHTML;
    $.ajax({
        type: "POST",
        dataType: "json",
        url: '/manager/menu/addOrUpdateMenu',
        contentType: "application/json",
        data: JSON.stringify({
            "menuId": menuId,
            "menuName": menuName,
            "menuUrl": menuUrl,
            "perms": perms,
            "parentId": parentId,
            "type": type,
            "icon": icon,
            "orderNum": orderNum
        }),
        success: function (data) {
            console.log("data is :" + data);
            swal("成功", "菜单" + title + "成功！", "success");
            $("#menuModal").modal('hide');
            $('#menuTable').bootstrapTable('refresh');
        },
        error: function () {
            swal("失败", "菜单" + title + "失败！", "error");
        }
    });
}

function showAddModel() {
    $("#modelTitle").text("新增");
    $("#modelButton").text('新增');
    $("#menuId").val(null);
    $("#parentId").val(null);
    $("#menuName").val(null);
    $("#menuUrl").val(null);
    $("#perms").val(null);
    $("#type").val(null);
    $("#icon").val(null);
    $("#orderNum").val(null);
    $("#menuModal").modal('show');
}

$(function () {
    $('#menuTable').bootstrapTable({
        ajax: function (request) {
            $.ajax({
                type: "GET",
                url: "/manager/menu/allMenu",
                contentType: "application/json;charset=utf-8",
                dataType: "json",
                data: '',
                success: function (msg) {
                    request.success({
                        row: msg
                    });
                    $('#menuTable').bootstrapTable('load', msg.data);
                },
                error: function () {
                    swal("失败", "菜单表格读取失败！", "error");
                }
            });
        },

        toolbar: '#toolbar',
        singleSelect: true,
        sidePagination: "client",
        showRefresh: false,
        uniqueId: "menuId",
        sortName: "menuId",
        sortOrder: "asc",
        pageSize: 10,
        pageList: "[10, 25, 50, 100]",
        pageNumber: 1,
        search: true,
        pagination: true,
        columns: [{
            field: 'menuId',
            title: '菜单id',
            align: 'center',
            switchable: true,
            width: 200
        }, {
            field: 'parentId',
            title: '父级id',
            align: 'center',
            switchable: true,
            width: 200
        }, {
            field: 'menuName',
            title: '菜单名称',
            align: 'center',
            switchable: true,
            width: 200
        }, {
            field: 'menuUrl',
            title: '菜单路径',
            align: 'center',
            switchable: true,
            width: 200
        }, {
            field: 'perm',
            title: '授权',
            align: 'center',
            switchable: true,
            width: 250
        }, {
            field: 'type',
            title: '菜单类型',
            align: 'center',
            formatter: typeFormatter,
            switchable: true,
            width: 200
        }, {
            field: 'icon',
            title: '菜单图标',
            align: 'center',
            switchable: true,
            width: 200
        }, {
            field: 'orderNum',
            title: '排序',
            align: 'center',
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
        "data-original-title='Edit Task' title='编辑' onclick='editMenu(" + row.menuId + ")'> <i class='fa fa-edit'></i></button>";
    result += "<button type='button:' data-toggle='tooltip' class='btn btn-link btn-danger' " +
        "data-original-title='Remove' title='删除' onclick='deleteMenu(" + row.menuId + ")'> <i class='fa fa-times'></i></button>";
    return result;
}

function editMenu(menuId) {
    $("#modelTitle").text('修改');
    $("#modelButton").text('修改');
    var data = JSON.stringify($('#menuTable').bootstrapTable('getRowByUniqueId', menuId));
    data = eval("(" + data + ")");
    $("#menuId").val(data.menuId);
    $("#parentId").val(data.parentId);
    $("#menuName").val(data.menuName);
    $("#menuUrl").val(data.menuUrl);
    $("#perms").val(data.perms);
    $("#type").val(data.type);
    $("#icon").val(data.icon);
    $("#orderNum").val(data.orderNum);
    $("#menuModal").modal('show');
}

function deleteMenu(menuId) {
    swal({
        title: "确定是否删除该菜单？",
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
                url: "/manager/menu/deleteMenuById/" + menuId,
                method: 'get',
                success: function () {
                    swal('成功', '删除菜单成功!', 'success');
                    $('#menuTable').bootstrapTable('refresh');
                },
                error: function () {
                    swal('失败', '删除菜单失败!', 'error');
                }
            });
        }
    });
}

function typeFormatter(value) {
    switch (value) {
        case 0:
            return "目录";
        case 1:
            return "菜单";
        case 2:
            return "按钮";
    }
}