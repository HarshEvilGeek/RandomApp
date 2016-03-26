package com.example.zaas.pocketbanker.fragments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zaas.pocketbanker.R;
import com.example.zaas.pocketbanker.activities.TransactionCategoryActivity;
import com.example.zaas.pocketbanker.models.local.Transaction;
import com.example.zaas.pocketbanker.utils.DateUtils;
import com.example.zaas.pocketbanker.utils.TransactionCategoryUtils;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

/**
 * Created by zaas on 3/17/16.
 */
public class AnalyticsFragment extends Fragment implements OnChartValueSelectedListener
{
    private static final int FROM_DATE_PICKER_ID = 1000;
    private static final int TO_DATE_PICKER_ID = 1001;

    // private int[] mColors = new int[] { Color.GREEN, Color.BLUE, Color.YELLOW, Color.MAGENTA, Color.CYAN, Color.RED
    // };

    // private static String[] CATEGORY_LABELS =
    // Arrays.toString(TransactionCategoryUtils.Category.values()).replaceAll("^.|.$", "").split(", ");

    private List<Transaction> mAllTransactions;
    private HashMap<String, Double> mAmountPerCategory;
    private PieDataSet mDataset;
    private PieChart mPieChart;
    private TextView mFromDate;
    private TextView mToDate;
    private long mFromDateValue = System.currentTimeMillis();
    private long mToDateValue = System.currentTimeMillis();
    private List<Transaction> mOriginalTransactionList;
    private String[] mLabels;
    private DatePickerDialog.OnDateSetListener fromdatePickerListener = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay)
        {
            Time updateDate = new Time();
            updateDate.set(selectedDay, selectedMonth, selectedYear);
            mFromDateValue = updateDate.normalize(true);
            updateDates(false);
        }
    };
    private DatePickerDialog.OnDateSetListener todatePickerListener = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay)
        {
            Time updateDate = new Time();
            updateDate.set(selectedDay, selectedMonth, selectedYear);
            mToDateValue = updateDate.normalize(true);
            updateDates(false);
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(R.string.action_analytics);
    }

    @Override
    public void onStart()
    {
        super.onStart();
        mOriginalTransactionList = TransactionCategoryUtils.getAllTransactions();
        mAllTransactions = mOriginalTransactionList;
        categorizeTransactionAmounts();
        setDefaultDates();
        setDatePickers();
        updatePieDataset();
        setPieData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_analytics, container, false);
        mFromDate = (TextView) rootView.findViewById(R.id.from_date);
        mToDate = (TextView) rootView.findViewById(R.id.to_date);
        mPieChart = (PieChart) rootView.findViewById(R.id.chart);
        mPieChart.setOnChartValueSelectedListener(this);

        return rootView;
    }

    @Override
    public void onValueSelected(Entry e, int dataSetIndex, Highlight h)
    {
        // Toast.makeText(getActivity(), mLabels[e.getXIndex()], Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getActivity(), TransactionCategoryActivity.class);
        intent.putExtra("Category", TransactionCategoryUtils.Category.valueOf(mLabels[e.getXIndex()]));
        intent.putExtra("FromDate", mFromDateValue);
        intent.putExtra("ToDate", mToDateValue);
        getActivity().startActivity(intent);
    }

    @Override
    public void onNothingSelected()
    {

    }

    private void setDatePickers()
    {
        mFromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                // On button click show datepicker dialog
                createDialog(FROM_DATE_PICKER_ID).show();
            }
        });

        mToDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                // On button click show datepicker dialog
                createDialog(TO_DATE_PICKER_ID).show();
            }
        });
    }

    protected Dialog createDialog(int id)
    {
        Time initialDate = new Time();
        switch (id)
        {
        case FROM_DATE_PICKER_ID:
            initialDate.set(mFromDateValue);
            return new DatePickerDialog(getActivity(), R.style.DatePickerTheme, fromdatePickerListener,
                    initialDate.year, initialDate.month, initialDate.monthDay);
        case TO_DATE_PICKER_ID:
            initialDate.set(mToDateValue);
            return new DatePickerDialog(getActivity(), R.style.DatePickerTheme, todatePickerListener, initialDate.year,
                    initialDate.month, initialDate.monthDay);
        }
        return null;
    }

    private void setDefaultDates()
    {
        for (Transaction transaction : mAllTransactions) {
            if (transaction.getTime() < mFromDateValue) {
                mFromDateValue = transaction.getTime();
            }
        }
        updateDates(true);
    }

    private void updateDates(boolean isFirstSync)
    {
        mFromDate.setText(DateUtils.getDateStringFromMillis(mFromDateValue));
        mToDate.setText(DateUtils.getDateStringFromMillis(mToDateValue));
        if (!isFirstSync) {
            if (mToDateValue < mFromDateValue) {
                Toast.makeText(getActivity(), "Oops! Invalid date selection", Toast.LENGTH_SHORT).show();
                return;
            }
            // refresh pie chart
            filterTransactions();
            categorizeTransactionAmounts();
            updatePieDataset();
            setPieData();
        }
    }

    private void filterTransactions()
    {
        mAllTransactions = mOriginalTransactionList;
        List<Transaction> tempList = new ArrayList<>();
        for (Transaction transaction : mAllTransactions) {
            if (transaction.getTime() >= mFromDateValue && transaction.getTime() <= mToDateValue) {
                tempList.add(transaction);
            }
        }
        mAllTransactions = tempList;
    }

    private void updatePieDataset()
    {
        // creating data values
        ArrayList<Entry> values = new ArrayList<>();
        int k = 0;
        for (Double amount : mAmountPerCategory.values()) {
            values.add(new Entry(amount.floatValue(), k++));
        }

        mDataset = new PieDataSet(values, "Transaction Amounts");
        // add many colors
        ArrayList<Integer> colors = getPieColorPalette();
        mDataset.setColors(colors);
        mDataset.setSliceSpace(3f);
        mDataset.setDrawValues(false);
    }

    private void setPieData()
    {
        mLabels = new String[mAmountPerCategory.size()];
        int k = 0;
        for (String label : mAmountPerCategory.keySet()) {
            mLabels[k++] = label;
        }
        mPieChart.getLegend().setEnabled(false);
        mPieChart.setCenterText("Transaction Analysis");
        mPieChart.setDescription("");
        mPieChart.setCenterTextColor(getResources().getColor(R.color.colorPrimaryDark));
        mPieChart.setDescriptionTextSize(15f);
        mPieChart.animateY(1000);
        mPieChart.setData(new PieData(mLabels, mDataset));

        mPieChart.notifyDataSetChanged();
        mPieChart.invalidate();
    }

    private void categorizeTransactionAmounts()
    {
        mAmountPerCategory = new HashMap<>();
        double amount;
        Double existingAmount;
        for (Transaction currentTransaction : mAllTransactions) {
            if (currentTransaction.getType() != Transaction.Type.Debit) {
                continue;
            }

            existingAmount = mAmountPerCategory.get(currentTransaction.getCategory().name());
            amount = currentTransaction.getAmount();

            if (existingAmount == null) {
                if (amount > 0) {
                    mAmountPerCategory.put(currentTransaction.getCategory().name(), Double.valueOf(amount));
                }
            }
            else {
                mAmountPerCategory.put(currentTransaction.getCategory().name(),
                        Double.valueOf(existingAmount.doubleValue() + amount));
            }
        }
    }

    private ArrayList<Integer> getPieColorPalette()
    {
        ArrayList<Integer> colors = new ArrayList<>();

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());
        return colors;
    }
}
