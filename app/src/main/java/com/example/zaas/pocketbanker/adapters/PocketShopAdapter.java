package com.example.zaas.pocketbanker.adapters;

import java.util.List;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.zaas.pocketbanker.R;
import com.example.zaas.pocketbanker.fragments.PocketsAddMoneyFragment;
import com.example.zaas.pocketbanker.models.local.PocketAccount;
import com.example.zaas.pocketbanker.models.local.Shop;
import com.example.zaas.pocketbanker.sync.NetworkHelper;
import com.example.zaas.pocketbanker.utils.Constants;
import com.example.zaas.pocketbanker.utils.SecurityUtils;

/**
 * Created by akhil on 3/26/16.
 */
public class PocketShopAdapter extends RecyclerView.Adapter<PocketShopAdapter.PocketShopViewHolder>
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
            public void onClick(View v)
            {
                showBuyDialog(recommendation.getId(), recommendation.getName());
            }
        });
    }

    private void showBuyDialog(final int id, final String name)
    {

        final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        View rootView = LayoutInflater.from(mContext).inflate(R.layout.buy_layout, null, false);
        builder.setView(rootView);
        builder.setTitle("Purchase");
        final AlertDialog dialog = builder.show();

        final EditText moneyField = (EditText) rootView.findViewById(R.id.buy_field);
        final Button buyButton = (Button) rootView.findViewById(R.id.buy_button);

        buyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                double amount = 0.0;
                try {
                    amount = Double.parseDouble(moneyField.getText().toString());
                }
                catch (Exception e) {
                    Toast.makeText(mContext, "Invalid amount", Toast.LENGTH_LONG).show();
                }
                if (amount > 0) {
                    buyWithWallet(amount);
                }
                else {
                    Toast.makeText(mContext, "Invalid amount", Toast.LENGTH_LONG).show();
                }

                if(dialog != null) {
                    dialog.dismiss();
                }
            }
        });


    }

    private void buyWithWallet(double amount)
    {
        new BuyItem().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Double[] { amount });
    }

    private void showLessFundsDialog()
    {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setTitle("Pocket transaction failed!");
            builder.setMessage("Transaction failed due to limited funds in Pocket. Press continue to add more funds.");
            builder.setPositiveButton("Add Funds", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    PocketsAddMoneyFragment fragment = new PocketsAddMoneyFragment();
                    FragmentManager fragmentManager = ((Activity) mContext).getFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
                }
            });

            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    dialog.dismiss();
                }
            });

            builder.create().show();
        }
        catch (Exception e) {
            Log.e("LOG_TAG", "Exception while showing add funds dialog");
        }

    }

    @Override
    public int getItemCount()
    {
        return mShopList.size();
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

    public class BuyItem extends AsyncTask<Double, Void, Integer>
    {
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
        }

        @Override
        protected Integer doInBackground(Double... params)
        {
            int result = Constants.WALLET_PURCHASE_TRANSACTION_FAILED;
            NetworkHelper networkHelper = new NetworkHelper();
            PocketAccount pocketAccount = SecurityUtils.getPocketsAccount();
            double balance = networkHelper.getWalletBalance(pocketAccount.getPhoneNumber());
            if (balance < params[0]) {
                result = Constants.WALLET_PURCHASE_TRANSACTION_FAILED_DUE_TO_LESS_FUNDS;
            }
            else {
                boolean transactionResult = networkHelper.debitWalletAmount(pocketAccount.getPhoneNumber(), params[0],
                        "promo1", "Adding funds", "submerchant1");
                if (transactionResult) {
                    result = Constants.WALLET_PURCHASE_SUCCESSFUL;
                }
                else {
                    result = Constants.WALLET_PURCHASE_TRANSACTION_FAILED;
                }

            }

            return result;
        }

        @Override
        protected void onPostExecute(Integer result)
        {
            if (result == Constants.WALLET_PURCHASE_SUCCESSFUL) {
                Toast.makeText(mContext, "Purchase successful", Toast.LENGTH_LONG).show();
            }
            else if (result == Constants.WALLET_PURCHASE_TRANSACTION_FAILED) {
                Toast.makeText(mContext, "Purchase failed.", Toast.LENGTH_LONG).show();
            }
            else if (result == Constants.WALLET_PURCHASE_TRANSACTION_FAILED_DUE_TO_LESS_FUNDS) {
                showLessFundsDialog();
            }
        }
    }
}
