/**
 * @author hanguanghui
 * @Description **
 * @version V1.0, 2017/3/16
 * @project platform-land
 */
layui.use(['form', 'element', 'laypage', 'laydate', 'layer', 'laytpl'], function () {
    var form = layui.form(),
        laydate = layui.laydate,
        layer = layui.layer,
        laytpl = layui.laytpl,
        laypage = layui.laypage,
        $ = layui.jquery,
        element = layui.element(); //Tab的切换功能，切换事件监听等，需要依赖element模块

    var photoHander;
    var picSource;

    //获取proid
    var proid = $("#proid").val();

    //初始化 页面展示图片 默认获取当年年份 月份的图片进行展示
    var date = new Date;
    var year = date.getFullYear();
    var month = date.getMonth() + 1;
    var type=["pic","doc,pdf",];
    $.each(type,function(index,item){
        var data={};
        if(item=="pic"){
            data = {proid: proid, year: year, month: month,type:item, page: 1, size: 15};
        }else{
            data = {proid: proid, year: year, month: month,type:item, page: 1, size: 0};
        }
        $.ajax({
            url: "/land/logService/query",
            data: data,
            success: function (r) {
                if(item=="pic"){
                    rendererTpl(r);
                    showPageTool("page", r.totalPages, r.number, data);//使用分页
                }else{
                    debugger;
                    rendererMaList(r);
                }

            }
    })

    });

    //月份改变时间
    form.on('select(month)', function() {
        timeChange();
    });
    form.on('select(year)',function(){
        timeChange();
    });

    /**
     * 时间选择 重新渲染页面
     */
    function timeChange(){
        var year = $("#year").val();
        var month = $("#month").val();
        var data = {proid: proid, year: year, month: month, type:"pic",page: 1, size: 15};
        $.ajax({
            url: "/land/logService/query",
            data: data,
            success: function (r) {
                rendererTpl(r);
                showPageTool("page", r.totalPages, r.number, data);//使用分页
            }
        });
    }



    //播放事件
    $("#autoPlay").off("click");
    $("#autoPlay").on("click",function(){
        debugger;
        var data=[];
        $(picSource).each(function(index,item){
            var x = {
                "alt": item.name,
                "pid": item.id, //图片id
                "src": item.serverpath, //原图地址
                "thumb": item.thumb //缩略图地址
            };
            data.push(x);
        });

        var json = {
            "title": "ss", //相册标题
            "id": 123, //相册id
            "start": 0, //初始显示的图片序号，默认0
            "data": data
        }
        layerPhoto(json);
    });


    $(".img").on("click",function(){
        var data=[];
        $(picSource).each(function(index,item){
            var x = {
                "alt": item.createtime,
                "pid": item.id, //图片id
                "src": item.serverpath, //原图地址
                "thumb": item.thumb //缩略图地址
            };
            data.push(x);
        });
        var json = {
            "title": "ss", //相册标题
            "id": 123, //相册id
            "start": $(this).data('index'), //初始显示的图片序号，默认0
            "data": data
        }
        layerPhoto(json);

    });

    function layerPhoto(json){
        photoHander =layer.photos({
            photos: json
            ,anim: 5 /*,
             success:function(){
             $(".layui-layer-imgnext").trigger("click");
             }*/
        });
    }

    /**
     * 渲染图片列表
     * @param r
     * @param type
     */
    function rendererTpl(r) {
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
            layer.msg("图片获取异常！");
        } else {
            picSource = rp.content;
            var data = {"data": rp.content};
                var getTpl = photoTpl.innerHTML;
                laytpl(getTpl).render(data, function (html) {
                    render(photoLine, html);
                });
        }
    }

    /**
     * 渲染材料列表
     * @param r
     */
    function rendererMaList(r) {
        var rp = {};
        var totalSize = 0;
        if (r.hasOwnProperty("content")) {
            rp.content = r.content;
        } else {
            rp = r;
        }
        if (rp == undefined || rp.content == undefined) {
            layer.msg("材料列表获取异常！");
        } else {
            debugger;
            var urlParams = getUrlParams();

            $.each(rp.content,function(i,item){
                var date=new Date(item.createtime);

                var month = (date.getMonth()+1)<10?"0"+(date.getMonth()+1):(date.getMonth()+1);
                var day =date.getDate()<10?"0"+date.getDate():date.getDate();
                var str = date.getFullYear()+"-"+month+"-"+day;
                var html="<tr id='doc_"+data.id+"'><td>"+item.name+"</td><td>"+str+"</td><td><a href='"+item.serverpath+"' target='_blank' class='layui-btn layui-btn-mini layui-btn-normal' data-id="+data.id+">查看</a>";
                if(urlParams.hasOwnProperty("showUpload")&&urlParams.showUpload=="true"||urlParams.showUpload==true){
                    html+="&nbsp;&nbsp;&nbsp;&nbsp;<a href='javascript:;' class='layui-btn layui-btn-mini layui-btn-danger deleteFile' id='DelSupPicBtn' data-type='doc' data-id="+item.id+" ><i class=\"fa fa-trash\">&nbsp;</i>删除</a></td></tr>"
                }else{
                    html+="</td></tr>";
                }
                $("#logLst").append(html);
            })
        }
    }

    function getUrlParams(){
        var result = {};
        var s = window.location.search;
        var param = s.substr(1);
        if (param == "")return result;
        param = decodeURIComponent(param);
        var arrWithEqual = param.split("&");
        for (var i = 0; i < arrWithEqual.length; i++) {
            var val=arrWithEqual[i];
            var firstIndex=val.indexOf("=");
            result[val.substring(0,firstIndex)] = val.substr(firstIndex+1);
        }
        return result;
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
                debugger;
                if (!first) {
                    data.page = obj.curr;
                    $.ajax({
                        url: "/land/logService/query",
                        data: data,
                        success: function (r) {
                            rendererTpl(r);
                            showPageTool(selector, r.totalPages, r.number, data);
                        }
                    });
                }
            }
        });
    }
    //echarts 图表
