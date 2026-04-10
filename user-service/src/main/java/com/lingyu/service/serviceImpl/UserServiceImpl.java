package com.lingyu.service.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lingyu.entity.User;
import com.lingyu.mapper.UserMapper;
import com.lingyu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Override
    public void TestRedis(String name, String password) {
        redisTemplate.opsForValue().set(name,password);
    }
}
