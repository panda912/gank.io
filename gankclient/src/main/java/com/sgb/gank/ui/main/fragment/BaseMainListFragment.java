package com.sgb.gank.ui.main.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sgb.gank.R;
import com.sgb.gank.data.main.module.MainListResBody;
import com.sgb.gank.data.main.source.MainConstant;
import com.sgb.gank.data.main.source.MainListRepository;
import com.sgb.gank.data.main.source.local.MainLocalDataSource;
import com.sgb.gank.data.main.source.remote.ApiService;
import com.sgb.gank.data.main.source.remote.MainRemoteDataSource;
import com.sgb.gank.databinding.FragmentMainBinding;
import com.sgb.gank.net.RetrofitService;
import com.sgb.gank.net.exception.BizException;
import com.sgb.gank.ui.BaseFragment;
import com.sgb.gank.ui.main.MainContract;
import com.sgb.gank.ui.main.MainPresenter;
import com.sgb.gank.util.PLog;
import com.youzan.titan.TitanRecyclerView;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by panda on 2016/10/21 下午1:31.
 */
public abstract class BaseMainListFragment extends BaseFragment implements MainContract.View {

    protected MainContract.Presenter mPresenter;

    private FragmentMainBinding binding;

    protected MainListAdapter mAdapter;

    protected boolean isFirstLoad = true;

    protected int mReqPage = 1;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final SwipeRefreshLayout swipeRefreshLayout = binding.swipeRefreshLayout;
        TitanRecyclerView recyclerView = binding.recyclerView;

        recyclerView.setLayoutManager(getLayoutManager());
        recyclerView.setAdapter(mAdapter = new MainListAdapter(getContext()));
        recyclerView.setOnLoadMoreListener(new TitanRecyclerView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                request();
            }
        });

        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                resetData();
                request();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        mPresenter = new MainPresenter(this, MainListRepository.getInstance(MainLocalDataSource.getInstance(getContext().getApplicationContext()), MainRemoteDataSource.getInstance()));
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onVisible() {
        if (isFirstLoad) {
            request();
        }
    }

    public void requestData(@MainConstant.Category String category, int count, int reqPage) {
        RetrofitService.getInstance().createService(ApiService.class)
                .getDataList(category, count, reqPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Function<MainListResBody, Flowable<MainListResBody>>() {
                    @Override
                    public Flowable<MainListResBody> apply(MainListResBody resBody) throws Exception {
                        if (resBody.error) {
                            return Flowable.error(new BizException(resBody.msg));
                        }
                        return Flowable.just(resBody);
                    }
                })
                .subscribe(new Consumer<MainListResBody>() {
                    @Override
                    public void accept(MainListResBody resBody) throws Exception {
                        mAdapter.addDataEnd(resBody.results);
                        mReqPage++;

                        if (isFirstLoad) {
                            isFirstLoad = false;
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        PLog.e("onError", throwable.getMessage());
                    }
                });
    }

    public void resetData() {
        mReqPage = 1;
        mAdapter.clearData();
    }

    public abstract RecyclerView.LayoutManager getLayoutManager();

    public abstract void request();

}
