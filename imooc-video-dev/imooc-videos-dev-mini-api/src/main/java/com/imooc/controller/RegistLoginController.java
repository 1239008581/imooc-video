package com.imooc.controller;

import com.imooc.pojo.Users;
import com.imooc.pojo.vo.UsersVO;
import com.imooc.service.UserService;
import com.imooc.utils.IMoocJSONResult;
import com.imooc.utils.MD5Utils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Api("用户登录注册api")
@RestController
public class RegistLoginController extends BasicController {

    @Autowired
    private UserService userService;

    @ApiOperation("用户注册")
    @PostMapping("/regist")
    public IMoocJSONResult regist(@RequestBody Users users) throws Exception {
        if (StringUtils.isBlank(users.getUsername()) || StringUtils.isBlank(users.getPassword())) {
            return IMoocJSONResult.errorMsg("账号或密码不能为空！");
        }

        boolean isExist = userService.queryUsernameIsExist(users.getUsername());
        if (!isExist) {
            users.setNickname(users.getUsername());
            users.setPassword(MD5Utils.getMD5Str(users.getPassword()));
            users.setFaceImage(null);
            users.setFansCounts(0);
            users.setFollowCounts(0);
            users.setReceiveLikeCounts(0);
            userService.saveUser(users);
        } else {
            return IMoocJSONResult.errorMsg("该用户信息已被注册！");
        }
        users.setPassword("");

        UsersVO usersVO = getUserToken(users);

        return IMoocJSONResult.ok(usersVO);
    }

    @ApiOperation("用户登录")
    @PostMapping("/login")
    public IMoocJSONResult login(@RequestBody Users users) throws Exception {
        if (StringUtils.isBlank(users.getUsername()) || StringUtils.isBlank(users.getPassword())) {
            return IMoocJSONResult.errorMsg("账号或密码不能为空！");
        }
        users.setPassword(MD5Utils.getMD5Str(users.getPassword()));
        Users userResult = userService.queryUsersIsExist(users);
        if (userResult == null) {
            return IMoocJSONResult.errorMsg("用户名或密码错误，请重试！");
        }
        userResult.setPassword("");

        UsersVO usersVO = getUserToken(userResult);

        return IMoocJSONResult.ok(usersVO);
    }

    @ApiOperation("用户注销")
    @PostMapping("/logout")
    public IMoocJSONResult logout(String userId) {
        redis.del(USER_REDIS_SESSION + ":" + userId);
        return IMoocJSONResult.ok("注销成功");
    }

    public UsersVO getUserToken(Users users) {
        String uniqueToken = UUID.randomUUID().toString();
        redis.set(USER_REDIS_SESSION + ":" + users.getId(), uniqueToken, 60 * 60 * 24 * 7);

        UsersVO usersVO = new UsersVO();
        BeanUtils.copyProperties(users, usersVO);
        usersVO.setUserToken(uniqueToken);
        return usersVO;
    }

    @GetMapping("hello")
    public String hello() {
        return "hello first-maven-together";
    }
}
