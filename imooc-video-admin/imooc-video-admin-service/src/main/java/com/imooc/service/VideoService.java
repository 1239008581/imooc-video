package com.imooc.service;

import com.imooc.pojo.Bgm;
import com.imooc.utils.IMoocJSONResult;
import com.imooc.utils.PagedResult;

public interface VideoService {

    int addBgm(Bgm bgm);

    int delBgm(String id);

    int updateVideoStatus(String videoId, int status);

    PagedResult queryBgmList(Integer page, Integer pageSize);

    PagedResult queryReportList(Integer page, Integer pageSize);

}
