package com.sgb.gank.data.search.source.remote;

import com.sgb.gank.data.search.module.SearchListObj;
import com.sgb.gank.data.search.source.SearchDataSource;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.reactivestreams.Publisher;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by panda on 2017/2/13 上午11:32.
 */
public class SearchRemoteDataSource implements SearchDataSource {

    private static SearchRemoteDataSource sInstance;

    private SearchRemoteDataSource() {
    }

    public static SearchRemoteDataSource getInstance() {
        if (sInstance == null) {
            sInstance = new SearchRemoteDataSource();
        }
        return sInstance;
    }

    @Override
    public Flowable<List<SearchListObj>> getSearchList(String keyword) {
        return Flowable.just(keyword)
                .map(s -> "http://gank.io/search?q=" + s.replace(" ", "+"))
                .map(s -> {
                    Document doc = Jsoup.parse(new URL(s), 30000);
                    return doc.select("div.container.content > ol > li");
                })
                .flatMap(new Function<Elements, Publisher<List<SearchListObj>>>() {
                    @Override
                    public Publisher<List<SearchListObj>> apply(Elements elements) throws Exception {
                        List<SearchListObj> searchList = new ArrayList<>();

                        for (Element element : elements) {
                            String title = element.select("a").text();
                            String url = element.select("a").attr("abs:href");
                            String author = element.select("small.u-pull-right").text();
                            String type = element.select("small:not(.u-pull-right)").text();

                            SearchListObj obj = new SearchListObj();
                            obj.title = title;
                            obj.author = author;
                            obj.type = type;
                            obj.url = url;

                            searchList.add(obj);
                        }
                        return Flowable.just(searchList);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void saveSearchList(String keyword, List<SearchListObj> list) {
        //do nothing
    }

}
