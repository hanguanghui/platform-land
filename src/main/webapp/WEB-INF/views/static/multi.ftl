<#include '../common.ftl'/>
<html>
<head>
    <meta charset="UTF-8">
    <title>项目统计</title>
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
    <div style="width:1000px;height:800px;margin:0 auto" id="dynamicStatic" style="width:1000px;height:800px;overflow: auto;margin:0 auto">
        <!--最新年度投资-->
        <div style="height:300px;width:460px;margin:10px;float:left" id="yearInverse">

        </div>
        <!--最新年度项目面积-->
        <div style="height:300px;width:460px;margin:10px;float:left" id="yearConstruct">

        </div>
        <!--分年度地区项目投资趋势-->
        <div style="height:300px;width:460px;margin:10px;float:left" id="areaInverse">

        </div>
        <!--分地区 分年度项目建设规模-->
        <div style="height:300px;width:460px;margin:10px;float:left" id="areaConstruct">

        </div>
    </div>
</div>
<script src="<@s.url'/js/static/areaConstruct.js'/>" type="text/javascript"></script>
<script src="<@s.url'/js/static/areaInverse.js'/>" type="text/javascript"></script>
<script src="<@s.url'/js/static/yearConstruct.js'/>" type="text/javascript"></script>
<script src="<@s.url'/js/static/yearInverse.js'/>" type="text/javascript"></script>
</body>
</html>