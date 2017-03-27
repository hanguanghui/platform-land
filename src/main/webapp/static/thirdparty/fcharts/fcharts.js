/**
 * @author hanguanghui
 * @Description **
 * @version V1.0, 2017/3/17
 * @project platform
 */
!
    function($) {
        $.fn.fcharts={
            initChart:function(option){
                return _initChart(option);
            }
        }


        var bodyCssText = "height:500px;width:1000px;margin: 0 auto;overflow:auto;";

        var firstBtnCssTxt ="font-family: Arial;border-radius: 20px;padding: 10px 10px;width: 120px;text-align: center;font-size: 20px;font-weight: 700;color:white;";

        var dotBtnCssTxt = "display: inline;margin-left:62.5px;line-height: 50px;font-size:48px;";



        function _initChart(option){
            //创建主body
            var returnStr= document.createElement("div");

            var wb= document.createElement("div");
            wb.style.cssText = bodyCssText;

            if(option.length>0){
                //头部 一级菜单
                var h = document.createElement("div");
                h.style.cssText="height:50px;"

                wb.appendChild(h);
                var evLength = 1000/option.length;

                $.each(option,function(i,item) {
                        //一级按钮样式
                        var t = document.createElement("div");
                        t.style.cssText = "width:144px;float: left;";

                        var styleColor= filterColor(item.state);

                        if (i > 0 && i < option.length) {
                            //阶段过度符号样式
                            var d = document.createElement("div");
                            var width =  evLength - 144;
                            d.style.cssText = "float:left;width:" +width+ "px";

                            //阶段过度符号
                            var db = document.createElement("div");
                            db.style.cssText = dotBtnCssTxt + "color:" + styleColor;
                            db.innerHTML ="········";
                            d.appendChild(db);
                            h.appendChild(d);
                        }

                        //一级按钮
                        var b = document.createElement("div");
                        b.style.cssText = firstBtnCssTxt + "border: 2px solid " +styleColor+";background:"+ styleColor;

                        b.innerHTML = item.text;
                        t.appendChild(b);
                        h.appendChild(t);

                        //二级按钮部分
                        var st= document.createElement("div");
                        wb.appendChild(st);
                        var stLength = evLength -20;
                        st.style.cssText = "height:450px;float:left;margin-left:20px;"+"width:"+stLength+"px";
                        if(item.hasOwnProperty("children")&&item.children.length>0){
                            st.style.cssText+="border-left: 3px solid "+styleColor;

                            var fl = document.createElement("div");
                            fl.style.cssText="width:22px;height:450px;float: left;";

                            var fb = document.createElement("div");
                            fb.style.cssText ="height:450px;width:120px;float: left;";

                            var fla = document.createElement("div");
                            fla.style.cssText="width:22px;height:450px;float: left;";

                            var flb = document.createElement("div");
                            flb.style.cssText="width:22px;height:450px;float: left;";

                            var fbb = document.createElement("div");
                            fbb.style.cssText ="height:450px;width:120px;float: left;";

                            st.appendChild(fl);
                            st.appendChild(fb);
                            st.appendChild(fla);
                            st.appendChild(flb);
                            st.appendChild(fbb);

                            var tag=0;
                            $.each(item.children,function(j,it){
                                var child = item.children;
                                var stSpLen = 450/(child.length);

                                var fll = document.createElement("div");
                                var hfLen = stSpLen/2;
                                if(j==0){

                                    fll.style.cssText="width:22px;border-bottom:2px solid"+styleColor+";height:"+hfLen+"px;";
                                }else{
                                    fll.style.cssText="width:22px;border-bottom:2px solid"+styleColor+";height:"+stSpLen+"px;";
                                }
                                fl.appendChild(fll);

                                var bb= document.createElement("div");
                                fb.appendChild(bb);
                                var bbheight = (stSpLen/2)-15;
                                if(j>0){
                                    bbheight = stSpLen-30-j*2-20-5;
                                }
                                bb.style.cssText="float:left;font-size:16px;color:white;line-height:30px;text-align:center;width:120px;height:30px;border-radius: 10px;margin-top:"+bbheight+"px;border: 2px solid " +styleColor+";background:"+ styleColor;
                                bb.innerHTML=it.text;

                                var bbtxt = document.createElement("div");
                                bbtxt.style.cssText="float:left;font-size:14px;color:red;line-height:20px;text-align:center;width:120px;height:20px;padding-top:5px;";
                                bbtxt.innerHTML="<p style=\"text-align: left;\">操作人："+it.operator+"   时间："+it.starttime+"</p>";
                                fb.appendChild(bbtxt);
                                if(child[j].hasOwnProperty("children")&&child[j].children.length>0){
                                    var top =0;
                                    if(tag==j){
                                        top = j==0?stSpLen/2:stSpLen/2+(j-tag+1)*stSpLen;
                                    }else{
                                        top = tag==0?stSpLen/2+(j-tag-1)*stSpLen+2*(j-1):stSpLen/2+(j-tag)*stSpLen+2*(j-1);
                                    }
                                    var flla = document.createElement("div");
                                    if(j==0){
                                        flla.style.cssText="width:22px;border-bottom:2px solid"+styleColor+";height:"+hfLen+"px;";
                                    }else{
                                        flla.style.cssText="width:22px;border-bottom:2px solid"+styleColor+";height:"+stSpLen+"px;margin-top:"+top+"px";
                                    }

                                    fla.appendChild(flla);

                                    var fllb=document.createElement("div");
                                    top +=stSpLen/2;
                                    if(j==0){
                                        fllb.style.cssText="width:22px;border-left:2px solid"+styleColor+";height:"+stSpLen+"px;";
                                    }else{
                                        fllb.style.cssText="width:22px;border-left:2px solid"+styleColor+";height:"+stSpLen+"px;margin-top:"+top+"px";
                                    }

                                    flb.appendChild(fllb);

                                    var fbba = document.createElement("div");
                                    if(j==0){
                                        fbba.style.cssText="width:120px;color:"+styleColor+";height:"+hfLen+"px;";
                                    }else{
                                        fbba.style.cssText="width:120px;color:"+styleColor+";height:"+stSpLen+"px;margin-top:"+top+"px";
                                    }

                                    fbb.appendChild(fbba);

                                    var itChild = child[j].children;
                                    var evChildHeight = stSpLen/(itChild.length);
                                    $.each(itChild,function(k,childItem){
                                        var childColor=filterColor(childItem.state);

                                        var u= document.createElement("div");
                                        var height;
                                        if(k==0){
                                            height=11
                                        }else{
                                            height = evChildHeight+11-k*4;
                                        }
                                        u.style.cssText ="width:20px;float:left;border-bottom:2px solid"+childColor+";height:"+height+"px";
                                        fllb.appendChild(u);

                                        var y= document.createElement("div");
                                        var yheight=k==0?0:evChildHeight-10-k*2-10-5;
                                        y.style.cssText="float:left;font-size:12px;color:white;line-height:20px;text-align:center;width:120px;height:20px;border-radius: 10px;margin-top:"+yheight+"px;border: 1px solid " +childColor+";background:"+ childColor;
                                        y.innerHTML = childItem.text;

                                        fbba.append(y);
                                        var ytxt = document.createElement("div");
                                        ytxt.style.cssText="float:left;font-size:12px;color:red;line-height:10px;padding-top:5px;text-align:center;width:120px;height:10px;";
                                        ytxt.innerHTML="<p style=\"text-align: left;\">操作人："+childItem.operator+"</p>";
                                        fbba.appendChild(ytxt);
                                    });
                                    tag ++;
                                }
                            });
                        }
                    }
                );

                console.log(wb.innerHTML);
            }
            return wb;
        }


        function filterColor(item){
            var normalColor="#5FB878";
            var warnColor="#FF5722";
            var undefineColor="#F7B824";

            if (item == "normal") {
                return normalColor;
            } else if (item == "warn") {
                return warnColor;
            } else {
                return undefineColor;
            }
        }

    }(jQuery), function(e) {



}