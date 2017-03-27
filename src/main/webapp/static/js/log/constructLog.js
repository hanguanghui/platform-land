/**
 * Created by asus on 2017/3/13.
 */

layui.use(['form', 'element', 'laypage', 'laydate', 'layer', 'laytpl'], function () {
    var form = layui.form(),
        laydate = layui.laydate,
        layer = layui.layer,
        laytpl = layui.laytpl,
        laypage = layui.laypage,
        $ = layui.jquery,
        element = layui.element(); //Tab的切换功能，切换事件监听等，需要依赖element模块

    //初始化页面等信息
    var myDate = new Date();
    var str = "" + myDate.getFullYear() + "年";
    str += (myDate.getMonth()+1) + "月";
    str += myDate.getDate() + "日";

    var today = new Array('日','一','二','三','四','五','六');
    var week = today[ myDate .getDay()];

    $("#nowDay").html("星期"+week+"<input type=\"hidden\" id=\"day\" name=\"day\" value='"+week+"'/>");
    $("#nowDate").html(str);

    var location =$("#location").val();
    var cityJson=[{"city":"滁州","code":"101221101","country":"滁州","provience":"安徽"},{"city":"滁州","code":"101221102","country":"凤阳","provience":"安徽"},{"city":"滁州","code":"101221103","country":"明光","provience":"安徽"},{"city":"滁州","code":"101221104","country":"定远","provience":"安徽"},{"city":"滁州","code":"101221105","country":"全椒","provience":"安徽"},{"city":"滁州","code":"101221106","country":"来安","provience":"安徽"},{"city":"滁州","code":"101221107","country":"天长","provience":"安徽"}];
    var code="101221101";
    // var url = "http://www.weather.com.cn/weather1d/101221101.shtml";
    var city="滁州";
    $.each(cityJson,function(i,item){
        if(item.city==location||item.country ==location){
            city = item.country;
            code = item.code;
            return;
        }
    });
    debugger;
    $.getJSON("/land/utilService/getWeather?city="+city+"&code="+code,function(data){
        debugger;
        var weather = data.result ;
        $("#weather").val(weather.weather);
        $("#morning").val(weather.dayWea);
        $("#afternoon").val(weather.dayWea);
        $("#night").val(weather.nightWea);
        debugger;
    });

    $("#subConBtn").on("click",function(){
        var url = $("#conLogForm").attr("action");
        var data = $('#conLogForm').serialize();
        $.post(url,data,function(data){
            debugger;
            layer.msg("提交成功！",{icon:1});
            $('#conLogForm')[0].reset();
            layer.closeAll();
        });
    })


});
