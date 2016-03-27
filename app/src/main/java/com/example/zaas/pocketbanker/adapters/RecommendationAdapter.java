package com.example.zaas.pocketbanker.adapters;

import java.util.List;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.zaas.pocketbanker.R;
import com.example.zaas.pocketbanker.models.local.Recommendation;

/**
 * Created by adugar on 3/25/16.
 */
public class RecommendationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    private Context mContext;
    private LayoutInflater mInflater;
    private List<Recommendation> mRecommendationList;
    private OnClickListener mOnClickListener;

    public RecommendationAdapter(Context context, List<Recommendation> recommendationList)
    {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mRecommendationList = recommendationList;
    }

    public void setOnClickListener(OnClickListener onClickListener)
    {
        this.mOnClickListener = onClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        if (ViewType.HEADER.ordinal() == viewType) {
            View view = mInflater.inflate(R.layout.header_separators, parent, false);
            return new RecommendationHeaderViewHolder(view);
        }
        else {
            View view = mInflater.inflate(R.layout.recommendation_item, parent, false);
            return new RecommendationNormalViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {
        if (ViewType.HEADER.ordinal() == getItemViewType(position)) {
            RecommendationHeaderViewHolder viewHolder = (RecommendationHeaderViewHolder) holder;
            viewHolder.headerTv.setText(getHeader(position));
        }
        else {
            Recommendation recommendation = mRecommendationList.get(getNormalPosition(position));
            RecommendationNormalViewHolder viewHolder = (RecommendationNormalViewHolder) holder;
            Glide.with(mContext).load(recommendation.getImageUrl()).into(viewHolder.imageView);
            viewHolder.messageTv.setText(recommendation.getMessage());
            viewHolder.reasonTv.setText(recommendation.getReason());
        }
    }

    @Override
    public int getItemCount()
    {
        return mRecommendationList.size() + 5;
    }

    @Override
    public int getItemViewType(int position)
    {
        if (position == 0 || position == 3 || position == 6 || position == 8 || position == 11) {
            return ViewType.HEADER.ordinal();
        }
        else {
            return ViewType.NORMAL.ordinal();
        }
    }

    private String getHeader(int position)
    {
        if (position == 0) {
            return "Credit or Debit Card";
        }
        if (position == 3) {
            return "Internet Banking";
        }
        if (position == 6) {
            return "Pockets";
        }
        if (position == 8) {
            return "Travel";
        }
        if (position == 11) {
            return "Lifestyle";
        }
        return "";
    }

    private int getNormalPosition(int position)
    {
        if (position < 3) {
            return position - 1;
        }
        if (position < 6) {
            return position - 2;
        }
        if (position < 8) {
            return position - 3;
        }
        if (position < 11) {
            return position - 4;
        }
        return position - 5;
    }

    public enum ViewType
    {
        HEADER,
        NORMAL
    }

    public interface OnClickListener
    {
        void onClick(Recommendation recommendation);
    }

    public class RecommendationNormalViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private final ImageView imageView;
        private final TextView messageTv;
        private final TextView reasonTv;

        public RecommendationNormalViewHolder(View itemView)
        {
            super(itemView);
            itemView.setOnClickListener(this);
            imageView = (ImageView) itemView.findViewById(R.id.image);
            messageTv = (TextView) itemView.findViewById(R.id.message);
            reasonTv = (TextView) itemView.findViewById(R.id.reason);
        }

        @Override
        public void onClick(View v)
        {
            if (mOnClickListener != null) {
                mOnClickListener.onClick(mRecommendationList.get(getNormalPosition(getLayoutPosition())));
            }
        }
    }

    public class RecommendationHeaderViewHolder extends RecyclerView.ViewHolder
    {
        private final TextView headerTv;

        public RecommendationHeaderViewHolder(View itemView)
        {
            super(itemView);
            headerTv = (TextView) itemView.findViewById(R.id.header_tv);
        }
    }
}
