package io.github.haoyiwen.jinritoutiao.base;

import io.github.haoyiwen.jinritoutiao.api.ApiRetrofit;
import io.github.haoyiwen.jinritoutiao.api.ApiService;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.observers.DisposableObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public abstract class BasePresenter<V> {
    protected ApiService mApiService = ApiRetrofit.getInstance().getmApiservice();
    protected V mView;

    private CompositeDisposable compositeDisposable;

    public BasePresenter(V view) {
        attachView(view);
    }

    private void attachView(V view) {
        mView = view;
    }

    public void detachView() {
        mView = null;
        onUnsubscribe();
    }

    public <T> void addSubscription(Observable<T> observable, DisposableObserver<T> observer) {
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        Disposable disposable = observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer);
        compositeDisposable.add(disposable);
    }

    public void onUnsubscribe() {
        if (compositeDisposable != null && compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
    }
}
