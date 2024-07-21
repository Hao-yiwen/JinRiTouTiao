package io.github.haoyiwen.jinritoutiao.presenter;

import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.Subscribe;

import io.github.haoyiwen.jinritoutiao.api.DisposableObserverCallback;
import io.github.haoyiwen.jinritoutiao.base.BasePresenter;
import io.github.haoyiwen.jinritoutiao.constants.Constant;
import io.github.haoyiwen.jinritoutiao.model.response.CommentResponse;
import io.github.haoyiwen.jinritoutiao.model.response.NewDetail;
import io.github.haoyiwen.jinritoutiao.presenter.view.INewsDetailView;
import io.github.haoyiwen.jinritoutiao.presenter.view.INewsListView;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.observers.DisposableObserver;

public class NewsDetailPresenter extends BasePresenter<INewsDetailView> {
    public NewsDetailPresenter(INewsDetailView view) {
        super(view);
    }

    public void getNewsDetail(String mDetailUrl) {
        addSubscription(mApiService.getNewsDetail(mDetailUrl), new DisposableObserverCallback<NewDetail>() {
            @Override
            protected void onSuccess(NewDetail data) {
                mView.onGetNewsDetailSuccess(data);
            }

            @Override
            protected void onError() {
                mView.onError();
            }
        });
    }

    public void getComment(String mGroupId, String mItemId, int pageNow) {
        int offset = (pageNow - 1) * Constant.COMMENT_PAGE_SIZE;
        addSubscription(mApiService.getComment(mGroupId, mItemId, offset + "", String.valueOf(Constant.COMMENT_PAGE_SIZE)), new DisposableObserver<CommentResponse>() {
            @Override
            public void onNext(@NonNull CommentResponse commentResponse) {
                mView.onGetCommentSuccess(commentResponse);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                e.printStackTrace();
                Logger.e(e.getLocalizedMessage());
                mView.onError();
            }

            @Override
            public void onComplete() {

            }
        });
    }
}
