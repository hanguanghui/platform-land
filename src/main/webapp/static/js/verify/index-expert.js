layui.use(['icheck', 'laypage', 'layer', 'laytpl', 'jquery', 'element'], function () {
    var $ = layui.jquery,
        laypage = layui.laypage,
        layer = layui.layer,
        laytpl = layui.laytpl,
        element = layui.element(); //Tab的切换功能，切换事件监听等，需要依赖element模块

    var taskstep = $("#taskStep").val();
    var id = $("#userId").val();

    var materialHander;

    if (taskstep == undefined || id == undefined)
        return;
    layer.msg("列表查询中……", {time: 500});
    var where
    if(taskstep>=1){
        where = "state >= " + taskstep + ",relationparentid = '" + id + "',limittime is not null";
    }else{
       where = "state = " + taskstep + ",relationparentid = '" + id + "',limittime is not null";
    }

    var data = {where: where, page: 1, size: 10};

    $.ajax({
        url: "/land/project/queryRelateInfo",
        data: data,
        success: function (r) {
            debugger;
            rendererTpl(r,$("#taskStep").val());
            showPageTool("page", r.totalPages, r.number, data);//使用分页
            validListener();
        }
    });


    function validListener() {
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

        $('.validMaterial').on("click", function () {
            var proid = $(this).data('id');
            $.ajax({
                url:"/land/fileService/getFileList?proid="+proid,
                success:function(data){
                    renderMaterialList(data,proid);
                }
            });
        });
    }
    /**
     * 渲染材料列表
     * @param r
     */
    function renderMaterialList(r,proid){
        var data = {"data": r};

        var getTpl = maViewTpl.innerHTML;
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
                    $("#finish").append("<a href='javascript:;' class='layui-btn layui-btn-normal ' id='agreeBtn' data-id='" + proid + "'>同意通过</a>");
                    $("#finish").append("<a href='javascript:;' class='layui-btn layui-btn-danger ' id='refuseBtn' data-id='" + proid + "'>不同意</a>");
                    listenerInput()
                }
            });
        });
    }

    function listenerInput(){
        $('#agreeBtn').off('click');
        $("#agreeBtn").on("click",function(){
            var proid = $(this).data('id');
            $.ajax({
                url:"/land/AssginmentService/finishExpertAssign?proid="+proid+"&agree=1",
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


