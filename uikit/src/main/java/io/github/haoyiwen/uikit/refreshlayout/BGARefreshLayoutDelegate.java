package io.github.haoyiwen.uikit.refreshlayout;

public interface BGARefreshLayoutDelegate {
    /**
     * 下啦刷新控件可见时 处理上下拉进度
     *
     * @param moveYdistance 珍格格下拉控件paddingTop的变化
     * @oaram scale 下啦过程中0到1 回弹过程是1到0 没有加上弹簧移动时的比例
     */

    void onRefreshScaleChanged(float scale, int moveYdistance);

    void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout bgaRefreshLayout);

    boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout bgaRefreshLayout);
}
