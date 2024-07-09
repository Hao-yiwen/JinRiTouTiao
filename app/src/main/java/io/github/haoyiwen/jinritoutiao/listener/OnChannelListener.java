package io.github.haoyiwen.jinritoutiao.listener;

public interface OnChannelListener {
    void onItemMove(int starPos, int endPos);

    void onMoveToMyChannel(int starPos, int endPos);

    void onMoveToOtherChannel(int starPos, int endPos);
}
