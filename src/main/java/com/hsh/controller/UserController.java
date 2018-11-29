package com.hsh.controller;

import com.hsh.common.config.RestResult;
import com.hsh.common.config.ResultCode;
import com.hsh.domain.mongo.User;
import com.hsh.service.mongo.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author hushihai
 * @version V1.0, 2018/11/6
 */
@RestController
@RequestMapping(value = "/user")
public class UserController {

     private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    UserService userService;

    @GetMapping(value = "/getUserById")
    public User getUserById(String id){
        return userService.findUserById(id);
    }

    @PostMapping(value = "/insertUser")
    public RestResult insertUser(@RequestBody User user){
        try {
            userService.insert(user);
            log.debug("添加用户信息成功!");
        }catch (Exception e){
            log.error("添加用户信息失败!",e);
            return RestResult.failed(ResultCode.INTERFACE_INNER_INVOKE_ERROR);
        }
        return RestResult.success();
    }
}
