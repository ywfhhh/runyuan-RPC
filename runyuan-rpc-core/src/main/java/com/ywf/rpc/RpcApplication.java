package com.ywf.rpc;

import com.ywf.rpc.config.RegistryConfig;
import com.ywf.rpc.config.RpcConfig;
import com.ywf.rpc.constant.RpcConstant;
import com.ywf.rpc.registry.Registry;
import com.ywf.rpc.registry.RegistryFactory;
import com.ywf.rpc.utils.ConfigUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * RPC 框架应用
 * 相当于 holder，存放了项目全局用到的变量。双检锁单例模式实现
 */
@Slf4j
public class RpcApplication {

    private static volatile RpcConfig rpcConfig;

    /**
     * 框架初始化，支持传入自定义配置
     *
     * @param newRpcConfig
     */
    public static void init(RpcConfig newRpcConfig) {
        rpcConfig = newRpcConfig;
        log.info("rpc init, config = {}", newRpcConfig.toString());
        // 注册中心初始化
        RegistryConfig registryConfig = rpcConfig.getRegistryConfig();
        Registry registry = RegistryFactory.getInstance(registryConfig.getRegistry());
        registry.init(registryConfig);
        log.info("registry init, config = {}", registryConfig);
    }

    /**
     * 初始化
     */
    public static void init() {
        RpcConfig newRpcConfig;
        try {
            newRpcConfig = ConfigUtils.loadConfig(RpcConfig.class, RpcConstant.DEFAULT_CONFIG_PREFIX, "", "yaml");
        } catch (Exception e) {
            // 配置加载失败，使用默认值
            log.error("配置加载失败，使用默认值");
            newRpcConfig = new RpcConfig();
        }
        init(newRpcConfig);
    }

    /**
     * 获取配置
     *
     * @return
     */
    public static RpcConfig getRpcConfig() {
        if (rpcConfig == null) {
            synchronized (RpcApplication.class) {
                if (rpcConfig == null) {
                    init();
                }
            }
        }
        return rpcConfig;
    }

    public static void main(String[] args) {
        RpcApplication rpcApplication = new RpcApplication();
        RpcConfig config = rpcApplication.getRpcConfig();
        System.out.println(config.toString());
    }
}
