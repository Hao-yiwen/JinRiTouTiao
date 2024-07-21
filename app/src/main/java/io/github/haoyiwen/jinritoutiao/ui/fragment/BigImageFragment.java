package io.github.haoyiwen.jinritoutiao.ui.fragment;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.viewbinding.ViewBinding;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.github.chrisbanes.photoview.OnPhotoTapListener;
import com.github.chrisbanes.photoview.PhotoView;
import com.orhanobut.logger.Logger;


import io.github.haoyiwen.jinritoutiao.base.BaseFragment;
import io.github.haoyiwen.jinritoutiao.base.BasePresenter;
import io.github.haoyiwen.jinritoutiao.databinding.FragmentBigImageBinding;
import io.github.haoyiwen.jinritoutiao.utils.GlideUtils;

public class BigImageFragment extends BaseFragment<BasePresenter, FragmentBigImageBinding> {
    public static final String IMG_URL = "imgUrl";

    PhotoView mIvPic;

//    CircleProgressView mCircleProgressView;

    @Override
    protected FragmentBigImageBinding initViewBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentBigImageBinding.inflate(inflater, container, false);
    }

    @Override
    public void initBinding() {
        mIvPic = binding.pvPic;
//        mCircleProgressView = binding.progressView;
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public void initListener() {
        mIvPic.setOnPhotoTapListener(new OnPhotoTapListener() {
            @Override
            public void onPhotoTap(ImageView view, float x, float y) {
                mActivity.finish();
            }
        });
    }

    @Override
    protected void loadData() {
        String imgUrl = getArguments().getString(IMG_URL);
        Logger.i("imgUrl:" + imgUrl);
        String[] urls = imgUrl.split(";");
        if (urls.length > 0) {
            GlideUtils.load(mActivity, urls[0], mIvPic);
        }

    }
}
