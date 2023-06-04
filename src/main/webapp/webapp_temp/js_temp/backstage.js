$(document).ready(function(){
    $(".siderbar_menu li").click(function(){
        $(".siderbar_menu li").removeClass("active");
        $(this).addClass("active");
    })
    $(".accordion li a").click(function(){
        $(".accordion li a").removeClass("active");
        $(this).addClass("active");
    })
    $(".hamburger").click(function(){
        $(".wrapper").addClass("active");
    })
    $(".bg_shadow,.close").click(function(){
        $(".wrapper").removeClass("active");
    })
})