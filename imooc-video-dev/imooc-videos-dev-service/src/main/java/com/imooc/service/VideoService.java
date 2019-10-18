package com.imooc.service;

import com.imooc.pojo.Videos;
import com.imooc.utils.PagedResult;
import io.swagger.models.auth.In;

import java.util.List;

public interface VideoService {

    String saveVideo(Videos video);

    PagedResult getALLVideos(Videos video, Integer isSaveRecord, Integer page, Integer pageSize);

    PagedResult getMyLikeVideos(String userId, Integer page, Integer pageSize);

    PagedResult getMyFollowerVideos(String userId, Integer page, Integer pageSize);

    PagedResult getVideoComments(String videoId, Integer page, Integer pageSize);

    List<String> getHotWords();

    void userLikeVideo(String userId, String videoId, String createUserId);

    void userUnlikeVideo(String userId, String videoId, String createUserId);

    int saveComment(String fatherCommentId, String toUserId, String fromUserId, String videoId, String comment);

    int updateVideo(Videos video);
}
