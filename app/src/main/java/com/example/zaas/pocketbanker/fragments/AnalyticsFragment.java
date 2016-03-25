package com.example.zaas.pocketbanker.fragments;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.zaas.pocketbanker.R;
import com.example.zaas.pocketbanker.interfaces.IFloatingButtonListener;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.SeriesSelection;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

/**
 * Created by zaas on 3/17/16.
 */
public class AnalyticsFragment extends Fragment implements IFloatingButtonListener {

    private static int[] COLORS = new int[] { Color.GREEN, Color.BLUE,Color.MAGENTA, Color.CYAN };

    private static double[] VALUES = new double[] { 10, 11, 12, 13 };

    private static String[] NAME_LIST = new String[] { "A", "B", "C", "D" };

    private CategorySeries mSeries = new CategorySeries("");

    private DefaultRenderer mRenderer = new DefaultRenderer();

    private GraphicalView mChartView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mRenderer.setApplyBackgroundColor(true);
        mRenderer.setBackgroundColor(Color.argb(100, 50, 50, 50));
        mRenderer.setChartTitleTextSize(20);
        mRenderer.setLabelsTextSize(15);
        mRenderer.setLegendTextSize(15);
        mRenderer.setMargins(new int[]{20, 30, 15, 0});
        mRenderer.setZoomButtonsVisible(true);
        mRenderer.setStartAngle(90);

        for (int i = 0; i < VALUES.length; i++) {
            mSeries.add(NAME_LIST[i] + " " + VALUES[i], VALUES[i]);
            SimpleSeriesRenderer renderer = new SimpleSeriesRenderer();
            renderer.setColor(COLORS[(mSeries.getItemCount() - 1) % COLORS.length]);
            mRenderer.addSeriesRenderer(renderer);
        }



        if (mChartView != null) {
            mChartView.repaint();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_analytics, container,false);
        LinearLayout layout = (LinearLayout) rootView.findViewById(R.id.chart);
        mChartView = ChartFactory.getPieChartView(getActivity(), mSeries, mRenderer);
        mRenderer.setClickEnabled(true);
        mRenderer.setSelectableBuffer(10);

        mChartView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SeriesSelection seriesSelection = mChartView.getCurrentSeriesAndPoint();

                if (seriesSelection == null) {
                    Toast.makeText(getActivity(), "No chart element was clicked", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getActivity(),"Chart element data point index "+ (seriesSelection.getPointIndex()+1) + " was clicked" + " point value="+ seriesSelection.getValue(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        mChartView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                SeriesSelection seriesSelection = mChartView.getCurrentSeriesAndPoint();
                if (seriesSelection == null) {
                    Toast.makeText(getActivity(),"No chart element was long pressed", Toast.LENGTH_SHORT);
                    return false;
                }
                else {
                    Toast.makeText(getActivity(),"Chart element data point index "+ seriesSelection.getPointIndex()+ " was long pressed",Toast.LENGTH_SHORT);
                    return true;
                }
            }
        });
        layout.addView(mChartView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));

        return rootView;
    }

    @Override
    public Drawable getFloatingButtonDrawable(Context context) {
        return ContextCompat.getDrawable(context, R.drawable.plus_32);
    }

    @Override
    public void onFloatingButtonPressed() {
        Toast.makeText(getActivity(), "BLET", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mChartView != null) {
            mChartView.repaint();
        }
    }
}
