package com.example.zaas.pocketbanker.fragments;

import android.app.Fragment;
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
                int amount = 0;
                try {
                    amount = Integer.parseInt(addMoney.getText().toString());
                } catch (Exception e) {
                    Toast.makeText(getActivity(), "Invalid amount", Toast.LENGTH_LONG).show();
                }
                if (amount > 0) {

                } else {
                    Toast.makeText(getActivity(), "Invalid amount", Toast.LENGTH_LONG).show();
                }
            }
        });
        return rootView;
    }
}
