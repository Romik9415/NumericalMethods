package com.example.romik9415.numericalmethods;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import java.text.DecimalFormat;

public class Lab_10_RungeKutta extends AppCompatActivity {

    double x0 = 4.0;//TODO x0
    double xn = 5.0;//TODO xn [x0;xn]
    double y0 = -1.0;// TODO Значення в точці

    EditText editTextlab10Result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab_10__runge_kutta);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        editTextlab10Result = (EditText) findViewById(R.id.result_lab10);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int number;
                number = 20;
                //System.out.println("Result of Euler Method y = " + solveByEulerMethod(number));
                System.out.println("Result of Runge-Kutta Method y = " + solveByRunge_KuttaMethod(number));
                //Log.d("Result",String.valueOf(solveByRunge_KuttaMethod(number)));

            }
        });
    }

    //

    public static double differentialFunction(double x, double y) {
        return (1-x*x)/x*y;// TODO change to your formua
    }



//    public double solveByEulerMethod(int number) {
//        double y;
//        int i = 0;
//        double h = (xn - x0) / number;
//        System.out.println("Solve by Method Euler:");
//        System.out.println("x[" + i + "] = " + x0 + ", y[" + i + "] = " + y0);
//        double X[] = new double[number];
//        double Y[] = new double[number];
//        X[0] = x0;
//        Y[0] = y0;
//        for (int j = 1; j < number; j++) {
//            X[j] = x0 + j * h;
//            Y[j] = Y[j - 1] + h * differentialFunction(X[j - 1], Y[j - 1]);
//            System.out.println("x[" + j + "] = " + Round.round(X[j], 3) + ", y[" + j + "] = " + Round.round(Y[j], 5));
//        }
//        y = Y[number - 1];
//        return y;
//    }
    public static double differrencesRunge_KuttaMethod(double x, double y, double h) {
        double k1, k2, k3, k4;
        k1 = h * differentialFunction(x, y);
        k2 = h * differentialFunction(x + h / 2, y + k1 / 2);
        k3 = h * differentialFunction(x + h / 2, y + k2 / 2);
        k4 = h * differentialFunction(x + h, y + k3);
        return (k1 + 2 * k2 + 2 * k3 + k4) / 6;
    }

    public double solveByRunge_KuttaMethod(int number) {
        double y = 0;
        int i = 0;
        double h = (xn - x0) / number;
        editTextlab10Result.append("Solve by Method Runge-Kutta:\n");
        System.out.println("Solve by Method Runge-Kutta:\n");
        editTextlab10Result.append("x[" + i + "] = " + x0 + ", y[" + i + "] = " + y0+"\n");
        System.out.println("x[" + i + "] = " + x0 + ", y[" + i + "] = " + y0+"\n");
        double X[] = new double[number];
        double Y[] = new double[number];
        X[0] = x0;
        Y[0] = y0;
        for (int j = 1; j < number; j++) {
            X[j] = x0 + j * h;
            Y[j] = Y[j - 1] + differrencesRunge_KuttaMethod(X[j - 1], Y[j - 1], h);
            editTextlab10Result.append("x[" + j + "] = " + String.valueOf(new DecimalFormat("##.##").format(X[j])) + ", y[" + j + "] = " + String.valueOf(new DecimalFormat("##.####").format(Y[j]))+"\n");
            System.out.println("x[" + j + "] = " + String.valueOf(new DecimalFormat("##.##").format(X[j])) + ", y[" + j + "] = " + String.valueOf(new DecimalFormat("##.####").format(Y[j])));
        }
        y = Y[number - 1];
        return y;
    }
}
