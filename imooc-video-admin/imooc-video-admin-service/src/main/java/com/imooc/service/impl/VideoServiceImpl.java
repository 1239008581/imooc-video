package com.imooc.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.imooc.enums.BGMOperatorTypeEnum;
import com.imooc.mapper.BgmMapper;
import com.imooc.mapper.UsersReportMapperCustom;
import com.imooc.mapper.VideosMapper;
import com.imooc.pojo.Bgm;
import com.imooc.pojo.BgmExample;
import com.imooc.pojo.Videos;
import com.imooc.pojo.vo.Reports;
import com.imooc.service.VideoService;
import com.imooc.utils.JsonUtils;
import com.imooc.utils.PagedResult;
import com.imooc.web.util.ZKCurator;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class VideoServiceImpl implements VideoService {

    @Autowired
    private BgmMapper bgmMapper;

    @Autowired
    private VideosMapper videosMapper;

//    @Autowired
//    private ZKCurator zkCurator;

    @Autowired
    private UsersReportMapperCustom usersReportMapperCustom;

    @Override
    public int addBgm(Bgm bgm) {
        String id = Sid.next();
        bgm.setId(id);
        int result = bgmMapper.insertSelective(bgm);

        Map<String, String> map = new HashMap<>();
        map.put("type", BGMOperatorTypeEnum.ADD.type);
        map.put("path", bgm.getPath());

        //zkCurator.sendBgmOperator(id, JsonUtils.objectToJson(map));

        return result;
    }

    @Override
    public int delBgm(String id) {
        Bgm bgm = bgmMapper.selectByPrimaryKey(id);
        int result = bgmMapper.deleteByPrimaryKey(id);

        Map<String, String> map = new HashMap<>();
        map.put("type", BGMOperatorTypeEnum.DELETE.type);
        map.put("path", bgm.getPath());

        //zkCurator.sendBgmOperator(id, JsonUtils.objectToJson(map));
        return result;
    }

    @Override
    public PagedResult queryBgmList(Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);

        BgmExample bgmExample = new BgmExample();
        List<Bgm> list = bgmMapper.selectByExample(bgmExample);

        PageInfo<Bgm> pageInfo = new PageInfo<>(list);

        PagedResult result = new PagedResult();
        result.setPage(page);
        result.setRows(list);
        result.setTotal(pageInfo.getPages());
        result.setRecords(pageInfo.getTotal());

        return result;
    }

    @Override
    public PagedResult queryReportList(Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);

        List<Reports> reportsList = usersReportMapperCustom.selectAllVideoReport();

        PageInfo<Reports> pageList = new PageInfo<>(reportsList);

        PagedResult grid = new PagedResult();
        grid.setTotal(pageList.getPages());
        grid.setRows(reportsList);
        grid.setPage(page);
        grid.setRecords(pageList.getTotal());

        return grid;
    }

    @Override
    public int updateVideoStatus(String videoId, int status) {

        Videos videos = new Videos();
        videos.setId(videoId);
        videos.setStatus(status);

        int result = videosMapper.updateByPrimaryKeySelective(videos);
        return result;

    }

}
