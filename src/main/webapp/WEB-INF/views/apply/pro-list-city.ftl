<#include '../common.ftl'/>
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
    <input type="hidden" value="${taskstep}" id="taskStep"/>
    <input type="hidden" value="${user.id}" id="userId"/>
    <input type="hidden" value="${user.type}" id="usertype"/>

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
                    <th>状态</th>
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
<script src="<@s.url'/js/apply.js'/>" type="text/javascript"></script>
<script src="<@s.url'/thirdparty/fcharts/fcharts.js'/>" type="text/javascript"></script>

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
        <td style="color: red">
            {{# if(item.prostep==5) {}}
            市局审核中
            {{#  } }}
            {{# if(item.prostep==6) {}}
            二审专家审核中
            {{#  } }}
            {{# if(item.prostep==7) {}}
            二审专家不通过
            {{#  } }}
            {{# if(item.prostep==8) {}}
            二审专家审核完成
            {{#  } }}
            {{# if(item.prostep==9) {}}
            项目已下达
            {{#  } }}
            {{# if(item.prostep==10) {}}
            项目待开工
            {{#  } }}
            {{# if(item.prostep==11) {}}
            项目施工中
            {{#  } }}
        </td>
        <td>
            <a href="javascript:;" data-id="{{ item.proid }}" class="layui-btn layui-btn-normal layui-btn-mini proview"><i class="fa fa-list-alt"></i>&nbsp;&nbsp;查看</a>
            <a href="javascript:;" data-id="{{ item.dkid }}" class="layui-btn layui-btn-mini layui-btn-warm location"><i class="fa fa-location-arrow"></i>&nbsp;&nbsp;定位</a>
            <a href="javascript:;" data-id="{{ item.proid }}" class="layui-btn layui-btn-mini proflow"><i class="fa fa-stack-overflow"></i>&nbsp;&nbsp;流程</a>

            {{# if(item.prostep==5) {}}
            <a href="javascript:;" data-id="{{ item.proid }}" class="layui-btn layui-btn-mini proAppoint"<i class="fa fa-indent"></i>&nbsp;&nbsp;>分派专家</a>
            {{#  } }}
            {{# if(item.prostep==6) {}}
            {{#  } }}
            {{# if(item.prostep==7) {}}
            <a href="javascript:;" data-id="{{ item.proid }}" class="layui-btn layui-btn-mini proTurnBack"><i class="fa fa-mail-reply"></i>&nbsp;&nbsp;转回</a>
            {{#  } }}
            {{# if(item.prostep==8) {}}
            <a href="javascript:;" data-id="{{ item.proid }}" class="layui-btn layui-btn-mini proApproval"><i class="fa fa-level-down"></i>&nbsp;&nbsp;下发</a>
            {{#  } }}
            {{#  if(item.prostep == 11){ }}
            <a href="javascript:;" data-id="{{ item.proid }}" class="layui-btn layui-btn-mini  layui-btn-normal supervisorLog"><i class="fa fa-info"></i>&nbsp;&nbsp;监理信息</a>
            {{#  } }}
        </td>
    </tr>
    {{#  }); }}
    {{#  if(d.data.length === 0){ }}
    无数据
    {{#  } }}
</script>

<script type="text/html" id="appointTpl">
    <div clas="pub_company" id="companyList">
        <div class="layui-field-box">
            <div class="layui-form-item" >
                <label class="layui-form-label">指派专家:</label>
                <div class="layui-input-block" id ="loadExpert" style="border: 1px solid #eaeaea">
                </div>
            </div>
            <div class="layui-form-item" id="selExpertHead">
                <label class="layui-form-label">限制时间:</label>
                <div class="layui-input-block">
                    <input type="number" name="limitTime" lay-verify="limitTime" id="limitTime"
                           autocomplete="off" placeholder="请输入限制办结时间" class="layui-input" value="1"
                           lay-verify="required">
                </div>
            </div>
            <div class="layui-tab layui-tab-brief">
                <ul class="layui-tab-title">
                    <li class="layui-this">姓氏</li>
                    <li>部门</li>
                </ul>
                <div class="layui-tab-content">
                    <div class="layui-tab-item layui-show">
                        <table class="sitenone-table table-hover">
                            <tbody>
                            {{# layui.each(d.name, function(index, item){ }}
                            <tr>
                                <td style="font-weight:700">{{ item.key }}:</td>
                                <td>
                                    {{# layui.each(item.value, function(ind, it){ }}
                                    <a href="javascript:void(0)" class="layui-btn layui-btn-mini expert-sel-btn" data-id="{{it.expertid}}" data-profession="{{it.profession}}"
                                       data-depart="{{it.depart}}" data-name="{{ it.expertname}}">{{ it.expertname}}</a>
                                    {{# }); }}
                                </td>
                            </tr>
                            {{# }); }}
                            </tbody>
                        </table>
                    </div>
                    <div class="layui-tab-item">
                        <table class="sitenone-table table-hover">
                            <tbody>
                            {{# layui.each(d.depart, function(index, item){ }}
                            <tr>
                                <td style="font-weight:700">{{ item.key }}:</td>
                                <td>
                                    {{# layui.each(item.value, function(ind, it){ }}
                                    <a href="javascript:void(0)" class="layui-btn layui-btn-mini expert-sel-btn" data-id="{{it.expertid}}" data-profession="{{it.profession}}"
                                       data-depart="{{it.depart}}" data-name="{{ it.expertname}}">{{ it.expertname}}</a>
                                    {{# }); }}
                                </td>
                            </tr>
                            {{# }); }}
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</script>

</body>
</html>