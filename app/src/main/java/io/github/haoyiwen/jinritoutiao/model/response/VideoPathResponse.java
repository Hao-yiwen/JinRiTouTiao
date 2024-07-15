package io.github.haoyiwen.jinritoutiao.model.response;

import java.util.List;

/**
 * 视频解析返回
 * @data 2024/07/16
 * @location shanghai
 */
public class VideoPathResponse {
    public String status;
    public List<VideoEntity> video;

    public class VideoEntity{
        public String url;
    }
}
