package com.center.platform.service;

import com.center.platform.entity.ConstructLog;
import com.center.platform.entity.Log;
import com.center.platform.entity.Supervision;
import com.center.platform.entity.SupervisionLog;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.List;

/**
 * Created by hangu on 2017/1/2.
 */
public interface ILogService {
    /**
     * 保存图片到数据库（监理图片）
     * @param log
     * @return
     */
    boolean sava2DB(Log log);
    /**
     * 删除监理图片
     * @param logId
     * @return
     */
    boolean delete(String logId);
    /**
     * 保存日志信息（监理图片信息）
     * @param request
     * @param proid
     * @param path
     * @param type
     * @return
     */
     Log sava(MultipartHttpServletRequest request, String proid,String Path,String type);
    /**
     * 根据年份查询
     * @param proid
     * @return
     */
     List<String> findYears(String proid);
    /**
     * 根据时间查询 分页
     * @param proid
     * @param year
     * @param month
     * @param page
     * @param size
     * @return
     */
     Page queryTasks(String proid,String year,String type,String month,int page, int size);

    /**
     * 保存监理信息（监理日志 监理详细）
     * @param lst
     * @param supervisionLog
     * @return
     */
    boolean saveSupervisionLog(List<Supervision> lst,SupervisionLog supervisionLog);

    /**
     * 获取所有监理信息 用于国土局查看
     * @param proid
     * @return
     */
    List getFullLogInfo(String proid,String year,String month);

    /**
     * 获取监理日志概述信息
     * @param supervisionLog
     * @return
     */
    SupervisionLog getSupervisionLog(SupervisionLog supervisionLog);

    /**
     *获取监理日志详细
     * @param supervision
     * @return
     */
    List<Supervision> getSupervisionDetail(Supervision supervision);

    /**
     * 保存施工日志详细信息
     * @param constructLog
     * @return
     */
    boolean saveConstructLog(ConstructLog constructLog);

    /**
     * 获取施工日志详细
     * @param constructLog
     * @return
     */
    ConstructLog getConstructDetail(ConstructLog constructLog);

}
