package cs.utep.edu.app10.Fragment;


import android.content.Context;
import android.graphics.DashPathEffect;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidplot.util.PixelUtils;
import com.androidplot.xy.CatmullRomInterpolator;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYGraphWidget;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cs.utep.edu.app10.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    private XYPlot plot;
    LineChart chart;
    Context ctx;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        ctx = getActivity();
        //plot = (XYPlot) rootView.findViewById(R.id.plot);

        //setPlot();
         chart = (LineChart) rootView.findViewById(R.id.chart);
         setCoolerPlot();
        return rootView;
    }

    public void setCoolerPlot(){
       GraphObject go1 = new GraphObject(1,4);
        GraphObject go2 = new GraphObject(2,1);
        GraphObject go3 = new GraphObject(3,2);
        GraphObject go4 = new GraphObject(4,3);
        GraphObject go5 = new GraphObject(5,1);
        GraphObject go6 = new GraphObject(6,3);
        GraphObject go7 = new GraphObject(7,0);
        GraphObject[] series1Numbers = {go1,go2,go3,go4,go5,go6,go7};
        List<Entry> entries = new ArrayList<Entry>();
        for(GraphObject data : series1Numbers){
            entries.add(new Entry(data.getxValue(),data.getyValue()));
        }

        LineDataSet dataSet = new LineDataSet(entries,"Contributions Last Week");
        dataSet.setColor(android.R.color.holo_red_dark);
        dataSet.setValueTextColor(android.R.color.black);

        LineData lineData = new LineData(dataSet);
        chart.setData(lineData);
        chart.invalidate();
    }

    public void setPlot(){
        final Number[] domainLabels = {1,2,3,4,5,6,7};
        Number[] series1Numbers = {1 , 4 , 2, 3, 1};
        Number[] series2Numbers = {60,75,80,87,90};
        XYSeries series1 = new SimpleXYSeries(Arrays.asList(series1Numbers),SimpleXYSeries.ArrayFormat.Y_VALS_ONLY,"Contributions/Day");
        XYSeries series2 = new SimpleXYSeries(Arrays.asList(series2Numbers),SimpleXYSeries.ArrayFormat.Y_VALS_ONLY,"Score");

        LineAndPointFormatter series1Format = new LineAndPointFormatter(getActivity(),R.xml.line_point_formatter_with_labels);
        LineAndPointFormatter series2Format = new LineAndPointFormatter(getActivity(),R.xml.line_formatter_with_labels_2);

        series2Format.getLinePaint().setPathEffect(new DashPathEffect(new float[]{
                PixelUtils.dpToPix(20),
                PixelUtils.dpToPix(15)
        },0));

        series1Format.setInterpolationParams(
                new CatmullRomInterpolator.Params(10,CatmullRomInterpolator.Type.Centripetal)
        );
        series2Format.setInterpolationParams(
                new CatmullRomInterpolator.Params(10,CatmullRomInterpolator.Type.Centripetal)
        );

        plot.addSeries(series1,series1Format);
        plot.addSeries(series2,series2Format);

        plot.getGraph().getLineLabelStyle(XYGraphWidget.Edge.BOTTOM).setFormat(new Format(){
            @Override
            public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
                int i = Math.round(((Number) obj).floatValue());
                return toAppendTo.append(domainLabels[i]);
            }
            @Override
            public Object parseObject(String source, ParsePosition pos) {
                return null;
            }
        });

    }

    private class GraphObject{
        private int xValue;
        private int yValue;

        public GraphObject(int x,int y){
            this.xValue = x;
            this.yValue = y;
        }
        public int getxValue() {
            return xValue;
        }

        public void setxValue(int xValue) {
            this.xValue = xValue;
        }

        public int getyValue() {
            return yValue;
        }

        public void setyValue(int yValue) {
            this.yValue = yValue;
        }
    }
}
