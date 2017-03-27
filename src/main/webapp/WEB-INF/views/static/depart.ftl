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
    <div style="width:1000px;height:600px;" id="departStatic">

    </div>
</div>
<script src="<@s.url'/thirdparty/layui/layui.js'/>" type="text/javascript"></script>
<script>
    layui.config({
        base: '../thirdparty/layui/modules/'
    });
    var roseChart = echarts.init(document.getElementById('departStatic'));

    var option = {
        title : {
            text: '办件任务统计',
            x:'center'
        },
        tooltip : {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        legend: {
            x : 'center',
            y : 'bottom',
            data:['曹辉','王秀萍','王卉','朱晓彬','蔡卫东','潘廉','巴竞','马维林']
        },
        toolbox: {
            show : true,
            feature : {
                mark : {show: true},
                dataView : {show: true, readOnly: false},
                magicType : {
                    show: true,
                    type: ['pie', 'funnel']
                },
                restore : {show: true},
                saveAsImage : {show: true}
            }
        },
        calculable : true,
        series : [
            {
                name:'办件数量统计',
                type:'pie',
                radius : [20, 110],
                center : ['25%', '50%'],
                roseType : 'radius',
                label: {
                    normal: {
                        show: false
                    },
                    emphasis: {
                        show: true
                    }
                },
                lableLine: {
                    normal: {
                        show: false
                    },
                    emphasis: {
                        show: true
                    }
                },
                data:[
                    {value:10, name:'曹辉'},
                    {value:5, name:'王秀萍'},
                    {value:15, name:'王卉'},
                    {value:25, name:'朱晓彬'},
                    {value:20, name:'蔡卫东'},
                    {value:35, name:'潘廉'},
                    {value:30, name:'巴竞'},
                    {value:40, name:'马维林'}
                ]
            },
            {
                name:'办件数量统计',
                type:'pie',
                radius : [30, 110],
                center : ['75%', '50%'],
                roseType : 'area',
                data:[
                    {value:10, name:'曹辉'},
                    {value:5, name:'王秀萍'},
                    {value:15, name:'王卉'},
                    {value:25, name:'朱晓彬'},
                    {value:20, name:'蔡卫东'},
                    {value:35, name:'潘廉'},
                    {value:30, name:'巴竞'},
                    {value:40, name:'马维林'}
                ]
            }
        ]
    };
    // 使用刚指定的配置项和数据显示图表。
    roseChart.setOption(option);
</script>
</body>
</html>