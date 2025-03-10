package com.ywf.rpc.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ServiceRegisterInfo<T> {
    private String serviceName;
    private Class<? extends T> serviceInterface;

}
