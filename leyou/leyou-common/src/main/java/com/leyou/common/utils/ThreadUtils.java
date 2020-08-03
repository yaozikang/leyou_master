package com.leyou.common.utils;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author 要子康
 * @description ThreadUtils
 * @since 2020/7/17 10:48
 */
public class ThreadUtils {

    private static final ExecutorService es = Executors.newFixedThreadPool(10);

    public static void execute(Runnable runnable) {
        es.submit(runnable);
    }
}
