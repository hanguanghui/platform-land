
var index = 1;

layui.use(['form', 'element', 'laypage', 'laydate', 'layer', 'laytpl'], function () {
    var form = layui.form(),
        laydate = layui.laydate,
        layer = layui.layer,
        laytpl = layui.laytpl,
        laypage = layui.laypage,
        $ = layui.jquery,
        element = layui.element(); //Tab的切换功能，切换事件监听等，需要依赖element模块

    //监听上传事件
    var loadHander;

    //获取proid
    var proid = $("#proid").val();

    //上传事件
    $('.upload').off('change');
    $('.upload').on('change', function () {
        importFile($(this), $(this).data('id'));
        timer = setTimeout(function () {
        }, 2000);
    });

    listener();

    /**
     * 监理日志信息 提交
     */
    $("#subSuperBtn").off("click");
    $("#subSuperBtn").on("click",function(event){
        event.stopPropagation();
        var url = $("#superLogForm").attr("action");
        var data = $('#superLogForm').serialize();
        $.post(url,data,function(data){
            layer.msg("提交成功！",{icon:1});
            $('#superLogForm')[0].reset();
            layer.closeAll();
        });
    })



    /**
     * 监理日志详细信息增加
     */
    $("#addDetailBtn").on("click",function(){
        if(index>=10){
            layer.msg("已达上限！")
            return;
        }
        index++;
        var randomId = _getRandomString(16);
        var html="<tr id="+randomId+"><td>"+index+"</td>"+$("#tipsInfoTpl").html()+"<td><button type=\"button\" class=\"layui-btn layui-btn-danger layui-btn-small\" id=\"deleteDetailBtn\" title=\"删除\"onclick=\"removeTag('"+randomId+"')\"><i class=\"fa fa-trash\"></i></button></td></tr>";
        $("#infoTipsBody").append(html);
    });

    //上传图片按钮触发事件
    $(".uploadArea").on("click",function(){
        listener();
        $('.upload').trigger("click");
    });

    function listener() {
        $("#DelSupPicBtn").on("click",function(){
            var id = $(this).data("id");
            var type= $(this).data("type");
            $.ajax({
                url:"/land/logService/delete",
                data:{logId:id},
                success:function(data){
                    var result = JSON.parse(data).result;
                    if(result.hasOwnProperty("success")&&result.success==true){
                        if(type=="pic"){
                            $("#uploadSuperLog").removeClass("hidden");
                            $(".history-img-content").remove();
                        }else if(type=="doc"){
                            $("#doc_"+id).remove();
                        }
                        layer.msg("删除成功！");

                    }else{
                        layer.msg("请求失败！");
                    }

                }
            })
        });

        //上传图片input改变触发事件
        $('.upload').off('change');
        $('.upload').on('change', function () {
            importFile($(this), $(this).data('id'));
            timer = setTimeout(function () {
            }, 2000);
        });
    }

    /**
     * 文件上传方法
     * @param file
     */
    function importFile(file, proId) {
        var fileVal = file.val();
        if (fileVal == "")return;
        //获取文件后缀
        var tmp = fileVal.split('.');
        var suffix = tmp[tmp.length - 1];
        suffix = suffix.toLowerCase();

        if (suffix != "doc" && suffix != "docx" && suffix != "jpg" && suffix != "png" && suffix != "gif"&&suffix!="pdf") {
            layer.msg("上传文件格式错误！", {icon: 2});
            return;
        }

        loadHander = layer.load(0, {shade: false});

        var type = "";
        if (suffix == "doc" || suffix == "docx") {
            type = "doc";
        } else if(suffix=="jpg"||suffix=="png"||suffix=="gif"){
            type = "pic";
        }else if(type=="pdf"){
            type = "pdf";
        }
        //文件上传
        $.ajaxFileUpload({
            fileElementId: proId,
            url: '/land/logService/upload',
            data: {proid: proId, type: type},
            dataType: 'text/html',
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            secureuri: false,
            success: function (d) {
                var data = $.parseJSON($.parseJSON(d).result);
                var date=new Date(data.createtime);

                var month = (date.getMonth()+1)<10?"0"+(date.getMonth()+1):(date.getMonth()+1);
                var day =date.getDate()<10?"0"+date.getDate():date.getDate();
                var str = date.getFullYear()+"-"+month+"-"+day;
                if(type=="pic"){
                    var html="<div class='history-img-content'><img src=" + data.serverpath + " alt=" + data.name + " width='500px' height='320px' data-id=" + data.id + " data-time=" + date + "/><div style=\"margin-top: 20px\"><a href=\"javascript:void(0);\" data-id=" + data.id + " data-type='pic' id='DelSupPicBtn' class=\"layui-btn layui-btn-danger\"><i class=\"fa fa-trash\">&nbsp;</i>删除</a></div></div>" +
                        "<input type=\"hidden\" value='" + data.serverpath + "' name='picturepath'>";
                    $("#logPic").append(html);
                    $("#uploadSuperLog").addClass("hidden");
                }
                if(type=="pdf"||type=="doc"){
                    $("#logLst").append("<tr id='doc_"+data.id+"'><td>"+data.name+"</td><td>"+str+"</td><td><a href='"+data.serverpath+"' target='_blank' class='layui-btn layui-btn-mini layui-btn-normal' data-id="+data.id+">查看</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href='javascript:;' class='layui-btn layui-btn-mini layui-btn-danger deleteFile' id='DelSupPicBtn' data-type='doc' data-id="+data.id+" ><i class=\"fa fa-trash\">&nbsp;</i>删除</a></td></tr>")
                }

                layer.close(loadHander);
                listener();
            }
        });
        /**
         * 删除日志详细
         */

    }




    $(function(){
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
        $.getJSON("/land/utilService/getWeather?city="+city+"&code="+code,function(data){
            var weather = data.result ;
            $("#weather").val(weather.weather);
            $("#temperature").val(weather.temperature);
            $("#power").val(weather.power);
            $("#direction").val(weather.direction);
            $("#day").val(weather.day);
        })
    })
});

function render(tbody, html) {
    $(tbody).html(html);
}


function removeTag(id){
    index--;
    $("#"+id).remove();
}


