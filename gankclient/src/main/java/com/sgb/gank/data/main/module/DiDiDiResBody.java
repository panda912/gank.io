package com.sgb.gank.data.main.module;

import com.sgb.gank.net.BaseResponse;

import java.util.List;

/**
 * Created by panda on 16/9/7 下午5:08.
 */
public class DiDiDiResBody extends BaseResponse<List<DiDiDiResBody.ResultsObj>> {
    public int count;

    public static class ResultsObj {
        public String desc;
        public String ganhuo_id;
        public String publishedAt;
        public String readability;
        public String type;
        public String url;
        public String who;
    }
}
