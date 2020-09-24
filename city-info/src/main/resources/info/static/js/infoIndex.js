// 百度地图API功能
var map = new BMap.Map("allmap");
var point = new BMap.Point(117.97, 28.45);
map.centerAndZoom(point, 12);
// 根据ip定位地图
var geolocation = new BMap.Geolocation();
geolocation.getCurrentPosition(function (r) {
    if (this.getStatus() == BMAP_STATUS_SUCCESS) {
        var mk = new BMap.Marker(r.point);
        // map.addOverlay(mk);
        map.panTo(r.point);
    } else {
        alert('failed' + this.getStatus());
    }
}, {enableHighAccuracy: true});
//右上角，仅包含平移和缩放按钮
var top_right_navigation = new BMap.NavigationControl({
    anchor: BMAP_ANCHOR_TOP_RIGHT
});
//添加控件和比例尺
map.addControl(top_right_navigation);
// 城市控件
var size = new BMap.Size(10, 20);
map.addControl(new BMap.CityListControl({
    anchor: BMAP_ANCHOR_TOP_LEFT,
    offset: size,
}));
var opts = {
    width: 200,     // 信息窗口宽度
    height: 200,     // 信息窗口高度
    title: "预警信息", // 信息窗口标题
    enableMessage: true//设置允许信息窗发送短息
};
var data_info = [];

function addClickHandler(content, marker) {
    marker.addEventListener("click", function (e) {
            openInfo(content, e)
        }
    );
}

function openInfo(content, e) {
    var p = e.target;
    var point = new BMap.Point(p.getPosition().lng, p.getPosition().lat);
    var infoWindow = new BMap.InfoWindow(content, opts);  // 创建信息窗口对象
    map.openInfoWindow(infoWindow, point); //开启信息窗口
}

$(document).ready(function () {
    $.ajax({
        type: "GET",
        // /info/info/状态标识符（0特殊状态1全状态）/展示标识符(0不展示1展示2全部)/info
        url: "/city-info/info/info/0/1/info",
        contentType: "application/json;charset=utf-8",
        dataType: "json",
        data: '',
        success: function (data) {
            data_info = data.data;
            for (var i = 0; i < data_info.length; i++) {
                var marker = new BMap.Marker(new BMap.Point(data_info[i].coordinateEntity.longitude, data_info[i].coordinateEntity.latitude));  // 创建标注
                var content = "";
                var status = "";
                switch (data_info[i].infoStatus) {
                    case 0:
                        status = "识别错误";
                        break;
                    case 1:
                        status = "无积水";
                        break;
                    case 2:
                        status = "积水";
                        break;
                    case 3:
                        status = "内涝";
                        break;
                    case 4:
                        status = "冰雪";
                        break;
                }
                content += "更新时间:" + data_info[i].infoUpdateTime.toString() + "<br>";
                content += "道路状态:" + status + "<br>";
                content += "图片:<img style='float:right;margin:4px' id='imgDemo' src='" + data_info[i].imageEntity.imageUrl.toString() +
                    "' width='139' height='104'/><br>";
                map.addOverlay(marker);               // 将标注添加到地图中
                addClickHandler(content, marker);
            }
        },
        error: function () {
            swal("错误", "地图标注预警消息失败", "error");
        }
    });
});

// 获取所有消息
$(function () {
    $('#recordTable').bootstrapTable({
        ajax: function (request) {
            $.ajax({
                type: "GET",
                // /info/info/状态标识符（0特殊状态1全状态）/展示标识符(0不展示1展示2全部)/info
                url: "/city-info/info/info/1/1/info",
                contentType: "application/json;charset=utf-8",
                dataType: "json",
                data: '',
                success: function (msg) {
                    request.success({
                        row: msg
                    });
                    $('#recordTable').bootstrapTable('load', msg.data);
                },
                error: function () {
                    swal("错误", "所有消息表格数据获取失败", "error");
                }
            });
        },

        toolbar: '#toolbar',
        singleSelect: true,
        sidePagination: "client",
        showRefresh: false,
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
            field: 'infoDescription',
            title: '图片地址描述',
            align: 'center',
            switchable: true,
            width: 200
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
        },],
    });
});

