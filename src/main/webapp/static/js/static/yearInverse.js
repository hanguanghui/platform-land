/**
 * @author hanguanghui
 * @Description **
 * @version V1.0, 2017/3/3
 * @project platform
 */
var testOChart = echarts.init(document.getElementById('yearInverse'));

var option = {
    title: {
        text: '年度投资'
    },
    color: ['#3398DB'],
    tooltip : {
        trigger: 'axis',
        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
        }
    },
    grid: {
        left: '3%',
        right: '4%',
        bottom: '3%',
        containLabel: true
    },
    xAxis : [
        {
            type : 'category',
            data : ['琅琊区', '南谯区', '来安县', '全椒县', '定远县', '凤阳县', '天长市','明光市'],
            axisTick: {
                alignWithLabel: true
            }
        }
    ],
    yAxis : [
        {
            type : 'value'
        }
    ],
    series : [
        {
            name:'投资信息',
            type:'bar',
            barWidth: '60%',
            data:[100, 52, 150, 334, 190, 330, 250,150]
        }
    ]
};
// 使用刚指定的配置项和数据显示图表。
testOChart.setOption(option);