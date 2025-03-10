package com.ywf.rpc.loadbalancer;

import com.ywf.rpc.model.ServiceMetaInfo;

import java.util.*;

public class ConsistentHashLoadBalancer implements LoadBalancer {
    private TreeMap<Integer, ServiceMetaInfo> serviceMap = new TreeMap<>();
    private final Integer VIRTUAL_NODE_NUM = 100;

    @Override
    public ServiceMetaInfo select(Map<String, Object> requestParams, List<ServiceMetaInfo> metaInfoList) {
        if (metaInfoList == null || metaInfoList.isEmpty()) return null;
        if (metaInfoList.size() == 1) return metaInfoList.get(0);
        for (ServiceMetaInfo metaInfo : metaInfoList) {
            for (int i = 0; i < VIRTUAL_NODE_NUM; i++) {
                int key = getHash(metaInfo.getServiceAddress() + "#" + i);
                serviceMap.putIfAbsent(key, metaInfo);
            }
        }
        Map.Entry<Integer, ServiceMetaInfo> entry = serviceMap.ceilingEntry(getHash(requestParams));
        if (entry == null) entry = serviceMap.firstEntry();
        return entry.getValue();
    }

    private int getHash(Object obj) {
        return obj.hashCode();
    }
}
