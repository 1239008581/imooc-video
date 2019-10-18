package com.imooc.mapper;

import com.imooc.pojo.UsersLikeVideos;
import com.imooc.pojo.UsersLikeVideosExample;
import java.util.List;

public interface UsersLikeVideosMapper {
    int deleteByPrimaryKey(String id);

    int insert(UsersLikeVideos record);

    int insertSelective(UsersLikeVideos record);

    List<UsersLikeVideos> selectByExample(UsersLikeVideosExample example);

    UsersLikeVideos selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(UsersLikeVideos record);

    int updateByPrimaryKey(UsersLikeVideos record);
}