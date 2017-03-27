package com.center.platform.utils;

/**
 * @author hanguanghui
 * @version V1.0, 2016/9/23
 */
public final class EnumUtils {
    /**
     * error types
     */
    public enum ERROR_TAG{
        loginError,pageError
    }
    public enum NULL_TAG{
        projectNull,userNull
    }
    public static enum REWORD_TYPE
    {
        company, expert, report;
    }

    public static enum LOG_TYPE{
        pic,doc,pdf
    }
}
