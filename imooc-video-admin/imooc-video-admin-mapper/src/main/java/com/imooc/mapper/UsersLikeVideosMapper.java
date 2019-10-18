package com.imooc.mapper;

import com.imooc.pojo.UsersLikeVideos;
import com.imooc.pojo.UsersLikeVideosExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UsersLikeVideosMapper {
    long countByExample(UsersLikeVideosExample example);

    int deleteByExample(UsersLikeVideosExample example);

    int deleteByPrimaryKey(String id);

    int insert(UsersLikeVideos record);

    int insertSelective(UsersLikeVideos record);

    List<UsersLikeVideos> selectByExample(UsersLikeVideosExample example);

    UsersLikeVideos selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") UsersLikeVideos record, @Param("example") UsersLikeVideosExample example);

    int updateByExample(@Param("record") UsersLikeVideos record, @Param("example") UsersLikeVideosExample example);

    int updateByPrimaryKeySelective(UsersLikeVideos record);

    int updateByPrimaryKey(UsersLikeVideos record);
}