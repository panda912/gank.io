package com.sgb.gank.ui.setting;

import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.sgb.gank.R;
import com.sgb.gank.databinding.ActivitySettingBinding;
import com.sgb.gank.ui.ToolbarActivity;

public class SettingActivity extends ToolbarActivity implements View.OnClickListener {

    ActivitySettingBinding mBinding;

    @Override
    public View setRootLayout() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_setting);
        return mBinding.getRoot();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding.setFabClick(this);
    }

    @Override
    public void setStatusBar() {
        super.setStatusBar();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(android.R.color.transparent));
        }
    }

    @Override
    public boolean canGoBack() {
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:
                Snackbar.make(v, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                break;
            default:
                break;
        }
    }

}
