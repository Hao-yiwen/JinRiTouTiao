package io.github.haoyiwen.jinritoutiao.ui.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.target.Target;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.FutureTask;

import io.github.haoyiwen.jinritoutiao.R;
import io.github.haoyiwen.jinritoutiao.base.BaseActivity;
import io.github.haoyiwen.jinritoutiao.base.BasePresenter;
import io.github.haoyiwen.jinritoutiao.databinding.ActivityImageViewPagerBinding;
import io.github.haoyiwen.jinritoutiao.listener.PermissionListener;
import io.github.haoyiwen.jinritoutiao.ui.fragment.BigImageFragment;
import io.github.haoyiwen.jinritoutiao.utils.FileUtils;
import io.github.haoyiwen.jinritoutiao.utils.UIUtils;
import io.github.haoyiwen.uikit.statusbar.Eyes;

public class ImageViewPagerActivity extends BaseActivity<BasePresenter, ActivityImageViewPagerBinding>  {

    public static final String TAG = ImageViewPagerActivity.class.getSimpleName();

    public static final String IMG_URLS = "mImageUrls";

    public static final String Position = "position";

    ViewPager2 mVpPics;

    TextView mTvIndicator;

    TextView mTvSave;

    private List<String> mImageUrls = new ArrayList<>();

    private List<BigImageFragment> mFragments = new ArrayList<>();

    private int mCurrentPosition;

    private Map<Integer, Boolean> mDownloadingFlagMap = new HashMap<>();

    @Override
    public void initView() {
        Eyes.translucentStatusBar(this);
        mVpPics = mBinding.vpPics;
        mTvIndicator = mBinding.tvIndicator;
        mTvSave = mBinding.tvSave;
    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        mImageUrls = intent.getStringArrayListExtra(IMG_URLS);
        int position = intent.getIntExtra(Position, 0);
        if (position > 0) {
            mCurrentPosition = position;
        } else {
            mCurrentPosition = 0;
        }

        for (int i = 0; i < mImageUrls.size(); i++) {
            String url = mImageUrls.get(i);
            BigImageFragment imageFragment = new BigImageFragment();

            Bundle bundle = new Bundle();
            bundle.putString(BigImageFragment.IMG_URL, url);
            imageFragment.setArguments(bundle);

            mFragments.add(imageFragment);
            mDownloadingFlagMap.put(i, false);

            mVpPics.setAdapter(new MyPageAdapter(this));
            mVpPics.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                }

                @Override
                public void onPageSelected(int position) {
                    mCurrentPosition = position;
                    setIndicator(mCurrentPosition);
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                    super.onPageScrollStateChanged(state);
                }
            });

            mVpPics.setCurrentItem(mCurrentPosition);
            setIndicator(mCurrentPosition);
        }
    }

    private void setIndicator(int position) {
        mTvIndicator.setText(position + 1 + "/" + mImageUrls.size());
    }

    @Override
    protected Class getBindingClass() {
        return ActivityImageViewPagerBinding.class;
    }

    @Override
    public void initListener() {
        mTvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestRuntimePermission(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, new PermissionListener() {
                    @Override
                    public void onGranted() {
                        downloadImg();
                    }

                    @Override
                    public void inDenied(List<String> deniedPermissions) {
                        UIUtils.showToast(getString(R.string.write_storage_permission_deny));
                    }
                });
            }
        });
    }

    private void downloadImg() {
        String imgUrl = mImageUrls.get(mCurrentPosition);
        Boolean isDownloading = mDownloadingFlagMap.get(mCurrentPosition);
        if (!isDownloading) {
            mDownloadingFlagMap.put(mCurrentPosition, true);
            new DownloadTask(mCurrentPosition).execute(imgUrl);
        }
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    class MyPageAdapter extends FragmentStateAdapter {

        public MyPageAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getItemCount() {
            return mFragments.size();
        }
    }

    private class DownloadTask extends AsyncTask<String, Integer, Void> {
        private int mPosition;

        public DownloadTask(int position) {
            mPosition = position;
        }

        // 在DownloadTask类中添加scanFile方法
        private void scanFile(String path) {
            MediaScannerConnection.scanFile(ImageViewPagerActivity.this, new String[]{path}, null,
                    new MediaScannerConnection.OnScanCompletedListener() {
                        @Override
                        public void onScanCompleted(String path, Uri uri) {
                            Logger.i("Scan completed for: " + path);
                        }
                    });
        }

        @Override
        protected Void doInBackground(String... params) {
            String url = params[0];
            if (TextUtils.isEmpty(url)) {
                return null;
            }
            String[] urls = url.split(";");
            String imgUrl = urls[0];
            File file = null;
            try {
                FutureTarget<File> future = Glide
                        .with(ImageViewPagerActivity.this)
                        .load(imgUrl)
                        .downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL);

                file = future.get();

                if (file == null) {
                    Logger.e("Downloaded file is null");
                    return null;
                }

                String filePath = file.getAbsolutePath();
                Logger.i("Downloaded file path: " + filePath);

                // 获取公共图片目录路径
                String destDirPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + File.separator + "JINRITouTiao";
                File destDir = new File(destDirPath);
                if (!destDir.exists()) {
                    if (!destDir.mkdirs()) {
                        Logger.e("Failed to create directory: " + destDir.getAbsolutePath());
                        return null;
                    }
                }

                String destFileName = System.currentTimeMillis() + ".jpg";
                File destFile = new File(destDir, destFileName);

                // 拷贝文件
                FileUtils.copy(file, destFile);
                Logger.i("destFile.getPath():" + destFile.getPath());

                // 确保媒体库更新
                scanFile(destFile.getAbsolutePath());

            } catch (Exception e) {
                Logger.e("Error during download and save: " + e.getMessage(), e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            mDownloadingFlagMap.put(mPosition, false);
            UIUtils.showToast("保存成功，图片所在文件夹:SD卡根路径/JINRITOUTIAO");
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            Logger.i(TAG, "progress: " + values[0]);
        }
    }
}


