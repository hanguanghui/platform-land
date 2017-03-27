layui.use(['form', 'element', 'laydate', 'layer', 'laytpl'], function () {
    var form = layui.form(),
        laydate = layui.laydate;
        layer = layui.layer,
        laytpl = layui.laytpl;
    var $ = layui.jquery,
        element = layui.element(); //Tab的切换功能，切换事件监听等，需要依赖element模块

    var companyHandler, expertHandler;

    var geojson={};

    //触发事件
    var active = {
        tabAdd: function () {
            //新增一个Tab项
            element.tabAdd('demo', {
                title: '新选项' + (Math.random() * 1000 | 0),
                content: '内容' + (Math.random() * 1000 | 0)
            })
        },
        tabDelete: function () {
            //删除指定Tab项
            element.tabDelete('demo', 2); //删除第3项（注意序号是从0开始计算）
        },
        tabChange: function () {
            //切换到指定Tab项
            element.tabChange('demo', 1); //切换到第2项（注意序号是从0开始计算）
        }
    };
    $('.site-demo-active').on('click', function () {
        var type = $(this).data('type');
        active[type] ? active[type].call(this) : '';
    });
    $("#expertAdd").on("click", function () {
        var where = "1=1";
        $.ajax({
            url: "/land/expert/rest/list",
            data: {where: where},
            success: function (r) {
                var getTpl = expertTpl.innerHTML;
                laytpl(getTpl).render(r, function (html) {
                    expertHandler = layer.open({
                        title: '专家列表',
                        type: 1,
                        skin: 'layui-layer-molv',
                        fix: false,
                        shadeClose: true,
                        maxmin: true,
                        area: ['600px', '450px'],
                        content: html,//这里content是一个DOM,
                        success: function () {
                            $(".expert-sel-btn").on("click", function () {
                                var id = $(this).data('id');
                                var expertName = $(this).data('name');
                                var profession = $(this).data('profession');
                                var depart = $(this).data('depart');
                                var randomId = _getRandomString(16);
                                var x = "<tr id="+randomId+">" +
                                    "<input type='hidden' name='expertid' value='"+id+"'>"+
                                    "<td><input type='text' name='expertname'  lay-verify='required' value='"+expertName+"' autocomplete='off' class='layui-input input-expert' lay-verify='required'></td>"+
                                    "<td><input type='text' name='profession' i lay-verify='required'  value='"+profession+"' autocomplete='off' class='layui-input' lay-verify='required'></td>"+
                                    "<td><input type='text' name='depart' lay-verify='required' value='"+depart+"'autocomplete='off' class='layui-input' lay-verify='required'></td>"+
                                    "<td><button  class='fa fa-times btn-info btn'  type='button' style='font-size: 18px;color: #FF5722' onclick=\"remove('"+randomId+"')\"></button></td><tr>"+
                                    "</tr>";
                                $("#exportView").append(x);
                                layer.close(expertHandler);
                            });
                            $("#btnExpertCommit").on("click", function () {
                                var id = _getRandomString(32);
                                var expertName = $("#inputName").val();
                                var profession = $("#inputPro").val();
                                var depart = $("#inputDepart").val();
                                var randomId = _getRandomString(16);
                                var x = "<tr id="+randomId+">" +
                                    "<input type='hidden' name='expertid' value='"+id+"'>"+
                                    "<td><input type='text' name='expertname'  lay-verify='required' value='"+expertName+"' autocomplete='off' class='layui-input input-expert' lay-verify='required'></td>"+
                                    "<td><input type='text' name='profession' i lay-verify='required'  value='"+profession+"' autocomplete='off' class='layui-input' lay-verify='required'></td>"+
                                    "<td><input type='text' name='depart' lay-verify='required' value='"+depart+"'autocomplete='off' class='layui-input' lay-verify='required'></td>"+
                                    "<td><button  class='fa fa-times btn-info btn'  type='button' style='font-size: 18px;color: #FF5722' onclick=\"remove('"+randomId+"')\"></button></td><tr>"+
                                    "</tr>";
                                $("#exportView").append(x);
                                layer.close(expertHandler);
                            });
                        }
                    });
                });
            }
        });
    });


    $('.upload').off('change');
    $('.upload').on('change', function (event) {
        //event.stopPropagation();
        var type = $("#ShapeType").val();
        if(type==""||type==null){
            layer.msg("请选择文件上传类型！",{icon:3});
            return;
        }
        importFile($(this),$(this).data("id"),type);
    });

    function importFile(f, proId,type) {
        var fileVal = f.val();
        if(fileVal=="")return;
        var tmp = fileVal.split('.');
        var suffix = tmp[tmp.length - 1];
        var fileName = (tmp[0].split("\\"))[tmp[0].split("\\").length - 1];
        suffix = suffix.toLowerCase();
        if(suffix != type){
            layer.msg("上传文件类型错误！",{icon:2});
            return;
        }
        switch(suffix){
            case "zip":{
                $.ajaxFileUpload({
                    fileElementId: f[0].id,
                    url: "/land/fileService/zip/upload",
                    type: 'post',
                    dataType: 'json',
                    success: function(d){
                        debugger;
                        geojson = $.parseJSON(d.value);
                        $.each(geojson.features,function(index,item){
                            var coords = item.geometry.coordinates;
                            $.each(coords,function(indexc,itemc){
                                debugger;
                                var ptx,pty;
                                $.each(itemc,function(i,it){
                                    var polygonIndex = index+1;
                                    var ringIndex = indexc+1;
                                    var ptIndex= i+1;
                                    var html = "<tr><td>"+polygonIndex+"</td><td>"+ringIndex+"</td><td>"+ptIndex+"</td><td>"+it[0]+"</td><td>"+it[1];

                                    ptx = it[0];
                                    pty = it[1];
                                    html+="</td><td></td></tr>";

                                    $("#shape-detail").append(html);
                                });
                            });
                        });
                    }
                });
                break;
            }
            case "dwg":{
                break;
            }

        }



    }

    function getLength(ptx,pty,pt1x,pt1y){
        var xdiff = pt1x - ptx;
        var ydiff = pt1y - pty;
        return  Math.pow((xdiff * xdiff + ydiff * ydiff), 0.5);
    }

    $("#uploadArea").on("click",function(){
        $("#uploadShape").trigger("click");
    })
    /**
     * 图形上传
     */
    $("#shapeUpload").on("click",function(){
        debugger;
        if(geojson=={}){
            layer.msg("未选择任何图形！",{icon:3})
            return;
        }

        $.each(geojson.features,function(index,item) {
            var coords = item.geometry.coordinates;
            item.properties.DKID = $("#dkid").val();
            item.properties.PROID = $("#proid").val();
        });
        var url = "http://127.0.0.1:8088/map/geometryService/rest/insert?geometry="+JSON.stringify(geojson)+"&layerName=ttt";
        $.getJSON("http://127.0.0.1:8088/map/geometryService/rest/insert", {geometry: JSON.stringify(geojson), layerName: 'BPDK_341100'}, function (data) {
            debugger;
            layer.msg("图形上传成功！");
        })
       /* $.getJSON("/land/proxy?requestUrl="+url,function(data){
            debugger;
        });*/
    });


    /**
     * 编制单位选择触发事件
     */
    $("#bCompany").on("click", function () {
        var companyName = $("#bCompany").val();
        var where = "companytype = 'b'";
        $.ajax({
            url: "/land/company/rest/list",
            data: {where: where},
            async: false,
            success: function (r) {
                debugger;
                var getTpl = companyLstTpl.innerHTML;
                laytpl(getTpl).render(r, function (html) {
                    companyHandler = layer.open({
                        title: '公司列表',
                        type: 1,
                        skin: 'layui-layer-molv',
                        fix: false,
                        shadeClose: true,
                        maxmin: true,
                        area: ['600px', '450px'],
                        content: html,//这里content是一个DOM,
                        success: function (layero, index) {
                            $(".company-sel-btn").on("click", function () {
                                $("#bCompany").val($(this).data('companyname'));
                                $("#bCompanyid").val($(this).data('id'));
                                layer.close(companyHandler);
                            });
                            $("#btnCompanyCommit").on("click", function () {
                                var companyName = $("#inpbc").val();
                                var companyId = _getRandomString(32);
                                $("#bCompany").val(companyName);
                                $("#bCompanyid").val(companyId);
                                layer.close(companyHandler);
                            });
                        }
                    });
                });
            }
        });
    });


    /**
     * 专家选择触发事件
     */
    $(".input-expert").on("click", function () {
        var where = "1=1";
        $.ajax({
            url: "/land/expert/rest/list",
            data: {where: where},
            success: function (r) {
                var getTpl = expertTpl.innerHTML;
                laytpl(getTpl).render(r, function (html) {
                    expertHandler = layer.open({
                        title: '专家列表',
                        type: 1,
                        skin: 'layui-layer-molv',
                        fix: false,
                        shadeClose: true,
                        maxmin: true,
                        area: ['600px', '450px'],
                        content: html,//这里content是一个DOM,
                        success: function () {
                            $(".expert-sel-btn").on("click", function () {
                                $("#expertid").val($(this).data('id'));
                                $("#expertName").val($(this).data('name'));
                                $("#expertPro").val($(this).data('profession'));
                                $("#expertDepart").val($(this).data('depart'));
                                layer.close(expertHandler);
                            });
                            $("#btnExpertCommit").on("click", function () {
                                $("#expertid").val(_getRandomString(32));
                                $("#expertName").val($("#inputName").val());
                                $("#expertPro").val($("#inputPro").val());
                                $("#expertDepart").val($("#inputDepart").val());
                                layer.close(expertHandler);
                            });
                        }
                    });
                });
            }
        });
    });

});


function remove(o) {
    $("#" + o).remove();
}

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