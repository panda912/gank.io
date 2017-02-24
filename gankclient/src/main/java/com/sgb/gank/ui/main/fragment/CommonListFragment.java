package com.sgb.gank.ui.main.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sgb.gank.R;
import com.sgb.gank.data.main.module.MainListResBody;
import com.sgb.gank.data.main.source.MainConstant;
import com.sgb.gank.data.main.source.MainListRepository;
import com.sgb.gank.data.main.source.local.MainLocalDataSource;
import com.sgb.gank.data.main.source.remote.MainRemoteDataSource;
import com.sgb.gank.databinding.FragmentMainBinding;
import com.sgb.gank.ui.BaseFragment;
import com.sgb.gank.ui.main.MainContract;
import com.sgb.gank.ui.main.MainPresenter;
import com.sgb.gank.util.ToastUtils;
import com.youzan.titan.TitanRecyclerView;

import java.util.List;


/**
 * Created by panda on 2016/10/21 下午1:31.
 */
public class CommonListFragment extends BaseFragment implements MainContract.View {
    private static final String EXTRA_CATEGORY = "extra_category";
    private static final int REQ_COUNT = 20;

    protected MainContract.Presenter mPresenter;
    private FragmentMainBinding binding;
    protected MainListAdapter mAdapter;

    private String mCategory;
    private boolean isFirstLoad = true;
    private int mReqPage = 1;
    private boolean hasMore = true;


    public CommonListFragment() {
    }

    public static CommonListFragment newInstance(@MainConstant.Category String category) {
        CommonListFragment fragment = new CommonListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_CATEGORY, category);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCategory = getArguments().getString(EXTRA_CATEGORY);
        }
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

        switch (mCategory) {
            case MainConstant.CATEGORY_ANDROID:
            case MainConstant.CATEGORY_FRONTEND:
            case MainConstant.CATEGORY_IOS:
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                break;
            case MainConstant.CATEGORY_PIC:
                recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
                break;
            default:
                break;
        }
        recyclerView.setAdapter(mAdapter = new MainListAdapter(getContext()));
        recyclerView.setOnLoadMoreListener(new TitanRecyclerView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                mPresenter.loadDatas(mCategory, REQ_COUNT, mReqPage);
            }
        });

        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                resetData();
                mPresenter.loadDatas(mCategory, REQ_COUNT, mReqPage);
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
            mPresenter.loadDatas(mCategory, REQ_COUNT, mReqPage);
        }
    }

    public void resetData() {
        mReqPage = 1;
        mAdapter.clearData();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void showDatas(List<MainListResBody.ResultsObj> list, boolean hasMore) {
        mAdapter.addDataEnd(list);
        mReqPage++;
        if (isFirstLoad) {
            isFirstLoad = false;
        }
    }

    @Override
    public void showNoResult() {

    }

    @Override
    public void showError(String msg) {
        ToastUtils.showShort(msg, getContext());
    }
}
