package com.ywf.rpc.fault.retry;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RetryStrategyTest {
    RetryStrategy strategy = new NoRetryStrategy();

    @Test
    public void testRetryStrategy() {
        try {
            strategy.doRetry(() -> {
                System.out.println("测试重试!");
                throw new RuntimeException("模拟重试失败");
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}