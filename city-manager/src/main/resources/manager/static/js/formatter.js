function sourceFormatter(value) {
    if(value===1){
        return "用户";
    }else {
        return "摄像头";
    }
}

function identifyFormatter(value) {
    if(value===1){
        return "系统识别";
    }else {
        return "人工修改";
    }
}

function imageFormatter(value) {
    return '<img style="width: 125px;height: 125px" src="' + value.imageUrl + '">';
}

function coordinateFormatter(value) {
    return value.longitude + "——" + value.latitude;
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

//操作栏的格式化
function actionInfoFormatter(value, row) {
    var result = "";
    result += "<button type='button:' data-toggle='tooltip' class='btn btn-link btn-primary btn-lg' " +
        "data-original-title='Edit Task' title='编辑' onclick='editInfo(" + row.infoId + ")'> <i class='fa fa-edit'></i></button>";
    result += "<button type='button:' data-toggle='tooltip' class='btn btn-link btn-danger' " +
        "data-original-title='Remove' title='删除' onclick='deleteLogicInfo(" + row.infoId + ")'> <i class='fa fa-times'></i></button>";
    return result;
}