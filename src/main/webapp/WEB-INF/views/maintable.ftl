<#include 'common.ftl'/>
<html>
<head>
    <meta charset="UTF-8">
    <title>Table</title>
    <link rel="stylesheet" href="<@s.url '/thirdparty/layui/css/layui.css'/>" media="all"/>
    <link rel="stylesheet" href="<@s.url '/css/public/global.css'/>"/>
    <link rel="stylesheet" href="<@s.url '/thirdparty/font-awesome/css/font-awesome.min.css'/>" media="all"/>
    <link rel="stylesheet" href="<@s.url '/css/public/table.css'/>"/>
</head>

<body>
<div class="admin-main">
    <fieldset class="layui-elem-field">
        <legend>数据列表</legend>
        <div class="layui-field-box">
            <table class="site-table table-hover">
                <thead>
                <tr>
                    <th><input type="checkbox" id="selected-all"></th>
                    <th>所属分类</th>
                    <th>标题</th>
                    <th>作者</th>
                    <th>创建时间</th>
                    <th>浏览量</th>
                    <th>状态</th>
                    <th>排序值</th>
                    <th>置顶</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td><input type="checkbox"></td>
                    <td>Layui</td>
                    <td>
                        <a href="/manage/article_edit_1">演示站点发布成功啦。</a>
                    </td>
                    <td>Beginner</td>
                    <td>2016-11-16 11:50</td>
                    <td>1298</td>
                    <td>正常</td>
                    <td>99</td>
                    <td style="text-align:center;"><i class="layui-icon" style="color:green;"></i></td>
                    <td>
                        <a href="/detail-1" target="_blank" class="layui-btn layui-btn-normal layui-btn-mini">预览</a>
                        <a href="/manage/article_edit_1" class="layui-btn layui-btn-mini">编辑</a>
                        <a href="javascript:;" data-id="1" data-opt="del"
                           class="layui-btn layui-btn-danger layui-btn-mini">删除</a>
                    </td>
                </tr>
                <tr>
                    <td><input type="checkbox"></td>
                    <td>Layui</td>
                    <td>
                        <a href="/manage/article_edit_19">神5飞天震寰宇</a>
                    </td>
                    <td>就是我</td>
                    <td>2016-11-18 10:18</td>
                    <td>74</td>
                    <td>正常</td>
                    <td>0</td>
                    <td style="text-align:center;"><i class="layui-icon" style="color:green;"></i></td>
                    <td>
                        <a href="/detail-19" target="_blank">预览</a>
                        <a href="/manage/article_edit_19">编辑</a>
                        <a href="javascript:;" data-id="19" data-opt="del">删除</a>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
    </fieldset>
    <div class="admin-table-page">
        <div id="page" class="page">
        </div>
    </div>
</div>
<script src="<@s.url'/thirdparty/layui/layui.js'/>" type="text/javascript"></script>
<script>
    layui.config({
        base: 'thirdparty/layui/modules/'
    });

    layui.use(['icheck', 'laypage', 'layer'], function () {
        var $ = layui.jquery,
                laypage = layui.laypage,
                layer = layui.layer;
        $('input').iCheck({
            checkboxClass: 'icheckbox_flat-green'
        });

        //page
        laypage({
            cont: 'page',
            pages: 25 //总页数
            ,
            groups: 5 //连续显示分页数
            ,
            jump: function (obj, first) {
                //得到了当前页，用于向服务端请求对应数据
                var curr = obj.curr;
                if (!first) {
                    //layer.msg('第 '+ obj.curr +' 页');
                }
            }
        });

        $('#search').on('click', function () {
            parent.layer.alert('你点击了搜索按钮')
        });

        layer.open({
            type: 1,
            title: "任务提示",
            closeBtn: 1, //不显示关闭按钮
            shade: [0],
            area: ['340px', '215px'],
            offset: 'rb', //右下角弹出
            time: 5000, //5秒后自动关闭
            anim: 2,
            content: "您有未办结项目，请及时处理！", //iframe的url，no代表不显示滚动条
            end: function(){ //此处用于演示

            }
        });


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
</script>
</body>

</html>