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
    <div style="" id="projectStatic">

    </div>
</div>
<script src="<@s.url'/thirdparty/layui/layui.js'/>" type="text/javascript"></script>
<script>
    layui.config({
        base: '../thirdparty/layui/modules/'
    });
    layui.use(['form', 'element', 'laypage', 'laydate', 'layer', 'laytpl'], function () {
        var form = layui.form(),
                laydate = layui.laydate,
                layer = layui.layer,
                laytpl = layui.laytpl,
                laypage = layui.laypage,
                $ = layui.jquery,
                element = layui.element(); //Tab的切换功能，切换事件监听等，需要依赖element模块


        $(function(){
            $("#projectStatic").height($(window).height()-100);
            $("#projectStatic").width($(window).width()-240);
        })
        // 基于准备好的dom，初始化echarts实例
        var staticChart = echarts.init(document.getElementById('projectStatic'));
        var option = {
            title: {
                text: '项目趋势分析'
            },
            tooltip: {
                trigger: 'axis'
            },
            legend: {

            },
            grid: {
                left: '3%',
                right: '4%',
                bottom: '3%',
                containLabel: true
            },
            toolbox: {
                feature: {
                    saveAsImage: {}
                }
            },
            xAxis: {
                type: 'category',
                boundaryGap: false,
                data: ['2008','2009','2010','2011','2012','2013','2014','2015','2016']
            },
            yAxis: {
                type: 'value'
            },
            series: [
                {
                    name:'项目数量',
                    type:'line',
                    stack: '总量',
                    data:[80, 85, 75, 100, 96, 88, 130,120,105,109,130]
                }
            ]
        };

        // 使用刚指定的配置项和数据显示图表。
        staticChart.setOption(option);
    });

</script>
</body>
</html>