package com.sgb.gank.ui.main.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.sgb.gank.R;
import com.sgb.gank.data.main.module.MainListResBody;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by panda on 16/9/7 下午4:40.
 */
public class DiDiDiRVAdapter extends RecyclerView.Adapter<DiDiDiRVAdapter.DiDiDiHolder> {

    private Context mContext;

    private List<MainListResBody.ResultsObj> mDataList = new ArrayList<>();

    public DiDiDiRVAdapter(Context context) {
        mContext = context;
    }

    public void setData(List<MainListResBody.ResultsObj> dataList) {
        mDataList.addAll(dataList);
        notifyDataSetChanged();
    }

    @Override
    public DiDiDiHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DiDiDiHolder(LayoutInflater.from(mContext).inflate(R.layout.item_dididi_list, parent, false));
    }

    @Override
    public void onBindViewHolder(DiDiDiHolder holder, int position) {
        Picasso.with(mContext)
                .load(mDataList.get(position).url)
                .config(Bitmap.Config.RGB_565)
                .into(holder.prettyIV);
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    static class DiDiDiHolder extends RecyclerView.ViewHolder {

        ImageView prettyIV;

        DiDiDiHolder(View itemView) {
            super(itemView);
            prettyIV = (ImageView) itemView.findViewById(R.id.iv_pretty);
        }
    }
}
