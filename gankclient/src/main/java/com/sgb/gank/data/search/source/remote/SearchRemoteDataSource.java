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
import io.reactivex.functions.Consumer;
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
                    Document doc = Jsoup.parse(new URL(s), 5000);
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

    }


    @Override
    public void getSearchList(String keyword, SearchCallback callback) {
        if (callback == null) {
            return;
        }

        Flowable.just(keyword)
                .map(s -> "http://gank.io/search?q=" + s.replace(" ", "+"))
                .map(s -> {
                    Document doc = Jsoup.parse(new URL(s), 5000);
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
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<SearchListObj>>() {
                    @Override
                    public void accept(List<SearchListObj> searchList) throws Exception {
                        callback.onSuccess(searchList);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        callback.onFailure(throwable.getMessage());
                    }
                });

//        Observable.just(keyword)
//                .map(new fun<String, String>() {
//                    @Override
//                    public String call(String parseUrl) {
//                        return "http://gank.io/search?q=" + parseUrl.replace(" ", "+");
//                    }
//                })
//                .flatMap(new Func1<String, Observable<Elements>>() {
//                    @Override
//                    public Observable<Elements> call(String s) {
//                        try {
//                            Document doc = Jsoup.parse(new URL(s), 5000);
//                            Elements elements = doc.select("div.container.content > ol > li");
//                            return Observable.just(elements);
//                        } catch (IOException e) {
//                            return Observable.error(e);
//                        }
//                    }
//                })
//                .flatMap(new Func1<Elements, Observable<List<SearchListObj>>>() {
//                    @Override
//                    public Observable<List<SearchListObj>> call(Elements elements) {
//                        List<SearchListObj> searchList = new ArrayList<>();
//
//                        for (Element element : elements) {
//                            String title = element.select("a").text();
//                            String url = element.select("a").attr("abs:href");
//                            String author = element.select("small.u-pull-right").text();
//                            String type = element.select("small:not(.u-pull-right)").text();
//
//                            SearchListObj obj = new SearchListObj();
//                            obj.title = title;
//                            obj.author = author;
//                            obj.type = type;
//                            obj.url = url;
//
//                            searchList.add(obj);
//                        }
//                        return Observable.just(searchList);
//                    }
//                })
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<List<SearchListObj>>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        callback.onFailure(e.getMessage());
//                    }
//
//                    @Override
//                    public void onNext(List<SearchListObj> searchList) {
//                        callback.onSuccess(searchList);
//                    }
//                });
    }

}
