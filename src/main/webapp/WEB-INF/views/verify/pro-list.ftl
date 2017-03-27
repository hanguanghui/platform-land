<#include '../common.ftl'/>
<html>
<head>
    <meta charset="UTF-8">
    <title>Table</title>
    <link rel="stylesheet" href="<@s.url '/thirdparty/layui/css/layui.css'/>" media="all"/>
    <link rel="stylesheet" href="<@s.url '/css/public/global.css'/>"/>
    <link rel="stylesheet" href="<@s.url '/thirdparty/font-awesome/css/font-awesome.min.css'/>" media="all"/>
    <link rel="stylesheet" href="<@s.url '/css/public/table.css'/>"/>
    <script src="<@s.url'/thirdparty/jquery/jquery.ajaxfileupload.js'/>" type="text/javascript"></script>
</head>

<body>
<div class="admin-main">



    <input type="hidden" value="${taskstep}" id="taskStep"/>
    <input type="hidden" value="${user.id}" id="userId"/>

    <fieldset class="layui-elem-field">
        <legend>项目列表</legend>
        <div class="layui-field-box">
            <table class="site-table table-hover">
                <thead>
                <tr>
                    <th><input type="checkbox" id="selected-all"></th>
                    <th>项目类型</th>
                    <th>项目名称</th>
                    <th>所在地</th>
                    <th>创建时间</th>
                    <th>项目总投资</th>
                    <th>建设总规模</th>
                    <th>是否变更</th>
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
<script src="<@s.url'/js/verify/index.js'/>" type="text/javascript"></script>
<script id="proListTpl" type="text/html">
    {{#  layui.each(d.data, function(index, item){ }}
    <tr>
        <td><input type="checkbox"></td>
        <td>{{ item.protype }}</td>
        <td>{{ item.proname }}</td>
        <td>{{ item.prolocation }}</td>
        <td>{{ item.createtime }}</td>
        <td>{{ item.prototalinvest }}</td>
        <td>{{ item.prototalscale }}</td>
        <td>
            {{#  if(item.changed == 0){ }}
            未变更
            {{#  } }}
            {{#  if(item.changed == 1){ }}
            已变更
            {{#  } }}
        </td>
        <td>
            <a href="javascript:;" data-id="{{ item.proid }}" class="layui-btn layui-btn-normal layui-btn-mini proview"><i class="fa fa-list-alt"></i>&nbsp;&nbsp;查看详细</a>
            {{#  if(item.prostep == 1){ }}
                <a href="javascript:;" data-id="{{ item.proid }}" class="layui-btn layui-btn-mini uploadMaterial"><i class="fa fa-upload"></i>&nbsp;&nbsp;上传资料</a>
            {{#  } }}
        </td>
    </tr>
    {{#  }); }}
    {{#  if(d.data.length === 0){ }}
    无数据
    {{#  } }}
</script>

<script id="uploadTpl" type="text/html">
        <div class="layui-field-box">
            <a href="javascript:void(0);"  class="layui-btn layui-btn-mini file" id="uploadArea"><i class="fa fa-upload">&nbsp;</i>上传资料</a>
            <table class="site-table table-hover">
                <thead>
                <tr>
                    <th>材料名称</th>
                    <th>上传时间</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody id="maLst">
                {{#  layui.each(d.data, function(index, item){ }}
                    <tr id="{{item.fileid}}">
                        <td><a href="<@s.url '{{item.pdfpath}}'/>" target="_blank">{{item.filename}}</a></td>
                        <td>{{item.createtime}}</td>
                        <td><a href='javascript:;' class='layui-btn layui-btn-mini layui-btn-danger deleteFile' data-id="{{item.fileid}}" onclick="deleteFiles('{{item.fileid}}')" >删除</a></td>
                    </tr>
                {{#  }); }}
                </tbody>
            </table>
            <div id="finish" style="margin-left: 41%">

            </div>
        </div>
</script>

</body>
</html>