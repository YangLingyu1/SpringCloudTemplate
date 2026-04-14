package com.lingyu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lingyu.common.Result;
import com.lingyu.entity.User;

public interface UserService extends IService<User> {
    void TestRedis(String name,String password);
    void TestSendKafka(String name,String password);

    Result<String> loginUser(User user);
}
