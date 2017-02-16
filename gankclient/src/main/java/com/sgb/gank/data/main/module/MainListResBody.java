package com.sgb.gank.data.main.module;

import com.google.gson.annotations.SerializedName;
import com.sgb.gank.net.BaseResponse;

import java.util.List;

/**
 * Created by panda on 16/9/2 下午4:20.
 */
public class MainListResBody extends BaseResponse<List<MainListResBody.ResultsObj>> {
    public static class ResultsObj {
        @SerializedName("_id")
        public String id;
        public String createdAt;
        public String desc;
        public String publishedAt;
        public String source;
        public String type;
        public String url;
        public boolean used;
        public String who;
        public List<String> images;

        @Override
        public String toString() {
            return "ResultsObj{" +
                    "id='" + id + '\'' +
                    ", createdAt='" + createdAt + '\'' +
                    ", desc='" + desc + '\'' +
                    ", publishedAt='" + publishedAt + '\'' +
                    ", source='" + source + '\'' +
                    ", type='" + type + '\'' +
                    ", url='" + url + '\'' +
                    ", used=" + used +
                    ", who='" + who + '\'' +
                    ", images=" + images +
                    '}';
        }
    }

}
