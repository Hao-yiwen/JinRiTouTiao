package io.github.haoyiwen.jinritoutiao.api;

import com.google.gson.GsonBuilder;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import io.github.haoyiwen.jinritoutiao.app.ApiConstant;
import io.github.haoyiwen.jinritoutiao.app.TouTiaoApp;
import io.github.haoyiwen.jinritoutiao.utils.NetWorkUtils;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * @data 2024/07/16
 */
public class ApiRetrofit {
    private static ApiRetrofit mApiRetrofit;

    private final Retrofit mRetrofit;

    private OkHttpClient mClient;

    private ApiService mApiservice;

    // 缓存配置
    private Interceptor mChannelInterceptor = chain -> {
        CacheControl.Builder cacheBuilder = new CacheControl.Builder();
        cacheBuilder.maxAge(0, TimeUnit.SECONDS);
        cacheBuilder.maxStale(365, TimeUnit.DAYS);
        CacheControl cacheControl = cacheBuilder.build();

        Request request = chain.request();
        if (!NetWorkUtils.isNetworkAvailable(TouTiaoApp.getmContext())) {
            request = request.newBuilder()
                    .cacheControl(cacheControl)
                    .build();
        }

        Response originalResponse = chain.proceed(request);
        if (NetWorkUtils.isNetworkAvailable(TouTiaoApp.getmContext())) {
            int maxAge = 0;
            return originalResponse.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public ,max-age=" + maxAge)
                    .build();
        } else {
            int maxStale = 60 * 60 * 24 + 28;
            return originalResponse.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                    .build();
        }
    };

    private Interceptor mLogInterceptor = chain -> {
        Request request = chain.request();
        long startTime = System.currentTimeMillis();
        okhttp3.Response response = chain.proceed(chain.request());
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        okhttp3.MediaType mediaType = response.body().contentType();
        String content = request.body().toString();
        Logger.e("----------Request Start----------------");
        Logger.e("| " + request.toString());
        Logger.json("| Response:" + content);
        Logger.e("----------Request End:" + duration + "毫秒----------");
        return response.newBuilder()
                .body(okhttp3.ResponseBody.create(mediaType, content))
                .build();
    };

    private Interceptor mHeaderInterceptor = chain -> {
        Request.Builder builder = chain.request().newBuilder();
        builder.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.108 Safari/537.36 2345Explorer/8.0.0.13547");
        builder.addHeader("Cache-Control", "max-age=0");
        builder.addHeader("Upgrade-Insecure-Requests", "1");
        builder.addHeader("X-Requested-With", "XMLHttpRequest");
        builder.addHeader("Cookie", "uuid=\"w:f2e0e469165542f8a3960f67cb354026\"; __tasessionId=4p6q77g6q1479458262778; csrftoken=7de2dd812d513441f85cf8272f015ce5; tt_webid=36385357187");
        return chain.proceed(builder.build());
    };

    public ApiRetrofit(){
        File httpCacheDirectory = new File(TouTiaoApp.getmContext().getCacheDir(), "response");
        int cacheSize = 10 * 1024 * 1024; // 10mb
        Cache cache = new Cache(httpCacheDirectory, cacheSize);

        mClient = new OkHttpClient.Builder()
                .addInterceptor(mHeaderInterceptor)
                .addInterceptor(mLogInterceptor)
                .cache(cache)
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .build();

        mRetrofit = new Retrofit.Builder()
                .baseUrl(ApiConstant.BASE_SERVER_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .client(mClient)
                .build();

        mApiservice = mRetrofit.create(ApiService.class);
    }


    public static ApiRetrofit getInstance(){
        if(mApiRetrofit == null){
            synchronized (Object.class){
                if(mApiRetrofit == null){
                    mApiRetrofit = new ApiRetrofit();
                }
            }
        }
        return mApiRetrofit;
    }

    public ApiService getmApiservice(){
        return mApiservice;
    }
}
