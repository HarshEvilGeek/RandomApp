package com.example.zaas.pocketbanker.fragments;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zaas.pocketbanker.R;
import com.example.zaas.pocketbanker.models.local.PocketAccount;
import com.example.zaas.pocketbanker.sync.NetworkHelper;
import com.example.zaas.pocketbanker.utils.SecurityUtils;

/**
 * Created by akhil on 3/25/16.
 */
public class PocketsAddMoneyFragment extends Fragment {

    EditText addMoney;
    Button add;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_pockets_add_money, container, false);
        addMoney = (EditText) rootView.findViewById(R.id.add_money);
        add = (Button) rootView.findViewById(R.id.add_button);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double amount = 0.0;
                try {
                    amount = Double.parseDouble(addMoney.getText().toString());
                } catch (Exception e) {
                    Toast.makeText(getActivity(), "Invalid amount", Toast.LENGTH_LONG).show();
                }
                if (amount > 0) {

                    addFundsToWallet(amount);

                } else {
                    Toast.makeText(getActivity(), "Invalid amount", Toast.LENGTH_LONG).show();
                }
            }
        });
        return rootView;
    }

    private void addFundsToWallet(Double amount)
    {
        new AddFundsTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Double[]{amount});

    }

    public class AddFundsTask extends AsyncTask<Double, Void, Boolean>
    {
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(Double... params)
        {
            NetworkHelper networkHelper = new NetworkHelper();
            PocketAccount pocketAccount = SecurityUtils.getPocketsAccount();
            return networkHelper.creditWalletAmount(pocketAccount.getPhoneNumber(), params[0], "promo1", "Adding funds", "submerchant1");
        }

        @Override
        protected void onPostExecute(Boolean result)
        {
            if(result) {
                Toast.makeText(getActivity(), "Amount added successfully", Toast.LENGTH_LONG).show();
                addMoney.setText("0.0");
            }
            else {
                Toast.makeText(getActivity(), "Transaction to add money failed.", Toast.LENGTH_LONG).show();
            }
        }
    }
}
