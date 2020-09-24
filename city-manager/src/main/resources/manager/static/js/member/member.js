function updateMember() {
    var id = document.getElementById("id").value;
    var name = document.getElementById("name").value;
    var memberLevel = document.getElementById("memberLevel").value;
    swal("修改用户信息中，请稍后！", {buttons: false});
    $.ajax({
        type: "POST",
        url: "/city-manager/member/feign/updateMember",
        dataType: "json",
        contentType: "application/json",
        data: JSON.stringify({
            "id": id,
            "name": name,
            "memberLevel": memberLevel
        }),
        success: function (data) {
            swal.close();
            console.log("data is :" + data);
            swal("成功！", "修改成功", "success");
            $("#editModal").modal('hide');
            $('#memberTable').bootstrapTable('refresh');
        },
        error: function () {
            swal.close();
            swal("失败！", "修改失败", "error");
            return false;
        }
    });
}

function editMember(id) {
    var data = JSON.stringify($('#memberTable').bootstrapTable('getRowByUniqueId', id));
    data = eval("(" + data + ")");
    $("#id").val(data.id);
    $("#name").val(data.name);
    $("#memberLevel").val(data.memberLevel);
    $("#editModal").modal('show');
}

function deleteLogicMember(id) {
    swal({
        title: "确定是否封禁该用户？",
        text: "确定该用户是否违规！",
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
            swal("封禁用户中，请稍后！", {buttons: false});
            $.ajax({
                url: "/city-manager/member/feign/deleteLogicMember/" + id,
                method: 'get',
                success: function () {
                    swal.close();
                    swal('成功', '封禁用户成功!', 'success');
                    $('#memberTable').bootstrapTable('refresh');
                },
                error: function () {
                    swal.close();
                    swal('失败', '封禁用户失败!', 'error');
                }
            });
        }
    });
}

function statusFormatter(value) {
    switch (value) {
        case 0:
            return "未激活";
        case 1:
            return "已激活";
        case 2:
            return "封禁";
    }
}

function levelFormatter(value) {
    switch (value) {
        case 0:
            return "普通用户";
        case 1:
            return "管理员";
        case 2:
            return "超级管理员";
    }
}

function avatarFormatter(value) {
    return '<img style="width: 125px;height: 125px" src="' + value + '">';
}

//操作栏的格式化
function actionMemberFormatter(value, row) {
    var result = "";
    result += "<button type='button:' data-toggle='tooltip' class='btn btn-link btn-primary btn-lg' " +
        "data-original-title='Edit Task' title='编辑' onclick='editMember(" + row.id + ")'> <i class='fa fa-edit'></i></button>";
    result += "<button type='button:' data-toggle='tooltip' class='btn btn-link btn-danger' " +
        "data-original-title='Remove' title='删除' onclick='deleteLogicMember(" + row.id + ")'> <i class='fa fa-times'></i></button>";
    return result;
}

$(function () {
    $('#memberTable').bootstrapTable({
        ajax: function (request) {
            $.ajax({
                type: "GET",
                url: "/city-manager/member/feign/getAllMembers",
                contentType: "application/json;charset=utf-8",
                dataType: "json",
                data: '',
                success: function (msg) {
                    request.success({
                        row: msg
                    });
                    $('#memberTable').bootstrapTable('load', msg.data);
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
        uniqueId: "id",
        sortName: "id",
        sortOrder: "desc",
        pageSize: 10,
        pageList: "[10, 25, 50, 100]",
        pageNumber: 1,
        search: true,
        pagination: true,
        columns: [{
            field: 'id',
            title: '用户id',
            align: 'center',
            switchable: true,
            width: 200
        }, {
            field: 'name',
            title: '用户名',
            align: 'center',
            switchable: true,
            width: 200
        }, {
            field: 'password',
            title: '密码',
            align: 'center',
            switchable: true,
            width: 200
        }, {
            field: 'memberLevel',
            title: '用户等级',
            align: 'center',
            formatter: levelFormatter,
            switchable: true,
            width: 200
        }, {
            field: 'phone',
            title: '手机号',
            align: 'center',
            switchable: true,
            width: 250
        }, {
            field: 'email',
            title: '邮箱',
            align: 'center',
            switchable: true,
            width: 250
        }, {
            field: 'avatar',
            title: '头像',
            align: 'center',
            formatter: avatarFormatter,
            switchable: true,
            width: 250
        }, {
            field: 'status',
            title: '状态',
            align: 'center',
            formatter: statusFormatter,
            switchable: true,
            width: 250
        }, {
            field: 'createTime',
            title: '注册时间',
            align: 'center',
            switchable: true,
            sortable: true,
            width: 200
        }, {
            field: 'ID',
            title: '操作',
            align: 'center',
            formatter: actionMemberFormatter,
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