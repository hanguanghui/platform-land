/**
 * @author hanguanghui
 * @Description **
 * @version V1.0, 2017/3/3
 * @project platform
 */
var myChart = echarts.init(document.getElementById('timeLine'));
var dataMap = {};
function dataFormatter(obj) {
    var pList = ['琅琊区','南谯区','来安县','定远县','天长市','凤阳县','全椒县','明光市'];
    var temp;
    for (var year = 2009; year <= 2016; year++) {
        var max = 0;
        var sum = 0;
        temp = obj[year];
        for (var i = 0, l = temp.length; i < l; i++) {
            max = Math.max(max, temp[i]);
            sum += temp[i];
            obj[year][i] = {
                name : pList[i],
                value : temp[i]
            }
        }
        obj[year + 'max'] = Math.floor(max / 100) * 100;
        obj[year + 'sum'] = sum;
    }
    return obj;
}

dataMap.dataTI = dataFormatter({
    //max : 25000,
    2016:[400.10,350.56,500.25,480.23,400.23,500.45,380.23,350.23],
    2015:[280.20,430.56,280.25,140.23,580.23,200.23,600.23,150.23],
    2014:[200.23,500.23,200.23,400.23,300.23,200.23,350.23,180.23],
    2013:[200.23,490.23,220.23,380.23,300.23,500.23,290.23,390.23],
    2012:[380.20,330.56,280.25,440.23,280.23,200.23,350.23,250.23],
    2011:[250.23,400.23,600.23,300.23,200.23,280.23,350.23,280.23],
    2010:[400.10,300.56,500.25,480.23,350.23,500.45,480.23,350.23],
    2009:[200.23,400.23,480.23,300.23,380.23,280.23,250.23,480.23]
});


option = {
    title : {
        text: '区县年度投资信息'
    },
    baseOption: {
        timeline: {
            axisType: 'category',
            autoPlay: true,
            playInterval: 1000,
            data: [
                '2009-01-01','2010-01-01','2011-01-01', '2012-01-01','2013-01-01','2014-01-01','2015-01-01','2016-01-01'
            ],
            label: {
                formatter : function(s) {
                    return (new Date(s)).getFullYear();
                }
            }
        },
        title: {
            subtext: '滁州市国土资源局耕地保护科'
        },
        tooltip: {},
        legend: {
            x: 'right',
            data: ['投资信息']
        },
        calculable : true,
        grid: {
            top: 80,
            bottom: 100
        },
        xAxis: [
            {
                'type':'category',
                'axisLabel':{'interval':0},
                'data':[
                    '琅琊区','\n南谯区','来安县','\n定远县','天长市','\n凤阳县','全椒县','\n明光市'
                ],
                splitLine: {show: false}
            }
        ],
        yAxis: [
            {
                type: 'value',
                name: '投资（万元）'
            }
        ],
        series: [
            {name: '投资信息', type: 'bar'}
        ]
    },
    options: [
        {
            title : {text: '2009年度投资信息'},
            series : [
                {data: dataMap.dataTI['2009']}
            ]
        },
        {
            title : {text: '2010年度投资信息'},
            series : [
                {data: dataMap.dataTI['2010']}
            ]
        },
        {
            title : {text: '2011年度投资信息'},
            series : [
                {data: dataMap.dataTI['2011']}
            ]
        },
        {
            title : {text: '2012年度投资信息'},
            series : [

                {data: dataMap.dataTI['2012']}
            ]
        },
        {
            title : {text: '2013年度投资信息'},
            series : [

                {data: dataMap.dataTI['2013']}
            ]
        },
        {
            title : {text: '2014年度投资信息'},
            series : [

                {data: dataMap.dataTI['2014']}
            ]
        },
        {
            title : {text: '2015年度投资信息'},
            series : [

                {data: dataMap.dataTI['2015']}
            ]
        },
        {
            title : {text: '2016年度投资信息'},
            series : [

                {data: dataMap.dataTI['2016']}

            ]
        }
    ]
};

myChart.setOption(option);