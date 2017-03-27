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

    var year, month;
    $(function () {
        var proid = $("#proid").val();

        var b = new Date();
        var c = b.getDate();
        var a = b.getMonth();
        var e = b.getFullYear();

        debugger;
        year = e;
        month = a+1;
        var data={year:year,month:month,proid:proid};
        $.ajax({
            url: "/land/logService/getFullLogInfo",
            data: data,
            success: function (r) {
                var result = JSON.parse(r).result;
                var event=[];
                $.each(result,function(i,item){
                    item.start = new Date(item.start);
                    event.push(item);
                });
                $("#calendar").fullCalendar({
                    header: {left: "prev,next", center: "title", right: "month"},
                    editable: true,
                    droppable: true,
                    events: event,
                    eventClick: function (calEvent, jsEvent, view) {
                        debugger;
                        var supervisionLogId = calEvent["supervisionLogId"];
                        var constructLogId = calEvent["constructLogId"];
                        var proid = calEvent["proid"];
                        debugger;
                        if(supervisionLogId!=undefined&&supervisionLogId!=""){
                            layer.open({
                                type: 2,
                                title: '监理日志',
                                shadeClose: true,
                                shade: 0.8,
                                area: ['90%', '95%'],
                                content: '/land/logService/supervisionDetail?proid='+proid+"&supervisionLogId="+supervisionLogId //iframe的url
                            });
                        }
                        if(constructLogId!=undefined&&constructLogId!=""){
                            layer.open({
                                type: 2,
                                title: '施工日志',
                                shadeClose: true,
                                shade: 0.8,
                                area: ['90%', '95%'],
                                content: '/land/logService/constructLogDetail?constructLogId='+ constructLogId//iframe的url
                            });
                        }
                    }
                });
                $('#calendar').find('.fc-button-prev,.fc-button-next').click(function () {

                });
            }
        })

    });

});
