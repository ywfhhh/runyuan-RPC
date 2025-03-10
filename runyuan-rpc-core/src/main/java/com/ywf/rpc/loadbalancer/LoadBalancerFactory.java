package com.ywf.rpc.loadbalancer;

import com.ywf.rpc.spi.SpiLoader;

public class LoadBalancerFactory {
    static {
        SpiLoader.load(LoadBalancer.class);
    }

    public static LoadBalancer getInstance(String className) {
        return SpiLoader.getInstance(LoadBalancer.class, className);
    }
}
