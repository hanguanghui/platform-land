/**
 * @author hanguanghui
 * @Description **
 * @version V1.0, 2017/3/3
 * @project platform
 */

var threeChart = echarts.init(document.getElementById('yearConstruct'));
var thoption = {
    title : {
        text: '年度建设规模'
    },
    tooltip : {
        trigger: 'axis'
    },
    legend: {
        data:['2016年']
    },
    toolbox: {
        show : true,
        feature : {
            mark : {show: true},
            dataView : {show: true, readOnly: false},
            magicType: {show: true, type: ['line', 'bar']},
            restore : {show: true},
            saveAsImage : {show: true}
        }
    },
    calculable : true,
    xAxis : [
        {
            type : 'value',
            boundaryGap : [0, 0.01]
        }
    ],
    yAxis : [
        {
            type : 'category',
            data : ['琅琊区','南谯区','来安县','定远县','全椒县','凤阳县','天长市','明光市']
        }
    ],
    series : [
        {
            name:'2011年',
            type:'bar',
            data:[180, 100, 390,220,300,120,200,160]
        }
    ]
};
threeChart.setOption(thoption);