package io.github.haoyiwen.jinritoutiao.relations;

import android.content.Context;
import android.content.Intent;
import android.webkit.JavascriptInterface;

import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import io.github.haoyiwen.jinritoutiao.ui.activities.ImageViewPagerActivity;

public class ShowPicRelation {

    private static final String TAG = ShowPicRelation.class.getSimpleName();
    private Context mContext;

    private List<String> mUrls = new ArrayList();

    public ShowPicRelation(Context mContext) {
        this.mContext = mContext;
    }

    @JavascriptInterface
    public void openImg(String url) {
        Intent intent = new Intent(mContext, ImageViewPagerActivity.class);
        intent.putExtra(ImageViewPagerActivity.Position, mUrls.indexOf(url));
        intent.putStringArrayListExtra(ImageViewPagerActivity.IMG_URLS, (ArrayList<String>) mUrls);
        mContext.startActivity(intent);
    }

    @JavascriptInterface
    public void getImgArray(String urlArray) {
        Logger.i(TAG, urlArray);
        String[] urls = urlArray.split(",");
        for (String url : urls) {
            mUrls.add(url);
        }
    }
}
