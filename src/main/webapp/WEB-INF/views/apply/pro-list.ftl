<#include '../common.ftl'/>
<html>
<head>
    <meta charset="UTF-8">
    <title>Table</title>
    <link rel="stylesheet" href="<@s.url '/thirdparty/layui/css/layui.css'/>" media="all"/>
    <link rel="stylesheet" href="<@s.url '/css/public/global.css'/>"/>
    <link rel="stylesheet" href="<@s.url '/css/apply.css'/>" media="all"/>
    <link rel="stylesheet" href="<@s.url '/thirdparty/font-awesome/css/font-awesome.min.css'/>" media="all"/>
    <link rel="stylesheet" href="<@s.url '/css/public/table.css'/>"/>
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
<script src="<@s.url'/thirdparty/fcharts/fcharts.js'/>" type="text/javascript"></script>
<script>
    layui.config({
        base: '../thirdparty/layui/modules/'
    });
</script>
<script src="<@s.url'/js/apply.js'/>" type="text/javascript"></script>
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
            {{# if(item.changed==0) {}}
                未变更
            {{# } }}
            {{# if(item.changed==1){}}
               已变更
            {{# } }}
        </td>
        <td style="color: red">
            {{# if(item.prostep==0) {}}
            待转发…
            {{#  } }}
            {{# if(item.prostep==1) {}}
            编制单位处理中…
            {{#  } }}
            {{# if(item.prostep==2) {}}
            专家评审中…
            {{#  } }}
            {{# if(item.prostep==3) {}}
            专家评审不通过
            {{#  } }}
            {{# if(item.prostep==4) {}}
            专家评审通过
            {{#  } }}
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
            {{# if(item.prostep==0) {}}
            <a href="javascript:;" data-id="{{ item.proid }}" class="layui-btn layui-btn-normal layui-btn-mini reword"><i class="fa fa-external-link-square"></i>&nbsp;&nbsp;转发</a>
            <a href="javascript:;" data-id="{{ item.proid }}" class="layui-btn layui-btn-mini edit"><i class="fa fa-edit"></i>&nbsp;&nbsp;编辑</a>
            <a href="javascript:;" data-id="{{ item.proid }}" class="layui-btn layui-btn-danger layui-btn-mini delete"><i class="fa fa-remove"></i>&nbsp;&nbsp;删除</a>

            {{#  } }}
            {{#  if(item.prostep == 1){ }}
            {{#  } }}
            {{# if(item.prostep==4) {}}
            <a href="javascript:;" data-id="{{ item.proid }}" class="layui-btn layui-btn-mini proreport">上报</a>
            {{#  } }}
            {{# if(item.prostep==5) {}}
            {{#  } }}
            {{# if(item.prostep==9) {}}+
            <a href="javascript:;" data-id="{{ item.proid }}" class="layui-btn layui-btn-mini proAppointCompany">分派监理、施工单位</a>
            {{#  } }}
            {{#  if(item.prostep == 11){ }}
            <a href="javascript:;" data-id="{{ item.proid }}" class="layui-btn layui-btn-mini layui-btn-normal supervisorLog"><i class="fa fa-info"></i>&nbsp;&nbsp;监理信息</a>
            <a href="javascript:;" data-id="{{ item.proid }}" class="layui-btn layui-btn-mini prochange"><i class="fa fa-exchange"></i>&nbsp;&nbsp;变更</a>
            {{#  } }}
        </td>
    </tr>
    {{#  }); }}
    {{#  if(d.data.length === 0){ }}
    无数据
    {{#  } }}
</script>

<script type="text/html" id="rewordTpl">
        <div class="layui-form-item limit-day">
            <label class="layui-form-label">限制天数</label>
            <div class="layui-input-block">
                <input type="number" id="limitDay" required="" lay-verify="required" placeholder="请输入任务限制时间"
                       autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-input-block">
                <button class="layui-btn" type="submit" id="rewordCofirm">提交</button>
                <button type="reset" class="layui-btn layui-btn-primary">重置</button>
            </div>
        </div>
</script>


<script id="companyLstTpl" type="text/html">
    <div clas="pub_company" id="companyList">
        <div class="layui-field-box">
            <div class="layui-form-item" >
                <label class="layui-form-label">施工单位:</label>
                <div class="layui-input-block" id ="construction" style="border: 1px solid #eaeaea">
                </div>
            </div>
            <div class="layui-form-item" id ="companySure">
                <label class="layui-form-label">监理单位:</label>
                <div class="layui-input-block" id ="supervisor" style="border: 1px solid #eaeaea">
                </div>
            </div>

                <div class="layui-tab layui-tab-brief">
                    <ul class="layui-tab-title">
                        <li class="layui-this">施工单位</li>
                        <li>监理单位</li>
                    </ul>
                    <div class="layui-tab-content">
                        <div class="layui-tab-item layui-show">
                            <table class="sitenone-table table-hover">
                                <tbody>
                                    {{# layui.each(d.s, function(index, item){ }}
                                    <tr>
                                        <td style="font-weight:700">{{ item.key }}:</td>
                                        <td>
                                            {{# layui.each(item.value, function(ind, it){ }}
                                            <a href="javascript:void(0)" class="layui-btn layui-btn-mini company-sel-btn construction-btn" data-id="{{it.companyid}}" data-companyname="{{ it.companyname}}">{{ it.companyname}}</a>
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
                                {{# layui.each(d.j, function(index, item){ }}
                                <tr>
                                    <td style="font-weight:700">{{ item.key }}:</td>
                                    <td>
                                        {{# layui.each(item.value, function(ind, it){ }}
                                        <a href="javascript:void(0)" class="layui-btn layui-btn-mini company-sel-btn supervisor-btn" data-id="{{it.companyid}}" data-companyname="{{ it.companyname}}">{{ it.companyname}}</a>
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