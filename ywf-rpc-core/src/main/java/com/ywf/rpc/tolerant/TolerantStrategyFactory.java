package com.ywf.rpc.tolerant;

import com.ywf.rpc.spi.SpiLoader;

public class TolerantStrategyFactory {
    static {
        SpiLoader.load(TolerantStrategy.class);
    }

    private String defaultTolerantStrategy = TolerantStrategyKeys.FailFast;

    public static TolerantStrategy getInstance(String tolerantStrategy) {
        return SpiLoader.getInstance(TolerantStrategy.class, tolerantStrategy);
    }
}
