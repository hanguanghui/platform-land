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
    <input type="hidden" value="${proid!}" id="proid"/>

    <div class="material-area">

        <fieldset class="layui-elem-field">
            <div class="layui-field-box">
                <div class="construct-title">
                    <input type="hidden" value="${project.prolocation!}" id="location"/>
                    施&nbsp;&nbsp;工&nbsp;&nbsp;日&nbsp;&nbsp;志
                </div>
                <form id="conLogForm" action="/land/logService/submitConLog" method="post">
                    <div class="construct-day-info">
                        <span id="nowDate"></span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span id="nowDay"></span>
                    </div>

                    <input type="hidden" value="${proid!}" id="proid" name="proid"/>
                    <table class="site-table">
                        <tbody>
                        <tr>
                            <td rowspan="2" style="width:10px">气候</td>
                            <td colspan="9"><input type="text" name="weather" id="weather" lay-verify="weather"
                                                   autocomplete="off" placeholder="气候" class="layui-input" value=""
                                                   lay-verify="required"></td>
                        </tr>
                        <tr>
                            <td>上午</td>
                            <td colspan="2"><input type="text" name="morning" id="morning" lay-verify="morning"
                                                   autocomplete="off" placeholder="上午" class="layui-input" value=""
                                                   lay-verify="required"></td>
                            <td>下午</td>
                            <td colspan="2"><input type="text" name="afternoon" id="afternoon" lay-verify="afternoon"
                                                   autocomplete="off" placeholder="下午" class="layui-input" value=""
                                                   lay-verify="required"></td>
                            <td>夜间</td>
                            <td colspan="2"><input type="text" name="night" id="night" lay-verify="night"
                                                   autocomplete="off" placeholder="夜间" class="layui-input" value=""
                                                   lay-verify="required"></td>
                        </tr>
                        <tr>
                            <td style="height:200px;width:10px">生产情况记录</td>
                            <td colspan="9">
                                <textarea name="produceinfo" id="produceinfo" lay-verify="produceinfo"
                                          placeholder="请输入内容" class="layui-textarea"></textarea>
                            </td>
                        </tr>
                        <tr>
                            <td style="height:200px;width:10px">技术质量安全工作记录</td>
                            <td colspan="9"><textarea name="safetyinfomation" id="safetyinfomation"
                                                      lay-verify="safetyinfomation" placeholder="请输入内容"
                                                      class="layui-textarea"></textarea></td>
                        </tr>
                        <tr>
                            <td colspan="2">工程负责人</td>
                            <td colspan="3"><input type="text" name="chief" id="chief" lay-verify="chief"
                                                   autocomplete="off" placeholder="夜间" class="layui-input" value="1"
                                                   lay-verify="required"></td>
                            <td colspan="2">记录人</td>
                            <td colspan="3"><input type="text" name="notetaker" id="notetaker" lay-verify="notetaker"
                                                   autocomplete="off" placeholder="夜间" class="layui-input" value="1"
                                                   lay-verify="required"></td>
                        </tr>
                        </tbody>
                    </table>
                </form>
                <div class="sum-area">
                    <div class="layui-form-item">
                        <div class="layui-input-block">
                            <button class="layui-btn" type="buton" id="subConBtn">立即提交</button>
                            <button type="reset" class="layui-btn layui-btn-primary">重置</button>
                        </div>
                    </div>
                </div>
        </fieldset>

    </div>
    <script src="<@s.url'/thirdparty/layui/layui.js'/>" type="text/javascript"></script>
    <script>
        layui.config({
            base: '../thirdparty/layui/modules/'
        });
    </script>
    <script src="<@s.url'/js/log/constructLog.js'/>" type="text/javascript"></script>
</body>
</html>