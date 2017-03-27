<#include "common.ftl">
<html>

<head>
    <meta charset="utf-8">
    <title>土地整治项目监管监理平台</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="format-detection" content="telephone=no">

    <link rel="stylesheet" href="<@s.url '/thirdparty/layui/css/layui.css'/>" media="all"/>
    <link rel="stylesheet" href="<@s.url '/css/public/global.css'/>"/>
    <link rel="stylesheet" href="<@s.url '/thirdparty/font-awesome/css/font-awesome.min.css'/>" media="all"/>
</head>

<body>
<div class="layui-layout layui-layout-admin">
    <div class="layui-header header header-demo">
        <div class="layui-main">
            <div class="admin-login-box">
                <a class="logo" style="" href="http://beginner.zhengjinfan.cn/demo/beginner_admin/">
                <#-- <span style="font-size: 22px;">BeginnerAdmin</span>-->
                    <img src="<@s.url '/image/logo-head.png'/>" style="width:201px; height: 40px;">
                </a>

                <div class="admin-side-toggle" style="left:231px">
                    <i class="fa fa-bars" aria-hidden="true"></i>
                </div>
            </div>

            <ul class="layui-nav">
                <li class="layui-nav-item">
                    <a href="javascript:;" class="admin-header-user">
                        <img src="<@s.url '/image/0.jpg'/>" style="width: 40px; height: 40px; border-radius: 100%;">
                        <span>Admin</span>
                    </a>
                    <dl class="layui-nav-child">
                        <dd>
                            <a href="javascript:;"><i class="fa fa-user-circle" aria-hidden="true"></i> 个人信息</a>
                        </dd>
                        <dd>
                            <a href="javascript:;"><i class="fa fa-gear" aria-hidden="true"></i> 设置</a>
                        </dd>
                        <dd>
                            <a href="/land/logOut"><i class="fa fa-sign-out" aria-hidden="true"></i> 注销</a>
                        </dd>
                    </dl>
                </li>
            </ul>
        </div>
    </div>
    <div class="layui-side layui-bg-black" id="admin-side">
        <div class="layui-side-scroll">
            <ul class="layui-nav layui-nav-tree admin-nav-tree">
            <#assign index=0 />
            <#list menuResult as menu>
                <#if menu.parentid == "0">
                    <#if index ==0>
                    <li class="layui-nav-item layui-nav-itemed">
                    <#else>
                    <li class="layui-nav-item">
                    </#if>
                        <a href="javascript:;">${menu.menuname!}</a>
                        <dl class="layui-nav-child">
                            <#list menuResult as menu1>
                                <#if menu1.parentid == menu.id>
                                    <dd>
                                        <#if menu1.type =="1">
                                            <a href="javascript:;" data-url="<@s.url '${menu1.responseurl!}'/>">
                                                <i class="fa ${menu1.icon!}"></i>
                                                <cite>${menu1.menuname!}</cite>
                                            </a>
                                        <#else>
                                            <a href="javascript:;" data-url="${menu1.responseurl!}">
                                                <i class="fa ${menu1.icon}"></i>
                                                <cite>${menu1.menuname!}</cite>
                                            </a>
                                        </#if>
                                    </dd>
                                </#if>
                            </#list>
                        </dl>
                    <#assign index=index+1 />
                    </li>
                </#if>
            </#list>
            </ul>
        </div>
    </div>
    <div class="layui-body" style="bottom: 0;border-left: solid 2px #5FB878;" id="admin-body">
        <div class="layui-tab admin-nav-card layui-tab-brief" lay-filter="admin-tab">
            <ul class="layui-tab-title" id="admin-tab">
                <li class="layui-this">
                    <i class="layui-icon" style="top: 2px; font-size: 16px;">&#xe609;</i>
                    <cite>面板</cite>
                </li>
            </ul>
            <div class="layui-tab-content" style="min-height: 600px; padding: 5px 0 0 0;" id="admin-tab-container">
                <div class="layui-tab-item layui-show">
                    <iframe src="<@s.url '/main'/>" style="height:450px;"></iframe>
                </div>
            </div>
        </div>
    </div>
    <div class="layui-footer footer footer-demo">
        <div class="layui-main">
            <p>2016 &copy;
                <a href="http://beginner.zhengjinfan.cn/demo/beginner_admin/">安徽中遥地理信息科技有限公司</a>
            </p>
        </div>
    </div>
    <div class="site-tree-mobile layui-hide">
        <i class="layui-icon">&#xe602;</i>
    </div>
    <div class="site-mobile-shade"></div>
    <script src="<@s.url'/thirdparty/layui/layui.js'/>" type="text/javascript"></script>
    <script src="<@s.url'/js/index.js'/>" type="text/javascript"></script>
</div>
</body>

</html>