// 获取预警消息
$(function () {
    $('#messageTable').bootstrapTable({
        ajax: function (request) {
            $.ajax({
                type: "GET",
                // /info/info/状态标识符（0特殊状态1全状态）/展示标识符(0不展示1展示2全部)/info
                url: "/city-info/info/info/0/1/info",
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
                    swal("错误", "预警消息表格数据获取失败", "error");
                }
            });
        },

        toolbar: '#toolbar',
        singleSelect: true,
        sidePagination: "client",
        showRefresh: false,
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
            field: 'infoDescription',
            title: '图片地址描述',
            align: 'center',
            switchable: true,
            width: 200
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
        },],
    });
});

function imageFormatter(value) {
    return '<img style="width: 125px;height: 125px" src="' + value.imageUrl + '">';
}

function coordinateFormatter(value) {
    return value.longitude + "," + value.latitude;
}

function provinceFormatter(value) {
    return value.provinceName;
}

function cityFormatter(value) {
    return value.cityName;
}

function areaFormatter(value) {
    return value.areaName;
}

function infoStatusFormatter(value) {
    switch (value) {
        case 0:
            return "识别错误";
        case 1:
            return "无积水";
        case 2:
            return "积水";
        case 3:
            return "内涝";
        case 4:
            return "冰雪";
    }
}

// 预警上传模态框显示
function showUploadModal() {
    $("#uploadModal").modal('show');
}

function uploadInfo() {
    swal("上传消息中，请稍后！",{buttons: false});
    var imgUrl = document.getElementById("imgUrl").value;
    var areaCode = document.getElementById("areaCode").value;
    var infoDescription = document.getElementById("infoDescription").value;
    var longitude = document.getElementById("longitude").value;
    var latitude = document.getElementById("latitude").value;
    $.ajax({
        type: "POST",
        dataType: "json",
        url: '/city-info/info/info/saveInfoByUser',
        contentType: "application/json",
        data: JSON.stringify({
            "imgUrl": imgUrl,
            "areaCode": areaCode,
            "infoDescription": infoDescription,
            "longitude": longitude,
            "latitude": latitude
        }),
        success: function (data) {
            swal.close();
            console.log("data is :" + data);
            setTimeout(function(){swal("成功", "预警上传成功！", "success"); },100);
            setTimeout(function(){window.location.reload(); },10000);
        },
        error: function () {
            swal.close();
            swal("失败", "预警上传失败！", "error");
        }
    });
}

// 省市区三级联动
function getDistrict() {
    $("#areaCode").html('<option value="">----请选择区域----</option>');
    $.ajax({
        url: "/city-info/info/area/getAreaByCityCode?cityCode=" + $("#city").val(),
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
            swal("失败", "请先选择城市！", "error");
        }
    });
}

function getCity() {
    $("#city").html('<option value="">----请选择城市----</option>');
    $.ajax({
        url: "/city-info/info/city/getCityByProvinceCode?provinceCode=" + $("#province").val(),
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
            swal("失败", "请先选择省份！", "error");
        }
    });
}

// 上传图片并回显
function uploadImg() {
    swal("上传图片中，请稍后！",{buttons: false});
    $.ajax({
        type: "POST",
        url: "/city-info/info/image/upload/image",
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
            $("#imgUrl").attr("value", data.data.url);
            // 图片自带经纬度存入表单中传入后台
            $("#longitude").attr("value", data.data.longitude);
            $("#latitude").attr("value", data.data.latitude);
            if (data.data.longitude !== "" && data.data.latitude !== "") {
                $("#isExistCoordinate").attr("style", "color:green").attr("value", "图片自带坐标");
            }
        },
        error: function () {
            swal.close();
            swal("失败", "ajax图片上传错误！", "error");
        }
    });
}

function toLogin() {
    window.location.href="http://localhost:88/city-user/login";
}

function imageHint() {
    swal({
        title: "上传须知",
        text: "预警上传坐标默认采用图片自带经纬度，若图片不带经纬度请认真填写地址",
        icon: 'warning',
        buttons: {
            confirm: {
                text: "知道了",
                value: true,
                visible: true,
                closeModal: true
            }
        },
        confirmButtonColor: "#DD6B55",
    });
}