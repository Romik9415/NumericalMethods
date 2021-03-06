package com.example.romik9415.numericalmethods;

import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Pair;
import android.view.View;
import android.widget.EditText;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
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
    GraphView graph;
    private float X_DELTA = .3f;
    private int RIGHT_LIMIT = 5;
    private int LEFT_LIMIT = -RIGHT_LIMIT;
    private int LENGTH = 100000;


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

                try {

                    Polynom N = Polynom.newtonian(x,y);
                    output.setText(
                        Polynom.testLagrangian(Polynom.lagrangian(polynomInput), polynomInput)
                        + "\n" +
                        Polynom.testNewtone2(N, polynomInput)
                        + "\n" +
                        Polynom.newtonian(x,y).toStr()
                    );

                drawOnPlot(Polynom.lagrangian(polynomInput), N);


                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        //drawOnPlot(Polynom.lagrangian(polynomInput));

    }


    private void initialize() {
        x1 = (EditText) findViewById(R.id.x1);
        x2 = (EditText) findViewById(R.id.x2);
        x3 = (EditText) findViewById(R.id.x3);
        x4 = (EditText) findViewById(R.id.x4);
        x5 = (EditText) findViewById(R.id.x5);

        y1 = (EditText) findViewById(R.id.y1);
        y2 = (EditText) findViewById(R.id.y2);
        y3 = (EditText) findViewById(R.id.y3);
        y4 = (EditText) findViewById(R.id.y4);
        y5 = (EditText) findViewById(R.id.y5);
        output = (EditText) findViewById(R.id.output);

        graph = (GraphView) findViewById(R.id.graph);
    }

    private void setXYParams(int mikeRomanOrAndrew012) {
        switch (mikeRomanOrAndrew012) {
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

    private void drawOnPlot(Polynom lagrangian, Polynom newtonian) {
        // create a couple arrays of y-values to plot:

        LineGraphSeries<DataPoint> series = new LineGraphSeries<>();
        LineGraphSeries<DataPoint> series2 = new LineGraphSeries<>();

        /*
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
*/

        double xi = LEFT_LIMIT;
        for (int pos = 0; pos < LENGTH; pos++) {
            series.appendData(new DataPoint(xi, lagrangian.val(xi)), true, LENGTH);
            series2.appendData(new DataPoint(xi, newtonian.val(xi)), true, LENGTH);
            xi += X_DELTA;
        }

        series2.setColor(Color.YELLOW);
        series.setColor(Color.BLUE);
        series2.setAnimated(true);

        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(15);
        paint.setPathEffect(new DashPathEffect(new float[]{16, 0}, 0));
        series2.setCustomPaint(paint);

        graph.getViewport().setScalable(true);
        graph.getViewport().setScrollable(true);
        //graph.addSeries(series2);
        graph.addSeries(series);
    }
}
