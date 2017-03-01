package com.sgb.gank.ui.main.fragment;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sgb.gank.R;
import com.sgb.gank.data.main.module.MainListResBody;
import com.sgb.gank.ui.webview.CustomTabActivityHelper;
import com.sgb.gank.util.DateUtils;
import com.sgb.gank.util.ImageLoader;
import com.sgb.widget.imageview.RoundedImageView;
import com.youzan.titan.TitanAdapter;

/**
 * Created by panda on 2016/10/21 下午2:59.
 */
public class MainListAdapter extends TitanAdapter<MainListResBody.ResultsObj> {

    private static final int TYPE_NO_PIC = 0;
    private static final int TYPE_WITH_PIC = 1;

    private Context mContext;

    public MainListAdapter(Context context) {
        mContext = context;
    }

    @Override
    protected RecyclerView.ViewHolder createVHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_WITH_PIC:
                return new ViewHolderWithPic(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tech_with_pic, parent, false));
            case TYPE_NO_PIC:
            default:
                return new ViewHolderNoPic(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tech_no_pic, parent, false));
        }
    }

    @Override
    protected int getAttackItemViewType(int position) {
        if (mData.get(position).images == null || mData.get(position).images.isEmpty()) {
            return TYPE_NO_PIC;
        }
        return TYPE_WITH_PIC;
    }

    @Override
    protected void showItemView(RecyclerView.ViewHolder holder, int position) {

        MainListResBody.ResultsObj resultsObj = mData.get(position);

        if (holder instanceof ViewHolderWithPic) {
            ViewHolderWithPic holder1 = (ViewHolderWithPic) holder;

            ImageLoader.with(mContext)
                    .load(resultsObj.images.get(0) + "?imageView2/0/w/100")
                    .build()
                    .into(holder1.thumbnailIV);

            holder1.titleTV.setText(resultsObj.desc);
            holder1.authorTV.setText(resultsObj.who);
            holder1.publishedTimeTV.setText(DateUtils.getDateTime(resultsObj.publishedAt));

        } else if (holder instanceof ViewHolderNoPic) {
            ViewHolderNoPic holder1 = (ViewHolderNoPic) holder;

            holder1.titleTV.setText(resultsObj.desc);
            holder1.authorTV.setText(resultsObj.who);
            holder1.publishedTimeTV.setText(DateUtils.getDateTime(resultsObj.publishedAt));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomTabActivityHelper.openCustomTab((Activity) mContext, resultsObj.url);
            }
        });

    }

    @Override
    public long getAdapterItemId(int position) {
        return position;
    }

    private static class ViewHolderNoPic extends RecyclerView.ViewHolder {

        TextView titleTV;
        TextView authorTV;
        TextView publishedTimeTV;

        ViewHolderNoPic(View itemView) {
            super(itemView);
            titleTV = (TextView) itemView.findViewById(R.id.tv_title);
            authorTV = (TextView) itemView.findViewById(R.id.tv_author);
            publishedTimeTV = (TextView) itemView.findViewById(R.id.tv_published_time);
        }
    }

    private static class ViewHolderWithPic extends RecyclerView.ViewHolder {

        RoundedImageView thumbnailIV;
        TextView titleTV;
        TextView authorTV;
        TextView publishedTimeTV;

        ViewHolderWithPic(View itemView) {
            super(itemView);
            thumbnailIV = (RoundedImageView) itemView.findViewById(R.id.iv_thumbnail);
            titleTV = (TextView) itemView.findViewById(R.id.tv_title);
            authorTV = (TextView) itemView.findViewById(R.id.tv_author);
            publishedTimeTV = (TextView) itemView.findViewById(R.id.tv_published_time);
        }
    }

}


