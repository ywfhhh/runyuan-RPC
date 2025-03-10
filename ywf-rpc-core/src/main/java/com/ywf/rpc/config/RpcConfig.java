package com.ywf.rpc.config;

import com.ywf.rpc.fault.retry.RetryStrategyKeys;
import com.ywf.rpc.loadbalancer.LoadBalancerConstant;
import com.ywf.rpc.serializer.SerializerKeys;
import com.ywf.rpc.tolerant.TolerantStrategyKeys;
import lombok.Data;

/**
 * RPC 框架配置
 */
@Data
public class RpcConfig {

    /**
     * 名称
     */
    private String name = "ywf-rpc";

    /**
     * 版本号
     */
    private String version = "1.0";

    /**
     * 服务器主机名
     */
    private String serverHost = "localhost";

    /**
     * 服务器端口号
     */
    private Integer serverPort = 8080;

    private boolean mock = false;

    private String serializer = SerializerKeys.JDK;

    private RegistryConfig registryConfig = new RegistryConfig();

    private String loaderBalancer = LoadBalancerConstant.RoundRobin;

    private String retryStrategy = RetryStrategyKeys.NO;

    private String tolerantStrategy = TolerantStrategyKeys.FailFast;
}
