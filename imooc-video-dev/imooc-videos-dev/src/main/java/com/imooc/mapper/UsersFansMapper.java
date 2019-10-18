package com.imooc.mapper;

import com.imooc.pojo.UsersFans;
import com.imooc.pojo.UsersFansExample;
import java.util.List;

public interface UsersFansMapper {
    int deleteByPrimaryKey(String id);

    int insert(UsersFans record);

    int insertSelective(UsersFans record);

    List<UsersFans> selectByExample(UsersFansExample example);

    UsersFans selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(UsersFans record);

    int updateByPrimaryKey(UsersFans record);
}