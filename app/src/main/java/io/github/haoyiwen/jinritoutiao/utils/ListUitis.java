package io.github.haoyiwen.jinritoutiao.utils;

import java.util.List;

public class ListUitis {
    public static boolean isEmpty(List list){
        if(list == null || list.size() == 0){
            return true;
        }
        return false;
    }
}
