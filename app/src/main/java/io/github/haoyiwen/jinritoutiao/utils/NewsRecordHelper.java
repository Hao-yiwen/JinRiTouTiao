package io.github.haoyiwen.jinritoutiao.utils;

import android.renderscript.Sampler;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.litepal.LitePal;
import org.litepal.crud.LitePalSupport;

import java.util.List;

import io.github.haoyiwen.jinritoutiao.model.entity.NewRecord;
import io.github.haoyiwen.jinritoutiao.model.entity.News;

public class NewsRecordHelper {
    private static Gson mGson = new Gson();

    public static NewRecord getLastNewsRecord(String mChnnelCode) {
        return LitePal.where("channelCode=?", mChnnelCode).findLast(NewRecord.class);
    }

    public static List<News> convertToNewsList(String json) {
        return mGson.fromJson(json, new TypeToken<List<News>>() {
        }.getType());
    }

    public static void save(String mChnnelCode, String json) {
        int page = 1;
        NewRecord lastNewRecord = getLastNewsRecord(mChnnelCode);
        if (lastNewRecord != null) {
            page = lastNewRecord.getPage() + 1;
        }

        NewRecord record = new NewRecord(mChnnelCode, page, json, System.currentTimeMillis());
        record.saveOrUpdate("channelCode=? and page=?", mChnnelCode, String.valueOf(page));
    }

    public static NewRecord getPreNewsRecord(String mChnnelCode, int page) {
        List<NewRecord> newsRecords = selectNewsRecords(mChnnelCode, page - 1);

        if(ListUitis.isEmpty(newsRecords)){
            return null;
        }
        return newsRecords.get(0);
    }

    private static List<NewRecord> selectNewsRecords(String mChnnelCode, int i) {
        return LitePal.where("channelCode = ? and page= ?", mChnnelCode, String.valueOf(i)).find(NewRecord.class);
    }
}
