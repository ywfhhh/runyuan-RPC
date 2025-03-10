package com.ywf;

import com.ywf.annotations.RpcService;
import com.ywf.common.model.User;
import com.ywf.common.service.UserService;
import org.springframework.stereotype.Service;

/**
 * 用户服务实现类
 *
 */
@Service
@RpcService
public class UserServiceImpl implements UserService {

    public User getUser(User user) {
        System.out.println("用户名：" + user.getName());
        return user;
    }

    @Override
    public int getNumber() {
        return 0;
    }

}
