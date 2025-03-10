package com.ywf.annotations;

import com.ywf.bootstrap.RpcConsumerBootstrap;
import com.ywf.bootstrap.RpcInitBootstrap;
import com.ywf.bootstrap.RpcProviderBootstrap;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({RpcInitBootstrap.class, RpcProviderBootstrap.class, RpcConsumerBootstrap.class})
public @interface EnableRpc {
    boolean needServer() default true;
}
