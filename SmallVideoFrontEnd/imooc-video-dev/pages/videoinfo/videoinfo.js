var videoUtil = require('../../utils/videoUtil.js')

const app = getApp()

Page({
  data: {
    cover: "cover",
    src: "",
    serverUrl: "",
    videoInfo: {},
    faceUrl: "../resource/images/noneface.png",

    isHidden: true,
    isPlay: true,
    isMain: false,
    isBuffer: false,

    userLikeVideo: false,
    videoList: [],
    number: 0,
    page: 0,
    totalPage: 1,

    commentsPage: 1,
    commentsTotalPage: 1,
    commentsList: [],

    touchS: 0,
    touchE: 0,

    placeholder: "说点什么..."
  },

  videoCtx: {},

  onLoad: function(params) {
    var me = this;

    // 获取上一个页面传入的参数
    var videoInfo = JSON.parse(params.videoInfo);

    var height = videoInfo.videoHeight;
    var width = videoInfo.videoWidth;
    var cover = "cover";
    if (width >= height) {
      cover = "";
    }

    me.setData({
      src: app.serverUrl + videoInfo.videoPath,
      videoInfo: videoInfo,
      cover: cover,
      isMain: app.isVideoMain,
    });

    var serverUrl = app.serverUrl;
    var user = app.getGlobalUserInfo();
    var loginUserId = "";
    if (user != null && user != undefined && user != '') {
      loginUserId = user.id;
    }
    wx.request({
      url: serverUrl + '/video/queryPublisher?loginUserId=' + loginUserId + "&videoId=" + videoInfo.id + "&publisherId=" + videoInfo.userId,
      method: 'POST',
      success: function(res) {

        var publisher = res.data.data.publisher;
        var userLikeVideo = res.data.data.userLikeVideo;

        if (publisher.faceImage != null && publisher.faceImage != '' && publisher.faceImage != undefined) {
          me.setData({
            faceUrl: serverUrl + publisher.faceImage,
          });
        }

        var newvideoList = me.data.videoList;

        me.setData({
          videoList: newvideoList.concat(videoInfo),
          serverUrl: serverUrl,
          publisher: publisher,
          userLikeVideo: userLikeVideo
        });
      }
    })

    me.getCommentsList(1);
  },

  onReady: function() {
    this.videoCtx = wx.createVideoContext("myVideo");
  },

  onShow: function() {
    var me = this;
    app.isVideoMain = false;
  },

  onUnload: function() {
    var me = this;
    if (me.data.isMain == true) {
      app.isVideoMain = true;
    }
  },

  showSearch: function() {
    wx.navigateTo({
      url: '../searchVideo/searchVideo',
    })
  },

  showPublisher: function() {
    var me = this;

    var user = app.getGlobalUserInfo();

    var videoInfo = me.data.videoInfo;
    var realUrl = '../mine/mine#publisherId@' + videoInfo.userId;

    if (user == null || user == undefined || user == '') {
      wx.navigateTo({
        url: '../userLogin/login?redirectUrl=' + realUrl,
      })
    } else {
      wx.navigateTo({
        url: '../mine/mine?publisherId=' + videoInfo.userId,
      })
    }

  },

  getAllVideoList: function(page, videoId) {
    var me = this;
    var serverUrl = app.serverUrl;
    wx.showLoading({
      title: '请等待，加载中...',
    });

    wx.request({
      url: serverUrl + '/video/showall?page=' + page + "&isSaveRecord=0",
      method: "POST",
      data: {
        id: videoId,
      },
      success: function(res) {
        wx.hideLoading();
        wx.hideNavigationBarLoading();

        var videoList = res.data.data.rows;
        var newVideoList = me.data.videoList;

        me.setData({
          videoList: newVideoList.concat(videoList),
          page: page,
          totalPage: res.data.data.total,
          serverUrl: serverUrl
        });

        me.resetting();
      }
    })
  },

  pullup: function() {
    var me = this;

    if (me.data.number == (me.data.videoList.length - 1)) {
      if (me.data.totalPage == me.data.page) {
        wx.showToast({
          title: '已经没有视频啦~~',
          icon: "none"
        });
        return;
      }
      var page = me.data.page + 1;
      me.getAllVideoList(page, me.data.videoList[0].id);
    } else {
      me.resetting();
    }
  },

  pulldown: function() {
    var me = this;
    var number = me.data.number - 1;
    var videoInfo = me.data.videoList[number];

    if (number == -1) {
      wx.showToast({
        title: '到顶了~~',
        icon: "none"
      });
      return;
    } else {
      me.setData({
        isPlay: true,
        number: number,
        src: app.serverUrl + videoInfo.videoPath,
        commentsList:[],
        videoInfo: videoInfo,
      });
      me.getCommentsList(1);
    }
  },

  touchStart: function(e) {
    this.data.touchS = e.touches[0].pageY;
  },
  touchMove: function(e) {
    this.data.touchE = e.touches[0].pageY;
  },
  touchEnd: function(e) {
    var me = this;
    if (me.data.isBuffer && me.data.isHidden) {
      me.data.touchE = 0;
      return;
    }
    if(me.data.touchE != 0){
      if (!me.data.isHidden) {
        me.setData({
          isHidden: true,
          placeholder: "说点什么...",
          replyFatherCommentId: null,
          replyToUserId: null,
          commentFocus: false,
        });
      } else {
        if ((me.data.touchS - me.data.touchE) > 50) {
          me.pullup();
        } else if ((me.data.touchE - me.data.touchS) > 50) {
          me.pulldown();
        }
      }
    }
    me.setData({
      touchE: 0,
    })
  },


  upload: function() {
    var me = this;

    var user = app.getGlobalUserInfo();

    var videoInfo = JSON.stringify(me.data.videoInfo);
    var realUrl = '../videoinfo/videoinfo#videoInfo@' + videoInfo;

    if (user == null || user == undefined || user == '') {
      wx.navigateTo({
        url: '../userLogin/login?redirectUrl=' + realUrl,
      })
    } else {
      videoUtil.uploadVideo();
    }

  },

  showIndex: function() {
    wx.reLaunch({
      url: '../index/index',
    })
  },

  showMine: function() {
    var user = app.getGlobalUserInfo();

    if (user == null || user == undefined || user == '') {
      wx.navigateTo({
        url: '../userLogin/login',
      })
    } else {
      wx.navigateTo({
        url: '../mine/mine',
      })
    }
  },

  likeVideoOrNot: function() {
    var me = this;
    var videoInfo = me.data.videoInfo;
    var user = app.getGlobalUserInfo();

    if (user == null || user == undefined || user == '') {
      wx.navigateTo({
        url: '../userLogin/login',
      });
    } else {

      var userLikeVideo = me.data.userLikeVideo;
      var url = '/video/userLike?userId=' + user.id + '&videoId=' + videoInfo.id + '&createUserId=' + videoInfo.userId;
      if (userLikeVideo) {
        url = '/video/userUnlike?userId=' + user.id + '&videoId=' + videoInfo.id + '&createUserId=' + videoInfo.userId;
      }

      var serverUrl = app.serverUrl;
      wx.showLoading({
        title: '...',
      });
      wx.request({
        url: serverUrl + url,
        method: 'POST',
        header: {
          'content-type': 'application/json', // 默认值
          'headerUserId': user.id,
          'headerUserToken': user.userToken
        },
        success: function(res) {
          wx.hideLoading();
          if (res.data.status == 200) {
            me.setData({
              userLikeVideo: !userLikeVideo
            });
          } else if (res.data.status == 502) {
            wx.showToast({
              title: res.data.msg,
              duration: 1000,
              icon: "none",
              success: function() {
                wx.navigateTo({
                  url: '../userLogin/login',
                })
              }
            });
          }
        }
      });
    }
  },

  shareMe: function() {
    var me = this;
    var user = app.getGlobalUserInfo();

    wx.showActionSheet({
      itemList: ['下载到本地', '举报用户', '分享到朋友圈', '分享到QQ空间', '分享到微博'],
      success: function(res) {
        if (res.tapIndex == 0) {
          // 下载
          wx.showLoading({
            title: '下载中...',
          })
          wx.downloadFile({
            url: app.serverUrl + me.data.videoInfo.videoPath,
            success: function(res) {
              // 只要服务器有响应数据，就会把响应内容写入文件并进入 success 回调，业务需要自行判断是否下载到了想要的内容
              if (res.statusCode === 200) {
                wx.saveVideoToPhotosAlbum({
                  filePath: res.tempFilePath,
                  success: function(res) {
                    wx.hideLoading();
                  }
                })
              }
            }
          })
        } else if (res.tapIndex == 1) {
          // 举报
          var videoInfo = JSON.stringify(me.data.videoInfo);
          var realUrl = '../videoinfo/videoinfo#videoInfo@' + videoInfo;

          if (user == null || user == undefined || user == '') {
            wx.navigateTo({
              url: '../userLogin/login?redirectUrl=' + realUrl,
            })
          } else {
            var publishUserId = me.data.videoInfo.userId;
            var videoId = me.data.videoInfo.id;
            var currentUserId = user.id;
            wx.navigateTo({
              url: '../report/report?videoId=' + videoId + "&publishUserId=" + publishUserId
            })
          }
        } else {
          wx.showToast({
            title: '官方暂未开放...',
          })
        }
      }
    })
  },

  onShareAppMessage: function(res) {

    var me = this;
    var videoInfo = me.data.videoInfo;

    return {
      title: '短视频内容分析',
      path: "pages/videoinfo/videoinfo?videoInfo=" + JSON.stringify(videoInfo)
    }
  },

  leaveComment: function() {
    this.setData({
      isHidden: false
    });
  },

  replyFocus: function(e) {
    var fatherCommentId = e.currentTarget.dataset.fathercommentid;
    var toUserId = e.currentTarget.dataset.touserid;
    var toNickname = e.currentTarget.dataset.tonickname;

    this.setData({
      placeholder: "回复  " + toNickname,
      replyFatherCommentId: fatherCommentId,
      replyToUserId: toUserId,
      commentFocus: true,
    });
  },

  saveComment: function(e) {
    var me = this;
    var content = e.detail.value;

    var fatherCommentId = e.currentTarget.dataset.replyfathercommentid;
    var toUserId = e.currentTarget.dataset.replytouserid;

    var user = app.getGlobalUserInfo();
    var videoInfo = JSON.stringify(me.data.videoInfo);
    var realUrl = '../videoinfo/videoinfo#videoInfo@' + videoInfo;

    if (user == null || user == undefined || user == '') {
      wx.navigateTo({
        url: '../userLogin/login?redirectUrl=' + realUrl,
      })
    } else {
      wx.showLoading({
        title: '请稍后...',
      })
      wx.request({
        url: app.serverUrl + '/video/saveComment?fatherCommentId=' + fatherCommentId + "&toUserId=" + toUserId,
        method: 'POST',
        header: {
          'content-type': 'application/json', // 默认值
          'headerUserId': user.id,
          'headerUserToken': user.userToken
        },
        data: {
          fromUserId: user.id,
          videoId: me.data.videoInfo.id,
          comment: content
        },
        success: function(res) {
          wx.hideLoading();

          me.setData({
            contentValue: "",
            commentsList: []
          });

          me.getCommentsList(1);
        }
      })
    }
  },

  getCommentsList: function(page) {
    var me = this;

    var videoId = me.data.videoInfo.id;

    wx.request({
      url: app.serverUrl + '/video/getVideoComments?videoId=' + videoId + "&page=" + page,
      method: "POST",
      success: function(res) {
        var commentsList = res.data.data.rows;
        var newCommentsList = me.data.commentsList;

        me.setData({
          commentsList: newCommentsList.concat(commentsList),
          commentsPage: page,
          commentsTotalPage: res.data.data.total
        });
      }
    })
  },

  commentReachBottom: function() {
    var me = this;
    var currentPage = me.data.commentsPage;
    var totalPage = me.data.commentsTotalPage;
    if (currentPage === totalPage) {
      wx.showToast({
        title: '没有评论了',
        duration: 3000,
        icon: "none",
      })
      return;
    }
    var page = currentPage + 1;
    wx.showLoading({
      title: '加载中',
    });
    me.getCommentsList(page);
    wx.hideLoading();
  },

  resetting: function() {
    var me = this;
    var number = me.data.number + 1;
    var videoInfo = me.data.videoList[number];
    me.setData({
      isPlay: true,
      number: number,
      src: app.serverUrl + videoInfo.videoPath,
      commentsList:[],
      videoInfo: videoInfo,
    });
    me.getCommentsList(1);
  },

  bindwaiting: function(e) {
    this.data.isBuffer = true;
  },
  bindplay: function(e) {
    this.data.isBuffer = false;
  },

  bindtap: function() {
    var me = this;
    if(me.data.isBuffer&&me.data.isHidden){
      return;
    }
    if(!me.data.isHidden){
      me.setData({
        isHidden:true,
      })
      return;
    }
    if (me.data.isPlay) {
      me.videoCtx.pause();
      me.setData({
        isPlay: false,
      })
    } else {
      me.videoCtx.play();
      me.setData({
        isPlay: true,
      })
    }
  }
})