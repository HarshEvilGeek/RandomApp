package com.example.zaas.pocketbanker.adapters;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.zaas.pocketbanker.R;
import com.example.zaas.pocketbanker.models.local.Shop;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by akhil on 3/26/16.
 */
public class PocketShopAdapter extends RecyclerView.Adapter<PocketShopAdapter.PocketShopViewHolder> implements Filterable
{
    private Context mContext;
    private LayoutInflater mInflater;
    private List<Shop> mShopList;

    public PocketShopAdapter(Context context, List<Shop> shopList)
    {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mShopList = shopList;
    }

    @Override
    public PocketShopViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = mInflater.inflate(R.layout.shop_item, parent, false);
        return new PocketShopViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PocketShopViewHolder holder, int position)
    {
        final Shop recommendation = mShopList.get(position);
        Glide.with(mContext).load(recommendation.getUrl()).centerCrop().into(holder.imageView);
        holder.messageTv.setText(recommendation.getName());
        holder.buyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBuyDialog(recommendation.getId(), recommendation.getName());
            }
        });
    }

    private void showBuyDialog(final int id, final String name)
    {

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        View rootView = LayoutInflater.from(mContext).inflate(R.layout.buy_layout, null, false);

        final EditText moneyField = (EditText) rootView.findViewById(R.id.buy_field);
        final Button buyButton = (Button) rootView.findViewById(R.id.buy_button);

        buyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                double amount = 0.0;
                try {
                    amount = Double.parseDouble(moneyField.getText().toString());
                } catch (Exception e) {
                    Toast.makeText(mContext, "Invalid amount", Toast.LENGTH_LONG).show();
                }
                if (amount > 0) {
                    buyWithWallet(amount);
                } else {
                    Toast.makeText(mContext, "Invalid amount", Toast.LENGTH_LONG).show();
                }
            }
        });

        // Set grid view to alertDialog
        builder.setView(rootView);
        builder.setTitle("Purchase");
        builder.show();
    }

    private void buyWithWallet(double amount) {
        Toast.makeText(mContext, "WOHOOO", Toast.LENGTH_LONG).show();
    }

    @Override
    public int getItemCount()
    {
        return mShopList.size();
    }

    @Override
    public Filter getFilter() {

        Filter filter = new Filter() {

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mShopList = (List<Shop>) results.values;
                notifyDataSetChanged();
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                FilterResults results = new FilterResults();
                List<Shop> filteredShops = new ArrayList<>();

                // perform your search here using the searchConstraint String.

                constraint = constraint.toString().toLowerCase();
                for (int i = 0; i < mShopList.size(); i++) {
                    String shopName = mShopList.get(i).getName();
                    if (shopName.toLowerCase().startsWith(constraint.toString()))  {
                        filteredShops.add(mShopList.get(i));
                    }
                }

                results.count = filteredShops.size();
                results.values = filteredShops;
                Log.e("VALUES", results.values.toString());

                return results;
            }
        };

        return filter;
    }

    public class PocketShopViewHolder extends RecyclerView.ViewHolder
    {
        private final ImageView imageView;
        private final TextView messageTv;
        private final Button buyButton;

        public PocketShopViewHolder(View itemView)
        {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.image);
            messageTv = (TextView) itemView.findViewById(R.id.shop_name);
            buyButton = (Button) itemView.findViewById(R.id.buy_button);
        }
    }
}
