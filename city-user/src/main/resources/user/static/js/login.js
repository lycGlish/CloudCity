var phone, password;

function login() {
    phone = document.getElementById("phone").value;
    password = document.getElementById("password").value;
    var flag = true;
    var verificationCode = document.getElementById("verificationCode").value;
    console.log(verificationCode);
    if (verificationCode != null) {
        $.ajax({
            type: "POST",
            dataType: "json",
            url: '/city-user/valid',
            contentType: "application/x-www-form-urlencoded",
            data: {
                'verificationCode': verificationCode
            },
            success: function (data) {
                if (data.code === 500) {
                    swal('登录失败', '验证码错误!', 'error');
                    return false;
                }
            },
            error: function () {
                swal('失败', '验证码校验请求错误，请稍后再试！', 'error');
            }
        });
        if (flag) {
            swal("登陆中，请稍后！", {buttons: false});
            $.ajax({
                type: "POST",
                dataType: "json",
                url: '/city-user/user/member/doLogin',
                contentType: "application/json",
                data: JSON.stringify({
                    "phone": phone,
                    "password": password
                }),
                success: function (data) {
                    if (data.data != null) {
                        if (data.data.status === 2) {
                            swal.close();
                            swal('登录失败', '该账号已被封禁!', 'error');
                        } else if (data.data.memberLevel > 0) {
                            window.location.href = "http://localhost:88/city-manager/";
                        } else if (data.data.memberLevel === 0) {
                            window.location.href = "http://localhost:88/city-info/";
                        }
                    } else {
                        swal.close();
                        swal('登录失败', '请检查账号密码是否错误!', 'error');
                    }
                },
                error: function () {
                    swal.close();
                    swal('失败', '发起登录请求错误，请稍后再试！', 'error');
                }
            });
        }
    } else {
        swal("输入错误", "验证码不能为空", "error");
    }
}

function verification() {
    phone = document.getElementById("phone").value;
    password = document.getElementById("password").value;
    if (phone == null || password == null) {
        swal("输入错误", "账号密码不能为空", "error");
    } else {
        $("#verificationModal").modal('show');
    }
}

function build() {
    swal("制作中", "功能制作中", "error");
}