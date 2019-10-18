package com.imooc.pojo;

import java.util.Date;

public class Comments {
    private String id;

    private String father_comment_id;

    private String to_user_id;

    private String video_id;

    private String from_user_id;

    private Date create_time;

    private String comment;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getFather_comment_id() {
        return father_comment_id;
    }

    public void setFather_comment_id(String father_comment_id) {
        this.father_comment_id = father_comment_id == null ? null : father_comment_id.trim();
    }

    public String getTo_user_id() {
        return to_user_id;
    }

    public void setTo_user_id(String to_user_id) {
        this.to_user_id = to_user_id == null ? null : to_user_id.trim();
    }

    public String getVideo_id() {
        return video_id;
    }

    public void setVideo_id(String video_id) {
        this.video_id = video_id == null ? null : video_id.trim();
    }

    public String getFrom_user_id() {
        return from_user_id;
    }

    public void setFrom_user_id(String from_user_id) {
        this.from_user_id = from_user_id == null ? null : from_user_id.trim();
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment == null ? null : comment.trim();
    }
}