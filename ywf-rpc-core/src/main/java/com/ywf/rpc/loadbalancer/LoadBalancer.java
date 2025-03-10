package com.ywf.rpc.loadbalancer;

import com.ywf.rpc.model.ServiceMetaInfo;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public interface LoadBalancer {
    ServiceMetaInfo select(Map<String, Object> requestParams, List<ServiceMetaInfo> metaInfoList);
}
