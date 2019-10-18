package com.imooc.mapper;

import com.imooc.pojo.UsersReport;
import com.imooc.pojo.UsersReportExample;
import java.util.List;

public interface UsersReportMapper {
    int deleteByPrimaryKey(String id);

    int insert(UsersReport record);

    int insertSelective(UsersReport record);

    List<UsersReport> selectByExample(UsersReportExample example);

    UsersReport selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(UsersReport record);

    int updateByPrimaryKey(UsersReport record);
}