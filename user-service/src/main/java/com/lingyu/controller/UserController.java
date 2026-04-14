package com.lingyu.controller;

import com.lingyu.common.Result;
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
    public Result<User> getUserInfo(@PathVariable("id") String id) {
        return Result.success(userService.getById(id));
    }

    @GetMapping
    public Result<List<User>> getAllUser() {
        return Result.success(userService.list());
    }

    @PostMapping
    public Result<String> addUser(@RequestBody User user) {
        return userService.save(user) ? Result.success("添加成功") : Result.fail("添加失败");
    }

    @PutMapping
    public Result<String> updateUser(@RequestBody User user) {
        return userService.updateById(user) ? Result.success("更新成功") : Result.fail("更新失败");
    }

    @DeleteMapping("/{id}")
    public Result<String> deleteUser(@PathVariable("id") String id) {
        return userService.removeById(id) ? Result.success("删除成功") : Result.fail("删除失败");
    }
    @PostMapping("/login")
    public Result<String> loginUser(@RequestBody User user){
        return userService.loginUser(user);
    }
    @PostMapping("/testRedis")
    public Result<String> testRedis(@RequestBody RedisUserDTO redisUserDTO) {
        userService.TestRedis(redisUserDTO.getUsername(), redisUserDTO.getPassword());
        return Result.success("Redis测试成功");
    }

    @GetMapping("/testKafka")
    public Result<String> testKafka(/*@RequestParam("username") */String username,
                                    /* @RequestParam("password")*/ String password) {
        userService.TestSendKafka(username, password);
        return Result.success("Kafka发送成功");
    }
}
