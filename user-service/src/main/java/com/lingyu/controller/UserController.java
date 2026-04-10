package com.lingyu.controller;

import com.lingyu.dto.RedisUserDTO;
import com.lingyu.entity.User;
import com.lingyu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;
    @GetMapping("/{id}")
    public User getUserInfo(@PathVariable("id") String id) {
        return userService.getById( id);
    }
    @GetMapping
    public List<User> getAllUser() {
        return userService.list();
    }
    @PostMapping
    public void addUser(@RequestBody User user) {
        userService.save(user);
    }
    @PutMapping
    public void updateUser(@RequestBody User user) {
        userService.updateById(user);
    }
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable("id") String id) {
        userService.removeById(id);
    }
    @PostMapping("/testRedis")
    public void testRedis(@RequestBody RedisUserDTO redisUserDTO) {
        userService.TestRedis(redisUserDTO.getUsername(),redisUserDTO.getPassword());
    }
    @GetMapping("/testKafka")
    public void testKafka(@RequestBody RedisUserDTO redisUserDTO) {
        userService.TestSendKafka(redisUserDTO.getUsername(),redisUserDTO.getPassword());
    }
}
