layui.use(['icheck', 'laypage','layer','laytpl','jquery','element'], function() {
    var $ = layui.jquery,
        laypage = layui.laypage,
        layer = layui.layer,
        laytpl = layui.laytpl,
        element = layui.element(); //Tab的切换功能，切换事件监听等，需要依赖element模块

    //define
    var rewordHander,appointHander,companyHandler;

    var selExpert =[];


    $('input').iCheck({
        checkboxClass: 'icheckbox_flat-green'
    });


    var taskstep = $("#taskStep").val();
    var userid = $("#userId").val();
    var type = $("#usertype").val();
    if(taskstep==undefined||userid==undefined)
        return;
    layer.msg("列表查询中……",{time: 500});
    debugger;
    var where;
    //市局角色
    if(type==2){
        where = "assignmentdetailstep >= 5";
    }
    else if(taskstep==0){
        where = "assignmentdetailstep = " + taskstep + ",userid = '" + userid + "'";
    }else if(taskstep==1){
        where = "assignmentdetailstep >= " + taskstep + ",assignmentdetailstep <= 4,userid = '" + userid + "'";
    }else if(taskstep ==5){
        where = "assignmentdetailstep >= " + taskstep + ",userid = '" + userid + "'";
    }
    var data = {where: where, page: 1, size: 10};
    $.ajax({
        url: "/land/project/query",
        data: data,
        success: function (r) {
            debugger;
            rendererTpl(r, $("#taskStep").val());
            showPageTool("page", r.totalPages, r.number, data);//使用分页
        }
    });

    /**
     * 渲染分页结果
     * @param r
     */
    function rendererTpl(r,step) {
        var rp = {};
        var totalSize = 0;
        if (r.hasOwnProperty("content")) {
            rp.content = r.content;
            totalSize = r.totalElements;
        } else {
            rp = r;
            totalSize = rp.content.length;
        }
        if (rp == undefined || rp.content == undefined) {
            layer.msg("项目列表获取异常！");
        } else {
            var data = {"data":rp.content,"step":step};
           var getTpl = proListTpl.innerHTML;
           laytpl(getTpl).render(data, function(html){
               render(view,html);

               btnlistener();
           });
        }
    }

    function btnlistener(){
        $(".proview").off("click");
        $(".proview").on("click",function(){
            var proid = $(this).data('id');
            var url = "/land/project/proDetail?proid="+proid;
            layer.open({
                type: 2,
                title: '项目详细',
                shadeClose: true,
                shade: 0.8,
                area: ['600px', '450px'],
                content:  url  //iframe的url
            });
        });

        $(".location").off("click");
        $(".location").on("click",function(){
            debugger;
            var DKID = $(this).data("id");
            var params = {"params":{"layerAlias":"BPDK","where":"DKID='"+DKID+"'"},"type":"layerLocation"};
            var locationUrl = "http://127.0.0.1:8088/map/mapService/tpl_test?action=location&hideLeftPanel=false&params="+JSON.stringify(params)+"&hideTopBar=true";
            window.open(locationUrl);
        });
        //转发编辑删除等事件监听
        $(".reword").off("click");
        $(".reword").on("click",function(){
            debugger;
            var id=$(this).data('id');
            var html = rewordTpl.innerHTML;
            html+="<input type='hidden' id='reowrdPorId' value='"+id+"'>";
            rewordHander = layer.open({
                type: 1,
                title: '转发',
                skin: 'layui-layer-molv',
                fix: false,
                shadeClose: true,
                maxmin: true,
                area: ['400px', '200px'],
                content:html,
                success:function(){
                    $("#rewordCofirm").on("click", function () {
                        var id = $("#reowrdPorId").val();
                        var limitDay = $("#limitDay").val();
                        $.ajax({
                            url:"/land/project/reword",
                            data:{proid:id,limitDay:limitDay,type:"company"},
                            success: function (r) {
                                debugger;
                                layer.close(rewordHander);
                                if(r.success==true){
                                    //userid
                                    layer.msg("转发成功！");
                                    document.location.reload();
                                }else{
                                    layer.msg("网络异常，转发失败！");
                                }
                            }
                        });
                    });
                }
            });
        });

        //县国土局上报
        $(".proreport").off("click");
        $(".proreport").on("click",function(){
            var proid = $(this).data('id');
            $.ajax({
                url:"/land/project/report?proid="+proid,
                method:"post",
                success:function(r){
                    if(r.success==true){
                        //userid
                        layer.msg("转发成功！");
                        document.location.reload();
                    }else{
                        layer.msg("网络异常，转发失败！");
                    }
                }
            });
        });

        //市国土局分配专家
        $(".proAppoint").off("click");
        $(".proAppoint").on("click",function(){
            debugger;
            var proid = $(this).data('id');

            var where = "1=1";
            $.ajax({
                url: "/land/expert/rest/list",
                data: {where: where},
                success: function (r) {
                    var getTpl = appointTpl.innerHTML;
                    laytpl(getTpl).render(r, function (html) {
                        appointHander = layer.open({
                            title: '专家列表',
                            type: 1,
                            skin: 'layui-layer-molv',
                            fix: false,
                            shadeClose: true,
                            maxmin: true,
                            area: ['600px', '450px'],
                            content: html,//这里content是一个DOM,
                            success: function () {

                                $("#selExpertHead").append(" <a href='javascript:;' class='layui-btn layui-btn-normal 'style='float:right;margin-top:10px;' id='finishBtn' data-id='"+proid+"'>确定</a>");

                                $(".expert-sel-btn").off("click");
                                $(".expert-sel-btn").on("click",function(){
                                    this.remove();
                                    var expertName = $(this).data("name");
                                    var expertId = $(this).data("id");
                                    if(selExpert.indexOf(expertId)>-1){
                                        layer.msg("请勿重复选择!",{icon:2,time:1000});
                                    }else{
                                        var x =" <a href='javascript:void(0)' class='layui-btn layui-btn-mini selected-expert' data-id='"+expertId+"'>"+expertName+"<span class='fa fa-remove' href='##'  style='color:red;margin-left:5px;'></span></a>";
                                        $("#loadExpert").append(x);
                                        selExpert.push(expertId);
                                        removeListener();
                                    }
                                });
                                $("#finishBtn").off("click");
                                $("#finishBtn").on("click",function(){
                                    var exp =selExpert.join(",");
                                    var proid = $(this).data('id');
                                    var limit = $("#limitTime").val();
                                    $.ajax({
                                        url:"/land/project/cityReword",
                                        data:{experts:exp,proid:proid,limitTime:limit},
                                        method:"POST",
                                        dataType:"json",
                                        success: function (r) {
                                            if(r.success==true){
                                                layer.msg("转发成功！");
                                                document.location.reload();
                                            }else{
                                                layer.msg("网络异常，转发失败！");
                                            }
                                        }
                                    });
                                });
                            }
                        });
                    });
                }
            });
        });

        //市国土局通过下发
        $(".proApproval").off("click");
        $(".proApproval").on("click",function(){
            var proid = $(this).data('id');
            $.ajax({
                url:"/land/project/reword",
                data:{proid:proid,type:"report"},
                method:"post",
                success: function (r) {
                    if(r.success==true){
                        layer.msg("下达成功！",{icon:1,time:2000});
                        document.location.reload();
                    }else{
                        layer.msg("网络异常，下达失败！");
                    }
                }
            });
        });

        //县国土局 分派施工监理单位
        $(".proAppointCompany").on("click",function(){
            var proid=$(this).data("id");
            appointCompany(proid);
        });

        $(".supervisorLog").off("click");
        $(".supervisorLog").on("click",function(){
            var proid = $(this).data("id");
            //layer.msg(proid);
            //iframe层
            layer.open({
                type: 2,
                title: '日志材料',
                shadeClose: true,
                shade: 0.8,
                area: ['100%', '100%'],
                content: '/land/logService/calendarLog?proid='+proid //iframe的url
            });
        })

        $(".proflow").off("click");
        $(".proflow").on("click",function(){

            var json =[{"children":[{"starttime":"2017/03/17","operator":"a","endtime":"","msg":"","text":"立项申请","state":"normal"},{"starttime":"2017/03/17","msg":"","operator":"南京国图","endtime":"","text":"编制处理","state":"normal"},{"starttime":"2017/03/17","children":[{"children":[],"starttime":"7BD3DA845A6C433B88F454FB44B9CD78","operator":"巴竞","msg":"fsddsgdf","endtime":"2017/03/14","text":"巴竞","state":"normal"},{"children":[],"starttime":"7BD3DA845A6C433B88F454FB44B9CD78","operator":"曹辉","endtime":"","msg":"","text":"曹辉","state":"warn"},{"children":[],"starttime":"2017/03/17","operator":"李小军","msg":"fsddsgdf","endtime":"","text":"李小军","state":"warn"}],"operator":"","msg":"这倒是","endtime":"","text":"项目审批","state":"normal"}],"id":"1209E3AFE3134DE5BA6171BA57E06DDC","msg":"asa实打实","operator":"asdafsd","starttime":"2017/03/17","text":"申请阶段","state":"normal"},{"children":[],"id":"1209E3AFE3134DE5BA6171BA57E06DDC","msg":"asa实打实","operator":"asdafsd","starttime":"2017/03/17","text":"施工阶段","state":"warn"},{"children":[],"id":"1209E3AFE3134DE5BA6171BA57E06DDC","msg":"asa实打实","operator":"asdafsd","starttime":"2017/03/17","text":"验收阶段","state":"undefine"}];
            debugger;
            var html = $.fn.fcharts.initChart(json).innerHTML;
            layer.open({
                type: 1,
                area: ['100%', '100%'],
                content: html //注意，如果str是object，那么需要字符拼接。
            });
        });
    }
    function appointCompany(proid){
        //施工单位 监理单位
        var where = "companytype = 's';companytype ='j'";
        $.ajax({
            url: "/land/company/rest/list1",
            data: {where: where},
            async: false,
            success: function (r) {
                var getTpl = companyLstTpl.innerHTML;
                laytpl(getTpl).render(r, function (html) {
                    companyHandler =  layer.open({
                        title: '公司列表',
                        type: 1,
                        skin: 'layui-layer-molv',
                        fix: false,
                        shadeClose: true,
                        maxmin: true,
                        area: ['600px', '450px'],
                        content: html,//这里content是一个DOM,
                        success: function (layero, index) {

                            $("#companySure").append(" <a href='javascript:;' class='layui-btn layui-btn-normal 'style='float:right;margin-top:10px;' id='companySureBtn' data-id='"+proid+"'>确定</a>");

                            $(".construction-btn").off("click");
                            $(".construction-btn").on("click",function(){
                                $("#construction").empty();
                                $("#construction").append("<a disabled='' class='layui-btn layui-btn-mini  company-sel-btn' id='constructSel' data-id='"+$(this).data("id")+"' data-companyname='"+$(this).data("companyname")+"'>"+$(this).data("companyname")+"</a>");
                                layer.msg("施工单位选择完成！",{icon:1,time:1000});
                            });
                            $(".supervisor-btn").off("click");
                            $(".supervisor-btn").on("click",function(){
                                $("#supervisor").empty();
                                $("#supervisor").append("<a disabled='' class='layui-btn layui-btn-mini  company-sel-btn' id='supervisorSel' data-id='"+$(this).data("id")+"' data-companyname='"+$(this).data("companyname")+"'>"+$(this).data("companyname")+"</a>");
                                layer.msg("监理单位选择完成！",{icon:1,time:1000});
                            });
                            
                            $("#companySureBtn").off("click");
                            $("#companySureBtn").on("click", function () {
                                debugger;
                                var construct = $("#constructSel").data("id");
                                var supervisor = $("#supervisorSel").data("id");
                                var proid = $(this).data("id");
                                $.ajax({
                                    url:"/land/project/approve",
                                    data:{proid:proid,constructId:construct,supervisorId:supervisor},
                                    method:"post",
                                    success:function(r){
                                        if(r.success==true){
                                            layer.msg("分派成功！",{icon:1,time:2000});
                                            document.location.reload();
                                        }else{
                                            layer.msg("网络异常，分派失败！");
                                        }
                                    }
                                });
                            });
                        }
                    });
                });
            }
        });
    }

    function removeListener(){
        $(".selected-expert").off("click");
        $(".selected-expert").on("click",function(){
            var proid = $(this).data('id');
            selExpert.splice($.inArray(proid,selExpert),1);
            this.remove();
            layer.msg("移除成功！",{icon:1,time:1000});
        });
    }


    /**
     * 显示分页工具
     * @param selector
     * @param pageCount
     * @param currPage
     * @param callback
     */
    function showPageTool(selector, pageCount, currPage, data) {
        if (typeof selector === "string")
            selector = $("#" + selector);
        selector.show();
        laypage({
            cont: selector,
            pages: pageCount,
            curr: currPage,
            skip: true, //是否开启跳页
            skin: 'molv', //皮肤
            jump: function (obj, first) {
                if (!first) {
                    data.page = obj.curr;
                    $.ajax({
                        url: "/land/project/query",
                        data: data,
                        success: function (r) {
                            rendererTpl(r);
                            showPageTool("pageTool", r.totalPages, r.number, data);
                        }
                    });
                }
            }
        });
    }
});

function render(tbody,html){
    $(tbody).html(html);
}