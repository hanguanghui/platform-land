package com.center.platform.utils;

import java.util.Comparator;

/**
 * @author hanguanghui
 * @version V1.0, 2016/12/5
 */
public class MapKeyComparator implements Comparator<String> {


    @Override
    public int compare(String str1, String str2) {
        return str1.compareTo(str2);
    }
}
