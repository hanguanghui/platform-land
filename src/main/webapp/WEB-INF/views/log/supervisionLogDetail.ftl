<#include '../common.ftl'/>
<html>
<head>
    <meta charset="UTF-8">
    <title>日志上传页面</title>
    <link rel="stylesheet" href="<@s.url '/thirdparty/layui/css/layui.css'/>" media="all"/>
    <link rel="stylesheet" href="<@s.url '/css/public/global.css'/>"/>
    <link rel="stylesheet" href="<@s.url '/css/public/log.css'/>"/>
    <link rel="stylesheet" href="<@s.url '/thirdparty/font-awesome/css/font-awesome.min.css'/>" media="all"/>
    <link rel="stylesheet" href="<@s.url '/css/public/table.css'/>"/>
    <script src="<@s.url'/thirdparty/jquery/jquery.ajaxfileupload.js'/>" type="text/javascript"></script>
    <script src="<@s.url'/thirdparty/echarts/echarts.min.js'/>" type="text/javascript"></script>
</head>

<body>
<div class="admin-main">
    <div class="material-area">
        <div class="layui-tab layui-tab-brief material-area">
            <ul class="layui-tab-title">
                <li class="layui-this">图片</li>
                <li>监理日志</li>
                <li>材料</li>
                <li>监理统计</li>
            </ul>
            <div class="layui-tab-content">
                <div class="layui-tab-item layui-show">
                    <form class="layui-form">
                        <input type="hidden" value="${proid!}" id="proid"/>
                        <div class="layui-form-item time-select">
                            <div class="layui-input-inline">
                                <select name="year" lay-filter="year" id="year">
                                    <option value="2016">2016</option>
                                    <option value="2017" selected>2017</option>
                                </select>
                            </div>

                            <div class="layui-input-inline">
                                <select name="month" lay-filter="month" id="month">
                                    <option value="1">1</option>
                                    <option value="2">2</option>
                                    <option value="3">3</option>
                                    <option value="4">4</option>
                                    <option value="5">5</option>
                                    <option value="6">6</option>
                                    <option value="7">7</option>
                                    <option value="8">8</option>
                                    <option value="9">9</option>
                                    <option value="10">10</option>
                                    <option value="11">11</option>
                                    <option value="12">12</option>
                                </select>
                            </div>
                            <div class="auto-play">
                                <button type="button" class="layui-btn layui-btn-normal layui-btn-small" id="autoPlay"
                                        title="自动播放"><i class="fa fa-play"></i></button>
                            </div>
                        </div>
                    </form>

                    <div class="photo">
                        <ul class="list-inline" id="photoLine">

                        </ul>
                    </div>
                    <div class="admin-table-page">
                        <div id="page" class="page">
                        </div>
                    </div>
                </div>
                <div class="layui-tab-item ">
                    <fieldset class="layui-elem-field">
                        <legend>监理日志</legend>
                            <div class="layui-field-box">
                                <table class="site-table">
                                    <tbody>
                                    <tr>
                                        <th>工程名称：</th>
                                        <td colspan="3"><input type="text"
                                                               lay-verify="proname" name="proname"
                                                               autocomplete="off" placeholder="工程名称" class="layui-input"
                                                               value="${supervisionLog.proname}"
                                                               lay-verify="required"></td>
                                        <th>施工单位:</th>
                                        <td colspan="2"><input type="text"
                                                               lay-verify="constructName" name="constructname"
                                                               autocomplete="off" placeholder="施工单位" class="layui-input"
                                                               value="${supervisionLog.constructname}"
                                                               lay-verify="required">
                                        </td>
                                        <th>编号:</th>
                                        <td colspan="2"><input type="text"
                                                               lay-verify="identifier" name="identifier"
                                                               autocomplete="off" placeholder="编号" class="layui-input"
                                                               value="${supervisionLog.identifier}"
                                                               lay-verify="required">
                                        </td>
                                    </tr>
                                    <tr>
                                        <th>天气:</th>
                                        <td><input type="text" name="weather" id="weather" lay-verify="weather"
                                                   autocomplete="off" placeholder="天气" class="layui-input" value="${supervisionLog.weather}"
                                                   lay-verify="required">
                                        </td>
                                        <th>气温:</th>
                                        <td><input type="text" name="temperature" id="temperature"
                                                   lay-verify="temperature"
                                                   autocomplete="off" placeholder="气温" class="layui-input" value="${supervisionLog.temperature}"
                                                   lay-verify="required">
                                        </td>
                                        <th>风力:</th>
                                        <td><input type="text" name="power" id="power" lay-verify="power"
                                                   autocomplete="off" placeholder="风力" class="layui-input" value="${supervisionLog.power}"
                                                   lay-verify="required">
                                        </td>
                                        <th>风向:</th>
                                        <td><input type="text" name="direction" id="direction" lay-verify="direction"
                                                   autocomplete="off" placeholder="风向" class="layui-input" value="${supervisionLog.direction}"
                                                   lay-verify="required">
                                        </td>
                                        <th>星期:</th>
                                        <td><input type="text" name="day" id="day" lay-verify="day"
                                                   autocomplete="off" placeholder="编号" class="layui-input" value="${supervisionLog.day}"
                                                   lay-verify="required">
                                        </td>
                                    </tr>
                                    </tbody>
                                </table>
                                <table class="site-table">
                                    <tbody id="infoTipsBody">
                                    <tr>
                                        <th rowspan="12" id="infoTips" style="width:5px">人员、材料、施工设备动态情况</th>
                                        <th colspan="4">施工人员</th>
                                        <th colspan="4">进场材料</th>
                                        <th colspan="4">施工机械</th>
                                    </tr>
                                    <tr>
                                        <th>标段</th>
                                        <th>管理人员</th>
                                        <th >技术人员</th>
                                        <th>其他</th>
                                        <td ></td>
                                        <td></td>
                                        <td ></td>
                                        <td ></td>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                    </tr>
                                    <#list supervisionLst as log>
                                    <tr>
                                        <td>${log_index+1}</td>
                                        <td><input type="text" name="manager" lay-verify="manager"
                                                   autocomplete="off" placeholder="" class="layui-input" value="${log.manager}"
                                                   lay-verify="required"></td>
                                        <td><input type="text" name="artisan" lay-verify="artisan"
                                                   autocomplete="off" placeholder="" class="layui-input" value="${log.artisan}"
                                                   lay-verify="required"></td>
                                        <td><input type="text" name="other" lay-verify="other"
                                                   autocomplete="off" placeholder="" class="layui-input" value="${log.artisan}"
                                                   lay-verify="required"></td>
                                        <td><input type="text" name="materialone" lay-verify="materialone"
                                                   autocomplete="off" placeholder="" class="layui-input"
                                                   value="${log.materialone}"
                                                   lay-verify="required"></td>
                                        <td><input type="text" name="materialtwo" lay-verify="materialtwo"
                                                   autocomplete="off" placeholder="" class="layui-input"
                                                   value="${log.materialtwo}"
                                                   lay-verify="required"></td>
                                        <td><input type="text" name="materiathree" lay-verify="materiathree"
                                                   autocomplete="off" placeholder="" class="layui-input"
                                                   value="${log.materiathree}"
                                                   lay-verify="required"></td>
                                        <td><input type="text" name="materiafour" lay-verify="materiafour"
                                                   autocomplete="off" placeholder="" class="layui-input"
                                                   value="${log.materiafour}"
                                                   lay-verify="required"></td>
                                        <td><input type="text" name="machineone" lay-verify="machineone"
                                                   autocomplete="off" placeholder="" class="layui-input"
                                                   value="${log.machineone}"
                                                   lay-verify="required"></td>
                                        <td><input type="text" name="machinetwo" lay-verify="machinetwo"
                                                   autocomplete="off" placeholder="" class="layui-input"
                                                   value="${log.machinetwo}"
                                                   lay-verify="required"></td>
                                        <td><input type="text" name="machinethree" lay-verify="machinethree"
                                                   autocomplete="off" placeholder="" class="layui-input"
                                                   value="${log.machinethree}"
                                                   lay-verify="required"></td>
                                    </tr>
                                    </#list>
                                    </tbody>
                                    <tbody>
                                    <tr>
                                        <th colspan="2">监理工程师:</th>
                                        <td colspan="4"><input type="text" name="engineer" id="engineer"
                                                               lay-verify="engineer"
                                                               autocomplete="off" placeholder="监理工程师"
                                                               class="layui-input" value="${supervisionLog.engineer}"
                                                               lay-verify="required">
                                        </td>
                                        <th colspan="3">监理总工程师:</th>
                                        <td colspan="5"><input type="text" name="chiefengineer" id="chiefengineer"
                                                               lay-verify="chiefengineer"
                                                               autocomplete="off" placeholder="监理总工程师"
                                                               class="layui-input" value="${supervisionLog.chiefengineer}"
                                                               lay-verify="required">
                                        </td>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                    </fieldset>
                </div>
                <div class="layui-tab-item ">
                    <div class="upload-area">
                        <div class="layui-field-box">
                            <table class="site-table table-hover">
                                <thead>
                                <tr>
                                    <th>材料名称</th>
                                    <th>上传时间</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                <tbody id="logLst">
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
                <div class="layui-tab-item">
                    <div style="width:600px;height: 400px;margin:0 auto" id="dynamicInfo">

                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="<@s.url'/thirdparty/layui/layui.js'/>" type="text/javascript"></script>
<script>
    layui.config({
        base: '../thirdparty/layui/modules/'
    });
</script>
<script src="<@s.url'/js/log/logPublic.js'/>" type="text/javascript"></script>
<script id="photoTpl" type="text/html">
    {{# layui.each(d.data,function(index,item){}}
    <li>
        <div class="history-img-content">
            <img class="img" src="{{item.thumb}}" data-index={{index}} layer-src="{{item.serverpath}}"
                 alt="{{item.name}}" width="160px"
                 height="120px" data-id="{{item.id}}" data-time="{{item.createtime}}"/>

            <div class="buttonInfo">
                {{item.createtime}}
            </div>
        </div>
    </li>
    {{#});}}
</script>

</body>
</html>