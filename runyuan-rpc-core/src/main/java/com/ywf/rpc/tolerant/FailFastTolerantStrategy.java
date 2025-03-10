package com.ywf.rpc.tolerant;

import java.util.Map;

public class FailFastTolerantStrategy implements TolerantStrategy {
    @Override
    public void doTolerant(Map<String, Object> context, Exception e) {
        throw new RuntimeException("服务报错!", e);
    }
}
