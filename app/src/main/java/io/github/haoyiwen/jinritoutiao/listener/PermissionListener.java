package io.github.haoyiwen.jinritoutiao.listener;

import java.util.List;

public interface PermissionListener {

    void onGranted();

    void inDenied(List<String> deniedPermissions);
}
