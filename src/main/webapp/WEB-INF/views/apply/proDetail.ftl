<#include '../common.ftl'/>
<html>

<head>
    <meta charset="utf-8">
    <title>Layui</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="<@s.url '/thirdparty/layui/css/layui.css'/>" media="all"/>
    <link rel="stylesheet" href="<@s.url '/css/public/global.css'/>"/>
    <link rel="stylesheet" href="<@s.url '/css/apply.css'/>" media="all"/>
    <link rel="stylesheet" href="<@s.url '/thirdparty/font-awesome/css/font-awesome.min.css'/>" media="all"/>

    <script src="<@s.url'/thirdparty/bootstrap/js/bootstrap-v3.js'/>" type="text/javascript"></script>
</head>

<body>
<div style="margin: 15px;">
    <div class="layui-tab layui-tab-brief" lay-filter="docDemoTabBrief">
        <ul class="layui-tab-title">
            <li class="layui-this">项目类型</li>
            <li>项目基本信息</li>
            <li>项目投资信息</li>
            <li>项目建设规模</li>
            <li>图形模块</li>
        </ul>
        <div class="layui-tab-content">
            <div class="layui-tab-item layui-show">
                <div class="pro-type">
                    <div class="layui-form-item">
                        <label class="layui-form-label">项目类型:</label>

                        <div class="layui-input-block">
                            <input type="text" autocomplete="off" class="layui-input" value="${project.protype!}"
                                   lay-verify="required">
                        </div>
                    </div>
                </div>
            </div>
        <#--基本信息开始-->
            <div class="layui-tab-item">
                <div class="pro-type">
                    <div class="form-line">
                        <div class="layui-form-item">
                            <label class="layui-form-label">项目名称</label>

                            <div class="layui-input-block">
                                <input type="text" lay-verify="proname" autocomplete="off"
                                       class="layui-input" value="${project.proname!}" lay-verify="required">
                            </div>
                        </div>

                        <div class="layui-form-item">
                            <label class="layui-form-label">下达机关</label>

                            <div class="layui-input-block">
                                <input type="text" name="probudgetorgan" lay-verify="probudgetorgan"
                                       autocomplete="off" placeholder="请输入下达预算与计划机关" class="layui-input"
                                       value="${project.probudgetorgan!}"
                                       lay-verify="required">
                            </div>
                        </div>

                        <div class="layui-form-item">
                            <label class="layui-form-label">下达文件名</label>

                            <div class="layui-input-block">
                                <input type="text" name="probudgetorname" lay-verify="probudgetorname"
                                       autocomplete="off" placeholder="请输入下达预算与计划文件名" class="layui-input"
                                       value="${project.probudgetorname!}"
                                       lay-verify="required">
                            </div>
                        </div>

                        <div class="layui-form-item">
                            <label class="layui-form-label">建设期</label>

                            <div class="layui-input-block">
                                <input type="number" name="probuildtime" lay-verify="probuildtime"
                                       autocomplete="off" class="layui-input" placeholder="请输入建设期"
                                       value="${project.probuildtime!}"
                                       lay-verify="required">
                            </div>
                        </div>
                    </div>

                    <div class="form-line">
                        <div class="layui-form-item">
                            <label class="layui-form-label">所在地</label>

                            <div class="layui-input-block">
                                <input type="text" name="prolocation" lay-verify="prolocation" autocomplete="off"
                                       placeholder="请输入所在地" class="layui-input" value="${project.prolocation!}"
                                       lay-verify="required">
                            </div>
                        </div>

                        <div class="layui-form-item">
                            <label class="layui-form-label">下达文件号</label>

                            <div class="layui-input-block">
                                <input type="text" name="probudgetnumber" lay-verify="probudgetnumber"
                                       autocomplete="off" placeholder="请输入下达预算与计划文件号" class="layui-input"
                                       value="${project.probudgetnumber!}"
                                       lay-verify="required">
                            </div>
                        </div>

                        <div class="layui-form-item">
                            <label class="layui-form-label">批复日期</label>

                            <div class="layui-input-block">
                                <input type="text" name="proreplytime" id="date" lay-verify="date"
                                       placeholder="批复日期" autocomplete="off" class="layui-input"
                                       value="${project.proreplytime!}" lay-verify="required">
                            </div>
                        </div>

                        <div class="layui-form-item">
                            <label class="layui-form-label">灾毁项目</label>

                            <div class="layui-input-block">
                                <input class="layui-input" value="${project.proisdamage!}"/>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        <#--基本信息结束-->
        <#--项目投资信息开始-->
            <div class="layui-tab-item">
                <div class="pro-type">
                    <div class="form-line">
                        <div class="layui-form-item">
                            <label class="layui-form-label">项目总投资</label>

                            <div class="layui-input-block">
                                <input type="number" name="prototalinvest" lay-verify="prototalinvest"
                                       autocomplete="off" placeholder="请输入总投资" class="layui-input"
                                       value="${project.prototalinvest!}"
                                       lay-verify="required">
                            </div>
                        </div>

                        <div class="layui-form-item">
                            <label class="layui-form-label">中央资金</label>

                            <div class="layui-input-block">
                                <input type="number" name="procenternewinvest" lay-verify="procenternewinvest"
                                       autocomplete="off" placeholder="请输入中央分配的新增资金" class="layui-input"
                                       value="${project.procenternewinvest!}"
                                       lay-verify="required">
                            </div>
                        </div>

                        <div class="layui-form-item">
                            <label class="layui-form-label">耕地开垦费</label>

                            <div class="layui-input-block">
                                <input type="number" name="procultivatedlandreclamation"
                                       lay-verify="procultivatedlandreclamation" autocomplete="off"
                                       placeholder="请输入耕地开垦费" class="layui-input"
                                       value="${project.procultivatedlandreclamation!}" lay-verify="required">
                            </div>
                        </div>

                        <div class="layui-form-item">
                            <label class="layui-form-label">土地出让费</label>

                            <div class="layui-input-block">
                                <input type="number" name="proagrilandsell" lay-verify="proagrilandsell"
                                       autocomplete="off" placeholder="请输入用于农业土地开发的土地出让费" class="layui-input"
                                       value="${project.proagrilandsell!}" lay-verify="required">
                            </div>
                        </div>

                        <div class="layui-form-item">
                            <label class="layui-form-label">投资人投资</label>

                            <div class="layui-input-block">
                                <input type="number" name="prolandobligorinvest" lay-verify="prolandobligorinvest"
                                       autocomplete="off" placeholder="请输入土地复垦义务投资人投资" class="layui-input"
                                       value="${project.prolandobligorinvest!}" lay-verify="required">
                            </div>
                        </div>
                    </div>
                    <div class="form-line">
                        <div class="layui-form-item">
                            <label class="layui-form-label">地方资金</label>

                            <div class="layui-input-block">
                                <input type="number" name="prolocalinvest" lay-verify="prolocalinvest"
                                       autocomplete="off" placeholder="请输入地方资金" class="layui-input"
                                       value="${project.prolocalinvest!}"
                                       lay-verify="required">
                            </div>
                        </div>

                        <div class="layui-form-item">
                            <label class="layui-form-label">地方资金</label>

                            <div class="layui-input-block">
                                <input type="number" name="prolocalnewinvest" lay-verify="prolocalnewinvest"
                                       autocomplete="off" placeholder="请输入地方留成新增资金" class="layui-input"
                                       value="${project.prolocalnewinvest!}"
                                       lay-verify="required">
                            </div>
                        </div>

                        <div class="layui-form-item">
                            <label class="layui-form-label">补充资金</label>

                            <div class="layui-input-block">
                                <input type="number" name="prosupplementinvest" lay-verify="prosupplementinvest"
                                       autocomplete="off" placeholder="请输入自行补充耕地资金" class="layui-input"
                                       value="${project.prosupplementinvest!}"
                                       lay-verify="required">
                            </div>
                        </div>

                        <div class="layui-form-item">
                            <label class="layui-form-label">土地复垦费</label>

                            <div class="layui-input-block">
                                <input type="number" name="prolandcontinuously" lay-verify="prolandcontinuously"
                                       autocomplete="off" placeholder="请输入土地复垦费" class="layui-input"
                                       value="${project.prolandcontinuously!}"
                                       lay-verify="required">
                            </div>
                        </div>

                        <div class="layui-form-item">
                            <label class="layui-form-label">其他资金</label>

                            <div class="layui-input-block">
                                <input type="number" name="prootherinvest" lay-verify="prootherinvest"
                                       autocomplete="off" placeholder="请输入其他资金" class="layui-input"
                                       value="${project.prootherinvest!}"
                                       lay-verify="required">
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        <#--项目投资信息结束-->
        <#--项目规模开始-->
            <div class="layui-tab-item">
                <div class="pro-type">
                    <div class="form-line">
                        <div class="layui-form-item">
                            <label class="layui-form-label">建设总规模</label>

                            <div class="layui-input-block">
                                <input type="number" name="prototalscale" lay-verify="prototalscale"
                                       autocomplete="off" placeholder="请输入建设总规模" class="layui-input"
                                       value="${project.prototalscale!}"
                                       lay-verify="required">
                            </div>
                        </div>

                        <div class="layui-form-item">
                            <label class="layui-form-label">农田规模</label>

                            <div class="layui-input-block">
                                <input type="number" name="probasefarmscale" lay-verify="probasefarmscale"
                                       autocomplete="off" placeholder="请输入基本农田整理规模" class="layui-input"
                                       value="${project.probasefarmscale!}"
                                       lay-verify="required">
                            </div>
                        </div>

                        <div class="layui-form-item">
                            <label class="layui-form-label">开发规模</label>

                            <div class="layui-input-block">
                                <input type="number" name="prodevelopscale" lay-verify="prodevelopscale"
                                       autocomplete="off" placeholder="请输入开发规模" class="layui-input"
                                       value="${project.prodevelopscale!}"
                                       lay-verify="required">
                            </div>
                        </div>

                        <div class="layui-form-item">
                            <label class="layui-form-label">建设条件</label>

                            <div class="layui-input-block">
                                <input type="number" name="prohblandconscondition"
                                       lay-verify="prohblandconscondition" autocomplete="off"
                                       placeholder="请输入高标准基本农田建设条件" class="layui-input"
                                       value="${project.prohblandconscondition!}"
                                       lay-verify="required">
                            </div>
                        </div>

                        <div class="layui-form-item">
                            <label class="layui-form-label">灾毁面积</label>

                            <div class="layui-input-block">
                                <input type="number" name="prodamagearea" lay-verify="prodamagearea"
                                       autocomplete="off" placeholder="请输入灾毁耕地面积" class="layui-input"
                                       value="${project.prodamagearea!}"
                                       lay-verify="required">
                            </div>
                        </div>
                    </div>
                    <div class="form-line">
                        <div class="layui-form-item">
                            <label class="layui-form-label">整理规模</label>

                            <div class="layui-input-block">
                                <input type="number" name="protidyscale" lay-verify="protidyscale"
                                       autocomplete="off" placeholder="请输入整理规模" class="layui-input"
                                       value="${project.protidyscale!}"
                                       lay-verify="required">
                            </div>
                        </div>

                        <div class="layui-form-item">
                            <label class="layui-form-label">复垦规模</label>

                            <div class="layui-input-block">
                                <input type="number" name="proreclamationscale" lay-verify="proreclamationscale"
                                       autocomplete="off" placeholder="请输入复垦规模" class="layui-input"
                                       value="${project.proreclamationscale!}"
                                       lay-verify="required">
                            </div>
                        </div>

                        <div class="layui-form-item">
                            <label class="layui-form-label">计划建规模</label>

                            <div class="layui-input-block">
                                <input type="number" name="proplanchblscale" lay-verify="proplanchblscale"
                                       autocomplete="off" placeholder="请输入计划建成高标准基本农田规模" class="layui-input"
                                       value="${project.proplanchblscale!}" lay-verify="required">
                            </div>
                        </div>

                        <div class="layui-form-item">
                            <label class="layui-form-label">新增面积</label>

                            <div class="layui-input-block">
                                <input type="number" name="pronewlandscale" lay-verify="pronewlandscale"
                                       autocomplete="off" placeholder="请输入新增耕地面积" class="layui-input"
                                       value="${project.pronewlandscale!}"
                                       lay-verify="required">
                            </div>
                        </div>

                        <div class="layui-form-item">
                            <label class="layui-form-label">占补平衡</label>

                            <div class="layui-input-block">
                                <input type="number" name="probalancearea" lay-verify="probalancearea"
                                       autocomplete="off" placeholder="请输入计划可用于占补平衡面积" class="layui-input"
                                       value="${project.probalancearea!}" lay-verify="required">
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        <#--项目规模结束-->

        <#--图形-->
            <div class="layui-tab-item">
                <div class="pro-type1">
                    <iframe src="<@s.url '/map'/>" style="height:450px;width:100%"></iframe>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="<@s.url'/thirdparty/layui/layui.js'/>" type="text/javascript"></script>
<script src="<@s.url'/js/draft.js'/>" type="text/javascript"></script>
</body>

</html>