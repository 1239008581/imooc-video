package com.imooc.mapper;

import com.imooc.pojo.vo.VideosVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VideosMapperCustom {

    List<VideosVO> queryAllVideos(@Param("videoId") String videoId, @Param("videoDesc") String videoDesc, @Param("userId") String userId);

    void addVideoLikeCount(@Param("videoId") String videoId);

    void reduceVideoLikeCount(@Param("videoId") String videoId);

    List<VideosVO> queryUserLikeVideos(@Param("userId") String userId);

    List<VideosVO> queryUserFollowerVideos(@Param("userId") String userId);

}
