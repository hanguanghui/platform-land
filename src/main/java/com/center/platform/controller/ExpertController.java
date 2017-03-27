package com.center.platform.controller;

import com.center.platform.service.IExpertService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/expert")
public class ExpertController extends BaseController
{

    @Autowired
    private IExpertService expertService;

    /**
     * 查询专家列表 返回根据部门 姓名首字母排序的map
     * @param where
     * @return
     */
    @RequestMapping("/rest/list")
    @ResponseBody
    public Map query(@RequestParam(value="where", required=false, defaultValue="1=1") String where)
    {
        try
        {
            Map map = this.expertService.query(where);
            if (isNotNull(map)) {
                return map;
            }
            return null;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}