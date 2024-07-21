package io.github.haoyiwen.jinritoutiao.relations;

import android.content.Context;
import android.webkit.JavascriptInterface;

import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

public class ShowPicRelation {

    private static final String TAG = ShowPicRelation.class.getSimpleName();
    private Context mContext;

    private List<String> mUrls = new ArrayList();
    public ShowPicRelation(Context mContext) {
        mContext = mContext;
    }

    @JavascriptInterface
    public void openImg(String url){
    }

    public void getImageArr(String urlArray){
        Logger.i(TAG, urlArray);
        String[] urls = urlArray.split(",");
        for (String url : urls) {
            mUrls.add(url);
        }
    }
}
