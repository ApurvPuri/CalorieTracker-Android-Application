package com.example.a3;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ViewPortHandler;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.OptionalDataException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;

@SuppressWarnings("ALL")
public class ReportFragment extends Fragment {
    private View vReport;
    private Date datePieChart = new Date();
    private Date dateStartBarChart = new Date();
    private Date dateEndBarChart = new Date();
    private int userId;
    private String[] datesBarChart, calConsumedBarChart, calBurnedBarChart;
    private double totalCalConsumed, totalCalburned, remainingCal;
    private PieChart pieChart;
    private float[] yData;
    private String[] xData = {"Calories Consumed", "Calories Burned", "Calories Remaining"};

    private BarChart barChart;
    private float[] yDataBarCalConsumed;
    private String[] xDataBar;
    private float[] yDataBarCalBurned;
    private float totalCal;

    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) { // Set Variables and listeners
        vReport = inflater.inflate(R.layout.fragment_report, container, false);
        pieChart = (PieChart) vReport.findViewById(R.id.idPieChart);
        pieChart.setRotationEnabled(true);
        pieChart.setHoleRadius(70f);
        pieChart.setCenterText("Fitness Report");
        pieChart.setCenterTextSize(15);
        pieChart.setTransparentCircleAlpha(0);

        barChart = (BarChart) vReport.findViewById(R.id.idBarChart);
        barChart.setDrawBarShadow(false);
        barChart.setDrawValueAboveBar(true);
        barChart.setMaxVisibleValueCount(50);
        barChart.setPinchZoom(false);

        final Calendar myCalendar = Calendar.getInstance();

        final EditText etDatePieChart = (EditText) vReport.findViewById(R.id.etDatePieChart);
        final EditText etDateStartBarChart = (EditText) vReport.findViewById(R.id.etDateStartBarChart);
        final EditText etDateEndBarChart = (EditText) vReport.findViewById(R.id.etDateEndBarChart);
        final DatePickerDialog.OnDateSetListener datePie = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {

                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "yyyy-MM-dd"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                etDatePieChart.setText(sdf.format(myCalendar.getTime()));
                datePieChart = myCalendar.getTime();
            }

        };

        final DatePickerDialog.OnDateSetListener dateBarStart = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {

                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "yyyy-MM-dd"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);


                etDateStartBarChart.setText(sdf.format(myCalendar.getTime()));
                dateStartBarChart = myCalendar.getTime();

            }

        };

        final DatePickerDialog.OnDateSetListener dateBarEnd = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {

                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "yyyy-MM-dd"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                etDateEndBarChart.setText(sdf.format(myCalendar.getTime()));
                dateEndBarChart = myCalendar.getTime();
            }

        };


        etDatePieChart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(getActivity(), datePie, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        etDateStartBarChart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(getActivity(), dateBarStart, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        etDateEndBarChart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(getActivity(), dateBarEnd, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        Button bShowPieChart = (Button) vReport.findViewById(R.id.bShowPieChart);
        Button bShowBarChart = (Button) vReport.findViewById(R.id.bShowBarChart);

        bShowPieChart.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Date currentTime = Calendar.getInstance().getTime();
                if (datePieChart.compareTo(currentTime) > 0)
                    etDatePieChart.setError("Date cannot be in future");
                else {

                    userId = ((MainActivity) getActivity()).getTheUserId();

                    String myFormat = "yyyy-MM-dd"; //In which you need put here
                    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                    String PieDate = sdf.format(datePieChart);

                    ReportAsyncTask reportAsyncTask = new ReportAsyncTask();
                    reportAsyncTask.execute(Integer.toString(userId), PieDate);


                }


            }
        });


        bShowBarChart.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Date currentTime = Calendar.getInstance().getTime();
                if (dateStartBarChart.compareTo(currentTime) > 0)
                    etDateStartBarChart.setError(" Start date cannot be in future");
                else if (dateEndBarChart.compareTo(currentTime) > 0)
                    etDateEndBarChart.setError(" End date cannot be in future");
                else if (dateStartBarChart.compareTo(dateEndBarChart) > 0)
                    etDateStartBarChart.setError(" Start date should be before end date");
                else {

                    userId = ((MainActivity) getActivity()).getTheUserId();

                    String myFormat = "yyyy-MM-dd"; //In which you need put here
                    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                    String sdateStartBarChart = sdf.format(dateStartBarChart);
                    String sdateEndBarChart = sdf.format(dateEndBarChart);

                    ReportPerDayAsyncTask reportPerDayAsyncTask = new ReportPerDayAsyncTask();
                    reportPerDayAsyncTask.execute(Integer.toString(userId), sdateStartBarChart, sdateEndBarChart);


                }

            }
        });


        return vReport;
    }

    private void addDataSetPie() {

        ArrayList<PieEntry> yEntries = new ArrayList<>();
        ArrayList<String> xEntries = new ArrayList<>();

        for (int i = 0; i < yData.length; i++) {

            totalCal += yData[i];
            switch (i) {
                case 0:
                    yEntries.add(new PieEntry(yData[i], "Calories Consumed"));
                    break;
                case 1:
                    yEntries.add(new PieEntry(yData[i], "Calories Burned"));
                    break;
                case 2:
                    yEntries.add(new PieEntry(yData[i], "Remaining calories"));
                    break;
            }
        }

        for (int i = 0; i < xData.length; i++) {
            xEntries.add(xData[i]);
        }


        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.GREEN);
        colors.add(Color.rgb(255, 153, 0));
        colors.add(Color.RED);

        PieDataSet pieDataSet = new PieDataSet(yEntries, "");
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(12);

        pieDataSet.setColors(colors);

        Legend legend = pieChart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setPosition(Legend.LegendPosition.LEFT_OF_CHART);


        PieData pieData = new PieData(pieDataSet);
        pieData.setValueFormatter(new dataGraph());
        pieChart.setData(pieData);
        pieChart.setEntryLabelColor(Color.TRANSPARENT);

        Description description = new Description();
        pieChart.setDescription(description);
        description.setEnabled(false);
        pieChart.invalidate();
    }

    private void addDataSetBar() {

        ArrayList<BarEntry> yBarEntriesCalConsumed = new ArrayList<>();
        ArrayList<String> xBarEntries = new ArrayList<>();

        for (int i = 0; i < yDataBarCalConsumed.length; i++) {
            yBarEntriesCalConsumed.add(new BarEntry(i, yDataBarCalConsumed[i]));
        }

        for (int i = 0; i < xDataBar.length; i++) {
            xBarEntries.add(xDataBar[i]);
        }

        ArrayList<BarEntry> yBarEntriesCalBurned = new ArrayList<>();

        for (int i = 0; i < yDataBarCalBurned.length; i++) {
            yBarEntriesCalBurned.add(new BarEntry(i, yDataBarCalBurned[i]));
        }

        ArrayList<Integer> colors1 = new ArrayList<>();
        colors1.add(Color.GREEN);
        ArrayList<Integer> colors2 = new ArrayList<>();
        colors2.add(Color.rgb(255, 153, 0));

        BarDataSet barDataSetCalConsumed = new BarDataSet(yBarEntriesCalConsumed, "Calories Consumed");
        barDataSetCalConsumed.setColors(colors1);

        BarDataSet barDataSetCalBurned = new BarDataSet(yBarEntriesCalBurned, "Calories burned");
        barDataSetCalBurned.setColors(colors2);

        BarData barData = new BarData(barDataSetCalConsumed, barDataSetCalBurned);

        float groupSpace = 0.4f;
        float barSpace = 0.05f;
        float barWidth = 0.15f;

        barChart.setData(barData);


        barChart.invalidate();


        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xDataBar));
        barChart.getAxisLeft().setAxisMinimum(0);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setCenterAxisLabels(true);


        xAxis.setGranularity(1);
        xAxis.setGranularityEnabled(true);
        xAxis.setAxisMinimum(1);
        xAxis.setAxisMaximum(xDataBar.length);

        barData.setBarWidth(barWidth);
        barChart.getXAxis().setAxisMinimum(0.1f);
        barChart.groupBars(0.2f, groupSpace, barSpace);
        Description description = new Description();
        barChart.setDescription(description);
        description.setEnabled(false);
        barChart.moveViewToX(0.5f);
        barChart.setBackgroundColor(Color.TRANSPARENT);
        barChart.getAxisLeft().setDrawGridLines(false);
        barChart.getAxisRight().setEnabled(false);
        barChart.getXAxis().setDrawGridLines(false);


    }

    class dataGraph implements IValueFormatter {
        private DecimalFormat percentageFormat;

        dataGraph() {
            percentageFormat = new DecimalFormat("###,###,##0.0");
        }

        public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
            Float f = (value / totalCal) * 1000;

            f = (f.intValue()) / 10f;

            return Integer.toString(Float.valueOf(value).intValue()) + "\n" + "(" + Float.toString(f) + "%" + ")";
        }
    }



    private class ReportAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            return restClient.findReport(Integer.valueOf(params[0]), params[1]);
        }

        @Override
        protected void onPostExecute(String result) {

            totalCalConsumed = Double.parseDouble(restClient.getTotalCalConsumed(result));
            totalCalburned = Double.parseDouble(restClient.getTotalCalBurned(result));
            remainingCal = Double.parseDouble(restClient.getRemainingCal(result));
            yData = new float[]{(float) totalCalConsumed, (float) totalCalburned, (float) remainingCal};
            addDataSetPie();

            pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
                @Override
                public void onValueSelected(Entry e, Highlight h) {
                    int pos1 = e.toString().indexOf("Entry, x:0.0 y: ");
                    String value = e.toString().substring(pos1 + 18);

                    for (int i = 0; i < yData.length; i++) {
                        if (yData[i] == Float.parseFloat(value)) {
                            pos1 = i;
                            break;
                        }
                    }
                    String categoryOfCalories = xData[pos1];
                    Toast.makeText(getActivity(), categoryOfCalories, Toast.LENGTH_LONG).show();
                }

                @Override
                public void onNothingSelected() {

                }
            });


        }
    }

    private class ReportPerDayAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            return restClient.findReportPerDay(Integer.valueOf(params[0]), params[1], params[2]);
        }

        @Override
        protected void onPostExecute(String result) {

            try {
                JSONArray jsonArray = new JSONArray(result);
                datesBarChart = new String[jsonArray.length()];
                calConsumedBarChart = new String[jsonArray.length()];
                xDataBar = new String[jsonArray.length()];
                yDataBarCalBurned = new float[jsonArray.length()];
                yDataBarCalConsumed = new float[jsonArray.length()];
                calBurnedBarChart = new String[jsonArray.length()];
                for (int i = 0; i < jsonArray.length(); i++) {

                    Iterator<String> keys = jsonArray.getJSONObject(i).keys();

                    datesBarChart[i] = keys.next();
                    calConsumedBarChart[i] = jsonArray.getJSONObject(i).getJSONObject(datesBarChart[i]).getString("Total calories consumed for the duration");
                    calBurnedBarChart[i] = jsonArray.getJSONObject(i).getJSONObject(datesBarChart[i]).getString("Total calories burned for the duration");

                    xDataBar[i] = datesBarChart[i];
                    yDataBarCalConsumed[i] = Float.parseFloat(calConsumedBarChart[i]);
                    yDataBarCalBurned[i] = Float.parseFloat(calBurnedBarChart[i]);
                }

                addDataSetBar();


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }


}