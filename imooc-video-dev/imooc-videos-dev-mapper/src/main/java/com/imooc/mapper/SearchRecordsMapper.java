package com.imooc.mapper;

import com.imooc.pojo.SearchRecords;
import com.imooc.utils.MyMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SearchRecordsMapper extends MyMapper<SearchRecords> {

    List<String> getHotWords();

}