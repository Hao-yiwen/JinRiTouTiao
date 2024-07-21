package io.github.haoyiwen.jinritoutiao.presenter.view;

import io.github.haoyiwen.jinritoutiao.model.response.CommentResponse;
import io.github.haoyiwen.jinritoutiao.model.response.NewDetail;

public interface INewsDetailView {
    void onGetNewsDetailSuccess(NewDetail newDetail);

    void onGetCommentSuccess(CommentResponse response);

    void onError();
}
