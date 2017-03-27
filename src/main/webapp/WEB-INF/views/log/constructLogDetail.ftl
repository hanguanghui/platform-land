<#include '../common.ftl'/>
<html>
<head>
    <meta charset="UTF-8">
    <title>日志上传页面</title>
    <link rel="stylesheet" href="<@s.url '/css/public/global.css'/>"/>
    <link rel="stylesheet" href="<@s.url '/css/public/log.css'/>"/>
    <link rel="stylesheet" href="<@s.url '/thirdparty/layui/css/layui.css'/>" media="all"/>
    <link rel="stylesheet" href="<@s.url '/thirdparty/font-awesome/css/font-awesome.min.css'/>" media="all"/>
    <link rel="stylesheet" href="<@s.url '/css/public/table.css'/>"/>
    <script src="<@s.url'/thirdparty/jquery/jquery.ajaxfileupload.js'/>" type="text/javascript"></script>
    <script src="<@s.url'/thirdparty/echarts/echarts.min.js'/>" type="text/javascript"></script>
</head>

<body>
<div class="admin-main">
    <div class="material-area">

        <fieldset class="layui-elem-field">
            <div class="layui-field-box">
                <div class="construct-title">
                    施&nbsp;&nbsp;工&nbsp;&nbsp;日&nbsp;&nbsp;志
                </div>
                    <div class="construct-day-info">
                        <span id="nowDate">${constructLog.createtime}</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span id="nowDay">星期${constructLog.day}</span>
                    </div>
                    <table class="site-table">
                        <tbody>
                        <tr>
                            <td rowspan="2" style="width:10px">气候</td>
                            <td colspan="9"><input type="text" name="weather" id="weather" lay-verify=""
                                                   autocomplete="off" placeholder="气候" class="layui-input" value="${constructLog.weather}"
                                                   lay-verify="required"></td>
                        </tr>
                        <tr>
                            <td>上午</td>
                            <td colspan="2"><input type="text" name="morning" id="morning" lay-verify="morning"
                                                   autocomplete="off" placeholder="上午" class="layui-input" value="${constructLog.morning}"
                                                   lay-verify="required"></td>
                            <td>下午</td>
                            <td colspan="2"><input type="text" name="afternoon" id="afternoon" lay-verify="afternoon"
                                                   autocomplete="off" placeholder="下午" class="layui-input" value="${constructLog.afternoon}"
                                                   lay-verify="required"></td>
                            <td>夜间</td>
                            <td colspan="2"><input type="text" name="night" id="night" lay-verify=""
                                                   autocomplete="off" placeholder="夜间" class="layui-input" value="${constructLog.night}"
                                                   lay-verify="required"></td>
                        </tr>
                        <tr>
                            <td style="height:200px;width:10px">生产情况记录</td>
                            <td colspan="9">
                                <textarea name="produceinfo" id="produceinfo" lay-verify="produceinfo"
                                          placeholder="请输入内容" class="layui-textarea">${constructLog.produceinfo}</textarea>
                            </td>
                        </tr>
                        <tr>
                            <td style="height:200px;width:10px">技术质量安全工作记录</td>
                            <td colspan="9"><textarea name="safetyinfomation" id="safetyinfomation"
                                                      lay-verify="safetyinfomation" placeholder="请输入内容"
                                                      class="layui-textarea" >${constructLog.safetyinfomation}</textarea></td>
                        </tr>
                        <tr>
                            <td colspan="2">工程负责人</td>
                            <td colspan="3"><input type="text" name="chief" id="chief" lay-verify="chief"
                                                   autocomplete="off" placeholder="夜间" class="layui-input" value="${constructLog.chief}"
                                                   lay-verify="required"></td>
                            <td colspan="2">记录人</td>
                            <td colspan="3"><input type="text" name="notetaker" id="notetaker" lay-verify="notetaker"
                                                   autocomplete="off" placeholder="夜间" class="layui-input" value="${constructLog.notetaker}"
                                                   lay-verify="required"></td>
                        </tr>
                        </tbody>
                    </table>

        </fieldset>

    </div>
    <script src="<@s.url'/thirdparty/layui/layui.js'/>" type="text/javascript"></script>
    <script>
        layui.config({
            base: '../thirdparty/layui/modules/'
        });
    </script>
</body>
</html>