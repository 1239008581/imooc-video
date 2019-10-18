package com.imooc.mapper;

import com.imooc.pojo.SearchRecords;
import com.imooc.pojo.SearchRecordsExample;
import java.util.List;

public interface SearchRecordsMapper {
    int deleteByPrimaryKey(String id);

    int insert(SearchRecords record);

    int insertSelective(SearchRecords record);

    List<SearchRecords> selectByExample(SearchRecordsExample example);

    SearchRecords selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SearchRecords record);

    int updateByPrimaryKey(SearchRecords record);
}