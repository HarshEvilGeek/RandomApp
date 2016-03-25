package com.example.zaas.pocketbanker.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zaas.pocketbanker.R;
import com.example.zaas.pocketbanker.data.PocketBankerDBHelper;
import com.example.zaas.pocketbanker.models.local.Transaction;
import com.example.zaas.pocketbanker.utils.TransactionCategoryUtils;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by zaas on 3/17/16.
 */
public class AnalyticsFragment extends Fragment{

    //private int[] mColors;
            //new int[] { Color.GREEN, Color.BLUE, Color.YELLOW, Color.MAGENTA, Color.CYAN, Color.RED  };

    private static String[] CATEGORY_LABELS = Arrays.toString(TransactionCategoryUtils.Category.values()).replaceAll("^.|.$", "").split(", ");

    private HashMap<String, Double> mAmountPerCategory = new HashMap<>();
    private PieDataSet mDataset;
    private PieChart mPieChart;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeAmounts();

        List<Transaction> allTransactions = PocketBankerDBHelper.getInstance().getAllTransactions();
        categorizeTransactionAmounts(allTransactions);

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_analytics, container, false);
        mPieChart = (PieChart) rootView.findViewById(R.id.chart);
        mPieChart.setData(new PieData(CATEGORY_LABELS, mDataset));
        updateChartUI();

        return rootView;
    }

    private void updateChartUI() {
        mPieChart.getLegend().setEnabled(false);
        mPieChart.setCenterText("Transaction Analysis");
        mPieChart.setDescription("");
        mPieChart.setCenterTextColor(getResources().getColor(R.color.colorPrimaryDark));
        mPieChart.setDescriptionTextSize(15f);
        mPieChart.animateY(1000);
    }

    private void categorizeTransactionAmounts(List<Transaction> allTransactions) {
        double amount;
        for (Transaction currentTransaction : allTransactions) {
            amount = mAmountPerCategory.get(currentTransaction.getCategory().name()).doubleValue();
            amount = Double.valueOf(amount + currentTransaction.getAmount());
            mAmountPerCategory.put(currentTransaction.getCategory().name(), amount);
        }
    }

    private ArrayList<Integer> getPieColorPalette() {
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

    private void initializeAmounts() {
        for (String categoryString : CATEGORY_LABELS) {
            mAmountPerCategory.put(categoryString, Double.valueOf(0));
        }
    }
}
