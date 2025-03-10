package com.ywf.rpc.loadbalancer;

import com.ywf.rpc.model.ServiceMetaInfo;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

public class RandomLoadBalancer implements LoadBalancer {
    private Random random = new Random();

    @Override
    public ServiceMetaInfo select(Map<String, Object> requestParams, List<ServiceMetaInfo> metaInfoList) {
        if (metaInfoList == null || metaInfoList.isEmpty()) return null;
        if (metaInfoList.size() == 1) return metaInfoList.get(0);
        return metaInfoList.get(random.nextInt(metaInfoList.size()));
    }
}
