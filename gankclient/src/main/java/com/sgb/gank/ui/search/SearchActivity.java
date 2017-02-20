package com.sgb.gank.ui.search;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.sgb.gank.R;
import com.sgb.gank.data.search.module.SearchListObj;
import com.sgb.gank.data.search.source.SearchRepository;
import com.sgb.gank.data.search.source.local.SearchLocalDataSource;
import com.sgb.gank.data.search.source.remote.SearchRemoteDataSource;
import com.sgb.gank.databinding.ActivitySearchBinding;
import com.sgb.gank.databinding.ItemSearchListBinding;
import com.sgb.gank.ui.BaseActivity;
import com.sgb.gank.ui.webview.CustomTabActivityHelper;
import com.sgb.gank.util.NetworkUtils;
import com.sgb.gank.util.ToastUtils;
import com.sgb.widget.edittext.EditTextDeletable;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by panda on 16/9/9 下午5:49.
 */
public class SearchActivity extends BaseActivity implements SearchContract.View, TextView.OnEditorActionListener {

    private SearchContract.Presenter mPresenter;

    private EditTextDeletable mEditText;
    private RecyclerView mRecyclerView;

    private CustomTabActivityHelper mCustomTabActivityHelper;

    private SearchListAdapter mAdapter;

    @Override
    public void setPresenter(SearchContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySearchBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_search);

        Toolbar toolbar = binding.toolbar;
        mRecyclerView = binding.recyclerView;
        mEditText = binding.searchEditText;

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        mPresenter = new SearchPresenter(this, SearchRepository.getInstance(SearchRemoteDataSource.getInstance(), SearchLocalDataSource.getInstance(getApplicationContext())));

        mCustomTabActivityHelper = new CustomTabActivityHelper();

        mEditText.setOnEditorActionListener(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mRecyclerView.setAdapter(mAdapter = new SearchListAdapter(mPresenter));
    }

    @Override
    protected void onStart() {
        super.onStart();
        mCustomTabActivityHelper.bindCustomTabsService(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mPresenter.cancelLoadTask();
        mCustomTabActivityHelper.unbindCustomTabsService(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.action_search:
                startSearch();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            startSearch();
        }
        return true;
    }

    @Override
    public void showResults(List<SearchListObj> list) {
        mAdapter.setData(list);
    }

    @Override
    public void showNoResult() {
        mAdapter.clearData();
        ToastUtils.showShort("暂无结果", mContext);
    }

    @Override
    public void showError(String msg) {

    }

    @Override
    public void showDetail(String url) {
        CustomTabActivityHelper.openCustomTab(mActivity, url);
    }

    private void startSearch() {
        if (TextUtils.isEmpty(mEditText.getText().toString())) {
            mPresenter.loadResults(mEditText.getHint().toString());
        } else {
            mPresenter.loadResults(mEditText.getText().toString());
        }
        mRecyclerView.scrollToPosition(0);
    }

    private static class SearchListAdapter extends RecyclerView.Adapter<SearchListAdapter.SearchListHolder> {

        private SearchContract.Presenter mActionListener;

        private List<SearchListObj> mDataList = new ArrayList<>();

        public SearchListAdapter(SearchContract.Presenter mActionListener) {
            this.mActionListener = mActionListener;
        }

        public void setData(List<SearchListObj> list) {
            mDataList.clear();
            mDataList.addAll(list);
            notifyDataSetChanged();
        }

        public void clearData() {
            mDataList.clear();
            notifyDataSetChanged();
        }

        @Override
        public SearchListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            ItemSearchListBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_search_list, parent, false);
            return new SearchListHolder(binding);
        }

        @Override
        public void onBindViewHolder(SearchListHolder holder, int position) {
            holder.binding.setSearchObj(mDataList.get(position));

            SearchItemActionHandler actionHandler = new SearchItemActionHandler(mActionListener);
            holder.binding.setActionHandler(actionHandler);

            //当数据改变时, binding会在下一帧去改变数据, 如果需要立即改变, 就调用executePendingBindings方法.
            holder.binding.executePendingBindings();
        }

        @Override
        public int getItemCount() {
            return mDataList.size();
        }

        static final class SearchListHolder extends RecyclerView.ViewHolder {

            ItemSearchListBinding binding;

            TextView titleTV;
            TextView authorTV;
            TextView typeTV;

            SearchListHolder(ItemSearchListBinding binding) {
                super(binding.getRoot());

                this.binding = binding;
                titleTV = binding.titleTv;
                authorTV = binding.authorTv;
                typeTV = binding.typeTv;
            }
        }
    }

    @Override
    public void onNetwokConnected(int state) {
        switch (state) {
            case NetworkUtils.TYPE_NET_WIFI:
                startSearch();
                break;
            default:
                break;
        }
    }

}
