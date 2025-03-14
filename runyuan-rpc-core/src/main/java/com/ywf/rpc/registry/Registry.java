package com.ywf.rpc.registry;

import com.ywf.rpc.config.RegistryConfig;
import com.ywf.rpc.model.ServiceMetaInfo;

import java.util.List;

/**
 * 注册中心
 */
public interface Registry {

    /**
     * 初始化
     *
     * @param registryConfig
     */
    void init(RegistryConfig registryConfig);

    /**
     * 注册服务（服务端）
     *
     * @param serviceMetaInfo
     */
    void register(ServiceMetaInfo serviceMetaInfo) throws Exception;

    /**
     * 注销服务（服务端）
     *
     * @param serviceMetaInfo
     */
    void unRegister(ServiceMetaInfo serviceMetaInfo);

    /**
     * 服务发现（获取某服务的所有节点，消费端）
     *
     * @param serviceKey 服务键名
     * @return
     */
    List<ServiceMetaInfo> serviceDiscovery(String serviceKey);

    /**
     * 服务销毁
     */
    void destroy();

    /**
     * 心跳监测（提供者端）
     */
    void heartbeat();

    /**
     * 监听（消费者端）
     */
    void watch(String serviceNodeKey);

    /**
     * 删除节点（消费者端）
     */
    void deleteNode(String serviceNodeKey);
}
