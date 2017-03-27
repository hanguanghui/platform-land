layui.use(['icheck', 'laypage','layer','laytpl','jquery','element'], function() {
    var $ = layui.jquery,
        laypage = layui.laypage,
        layer = layui.layer,
        laytpl = layui.laytpl,
        element = layui.element(); //Tab的切换功能，切换事件监听等，需要依赖element模块

    var roleInfo;

    //获取role 角色信息
    $.ajax({
        url:"/land/user/roleInfo",
        dataType:"json",
        success: function (r) {
            roleInfo = r;
        }
    });

    //请求用户列表信息
    var data = {where: "1=1", page: 1, size: 10};
    $.ajax({
        url:"/land/user/query",
        data:data,
        success: function (r) {
            rendererTpl(r,roleInfo);
            showPageTool("page", r.totalPages, r.number, data,roleInfo);//使用分页
        }
    });

    $(".addUser").off("click");
    $(".addUser").on("click",function(){
        debugger;
        var html = addTpl.innerHTML;
        layer.open({
            type: 1,
            title: '新增用户',
            shadeClose: true,
            shade: 0.8,
            area: ['650px', '450px'],
            content:  html  //iframe的url
        });
    });

    /**
     * 渲染分页结果
     * @param r
     */
    function rendererTpl(r,roleInfo) {
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
            layer.msg("用户列表获取异常！");
        } else {
            var content = rp.content;
            $(content).each(function(index,obj){
                $(roleInfo).each(function (ind,o) {
                        if (o.rolecode == obj.role)
                            obj.rolename = o.rolename;
                    }
                );
            });

            var data = {"data": content};
            var getTpl = proListTpl.innerHTML;
            laytpl(getTpl).render(data, function (html) {
                render(view, html);
                $(".edit").off("click");
                $(".edit").on("click",function(){
                    var id = $(this).data("id");
                    layer.msg(id);
                });
                $(".Authorize").off("click");
                $(".Authorize").on("click",function(){
                    var id = $(this).data("id");
                    layer.msg(id);
                });

                $(".delete").off("click");
                $(".delete").on("click",function(){
                    var id = $(this).data("id");
                    layer.msg(id);
                });
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
    function showPageTool(selector, pageCount, currPage, data,roleinfo) {
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
                        url: "/land/user/query",
                        data: data,
                        success: function (r) {
                            rendererTpl(r,roleinfo);
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