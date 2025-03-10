package com.ywf;

import com.ywf.annotations.RpcReference;
import com.ywf.common.model.User;
import com.ywf.common.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class ExampleServiceImpl {

    @RpcReference
    private UserService userService;

    public void test() {
        User user = new User();
        user.setName("ywf");
        User resultUser = userService.getUser(user);
        System.out.println(resultUser.getName());
    }

}
