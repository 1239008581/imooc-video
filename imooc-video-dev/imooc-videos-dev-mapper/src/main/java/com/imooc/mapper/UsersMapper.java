package com.imooc.mapper;

import com.imooc.pojo.Users;
import com.imooc.utils.MyMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersMapper extends MyMapper<Users> {

    void addReceiveLikeCount(@Param("userId") String userId);

    void reduceReceiveLikeCount(@Param("userId") String userId);

    void addFollowCount(@Param("userId") String userId);

    void reduceFollowCount(@Param("userId") String userId);

    void addFansCount(@Param("userId") String userId);

    void reduceFansCount(@Param("userId") String userId);

}