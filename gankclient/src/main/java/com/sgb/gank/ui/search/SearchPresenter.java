package com.sgb.gank.ui.search;

import com.sgb.gank.data.search.module.SearchListObj;
import com.sgb.gank.data.search.source.SearchRepository;
import com.sgb.gank.data.search.source.remote.SearchRemoteDataSource;

import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * Created by panda on 2016/11/16 上午11:39.
 */
public class SearchPresenter implements SearchContract.Presenter {

    private SearchContract.View mSearchView;

    private SearchRepository mRepository;

    public SearchPresenter(SearchContract.View searchView) {
        mSearchView = searchView;
        mSearchView.setPresenter(this);

        mRepository = SearchRepository.getInstance(SearchRemoteDataSource.getInstance());
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }

    @Override
    public void loadResults(String keyword) {
//        mRepository.getSearchList(keyword, new SearchDataSource.SearchCallback() {
//            @Override
//            public void onSuccess(List<SearchListObj> list) {
//                mSearchView.showResults(list);
//            }
//
//            @Override
//            public void onFailure(String msg) {
//
//            }
//        });

        mRepository.getSearchList(keyword)
                .subscribe(new Consumer<List<SearchListObj>>() {
                    @Override
                    public void accept(List<SearchListObj> searchList) throws Exception {
                        if (!searchList.isEmpty()) {
                            mSearchView.showResults(searchList);
                        } else {
                            mSearchView.showNoResult();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mSearchView.showError(throwable.getMessage());
                    }
                });
    }

    @Override
    public void cancelLoadTask() {

    }

    @Override
    public void openDetail(SearchListObj obj) {
        mSearchView.showDetail(obj.url);
    }

//    private static class ParseHtmlTask extends AsyncTask<String, Integer, List<SearchListObj>> {
//
//        WeakReference<SearchContract.View> mWRSearchView;
//
//        ParseHtmlTask(SearchContract.View mSearchView) {
//            this.mWRSearchView = new WeakReference<>(mSearchView);
//        }
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//        }
//
//        @Override
//        protected List<SearchListObj> doInBackground(String... params) {
//            List<SearchListObj> searchList = new ArrayList<>();
//            Document doc;
//            try {
//                String keyword = params[0];
//                keyword = keyword.replace(" ", "+");
//                doc = Jsoup.parse(new URL("http://gank.io/search?q=" + keyword), 5000);
//                if (isCancelled()) {
//                    return searchList;
//                }
//                Elements list = doc.select("div.container.content > ol > li");
//                if (isCancelled()) {
//                    return searchList;
//                }
//                for (int i = 0, size = list.size(); i < size; i++) {
//                    if (isCancelled()) {
//                        searchList.clear();
//                        return searchList;
//                    }
//
//                    String title = list.get(i).select("a").text();
//                    String url = list.get(i).select("a").attr("abs:href");
//                    String author = list.get(i).select("small.u-pull-right").text();
//                    String type = list.get(i).select("small:not(.u-pull-right)").text();
//
//                    SearchListObj obj = new SearchListObj();
//                    obj.title = title;
//                    obj.author = author;
//                    obj.type = type;
//                    obj.url = url;
//                    searchList.add(obj);
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            return searchList;
//        }
//
//        @Override
//        protected void onProgressUpdate(Integer... values) {
//            super.onProgressUpdate(values);
//        }
//
//        @Override
//        protected void onPostExecute(List<SearchListObj> searchList) {
//            mWRSearchView.get().showResults(searchList);
//        }
//
//        @Override
//        protected void onCancelled(List<SearchListObj> searchListObjs) {
//
//        }
//    }

}
