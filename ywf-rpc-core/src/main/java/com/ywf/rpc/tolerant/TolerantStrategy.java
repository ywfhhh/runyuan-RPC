package com.ywf.rpc.tolerant;

import java.util.Map;

public interface TolerantStrategy {
    public void doTolerant(Map<String, Object> context,Exception e);
}
