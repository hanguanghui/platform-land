package com.center.platform.services.impl;

import com.center.platform.BaseServiceTest;
import com.esri.sde.sdk.client.SeConnection;
import com.esri.sde.sdk.client.SeException;
import com.esri.sde.sdk.client.SeRelease;
import org.junit.Test;

/**
 * @author hanguanghui
 * @version V1.0, 2017/1/5
 */
public class sdeTest extends BaseServiceTest{
    private static SeConnection conn = null;

    private static String server = "192.168.90.123";
    //如果输入sde:oracle11g
    private static String instance = "sde:oracle11g:orcl";
    private static String database = "";
    private static String username = "sde";
    //密码需要输入sde@orcl
    private static String password = "sde";
    //获得ArcSDE连接
    private static SeConnection getConn() {
        if (conn == null) {
            try {
                conn = new SeConnection(server, instance, database, username,password);
            } catch (SeException ex) {
                ex.printStackTrace();
            }
        }
        return conn;
    }

    @Test
    public void test(){
        // TODO Auto-generated method stub
        SeConnection conn =getConn();
        SeRelease release=conn.getRelease();
        System.out.println(release.getBugFix());
        System.out.println(release.getDesc());
        System.out.println(release.getRelease());
        System.out.println(release.getMajor());
        System.out.println(release.getMinor());
    }
}
