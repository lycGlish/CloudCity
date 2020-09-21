function login() {
    swal("登陆中，请稍后！",{buttons: false});
    var phone = document.getElementById("phone").value;
    var password = document.getElementById("password").value;
    $.ajax({
        type: "POST",
        dataType: "json",
        url: '/user/member/doLogin',
        contentType: "application/json",
        data: JSON.stringify({
            "phone": phone,
            "password": password
        }),
        success: function (data) {
            swal.close();
            if(data.data!=null){
                window.location.href="http://localhost:8000"
            }else {
                swal('登录失败', '请检查账号密码是否错误!', 'error');
            }
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