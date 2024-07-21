package io.github.haoyiwen.jinritoutiao.ui.activities;

import android.graphics.Bitmap;
import android.media.Image;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import io.github.haoyiwen.jinritoutiao.R;
import io.github.haoyiwen.jinritoutiao.base.BaseActivity;
import io.github.haoyiwen.jinritoutiao.base.BasePresenter;
import io.github.haoyiwen.jinritoutiao.databinding.ActivityWebViewBinding;
import io.github.haoyiwen.jinritoutiao.utils.UIUtils;
import io.github.haoyiwen.uikit.statusbar.Eyes;

public class WebViewActivity extends BaseActivity<BasePresenter, ActivityWebViewBinding> {
    public static final String URL = "url";

    ImageView mIvBack;

    TextView mTvTitle;

    ProgressBar mPbLoading;

    WebView mWvContent;

    @Override
    protected Class getBindingClass() {
        return ActivityWebViewBinding.class;
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public void initView() {
        Eyes.setStatusBarColor(this, UIUtils.getColor(R.color.color_BDBDBD));
        mIvBack = mBinding.included.ivBack;
        mTvTitle = mBinding.included.tvAuthor;
        mPbLoading = mBinding.pbLoading;
        mWvContent = mBinding.wvContent;
    }

    @Override
    public void initData() {
        String url = getIntent().getStringExtra(URL);
        mWvContent.loadUrl(url);
    }

    @Override
    public void initListener() {
        WebSettings settings = mWvContent.getSettings();
        settings.setJavaScriptEnabled(true);

        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mWvContent.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                mPbLoading.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                mPbLoading.setVisibility(View.GONE);
            }
        });

        mWvContent.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                mPbLoading.setProgress(newProgress);
            }
        });

        mWvContent.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK && mWvContent.canGoBack()) {
                        mWvContent.goBack();   //后退
                        return true;    //已处理
                    }
                }
                return false;
            }
        });
    }
}
