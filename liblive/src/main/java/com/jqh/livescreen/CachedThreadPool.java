package com.jqh.livescreen;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CachedThreadPool {
    public static ExecutorService executorService = Executors.newFixedThreadPool(100);
}