// 基于准备好的dom，初始化echarts实例
    var staticChart = echarts.init(document.getElementById('dynamicInfo'));
    function randomData() {
        now = new Date(+now + oneDay);
        value = value + Math.random() * 21 - 10;
        return {
            name: now.toString(),
            value: [
                [now.getFullYear(), now.getMonth() + 1, now.getDate()].join('/'),
                Math.round(value)
            ]
        }
    }

    var data = [];
    var now = +new Date(2016, 9, 3);
    var oneDay = 24 * 3600 * 1000;
    var value = Math.random() * 100;
    for (var i = 0; i < 90; i++) {
        data.push(randomData());
    }

    var option = {
        title: {
            text: '动态情况'
        },
        tooltip: {
            trigger: 'axis',
            formatter: function (params) {
                params = params[0];
                var date = new Date(params.name);
                return date.getDate() + '/' + (date.getMonth() + 1) + '/' + date.getFullYear() + ' : ' + params.value[1];
            },
            axisPointer: {
                animation: false
            }
        },
        xAxis: {
            type: 'time',
            splitLine: {
                show: false
            }
        },
        yAxis: {
            type: 'value',
            boundaryGap: [0, '100%'],
            splitLine: {
                show: false
            }
        },
        series: [{
            name: '模拟数据',
            type: 'line',
            showSymbol: false,
            hoverAnimation: false,
            data: data
        }]
    };

    /**
     * 创建随机字符串
     * @param len
     * @returns {string}
     * @private
     */
    function _getRandomString(len) {
        var $chars = 'ABCDEFGHJKMNPQRSTWXYZabcdefhijkmnprstwxyz2345678'; // 默认去掉了容易混淆的字符oOLl,9gq,Vv,Uu,I1
        var maxPos = $chars.length;
        var pwd = '';
        for (i = 0; i < len; i++) {
            pwd += $chars.charAt(Math.floor(Math.random() * maxPos));
        }
        return pwd;
    }
// 使用刚指定的配置项和数据显示图表。
    staticChart.setOption(option);
})
function render(tbody, html) {
    $(tbody).html(html);
}
