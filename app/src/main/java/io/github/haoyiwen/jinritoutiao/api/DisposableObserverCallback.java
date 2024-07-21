package io.github.haoyiwen.jinritoutiao.api;

import android.text.TextUtils;

import com.orhanobut.logger.Logger;

import io.github.haoyiwen.jinritoutiao.model.response.ResultRespose;
import io.github.haoyiwen.jinritoutiao.utils.UIUtils;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.observers.DisposableObserver;

public abstract class DisposableObserverCallback<T> extends DisposableObserver<ResultRespose<T>> {
    @Override
    public void onNext(@NonNull ResultRespose<T> response) {
        boolean isSuccess = (!TextUtils.isEmpty(response.message) && response.message.equals("success"))
                || !TextUtils.isEmpty(response.success) && response.success.equals("true");
        if (isSuccess) {
            onSuccess((T) response.data);
        } else {
            UIUtils.showToast(response.message);
            onFailure(response);
        }
    }

    private void onFailure(ResultRespose<T> response) {
    }

    protected abstract void onSuccess(T data);


    @Override
    public void onError(@NonNull Throwable e) {
        Logger.e(e.getLocalizedMessage());
        onError();
    }

    protected abstract void onError();

    @Override
    public void onComplete() {
    }
}
