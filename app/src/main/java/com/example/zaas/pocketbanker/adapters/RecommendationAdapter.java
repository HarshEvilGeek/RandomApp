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
public class RecommendationAdapter extends RecyclerView.Adapter<RecommendationAdapter.RecommendationViewHolder>
{
    private Context mContext;
    private LayoutInflater mInflater;
    private List<Recommendation> mRecommendationList;

    public RecommendationAdapter(Context context, List<Recommendation> recommendationList)
    {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mRecommendationList = recommendationList;
    }

    @Override
    public RecommendationViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = mInflater.inflate(R.layout.recommendation_item, parent, false);
        return new RecommendationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecommendationViewHolder holder, int position)
    {
        Recommendation recommendation = mRecommendationList.get(position);
        Glide.with(mContext).load(recommendation.getUrl()).centerCrop().into(holder.imageView);
        holder.messageTv.setText(recommendation.getMessage());
        holder.reasonTv.setText(recommendation.getReason());
    }

    @Override
    public int getItemCount()
    {
        return mRecommendationList.size();
    }

    public class RecommendationViewHolder extends RecyclerView.ViewHolder
    {
        private final ImageView imageView;
        private final TextView messageTv;
        private final TextView reasonTv;

        public RecommendationViewHolder(View itemView)
        {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.image);
            messageTv = (TextView) itemView.findViewById(R.id.message);
            reasonTv = (TextView) itemView.findViewById(R.id.reason);
        }
    }
}
