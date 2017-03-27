<#include '../common.ftl'/>
<html>
<head>
    <meta charset="UTF-8">
    <title>日志日历视图</title>
   <#-- <link rel="stylesheet" href="<@s.url '/thirdparty/layui/css/layui.css'/>" media="all"/>
    <link rel="stylesheet" href="<@s.url '/thirdparty/font-awesome/css/font-awesome.min.css'/>" media="all"/>-->
    <link href='<@s.url '/thirdparty/fullcalendar/fullcalendar.css'/>' rel='stylesheet' />
    <link href='<@s.url '/thirdparty/fullcalendar/fullcalendar.print.css'/>' rel='stylesheet'/>
    <link href='<@s.url '/thirdparty/fullcalendar/fullcalendar.style.css'/>' rel='stylesheet'/>
</head>

<body>
<input type="hidden" id="proid" value="${proid}"/>
<div class="" style="width: 900px; margin: 0 auto;" id="calendar">

</div>
<script src="<@s.url'/thirdparty/layui/layui.js'/>" type="text/javascript"></script>
<script src="<@s.url'/thirdparty/jquery/jquery.min.js'/>" type="text/javascript"></script>
<script src='<@s.url'/thirdparty/jquery/jquery-ui.custom.min.js'/>' type="text/javascript"></script>
<script src='<@s.url'/thirdparty/fullcalendar/fullcalendar.min.js'/>' type="text/javascript"></script>
<script>
    layui.config({
        base: '../thirdparty/layui/modules/'
    });
</script>
<script src="<@s.url'/js/log/calendarLog.js'/>" type="text/javascript"></script>
</body>
</html>