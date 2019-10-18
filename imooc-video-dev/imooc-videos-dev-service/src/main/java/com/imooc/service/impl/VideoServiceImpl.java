package com.imooc.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.imooc.mapper.*;
import com.imooc.pojo.Comments;
import com.imooc.pojo.SearchRecords;
import com.imooc.pojo.UsersLikeVideos;
import com.imooc.pojo.Videos;
import com.imooc.pojo.vo.CommentsVO;
import com.imooc.pojo.vo.VideosVO;
import com.imooc.service.VideoService;
import com.imooc.utils.PagedResult;
import com.imooc.utils.TimeAgoUtils;
import org.apache.commons.lang3.StringUtils;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import java.util.Date;
import java.util.List;

@Service
public class VideoServiceImpl implements VideoService {

    @Autowired
    private VideosMapper videosMapper;

    @Autowired
    private VideosMapperCustom videosMapperCustom;

    @Autowired
    private SearchRecordsMapper searchRecordsMapper;

    @Autowired
    private UsersMapper usersMapper;

    @Autowired
    private UsersLikeVideosMapper usersLikeVideosMapper;

    @Autowired
    private CommentsMapperCustom commentsMapperCustom;

    @Autowired
    private CommentsMapper commentsMapper;

    @Autowired
    private Sid sid;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public String saveVideo(Videos video) {
        String id = sid.nextShort();
        video.setId(id);
        int result = videosMapper.insertSelective(video);
        if (result == 0) return "fail";
        else return id;
    }

    @Override
    public PagedResult getALLVideos(Videos video, Integer isSaveRecord, Integer page, Integer pageSize) {

        String videoId = video.getId();
        String desc = video.getVideoDesc();
        String userId = video.getUserId();

        if (isSaveRecord == 1){
            SearchRecords searchRecord = new SearchRecords();
            searchRecord.setId(sid.nextShort());
            searchRecord.setContent(desc);
            searchRecordsMapper.insertSelective(searchRecord);
        }

        PageHelper.startPage(page, pageSize);
        List<VideosVO> list = videosMapperCustom.queryAllVideos(videoId, desc, userId);
        PagedResult pagedResult = getPage(page, list);
        return pagedResult;
    }

    @Override
    public PagedResult getMyLikeVideos(String userId, Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        List<VideosVO> list = videosMapperCustom.queryUserLikeVideos(userId);
        PagedResult pagedResult = getPage(page, list);
        return pagedResult;
    }

    @Override
    public PagedResult getMyFollowerVideos(String userId, Integer page, Integer pageSize) {
        PageHelper.startPage(page,pageSize);
        List<VideosVO> list = videosMapperCustom.queryUserFollowerVideos(userId);
        PagedResult pagedResult = getPage(page, list);
        return pagedResult;
    }

    @Override
    public PagedResult getVideoComments(String videoId, Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        List<CommentsVO> list = commentsMapperCustom.queryComments(videoId);
        for (CommentsVO commentsVO : list){
            commentsVO.setTimeAgoStr(TimeAgoUtils.format(commentsVO.getCreateTime()));
        }
        PagedResult pagedResult = getPage(page,list);
        return pagedResult;
    }

    @Override
    public List<String> getHotWords(){
        return searchRecordsMapper.getHotWords();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void userLikeVideo(String userId, String videoId, String createUserId) {
        videosMapperCustom.addVideoLikeCount(videoId);
        usersMapper.addReceiveLikeCount(createUserId);

        UsersLikeVideos usersLikeVideos = new UsersLikeVideos();
        usersLikeVideos.setId(sid.nextShort());
        usersLikeVideos.setUserId(userId);
        usersLikeVideos.setVideoId(videoId);
        usersLikeVideosMapper.insertSelective(usersLikeVideos);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void userUnlikeVideo(String userId, String videoId, String createUserId) {
        videosMapperCustom.reduceVideoLikeCount(videoId);
        usersMapper.reduceReceiveLikeCount(createUserId);

        Example usersLikeVideosExample = new Example(UsersLikeVideos.class);
        Criteria criteria = usersLikeVideosExample.createCriteria();
        criteria.andEqualTo("userId",userId);
        criteria.andEqualTo("videoId",videoId);
        usersLikeVideosMapper.deleteByExample(usersLikeVideosExample);
    }

    @Override
    public int saveComment(String fatherCommentId, String toUserId, String fromUserId, String videoId, String comment) {
        Comments comments = new Comments();
        if (StringUtils.isNotBlank(fatherCommentId)&&StringUtils.isNotBlank(toUserId)){
            comments.setFatherCommentId(fatherCommentId);
            comments.setToUserId(toUserId);
        }
        comments.setId(sid.nextShort());
        comments.setFromUserId(fromUserId);
        comments.setVideoId(videoId);
        comments.setComment(comment);
        comments.setCreateTime(new Date());
        comments.setId(sid.nextShort());
        comments.setCreateTime(new Date());
        int result = commentsMapper.insertSelective(comments);
        if (result == 0){
            return 0;
        }else {
            return 1;
        }
    }

    @Override
    public int updateVideo(Videos video) {
        int result = videosMapper.updateByPrimaryKeySelective(video);
        if (result == 0) return 0;
        else return 1;
    }

    public PagedResult getPage(Integer page, List<?> list){

        PageInfo<?> pageList = new PageInfo<>(list);

        PagedResult pagedResult = new PagedResult();
        pagedResult.setPage(page);
        pagedResult.setTotal(pageList.getPages());
        pagedResult.setRows(list);
        pagedResult.setRecords(pageList.getTotal());

        return pagedResult;
    }
}
