package com.imooc.controller;

import com.imooc.enums.VideoStatusEnum;
import com.imooc.pojo.Bgm;
import com.imooc.service.VideoService;
import com.imooc.utils.IMoocJSONResult;
import com.imooc.utils.PagedResult;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@Controller
@RequestMapping("video")
public class VideoController {

    @Autowired
    private VideoService videoService;

    @GetMapping("showBgmList")
    public String showBgmList() {
        return "video/bgmList";
    }

    @GetMapping("showAddBgm")
    public String showAddBgm() {
        return "video/addBgm";
    }

    @GetMapping("showReportList")
    public String showReportList() {
        return "video/reportList";
    }

    @PostMapping("queryBgmList")
    @ResponseBody
    public PagedResult queryBgmList(Integer page){
        PagedResult result = videoService.queryBgmList(page,10);
        return result;
    }

    @PostMapping("reportList")
    @ResponseBody
    public PagedResult reportList(Integer page) {
        PagedResult result = videoService.queryReportList(page, 10);
        return result;
    }

    @PostMapping("addBgm")
    @ResponseBody
    public IMoocJSONResult addBgm(Bgm bgm){
        int result = videoService.addBgm(bgm);
        return result == 1 ? IMoocJSONResult.ok("bgm添加成功！") : IMoocJSONResult.errorMsg("添加失败！");
    }

    @PostMapping("delBgm")
    @ResponseBody
    public IMoocJSONResult delBgm(String bgmId){
        int result = videoService.delBgm(bgmId);
        return result == 1 ? IMoocJSONResult.ok("删除失败！") : IMoocJSONResult.errorMsg("删除失败！");
    }

    @PostMapping("forbidVideo")
    @ResponseBody
    public IMoocJSONResult forbidVideo(String videoId) {
        int result = videoService.updateVideoStatus(videoId, VideoStatusEnum.FORBID.value);
        return result == 1 ? IMoocJSONResult.ok() : IMoocJSONResult.errorMsg("禁播失败！");
    }

    @PostMapping("recoverVideo")
    @ResponseBody
    public IMoocJSONResult recoverVideo(String videoId) {
        int result = videoService.updateVideoStatus(videoId, VideoStatusEnum.SUCCESS.value);
        return result == 1 ? IMoocJSONResult.ok() : IMoocJSONResult.errorMsg("恢复失败！");
    }

    @PostMapping("bgmUpload")
    @ResponseBody
    public IMoocJSONResult bgmUpload(@RequestParam("file") MultipartFile files[]) {
        String fileSpace = "C:/imooc-videos-dev";
        String fileDB = "/bgm/";

        InputStream inputStream = null;
        FileOutputStream outputStream = null;

        try {
            if (files != null && files.length > 0) {
                String fileName = files[0].getOriginalFilename();
                if (StringUtils.isNotBlank(fileName)) {
                    String filePath = fileSpace + fileDB + fileName;
                    fileDB = fileDB + fileName;

                    File outputFile = new File(filePath);
                    if (outputFile.getParentFile() == null || !outputFile.getParentFile().isDirectory()) {
                        outputFile.getParentFile().mkdirs();
                    }

                    outputStream = new FileOutputStream(outputFile);
                    inputStream = files[0].getInputStream();
                    IOUtils.copy(inputStream, outputStream);

                } else {
                    return IMoocJSONResult.errorMsg("文件名为空！");
                }
            } else {
                return IMoocJSONResult.errorMsg("上传文件为空");
            }
        } catch (Exception e) {
            return IMoocJSONResult.errorMsg("上传出错，请重试！");
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
                if (inputStream != null) {
                    outputStream.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return IMoocJSONResult.ok(fileDB);
    }

}
