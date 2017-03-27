package com.center.platform;

/**
 * .
 *
 * @author <a href="mailto:lanxy88@gmail.com">NelsonXu</a>
 * @version V1.0, 13-3-26 下午4:39
 */
public final class Constant {

    public static final String UTF_8 = "utf-8";

    /**
     *
     */
    public static final String DEFAULT_DATE_FORMATE = "yyyy-MM-dd";

    /**
     *
     */
    public static final String DEFAULT_DATETIME_FORMATE = "yyyy-MM-dd HH:mm:ss";

    /**
     *
     */
    public static final String TPL = "/tpls/";

    /**
     *
     */
    public static final String DEFAULT_TPL = "base.tpl";

    /**
     * 默认年份
     */
    public static final String DEFAULT_YEAR = "default.year";

    /**
     *
     */
    public static final String YEAR_CURRENT = "current";

    /**
     *
     */
    public static final String XZDM = "xzdm";

    /**
     *
     */
    public static final String CONFIG_SUFFIX = ".json";

    /**
     *
     */
    public static final String FUNCTION_KEY = "functions";

    public static final String DICTS_WORD = "dicts";

    public static final String MAP_WORD = "map";

    public static final String LAYER_KEY = "operationalLayers";

    /**
     * 空间引擎方式
     */
    public static final String SPATIAL_ENGINE = "spatial.engine";

    public static final String LOCATION_LABEL = "定位";

    public static final String SE_SHAPE_FIELD = "SHAPE";

    public static final String SE_SHAPE_AREA = "SHAPE_AREA";

    public static final String SE_OBJECTID = "OBJECTID";

    public static final String ORIGINAL_SHAPE_AREA = "OG_SHAPE_AREA";

    public static final String INPUT_SHAPE_AREA = "IN_SHAPE_AREA";

    public static final String GEO_KEY_FEATURES="features";

    public static final String GEO_KEY_PROPERTIES="properties";

    /**
     * 空间关系类型
     */
    public static final String INTERSECT_RELATION = "INTERSECT";
    /***
     *
     */
    public static final String WITHIN_RELATION = "WITHIN";

    /**
     *
     */
    public static final String ACCESS_CONTROL_ALLOW_ORIGN = "Access-Control-Allow-Origin";

    /**
     * 单独选址项目
     */
    public static final String PROJ_DDXZ = "单独选址项目";
    /**
     * 批次项目
     */
    public static final String PROJ_PC = "批次城镇建设用地";

    /***
     * 4610 xian 1980
     */
    public static final String EPSG_4610 = "EPSG:4610";
    /***
     * 2364 xian 1980
     */
    public static final String EPSG_2364 = "EPSG:2364";

    /**
     * 普通探头
     */
    public static final String CAMERA_TYPE_NORMAL = "normal";


    /**
     * 空间引擎类型
     */
    public enum SpatialType {
        ARC_SDE, ORACLE_SPATIAL
    }

    /**
     * 定时调度任务类型
     */
    public enum QuartzJobKey {
        CAPTRUE("captureImgFromVideoBean");

        private String name;

        QuartzJobKey(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }

    }

    /**
     * 视频探头操作分类
     * OPEN(1) 打开操作
     * CLOSE(2) 关闭操作
     * END(3) 超时不操作关闭操作
     * CAPTURE(4) 抓图操作
     * HEARTBEAT(5) 心跳
     */
    public enum VideoOptType {
        OPEN(1), CLOSE(2), END(3), CAPTURE(4), HEARTBEAT(5);
        private int type;

        VideoOptType(int type) {
            this.type = type;
        }

        public int getType() {
            return type;
        }
    }

    /**
     * 操作日志类型
     * PROJECTLOG: 项目操作日志
     * INSPECTLOG: 巡查操作日志
     */
    public enum OptLogType {
        PROJECTLOG(1), INSPECTLOG(2);
        private int type;

        OptLogType(int type) {
            this.type = type;
        }

        ;

        public int getType() {
            return this.type;
        }
    }
}
