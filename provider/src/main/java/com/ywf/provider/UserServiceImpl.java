package com.ywf.provider;

import com.ywf.common.model.User;
import com.ywf.common.service.UserService;

/**
 * 用户服务实现类
 */
public class UserServiceImpl implements UserService {

    public User getUser(User user) {
        System.out.println("用户名：" + user.getName());
        return user;
    }

    public int getNumber() {
        return 1;
    }
}
