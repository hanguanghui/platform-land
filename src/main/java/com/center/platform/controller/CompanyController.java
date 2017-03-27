package com.center.platform.controller;

import com.center.platform.service.ICompanyService;
import com.center.platform.utils.Utils;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/company")
public class CompanyController extends BaseController
{

    @Autowired
    private ICompanyService companyService;

    /**
     * 查询公司（编制单位）
     * @param where
     * @return
     */
    @RequestMapping("/rest/list")
    @ResponseBody
    public List query(@RequestParam(value="where", required=false, defaultValue="1=1") String where)
    {
        Map map = this.companyService.find(where);
        List list = Utils.MapToList(map);
        return list;
    }

    /**
     * 查询公司 施工单位监理单位  分开查询
     * @param where
     * @return {"j":map,"s":map}
     */
    @RequestMapping("/rest/list1")
    @ResponseBody
    public Map queryCompany(@RequestParam(value="where", required=false, defaultValue="1=1") String where)
    {
        try
        {
            Map map = new HashMap();
            String[] wheres = where.split(";");
            for (String s : wheres) {
                String type = s.substring(s.indexOf("'") + 1, s.indexOf("'") + 2);
                List list = Utils.MapToList(this.companyService.find(s));
                map.put(type, list);
            }
            return map;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}