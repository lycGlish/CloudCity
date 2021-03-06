function register() {
    swal("注册中，请稍后！",{buttons: false});
    var name = document.getElementById("name").value;
    var email = document.getElementById("email").value;
    var phone = document.getElementById("phone").value;
    var password = document.getElementById("password").value;
    $.ajax({
        type: "POST",
        dataType: "json",
        url: '/city-user/user/member/doRegister',
        contentType: "application/json",
        data: JSON.stringify({
            "phone": phone,
            "password": password,
            "email": email,
            "name": name
        }),
        success: function (data) {
            swal.close();
            setTimeout(function(){swal('成功', '注册成功!', 'success'); },100);
            setTimeout(function(){window.location.href="/login"; },5000);
        },
        error: function () {
            swal.close();
            swal('失败', '发起登录请求错误，请稍后再试！', 'error');
        }
    });
}

function build() {
    swal("制作中", "功能制作中", "error");
}