package com.imooc.controller;

import com.imooc.pojo.Users;
import com.imooc.pojo.UsersReport;
import com.imooc.pojo.vo.UsersVO;
import com.imooc.service.UserService;
import com.imooc.utils.IMoocJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Api("用户相关操作接口")
@RequestMapping("/user")
@RestController
public class UserController extends BasicController{

    @Autowired
    private UserService userService;

    @ApiOperation("上传用户头像接口")
    @PostMapping("/uploadFace")
    public IMoocJSONResult uploadFace(String userId, @RequestParam("file") MultipartFile[] files) {

        //保存到数据库的相对路径
        String uploadPathDB = "/" + userId + "/face";

        FileOutputStream fileOutputStream = null;
        InputStream inputStream = null;

        if (StringUtils.isBlank(userId)){
            return IMoocJSONResult.errorMsg("用户信息不能为空！");
        }

        try {
            if (files != null && files.length > 0) {
                String fileName = files[0].getOriginalFilename();
                if (StringUtils.isNotBlank(fileName)) {
                    String finalFacePath = FILE_SPACE + uploadPathDB + "/" + fileName;
                    uploadPathDB += ("/" + fileName);

                    File outFile = new File(finalFacePath);
                    if (outFile.getParentFile() != null || !outFile.getParentFile().isDirectory()) {
                        outFile.getParentFile().mkdirs();
                    }

                    fileOutputStream = new FileOutputStream(outFile);
                    inputStream = files[0].getInputStream();
                    IOUtils.copy(inputStream, fileOutputStream);
                }else{
                    return IMoocJSONResult.errorMsg("上传出错！");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.flush();
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        Users users = new Users();
        users.setId(userId);
        users.setFaceImage(uploadPathDB);
        userService.updateUser(users);

        return IMoocJSONResult.ok(uploadPathDB);
    }

    @ApiOperation("用户信息查询接口")
    @PostMapping("/query")
    public IMoocJSONResult query(String userId, String fanId) {
        if (StringUtils.isBlank(userId) || StringUtils.isBlank(fanId)) {
            return IMoocJSONResult.errorMsg("用户信息为空！");
        }

        Users userInfo = userService.queryUserInfo(userId);
        if (userInfo == null){
            return IMoocJSONResult.errorMsg("该用户不存在！");
        }

        UsersVO usersVO = new UsersVO();
        BeanUtils.copyProperties(userInfo, usersVO);

        boolean isFollow = userService.isUserFanRelation(userId, fanId);
        usersVO.setFollow(isFollow);

        return IMoocJSONResult.ok(usersVO);
    }

    @ApiOperation("用户关注接口")
    @PostMapping("/beyourfan")
    public IMoocJSONResult beYourFan(String userId, String fanId){
        if (StringUtils.isBlank(userId) || StringUtils.isBlank(fanId)){
            return IMoocJSONResult.errorMsg("");
        }
        userService.saveUserFanRelation(userId, fanId);
        return IMoocJSONResult.ok();
    }

    @ApiOperation("用户取消关注接口")
    @PostMapping("/notyourfan")
    public IMoocJSONResult notYourFan(String userId, String fanId){
        if (StringUtils.isBlank(userId) || StringUtils.isBlank(fanId)){
            return IMoocJSONResult.errorMsg("");
        }
        userService.deleteUserFanRelation(userId, fanId);
        return IMoocJSONResult.ok();
    }

    @ApiOperation("举报视频接口")
    @PostMapping("/reportUser")
    public IMoocJSONResult reportUser(@RequestBody UsersReport usersReport){
        int result = userService.reportUser(usersReport);
        if (result == 0){
            return IMoocJSONResult.errorMsg("系统错误，举报失败！");
        }else {
            return IMoocJSONResult.okMsg("举报成功，系统将判断！");
        }
    }

}
