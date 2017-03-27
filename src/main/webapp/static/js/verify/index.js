layui.use(['icheck', 'laypage', 'layer', 'laytpl', 'jquery', 'element'], function () {
    var $ = layui.jquery,
        laypage = layui.laypage,
        layer = layui.layer,
        laytpl = layui.laytpl,
        element = layui.element(); //Tab的切换功能，切换事件监听等，需要依赖element模块

    //define
    var rewordHander,loadHander,materialHander;


    $('input').iCheck({
        checkboxClass: 'icheckbox_flat-green'
    });

    var taskstep = $("#taskStep").val();
    var id = $("#userId").val();
    if (taskstep == undefined || id == undefined)
        return;
    layer.msg("列表查询中……", {time: 500});
    var where = "state = " + taskstep + ",relationparentid = '" + id + "'"+",limittime is not null";
    var data = {where: where, page: 1, size: 10};
    $.ajax({
        url: "/land/project/queryRelateInfo",
        data: data,
        success: function (r) {
            debugger;
            rendererTpl(r,$("#taskStep").val());
            showPageTool("page", r.totalPages, r.number, data);//使用分页
            uploadListener();
        }
    });

    function uploadListener() {
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


        $('.uploadMaterial').on("click", function () {
            var proid = $(this).data('id');
            // get file list by proid
            $.ajax({
                url:"/land/fileService/getFileList?proid="+proid,
                success:function(data){
                    renderMaterialList(data,proid);
                }
            });
        });
    }


    function listenerFileInput() {

        $('.upload').off('change');
        $('.upload').on('change', function () {
            loadHander = layer.load(0, {shade: false});
            importFile($(this), $(this).data('id'));
            timer = setTimeout(function () {
                listenerFileInput();
            }, 2000);
        });

        //办结任务事件
        $('#finishBtn').off('click');
        $("#finishBtn").on("click",function(){
            var proid = $(this).data('id');
            $.ajax({
                url:"/land/AssginmentService/finishCompanyAssign?proid="+proid,
                dataType:"json",
                success:function(d){
                    debugger;
                    if(d.success==true){
                        layer.close(materialHander);
                        layer.msg("任务已办结",{icon:1});
                        document.location.reload();
                    }
                }
            });
        });
    }



    /**
     * 文件上传方法
     * @param file
     */
    function importFile(file,proId) {
        var fileVal = file.val();
        if (fileVal == "")return;
        //获取文件后缀
        var tmp = fileVal.split('.');
        var suffix = tmp[tmp.length - 1];
        suffix = suffix.toLowerCase();

        if (suffix != "doc" && suffix != "pdf" && suffix != "docx" && suffix != "xlsx") {
            layer.msg("上传文件格式错误！", {icon: 2});
            return;
        }
        //文件上传
        $.ajaxFileUpload({
            fileElementId: proId,
            url: '/land/fileService/upload/' + proId,
            dataType:'text/html',
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            secureuri: false,
            success: function (d) {
                var data = $.parseJSON($.parseJSON(d).result);
                layer.close(loadHander);
                $("#maLst").append("<tr  id='"+data.fileid+"'><td><a href='.."+data.pdfpath+"' target='_blank'>"+data.filename+"</a></td><td>"+changeDatetoString(data.createtime)+"</td><td><a href='javascript:;'onclick=\"deleteFiles('"+data.fileid+"')\" class='layui-btn layui-btn-mini layui-btn-danger deleteFile' data-id='"+data.fileid+"'>删除</a></td></tr>")
                layer.msg("上传成功！", {icon: 1,time :2000});
            }
        });

    }


    function changeDatetoString(stamp){
        var date=new Date(stamp);
        var str=date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate();

        return str;
    }

    /**
     * 渲染材料列表
     * @param r
     */
    function renderMaterialList(r,proid){
        var data = {"data": r};

        var getTpl = uploadTpl.innerHTML;
        laytpl(getTpl).render(data, function (html) {
            materialHander = layer.open({
                type: 1,
                title: '材料列表',
                skin: 'layui-layer-molv',
                fix: false,
                shadeClose: true,
                maxmin: true,
                area: ['600px', '450px'],
                content: html,
                success: function () {
                    $("#uploadArea").append("<input type='file' name='file' class='file-input upload' data-id='"+proid+"' id='"+proid+"' accept='.doc,.docx'>");
                    $("#finish").append("<a href='javascript:;' class='layui-btn layui-btn-normal ' id='finishBtn' data-id='"+proid+"'><i class=\"fa fa-check-square\"></i>&nbsp;&nbsp;办结</a>");
                    listenerFileInput();
                }
            });
        });
    }
    /**
     * 渲染分页结果
     * @param r
     */
    function rendererTpl(r,step) {
        debugger;
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
            var data = {"data": rp.content,"step":step};
            var getTpl = proListTpl.innerHTML;
            laytpl(getTpl).render(data, function (html) {
                render(view, html);
            });
        }
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
    $('.site-table tbody tr').on('click', function (event) {
        var $this = $(this);
        var $input = $this.children('td').eq(0).find('input');
        $input.on('ifChecked', function (e) {
            $this.css('background-color', '#EEEEEE');
        });
        $input.on('ifUnchecked', function (e) {
            $this.removeAttr('style');
        });
        $input.iCheck('toggle');
    }).find('input').each(function () {
        var $this = $(this);
        $this.on('ifChecked', function (e) {
            $this.parents('tr').css('background-color', '#EEEEEE');
        });
        $this.on('ifUnchecked', function (e) {
            $this.parents('tr').removeAttr('style');
        });
    });
    $('#selected-all').on('ifChanged', function (event) {
        var $input = $('.site-table tbody tr td').find('input');
        $input.iCheck(event.currentTarget.checked ? 'check' : 'uncheck');
    });
});

function render(tbody, html) {
    $(tbody).html(html);
}

function deleteFiles(o){
    $.ajax({
        url:"/land/fileService/deleteFile?fileid="+o,
        success:function(r){
            if(r.success){
                layer.msg("删除成功！",{icon:1});
                $("#"+o).remove();
            }else
                layer.msg("网络异常！",{icon:2});
        },
        error:function(){
            layer.msg("网络异常！",{icon:2});
        }
    });
}