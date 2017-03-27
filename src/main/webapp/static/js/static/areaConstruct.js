/**
 * @author hanguanghui
 * @Description **
 * @version V1.0, 2017/3/3
 * @project platform
 */

var fourChart = echarts.init(document.getElementById('areaConstruct'));
var option = {
    title: {
        text: '建设规模'
    },
    tooltip: {
        trigger: 'axis',
        axisPointer: { // 坐标轴指示器，坐标轴触发有效
            type: 'shadow' // 默认为直线，可选为：'line' | 'shadow'
        }
    },
    legend: {
        data: ['琅琊区', '南谯区', '来安县', '全椒县'],
        align: 'right',
        right: 10
    },
    grid: {
        left: '8%',
        right: '4%',
        bottom: '3%',
        containLabel: true
    },
    xAxis: [{
        type: 'category',
        data: [ '2013', '2014', '2015', '2016']
    }],
    yAxis: [{
        type: 'value',
        left:'5%',
        axisLabel: {
            formatter: '{value}'
        }
    }],
    series: [{
        name: '琅琊区',
        type: 'bar',
        data: [8, 5, 10, 14]
    }, {
        name: '南谯区',
        type: 'bar',
        data: [5, 15, 5, 9]
    }, {
        name: '来安县',
        type: 'bar',
        data: [10, 8, 15, 18]
    }, {
        name: '全椒县',
        type: 'bar',
        data: [5, 12, 8, 10]
    }]
};
fourChart.setOption(option);