package com.imooc.service;

import com.imooc.pojo.Users;
import com.imooc.pojo.UsersReport;

public interface UserService {

    Users queryUsersIsExist(Users users);

    boolean queryUsernameIsExist(String username);

    void saveUser(Users user);

    void updateUser(Users user);

    Users queryUserInfo(String userId);

    boolean isUserLikeVideo(String userId, String videoId);

    void saveUserFanRelation(String userId, String fansId);

    void deleteUserFanRelation(String userId, String fansId);

    boolean isUserFanRelation(String userId, String fansId);

    int reportUser(UsersReport usersReport);
}
