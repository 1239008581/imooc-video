package com.imooc.service.impl;

import com.imooc.mapper.UsersFansMapper;
import com.imooc.mapper.UsersLikeVideosMapper;
import com.imooc.mapper.UsersMapper;
import com.imooc.mapper.UsersReportMapper;
import com.imooc.pojo.Users;
import com.imooc.pojo.UsersFans;
import com.imooc.pojo.UsersLikeVideos;
import com.imooc.pojo.UsersReport;
import com.imooc.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import java.util.Date;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UsersMapper usersMapper;

    @Autowired
    private UsersLikeVideosMapper usersLikeVideosMapper;

    @Autowired
    private UsersFansMapper usersFansMapper;

    @Autowired
    private UsersReportMapper usersReportMapper;

    @Autowired
    private Sid sid;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public boolean queryUsernameIsExist(String username) {
        Users users = new Users();
        users.setUsername(username);
        Users result = usersMapper.selectOne(users);
        return result == null ? false : true;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void saveUser(Users user) {
        String userId = sid.nextShort();
        user.setId(userId);
        usersMapper.insert(user);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void updateUser(Users user) {
        Example usersExample = new Example(Users.class);
        Criteria criteria = usersExample.createCriteria();
        criteria.andEqualTo("id",user.getId());
        usersMapper.updateByExampleSelective(user,usersExample);
    }

    @Override
    public Users queryUserInfo(String userId) {
        Example userExample = new Example(Users.class);
        Criteria criteria = userExample.createCriteria();
        criteria.andEqualTo("id",userId);
        Users users = usersMapper.selectOneByExample(userExample);
        return users;
    }

    @Override
    public boolean isUserLikeVideo(String userId, String videoId) {
        if (StringUtils.isBlank(userId) || StringUtils.isBlank(videoId)){
            return false;
        }
        Example usersLikeVideosExample = new Example(UsersLikeVideos.class);
        Criteria criteria = usersLikeVideosExample.createCriteria();
        criteria.andEqualTo("userId",userId);
        criteria.andEqualTo("videoId",videoId);
        UsersLikeVideos usersLikeVideos = usersLikeVideosMapper.selectOneByExample(usersLikeVideosExample);
        if (usersLikeVideos == null){
            return false;
        } else {
            return true;
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void saveUserFanRelation(String userId, String fansId) {
        usersMapper.addFollowCount(fansId);
        usersMapper.addFansCount(userId);

        UsersFans usersFans = new UsersFans();
        usersFans.setId(sid.nextShort());
        usersFans.setUserId(userId);
        usersFans.setFanId(fansId);
        usersFansMapper.insertSelective(usersFans);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void deleteUserFanRelation(String userId, String fansId) {
        usersMapper.reduceFollowCount(fansId);
        usersMapper.reduceFansCount(userId);

        Example userFansExample = new Example(UsersFans.class);
        Criteria criteria = userFansExample.createCriteria();
        criteria.andEqualTo("userId",userId);
        criteria.andEqualTo("fanId",fansId);
        usersFansMapper.deleteByExample(userFansExample);
    }

    @Override
    public boolean isUserFanRelation(String userId, String fansId) {
        Example usersFansExample = new Example(UsersFans.class);
        Criteria criteria = usersFansExample.createCriteria();
        criteria.andEqualTo("userId",userId);
        criteria.andEqualTo("fanId",fansId);
        UsersFans usersFans = usersFansMapper.selectOneByExample(usersFansExample);
        if (usersFans == null){
            return false;
        }else{
            return true;
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Users queryUsersIsExist(Users users) {
        Users result = usersMapper.selectOne(users);
        return result;
    }

    @Override
    public int reportUser(UsersReport usersReport) {
        usersReport.setId(sid.nextShort());
        usersReport.setCreateDate(new Date());
        int result = usersReportMapper.insertSelective(usersReport);
        if (result == 1){
            return 1;
        }else {
            return 0;
        }
    }
}
