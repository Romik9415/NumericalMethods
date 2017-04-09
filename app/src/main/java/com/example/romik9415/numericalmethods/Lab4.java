package com.example.romik9415.numericalmethods;

import android.graphics.DashPathEffect;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Pair;
import android.view.View;
import android.widget.EditText;

import com.androidplot.util.PixelUtils;
import com.androidplot.xy.CatmullRomInterpolator;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYGraphWidget;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;

import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Lab4 extends AppCompatActivity {

    EditText x1;
    EditText x2;
    EditText x3;
    EditText x4;
    EditText x5;

    EditText y1;
    EditText y2;
    EditText y3;
    EditText y4;
    EditText y5;
    EditText output;
    XYPlot plot;
    private int LENGTH = 20;
    private float X_DELTA = 2f;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab4);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initialize();


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setXYParams(0); //

        final List<Pair<Double, Double>> polynomInput = new ArrayList<>();
        polynomInput.add(new Pair<>(Double.valueOf(x1.getText().toString()),
                Double.valueOf(y1.getText().toString())));
        polynomInput.add(new Pair<>(Double.valueOf(x2.getText().toString()),
                Double.valueOf(y2.getText().toString())));
        polynomInput.add(new Pair<>(Double.valueOf(x3.getText().toString()),
                Double.valueOf(y3.getText().toString())));
        polynomInput.add(new Pair<>(Double.valueOf(x4.getText().toString()),
                Double.valueOf(y4.getText().toString())));
        polynomInput.add(new Pair<>(Double.valueOf(x5.getText().toString()),
                Double.valueOf(y5.getText().toString())));


        final double x[] = {
                Double.valueOf(x1.getText().toString()),
                Double.valueOf(x2.getText().toString()),
                Double.valueOf(x3.getText().toString()),
                Double.valueOf(x4.getText().toString()),
                Double.valueOf(x5.getText().toString())
        };

        final double y[] = {
                Double.valueOf(y1.getText().toString()),
                Double.valueOf(y2.getText().toString()),
                Double.valueOf(y3.getText().toString()),
                Double.valueOf(y4.getText().toString()),
                Double.valueOf(y5.getText().toString())
        };

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                output.setText(Polynom.testLagrangian(
                        Polynom.lagrangian(polynomInput),polynomInput)
                        +"\n"+
                        Polynom.testNewtone(x,y,polynomInput));

                Snackbar.make(view, "Calculation finished!", Snackbar.LENGTH_LONG)
                        .setAction("LIKE!", null).show();
                drawOnPlot(Polynom.lagrangian(polynomInput));

            }
        });

    }



    private void initialize() {
        x1 = (EditText) findViewById(R.id.x1);
        x2= (EditText) findViewById(R.id.x2);
        x3= (EditText) findViewById(R.id.x3);
        x4= (EditText) findViewById(R.id.x4);
        x5= (EditText) findViewById(R.id.x5);

        y1= (EditText) findViewById(R.id.y1);
        y2= (EditText) findViewById(R.id.y2);
        y3= (EditText) findViewById(R.id.y3);
        y4= (EditText) findViewById(R.id.y4);
        y5= (EditText) findViewById(R.id.y5);
        output= (EditText) findViewById(R.id.output);

        plot = (XYPlot) findViewById(R.id.plot);
    }

    private void setXYParams(int mikeRomanOrAndrew012) {
        switch (mikeRomanOrAndrew012){
            case 0://mike
                //(0.5,1.337904) (0.6,1.401593) (0.7,1.461175) (0.8, 1.516552) (0.9, 1.567650)
                //f(x)=0.01x^4−0.0423333x^3−0.15085x^2+0.834638x+0.962964

                x1.setText(String.valueOf(0.5));
                x2.setText(String.valueOf(0.6));
                x3.setText(String.valueOf(0.7));
                x4.setText(String.valueOf(0.8));
                x5.setText(String.valueOf(0.9));

                y1.setText(String.valueOf(1.337904));
                y2.setText(String.valueOf(1.401593));
                y3.setText(String.valueOf(1.461175));
                y4.setText(String.valueOf(1.516552));
                y5.setText(String.valueOf(1.567650));


                break;
            case 1:
                //TODO: roman input
                break;
            case 2:
                //TODO: andrew input
                break;
            default:
        }
    }
    private void drawOnPlot(Polynom lagrangian){
        // create a couple arrays of y-values to plot:
        final Number[] domainLabels = new Number[LENGTH];
        Number[] series1Numbers = new Number[LENGTH];
        Number[] series2Numbers = new Number[LENGTH];

        final double x[] = {
                Double.valueOf(x1.getText().toString()),
                Double.valueOf(x2.getText().toString()),
                Double.valueOf(x3.getText().toString()),
                Double.valueOf(x4.getText().toString()),
                Double.valueOf(x5.getText().toString())
        };
        final double y[] = {
                Double.valueOf(y1.getText().toString()),
                Double.valueOf(y2.getText().toString()),
                Double.valueOf(y3.getText().toString()),
                Double.valueOf(y4.getText().toString()),
                Double.valueOf(y5.getText().toString())
        };



        double  xi=-5;
        for(int pos=0;pos<LENGTH;pos++){
            series1Numbers[pos]=lagrangian.val(xi);
            series2Numbers[pos]=Polynom.newtone(x,y,xi);
            xi+=X_DELTA;
        }


        // turn the above arrays into XYSeries':
        // (Y_VALS_ONLY means use the element index as the x value)
        XYSeries series1 = new SimpleXYSeries(
                Arrays.asList(series1Numbers), SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "Series1");
        XYSeries series2 = new SimpleXYSeries(
                Arrays.asList(series2Numbers), SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "Series2");

        // create formatters to use for drawing a series using LineAndPointRenderer
        // and configure them from xml:
        LineAndPointFormatter series1Format =
                new LineAndPointFormatter(this, R.xml.line_point_formatter_with_labels);

        LineAndPointFormatter series2Format =
                new LineAndPointFormatter(this, R.xml.line_point_formatter_with_labels_2);

        // add an "dash" effect to the series2 line:
        series2Format.getLinePaint().setPathEffect(new DashPathEffect(new float[] {
                // always use DP when specifying pixel sizes, to keep things consistent across devices:
                PixelUtils.dpToPix(1),
                PixelUtils.dpToPix(1)}, 0)
        );

        // just for fun, add some smoothing to the lines:
        // see: http://androidplot.com/smooth-curves-and-androidplot/
        series1Format.setInterpolationParams(
                new CatmullRomInterpolator.Params(10, CatmullRomInterpolator.Type.Centripetal));

        series2Format.setInterpolationParams(
                new CatmullRomInterpolator.Params(10, CatmullRomInterpolator.Type.Centripetal));

        // add a new series' to the xyplot:
        plot.addSeries(series1, series1Format);
        plot.addSeries(series2, series2Format);
        plot.invalidate();

        plot.getGraph().getLineLabelStyle(XYGraphWidget.Edge.BOTTOM).setFormat(new Format() {
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
}
