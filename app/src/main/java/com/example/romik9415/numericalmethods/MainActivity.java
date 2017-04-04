package com.example.romik9415.numericalmethods;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

//TODO all labs:
//      4.Інетрування функційй за дпомогою полінома Лагранжа та Нютона
//      5. Апроксимарія ф. за допомогою методів найменших квадратів
//      6. Розв'язання невизначостей методомо лінійних алгебраїчних систем.
//      7. Числове диференціована дисперсійно заданих функцій ?
//      8. Числові інтегрування функцій.
//      9. розв-ня задач Коші методом ПІ-ка-Ро
//      10 Розв'язання Коші метод. Руаге-Кутта

    int iterations = 0;
    double result = 0;
    TextView LU_l;
    TextView LU_u;
    TextView result_lu;
    TextView gause_matrix;
    TextView gause_result;
    TextView test;
    TextView testLU;

    double[][] matrixCopy;
    double[][] matrixCopy2;
    double b[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //mike added
        startActivity(new Intent(MainActivity.this,Lab4.class));
//        if (savedInstanceState == null) {
//            Fragment fragment = null;
//            Class fragmentClass = null;
//            FragmentTransaction mFragmentTransaction;
//            fragmentClass = Lab1.class;
//            try {
//                fragment = (Fragment) fragmentClass.newInstance();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            FragmentManager fragmentManager = getSupportFragmentManager();
//            fragmentManager.beginTransaction().replace(R.id.flContent, new Lab1()).commit();
//        }

        //----------------------------------------------Lab1-----------------------------------------------------//
//        final EditText leftEnd = (EditText) findViewById(R.id.leftEnd);
//        final EditText rightEnd = (EditText) findViewById(R.id.right_end);
//        final EditText eps = (EditText) findViewById(R.id.eps);
//
//        final TextView method1Res = (TextView) findViewById(R.id.method1_res);
//        final TextView method1Itr = (TextView) findViewById(R.id.method1_itr);
//
//        final TextView method2Res = (TextView) findViewById(R.id.method2_res);
//        final TextView method2Itr = (TextView) findViewById(R.id.method2_itr);
//
//        final TextView method3Res = (TextView) findViewById(R.id.method3_res);
//        final TextView method3Itr = (TextView) findViewById(R.id.method3_itr);
//
//        final TextView method4Res = (TextView) findViewById(R.id.method4_res);
//        final TextView method4Itr = (TextView) findViewById(R.id.method4_itr);
        //----------------------------------------------Lab2-----------------------------------------------------//
        //Gause
        final double[][] gauseMatrix = {{44.60, 5.50, -6.00, 3.43, 241.28}, {5.34, 42.54, 6.35, -3.65, 240.13}, {4.34, 3.51, -45.52, 3.34, -12.08}, {3.45, -2.42, 4.32, 56.46, 254.57}};

        //LU method
        final double[][] matrix = {{44.60, 5.50, -6.00, 3.43}, {5.34, 42.54, 6.35, -3.65}, {4.34, 3.51, -45.52, 3.34}, {3.45, -2.42, 4.32, 56.46}};
        matrixCopy = matrix;
        matrixCopy2 = matrix;
        b = new double[]{241.28, 240.13, -12.08, 254.57};
        final int matrixSize = 4;

        LU_l = (TextView) findViewById(R.id.lu_l);
        LU_u = (TextView) findViewById(R.id.lu_u);
        result_lu = (TextView) findViewById(R.id.result_LU);
        gause_matrix = (TextView) findViewById(R.id.gause_matrix);
        gause_result = (TextView) findViewById(R.id.gause_result);
        test = (TextView) findViewById(R.id.test);
        testLU = (TextView) findViewById(R.id.testLU);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
//                double dLEft = Double.valueOf(String.valueOf(leftEnd.getText()));
//                double dRight = Double.valueOf(String.valueOf(rightEnd.getText()));
//                double dEps = Double.valueOf(String.valueOf(eps.getText()));
//
//                simpleIteration(dLEft, dRight, dEps);
//                method1Res.setText(String.valueOf(result));
//                method1Itr.setText(String.valueOf(iterations));
//
//                divide(dLEft, dRight, dEps);
//                method2Res.setText(String.valueOf(result));
//                method2Itr.setText(String.valueOf(iterations));
//
//                metodNuton(dLEft, dRight, dEps);
//                method3Res.setText(String.valueOf(result));
//                method3Itr.setText(String.valueOf(iterations));
//
//                methodChord(dLEft, dRight, dEps);
//                method4Res.setText(String.valueOf(result));
//                method4Itr.setText(String.valueOf(iterations));
                //------------------------Lab2------------------------------//
                solveGause(gauseMatrix, matrixSize);
                solveLU(matrix, matrixSize);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    //------------------------------Gause----------------------------------------//
    double[] gauseX = new double[4];

    private void solveGause(double[][] matrix, int matrixSize) {
        getZeros(matrix);
        print(matrix);
        getGauseX(matrix);
        preintGauseResult();
        printTestGause(test, matrix);
        printTestGause(testLU, matrix);
    }

    private void printTestGause(TextView test, double[][] matrix) {
        double xResult = 0;
        test.append("Test:\n");
        for (int i = 0; i < 4; i++, xResult = 0) {
            for (int j = 0; j < 4; j++) {
                test.append(String.valueOf(matrixCopy[i][j]) + "*" + new DecimalFormat("##.##").format(gauseX[j]) + " + ");
                xResult = xResult + matrixCopy[i][j] * gauseX[j];
            }
            test.append("=" + String.valueOf(new DecimalFormat("##.##").format(xResult)) + ";\n");
        }
    }

    private void preintGauseResult() {
        for (int i = 0; i < gauseX.length; i++) {
            gause_result.append("X" + (i + 1) + "= " + String.valueOf(gauseX[i]) + "\n");
        }
    }

    private void getGauseX(double[][] matrix) {
        gauseX[3] = matrix[3][4] / matrix[3][3];
        gauseX[2] = (matrix[2][4] - gauseX[3] * matrix[2][3]) / matrix[2][2];
        gauseX[1] = (matrix[1][4] - (gauseX[2] * matrix[1][2] + gauseX[3] * matrix[1][3])) / matrix[1][1];
        gauseX[0] = (matrix[0][4] - (gauseX[1] * matrix[0][1] + gauseX[2] * matrix[0][2] + gauseX[3] * matrix[0][3])) / matrix[0][0];
    }

    private void print(double[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            gause_matrix.append("[");
            for (int j = 0; j < matrix[i].length - 1; j++) {
                //System.out.print(matrix[i][j] + " ");
                gause_matrix.append(String.valueOf(new DecimalFormat("##.##").format(matrix[i][j])) + "; ");
            }
            gause_matrix.append(" || " + String.valueOf(new DecimalFormat("##.##").format(matrix[i][4])));
            gause_matrix.append("]\n");
        }
    }

    private double[][] getZeros(double[][] matrix) {
        double[][] u = matrix;
        double coff;
        for (int i = 0; i < 3; i++) {
            for (int j = 1 + i; j < 4; j++) {
                Log.i("textG", "Start new line");
                coff = (u[j][i] * (-1)) / u[i][i];
                //Set number to U
                //Log.i("info"," up1 ="+String.valueOf(matrix[j][i]));
                for (int k = 0; k < 5; k++) {
//                    Log.i("info"," up ="+String.valueOf(matrix[j][1]));
                    Log.i("textG", "mat" + String.valueOf(matrix[j][k]));
                    Log.i("textG", "coff" + String.valueOf(coff));
                    u[j][k] = matrix[i][k] * coff + matrix[j][k];
                    Log.i("textG", "u==" + String.valueOf(u[j][k]));
                }

//                Log.i("info", "fsfsfsfs"+String.valueOf(u[j][i]));
            }
        }
        return u;
    }

    //------------------------------LU----------------------------------------//
    public double[][] l;
    public double[][] u;
    public double[] y = new double[4];
    public double[] x = new double[4];

    private void solveLU(double[][] matrix, int matrixSize) {
        l = new double[matrixSize][matrixSize];
        setNumOne();
        u = getUandL(matrix);
        print(l, LU_l);
        print(u, LU_u);
        getY();
        getX();
        printResult();
        //printTestLU(testLU,matrix);
    }

//    private void printTestLU(TextView test, double[][] matrix) {
//        test.append("LU test:\n");
//        double xSum=0;
//        for(int i=0;i<4;i++,xSum=0){
//            for (int j=0;j<4;j++){
//                test.append(String.valueOf(matrixCopy2[i][j])+"*"+x[j]+" + ");
//                xSum = xSum+matrixCopy2[i][j]*x[j];
//            }
//            test.append("="+String.valueOf(xSum)+";\n");
//        }
//    }
//
//    private void printTestLU(TextView test, double[][] matrix) {
//        test.append("LU test:\n");
//        double xSum=0;
//        for(int i=0;i<4;i++,xSum=0){
//            for (int j=0;j<4;j++){
//                test.append(String.valueOf(new DecimalFormat("##.##").format(matrixCopy2[i][j]))+"*"+new DecimalFormat("##.##").format(x[j])+" + ");
//                xSum = xSum+matrixCopy2[i][j]*x[j];
//            }
//            test.append("="+String.valueOf(new DecimalFormat("##.##").format(xSum))+";\n");
//        }
//    }

    private void printResult() {
        for (int i = 0; i < 4; i++) {
            result_lu.append("x" + (i + 1) + "=" + String.valueOf(x[i]) + "\n");
        }
    }

    private void getX() {
        x[3] = y[3] / u[3][3];
        x[2] = (y[2] - x[3] * u[2][3]) / u[2][2];
        x[1] = (y[1] - (x[2] * u[1][2] + x[3] * u[1][3])) / u[1][1];
        x[0] = (y[0] - (x[1] * u[0][1] + x[2] * u[0][2] + x[3] * u[0][3])) / u[0][0];
    }

    private void getY() {
        y[0] = b[0];
        y[1] = b[1] - y[0] * l[1][0];
        y[2] = b[2] - (y[0] * l[2][0] + y[1] * l[2][1]);
        y[3] = b[3] - (y[0] * l[3][0] + y[1] * l[3][1] + y[2] * l[3][2]);
        Log.i("y_", "y==" + y[0] + y[1] + y[2] + y[3]);
    }

    private void print(double[][] maMatrix, TextView textView) {
        for (int i = 0; i < maMatrix.length; i++) {
            textView.append("[");
            for (int j = 0; j < maMatrix[i].length; j++) {
                System.out.print(maMatrix[i][j] + " ");
                textView.append(String.valueOf(new DecimalFormat("##.##").format(maMatrix[i][j])) + "; ");
            }
            textView.append("]\n");
        }
    }

    private void setNumOne() {
        for (int i = 0; i < 4; i++) {
            l[i][i] = 1;
        }
    }

    private void setZeros(double[][] matrix) {
        int matrixSize = 4;
        for (int i = 0; i < matrixSize; i++) {
            for (int j = 0; j < matrixSize; j++) {
                matrix[i][j] = 0;
            }
        }
    }

    private double[][] getUandL(double[][] matrix) {
        double[][] u = matrix;
        double coff;
        for (int i = 0; i < 3; i++) {
            for (int j = 1 + i; j < 4; j++) {
                Log.i("text", "mat" + String.valueOf(matrix[j][0]));
                coff = (u[j][i] * (-1)) / u[i][i];
                //Set number to L
                l[j][i] = coff * (-1);

                //Set number to U
                //Log.i("info"," up1 ="+String.valueOf(matrix[j][i]));
                for (int k = 0; k < 4; k++) {
//                    Log.i("info"," up ="+String.valueOf(matrix[j][1]));
                    Log.i("text", "mat" + String.valueOf(matrix[j][k]));
                    Log.i("text", "coff" + String.valueOf(coff));
                    u[j][k] = matrix[i][k] * coff + matrix[j][k];

                }
//                Log.i("info", "fsfsfsfs"+String.valueOf(u[j][i]));
            }
        }
        return u;
    }


    //Lab 1 Numerical Methods

//    public double myFunction(double x) {
//        return (pow(x, 2) - sin(x));
//    }
//
//    public double iteration(double x) {
//        return sqrt(sin(x));
//    }
//
//
//    public double firstDerivative(double x) {
//        return (2 * x - cos(x));
//    }
//
//
//    public double secondDerivative(double x) {
//        return (2 + sin(x));
//    }
//
//
//    void simpleIteration(double a, double b, double e) {
//        double x0;
//        double x1;
//        x0 = (a + b) / 2;
//        x1 = iteration(x0);
//        int i;
//        for (i = 0; abs(x1 - x0) >= e; i++) {
//            x0 = x1;
//            x1 = iteration(x1);
//        }
//        iterations = i;
//        result = x1;
//    }
//
//    void divide(double a, double b, double eps) {
//        double x0 = (a + b) / 2;
//        double x1 = 0;
//        int i;
//        if (myFunction(x0) > 0) {
//            x1 = ((a + x0) / 2);
//        } else {
//            x1 = (x0 + b) / 2;
//        }
//        for (i = 0; abs(x1 - x0) >= eps; i++) {
//            x0 = x1;
//            if (myFunction(x1) > 0) {
//                b = x0;
//            } else {
//                a = x1;
//            }
//            x1 = (a + b) / 2;
//        }
//        iterations = i;
//        result = x1;
//    }
//
//    void metodNuton(double a, double b, double eps) {
//        double x1;
//        double x0;
//        int i;
//        if (myFunction(b) * secondDerivative(b) > 0) {
//            x0 = b;
//        } else {
//            x0 = a;
//        }
//        x1 = x0 - myFunction(x0) * firstDerivative(x0);
//        for (i = 0; abs(myFunction(x1)) >= eps; i++) {
//            x0 = x1;
//            x1 = x1 - myFunction(x1) * firstDerivative(x1);
//        }
//        iterations = i;
//        result = x1;
//    }
//
//    void methodChord(double a, double b, double eps) {
//        double x0, x1;
//        int i = 0, flag = 1;
//        if (myFunction(b) * secondDerivative(b) > 0) {
//            x0 = a;
//            x1 = x0 - myFunction(x0) * (b - x0) / (myFunction(b) - myFunction(x0));
//            flag = 1;
//        } else {
//            x0 = b;
//            x1 = x0 - myFunction(x0) * (a - x0) / (myFunction(a) - myFunction(x0));
//        }
//        for (i = 0; abs(x1 - x0) >= eps; i++) {
//            x0 = x1;
//            if (flag == 1) {
//                x1 = x1 - ((myFunction(x1) * (b - x1) / (myFunction(b) - myFunction(x1))));
//            } else {
//                x1 = x1 - (myFunction(x1) * (a - x1) / (myFunction(a) - myFunction(x1)));
//            }
//        }
//        iterations = i;
//        result = x1;
//
//    }
//    boolean isGood(double a, double b){
//        for(double x = a;x<b;x+=0.001){
//            double current = firstDerivative(x);
//            if(current>1){
//                return false;
//            }
//        }
//        return true;
//    }
    //------------------------------Lab2------------------------------//
    //------------------------------LU-method------------------------------//

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            Intent intent = new Intent(this, Lab3.class);
            startActivity(intent);
        } else if (id == R.id.lab4) {
            startActivity(new Intent(MainActivity.this,Lab4.class));
        } else if (id == R.id.lab5) {

        } else if (id == R.id.lab6) {

        } else if (id == R.id.lab7) {

        } else if (id == R.id.lab8) {

        } else if (id == R.id.lab9) {


        } else if (id == R.id.lab10) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
