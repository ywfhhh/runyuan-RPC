package com.ywf.rpc.loadbalancer;

import com.ywf.rpc.model.ServiceMetaInfo;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class RoundRobinLoadBalancer implements LoadBalancer {

    private AtomicInteger counter = new AtomicInteger(0);

    @Override
    public ServiceMetaInfo select(Map<String, Object> requestParams, List<ServiceMetaInfo> metaInfoList) {
        if (metaInfoList == null || metaInfoList.isEmpty()) return null;
        if(metaInfoList.size() == 1) return metaInfoList.get(0);
        return metaInfoList.get(counter.getAndIncrement() % metaInfoList.size());
    }
}
