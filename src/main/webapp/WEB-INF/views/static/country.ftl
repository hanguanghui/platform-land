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
    <div style="" id="countryStatic">

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
            $("#countryStatic").height($(window).height()-100);
            $("#countryStatic").width($(window).width()-240);
        })
        // 基于准备好的dom，初始化echarts实例
        var staticChart = echarts.init(document.getElementById('countryStatic'));
        var option = {
            backgroundColor: '#FFFFFF',

            title: {
                text: '区县项目统计',
                left: 'center',
                top: 20,
                textStyle: {
                    color: '#000'
                }
            },

            tooltip : {
                trigger: 'item',
                formatter: "{a} <br/>{b} : {c} ({d}%)"
            },

            visualMap: {
                show: false,
                min: 80,
                max: 600,
                inRange: {
                    colorLightness: [0, 1]
                }
            },
            series : [
                {
                    name:'区县项目统计',
                    type:'pie',
                    radius : '55%',
                    center: ['50%', '50%'],
                    data:[
                        {value:335, name:'来安县'},
                        {value:310, name:'凤阳县'},
                        {value:274, name:'琅琊区'},
                        {value:235, name:'南谯区'},
                        {value:300, name:'滁州港'},
                        {value:200, name:'定远县'},
                        {value:220, name:'全椒县'}
                    ].sort(function (a, b) { return a.value - b.value}),
                    roseType: 'angle',
                    label: {
                        normal: {
                            textStyle: {
                                color: 'rgba(0, 0, 0, 1)'
                            }
                        }
                    },
                    labelLine: {
                        normal: {
                            lineStyle: {
                                color: 'rgba(0, 0, 0, 1)'
                            },
                            smooth: 0.2,
                            length: 10,
                            length2: 20
                        }
                    },
                    itemStyle: {
                        normal: {
                            color: '#c23531',
                            shadowBlur: 0,
                            shadowColor: 'rgba(0, 0, 0, 0.5)'
                        }
                    },

                    animationType: 'scale',
                    animationEasing: 'elasticOut',
                    animationDelay: function (idx) {
                        return Math.random() * 200;
                    }
                }
            ]
        };
        // 使用刚指定的配置项和数据显示图表。
        staticChart.setOption(option);
    });

</script>
</body>
</html>