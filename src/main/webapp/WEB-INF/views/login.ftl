<#include "common.ftl">
<html>
<head>
    <title>登陆系统</title>
    <link rel="stylesheet" type="text/css" href="<@s.url '/thirdparty/bootstrap/css/bootstrap-v3.css'/>"/>
    <link rel="stylesheet" type="text/css" href="<@s.url '/thirdparty/font-awesome/css/font-awesome.css'/>"/>
    <link rel="stylesheet" type="text/css" href="<@s.url '/css/login.css'/>"/>
    <script>
        $(function () {
            debugger;
            var nBodyHeight = 730;
            var nClientHeight = $(window).height();
            if (nClientHeight >= nBodyHeight + 2) {
                var nDis = (nClientHeight - nBodyHeight) / 2;
                document.body.style.paddingTop = nDis / 2 + 'px';
                $(".login-body").css("height", nClientHeight - nDis - 65 - 64);
            } else {
                document.body.style.paddingTop = '0px';
                $(".login-body").css("height", nClientHeight - 65 - 64);
            }
        });

    </script>
</head>

<body>
<header class="header">
    <div class="headerLogo">

    </div>
</header>
<div class="main-container">
    <div class="login-body">
        <div class="theme">
            <form action="user/index" method="post">
                <div id="loginBlock" class="login tab-1">
                    <div class="loginFunc">
                        <div id="lbNormal" class="loginFuncNormal">帐号登录</div>
                    </div>
                    <div class="form-body">
                    <#if error??>
                        <div style="color:red;font-weight: 700">${error}</div>
                    </#if>
                        <div class="input-box">
                            <div class="u-logo">
                                <i class="fa fa-user i-logo" aria-hidden="true"></i>
                            </div>
                            <div class="u-input">
                                <input type="text" name="name" class="u-text-input" placeholder="输入用户名..."
                                       value="a">
                            </div>
                        </div>

                        <div class="input-box margin-clear">
                            <div class="u-logo">
                                <i class="fa fa-unlock-alt i-logo" aria-hidden="true"></i>
                            </div>
                            <div class="u-input">
                                <input type="password" name="password" class="u-text-input" placeholder="输入密码..." value="1">
                            </div>
                        </div>

                        <div class="submit-button">
                            <button type="submit" class="btn-c btn-submit btn-color">登&nbsp;录</button>
                            <button type="reset" class="btn-c btn-cancel">取&nbsp;消</button>
                        </div>
                    </div>
                </div>
            </form>
        </div>
    </div>
    <div class="footer">
        <span>版权所有 © 安徽中遥地理信息科技有限公司</span>
    </div>
</div>

</body>
</html>