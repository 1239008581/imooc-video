package com.imooc.mapper;

import com.imooc.pojo.Videos;
import com.imooc.pojo.VideosExample;
import java.util.List;

public interface VideosMapper {
    int deleteByPrimaryKey(String id);

    int insert(Videos record);

    int insertSelective(Videos record);

    List<Videos> selectByExample(VideosExample example);

    Videos selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Videos record);

    int updateByPrimaryKey(Videos record);
}