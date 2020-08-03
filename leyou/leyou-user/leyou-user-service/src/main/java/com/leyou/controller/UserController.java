package com.leyou.controller;

import com.leyou.service.UserService;
import com.leyou.user.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author 要子康
 * @description UserController
 * @since 2020/7/18 14:51
 */
@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 校验数据是否可用
     * @param data
     * @param type
     * @return
     */
    @GetMapping("check/{data}/{type}")
    public ResponseEntity<Boolean> checkUserData(@PathVariable("data") String data,
                                   @PathVariable("type") Integer type){

        Boolean bool = this.userService.checkData(data, type);

        if (bool == null){
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(bool);
    }

    /**
     * 发送手机验证码
     * @param phone
     * @return
     */
    @PostMapping("code")
    public ResponseEntity<Void> sendVerifyCode(@RequestParam("phone") String phone){
        Boolean bool = this.userService.sendVerifyCode(phone);
        if (bool == null || !bool){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * 用户注册
     * @param user
     * @param code
     * @return
     */
    @PostMapping("register")
    public ResponseEntity<Void> register(@Valid User user, @RequestParam("code") String code){
        Boolean bool = this.userService.register(user, code);
        if (bool == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * 根据用户名和密码查询用户
     * @param username
     * @param password
     * @return
     */
    @GetMapping("query")
    public ResponseEntity<User> queryUser(
            @RequestParam("username") String username,
            @RequestParam("password") String password
    ){
        User user = this.userService.queryUser(username, password);
        if (user == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(user);
    }
}
