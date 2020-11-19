function updateMemberInfo() {
    var id = document.getElementById("id").value;
    var name = document.getElementById("name").value;
    var email = document.getElementById("email").value;
    var avatar = document.getElementById("avatar").value;
    swal("修改个人信息中，请稍后！", {buttons: false});
    $.ajax({
        type: "POST",
        url: "/city-manager/member/feign/updateMember",
        dataType: "json",
        contentType: "application/json",
        data: JSON.stringify({
            "id": id,
            "name": name,
            "email": email,
            "avatar": avatar
        }),
        success: function () {
            swal.close();
            setTimeout(function () {
                swal("成功", "个人信息修改成功,重新登录后展示更新！", "success");
            }, 100);
            setTimeout(function () {
                window.location.href = "http://localhost:88/city-manager/";
            }, 5000);
        },
        error: function () {
            swal.close();
            swal("失败！", "修改失败", "error");
            return false;
        }
    });
}

// 上传图片并回显
function uploadImg() {
    swal("上传图片中，请稍后！", {buttons: false});
    $.ajax({
        type: "POST",
        url: "/city-manager/manager/image/fast/upload",
        cache: false,
        contentType: false,
        processData: false,
        dataType: "json",
        data: new FormData($("#formTag")[0]),
        success: function (data) {
            swal.close();
            // 图片回显
            $("#img").attr("src", data.data.url);
            // 图片url存入表单中传入后台保存
            $("#avatar").attr("value", data.data.url);
        },
        error: function () {
            swal.close();
            swal("失败", "ajax图片上传错误！", "error");
        }
    });
}

function showModel() {
    $("#resetModal").modal('show');
}

function resetPassword() {
    var id = document.getElementById("id").value;
    var password = document.getElementById("password").value;
    swal({
        title: "确定重置密码？",
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
            swal("重置密码中，请稍后！",{buttons: false});
            $.ajax({
                url: "/city-manager/member/feign/updateMember",
                method: 'post',
                dataType: "json",
                contentType: "application/json",
                data: JSON.stringify({
                    "id": id,
                    "password": password,
                }),
                success: function () {
                    swal.close();
                    setTimeout(function(){
                        swal('成功', '重置密码成功，正在注销', 'success',{buttons: false}); },100);
                    setTimeout(function(){
                        window.location.href = "http://localhost:88/city-manager/logout";},3000);
                },
                error: function () {
                    swal.close();
                    swal('失败', '重置密码失败!', 'error');
                }
            });
        }
    });
}