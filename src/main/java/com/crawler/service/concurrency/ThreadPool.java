package com.crawler.service.concurrency;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public final class ThreadPool {
    private final int numberOfCore = Runtime.getRuntime().availableProcessors();

    private final double utilizationCPU;

    private final int computeTimeRatio;

    private final int numberThreads;

    private final Executor executor;

    public ThreadPool(double utilizationCPU, int computeTimeRatio) {
        this.utilizationCPU = utilizationCPU;
        this.computeTimeRatio = computeTimeRatio;
        numberThreads = (int) (numberOfCore * utilizationCPU * computeTimeRatio);
        executor = setExecutor();
    }

    public int getNumberOfCore() {
        return numberOfCore;
    }

    public double getUtilizationCPU() {
        return utilizationCPU;
    }

    public int getComputeTimeRatio() {
        return computeTimeRatio;
    }

    public int getNumberThreads() {
        return numberThreads;
    }

    public Executor getExecutor() {
        return executor;
    }

    private ExecutorService setExecutor() {
        return Executors.newFixedThreadPool(numberThreads,
                        new ThreadFactory() {
                            public Thread newThread(Runnable r) {
                                Thread t = new Thread(r);
                                t.setDaemon(true);
                                return t;
                            }
                        });
    }
}
