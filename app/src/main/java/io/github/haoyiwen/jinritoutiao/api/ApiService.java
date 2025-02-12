package io.github.haoyiwen.jinritoutiao.api;

import io.github.haoyiwen.jinritoutiao.model.entity.VideoModel;
import io.github.haoyiwen.jinritoutiao.model.response.CommentResponse;
import io.github.haoyiwen.jinritoutiao.model.response.NewDetail;
import io.github.haoyiwen.jinritoutiao.model.response.NewsResponse;
import io.github.haoyiwen.jinritoutiao.model.response.ResultRespose;
import io.github.haoyiwen.jinritoutiao.model.response.VideoPathResponse;
import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface ApiService {

    String GET_ARTICLE_LIST = "api/news/feed/v62/?refer=1&count=20&loc_mode=4&device_id=34960436458&iid=13136511752";

    String GET_COMMENT_LIST = "article/v2/tab_comments/";

    /**
     * @param categroy 频道
     * @return
     */
    @GET(GET_ARTICLE_LIST)
    Observable<NewsResponse> getNewsList(@Query("category") String category, @Query("min_behot_time") long lastTime, @Query("last_refresh_sub_entrance_interval") long currentTime);


    /**
     * 获取新闻详情
     * @param url
     * @return
     */
    @GET
    Observable<ResultRespose<NewDetail>> getNewsDetail(@Url String url);

    /**
     * 获取评论数据
     * @param groupId
     * @param itemId
     * @param offset
     * @param count
     * @return
     */
    @GET(GET_COMMENT_LIST)
    Observable<CommentResponse> getComment(@Query("group_id") String groupId, @Query("item_id") String itemId, @Query("offset") String offset, @Query("count") String count);

    /**
     * 获取视频页的html代码
     */
    @GET
    Observable<String> getVideoHtml(@Url String url);

    /**
     * 获取视频页json数据
     * @param url
     * @return
     */

    @GET
    Observable<VideoModel> getVideoData(@Url String url);

    @Headers({
            "Content-Type:application/x-www-form-urlencoded; charset=UTF-8",
            "Cookie:PHPSESSIID=334267171504; _ga=GA1.2.646236375.1499951727; _gid=GA1.2.951962968.1507171739; Hm_lvt_e0a6a4397bcb500e807c5228d70253c8=1507174305;Hm_lpvt_e0a6a4397bcb500e807c5228d70253c8=1507174305; _gat=1",
            "Origin:http://toutiao.iiilab.com"
    })
    @POST("https://www.parsevideo.com/api.php")
    Observable<VideoPathResponse> parseVideo(@Query("url") String url, @Query("hash") String hash);
}
