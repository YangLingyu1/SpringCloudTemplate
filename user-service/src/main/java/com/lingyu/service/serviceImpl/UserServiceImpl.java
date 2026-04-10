package com.lingyu.service.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lingyu.entity.User;
import com.lingyu.mapper.UserMapper;
import com.lingyu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    @Override
    public void TestRedis(String name, String password) {
        redisTemplate.opsForValue().set(name,password);
    }

    @Override
    public void TestSendKafka(String name, String password) {
        kafkaTemplate.send("test",name,password);
        System.out.println("发送成功");
    }

}
