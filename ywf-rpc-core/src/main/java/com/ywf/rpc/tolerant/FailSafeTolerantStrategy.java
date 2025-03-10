package com.ywf.rpc.tolerant;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public class FailSafeTolerantStrategy implements TolerantStrategy {
    @Override
    public void doTolerant(Map<String, Object> context, Exception e) {
        log.error("服务发生错误!静默处理!", e);
        return;
    }
}
