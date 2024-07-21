package io.github.haoyiwen.jinritoutiao.ui.widget;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.orhanobut.logger.Logger;

import io.github.haoyiwen.jinritoutiao.R;
import io.github.haoyiwen.jinritoutiao.model.response.NewDetail;
import io.github.haoyiwen.jinritoutiao.relations.ShowPicRelation;
import io.github.haoyiwen.jinritoutiao.utils.GlideUtils;
import io.github.haoyiwen.jinritoutiao.utils.TimeUtils;

public class NewsDetailHeaderView extends FrameLayout {

    private static String TAG = "yiwen";

    TextView mTvTitle;

    public LinearLayout mLlInfo;

    ImageView mIvAvatar;

    TextView mTvAuthor;

    TextView mTvTime;

    WebView mWvContent;

    private Context mContext;

    public NewsDetailHeaderView(@NonNull Context context) {
        this(context, null);
    }

    public NewsDetailHeaderView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NewsDetailHeaderView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
    }

    private void initView() {
        inflate(getContext(), R.layout.header_news_detail, this);
        mTvTitle = findViewById(R.id.tvTitle);
        mLlInfo = findViewById(R.id.ll_info);
        mIvAvatar = findViewById(R.id.iv_avatar);
        mTvAuthor = findViewById(R.id.tv_author);
        mTvTime = findViewById(R.id.tv_time);
        mWvContent = findViewById(R.id.wv_content);
    }

    public void setDetail(NewDetail newDetail, LoadWebListener listener) {
        mWebListener = listener;

        mTvTitle.setText(newDetail.title);

        if (newDetail.media_user == null) {
            mLlInfo.setVisibility(GONE);
        } else {
            if (!TextUtils.isEmpty(newDetail.media_user.avatar_url)) {
                GlideUtils.loadRound(mContext, newDetail.media_user.avatar_url, mIvAvatar);
            }
            mTvAuthor.setText(newDetail.media_user.screen_name);
            mTvTime.setText(TimeUtils.getShortTime(newDetail.publish_time * 1000L));
        }

        if (TextUtils.isEmpty(newDetail.content)) {
            mWvContent.setVisibility(GONE);
        }

        mWvContent.getSettings().setJavaScriptEnabled(true);
        mWvContent.addJavascriptInterface(new ShowPicRelation(mContext), TAG);

        String htmlPart1 = "<!DOCTYPE HTML html>\n" +
                "<head><meta charset=\"utf-8\"/>\n" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, minimum-scale=1.0, user-scalable=no\"/>\n" +
                "</head>\n" +
                "<body>\n" +
                "<style> \n" +
                "img{width:100%!important;height:auto!important}\n" +
                " </style>";
        String htmlPart2 = "</body></html>";

        String html = htmlPart1 + newDetail.content + htmlPart2;

        Logger.e("content:" + newDetail.content);

        mWvContent.loadDataWithBaseURL(null, html, "text/html", "UTF-8", null);
        mWvContent.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                addJs(view);
                if (mWebListener != null) {
                    mWebListener.onLoadFinished();
                }
            }
        });
    }

    private void addJs(WebView wv) {
        wv.loadUrl("javascript:(function  pic(){" +
                "var imgList = \"\";" +
                "var imgs = document.getElementsByTagName(\"img\");" +
                "for(var i=0;i<imgs.length;i++){" +
                "var img = imgs[i];" +
                "imgList = imgList + img.src +\";\";" +
                "img.onclick = function(){" +
                "window.yiwen.openImg(this.src);" +
                "}" +
                "}" +
                "window.yiwen.getImgArray(imgList);" +
                "})()");
    }

    private LoadWebListener mWebListener;

    public interface LoadWebListener {
        void onLoadFinished();
    }
}
