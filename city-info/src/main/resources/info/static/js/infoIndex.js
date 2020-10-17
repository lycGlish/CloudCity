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
// 右上角，仅包含平移和缩放按钮
var top_right_navigation = new BMap.NavigationControl({
    anchor: BMAP_ANCHOR_TOP_RIGHT
});
// 添加控件和比例尺
map.addControl(top_right_navigation);
// 城市控件
var size = new BMap.Size(10, 20);
map.addControl(new BMap.CityListControl({
    anchor: BMAP_ANCHOR_TOP_LEFT,
    offset: size,
}));
var opts = {
    // 信息窗口宽度
    width: 200,
    // 信息窗口高度
    height: 200,
    // 信息窗口标题
    title: "预警信息",
    // 设置允许信息窗发送短息
    enableMessage: true
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
    // 创建信息窗口对象
    var infoWindow = new BMap.InfoWindow(content, opts);
    // 开启信息窗口
    map.openInfoWindow(infoWindow, point);
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
                var color = "";
                var myIcon = "";
                var marker = "";
                var content = "";
                var status = "";
                switch (data_info[i].infoStatus) {
                    case 0:
                        color = "blue";
                        status = "识别错误";
                        break;
                    case 1:
                        color = "green";
                        status = "无积水";
                        break;
                    case 2:
                        color = "yellow";
                        status = "积水";
                        break;
                    case 3:
                        color = "red";
                        status = "内涝";
                        break;
                    case 4:
                        color = "pink";
                        status = "冰雪";
                        break;
                }
                myIcon = new BMap.Icon('../info/static/images/' + color + '.png', new BMap.Size(30, 30));
                myIcon.setImageSize(new BMap.Size(30, 30));
                marker = new BMap.Marker(new BMap.Point(data_info[i].coordinateEntity.longitude, data_info[i].coordinateEntity.latitude
                ), {icon: myIcon});
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

// 分页工具
$(document).ready(function () {
    $("#example").dataTable({
        "bProcessing": true, //DataTables载入数据时，是否显示‘进度’提示
        "aLengthMenu": [6, 12, 20], //更改显示记录数选项
        "sPaginationType": "full_numbers", //详细分页组，可以支持直接跳转到某页
        "bAutoWidth": true, //是否自适应宽度
        //"bJQueryUI" : true,
        "oLanguage": { //国际化配置
            "sProcessing": "正在获取数据，请稍后...",
            "sLengthMenu": "显示 _MENU_ 条",
            "sZeroRecords": "没有您要搜索的内容",
            "sInfo": "从 _START_ 到  _END_ 条记录 总记录数为 _TOTAL_ 条",
            "sInfoEmpty": "记录数为0",
            "sInfoFiltered": "(全部记录数 _MAX_ 条)",
            "sInfoPostFix": "",
            "sSearch": "搜索",
            "sUrl": "",
            "oPaginate": {
                "sFirst": "第一页",
                "sPrevious": "上一页",
                "sNext": "下一页",
                "sLast": "最后一页"
            }
        },
    });
});

function uploadInfo() {
    swal("上传消息中，请稍后！", {buttons: false});
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
            setTimeout(function () {
                swal("成功", "预警上传成功！", "success");
            }, 100);
            setTimeout(function () {
                window.location.reload();
            }, 10000);
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
    swal("上传图片中，请稍后！", {buttons: false});
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
    window.location.href = "http://localhost:88/city-user/login";
}

function toLogout() {

    window.location.href = "http://localhost:88/city-manager/logout";
}

function toManager() {
    window.location.href = "http://localhost:88/city-manager/";
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

var accuracyChart = echarts.init(document.getElementById('accuracy'));
// 指定图表的配置项和数据
accuracy = {
    tooltip: {
        trigger: 'item',
        formatter: '{a} <br/>{b}: {c} ({d}%)'
    },
    legend: {
        orient: 'vertical',
        left: 10,
        data: ['系统识别', '人工修改']
    },
    series: [
        {
            name: '识别来源',
            type: 'pie',
            radius: ['50%', '70%'],
            avoidLabelOverlap: false,
            label: {
                show: false,
                position: 'center'
            },
            emphasis: {
                label: {
                    show: true,
                    fontSize: '30',
                    fontWeight: 'bold'
                }
            },
            labelLine: {
                show: false
            },
            data: [
                {value: 90, name: '系统识别'},
                {value: 10, name: '人工修改'},
            ]
        }
    ]
};
// 使用刚指定的配置项和数据显示图表。
accuracyChart.setOption(accuracy);

var sourceChart = echarts.init(document.getElementById('source'));
source = {
    tooltip: {
        trigger: 'item',
        formatter: '{a} <br/>{b}: {c} ({d}%)'
    },
    legend: {
        orient: 'vertical',
        left: 10,
        data: ['用户上传', '摄像头采集', '网络获取']
    },
    series: [
        {
            name: '图片来源',
            type: 'pie',
            radius: ['50%', '70%'],
            avoidLabelOverlap: false,
            label: {
                show: false,
                position: 'center'
            },
            emphasis: {
                label: {
                    show: true,
                    fontSize: '30',
                    fontWeight: 'bold'
                }
            },
            labelLine: {
                show: false
            },
            data: [
                {value: 335, name: '用户上传'},
                {value: 310, name: '摄像头采集'},
                {value: 234, name: '网络获取'}
            ]
        }
    ]
};
sourceChart.setOption(source);

var sumChart = echarts.init(document.getElementById('sum'));
sum = {
    tooltip: {
        trigger: 'axis',
        axisPointer: {            // 坐标轴指示器，坐标轴触发有效
            type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
        }
    },
    legend: {
        data: ['无积水', '积水', '内涝', '冰雪']
    },
    grid: {
        left: '3%',
        right: '4%',
        bottom: '3%',
        containLabel: true
    },
    xAxis: {
        type: 'value'
    },
    yAxis: {
        type: 'category',
        data: ['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月']
    },
    series: [
        {
            name: '无积水',
            type: 'bar',
            stack: '总量',
            label: {
                show: true,
                position: 'insideRight'
            },
            data: [320, 302, 301, 334, 390, 330, 320, 120, 213, 253, 123, 252]
        },
        {
            name: '积水',
            type: 'bar',
            stack: '总量',
            label: {
                show: true,
                position: 'insideRight'
            },
            data: [120, 132, 101, 134, 90, 230, 210, 123, 234, 645, 673, 123]
        },
        {
            name: '内涝',
            type: 'bar',
            stack: '总量',
            label: {
                show: true,
                position: 'insideRight'
            },
            data: [220, 182, 191, 234, 290, 330, 310, 213, 432, 123, 122, 242]
        },
        {
            name: '冰雪',
            type: 'bar',
            stack: '总量',
            label: {
                show: true,
                position: 'insideRight'
            },
            data: [150, 212, 201, 154, 190, 330, 410, 234, 123, 232, 322, 311]
        }
    ]
};
sumChart.setOption(sum);