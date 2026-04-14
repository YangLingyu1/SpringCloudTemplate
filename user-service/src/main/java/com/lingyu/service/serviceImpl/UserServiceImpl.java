package com.lingyu.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lingyu.common.Result;
import com.lingyu.entity.User;
import com.lingyu.mapper.UserMapper;
import com.lingyu.service.UserService;
import com.lingyu.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    @Autowired
    private JwtUtil jwtUtil;
    @Override
    public void TestRedis(String name, String password) {
        redisTemplate.opsForValue().set(name,password);
    }

    @Override
    public void TestSendKafka(String name, String password) {
        kafkaTemplate.send("test",name,password);
        System.out.println("发送成功");
    }

    @Override
    public Result<String> loginUser(User user) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, user.getUsername())
                .eq(User::getPassword, user.getPassword());
        User dbUser = baseMapper.selectOne(wrapper);
   /*     User dbUser = lambdaQuery()
                .eq(User::getUsername, user.getUsername())
                .eq(User::getPassword, user.getPassword())
                .one();*/

        if (dbUser == null) {
            return Result.fail("用户名或密码错误");
        }

        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", dbUser.getId()); // 把用户ID放进Token

        String token = jwtUtil.generateToken(claims);

        // 4. 返回 Token 给前端
        return Result.success(token);
    }

}
