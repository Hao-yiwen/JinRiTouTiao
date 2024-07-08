package io.github.haoyiwen.jinritoutiao.base;

import static com.github.anzewei.parallaxbacklayout.widget.ParallaxBackLayout.EDGE_MODE_DEFAULT;
import static com.github.anzewei.parallaxbacklayout.widget.ParallaxBackLayout.LAYOUT_COVER;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewbinding.ViewBinding;

import com.github.anzewei.parallaxbacklayout.ParallaxBack;
import com.github.anzewei.parallaxbacklayout.ParallaxHelper;
import com.github.anzewei.parallaxbacklayout.widget.ParallaxBackLayout;

import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import io.github.haoyiwen.jinritoutiao.ui.activities.MainActivity;
import io.github.haoyiwen.jinritoutiao.listener.PermissionListener;
import io.github.haoyiwen.jinritoutiao.utils.UIUtils;

/**
 * @author yw.hao
 * @description Activity基类
 * @date 2024/07/03
 */
public abstract class BaseActivity<T extends BasePresenter, VB extends ViewBinding> extends AppCompatActivity {
    protected T mPresenter;
    protected VB mBinding;

    private static long mPreTime;

    private static Activity mCurrentActivity;

    public static List<Activity> mActivities = new LinkedList<Activity>();

    protected Bundle savedInstanceState;

    public PermissionListener mPermissionListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(enableSlideClose()){
            ParallaxBackLayout layout = ParallaxHelper.getParallaxBackLayout(this, true);
            layout.setEdgeMode(EDGE_MODE_DEFAULT);
            layout.setEdgeFlag(getEdgeDirection());
            layout.setLayoutType(getSildeLayoutType(), null);

            layout.setSlideCallback(new ParallaxBackLayout.ParallaxSlideCallback() {
                @Override
                public void onStateChanged(int state) {
                    UIUtils.hideInput(getWindow().getDecorView());
                }

                @Override
                public void onPositionChanged(float percent) {

                }
            });
        }

        this.savedInstanceState = savedInstanceState;

        synchronized (mActivities) {
            mActivities.add(this);
        }

        mPresenter = createPresenter();

        mBinding = initViewBinding();

        setContentView(mBinding.getRoot());

        initView();
        initData();
        initListener();
    }

    public boolean enableSlideClose() {
        return true;
    }

    protected VB initViewBinding(){
        Class<VB> bindingClass = getBindingClass();
        try {
            Method inflateMethod = bindingClass.getMethod("inflate", LayoutInflater.class);
            return (VB) inflateMethod.invoke(null, getLayoutInflater());
        } catch (Exception e) {
            throw new RuntimeException("ViewBinding inflate failed!", e);
        }
    }

    protected abstract Class<VB> getBindingClass();

    public int getEdgeDirection() {
        return ParallaxBack.Edge.LEFT.getValue();
    }

    public int getSildeLayoutType() {
        return LAYOUT_COVER;
    }

    public void initView() {

    }

    public void initData() {
    }

    public void initListener() {
    }

    protected abstract T createPresenter();

    @Override
    protected void onResume() {
        super.onResume();
        mCurrentActivity = this;
    }

    @Override
    protected void onPause() {
        super.onPause();
        mCurrentActivity = null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        synchronized (mActivities) {
            mActivities.remove(this);
        }

        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }

    public static void exitApp() {
        ListIterator<Activity> iterator = mActivities.listIterator();

        while (iterator.hasNext()) {
            Activity next = iterator.next();
            next.finish();
        }
    }

    public static Activity getCurrentActivity() {
        return mCurrentActivity;
    }

    @Override
    public void onBackPressed() {
        if (mCurrentActivity instanceof MainActivity) {
            long currentTime = System.currentTimeMillis();
            if (currentTime - mPreTime > 2000) {
                mPreTime = currentTime;
                Toast.makeText(mCurrentActivity, "再按一次，退出应用", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        super.onBackPressed();
    }

    public boolean isEventBusRegisted(Object subscribe) {
        return EventBus.getDefault().isRegistered(subscribe);
    }

    public void registerEventBus(Object subscribe) {
        if(!isEventBusRegisted(subscribe)) {
            EventBus.getDefault().register(subscribe);
        }
    }

    public void unregisterEventBus(Object subscribe) {
        if(isEventBusRegisted(subscribe)) {
            EventBus.getDefault().unregister(subscribe);
        }
    }

    public void requestRuntimePermission(String[] permissions, PermissionListener listener) {
        mPermissionListener = listener;
        List<String> permissionList = new ArrayList<>();
        for (String permission: permissions){
            if(ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED){
                permissionList.add(permission);
            }
        }

        if(permissionList.size() > 0){
            ActivityCompat.requestPermissions(this, permissionList.toArray(new String[permissionList.size()]), 1);
        } else {
            listener.onGranted();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){
            case 1:
                if(grantResults.length >0){
                    List<String> deniedPermissions = new ArrayList<>();
                    for(int i = 0; i < grantResults.length; i++){
                        int grantResult = grantResults[i];
                        String permission = permissions[i];
                        if(grantResult != PackageManager.PERMISSION_GRANTED){
                            deniedPermissions.add(permission);
                        }
                    }
                    if(deniedPermissions.isEmpty()){
                        mPermissionListener.onGranted();
                    } else {
                        mPermissionListener.inDenied(deniedPermissions);
                    }
                }
                break;
        }
    }
}
