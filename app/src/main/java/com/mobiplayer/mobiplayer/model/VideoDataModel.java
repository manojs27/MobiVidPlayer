package com.mobiplayer.mobiplayer.model;

import android.net.Uri;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VideoDataModel {

    public static final VideoDataModel mSharedInstance = new VideoDataModel();

    private Map<Uri, Long> videoMap = new HashMap<>();

    private List<VideoData> mVideoList;

    public void setVideoList(List<VideoData> mVideoList) {
        this.mVideoList = mVideoList;
    }

    public List<VideoData> getVideoList() {
        return mVideoList;
    }

    public void setVideoSeekTime(Uri uri, long seekTime) {
        videoMap.put(uri, seekTime);
    }

    public long getSeekTime(Uri uri) {
        if (videoMap != null && videoMap.containsKey(uri)) {
            return videoMap.get(uri);
        }
        return 0;
    }
}
