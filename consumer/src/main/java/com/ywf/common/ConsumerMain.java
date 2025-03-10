package com.ywf.common;
import com.ywf.common.model.User;
import com.ywf.rpc.proxy.ServiceProxyFactory;
import com.ywf.common.service.UserService;

/**
 * 简易服务消费者示例
 */
public class ConsumerMain {
    public static void main(String[] args) throws Exception {
        // todo 需要获取 UserService 的实现类对象
        UserService userService = ServiceProxyFactory.getProxy(UserService.class);
        User user = new User();
        user.setName("ywf");
        // 调用
        User newUser = userService.getUser(user);
        if (newUser != null) {
            System.out.println(newUser.getName());
        } else {
            System.out.println("user == null");
        }
        System.out.println(userService.getNumber());
//        RpcConfig rpcConfig = ConfigUtils.loadConfig(RpcConfig.class, "rpc","","");
//        System.out.println(rpcConfig);
    }

}
