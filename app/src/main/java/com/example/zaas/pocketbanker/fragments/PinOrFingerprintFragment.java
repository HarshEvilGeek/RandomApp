package com.example.zaas.pocketbanker.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zaas.pocketbanker.R;
import com.example.zaas.pocketbanker.activities.MainActivity;
import com.example.zaas.pocketbanker.utils.SecurityUtils;

/**
 * Created by akhil on 3/20/16.
 */
public class PinOrFingerprintFragment extends Fragment {

    private static final int PIN_LENGTH = 6;

    public static final String CREATE_PIN_KEY = "CREATE_PIN_KEY";
    public static final String USER_NAME_KEY = "USER_NAME_KEY";
    public static final String PASSWORD_KEY = "PASSWORD_KEY";

    TextView numButtons[];
    ImageView cancelButton;
    ImageView deleteButton;
    TextView pinPrompt;
    TextView pinText;
    TextView skipPin;

    String currentPin = "";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_pin_fingerprint, container,false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        numButtons = new TextView[10];
        int num = 0;
        pinText = (TextView) rootView.findViewById(R.id.pin_text);
        pinPrompt = (TextView) rootView.findViewById(R.id.pin_prompt);
        if (getArguments().getBoolean(CREATE_PIN_KEY, false)) {
            pinPrompt.setText("Select a 6 digit pin code");
        } else {
            pinPrompt.setText("Enter your pin");
        }
        for (TextView tv : numButtons) {
            int id;
            final int digit = num;
            switch (digit) {
                case 0: id = R.id.button_zero;
                    break;
                case 1: id = R.id.button_one;
                    break;
                case 2: id = R.id.button_two;
                    break;
                case 3: id = R.id.button_three;
                    break;
                case 4: id = R.id.button_four;
                    break;
                case 5: id = R.id.button_five;
                    break;
                case 6: id = R.id.button_six;
                    break;
                case 7: id = R.id.button_seven;
                    break;
                case 8: id = R.id.button_eight;
                    break;
                default: id = R.id.button_nine;
            }
            tv = (TextView) rootView.findViewById(id);
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    appendDigit(digit);
                }
            });
            num++;
        }
        return rootView;
    }

    private void appendDigit (int digit) {

        currentPin += String.valueOf(digit);

        pinText.setText(currentPin);

        if (currentPin.length() == PIN_LENGTH) {
            if (getArguments().getBoolean(CREATE_PIN_KEY, false)) {
                goToMainActivity();
            }
            else if (SecurityUtils.isValidPin(currentPin)) {
            }
        }
    }

    private void goToMainActivity() {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        getActivity().startActivity(intent);
    }
}
