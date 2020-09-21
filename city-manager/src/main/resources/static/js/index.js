function saveTask() {
    var taskName = document.getElementById("taskName").value;
    var taskDescription = document.getElementById("taskDescription").value;
    $.ajax({
        type: "POST",
        url: "/manager/task/save",
        dataType: "json",
        contentType: "application/json",
        data: JSON.stringify({
            "taskName": taskName,
            "taskDescription": taskDescription,
        }),
        success: function (data) {
            console.log("data is :" + data);
            setTimeout(function(){swal('成功', '添加任务成功!', 'success'); },100);
            setTimeout(function(){window.location.reload(); },2000);
        },
        error: function () {
            swal("失败！", "新增任务失败", "error");
            return false;
        }
    });
}

function addTask() {
    $("#taskModal").modal('show');
}

function accomplish(taskId) {
    swal({
        title: "确定是否完成该任务？",
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
                url: "/manager/task/deleteLogicTaskById/" + taskId,
                method: 'get',
                success: function () {
                    setTimeout(function(){swal('成功', '完成任务成功!', 'success'); },100);
                    setTimeout(function(){window.location.reload(); },2000);
                },
                error: function () {
                    swal('失败', '完成任务失败!', 'error');
                }
            });
        }
    });
}

var barChart = document.getElementById('barChart').getContext('2d');
var myBarChart = new Chart(barChart, {
    type: 'bar',
    data: {
        labels: ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"],
        datasets: [{
            label: "图片数量",
            backgroundColor: 'rgb(23, 125, 255)',
            borderColor: 'rgb(23, 125, 255)',
            data: [10, 15, 9, 5, 4, 6, 4, 6, 7, 8, 7, 4],
        }],
    },
    options: {
        responsive: true,
        maintainAspectRatio: false,
        scales: {
            yAxes: [{
                ticks: {
                    beginAtZero: true
                }
            }]
        },
    }
});

$('#lineChart').sparkline([5, 7, 3, 5, 7, 8, 2], {
    type: 'line',
    height: '70',
    width: '100%',
    lineWidth: '2',
    lineColor: 'rgba(255, 255, 255, .5)',
    fillColor: 'rgba(255, 255, 255, .15)'
});

$('#lineChart2').sparkline([5, 7, 8, 10, 11, 12, 15], {
    type: 'line',
    height: '70',
    width: '100%',
    lineWidth: '2',
    lineColor: 'rgba(255, 255, 255, .5)',
    fillColor: 'rgba(255, 255, 255, .15)'
});

$('#lineChart3').sparkline([30, 54, 76, 89, 95, 105, 115], {
    type: 'line',
    height: '70',
    width: '100%',
    lineWidth: '2',
    lineColor: 'rgba(255, 255, 255, .5)',
    fillColor: 'rgba(255, 255, 255, .15)'
});