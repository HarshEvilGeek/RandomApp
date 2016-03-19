package com.example.zaas.pocketbanker.adapters;

import java.util.List;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.zaas.pocketbanker.R;
import com.example.zaas.pocketbanker.models.local.BranchAtm;

/**
 * Recycler view adapter for branch/ATM list Created by adugar on 3/19/16.
 */
public class BranchAtmAdapter extends RecyclerView.Adapter<BranchAtmAdapter.BranchAtmViewHolder>
{
    private Context mContext;
    private LayoutInflater mInflater;
    private List<BranchAtm> mBranchAtmList;
    private OnClickListener mOnClickListener;
    private OnMapButtonClickListener mOnMapButtonClickListener;

    public BranchAtmAdapter(Context context, List<BranchAtm> branchAtmList)
    {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mBranchAtmList = branchAtmList;
    }

    public void setOnClickListener(OnClickListener onClickListener, OnMapButtonClickListener onMapButtonClickListener)
    {
        mOnClickListener = onClickListener;
        mOnMapButtonClickListener = onMapButtonClickListener;
    }

    @Override
    public BranchAtmViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = mInflater.inflate(R.layout.branch_atm_item, parent, false);
        return new BranchAtmViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BranchAtmViewHolder holder, int position)
    {
        BranchAtm branchAtm = mBranchAtmList.get(position);
        holder.name.setText(branchAtm.getName());
        holder.address.setText(branchAtm.getAddress());
    }

    @Override
    public int getItemCount()
    {
        return mBranchAtmList.size();
    }

    public interface OnClickListener
    {
        void onClick(BranchAtm branchAtm);
    }

    public interface OnMapButtonClickListener
    {
        void onMapButtonClick(int branchAtmID);
    }

    public class BranchAtmViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private final TextView name;
        private final TextView address;

        public BranchAtmViewHolder(final View itemView)
        {
            super(itemView);
            itemView.setOnClickListener(this);
            ImageButton mapButton = (ImageButton) itemView.findViewById(R.id.map_button);
            mapButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnMapButtonClickListener != null) {
                        mOnMapButtonClickListener.onMapButtonClick(getLayoutPosition());
                    }
                }
            });
            name = (TextView) itemView.findViewById(R.id.name);
            address = (TextView) itemView.findViewById(R.id.address);
        }

        @Override
        public void onClick(View v)
        {
            if (mOnClickListener != null) {
                mOnClickListener.onClick(mBranchAtmList.get(getLayoutPosition()));
            }
        }
    }
}
