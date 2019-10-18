package com.imooc.controller;

import com.imooc.enums.VideoStatusEnum;
import com.imooc.pojo.Bgm;
import com.imooc.pojo.Comments;
import com.imooc.pojo.Users;
import com.imooc.pojo.Videos;
import com.imooc.pojo.vo.PublisherVideo;
import com.imooc.pojo.vo.UsersVO;
import com.imooc.service.BgmService;
import com.imooc.service.UserService;
import com.imooc.service.VideoService;
import com.imooc.utils.IMoocJSONResult;
import com.imooc.utils.PagedResult;
import com.imooc.utils.ffmpegUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Api("视频相关操作接口")
@RestController
@RequestMapping("/video")
public class VideoController extends BasicController {

    @Autowired
    private BgmService bgmService;

    @Autowired
    private VideoService videoService;

    @Autowired
    private UserService userService;

    @ApiOperation("上传视频接口")
    @PostMapping("/upload")
    public IMoocJSONResult upload(String userId, String bgmId, String desc, double videoSeconds, int videoHeight, int videoWidth, @RequestParam("file") MultipartFile[] files) {

        //保存到数据库的相对路径
        String uploadPathDB = "/" + userId + "/video";
        String coverPathDB = "/" + userId + "/video";

        //无bgm文件最终保存路径
        String finalVideoPath = null;

        FileOutputStream fileOutputStream = null;
        InputStream inputStream;

        if (StringUtils.isBlank(userId)) {
            return IMoocJSONResult.errorMsg("用户信息不能为空！");
        }

        try {
            if (files != null && files.length > 0) {
                String fileName = files[0].getOriginalFilename();
                if (StringUtils.isNotBlank(fileName)) {
                    finalVideoPath = FILE_SPACE + uploadPathDB + "/" + fileName;
                    uploadPathDB += ("/" + fileName);
                    coverPathDB += ("/" + fileName.split("\\.")[0] + ".jpg");

                    File outFile = new File(finalVideoPath);
                    if (outFile.getParentFile() != null || !outFile.getParentFile().isDirectory()) {
                        outFile.getParentFile().mkdirs();
                    }

                    fileOutputStream = new FileOutputStream(outFile);
                    inputStream = files[0].getInputStream();
                    IOUtils.copy(inputStream, fileOutputStream);
                } else {
                    return IMoocJSONResult.errorMsg("上传出错！");
                }
            } else {
                return IMoocJSONResult.errorMsg("文件出错！");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.flush();
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        if (StringUtils.isNotBlank(bgmId)) {
            Bgm bgm = bgmService.queryBgmById(bgmId);
            String bgmInputPath = FILE_SPACE + bgm.getPath();

            //将无bgm视频的路径做为有bgm视频的源文件路径
            String videoInputPath = finalVideoPath;

            String videoOutputName = UUID.randomUUID().toString() + ".mp4";
            uploadPathDB = "/" + userId + "/video/" + videoOutputName;
            finalVideoPath = FILE_SPACE + uploadPathDB;
            try {
                ffmpegUtils.convertor(FFMPEG, videoInputPath, bgmInputPath, videoSeconds, finalVideoPath);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        try {
            ffmpegUtils.getCover(FFMPEG, finalVideoPath, FILE_SPACE + coverPathDB);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Videos video = new Videos();
        video.setUserId(userId);
        video.setAudioId(bgmId);
        video.setCreateTime(new Date());
        video.setVideoDesc(desc);
        video.setVideoHeight(videoHeight);
        video.setVideoPath(uploadPathDB);
        video.setCoverPath(coverPathDB);
        video.setVideoSeconds((float) videoSeconds);
        video.setVideoWidth(videoWidth);
        video.setStatus(VideoStatusEnum.SUCCESS.value);

        String videoId = videoService.saveVideo(video);
        if (StringUtils.equals(videoId, "fail")) {
            return IMoocJSONResult.errorMsg("保存失败");
        }
        return IMoocJSONResult.ok(videoId);
    }

    @ApiOperation("显示视频列表接口")
    @PostMapping("/showall")
    public IMoocJSONResult showAll(@RequestBody Videos video, Integer isSaveRecord, Integer page){
        if (page == null){
            page = 1;
        }
        PagedResult pagedResult  = videoService.getALLVideos(video, isSaveRecord, page,PAGE_SIZE);
        return IMoocJSONResult.ok(pagedResult);
    }

    @ApiOperation("查询用户喜欢视频接口")
    @PostMapping("/showMyLike")
    public IMoocJSONResult showMyLike(String userId, Integer page){
        if (page == null){
            page = 1;
        }
        PagedResult pagedResult = videoService.getMyLikeVideos(userId, page, 6);
        return IMoocJSONResult.ok(pagedResult);
    }

    @ApiOperation("查询用户关注者的视频接口")
    @PostMapping("/showMyFollow")
    public IMoocJSONResult showMyFollow(String userId, Integer page){
        if (page == null){
            page = 1;
        }
        PagedResult pagedResult = videoService.getMyFollowerVideos(userId, page, 6);
        return IMoocJSONResult.ok(pagedResult);
    }

    @ApiOperation("查询视频评论接口")
    @PostMapping("/getVideoComments")
    public IMoocJSONResult getVideoComments(String videoId, Integer page){
        if (page == null){
            page = 1;
        }
        PagedResult pagedResult = videoService.getVideoComments(videoId,page, 20);
        return IMoocJSONResult.ok(pagedResult);
    }

    @ApiOperation("热搜词查询接口")
    @PostMapping("/hot")
    public IMoocJSONResult hot(){
        List<String> hotWords = videoService.getHotWords();
        return IMoocJSONResult.ok(hotWords);
    }

    @ApiOperation("用户喜欢接口")
    @PostMapping("/userLike")
    public IMoocJSONResult userLike(String userId, String videoId, String createUserId){
        videoService.userLikeVideo(userId, videoId, createUserId);
        return IMoocJSONResult.ok();
    }

    @ApiOperation("用户取消喜欢接口")
    @PostMapping("/userUnlike")
    public IMoocJSONResult userUnlike(String userId, String videoId, String createUserId){
        videoService.userUnlikeVideo(userId, videoId, createUserId);
        return IMoocJSONResult.ok();
    }

    @ApiOperation("查询视频发布者信息接口")
    @PostMapping("/queryPublisher")
    public IMoocJSONResult queryPublisher(String loginUserId, String videoId, String publisherId) {

        if (StringUtils.isBlank(publisherId)) {
            return IMoocJSONResult.errorMsg("发布者信息为空，系统出错！");
        }

        Users publisher = userService.queryUserInfo(publisherId);
        UsersVO publisherVo = new UsersVO();
        BeanUtils.copyProperties(publisher, publisherVo);

        boolean isUserLikeVideo = userService.isUserLikeVideo(loginUserId, videoId);

        PublisherVideo publisherVideo = new PublisherVideo();
        publisherVideo.setPublisher(publisherVo);
        publisherVideo.setUserLikeVideo(isUserLikeVideo);

        return IMoocJSONResult.ok(publisherVideo);

    }

    @ApiOperation("保存评论接口")
    @PostMapping("/saveComment")
    public IMoocJSONResult saveComment(String fatherCommentId, String toUserId, @RequestBody Comments comments){
        String fromUserId = comments.getFromUserId();
        String videoId = comments.getVideoId();
        String comment = comments.getComment();
        int result = videoService.saveComment(fatherCommentId, toUserId,fromUserId, videoId, comment);
        if (result == 0){
            return IMoocJSONResult.errorMsg("系统错误，评论保存失败");
        }else {
            return IMoocJSONResult.ok();
        }
    }

}
