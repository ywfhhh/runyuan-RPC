package com.ywf.rpc.proxy;

import cn.hutool.core.collection.CollUtil;
import com.ywf.rpc.RpcApplication;
import com.ywf.rpc.config.RpcConfig;
import com.ywf.rpc.constant.RpcConstant;
import com.ywf.rpc.fault.retry.RetryStrategy;
import com.ywf.rpc.fault.retry.RetryStrategyFactory;
import com.ywf.rpc.loadbalancer.LoadBalancer;
import com.ywf.rpc.loadbalancer.LoadBalancerFactory;
import com.ywf.rpc.model.RpcRequest;
import com.ywf.rpc.model.RpcResponse;
import com.ywf.rpc.model.ServiceMetaInfo;
import com.ywf.rpc.registry.Registry;
import com.ywf.rpc.registry.RegistryFactory;
import com.ywf.rpc.serializer.Serializer;
import com.ywf.rpc.serializer.SerializerFactory;
import com.ywf.rpc.server.tcp.VertxTcpClient;
import com.ywf.rpc.tolerant.TolerantStrategy;
import com.ywf.rpc.tolerant.TolerantStrategyFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 服务代理（JDK 动态代理）
 *
 */
public class ServiceProxy implements InvocationHandler {

    /**
     * 调用代理
     *
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 指定序列化器
        final Serializer serializer = SerializerFactory.getInstance(RpcApplication.getRpcConfig().getSerializer());

        // 构造请求
        String serviceName = method.getDeclaringClass().getName();
        RpcRequest rpcRequest = RpcRequest.builder()
                .serviceName(serviceName)
                .methodName(method.getName())
                .parameterTypes(method.getParameterTypes())
                .args(args)
                .build();
        try {
            // 从注册中心获取服务提供者请求地址
            RpcConfig rpcConfig = RpcApplication.getRpcConfig();
            Registry registry = RegistryFactory.getInstance(rpcConfig.getRegistryConfig().getRegistry());
            ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
            serviceMetaInfo.setServiceName(serviceName);
            serviceMetaInfo.setServiceVersion(RpcConstant.DEFAULT_SERVICE_VERSION);
            List<ServiceMetaInfo> serviceMetaInfoList = registry.serviceDiscovery(serviceMetaInfo.getServiceKey());
            if (CollUtil.isEmpty(serviceMetaInfoList)) {
                throw new RuntimeException("暂无服务地址");
            }
            LoadBalancer loadBalancer = LoadBalancerFactory.getInstance(rpcConfig.getLoaderBalancer());
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("methodName", method.getName());
            ServiceMetaInfo selectedServiceMetaInfo = loadBalancer.select(parameters, serviceMetaInfoList);
            RetryStrategy retryStrategy = RetryStrategyFactory.getInstance(rpcConfig.getRetryStrategy());
//            方法体只有一条语句时，可以省略 {},只有一个返回值省略return
            RpcResponse rpcResponse = null;
            try {
                rpcResponse = retryStrategy.doRetry(() ->
                        VertxTcpClient.doRequest(rpcRequest, selectedServiceMetaInfo, registry)
                );
            } catch (Exception e) {
                TolerantStrategy tolerantStrategy = TolerantStrategyFactory.getInstance(rpcConfig.getTolerantStrategy());
                tolerantStrategy.doTolerant(null, e);
            }
            return rpcResponse.getData();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
