package com.example.zaas.pocketbanker.adapters;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

    public BranchAtmAdapter(Context context)
    {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mBranchAtmList = createDummyBranchAtmList();
    }

    public BranchAtmAdapter(Context context, List<BranchAtm> branchAtmList)
    {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mBranchAtmList = branchAtmList;
    }

    public void setOnClickListener(OnClickListener onClickListener)
    {
        mOnClickListener = onClickListener;
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

    private List<BranchAtm> createDummyBranchAtmList()
    {
        List<BranchAtm> branchAtmList = new ArrayList<>();
        branchAtmList.add(new BranchAtm("Richmond Road", "1, Richmond Circle, Richmond Road", "Bangalore",
                BranchAtm.Type.BRANCH));
        branchAtmList.add(new BranchAtm("Koramangala", "4th Block, Koramangala", "Bangalore", BranchAtm.Type.BRANCH));
        branchAtmList.add(new BranchAtm("Indira Nagar", "100 Ft Road, Indira Nagar", "Bangalore", BranchAtm.Type.ATM));
        branchAtmList.add(new BranchAtm("Ulsoor", "Near Lido Mall, Ulsoor", "Bangalore", BranchAtm.Type.ATM));
        return branchAtmList;
    }

    public interface OnClickListener
    {
        void onClick(BranchAtm branchAtm);
    }

    public class BranchAtmViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private final TextView name;
        private final TextView address;

        public BranchAtmViewHolder(View itemView)
        {
            super(itemView);
            itemView.setOnClickListener(this);
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
