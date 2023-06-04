$(function () {
    $.get("left_sidebar.html",function (data) {
        $("#left_sidebar").html(data);
    });
});