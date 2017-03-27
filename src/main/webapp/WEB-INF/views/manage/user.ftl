<#include "../common.ftl"/>
<html>
<head>
    <link rel="stylesheet" href="<@s.url '/thirdparty/layui/css/layui.css'/>" media="all"/>
    <link rel="stylesheet" href="<@s.url '/css/public/global.css'/>"/>
    <link rel="stylesheet" href="<@s.url '/thirdparty/font-awesome/css/font-awesome.min.css'/>" media="all"/>
    <link rel="stylesheet" href="<@s.url '/css/public/table.css'/>"/>
</head>
<body>
<div class="admin-main">
    <fieldset class="layui-elem-field">
        <legend>用户列表</legend>
        <div style="margin:5px 40px;float:right">
            <a href="javascript:;" data-id="{{ item.id }}" class="layui-btn layui-btn-normal addUser">增加用户</a>
        </div>
        <div class="layui-field-box">
            <table class="site-table table-hover">
                <thead>
                <tr>
                    <th>用户名</th>
                    <th>密码</th>
                    <th>联系电话</th>
                    <th>工作单位</th>
                    <th>角色</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody id="view">

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
        base: '../thirdparty/layui/modules/'
    });
</script>
<script src="<@s.url'/js/manage/user.js'/>" type="text/javascript"></script>

<script id="proListTpl" type="text/html">
    {{#  layui.each(d.data, function(index, item){ }}
    <tr>
        <td>{{ item.name }}</td>
        <td>{{ item.password}}</td>
        <td>{{ item.phone}}</td>
        <td>{{ item.depart }}</td>
        <td>
            {{item.rolename}}
        </td>
        <td>
            <a href="javascript:;" data-id="{{ item.id }}" class="layui-btn layui-btn-normal layui-btn-mini edit">编辑</a>
            <a href="javascript:;" data-id="{{ item.id }}" class="layui-btn  layui-btn-mini Authorize">授权</a>
            <a href="javascript:;" data-id="{{ item.id }}"
               class="layui-btn layui-btn-danger layui-btn-mini delete">删除</a>
        </td>
    </tr>
    {{#  }); }}
    {{#  if(d.data.length === 0){ }}
    无数据
    {{#  } }}
</script>

<script id="addTpl" type="text/html">
    <fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
        <legend>用户信息</legend>
    </fieldset>
    <form class="layui-form" action="">
        <div class="layui-form-item">
            <label class="layui-form-label">用户名：</label>

            <div class="layui-input-inline">
                <input type="text" name="username" lay-verify="required" autocomplete="off" class="layui-input">
            </div>

            <label class="layui-form-label">初始密码:</label>

            <div class="layui-input-inline">
                <input type="input" name="password" lay-verify="password" autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">工作单位:</label>

            <div class="layui-input-inline">
                <input type="input" name="depart" lay-verify="" autocomplete="off" class="layui-input">
            </div>

            <label class="layui-form-label">联系电话:</label>

            <div class="layui-input-inline">
                <input type="tel" name="phone" lay-verify="" autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">用户类型:</label>

            <div class="layui-input-inline">
                <input type="input" name="role" lay-verify="" autocomplete="off" class="layui-input">
           <#--     <select name="type">
                    <option value="0">评审专家</option>
                    <option value="1">申报用户</option>
                    <option value="2">审批用户</option>
                    <option value="3">编制单位</option>
                    <option value="4">施工单位</option>
                    <option value="5">监理单位</option>
                    <option value="6">管理员</option>
                </select>-->
            </div>


            <label class="layui-form-label">权限角色:</label>

            <div class="layui-input-inline">
                <input type="input" name="role" lay-verify="" autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="sum-area">
            <div class="layui-form-item">
                <div class="layui-input-inline">
                    <button class="layui-btn" type="submit">立即提交</button>
                    <button type="reset" class="layui-btn layui-btn-primary">重置</button>
                </div>
            </div>
        </div>

    </form>

</script>
</body>
</html>