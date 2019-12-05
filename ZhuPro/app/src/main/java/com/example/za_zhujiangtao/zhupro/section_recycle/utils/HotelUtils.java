package com.example.za_zhujiangtao.zhupro.section_recycle.utils;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by lyd10892 on 2016/8/23.
 */

public class HotelUtils {
    public static <D> boolean isEmpty(List<D> list) {
        return list == null || list.isEmpty();
    }

}
